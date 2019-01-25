package com.axp.domain;

/**
 * AbstractOpenClient entity provides the base persistence definition of the
 * OpenClient entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractOpenJDOrderList implements java.io.Serializable {

	private Integer id ;
	private String finishTime;
	private String orderEmt;
	private String orderId;
	private String orderTime;
	private String parentId;
	private String payMonth;
	private String plus;
	private String popId;
	private String unionId;
	private String validCode;
	private String unionUserName;
	private String skuList;
	
	private String skuId;
	private String skuName;
	private String skuNum;
	private String skuReturnNum;
	private String frozenSkuNum;
	private String price;
	private String payPrice;
	private String commissionRate;
	private String subSideRate;
	private String subsidyRate;
	private String finalRate;
	private String estimateCosPrice;
	private String estimateCommission;
	private String estimateFee;
	private String actualCosPrice;
	private String actualCommission;
	private String actualFee;
	private String traceType;
	private String spId;
	private String siteId;
	private String unionAlias;
	private String pid;
	private String unionTrafficGroup;
	private String firstLevel;
	private String secondLevel;
	private String thirdLevel;
	private String subUnionId;
	private String unionTag;
	private String img;
	
	
	
	
	public AbstractOpenJDOrderList() {
		super();
	}

	public AbstractOpenJDOrderList(Integer id, String finishTime,
			String orderEmt, String orderId, String orderTime, String parentId,
			String payMonth, String plus, String popId, String unionId,
			String validCode, String unionUserName, String skuList,
			String skuId, String skuName, String skuNum, String skuReturnNum,
			String frozenSkuNum, String price, String payPrice,
			String commissionRate, String subSideRate, String subsidyRate,
			String finalRate, String estimateCosPrice,
			String estimateCommission, String estimateFee,
			String actualCosPrice, String actualCommission, String actualFee,
			String traceType, String spId, String siteId, String unionAlias,
			String pid, String unionTrafficGroup, String firstLevel,
			String secondLevel, String thirdLevel, String subUnionId,
			String unionTag,String img) {
		super();
		this.id = id;
		this.finishTime = finishTime;
		this.orderEmt = orderEmt;
		this.orderId = orderId;
		this.orderTime = orderTime;
		this.parentId = parentId;
		this.payMonth = payMonth;
		this.plus = plus;
		this.popId = popId;
		this.unionId = unionId;
		this.validCode = validCode;
		this.unionUserName = unionUserName;
		this.skuList = skuList;
		this.skuId = skuId;
		this.skuName = skuName;
		this.skuNum = skuNum;
		this.skuReturnNum = skuReturnNum;
		this.frozenSkuNum = frozenSkuNum;
		this.price = price;
		this.payPrice = payPrice;
		this.commissionRate = commissionRate;
		this.subSideRate = subSideRate;
		this.subsidyRate = subsidyRate;
		this.finalRate = finalRate;
		this.estimateCosPrice = estimateCosPrice;
		this.estimateCommission = estimateCommission;
		this.estimateFee = estimateFee;
		this.actualCosPrice = actualCosPrice;
		this.actualCommission = actualCommission;
		this.actualFee = actualFee;
		this.traceType = traceType;
		this.spId = spId;
		this.siteId = siteId;
		this.unionAlias = unionAlias;
		this.pid = pid;
		this.unionTrafficGroup = unionTrafficGroup;
		this.firstLevel = firstLevel;
		this.secondLevel = secondLevel;
		this.thirdLevel = thirdLevel;
		this.subUnionId = subUnionId;
		this.unionTag = unionTag;
		this.img = img;
	}

	

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
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
	public String getSkuNum() {
		return skuNum;
	}
	public void setSkuNum(String skuNum) {
		this.skuNum = skuNum;
	}
	public String getSkuReturnNum() {
		return skuReturnNum;
	}
	public void setSkuReturnNum(String skuReturnNum) {
		this.skuReturnNum = skuReturnNum;
	}
	public String getFrozenSkuNum() {
		return frozenSkuNum;
	}
	public void setFrozenSkuNum(String frozenSkuNum) {
		this.frozenSkuNum = frozenSkuNum;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(String payPrice) {
		this.payPrice = payPrice;
	}
	public String getCommissionRate() {
		return commissionRate;
	}
	public void setCommissionRate(String commissionRate) {
		this.commissionRate = commissionRate;
	}
	public String getSubSideRate() {
		return subSideRate;
	}
	public void setSubSideRate(String subSideRate) {
		this.subSideRate = subSideRate;
	}
	public String getSubsidyRate() {
		return subsidyRate;
	}
	public void setSubsidyRate(String subsidyRate) {
		this.subsidyRate = subsidyRate;
	}
	public String getFinalRate() {
		return finalRate;
	}
	public void setFinalRate(String finalRate) {
		this.finalRate = finalRate;
	}
	public String getEstimateCosPrice() {
		return estimateCosPrice;
	}
	public void setEstimateCosPrice(String estimateCosPrice) {
		this.estimateCosPrice = estimateCosPrice;
	}
	public String getEstimateCommission() {
		return estimateCommission;
	}
	public void setEstimateCommission(String estimateCommission) {
		this.estimateCommission = estimateCommission;
	}
	public String getEstimateFee() {
		return estimateFee;
	}
	public void setEstimateFee(String estimateFee) {
		this.estimateFee = estimateFee;
	}
	public String getActualCosPrice() {
		return actualCosPrice;
	}
	public void setActualCosPrice(String actualCosPrice) {
		this.actualCosPrice = actualCosPrice;
	}
	public String getActualCommission() {
		return actualCommission;
	}
	public void setActualCommission(String actualCommission) {
		this.actualCommission = actualCommission;
	}
	public String getActualFee() {
		return actualFee;
	}
	public void setActualFee(String actualFee) {
		this.actualFee = actualFee;
	}
	public String getTraceType() {
		return traceType;
	}
	public void setTraceType(String traceType) {
		this.traceType = traceType;
	}
	public String getSpId() {
		return spId;
	}
	public void setSpId(String spId) {
		this.spId = spId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getUnionAlias() {
		return unionAlias;
	}
	public void setUnionAlias(String unionAlias) {
		this.unionAlias = unionAlias;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getUnionTrafficGroup() {
		return unionTrafficGroup;
	}
	public void setUnionTrafficGroup(String unionTrafficGroup) {
		this.unionTrafficGroup = unionTrafficGroup;
	}
	public String getFirstLevel() {
		return firstLevel;
	}
	public void setFirstLevel(String firstLevel) {
		this.firstLevel = firstLevel;
	}
	public String getSecondLevel() {
		return secondLevel;
	}
	public void setSecondLevel(String secondLevel) {
		this.secondLevel = secondLevel;
	}
	public String getThirdLevel() {
		return thirdLevel;
	}
	public void setThirdLevel(String thirdLevel) {
		this.thirdLevel = thirdLevel;
	}
	public String getSubUnionId() {
		return subUnionId;
	}
	public void setSubUnionId(String subUnionId) {
		this.subUnionId = subUnionId;
	}
	public String getUnionTag() {
		return unionTag;
	}
	public void setUnionTag(String unionTag) {
		this.unionTag = unionTag;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	public String getOrderEmt() {
		return orderEmt;
	}
	public void setOrderEmt(String orderEmt) {
		this.orderEmt = orderEmt;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getPayMonth() {
		return payMonth;
	}
	public void setPayMonth(String payMonth) {
		this.payMonth = payMonth;
	}
	public String getPlus() {
		return plus;
	}
	public void setPlus(String plus) {
		this.plus = plus;
	}
	public String getPopId() {
		return popId;
	}
	public void setPopId(String popId) {
		this.popId = popId;
	}
	public String getUnionId() {
		return unionId;
	}
	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	public String getValidCode() {
		return validCode;
	}
	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}
	public String getUnionUserName() {
		return unionUserName;
	}
	public void setUnionUserName(String unionUserName) {
		this.unionUserName = unionUserName;
	}
	public String getSkuList() {
		return skuList;
	}
	public void setSkuList(String skuList) {
		this.skuList = skuList;
	}
	
	
	
	
}