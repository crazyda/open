package com.axp.domain;

/**
 * AbstractReroleRepermission entity provides the base persistence definition of
 * the ReroleRepermission entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractReroleRepermission implements
		java.io.Serializable {

	// Fields

	private ReroleRepermissionId id;

	// Constructors

	/** default constructor */
	public AbstractReroleRepermission() {
	}

	/** full constructor */
	public AbstractReroleRepermission(ReroleRepermissionId id) {
		this.id = id;
	}

	// Property accessors

	public ReroleRepermissionId getId() {
		return this.id;
	}

	public void setId(ReroleRepermissionId id) {
		this.id = id;
	}

}