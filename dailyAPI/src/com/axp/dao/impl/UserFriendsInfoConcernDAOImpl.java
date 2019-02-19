package com.axp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.UserFriendsInfoConcernDAO;
import com.axp.domain.UserFriendsInfo;
import com.axp.domain.UserFriendsInfoConcern;
import com.axp.domain.Users;

@Repository
public class UserFriendsInfoConcernDAOImpl extends BaseDaoImpl<UserFriendsInfoConcern> implements UserFriendsInfoConcernDAO {

	@Override
	public List<UserFriendsInfoConcern> getUserConcern(Users user, UserFriendsInfo userFriendsInfo) {
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from UserFriendsInfoConcern  where  userFriendsInfoId = :userFriendsInfoId and userId = :userId";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("userFriendsInfoId", userFriendsInfo.getId());
		queryObject.setParameter("userId", user.getId());
		return queryObject.list();
		
	}
	
}
