<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>每天积分商家版APP</title>
    <link rel="stylesheet" href="${BASEPATH_IN_SESSION }/css/share/media_seller.css">
    <link rel="stylesheet" href="${BASEPATH_IN_SESSION }/css/share/download_seller.css">
    <script type="text/javascript" src="${BASEPATH_IN_SESSION }js/jquery-1.7.2.min.js"></script>
</head>
<body class="iphonex">
    <div class="download">
        <div class="downloadAndroidBtn btns" id="android" ></div>
        <div class="downloadIosBtn btns" id = "ios" ></div>
    </div>
     <div id="Share">
    	<%-- <img class="maskShare" src="${BASEPATH_IN_SESSION }images/download/bg1.png" alt="" > --%>
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

    
    $(function () {
        
        
         $('.btns').click(function(){
            

        var ua = navigator.userAgent.toLowerCase();//获取判断用的对象
        if (ua.match(/MicroMessenger/i) == "micromessenger") {
            
            $(".maskShare").show();

        }else{

            if(navigator.userAgent.match(/(iPhone|iPod|iPad);?/i)) {
                 window.location.href = "http://url.cn/5xo5T3H";
                
            }
            if(navigator.userAgent.match(/android/i)){
           		window.location.href = "http://mtjf.518wk.cn:8080/dailyRes/app/mts1.0.apk";

            }
        }                  
           });

         $(".maskShare").click(function(){
             $(".maskShare").hide();
         })
    
    });
    </script>

</body>



</html>