package com.axp.domain;

/**
 * AbstractOpenClient entity provides the base persistence definition of the
 * OpenClient entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractOpenClassfy implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer categoryId;
	private String categoryName;
	private String orderby;
	
	
	
	
	
	
	public String getOrderby() {
		return orderby;
	}
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
	public AbstractOpenClassfy() {
		super();
	}
	public AbstractOpenClassfy(Integer id, Integer categoryId,
			String categoryName) {
		super();
		this.id = id;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
	


}