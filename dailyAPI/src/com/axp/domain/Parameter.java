package com.axp.domain;

import java.sql.Timestamp;
import java.util.Set;

/**
 * Parameter entity. @author MyEclipse Persistence Tools
 */
public class Parameter extends AbstractParameter implements java.io.Serializable {

	// Constructors
	private Set paraChilds;

	/** default constructor */
	public Parameter() {
	}

	/** minimal constructor */
	public Parameter(Boolean isValid) {
		super(isValid);
	}

	/** full constructor */
	public Parameter(AdminUser adminUser, Parameter parameter, String name, Boolean isValid, Timestamp createTime, String description,
			String remark, Set parameters, Set cashshopParameterRecords) {
		super(adminUser, parameter, name, isValid, createTime, description, remark, parameters, cashshopParameterRecords);
	}

}
