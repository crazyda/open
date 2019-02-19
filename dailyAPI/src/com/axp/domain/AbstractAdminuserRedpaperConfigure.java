package com.axp.domain;

import java.sql.Timestamp;

/**
 * AbstractAdminuserRedpaperConfigure entity provides the base persistence
 * definition of the AdminuserRedpaperConfigure entity. @author MyEclipse
 * Persistence Tools
 */

public abstract class AbstractAdminuserRedpaperConfigure implements
		java.io.Serializable {

	// Fields

	private Integer id;
	private Integer total;
	private Integer intervalTime;
	private String startTime;
	private String endTime;
	private Integer userReceive;
	private Boolean isvalid;
	private Timestamp createtime;

	// Constructors

	/** default constructor */
	public AbstractAdminuserRedpaperConfigure() {
	}

	/** full constructor */
	public AbstractAdminuserRedpaperConfigure(Integer total,
			Integer intervalTime, String startTime, String endTime,
			Integer userReceive, Boolean isvalid, Timestamp createtime) {
		this.total = total;
		this.intervalTime = intervalTime;
		this.startTime = startTime;
		this.endTime = endTime;
		this.userReceive = userReceive;
		this.isvalid = isvalid;
		this.createtime = createtime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTotal() {
		return this.total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getIntervalTime() {
		return this.intervalTime;
	}

	public void setIntervalTime(Integer intervalTime) {
		this.intervalTime = intervalTime;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getUserReceive() {
		return this.userReceive;
	}

	public void setUserReceive(Integer userReceive) {
		this.userReceive = userReceive;
	}

	public Boolean getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(Boolean isvalid) {
		this.isvalid = isvalid;
	}

	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

}