package com.axp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.axp.domain.AdminuserWithdrawals;
import com.axp.domain.AdminuserWithdrawalsData;

public interface AdminuserWithdrawalsService extends IBaseService<AdminuserWithdrawals>{
	Map<String, Object> withdrawApply(HttpServletRequest request,HttpServletResponse response);
	Map<String, Object> getwithdrawalApply(HttpServletRequest request,HttpServletResponse response);
	Map<String, Object> getwithdrawalApplyForPay(HttpServletRequest request,HttpServletResponse response);


}