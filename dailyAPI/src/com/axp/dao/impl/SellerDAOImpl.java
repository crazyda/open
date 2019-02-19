package com.axp.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.axp.dao.SellerDAO;
import com.axp.domain.AdminUser;
import com.axp.domain.Seller;

@Repository
public class SellerDAOImpl extends BaseDaoImpl<Seller> implements SellerDAO{

	@Override
	public List<Integer> getBrachesIds(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String queryString = "select id from Seller where adminUser.id = "+id+" and isValid = true and level = 1";
		Query queryObject = session.createQuery(queryString);
		return queryObject.list();
	}

	@Override
	public Seller findByUserId(java.lang.Integer userId) {
		Seller au = null;
			Session session = sessionFactory.getCurrentSession();
			@SuppressWarnings("unchecked")
			List<Seller> aulist = session.createQuery("from Seller where isvalid=true and users.id = ?0")
					.setParameter("0", userId).list();
			if (aulist.size() > 0) {
				au = aulist.get(aulist.size()-1);
			}
		return au;
	}
	@Override
	public Seller findByName(String name) {
		Seller au = null;
			Session session = sessionFactory.getCurrentSession();
			@SuppressWarnings("unchecked")
			List<Seller> aulist = session.createQuery("from Seller where name = :name and isvalid = true ")
					.setParameter("name", name).list();
			if (aulist.size() > 0) {
				au = aulist.get(0);
			}
		return au;
	}
	@Override
	public List<Seller> getSellerListByAdminId(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
			List<Seller> aulist = session.createQuery("from Seller where adminUser.id = "+userId+" and isvalid = true ").list();
			return aulist;	
		
	}

}
