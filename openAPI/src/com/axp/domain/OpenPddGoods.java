package com.axp.domain;

import java.sql.Timestamp;

/**
 * OpenClient entity. @author MyEclipse Persistence Tools
 */
public class OpenPddGoods extends AbstractOpenPddGoods implements
		java.io.Serializable {

	public OpenPddGoods() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OpenPddGoods(Integer id, String orderReceiveTime,
			String customParameters, String pid, String orderVerifyTime,
			String type, String orderPayTime, String orderGroupSuccessTime,
			String orderModifyAt, String orderStatusDesc, String goodsPrice,
			String goodsThumbnailUrl, String goodsName, String orderSn,
			boolean isWithdraw, Integer orderStatus, Timestamp orderCreateTime,
			Integer goodsQuantity, String goodsId, Double promotionAmount,
			String promotionRate, String orderAmount) {
		super(id, orderReceiveTime, customParameters, pid, orderVerifyTime, type,
				orderPayTime, orderGroupSuccessTime, orderModifyAt, orderStatusDesc,
				goodsPrice, goodsThumbnailUrl, goodsName, orderSn, isWithdraw,
				orderStatus, orderCreateTime, goodsQuantity, goodsId, promotionAmount,
				promotionRate, orderAmount);
		// TODO Auto-generated constructor stub
	}

	
	
}
