package com.axp.domain;

import java.sql.Timestamp;


/**
 * AbstractAdminuserCashpointRecord entity provides the base persistence
 * definition of the AdminuserCashpointRecord entity. @author MyEclipse
 * Persistence Tools
 */

public abstract class AbstractUsersMoneyRecord implements
		java.io.Serializable {

	// Fields

	private Integer id;
	private Users users;
	private Double beforeMoney;
	private Double money;
	private Double afterMoney;
	private Integer type;
	private String remark;
	private Timestamp createTime;
	private String relateName;
	private Boolean isValid;
	private String orderString;
	private String accountType;
	private String account;
	private ReGoodsorderItem orderItem;
	private Users fromUsers;

	// Constructors

	/** default constructor */
	public AbstractUsersMoneyRecord() {
	}

	/** full constructor */

	public AbstractUsersMoneyRecord(Integer id, Users users,
			Double beforeMoney, Double money, Double afterMoney, Integer type,
			String remark, Timestamp createTime,
			String relateName, Boolean isValid, String orderString,
			String accountType, String account, ReGoodsorderItem orderItem,
			Users fromUsers) {
		this.id = id;
		this.users = users;
		this.beforeMoney = beforeMoney;
		this.money = money;
		this.afterMoney = afterMoney;
		this.type = type;
		this.remark = remark;
		this.createTime = createTime;
		this.relateName = relateName;
		this.isValid = isValid;
		this.orderString = orderString;
		this.accountType = accountType;
		this.account = account;
		this.orderItem = orderItem;
		this.fromUsers = fromUsers;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Double getBeforeMoney() {
		return beforeMoney;
	}

	public void setBeforeMoney(Double beforeMoney) {
		this.beforeMoney = beforeMoney;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getAfterMoney() {
		return afterMoney;
	}

	public void setAfterMoney(Double afterMoney) {
		this.afterMoney = afterMoney;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getRelateName() {
		return relateName;
	}

	public void setRelateName(String relateName) {
		this.relateName = relateName;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public ReGoodsorderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(ReGoodsorderItem orderItem) {
		this.orderItem = orderItem;
	}

	public Users getFromUsers() {
		return fromUsers;
	}

	public void setFromUsers(Users fromUsers) {
		this.fromUsers = fromUsers;
	}

	public String getOrderString() {
		return orderString;
	}

	public void setOrderString(String orderString) {
		this.orderString = orderString;
	}

	
}