package com.axp.interceptor;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.axp.dao.AdminUserDAO;
import com.axp.dao.IUsersDao;
import com.axp.dao.UsersOperationRecordDao;
import com.axp.domain.Users;
import com.axp.domain.UsersOperationRecord;
import com.axp.service.UsersOperationRecordService;
import com.axp.util.MD5Util;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;

public class SystemContextInterceptor implements HandlerInterceptor{
	@Autowired
	public UsersOperationRecordService usersOperationRecordService;
	@Autowired
	public UsersOperationRecordDao usersOperationRecordDao;
	@Autowired
	public AdminUserDAO adminUserDAO;
	@Autowired
	public IUsersDao usersDao;
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		
		UsersOperationRecord userRecord = (UsersOperationRecord) request.getAttribute("userRecord");
		if(userRecord!=null){	
			long startTime = (long) request.getAttribute("startTime");
			long endTime=new Date().getTime();
			Long accessTime=endTime-startTime;
			userRecord.setAccessTime(accessTime.intValue());
			usersOperationRecordDao.update(userRecord);
			request.removeAttribute("startTime");
			request.removeAttribute("userRecord");
		}
		//记录访问耗时
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2, ModelAndView arg3) throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		try {
			request.setAttribute("startTime", new Date().getTime());
			
			String requsetUri= request.getRequestURI(); //MD5Util.GetMD5Code
			Parameter parameter = ParameterUtil.getParameter(request);
			String app="";
			String v="";
			
			if("/dailyAPI/invoke/users/sendCaptcha".equals(requsetUri)){
				String cid = parameter.getChannelId();
				if("2ac8ed80bf4bf7028dc3f8449740cd02".equals(cid)){
					return false;
				}
				
				String lng = parameter.getLng();
				if("117.16169".equals(lng)){
					return false;
				}
				
				String lat = parameter.getLat();
				if("35.085608".equals(lat)){
					return false;
				}
				
				String data = request.getParameter("data");
				if(data.contains("2017-07-10 16:41:08")){
					return false;
				}
				
				String mC = parameter.getMachineCode();
				if("99000563371025".equals(mC)){
					return false;
				}
			}
			if(parameter!=null){
				app=parameter.getApp();
				v = parameter.getAppVersion();
			}
			
	
			if(requsetUri.startsWith("/dailyAPI/invoke/")){//需要加密访问的接口
					if(requsetUri.equals("/dailyAPI/invoke/users/sendCaptcha") 
							||requsetUri.equals("/dailyAPI/invoke/users/updateScore") ||	
							requsetUri.equals("/dailyAPI/invoke/order/createTempOrderList") ||	
							requsetUri.equals("/dailyAPI/invoke/order/payOrders") ||
							requsetUri.equals("/dailyAPI/invoke/order/putComment") ||
							requsetUri.equals("/dailyAPI/invoke/order/confirmReceipt") ||
							requsetUri.equals("/dailyAPI/invoke/order/applyBackOrder") ||
							requsetUri.equals("/dailyAPI/invoke/advers/getAdverImgs") ||
							requsetUri.equals("/dailyAPI/invoke/users/checkAdminRedPaper") ||
							requsetUri.equals("/dailyAPI/invoke/users/openAdminRedPaper") ||
							requsetUri.equals("/dailyAPI/invoke/users/applyWithdrawals") 
							
						){
						/*if(!MD5Util.getAxpMd5code(parameter.getUserId(),parameter.getAxp(),parameter.getTimes())){
							System.out.println("被拦截的===="+requsetUri+"====="+request.getParameter("data"));
						    return false;
						}*/
				}
				
				
				return	usersOperationRecordService.commitRecord(request);
			}
			return true;   
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String getIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("x-forwarded-for");  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP"); 
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}  
	

}

