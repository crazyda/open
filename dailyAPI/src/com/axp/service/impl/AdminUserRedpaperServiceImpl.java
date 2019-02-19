package com.axp.service.impl;




import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.axp.domain.AdminUser;
import com.axp.domain.AdminuserCashpointRecord;
import com.axp.domain.AdminuserRedpaper;
import com.axp.domain.ProvinceEnum;
import com.axp.domain.ReroleRepermission;
import com.axp.domain.ReroleRepermissionId;
import com.axp.service.AdminUserRedpaperService;
import com.axp.util.CalcUtil;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;

@Service
public class AdminUserRedpaperServiceImpl extends BaseServiceImpl<AdminuserRedpaper> implements AdminUserRedpaperService{

	


	@Override
	public Map<String, Object> sendRedpaper(HttpServletRequest request,HttpServletResponse response) {
		
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try{
			
			AdminuserRedpaper adminuserRedpaper=new AdminuserRedpaper();
			Map<String, Object> data=new HashMap<String, Object>();
			Parameter parameter=ParameterUtil.getParameter(request);
			if (parameter==null) {
				return JsonResponseUtil.getJson(-2,"参数data不是合法的json字符串");
			}
			
			String adminuserId=parameter.getAdminuserId();
			double money = parameter.getData().getDoubleValue("money");
			int mun = parameter.getData().getIntValue("mun");
			String message=parameter.getData().getString("message");
			int type = parameter.getData().getIntValue("type");
			
			if(money<0.08){
				return JsonResponseUtil.getJson(-4,"红包金额不能小于0.08");
			}
			
			if(mun<=0){
				return JsonResponseUtil.getJson(-4,"红包个数不能小于1");
			}
			
			
			if(type==50){//定额红包
				money=mun*money;
				
			}else if(type==10){
				double ninmoney = mun*0.08;
				if(ninmoney>money){
					return JsonResponseUtil.getJson(-5,"单个红包金额不能小于0.08");
				}
			}else{
				return JsonResponseUtil.getJson(-4,"无此红包类型");
			}
			String basePath=request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
			
			AdminUser adminUser= adminUserDao.findById(Integer.parseInt(adminuserId));
		
			if(adminUser.getMoney()==null || adminUser.getMoney()<=0 || adminUser.getMoney()<money){
				return JsonResponseUtil.getJson(-3,"余额不足");
			}
			ProvinceEnum zone=null;
			if(adminUser.getProvinceEnum()!=null){
				zone= adminUser.getProvinceEnum();
			}
			
			 
			int max=99999;
			int min=10000;
			//int num=(int)(Math.random()*(max-min))+min;
			//=====================ZL========start=======//
			int A=(int) CalcUtil.sub(max, min);
			int B=(int) CalcUtil.mul(Math.random(), A, 2);
			int num=(int) CalcUtil.add(B, min, 2);
		
			adminuserRedpaper.setIsvalid(true);
			adminuserRedpaper.setTotalMoney(money);
			adminuserRedpaper.setTotalQuantity(mun);
			adminuserRedpaper.setReamrk(message);
			adminuserRedpaper.setAdminUser(adminUser);
			adminuserRedpaper.setCreattime(new java.sql.Timestamp(System.currentTimeMillis()));
			adminuserRedpaper.setType(type);
			adminuserRedpaper.setSurplusMoney(money);
			adminuserRedpaper.setSurplusQuantity(mun);
			adminuserRedpaper.setProvinceEnum(zone);
			adminuserRedpaper.setName(num+"");
			//==========================ZL==================================//
			
			
			if(zone==null){//地区为空，直接发全国红包
				adminuserRedpaper.setIsCountry(true);
			}else{
			
				QueryModel model=new QueryModel();
				model.clearQuery();
				model.combEquals("role_id", adminUser.getRePermissionRoleId());
				List<ReroleRepermission> list =dateBaseDAO.findLists(ReroleRepermission.class, model);
				for (ReroleRepermission reroleRepermission:list) {
					//判断ReRoleId是否为125（总部商城）
					if (reroleRepermission.getId().getRePermission().getId()!=null&&reroleRepermission.getId().getRePermission().getId()==125){
						adminuserRedpaper.setIsCountry(true);
					}else{
						adminuserRedpaper.setIsCountry(false);
					}
				}
			}
			//============================END==============================//
			AdminuserCashpointRecord acr =new AdminuserCashpointRecord();
			acr.setAdminUser(adminUser);
			acr.setBeforepoint(adminUser.getMoney());
			acr.setCashpoint(-money);
			//acr.setAfterpoint(adminUser.getMoney()==null?0.0:adminUser.getMoney()-money);
			acr.setAfterpoint(adminUser.getMoney()==null?0.0:CalcUtil.sub(adminUser.getMoney(), money));
			acr.setRemark("发红包扣除金额"+money);
			acr.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
			acr.setIsValid(true);
			acr.setType(1);
			//adminUser.setMoney(adminUser.getMoney()==null?0.0:adminUser.getMoney()-money);
			adminUser.setMoney(adminUser.getMoney()==null?0.0:CalcUtil.sub(adminUser.getMoney(), money));
			adminUserDao.update(adminUser);
			adminuserRedpaperDao.save(adminuserRedpaper);
			adminuserCashpointRecordDao.save(acr);
		
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功");
			
	
		}catch(Exception e){
			e.printStackTrace();
			statusMap.put("status", -2);
			statusMap.put("message", "获取金额失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
		
		
		
		
	}



}
