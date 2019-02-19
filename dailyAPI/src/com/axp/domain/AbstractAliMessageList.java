package com.axp.domain;

import java.sql.Timestamp;

/**
 * AbstractAliMessageList entity provides the base persistence definition of the
 * AliMessageList entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractAliMessageList implements java.io.Serializable {

	// Fields

	private Integer id;
	private String receivePhone;
	private String content;
	private Boolean isValid;
	private Timestamp createtime;

	// Constructors

	/** default constructor */
	public AbstractAliMessageList() {
	}

	/** minimal constructor */
	public AbstractAliMessageList(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractAliMessageList(Integer id,String receivePhone,
			String content, Boolean isValid, Timestamp createtime) {
		this.id = id;
		this.receivePhone = receivePhone;
		this.content = content;
		this.isValid = isValid;
		this.createtime = createtime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getReceivePhone() {
		return this.receivePhone;
	}

	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
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

	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

}