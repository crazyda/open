package com.axp.domain;

/**
 * OpenClient entity. @author MyEclipse Persistence Tools
 */
public class OpenClient extends AbstractOpenClient implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public OpenClient() {
	}

	/** minimal constructor */
	public OpenClient(Integer id) {
		super(id);
	}

	/** full constructor */
	public OpenClient(Integer id, String clientId, String clientSecret,
			Boolean isvalid,String appId) {
		super(id, clientId, clientSecret, isvalid,appId);
	}

}
