package com.axp.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.OpenJDGoods2DAO;
import com.axp.dao.OpenJDGoodsDAO;
import com.axp.domain.OpenJDGoods;
import com.axp.domain.OpenJDGoods2;
@Repository
public class OpenJDGoods2DAOImpl extends BaseDaoImpl<OpenJDGoods2> implements OpenJDGoods2DAO {

	@Override
	public void delAll() {
		StringBuffer sb = new StringBuffer("delete OpenJDGoods2 ");
		Session session = sessionFactory.getCurrentSession();
		Query createQuery = session.createQuery(sb.toString());	
		createQuery.executeUpdate();
		
	}

}
