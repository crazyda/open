package com.axp.domain;

/**
 * ReroleRepermissionId entity. @author MyEclipse Persistence Tools
 */
public class ReroleRepermissionId extends AbstractReroleRepermissionId
		implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public ReroleRepermissionId() {
	}

	/** full constructor */
	public ReroleRepermissionId(ReRole reRole, RePermission rePermission) {
		super(reRole, rePermission);
	}

}
