package com.axp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.SellerMainPageDAO;
import com.axp.domain.SellerMainPage;



@Repository("sellerMainPageDAO")
public class SellerMainPageDAOImpl extends BaseDaoImpl<SellerMainPage> implements SellerMainPageDAO{
	
	@Override
	public SellerMainPage findOneBySellerId(Integer sellerId){
		SellerMainPage sellerMainPage = null;
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from SellerMainPage where seller.id = :sellerId and isValid = true";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("sellerId", sellerId);
		List<SellerMainPage> list = queryObject.list();
		if(list.size()>0){
			sellerMainPage = list.get(0);
		}
		return sellerMainPage;
	}

	@Override
	public List<SellerMainPage> getSellerMainPageIds(String ids) {
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from SellerMainPage where seller.id in("+ids+") and isValid = true";
		Query queryObject = session.createQuery(queryString);
		return queryObject.list();
	}

}
