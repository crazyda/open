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
    
    <title>红包使用规则</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport" content="width=device-width,user-scalable=no, initial-scale=1">
	<link href="<%=basePath %>css/redpaper/rule.css" rel="stylesheet" type="text/css" >

  </head>
  
  <body>
  <!-- 
  	<div class="head">
		<a href="myred01.html" class="a1">红包使用规则</a>
    	<a href="#" class="a3"><img src="<%=basePath %>images/redpaper/head03.png" width="8%"/></a>
    	<%-- <a href="#" class="a2"><img src="<%=basePath %>images/redpaper/head02.png" width="8%"/></a> --%>
	</div>
	<p> -->
	请您务必审慎阅读以下规则条款，如您参与活动视为您同意并认可“红包活动”规则的相关内容，每天积分拥有最终解释权。<br>
	<br>
	一、怎么获得红包？<br>
	1、通过屏幕解锁、浏览广告或商家店铺都可随机获得红包。<br>
	2、使用范围：平台所有商家店铺允许使用红包消费的商品。<br>
	3、有效期：领取的红包从下个自然月起算，3个月内有效。<br>
    	（例如：1月20日领到的红包，则有效期至4月30日。）
	<br>
	二、红包使用规则：红包需要提交商品订单，在支付环节才可以使用哦。<br>
	顺序<br>
	<br>
	三、订单退款规则<br>
	1、退款成功后，红包还在有效期内；红包会退还，红包使用到期时间不变；<br>
	2、退款成功后，红包已过有效期<br>
	<br>
	四、注意事项<br>
	1、客户端的版本较低，会影响红包的使用吗？<br>
	<br>
	待续
	</p>

<!-- 
	<ul class="menu">
		<li><a href="#"><img src="<%=basePath %>images/redpaper/icon1.png" width="30%"/><br>首页</a></li>
		<li><a href="#"><img src="<%=basePath %>images/redpaper/icon2.png" width="32%"/><br>个人中心</a></li>
		<li><a href="#"><img src="<%=basePath %>images/redpaper/icon3.png" width="30%"/><br>钱包</a></li>
		<li><a href="#"><img src="<%=basePath %>images/redpaper/icon4.png" width="28%"/><br>订单</a></li>
    	<div class="sort"></div>
	</ul>
	 -->
  </body>
</html>
