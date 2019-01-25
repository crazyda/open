<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
	request.setAttribute("basePath", basePath);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分类选择</title>
<script type="text/javascript" src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
</head>
<body>
<form class="adduser-form" action="save">

	<div>
		<div>
			<select name="selectApp" id="selectApp" onclick="func()">
				<c:forEach items="${openApp}" var="app">
					<option value="${app.id }" >${app.appName }</option>
				</c:forEach>
			
			</select>
		<script type="text/javascript">
		function func(){
			 var vs = $('select option:selected').val();
			
			
				
		}
		
		
		</script>
		
		</div>
	
	
		<div id="cid">
			<ul>
				<c:forEach items="${ pddCliassify}" var="pddify">
					<li><p><input type="checkbox" value="${pddify.id }" name="pddifys"/>${pddify.categoryName } </p></li>
					
					
				</c:forEach>
			</ul>
	
		</div>
		<div id="rate_range">
			分佣范围(千分比):<input id="start" name ="start_rate" onkeyup="value=value.replace(/[^\d]/g,'')" placeholder="只能输入数字"/> &nbsp;至&nbsp;
							 <input id="end" name="end_rate" onkeyup="value=value.replace(/[^\d]/g,'')" placeholder="只能输入数字"/>
		
		</div>
	</div>
	<p class="sub"><input type="submit" value="提交"></p>
	<script>
	
	
	
	
	</script>
</form>

</body>
</html>