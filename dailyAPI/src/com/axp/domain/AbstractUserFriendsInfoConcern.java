package com.axp.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;



public abstract class AbstractUserFriendsInfoConcern implements java.io.Serializable {
	
	private Integer id;
	private Users user;
	private UserFriendsInfo userFriendsInfo; 
	private boolean isfocus; //是否收藏 是否光柱
	private boolean isGiveUp;//是否点赞
	
	
	
	
	public AbstractUserFriendsInfoConcern() {
		super();
	}
	
	public AbstractUserFriendsInfoConcern(Integer id, Users user, UserFriendsInfo userFriendsInfo, boolean isfocus,
			boolean isGiveUp) {
		super();
		this.id = id;
		this.user = user;
		this.userFriendsInfo = userFriendsInfo;
		this.isfocus = isfocus;
		this.isGiveUp = isGiveUp;
	}

	public boolean isGiveUp() {
		return isGiveUp;
	}

	public void setIsGiveUp(boolean isGiveUp) {
		this.isGiveUp = isGiveUp;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public UserFriendsInfo getUserFriendsInfo() {
		return userFriendsInfo;
	}
	public void setUserFriendsInfo(UserFriendsInfo userFriendsInfo) {
		this.userFriendsInfo = userFriendsInfo;
	}
	public boolean isIsfocus() {
		return isfocus;
	}
	public void setIsfocus(boolean isfocus) {
		this.isfocus = isfocus;
	}
	
	
	
}