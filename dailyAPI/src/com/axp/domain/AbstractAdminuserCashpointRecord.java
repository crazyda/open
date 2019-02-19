package com.axp.domain;

import java.sql.Timestamp;


/**
 * AbstractAdminuserCashpointRecord entity provides the base persistence
 * definition of the AdminuserCashpointRecord entity. @author MyEclipse
 * Persistence Tools
 */

public abstract class AbstractAdminuserCashpointRecord implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private AdminUser adminUser;
	private Double beforepoint;
	private Double afterpoint;
	private Double cashpoint;
	private Integer type;
	private String cardCode;
	private String remark;
	private Timestamp createTime;
	private Boolean isValid;
	private String tradeNo;
	private ReGoodsorderItem orderItem;
	private Users users;
	//da
	private Integer isDeposit; 
	private Integer goodsId;
	
	// Constructors

	/** default constructor */
	public AbstractAdminuserCashpointRecord() {
	}

	/** full constructor */
	public AbstractAdminuserCashpointRecord(AdminUser adminUser,
			Double beforepoint, Double afterpoint, Double cashpoint,
			Integer type, String cardCode, String remark, Timestamp createTime,
			Boolean isValid, String tradeNo,ReGoodsorderItem orderItem,Users users,Integer isDeposit) {
		this.adminUser = adminUser;
		this.beforepoint = beforepoint;
		this.afterpoint = afterpoint;
		this.cashpoint = cashpoint;
		this.type = type;
		this.cardCode = cardCode;
		this.remark = remark;
		this.createTime = createTime;
		this.isValid = isValid;
		this.tradeNo = tradeNo;
		this.users = users;
		this.orderItem = orderItem;
		this.isDeposit = isDeposit;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	
	
	
	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getIsDeposit() {
		return isDeposit;
	}

	public void setIsDeposit(Integer isDeposit) {
		this.isDeposit = isDeposit;
	}

	public AdminUser getAdminUser() {
		return this.adminUser;
	}

	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}

	public Double getBeforepoint() {
		return this.beforepoint;
	}

	public void setBeforepoint(Double beforepoint) {
		this.beforepoint = beforepoint;
	}

	public Double getAfterpoint() {
		return this.afterpoint;
	}

	public void setAfterpoint(Double afterpoint) {
		this.afterpoint = afterpoint;
	}

	public Double getCashpoint() {
		return this.cashpoint;
	}

	public void setCashpoint(Double cashpoint) {
		this.cashpoint = cashpoint;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCardCode() {
		return this.cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Boolean getIsValid() {
		return this.isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public String getTradeNo() {
		return this.tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public ReGoodsorderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(ReGoodsorderItem orderItem) {
		this.orderItem = orderItem;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

}