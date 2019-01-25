package com.axp.domain;

/**
 * OpenGoodsCurrent entity. @author MyEclipse Persistence Tools
 */
public class OpenGoodsCurrent extends AbstractOpenGoodsCurrent implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public OpenGoodsCurrent() {
	}

	/** minimal constructor */
	public OpenGoodsCurrent(Integer id) {
		super(id);
	}

	/** full constructor */
	public OpenGoodsCurrent(Integer id, Integer openGoods,Integer openIncrementGoods,String orderModifyAt) {
		super(id, openGoods, openIncrementGoods, orderModifyAt);
	}

}
