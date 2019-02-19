package com.axp.domain;

import java.sql.Timestamp;

/**
 * ReRole entity. @author MyEclipse Persistence Tools
 */
public class ReRole extends AbstractReRole implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public ReRole() {
	}

	/** full constructor */
	public ReRole(Boolean isValid, Timestamp createTime, String name,
			Integer adminusersId, Integer sellerId) {
		super(isValid, createTime, name, adminusersId, sellerId);
	}

}
