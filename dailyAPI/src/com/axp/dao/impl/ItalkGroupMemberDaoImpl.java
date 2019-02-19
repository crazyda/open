package com.axp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.ItalkGroupMemberDao;
import com.axp.domain.ItalkGroupMember;
@Repository
public class ItalkGroupMemberDaoImpl extends BaseDaoImpl<ItalkGroupMember> implements
		ItalkGroupMemberDao {

	@Override
	public List<ItalkGroupMember> findByGroupId(Integer groupId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from ItalkGroupMember i where i.italkGroup.groupId="+groupId);
		
		return query.list();
	}

	@Override
	public List<ItalkGroupMember> findByGroup(Integer groupId, Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from ItalkGroupMember i where i.italkGroup.groupId="+groupId+"and i.users.id="+userId);
		
		return query.list();
	}

	@Override
	public List<ItalkGroupMember> findByUserId(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from ItalkGroupMember i where isValid = true and i.users.id="+userId);
		return query.list();
	}

	

}
