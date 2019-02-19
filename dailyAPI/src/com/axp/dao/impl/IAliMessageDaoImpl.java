package com.axp.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.IAliMessageDao;
import com.axp.domain.AliMessage;

@Repository("iAliMessageDao")
public class IAliMessageDaoImpl extends BaseDaoImpl<AliMessage> implements IAliMessageDao{

	@Override
	public AliMessage getAppKey(String template_code) {
		
		Session session = sessionFactory.getCurrentSession(); 
    	Query query = session.createQuery("from AliMessage where isValid=1 and templateCode=:template_code");
    	query.setString("template_code", template_code);
		return (AliMessage) query.uniqueResult();
	}


}
