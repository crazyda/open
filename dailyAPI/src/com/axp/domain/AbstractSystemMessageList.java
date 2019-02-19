package com.axp.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * AbstractSystemMessageList entity provides the base persistence definition of
 * the SystemMessageList entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractSystemMessageList implements java.io.Serializable {

	// Fields

	private Integer id;
	private String icon;
	private String iconMax;
	private String title;
	private MessageType messageType;
	private String content;
	private Timestamp time;
	private Boolean isValid;
	private Timestamp createtime;
	private Users users;
	private Double money;
	private Integer moneyState;
	private String image;
	// Constructors

	
	/** default constructor */
	public AbstractSystemMessageList() {
	}

	/** full constructor */
	public AbstractSystemMessageList(MessageType messageType, String content,Double money,Integer moneyState,
			Timestamp time,  Boolean isValid, Timestamp createtime,String title,Users users) {
		this.messageType = messageType;
		this.content = content;
		this.time = time;
		this.isValid = isValid;
		this.createtime = createtime;
		this.title = title;
		this.users = users;
		this.moneyState =moneyState;
		this.money = money;
	}

	public String getContent() {
		return this.content;
	}

	public Timestamp getCreatetime() {
		return this.createtime;
	}
	public String getIcon() {
		return icon;
	}

	public String getIconMax() {
		return iconMax;
	}

	public Integer getId() {
		return this.id;
	}

	public String getImage() {
		return image;
	}

	// Property accessors

	public Boolean getIsValid() {
		return this.isValid;
	}

	@JsonIgnore
	public MessageType getMessageType() {
		return this.messageType;
	}
	
	public Double getMoney() {
		return money;
	}

	public Integer getMoneyState() {
		return moneyState;
	}

	public Timestamp getTime() {
		return this.time;
	}

	public String getTitle() {
		return title;
	}

	public Users getUsers() {
		return users;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setIconMax(String iconMax) {
		this.iconMax = iconMax;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public void setMoney(Double money) {
		this.money = money;
	}


	public void setMoneyState(Integer moneyState) {
		this.moneyState = moneyState;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUsers(Users users) {
		this.users = users;
	}
	
}