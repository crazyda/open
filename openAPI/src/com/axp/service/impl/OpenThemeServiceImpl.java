package com.axp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.axp.dao.DateBaseDAO;
import com.axp.domain.OpenApp;
import com.axp.domain.OpenTheme;
import com.axp.domain.OpenThemeGoods;
import com.axp.service.OpenThemeGoodsService;
import com.axp.service.OpenThemeService;
import com.axp.util.JsonResponseUtil;
import com.axp.util.MD5Util;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;
import com.axp.util.StringUtil;
import com.axp.util.UrlUtil;


@Service
public class OpenThemeServiceImpl extends BaseServiceImpl<OpenTheme> implements OpenThemeService{
	
	@Resource
	private DateBaseDAO dateBaseDAO;
	@Autowired
	OpenThemeGoodsService openThemeGoodsService;
	
	@Override
	public Map<String, Object> thmemList(HttpServletRequest request) {
		Parameter parameter = ParameterUtil.getParameter(request);
		
		String openId = parameter.getClient_id();
		String sign = parameter.getSign();
		String pageSize = "";
		String page = "";
		if(parameter.getData() != null){
			

			pageSize = parameter.getData().getString("page_size") == null ?"50":parameter.getData().getString("page_size");

			page = parameter.getData().getString("page")== null?"1":parameter.getData().getString("page");
		
		}else{

			pageSize = "50";

			page = "1";
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
		
		
		
		Map<String ,String> signMap = new HashMap<String,String>();
		String client_id = "";
    	String client_secret = "";
//    	if(StringUtil.openId.equals(openId)){
//    		client_id = StringUtil.client_id_other;
//    		client_secret = StringUtil.client_secret_other;
//    	}else{
    		client_id = StringUtil.client_id_axp;
    		client_secret = StringUtil.client_secret_axp;
//    	}
		String time =String.valueOf( System.currentTimeMillis());
		signMap.put("data_type", StringUtil.data_type);
		signMap.put("client_id", client_id);
		signMap.put("page_size", pageSize);
		signMap.put("page", page);
		signMap.put("timestamp", time);
		signMap.put("type", "pdd.ddk.theme.list.get");
		String sign1 = MD5Util.getSign(signMap,client_secret);
		
		List<Map<String,Object>> orderList = null;
		List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
		String url = "http://gw-api.pinduoduo.com/api/router";
		String param = "type=pdd.ddk.theme.list.get&data_type=JSON&timestamp="
				+time+"&client_id="+client_id+"&sign="+sign1+"&page="+page+"&page_size="+pageSize;
		
		List<Map<String,Object>> thmemList =  UrlUtil.sendPostForListNew(url, param, "theme_list_get_response");
		
		try {
			
			orderList = UrlUtil.jsonObjectToList(thmemList.get(0).get("theme_list").toString());
			 //  orderList = UrlUtil.jsonObjectToList(((Map)thmemList.get(0)).get("theme_list").toString());
			
			//在前两个主题中,第一个主题显示全部,第二个主题显示4个
			
			List<Map<String,Object>> themeL = new ArrayList<Map<String,Object>>();
			Map<String,Object> dataMap = new HashMap<String,Object>();
			for(int i=0;i<orderList.size();i++){
				String themeId = orderList.get(i).get("id").toString();

				Map<String,Object> themeMap = openThemeGoodsService.findThemeGoods(request,themeId);
				
				if(themeMap == null ){
					orderList.remove(i);
				}

				if(i==0){

						themeL = (List<Map<String, Object>>) themeMap.get("orderList");
						orderList.get(i).put("data", themeL);
				}
				if(i==1){
					if(themeMap != null ){
						themeL = (List<Map<String, Object>>) themeMap.get("orderList");
						if(themeL.size()>0 && themeL != null){
							if(themeL.size()>=4){ 
								themeL = themeL.subList(0, 4);
							}
						}
					
					orderList.get(i).put("data", themeL);
				}
				}
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String,Object> statusMap = new HashMap<String,Object>();
		statusMap.put("statusMap", orderList);
		
		
		return statusMap;
	}
	
	
	
	
	@Override
	public Map<String, Object> synThmemList(HttpServletRequest request) {
		Parameter parameter = ParameterUtil.getParameter(request);
		String openId = parameter.getClient_id();
		Map<String ,String> signMap = new HashMap<String,String>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		String client_id = "";
    	String client_secret = "";
//    	if(StringUtil.openId.equals(openId)){
//    		client_id = StringUtil.client_id_other;
//    		client_secret = StringUtil.client_secret_other;
//    	}else{
    		client_id = StringUtil.client_id_axp;
    		client_secret = StringUtil.client_secret_axp;
//    	}
		String time =String.valueOf( System.currentTimeMillis());
		signMap.put("data_type", StringUtil.data_type);
		signMap.put("client_id", client_id);
		signMap.put("timestamp", time);
		signMap.put("type", "pdd.ddk.theme.list.get");
		String sign1 = MD5Util.getSign(signMap,client_secret);
		
		List<Map<String,Object>> orderList = null;
		List<Map<String,Object>> thmemL = null;
		String url = "http://gw-api.pinduoduo.com/api/router";
		String param = "type=pdd.ddk.theme.list.get&data_type=JSON&timestamp="
				+time+"&client_id="+client_id+"&sign="+sign1;
		
		try {
			List<Map<String,Object>> thmemList =  UrlUtil.sendPostForListNew(url, param, "theme_list_get_response");
		
			QueryModel model = new QueryModel();
			if(thmemList != null && thmemList.size()>0){
				thmemL = UrlUtil.jsonObjectToList(thmemList.get(0).get("theme_list").toString());
				openThemeDAO.delAll();
				
			
			for(int j=0;j<thmemL.size();j++){
				String themeId = thmemL.get(j).get("id").toString();
				//同步主题列表同一个表清空,然后在保存
				OpenTheme theme = new OpenTheme();
				theme.setGoodsNum(thmemL.get(j).get("goods_num").toString());
				theme.setImageUrl(thmemL.get(j).get("image_url").toString());
				theme.setThemeId(themeId);
				theme.setName(thmemL.get(j).get("name").toString());
				openThemeDAO.saveOrUpdate(theme);
				
				
				
				Map<String,String> sign = new HashMap<String,String>();
				sign.put("client_id", client_id);
				sign.put("type", "pdd.ddk.theme.goods.search");
				sign.put("timestamp", String.valueOf(time));
				sign.put("data_type", StringUtil.data_type);
				sign.put("theme_id", themeId);
				String sign2 = MD5Util.getSign(sign,client_secret);
				
				String url1 = "http://gw-api.pinduoduo.com/api/router";
				String param1 = "type=pdd.ddk.theme.goods.search&data_type=JSON&timestamp="+String.valueOf(time)+"&client_id="+client_id+"&theme_id="+themeId+"&sign="+sign2;
				List<Map<String,Object>>  themeGoods = UrlUtil.sendPostForListNew(url1, param1, "theme_list_get_response");		
				 orderList = UrlUtil.jsonObjectToList(themeGoods.get(0).get("goods_list").toString());
				if(orderList.size()>0 && orderList != null){
					for(int i=0;i<orderList.size();i++){
						 String	hasCoupon = orderList.get(i).get("has_coupon").toString();
						 String remainQuantity = orderList.get(i).get("coupon_remain_quantity").toString();
						 String goodsId = orderList.get(i).get("goods_id").toString();
						if("true".equals(hasCoupon)){ //过滤没有优惠券的
							model.clearQuery();
							model.combPreEquals("goodsId", goodsId);
							List<OpenThemeGoods> OpenThemeGood= dateBaseDAO.findLists(OpenThemeGoods.class, model);
							OpenThemeGoods ot = new OpenThemeGoods();
							if(OpenThemeGood!=null && OpenThemeGood.size()>0){
								ot = OpenThemeGood.get(0);
							}
							ot.setThemeId(themeId);
							ot.setCouponDiscount(orderList.get(i).get("coupon_discount").toString());
							ot.setCouponEndTime(orderList.get(i).get("coupon_end_time").toString());
							ot.setCouponMinOrderAmount(orderList.get(i).get("coupon_min_order_amount").toString());
							ot.setCouponRemainQuantity(remainQuantity);
							ot.setCouponStartTime(orderList.get(i).get("coupon_start_time").toString());
							ot.setCouponTotalQuantity(orderList.get(i).get("coupon_total_quantity").toString());
							ot.setGoodsDesc(orderList.get(i).get("goods_desc").toString());
							ot.setGoodsEvalCount(orderList.get(i).get("goods_eval_count").toString());
							ot.setGoodsEvalScore(orderList.get(i).get("goods_eval_score").toString());
							ot.setGoodsId(goodsId);
							ot.setGoodsImageUrl(orderList.get(i).get("goods_image_url").toString());
							ot.setGoodsName(orderList.get(i).get("goods_name").toString());
							ot.setGoodsThumbnailUrl(orderList.get(i).get("goods_thumbnail_url").toString());
							ot.setMallName(orderList.get(i).get("mall_name").toString());
							ot.setMinGroupPrice(orderList.get(i).get("min_group_price").toString());
							ot.setMinNormalPrice(orderList.get(i).get("min_normal_price").toString());
							//Integer rate = (Integer)orderList.get(i).get("promotion_rate")*maid/100;
							ot.setPromotionRate(orderList.get(i).get("promotion_rate").toString());
							ot.setSoldQuantity(orderList.get(i).get("sold_quantity").toString());
							ot.setAveDesc(orderList.get(i).get("avg_desc").toString());
							ot.setAvgLgst(orderList.get(i).get("avg_lgst").toString());
							ot.setAvgServ(orderList.get(i).get("avg_serv").toString());
							ot.setCategoryId(orderList.get(i).get("category_id").toString());
							ot.setCategoryName(orderList.get(i).get("category_name").toString());
							ot.setCatId(orderList.get(i).get("cat_id").toString());
							ot.setCatIds(orderList.get(i).get("cat_ids").toString());
							ot.setGoodsGalleryUrls(orderList.get(i).get("goods_gallery_urls").toString());
							ot.setOptId(orderList.get(i).get("opt_id").toString());
							ot.setOptName(orderList.get(i).get("opt_name").toString());
							ot.setHasCoupon(hasCoupon);
							openThemeGoodsDAO.saveOrUpdate(ot);
						}
					}
					
				}		
						
			  }
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String,Object> statusMap = new HashMap<String,Object>();
		statusMap.put("statusMap", orderList);
		return statusMap;
	}
	
	
	
	
	@Override
	public Map<String, Object> getThmemGoods(HttpServletRequest request) {
		Parameter parameter = ParameterUtil.getParameter(request);
		
		String openId = parameter.getClient_id();
		String sign = parameter.getSign();
		String pageSize = "";
		String page = "";
		if(parameter.getData() != null){
			
			pageSize = parameter.getData().getString("page_size") == null ?"10":parameter.getData().getString("page_size");
			page = parameter.getData().getString("page")== null?"1":parameter.getData().getString("page");
		
		}else{
			pageSize = "10";
			page = "1";
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
		
		Map<String,Object> statusMap = new HashMap<String,Object>();
		List<Map<String,Object>> dataMap = new ArrayList<Map<String,Object>>();
		QueryModel model = new QueryModel();
		model.clearQuery();
		List<OpenTheme> themes = dateBaseDAO.findLists(OpenTheme.class, model);//所有主题列表
		
		for(int i = 0;i<themes.size();i++){
			Map<String,Object> theme = new HashMap<String,Object>();
			theme.put("id", Integer.valueOf(themes.get(i).getThemeId()));
			theme.put("goods_num", Integer.valueOf(themes.get(i).getGoodsNum()));
			theme.put("imge_url", themes.get(i).getImageUrl());
			theme.put("name", themes.get(i).getName());
			
			model.clearQuery();
			model.combPreEquals("themeId", themes.get(i).getThemeId(),"theme_id");
			List<OpenThemeGoods> themeGoods = dateBaseDAO.findLists(OpenThemeGoods.class, model);
			List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
			for(OpenThemeGoods o: themeGoods){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("goods_desc", o.getGoodsDesc());
				map.put("min_group_price", o.getMinGroupPrice());
				map.put("goods_gallery_urls", o.getGoodsGalleryUrls());
				map.put("goods_name", o.getGoodsName());
				map.put("opt_name", o.getOptName());
				map.put("coupon_total_quantity",o.getCouponTotalQuantity());
				map.put("cat_id", o.getCatId());
				map.put("has_coupon", o.getHasCoupon());
				map.put("min_normal_price", o.getMinNormalPrice());
				map.put("coupon_min_order_amount", o.getCouponMinOrderAmount());
				map.put("coupon_start_time", o.getCouponStartTime());
				map.put("cat_ids", o.getCatIds());
				map.put("mall_name", o.getMallName());
				map.put("coupon_remain_quantity", o.getCouponRemainQuantity());
				map.put("coupon_end_time", o.getCouponEndTime());
				Integer rate = Integer.valueOf(o.getPromotionRate())*maid/100;
				map.put("promotion_rate", rate);
				map.put("goods_eval_score", o.getGoodsEvalScore());
				map.put("goods_eval_count",o.getGoodsEvalCount());
				map.put("category_id", o.getCategoryId());
				map.put("goods_image_url", o.getGoodsImageUrl());
				map.put("goods_thumbnail_url", o.getGoodsThumbnailUrl());
				map.put("avg_serv", o.getAvgServ());
				map.put("opt_id", o.getOptId());
				map.put("avg_lgst", o.getAvgLgst());
				map.put("category_name", o.getCategoryName());
				map.put("avg_desc", o.getAveDesc());
				map.put("goods_id", o.getGoodsId());
				map.put("coupon_discount",o.getCouponDiscount());
				map.put("sold_quantity", o.getSoldQuantity());
				dataList.add(map);
			}
			if(i==0){
				
				theme.put("data", dataList);
			}else if(i==1){
				dataList = dataList.subList(0, 4);
				theme.put("data", dataList);
			}
			dataMap.add(theme);
			
		}
		
		
		statusMap.put("statusMap", dataMap);
		
		return statusMap;
		
	
	}
	
}
	
