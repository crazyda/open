<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>结算申请</title>
    <link rel="stylesheet" href="<%=basePath %>/css/money/media.css">
    <link rel="stylesheet" href="<%=basePath %>/css/money/extractCash.css">
    <link rel="stylesheet" href="<%=basePath %>/css/money/checkID.css">
    <script type="text/javascript" src="<%=basePath %>js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/jquery-1.7.2.min.js"></script>
</head>

<body>
<input type="hidden"  value="${id }" id="id"/>
<input type="hidden"  value="${phone }" id="phone"/>
<input type="hidden"  value="${money }" id="money"/>

 <form action="<%=basePath%>invoke/users/webWithdrawals?id=${id}" id="saveForm" method="post">
   <div class="extractCashApply">
  <!--  <div class="phoneNub">
           <span>手机号码</span>
           <input type="text" id="inpPhone" name="inpPhone" onchange="doCheckphone()">
       </div> -->
      <div class="tip" id = "tipPhone" style="display: none"> 
           <span id="spanPhone"></span>
       </div> 
       <div class="balance">
           <span>余额: ${money }元 </span>
       </div>
       <div class="extractCash">
           <p>申请结算金额</p>
           <div class="inpCash" >
               <span>￥</span>
               <input value="" type="text" name="money" id="inpMoney" onchange="doCheckmoney()">
           </div>
       </div>
      <!--  <div class="tip" id = "tipMoney" style="display: none">
            <span id="spanMoney"></span>
        </div>
       <div class="cardInfo">
           <div class="Cardholder">
               <span>持卡人</span>
               <input type="text" id="cardUser">
           </div>
           <div class="bankCardNumber">
            <span>银行卡号</span>
            <input type="text" id="bankCode">
           </div>
       </div>
       
       <div class="bankName">
           <p>开户行名称</p>
           <input type="text" id="address">
       </div>
       <div class="tip" id = "tipCard" style="display: none"> 
           <span id="spanCard"></span>
       </div>  -->
       <p>申请结算,分佣会添加到您的钱包中,可通过钱包提现</p>
       <!-- <p>提现金额为100元起，提交后3个工作日内到账，手续费按银行标准收取，单笔最低100元起。</p> -->
       <button onclick="save();">提交</button>
   </div>
    </form> 
</body>

	<script type="text/javascript">
		var verifyPhone = false;
		var verifyMoney = false;
    /*    function doCheckphone(){
    	   var inpPhone = $("#inpPhone").val();
    	   var phone = $("#phone").val();
    	  
    	   if(inpPhone == phone){
    		   $("#tipPhone").hide();
    		   verifyPhone =true;
    	   }else{
    		   $("#tipPhone").show();
    		   $("#spanPhone").html("手机号没有填写正确,重新填写");
    		   verifyPhone = false;
    	   }
       } */
       
       function doCheckmoney(){
    	  
    	   var inpMoney = $("#inpMoney").val();
    	   var money = $("#money").val();
    	  
    	   if(inpMoney > money){
    		   $("#tipMoney").show();
    		   $("#spanMoney").html("申请提现金额不能大于账户余额!");
    		   verifyMoney= true;
    	   }else if(inpMoney < 200){
    		   $("#tipMoney").show();
    		   $("#spanMoney").html("申请提现金额需要大于200元!");
    		   verifyMoney= true;
    	   }else{
    		   $("#tipMoney").hide();
    		   verifyMoney =true;
    	   }
       }
       
       
 	   
       function save(){
    	   var verifyCardUser = true;
    	   var verifyBankCode = true;
    	   var verifyAddress = true;
    	   var id = $("#id");
    	   var cardUser = $("#cardUser").val();
    	   var bankCode = $("#bankCode").val();
    	   var address = $("#address").val();
    	   var userId = $("#id").val();
    	  /*  if(cardUser == ""){
    		   $("#spanCard").html("请填写正确持卡人");
    		   verifyCardUser = false;
    	   }
    	   
    	   if(bankCode == ""){
    		   $("#spanCard").html("请填写正确银行卡号");
    		   verifyBankCode = false;
    	   }
    	   if(address == ""){
    		   $("#spanCard").html("请填写正确开户行地址");
    		   verifyAddress = false;
    		   
    	   }
		 */
     	   if(verifyPhone==true  && verifyMoney == true && verifyCardUser ==true && verifyBankCode == true && verifyAddress == true){
     			
     		   $("#saveForm").submit();
     		  
     		  
     		   
     	   }else{
     		  
     		 return false;
     		   
     	   }
    	  
       }
       
       
	
       </script>


</html>