package com.axp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.axp.dao.DateBaseDAO;
import com.axp.domain.OpenApp;
import com.axp.domain.OpenThemeGoods;
import com.axp.service.OpenThemeGoodsService;
import com.axp.util.JsonResponseUtil;
import com.axp.util.MD5Util;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;
import com.axp.util.StringUtil;
import com.axp.util.UrlUtil;


@Service
public class OpenThemeGoodsServiceImpl extends BaseServiceImpl<OpenThemeGoods> implements OpenThemeGoodsService{

	@Resource
	private DateBaseDAO dateBaseDAO;
	
	@Override
	public Map<String, Object> findThemeGoods(HttpServletRequest request,String themeId) {
		Parameter parameter = ParameterUtil.getParameter(request);
		String openId = parameter.getClient_id();
		String sign = parameter.getSign();
		if(themeId == ""){
			themeId = parameter.getData().getString("themeId");
		}
		
		
		QueryModel queryModel = new QueryModel();
		queryModel.clearQuery();
		queryModel.combPreEquals("clientId", openId,"client_id");
		OpenApp openApp = (OpenApp) dateBaseDAO.findOne(OpenApp.class, queryModel);
		if(openApp == null){
			return JsonResponseUtil.getJson(-0x02, "client_id错误,请联系管理员");
		}else{
			Map<String,String> signMap = new HashMap<String,String>();
			signMap.put("client_id", openId);
			
			if(!(MD5Util.getSign(signMap, openApp.getClientScrect()).equals(sign))){
				
				return JsonResponseUtil.getJson(-0x02, "签名验证不通过");
			}
		}
		
		Integer maid = Integer.valueOf(openApp.getMaid());
		
		long time = System.currentTimeMillis();
		String client_id = "";
    	String client_secret = "";
//    	if(StringUtil.openId.equals(openId)){
//    		client_id = StringUtil.client_id_other;
//    		client_secret = StringUtil.client_secret_other;
//    	}else{
    		client_id = StringUtil.client_id_axp;
    		client_secret = StringUtil.client_secret_axp;
//    	}
		Map<String,String> sign1 = new HashMap<String,String>();
		sign1.put("client_id", client_id);
		sign1.put("type", "pdd.ddk.theme.goods.search");
		sign1.put("timestamp", String.valueOf(time));
		sign1.put("data_type", StringUtil.data_type);
		sign1.put("theme_id", themeId);
		
		String sign2 = MD5Util.getSign(sign1,client_secret);
		
		String url = "http://gw-api.pinduoduo.com/api/router";
		String param = "type=pdd.ddk.theme.goods.search&data_type=JSON&timestamp="+String.valueOf(time)+"&client_id="+client_id+"&theme_id="+themeId+"&sign="+sign2;
		
		List<Map<String,Object>> orderList = null;
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>>  themeGoods = UrlUtil.sendPostForListNew(url, param, "theme_list_get_response");
		boolean hasCoupons = false;
		int quantity =0;
		
		try {
			orderList = UrlUtil.jsonObjectToList(themeGoods.get(0).get("goods_list").toString());
			if(orderList.size()>0 && orderList != null){
				
				for(int i=0;i<orderList.size();i++){
				 String	hasCoupon = orderList.get(i).get("has_coupon").toString();
				 String remainQuantity = orderList.get(i).get("coupon_remain_quantity").toString();
					if("true".equals(hasCoupon)){ //过滤没有优惠券的

						hasCoupons =true;

						quantity++;

						Map<String,Object> map = new HashMap<String,Object>();
						map.put("goods_desc", orderList.get(i).get("goods_desc").toString());
						map.put("min_group_price", orderList.get(i).get("min_group_price").toString());
						map.put("goods_gallery_urls", orderList.get(i).get("goods_gallery_urls").toString());
						map.put("goods_name", orderList.get(i).get("goods_name").toString());
						map.put("opt_name", orderList.get(i).get("opt_name").toString());
						map.put("coupon_total_quantity", orderList.get(i).get("coupon_total_quantity").toString());
						map.put("cat_id", orderList.get(i).get("cat_id").toString());
						map.put("has_coupon", hasCoupon);
						map.put("min_normal_price", orderList.get(i).get("min_normal_price").toString());
						map.put("coupon_min_order_amount", orderList.get(i).get("coupon_min_order_amount").toString());
						map.put("coupon_start_time", orderList.get(i).get("coupon_start_time").toString());
						map.put("cat_ids", orderList.get(i).get("cat_ids").toString());
						map.put("mall_name", orderList.get(i).get("mall_name").toString());
						map.put("coupon_remain_quantity", remainQuantity);
						map.put("coupon_end_time", orderList.get(i).get("coupon_end_time").toString());
						Integer rate = (Integer)orderList.get(i).get("promotion_rate")*maid/100;
						map.put("promotion_rate", rate);
						map.put("goods_eval_score", orderList.get(i).get("goods_eval_score").toString());
						map.put("goods_eval_count", orderList.get(i).get("goods_eval_count").toString());
						map.put("category_id", orderList.get(i).get("category_id").toString());
						map.put("goods_image_url", orderList.get(i).get("goods_image_url").toString());
						map.put("goods_thumbnail_url", orderList.get(i).get("goods_thumbnail_url").toString());
						map.put("avg_serv", orderList.get(i).get("avg_serv").toString());
						map.put("opt_id", orderList.get(i).get("opt_id").toString());
						map.put("avg_lgst", orderList.get(i).get("avg_lgst").toString());
						map.put("category_name", orderList.get(i).get("category_name").toString());
						map.put("avg_desc", orderList.get(i).get("avg_desc").toString());
						map.put("goods_id", orderList.get(i).get("goods_id").toString());
						map.put("coupon_discount", orderList.get(i).get("coupon_discount").toString());
						map.put("sold_quantity", orderList.get(i).get("sold_quantity").toString());
						dataList.add(map);
						
					}
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonResponseUtil.getJson(-0x01, "连接超时或者没有查询到");
		}
		Map<String,Object> statusMap = null;
		if(hasCoupons){
		   statusMap = new HashMap<String,Object>();
			statusMap.put("orderList", dataList);
		}
	
		
		

		
		
		return statusMap;
	}

	@Override
	public Map<String, Object> generateThemeUrl(HttpServletRequest request) {
		Parameter parameter = ParameterUtil.getParameter(request);
		
		String openId = parameter.getClient_id();
		String sign = parameter.getSign();
		String pid = parameter.getData().getString("pid");
		String themeIdList = parameter.getData().getString("themeIdList");//可以传多个主题id上来
		String generateShortUrl =  parameter.getData().getString("generate_short_url")==null?"true":parameter.getData().getString("generate_short_url");
		String generateMobile =  parameter.getData().getString("generate_mobile")==null?"true":parameter.getData().getString("generate_mobile");
		
		
		if(openId == null || sign == null || pid == null || themeIdList == null){
			return JsonResponseUtil.getJson(-0x02, "必要参数不能为空");
		}
		
		QueryModel query = new QueryModel();
		query.combPreEquals("clientId", openId,"client_id");
		OpenApp oc = (OpenApp) dateBaseDAO.findOne(OpenApp.class, query);
		
		if(oc == null){
			return JsonResponseUtil.getJson(-0x02, "client_id错误,请联系管理员");
		}
		Map<String,String> signMap = new HashMap<String,String>();
		signMap.put("client_id", openId);
		if(!MD5Util.getSign(signMap, oc.getClientScrect()).equals(sign)){
			return JsonResponseUtil.getJson(-0x02, "签名不通过");
		}
		
		String time = String.valueOf(System.currentTimeMillis()/1000);
		String client_id = "";
    	String client_secret = "";
//    	if(StringUtil.openId.equals(openId)){
//    		client_id = StringUtil.client_id_other;
//    		client_secret = StringUtil.client_secret_other;
//    	}else{
    		client_id = StringUtil.client_id_axp;
    		client_secret = StringUtil.client_secret_axp;
//    	}
		Map<String,String> sign1 = new HashMap<String,String>();
		sign1.put("client_id", client_id);
		sign1.put("type", "pdd.ddk.theme.prom.url.generate");
		sign1.put("timestamp", String.valueOf(time));
		sign1.put("data_type", StringUtil.data_type);
		sign1.put("theme_id_list", "["+themeIdList+"]");
		sign1.put("generate_short_url", generateShortUrl);
		sign1.put("generate_mobile", generateMobile);
		sign1.put("pid", pid);
		String sign2 = MD5Util.getSign(sign1,client_secret);
		
		List<Map<String,Object>> orderList = new ArrayList<Map<String,Object>>();
		String url = "http://gw-api.pinduoduo.com/api/router";
		String param = "type=pdd.ddk.theme.prom.url.generate&data_type=JSON&timestamp="+time+"&client_id="+client_id+"&theme_id_list=["+themeIdList+"]&sign="+sign2
						+"&generate_short_url="+generateShortUrl+"&generate_mobile="+generateMobile+"&pid="+pid;
				
		List<Map<String,Object>> themeList = UrlUtil.sendPostForListNew(url, param, "theme_promotion_url_generate_response");
		
		try {
			orderList = UrlUtil.jsonObjectToList(themeList.get(0).get("url_list").toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonResponseUtil.getJson(-0x02, "连接超时,请重试");
		}
		
		Map<String,Object> statusMap = new HashMap<String,Object>();
		statusMap.put("orderList", orderList);
		
		
		return statusMap;
	}
	
	
	
	
	
	
	
	
	
}
	
