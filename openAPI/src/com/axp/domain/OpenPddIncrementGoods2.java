package com.axp.domain;

import java.sql.Timestamp;

import com.axp.util.CalcUtil;

/**
 * OpenClient entity. @author MyEclipse Persistence Tools
 */
public class OpenPddIncrementGoods2 extends AbstractOpenPddIncrementGoods2 implements
		java.io.Serializable {

	public OpenPddIncrementGoods2() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OpenPddIncrementGoods2(Integer id, String orderReceiveTime,
			String customParameters, String pid, String orderVerifyTime,
			String type, String orderPayTime, String orderGroupSuccessTime,
			String orderModifyAt, String orderStatusDesc, String goodsPrice,
			String goodsThumbnailUrl, String goodsName, String orderSn,
			 Integer orderStatus, Timestamp orderCreateTime,String isWithdraw,
			Integer goodsQuantity, String goodsId, Double promotionAmount,
			String promotionRate, String orderAmount,String upPid) {
		super(id, orderReceiveTime, customParameters, pid, orderVerifyTime, type,
				orderPayTime, orderGroupSuccessTime, orderModifyAt, orderStatusDesc,
				goodsPrice, goodsThumbnailUrl, goodsName, orderSn, 
				orderStatus, orderCreateTime,isWithdraw, goodsQuantity, goodsId, promotionAmount,
				promotionRate, orderAmount,upPid);
		// TODO Auto-generated constructor stub
	}
	
	/*public void setPromotionAmount(Double promotionAmount,double maid) {
		this.setPromotionAmount(CalcUtil.mul(promotionAmount, maid));
	}*/

	
	
}
