package com.axp.domain;

/**
 * AbstractOpenClient entity provides the base persistence definition of the
 * OpenClient entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractOpenPddPromotionUrl implements java.io.Serializable {

	private Integer id;
	private String pid;
	private String goodsId;
	private String goodsName;
	private String mobileShortUrl;//唤醒端连接
	private String mobileUrl;//唤醒长连接
	private String shortUrl;//短连接
	private String url;//长连接
	
	
	
	
	
	
	public AbstractOpenPddPromotionUrl() {
		super();
	}
	public AbstractOpenPddPromotionUrl(Integer id, String pid, String goodsId,
			String goodsName, String mobileShortUrl, String mobileUrl,
			String shortUrl, String url) {
		super();
		this.id = id;
		this.pid = pid;
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.mobileShortUrl = mobileShortUrl;
		this.mobileUrl = mobileUrl;
		this.shortUrl = shortUrl;
		this.url = url;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getMobileShortUrl() {
		return mobileShortUrl;
	}
	public void setMobileShortUrl(String mobileShortUrl) {
		this.mobileShortUrl = mobileShortUrl;
	}
	public String getMobileUrl() {
		return mobileUrl;
	}
	public void setMobileUrl(String mobileUrl) {
		this.mobileUrl = mobileUrl;
	}
	public String getShortUrl() {
		return shortUrl;
	}
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}