package com.axp.domain;

/**
 * AbstractOpenClient entity provides the base persistence definition of the
 * OpenClient entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractAgent implements java.io.Serializable {

	// Fields

	private Integer id;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public AbstractAgent() {
		super();
	}

	/** full constructor */
	public AbstractAgent(Integer id) {
		this.id = id;
		
	}


}