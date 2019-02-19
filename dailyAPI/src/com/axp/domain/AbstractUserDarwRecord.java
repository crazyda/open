/**
 * 2018-10-17
 * Administrator
 */
package com.axp.domain;

import java.sql.Timestamp;


/**
 * @author da
 * @data 2018-10-17下午5:10:12
 */
public abstract class AbstractUserDarwRecord implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Boolean isvalid;
	private Users users;
	private GameActivity gameActivity;
	private Timestamp createTime;
	public AbstractUserDarwRecord() {
		super();
	}
	public AbstractUserDarwRecord(Integer id, Boolean isvalid, Users users,
			GameActivity gameActivity, Timestamp createTime) {
		super();
		this.id = id;
		this.isvalid = isvalid;
		this.users = users;
		this.gameActivity = gameActivity;
		this.createTime = createTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Boolean getIsvalid() {
		return isvalid;
	}
	public void setIsvalid(Boolean isvalid) {
		this.isvalid = isvalid;
	}
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	public GameActivity getGameActivity() {
		return gameActivity;
	}
	public void setGameActivity(GameActivity gameActivity) {
		this.gameActivity = gameActivity;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	








}
