package com.axp.domain;


public class RedPaperDto {
	private String sellerName;//商家名称
	private String endTime;//有效期
	private double money;//金额
	private double avail;//使用金额
	private String address;//商家网页地址
	private int isToday = 1;//是否今天领取
	
	private String sellerImg;//商家图片
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getIsToday() {
		return isToday;
	}
	public void setIsToday(int isToday) {
		this.isToday = isToday;
	}
	public String getSellerImg() {
		return sellerImg;
	}
	public void setSellerImg(String sellerImg) {
		this.sellerImg = sellerImg;
	}
	public double getAvail() {
		return avail;
	}
	public void setAvail(double avail) {
		this.avail = avail;
	}
	
}
