package com.axp.domain;

import java.sql.Timestamp;

/**
 * AbstractReRole entity provides the base persistence definition of the ReRole
 * entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractReRole implements java.io.Serializable {

	// Fields

	private Integer id;
	private Boolean isValid;
	private Timestamp createTime;
	private String name;
	private Integer adminusersId;
	private Integer sellerId;

	// Constructors

	/** default constructor */
	public AbstractReRole() {
	}

	/** full constructor */
	public AbstractReRole(Boolean isValid, Timestamp createTime, String name,
			Integer adminusersId, Integer sellerId) {
		this.isValid = isValid;
		this.createTime = createTime;
		this.name = name;
		this.adminusersId = adminusersId;
		this.sellerId = sellerId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getIsValid() {
		return this.isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAdminusersId() {
		return this.adminusersId;
	}

	public void setAdminusersId(Integer adminusersId) {
		this.adminusersId = adminusersId;
	}

	public Integer getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

}