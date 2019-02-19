package com.axp.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractCommodityType entity provides the base persistence definition of the
 * CommodityType entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractCommodityType implements java.io.Serializable {

	// Fields

	private Integer id;
	private CommodityType commodityType;
	private String name = "";
	private Integer level;
	private Boolean isValid;
	private String imgUrl = "";
	private Timestamp createTime;
	private Set commodityTypes = new HashSet(0);
	private Boolean isLocal;
	


	/*	private Set cashshops = new HashSet(0);*/
	private Integer modelId;
	// Constructors

	/** default constructor */
	public AbstractCommodityType() {
	}

	/** minimal constructor */
	public AbstractCommodityType(String name, Boolean isValid) {
		this.name = name;
		this.isValid = isValid;
	}

	
	public AbstractCommodityType(Integer id, CommodityType commodityType,
			String name, Integer level, Boolean isValid, String imgUrl,
			Timestamp createTime, Set commodityTypes, Integer modelId) {
		super();
		this.id = id;
		this.commodityType = commodityType;
		this.name = name;
		this.level = level;
		this.isValid = isValid;
		this.imgUrl = imgUrl;
		this.createTime = createTime;
		this.commodityTypes = commodityTypes;
		this.modelId = modelId;
	}
	
	
	public Boolean getIsLocal() {
		return isLocal==null?false:isLocal;
	}

	public void setIsLocal(Boolean isLocal) {
		this.isLocal = isLocal;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}



	public void setId(Integer id) {
		this.id = id;
	}

	public CommodityType getCommodityType() {
		return this.commodityType;
	}

	public void setCommodityType(CommodityType commodityType) {
		this.commodityType = commodityType;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsValid() {
		return this.isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public String getImgUrl() {
		return this.imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Set getCommodityTypes() {
		return this.commodityTypes;
	}

	public void setCommodityTypes(Set commodityTypes) {
		this.commodityTypes = commodityTypes;
	}



	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}



}