package com.axp.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.ScoreMarkDAO;
import com.axp.domain.ScoreMark;


@Repository
public class ScoreMarkDaoImpl extends BaseDaoImpl<ScoreMark> implements ScoreMarkDAO{

	@Override
	public List<Object[]> findGroup(String remark,String newHands,String orderBy,Integer score) {
		
		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT "+remark+","+"count('"+remark+"')"+" FROM ("
						+"SELECT *  from score_mark  where newHands = '"+newHands+"'"
						+"ORDER BY "+ orderBy +" LIMIT 0,"+score+")AS c GROUP BY "+remark;
		SQLQuery sqlQuery=session.createSQLQuery(sql);
		
		return sqlQuery.list();
	}

	
	@Override
	public List<Object[]> findByOverdue(Integer day) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT userId ,validityTime,COUNT(userId) FROM score_mark_user WHERE isValid=1 and datediff(NOW(),validityTime)="+day+" GROUP BY userId";
		SQLQuery sqlQuery=session.createSQLQuery(sql);
		
		return sqlQuery.list();
	}

}
