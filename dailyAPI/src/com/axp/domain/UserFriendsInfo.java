package com.axp.domain;

import java.sql.Timestamp;


public class UserFriendsInfo extends AbstractUserFriendsInfo implements java.io.Serializable {

	public UserFriendsInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserFriendsInfo(Integer id, Boolean isValid, Users userId, Timestamp createTime, String info, String infoImg,
			Integer concernNum, Integer praiseNum, Integer appreciate) {
		super(id, isValid, userId, createTime, info, infoImg, concernNum, praiseNum, appreciate);
		// TODO Auto-generated constructor stub
	}

	
	

}
