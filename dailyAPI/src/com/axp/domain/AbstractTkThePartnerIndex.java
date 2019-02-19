package com.axp.domain;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * AbstractAdver entity provides the base persistence definition of the Adver
 * entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractTkThePartnerIndex implements java.io.Serializable {
	
	private Integer id ; 
	private Integer userId ;
	private String pidId;
	private String userName;
	private String  encryptId;//抓取加密Id
	private String uId;
	private double allFeeYesterday;//昨天预估收入
	private double selfFeeYesterday;//昨天个人收入
	private double allFeeToweek;//本周预估收入
	private double selfFeeToweek;//本周个人收入
	private double allFeeThismonth;//本月预估收入
	private double selfFeeThismonth;//本月个人收入
	private double allFeeLastmonth;//上月预估收入
	private double selfFeeLastmonth;//上月个人收入
	private Integer orderCountYesterday;//昨天订单总数
	private Integer selfCountYesterday;//昨天个人订单总数
	private Integer orderCountToweek;//本周订单数
	private Integer selfCount_toweek;//本周个人订单总数
	private Integer orderCount_thismonth;//本月订单总数
	private Integer selfCount_thismonth;//本月个人订单总数
	private Integer orderCount_lastmonth;//上月订单总数
	private Integer selfCount_lastmonth;//上月个人订单总数
	private Date date;//日期
	private double allFee_today;//今天预估收入
	private double selfFee_today;//今天个人收收入
	private Integer orderCount_today;//订单总数
	private Integer selfCount_today;//今天个人订单总数
	private double userprice;//可计算余额
	private double last_month_price;//上月结算
	private double this_month_price;//本月结算
	
	
	
	
	
	public AbstractTkThePartnerIndex() {
		super();
	}
	public AbstractTkThePartnerIndex(Integer id, Integer userId, String pidId,
			String userName, String encryptId, String uId,
			double allFeeYesterday, double selfFeeYesterday,
			double allFeeToweek, double selfFeeToweek, double allFeeThismonth,
			double selfFeeThismonth, double allFeeLastmonth,
			double selfFeeLastmonth, Integer orderCountYesterday,
			Integer selfCountYesterday, Integer orderCountToweek,
			Integer selfCount_toweek, Integer orderCount_thismonth,
			Integer selfCount_thismonth, Integer orderCount_lastmonth,
			Integer selfCount_lastmonth, Date date, double allFee_today,
			double selfFee_today, Integer orderCount_today,
			Integer selfCount_today, double userprice, double last_month_price,
			double this_month_price) {
		super();
		this.id = id;
		this.userId = userId;
		this.pidId = pidId;
		this.userName = userName;
		this.encryptId = encryptId;
		this.uId = uId;
		this.allFeeYesterday = allFeeYesterday;
		this.selfFeeYesterday = selfFeeYesterday;
		this.allFeeToweek = allFeeToweek;
		this.selfFeeToweek = selfFeeToweek;
		this.allFeeThismonth = allFeeThismonth;
		this.selfFeeThismonth = selfFeeThismonth;
		this.allFeeLastmonth = allFeeLastmonth;
		this.selfFeeLastmonth = selfFeeLastmonth;
		this.orderCountYesterday = orderCountYesterday;
		this.selfCountYesterday = selfCountYesterday;
		this.orderCountToweek = orderCountToweek;
		this.selfCount_toweek = selfCount_toweek;
		this.orderCount_thismonth = orderCount_thismonth;
		this.selfCount_thismonth = selfCount_thismonth;
		this.orderCount_lastmonth = orderCount_lastmonth;
		this.selfCount_lastmonth = selfCount_lastmonth;
		this.date = date;
		this.allFee_today = allFee_today;
		this.selfFee_today = selfFee_today;
		this.orderCount_today = orderCount_today;
		this.selfCount_today = selfCount_today;
		this.userprice = userprice;
		this.last_month_price = last_month_price;
		this.this_month_price = this_month_price;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getPidId() {
		return pidId;
	}
	public void setPidId(String pidId) {
		this.pidId = pidId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEncryptId() {
		return encryptId;
	}
	public void setEncryptId(String encryptId) {
		this.encryptId = encryptId;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public double getAllFeeYesterday() {
		return allFeeYesterday;
	}
	public void setAllFeeYesterday(double allFeeYesterday) {
		this.allFeeYesterday = allFeeYesterday;
	}
	public double getSelfFeeYesterday() {
		return selfFeeYesterday;
	}
	public void setSelfFeeYesterday(double selfFeeYesterday) {
		this.selfFeeYesterday = selfFeeYesterday;
	}
	public double getAllFeeToweek() {
		return allFeeToweek;
	}
	public void setAllFeeToweek(double allFeeToweek) {
		this.allFeeToweek = allFeeToweek;
	}
	public double getSelfFeeToweek() {
		return selfFeeToweek;
	}
	public void setSelfFeeToweek(double selfFeeToweek) {
		this.selfFeeToweek = selfFeeToweek;
	}
	public double getAllFeeThismonth() {
		return allFeeThismonth;
	}
	public void setAllFeeThismonth(double allFeeThismonth) {
		this.allFeeThismonth = allFeeThismonth;
	}
	public double getSelfFeeThismonth() {
		return selfFeeThismonth;
	}
	public void setSelfFeeThismonth(double selfFeeThismonth) {
		this.selfFeeThismonth = selfFeeThismonth;
	}
	public double getAllFeeLastmonth() {
		return allFeeLastmonth;
	}
	public void setAllFeeLastmonth(double allFeeLastmonth) {
		this.allFeeLastmonth = allFeeLastmonth;
	}
	public double getSelfFeeLastmonth() {
		return selfFeeLastmonth;
	}
	public void setSelfFeeLastmonth(double selfFeeLastmonth) {
		this.selfFeeLastmonth = selfFeeLastmonth;
	}
	public Integer getOrderCountYesterday() {
		return orderCountYesterday;
	}
	public void setOrderCountYesterday(Integer orderCountYesterday) {
		this.orderCountYesterday = orderCountYesterday;
	}
	public Integer getSelfCountYesterday() {
		return selfCountYesterday;
	}
	public void setSelfCountYesterday(Integer selfCountYesterday) {
		this.selfCountYesterday = selfCountYesterday;
	}
	public Integer getOrderCountToweek() {
		return orderCountToweek;
	}
	public void setOrderCountToweek(Integer orderCountToweek) {
		this.orderCountToweek = orderCountToweek;
	}
	public Integer getSelfCount_toweek() {
		return selfCount_toweek;
	}
	public void setSelfCount_toweek(Integer selfCount_toweek) {
		this.selfCount_toweek = selfCount_toweek;
	}
	public Integer getOrderCount_thismonth() {
		return orderCount_thismonth;
	}
	public void setOrderCount_thismonth(Integer orderCount_thismonth) {
		this.orderCount_thismonth = orderCount_thismonth;
	}
	public Integer getSelfCount_thismonth() {
		return selfCount_thismonth;
	}
	public void setSelfCount_thismonth(Integer selfCount_thismonth) {
		this.selfCount_thismonth = selfCount_thismonth;
	}
	public Integer getOrderCount_lastmonth() {
		return orderCount_lastmonth;
	}
	public void setOrderCount_lastmonth(Integer orderCount_lastmonth) {
		this.orderCount_lastmonth = orderCount_lastmonth;
	}
	public Integer getSelfCount_lastmonth() {
		return selfCount_lastmonth;
	}
	public void setSelfCount_lastmonth(Integer selfCount_lastmonth) {
		this.selfCount_lastmonth = selfCount_lastmonth;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getAllFee_today() {
		return allFee_today;
	}
	public void setAllFee_today(double allFee_today) {
		this.allFee_today = allFee_today;
	}
	public double getSelfFee_today() {
		return selfFee_today;
	}
	public void setSelfFee_today(double selfFee_today) {
		this.selfFee_today = selfFee_today;
	}
	public Integer getOrderCount_today() {
		return orderCount_today;
	}
	public void setOrderCount_today(Integer orderCount_today) {
		this.orderCount_today = orderCount_today;
	}
	public Integer getSelfCount_today() {
		return selfCount_today;
	}
	public void setSelfCount_today(Integer selfCount_today) {
		this.selfCount_today = selfCount_today;
	}
	public double getUserprice() {
		return userprice;
	}
	public void setUserprice(double userprice) {
		this.userprice = userprice;
	}
	public double getLast_month_price() {
		return last_month_price;
	}
	public void setLast_month_price(double last_month_price) {
		this.last_month_price = last_month_price;
	}
	public double getThis_month_price() {
		return this_month_price;
	}
	public void setThis_month_price(double this_month_price) {
		this.this_month_price = this_month_price;
	}
	
	
	
	
	
	
}