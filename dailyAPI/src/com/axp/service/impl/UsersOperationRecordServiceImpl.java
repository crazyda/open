package com.axp.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.SimpleFormatter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.axp.domain.Users;
import com.axp.domain.UsersOperationRecord;
import com.axp.service.UsersOperationRecordService;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.StringUtil;

@Service
public class UsersOperationRecordServiceImpl extends BaseServiceImpl<UsersOperationRecord> implements UsersOperationRecordService{

	@Override
	public boolean commitRecord(HttpServletRequest request) {
		
		Map<String, Object> statusMap= new HashMap<String, Object>();
		String data=request.getParameter("data");
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			UsersOperationRecord usersOperationRecord= new UsersOperationRecord();
			String requsetUri= request.getRequestURI();
			
			
			
			if(parameter==null){
				usersOperationRecord.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
				usersOperationRecord.setRequsetUri(requsetUri);
				usersOperationRecord.setData(request.getQueryString());
				usersOperationRecordDao.save(usersOperationRecord);
				return true;
			}else{
				String cId=parameter.getChannelId();
				String userId=parameter.getUserId();
				String adminuserId=parameter.getAdminuserId();
				String v =parameter.getAppVersion();
				String os =parameter.getOs();
				if(StringUtils.isBlank(v) ){
					return false;
				}	
				if(StringUtils.isBlank(userId) || "null".equals(userId) || "-1".equals(userId)){
					userId="0";
				}
				if(StringUtils.isBlank(adminuserId)){
					adminuserId="0";
				}
				if(Integer.parseInt(userId)>0){
					Users user = usersDao.findById(Integer.parseInt(userId));
					if(user==null || !user.getIsvalid()){
						return false;
					}
				}
				if("48048".equals(userId) || "48081".equals(userId)){//黑名单
					return false;
				}
				
				usersOperationRecord.setAdminuserId(Integer.parseInt(adminuserId));
				usersOperationRecord.setUserId(Integer.parseInt(userId));
				
				double lng = StringUtil.isEmpty(parameter.getLng())?0:Double.parseDouble(parameter.getLng());
				double lat = StringUtil.isEmpty(parameter.getLat())?0:Double.parseDouble(parameter.getLat());
				String channelId=parameter.getChannelId();
				String machineCode=parameter.getMachineCode();
				String appVersion=parameter.getAppVersion();
				Integer zoneId = 0;
				if("undefined".equals(parameter.getZoneId())){
					System.out.println("地区数据有误"+requsetUri);
					return false;
				}else{
					zoneId = StringUtil.isEmpty(parameter.getZoneId())?0:Integer.parseInt(parameter.getZoneId());
				}
				
				usersOperationRecord.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
					
					usersOperationRecord.setData(data);
					usersOperationRecord.setRequsetUri(requsetUri);
					usersOperationRecord.setLng(lng);
					usersOperationRecord.setLat(lat);
					usersOperationRecord.setChannelId(channelId);
					usersOperationRecord.setOs(os);
					usersOperationRecord.setMachineCode(machineCode);
					usersOperationRecord.setAppVersion(appVersion);
					usersOperationRecord.setZoneId(zoneId);
				usersOperationRecordDao.save(usersOperationRecord);
				request.setAttribute("userRecord", usersOperationRecord);
				
				
				return true;
				
				}
		
		} catch (NumberFormatException e) {
			System.out.println("data问题：----"+data);
			
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	
		
		return false;
	}

	@Override
	public boolean isUsers(HttpServletRequest request) {
		
		Parameter parameter = ParameterUtil.getParameter(request);
		if(parameter!=null){
			String userId=parameter.getUserId();
			
			if(StringUtils.isBlank(userId) || "null".equals(userId) || "-1".equals(userId)){
				userId="0";
			}
			
			if(Integer.parseInt(userId)>0){
				Users user = usersDao.findById(Integer.parseInt(userId));
				if(user==null || !user.getIsvalid()){
					return false;
				}
			}
		}
		
		
		return true;
	}
	
	
}
