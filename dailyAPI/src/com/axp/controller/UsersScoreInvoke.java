package com.axp.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.axp.util.DateUtil;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.StringUtil;

@Controller
@RequestMapping("invoke/users")
public class UsersScoreInvoke extends BaseController {
	
	//实时数据列表滚动列表
	@RequestMapping("/getBounsRealTime")
	@ResponseBody
	public Map<String,Object> getBounsRealTimeList(HttpServletRequest request, HttpServletResponse response){
		String type = request.getParameter("type");
		return usersService.getBounsRealTime(request,response,Integer.valueOf(type));
		
	}
	
	//实时数据可视化,有地图的
	@RequestMapping("/getSettlement")
	@ResponseBody
	public Map<String,Object> getSettlement(HttpServletRequest request, HttpServletResponse response){
		String type = request.getParameter("type")==null?"0":request.getParameter("type");
		return usersService.getSettlement(request,response,Integer.valueOf(type));
		
	}
	//球星旋转 用户信息
	@RequestMapping("/getRotateUsers")
	@ResponseBody
	public Map<String,Object> getRotateUsers(HttpServletRequest request, HttpServletResponse response){
		String type = request.getParameter("type")==null?"0":request.getParameter("type");
		return usersService.getRotateUsers(request,response,Integer.valueOf(type));
		
	}
	
	//加减积分接口
	@RequestMapping("/updateScore")
	@ResponseBody
	public Map<String, Object> getAdverImgs(HttpServletRequest request, HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		
		return null;
	}
	
	//积分记录 da 新增聚品惠金币记录
	@RequestMapping("/getScoreList")
	@ResponseBody
	public Map<String, Object> getScoreList(HttpServletRequest request, HttpServletResponse response) {
		String xcx = request.getParameter("xcx");
		Integer userId = null;
		int pageIndex = 1;
		int type = 0;
		if(xcx  != null){
			userId = Integer.valueOf(request.getParameter("userId"));
			pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
			type = Integer.valueOf(request.getParameter("type"));
		}else{
			
			Parameter parameter = ParameterUtil.getParameter(request);
			
			if (parameter == null) {
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			userId = Integer.parseInt(parameter.getUserId());
			pageIndex = Integer.parseInt(parameter.getData().getString("pageIndex"));
			type = Integer.parseInt(parameter.getData().getString("type"));
		}
		return usersScoreService.getRecord(userId, pageIndex,type);
	}
	
	
	
	//新 加减积分 d
	@RequestMapping("/updateScore2")
	@ResponseBody
	public Map<String,Object> updateScore2(HttpServletRequest request,HttpServletResponse response){
		return usersScoreService.updateScore2(request,response);
		
	}
	
	
	//da 聚品惠 积分赠送
	@RequestMapping("/sendGoldCoins")
	@ResponseBody
	public Map<String,Object> jphSendScore(HttpServletRequest request,HttpServletResponse response){
		return usersScoreService.jphSendScore(request,response);
		
	}
	
	//da 聚品惠 金币兑换
	@RequestMapping("/goldCoinConvert")
	@ResponseBody
	public Map<String,Object> goldCoinConvert(HttpServletRequest request,HttpServletResponse response){
		return usersScoreService.goldCoinConvert(request,response);
			
	}
	
	
	//da 聚品惠  验证被赠送的用户
	@RequestMapping("/checkReceiverInfo")
	@ResponseBody
	public Map<String,Object> checkReceiverInfo(HttpServletRequest request,HttpServletResponse response){
		return usersScoreService.checkReceiverInfo(request,response);
			
	}
	
}
