<?php
	$connect = mysqli_connect('maison.c22zsfchgrm1.ap-northeast-2.rds.amazonaws.com:3306','admin','sswu1234','maison');
	$userID = $_POST["userID"];
	$userPW = $_POST["userPW"];
	$gender = $_POST["gender"];
	$age = $_POST["age"];
	$skinType = $_POST["skin"];
	$interest = $_POST["interest"];

	$statement = mysqli_prepare($connect, "INSERT INTO user VALUES (?,?,?,?,?,?)");
	mysqli_stmt_bind_param($statement, "sssiss", $userID, $userPW, $gender, $age, $skinType, $interest);
	mysqli_stmt_execute($statement);

	$response = array();
	$response["success"] = true;
	

	echo json_encode($response);
?>