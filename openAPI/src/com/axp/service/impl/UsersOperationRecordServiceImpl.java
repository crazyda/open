package com.axp.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.axp.dao.UsersOperationRecordDao;
import com.axp.domain.UsersOperationRecord;
import com.axp.service.UsersOperationRecordService;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;

@Service
public class UsersOperationRecordServiceImpl extends BaseServiceImpl<UsersOperationRecord> implements UsersOperationRecordService{

	@Override
	public boolean commitRecord(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map<String, Object> statusMap= new HashMap<String, Object>();
		String data=request.getParameter("data");
		
		
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			UsersOperationRecord usersOperationRecord= new UsersOperationRecord();
			String requsetUri= request.getRequestURI();
			
			if(parameter==null){
				usersOperationRecord.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
				usersOperationRecord.setRequsetUri(requsetUri);
				usersOperationRecordDAO.save(usersOperationRecord);
				return true;
			}else{
				
				usersOperationRecord.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
				usersOperationRecord.setData(data);
				usersOperationRecord.setRequsetUri(requsetUri);
				usersOperationRecordDAO.save(usersOperationRecord);
				request.setAttribute("userRecord", usersOperationRecord);
				
				
				return true;
			}
		} catch (Exception e) {
			System.out.println("data问题：----"+data);
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return false;
	}

	@Override
	public boolean isUsers(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
