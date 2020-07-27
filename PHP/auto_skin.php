<?php
	session_start();
	date_default_timezone_set('Asia/Seoul');

#데이터베이스에 바로저장? 아니면 php (파일권한 chmod 755)에서 받아서 처리? -> 추후
	$connect = mysqli_connect('maison.c22zsfchgrm1.ap-northeast-2.rds.amazonaws.com:3306','admin','sswu1234','maison');
	if(isset($_GET['moisture']) && isset($_GET['oil']) && isset($_GET['clean']) && isset($_GET['pimple'])){
	$clean = $_GET["clean"]; #여기부터 pimple까지는 측정기기에서 받아온 데이터로 저장할 수 있게끔 바꿔야함
	$moisture = $_GET["moisture"];
	$oil = $_GET["oil"];
	$pimple = $_GET["pimple"];
	$skinDate = date("Y-m-d H:i:s"); #시간은 현재시간으로
	$userID = $_SESSION["userID"]; #세션에 연결되어있는 user

	$statement = mysqli_prepare($connect, "INSERT INTO skinitem VALUES (?,?,?,?,?,?,?)");
	mysqli_stmt_bind_param($statement, "issssss", $skinnum, $userID, $clean, $moisture, $oil, $pimple, $skinDate); #skinnum은 자동 증가
	mysqli_stmt_execute($statement);

	$response = array();
	$response["success"] = true;
	
}
	echo json_encode($response);
?>