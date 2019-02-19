package com.axp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.axp.service.IntegralService;

@Controller
@RequestMapping("invoke/integral")
public class IntegralInvoke  extends BaseController{
	@Autowired
	IntegralService integralService;
	
	/*
	 * 积分列表
	 * @author ZhangLu
	 * */
	@RequestMapping("/getIntegralList")
	@ResponseBody
	public Map<String, Object> getIntegralList(HttpServletRequest request,HttpServletResponse response){
		return integralService.getIntegralList(request, response);
	}
	
	
	/*
	 * 积分赠送页
	 * @author ZhangLu
	 * */
	@RequestMapping("/checkPresenters")
	@ResponseBody
	public Map<String, Object> checkPresenters(HttpServletRequest request,HttpServletResponse response){
		
		return integralService.checkPresenters(request, response);
	}
	
	
	/*
	 * 积分赠送
	 * @author ZhangLu
	 * */
	@RequestMapping("/sendScore")
	@ResponseBody
	public Map<String, Object> sendScore(HttpServletRequest request,HttpServletResponse response){
		return integralService.sendScore(request, response);
	}
	
	
	/**
	 * 在线充值页面数据展示
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/rechargeOnLine")
	@ResponseBody
	public Map<String, Object> rechargeOnLine(HttpServletRequest request, HttpServletResponse response) {
		return integralService.rechargeOnLine(request, response);
	}
	/**
	 * 押金充值页面数据展示
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/depositOnLine")
	@ResponseBody
	public Map<String,Object> depositOnLine(HttpServletRequest request, HttpServletResponse response){
		return integralService.depositOnLine(request, response);
	}
	/**
	 * 退押金 流程
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/unDeposit")
	@ResponseBody
	public Map<String,Object> reDeposit(HttpServletRequest request, HttpServletResponse response){
		return integralService.reDeposit(request, response);
	}
	
	
	
	/**
	 * 实卡充值
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/recharge")
	@ResponseBody
	public Map<String, Object> recharge(HttpServletRequest request, HttpServletResponse response) {
		return integralService.recharge(request, response);
	}
	
	
	/*
	 * 扫码接收
	 * */
	@RequestMapping("/receiveQRScore")
	@ResponseBody
	public Map<String, Object> receiveQRScore(HttpServletRequest request, HttpServletResponse response){
		return integralService.receiveQRScore(request, response);
	}
	
}
