package com.axp.domain;

import java.sql.Timestamp;

/**
 * UserOrderMessage entity. @author MyEclipse Persistence Tools
 */
public class UserOrderMessage extends AbstractUserOrderMessage implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public UserOrderMessage() {
	}

	/** full constructor */
	public UserOrderMessage(OrderMessageList orderMessageList, Integer userId,Integer adminuserId,
			Integer isRead, Timestamp readtime, Boolean isValid, Timestamp createtime) {
		super(orderMessageList, userId, isRead, readtime, adminuserId, isValid, createtime);
	}

}
