package com.axp.domain;

/**
 * AbstractOpenGoods entity provides the base persistence definition of the
 * OpenGoods entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractOpenJDGoods2 implements java.io.Serializable {
	//拼购商品 信息
	public Integer id;
	public Integer comments;//商品评价总数
	public String commissionshare;//最高佣金比例
	public String startTime;//通用计划开始时间
	public String endTime;//通用计划技术时间
	public String goodCommentsShare;//商品好评率
	public String imageUrl;//商品url
	public Integer inOrderCount30Days;//30天引单量
	public String pingouPrice;//拼团价格
	public Integer pingouTmCount;//拼购成团所需人数
	public String pingouUrl ;//拼购落地页url
	public String skuId; //商品id
	public String skuName;//商品名称
	public String wlCommissionShare;//无线佣金比例
	public String wlPrice;//单价买 无线价格
	
	
	
	
	
	
	
	
	
	
	public AbstractOpenJDGoods2() {
		super();
	}
	public AbstractOpenJDGoods2(Integer id,Integer comments, String commissionshare,
			String startTime, String endTime, String goodCommentsShare,
			String imageUrl, Integer inOrderCount30Days, String pingouPrice,
			Integer pingouTmCount, String pingouUrl, String skuId,
			String skuName, String wlCommissionShare, String wlPrice) {
		super();
		this.id = id;
		this.comments = comments;
		this.commissionshare = commissionshare;
		this.startTime = startTime;
		this.endTime = endTime;
		this.goodCommentsShare = goodCommentsShare;
		this.imageUrl = imageUrl;
		this.inOrderCount30Days = inOrderCount30Days;
		this.pingouPrice = pingouPrice;
		this.pingouTmCount = pingouTmCount;
		this.pingouUrl = pingouUrl;
		this.skuId = skuId;
		this.skuName = skuName;
		this.wlCommissionShare = wlCommissionShare;
		this.wlPrice = wlPrice;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getComments() {
		return comments;
	}
	public void setComments(Integer comments) {
		this.comments = comments;
	}
	public String getCommissionshare() {
		return commissionshare;
	}
	public void setCommissionshare(String commissionshare) {
		this.commissionshare = commissionshare;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getGoodCommentsShare() {
		return goodCommentsShare;
	}
	public void setGoodCommentsShare(String goodCommentsShare) {
		this.goodCommentsShare = goodCommentsShare;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Integer getInOrderCount30Days() {
		return inOrderCount30Days;
	}
	public void setInOrderCount30Days(Integer inOrderCount30Days) {
		this.inOrderCount30Days = inOrderCount30Days;
	}
	public String getPingouPrice() {
		return pingouPrice;
	}
	public void setPingouPrice(String pingouPrice) {
		this.pingouPrice = pingouPrice;
	}
	public Integer getPingouTmCount() {
		return pingouTmCount;
	}
	public void setPingouTmCount(Integer pingouTmCount) {
		this.pingouTmCount = pingouTmCount;
	}
	public String getPingouUrl() {
		return pingouUrl;
	}
	public void setPingouUrl(String pingouUrl) {
		this.pingouUrl = pingouUrl;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public String getWlCommissionShare() {
		return wlCommissionShare;
	}
	public void setWlCommissionShare(String wlCommissionShare) {
		this.wlCommissionShare = wlCommissionShare;
	}
	public String getWlPrice() {
		return wlPrice;
	}
	public void setWlPrice(String wlPrice) {
		this.wlPrice = wlPrice;
	}
	
	
	
	
	
	
}