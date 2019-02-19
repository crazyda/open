package com.axp.dao;

import java.util.List;

import com.axp.domain.ItalkGroupMember;


public interface ItalkGroupMemberDao extends IBaseDao<ItalkGroupMember>{
	List<ItalkGroupMember> findByGroupId(Integer groupId);
	
	List<ItalkGroupMember> findByGroup(Integer groupId,Integer userId);

	List<ItalkGroupMember> findByUserId(Integer userId);
	
}
