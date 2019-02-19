<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>用每天积分 享品质生活</title>
    <link rel="stylesheet" href="${BASEPATH_IN_SESSION}css/share/media.css">
    <link rel="stylesheet" href="${BASEPATH_IN_SESSION}css/share/download.css">
    <script type="text/javascript" src="${BASEPATH_IN_SESSION }js/jquery-1.7.2.min.js"></script>
	
</head>
<body class="iphonex">
<input type="hidden" value="${type }" id="type">
    <div class="download">
        <div class="downloadBtn" onclick="gettype();"></div>
    </div>
    <script type="text/javascript">
    
    var type="未知";
    var browser={
 versions:function(){
         var u = navigator.userAgent, app = navigator.appVersion;
         return {         //移动终端浏览器版本信息
              trident: u.indexOf('Trident') > -1, //IE内核
             presto: u.indexOf('Presto') > -1, //opera内核
             webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
             gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
             mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
             ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
             android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或uc浏览器
             iPhone: u.indexOf('iPhone') > -1 , //是否为iPhone或者QQHD浏览器
             iPad: u.indexOf('iPad') > -1, //是否iPad
             webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
         };
      }(),
      language:(navigator.browserLanguage || navigator.language).toLowerCase()

}

//得到类型

function gettype(){                
       var iphone=browser.versions.iPhone;
       var android=browser.versions.android;   
       if(iphone){
    	   window.location.href="http://url.cn/5OBIqS9";
       }
       if(android){
    	   window.location.href="https://android.myapp.com/myapp/detail.htm?apkName=com.axp.everydayscore";
       }              
   }

    
    
    	
    
    
    </script>
</body>
</html>
