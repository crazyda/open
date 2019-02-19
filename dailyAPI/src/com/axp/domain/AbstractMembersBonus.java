package com.axp.domain;

import java.sql.Timestamp;

/**
 * AbstractMembersBonus entity provides the base persistence definition of the
 * MembersBonus entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractMembersBonus implements java.io.Serializable {

	// Fields

	private Integer id;
	private MembersConfig membersConfig;
	private Double levelOneScale;
	private Double levelTwoScale;
	private Double levelThreeScale;
	private Boolean isValid;
	private Timestamp createTime;
	private AdminUser provider;

	// Constructors

	/** default constructor */
	public AbstractMembersBonus() {
	}

	/** minimal constructor */
	public AbstractMembersBonus(Boolean isValid) {
		this.isValid = isValid;
	}

	/** full constructor */
	public AbstractMembersBonus(MembersConfig membersConfig,
			Double levelOneScale, Double levelTwoScale, Double levelThreeScale,
			Boolean isValid, Timestamp createTime) {
		this.membersConfig = membersConfig;
		this.levelOneScale = levelOneScale;
		this.levelTwoScale = levelTwoScale;
		this.levelThreeScale = levelThreeScale;
		this.isValid = isValid;
		this.createTime = createTime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MembersConfig getMembersConfig() {
		return membersConfig;
	}

	public void setMembersConfig(MembersConfig membersConfig) {
		this.membersConfig = membersConfig;
	}

	public Double getLevelOneScale() {
		return this.levelOneScale;
	}

	public void setLevelOneScale(Double levelOneScale) {
		this.levelOneScale = levelOneScale;
	}

	public Double getLevelTwoScale() {
		return this.levelTwoScale;
	}

	public void setLevelTwoScale(Double levelTwoScale) {
		this.levelTwoScale = levelTwoScale;
	}

	public Double getLevelThreeScale() {
		return this.levelThreeScale;
	}

	public void setLevelThreeScale(Double levelThreeScale) {
		this.levelThreeScale = levelThreeScale;
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

	public AdminUser getProvider() {
		return provider;
	}

	public void setProvider(AdminUser provider) {
		this.provider = provider;
	}

}