package com.axp.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractAdminUser entity provides the base persistence definition of the
 * AdminUser entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractAdminuserZoneTaoke implements java.io.Serializable {

	// Fields
	private ProvinceEnum provinceEnum;
	private Integer id;
	
	private String uri;

	private Timestamp createtime;
	
	private Boolean isValid;
	
	private String bak_uri;
	
	

	// Constructors

	public String getBak_uri() {
		return bak_uri;
	}



	public void setBak_uri(String bak_uri) {
		this.bak_uri = bak_uri;
	}



	/** default constructor */
	public AbstractAdminuserZoneTaoke() {
	}



	public ProvinceEnum getProvinceEnum() {
		return provinceEnum;
	}



	public void setProvinceEnum(ProvinceEnum provinceEnum) {
		this.provinceEnum = provinceEnum;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getUri() {
		return uri;
	}



	public void setUri(String uri) {
		this.uri = uri;
	}



	public Timestamp getCreatetime() {
		return createtime;
	}



	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}



	public Boolean getIsValid() {
		return isValid;
	}



	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}





	

	
	
}