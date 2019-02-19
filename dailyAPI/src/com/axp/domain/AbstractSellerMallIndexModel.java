package com.axp.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 首页模板类：
 * 此类用装载首页模板中需要的各种信息；
 */
public abstract class AbstractSellerMallIndexModel implements java.io.Serializable {
	private static final long serialVersionUID = 8486002949850145209L;

	private Integer id;
	private Boolean isValid;
	private Timestamp createTime;

	private Integer modelType = 1; // 模板类型；
	private SellerMallPicture background; // 背景图片；
	private Set<SellerMallPicture> sellerFivePictures = new HashSet<SellerMallPicture>();// 商城首页的五张展示图片；
	private List<SellerMallPicture> classifyPictures = new ArrayList<SellerMallPicture>(); // 分类图片；
	private Set<SellerMallPicture> goodsPictures = new HashSet<SellerMallPicture>();// 商品图片；
	private Set<SellerMallModelModule> modules = new HashSet<SellerMallModelModule>();// 首页中包含的分类模块；

	// 构造函数；
	public AbstractSellerMallIndexModel() {
	}

	// =====================getAndSet=============================
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getModelType() {
		return modelType;
	}

	public void setModelType(Integer modelType) {
		this.modelType = modelType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SellerMallPicture getBackground() {
		return background;
	}

	public void setBackground(SellerMallPicture background) {
		this.background = background;
	}

	public Set<SellerMallPicture> getSellerFivePictures() {
		return sellerFivePictures;
	}

	public void setSellerFivePictures(Set<SellerMallPicture> sellerFivePictures) {
		this.sellerFivePictures = sellerFivePictures;
	}

	public List<SellerMallPicture> getClassifyPictures() {
		return classifyPictures;
	}

	public void setClassifyPictures(List<SellerMallPicture> classifyPictures) {
		this.classifyPictures = classifyPictures;
	}

	public Set<SellerMallPicture> getGoodsPictures() {
		return goodsPictures;
	}

	public void setGoodsPictures(Set<SellerMallPicture> goodsPictures) {
		this.goodsPictures = goodsPictures;
	}

	public Set<SellerMallModelModule> getModules() {
		return modules;
	}

	public void setModules(Set<SellerMallModelModule> modules) {
		this.modules = modules;
	}

}