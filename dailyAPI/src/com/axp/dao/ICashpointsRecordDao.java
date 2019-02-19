package com.axp.dao;

import com.axp.domain.CashpointsRecord;
import com.axp.domain.Users;

public interface ICashpointsRecordDao extends IBaseDao<CashpointsRecord> {
	public static final int BUY = 0x21;//购物
	public static final int BACK = 0x22;//退货
	public static final int CASH = 0x23;//提现
	
	CashpointsRecord updateRecord(Users user, Double updateValue, Integer type);
}
