package com.axp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.ISystemConfigDao;
import com.axp.domain.SystemConfig;
@Repository
public class SystemConfigDaoImpl extends BaseDaoImpl<SystemConfig> implements ISystemConfigDao{

	
	@Override
	public SystemConfig findByParameter(String parameter) {
		
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from SystemConfig where parameter = '"+ parameter+"'";
		Query queryObject = session.createQuery(queryString);
		
		return (SystemConfig) queryObject.uniqueResult();
	}

}
