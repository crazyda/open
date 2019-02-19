package com.axp.dao.impl;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.axp.dao.AdminuserCashpointRecordDAO;
import com.axp.domain.AdminuserCashpointRecord;

@Repository
public class AdminUserCashpointRecordImpl extends BaseDaoImpl<AdminuserCashpointRecord> implements AdminuserCashpointRecordDAO{

	@Override
	public void updateMoneyById(Integer itemId) {
			Session session = getSessionFactory().getCurrentSession();
			
			String queryString = "update AdminuserCashpointRecord set isValid = false where orderItem.id =:itemId ";
			Query queryObject = session.createQuery(queryString);
			
			queryObject.setParameter("itemId", itemId);

			queryObject.executeUpdate();
	}

}
