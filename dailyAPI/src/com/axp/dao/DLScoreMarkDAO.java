package com.axp.dao;

import java.util.List;

import com.axp.domain.DLScoreMark;



public interface DLScoreMarkDAO extends IBaseDao<DLScoreMark> {

	/**
	 * 查询代理七天的兑换量
	 * @return
	 */
	List<Object[]> findMonToSun();

}
