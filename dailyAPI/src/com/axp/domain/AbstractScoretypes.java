package com.axp.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractScoretypes entity provides the base persistence definition of the
 * Scoretypes entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractScoretypes implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer score;
	private String name;
	private Boolean isvalid;
	private Set userScoretypeses = new HashSet(0);

	// Constructors

	/** default constructor */
	public AbstractScoretypes() {
	}

	/** minimal constructor */
	public AbstractScoretypes(Integer score, String name, Boolean isvalid) {
		this.score = score;
		this.name = name;
		this.isvalid = isvalid;
	}

	/** full constructor */
	public AbstractScoretypes(Integer score, String name, Boolean isvalid,
			Set userScoretypeses) {
		this.score = score;
		this.name = name;
		this.isvalid = isvalid;
		this.userScoretypeses = userScoretypeses;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(Boolean isvalid) {
		this.isvalid = isvalid;
	}

	public Set getUserScoretypeses() {
		return this.userScoretypeses;
	}

	public void setUserScoretypeses(Set userScoretypeses) {
		this.userScoretypeses = userScoretypeses;
	}

}