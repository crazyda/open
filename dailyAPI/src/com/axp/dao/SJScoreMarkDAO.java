package com.axp.dao;

import java.util.List;

import com.axp.domain.AdminUser;
import com.axp.domain.DLScoreMark;
import com.axp.domain.SJScoreMark;



public interface SJScoreMarkDAO extends IBaseDao<SJScoreMark> {

	/**
	 * 折线图 送出量查询
	 * @return
	 */
	List<Object[]> findMonToSun();
	/**
	 * 批量添加数据
	 * @param dlsmsTemp
	 */
	void saveDlsmsTemp(List<DLScoreMark> dlsmsTemp,AdminUser au);

}
