<?php 
	session_start();
	$connect = mysqli_connect('maison.c22zsfchgrm1.ap-northeast-2.rds.amazonaws.com:3306','admin','sswu1234','maison');

	$statement = mysqli_prepare($connect, "SELECT * FROM skinitem WHERE userID=? ORDER BY skinDate DESC limit 0,5");
	mysqli_stmt_bind_param($statement, "s", $_SESSION["userID"]);
	mysqli_stmt_execute($statement);

	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $skinnum, $userID, $clean, $moisture, $oil, $pimple, $skinDate);

	
  	$_SESSION["success"] = 2; 
	
  	if(!isset($_SESSION['userID'])){
  		$_SESSION["success"] = 2; #데이터를 읽어올 수 없음 
  	}else{

  		while(mysqli_stmt_fetch($statement)){
  		$_SESSION["success"] = 0; #정상 작동
   		$_SESSION["skinnum"] = $skinnum;
   		$_SESSION["userID"] = $userID;
    	$_SESSION["clean"] = $clean;
    	$_SESSION["moisture"] = $moisture;
    	$_SESSION["oil"] = $oil;
    	$_SESSION["pimple"] = $pimple;
    	$_SESSION["skinDate"] = $skinDate;
    	$json = json_encode($_SESSION); #데이터 쌓아두기
  		echo $json;
  	}
  }
?>
