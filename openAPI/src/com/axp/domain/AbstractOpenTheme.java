package com.axp.domain;

/**
 * AbstractOpenClient entity provides the base persistence definition of the
 * OpenClient entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractOpenTheme implements java.io.Serializable {

	private Integer id;
	private String imageUrl;
	private String name;
	private String goodsNum;
	private String themeId;
	
	
	
	
	
	
	public AbstractOpenTheme() {
		super();
	}
	public AbstractOpenTheme(Integer id, String imageUrl, String name,
			String goodsNum, String themeId) {
		super();
		this.id = id;
		this.imageUrl = imageUrl;
		this.name = name;
		this.goodsNum = goodsNum;
		this.themeId = themeId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(String goodsNum) {
		this.goodsNum = goodsNum;
	}
	public String getThemeId() {
		return themeId;
	}
	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}
	
	
}