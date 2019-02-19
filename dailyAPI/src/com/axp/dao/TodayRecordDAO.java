package com.axp.dao;

import java.sql.Timestamp;

import com.axp.domain.TodayRecord;

public interface TodayRecordDAO extends IBaseDao<TodayRecord> {

	Integer getTodaySellerRedpaperNum(Integer userId);

	Integer getTodayRedpaperNum(Integer userId);

	void updateTodayScore(Integer userId, Integer score);

	void updateTodayCash(Integer userId, Double cash);

	void updateTodayReferee(Integer userId, Integer mun);

	void updateRedPaper(Integer userId, Integer mun);

	void updateTodaySellerRedpaper(Integer userId, Integer mun);

	TodayRecord updateTodayInfo(Integer userId);

	Integer getTodayScoreByUserId(Integer userId);

	Double getTodayCashByUserId(Integer userId);

	Integer getTodayRefereeByUserId(Integer userId);

	Integer getRedPaper(Integer userId);
	
	Timestamp getLastRedpaperTimeByUserId(Integer userId);

}
