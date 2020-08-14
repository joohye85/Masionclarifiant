<?php
	session_start();
	date_default_timezone_set('Asia/Seoul');

	$connect = mysqli_connect('maison.c22zsfchgrm1.ap-northeast-2.rds.amazonaws.com:3306','admin','sswu1234','maison');	
	$userID = $_POST["userID"];
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
		$statement = mysqli_prepare($connect, "SELECT cosnum, createDate FROM cositem WHERE userID =? ORDER BY cosnum DESC LIMIT 1");
		mysqli_stmt_bind_param($statement, "s", $userID);
		mysqli_stmt_execute($statement);
		mysqli_stmt_store_result($statement);
		mysqli_stmt_bind_result($statement, $cosnum, $createDate);
		while (mysqli_stmt_fetch($statement)) {
			$_SESSION["createDate"] = $createDate;
			# code...
		}
		$statement = mysqli_prepare($connect, "SELECT * FROM user_cos WHERE cosnum =?");
		mysqli_stmt_bind_param($statement, "i", $cosnum);
		mysqli_stmt_execute($statement);
		mysqli_stmt_store_result($statement);
		mysqli_stmt_bind_result($statement, $cosnum, $i_name, $perfume, $skinType);
		while (mysqli_stmt_fetch($statement)) {
			$_SESSION["i_name"] = $i_name;
			$_SESSION["skinType"] = $skinType;
			# code...
		}
		

		$_SESSION["success"] = true;
	}else{
		$_SESSION["success"] = false;
	}
	

	echo json_encode($_SESSION);
?>