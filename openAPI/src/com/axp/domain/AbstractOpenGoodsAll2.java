package com.axp.domain;

/**
 * AbstractOpenGoods entity provides the base persistence definition of the
 * OpenGoods entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractOpenGoodsAll2 implements java.io.Serializable {

	// Fields

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
	private String categoryName;
	private String categoryId;
	private String mallName;
	private String minNormalPrice;
	private String minGroupPrice;
	private String soldQuantity;
	private String goodsImageUrl;
	private String goodsThumbnailUrl;
	private String goodsName;
	private String goodsId;
	private String optId;
	// Constructors

	/** default constructor */
	public AbstractOpenGoodsAll2() {
	}

	/** minimal constructor */
	public AbstractOpenGoodsAll2(String goodsEvalScore) {
		this.goodsEvalScore = goodsEvalScore;
	}

	/** full constructor */
	public AbstractOpenGoodsAll2(String goodsEvalCount, String goodsEvalScore,
			String promotionRate, String couponEndTime, String couponStartTime,
			String couponRemainQuantity, String couponTotalQuantity,
			String couponDiscount, String couponMinOrderAmount,
			String categoryName, String categoryId, String mallName,
			String minNormalPrice, String minGroupPrice,
			String soldQuantity, String goodsImageUrl,
			String goodsThumbnailUrl, String goodsName, String goodsId,String optId) {
		this.goodsEvalCount = goodsEvalCount;
		this.goodsEvalScore = goodsEvalScore;
		this.promotionRate = promotionRate;
		this.couponEndTime = couponEndTime;
		this.couponStartTime = couponStartTime;
		this.couponRemainQuantity = couponRemainQuantity;
		this.couponTotalQuantity = couponTotalQuantity;
		this.couponDiscount = couponDiscount;
		this.couponMinOrderAmount = couponMinOrderAmount;
		this.categoryName = categoryName;
		this.categoryId = categoryId;
		this.mallName = mallName;
		this.minNormalPrice = minNormalPrice;
		this.minGroupPrice = minGroupPrice;
		this.soldQuantity = soldQuantity;
		this.goodsImageUrl = goodsImageUrl;
		this.goodsThumbnailUrl = goodsThumbnailUrl;
		this.goodsName = goodsName;
		this.goodsId = goodsId;
		this.optId = optId;
	}

	// Property accessors
	
	
	public Integer getId() {
		return this.id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	


	public String getGoodsEvalCount() {
		return this.goodsEvalCount;
	}

	public void setGoodsEvalCount(String goodsEvalCount) {
		this.goodsEvalCount = goodsEvalCount;
	}

	public String getGoodsEvalScore() {
		return this.goodsEvalScore;
	}

	public void setGoodsEvalScore(String goodsEvalScore) {
		this.goodsEvalScore = goodsEvalScore;
	}

	public String getPromotionRate() {
		return this.promotionRate;
	}

	public void setPromotionRate(String promotionRate) {
		this.promotionRate = promotionRate;
	}

	public String getCouponEndTime() {
		return this.couponEndTime;
	}

	public void setCouponEndTime(String couponEndTime) {
		this.couponEndTime = couponEndTime;
	}

	public String getCouponStartTime() {
		return this.couponStartTime;
	}

	public void setCouponStartTime(String couponStartTime) {
		this.couponStartTime = couponStartTime;
	}

	

	public String getCouponDiscount() {
		return this.couponDiscount;
	}

	public void setCouponDiscount(String couponDiscount) {
		this.couponDiscount = couponDiscount;
	}

	public String getCouponMinOrderAmount() {
		return this.couponMinOrderAmount;
	}

	public void setCouponMinOrderAmount(String couponMinOrderAmount) {
		this.couponMinOrderAmount = couponMinOrderAmount;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
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

	public String getOptId() {
		return optId;
	}

	public void setOptId(String optId) {
		this.optId = optId;
	}

	public String getGoodsImageUrl() {
		return this.goodsImageUrl;
	}

	public void setGoodsImageUrl(String goodsImageUrl) {
		this.goodsImageUrl = goodsImageUrl;
	}

	public String getGoodsThumbnailUrl() {
		return this.goodsThumbnailUrl;
	}

	public void setGoodsThumbnailUrl(String goodsThumbnailUrl) {
		this.goodsThumbnailUrl = goodsThumbnailUrl;
	}

	public String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

}