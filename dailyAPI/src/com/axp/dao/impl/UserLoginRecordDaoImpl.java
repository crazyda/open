package com.axp.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.UserLoginRecordDao;
import com.axp.domain.UserLoginRecord;
@Repository
public class UserLoginRecordDaoImpl extends BaseDaoImpl<UserLoginRecord> implements UserLoginRecordDao{

	
	@Override
	public List<Object[]> findZoneIdCount() {
		Session session = sessionFactory.getCurrentSession();
		String sql = "select distinct zoneId,COUNT(1)as zong from user_login_record where 1=1 GROUP BY zoneId ORDER BY zong DESC";

		 SQLQuery createSQLQuery = session.createSQLQuery(sql);
		return createSQLQuery.list();
	}
	
	
}
