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
@RequestMapping("invoke/advers")
public class AdversInvoke extends BaseController {

	@RequestMapping("/getAdverImgs")
	@ResponseBody
	public Map<String, Object> getAdverImgs(HttpServletRequest request, HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		int userId = StringUtil.isEmpty(parameter.getUserId())?-1:Integer.parseInt(parameter.getUserId());
		int zoneId = StringUtil.isEmpty(parameter.getZoneId())?-1:Integer.parseInt(parameter.getZoneId());
		double lng = StringUtil.isEmpty(parameter.getLng())?0:Double.parseDouble(parameter.getLng());
		double lat = StringUtil.isEmpty(parameter.getLat())?0:Double.parseDouble(parameter.getLat());
		int pool1 = StringUtil.isEmpty(parameter.getData().getString("pool1"))?0:Integer.parseInt(parameter.getData().getString("pool1"));
		int pool2 = StringUtil.isEmpty(parameter.getData().getString("pool2"))?0:Integer.parseInt(parameter.getData().getString("pool2"));
		int pool3 = StringUtil.isEmpty(parameter.getData().getString("pool3"))?0:Integer.parseInt(parameter.getData().getString("pool3"));
		int pool4 = StringUtil.isEmpty(parameter.getData().getString("pool4"))?0:Integer.parseInt(parameter.getData().getString("pool4"));
		String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
//		return adversService.getPutOutAdvers(userId, zoneId, pool1, pool2, pool3, pool4, lng, lat, basePath);
		return adversService.getAdvers(userId, zoneId, pool1, pool2, pool3, pool4, lng, lat, basePath);
	}
}
