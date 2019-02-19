package com.axp.domain;

import java.sql.Timestamp;

/**
 * AbstractSellerWithdraw entity provides the base persistence definition of the
 * SellerWithdraw entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractSellerWithdraw implements java.io.Serializable {

	// Fields

	private Integer id;
	private Seller seller;
	private double cashpoint;
	private double money;
	private Integer checkStatus;
	private Integer withdrawStatus;
	private Integer type;
	private String cardCode;
	private Timestamp toAccountDate;
	private String remark;
	private String alipayReason;
	private Timestamp createTime;
	private String alipayInwardId;
	private boolean isValid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public double getCashpoint() {
		return cashpoint;
	}

	public void setCashpoint(double cashpoint) {
		this.cashpoint = cashpoint;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Integer getWithdrawStatus() {
		return withdrawStatus;
	}

	public void setWithdrawStatus(Integer withdrawStatus) {
		this.withdrawStatus = withdrawStatus;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public Timestamp getToAccountDate() {
		return toAccountDate;
	}

	public void setToAccountDate(Timestamp toAccountDate) {
		this.toAccountDate = toAccountDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAlipayReason() {
		return alipayReason;
	}

	public void setAlipayReason(String alipayReason) {
		this.alipayReason = alipayReason;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getAlipayInwardId() {
		return alipayInwardId;
	}

	public void setAlipayInwardId(String alipayInwardId) {
		this.alipayInwardId = alipayInwardId;
	}

	public boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(boolean isValid) {
		this.isValid = isValid;
	}

}