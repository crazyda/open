<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>个人中心</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="${BASEPATH_IN_SESSION }shareweb/css/bootstrap.min.css">
<link type="text/css" href="${BASEPATH_IN_SESSION }shareweb/css/swiper.min.css" rel="stylesheet">
<link type="text/css" href="${BASEPATH_IN_SESSION }shareweb/css/FE-redeem.css" rel="stylesheet">
<script src="${BASEPATH_IN_SESSION }shareweb/js/jquery.min.js" type="text/javascript"></script>
<script src="${BASEPATH_IN_SESSION }shareweb/js/swiper.jquery.min.js" type="text/javascript"></script>
<script src="${BASEPATH_IN_SESSION }shareweb/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${BASEPATH_IN_SESSION }shareweb/js/my.js" type="text/javascript"></script>
</head>


<body>
<div class="head_center">
	<div class="back">
    	<a href="index.html"><span></span></a>
    </div>
    <h4>个人中心</h4>
    <div class="news">
    	<a href="PS-message.html"><span></span></a>
    </div>
    <div class="sao">
    	<a href="#"><span></span></a>
    </div>
</div>
<!---------------会员资料信息-------------------->
<div class="member">
	<dl class="head">
    	<dt><img src="images/head-pic.png" width="60"></dt>
        <dd>
        	<h4>Jack Yang <i>›</i></h4>
            <p><i><img src="images/icon_member.png" width="19"/></i> 1级会员</p>
        </dd>
    </dl>
    <div class="integral"><p>积分 1021420</p></div>
</div>
<!---------------收益免单券-------------------->
<div class="income">
	<div><p><b>3694</b><br>红包收益/元</p></div>
	<div><p onClick="location.href='PS-cash-earning.html'"><b>9988</b><br>现金收益/元</p></div>
	<div><p><a href="PS-free-ticket.html"><b>3</b><br>免单券/张</a></p></div>
</div>
<!---------------我的订单-------------------->
<div class="order">
	<div class="order_head">
    	<h4><i><img src="images/icon_order.png" width="20"></i> 我的订单</h4>
        <a href="#">查看全部订单 ></a>
    </div>
    <div class="order_list">
    	<ul>
        	<li class="current"><span></span><img src="images/order-list01.png" height="25"><br>待支付</li>
        	<li><span></span><img src="images/order-list02.png" height="25"><br>待确认</li>
        	<li><span></span><img src="images/order-list03.png" height="25"><br>待评价</li>
        	<li><span></span><img src="images/order-list04.png" height="25"><br>退单售后</li>
        </ul>
    </div>
</div>
<!---------------我的积分-功能分类-------------------->
<div class="function">
	<ul>
    	<li onClick="location.href='PS-my-scores.html'"><img src="images/function01.png" height="25"><br>我的积分</li>
    	<li onClick="location.href='PS-my-address.html'"><img src="images/function02.png" height="25"><br>我的地址</li>
    	<li onClick="location.href='PS-withdraw.html'"><img src="images/function03.png" height="25"><br>我的提现</li>
    	<li onClick="location.href='PS-personal-data.html'"><img src="images/function04.png" height="25"><br>个人资料</li>
    	<li onClick="location.href=''"><img src="images/function05.png" height="25"><br>店铺收藏</li>
    	<li onClick="location.href='PS-goods-attention02.html'"><img src="images/function06.png" height="25"><br>商品关注</li>
    	<li><img src="images/function07.png" height="25"><br>送礼提醒</li>
    	<li onClick="location.href='PS-screen-switch.html'"><img src="images/function08.png" height="25"><br>锁屏开关</li>
    	<li style="border-bottom:0"><img src="images/function09.png" height="25"><br>我是商家</li>
    	<li style="border-bottom:0" onClick="location.href='PS-setup.html'"><img src="images/function10.png" height="25"><br>我的设置</li>
    </ul>
</div>
<!---------------我的邀请码------------------------>
<div class="invitation">
	<div class="invitation_head">
    	<h4 onClick="location.href='PS-invitation.html'"><i><img src="images/icon_erweima.png" width="30"></i> 我的邀请码：<span>Y7984</span></h4>
        <a href="PS-my-fans.html">300粉丝 ></a>
    </div>
    <p class="note">温馨提示：点击二维码能放大扫描哦</p>
    <div class="invitn_btn">
    	<span onClick="location.href='PS-binding-recommend.html'">绑定联系人</span>
    	<span>必看攻略</span>
    </div>
</div>
<!------------主菜单menu------------------------>
<div class="menu">
	<ul>
    	<li class="current"><a href="index.html"><img src="${BASEPATH_IN_SESSION }shareweb/images/home-hover.png" width="22"/><br>首页</a></li>
    	<li><a href="SC-shop-cart.html"><img src="${BASEPATH_IN_SESSION }shareweb/images/shop.png" width="22"/><br>购物车</a></li>
    	<li><a href="OU-order.html"><img src="${BASEPATH_IN_SESSION }shareweb/images/order.png" width="22"/><br>订单</a></li>
    	<li><a href="FE-personal.html"><img src="${BASEPATH_IN_SESSION }shareweb/images/personal.png" width="22"/><br>个人中心</a></li>
    </ul>
</div>





</body>
</html>
<script type="text/javascript">
$(function(){
	$.ajax({
		url:"${BASEPATH_IN_SESSION}invoke/mall/personal",
		type:"post",
		data:{"data":${dataJson}},
		dataType: "json",
		success:function(dataJson){//获得数据成功
			//商品分类
			var goodsClassifys = dataJson.data.goodsClassifys;
			$(goodsClassifys).each(function(index,item){
				$('.sort ul').append('<li><a href="#"><img src="'+item.image+'" width="60%"/><br><span>'+item.name+'</span></a></li>');
			});
			//商品分类
			var scoreGoodsClassifys = dataJson.data.scoreGoodsClassifys;
			$(scoreGoodsClassifys).each(function(index,item){
				if(index==0){
					$('.sort_s h4').append('<span class="current" onclick="getScoreImages(\''+item.typeId+'\','+index+',this)"><i>'+item.name+'</i></span>');
				}else{
					$('.sort_s h4').append('<span onclick="getScoreImages(\''+item.typeId+'\','+index+',this)"><i>'+item.name+'</i></span>');
				}	
			});
		}
	});
});