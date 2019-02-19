<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <title>合伙人收益</title>
    <link rel="stylesheet" href="<%=basePath %>/css/money/media.css">
    <link rel="stylesheet" href="<%=basePath %>/css/money/index.css">
    <script type="text/javascript" src="<%=basePath %>js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/jquery-1.7.2.min.js"></script>
</head>
	
<body>
<input type="hidden"  value="${adminUserId}" id="adminUserId"/>
    <div class="bill">
        <div class="jfBox">
            <div class="jfBox-top">
                <div class="jfBox-topLeft">
                    <p>总积分数量</p>
                    <p>${scoreSum}</p>
                </div>
                <div class="jfBox-topRight">
                    <p>可用积分</p>
                    <p>${scoreCount }</p>
                </div>
            </div>
            <div class="jfBox-bottom">
                <div class="jfBox-bottomLeft">
                    <div class="leftTop">
                        <span>总佣金池 </span>
                       <%--  <img src="<%=basePath %>/images/wenhao@2x.png" alt=""> --%>
                    </div>
                    <div class="leftBottom">
						￥<fmt:formatNumber value="${maid}" type="number" maxFractionDigits="7"/>
                    </div>
                </div>
                <div class="jfBox-bottomRight">
                    <p>今日佣金池</p>
                    <p>￥<fmt:formatNumber value="${todayMaid}" type="number" maxFractionDigits="7"/></p>
                </div>
            </div>
        </div>
        
        <div class="srBox">
	        <div class="srTit">
	        	<p>我的收入明细</p>
	        </div>
            <div class="srBoxData">
            
                <div>
                    <p>总收入</p>
                    <span>￥<fmt:formatNumber value="${maidhhr}" type="number" maxFractionDigits="7"/></span>
                </div>
                <div>
                    <p>今日收入</p>
                    <span>￥<fmt:formatNumber value="${todayMaidhhr }" type="number" maxFractionDigits="7"/></span>
                </div>
                <div>
                    <p>可结算</p>
                    <span>￥<fmt:formatNumber value="${money}" type="number" maxFractionDigits="7"/></span>
                </div>
                <div>
                    <p>已结算</p>
                    <span>￥<fmt:formatNumber value="${yjsMoney}" type="number" maxFractionDigits="7"/></span>
                </div>
            </div>
            <div class="srBoxDetail">
            	<c:forEach items="${getMoneys }" var="each">
            		<div>
	                    <p><fmt:formatDate value="${each.createTime }" pattern="yyyy-MM-dd"/>  提现</p>
	                    <span>￥${each.money }</span>
               		 </div>
            	
            	</c:forEach>
            
            </div>
        </div>
		
			 <img class="gzhImg" id="gzhImg" src="${gzhImg }" height="150px" width="150px" style="display: none"/> 
		
		
		
        <div class="billBtn" onclick="put()">
            <span>结算到钱包</span>
        </div>
    </div>
        <input type="hidden" id = "gzhOpenId" value="${gzhOpenId }"/>
         <input type="hidden" id = "kjs" name = "money" value="${money }"/>
<script>
	
function put(){
	var gzh = $("#gzhOpenId").val();
	var adminUserId = $("#adminUserId").val();
	var money = $("#kjs").val();
	 if(money<30 ){
		alert('可结算金额必须大于30元');
		return;
	}
	 
	$.ajax({
		type:"post",
		url:"<%=basePath%>invoke/users/webWithdrawals",
		data: "id="+adminUserId+"&money="+money,        
		dataType: "JSON",
        success: function (statusMap){
        	if(statusMap.status==1){
        		alert(statusMap.message);
        		
        	}

        }
	});
	
	
	
}

	
	
</script>

</body>

</html>