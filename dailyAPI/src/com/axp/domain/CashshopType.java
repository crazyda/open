package com.axp.domain;

import java.sql.Timestamp;

/**
 * CashshopType entity. @author MyEclipse Persistence Tools
 */
public class CashshopType extends AbstractCashshopType implements
		java.io.Serializable {

	public static final int NINE_NINE_MALL = 0x01;
	
	// Constructors

	/** default constructor */
	public CashshopType() {
	}

	public CashshopType(AdminUser adminUser, ImageType imageType, String name,
			String url, String remark, String img, Timestamp createTime,
			Boolean isValid, CommodityType commodityType, Integer appVersion) {
		super(adminUser, imageType, name, url, remark, img, createTime, isValid,
				commodityType, appVersion);
		// TODO Auto-generated constructor stub
	}



}

