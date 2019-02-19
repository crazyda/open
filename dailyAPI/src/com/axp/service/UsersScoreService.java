package com.axp.service;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.axp.domain.Scorerecords;

public interface UsersScoreService extends IBaseService<Scorerecords>{

	Map<String, Object> updateSocre(ServletContext servletContext, Integer userId, String dataList,String v,boolean isAndroidD);

	Map<String, Object> getRecord(Integer userId, Integer pagenum,Integer type);
	
	Map<String,Object> updateScore2(HttpServletRequest request,HttpServletResponse response);
	

	Map<String,Object> jphSendScore(HttpServletRequest request,HttpServletResponse response);
	
	Map<String,Object> goldCoinConvert(HttpServletRequest request,HttpServletResponse response);
	
	
	
	Map<String,Object> checkReceiverInfo(HttpServletRequest request,HttpServletResponse response);
}