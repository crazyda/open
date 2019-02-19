package com.axp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.ShopConcernDao;
import com.axp.domain.ShopConcern;

@Repository
public class ShopConcernDaoImpl extends BaseDaoImpl<ShopConcern> implements
		ShopConcernDao {
	
	@Override
	public Boolean isConcern(Integer sellerId, Integer userId){
		String queryString = "select count(id) from ShopConcern where users.id = :userId and seller.id = :sellerId and isfocus = true";
		Session session = sessionFactory.getCurrentSession();
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("userId", userId);
		queryObject.setInteger("sellerId", sellerId);
		int count = Integer.parseInt(queryObject.uniqueResult().toString());
		return count>0?true:false;
	}
	
}
