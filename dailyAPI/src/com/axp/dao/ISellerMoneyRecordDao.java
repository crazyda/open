package com.axp.dao;

import com.axp.domain.Seller;
import com.axp.domain.SellerMoneyRecord;

public interface ISellerMoneyRecordDao extends IBaseDao<SellerMoneyRecord> {
	
	public static final int BUY = 0x01;//购物
	public static final int BACK = 0x02;//退货
	public static final int CASH = 0x03;//提现
	
	Double getDisComfirmedSum(Integer sellerId);

	void saveRecord(Double money, Double balance, Seller seller,
			Boolean isConfirmed, Integer type, Integer relateId,
			Class<?> relateObject);
	
	void saveRecord(Double money, Double balance, Seller seller,
			Boolean isConfirmed, Integer type, Integer relateId,
			Class<?> relateObject,String remark);

	void activateRecord(Seller seller, Integer type, Integer relateId,
			Class<?> relateObject);

}
