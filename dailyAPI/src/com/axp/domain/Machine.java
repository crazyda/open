package com.axp.domain;

/**
 * Machine entity. @author MyEclipse Persistence Tools
 */
public class Machine extends AbstractMachine implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Machine() {
	}

	private String showtypeview;

	public void setShowtypeview(String showtypeview) {
		this.showtypeview = showtypeview;
	}

}
