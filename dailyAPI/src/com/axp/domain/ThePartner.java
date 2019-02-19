package com.axp.domain;

import java.sql.Timestamp;


/**
 * ThePartner entity. @author MyEclipse Persistence Tools
 */
public class ThePartner extends AbstractThePartner implements
		java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public ThePartner() {
		super();
		
	}

	/** full constructor */
	public ThePartner(Integer userId, String name, String parnerName,
			String pidId, Timestamp addTime, Boolean isValid) {
		super(userId, name, parnerName, pidId, addTime, isValid);
	}

}
