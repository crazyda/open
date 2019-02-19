package com.axp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.axp.service.CommodityManageService;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;

@Controller
@RequestMapping("/invoke/Commodity")
public class CommodityManageInvoke extends BaseController{

	@Autowired
	CommodityManageService commodityManageSerive;
	@RequestMapping("/getCommodityList")
	@ResponseBody
	public Map<String, Object> getCommodityList(HttpServletRequest request,HttpServletResponse response){
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter==null) {
			JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
		}
		return commodityManageSerive.getCommodityList(request, response);
	}
}
