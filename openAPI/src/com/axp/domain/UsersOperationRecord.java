package com.axp.domain;

import java.sql.Timestamp;

/**
 * UsersOperationRecord entity. @author MyEclipse Persistence Tools
 */
public class UsersOperationRecord extends AbstractUsersOperationRecord
		implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public UsersOperationRecord() {
	}

	/** full constructor */
	public UsersOperationRecord(String openId, Timestamp createtime,
			String requsetUri, String data
			
			 ) {
		super(openId, createtime, requsetUri, data);
	}

}
