package com.axp.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.INewRedPaperLogDao;
import com.axp.domain.NewRedPaperLog;

@Repository
public class NewRedPaperLogDaoImpl extends BaseDaoImpl<NewRedPaperLog> implements INewRedPaperLogDao{

	@Override
	public List<NewRedPaperLog> getMinNewRedPaperLog(Integer id,Timestamp createTime) {
		Session session = sessionFactory.getCurrentSession();
		StringBuffer sb = new StringBuffer();
		sb.append("FROM NewRedPaperLog WHERE endTime >= ? AND userid = ? AND (status < 1 or status is null) AND avail > 0 ORDER BY endtime  ASC,avail ASC limit 0,1");
		Query query = session.createQuery(sb.toString());
		query.setParameter(0, createTime);
		query.setParameter(1, id);
		return  query.list();
	}

	@Override
	public List<Object[]> findBySql(String sql) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		return  query.list();
	}

}
