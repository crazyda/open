package com.axp.dao;

import java.util.List;

import com.axp.domain.CommodityType;
import com.axp.domain.ShopCategory;


public interface ShopCategoryDao extends IBaseDao<ShopCategory> {
	List<ShopCategory> getList();
}