package com.axp.domain;

import java.sql.Timestamp;

/**
 * UserCoupons entity. @author MyEclipse Persistence Tools
 */
public class UserCoupons extends AbstractUserCoupons implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public UserCoupons() {
	}

	public UserCoupons(Integer id, Boolean isValid, Timestamp createTime,
			Users users, Seller seller, ReGoodsofextendmall ticket,
			Boolean isUse, String goodsName, Double ticketprice,
			Timestamp validtime, String goodsMall, Integer transportationType,
			String sellerAddress, Integer sign) {
		super(id, isValid, createTime, users, seller, ticket, isUse, goodsName,
				ticketprice, validtime, goodsMall, transportationType, sellerAddress,
				sign);
		// TODO Auto-generated constructor stub
	}



	

}
