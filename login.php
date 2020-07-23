<?php
	$connect = mysqli_connect('maison.c22zsfchgrm1.ap-northeast-2.rds.amazonaws.com:3306','admin','sswu1234','maison');
	$userID = $_POST["userID"];
	$userPW = $_POST["userPW"];

	$statement = mysqli_prepare($connect, "SELECT * FROM user WHERE userID=? and userPW=?");
	mysqli_stmt_bind_param($statement, "ss", $userID, $userPW);
	mysqli_stmt_execute($statement);

	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $userID, $userPW, $gender, $age, $skin, $interest);

	$response = array();
	$response["success"] = false;
	
	while(mysqli_stmt_fetch($statement)){
		$response["success"] = true;
		$response["userID"] = $userID;
		$response["userPW"] = $userPW;
		$response["gender"] = $gender;
		$response["age"] = $age;
		$response["skinType"] = $skin;
		$response["interest"] = $interest;
	}

	echo json_encode($response);
?>