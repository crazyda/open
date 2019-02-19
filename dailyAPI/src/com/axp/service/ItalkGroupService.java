package com.axp.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.axp.domain.ItalkGroup;




public interface ItalkGroupService  {
	/**
	 * 拉人入群
	 * @param request
	 */
	void creategroup(HttpServletRequest request) ;
	/**
	 * 通过userID
	 * 查找对应的群
	 * @param request
	 * @return 
	 */
	List<ItalkGroup> findByUserId(Integer userId);
	
}
