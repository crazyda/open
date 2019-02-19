package com.axp.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AbstractRedpaper entity provides the base persistence definition of the
 * Redpaper entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractRedpaper implements java.io.Serializable {

	// Fields

	private Integer id;
	private Double probability;
	private Double minMoney;
	private Double maxMoney;
	private Timestamp createTime;
	private Timestamp lastTime;
	private Boolean isValid;
	private Integer adminUserId;
	private String descript;
	private String title;
	private Integer getMaxTimes;
	private Double spaceTime;
	private Integer maxPresendNumber;
	private Boolean status;
	private Double maxPresendMoney;
	private Set redpaperlogs = new HashSet(0);

	private Integer isOpen;

	// Constructors

	/** default constructor */
	public AbstractRedpaper() {
	}

	/** minimal constructor */
	public AbstractRedpaper(Double probability) {
		this.probability = probability;
	}

	/** full constructor */
	public AbstractRedpaper(Double probability, Double minMoney, Double maxMoney, Timestamp createTime, Timestamp lastTime,
			Boolean isValid, Integer adminUserId, String descript, String title, Integer getMaxTimes, Double spaceTime,
			Integer maxPresendNumber, Boolean status, Double maxPresendMoney, Set redpaperlogs) {
		this.probability = probability;
		this.minMoney = minMoney;
		this.maxMoney = maxMoney;
		this.createTime = createTime;
		this.lastTime = lastTime;
		this.isValid = isValid;
		this.adminUserId = adminUserId;
		this.descript = descript;
		this.title = title;
		this.getMaxTimes = getMaxTimes;
		this.spaceTime = spaceTime;
		this.maxPresendNumber = maxPresendNumber;
		this.status = status;
		this.maxPresendMoney = maxPresendMoney;
		this.redpaperlogs = redpaperlogs;
	}

	@JsonProperty("redpaperId")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getProbability() {
		return this.probability;
	}

	public void setProbability(Double probability) {
		this.probability = probability;
	}

	public Double getMinMoney() {
		return this.minMoney;
	}

	public void setMinMoney(Double minMoney) {
		this.minMoney = minMoney;
	}

	public Double getMaxMoney() {
		return this.maxMoney;
	}

	public void setMaxMoney(Double maxMoney) {
		this.maxMoney = maxMoney;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@JsonIgnore
	public Timestamp getLastTime() {
		return this.lastTime;
	}

	public void setLastTime(Timestamp lastTime) {
		this.lastTime = lastTime;
	}
	@JsonIgnore
	public Boolean getIsValid() {
		return this.isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
	@JsonIgnore
	public Integer getAdminUserId() {
		return this.adminUserId;
	}

	public void setAdminUserId(Integer adminUserId) {
		this.adminUserId = adminUserId;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getGetMaxTimes() {
		return this.getMaxTimes;
	}

	public void setGetMaxTimes(Integer getMaxTimes) {
		this.getMaxTimes = getMaxTimes;
	}

	public Double getSpaceTime() {
		return this.spaceTime;
	}

	public void setSpaceTime(Double spaceTime) {
		this.spaceTime = spaceTime;
	}

	public Integer getMaxPresendNumber() {
		return this.maxPresendNumber;
	}

	public void setMaxPresendNumber(Integer maxPresendNumber) {
		this.maxPresendNumber = maxPresendNumber;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Double getMaxPresendMoney() {
		return this.maxPresendMoney;
	}

	public void setMaxPresendMoney(Double maxPresendMoney) {
		this.maxPresendMoney = maxPresendMoney;
	}

	public Set getRedpaperlogs() {
		return this.redpaperlogs;
	}

	public void setRedpaperlogs(Set redpaperlogs) {
		this.redpaperlogs = redpaperlogs;
	}

	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}

}