package com.axp.domain;

/**
 * AbstractOpenGoods entity provides the base persistence definition of the
 * OpenGoods entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractOpenGoods implements java.io.Serializable {

	// Fields

	private Integer id;
	private String goodsEvalCount;
	private String goodsEvalScore;
	private String promotionRate;
	private String couponEndTime;
	private String couponStartTime;
	private Integer couponRemainQuantity;
	private Integer couponTotalQuantity;
	private String couponDiscount;
	private String couponMinOrderAmount;
	private String categoryName;
	private Integer categoryId;
	private String mallName;
	private Integer minNormalPrice;
	private Integer minGroupPrice;
	private Integer soldQuantity;
	private String goodsImageUrl;
	private String goodsThumbnailUrl;
	private String goodsName;
	private String goodsId;

	// Constructors

	/** default constructor */
	public AbstractOpenGoods() {
	}

	/** minimal constructor */
	public AbstractOpenGoods(String goodsEvalScore) {
		this.goodsEvalScore = goodsEvalScore;
	}

	/** full constructor */
	public AbstractOpenGoods(String goodsEvalCount, String goodsEvalScore,
			String promotionRate, String couponEndTime, String couponStartTime,
			Integer couponRemainQuantity, Integer couponTotalQuantity,
			String couponDiscount, String couponMinOrderAmount,
			String categoryName, Integer categoryId, String mallName,
			Integer minNormalPrice, Integer minGroupPrice,
			Integer soldQuantity, String goodsImageUrl,
			String goodsThumbnailUrl, String goodsName, String goodsId) {
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

	public Integer getCouponRemainQuantity() {
		return this.couponRemainQuantity;
	}

	public void setCouponRemainQuantity(Integer couponRemainQuantity) {
		this.couponRemainQuantity = couponRemainQuantity;
	}

	public Integer getCouponTotalQuantity() {
		return this.couponTotalQuantity;
	}

	public void setCouponTotalQuantity(Integer couponTotalQuantity) {
		this.couponTotalQuantity = couponTotalQuantity;
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

	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getMallName() {
		return this.mallName;
	}

	public void setMallName(String mallName) {
		this.mallName = mallName;
	}

	public Integer getMinNormalPrice() {
		return this.minNormalPrice;
	}

	public void setMinNormalPrice(Integer minNormalPrice) {
		this.minNormalPrice = minNormalPrice;
	}

	public Integer getMinGroupPrice() {
		return this.minGroupPrice;
	}

	public void setMinGroupPrice(Integer minGroupPrice) {
		this.minGroupPrice = minGroupPrice;
	}

	public Integer getSoldQuantity() {
		return this.soldQuantity;
	}

	public void setSoldQuantity(Integer soldQuantity) {
		this.soldQuantity = soldQuantity;
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