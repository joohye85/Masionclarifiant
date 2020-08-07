<?php
	session_start();
	date_default_timezone_set('Asia/Seoul');

	$connect = mysqli_connect('maison.c22zsfchgrm1.ap-northeast-2.rds.amazonaws.com:3306','admin','sswu1234','maison');
	$costype = "직접배합";
	$i_name = $_POST["i_name"];
	$perfume = $_POST["perfume"];
	$skinType = $_POST["skinType"];
	$createDate = date("Y-m-d H:i:s"); #시간은 현재시간으로
	$userID = $_SESSION["userID"]; #세션에 연결되어있는 user
	$feedback = null;

	
	if(isset($i_name) && isset($perfume) && isset($skinType)){
		$statement = mysqli_prepare($connect, "INSERT INTO cositem VALUES (?,?,?,?,?)");
		mysqli_stmt_bind_param($statement, "issss", $cosnum, $userID, $costype, $createDate,$feedback); #skinnum은 자동 증가
		mysqli_stmt_execute($statement);
		$statement = mysqli_prepare($connect, "INSERT INTO user_cos VALUES (?,?,?,?)");
		mysqli_stmt_bind_param($statement, "isss", $cosnum, $i_name, $perfume, $skinType);
		mysqli_stmt_execute($statement);
		$response = array();
		$response["success"] = 0;
	}
	else{
		$response["success"] = 1; #재료 전달 못받음
	}

	echo json_encode($response);
?>