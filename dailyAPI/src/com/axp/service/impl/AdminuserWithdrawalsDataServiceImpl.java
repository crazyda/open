package com.axp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.axp.domain.AdminUser;
import com.axp.domain.AdminuserWithdrawalsBank;
import com.axp.domain.AdminuserWithdrawalsData;
import com.axp.domain.AdminuserWithdrawalsDataLog;
import com.axp.service.AdminuserWithdrawalsDataService;
import com.axp.util.CalcUtil;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;

@Service
public class AdminuserWithdrawalsDataServiceImpl extends BaseServiceImpl<AdminuserWithdrawalsData> implements AdminuserWithdrawalsDataService{

	@Override
	public Map<String, Object> withdrawApply(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter=ParameterUtil.getParameter(request);
			if (parameter==null) {
				return JsonResponseUtil.getJson(-2,"参数data不是合法的json字符串");
			}
			String adminuserId=parameter.getAdminuserId();
			AdminuserWithdrawalsData adminuserWithdrawalsData=new AdminuserWithdrawalsData();
			AdminUser adminUser=adminUserDao.findById(Integer.parseInt(adminuserId));
			String name=parameter.getData().getString("name");
			String phone=parameter.getData().getString("phone");
			String code=parameter.getData().getString("code");
			String verify=parameter.getData().getString("verify");
			
			
			//确少通过手机号码获取验证码并做比较的逻辑
			
			
			QueryModel queryModel=new QueryModel();
			queryModel.clearQuery();
			queryModel.combEquals("isvalid", 1);
			queryModel.combEquals("adminUser.id", adminUser.getId());
			
			List<AdminuserWithdrawalsData> datalist=dateBaseDAO.findPageList(AdminuserWithdrawalsData.class, queryModel, 0, pageSize);
			if (datalist!=null&&datalist.size()>0) {
				queryModel.clearQuery();
				queryModel.combEquals("isvalid", 1);
				queryModel.combEquals("adminUser.id", adminUser.getId());
				queryModel.combEquals("status", 0);
				List<AdminuserWithdrawalsDataLog> loglist=dateBaseDAO.findPageList(AdminuserWithdrawalsDataLog.class, queryModel, 0, pageSize);
				AdminuserWithdrawalsDataLog adminuserWithdrawalsDataLog=null;
				if(loglist !=null && loglist.size()>0){
					adminuserWithdrawalsDataLog =loglist.get(0);
				}else{
					adminuserWithdrawalsDataLog=new AdminuserWithdrawalsDataLog();
				}
				adminuserWithdrawalsDataLog.setIsValid(true);
				adminuserWithdrawalsDataLog.setName(name);
				adminuserWithdrawalsDataLog.setPhone(phone);
				adminuserWithdrawalsDataLog.setCode(code);
				adminuserWithdrawalsDataLog.setCretatetime(new java.sql.Timestamp(System.currentTimeMillis()));
				adminuserWithdrawalsDataLog.setAdminUser(adminUser);
				adminuserWithdrawalsDataLog.setStatus(0);
				adminuserWithdrawalsDataLogDao.saveOrUpdate(adminuserWithdrawalsDataLog);
				statusMap.put("status", 1);
				statusMap.put("message", "提交成功-等待审核！");
			}else{
				AdminuserWithdrawalsDataLog adminuserWithdrawalsDataLog=new AdminuserWithdrawalsDataLog();
				adminuserWithdrawalsDataLog.setIsValid(true);
				adminuserWithdrawalsDataLog.setName(name);
				adminuserWithdrawalsDataLog.setPhone(phone);
				adminuserWithdrawalsDataLog.setCode(code);
				adminuserWithdrawalsDataLog.setCretatetime(new java.sql.Timestamp(System.currentTimeMillis()));
				adminuserWithdrawalsDataLog.setAdminUser(adminUser);
				adminuserWithdrawalsDataLog.setStatus(10);
				adminuserWithdrawalsDataLogDao.save(adminuserWithdrawalsDataLog);
				
				adminuserWithdrawalsData.setIsValid(true);
				adminuserWithdrawalsData.setName(name);
				adminuserWithdrawalsData.setPhone(phone);
				adminuserWithdrawalsData.setCode(code);
				adminuserWithdrawalsData.setCretatetime(new java.sql.Timestamp(System.currentTimeMillis()));
				adminuserWithdrawalsData.setAdminUser(adminUser);
				
				adminuserWithdrawalsDataDao.save(adminuserWithdrawalsData);
				
				statusMap.put("status", 1);
				statusMap.put("message", "提交成功-审核通过");
			}
		} catch (Exception e) {
			e.printStackTrace();
			statusMap.put("status", -2);
			statusMap.put("message", "提交失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return statusMap;
	}

	@Override
	public Map<String, Object> getWithdrawals(HttpServletRequest request,
			HttpServletResponse response) {
			Map<String, Object> statusMap = new HashMap<String, Object>();
			try {
				Parameter parameter=ParameterUtil.getParameter(request);
				if (parameter==null) {
					return JsonResponseUtil.getJson(-2,"参数data不是合法的json字符串");
				}
				
				String adminuserId=parameter.getAdminuserId();
				AdminUser adminUser=adminUserDao.findById(Integer.parseInt(adminuserId));
				Map<String,Object> data =new HashMap<String,Object>();
				List<Map<String,Object>> newsList =new ArrayList<Map<String,Object>>();
				QueryModel queryModel=new QueryModel();
				queryModel.clearQuery();
				queryModel.combEquals("isValid", 1);
				queryModel.combEquals("adminUser.id", adminUser.getId());
				List<AdminuserWithdrawalsData> Wdrawalslist=dateBaseDAO.findPageList(AdminuserWithdrawalsData.class, queryModel, 0, pageSize);
				for (int i = 0; i < Wdrawalslist.size(); i++) {
					Map<String,Object> temp =new HashMap<String, Object>();
					temp.put("name", Wdrawalslist.get(i).getName()==null?"":Wdrawalslist.get(i).getName());
					temp.put("phone", Wdrawalslist.get(i).getPhone()==null?"":Wdrawalslist.get(i).getPhone());
					temp.put("code", Wdrawalslist.get(i).getCode()==null?"":Wdrawalslist.get(i).getCode());
					newsList.add(temp);
				}
				data.put("adminuserId", adminUser.getId());
				data.put("dataList", newsList);
				statusMap.put("data", data);
				statusMap.put("status", 1);
				statusMap.put("message", "请求成功");
			} catch (Exception e) {
				e.printStackTrace();
				statusMap.put("status", -2);
				statusMap.put("message", "请求失败");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
		return statusMap;
		
	}

	@Override
	public Map<String, Object> withdrawApplyInfo(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter=ParameterUtil.getParameter(request);
			if (parameter==null) {
				return JsonResponseUtil.getJson(-2,"参数data不是合法的json字符串");
			}
			String adminuserId=parameter.getAdminuserId();
			AdminUser adminUser=adminUserDao.findById(Integer.parseInt(adminuserId));
			Map<String,Object> data =new HashMap<String,Object>();
			List<Map<String,Object>> newsList =new ArrayList<Map<String,Object>>();
			QueryModel queryModel=new QueryModel();
			queryModel.clearQuery();
			queryModel.combEquals("isValid", 1);
			queryModel.combEquals("adminUser.id", adminUser.getId());
			queryModel.combEquals("isDefault", 1);
			List<AdminuserWithdrawalsBank> wdrawalslist=dateBaseDAO.findPageList(AdminuserWithdrawalsBank.class, queryModel, 0, pageSize);
			if(wdrawalslist!=null && wdrawalslist.size()>0){
				AdminuserWithdrawalsBank awb=wdrawalslist.get(0);
				Map<String,Object> temp =new HashMap<String, Object>();
				temp.put("bankName",awb.getBankName());
				//temp.put("cardNo",awb.getCardNo().substring(awb.getCardNo().length()-4, awb.getCardNo().length()));
				temp.put("cardNo",awb.getCardNo().substring((int)CalcUtil.sub(awb.getCardNo().length(), 4), awb.getCardNo().length()));
				temp.put("counterFee",awb.getCounterFee()==null?"0.5":awb.getCounterFee()+"");
				temp.put("bankId",awb.getId());
				newsList.add(temp);
			}
			data.put("message", "提现金额为200元起，提交后3个工作日内到账，手续费按银行标准收取，单笔最低2元。");
			data.put("minMoney", "200");
			data.put("totalMoney", adminUser.getMoney()+"");
			data.put("adminuserId", adminUser.getId());
			data.put("dataList", newsList);
			statusMap.put("data", data);
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功");
		} catch (Exception e) {
			e.printStackTrace();
			statusMap.put("status", -2);
			statusMap.put("message", "请求失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	return statusMap;
	}
}
	
	
