package com.axp.domain;

import java.sql.Timestamp;


public class UserGiveUp extends AbstractUserGiveUp implements java.io.Serializable {

	public UserGiveUp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserGiveUp(Integer id, Boolean isValid, Timestamp createTime, UserFriendsInfo userFriendsInfoId,
			Users readerUserId) {
		super(id, isValid, createTime, userFriendsInfoId, readerUserId);
		// TODO Auto-generated constructor stub
	}

	
	
	

}
