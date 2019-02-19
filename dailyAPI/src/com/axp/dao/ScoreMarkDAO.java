package com.axp.dao;

import java.util.List;

import com.axp.domain.ScoreMark;

public interface ScoreMarkDAO extends IBaseDao<ScoreMark> {

	/**
	 * 查询每积分对应是谁的
	 * @param string
	 * @return
	 */
	List<Object[]> findGroup(String remark,String newHands,String orderBy,Integer score);
	
	
	/**
	 * 提醒用户还有几天积分到期
	 * @param day
	 * @return
	 */
	List<Object[]> findByOverdue(Integer day);
}
