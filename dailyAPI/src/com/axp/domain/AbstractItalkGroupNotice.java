package com.axp.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * AbstractItalkGroupNotice entity provides the base persistence definition of
 * the ItalkGroupNotice entity. @author MyEclipse Persistence Tools
 */
public abstract class AbstractItalkGroupNotice implements Serializable {

	// Fields

	private Integer id;
	private ItalkGroup italkGroup;
	private Integer groupId;
	private String name;
	private String content;
	private Boolean isValid;
	private Timestamp createTime;
	private Timestamp lastTime;

	// Constructors

	/** default constructor */
	public AbstractItalkGroupNotice() {
	}

	/** full constructor */
	public AbstractItalkGroupNotice( ItalkGroup italkGroup, String name, String content, Boolean isValid, Timestamp createTime, Timestamp lastTime) {
		this.italkGroup = italkGroup;
		this.name = name;
		this.content = content;
		this.isValid = isValid;
		this.createTime = createTime;
		this.lastTime = lastTime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ItalkGroup getItalkGroup() {
		return this.italkGroup;
	}

	public void setItalkGroup(ItalkGroup italkGroup) {
		this.italkGroup = italkGroup;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Timestamp getLastTime() {
		return this.lastTime;
	}

	public void setLastTime(Timestamp lastTime) {
		this.lastTime = lastTime;
	}

}