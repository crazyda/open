package com.axp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.axp.domain.Adverpool;

public interface LiveService extends IBaseService<Adverpool>{

	Map<String, Object> getTopList(Integer userId, Integer zoneId, String basePath);

	Map<String, Object> getList(Integer userId, Integer zoneId, String basePath,Integer pageIndex);
	
	Map<String, Object> getListById(Integer userId, Integer zoneId, String basePath,Integer liveid);
	
	//Map<String, Object> getSeLivelist(HttpServletRequest request,HttpServletResponse response);

}