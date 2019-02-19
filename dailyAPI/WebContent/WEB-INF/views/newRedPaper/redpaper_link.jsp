<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>红包外链</title>
<style type="text/css">
.top{
	width: 100%;
	height: 15px;
	line-height: 15px;
	position: fixed;
	top: 0;
	margin: 0;
	background:#fff; 
	filter: alpha(opacity=70); 
	opacity: 0.7;
	padding: 2px;
	cursor: pointer;
}
</style>
<script type="text/javascript" language="javascript">
	function js_back(){
		axp_app.back();
	}
	function load_page(){
		var rame = document.getElementById("iframepage");
		//得到页面高度 
		var yScroll = (document.documentElement.scrollHeight >document.documentElement.clientHeight) ? document.documentElement.scrollHeight : document.documentElement.clientHeight; 
		rame.style.height = yScroll;
		//得到页面宽度 
		var xScroll=(document.documentElement.scrollWidth>document.documentElement.clientWidth) ? document.documentElement.scrollWidth : document.documentElement.scrollWidth; 
		rame.style.width = xScroll;
	}
</script>
</head>
<body>
	<div class="top">
		 <span onclick="javascript:js_back();">&lt;&nbsp;返回</span>
	</div>
	<div>
		<iframe src="${link }" id="iframepage" name="iframepage" frameBorder=0 scrolling=no width="100%" height="1000" onLoad="load_page()"></iframe>
	</div>
</body>
</html>