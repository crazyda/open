package com.axp.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.OpenJDGoodsDAO;
import com.axp.domain.OpenJDGoods;
@Repository
public class OpenJDGoodsDAOImpl extends BaseDaoImpl<OpenJDGoods> implements OpenJDGoodsDAO {

	@Override
	public void delAll() {
		StringBuffer sb = new StringBuffer("delete OpenJDGoods ");
		Session session = sessionFactory.getCurrentSession();
		Query createQuery = session.createQuery(sb.toString());	
		createQuery.executeUpdate();
		
	}

}
