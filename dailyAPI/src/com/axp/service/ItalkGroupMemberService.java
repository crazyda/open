package com.axp.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.axp.domain.ItalkGroupMember;
import com.axp.domain.Users;




public interface ItalkGroupMemberService  {
	/**
	 * 拉人入群
	 * @param request
	 */
	String addGroup(Integer groupId,Users user) ;
	/**
	 * 查看群成员
	 * @param groupId
	 * @return
	 */
	List<ItalkGroupMember> findByGroupId(Integer groupId);
	
	List<ItalkGroupMember> findByUserId(Integer userId);
	
	
}
