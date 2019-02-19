package com.axp.domain;

import java.sql.Timestamp;

/**
 * AbstractUserOrderMessage entity provides the base persistence definition of
 * the UserOrderMessage entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractUserOrderMessage implements java.io.Serializable {

	// Fields

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	private Integer id;
	private OrderMessageList orderMessageList;
	private Integer adminuserId;
	private Integer userId;
	private Integer isRead;
	private Timestamp readtime;
	private Boolean isValid;
	private Timestamp createtime;
	private Users users;
	private MessageType messageType;
	private AdminUser adminUser;

	// Constructors

	/** default constructor */
	public AbstractUserOrderMessage() {
	}

	/** full constructor */
	public AbstractUserOrderMessage(OrderMessageList orderMessageList,
			Integer userId, Integer isRead, Timestamp readtime,Integer adminuserId,
			 Boolean isValid, Timestamp createtime) {
		this.orderMessageList = orderMessageList;
		this.userId = userId;
		this.isRead = isRead;
		this.readtime = readtime;
		this.isValid = isValid;
		this.createtime = createtime;
		this.adminuserId = adminuserId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public OrderMessageList getOrderMessageList() {
		return this.orderMessageList;
	}

	public void setOrderMessageList(OrderMessageList orderMessageList) {
		this.orderMessageList = orderMessageList;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getIsRead() {
		return this.isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public Timestamp getReadtime() {
		return this.readtime;
	}

	public void setReadtime(Timestamp readtime) {
		this.readtime = readtime;
	}

	public Boolean getIsValid() {
		return this.isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public Integer getAdminuserId() {
		return adminuserId;
	}

	public void setAdminuserId(Integer adminuserId) {
		this.adminuserId = adminuserId;
	}

	public AdminUser getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}
	

}