<?php
	session_start();
	$connect = mysqli_connect('maison.c22zsfchgrm1.ap-northeast-2.rds.amazonaws.com:3306','admin','sswu1234','maison');
	header("Content-Type: application/json;charset=utf-8");
	$userID = $_POST["userID"];

	
	$statement = mysqli_prepare($connect, "SELECT * FROM user WHERE userID=?");
	mysqli_stmt_bind_param($statement, "s", $userID);
	mysqli_stmt_execute($statement);

	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $userID, $userPW, $gender, $age, $skinType, $interest);

	$_SESSION = array(); #세션 배열 생성
	if(mysqli_stmt_fetch($statement) >= 1){
		$_SESSION["userID"] = false;
	}else{
    	$_SESSION["userID"] = true;
	}

	
	$json = json_encode($_SESSION);
  	echo $json;
?>