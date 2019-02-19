package com.axp.domain;

import java.sql.Timestamp;

/**
 * 访问参数记录类
 */
public class VisitLog {
	/**支付*/
	public static final Integer PAY=10;  
	/**退款*/
	public static final Integer REFUND=20;
	
	public static final String WEIXIN="WEIXIN";
	public static final String ALIPAY="ALIPAY";
	public static final String YILIAN="YILIAN";
	public static final String WALLET="WALLET";
	
	private Integer orderId;
	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	private Timestamp createTime;
	
	private String data;
	
	private Integer id;
	
	private Integer userId;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	private String payType;  //支付商

	private Integer type;   
	public Timestamp getCreateTime() {
		return createTime;
	}
	
	public String getData() {
		return data;
	}

	
	public Integer getId() {
		return id;
	}


	public Integer getType() {
		return type;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}


	public void setData(String data) {
		this.data = data;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
}
