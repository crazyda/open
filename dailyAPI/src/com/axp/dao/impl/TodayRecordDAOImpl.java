package com.axp.dao.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.axp.dao.TodayRecordDAO;
import com.axp.domain.TodayRecord;

@Repository
public class TodayRecordDAOImpl extends BaseDaoImpl<TodayRecord> implements
		TodayRecordDAO {

	// 获取用户今天获取得商家红包总数；
	@Override
	public Integer getTodaySellerRedpaperNum(Integer userId) {
		TodayRecord todayRecord = getTodayRecordByUserId(userId);
		if (todayRecord != null && isToday(todayRecord.getUpdateTime())) {
			return todayRecord.getTodaySellerRedpaper();
		} else {
			return 0;
		}
	}

	// 获取用户今天获取得总部红包数；
	@Override
	public Integer getTodayRedpaperNum(Integer userId) {
		TodayRecord todayRecord = getTodayRecordByUserId(userId);
		if (todayRecord != null && isToday(todayRecord.getUpdateTime())) {
			return todayRecord.getTodayRedpaper();
		} else {
			return 0;
		}
	}

	// 更新今天的总分数
	@Override
	public void updateTodayScore(Integer userId, Integer score) {
		TodayRecord todayRecord = updateTodayInfo(userId);
		todayRecord.setTodayScore(todayRecord.getTodayScore() + score);
		super.saveOrUpdate(todayRecord);
	}

	// 更新今天的代金券
	@Override
	public void updateTodayCash(Integer userId, Double cash) {
		TodayRecord todayRecord = updateTodayInfo(userId);
		todayRecord.setTodayCash(todayRecord.getTodayCash() + cash);
		super.saveOrUpdate(todayRecord);
	}

	// 更新今天推荐人数
	@Override
	public void updateTodayReferee(Integer userId, Integer mun) {
		TodayRecord todayRecord = updateTodayInfo(userId);
		todayRecord.setTodayReferee(todayRecord.getTodayReferee() + mun);
		super.saveOrUpdate(todayRecord);
	}

	// 更新用户今天获得的总部红包数；
	@Override
	public void updateRedPaper(Integer userId, Integer mun) {
		TodayRecord todayRecord = updateTodayInfo(userId);
		todayRecord.setTodayRedpaper(todayRecord.getTodayRedpaper() + mun);
		todayRecord.setLastRedpaperTime(new java.sql.Timestamp(System.currentTimeMillis()));
		super.saveOrUpdate(todayRecord);
	}

	// 更新用户今天获得的商家红包数；
	@Override
	public void updateTodaySellerRedpaper(Integer userId, Integer mun) {
		TodayRecord todayRecord = updateTodayInfo(userId);
		todayRecord.setTodaySellerRedpaper(todayRecord.getTodaySellerRedpaper()
				+ mun);
		super.saveOrUpdate(todayRecord);
	}

	@Override
	public TodayRecord updateTodayInfo(Integer userId) {
		TodayRecord todayRecord = getTodayRecordByUserId(userId);
		if (todayRecord == null) {
			TodayRecord t = new TodayRecord();
			t.setUserId(userId);
			t.setIsValid(true);
			t.setTodayScore(0);
			t.setRedPaper(0);
			t.setTodayReferee(0);
			t.setTodayCash(0.0);
			t.setTodayRedpaper(0);
			t.setTodaySellerRedpaper(0);
			t.setUpdateTime(new Date());
			Timestamp nowtime = new Timestamp(System.currentTimeMillis()-(long)(0.6*60*60*1000));
			t.setLastRedpaperTime(nowtime);
			todayRecord = t;
		} else if (!isToday(todayRecord.getUpdateTime())) {
			todayRecord.setRedPaper(0);
			todayRecord.setTodayScore(0);
			todayRecord.setRedPaper(0);
			todayRecord.setTodayReferee(0);
			todayRecord.setTodayCash(0.0);
			todayRecord.setUpdateTime(new Date());
			todayRecord.setTodaySellerRedpaper(0);
			todayRecord.setTodayRedpaper(0);

		}

		return todayRecord;
	}

	// 查詢今天的總分數
	@Override
	public Integer getTodayScoreByUserId(Integer userId) {
		TodayRecord todayRecord = getTodayRecordByUserId(userId);
		if (todayRecord != null && isToday(todayRecord.getUpdateTime())) {
			return todayRecord.getTodayScore() == null ? 0 : todayRecord
					.getTodayScore();
		} else {
			return 0;
		}
	}

	// 查詢今天的總分數
	@Override
	public Double getTodayCashByUserId(Integer userId) {
		TodayRecord todayRecord = getTodayRecordByUserId(userId);
		if (todayRecord != null && isToday(todayRecord.getUpdateTime())) {
			return todayRecord.getTodayCash() == null ? 0.0 : todayRecord
					.getTodayCash();
		} else {
			return 0.0;
		}
	}

	// 查詢今天的總分數
	@Override
	public Integer getTodayRefereeByUserId(Integer userId) {
		TodayRecord todayRecord = getTodayRecordByUserId(userId);
		if (todayRecord != null && isToday(todayRecord.getUpdateTime())) {
			return todayRecord.getTodayReferee() == null ? 0 : todayRecord
					.getTodayReferee();
		} else {
			return 0;
		}
	}

	// 查詢今天的總分數
	@Override
	public Integer getRedPaper(Integer userId) {
		TodayRecord todayRecord = getTodayRecordByUserId(userId);
		if (todayRecord != null && isToday(todayRecord.getUpdateTime())) {
			return todayRecord.getRedPaper() == null ? 0 : todayRecord
					.getRedPaper();
		} else {
			return 0;
		}
	}

	
	
	
	// 通过userId获取TodayRecord对象；
	@SuppressWarnings("unchecked")
	private TodayRecord getTodayRecordByUserId(Integer userId) {
		List<TodayRecord> todayRecords;
		try {
			String queryString = "from TodayRecord as model where model.userId= :userId";
			Query queryObject = sessionFactory.getCurrentSession().createQuery(
					queryString);
			queryObject.setParameter("userId", userId);
			todayRecords = queryObject.list();
		} catch (RuntimeException re) {
			throw re;
		}
		if (todayRecords.size() == 1 && todayRecords.get(0) != null) {
			return todayRecords.get(0);
		}
		return null;
	}

	// 时间比较，判断指定时间是否是在今天；
	private Boolean isToday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		Calendar now = Calendar.getInstance();
		now.setTime(new Date());

		int cal_year = cal.get(Calendar.YEAR);
		int cal_month = cal.get(Calendar.MONTH) + 1;
		int cal_day = cal.get(Calendar.DAY_OF_MONTH);

		int now_year = now.get(Calendar.YEAR);
		int now_month = now.get(Calendar.MONTH) + 1;
		int now_day = now.get(Calendar.DAY_OF_MONTH);

		if (cal_day == now_day && cal_month == now_month
				&& cal_year == now_year) {
			return true;
		}
		return false;
	}

	@Override
	public Timestamp getLastRedpaperTimeByUserId(Integer userId) {
		TodayRecord todayRecord = updateTodayInfo(userId);
		return todayRecord.getLastRedpaperTime();
	}

}
