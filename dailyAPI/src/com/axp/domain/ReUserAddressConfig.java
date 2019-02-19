package com.axp.domain;

import java.sql.Timestamp;

/**
 * ReUserAddressConfig entity. @author MyEclipse Persistence Tools
 */

public class ReUserAddressConfig implements java.io.Serializable {

	// Fields

	private Integer id;
	private Users user;
	private Timestamp createTime;
	private String name;
	private String phone;
	private String province;
	private String city;
	private String district;
	private String detailedAddress;
	private Boolean isDefaul;
	private Boolean isValid;

	// Constructors

	/** default constructor */
	public ReUserAddressConfig() {
	}

	/** full constructor */
	public ReUserAddressConfig(Users user, Timestamp createTime,
			String name, String phone, String province, String city,
			String district, String detailedAddress, Boolean isDefaul,
			Boolean isValid) {
		this.user = user;
		this.createTime = createTime;
		this.name = name;
		this.phone = phone;
		this.province = province;
		this.city = city;
		this.district = district;
		this.detailedAddress = detailedAddress;
		this.isDefaul = isDefaul;
		this.isValid = isValid;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getDetailedAddress() {
		return this.detailedAddress;
	}

	public void setDetailedAddress(String detailedAddress) {
		this.detailedAddress = detailedAddress;
	}

	public Boolean getIsDefaul() {
		return this.isDefaul;
	}

	public void setIsDefaul(Boolean isDefaul) {
		this.isDefaul = isDefaul;
	}

	public Boolean getIsValid() {
		return this.isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

}