package com.axp.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.axp.service.IOrderPayService;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;

@Controller
@RequestMapping("invoke/recharge")
public class RechargeInvoke extends BaseController{
	@Autowired
	private IOrderPayService orderPayService;
	
	public static final String WALLET = "100";//钱包
	public static final String ALIPAY = "200";//支付宝
	public static final String WEIXIN = "300";//微信
	public static final String YILIANSCORE = "400";//易联积分

	
	
	
	/**
	 * app支付接口
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/payRecharge")
	public Map<String,Object> payOrders(HttpServletRequest request, HttpServletResponse response){
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		String payType = parameter.getData().getString("payType");
		if (ALIPAY.equals(payType)) {//支付宝支付；
			return orderPayService.getAlipaySignByRecharge(request, response);
		} else if (WEIXIN.equals(payType)){//微信支付
			return orderPayService.getWeixinSignByRecharge(request, response);
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping("/payRechargeScore")
	public Map<String,Object> payRechargeScore(HttpServletRequest request, HttpServletResponse response){
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		String payType = parameter.getData().getString("payType");
		
//		if(!orderPayService.isDeposit(request, response)){
//			return JsonResponseUtil.getJson(-0x02,"押金不足,请先充值押金(购买积分金额的10%以上)");
//		}
		if(!orderPayService.isScore(request, response)){
			return JsonResponseUtil.getJson(-0x02,"代理积分池积分不足暂时不能购买");
		}
		if (ALIPAY.equals(payType)) {//支付宝支付；
			return orderPayService.getAlipaySignByRechargeScore(request, response);
		} else if (WEIXIN.equals(payType)){//微信支付
			return orderPayService.getWeixinSignByRechargeScore(request, response);
		}else if(WALLET.equals(payType)){ //钱包支付
			return orderPayService .getWalletByRechargeScore(request, response);
		}
		
		return null;
	}
	
	
	/**
	 * 支付宝回调接口
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/alipayRechargeNotify")
	public String alipayNotify(HttpServletRequest request, HttpServletResponse response){
		return orderPayService.payByAlipayByRecharge(request, response);
	}
	
	/**
	 * 支付宝积分回调接口
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/alipayRechargeScoreNotify")
	public String alipayRechargeScoreNotify(HttpServletRequest request, HttpServletResponse response){
		return orderPayService.alipayRechargeScoreNotify(request, response);
	}
	
	/**
	 * 易联回调接口
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/yiLianRechargeScoreNotify")
	public String yiLianRechargeScoreNotify(HttpServletRequest request, HttpServletResponse response){
		return orderPayService.yiLianRechargeScoreNotify(request, response);
	}
	
	/**
	 * 微信回调接口
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/weixinRechargeNotify")
	public String weixinNotify(HttpServletRequest request, HttpServletResponse response){
		return orderPayService.payByWeixinByRecharge(request, response);
	}
	
	/**
	 * 微信积分回调接口
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/weixinRechargeScoreNotify")
	public String weixinRechargeScoreNotify(HttpServletRequest request, HttpServletResponse response){
		return orderPayService.weixinRechargeScoreNotify(request, response);
	}
	

	/**
	 * 微信关闭订单接口
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/closeWeixinOrder")
	public String closeWeixinOrder(HttpServletRequest request, HttpServletResponse response){
		String code = request.getParameter("code");
		orderPayService.closeWeixinOrder(code,"100",request);
		return null;
	}

}
