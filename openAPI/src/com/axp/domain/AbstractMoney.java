package com.axp.domain;

import java.sql.Timestamp;

/**
 * AbstractOpenClient entity provides the base persistence definition of the
 * OpenClient entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractMoney implements java.io.Serializable {

	// Fields

	public Integer id;
	public String clientId;
	public Double money;
	public String bankCard;
	public String bankName;
	public String bankAddress;
	public Timestamp createTime;
	public Integer isPay;//是否已支付
	public Integer isCheck;//是否已审核
	public String payCard;//支付卡号
	public Integer payUsers;//支付人
	public Integer checkUsers;//审核人
	public Timestamp payTime;
	public String name;
	public String remark;
	public String orderId;
	public Integer presentationClass;
	
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Integer getPresentationClass() {
		return presentationClass;
	}
	public void setPresentationClass(Integer presentationClass) {
		this.presentationClass = presentationClass;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public AbstractMoney() {
		super();
	}
	public AbstractMoney(Integer id, String clientId, Double money,
			String bankCard, String bankName, String bankAddress,
			Timestamp createTime, Integer isPay, Integer isCheck, String payCard,
			Integer payUsers, Integer checkUsers, Timestamp payTime,String name) {
		super();
		this.id = id;
		this.clientId = clientId;
		this.money = money;
		this.bankCard = bankCard;
		this.bankName = bankName;
		this.bankAddress = bankAddress;
		this.createTime = createTime;
		this.isPay = isPay;
		this.isCheck = isCheck;
		this.payCard = payCard;
		this.payUsers = payUsers;
		this.checkUsers = checkUsers;
		this.payTime = payTime;
		this.name = name;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPay(Integer isPay) {
		this.isPay = isPay;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAddress() {
		return bankAddress;
	}
	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	public Integer getIsPay() {
		return isPay;
	}
	public void setIsPay(Integer isPay) {
		this.isPay = isPay;
	}
	public Integer getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(Integer isCheck) {
		this.isCheck = isCheck;
	}
	public String getPayCard() {
		return payCard;
	}
	public void setPayCard(String payCard) {
		this.payCard = payCard;
	}
	public Integer getPayUsers() {
		return payUsers;
	}
	public void setPayUsers(Integer payUsers) {
		this.payUsers = payUsers;
	}
	public Integer getCheckUsers() {
		return checkUsers;
	}
	public void setCheckUsers(Integer checkUsers) {
		this.checkUsers = checkUsers;
	}
	public Timestamp getPayTime() {
		return payTime;
	}
	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}
	
	
	
} 