<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>エラー</title>
<link rel="STYLESHEET" href="css/normalize.css" type="text/css">
<link rel="STYLESHEET" href="css/main.css" type="text/css">
</head>
<body>
<div class="container">
	<header>
		<div class="title">
			<h1>エラー</h1>
		</div>
	</header>

	<main>
		<p class="error">エラーが発生しました<br><s:actionerror /></p>
		<s:form action="login">
			<s:submit value="戻る" />
		</s:form>
	</main>

	<footer>

	</footer>
</div>
</body>
</html>
