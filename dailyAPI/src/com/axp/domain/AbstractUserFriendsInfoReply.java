package com.axp.domain;

import java.sql.Timestamp;

/**
 * AbstractAdver entity provides the base persistence definition of the Adver
 * entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractUserFriendsInfoReply implements java.io.Serializable {

	// Fields

	private Integer id;
	private Boolean isvalid;
	private Timestamp createtime;
	private Users users ;
	private UserFriendsInfo userFriendsInfo;
	private String replyInfo; // 回复内容
	
	
	public AbstractUserFriendsInfoReply() {
		super();
	}


	public AbstractUserFriendsInfoReply(Integer id, Boolean isvalid, Timestamp createtime, Users users,
			UserFriendsInfo userFriendsInfo, String replyInfo) {
		super();
		this.id = id;
		this.isvalid = isvalid;
		this.createtime = createtime;
		this.users = users;
		this.userFriendsInfo = userFriendsInfo;
		this.replyInfo = replyInfo;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Boolean getIsvalid() {
		return isvalid;
	}


	public void setIsvalid(Boolean isvalid) {
		this.isvalid = isvalid;
	}


	public Timestamp getCreatetime() {
		return createtime;
	}


	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}


	public Users getUsers() {
		return users;
	}


	public void setUsers(Users users) {
		this.users = users;
	}


	public UserFriendsInfo getUserFriendsInfo() {
		return userFriendsInfo;
	}


	public void setUserFriendsInfo(UserFriendsInfo userFriendsInfo) {
		this.userFriendsInfo = userFriendsInfo;
	}


	public String getReplyInfo() {
		return replyInfo;
	}


	public void setReplyInfo(String replyInfo) {
		this.replyInfo = replyInfo;
	}



}