package com.axp.domain;

import java.sql.Timestamp;

/**
 * UserProfits entity. @author MyEclipse Persistence Tools
 */
public class UserProfits extends AbstractUserProfits implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public UserProfits() {
	}

	public UserProfits(Integer id, Users fromUsers, Users users, UserFriendsInfo userFriendsInfo, Integer score,
			Boolean isvalid, Timestamp createtime, String remark) {
		super(id, fromUsers, users, userFriendsInfo, score, isvalid, createtime, remark);
		// TODO Auto-generated constructor stub
	}

	
}
