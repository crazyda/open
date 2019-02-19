package com.axp.dao;

import java.util.List;

import com.axp.domain.TkldPid;

public interface TkldPidDao extends IBaseDao<TkldPid> {
	
	
	List<TkldPid> findByUserId(Integer UserId);
}
