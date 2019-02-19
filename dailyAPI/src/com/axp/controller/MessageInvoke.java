package com.axp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.axp.service.IMessageService;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;

@Controller
@RequestMapping("invoke/users") //好像用不上
public class MessageInvoke extends BaseController {
	
	@Autowired
	IMessageService messageService;

	@RequestMapping("/getMessageList")
	@ResponseBody
	public Map<String, Object> getMessageList(HttpServletResponse response, HttpServletRequest request) {
		//获取参数；
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		
		return messageService.getMessageList(request, response);
	}
	
	@RequestMapping("/getMessageContent")
	public String getMessageContent(HttpServletResponse response, HttpServletRequest request, Integer id) {
		request.setAttribute("messageContent", messageService.getMessageContent(id));
		return "messageCenter/content";
	}
	
	
	
	//用户发表图文信息
	@RequestMapping("/publishInfo")
	@ResponseBody
	public Map<String,Object> publishInfo(HttpServletResponse response, HttpServletRequest request){
		
		return usersService.publishInfo(response,request);
		
	}
	
	// 获取用户发表的信息
	@RequestMapping("/getPublishInfo")
	@ResponseBody
	public Map<String,Object> getPublishInfo(HttpServletResponse response, HttpServletRequest request){
		return usersService.getPublishInfo(response,request);
	}
	
	
	//用户点赞和取消赞
	@RequestMapping("/isGiveUp")
	@ResponseBody
	public Map<String,Object> UpOrDown(HttpServletResponse response, HttpServletRequest request){
		return usersService.UpOrDown(response,request);
	}
	
	//用户收藏信息, 取消收藏
	@RequestMapping("/isConcern")
	@ResponseBody
	public Map<String,Object> isConcern(HttpServletResponse response, HttpServletRequest request){
		return usersService.isConcern(response,request);
	}
	
	
	//赞赏 即是积分赠送的 只改变用户积分表中的用户id
	@RequestMapping("/payScore")
	@ResponseBody
	public Map<String,Object> payScore(HttpServletResponse response, HttpServletRequest request){
		return usersService.payScore(response,request);
	}
	
	
	//回复信息 评论
	@RequestMapping("/reply")
	@ResponseBody
	public Map<String,Object> reply(HttpServletResponse response, HttpServletRequest request){
		return usersService.reply(response,request);
	}
	
}
