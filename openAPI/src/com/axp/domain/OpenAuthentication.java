package com.axp.domain;

import java.sql.Timestamp;

/**
 * OpenClient entity. @author MyEclipse Persistence Tools
 */
public class OpenAuthentication extends AbstractOpenAuthentication implements
		java.io.Serializable {

	public OpenAuthentication() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OpenAuthentication(Integer id, String userName, String phone,
			String cardNo, Timestamp validityTime, Integer isLong,
			String cardFacade, String cardIdentity, String cardHold,
			Integer type, Integer isThree, String company,
			String organizationCode, String companyAddress, String license,
			String licenseAccount, Users users) {
		super(id, userName, phone, cardNo, validityTime, isLong, cardFacade,
				cardIdentity, cardHold, type, isThree, company, organizationCode,
				companyAddress, license, licenseAccount, users);
		// TODO Auto-generated constructor stub
	}

	
}
