<?php
	session_start();
	$connect = mysqli_connect('maison.c22zsfchgrm1.ap-northeast-2.rds.amazonaws.com:3306','admin','sswu1234','maison');
	header("Content-Type: application/json;charset=utf-8");
	$userID = $_POST["userID"];
	$userPW = $_POST["userPW"];

	
	$statement = mysqli_prepare($connect, "SELECT * FROM user WHERE userID=? and userPW=?");
	mysqli_stmt_bind_param($statement, "ss", $userID, $userPW);
	mysqli_stmt_execute($statement);

	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $userID, $userPW, $gender, $age, $skinType, $interest);

	$_SESSION = array(); #세션 배열 생성
  	$_SESSION["success"] = false; 
	
	if(!empty($_POST["userID"]) && !empty($_POST["userPW"])){ 
		while(mysqli_stmt_fetch($statement)){
		$_SESSION["success"] = true;
   		$_SESSION["userID"] = $userID;
    	$_SESSION["userPW"] = $userPW;
    	$_SESSION["gender"] = $gender;
    	$_SESSION["age"] = $age;
    	$_SESSION["skinType"] = $skinType;
    	$_SESSION["interest"] = $interest;
 	}
}
	$json = json_encode($_SESSION);
  	echo $json;
?>