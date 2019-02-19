package com.axp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("invoke/shareWeb")
public class ShareWebInvoke extends BaseController {

	@RequestMapping("/mall/getHome")
	public String mallGetHome(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("dataJson", request.getParameter("data"));
		return "shareWeb/mall/getHome";
	}
	
	//久久特惠商城
	@RequestMapping("/mall/preferential99")
	public String preferential99(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("dataJson", request.getParameter("data"));
		return "shareWeb/mall/preferential99";
	}
	
	//本地特产
	@RequestMapping("/mall/specialLocalProduct")
	public String specialLocalProduct(HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("dataJson", request.getParameter("data"));
		return "shareWeb/mall/specialLocalProduct";
	}
	
	//积分兑换商城
	@RequestMapping("/mall/scoreExchangeMall")
	public String scoreExchangeMall(HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("dataJson", request.getParameter("data"));
		return "shareWeb/mall/scoreExchangeMall";
	}
	
	//商家详情分享页面
	@RequestMapping("/mall/getSeller")
	public String getShopDetail(HttpServletRequest request, String dataObject){
		
		String sellerId= request.getParameter("sellerId");
		//String data="data={\"appVersion\":\"71\",\"channelId\":\"7798fd1591bf43a386b8f0d95ee10803\",\"data\":{\"sellerId\":\""+sellerId+"\"},\"dip\":\"480\",\"lat\":\"23.155497\",\"lng\":\"113.380485\",\"machineCode\":\"358022056765623\",\"os\":\"ANDROID\",\"userId\":\"44182\",\"zoneId\":\"1965\"}";

		request.setAttribute("dataJson", sellerId);
		return "shareWeb/mall/getShopDetail";
	}
	
	//商家详情分享页面
	@RequestMapping("/mall/getGoods")
	public String getGoods(HttpServletRequest request, String dataObject){
		request.setAttribute("dataJson", request.getParameter("data"));
		return "shareWeb/mall/getGoods";
	}
	
	//获取请求商品列表的JSON
	@RequestMapping("/getGoodsListJson")
	@ResponseBody
	public String getKillMallJson(HttpServletRequest request){
		String parameter = request.getParameter("data");
		String mallTyle = request.getParameter("mallTyle");
		String pageIndex = request.getParameter("pageIndex");
		JSONObject parseObject = JSONObject.parseObject(parameter);
		JSONObject jsonObject = parseObject.getJSONObject("data");
		jsonObject.clear();
		jsonObject.put("mallTyle",mallTyle);
		//积分商品
		if("2".equals(mallTyle)){
			String tag = request.getParameter("tag");
			String sellerId = request.getParameter("sellerId");		
			if(tag!=null){
				JSONArray array = new JSONArray();
				array.add(tag);
				jsonObject.put("tag", array);
			}
			if(sellerId!=null){
				jsonObject.put("sellerId", Integer.parseInt(sellerId));
			}
			
		}
		//秒杀商品
		if("3".equals(mallTyle)){
			Integer secondKillId =Integer.parseInt(request.getParameter("secondKillId"));
			jsonObject.put("secondKillId",secondKillId+"");
		}
		//各地特产
		if("4".equals(mallTyle)){

		}
		//久久特惠
		if("5".equals(mallTyle)){
			
		}
		
		
		
		jsonObject.put("salesVolume","0");
		jsonObject.put("value","0");
		jsonObject.put("pageIndex", Integer.parseInt(pageIndex));
		parseObject.put("data", jsonObject);
		return parseObject.toJSONString();
	}
	
	//获取请求商家列表JSON
	@RequestMapping("/getSellerListJson")
	@ResponseBody
	public String getSellerListJson(HttpServletRequest request){
		String parameter = request.getParameter("data");
		String pageIndex = request.getParameter("pageIndex");//分页页码，从1开始
		JSONObject parseObject = JSONObject.parseObject(parameter);
		JSONObject jsonObject = parseObject.getJSONObject("data");
		jsonObject.clear();
		jsonObject.put("pageIndex", Integer.parseInt(pageIndex));
		
		return parseObject.toJSONString();
	}
	
	
	@RequestMapping("/getParameterAjax")
	@ResponseBody
	public String getParameterAjax(HttpServletRequest request, String dataObject){
		String json = request.getSession().getAttribute("data").toString();
		JSONObject data = JSONObject.parseObject(json);
		data.put("data", dataObject);
		return JSONObject.toJSONString(data);
	}
	
}
