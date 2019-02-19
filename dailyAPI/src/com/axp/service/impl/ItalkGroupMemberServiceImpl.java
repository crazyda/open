package com.axp.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import com.axp.domain.ItalkGroup;
import com.axp.domain.ItalkGroupMember;
import com.axp.domain.Users;
import com.axp.service.ItalkGroupMemberService;
import com.axp.util.QueryModel;




@Service
public class ItalkGroupMemberServiceImpl extends BaseServiceImpl<ItalkGroupMember> implements ItalkGroupMemberService {
	
	
	
	@Override
	public String addGroup(Integer groupId, Users user) {
		
		List<ItalkGroup> italk = italkGroupDao.findByGroupId(groupId);
		ItalkGroupMember igMember = new ItalkGroupMember();
		igMember.setIsValid(true);
		igMember.setItalkGroup(italk.get(0));
		igMember.setUsers(user);
		igMember.setCreateTime(new Timestamp(System.currentTimeMillis()));
		italkGroupMemberDao.save(igMember);
		
		return "true";
		
	}

	@Override
	public List<ItalkGroupMember> findByGroupId(Integer groupId) {
		List<ItalkGroupMember> italkList = italkGroupMemberDao.findByGroupId(groupId);
		return italkList;
	}

	@Override
	public List<ItalkGroupMember> findByUserId(Integer userId) {
		QueryModel queryModel = new QueryModel();
		
		queryModel.combPreEquals("isValid", true);
		queryModel.combPreEquals("users.id", userId,"userId");
		queryModel.setOrder("createTime DESC");
		List<ItalkGroupMember> o = dateBaseDAO.findLists(ItalkGroupMember.class, queryModel);
		return o;
	}

	
	
		




}
