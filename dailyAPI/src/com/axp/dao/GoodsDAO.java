package com.axp.dao;

import java.util.List;

import com.axp.domain.Goods;


public interface GoodsDAO extends IBaseDao<Goods> {

	List<Goods> getAdverBySellerId(Integer sellerId);
	
}