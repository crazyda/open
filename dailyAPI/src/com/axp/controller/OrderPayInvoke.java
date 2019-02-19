package com.axp.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.axp.domain.VisitLog;
import com.axp.service.IOrderPayService;
import com.axp.service.UserSystemMessageService;
import com.axp.util.DateUtil;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;

@Controller
@RequestMapping("invoke/order")
public class OrderPayInvoke extends BaseController{
	@Autowired
	private IOrderPayService orderPayService;

	
	public static final String WALLET = "100";//钱包
	public static final String ALIPAY = "200";//支付宝
	public static final String WEIXIN = "300";//微信
	public static final String YILIAN="400"; //易联
	public static final String WEIXINSCAN="500"; //微信扫一扫支付
	public static final String WEIXINH5="600"; //微信H5支付
	public static final String WEIXINGZH="700"; //微信H5支付
	public static final String WEIXINGZH2="800"; //微信H5支付
	public static final String WEBSIGN="900";   //WEB支付获取签名
	public static final String XCXPAY="111";   //WEB支付获取签名
	/**
	 * app支付接口
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/payOrders")
	public Map<String,Object> payOrders(HttpServletRequest request, HttpServletResponse response){
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		String payType = parameter.getData().getString("payType");
		if (ALIPAY.equals(payType)) {//支付宝支付；
			return orderPayService.getAlipaySign(request, response);
		} else if (WEIXIN.equals(payType)){//微信支付
			return orderPayService.getWeixinSign(request, response);
		} else if (WALLET.equals(payType)){
			return orderPayService.payByWallet(request, response);
		}else if(YILIAN.equals(payType)){
			return orderPayService.getYiLianSign(request,response);
		}else if(WEIXINSCAN.equals(payType)){
			return orderPayService.getWeixinScan(request, response,"NATIVE");
		}else if(WEIXINH5.equals(payType)){
			return orderPayService.getWeixinScan(request, response,"MWEB");
		}else if(WEIXINGZH.equals(payType)){
			return orderPayService.getWeixinScan(request, response,"JSAPI");
		}else if(WEIXINGZH2.equals(payType)){
			return orderPayService.getWeixinScan(request, response,"JSAPI800");
		}else if(WEBSIGN.equals(payType)){ //WEB支付签名
			return orderPayService.getWEBSign(request, response);
		}
		
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/payOrdersXCX")
	public Map<String,Object> payOrdersXCX(HttpServletRequest request, HttpServletResponse response){
		String type = request.getParameter("type");
		
		if(WEIXIN.equals(type)){ //300微信支付
			System.out.println("进入微信支付");
			return orderPayService.getXCXSign(request, response);
		}else if(WALLET.equals(type)){ // 100 钱包支付
			return orderPayService.payByWallet(request, response);
		}
		return null;
	}
	
	/**
     * 微信小程序支付成功回调函数
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/weixin/callback")
    public void wxNotify(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	System.out.println("微信回调");
    	Map<String, String> params1 = orderPayService.sevaPayNotify(request, response, VisitLog.PAY, VisitLog.WEIXIN);
    	orderPayService.xcxNotify(request,response,params1);
        
    }
	
	/**
	 * 支付宝回调接口
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/alipayNotify")
	public String alipayNotify(HttpServletRequest request, HttpServletResponse response){
		System.out.println("支付宝回调开始");
		orderPayService.sevaPayNotify(request, response, VisitLog.PAY, VisitLog.ALIPAY);
		return orderPayService.payByAlipay(request, response);
	}
	
	/**
	 * 微信回调接口
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/weixinNotify")
	public String weixinNotify(HttpServletRequest request, HttpServletResponse response){
		System.out.println("微信回调开始");
		Map<String, String> params = orderPayService.sevaPayNotify(request, response, VisitLog.PAY, VisitLog.WEIXIN);
		return orderPayService.payByWeixin(request, response, params);
		
	}
	@ResponseBody
	@RequestMapping("/weixinNotifyXcx")
	public String weixinNotifyXcx(HttpServletRequest request, HttpServletResponse response){
		System.out.println("小程序回调开始");
		Map<String, String> params = orderPayService.sevaPayNotify(request, response, VisitLog.PAY, VisitLog.WEIXIN);
		return orderPayService.weixinNotifyXcx(request, response, params);
		
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

	

	/**
	 * 易联回调接口
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/yiLianNotify")
	public String yiLianNotify(HttpServletRequest request, HttpServletResponse response){
		orderPayService.sevaPayNotify(request, response, VisitLog.PAY, VisitLog.YILIAN);
		return orderPayService.payByYiLian(request, response);
	}
	
	/**
	 * WEB支付回调
	 */
	@ResponseBody
	@RequestMapping("/webNotify")
	public Map<String, Object> webNotify(HttpServletRequest request, HttpServletResponse response){
		return orderPayService.payByWEB(request, response);
	}
	
	/**
	 * 易联同步回调接口
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/synchroYiLianNotify")
	public Map<String, Object> synchroYiLianNotify(HttpServletRequest request, HttpServletResponse response){
		return orderPayService.synchroYiLianNotify(request, response);
	}
	
	/**
	 * 为老的用户创建pid
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/createPddPid")
	public boolean addPddPid(HttpServletRequest request, HttpServletResponse response){
		
		return tkldPidService.addPddPid(request, response);
		
	}
	
	
	
}
