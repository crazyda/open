package com.axp.dao.impl;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.INewRedPaperAddendumDao;
import com.axp.domain.NewRedPaperAddendum;

@Repository
public class NewRedPaperAddendumDaoImpl extends BaseDaoImpl<NewRedPaperAddendum> implements INewRedPaperAddendumDao{

	@Override
	public void updateOneAvail(Integer id, double userMoney) {
		Session session = sessionFactory.getCurrentSession();
		StringBuffer sb = new StringBuffer("update new_red_paper_addendum set avail = avail-? where id = ?");
		SQLQuery query = session.createSQLQuery(sb.toString());
		query.setParameter(0, userMoney);
		query.setParameter(1, id);
		query.executeUpdate();
	}

}
