package com.axp.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class ItalkGroupMember extends AbstractItalkGroupMember implements
		Serializable {
	public ItalkGroupMember() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItalkGroupMember(Integer id,  ItalkGroup italkGroup,
			Users users, Integer type, Boolean isValid, Boolean isForbid,Timestamp createTime) {
		super(id,  italkGroup, users, type, isValid,isForbid, createTime);
		// TODO Auto-generated constructor stub
	}
	
		
	
}
