package com.axp.dao;

import java.util.List;

import com.axp.domain.ProvinceEnum;

public interface ProvinceEnumDAO extends IBaseDao<ProvinceEnum> {

	ProvinceEnum getCurrentCity(Integer id);
	
	ProvinceEnum getCurrentCity2(Integer id);
	
	List<ProvinceEnum> getListByLevel(int level);//根据获取对应级别  1为省级，2为市级，3为县级
	
	List<ProvinceEnum> getListByParentCity(int zoneid);//根据上级id查找对应下级城市

	List<ProvinceEnum> findLevel();//查找等级不等于1的城市
	
	List<ProvinceEnum> findByIsHot(Integer isHot);//根据上级id查找对应下级城市
	
	
}