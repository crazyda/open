package com.axp.domain;

import java.sql.Timestamp;

/**
 * AbstractUsersOperationRecord entity provides the base persistence definition
 * of the UsersOperationRecord entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractUsersOperationRecord implements
		java.io.Serializable {

	// Fields

	private Integer id;
	private String openId;
	private Timestamp createtime;
	private String requsetUri;
	private String data;
	private Integer accessTime;
	// Constructors



	/** default constructor */
	public AbstractUsersOperationRecord() {
	}

	
	public Integer getAccessTime() {
		return accessTime;
	}


	public void setAccessTime(Integer accessTime) {
		this.accessTime = accessTime;
	}


	/** full constructor */
	public AbstractUsersOperationRecord(String openId, Timestamp createtime,
			String requsetUri, String data) {
		this.openId = openId;
		this.createtime = createtime;
		this.requsetUri = requsetUri;
		this.data = data;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getRequsetUri() {
		return this.requsetUri;
	}

	public void setRequsetUri(String requsetUri) {
		this.requsetUri = requsetUri;
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}




}