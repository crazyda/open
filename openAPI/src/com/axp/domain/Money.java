package com.axp.domain;

import java.sql.Timestamp;

/**
 * OpenClient entity. @author MyEclipse Persistence Tools
 */
public class Money extends AbstractMoney implements
		java.io.Serializable {

	public Money() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Money(Integer id, String clientId, Double money, String bankCard,
			String bankName, String bankAddress, Timestamp createTime,
			Integer isPay, Integer isCheck, String payCard, Integer payUsers,
			Integer checkUsers, Timestamp payTime, String name) {
		super(id, clientId, money, bankCard, bankName, bankAddress, createTime, isPay,
				isCheck, payCard, payUsers, checkUsers, payTime, name);
		// TODO Auto-generated constructor stub
	}

	

	// Constructors
	
}
