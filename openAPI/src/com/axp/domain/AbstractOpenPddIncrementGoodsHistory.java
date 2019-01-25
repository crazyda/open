package com.axp.domain;

import java.sql.Timestamp;

/**
 * AbstractOpenClient entity provides the base persistence definition of the
 * OpenClient entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractOpenPddIncrementGoodsHistory implements java.io.Serializable {

	private Integer id;
	private String orderReceiveTime;
	private String customParameters;
	private String type ;
	private String orderVerifyTime;
	private String orderPayTime;
	private String orderGroupSuccessTime;
	private String orderModifyAt ;
	private String orderStatusDesc;
	private String pid;
	private Integer orderStatus;
	private Double promotionAmount;
	private String promotionRate;
	private Timestamp orderCreateTime;
	private String orderAmount;
	private String goodsPrice;
	private Integer goodsQuantity;
	private String goodsThumbnailUrl;
	private String goodsName ;
	private String goodsId;
	private String orderSn;
	
	
	
	
	
	
	public AbstractOpenPddIncrementGoodsHistory() {
		super();
	}
	public AbstractOpenPddIncrementGoodsHistory(Integer id, String orderReceiveTime,String customParameters,String pid, String orderVerifyTime,
			String type,String orderPayTime,String orderGroupSuccessTime,String orderModifyAt,String orderStatusDesc,
			String goodsPrice,String goodsThumbnailUrl,String goodsName,String orderSn,
			Integer orderStatus, Timestamp orderCreateTime,
			Integer goodsQuantity, String goodsId, Double promotionAmount,
			String promotionRate, String orderAmount) {
		super();
		this.id = id;
		this.pid = pid;
		this.type = type;
		this.orderStatus = orderStatus;
		this.orderCreateTime = orderCreateTime;
		this.goodsQuantity = goodsQuantity;
		this.goodsId = goodsId;
		this.promotionAmount = promotionAmount;
		this.promotionRate = promotionRate;
		this.orderAmount = orderAmount;
		this.orderReceiveTime = orderReceiveTime;
		this.customParameters = customParameters;
		this.orderVerifyTime = orderVerifyTime;
		this.orderPayTime = orderPayTime;
		this.orderGroupSuccessTime = orderGroupSuccessTime;
		this.orderModifyAt = orderModifyAt;
		this.orderStatusDesc = orderStatusDesc;
		this.goodsPrice = goodsPrice;
		this.goodsThumbnailUrl = goodsThumbnailUrl;
		this.goodsName = goodsName;
		this.orderSn = orderSn;
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	
	public String getOrderReceiveTime() {
		return orderReceiveTime;
	}
	public void setOrderReceiveTime(String orderReceiveTime) {
		this.orderReceiveTime = orderReceiveTime;
	}
	public String getCustomParameters() {
		return customParameters;
	}
	public void setCustomParameters(String customParameters) {
		this.customParameters = customParameters;
	}
	public String getOrderVerifyTime() {
		return orderVerifyTime;
	}
	public void setOrderVerifyTime(String orderVerifyTime) {
		this.orderVerifyTime = orderVerifyTime;
	}
	public String getOrderPayTime() {
		return orderPayTime;
	}
	public void setOrderPayTime(String orderPayTime) {
		this.orderPayTime = orderPayTime;
	}
	public String getOrderGroupSuccessTime() {
		return orderGroupSuccessTime;
	}
	public void setOrderGroupSuccessTime(String orderGroupSuccessTime) {
		this.orderGroupSuccessTime = orderGroupSuccessTime;
	}
	public String getOrderModifyAt() {
		return orderModifyAt;
	}
	public void setOrderModifyAt(String orderModifyAt) {
		this.orderModifyAt = orderModifyAt;
	}
	public String getOrderStatusDesc() {
		return orderStatusDesc;
	}
	public void setOrderStatusDesc(String orderStatusDesc) {
		this.orderStatusDesc = orderStatusDesc;
	}
	public String getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
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
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Timestamp getOrderCreateTime() {
		return orderCreateTime;
	}
	public void setOrderCreateTime(Timestamp orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}
	public Integer getGoodsQuantity() {
		return goodsQuantity;
	}
	public void setGoodsQuantity(Integer goodsQuantity) {
		this.goodsQuantity = goodsQuantity;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public Double getPromotionAmount() {
		return promotionAmount;
	}
	public void setPromotionAmount(Double promotionAmount) {
		this.promotionAmount = promotionAmount;
	}
	public String getPromotionRate() {
		return promotionRate;
	}
	public void setPromotionRate(String promotionRate) {
		this.promotionRate = promotionRate;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	


}