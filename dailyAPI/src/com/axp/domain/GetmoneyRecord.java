package com.axp.domain;

import java.sql.Timestamp;

/**
 * GetmoneyRecord entity. @author MyEclipse Persistence Tools
 */
public class GetmoneyRecord extends AbstractGetmoneyRecord implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public GetmoneyRecord() {
	}

	public GetmoneyRecord(Promoter promoter, Double money, String remark,
			Integer status, Boolean isValid, Timestamp createtime) {
		super(promoter, money, remark, status, isValid, createtime);
	}

}
