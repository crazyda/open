package com.axp.service;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.axp.domain.AdminUser;
import com.axp.domain.TkldPid;
import com.axp.domain.Users;

		
public interface TkldPidService extends IBaseService<TkldPid> {
	
	List<TkldPid> findAll(String i);
	 Boolean isCareerPartner(AdminUser adminUser);
	void clearCache(String i);
	/**
	 * 查找事业合伙人 
	 */
	TkldPid findCareerPartner(Integer userId, Integer zoneId);
	/**
	 * level等级,
	 * @param userId
	 * @return
	 */
	public List<TkldPid> findLevelByUserId(Integer UserId);
	/**
	 * 给用佬用户添加pddpid
	 * @param request
	 * @param response
	 * @return
	 */
	boolean addPddPid(HttpServletRequest request, HttpServletResponse response);
	/**
	 * 请求接口获取pddpid
	 * @return
	 */
	String gainPddPid();
	/**
	 * 通过当前pid查找上级pid, 拼接 代理--上级-当前
	 * @param pid
	 * @return
	 */
	String findUp(String pid);
	/**
	 * @param userId
	 * @param areaType
	 * @param sortType
	 * @param zoneId
	 * @return
	 */
	Map<String, Object> getPartenerInfo(Integer userId, Integer areaType,
			Integer sortType, String zoneId);
	
	
	
	
}
