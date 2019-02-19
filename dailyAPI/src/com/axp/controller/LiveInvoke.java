package com.axp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.StringUtil;

@Controller
@RequestMapping("invoke/live")
public class LiveInvoke extends BaseController {

	@RequestMapping("/getTopLive")
	@ResponseBody
	public Map<String, Object> getTopLive(HttpServletRequest request, HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		int userId = StringUtil.isEmpty(parameter.getUserId())?-1:Integer.parseInt(parameter.getUserId());
		int zoneId = StringUtil.isEmpty(parameter.getZoneId())?-1:Integer.parseInt(parameter.getZoneId());
		double lng = StringUtil.isEmpty(parameter.getLng())?0:Double.parseDouble(parameter.getLng());
		double lat = StringUtil.isEmpty(parameter.getLat())?0:Double.parseDouble(parameter.getLat());
		String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
//		return adversService.getPutOutAdvers(userId, zoneId, pool1, pool2, pool3, pool4, lng, lat, basePath);
		return liveService.getTopList(userId, zoneId, basePath);
	}
	
	
	@RequestMapping("/getLive")
	@ResponseBody
	public Map<String, Object> getLive(HttpServletRequest request, HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		int userId = StringUtil.isEmpty(parameter.getUserId())?-1:Integer.parseInt(parameter.getUserId());
		int zoneId = StringUtil.isEmpty(parameter.getZoneId())?-1:Integer.parseInt(parameter.getZoneId());
		double lng = StringUtil.isEmpty(parameter.getLng())?0:Double.parseDouble(parameter.getLng());
		double lat = StringUtil.isEmpty(parameter.getLat())?0:Double.parseDouble(parameter.getLat());
		String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
//		return adversService.getPutOutAdvers(userId, zoneId, pool1, pool2, pool3, pool4, lng, lat, basePath);
		Integer pageIndex = parameter.getData().getInteger("pageIndex");
		return liveService.getList(userId, zoneId, basePath,pageIndex);
	}
	
	
	@RequestMapping("/getLiveById")
	@ResponseBody
	public Map<String, Object> getLiveById(HttpServletRequest request, HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		int userId = StringUtil.isEmpty(parameter.getUserId())?-1:Integer.parseInt(parameter.getUserId());
		int zoneId = StringUtil.isEmpty(parameter.getZoneId())?-1:Integer.parseInt(parameter.getZoneId());
		double lng = StringUtil.isEmpty(parameter.getLng())?0:Double.parseDouble(parameter.getLng());
		double lat = StringUtil.isEmpty(parameter.getLat())?0:Double.parseDouble(parameter.getLat());
		int liveid= parameter.getData().getInteger("liveid");
		String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
		
//		return adversService.getPutOutAdvers(userId, zoneId, pool1, pool2, pool3, pool4, lng, lat, basePath);
		return liveService.getListById(userId, zoneId, basePath,liveid);
	}
	
	
}
