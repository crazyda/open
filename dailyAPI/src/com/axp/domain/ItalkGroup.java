package com.axp.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class ItalkGroup extends AbstractItalkGroup implements Serializable {
//da
	public ItalkGroup() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItalkGroup(Users users, Integer groupId, Integer groupType,
			String name, String discription, Boolean isValid,
			Timestamp createTime, Timestamp lastTime) {
		super(users, groupId, groupType, name, discription, isValid, createTime,
				lastTime);
		// TODO Auto-generated constructor stub
	}
	
}
