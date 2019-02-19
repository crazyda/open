package com.axp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.UsersDAO;
import com.axp.domain.AdminUser;
import com.axp.domain.Users;
import com.rongcloud.methods.User;

@Repository
@SuppressWarnings("unchecked")
public class UsersDAOImpl2 extends BaseDaoImpl<Users> implements UsersDAO {
	
	@Override
	public Users findByLoginName(String loginName) {
		Users user = null;
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from Users as model where model.isvalid = true and model.name = :loginName";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("loginName", loginName);
		@SuppressWarnings("unchecked")
		List<Users> usersList = queryObject.list();
		if(usersList.size()>0){
			user = usersList.get(0);
		}
		return user;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Users> findFans(Users user) {
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from Users as model where model.isvalid = true and model.invitecode = :invitecode";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("invitecode", user.getMycode());
		return queryObject.list();
	}
	
	@Override
	public void delete(Users persistentInstance) {
		persistentInstance.setIsvalid(false);
		super.saveOrUpdate(persistentInstance);
	}
	@Override
	public Users findById(java.lang.Integer id) {
		Users au = super.findById(id);
		if(!au.getIsvalid()){
			au = null;
		}
		return au;
	}
	@Override
	public Users findByIdIgnoreValid(java.lang.Integer id) {
		return super.findById(id);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Users> findInIds(String propertyName,String ids) {
		if(ids==null||ids.length()==0)
		{
			ids = "-1";
		}
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from Users as model where model."+ propertyName + " in ("+ids+")";
		Query queryObject = session.createQuery(queryString);
		return queryObject.list();
	}
	
	@Override
	public List<String> findAllUsersid() {
		Session session = sessionFactory.getCurrentSession();
		String queryString = "select userid from Users where isvalid = true and userid is not null and length(userid) = 64";
		Query queryObject = session.createQuery(queryString);
		return queryObject.list();
	}
	
	@Override
	public List<Users> findAllUsers() {
		Session session = sessionFactory.getCurrentSession();
		String queryString = " from Users where isvalid = true and userid is not null and length(userid) = 64";
		Query queryObject = session.createQuery(queryString);
		return queryObject.list();
	}

	@Override
	public void del(String ids) {
		updatePropertyByIDs("isvalid", false, ids, Users.class);
	}

	
	public List<String> findInId(String propertyName,String ids){
		if(ids==null||ids.length()==0)
		{
			ids = "-1";
		}
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from Users as model where model."+ propertyName + " in ("+ids+")";
		Query queryObject = session.createQuery(queryString);
		return queryObject.list();
	}
//	@Override
//	public void del(String ids) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public Users findByNameOrPhone(String presenterName) {
		Users users = null;
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery("select * from users where isvalid=true and name ='"+presenterName+"'");
		query.addEntity(Users.class);
		List<Users> usersList = query.list();
		if (usersList.size()>0) {
			users = usersList.get(0);
		}
		return users;
	}

	
	@Override
	public Users findByOpenId(String openId) {
		Users users = null;
		Session session = sessionFactory.getCurrentSession();
		Query query = null ;
		if(openId != null){
			System.out.println("open--"+openId);
			query = session.createQuery("from Users where isvalid =1 and openId=:openId");
			query.setParameter("openId", openId);
		}
		List<Users> usersList = query.list();
		if (usersList.size()>0) {
			users = usersList.get(0);
		}
		
		return users;
	}
	
	@Override
	public Users findByUnionId(String unionId) {
		Users users = null;
		Session session = sessionFactory.getCurrentSession();
		Query query = null ;
		if(unionId != null){
			System.out.println("unionId--"+unionId);
			query = session.createQuery("from Users where isvalid =1 and unionId=:unionId");
			query.setParameter("unionId", unionId);
		}
		List<Users> usersList = query.list();
		if (usersList.size()>0) {
			users = usersList.get(0);
		}
	
		return users;
	}

	
	
}