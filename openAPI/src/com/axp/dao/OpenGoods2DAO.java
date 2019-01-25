package com.axp.dao;

import java.util.List;

import com.axp.domain.OpenGoods;
import com.axp.domain.OpenGoods2;

public interface OpenGoods2DAO extends IBaseDao<OpenGoods2> {
	
	void delAll();
	void  savebatch(List<OpenGoods2> ms);
}
