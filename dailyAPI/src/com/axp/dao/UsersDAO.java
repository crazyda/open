package com.axp.dao;

import java.util.List;

import com.axp.domain.Users;

public interface UsersDAO extends IBaseDao<Users> {

	Users findByIdIgnoreValid(Integer id);

	List<Users> findInIds(String propertyName, String ids);
	
	List<String> findInId(String propertyName, String ids);

	Users findByLoginName(String loginName);

	List<Users> findFans(Users user);

	List<String> findAllUsersid();
	
	List<Users> findAllUsers();
	
	void del(String ids);
	
	
	Users findByNameOrPhone(String presenterName);

	/**
	 * @param openId
	 * @return
	 */
	Users findByOpenId(String openId);

	/**
	 * @param unionId
	 * @return
	 */
	Users findByUnionId(String unionId);

	

	
	
}