package com.axp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.TkldPidDao;
import com.axp.domain.TkldPid;
@Repository("tkldPidDao")
public class TkldPidDaoImpl extends BaseDaoImpl<TkldPid> implements TkldPidDao{

	@Override
	public List<TkldPid> findByUserId(Integer UserId) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "from TkldPid as tk inner join fetch tk.users where tk.isValid = true and tk.users.id="+UserId;
		Query query = session.createQuery(sql);
		//Query query = session.createQuery("from TkldPid as tk inner join fetch tk.users where tk.isValid = true and tk.users.id=:userId");
		//query.setParameter("tk.users.id", UserId);
		
		return query.list();
	}

}
