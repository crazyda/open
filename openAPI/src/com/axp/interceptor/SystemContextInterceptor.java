package com.axp.interceptor;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.axp.dao.UsersOperationRecordDao;
import com.axp.domain.UsersOperationRecord;
import com.axp.service.UsersOperationRecordService;


public class SystemContextInterceptor implements HandlerInterceptor{
	
	@Autowired
	UsersOperationRecordService usersOperationRecordService;
	@Autowired
	UsersOperationRecordDao usersOperationRecordDao;
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		UsersOperationRecord userRecord = (UsersOperationRecord) request.getAttribute("userRecord");
		if(userRecord!=null){	
			long startTime =  (long) request.getAttribute("startTime");
			long endTime=new Date().getTime();
			Long accessTime=endTime-startTime;
			userRecord.setAccessTime(accessTime.intValue());
			usersOperationRecordDao.update(userRecord);
			request.removeAttribute("startTime");
			request.removeAttribute("userRecord");
		
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2, ModelAndView arg3) throws Exception {
		
	
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		request.setAttribute("startTime", new Date().getTime());
		
		return usersOperationRecordService.commitRecord(request);
	}
	
	public String getIpAddr(HttpServletRequest request) {
		return null;
		
	
	}  
	

}

