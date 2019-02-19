package com.axp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.axp.domain.AdminUser;
import com.axp.domain.AdminuserCashpointRecord;
import com.axp.domain.AdminuserWithdrawals;
import com.axp.domain.AdminuserWithdrawalsBank;
import com.axp.domain.AdminuserWithdrawalsData;
import com.axp.domain.GetmoneyRecord;
import com.axp.service.AdminuserWithdrawalsService;
import com.axp.util.CalcUtil;
import com.axp.util.DateUtil;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;
import com.axp.util.UrlUtil;

@Service
public class AdminuserWithdrawalsServiceImpl extends BaseServiceImpl<AdminuserWithdrawals> implements AdminuserWithdrawalsService{
    
	protected UrlUtil urlUtil;
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
			int bankId=parameter.getData().getInteger("bankId");
			double money = parameter.getData().getDoubleValue("money");
			AdminuserWithdrawals adminuserWithdrawals=new AdminuserWithdrawals();
			AdminUser adminUser=adminUserDao.findById(Integer.parseInt(adminuserId));
			AdminuserWithdrawalsBank adminuserWithdrawalsBank=adminuserWithdrawalsBankDao.findById(bankId);
			QueryModel queryModel=new QueryModel();
			queryModel.clearQuery();
			queryModel.combEquals("isValid", 1);
			queryModel.combPreEquals("adminUser.id", adminUser.getId(), "adminUserId");
			List<AdminuserWithdrawalsData> awdlist = dateBaseDAO.findPageList(AdminuserWithdrawalsData.class, queryModel,0,1);
			
			if(awdlist!=null && awdlist.size()>0){
				AdminuserWithdrawalsData awd = awdlist.get(0);
				adminuserWithdrawals.setAdminuserWithdrawalsData(awd);
			}else{
				return JsonResponseUtil.getJson(-2,"请先完善提现资料！");
			}
			
			double fee = adminuserWithdrawalsBank.getCounterFee()==null?0.005:adminuserWithdrawalsBank.getCounterFee()*0.01;
			double feeV  = CalcUtil.mul(money, fee, 2)<2.00?2.00:CalcUtil.mul(money, fee, 2);
			double feeV2 = 0.0;
			if(adminUser.getMoney()==money){
				feeV2 = CalcUtil.sub(money, feeV);  
				adminuserWithdrawals.setMoney(feeV2);
				adminuserWithdrawals.setRemark("从提现中扣除手续费"+feeV+"元");
				adminUser.setMoney(adminUser.getMoney()-money);
			}else{
				if(adminUser.getMoney()< money){
					statusMap.put("status", -3);
					statusMap.put("message", "提现金额不能大于账户余额");
					return statusMap;
				}
				if(adminUser.getMoney()<(money+feeV)){
					statusMap.put("status", -3);
					statusMap.put("message", "余额不足扣提现手续费:"+feeV+"元");
					return statusMap;
				}
				adminuserWithdrawals.setMoney(money);
				adminUser.setMoney(adminUser.getMoney()-(money+feeV));
				
			}
			
			adminuserWithdrawals.setIsValid(true);
			adminuserWithdrawals.setBankId(bankId);
			adminuserWithdrawals.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
			adminuserWithdrawals.setAdminuserWithdrawalsBank(adminuserWithdrawalsBank);
			adminuserWithdrawals.setAdminUser(adminUser);
			adminuserWithdrawals.setState(0);
			adminuserWithdrawals.setCounterFee(feeV);
			adminuserWithdrawalsDao.save(adminuserWithdrawals);
			
			adminUserDao.update(adminUser);
			statusMap.put("status", 1);
			statusMap.put("message", "提交成功");
			
			AdminuserCashpointRecord acr = new AdminuserCashpointRecord();
			if(adminUser.getMoney()!=money){
				acr.setRemark("提现扣除"+money);
				acr.setCashpoint(-money);
			}else{
				acr.setRemark("提现扣除"+feeV2);
				acr.setCashpoint(-feeV2);
			}
			acr.setIsDeposit(6);
			acr.setAdminUser(adminUser);
			acr.setBeforepoint(adminUser.getMoney());
			acr.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
			acr.setIsValid(true);
			acr.setType(1);
			adminuserCashpointRecordDao.save(acr);
			
			AdminuserCashpointRecord acr2 = new AdminuserCashpointRecord();
			acr2.setAdminUser(adminUser);
			acr2.setBeforepoint(adminUser.getMoney());
			acr2.setCashpoint(-feeV);
			acr2.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
			acr2.setIsValid(true);
			acr2.setRemark("提现手续费扣除"+feeV);
			acr2.setType(1);
			acr2.setIsDeposit(6);
			adminuserCashpointRecordDao.save(acr2);
		
			
		} catch (Exception e) {
			e.printStackTrace();
			statusMap.put("status", -2);
			statusMap.put("message", "提交失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return statusMap;
	}

	@Override
	public Map<String, Object> getwithdrawalApply(HttpServletRequest request,
			HttpServletResponse response) {
			Map<String, Object> statusMap = new HashMap<String, Object>();
			try {
				Parameter parameter=ParameterUtil.getParameter(request);
				if (parameter==null) {
					return JsonResponseUtil.getJson(-2,"参数data不是合法的json字符串");
				}
				Integer pageIndex = Integer.parseInt(parameter.getData().getString("pageIndex"));
				String adminuserId=parameter.getAdminuserId();
				AdminUser adminUser=adminUserDao.findById(Integer.parseInt(adminuserId));
				Map<String,Object> data =new HashMap<String,Object>();
				List<Map<String,Object>> newsList =new ArrayList<Map<String,Object>>();
				QueryModel queryModel=new QueryModel();
				queryModel.clearQuery();
				queryModel.combEquals("isValid", 1);
				queryModel.combPreEquals("adminUser.id", adminUser.getId(), "adminUserId");
				queryModel.combCondition(" ( state="+AdminuserWithdrawals.shen_qing+" "
						+ "or state="+AdminuserWithdrawals.shen_he_tong_guo+" or state="+AdminuserWithdrawals.zhi_fu_cheng_gong+")");
				Integer count =  dateBaseDAO.findCount(AdminuserWithdrawals.class, queryModel);
				List<AdminuserWithdrawals> Wdrawalslist=dateBaseDAO.findPageList(AdminuserWithdrawals.class, queryModel, 0, 16);
				int totalMoney=0;
				for (int i = 0; i < Wdrawalslist.size(); i++) {
					Map<String,Object> temp =new HashMap<String, Object>();
					String bankCode = Wdrawalslist.get(i).getAdminuserWithdrawalsBank().getCardNo();
					temp.put("bankCode", "尾号"+bankCode.substring(bankCode.length()-4, bankCode.length()));
					temp.put("bankName", Wdrawalslist.get(i).getAdminuserWithdrawalsBank().getBankName()+"");
					temp.put("totalMoney", Wdrawalslist.get(i).getMoney()==null?"":Wdrawalslist.get(i).getMoney()+"");
					temp.put("counterFee", Wdrawalslist.get(i).getCounterFee()==null?"0.5":Wdrawalslist.get(i).getCounterFee()+"");
					temp.put("createtime",DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", Wdrawalslist.get(i).getCreatetime()));
					temp.put("payState", AdminuserWithdrawals.getStatus(Wdrawalslist.get(i).getState()));
					totalMoney+= Wdrawalslist.get(i).getMoney()==null?0: Wdrawalslist.get(i).getMoney();
					newsList.add(temp);
				}
				data.put("dataList", newsList);
				data.put("adminuserId", adminUser.getId());
				queryModel.clearQuery();
				queryModel.combEquals("isValid", 1);
				queryModel.combEquals("adminUser.id", adminUser.getId());
				List<AdminuserWithdrawalsData> awdlist=dateBaseDAO.findPageList(AdminuserWithdrawalsData.class, queryModel, 0, pageSize);
				if(awdlist!=null && awdlist.size()>0){
					data.put("ischeck", "2");
				}else{
					data.put("ischeck", "0");
				}
				data.put("pageSize", count%16==0?count/16:(count/16+1));
				data.put("pageIndex", pageIndex);
				data.put("totalMoney", totalMoney+"");
				data.put("pageItemCount", 16);
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
	public Map<String, Object> getwithdrawalApplyForPay(HttpServletRequest request,
			HttpServletResponse response) {
			Map<String, Object> statusMap = new HashMap<String, Object>();
			try {
				Parameter parameter=ParameterUtil.getParameter(request);
				if (parameter==null) {
					return JsonResponseUtil.getJson(-2,"参数data不是合法的json字符串");
				}
				Integer pageIndex = Integer.parseInt(parameter.getData().getString("pageIndex"));
				String adminuserId=parameter.getAdminuserId();
				AdminUser adminUser=adminUserDao.findById(Integer.parseInt(adminuserId));
				Map<String,Object> data =new HashMap<String,Object>();
				List<Map<String,Object>> newsList =new ArrayList<Map<String,Object>>();
				QueryModel queryModel=new QueryModel();
				queryModel.clearQuery();
				queryModel.combEquals("isValid", 1);
				queryModel.combEquals("adminUser.id", adminUser.getId());
				queryModel.combEquals("state", 10);
				Integer count =  dateBaseDAO.findCount(AdminuserWithdrawals.class, queryModel);
				List<AdminuserWithdrawals> Wdrawalslist=dateBaseDAO.findPageList(AdminuserWithdrawals.class, queryModel, (pageIndex-1)*pageSize, pageSize);
				int totalMoney=0;
				for (int i = 0; i < Wdrawalslist.size(); i++) {
					Map<String,Object> temp =new HashMap<String, Object>();
					temp.put("bankName", Wdrawalslist.get(i).getAdminuserWithdrawalsBank().getBankName());
					String bankCode = Wdrawalslist.get(i).getAdminuserWithdrawalsBank().getCardNo();
					temp.put("bankCode", "尾号"+bankCode.substring(bankCode.length()-4, bankCode.length()));
					temp.put("payState", "（已支付）");
					temp.put("totalMoney", Wdrawalslist.get(i).getMoney()==null?"":Wdrawalslist.get(i).getMoney());
					temp.put("counterFee", Wdrawalslist.get(i).getCounterFee()==null?"0.5":Wdrawalslist.get(i).getCounterFee());
					String createtime= Wdrawalslist.get(i).getChecktime()==null?"":Wdrawalslist.get(i).getCreatetime().toString();
					if(StringUtils.isNotBlank(createtime)){
						createtime=DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", Wdrawalslist.get(i).getCreatetime());
					}
					temp.put("createtime",createtime);
					totalMoney+= Wdrawalslist.get(i).getMoney()==null?0: Wdrawalslist.get(i).getMoney();
					newsList.add(temp);
				}
				
				queryModel.clearQuery();
				queryModel.combEquals("isValid", 1);
				queryModel.combEquals("adminUser.id", adminUser.getId());
				List<AdminuserWithdrawalsData> awdlist=dateBaseDAO.findPageList(AdminuserWithdrawalsData.class, queryModel, (pageIndex-1)*pageSize, pageSize);
				if(awdlist!=null && awdlist.size()>0){
					data.put("ischeck", "2");//审核通过
				}else if(false){
				}
				else{
					data.put("ischeck", "0");//未提交资料
				}
				
				data.put("dataList", newsList);
				data.put("adminuserId", adminUser.getId());
				data.put("totalMoney", totalMoney+"");
				data.put("pageSize", count%pageSize==0?count/pageSize:(count/pageSize+1));
				data.put("pageIndex", pageIndex);
				data.put("pageItemCount", pageSize);
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
	
	
