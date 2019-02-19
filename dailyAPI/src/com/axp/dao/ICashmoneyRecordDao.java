package com.axp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.axp.domain.CashmoneyRecord;
import com.axp.domain.ReGoodsorder;
import com.axp.domain.Users;

public interface ICashmoneyRecordDao extends IBaseDao<CashmoneyRecord>  {
	
	public static final int BUY = 0x01;//购物
	public static final int BACK = 0x02;//退货
	public static final int CASH = 0x03;//提现
	public static final int BUYMERGE=0x04; //购物合并支付
	public static final int SHOP = 0x05;//一元开店的
	CashmoneyRecord updateRecord(Users user, Double updateMoney, Integer type);
	CashmoneyRecord updateRecord(Users user, Double updateMoney, Integer type,String remark);

	CashmoneyRecord updateRecordForBuy(Users user, Double updateMoney,
			ReGoodsorder order);

	CashmoneyRecord updateRecordByThirdParty(Users user, Double updateMoney,
			ReGoodsorder order);
	double getSumMoneyByType(Users user,Integer type);
	public void updateMoneyById(Integer itemId) ;
	
	
}