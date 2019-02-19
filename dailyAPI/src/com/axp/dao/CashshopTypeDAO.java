package com.axp.dao;

import java.util.List;
import java.util.Map;

import com.axp.dao.IBaseDao;
import com.axp.domain.CashshopTimes;
import com.axp.domain.CashshopType;

public interface CashshopTypeDAO extends IBaseDao<CashshopType> {

	List<CashshopType> findImageText();

	List<CashshopType> findImageText(Integer type);
	
	List<CashshopType> findImageText(Integer type,Integer zoneid);

	List<CashshopType> findImageTextByVersion(Integer Version);
	
	
	
	
}
