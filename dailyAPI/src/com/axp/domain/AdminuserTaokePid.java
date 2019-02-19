package com.axp.domain;

import java.sql.Timestamp;

/**
 * Adver entity. @author MyEclipse Persistence Tools
 */
public class AdminuserTaokePid extends AbstractAdminuserTaokePid implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public AdminuserTaokePid() {
	}

	/** full constructor */
	public AdminuserTaokePid(Integer id,String pid,String pidname,
			 Integer status, Boolean isValid,Timestamp createtime,
			 String tkLoginLoginname,String tkLoginPassword,String tkLoginUsername) {
		super(id, pid, pidname, status, isValid, createtime, tkLoginLoginname, tkLoginPassword, tkLoginUsername);
	}

}
