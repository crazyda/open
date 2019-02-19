package com.axp.dao;

import com.axp.dao.IBaseDao;
import com.axp.domain.ShopConcern;


public interface ShopConcernDao extends IBaseDao<ShopConcern> {

	Boolean isConcern(Integer sellerId, Integer userId);


}
