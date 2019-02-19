package com.axp.domain;

import java.sql.Timestamp;

/**
 * AbstractUserLoginRecord entity provides the base persistence definition of
 * the UserLoginRecord entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractUserLoginRecord implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer zoneId;
	private Users users;
	private Timestamp lasttime;
	private String appVersion;

	// Constructors

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	/** default constructor */
	public AbstractUserLoginRecord() {
	}

	/** minimal constructor */
	public AbstractUserLoginRecord(Integer id, Users users, Timestamp lasttime) {
		this.id = id;
		this.users = users;
		this.lasttime = lasttime;
	}

	/** full constructor */
	public AbstractUserLoginRecord(Integer id, Users users,
			 Timestamp lasttime,Integer zoneId) {
		this.id = id;
		this.users = users;
		this.lasttime = lasttime;
		this.zoneId=zoneId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
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


	public Timestamp getLasttime() {
		return this.lasttime;
	}

	public void setLasttime(Timestamp lasttime) {
		this.lasttime = lasttime;
	}


	public Integer getZoneId() {
		return zoneId;
	}

	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}

}