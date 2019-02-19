<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<link href="${BASEPATH_IN_SESSION}css/message-center.css" rel="stylesheet" type="text/css">
    <base href="<%=basePath%>">
    
    <title>帮助</title>
     <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
	<script type="text/javascript" src="<%=basePath %>js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/jquery-1.7.2.min.js"></script>
	
  </head>
  <input type="hidden" id="ty" value="${type}"/>
  <div id="dianzhu" style="display: none">
  
	 <p style="text-align: center;">店主</p>
	 <p><br/></p>
	 <p>&nbsp;开店成功后，可在使用每天积分的积分营销工具进行推广营销，可上架店铺产品，用户使用积分兑换后，可获得对应金额。</p>
	 <p><br/></p><p><br/></p><p><br/></p><p><br/></p><p><br/></p><p><br/></p>
	 <p><br/></p>
  
  </div>
	 <div id="fensi" style="display: none">
	 
		 <p style="text-align: center;">粉丝</p>
		 <p><br/></p>
		 <p>&nbsp;参与各种活动,获得积分;积分可兑换商品.<br/>每天积分粉丝也可以1元升级为商家,自己做老板!<br/>升级城市合伙人创造更大的收益!</p>
		 <p><br/></p><p><br/></p><p><br/></p><p><br/></p><p><br/></p><p><br/></p>
		 <p><br/></p>
  
    </div>
	<div id="hhr" style="display: none">
	 
		 <p style="text-align: center;">城市合伙人</p>
		 <p><br/></p>
		 <p>&nbsp;邀请商家入驻,获得商家和粉丝分佣收益!</p>
		 <p><br/></p><p><br/></p><p><br/></p><p><br/></p><p><br/></p><p><br/></p>
		 <p><br/></p>
  
    </div>
	
<script type="text/javascript">
	
		var type = $("#ty").val();
		alert(type);
		if(type == 1){
			$("#dianzhu").show();
			$("#fensi").hide();
			$("#hhr").hide();
		}else if(type == 2){
			$("#dianzhu").hide();
			$("#fensi").show();
			$("#hhr").hide();
		}else{
			$("#dianzhu").hide();
			$("#fensi").hide();
			$("#hhr").show();
		}
	
	
	</script>

</html>
