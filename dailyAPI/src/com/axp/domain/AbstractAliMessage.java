package com.axp.domain;

/**
 * AbstractAliMessage entity provides the base persistence definition of the
 * AliMessage entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractAliMessage implements java.io.Serializable {

	// Fields

	private Integer id;
	private String appKey;
	private String appSecret;
	private String signName;
	private String templateCode;
	private Boolean isValid;
	private String codeName;

	// Constructors

	/** default constructor */
	public AbstractAliMessage() {
	}

	/** full constructor */
	public AbstractAliMessage(String appKey, String appSecret, String signName,
			String templateCode, Boolean isValid,String codeName) {
		this.appKey = appKey;
		this.appSecret = appSecret;
		this.signName = signName;
		this.templateCode = templateCode;
		this.isValid = isValid;
		this.codeName = codeName;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppKey() {
		return this.appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return this.appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getSignName() {
		return this.signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public String getTemplateCode() {
		return this.templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public Boolean getIsValid() {
		return this.isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	

}