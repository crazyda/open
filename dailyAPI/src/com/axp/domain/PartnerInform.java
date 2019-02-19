package com.axp.domain;

import java.sql.Timestamp;

/**
 * PartnerInform entity. @author MyEclipse Persistence Tools
 */
public class PartnerInform extends AbstractPartnerInform implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public PartnerInform() {
	}

	public PartnerInform(Integer id, Users users, Users causeUsers, String remark, Boolean isValid,
			Timestamp createtime) {
		super(id, users, causeUsers, remark, isValid, createtime);
		// TODO Auto-generated constructor stub
	}


}
