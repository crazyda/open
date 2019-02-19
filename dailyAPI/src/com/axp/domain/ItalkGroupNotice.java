package com.axp.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * ItalkGroupNotice entity. @author MyEclipse Persistence Tools
 */
public class ItalkGroupNotice extends AbstractItalkGroupNotice implements Serializable {

	// Constructors

	/** default constructor */
	public ItalkGroupNotice() {
	}

	/** full constructor */
	public ItalkGroupNotice(ItalkGroup italkGroup, String name, String content, Boolean isValid, Timestamp createTime, Timestamp lastTime) {
		super(italkGroup, name, content, isValid, createTime, lastTime);
	}

}
