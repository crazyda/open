package com.axp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.axp.domain.Redpaper;

public interface IRedpaperService extends IBaseService<Redpaper> {

	Map<String, Object> getConfig(HttpServletRequest request);
	

	String openNewRedPaper(HttpServletRequest request);
	
	Map<String, Object> openAdminNewRedPaper(HttpServletRequest request);

	Map<String, Object> verifyNewRedpaper(HttpServletRequest request);
	
	Map<String, Object> verifyAdminNewRedpaper(HttpServletRequest request);

	void redPaperList(HttpServletRequest request, HttpServletResponse response);

	void updateByAuto();
	
	void moneyList(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 获取订单红包
	 * @param request
	 * @return
	 */
	Map<String, Object> checkOrderRedPaper(HttpServletRequest request);
}
