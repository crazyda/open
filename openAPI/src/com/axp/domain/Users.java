package com.axp.domain;

import java.sql.Timestamp;

/**
 * OpenClient entity. @author MyEclipse Persistence Tools
 */
public class Users extends AbstractUsers implements
		java.io.Serializable {

	
	
	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Users(Integer id, String name, String password, String code,
			Timestamp codeTime, Integer application, Timestamp lastTime, Integer level) {
		super(id, name, password, code, codeTime, application, lastTime, level);
		// TODO Auto-generated constructor stub
	}

	// Constructors
	

}
