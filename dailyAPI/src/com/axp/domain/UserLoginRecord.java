package com.axp.domain;

import java.sql.Timestamp;

/**
 * UserLoginRecord entity. @author MyEclipse Persistence Tools
 */
public class UserLoginRecord extends AbstractUserLoginRecord implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public UserLoginRecord() {
	}

	/** minimal constructor */
	public UserLoginRecord(Integer id, Users users, Timestamp lasttime) {
		super(id, users, lasttime);
	}

	/** full constructor */
	public UserLoginRecord(Integer id, Users users,
			Timestamp lasttime,Integer zoneId) {
		super(id, users,lasttime, zoneId);
	}

}
