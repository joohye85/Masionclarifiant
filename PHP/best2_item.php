<?php
if(!isset($_SESSION)){
	session_start();
}
	date_default_timezone_set('Asia/Seoul');

	$connect = mysqli_connect('maison.c22zsfchgrm1.ap-northeast-2.rds.amazonaws.com:3306','admin','sswu1234','maison');	
	$resp = array();
	
	$statement = mysqli_prepare($connect, "SELECT i_name, skinType, count(*) as CNT FROM user_cos GROUP BY i_name ORDER BY CNT DESC LIMIT 1, 1");
	//mysqli_stmt_bind_param($statement);
	mysqli_stmt_execute($statement);
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $i_name, $skinType, $CNT);
	while (mysqli_stmt_fetch($statement)) {
			#$_SESSION["createDate"] = $createDate;
		$resp["i_name"] = $i_name;
		$resp["skinType"] = $skinType;
		$resp["count"] = $CNT;
		$resp["success"] = true;
		echo json_encode($resp);

		}
		
	
?>