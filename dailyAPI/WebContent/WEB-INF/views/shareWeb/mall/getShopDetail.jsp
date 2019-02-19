<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>店铺详情</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="${BASEPATH_IN_SESSION }shareweb/css/bootstrap.min.css">
<link type="text/css" href="${BASEPATH_IN_SESSION }shareweb/css/swiper.min.css" rel="stylesheet">
<link type="text/css" href="${BASEPATH_IN_SESSION }shareweb/css/FE-shop-detail.css" rel="stylesheet">
<script src="${BASEPATH_IN_SESSION }shareweb/js/jquery.min.js" type="text/javascript"></script>
<script src="${BASEPATH_IN_SESSION }shareweb/js/swiper.jquery.min.js" type="text/javascript"></script>
<script src="${BASEPATH_IN_SESSION }shareweb/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${BASEPATH_IN_SESSION }shareweb/js/my.js" type="text/javascript"></script>
</head>

<body>
<div class="head_shop">
	<div class="back">
    	<a href="javascript:history.go(-1)"><span></span></a>
    </div>
    <h4><input type="text" placeholder="搜索店铺"></h4>
   
</div>
<div class="banner">
	<div class="swiper-container banner">
		<div class="swiper-wrapper"></div>
	</div>
</div>
<!------------店铺详情，地址，优惠--------------------->
<div class="shop_all">
	<div class="section01">
        <dl class="shop-name">
            <dt><img src="${BASEPATH_IN_SESSION }shareweb/images/shop01.png" width="100%"/></dt>
            <dd><b></b><br><span>营业时间：</span></dd>
        </dl>
        <div class="shop-in">
          <p><i><img id="heart-red" src="${BASEPATH_IN_SESSION }shareweb/images/heart-red.png" width="14"/></i></p>
          <!-- <p><i><img src="${BASEPATH_IN_SESSION }shareweb/images/locate.png" width="14"/></i> 18.56km</p> -->
          <p id="isLock"></p>
        </div>
    </div>
  <div class="section02">
   	    <p class="address"></p>
        <a href="#" class="phone"><i><img src="${BASEPATH_IN_SESSION }shareweb/images/icon_phone.png" width="24"/></i></a>
    </div>
   

   <!-- 
   <div class="section04">
    	<p><span>首</span>新用户首单立减20元</p>
    	<p><span style="background:#1cbca6">减</span>买单立减20元</p>
    	<p><span style="background:#1dbafc">满</span>满50减10</p>
    	<p><span style="background:#f98422">惠</span>1元立抢</p>
   	    <p><span style="background:none; padding:0;"><img src="${BASEPATH_IN_SESSION }shareweb/images/red.png" width="20"/></span>红包最高再减20元</p>
    </div>
     -->
    <div class="section04">
    	<p><span style="background:#f98422">惠</span>更多优惠</p>
    </div>
</div>
<!------------积分马上换integral--------------------->
<div class="integral">
	<div class="title">
    	<span class="left"></span>
    	<h4><i><img src="${BASEPATH_IN_SESSION }shareweb/images/heart.png" width="18" style="margin-bottom:3px;"/></i>积分马上换</h4>
        <span class="right"></span>
    </div>
    <div class="swiper-container integral">
    	<div class="swiper-wrapper">
    		<!-- 
        	<div class="swiper-slide">
            	<img src="${BASEPATH_IN_SESSION }shareweb/images/shop01-goods01.png" width="100%">
                <p class="color">积分：60</p>
                <p class="score">原价：￥6元</p>
            </div>
        	<div class="swiper-slide">
            	<img src="${BASEPATH_IN_SESSION }shareweb/images/shop01-goods02.png" width="100%">
                <p class="color">积分：60</p>
                <p class="score">原价：￥6元</p>
            </div>
        	<div class="swiper-slide">
            	<img src="${BASEPATH_IN_SESSION }shareweb/images/shop01-goods03.png" width="100%">
                <p class="color">积分：60</p>
                <p class="score">原价：￥6元</p>
            </div>
        	<div class="swiper-slide">
            	<img src="${BASEPATH_IN_SESSION }shareweb/images/shop01-goods04.png" width="100%">
                <p class="color">积分：60</p>
                <p class="score">原价：￥6元</p>
            </div> -->
        </div>
    </div>
    <!---------积分区轮播----------------->
</div>
<!------------ads广告商品--------------------->
<div class="ads">
	<div class="swiper-container ads">
		<div class="swiper-wrapper"></div>
	</div>
</div>
<div class="shop_all">
<div id=goodsDiv></div>
<!-- 
    <div class="all">
        <div class="img">
            <img src="${BASEPATH_IN_SESSION }shareweb/images/shop-detail-good01.png" width="100%">
            <span class="color01">上门自提</span>
        </div>
        <p class="name">最高价值12元的招牌明星奶茶3选1</p>
        <div class="price">
        	<h5>￥12</h5>
            <p><b>￥18</b></p>
            <span>已售300份</span>
        </div>
    </div>
    <div class="all">
        <div class="img">
            <img src="${BASEPATH_IN_SESSION }shareweb/images/shop-detail-good02.png" width="100%">
            <span class="color02">到店消费</span>
        </div>
        <p class="name">价值18元的饮品小吃9选1建议单人使用。</p>
        <div class="price">
        	<h5>￥18</h5>
            <p><b>￥26</b></p>
            <span>已售52份</span>
        </div>
    </div>
    <div class="all">
        <div class="img">
            <img src="${BASEPATH_IN_SESSION }shareweb/images/shop-detail-good03.png" width="100%">
            <span class="color03">周边派送</span>
        </div>
        <p class="name">最高价值8元的奶茶7选1建议单人使用</p>
        <div class="price">
        	<h5>￥8</h5>
            <p><b>￥12</b></p>
            <span>已售1120份</span>
        </div>
    </div>
    <div class="all">
        <div class="img">
            <img src="${BASEPATH_IN_SESSION }shareweb/images/shop-detail-good04.png" width="100%">
            <span class="color04">到店消费</span>
        </div>
        <p class="name">优乐美多味速溶奶茶12包装</p>
        <div class="price">
        	<h5>￥20</h5>
            <p><b>￥31</b></p>
            <span>已售37份</span>
        </div>
    </div> -->
</div>
<script type="text/javascript">
$(function(){
	$.ajax({
   		url:'${BASEPATH_IN_SESSION}invoke/mall/getSellerInfoById',
   		type:"post",
   		data:{"sellerId":'${dataJson}'},	
   	 	dataType: "json",//返回json格式的数据
   		success:function(dataJson){//获得数据成功
   			if(dataJson.status!=1){//异常状态，返回错误描述
   				alert(dataJson.message);
   				return null;
   			}
   			var data = dataJson.data;
   			//头部轮播================
   			var images = data.images;
   			var videos = data.videos;
   			$(images).each(function(index,item){
   				$('.banner').find($('.swiper-wrapper')).append('<div class="swiper-slide"><a href="'+item.uri+'">'+
   				'<img src="'+item.image+'" width="100%" height="100%" /></div>');
   			});
   			$(videos).each(function(index,item){
   				$('.banner').find($('.swiper-wrapper')).append('<div class="swiper-slide"><a href="'+item.uri+'">'+
   				'<img src="'+item.image+'" width="100%" height="100%" /></div>');
   			});
   			new Swiper('.banner.swiper-container', {
   				autoplay : 5000,
   		        pagination: '.swiper-pagination',
   		        slidesPerView: 1,
   		        paginationClickable: true,
   		        spaceBetween: 10,
   		        loop: true
   			});
   			//商家基本信息
   			$('.shop-name').children().remove();
   			var sellerIcon = data.sellerIcon;
   			var sellerName = data.sellerName;
   			var shopHours = data.shopHours;
   			var sellerAddress = data.sellerAddress;
   			var sellerPhone = data.sellerPhone;
   			var banner = data.banner;
   			var concern = data.concern;
   			$('.shop-name').append('<dt><img src="'+sellerIcon+'" width="100%"/></dt>');
   			$('.shop-name').append('<dd><b>'+sellerName+'</b><br><span>营业时间：'+shopHours+'</span></dd>');
   			$('.address').empty().append('地址：'+sellerAddress);
   			$('.phone').attr('href','tel:'+sellerPhone);
   			if(concern=="true"){
   				$('#heart-red').show();
   				$('#isLock').empty().append('');
   			}else{
   				$('#heart-red').hide();
   				$('#isLock').empty().append('');
   			}
   			$(banner).each(function(index,item){
   				$('.ads').find($('.swiper-wrapper')).append('<div class="swiper-slide"><a href="'+item.uri+'">'+
   				'<img src="'+item.image+'" width="100%" height="100%" /></div>');
   			});
   			new Swiper('.ads.swiper-container', {
   				autoplay : 5000,
   			});
   			//推荐积分商品
   			var scoreGoodsList = data.recommendScoreGoodsList;
   			$('.integral').find($('.swiper-wrapper')).children().remove();
   			$(scoreGoodsList).each(function(index,item){
   				$('.integral').find($('.swiper-wrapper')).append('<div class="swiper-slide">'+
   		            	'<img src="'+item.coverPic+'" width="100%">'+
   		                '<p class="color">积分：'+item.score+'</p>'+
   		                '<p class="score">原价：￥'+item.costPrice+'元</p></div>');
   			});
   			new Swiper('.integral.swiper-container', { 
   		        autoplay : 5000,
   		        pagination: '.swiper-pagination',
   		        slidesPerView: 3,
   		        paginationClickable: true,
   		        spaceBetween: 5,
   		        freeMode: true
   		    });
   			//分店
   			var branchs = data.branchs;
   			var branchsSize = 0;
   			$(branchs).each(function(index,item){
   				branchsSize = branchsSize + 1;
   			});
   			
   			
   			getSellerGoods(data.sellerId);
   		}
   	});
});
//商家商品
function getSellerGoods(sellerId){
	$.ajax({
		url:'${BASEPATH_IN_SESSION}invoke/mall/getGoodsListForWeb',
		type:"post",
		data:{"data":'{"data":{"mallTyle":"1","sellerId":"'+sellerId+'","pageIndex":1}}'},	
		dataType: "json",//返回json格式的数据
		success:function(dataJson){
			var data = dataJson.data;
			var dataList = data.dataList;
			$(dataList).each(function(index,item){
				$('#goodsDiv').append('<div class="all"><a href=${BASEPATH_IN_SESSION}invoke/shareWeb/mall/getGoods?data={"data":{"goodsId":"'+item.goodsId+'"}}>'+
				        '<div class="img">'+
			            '<img src="'+item.coverPic+'" width="100%">'+
			            '<span class="color01">'+item.expressTactics+'</span>'+
			        '</div>'+
			        '<p class="name">'+item.name+'</p>'+
			        '<div class="price">'+
			        	'<h5>￥'+item.price+'</h5>'+
			            '<p><b>￥'+item.costPrice+'</b></p>'+
			            '<span>已售'+item.salesVolume+'份</span>'+
			        '</div>'+
			    	'</div>');
			});
			
		}
});
}

</script>
</body>
</html>


