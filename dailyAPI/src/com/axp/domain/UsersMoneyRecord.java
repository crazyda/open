package com.axp.domain;

import java.sql.Timestamp;

/**
 * AdminuserCashpointRecord entity. @author MyEclipse Persistence Tools
 */
public class UsersMoneyRecord extends AbstractUsersMoneyRecord
		implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public UsersMoneyRecord() {
	}

	/** full constructor */
	public UsersMoneyRecord(Integer id, Users users,
			Double beforeMoney, Double money, Double afterMoney, Integer type,
			String remark, Timestamp createTime,
			String relateName, Boolean isValid, String orderString,
			String accountType, String account, ReGoodsorderItem orderItem,
			Users fromUsers) {
		super(id, users, beforeMoney, money, afterMoney, type, remark, createTime, relateName, isValid, orderString, accountType, account, orderItem, fromUsers);
	}

}
