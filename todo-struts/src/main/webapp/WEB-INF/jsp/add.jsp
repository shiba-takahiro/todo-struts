<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>作業登録</title>
<link rel="STYLESHEET" href="css/normalize.css" type="text/css">
<link rel="STYLESHEET" href="css/main.css" type="text/css">
</head>
<body>
<div class="container">
	<header>
		<div class="title">
			<h1>作業登録</h1>
		</div>
		<div class="login_info">
			<ul>
				<li>ようこそ<s:property value="#session.currentUser.name" />さん</li>
				<li><a href="logout">ログアウト</a></li>
			</ul>
		</div>
	</header>

	<main>
		<s:actionerror />
		<s:fielderror fieldName="name"/>
		<s:fielderror fieldName="expireDate"/>
		<s:form action="add_action">
			<table class="item">
				<tr>
					<th>項目名</th>
					<td>
						<s:textfield name="name" value="" />
					</td>
				</tr>
				<tr>
					<th>担当者</th>
					<td>
						<s:select name="userId" value="#session.currentUser.id"
							list="users" listValue="name" listKey="id" />
					</td>
				</tr>
				<tr>
					<th>期限</th>
					<td>
						<input type="date" name="expireDate"
							value='<s:date name="expireDate" format="yyyy-MM-dd"/>'/>
					</td>
				</tr>
			</table>
			<s:submit value="登録" />
			<button type="button" onclick="location.href='list'">キャンセル</button>
		</s:form>
	</main>

	<footer>

	</footer>
</div>
</body>
</html>