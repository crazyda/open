<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>特产速递</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="${BASEPATH_IN_SESSION }shareweb/css/bootstrap.min.css">
<link type="text/css" href="${BASEPATH_IN_SESSION }shareweb/css/swiper.min.css" rel="stylesheet">
<link type="text/css" href="${BASEPATH_IN_SESSION }shareweb/css/FE-special-local.css" rel="stylesheet">
<script src="${BASEPATH_IN_SESSION }shareweb/js/jquery.min.js" type="text/javascript"></script>
<script src="${BASEPATH_IN_SESSION }shareweb/js/swiper.jquery.min.js" type="text/javascript"></script>
<script src="${BASEPATH_IN_SESSION }shareweb/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${BASEPATH_IN_SESSION }shareweb/js/my.js" type="text/javascript"></script>
</head>

<body>
<div class="head_special">
	<div class="back">
    	<a href="index.html"><span></span></a>
    </div>
    <h4>特产速递</h4>
</div>
<div class="banner"><img src="${BASEPATH_IN_SESSION }shareweb/images/banner_special.png" width="100%"/></div>
<!---------------推荐品牌------------------------>
<%-- <div class="brands">
  <div class="title">
    	<span class="left"></span>
    	<h4><i><img src="${BASEPATH_IN_SESSION }shareweb/images/heart.png" width="18" style="margin-bottom:3px;"/></i>推荐品牌</h4>
        <span class="right"></span>
    </div>
    <ul>
    	<li><img src="${BASEPATH_IN_SESSION }shareweb/images/brands01.png" width="96%"/></li>
    	<li><img src="${BASEPATH_IN_SESSION }shareweb/images/brands02.png" width="96%"/></li>
    	<li><img src="${BASEPATH_IN_SESSION }shareweb/images/brands03.png" width="96%"/></li>
    	<li><img src="${BASEPATH_IN_SESSION }shareweb/images/brands04.png" width="96%"/></li>
    	<li><img src="${BASEPATH_IN_SESSION }shareweb/images/brands05.png" width="96%"/></li>
    	<li><img src="${BASEPATH_IN_SESSION }shareweb/images/brands06.png" width="96%"/></li>
    	<li><img src="${BASEPATH_IN_SESSION }shareweb/images/brands07.png" width="96%"/></li>
    	<li><img src="${BASEPATH_IN_SESSION }shareweb/images/brands08.png" width="96%"/></li>
    	<li><img src="${BASEPATH_IN_SESSION }shareweb/images/brands09.png" width="96%"/></li>
    	<li><img src="${BASEPATH_IN_SESSION }shareweb/images/brands10.png" width="96%"/></li>
    	<li><img src="${BASEPATH_IN_SESSION }shareweb/images/brands11.png" width="96%"/></li>
    	<li><img src="${BASEPATH_IN_SESSION }shareweb/images/brands12.png" width="96%"/></li>
    </ul>
</div> --%>
<!------------分类tab------------------------------->
<div class="tab">
	<h4>
    	<span class="current"><i><img src="${BASEPATH_IN_SESSION }shareweb/images/jiu-icon01.png" width="18"/>全部分类</i></span>
    	<span><i><img src="${BASEPATH_IN_SESSION }shareweb/images/special-icon02.png" width="18"/>产地</i></span>
    	<span><i><img src="${BASEPATH_IN_SESSION }shareweb/images/special-icon03.png" width="18"/>包装</i></span>
    </h4>
    <ul>
   	    <li class="current">
            <div class="all">
                <div class="img">
                    <img src="${BASEPATH_IN_SESSION }shareweb/images/special-pic01.png" width="100%">
                </div>
                <p class="name">湖北特产小鱼干野生小河鱼农家特产 烟熏小鱼干</p>
                <div class="price">
                    <p>￥30</p>
                    <span>满50元(包邮)</span>
                </div>
            </div>
            <div class="all">
                <div class="img">
                    <img src="${BASEPATH_IN_SESSION }shareweb/images/special-pic02.png" width="100%">
                </div>
                <p class="name">正宗新疆葡萄干 500g</p>
              <div class="price">
                <p>￥60</p>
                  <span class="blue">（包邮）</span>
                </div>
            </div>
            <div class="all">
                <div class="img">
                    <img src="${BASEPATH_IN_SESSION }shareweb/images/special-pic03.png" width="100%">
                </div>
                <p class="name">正宗祥和枣泥糕 手工糕点传统白皮零食500g</p>
                <div class="price">
                    <p>￥25</p>
                    <span>满150元(包邮)</span>
                </div>
            </div>
            <div class="all">
                <div class="img">
                    <img src="${BASEPATH_IN_SESSION }shareweb/images/special-pic04.png" width="100%">
                </div>
                <p class="name">原味散装德祥隆煮饼特惠装2公斤 闻喜煮饼</p>
              <div class="price">
                <p>￥19</p>
                <span>满90元(包邮)</span>
                </div>
            </div>
            <div class="all">
                <div class="img">
                    <img src="${BASEPATH_IN_SESSION }shareweb/images/special-pic05.png" width="100%">
                </div>
                <p class="name">原味传统糕点闽台特产零食小吃</p>
                <div class="price">
                    <p>￥25</p>
                    <span class="blue">运费10元</span>
                </div>
            </div>
            <div class="all">
                <div class="img">
                    <img src="${BASEPATH_IN_SESSION }shareweb/images/special-pic06.png" width="100%">
                </div>
                <p class="name">特级黑枣阿胶枣蜜枣干延安特产500g</p>
              <div class="price">
                <p>￥120</p>
                    <span>(包邮)</span>
                </div>
            </div>
            <div class="all">
                <div class="img">
                    <img src="${BASEPATH_IN_SESSION }shareweb/images/special-pic07.png" width="100%">
                </div>
                <p class="name">四川特产鸡蛋干100g鸡蛋干蛋整箱零食豆干</p>
                <div class="price">
                    <p>￥125</p>
                    <span class="blue">（包邮）</span>
                </div>
            </div>
            <div class="all">
                <div class="img">
                    <img src="${BASEPATH_IN_SESSION }shareweb/images/special-pic08.png" width="100%">
                </div>
                <p class="name">重庆特产零食小吃蚕豆炒货麻辣味食品</p>
              <div class="price">
                <p>￥19</p>
                    <span>满90元(包邮)</span>
                </div>
            </div>
            <div class="all">
                <div class="img">
                    <img src="${BASEPATH_IN_SESSION }shareweb/images/special-pic09.png" width="100%">
                </div>
                <p class="name">清真隆盛糕点 蜜三刀</p>
                <div class="price">
                    <p>￥25</p>
                    <span class="blue">（包邮）</span>
                </div>
            </div>
            <div class="all">
                <div class="img">
                    <img src="${BASEPATH_IN_SESSION }shareweb/images/special-pic10.png" width="100%">
                </div>
                <p class="name">云南核桃坚果特产1000g</p>
              <div class="price">
                <p>￥300</p>
                    <span>满90元(包邮)</span>
                </div>
            </div>
        </li>
    	<li>智能排序</li>
    	<li>全部分类</li>
    </ul>
</div>
</body>
</html>
<script type="text/javascript">
$(function(){
	$.ajax({
		url:"${BASEPATH_IN_SESSION}invoke/mall/preferential99",
		type:"post",
		data:{"data":'${dataJson}'},
		dataType: "text",
		success:function(dataJson){//获得数据成功
			var headBanners = dataJson.data.headBanners;
			$(headBanners).each(function(index,item){
				$('.banner').append('<img src="'+item.image+'" width="100%"/>');
			});
		}
	});
});
//-----------------------------特产商品列表--------------------------------------------------
$(function(){
	$.ajax({
		url:"${BASEPATH_IN_SESSION}invoke/shareWeb/getGoodsListJson",
		type:"post",
		data:{"data":'${dataJson}'},
		dataType: "text",
		success:function(dataJson){//获得数据成功
			$.ajax({
				url:"${BASEPATH_IN_SESSION}invoke/mall/getGoodsList",
				type:"post",
				data:{"data":dataJson,"mallTyle":"4","pageIndex":"1"},
				dataType: "text",
				success:function(dataJson){//获得数据成功
					$(".tab li").append(
						'<div class="all"> <div class="img">'
						+'<img src="'+item.coverPic+'" width="100%"></div><p class="name">item.seller.remark</p>'
						+'<div class="price"><p>￥'+item.price+'</p><span class="blue">'+item.expressTactics+'</span></div></div>'
					);
				}
			});
		}
	});
});
//---------------------------------------------------------------------------------------
</script>