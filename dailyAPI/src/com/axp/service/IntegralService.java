package com.axp.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.axp.domain.AdminUser;
import com.axp.domain.DLScoreMark;

public interface IntegralService {

	
	Map<String, Object> getIntegralList(HttpServletRequest request,  HttpServletResponse response);
	
	Map<String, Object> checkPresenters(HttpServletRequest request,  HttpServletResponse response);
	
	Map<String, Object> sendScore(HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * 获取在线充值页面数据
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> rechargeOnLine(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 实卡充值
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> recharge(HttpServletRequest request, HttpServletResponse response);
	
	
	/*
	 * 扫码接收
	 * */
	Map<String, Object> receiveQRScore(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 押金充值页面
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> depositOnLine(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 退押金
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> reDeposit(HttpServletRequest request,
			HttpServletResponse response);
	/**
	 * 批量插入
	 * @param dlsms
	 * @param au 
	 */
	void saveToList(List<DLScoreMark> dlsms, AdminUser au);
}
