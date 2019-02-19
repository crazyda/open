package com.axp.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.axp.dao.UserLoginRecordDao;
import com.axp.domain.ProvinceEnum;
import com.axp.domain.UserLoginRecord;
import com.axp.domain.Users;
import com.axp.service.UserLoginRecordService;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;
import com.axp.util.StringUtil;
@Service
public class UserLoginRecordServiceImpl extends BaseServiceImpl<UserLoginRecord> implements UserLoginRecordService{
	@Resource
	private UserLoginRecordDao userLoginRecordDao;
	@Override
	public Map<String, Object> commitRecord(HttpServletRequest request) {
		Map<String, Object> statusMap= new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			String userId=StringUtils.isBlank(parameter.getUserId())?"0":parameter.getUserId();
			
			Integer zoneId = StringUtil.isEmpty(parameter.getZoneId())?0:Integer.parseInt(parameter.getZoneId());
			String appV = parameter.getAppVersion();
			
			ProvinceEnum pe = provinceEnumDao.findById(zoneId);
			if(pe.getLevel2() ==3){
				zoneId = pe.getProvinceEnum().getId();
			}
			if (Integer.parseInt(userId)>0) {
				Users users = usersDao.findById(Integer.parseInt(userId));
				QueryModel queryModel = new QueryModel();
				queryModel.combPreEquals("users.id", Integer.parseInt(userId),"usersId");
				queryModel.setOrder(" id desc");
				List<UserLoginRecord> ulist = dateBaseDAO.findLists(UserLoginRecord.class, queryModel);
				
				if (ulist!=null&&ulist.size()>0) {
					UserLoginRecord userLoginRecord=ulist.get(0);
					
					if (userLoginRecord!=null) {
						if(zoneId.intValue()==userLoginRecord.getZoneId().intValue()){
							userLoginRecord.setLasttime(new java.sql.Timestamp(System.currentTimeMillis()));
							userLoginRecordDao.update(userLoginRecord);
						}else{
							UserLoginRecord userLoginRecord2 = new UserLoginRecord();
							userLoginRecord2.setUsers(users);
							userLoginRecord2.setZoneId(zoneId);
							userLoginRecord2.setAppVersion(appV);
							userLoginRecord2.setLasttime(new java.sql.Timestamp(System.currentTimeMillis()));
							userLoginRecordDao.save(userLoginRecord2);
						}
					}
				}else{
					UserLoginRecord userLoginRecord2 = new UserLoginRecord();
					userLoginRecord2.setUsers(users);
					userLoginRecord2.setZoneId(zoneId);
					userLoginRecord2.setAppVersion(appV);
					userLoginRecord2.setLasttime(new java.sql.Timestamp(System.currentTimeMillis()));
					userLoginRecordDao.save(userLoginRecord2);
				}
				
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}
}
