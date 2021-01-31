<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>削除確認</title>
<link rel="STYLESHEET" href="css/normalize.css" type="text/css">
<link rel="STYLESHEET" href="css/main.css" type="text/css">
</head>
<body>
	<div class="container">
		<header>
			<div class="title">
				<h1>削除確認</h1>
			</div>
			<div class="login_info">
				<ul>
					<li>ようこそ<s:property value="#session.currentUser.name" />さん
					</li>
					<li><a href="logout">ログアウト</a></li>
				</ul>
			</div>
		</header>

		<main>
			<s:actionerror />

			<p>下記の項目を削除します。よろしいですか？</p>
			<s:form action="delete_action">
				<s:hidden name="itemId" />
				<table class="item">
					<tr>
						<th>項目名</th>
						<td>
							<s:property value="item.name" />
						</td>
					</tr>
					<tr>
						<th>担当者</th>
						<td>
							<s:property value="item.user.name" />
						</td>
					</tr>
					<tr>
						<th>期限</th>
						<td>
							<s:date name="item.expireDate" format="yyyy/MM/dd"/>
						</td>
					</tr>
					<tr>
						<th>完了</th>
						<td>
							<s:if test="item.finished">
								<s:property value="item.finishedDate" />
							</s:if>
							<s:else>
								未
							</s:else>
						</td>
					</tr>
				</table>
				<s:submit value="削除" />
				<button type="button" onclick="location.href='list'">キャンセル</button>
			</s:form>
		</main>

		<footer> </footer>
	</div>
</body>
</html>
