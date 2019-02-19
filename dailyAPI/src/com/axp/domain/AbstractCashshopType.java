package com.axp.domain;

import java.sql.Timestamp;

/**
 * AbstractCashshopType entity provides the base persistence definition of the
 * CashshopType entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractCashshopType implements java.io.Serializable {

	// Fields

	private Integer id;
	private AdminUser adminUser;
	private ImageType imageType;
	private String name = "";
	private String url;
	private String remark;
	private String img = "";
	private String img2 = "";
	private Timestamp createTime;
	private Boolean isValid;
	private CommodityType commodityType;
	private Integer	appVersion;
	// Constructors

	public Integer getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(Integer appVersion) {
		this.appVersion = appVersion;
	}

	/** default constructor */
	public AbstractCashshopType() {
	}

	/** full constructor */
	public AbstractCashshopType(AdminUser adminUser, ImageType imageType,
			String name,String url, String remark, String img, Timestamp createTime,
			Boolean isValid,CommodityType commodityType,Integer appVersion) {
		this.adminUser = adminUser;
		this.imageType = imageType;
		this.name = name;
		this.url = url;
		this.remark = remark;
		this.img = img;
		this.createTime = createTime;
		this.isValid = isValid;
		this.commodityType = commodityType;
		this.appVersion=appVersion;
	}

	// Property accessors

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AdminUser getAdminUser() {
		return this.adminUser;
	}

	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}

	public ImageType getImageType() {
		return this.imageType;
	}

	public void setImageType(ImageType imageType) {
		this.imageType = imageType;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getImg() {
		return this.img;
	}

	public void setImg(String img) {
		this.img = img;
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

	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

	public CommodityType getCommodityType() {
		return commodityType;
	}

	public void setCommodityType(CommodityType commodityType) {
		this.commodityType = commodityType;
	}
	
}