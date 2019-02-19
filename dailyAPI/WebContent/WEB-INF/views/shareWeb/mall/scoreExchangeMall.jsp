<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>积分兑换</title>
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
<div class="head_redeem">
	<div class="back">
    	<a href="index.html"><span></span></a>
    </div>
  <h4>积分兑换</h4>
  <div class="rili">
    	<a href="#"><span></span></a>
    </div>
</div>
<!------------商品分类--------------------->
<div class="sort">
	<ul>
    	<%-- <li><a href="#"><img src="${BASEPATH_IN_SESSION }shareweb/images/sort01.png" width="60%"/><br><span>餐饮美食</span></a></li>
    	<li><a href="#"><img src="${BASEPATH_IN_SESSION }shareweb/images/sort02.png" width="60%"/><br><span>甜点饮品</span></a></li>
    	<li><a href="#"><img src="${BASEPATH_IN_SESSION }shareweb/images/sort03.png" width="60%"/><br><span>休闲娱乐</span></a></li>
    	<li><a href="#"><img src="${BASEPATH_IN_SESSION }shareweb/images/sort04.png" width="60%"/><br><span>运动健身</span></a></li>
    	<li><a href="#"><img src="${BASEPATH_IN_SESSION }shareweb/images/sort05.png" width="60%"/><br><span>养生保健</span></a></li>
    	<li><a href="#"><img src="${BASEPATH_IN_SESSION }shareweb/images/sort06.png" width="60%"/><br><span>摄影</span></a></li>
    	<li><a href="#"><img src="${BASEPATH_IN_SESSION }shareweb/images/sort07.png" width="60%"/><br><span>旅游</span></a></li>
    	<li><a href="#"><img src="${BASEPATH_IN_SESSION }shareweb/images/sort08.png" width="60%"/><br><span>酒店</span></a></li>
    	<li><a href="#"><img src="${BASEPATH_IN_SESSION }shareweb/images/sort09.png" width="60%"/><br><span>丽人</span></a></li>
    	<li><a href="#"><img src="${BASEPATH_IN_SESSION }shareweb/images/sort10.png" width="60%"/><br><span>生活服务</span></a></li> --%>
    </ul>
</div>
<!------------商品小分类--------------------->
<div class="sort_s tab">
    <h4>
       <!--  <span class="current"><i>精选</i></span>
        <span><i>送爸妈</i></span>
        <span><i>送宝贝</i></span>
        <span><i>送情人</i></span>
        <span><i>送基友</i></span>
        <span><i>送闺蜜</i></span>
        <span><i>送同事</i></span> -->
    </h4>
    <ul>
    	<li class="current">
        	<div class="ads"><img src="${BASEPATH_IN_SESSION }shareweb/images/redeem-ads.png" width="100%"/></div>
            <div class="chosen">
            	<div class="chosen_all">
                	<div><img src="${BASEPATH_IN_SESSION }shareweb/images/redeem-jing01.png" width="100%"><span>小件配饰</span></div>
                	<div><img src="${BASEPATH_IN_SESSION }shareweb/images/redeem-jing02.png" width="100%"><span>植物大赏</span></div>
                	<div><a href="FE-creative-special.html"><img src="${BASEPATH_IN_SESSION }shareweb/images/redeem-jing03.png" width="100%"><span>创意专题</span></a></div>
                	<div><img src="${BASEPATH_IN_SESSION }shareweb/images/redeem-jing04.png" width="100%"><span>丽人分享</span></div>
                </div>
            </div>
        </li>
    	<%-- <li>
        	<div class="goods_all">
                <div class="all">
                    <div class="img">
                        <img src="${BASEPATH_IN_SESSION }shareweb/images/redeem-goods01.png" width="100%">
                        <span class="color01">上门自提</span>
                    </div>
                    <p class="name">最高价值12元的招牌明星奶茶3选1</p>
                    <div class="price">
                        <h5>积分1500</h5>
                        <p><b>￥150</b></p>
                        <span>已售300份</span>
                    </div>
                </div>
                <div class="all">
                    <div class="img">
                        <img src="${BASEPATH_IN_SESSION }shareweb/images/redeem-goods02.png" width="100%">
                        <span class="color02">到店消费</span>
                    </div>
                    <p class="name">价值18元的饮品小吃9选1建议单人使用。</p>
                    <div class="price">
                        <h5>积分10800</h5>
                        <p><b>￥1080</b></p>
                        <span>已售52份</span>
                    </div>
                </div>
                <div class="all">
                    <div class="img">
                        <img src="${BASEPATH_IN_SESSION }shareweb/images/redeem-goods03.png" width="100%">
                        <span class="color03">周边派送</span>
                    </div>
                    <p class="name">最高价值8元的奶茶7选1建议单人使用</p>
                    <div class="price">
                        <h5>积分245</h5>
                        <p><b>￥24.5</b></p>
                        <span>已售1120份</span>
                    </div>
                </div>
                <div class="all">
                    <div class="img">
                        <img src="${BASEPATH_IN_SESSION }shareweb/images/redeem-goods04.png" width="100%">
                        <span class="color04">随单包邮</span>
                    </div>
                    <p class="name">优乐美多味速溶奶茶12包装</p>
                    <div class="price">
                        <h5>积分8000</h5>
                        <p><b>￥800</b></p>
                        <span>已售37份</span>
                    </div>
                </div>
                <div class="all">
                    <div class="img">
                        <img src="${BASEPATH_IN_SESSION }shareweb/images/redeem-goods05.png" width="100%">
                        <span class="color04">随单包邮</span>
                    </div>
                    <p class="name">最高价值8元的奶茶7选1建议单人使用</p>
                    <div class="price">
                        <h5>积分1090</h5>
                        <p><b>￥109</b></p>
                        <span>已售130份</span>
                    </div>
                </div>
                <div class="all">
                    <div class="img">
                        <img src="${BASEPATH_IN_SESSION }shareweb/images/redeem-goods06.png" width="100%">
                        <span class="color01">上门自提</span>
                    </div>
                    <p class="name">优乐美多味速溶奶茶12包装</p>
                    <div class="price">
                        <h5>积分2000</h5>
                        <p><b>￥200</b></p>
                        <span>已售17份</span>
                    </div>
                </div>
            </div>
        </li> --%>
    	<li>11</li>
    	<li>22</li>
    	<li>33</li>
    	<li>44</li>
    	<li>55</li>
    	<li>66</li>
    	<li>77</li> 
    </ul>
</div>
</body>
</html>
<script type="text/javascript">
$(function(){
	$.ajax({
		url:"${BASEPATH_IN_SESSION}invoke/mall/scoreExchangeMall",
		type:"post",
		data:{"data":'${dataJson}'},
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
//----------------------------------------------------------------------------------------------------	
//---------------------------------------积分兑换--------------------------------------------------------	
 function getScoreImages(id,i,obj){
	if(obj!=null&&obj!=undefined){
		$('.sort_s span').removeClass('current');
  		$(obj).addClass('current');
	}
	$(".sort_s li div").remove();
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
 	 			var classificationsStr = '';
				if(i==0){
					classificationsStr = '<div class="ads"><img src="${BASEPATH_IN_SESSION }shareweb/images/redeem-ads.png" width="100%"/></div>'
					+'<div class="chosen"><div class="chosen_all">'
					+'<div><img src="${BASEPATH_IN_SESSION }shareweb/images/redeem-jing01.png" width="100%"><span>小件配饰</span></div>'
					+'<div><img src="${BASEPATH_IN_SESSION }shareweb/images/redeem-jing02.png" width="100%"><span>植物大赏</span></div>'
					+'<div><a href="FE-creative-special.html"><img src="${BASEPATH_IN_SESSION }shareweb/images/redeem-jing03.png" width="100%"><span>创意专题</span></a></div>'
					+'<div><img src="${BASEPATH_IN_SESSION }shareweb/images/redeem-jing04.png" width="100%"><span>丽人分享</span></div>'
					+'</div></div>';
				} 
 	 			var goods_all_str = '<div class="goods_all">';
 	 			$(dataList).each(function(index,item){
 	 				
 	 				goods_all_str = goods_all_str +'<div class="all"><div class="img">'
 	 				+'<img src="'+item.coverPic+'" width="100%"><span class="color04">'+item.expressTactics+'</span></div>'
 	 				+'<p class="name">'+item.seller.remark+'</p><div class="price"><h5>积分'+item.score+'</h5>'
 	 				+'<p><b>￥'+item.costPrice+'</b></p></div></div>';
 	 			});
 	 			goods_all_str = goods_all_str + '</div>';
				$('.sort_s li:eq(0)').append(classificationsStr+''+goods_all_str);
				
 	 		}
 		});
 	 	}
	}); 
}

$(function(){
	getScoreImages('3',0,null);
}); 
//----------------------------------------------------------------------------------------------------
</script>

