package com.axp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.axp.domain.ReBackOrder;
import com.axp.domain.ReGoodsorder;

public interface IOrderService extends IBaseService<ReGoodsorder>{

	Map<String, Object> createTempOrderList(HttpServletRequest request,HttpServletResponse response);

	Map<String, Object> confirmOrder(HttpServletRequest request,HttpServletResponse response);

	Map<String, Object> getOrderList(HttpServletRequest request,HttpServletResponse response);

	Map<String, Object> cancelOrder(HttpServletRequest request,HttpServletResponse response);
	
	Map<String, Object> getSellerOrderList(HttpServletRequest request,HttpServletResponse response);

	Map<String, Object> sellerConfirmExchange(HttpServletRequest request,HttpServletResponse response);

	Map<String, Object> sellerConfirmReceipt(HttpServletRequest request,HttpServletResponse response);

	Map<String, Object> getOrder(HttpServletRequest request,HttpServletResponse response);

	Map<String, Object> sellerConfirmOrder(HttpServletRequest request,HttpServletResponse response);

	Map<String, Object> sellerBackOrderVerify(HttpServletRequest request,HttpServletResponse response);

	Map<String, Object> getSellerBackOrderList(HttpServletRequest request,HttpServletResponse response);

	Map<String, Object> confirmReceipt(HttpServletRequest request);
	
	Integer getNumberOfAlreadyBuy(String goodsOrder,Integer userId);
	
	/**
	 * 获取商品详情待拼单信息
	 */
	Map<String, Object> getGoodsTeamInfo(Integer userId,Integer sellerMallId,Map<String, Object> map,String basePath,Integer appVersion);
	
	/**
	 * 获取订单拼单成功信息  拼团有效时间好像会变  要写入数据库中
	 * status true拼团成功 false 待拼团
	 */
	 Map<String, Object> getOrderTeamStatusInfo(Map<String, Object> map,ReGoodsorder reGoodsorder,String basePath,Boolean status);

	 /**
		 * 获得拼团成功分享页面数据
		 * @param request
		 * @param response
		 * @return
		 */
	 Map<String, Object> getTeamShare(HttpServletRequest request, HttpServletResponse response);
}
