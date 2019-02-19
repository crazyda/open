package com.axp.dao.impl;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.INewRedPaperSettingDao;
import com.axp.domain.NewRedPaperSetting;

@Repository("newRedPaperSettingDao")
public class NewRedPaperSettingDaoImpl extends BaseDaoImpl<NewRedPaperSetting> implements INewRedPaperSettingDao{

	@Override
	public void updateAllNunUsed(Integer id, Integer value, String string) {
		Session session = sessionFactory.getCurrentSession();
		StringBuffer sb = new StringBuffer("update NewRedPaperSetting set allNumUsed = allNumUsed"+string+"? where id = ?");
		Query query = session.createQuery(sb.toString());
		query.setParameter(0, value);
		query.setParameter(1, id);
		query.executeUpdate();
	}
	
	//更新所有发放中红包的当天剩余个数
	@Override
	public Integer updateDaySurplus(){
		StringBuffer redpaperHql = new StringBuffer();
		redpaperHql.append("update NewRedPaperSetting ");
		redpaperHql.append("set todaySurplus = (case when (allNum-allNumColl) > maxPutout then maxPutout else (allNum-allNumColl) end ) ");
		redpaperHql.append("where now() between beginTime and endTime and  allNum > allNumColl and isValid = true");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(redpaperHql.toString());
		return query.executeUpdate();
	}
	
}
