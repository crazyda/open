package com.axp.domain;

import java.sql.Timestamp;

/**
 * AliMessageList entity. @author MyEclipse Persistence Tools
 */
public class AliMessageList extends AbstractAliMessageList implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public AliMessageList() {
	}

	/** minimal constructor */
	public AliMessageList(Integer id) {
		super(id);
	}

	/** full constructor */
	public AliMessageList(Integer id, Integer receiveUserId, String receivePhone, String content,
			Boolean isValid, Timestamp createtime) {
		super(id, receivePhone, content, isValid, createtime);
	}

}
