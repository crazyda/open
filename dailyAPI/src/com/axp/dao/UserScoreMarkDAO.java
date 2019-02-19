package com.axp.dao;

import java.util.List;

import com.axp.domain.UserScoreMark;
import com.axp.domain.Users;



public interface UserScoreMarkDAO extends IBaseDao<UserScoreMark> {

	/**
	 * 手动sql 查询用户消化积分归属那个商家,对应商家的积分数是多少
	 * seller   num
	 * @param user
	 * @param payScore
	 * @return
	 */
	List<Object[]> findBySQL(Users user, Integer payScore);

}
