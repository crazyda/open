package com.axp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.GoodsDAO;
import com.axp.domain.Goods;

@Repository
public class GoodsDAOImpl extends BaseDaoImpl<Goods> implements GoodsDAO {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Goods> getAdverBySellerId(Integer sellerId){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from goods where seller.id = :sellerId and type = 1 and isvalid = true");
		query.setParameter("sellerId", sellerId);
		return query.list();
	}
}