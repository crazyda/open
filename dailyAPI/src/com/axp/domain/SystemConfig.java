package com.axp.domain;

/**
 * SystemConfig entity. @author MyEclipse Persistence Tools
 */

public class SystemConfig implements java.io.Serializable {

	// Fields

	private Integer id;
	private String parameter;
	private Integer verson;
	//---da
	private String pddOrderModifyAt;//拼多多商品同步最后的时间
	public static String SYS_PARAMETER_zone = "zone";//城市参数
	// Constructors

	/** default constructor */
	public SystemConfig() {
	}

	/** full constructor */
	public SystemConfig(String parameter, Integer verson , String pddOrderModifyAt) {
		this.parameter = parameter;
		this.verson = verson;
		this.pddOrderModifyAt = pddOrderModifyAt;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public String getPddOrderModifyAt() {
		return pddOrderModifyAt;
	}

	public void setPddOrderModifyAt(String pddOrderModifyAt) {
		this.pddOrderModifyAt = pddOrderModifyAt;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getParameter() {
		return this.parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public Integer getVerson() {
		return this.verson;
	}

	public void setVerson(Integer verson) {
		this.verson = verson;
	}

}