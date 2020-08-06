<?php
	$connect = mysqli_connect('maison.cadwyruywj2k.us-east-2.rds.amazonaws.com:3306','admin','sswu1234','maison');
	$userID = $_POST["userID"];
	$userPW = $_POST["userPW"];
	$gender = $_POST["gender"];
	$age = $_POST["age"];

	$statement = mysqli_prepare($connect, "INSERT INTO user VALUES (?,?,?,?)");
	mysqli_stmt_bind_param($statement, "ssss", $userID, $userPW, $gender, $age);
	mysqli_stmt_execute($statement);

	$response = array();
	$response["success"] = true;
	

	echo json_encode($response);
?>