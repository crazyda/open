package com.axp.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axp.domain.ItalkGroup;
import com.axp.domain.ItalkGroupMember;
import com.axp.domain.Users;
import com.axp.service.ItalkGroupMemberService;
import com.axp.service.ItalkGroupService;
import com.axp.service.TkldPidService;




@Service
public class ItalkGroupServiceImpl extends BaseServiceImpl<ItalkGroup> implements ItalkGroupService {

	@Override
	public void creategroup(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ItalkGroup> findByUserId(Integer userId) {
		
		return null;
	}
	
	
	

	
	
		




}
