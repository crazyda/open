package com.axp.domain;

/**
 * AbstractOpenClient entity provides the base persistence definition of the
 * OpenClient entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractOpenClient implements java.io.Serializable {

	// Fields

	private Integer id;
	private String clientId;
	private String clientSecret;
	private Boolean isvalid;
	private String appId;
	

	// Constructors

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	/** default constructor */
	public AbstractOpenClient() {
	}

	/** minimal constructor */
	public AbstractOpenClient(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractOpenClient(Integer id, String clientId, String clientSecret,
			Boolean isvalid,String appId) {
		this.id = id;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.isvalid = isvalid;
		this.appId = appId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return this.clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public Boolean getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(Boolean isvalid) {
		this.isvalid = isvalid;
	}

}