package com.axp.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractAdminUser entity provides the base persistence definition of the
 * AdminUser entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractGoodsConcern implements java.io.Serializable {

	private Integer id;
	private String goodsId;
	private Users users;
	private Boolean isfocus;
	public AbstractGoodsConcern() {
	}
	public Boolean getIsfocus() {
		return isfocus;
	}
	public void setIsfocus(Boolean isfocus) {
		this.isfocus = isfocus;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}

}