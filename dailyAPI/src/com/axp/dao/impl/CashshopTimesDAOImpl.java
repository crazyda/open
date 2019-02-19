package com.axp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.CashshopTimesDAO;
import com.axp.domain.CashshopTimes;
import com.axp.util.StringUtil;

@Repository
@SuppressWarnings("unchecked")
public class CashshopTimesDAOImpl extends BaseDaoImpl<CashshopTimes> implements
		CashshopTimesDAO {
	
	@Override
	public List<CashshopTimes> findTimesOfHQ(){
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from CashshopTimes as model where model.user.level=:level and model.isValid = :isValid";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("level", StringUtil.ADMIN);
		queryObject.setParameter("isValid", true);
		return queryObject.list();
	}
	
	
}
