package com.axp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.axp.domain.AdminuserWithdrawalsBank;

public interface AdminuserWithdrawalsBankService extends IBaseService<AdminuserWithdrawalsBank>{
	Map<String, Object> commitBankInfo(HttpServletRequest request,HttpServletResponse response);
	Map<String, Object> getBankInfo(HttpServletRequest request,HttpServletResponse response);
	Map<String, Object> delBankInfoById(HttpServletRequest request,HttpServletResponse response);
	Map<String, Object> updataBankInfoById(HttpServletRequest request,HttpServletResponse response);

}