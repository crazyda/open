package com.axp.domain;

/**
 * AbstractOpenGoods entity provides the base persistence definition of the
 * OpenGoods entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractOpenJDQueryExplosiveGoods2 implements java.io.Serializable {

	// Fields

	private Integer id;
	private String skuId;
	private String skuName;
	private String skuUrl;
	private String shopName;
	private String shopUrl;
	private String imgUrl;
	private String pcPrice;
	private String wlPrice;
	private String exPrice;
	private String couponTab;
	private String couponNote;
	private String discountPrice;
	private String discountRate;
	private String startTime;
	private String endTime;
	private String isLock;
	private String manJianNote;
	private String pcCommission;
	private String pcCommissionShare;
	private String inOrderComm;
	private String inOrderCount;
	private String plan;
	private String isPlan;
	private String prmTab;
	private String realRate;
	private String adowner;
	private String vid;
	private String wlCommission;
	private String wlCommissionShare;
	
	
	
	public AbstractOpenJDQueryExplosiveGoods2() {
		super();
	}
	public AbstractOpenJDQueryExplosiveGoods2(Integer id, String skuId,
			String skuName, String skuUrl, String shopName, String shopUrl,
			String imgUrl, String pcPrice, String wlPrice, String exPrice,
			String couponTab, String couponNote, String discountPrice,
			String discountRate, String startTime, String endTime,
			String isLock, String manJianNote, String pcCommission,
			String pcCommissionShare, String inOrderComm, String inOrderCount,
			String plan, String isPlan, String prmTab, String realRate,
			String adowner, String vid,String wlCommission,String wlCommissionShare) {
		super();
		this.id = id;
		this.skuId = skuId;
		this.skuName = skuName;
		this.skuUrl = skuUrl;
		this.shopName = shopName;
		this.shopUrl = shopUrl;
		this.imgUrl = imgUrl;
		this.pcPrice = pcPrice;
		this.wlPrice = wlPrice;
		this.exPrice = exPrice;
		this.couponTab = couponTab;
		this.couponNote = couponNote;
		this.discountPrice = discountPrice;
		this.discountRate = discountRate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.isLock = isLock;
		this.manJianNote = manJianNote;
		this.pcCommission = pcCommission;
		this.pcCommissionShare = pcCommissionShare;
		this.inOrderComm = inOrderComm;
		this.inOrderCount = inOrderCount;
		this.plan = plan;
		this.isPlan = isPlan;
		this.prmTab = prmTab;
		this.realRate = realRate;
		this.adowner = adowner;
		this.vid = vid;
		this.wlCommission = wlCommission;
		this.wlCommissionShare = wlCommissionShare;
	}
	public String getWlCommissionShare() {
		return wlCommissionShare;
	}
	public void setWlCommissionShare(String wlCommissionShare) {
		this.wlCommissionShare = wlCommissionShare;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getSkuUrl() {
		return skuUrl;
	}
	public void setSkuUrl(String skuUrl) {
		this.skuUrl = skuUrl;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopUrl() {
		return shopUrl;
	}
	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getPcPrice() {
		return pcPrice;
	}
	public void setPcPrice(String pcPrice) {
		this.pcPrice = pcPrice;
	}
	public String getWlPrice() {
		return wlPrice;
	}
	public void setWlPrice(String wlPrice) {
		this.wlPrice = wlPrice;
	}
	public String getExPrice() {
		return exPrice;
	}
	public void setExPrice(String exPrice) {
		this.exPrice = exPrice;
	}
	public String getCouponTab() {
		return couponTab;
	}
	public void setCouponTab(String couponTab) {
		this.couponTab = couponTab;
	}
	public String getCouponNote() {
		return couponNote;
	}
	public void setCouponNote(String couponNote) {
		this.couponNote = couponNote;
	}
	public String getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(String discountPrice) {
		this.discountPrice = discountPrice;
	}
	public String getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(String discountRate) {
		this.discountRate = discountRate;
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
	public String getIsLock() {
		return isLock;
	}
	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}
	public String getManJianNote() {
		return manJianNote;
	}
	public void setManJianNote(String manJianNote) {
		this.manJianNote = manJianNote;
	}
	public String getPcCommission() {
		return pcCommission;
	}
	public void setPcCommission(String pcCommission) {
		this.pcCommission = pcCommission;
	}
	public String getPcCommissionShare() {
		return pcCommissionShare;
	}
	public void setPcCommissionShare(String pcCommissionShare) {
		this.pcCommissionShare = pcCommissionShare;
	}
	public String getInOrderComm() {
		return inOrderComm;
	}
	public void setInOrderComm(String inOrderComm) {
		this.inOrderComm = inOrderComm;
	}
	public String getInOrderCount() {
		return inOrderCount;
	}
	public void setInOrderCount(String inOrderCount) {
		this.inOrderCount = inOrderCount;
	}
	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}
	public String getIsPlan() {
		return isPlan;
	}
	public void setIsPlan(String isPlan) {
		this.isPlan = isPlan;
	}
	public String getPrmTab() {
		return prmTab;
	}
	public void setPrmTab(String prmTab) {
		this.prmTab = prmTab;
	}
	public String getRealRate() {
		return realRate;
	}
	public void setRealRate(String realRate) {
		this.realRate = realRate;
	}
	public String getAdowner() {
		return adowner;
	}
	public void setAdowner(String adowner) {
		this.adowner = adowner;
	}
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
	}
	public String getWlCommission() {
		return wlCommission;
	}
	public void setWlCommission(String wlCommission) {
		this.wlCommission = wlCommission;
	}
	
	

	
	

}