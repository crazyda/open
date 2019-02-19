package com.axp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.StringUtil;

@Controller
@RequestMapping("invoke/config")
public class StaticInfoInvoke extends BaseController {
	
	
		//获取运行参数
		@RequestMapping("/getClientConfig")
		@ResponseBody
		public Map<String, Object> getConfig(HttpServletRequest request, HttpServletResponse response) {
			String basePath = (String)request.getServletContext().getAttribute("BASEPATH_IN_SESSION");
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {
					return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			parameter.setBasePath(basePath);
			return staticInfoService.getConfig(parameter);
		}
		
		
		//获取运行参数
		@RequestMapping("/getZoneList")
		@ResponseBody
		public Map<String, Object> getZones(HttpServletRequest request, HttpServletResponse response) {
				return staticInfoService.getZones(request,response);
		}
		
		//获取是否显示换货会
				@RequestMapping("/getIsShow")
				@ResponseBody
				public Map<String, Object> getIsShow(HttpServletRequest request, HttpServletResponse response) {
					String basePath = (String)request.getServletContext().getAttribute("BASEPATH_IN_SESSION");
					Parameter parameter = ParameterUtil.getParameter(request);
					if (parameter == null) {
							return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
					}
					parameter.setBasePath(basePath);
					
						return staticInfoService.getIsShow(parameter);
				}

}
