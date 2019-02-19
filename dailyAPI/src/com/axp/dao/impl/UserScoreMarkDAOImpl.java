package com.axp.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.UserScoreMarkDAO;
import com.axp.domain.UserScoreMark;
import com.axp.domain.Users;


@Repository
public class UserScoreMarkDAOImpl extends BaseDaoImpl<UserScoreMark> implements UserScoreMarkDAO{

	
	@Override
	public List<Object[]> findBySQL(Users user, Integer payScore) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT userId ,COUNT(userId) FROM (SELECT * FROM scoreMark_user ORDER BY validityTime ASC LIMIT 1,"+payScore+") AS c"+
							"GROUP BY userId";
		
		
		SQLQuery sqlQuery=session.createSQLQuery(sql);
		return sqlQuery.list();
	}

}
