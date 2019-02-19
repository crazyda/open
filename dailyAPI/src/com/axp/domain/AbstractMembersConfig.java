package com.axp.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public abstract class AbstractMembersConfig implements java.io.Serializable {

	// Fields

	private Integer id;
	private String typeName;
	private String remark;
	private Double payMoney;
	private Double parentIncome;
	private Double parentSellerIncome;
	private Double cashIncome;
	private Integer award;
	private Timestamp createTime;
	private Boolean isValid;
	private Integer goodsAmount;
	private Set memberses = new HashSet(0);

	// Constructors

	/** default constructor */
	public AbstractMembersConfig() {
	}

	/** full constructor */
	public AbstractMembersConfig(String typeName, Double payMoney, Double parentIncome, Double parentSellerIncome, Double cashIncome,
			Integer award, Timestamp createTime, Boolean isValid, Set memberses) {
		this.typeName = typeName;
		this.payMoney = payMoney;
		this.parentIncome = parentIncome;
		this.parentSellerIncome = parentSellerIncome;
		this.cashIncome = cashIncome;
		this.award = award;
		this.createTime = createTime;
		this.isValid = isValid;
		this.memberses = memberses;
	}

	@JsonProperty("vipLevelId")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@JsonProperty("vipLevelName")
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	@JsonProperty("money")
	public Double getPayMoney() {
		return this.payMoney;
	}

	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}

	@JsonIgnore
	public Double getParentIncome() {
		return parentIncome;
	}

	public void setParentIncome(Double parentIncome) {
		this.parentIncome = parentIncome;
	}

	@JsonIgnore
	public Double getParentSellerIncome() {
		return parentSellerIncome;
	}

	public void setParentSellerIncome(Double parentSellerIncome) {
		this.parentSellerIncome = parentSellerIncome;
	}

	@JsonIgnore
	public Double getCashIncome() {
		return cashIncome;
	}

	public void setCashIncome(Double cashIncome) {
		this.cashIncome = cashIncome;
	}
	@JsonProperty("freeCount")
	public Integer getAward() {
		return this.award;
	}

	public void setAward(Integer award) {
		this.award = award;
	}

	@JsonIgnore
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@JsonIgnore
	public Boolean getIsValid() {
		return this.isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	@JsonIgnore
	public Set getMemberses() {
		return this.memberses;
	}

	public void setMemberses(Set memberses) {
		this.memberses = memberses;
	}
	@JsonIgnore
	public Integer getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(Integer goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}