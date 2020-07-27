<?php
	session_start();
	date_default_timezone_set('Asia/Seoul');

	$connect = mysqli_connect('maison.c22zsfchgrm1.ap-northeast-2.rds.amazonaws.com:3306','admin','sswu1234','maison');
	$clean = $_POST["clean"];
	$moisture = $_POST["moisture"];
	$oil = $_POST["oil"];
	$pimple = $_POST["pimple"];
	$skinDate = date("Y-m-d H:i:s"); #시간은 현재시간으로
	$userID = $_SESSION["userID"]; #세션에 연결되어있는 user

	$statement = mysqli_prepare($connect, "INSERT INTO skinitem VALUES (?,?,?,?,?,?,?)");
	mysqli_stmt_bind_param($statement, "issssss", $skinnum, $userID, $clean, $moisture, $oil, $pimple, $skinDate); #skinnum은 자동 증가
	mysqli_stmt_execute($statement);

	$response = array();
	$response["success"] = true;
	

	echo json_encode($response);
?>