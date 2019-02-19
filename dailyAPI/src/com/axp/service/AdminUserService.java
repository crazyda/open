package com.axp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.axp.domain.AdminUser;

public interface AdminUserService extends IBaseService<AdminUser>{
	Map<String, Object> getAdminUser(String loginname,String password);
	
	Map<String, Object> getSellerInfoById(String adminuserId,String sellerId,String basePath);
	
	Map<String, Object> getAdminUserMoney(String adminuserId,String basePath,Integer page,Integer assetType);
	
	Map<String, Object> getAdminUserTotalMoney(String adminuserId,String basePath,Integer page);
	
	Map<String, Object> getAdminUserRedPaper(String adminuserId,String basePath,Integer page);
	
	Map<String, Object> getAdminUserInfoById(String userId,String adminuserId,String sellerId,String basePath);
	
	Map<String, Object> updateStoreInfo (HttpServletRequest request,HttpServletResponse response);
	
	Map<String, Object> storeVerifyStatus(HttpServletRequest request,HttpServletResponse response);
	
	Map<String, Object> getSellerHomepageInfo(String userId,String adminuserId,String sellerId,String basePath);
	
	Map<String, Object> returnCheckstatus(HttpServletRequest request,HttpServletResponse response);
}
