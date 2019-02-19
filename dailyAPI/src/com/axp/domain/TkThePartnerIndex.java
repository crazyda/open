package com.axp.domain;

import java.sql.Date;



/**
 * Adver entity. @author MyEclipse Persistence Tools
 */
public class TkThePartnerIndex extends AbstractTkThePartnerIndex implements java.io.Serializable {

	public TkThePartnerIndex() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TkThePartnerIndex(Integer id, Integer userId, String pidId,
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
		super(id, userId, pidId, userName, encryptId, uId, allFeeYesterday,
				selfFeeYesterday, allFeeToweek, selfFeeToweek, allFeeThismonth,
				selfFeeThismonth, allFeeLastmonth, selfFeeLastmonth,
				orderCountYesterday, selfCountYesterday, orderCountToweek,
				selfCount_toweek, orderCount_thismonth, selfCount_thismonth,
				orderCount_lastmonth, selfCount_lastmonth, date, allFee_today,
				selfFee_today, orderCount_today, selfCount_today, userprice,
				last_month_price, this_month_price);
		// TODO Auto-generated constructor stub
	}


}
