package com.axp.domain;

import java.sql.Timestamp;

/**
 * AbstractOpenClient entity provides the base persistence definition of the
 * OpenClient entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractOpenAuthentication implements java.io.Serializable {

	// Fields

	public Integer id;
	
	public String userName;
	public String phone;
	public String cardNo;
	public Timestamp validityTime;
	public Integer isLong;
	public String cardFacade;
	public String cardIdentity;
	public String cardHold;
	public Integer type;
	public Integer isThree;
	public String company;
	public String organizationCode;
	public String companyAddress;
	public String license;
	public String licenseAccount;
	public Users users;
	private Integer isAdopt;
	
	
	
	
	
	public Integer getIsAdopt() {
		return isAdopt;
	}
	public void setIsAdopt(Integer isAdopt) {
		this.isAdopt = isAdopt;
	}
	public AbstractOpenAuthentication() {
		super();
	}
	public AbstractOpenAuthentication(Integer id, String userName,
			String phone, String cardNo, Timestamp validityTime, Integer isLong,
			String cardFacade, String cardIdentity, String cardHold,
			Integer type, Integer isThree, String company,
			String organizationCode, String companyAddress, String license,
			String licenseAccount, Users users) {
		super();
		this.id = id;
		this.userName = userName;
		this.phone = phone;
		this.cardNo = cardNo;
		this.validityTime = validityTime;
		this.isLong = isLong;
		this.cardFacade = cardFacade;
		this.cardIdentity = cardIdentity;
		this.cardHold = cardHold;
		this.type = type;
		this.isThree = isThree;
		this.company = company;
		this.organizationCode = organizationCode;
		this.companyAddress = companyAddress;
		this.license = license;
		this.licenseAccount = licenseAccount;
		this.users = users;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public Timestamp getValidityTime() {
		return validityTime;
	}
	public void setValidityTime(Timestamp validityTime) {
		this.validityTime = validityTime;
	}
	public Integer getIsLong() {
		return isLong;
	}
	public void setIsLong(Integer isLong) {
		this.isLong = isLong;
	}
	public String getCardFacade() {
		return cardFacade;
	}
	public void setCardFacade(String cardFacade) {
		this.cardFacade = cardFacade;
	}
	public String getCardIdentity() {
		return cardIdentity;
	}
	public void setCardIdentity(String cardIdentity) {
		this.cardIdentity = cardIdentity;
	}
	public String getCardHold() {
		return cardHold;
	}
	public void setCardHold(String cardHold) {
		this.cardHold = cardHold;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getIsThree() {
		return isThree;
	}
	public void setIsThree(Integer isThree) {
		this.isThree = isThree;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getOrganizationCode() {
		return organizationCode;
	}
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getLicenseAccount() {
		return licenseAccount;
	}
	public void setLicenseAccount(String licenseAccount) {
		this.licenseAccount = licenseAccount;
	}
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	
}