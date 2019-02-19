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
import com.axp.service.AdminuserWithdrawalsBankService;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;

@Service
public class AdminuserWithdrawalsBankServiceImpl extends BaseServiceImpl<AdminuserWithdrawalsBank> implements AdminuserWithdrawalsBankService{

	@Override
	public Map<String, Object> commitBankInfo(HttpServletRequest request,
			HttpServletResponse response) {
			Map<String, Object> statusMap = new HashMap<String, Object>();
			try {
				Parameter parameter=ParameterUtil.getParameter(request);
				if (parameter==null) {
					return JsonResponseUtil.getJson(-2,"参数data不是合法的json字符串");
				}
				
				String adminuserId=parameter.getAdminuserId();
				AdminuserWithdrawalsBank adminuserWithdrawalsBank=new AdminuserWithdrawalsBank();
				AdminUser adminUser=adminUserDao.findById(Integer.parseInt(adminuserId));
				
				String bankAddress=parameter.getData().getString("bankAddress");
				String bankName=parameter.getData().getString("bankName");
				String cardNo=parameter.getData().getString("cardNo");
				
				adminuserWithdrawalsBank.setAdminUser(adminUser);
				adminuserWithdrawalsBank.setIsValid(true);
				adminuserWithdrawalsBank.setBankAddress(bankAddress);
				adminuserWithdrawalsBank.setBankName(bankName);
				adminuserWithdrawalsBank.setCardNo(cardNo);
				adminuserWithdrawalsBank.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
				adminuserWithdrawalsBank.setIsDefault(true);
				adminuserWithdrawalsBank.setCounterFee(0.5);//手续费0.5%
				adminuserWithdrawalsBankDao.updateIsDefueltByAdminuserId(Integer.parseInt(adminuserId));
				adminuserWithdrawalsBankDao.save(adminuserWithdrawalsBank);
				
				
				statusMap.put("status", 1);
				statusMap.put("message", "提交成功");
			} catch (Exception e) {
				e.printStackTrace();
				statusMap.put("status", -2);
				statusMap.put("message", "提交失败");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
		return statusMap;
	}

	@Override
	public Map<String, Object> getBankInfo(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap= new HashMap<String, Object>();
		
		try {
			Parameter parameter=ParameterUtil.getParameter(request);
			if (parameter==null) {
				return JsonResponseUtil.getJson(-2, "参数data不是合法的json字符串");
			}
			String adminuserId=parameter.getAdminuserId();
			AdminUser adminUser=adminUserDao.findById(Integer.parseInt(adminuserId));
			Map<String, Object> data=new HashMap<String, Object>();
			List<Map<String,Object>> newsList =new ArrayList<Map<String,Object>>();
			QueryModel queryModel=new QueryModel();
			queryModel.clearQuery();
			queryModel.combEquals("isValid", 1);
			queryModel.combEquals("adminUser.id", adminUser.getId());
			List<AdminuserWithdrawalsBank> banklist=dateBaseDAO.findPageList(AdminuserWithdrawalsBank.class, queryModel, 0, pageSize);
			for (int i = 0; i < banklist.size(); i++) {
				Map<String,Object> temp =new HashMap<String, Object>();
				temp.put("adminuserName", banklist.get(i).getAdminUser().getUsername()==null?"":banklist.get(i).getAdminUser().getUsername());
				temp.put("bankAddress", banklist.get(i).getBankAddress()==null?"":banklist.get(i).getBankAddress());
				temp.put("bankName", banklist.get(i).getBankName()==null?"":banklist.get(i).getBankName());
				temp.put("cardNo", banklist.get(i).getCardNo()==null?"":banklist.get(i).getCardNo());
				temp.put("bankId",banklist.get(i).getId());
				temp.put("isDefault",banklist.get(i).getIsDefault()+"");
				temp.put("counterFee",banklist.get(i).getCounterFee()==null?"0.5":banklist.get(i).getCounterFee());
				newsList.add(temp);
			}
			
			
			data.put("dataList", newsList);
			data.put("adminuserId", adminUser.getId());
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
	public Map<String, Object> delBankInfoById(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> statusMap=new HashMap<String, Object>();
		
		try {
			Parameter parameter=ParameterUtil.getParameter(request);
			if (parameter==null) {
				JsonResponseUtil.getJson(-2, "参数data不是合法的json字符串");
			}
			String adminuserId=parameter.getAdminuserId();
			String bankId=parameter.getData().getString("bankId");
			
			AdminUser adminUser=adminUserDao.findById(Integer.parseInt(adminuserId));
			AdminuserWithdrawalsBank adminuserWithdrawalsBank=adminuserWithdrawalsBankDao.findById(Integer.parseInt(bankId));
			adminuserWithdrawalsBank.setIsValid(false);
			adminuserWithdrawalsBankDao.update(adminuserWithdrawalsBank);
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
	public Map<String, Object> updataBankInfoById(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap=new HashMap<String, Object>();
		try {
			Parameter parameter=ParameterUtil.getParameter(request);
			if (parameter==null) {
				return JsonResponseUtil.getJson(-2,"参数data不是合法的json字符串");
			}
			String adminuserId=parameter.getAdminuserId();
			String bankId=parameter.getData().getString("bankId");
			String isdefault=parameter.getData().getString("isdefault");
			
			QueryModel queryModel=new QueryModel();
			queryModel.clearQuery();
			queryModel.combEquals("isValid", 1);
			queryModel.combEquals("adminUser.id", adminuserId);
			queryModel.combEquals("isDefault", 1);
			List<AdminuserWithdrawalsBank> banklist=dateBaseDAO.findPageList(AdminuserWithdrawalsBank.class, queryModel, 0, 20);
			//AdminUser adminUser=adminUserDao.findById(Integer.parseInt(adminuserId));
			AdminuserWithdrawalsBank adminuserWithdrawalsBank=adminuserWithdrawalsBankDao.findById(Integer.parseInt(bankId));
			
			if(banklist!=null && banklist.size()>0){
				for(AdminuserWithdrawalsBank istrue:banklist){
					if(istrue.getId()!=adminuserWithdrawalsBank.getId()){
						istrue.setIsDefault(false);
					}
				}
			}
			
			adminuserWithdrawalsBank.setIsDefault(true);
			adminuserWithdrawalsBankDao.update(adminuserWithdrawalsBank);
			statusMap.put("status",1);
			statusMap.put("message", "请求成功");
		} catch (Exception e) {
			e.printStackTrace();
			statusMap.put("status",-2);
			statusMap.put("message", "请求失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}

	
	

		
	
}
	
	
