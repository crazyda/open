package com.axp.domain;

/**
 * OpenGoods entity. @author MyEclipse Persistence Tools
 */
public class OpenGoods extends AbstractOpenGoods implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public OpenGoods() {
	}

	/** minimal constructor */
	public OpenGoods(String goodsEvalScore) {
		super(goodsEvalScore);
	}

	/** full constructor */
	public OpenGoods(String goodsEvalCount, String goodsEvalScore,
			String promotionRate, String couponEndTime, String couponStartTime,
			Integer couponRemainQuantity, Integer couponTotalQuantity,
			String couponDiscount, String couponMinOrderAmount,
			String categoryName, Integer categoryId, String mallName,
			Integer minNormalPrice, Integer minGroupPrice,
			Integer soldQuantity, String goodsImageUrl,
			String goodsThumbnailUrl, String goodsName, String goodsId) {
		super(goodsEvalCount, goodsEvalScore, promotionRate, couponEndTime,
				couponStartTime, couponRemainQuantity, couponTotalQuantity,
				couponDiscount, couponMinOrderAmount, categoryName, categoryId,
				mallName, minNormalPrice, minGroupPrice, soldQuantity,
				goodsImageUrl, goodsThumbnailUrl, goodsName, goodsId);
	}

}
