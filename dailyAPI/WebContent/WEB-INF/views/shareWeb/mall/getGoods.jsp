<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>商品详情</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="${BASEPATH_IN_SESSION }shareweb/css/bootstrap.min.css">
<link type="text/css" href="${BASEPATH_IN_SESSION }shareweb/css/swiper.min.css" rel="stylesheet">
<link type="text/css" href="${BASEPATH_IN_SESSION }shareweb/css/FE-product-detail.css" rel="stylesheet">
<script src="${BASEPATH_IN_SESSION }shareweb/js/jquery.min.js" type="text/javascript"></script>
<script src="${BASEPATH_IN_SESSION }shareweb/js/swiper.jquery.min.js" type="text/javascript"></script>
<script src="${BASEPATH_IN_SESSION }shareweb/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${BASEPATH_IN_SESSION }shareweb/js/my.js" type="text/javascript"></script>
</head>

<body>
<div class="head_product head_product_cash">
	<div class="back">
    	<a href="javascript:history.go(-1)"><span></span></a>
    </div>
  <h4>商品详情</h4>
  <div class="share shop">
    	<a onclick="downloadUrl();"><span></span></a>
    </div>
</div>
<!-----------------商品展示图-------------------------------->
<!-- Swiper -->
<div class="swiper-container swiper-container-horizontal">
    <div class="swiper-wrapper">
    </div>
    <!-- Add Pagination -->
    <div class="swiper-pagination swiper-pagination-clickable swiper-pagination-bullets">
   </div>
</div>
<!---------------商品信息Information--------------------------->
<div class="information">
    <div class="name">
        <h4 id="name"></h4>
        <p class="itgl" id="money"></p>
        <p class="list">
            <span class="left" id="displayPrice"></span>
            <span id="expressTactics"></span>
            <span id="stockNumber" class="right"><i><img src="${BASEPATH_IN_SESSION }shareweb/images/icon_yishou.png" width="12"/></i></span>
        </p>
    </div>
    <div class="format">
    	<div class="size"></div>
    	<div class="size"></div>
    	<div class="size"></div>
    	<div class="size"></div>
        <div class="num_all">
        	<div class="s_name">数量</div>
            <div class="num_wrap">
            	<span class="minus minus_disabled" id="minus"></span>
                <input class="num_text" id="buyNum" type="text" value="1">
                <span class="plus" id="plus"></span>
            </div>
        </div>
    </div>
</div>
<!---------------店铺信息进店逛逛--------------------------->
<div class="shop-detail">
    <dl class="shop-name">
        <dt><img id=sellerLogo width="100%"  /></dt>
        <dd>
        	<b id=sellerName>YY酸奶吧</b><span id=youhui><!-- <i>首</i> <i>减</i> <i>惠</i> --> <i>惠</i></span><br>
        	<span id=sellerAddress>天河区中山中路20号</span>
        </dd>
    </dl>
    <div class="shop-in">
       <span ></span>
    </div>
</div>
<!---------------商品评论评论--------------------------->
<div class="comments">
	<dl class="title" onclick="downloadUrl();">
    	<dt>评价<span id=commentCount ></span></dt>
    	<dd>好评率<span id=goodPraise ></span> <i class="fa fa-angle-right"></i></dd>
    </dl>
    <ul class="comment">
    	
    </ul>
</div>
<div class="d_p_cash">
	<p class="title">图文详情</p>
    <div class="d_p">
        <h5>
            <span class="crt">商品介绍</span>
            <span>商品参数</span>
        </h5>
			<iframe id="webIframe" frameborder="0" scrolling="no" width="100%" height="1500px;" ></iframe>
    </div>
</div>
<!---------------底栏菜单--------------------------->
<div class="menu">
	<div class="left">
    	<div class="left01"><img src="${BASEPATH_IN_SESSION }shareweb/images/icon_shouye@3x.png" width="18"/><br>进店逛逛</div>
        <div class="left02"><img src="${BASEPATH_IN_SESSION }shareweb/images/icon_fenxiang@3x.png" width="18"/><br>分享</div>
        <div class="left03"><img src="${BASEPATH_IN_SESSION }shareweb/images/icon_guanzhu@3x.png" width="18"/><br>关注</div>
    </div>
    <div class="center" onclick="downloadUrl()">加入购物车</div>
    <div class="right" onclick="downloadUrl()">立即购买</div>
</div>
<input type=hidden id="sellerId" />
<script type="text/javascript">
$(function(){
	$.ajax({
   		url:"${BASEPATH_IN_SESSION}invoke/mall/getGoods",
   		type:"post",
   		data:{"data":'${dataJson}'},	
   	 	dataType: "json",//返回json格式的数据
   		success:function(dataJson){//获得数据成功
   			if(dataJson.status!=1){//异常状态，返回错误描述
   				alert(dataJson.message);
   				return null;
   			}
   			var data = dataJson.data;
   			//头部轮播================
   			var images = data.images;
   			var imgDiv = $('.swiper-container.swiper-container-horizontal');
   			var imgItems = $(imgDiv).find('.swiper-wrapper');
   			var imgPages = $(imgDiv).find('.swiper-pagination.swiper-pagination-clickable.swiper-pagination-bullets');
   			$(images).each(function(index,item){
   				$(imgItems).append('<div style="width:100%;" class="swiper-slide">'+
   				'<img src="'+item.image+'" width="100%"></div>');
   				$(imgPages).append('<span class="swiper-pagination-bullet"></span>');
   			});
   			new Swiper(imgDiv, {
   			    pagination: '.swiper-pagination',
   			    paginationClickable: true
   			});
   			$('#name').append(data.name);
   			if(parseFloat(data.price)>0){
   				$('#money').append('¥ '+data.price);
   			}else if(parseInt(data.score)>0){
   				$('#money').append(data.score+'积分');
   			}
   			$('#displayPrice').append('原价：¥'+data.costPrice);
   			$('#expressTactics').append(data.expressTactics);
   			$('#stockNumber').append(data.stockNumber);
   			//规格
   			var spec = data.spec;
   			/*
   			$(spec).each(function(index,item){
   				var specTxt = '<div class="size"><div class="s_name">'+item.name+'</div><div class="order">';
   				$(item.specItems).each(function(index,child){
   					specTxt = specTxt + '<span class="option">'+child.specItemName+'</span>';
   				});
   				specTxt = specTxt +  '</div></div>';
   				$('.format').append(specTxt);
   			});*/
   			$(spec).each(function(index,item){
   				var specTxt = '<div class="s_name">'+item.name+'</div><div class="order">';
   				$(item.specItems).each(function(index,child){
   					specTxt = specTxt + '<span class="option">'+child.specItemName+'</span>';
   				});
   				specTxt = specTxt +  '</div>';
   				$('.size').eq(index).append(specTxt);
   			});
   			//=====
   			$('#sellerLogo').attr("scr",data.seller.sellerIcon);
   			$('#sellerAddress').empty().append(data.seller.sellerAddress);
   			$('#sellerName').empty().append(data.seller.sellerName);
   			$('#sellerId').val(data.seller.sellerId);
   			//评论
   			
   			var comments = data.commentList.dataList;
   			var commentCount = data.commentList.commentCount;
            var goodCommentPraise = data.commentList.goodCommentPraise;
            $('#commentCount').append('('+commentCount+')');
            $('#goodPraise').append(goodCommentPraise+'%');
            $(comments).each(function(index,com){
            	var images = com.commentImages;
            	var detail='<li><dl class="name">'+
            	'<dt>'+com.userInfo.username+' &nbsp;&nbsp;<span class="time">'+com.commentDate+'</span></dt>'+
            	'<dd class="star">';
            	for(var i=0;i<parseInt(com.commentGoal)/2;i++){
            		detail = detail + '<img src="${BASEPATH_IN_SESSION}shareweb/images/star-hover.png" width="10">';
            	}
            	for(var i=0;i<(10-parseInt(com.commentGoal))/2;i++){
            		detail = detail + '<img src="${BASEPATH_IN_SESSION}shareweb/images/star.png" width="10">';
            	}
            	detail = detail + '</dd></dl><p class="text">'+com.commentContent+'</p><p class="pic">';
            	$(images).each(function(index,img){
            		detail = detail + '<img src="'+img+'" width="60">';
            	});
            	detail = detail + '</p></li>';
            	$('.comment').append(detail);
            });
            
            $('#webIframe').attr('src','${BASEPATH_IN_SESSION}/invoke/mall/goodsDetail?data={"data":{"goodsId":"'+data.goodsId+'"}}');
            
   		}
   	});
});

function goToSeller(){
	window.location.href='${BASEPATH_IN_SESSION}invoke/shareWeb/mall/getSeller?data={"data":{"sellerId":'+$('#sellerId').val()+'}}';
}

function downloadUrl(){
	window.location.href='${BASEPATH_IN_SESSION}invoke/download';
}
</script>
</body>
</html>


