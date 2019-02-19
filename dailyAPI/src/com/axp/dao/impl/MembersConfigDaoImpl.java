package com.axp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.IMembersConfigDao;
import com.axp.domain.MembersConfig;

@SuppressWarnings("unchecked")
@Repository
public class MembersConfigDaoImpl extends BaseDaoImpl<MembersConfig> implements IMembersConfigDao {
	
	@Override
	public List<MembersConfig> getVipConfigList(){
		String queryString = "from MembersConfig where isValid = true";
		Session session = sessionFactory.getCurrentSession();
		Query queryObject = session.createQuery(queryString);
		return queryObject.list();
	}
	
}
