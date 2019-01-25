package com.axp.dao;

import java.util.List;

import com.axp.domain.OpenGoods;

public interface OpenGoodsDAO extends IBaseDao<OpenGoods> {
	void delAll();
	
	void  savebatch(List<OpenGoods> ms);
	
	
}
