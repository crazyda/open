package com.axp.domain;

import java.sql.Timestamp;

/**
 * AbstractUserProfits entity provides the base persistence definition of the
 * UserProfits entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractUserProfits implements java.io.Serializable {

	// Fields

	private Integer id;
	private Users fromUsers; // 来源用户
	private Users users; // 收入用户
	private UserFriendsInfo userFriendsInfo;//被赞赏文章
	private Integer Score;
	private Boolean isvalid;
	private Timestamp createtime;
	private String remark;

	// Constructors

	/** default constructor */
	public AbstractUserProfits() {
	}

	/** full constructor */


	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public AbstractUserProfits(Integer id, Users fromUsers, Users users, UserFriendsInfo userFriendsInfo, Integer score,
			Boolean isvalid, Timestamp createtime, String remark) {
		super();
		this.id = id;
		this.fromUsers = fromUsers;
		this.users = users;
		this.userFriendsInfo = userFriendsInfo;
		Score = score;
		this.isvalid = isvalid;
		this.createtime = createtime;
		this.remark = remark;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	

	public Users getFromUsers() {
		return fromUsers;
	}

	public void setFromUsers(Users fromUsers) {
		this.fromUsers = fromUsers;
	}

	public UserFriendsInfo getUserFriendsInfo() {
		return userFriendsInfo;
	}

	public void setUserFriendsInfo(UserFriendsInfo userFriendsInfo) {
		this.userFriendsInfo = userFriendsInfo;
	}

	public Integer getScore() {
		return Score;
	}

	public void setScore(Integer score) {
		Score = score;
	}

	public Boolean getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(Boolean isvalid) {
		this.isvalid = isvalid;
	}

	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}