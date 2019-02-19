package com.axp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.axp.domain.AdminuserWithdrawalsData;

public interface AdminuserWithdrawalsDataService extends IBaseService<AdminuserWithdrawalsData>{
	Map<String, Object> withdrawApply(HttpServletRequest request,HttpServletResponse response);
	Map<String, Object> getWithdrawals(HttpServletRequest request,HttpServletResponse response);
	
	Map<String, Object> withdrawApplyInfo(HttpServletRequest request,HttpServletResponse response);


}