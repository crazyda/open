package com.axp.domain;

import java.sql.Timestamp;

/**
 * UserProfits entity. @author MyEclipse Persistence Tools
 */
public class UserFriendsInfoReply extends AbstractUserFriendsInfoReply implements
		java.io.Serializable {

	public UserFriendsInfoReply() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserFriendsInfoReply(Integer id, Boolean isvalid, Timestamp createtime, Users users,
			UserFriendsInfo userFriendsInfo, String replyInfo) {
		super(id, isvalid, createtime, users, userFriendsInfo, replyInfo);
		// TODO Auto-generated constructor stub
	}


	
}
