package com.axp.dao;

import com.axp.domain.Scorerecords;
import com.axp.domain.Users;

public interface ScorerecordsDAO extends IBaseDao<Scorerecords> {

	public static final int BUY = 0x21;//购物
	public static final int BACK = 0x22;//退货
	public static final int CASH = 0x23;//提现
	
	Object getLastRecordParameterInType(String parameter, Integer userId,
			Integer type);

	Scorerecords updateRecord(Users user, Integer updateMoney, Integer type);

	Scorerecords updateRecord(Users user,Integer updateValue);
}