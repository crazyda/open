package com.alipay.bean;

import java.io.Serializable;

import com.alipay.util.AlipayPayUtils;

/**
 * 支付宝支付签名参数 实体
 * 
 * @author Maijial
 * @Date 2015/12/02
 */
public class AlipaySign implements Serializable {

	private String goodName = "";//商品名称
	private String notifyUrl = "";//异步通知接口
	private String body = "";//订单自定义参数
	private String orderCode = "";//订单
	private Double totalFee = 0.00;
	private String sign = "";

	public String getSign() {
		this.sign = AlipayPayUtils.getOrder(goodName, totalFee, body, notifyUrl);
		return sign;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

}
