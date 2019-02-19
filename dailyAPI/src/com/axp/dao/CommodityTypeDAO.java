package com.axp.dao;

import java.util.List;

import com.axp.domain.CommodityType;

public interface CommodityTypeDAO extends IBaseDao<CommodityType> {

	List<CommodityType> getListOfLv1();

	List<CommodityType> getListByParentId(Integer parentId);
	
	List<CommodityType> getList(Integer modelId);
	
	List<CommodityType> getAllType();
}
