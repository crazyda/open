package com.axp.dao;

import java.util.List;

import com.axp.domain.ItalkGroup;
import com.axp.domain.Users;



public interface ItalkGroupDao extends IBaseDao<ItalkGroup> {

	public List<ItalkGroup> findByGroupId(Integer groupId);

	public List<ItalkGroup> findByUserId(Users user);

}
