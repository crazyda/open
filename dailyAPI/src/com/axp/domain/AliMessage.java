package com.axp.domain;

/**
 * AliMessage entity. @author MyEclipse Persistence Tools
 */
public class AliMessage extends AbstractAliMessage implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public AliMessage() {
	}

	/** full constructor */
	public AliMessage(String appKey, String appSecret, String signName,
			String templateCode, Boolean isValid,String codeName) {
		super(appKey, appSecret, signName, templateCode, isValid,codeName);
	}

}
