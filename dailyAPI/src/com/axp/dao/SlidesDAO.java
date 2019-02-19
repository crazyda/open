package com.axp.dao;

import java.util.List;

import com.axp.domain.Slides;


public interface SlidesDAO extends IBaseDao<Slides> {

	List<Slides> getList(Integer type);
	
	List<Slides> getsList(Integer type,Integer zoneId);
	List<Slides> getsListByZb(Integer type);
	
	List<Slides> getsListByAdminUserId(Integer adminuserId,Integer type);
	
	
	
	
}
