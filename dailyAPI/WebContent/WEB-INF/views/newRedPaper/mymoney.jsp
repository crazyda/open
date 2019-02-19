<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的资金</title>
<script src="${basePath }js/jquery-1.7.2.min.js" type="text/javascript"></script>
<link href="${basePath }css/share/myred02.css" rel="stylesheet" type="text/css" >
<link href="${basePath }css/share/myred02-s.css" rel="stylesheet" type="text/css" >
</head>
<body>
<div class="tab">
	
    <ul>
    	<li class="current">
        	<p class="p1">未确认金额：<span>${outmoney }</span></p>
            <h2>${sum }</h2>
           
	            	<div class="today">
		            	<a href="#" class="tittle"><span></span>资金列表<span></span></a>
		            	<c:forEach var="record" items="${records }">
		            		 	<p>${record.remark }<br>时间：${record.createTime } &nbsp;状态：${record.type>0?'已确认':'未确认' }<span>${record.money }</span></p>
		            	</c:forEach>
		            </div>
		           
        </li>
	    
    	
    </ul>
</div>
</body>
</html>