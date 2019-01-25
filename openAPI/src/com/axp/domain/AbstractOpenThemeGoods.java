package com.axp.domain;

/**
 * AbstractOpenGoods entity provides the base persistence definition of the
 * OpenGoods entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractOpenThemeGoods implements java.io.Serializable {
	
	private Integer id;
	private String goodsEvalCount;
	private String goodsEvalScore;
	private String promotionRate;
	private String couponEndTime;
	private String couponStartTime;
	private String couponRemainQuantity;
	private String couponTotalQuantity;
	private String couponDiscount;
	private String couponMinOrderAmount;
	private String goodsDesc;
	private String mallName;
	private String minNormalPrice;
	private String minGroupPrice;
	private String soldQuantity;
	private String goodsImageUrl;
	private String goodsThumbnailUrl;
	private String goodsName;
	private String goodsId;
	private String goodsGalleryUrls;
	private String optName;
	private String catId;
	private String catIds;
	private String hasCoupon;
	private String categoryId;
	private String avgServ;
	private String optId;
	private String avgLgst;
	private String categoryName;
	private String aveDesc;
	private String themeId;
	
	
	public AbstractOpenThemeGoods() {
		super();
	}
	
	public AbstractOpenThemeGoods(Integer id, String goodsEvalCount,
			String goodsEvalScore, String promotionRate, String couponEndTime,
			String couponStartTime, String couponRemainQuantity,
			String couponTotalQuantity, String couponDiscount,
			String couponMinOrderAmount, String goodsDesc, String mallName,
			String minNormalPrice, String minGroupPrice, String soldQuantity,
			String goodsImageUrl, String goodsThumbnailUrl, String goodsName,
			String goodsId, String goodsGalleryUrls, String optName,
			String catId, String catIds, String hasCoupon, String categoryId,
			String avgServ, String optId, String avgLgst, String categoryName,
			String aveDesc,String themeId) {
		super();
		this.id = id;
		this.goodsEvalCount = goodsEvalCount;
		this.goodsEvalScore = goodsEvalScore;
		this.promotionRate = promotionRate;
		this.couponEndTime = couponEndTime;
		this.couponStartTime = couponStartTime;
		this.couponRemainQuantity = couponRemainQuantity;
		this.couponTotalQuantity = couponTotalQuantity;
		this.couponDiscount = couponDiscount;
		this.couponMinOrderAmount = couponMinOrderAmount;
		this.goodsDesc = goodsDesc;
		this.mallName = mallName;
		this.minNormalPrice = minNormalPrice;
		this.minGroupPrice = minGroupPrice;
		this.soldQuantity = soldQuantity;
		this.goodsImageUrl = goodsImageUrl;
		this.goodsThumbnailUrl = goodsThumbnailUrl;
		this.goodsName = goodsName;
		this.goodsId = goodsId;
		this.goodsGalleryUrls = goodsGalleryUrls;
		this.optName = optName;
		this.catId = catId;
		this.catIds = catIds;
		this.hasCoupon = hasCoupon;
		this.categoryId = categoryId;
		this.avgServ = avgServ;
		this.optId = optId;
		this.avgLgst = avgLgst;
		this.categoryName = categoryName;
		this.aveDesc = aveDesc;
		this.themeId = themeId;
	}

	public String getThemeId() {
		return themeId;
	}

	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}

	public String getGoodsGalleryUrls() {
		return goodsGalleryUrls;
	}

	public void setGoodsGalleryUrls(String goodsGalleryUrls) {
		this.goodsGalleryUrls = goodsGalleryUrls;
	}

	public String getOptName() {
		return optName;
	}

	public void setOptName(String optName) {
		this.optName = optName;
	}

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public String getCatIds() {
		return catIds;
	}

	public void setCatIds(String catIds) {
		this.catIds = catIds;
	}

	public String getHasCoupon() {
		return hasCoupon;
	}

	public void setHasCoupon(String hasCoupon) {
		this.hasCoupon = hasCoupon;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getAvgServ() {
		return avgServ;
	}

	public void setAvgServ(String avgServ) {
		this.avgServ = avgServ;
	}

	public String getOptId() {
		return optId;
	}

	public void setOptId(String optId) {
		this.optId = optId;
	}

	public String getAvgLgst() {
		return avgLgst;
	}

	public void setAvgLgst(String avgLgst) {
		this.avgLgst = avgLgst;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getAveDesc() {
		return aveDesc;
	}

	public void setAveDesc(String aveDesc) {
		this.aveDesc = aveDesc;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGoodsEvalCount() {
		return goodsEvalCount;
	}
	public void setGoodsEvalCount(String goodsEvalCount) {
		this.goodsEvalCount = goodsEvalCount;
	}
	public String getGoodsEvalScore() {
		return goodsEvalScore;
	}
	public void setGoodsEvalScore(String goodsEvalScore) {
		this.goodsEvalScore = goodsEvalScore;
	}
	public String getPromotionRate() {
		return promotionRate;
	}
	public void setPromotionRate(String promotionRate) {
		this.promotionRate = promotionRate;
	}
	public String getCouponEndTime() {
		return couponEndTime;
	}
	public void setCouponEndTime(String couponEndTime) {
		this.couponEndTime = couponEndTime;
	}
	public String getCouponStartTime() {
		return couponStartTime;
	}
	public void setCouponStartTime(String couponStartTime) {
		this.couponStartTime = couponStartTime;
	}
	

	public String getCouponRemainQuantity() {
		return couponRemainQuantity;
	}

	public void setCouponRemainQuantity(String couponRemainQuantity) {
		this.couponRemainQuantity = couponRemainQuantity;
	}

	public String getCouponTotalQuantity() {
		return couponTotalQuantity;
	}
	public void setCouponTotalQuantity(String couponTotalQuantity) {
		this.couponTotalQuantity = couponTotalQuantity;
	}
	public String getCouponDiscount() {
		return couponDiscount;
	}
	public void setCouponDiscount(String couponDiscount) {
		this.couponDiscount = couponDiscount;
	}
	public String getCouponMinOrderAmount() {
		return couponMinOrderAmount;
	}
	public void setCouponMinOrderAmount(String couponMinOrderAmount) {
		this.couponMinOrderAmount = couponMinOrderAmount;
	}
	public String getGoodsDesc() {
		return goodsDesc;
	}
	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}
	public String getMallName() {
		return mallName;
	}
	public void setMallName(String mallName) {
		this.mallName = mallName;
	}
	public String getMinNormalPrice() {
		return minNormalPrice;
	}
	public void setMinNormalPrice(String minNormalPrice) {
		this.minNormalPrice = minNormalPrice;
	}
	public String getMinGroupPrice() {
		return minGroupPrice;
	}
	public void setMinGroupPrice(String minGroupPrice) {
		this.minGroupPrice = minGroupPrice;
	}
	public String getSoldQuantity() {
		return soldQuantity;
	}
	public void setSoldQuantity(String soldQuantity) {
		this.soldQuantity = soldQuantity;
	}
	public String getGoodsImageUrl() {
		return goodsImageUrl;
	}
	public void setGoodsImageUrl(String goodsImageUrl) {
		this.goodsImageUrl = goodsImageUrl;
	}
	public String getGoodsThumbnailUrl() {
		return goodsThumbnailUrl;
	}
	public void setGoodsThumbnailUrl(String goodsThumbnailUrl) {
		this.goodsThumbnailUrl = goodsThumbnailUrl;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	
	
	
}