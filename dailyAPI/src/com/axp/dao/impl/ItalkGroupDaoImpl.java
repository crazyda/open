package com.axp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.ItalkGroupDao;
import com.axp.domain.ItalkGroup;
import com.axp.domain.Users;




@Repository
public class ItalkGroupDaoImpl extends BaseDaoImpl<ItalkGroup> implements ItalkGroupDao {

	
	public List<ItalkGroup> findByGroupId(Integer groupId) {
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from ItalkGroup where groupId="+groupId+" order by createTime desc";
		
		Query queryObject = session.createQuery(queryString);
		
		return queryObject.list();
	}

	@Override
	public List<ItalkGroup> findByUserId(Users user) {
		
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from ItalkGroup where userId="+user.getId()+"order by createTime desc";
		
		Query queryObject = session.createQuery(queryString);
		
		return queryObject.list();
	}
	
	
	

}
