package com.axp.domain;

import java.sql.Timestamp;

/**
 * UserSystemMessage entity. @author MyEclipse Persistence Tools
 */
public class UserSystemMessage extends AbstractUserSystemMessage implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public UserSystemMessage() {
	}

	/** full constructor */
	public UserSystemMessage(SystemMessageList systemMessageList, Integer isRead,
			Timestamp readtime,  Boolean isValid,Integer adminuserId,
			Timestamp createtime, Integer userId) {
		super(systemMessageList, isRead, readtime, isValid, adminuserId, createtime, userId);
	}

}
