package com.axp.domain;

import java.sql.Timestamp;

/**
 * AbstractOpenClient entity provides the base persistence definition of the
 * OpenClient entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractPidRelation implements java.io.Serializable {

	// Fields
	public Integer id;
	public String pddPid;
	public String pddUpPid;
	public Timestamp createTime;
	
	
	public AbstractPidRelation() {
		super();
	}


	public AbstractPidRelation(Integer id, String pddPid, String pddUpPid,
			Timestamp createTime) {
		super();
		this.id = id;
		this.pddPid = pddPid;
		this.pddUpPid = pddUpPid;
		this.createTime = createTime;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getPddPid() {
		return pddPid;
	}


	public void setPddPid(String pddPid) {
		this.pddPid = pddPid;
	}


	public String getPddUpPid() {
		return pddUpPid;
	}


	public void setPddUpPid(String pddUpPid) {
		this.pddUpPid = pddUpPid;
	}


	public Timestamp getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	
	
	


}