package com.axp.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.axp.dao.OpenGoods2DAO;
import com.axp.dao.OpenGoodsAll2DAO;
import com.axp.domain.OpenGoods;
import com.axp.domain.OpenGoods2;
import com.axp.domain.OpenGoodsAll2;
@Repository
public class OpenGoods2AllDAOImpl extends BaseDaoImpl<OpenGoodsAll2> implements OpenGoodsAll2DAO {

	@Override
	public void delAll() {
		
			StringBuffer sb = new StringBuffer("delete OpenGoodsAll2 ");
			Session session = sessionFactory.getCurrentSession();
			Query createQuery = session.createQuery(sb.toString());	
			createQuery.executeUpdate();
		
	}

		

}
