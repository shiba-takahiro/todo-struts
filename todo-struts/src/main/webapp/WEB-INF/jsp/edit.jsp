<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>作業更新</title>
<link rel="STYLESHEET" href="css/normalize.css" type="text/css">
<link rel="STYLESHEET" href="css/main.css" type="text/css">
</head>
<body>
<div class="container">
	<header>
		<div class="title">
			<h1>作業更新</h1>
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
		<s:form action="edit_action">
			<s:hidden name="itemId" value="%{#session.item.id}" />
			<table class="item">
				<tr>
					<th>項目名</th>
					<td>
						<s:textfield name="name" value="%{#session.item.name}"/>
					</td>
				</tr>
				<tr>
					<th>担当者</th>
					<td>
						<s:select name="userId" value="%{#session.item.user.id}"
							list="#session.users" listValue="name" listKey="id" />
					</td>
				</tr>
				<tr>
					<th>期限</th>
					<td>
						<s:textfield name="expireDate" value="%{#session.item.expireDate}"/>
					</td>
				</tr>
				<tr>
					<th>完了</th>
					<td>
						<s:checkbox name="finished" value="%{#session.item.finished}"/> 完了した
					</td>
				</tr>
			</table>
		</s:form>
		<button type="button" onclick="document.forms[0].submit()">更新</button>
		<button type="button" onclick="location.href='list'">キャンセル</button>
	</main>

	<footer> </footer>
</div>
</body>
</html>