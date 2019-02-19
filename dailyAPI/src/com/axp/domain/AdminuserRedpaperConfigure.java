package com.axp.domain;

import java.sql.Timestamp;

/**
 * AdminuserRedpaperConfigure entity. @author MyEclipse Persistence Tools
 */
public class AdminuserRedpaperConfigure extends
		AbstractAdminuserRedpaperConfigure implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public AdminuserRedpaperConfigure() {
	}

	/** full constructor */
	public AdminuserRedpaperConfigure(Integer total, Integer intervalTime,
			String startTime, String endTime, Integer userReceive,
			Boolean isvalid, Timestamp createtime) {
		super(total, intervalTime, startTime, endTime, userReceive, isvalid,
				createtime);
	}

}
