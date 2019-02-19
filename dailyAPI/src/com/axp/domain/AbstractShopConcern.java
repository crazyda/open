package com.axp.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractAdminUser entity provides the base persistence definition of the
 * AdminUser entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractShopConcern implements java.io.Serializable {

	private Integer id;
	private Seller seller;
	private Users users;
	private Boolean isfocus;
	
	public AbstractShopConcern() {
	}

	public AbstractShopConcern(Integer id, Seller seller, Users users,Boolean isfocus) {
		this.id = id;
		this.seller = seller;
		this.users = users;
		this.isfocus = isfocus;
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

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}
	
	
	
}