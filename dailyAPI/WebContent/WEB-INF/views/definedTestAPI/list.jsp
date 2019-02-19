<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<head>
		<title>测试接口列表</title>
		<meta charset="UTF-8" />
		<script src="jquery-1.11.1.min.js" type="text/javascript" charset="utf-8"></script>
	</head>

	<body>
		<h2>自定义接口展示列表</h2>
		<br />
		
		<a href="/jupinhuiAPI/addTestAPI">增加新的自定义API</a>
		<br>
		<br>

		<table border="1px" cellpadding="0" cellspacing="0">
			<th style="width: 10%;">接口名</th>
			<th style="width: 70%;">内容</th>
			<th style="width: 10%;">操作</th>
			
			<c:forEach items="${all }" var="each">
				<tr>
					<td>
						${each.invokeName }
					</td>
					<td>
						${each.contain }
					</td>
					<td>
						<a href="/jupinhuiAPI/testAPIEdit?id=${each.id }">修改</a>
						<br />
						<a href="/jupinhuiAPI/testAPIDelete?id=${each.id }">删除</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</body>

</html>