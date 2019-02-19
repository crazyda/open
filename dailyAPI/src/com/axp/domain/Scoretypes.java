package com.axp.domain;

/**
 * Scoretypes entity. @author MyEclipse Persistence Tools
 */
public class Scoretypes extends AbstractScoretypes implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public Scoretypes() {
	}

	/** full constructor */
	public Scoretypes(Integer score, String name, Boolean isvalid) {
		super(score, name, isvalid);
	}

}
