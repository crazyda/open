package com.axp.domain;

import java.sql.Timestamp;

/**
 * OpenClient entity. @author MyEclipse Persistence Tools
 */
public class OpenIncrementGoods extends AbstractOpenIncrementGoods implements
		java.io.Serializable {

	public OpenIncrementGoods() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OpenIncrementGoods(Integer id, String orderReceiveTime,
			String customParameters, String pid, String orderVerifyTime,
			String type, String orderPayTime, String orderGroupSuccessTime,
			String orderModifyAt, String orderStatusDesc, String goodsPrice,
			String goodsThumbnailUrl, String goodsName, String orderSn,
			 Integer orderStatus, String orderCreateTime,String isWithdraw,
			Integer goodsQuantity, String goodsId, Double promotionAmount,
			String promotionRate, String orderAmount,String upPid) {
		super(id, orderReceiveTime, customParameters, pid, orderVerifyTime, type,
				orderPayTime, orderGroupSuccessTime, orderModifyAt, orderStatusDesc,
				goodsPrice, goodsThumbnailUrl, goodsName, orderSn, 
				orderStatus, orderCreateTime,isWithdraw, goodsQuantity, goodsId, promotionAmount,
				promotionRate, orderAmount,upPid);
		// TODO Auto-generated constructor stub
	}

	
	
}
