<?php
	session_start();
	date_default_timezone_set('Asia/Seoul');

	$connect = mysqli_connect('maison.c22zsfchgrm1.ap-northeast-2.rds.amazonaws.com:3306','admin','sswu1234','maison');
	$clean= $_POST["clean"];
	$moisture = $_POST["moisture"];
	$oil = $_POST["oil"];
	$pimple = $_POST["pimple"];
	$createDate = date("Y-m-d H:i:s"); #시간은 현재시간으로
	$userID = $_POST["userID"];
	$_SESSION = array();
	$userID2 = null;

	if(isset($oil) && isset($moisture) && isset($clean) && isset($pimple) && isset($userID)){
		$statement = mysqli_prepare($connect, "SELECT userID FROM user WHERE userID =?");
		mysqli_stmt_bind_param($statement, "s", $userID);
		mysqli_stmt_execute($statement);

		mysqli_stmt_store_result($statement);
		mysqli_stmt_bind_result($statement, $userID);
		while(mysqli_stmt_fetch($statement)){
			$userID2 = $userID;
		}
		if(!empty($userID2)){

			$statement = mysqli_prepare($connect, "INSERT INTO skinitem VALUES (?,?,?,?,?,?,?)");
			mysqli_stmt_bind_param($statement, "issssss", $skinnum, $userID, $clean, $moisture, $oil, $pimple, $createDate); #cosnum은 자동 증가
			mysqli_stmt_execute($statement);
			$_SESSION["success"] = true;
		}
		else{
		$_SESSION["success"] =  false; #유저 존재하지 않음
	}
}
	else{
		$_SESSION["success"] = false; #피부타입 전달 못받음
	}

	echo json_encode($_SESSION);
?>