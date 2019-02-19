package com.axp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.DLScoreMarkDAO;
import com.axp.domain.DLScoreMark;


@Repository
public class DLScoreMarkDAOImpl extends BaseDaoImpl<DLScoreMark> implements DLScoreMarkDAO{

	
	@Override
	public List<Object[]> findMonToSun() {
		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT COUNT(1),"+
					"(SELECT count(1) FROM score_mark_dl WHERE isValid=1 AND sellerId is  NULL AND refreshTime is not null AND DATEDIFF(refreshTime,NOW())=0),"+
					"(SELECT count(1) FROM score_mark_dl WHERE isValid=1 AND sellerId is  NULL AND refreshTime is not null  AND DATEDIFF(refreshTime,NOW())=-1),"+
					"(SELECT count(1) FROM score_mark_dl WHERE isValid=1 AND sellerId is  NULL AND refreshTime is not null  AND DATEDIFF(refreshTime,NOW())=-2),"+
					"(SELECT count(1) FROM score_mark_dl WHERE isValid=1 AND sellerId is  NULL AND refreshTime is not null  AND DATEDIFF(refreshTime,NOW())=-3),"+
					"(SELECT count(1) FROM score_mark_dl WHERE isValid=1 AND sellerId is  NULL AND refreshTime is not null  AND DATEDIFF(refreshTime,NOW())=-4),"+
					"(SELECT count(1) FROM score_mark_dl WHERE isValid=1 AND sellerId is  NULL AND refreshTime is not null  AND DATEDIFF(refreshTime,NOW())=-5),"+
					"(SELECT count(1) FROM score_mark_dl WHERE isValid=1 AND sellerId is  NULL AND refreshTime is not null  AND DATEDIFF(refreshTime,NOW())=-6)" +
					"FROM  score_mark_dl WHERE isValid=1 AND sellerId is NULL  AND refreshTime is not null ";

		SQLQuery createSQLQuery = session.createSQLQuery(sql);
		return createSQLQuery.list();
	}

}
