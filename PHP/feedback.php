<?php
	session_start();
	date_default_timezone_set('Asia/Seoul');

	$connect = mysqli_connect('maison.c22zsfchgrm1.ap-northeast-2.rds.amazonaws.com:3306','admin','sswu1234','maison');	
	$userID = $_POST["userID"];
	$feedback = $_POST["feedback"];
	$_SESSION = array();
	$userID2 = null;
	
	$statement = mysqli_prepare($connect, "SELECT userID FROM user WHERE userID =?");
	mysqli_stmt_bind_param($statement, "s", $userID);
	mysqli_stmt_execute($statement);

	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $userID);
	while(mysqli_stmt_fetch($statement)){
		$userID2 = $userID;
	}
		if(!empty($userID2)){

			$statement = mysqli_prepare($connect, "UPDATE cositem SET feedback = '$feedback' WHERE userID =? ORDER BY cosnum DESC LIMIT 1");
			mysqli_stmt_bind_param($statement, "s", $userID); #skinnum은 자동 증가
			mysqli_stmt_execute($statement);

			
			$_SESSION["success"] = true;
	}else{
		$_SESSION["success"] = false; #재료 전달 못받음
	}

	echo json_encode($_SESSION);
?>