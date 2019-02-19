<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
    
    <title>红包详情</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport" content="width=device-width,user-scalable=no, initial-scale=1">
<%-- 	<link href="<%=basePath %>css/redpaper/detail.css" rel="stylesheet" type="text/css" > 
	<link href="<%=basePath %>css/redpaper/detail01.css" rel="stylesheet" type="text/css" > --%>
	<link rel="stylesheet" type="text/css" media="screen and (min-width:320px) and (max-device-width: 1000px)" href="<%=basePath %>css/redpaper/detail01.css">
	<link rel="stylesheet" type="text/css" media="screen and (max-device-width: 320px)" href="<%=basePath %>css/redpaper/detail.css">
  </head>
  
  <body>
    <div class="top">
		<!-- <a href="<%=basePath %>invoke/users/redPaperLink?link=${link }"></a> -->
    	<div class="pic" style="background:url(${headImg });background-size: cover; -moz-background-size: cover;"></div>
    <div><p>${shopName}</p></div> 
	</div>
	<div class="con">
	<c:if test="${pd==1 }">
		<h4>恭喜您获得</h4>
		<h2>${remark}元</h2>
	</c:if>
	<c:if test="${pd==0 }">
    	<h2>${remark}</h2>
    </c:if>
    	<p class="p1">${description }</p>
    	<p class="p2">
    	<span>红包有效期：3个自然月</span><br>
    	例如：1月20号领到的红包，则有效期至4月30号。<br>
		（3个自然月是2月、3月、4月）<br>
        	你可以在个人中心我的钱包中查阅已获得的红包。
    	</p>
	</div>
	<div class="footer"><span>爱生活&nbsp;&nbsp;&nbsp; 每天积分</span></div>
  </body>
</html>
