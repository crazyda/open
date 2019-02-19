package com.axp.domain;

import java.sql.Timestamp;

/**
 * NewRedPaperSetting entity. @author MyEclipse Persistence Tools
 */
public class NewRedPaperSetting extends AbstractNewRedPaperSetting implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public NewRedPaperSetting() {
	}

	/** full constructor */
	public NewRedPaperSetting(AdminUser adminUser, Seller seller,
			String name, Integer rangeid, Double allMoney,
			Double allMoneyUsed, Integer allNum, Integer allNumColl,
			Integer allNumUsed, Integer maxPutout, Double minParvalue,
			Double maxParvalue, Integer limits, Timestamp beginTime,
			Double cyc, Timestamp endTime, String description, String headimg,
			String link, Integer status, Timestamp createTime,
			Timestamp lastTime, Boolean isValid) {
		super(adminUser, seller, name, rangeid, allMoney, allMoneyUsed,
				allNum, allNumColl, allNumUsed, maxPutout, minParvalue,
				maxParvalue, limits, beginTime, cyc, endTime, description,
				headimg, link, status, createTime, lastTime, isValid);
	}

}
