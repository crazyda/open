package com.axp.dao;

import java.util.List;

import com.axp.dao.IBaseDao;
import com.axp.domain.CashshopTimes;

public interface CashshopTimesDAO extends IBaseDao<CashshopTimes> {

	List<CashshopTimes> findTimesOfHQ();

}
