<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>99特惠</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="${BASEPATH_IN_SESSION }shareweb/css/bootstrap.min.css">
<link type="text/css" href="${BASEPATH_IN_SESSION }shareweb/css/swiper.min.css" rel="stylesheet">
<link type="text/css" href="${BASEPATH_IN_SESSION }shareweb/css/FE-jiujiu.css" rel="stylesheet">
<script src="${BASEPATH_IN_SESSION }shareweb/js/jquery.min.js" type="text/javascript"></script>
<script src="${BASEPATH_IN_SESSION }shareweb/js/swiper.jquery.min.js" type="text/javascript"></script>
<script src="${BASEPATH_IN_SESSION }shareweb/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${BASEPATH_IN_SESSION }shareweb/js/my.js" type="text/javascript"></script>
</head>

<body>
<div class="head_jiu">
	<div class="back">
    	<a href="index.html"><span></span></a>
    </div>
  <h4>99特惠</h4>
  <div class="search">
    	<a href="#"><span></span></a>
    </div>
</div>
<div class="banner"><%-- <img src="${BASEPATH_IN_SESSION }shareweb/images/jiujiu-banner.png" width="100%"/> --%></div>
<!------------免单专区------------------------------->
<div class="redeem">
	<div class="redeem-head">
    	<h4>免单专区</h4>
        <span>更多 <i class="fa fa-angle-right"></i></span>
    </div>
    <div class="redeem-tab">
        <ul>
        	<li>
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
                                </div> --%>
                            </div>
                    </div>
                    <!---------免单专区轮播----------------->
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
        </ul>
    </div>
</div>
<!------------免单券领取区------------------------------->
<div class="ads"><%-- <img src="${BASEPATH_IN_SESSION }shareweb/images/jiujiu-ads.png" width="100%"/> --%></div>
<!------------分类tab------------------------------->
<div class="tab">
	<h4>
    	<span class="current"><i><img src="${BASEPATH_IN_SESSION }shareweb/images/jiu-icon01.png" width="18"/>全部分类</i></span>
    	<span><i><img src="${BASEPATH_IN_SESSION }shareweb/images/jiu-icon02.png" width="18"/>智能排序</i></span>
    	<span><i><img src="${BASEPATH_IN_SESSION }shareweb/images/jiu-icon03.png" width="18"/>价格</i></span>
    </h4>
    <ul>
   	    <li class="current">
           <%--  <div class="all">
                <div class="img">
                    <img src="${BASEPATH_IN_SESSION }shareweb/images/jiu-pic01.png" width="100%">
                </div>
                <p class="name">日本进口山木太郎毛巾 加厚加大</p>
                <div class="price">
                    <p>￥24</p>
                    <span>(包邮)</span>
                </div>
            </div>
            <div class="all">
                <div class="img">
                    <img src="${BASEPATH_IN_SESSION }shareweb/images/jiu-pic02.png" width="100%">
                </div>
                <p class="name">正宗新疆葡萄干 500g</p>
              <div class="price">
                <p>￥60</p>
                  <span class="blue">运费20元</span>
                </div>
            </div>
            <div class="all">
                <div class="img">
                    <img src="${BASEPATH_IN_SESSION }shareweb/images/jiu-pic03.png" width="100%">
                </div>
                <p class="name">夏款婴幼儿宝宝粉色连衣裙</p>
                <div class="price">
                    <p>￥25</p>
                    <span>(包邮)</span>
                </div>
            </div>
            <div class="all">
                <div class="img">
                    <img src="${BASEPATH_IN_SESSION }shareweb/images/jiu-pic04.png" width="100%">
                </div>
                <p class="name">100%纯羊绒春装韩版针织开衫女大码羊毛薄外套</p>
              <div class="price">
                <p>￥19</p>
                <span>(包邮)</span>
                </div>
            </div>
            <div class="all">
                <div class="img">
                    <img src="${BASEPATH_IN_SESSION }shareweb/images/jiu-pic05.png" width="100%">
                </div>
                <p class="name">正宗祥禾枣泥糕 手工糕点传统白皮零食 500g</p>
                <div class="price">
                    <p>￥25</p>
                    <span class="blue">运费10元</span>
                </div>
            </div>
            <div class="all">
                <div class="img">
                    <img src="${BASEPATH_IN_SESSION }shareweb/images/jiu-pic06.png" width="100%">
                </div>
                <p class="name">百货公司活动礼品定制宿舍寝室必备神器</p>
              <div class="price">
                <p>￥19</p>
                    <span>(包邮)</span>
                </div>
            </div> --%>
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
		dataType: "json",
		success:function(dataJson){//获得数据成功
			if(dataJson.status!=1){//异常状态，返回错误描述
   				return null;
   			}
			//头部轮播图
			var headBanners = dataJson.data.headBanners;
			$(headBanners).each(function(index,item){
				$(".banner").append('<img src="'+item.image+'" width="100%"/>');
			});
			//免单商品
			var freeGoods = dataJson.data.freeGoods;
			$(freeGoods).each(function(index,item){
				$(".redeem-tab .swiper-container .swiper-wrapper").append(
				'<div class="swiper-slide" style="width: 99.3333px;margin-right: 30px;">'
				+'<img src="'+item.coverPic+'" width="100%"/>'
				+'<p class="color">积分：'+item.score+'</p><p class="score">原价：'+item.price+'</p></div>'
				);
			});
			//成为会员横幅广告
			var becomeVipBanner = dataJson.data.becomeVipBanner;
			$(becomeVipBanner).each(function(index,item){
				$(".ads").append('<img src="'+item.image+'" width="100%"/>');
			});
		}
	});
	
});
//------------------------------99特惠商品列表---------------------------------
$(function(){
	$.ajax({
		url:"${BASEPATH_IN_SESSION}invoke/shareWeb/getGoodsListJson",
		type:"post",
		data:{"data":'${dataJson}',"mallTyle":"5","pageIndex":"1"},
		dataType: "text",
		success:function(dataJson){
			$.ajax({
				url:"${BASEPATH_IN_SESSION}invoke/mall/getGoodsList",
				type:"post",
				data:{"data":dataJson},
				dataType: "Json",
				success:function(data){
					var dataList = data.data.dataList;
					$(dataList).each(function(index,item){
						'<div class="all"> <div class="img">'
						+'<img src="'+item.coverPic+'" width="100%"></div><p class="name">item.seller.remark</p>'
						+'<div class="price"><p>￥'+item.price+'</p><span class="blue">'+item.expressTactics+'</span></div></div>'
					});
				}
			});
		}
	});
});
//-----------------------------------------------------------------------------
</script>




























