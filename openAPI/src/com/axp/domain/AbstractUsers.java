package com.axp.domain;

import java.sql.Timestamp;

/**
 * AbstractOpenClient entity provides the base persistence definition of the
 * OpenClient entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractUsers implements java.io.Serializable {

	// Fields

	public Integer id;
	public String name;
	public String password;
	public String code;//邀请码
	public Timestamp codeTime;//有效时间
	public Integer application;//应用数
	public Timestamp lastTime ; //
	public Integer level;//等级 1 注册用户, 10 管理员
	
	
	
	public AbstractUsers() {
		super();
	}



	public AbstractUsers(Integer id, String name, String password, String code,
			Timestamp codeTime, Integer application, Timestamp lastTime, Integer level) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.code = code;
		this.codeTime = codeTime;
		this.application = application;
		this.lastTime = lastTime;
		this.level = level;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public Timestamp getCodeTime() {
		return codeTime;
	}



	public void setCodeTime(Timestamp codeTime) {
		this.codeTime = codeTime;
	}



	public Integer getApplication() {
		return application;
	}



	public void setApplication(Integer application) {
		this.application = application;
	}



	public Timestamp getLastTime() {
		return lastTime;
	}



	public void setLastTime(Timestamp lastTime) {
		this.lastTime = lastTime;
	}



	public Integer getLevel() {
		return level;
	}



	public void setLevel(Integer level) {
		this.level = level;
	}
	
	
	
}