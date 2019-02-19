package com.axp.domain;

import java.sql.Timestamp;

/**
 * AdminuserRedpaper entity. @author MyEclipse Persistence Tools
 */
public class AdminuserRedpaper extends AbstractAdminuserRedpaper implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public AdminuserRedpaper() {
	}

	/** full constructor */
	public AdminuserRedpaper(AdminUser adminUser, String name,
			Double totalMoney, Integer totalQuantity, String reamrk,
			Double surplusMoney, Integer surplusQuantity, Boolean isvalid,
			Timestamp creattime, Integer type) {
		super(adminUser, name, totalMoney, totalQuantity, reamrk, surplusMoney,
				surplusQuantity, isvalid, creattime, type);
	}

}
