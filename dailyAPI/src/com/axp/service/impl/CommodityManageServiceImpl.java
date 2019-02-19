package com.axp.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.axp.service.CommodityManageService;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;

@Service("commodityManageService")
public class CommodityManageServiceImpl  implements CommodityManageService{

	/**
	 * @author zhangLu
	 * 商品管理列表
	 */
	@Override
	public Map<String, Object> getCommodityList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			String typeId = parameter.getData().getString("typeId");//商城类别
			if (StringUtils.isNotBlank(typeId) && typeId.equals("1")) {//特产商城
				statusMap= getLocalCommodityList(request, response);
			}else if(StringUtils.isNotBlank(typeId) && typeId.equals("2")){//周边店铺
				statusMap=getSellerCommdityList(request, response);
			}else if(StringUtils.isNotBlank(typeId) && typeId.equals("3")){//积分商城
				statusMap = getScoreCommdityList(request, response);
			}else if(StringUtils.isNotBlank(typeId) && typeId.equals("4")){//普通优惠券
				statusMap=getOrdCouponCommdityList(request, response);
			}else if(StringUtils.isNotBlank(typeId) && typeId.equals("5")){//活动优惠券
				statusMap=getActCouponCommdityList(request, response);
			}else if(StringUtils.isNotBlank(typeId) && typeId.equals("6")){//秒杀商城
				statusMap = getSpikeCommdityList(request, response);
			}else if(StringUtils.isNotBlank(typeId) && typeId.equals("7")){//拼团
				statusMap = getGroupbuyCommdityList(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}
	
	
	
	//特产商城商品列表
	public Map<String, Object> getLocalCommodityList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功");
		} catch (Exception e) {
			e.printStackTrace();
			statusMap.put("status", -2);
			statusMap.put("message", "请求失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}
	
	//周边店铺商品列表
	public Map<String, Object> getSellerCommdityList (HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功");
		} catch (Exception e) {
			e.printStackTrace();
			statusMap.put("status", -2);
			statusMap.put("message", "请求失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}
	
	
	//积分商城商品
	public Map<String, Object> getScoreCommdityList (HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功");
		} catch (Exception e) {
			e.printStackTrace();
			statusMap.put("status", -2);
			statusMap.put("message", "请求失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}
	
	//普通优惠券商品
	public Map<String, Object> getOrdCouponCommdityList (HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功");
		} catch (Exception e) {
			e.printStackTrace();
			statusMap.put("status", -2);
			statusMap.put("message", "请求失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}
	
	//活动优惠券商品列表
	public Map<String, Object> getActCouponCommdityList (HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功");
		} catch (Exception e) {
			e.printStackTrace();
			statusMap.put("status", -2);
			statusMap.put("message", "请求失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}
	
	//秒杀商品列表
	public Map<String, Object> getSpikeCommdityList (HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功");
		} catch (Exception e) {
			e.printStackTrace();
			statusMap.put("status", -2);
			statusMap.put("message", "请求失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}
	
	//拼团商品列表
	public Map<String, Object> getGroupbuyCommdityList (HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功");
		} catch (Exception e) {
			e.printStackTrace();
			statusMap.put("status", -2);
			statusMap.put("message", "请求失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}

}
