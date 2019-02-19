package com.axp.domain;

import java.sql.Timestamp;

/**
 * AbstractThePartner entity provides the base persistence definition of the
 * ThePartner entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractThePartner implements java.io.Serializable {

	// Fields

	private Integer id;
	
	private Integer userId;
	private String name;
	private String parnerName;
	private String pidId;
	private Timestamp addTime;
	private Boolean isValid;

	// Constructors

	/** default constructor */
	public AbstractThePartner() {
	}

	/** full constructor */
	public AbstractThePartner(Integer userId, String name, String parnerName,
			String pidId, Timestamp addTime, Boolean isValid) {
		this.userId = userId;
		this.name = name;
		this.parnerName = parnerName;
		this.pidId = pidId;
		this.addTime = addTime;
		this.isValid = isValid;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	


	
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParnerName() {
		return this.parnerName;
	}

	public void setParnerName(String parnerName) {
		this.parnerName = parnerName;
	}

	public String getPidId() {
		return this.pidId;
	}

	public void setPidId(String pidId) {
		this.pidId = pidId;
	}

	public Timestamp getAddTime() {
		return this.addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	public Boolean getIsValid() {
		return this.isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

}