package com.axp.domain;

import java.sql.Timestamp;

/**
 * MessageType entity. @author MyEclipse Persistence Tools
 */
public class MessageType extends AbstractMessageType implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public MessageType() {
	}

	/** full constructor */
	public MessageType(String icon, String title, String content,Timestamp createtime,
			Timestamp time, Integer parentId,MessageType messageType, Boolean isValid,Integer level,Integer isorder) {
		super(icon, title, content, createtime, time, parentId, messageType, isValid, level,isorder);
	}

}
