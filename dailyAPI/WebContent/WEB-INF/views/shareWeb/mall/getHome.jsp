<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>每天积分购物</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="${BASEPATH_IN_SESSION }shareweb/css/bootstrap.min.css">
<!-----------移动端轮播效果插件------------------------->
<link type="text/css" href="${BASEPATH_IN_SESSION }shareweb/css/swiper.min.css" rel="stylesheet">
<link type="text/css" href="${BASEPATH_IN_SESSION }shareweb/css/style.css" rel="stylesheet">
<script src="${BASEPATH_IN_SESSION }shareweb/js/jquery.min.js" type="text/javascript"></script>
<script src="${BASEPATH_IN_SESSION }shareweb/js/swiper.jquery.min.js" type="text/javascript"></script>
<script src="${BASEPATH_IN_SESSION }shareweb/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${BASEPATH_IN_SESSION }shareweb/js/my.js" type="text/javascript"></script>
</head>

<body>
<!--搜索栏------------------>
<div class="w-search">
	<div class="w-search-inner position-fix">
    	<div class="w-search-gps">
			<span>广州市 <i class="fa fa-angle-down"></i></span>
            <ul>
            	<li>北京市</li>
            	<li>上海市</li>
            	<li>深圳市</li>
            	<li>天津市</li>
            </ul>
        </div>
        <form class="w-search-form">
            <input class="span2" type="text" placeholder="关键字搜索">
            <span class="add-on"></span>
        </form>
        <div class="w-search-share"><span></span></div>
    </div>
</div>
<!--banner------------------>
<div class="banner">
	<div class="swiper-container banner">
		<div class="swiper-wrapper"></div>
	</div>
</div>
<!----产品分类--------------------->
<div class="sort">
	<ul></ul>
</div>

<!-------Module模块分类------------------------>
<div class="module">
	<div class="left"></div>
    <div class="right">
    	<div class="top"></div>
        <div class="btm"></div>
    </div>
</div>
<!---------selection更多精选-------------------->
<%-- <div class="selection">
   <div class="swiper-container select-con">
            <div class="swiper-wrapper">
                <div class="swiper-slide"><img src="${BASEPATH_IN_SESSION }shareweb/images/selection01.png" height="100%"/></div>
                <div class="swiper-slide"><img src="${BASEPATH_IN_SESSION }shareweb/images/selection02.png" height="100%"/></div>
                <div class="swiper-slide"><img src="${BASEPATH_IN_SESSION }shareweb/images/selection03.png" height="100%"/></div>
                <div class="swiper-slide"><img src="${BASEPATH_IN_SESSION }shareweb/images/selection04.png" height="100%"/></div>
                <div class="swiper-slide"><img src="${BASEPATH_IN_SESSION }shareweb/images/selection05.png" height="100%"/></div>
                <div class="swiper-slide"><img src="${BASEPATH_IN_SESSION }shareweb/images/selection06.png" height="100%"/></div>
                <div class="swiper-slide"><img src="${BASEPATH_IN_SESSION }shareweb/images/selection01.png" height="100%"/></div>
                <div class="swiper-slide"><img src="${BASEPATH_IN_SESSION }shareweb/images/selection02.png" height="100%"/></div>
                <div class="swiper-slide"><img src="${BASEPATH_IN_SESSION }shareweb/images/selection03.png" height="100%"/></div>
                <div class="swiper-slide"><img src="${BASEPATH_IN_SESSION }shareweb/images/selection04.png" height="100%"/></div>
            </div>
    </div> --%>
    <!---------更多精选品牌轮播----------------->
<!-- 	<script>
		var swiper = new Swiper('.select-con.swiper-container', { 
			autoplay : 5000,
			pagination: '.swiper-pagination',
			slidesPerView: 6,
			paginationClickable: true,
			spaceBetween: 30,
			freeMode: true
		});
    </script>
    <div class="more">更多<br>精选</div>
</div> -->
<!------------爱划算特卖会ads-------------------------->
<div class="swiper-container ads">
        <div class="swiper-wrapper"></div>
</div>
<script>
	var swiper = new Swiper('.ads.swiper-container', {
		autoplay : 5000,
		pagination: '.swiper-pagination',
		slidesPerView: 2,
		paginationClickable: true,
		direction: 'vertical'
	});
</script>

<!------------spike限时秒杀-------------------------->
<div class="spike tab">
	<div class="tab_all">
    	<h5>限时秒杀</h5>
        <h4></h4>
    </div>
    <ul>
        <li class="current">
		  <div class="daily-time">
                <p>距离本场结束:<span id="times"></span></p>
                <!--------限时秒杀时间控制----------------->
                <SCRIPT LANGUAGE="JavaScript">
                var killTime = null;	
                function changeTime(time,obj){
                	$('.tab_all span').removeClass('current');
                	$(obj).addClass('current');
                	killTime = time;
                }
              	function fresh()
					{		
            	  			var myDate = new Date();
							var endtime=new Date(myDate.getFullYear()+"/"+(myDate.getMonth()+1)+"/"+myDate.getDate()+","+killTime);
							var nowtime = new Date();
							var leftsecond=parseInt((endtime.getTime()-nowtime.getTime())/1000);
							d=parseInt(leftsecond/3600/24);
							h=parseInt((leftsecond/3600)%24);
							m=parseInt((leftsecond/60)%60);
							s=parseInt(leftsecond%60);
						   
					//        document.getElementById("times").innerHTML=__h+"小时"+__m+"分"+__s+"秒";
					document.getElementById("times").innerHTML=h+":"+m+":"+s;
							if(leftsecond<=0){
							document.getElementById("times").innerHTML="抢购已结束";
							//clearInterval(sh);
							}
					}
					fresh();
					var sh;
					sh=setInterval(fresh,1000);
                </SCRIPT>
                <a href="seckill.html">更多 <i class="fa fa-angle-right"></i></a>
            </div>
            <div class="daily-time-con"></div>
        </li>
    	<li>
        	<a href="#">111</a>
        	<a href="#">111</a>
        </li>
    	<li>
        	<a href="#">222</a>
        	<a href="#">222</a>
        </li>
  </ul>
</div>
<!------------积分兑换Redeem-------------------------->
<div class="redeem">
	<div class="redeem-head">
    	<h4>积分兑换</h4>
        <a href="FE-redeem.html">更多 <i class="fa fa-angle-right"></i></a>
    </div>
    <div class="redeem-tab">
    	<h4></h4>
        <ul>
        	<li class="current">
                   <div class="swiper-container Choice">
                            <div class="swiper-wrapper">
                               <%-- <div class="swiper-slide">
                                	<img src="${BASEPATH_IN_SESSION }shareweb/images/Choice01.png" width="100%"/>
                                	<p class="color">积分：980</p>
                                    <p class="score">原价：￥90</p>
                                </div>
                                <div class="swiper-slide">
                                	<img src="${BASEPATH_IN_SESSION }shareweb/images/Choice02.png" width="100%"/>
                                	<p class="color">积分：520</p>
                                    <p class="score">原价：￥52</p>
                                </div>
                                <div class="swiper-slide">
                                	<img src="${BASEPATH_IN_SESSION }shareweb/images/Choice03.png" width="100%"/>
                                	<p class="color">积分：1120</p>
                                    <p class="score">原价：￥112</p>
                                </div>
                                <div class="swiper-slide">
                                	<img src="${BASEPATH_IN_SESSION }shareweb/images/Choice04.png" width="100%"/>
                                	<p class="color">积分：980</p>
                                    <p class="score">原价：￥98</p>
                                </div>
                                <div class="swiper-slide">
                                	<img src="${BASEPATH_IN_SESSION }shareweb/images/Choice01.png" width="100%"/>
                                	<p class="color">积分：980</p>
                                    <p class="score">原价：￥90</p>
                                </div>
                                <div class="swiper-slide">
                                	<img src="${BASEPATH_IN_SESSION }shareweb/images/Choice02.png" width="100%"/>
                                	<p class="color">积分：520</p>
                                    <p class="score">原价：￥52</p>
                                </div>  --%>
                            </div>
                    </div>
                    <!---------更多精选品牌轮播----------------->
                    <script>
                        var swiper = new Swiper('.Choice.swiper-container', { 
                            autoplay : 5000,
                            pagination: '.swiper-pagination',
                            slidesPerView: 3,
                            paginationClickable: true,
                            spaceBetween: 30,
                            freeMode: true
                        });
                    </script>
            </li>
   <!--      	<li>送爸妈</li>
        	<li>送宝贝</li>
        	<li>送情人</li>
        	<li>送基友</li>
        	<li>送闺蜜</li>
        	<li>送同事</li> -->
        </ul>
    </div>
</div>
<!------------周边店铺shops------------------------>
<div class="shops">
	<div class="shops-head">
    	<h4><i><img src="${BASEPATH_IN_SESSION }shareweb/images/shop-icon.png"/></i> 周边店铺</h4>
        <a href="FE-round-shop.html">更多 <i class="fa fa-angle-right"></i></a>
    </div>
    <%-- <div class="shop01">
    	<div class="shop-detail">
        	<dl class="shop-name">
            	<dt><img src="${BASEPATH_IN_SESSION }shareweb/images/shop01.png" width="100%"/></dt>
                <dd><b>YY酸奶吧</b> <i>首</i> <i>减</i> <i>惠</i><br><span>天河区中山中路20号</span></dd>
            </dl>
            <div class="shop-in">
           	  <p><i><img src="${BASEPATH_IN_SESSION }shareweb/images/locate.png" width="14"/></i> 18.56km</p>
              <p><i><img src="${BASEPATH_IN_SESSION }shareweb/images/heart-red.png" width="14"/></i><span>进店逛逛</span></p>
            </div>
        </div>
        <div class="shop-goods">
       		<ul>
            	<li>
                	<img src="${BASEPATH_IN_SESSION }shareweb/images/shop01-goods01.png" width="100%">
                    <p><span>￥6.0</span><br>(或60积分兑换)</p>
                </li>
            	<li>
                	<img src="${BASEPATH_IN_SESSION }shareweb/images/shop01-goods02.png" width="100%">
                    <p><span>￥12.0</span><br>(或120积分兑换)</p>
                </li>
            	<li>
                	<img src="${BASEPATH_IN_SESSION }shareweb/images/shop01-goods03.png" width="100%">
                    <p><span>￥13.0</span><br>(或130积分兑换)</p>
                </li>
            </ul>
       </div>
    </div>
    <div class="shop01 shop02">
    	<div class="shop-detail">
        	<dl class="shop-name">
            	<dt><img src="${BASEPATH_IN_SESSION }shareweb/images/shop02.png" width="100%"/></dt>
                <dd><b>九宫格婚纱摄影</b> <i>首</i> <i>减</i><br><span>天河区汇景北路180号</span></dd>
            </dl>
            <div class="shop-in">
           	  <p><i><img src="${BASEPATH_IN_SESSION }shareweb/images/locate.png" width="14"/></i> 1.56km</p>
              <p><i><img src="${BASEPATH_IN_SESSION }shareweb/images/heart-hui.png" width="14"/></i><span>进店逛逛</span></p>
          </div>
       </div>
       <div class="shop-goods">
       		<ul>
            	<li>
                	<img src="${BASEPATH_IN_SESSION }shareweb/images/shop02-goods01.png" width="100%">
                    <p><span>￥150.0</span><br>(或1500积分兑换)</p>
                </li>
            	<li>
                	<img src="${BASEPATH_IN_SESSION }shareweb/images/shop02-goods02.png" width="100%">
                    <p><span>￥300.0</span><br>(或3000积分兑换)</p>
                </li>
            	<li>
                	<img src="${BASEPATH_IN_SESSION }shareweb/images/shop02-goods03.png" width="100%">
                    <p><span>￥800.0</span><br>(或8000积分兑换)</p>
                </li>
            </ul>
       </div>
    </div>
    <div class="shop01 shop03">
    	<div class="shop-detail">
        	<dl class="shop-name">
            	<dt><img src="${BASEPATH_IN_SESSION }shareweb/images/shop03.png" width="100%"/></dt>
                <dd><b>牛8公馆</b> <i>首</i> <i>减</i><br><span>天河区汇景北路11号</span></dd>
            </dl>
            <div class="shop-in">
           	  <p><i><img src="${BASEPATH_IN_SESSION }shareweb/images/locate.png" width="14"/></i> 16km</p>
              <p><i><img src="${BASEPATH_IN_SESSION }shareweb/images/heart-hui.png" width="14"/></i><span>进店逛逛</span></p>
          </div>
       </div>
       <div class="shop-goods">
       		<ul>
            	<li>
                	<img src="${BASEPATH_IN_SESSION }shareweb/images/shop03-goods01.png" width="100%">
                    <p><span>￥248.0</span><br>(或2480积分兑换)</p>
                </li>
            	<li>
                	<img src="${BASEPATH_IN_SESSION }shareweb/images/shop03-goods02.png" width="100%">
                    <p><span>￥140.0</span><br>(或1400积分兑换)</p>
                </li>
            	<li>
                	<img src="${BASEPATH_IN_SESSION }shareweb/images/shop03-goods03.png" width="100%">
                    <p><span>￥200.0</span><br>(或2000积分兑换)</p>
                </li>
            </ul>
       </div>
    </div> --%>
</div>
<!------------主菜单menu------------------------>
<div class="menu">
	<ul>
    	<li class="current"><a href="index.html"><img src="${BASEPATH_IN_SESSION }shareweb/images/home-hover.png" width="22"/><br>首页</a></li>
    	<li><a href="SC-shop-cart.html"><img src="${BASEPATH_IN_SESSION }shareweb/images/shop.png" width="22"/><br>购物车</a></li>
    	<li><a href="OU-order.html"><img src="${BASEPATH_IN_SESSION }shareweb/images/order.png" width="22"/><br>订单</a></li>
    	<li><a href="personal.jsp"><img src="${BASEPATH_IN_SESSION }shareweb/images/personal.png" width="22"/><br>个人中心</a></li>
    </ul>
</div>
</body>
</html>
<script type="text/javascript">
//--------------定位跟随------------------------
	var num_redeem=$('.redeem-head').offset().top;
	var num_shop=$('.shops-head').offset().top;
	$(window).scroll(function() {
		var top=$(window).scrollTop();
		//$('.redeem-head').html(top)
		
		if( top<50 ){ 
			$('.w-search-inner').css('background','url(${BASEPATH_IN_SESSION }shareweb/images/jingling.png) repeat-x left -77px');
		}else if( 50<top&&top<num_redeem ){ 
			$('.w-search-inner').css('background','#ff2d47');
			$('.w-search-inner.position-fix').css('position','fixed');
			$('.redeem .redeem-head').css({'position':'static'});
			$('.shops .shops-head').css('position','static');
		}else if( num_redeem<top&&top<num_shop ){ 
			$('.w-search-inner.position-fix').css('position','static');
			$('.shops .shops-head').css('position','static');
			$('.redeem .redeem-head').css({'position':'fixed', 'top':0, 'left':0, 'z-index':999,'background':'#fff','width':'100%'});
			
		}else if( num_shop<top ){ 
			$('.w-search-inner.position-fix').css('position','static');
			$('.redeem .redeem-head').css({'position':'static'});
			$('.shops .shops-head').css({'position':'fixed', 'top':0, 'left':0, 'z-index':999,'background':'#fff','width':'100%'});
		}
	});
	
	
	$(function(){
		$.ajax({
	   		url:"${BASEPATH_IN_SESSION}invoke/mall/getHome",
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
	   			var banner = data.headBanners;
	   			$(banner).each(function(index,item){
	   				$('.banner').find($('.swiper-wrapper')).append('<div class="swiper-slide"><a href="'+item.uri+'">'+
	   				'<img src="'+item.image+'" width="100%" /></div>');
	   			});
	   			new Swiper('.banner.swiper-container', {
	   				autoplay : 5000,
	   			});
	   			//分类
	   			var goodsClassifys = data.goodsClassifys;
	   			$(goodsClassifys).each(function(index,item){
	   				var url = '';
	   				$('.sort').find($('ul')).append('<li><a href="'+url+'"><img src="'+item.image+'" width="60%"/><br><span>'+item.name+'</span></a></li>');
	   			});
	   			//久久特惠图文
	   			var preferential99 = data.preferential99;
	   			var preferential99_url = '${BASEPATH_IN_SESSION}invoke/shareWeb/mall/preferential99?data=${dataJson}';
	   			$('.module').find('.left').append('<a href=\''+preferential99_url+'\'><img src="'+preferential99.image+'" width="100%"/></a>');
	   			//各地特产图文
	   			var specialLocalProduct = data.specialLocalProduct;
	   			var specialLocalProduct_url = '${BASEPATH_IN_SESSION}invoke/shareWeb/mall/specialLocalProduct?data=${dataJson}';
	   			$('.module').find('.top').append('<a href=\''+specialLocalProduct_url+'\'"><img src="'+specialLocalProduct.image+'" width="100%"/></a>');
	   			//积分兑换图文
	   			var scoreMall = data.scoreMall;
	   			var scoreMall_url = '${BASEPATH_IN_SESSION}invoke/shareWeb/mall/scoreExchangeMall?data=${dataJson}';
	   			$('.module').find('.btm').append('<a href=\''+scoreMall_url+'\'><img src="'+scoreMall.image+'" width="100%"/></a>'); 
	   			//爱划算
	   			var costEfficients = data.costEfficients;
	   			$(costEfficients).each(function(index,item){
	   				var url = '';
	   				$('.swiper-wrapper').append('<div class="swiper-slide"><div style="padding-bottom:10px; overflow:hidden;"><h5>爱<br>划<br>算</h5><a href="'+url+'"><p style="background:url('+item.image+') no-repeat right top; background-size:cover;"></p></a></div></div>');
	   			});
	   			//特卖汇
	   			var saleExchanges = data.saleExchanges;
	   			$(saleExchanges).each(function(index,item){
	   				var url = '';
	   				$('.swiper-wrapper').append('<div class="swiper-slide"><div style="padding-bottom:10px; overflow:hidden;"><h5>特<br>卖<br>汇</h5><a href="'+url+'"><p style="background:url('+item.image+') no-repeat right top; background-size:cover;"></p></a></div></div>');
	   			});
	   			//限时秒杀
	   			var secondKill = data.secondKill;
	   			$(secondKill).each(function(index,item){
	   				var url = '';
	   				if(index==0){
	   					killTime = item.startDate;
	   					$('.tab_all').find('h4').append('<span class="current"  onclick="changeTime(\''+item.startDate+'\',this),getKillGoods(\''+item.secondKillId+'\') " >'+item.secondKillName+'</span>');
	   				}else{
	   					$('.tab_all').find('h4').append('<span onclick="changeTime(\''+item.startDate+'\',this),getKillGoods(\''+item.secondKillId+'\') " >'+item.secondKillName+'</span>');
	   				}
	   				
	   			});
	   			//积分兑换
	   			var scoreGoodsClassifys = data.scoreGoodsClassifys;
	   			$(scoreGoodsClassifys).each(function(index,item){
	   				if(index==0){
	   					$('.redeem-tab').find('h4').append('<span class="current" onclick="getScoreImages(\''+item.typeId+'\',this)"><i>'+item.name+'</i></span>');
	   				}else{
	   					$('.redeem-tab').find('h4').append('<span onclick="getScoreImages(\''+item.typeId+'\',this)"><i>'+item.name+'</i></span>');
	   				}
	   			});
	   		}
	   	});
	});
//---------------------------------------秒杀商品--------------------------------------------------------	
	//获取秒杀商品列表
	function getKillGoods(secondKillId){
		$(".all").remove();
		$.ajax({
	   		url:"${BASEPATH_IN_SESSION}invoke/shareWeb/getGoodsListJson",
	   		type:"post",
	   		data:{"data":'${dataJson}',"secondKillId":secondKillId,"mallTyle":"3","pageIndex":"1"},	
	   	 	dataType: "text",//返回json格式的数据
	   	 	async:false,
	   	 	success:function(data){
	   	 		$.ajax({
		   			url:"${BASEPATH_IN_SESSION}invoke/mall/getGoodsList",
		   			type:"post",
		   			data:{"data":data},	
		   	 		dataType: "json",//返回json格式的数据
		   	 		async:false,
		   	 		success:function(datas){	
		   	 			var dataList = datas.data.dataList;
		   	 			var count = 1;
		   	 			$(dataList).each(function(index,item){
		   	 				var discount;
		   	 				if(index<=count){
		   	 					if(item.discount==undefined){
		   	 						discount="";
		   	 					}else{
		   	 						discount=item.discount;
		   	 					}
		   	 				$(".daily-time-con").append('<div class="all"><div class="img"><a href="FE-product-detail-jifen.html">'
				   	 		+'<img src="'+item.coverPic+'" width="100%" /></a><span>'+discount+'</span></div>'
				   	 		+' <p class="name">'+item.name+'</p>'
				   	 		+'<div class="price"><p class="p1">'+item.price+'</p><p class="p2"><b>'+item.costPrice+'</b><span>'+item.expressTactics+'</span></p></div></div>'
				   	 			);
		   	 				}
		   	 				}); 
		   	 						
		   	 		}
		   		});
	   	 	}
	   		});
	}
	$(function(){
		getKillGoods('18');
	});
//----------------------------------------------------------------------------------------------------	
//---------------------------------------积分兑换--------------------------------------------------------	
function getScoreImages(id,obj){
	if(obj!=null&&obj!=undefined){
		$('.redeem-tab span').removeClass('current');
    	$(obj).addClass('current');
	}
	$(".current .swiper-slide").remove();
	$.ajax({
		url:"${BASEPATH_IN_SESSION}invoke/shareWeb/getGoodsListJson",
   		type:"post",
   		data:{"data":'${dataJson}',"mallTyle":"2","pageIndex":"1","tag":id},	
   	 	dataType: "text",//返回json格式的数据
   	 	async:false,
   	 	success:function(data){
   	 	$.ajax({
   			url:"${BASEPATH_IN_SESSION}invoke/mall/getGoodsList",
   			type:"post",
   			data:{"data":data},	
   	 		dataType: "json",//返回json格式的数据
   	 		async:false,
   	 		success:function(datas){
   	 			var dataList = datas.data.dataList;
   	 			$(dataList).each(function(index,item){
   	 				$("li[class='current'] .swiper-wrapper").append('<div class="swiper-slide" style="width:105px;margin-right:30px;"><img src="'+item.coverPic+'" width="100%"/>'
   	 				+'<p class="color">积分：'+item.score+'</p><p class="score">原价：'+item.costPrice+'</p></div>');

   	 			});
   	 		}
   		});
   	 	}
	});
}
$(function(){
	getScoreImages('3',null);
});
//----------------------------------------------------------------------------------------------------
//--------------------------------------周边店铺--------------------------------------------------------
  $(function(){
	$.ajax({
		url:"${BASEPATH_IN_SESSION}invoke/shareWeb/getSellerListJson",
		type:"post",
   		data:{"data":'${dataJson}',"pageIndex":"1"},	
   	 	dataType: "text",//返回json格式的数据
   	 	async:false,
   	 	success:function(data){
   	 		$.ajax({
   				url:"${BASEPATH_IN_SESSION}invoke/mall/getSellerList",
   				type:"post",
   				data:{"data":data},	
   	 			dataType: "json",//返回json格式的数据
   	 			async:false,
   	 			success:function(datas){
   	 				var dataList = datas.data.dataList;
   	 				var goodsList = null;
   	 				$(dataList).each(function(index,item){
   	 				$.ajax({
	   	   					url:"${BASEPATH_IN_SESSION}invoke/shareWeb/getGoodsListJson",
	   	   					type:"post",
	   	   					data:{"data":'${dataJson}',"mallTyle":"2","pageIndex":"1","sellerId":item.sellerId},	
	   	   	 				dataType: "text",//返回json格式的数据
	   	   	 				async:false,
	   	   	 				success:function(datass){
	   	   	 					$.ajax({
	 		   						url:"${BASEPATH_IN_SESSION}invoke/mall/getGoodsList",
	 		   						type:"post",
	 		   						data:{"data":datass},	
	 		   	 					dataType: "json",//返回json格式的数据
	 		   	 					async:false,
	 		   	 					success:function(datasss){
	 		   	 						goodsList = '<div class="shop-goods"><ul>';
	   	   	 							var dataLists = datasss.data.dataList;
	   	   	 							$(dataLists).each(function(index,item){
	   	   	 							if(index<=2){
	   	   	 								goodsList=goodsList + '<li><img src="'+item.coverPic+'" width="100%">'
	   	   	 								+'<p><span>'+item.costPrice+'</span><br>(或'+item.score+'积分兑换)</p></li>';
	   	   	 							}
	   	   	 							});
	   	   	 							goodsList = goodsList +'</ul></div>';
	 		   	 					}
	   	   	 					});
	   	   	 				}
   	 					});
   	 					
   	 					
   	 					$(".shops").append('<div class="shop01"><div class="shop-detail"><dl class="shop-name">'
   	 					+'<dt><img src="'+item.sellerIcon+'" width="100%"/></dt> <dd><b>'+item.sellerName+'</b><br><span>'+item.sellerAddress+'</span></dd></dl>'
   	 					+'<div class="shop-in">'
   	 					+'<p><i><img src="${BASEPATH_IN_SESSION }shareweb/images/heart-red.png" width="14"/></i><span>进店逛逛</span></p></div></div>'+goodsList
   	 					);
   	 					
   	 				});
   	 			}
   			});
   	 	}
	});
});
//----------------------------------------------------------------------------------------------------
</script>
