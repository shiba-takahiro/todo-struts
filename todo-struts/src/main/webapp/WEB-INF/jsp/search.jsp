<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>検索結果</title>
<link rel="STYLESHEET" href="css/normalize.css" type="text/css">
<link rel="STYLESHEET" href="css/main.css" type="text/css">
</head>
<body>
<div class="container">
	<header>
		<div class="title">
			<h1>検索結果</h1>
		</div>
		<div class="login_info">
			<ul>
				<li>ようこそ<s:property value="#session.currentUser.name" />さん</li>
				<li><a href="logout">ログアウト</a></li>
			</ul>
		</div>
	</header>

	<main>
		<div class="main-header">
			<div class="goback">
				<s:form action="list">
					<s:submit value="戻る" align="left" />
				</s:form>
			</div>
		</div>

		<table class="list">
			<tr>
				<th>項目名</th>
				<th>担当者</th>
				<th>期限</th>
				<th>完了</th>
				<th colspan="3">操作</th>
			</tr>

			<s:iterator value="items">
				<s:set var="className"></s:set>
				<s:if test="finished">
					<s:set var="className">finished</s:set>
				</s:if>
				<s:else>
					<s:if test="expired">
						<s:set var="className">warning</s:set>
					</s:if>
				</s:else>

				<tr class="${className}">
					<td class="align-left">
						<s:property value="name"/>
					</td>
					<td class="align-left">
						<s:property value="user.name"/>
					</td>
					<td>
						<s:property value="expireDate"/>
					</td>
					<td>
						<s:if test="finished">
							<s:property value="finishedDate" />
						</s:if>
						<s:else>
							未
						</s:else>
					</td>
					<td>
						<s:form action="finish">
							<s:hidden name="itemId" value="%{id}" />
							<s:hidden name="keyword" value="%{keyword}" />
							<s:if test="finished">
								<s:submit value="未完了" />
							</s:if>
							<s:else>
								<s:submit value="完了" />
							</s:else>
						</s:form>
					</td>
					<td>
						<s:form action="edit">
							<s:hidden name="itemId" value="%{id}" />
							<s:submit value="更新" />
						</s:form>
					</td>
					<td>
						<s:form action="delete">
							<s:hidden name="itemId" value="%{id}" />
							<s:submit value="削除" />
						</s:form>
					</td>
				</tr>
			</s:iterator>
		</table>

		<div class="main-footer">
			<div class="goback">
				<s:form action="list">
					<s:submit value="戻る" />
				</s:form>
			</div>
        </div>
	</main>

	<footer>

	</footer>
</div>
</body>
</html>