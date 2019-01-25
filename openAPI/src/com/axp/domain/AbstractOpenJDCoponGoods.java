package com.axp.domain;

/**
 * AbstractOpenGoods entity provides the base persistence definition of the
 * OpenGoods entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractOpenJDCoponGoods implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String skuId;
	private String skuName;
	private String pcPrice;
	private String wlPrice;
	private String inOrderCount;
	private String cidName;
	private String isJdSale;
	private String materiaUrl;
	private String commissionShare;
	private String cid3;
	private String wlCommissionShare;
	private String cid2;
	private String cid2Name;
	private String isSeckill;
	private String cid;
	private String cid3Name;
	private String imageurl;
	private String vid;
	private String couponList;
	private String link ;
	private String bindType ;
	private String discount ;
	private String quota ;
	private String platformType ;
	private String beginTime  ;
	private String endTime ;
	private Integer random;
	private Integer classify;
	

	public AbstractOpenJDCoponGoods() {
		super();
	}
	
	public AbstractOpenJDCoponGoods(Integer id, String skuId, String skuName,
			String pcPrice, String wlPrice, String inOrderCount,
			String cidName, String isJdSale, String materiaUrl,
			String commissionShare, String cid3, String wlCommissionShare,
			String cid2, String cid2Name, String isSeckill, String cid,
			String cid3Name, String imageurl, String vid, String couponList,
			String link, String bindType, String discount, String quota,
			String platformType, String beginTime, String endTime,
			Integer random, Integer classify) {
		super();
		this.id = id;
		this.skuId = skuId;
		this.skuName = skuName;
		this.pcPrice = pcPrice;
		this.wlPrice = wlPrice;
		this.inOrderCount = inOrderCount;
		this.cidName = cidName;
		this.isJdSale = isJdSale;
		this.materiaUrl = materiaUrl;
		this.commissionShare = commissionShare;
		this.cid3 = cid3;
		this.wlCommissionShare = wlCommissionShare;
		this.cid2 = cid2;
		this.cid2Name = cid2Name;
		this.isSeckill = isSeckill;
		this.cid = cid;
		this.cid3Name = cid3Name;
		this.imageurl = imageurl;
		this.vid = vid;
		this.couponList = couponList;
		this.link = link;
		this.bindType = bindType;
		this.discount = discount;
		this.quota = quota;
		this.platformType = platformType;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.random = random;
		this.classify = classify;
	}

	public Integer getClassify() {
		return classify;
	}

	public void setClassify(Integer classify) {
		this.classify = classify;
	}

	public Integer getRandom() {
		return random;
	}
	public void setRandom(Integer random) {
		this.random = random;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getBindType() {
		return bindType;
	}
	public void setBindType(String bindType) {
		this.bindType = bindType;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getQuota() {
		return quota;
	}
	public void setQuota(String quota) {
		this.quota = quota;
	}
	public String getPlatformType() {
		return platformType;
	}
	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInOrderCount() {
		return inOrderCount;
	}
	public void setInOrderCount(String inOrderCount) {
		this.inOrderCount = inOrderCount;
	}
	public String getPcPrice() {
		return pcPrice;
	}
	public void setPcPrice(String pcPrice) {
		this.pcPrice = pcPrice;
	}
	public String getCidName() {
		return cidName;
	}
	public void setCidName(String cidName) {
		this.cidName = cidName;
	}
	public String getIsJdSale() {
		return isJdSale;
	}
	public void setIsJdSale(String isJdSale) {
		this.isJdSale = isJdSale;
	}
	public String getMateriaUrl() {
		return materiaUrl;
	}
	public void setMateriaUrl(String materiaUrl) {
		this.materiaUrl = materiaUrl;
	}
	public String getCommissionShare() {
		return commissionShare;
	}
	public void setCommissionShare(String commissionShare) {
		this.commissionShare = commissionShare;
	}
	public String getCid3() {
		return cid3;
	}
	public void setCid3(String cid3) {
		this.cid3 = cid3;
	}
	public String getWlCommissionShare() {
		return wlCommissionShare;
	}
	public void setWlCommissionShare(String wlCommissionShare) {
		this.wlCommissionShare = wlCommissionShare;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public String getCid2() {
		return cid2;
	}
	public void setCid2(String cid2) {
		this.cid2 = cid2;
	}
	public String getCid2Name() {
		return cid2Name;
	}
	public void setCid2Name(String cid2Name) {
		this.cid2Name = cid2Name;
	}
	public String getIsSeckill() {
		return isSeckill;
	}
	public void setIsSeckill(String isSeckill) {
		this.isSeckill = isSeckill;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getWlPrice() {
		return wlPrice;
	}
	public void setWlPrice(String wlPrice) {
		this.wlPrice = wlPrice;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getCid3Name() {
		return cid3Name;
	}
	public void setCid3Name(String cid3Name) {
		this.cid3Name = cid3Name;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
	}
	public String getCouponList() {
		return couponList;
	}
	public void setCouponList(String couponList) {
		this.couponList = couponList;
	}
	
	
	
	
	
}