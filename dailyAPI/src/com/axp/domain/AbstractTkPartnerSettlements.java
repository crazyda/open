package com.axp.domain;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * AbstractAdver entity provides the base persistence definition of the Adver
 * entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractTkPartnerSettlements implements java.io.Serializable {
	
	
	private Integer id;
	private String pidId;
	private String dates;//日期
	private double settlementAmount;//每月金额
	private Integer is_settlement; //本月是否提现 0否 1 是
	
	
	
	
	
	public AbstractTkPartnerSettlements() {
		super();
	}
	public AbstractTkPartnerSettlements(Integer id, String pidId, String dates,
			double settlementAmount, Integer is_settlement) {
		super();
		this.id = id;
		this.pidId = pidId;
		this.dates = dates;
		this.settlementAmount = settlementAmount;
		this.is_settlement = is_settlement;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPidId() {
		return pidId;
	}
	public void setPidId(String pidId) {
		this.pidId = pidId;
	}
	public String getDates() {
		return dates;
	}
	public void setDates(String dates) {
		this.dates = dates;
	}
	public double getSettlementAmount() {
		return settlementAmount;
	}
	public void setSettlementAmount(double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
	public Integer getIs_settlement() {
		return is_settlement;
	}
	public void setIs_settlement(Integer is_settlement) {
		this.is_settlement = is_settlement;
	}
	
	
	
	
	
	
}