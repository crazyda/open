package com.axp.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.axp.dao.OpenGoodsAllDAO;
import com.axp.dao.OpenGoodsDAO;
import com.axp.domain.OpenGoods;
import com.axp.domain.OpenGoodsAll;

@Repository
public class OpenGoodsAllDAOImpl extends BaseDaoImpl<OpenGoodsAll> implements OpenGoodsAllDAO {

	@Override
	public void delAll() {
		StringBuffer sb = new StringBuffer("delete OpenGoodsAll ");
		Session session = sessionFactory.getCurrentSession();
		Query createQuery = session.createQuery(sb.toString());	
		createQuery.executeUpdate();
		
	}

	
}
