package com.axp.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.axp.domain.AdminUser;
import com.axp.domain.Users;



public interface UserSystemMessageService{


	Map<String, Object> savesysmessage(HttpServletRequest request,
			HttpServletResponse response);
	
	Map<String, Object> saveordmessage(HttpServletRequest request,
			HttpServletResponse response);
	
	Map<String, Object> saveAssetsmessage(HttpServletRequest request,
			HttpServletResponse response);
	
	////1 用户 2 商家 userType
	Map<String, Object> saveMessage(String userType,String content ,Integer type ,String title,List<Users> ulist,String orderId,double money,Integer state);
	
	Map<String, Object> saveMessageForAdmin(String userType,String content ,Integer type ,String title,List<AdminUser> ulist,String orderId,double money,Integer state);
	
	Map<String, Object> saveMessage(String userType,String content ,Integer type ,String title,List<Users> ulist,double money);
	
	Map<String, Object> saveMessage(String title,String content);//合伙人消息

	Map<String, Object> saveallmessage(HttpServletRequest request,
			HttpServletResponse response);
	
	Map<String, Object> Test(HttpServletRequest request,
			HttpServletResponse response);

	void push(String title ,String content,String cid,HttpServletRequest request);
	
	//----da 拼多多订单同步给用户发信息
	Map<String,Object> sendMessage(String userType,String content,Integer type,String title, Users user,AdminUser adminUser,String orderId,double money,Integer state);
	
	
}
