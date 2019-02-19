package com.axp.domain;

import java.sql.Timestamp;

/**
 * AbstractAdminuserRedpaperUsersReceive entity provides the base persistence
 * definition of the AdminuserRedpaperUsersReceive entity. @author MyEclipse
 * Persistence Tools
 */

public abstract class AbstractAdminuserRedpaperUsersReceive implements
		java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userid;
	private AdminuserRedpaper redpaper;
	public AdminuserRedpaper getRedpaper() {
		return redpaper;
	}

	public void setRedpaper(AdminuserRedpaper redpaper) {
		this.redpaper = redpaper;
	}

	private Timestamp createtime;
	private Boolean isvalid;
	private Integer zoneid;
	private Double money;
	
	// Constructors

	public Double getMoney() {
		return money;
	}

	

	public AbstractAdminuserRedpaperUsersReceive(Integer id, Integer userid,
			AdminuserRedpaper redpaper, Timestamp createtime, Boolean isvalid,
			Integer zoneid, Double money) {
		super();
		this.id = id;
		this.userid = userid;
		this.redpaper = redpaper;
		this.createtime = createtime;
		this.isvalid = isvalid;
		this.zoneid = zoneid;
		this.money = money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getZoneid() {
		return zoneid;
	}

	public void setZoneid(Integer zoneid) {
		this.zoneid = zoneid;
	}

	/** default constructor */
	public AbstractAdminuserRedpaperUsersReceive() {
	}

	/** minimal constructor */
	public AbstractAdminuserRedpaperUsersReceive(Integer id) {
		this.id = id;
	}

	

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}


	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public Boolean getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(Boolean isvalid) {
		this.isvalid = isvalid;
	}

	@Override
	public String toString() {
		return "AbstractAdminuserRedpaperUsersReceive [id=" + id + ", userid="
				+ userid + ", redpaper=" + redpaper + ", createtime="
				+ createtime + ", isvalid=" + isvalid + ", zoneid=" + zoneid
				+ ", money=" + money + "]";
	}
}