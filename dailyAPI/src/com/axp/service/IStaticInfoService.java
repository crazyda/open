package com.axp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.axp.domain.ProvinceEnum;
import com.axp.util.Parameter;

public interface IStaticInfoService  extends IBaseService<ProvinceEnum>{

	
	Map<String, Object> getZones(HttpServletRequest request, HttpServletResponse response);

	Map<String, Object> getConfig(Parameter parameter);
	
	Map<String, Object> getIsShow(Parameter parameter);

}
