package com.axp.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.axp.service.MessageDetailService;
import com.axp.service.MessageListService;
import com.axp.service.UserSystemMessageService;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;

@Controller
@RequestMapping("invoke/message")
public class SystemMessageInvoke extends BaseController{

	@Resource
	MessageListService messageListService;
	@Resource
	MessageDetailService messageDetailService;
	@Resource
	UserSystemMessageService userSystemMessageService;
	@RequestMapping("/getMessageHome")
	@ResponseBody
	public Map<String, Object> getMessageHome (HttpServletRequest request,HttpServletResponse response){
		//获取参数；
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		return systemMessageService.getMessageHome(request, response);
	}
	
	@RequestMapping("/getMessageList")
	@ResponseBody
	public Map<String, Object> getMessageList(HttpServletRequest request,HttpServletResponse response){
		//获取参数；
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		return messageListService.getMessageList(request, response);
	}
	
	//发送消息
	@RequestMapping("/Test")
	@ResponseBody
	public Map<String, Object>Test(HttpServletRequest request,HttpServletResponse response){
		//获取参数；
		Parameter parameter = ParameterUtil.getParameter(request);		
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		return userSystemMessageService.Test(request, response);
	}	
	
	
	
	//消息详情
	@RequestMapping("/msgDetail")
	@ResponseBody
	public Map<String, Object> msgDetailSystem(HttpServletRequest request,HttpServletResponse response){
		//获取参数；
		Parameter parameter = ParameterUtil.getParameter(request);		
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		return messageDetailService.msgDetail(request, response);
	}
	
	//删除消息
	@RequestMapping("/delMessage")
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
	/*@RequestMapping("/megsend")
	@ResponseBody
	public Map<String, Object>megsend(HttpServletRequest request,HttpServletResponse response){
		//获取参数；
		Parameter parameter = ParameterUtil.getParameter(request);		
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		return userSystemMessageService.saveallmessage(request, response);
	}	*/
}
