<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<head>
		<title></title>
		<meta charset="UTF-8" />
		<script src="jquery-1.11.1.min.js" type="text/javascript" charset="utf-8"></script>
	</head>

	<body>
		<h2>新建自定义接口</h2>
		<br />
		<a href="/jupinhuiAPI/testAPIList">查看已有自定义API</a>
		<br>
		<br>

		<form action="/jupinhuiAPI/test/save">
			<input type="hidden" name="id" value="${id }">
			<label style="font-weight: 700;">请求名:</label>
			<br />
			<input name="name" value="${name }"/><label style="color: red">1,参数名中不能有"/"  2,使用时，需要在请求名后加".test"</label>
			<br />
			<label style="font-weight: 700;">status:</label>
			<br />
			<input name="status" value="${status }"/>
			<br />
			<label style="font-weight: 700;">message:</label>
			<br />
			<input name="message" value="${message }"/>
			<br />
			<label style="font-weight: 700;">data:</label>
			<br />
			<textarea name="data" >${data }</textarea>
			<br>
			<input type="submit" value="提交">
		</form>
	</body>

</html>