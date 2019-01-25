package com.axp.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.AgentDAO;
import com.axp.dao.OpenClientDAO;
import com.axp.dao.OpenThemeDAO;
import com.axp.domain.Agent;
import com.axp.domain.OpenClient;
import com.axp.domain.OpenTheme;

@Repository
public class OpenThemeDAOImpl extends BaseDaoImpl<OpenTheme> implements OpenThemeDAO {
	@Override
	public void delAll() {
		StringBuffer sb = new StringBuffer("delete OpenTheme ");
		Session session = sessionFactory.getCurrentSession();
		Query createQuery = session.createQuery(sb.toString());	
		createQuery.executeUpdate();
		
	}
}
