package com.axp.dao;

import java.util.List;

import com.axp.domain.UserLoginRecord;

public interface UserLoginRecordDao extends IBaseDao<UserLoginRecord>{
	
	public List<Object[]> findZoneIdCount();
	
}
