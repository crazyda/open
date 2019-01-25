package com.axp.domain;

/**
 * AbstractOpenGoodsCurrent entity provides the base persistence definition of
 * the OpenGoodsCurrent entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractOpenGoodsCurrent implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer openGoods;
	private Integer openIncrementGoods;
	private String orderModifyAt;
	// Constructors

	/** default constructor */
	public AbstractOpenGoodsCurrent() {
	}

	/** minimal constructor */
	public AbstractOpenGoodsCurrent(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractOpenGoodsCurrent(Integer id, Integer openGoods,Integer openIncrementGoods,String orderModifyAt) {
		this.id = id;
		this.openGoods = openGoods;
		this.openIncrementGoods = openIncrementGoods;
		this.orderModifyAt = orderModifyAt;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOpenGoods() {
		return this.openGoods;
	}

	public void setOpenGoods(Integer openGoods) {
		this.openGoods = openGoods;
	}

	public Integer getOpenIncrementGoods() {
		return openIncrementGoods;
	}

	public void setOpenIncrementGoods(Integer openIncrementGoods) {
		this.openIncrementGoods = openIncrementGoods;
	}

	public String getOrderModifyAt() {
		return orderModifyAt;
	}

	public void setOrderModifyAt(String orderModifyAt) {
		this.orderModifyAt = orderModifyAt;
	}

}