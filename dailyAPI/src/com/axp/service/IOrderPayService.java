package com.axp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.axp.domain.ReGoodsorder;
import com.axp.domain.ReGoodsorderItem;
import com.axp.domain.Users;
import com.weixin.bean.PayParameter;

public interface IOrderPayService extends IBaseService<Users> {

	Map<String, Object> payByWallet(HttpServletRequest request,
			HttpServletResponse response);
	
	Map<String, Object> checkOrderPrice(HttpServletRequest request,ReGoodsorder order,Users user);
	
	String payByAlipay(HttpServletRequest request, HttpServletResponse response);
	
	String payByAlipayByRecharge(HttpServletRequest request, HttpServletResponse response);
	
	
	String payByWeixinByRecharge(HttpServletRequest request, HttpServletResponse response);
	
	String payByWeixin(HttpServletRequest request, HttpServletResponse response,Map<String, String> params);
	
	Map<String, Object> getYiLianSign(HttpServletRequest request, HttpServletResponse response);
	 /**
     * 保存接口所有参数
     * @param request
     */
	Map<String, String> sevaPayNotify(HttpServletRequest request,HttpServletResponse response,Integer type,String payType);
	
	String payByYiLian(HttpServletRequest request, HttpServletResponse response);
	
	Map<String, Object> getAlipaySign(HttpServletRequest request,
			HttpServletResponse response);
	Map<String, Object> getAlipaySignByRecharge(HttpServletRequest request,
			HttpServletResponse response);

	void closeWeixinOrder(String orderCode, String totalPrice,
			HttpServletRequest request);

	Map<String, Object> getWeixinSign(HttpServletRequest request,
			HttpServletResponse response);
	
	Map<String, Object> getWeixinScan(HttpServletRequest request,
			HttpServletResponse response,String type);

	
	Map<String, Object> getWeixinSignByRecharge(HttpServletRequest request,
			HttpServletResponse response);
	
	/**
	 * 微信充值积分回调
	 * @param request
	 * @param response
	 * @return
	 */
	String weixinRechargeScoreNotify(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 微信充值积分签名
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> getWeixinSignByRechargeScore(HttpServletRequest request,
			HttpServletResponse response);
	
	PayParameter returnParameter(HttpServletRequest request, String basePath,
			String body, String orderCode, String totalPrice);

	void updateItemByAuto(String ids);

	void updateGoodsStock(ReGoodsorderItem item, Integer value, boolean isAdd)
			throws Exception;
  
	
	void pushsingle(Integer userId, String channelId,String title,String message);
	
	boolean savePartnerInform(Users users,Users causeUser,String remark,Integer level ,Integer mode);
	
	Map<String, Object>	payByWEB(HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * 同步易联通知
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> synchroYiLianNotify (HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * WEB端获取签名
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> getWEBSign(HttpServletRequest request, HttpServletResponse response);
	
	/**\
	 * 易联积分充值
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> getYiLianSignByRechargeScore(HttpServletRequest request,
			HttpServletResponse response);
	/**
	 * 易联充值回调
	 * @param request
	 * @param response
	 * @return
	 */
	String yiLianRechargeScoreNotify(HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * 支付宝充值积分签名
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> getAlipaySignByRechargeScore(
			HttpServletRequest request, HttpServletResponse response);
	/**
	 * 支付宝积分回调
	 * @param request
	 * @param response
	 * @return
	 */
	String alipayRechargeScoreNotify(HttpServletRequest request, HttpServletResponse response);

	/**
	 *余额充值积分
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> getWalletByRechargeScore(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 判断是押金是否充足
	 * @param request
	 * @param response
	 * @return
	 */
	boolean isDeposit(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 判断积分池是否充足
	 * @param request
	 * @param response
	 * @return
	 */
	boolean isScore(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 微信小程序支付
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> getXCXSign(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 小程序支付回调
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	String weixinNotifyXcx(HttpServletRequest request,
			HttpServletResponse response, Map<String, String> params);
	/**
	 * 合并支付
	 * @param request
	 * @param userId
	 * @param orderIds
	 * @param payType
	 * @param thirdParty
	 * @param transaction_id
	 * @param timeEnd
	 * @param openId
	 * @param totalFee
	 * @param outTradeNo
	 * @param mergePay
	 * @param weixin_type
	 * @param os
	 * @param zoneId
	 * @return
	 */
	public Map<String, Object> payOrdersMerge(HttpServletRequest request,
			Integer userId, String orderIds, String payType, Double thirdParty,
			String transaction_id, String timeEnd, String openId,
			Integer totalFee, String outTradeNo, Integer mergePay,
			Integer weixin_type, String os, String zoneId) ;


	/**
	 * 小程序回调
	 * @param request
	 * @param response
	 * @param params1
	 */
	void xcxNotify(HttpServletRequest request, HttpServletResponse response,
			Map<String, String> params1)throws Exception;
	
}
