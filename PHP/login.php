<?php
	session_start();
	$connect = mysqli_connect('maison.cadwyruywj2k.us-east-2.rds.amazonaws.com:3306','admin','sswu1234','maison');
	$userID = $_POST["userID"];
	$userPW = $_POST["userPW"];

	
	$statement = mysqli_prepare($connect, "SELECT * FROM user WHERE userID=? and userPW=?");
	mysqli_stmt_bind_param($statement, "ss", $userID, $userPW);
	mysqli_stmt_execute($statement);

	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $userID, $userPW, $gender, $age, $skin, $interest);

	$_SESSION = array();
  	$_SESSION["success"] = false;
	
	if(!empty($_POST["userID"]) && !empty($_POST["userPW"])){ 
		while(mysqli_stmt_fetch($statement)){
		$_SESSION["success"] = true;
   		$_SESSION['userID'] = $userID;
    	$_SESSION['userPW'] = $userPW;
    	$_SESSION["gender"] = $gender;
    	$_SESSION["age"] = $age;
    	$_SESSION["skinType"] = $skin;
    	$_SESSION["interest"] = $interest;
	}
}
	$json = json_encode($_SESSION);
  	echo $json;
?>