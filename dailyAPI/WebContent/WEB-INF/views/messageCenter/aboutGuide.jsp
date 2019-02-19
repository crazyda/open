<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>

<base href="<%=basePath%>">

<title>新手指引</title>

	<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<link rel="stylesheet" type="text/css" href="<%=basePath %>css/guide.css"/>
	<script src="<%=basePath %>js/jquery.min.js"></script>
	<script src="<%=basePath %>js/reset.js"></script>
</head>
<body>
	<header class="header">
	<ul>
		<li class="back" onclick="back(this)"><img
			src="<%=basePath %>images/index.png" width="100%" /></li>
		<li>新手指引</li>
	</ul>
	</header>
	<section class="how"><a href="<%=basePath %>images/kdzy11.jpg">1.如何开通店铺？</a></section>
	
	<section class="how"><a href="<%=basePath %>images/spsczy.jpg">2.如何发布商品？</a></section>
</body>
<script>

    var ua = navigator.userAgent.toLowerCase();
    /* 关闭 网页 */
    function back(obj){
        if (/iphone|ipad|ipod/.test(ua)) {
            window.webkit.messageHandlers.NAV_POP.postMessage('');
        } else if (/android/.test(ua)) {
            android.backIndex();
        }
    }

    /* 隐藏网页地址 */
    $(function(){
        if (/iphone|ipad|ipod/.test(ua)) {
            window.webkit.messageHandlers.NAV_INFO.postMessage('0'); //1 显示 0 隐藏
        } else if (/android/.test(ua)) {
            android.showNav('noback');
        }
    });
</script>
</html>
