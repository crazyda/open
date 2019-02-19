package com.axp.domain;

import java.sql.Timestamp;

/**
 * AbstractAdminUserMenus entity provides the base persistence definition of the
 * AdminUserMenus entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractAdminUserRecharge implements java.io.Serializable {

	// Fields
	
	//积分
	public static final Integer SCORE=10;

	private Integer id;
	private AdminUser adminuser;
	private Double recharge;
	private String transaction_id;
	private String timeEnd;
	private String openId;
	private String outTradeNo;
	private Integer totalFee;
	private Timestamp createtime;
	private boolean isValid;
	private Integer rechargeType;
	private Integer score;
	private Integer payType;
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public AdminUser getAdminuser() {
		return adminuser;
	}
	public void setAdminuser(AdminUser adminuser) {
		this.adminuser = adminuser;
	}
	public Double getRecharge() {
		return recharge;
	}
	public void setRecharge(Double recharge) {
		this.recharge = recharge;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public Integer getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public boolean getIsValid() {
		return isValid;
	}
	public void setIsValid(boolean isValid) {
		this.isValid = isValid;
	}
	public Integer getRechargeType() {
		return rechargeType;
	}
	public void setRechargeType(Integer rechargeType) {
		this.rechargeType = rechargeType;
	}




}