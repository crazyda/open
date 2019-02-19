package com.axp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.axp.service.UserMessageDetailService;
import com.axp.service.UserMessageListService;
import com.axp.service.UserSystemMessageService;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;

@Controller
@RequestMapping("invoke/userMessage")
public class SystemUserMessageInvoke extends BaseController{

	@Resource
	UserMessageListService messageListService;
	@Resource
	UserMessageDetailService userMessageDetailService;
	@Resource
	UserSystemMessageService userSystemMessageService;
	
	@RequestMapping("/getUserMessageHome")
	@ResponseBody
	public Map<String, Object> getMessageHome (HttpServletRequest request,HttpServletResponse response){
		//获取参数；
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		return systemMessageService.getMessageHome(request, response);
	}
	
	@RequestMapping("/getUserMessageList")
	@ResponseBody
	public Map<String, Object> getMessageList(HttpServletRequest request,HttpServletResponse response){
		//获取参数；
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		return messageListService.getMessageList(request, response);
	}
	
	//消息详情
	@RequestMapping("/userMsgDetail")
	@ResponseBody
	public Map<String, Object> msgDetailSystem(HttpServletRequest request,HttpServletResponse response){
		//获取参数；
		Parameter parameter = ParameterUtil.getParameter(request);		
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		return userMessageDetailService.msgDetail(request, response);
	}
	
	//删除消息
	@RequestMapping("/delUserMessage")
	@ResponseBody
	public Map<String, Object> delMessage(HttpServletRequest request,HttpServletResponse response){
				//获取参数；
				Parameter parameter = ParameterUtil.getParameter(request);		
				if (parameter == null) {//错误的参数；
					return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
				}
				return messageListService.delMessage(request, response);
	}
	
	//发送消息
	/*@RequestMapping("/megsendUser")
	@ResponseBody
	public Map<String, Object>megsend(HttpServletRequest request,HttpServletResponse response){
		//获取参数；
		Parameter parameter = ParameterUtil.getParameter(request);		
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		return userSystemMessageService.saveallmessage(request, response);
	}	*/
	
		@RequestMapping("/Test")
		@ResponseBody
		public Map<String, Object>Test(HttpServletRequest request,HttpServletResponse response){
			userSystemMessageService.Test(request, response);
			Map<String, Object> test =new HashMap<String, Object>();
			return test;
		}
}
