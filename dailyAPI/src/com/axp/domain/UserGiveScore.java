package com.axp.domain;

import java.sql.Timestamp;


public class UserGiveScore extends AbstractUserGiveUp implements java.io.Serializable {

	public UserGiveScore() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserGiveScore(Integer id, Boolean isValid, Timestamp createTime, UserFriendsInfo userFriendsInfoId,
			Users readerUserId) {
		super(id, isValid, createTime, userFriendsInfoId, readerUserId);
		// TODO Auto-generated constructor stub
	}


	

}
