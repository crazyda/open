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
<title>我的红包</title>
<script src="${basePath }js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${basePath }js/share/my.js" type="text/javascript"></script>
<link href="${basePath }css/share/myred02.css" rel="stylesheet" type="text/css" >
<link href="${basePath }css/share/myred02-s.css" rel="stylesheet" type="text/css" >
</head>
<body>
<div class="tab">
	<h2>
    	<span class="current">可用</span>
        <span>已使用</span>
        <span>过期</span>
    </h2>
    <ul>
    	<li class="current">
        	<p class="p1">共<span>${redNum }</span>个现金红包</p>
            <h2>${sum }</h2>
            <a href="rule.action" class="rule">使用规则</a>
            <c:choose>
            	<c:when test="${!empty usable }">
	            	<div class="today">
		            	<a href="#" class="tittle"><span></span>今天领到<span></span></a>
		            	<c:forEach var="paper" items="${usable }">
		            		 <c:if test="${paper.isToday==0 }">
		            		 	<p><a href="${paper.address }">${paper.sellerName }</a><br>使用有效期：${paper.endTime }<span>${paper.money }</span></p>
		            		 </c:if>
		            	</c:forEach>
		            </div>
		            <div class="before">
		            	<a href="#" class="tittle"><span></span>早前领到<span></span></a>
		            	<c:forEach var="paper" items="${usable }">
		            		 <c:if test="${paper.isToday==1 }">
		            		 	<p><a href="${paper.address }">${paper.sellerName }</a><br>使用有效期：${paper.endTime }<span>${paper.money }</span></p>
		            		 </c:if>
		            	</c:forEach>
		            </div>
		        </c:when>
		        <c:otherwise>
		        	<p class="p2">亲，您现在没有可用的现金红包~</p>
		        </c:otherwise>
            </c:choose>
        </li>
	    <li>
	   		<c:choose>
	           	<c:when test="${!empty used }">
	        	<div>
	            	<span class="left">共<b>${redNum2 }</b>个红包</span>
	                <span class="center">${sum2 }</span>
	                <span class="right"><a href="${basePath }invoke/users/rule" class="rule">使用规则</a></span>
	            </div>
	            <div class="con">
	            	<c:forEach var="paper" items="${used }">
	            	<div>
	           	    	<img src="${RESOURCE_LOCAL_URL}${paper.sellerImg }" width="70"/>
	                    <p class="p1"><span>${paper.sellerName }</span><br>${paper.endTime }<br>消费￥${paper.money } 实付￥${paper.avail }</p>
	                    <p class="p2">${paper.money }</p>
	                </div>
	                </c:forEach>
	            </div>
	    		</c:when>
				<c:otherwise>
					亲还没有使用过红包哦~
				</c:otherwise>
			</c:choose>
	    </li>
    	<li>
	   		<c:choose>
	   			<c:when test="${!empty overTime }">
	   				<c:forEach var="item" items="${overTime }">
	       			<p>${item[0] } 到期 <a href="#">${item[1] }个</a> 红包<span>${item[2] }</span></p>
	       			</c:forEach>
	   			</c:when>
	   			<c:otherwise>
	            	暂时没有过期的红包
	   			</c:otherwise>
	   		</c:choose>
        </li>
    </ul>
</div>
</body>
</html>