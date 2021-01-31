<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>ログイン</title>
<link rel="STYLESHEET" href="css/normalize.css" type="text/css">
<link rel="STYLESHEET" href="css/main.css" type="text/css">
<s:head />
</head>
<body>
<div class="container">
	<header>
		<h1>ログイン</h1>
	</header>

	<main>
		<s:actionerror />
		<s:fielderror fieldName="username"/>
		<s:fielderror fieldName="password"/>
		<s:form action="login_action">
			<table class="login">
				<tr>
					<th><s:label value="ユーザ名"/></th>
					<td>
						<s:textfield name="username" id="username" />
					</td>
				</tr>
				<tr>
					<th><s:label value="パスワード"/></th>
					<td>
						<s:password name="password" id="password" label="パスワード" />
					</td>
				</tr>
			</table>
			<s:submit value="ログイン" id="login" align="center" />
		</s:form>
	</main>

	<footer>

	</footer>
</div>
</body>
</html>