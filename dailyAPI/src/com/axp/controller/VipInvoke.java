package com.axp.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.util.AlipayNotify;
import com.alipay.util.AlipayPayUtils;
import com.axp.domain.MembersConfig;
import com.axp.service.IMembersService;
import com.axp.service.impl.MembersServiceImpl;
import com.axp.util.CalcUtil;
import com.axp.util.DateUtil;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.UrlUtil;
import com.weixin.bean.PayParameter;
import com.weixin.config.WeixinConfig;
import com.weixin.util.WeixinUtil;
import com.weixin.util.XmlData;

/**
 * 用于处理简单收费项目的接口
 * @author hzc
 *
 */

@Controller
@RequestMapping("invoke/users")
public class VipInvoke extends BaseController{
	
	@Autowired 
	IMembersService vipService;
	
	public static final Integer VIP = 10;
	
	@RequestMapping("/becomeVip")
	@ResponseBody
	public Map<String, Object> becomeVip(HttpServletResponse response, HttpServletRequest request) {
		//获取参数；
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		Integer vipLevelId = parameter.getData().getInteger("vipLevelId");
		Integer userId = Integer.parseInt(parameter.getUserId().toString());
		Integer payType = parameter.getData().getInteger("payType");//200:支付宝 300:微信
		String inviteCode = parameter.getData().getString("inviteCode");
		return vipService.register(vipLevelId, userId, payType, inviteCode, request);
	}
	
	@RequestMapping("/alipayNotify")
	@ResponseBody
	public String alipayNotify (HttpServletResponse response, HttpServletRequest request) {
		try{
			Map<String, String> params = AlipayPayUtils.getParams(request, response);
			String trade_status = params.get("trade_status");
			if (AlipayNotify.verify(params)) {//验证成功
				if (trade_status.equals("TRADE_FINISHED")) {
					
					System.out.println("=======TRADE_FINISHED======");
					
					return "success";
				} else if (trade_status.equals("TRADE_SUCCESS")) {
					System.out.println("=======TRADE_SUCCESS======");
					
					//从支付宝异步返回值中取出实际交易金额
					Double money = Double.parseDouble(params.get("total_fee"));
					
					//买家支付宝账号
					String account = params.get("buyer_email");
					String body = params.get("body");
					
					JSONObject data = JSONObject.parseObject(body);
					Integer userId = data.getInteger("userId");
					Integer configId = data.getInteger("configId");
					
					vipService.confirmVip(userId, configId, money, account, Integer.parseInt(IMembersService.ALIPAY));
					return "success";
				} else {//验证失败
					System.out.println("验证失败");
					return "fail";
				}
			}else{
				return "fail";
			}
		}catch(Exception e){
			e.printStackTrace();
			return "fail";
		}
	}
	
	@RequestMapping("/weixinNotify")
	@ResponseBody
	public String weixinNotify (HttpServletResponse response, HttpServletRequest request) {
		System.out.println("微信注册会员开始");
		XmlData xml = new XmlData();
		try{
			Map<String, String> params = WeixinUtil.getParams(request, response);
			String return_code = params.get("return_code");
			String sign = params.get("sign");
			Double totalFee = Double.parseDouble(params.get("total_fee"));
			totalFee = CalcUtil.mul(totalFee, 0.01, 2);
			String openid = params.get("openid");
			String body = params.get("body");
			
			//自主生成验证签名
			String checkSign = WeixinUtil.notifySign(params);
			
			if (return_code.equals("SUCCESS")&&sign.equals(checkSign)) {//验证成功
				System.out.println("验证成功");
				JSONObject ob = JSONObject.parseObject(body);
				Integer userId = ob.getInteger("userId");
				Integer configId = ob.getInteger("configId");
				
				Map<String, Object> map = vipService.confirmVip(userId, configId, totalFee, openid, Integer.parseInt(IMembersService.WEIXIN));
				int status = Integer.parseInt(map.get("status").toString());
				if(status==0x01){
					xml.setProtectData("return_code", "SUCCESS");
					xml.setProtectData("return_msg", "SUCCESS");
					System.out.println("微信注册会员成功");
				}else{
					xml.setProtectData("return_code", "FAIL");
					xml.setProtectData("return_msg", "FAIL");
					System.out.println("微信注册会员失败："+map.get("message"));
				}
			}else{
				xml.setProtectData("return_code", "FAIL");
				xml.setProtectData("return_msg", "FAIL");
				System.out.println("微信注册会员失败：签名验证失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			xml.setProtectData("return_code", "FAIL");
			xml.setProtectData("return_msg", "FAIL");
			System.out.println("微信注册会员失败："+e.getMessage());
		}
		WeixinUtil.sendXml(WeixinConfig.HTTPS_VERIFY_URL, xml.putout());
		
		return null;
	}
	
}