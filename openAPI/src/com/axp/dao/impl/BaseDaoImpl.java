package com.axp.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.axp.domain.OpenJDCoponGoods;
import com.axp.util.StringUtil;

public class BaseDaoImpl<T> implements com.axp.dao.IBaseDao<T> {

	@Autowired
	protected SessionFactory sessionFactory;

	
	private Class<T> targetClass;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		// 获取类型
		ParameterizedType type = (ParameterizedType) this.getClass()
				.getGenericSuperclass();
		targetClass = (Class<T>) type.getActualTypeArguments()[0];
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void save(T t) {
		Session session = sessionFactory.getCurrentSession();
		session.save(t);
		session.flush();
		session.clear();
	}

	@Override
	public void saveOrUpdate(T t) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(t);
		session.flush();
	}

	@Override
	public void update(T t) {
		Session session = sessionFactory.getCurrentSession();
		session.update(t);
		session.flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		return (T) session.get(targetClass, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T merge(T t) {
		Session session = sessionFactory.getCurrentSession();
		return (T) session.merge(t);
	}

	

	
	@Override
	public void delete(T t) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(t);
		t.getClass().getName();
		session.flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> queryAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(targetClass).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		return (T) session.get(targetClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> queryAllByIsvalid() {// 找出有效数据
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(targetClass);
		criteria.add(Restrictions.eq("isvalid", true));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> queryAllByIsValid2() {// 找出有效数据
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(targetClass);
		criteria.add(Restrictions.eq("isValid", true));
		return criteria.list();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByIds(String ids) {//找出有效数据
		if(StringUtil.hasLength(ids)&&ids.split(",").length>0){
			String [] idss = ids.split(",");
			Integer [] idsi = new Integer[idss.length];
			for(int i=0;i<idss.length;i++){
				idsi[i] = Integer.parseInt(idss[i]);
			}
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(targetClass);
			criteria.add(Restrictions.in("id", idsi));
			return criteria.list();
		}else{
			return new ArrayList<T>();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> queryListByParameter(String parameter, Object value) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(targetClass);
		criteria.add(Restrictions.eq(parameter, value));
		return criteria.list();

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Integer queryCountByParameter(String parameter, Object value) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(targetClass);
		criteria.add(Restrictions.eq(parameter, value));
		return Integer.parseInt(criteria.uniqueResult().toString());

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> queryListByParameterByIsvalid(String parameter, Object value) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(targetClass);
		criteria.add(Restrictions.eq(parameter, value));
		criteria.add(Restrictions.eq("isvalid", true));
		return criteria.list();
		
		

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> queryListByParameterTwo(String parameter, Object value) {// 有些表isvalid写成了isValid
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(targetClass);
		criteria.add(Restrictions.eq("isValid", true));
		criteria.add(Restrictions.isNull(parameter));
		return criteria.list();

	}
	
	@Override
	public void updatePropertyByID(String propertyName, Object value, int id, Class<T> model) {
		String className = model.getName();
		String queryString = "update " + className + " set " + propertyName + " = ?0 where id = ?1";
		Session session = sessionFactory.getCurrentSession();
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("0", value);
		queryObject.setInteger("1", id);
		queryObject.executeUpdate();
	}
	
	public <E> void updatePropertyByIDs(String propertyName, Object value, String ids, Class<E> model) {
			Session session = getSessionFactory().getCurrentSession();
			String className = model.getName();
			String queryString = "update " + className + " set " + propertyName + " = ? where id in(" + ids + ")";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, value);

			queryObject.executeUpdate();
	}
	

	@Override
	public Object getPropertyById(String propertyName, Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String className = targetClass.getName();
		String queryString = "select " + propertyName + " from " + className + " where id =:id ";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("id", id);
		return queryObject.uniqueResult();
	}
	
	@Override
	public Object getPropertyById(String propertyName, Integer id, Class<T> model) {
		Session session = sessionFactory.getCurrentSession();
		String className = model.getName();
		String queryString = "select " + propertyName + " from " + className + " where id =:id ";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("id", id);
		return queryObject.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByProperty(String propertyName, Object value) {
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from " + targetClass.getName() + " as model where model." + propertyName + "=:propertyName order by id desc";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("propertyName", value);
		return queryObject.list();
	}
	@Override
	public List<T> findByPropertyIsValid(String propertyName, Object value) {
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from " + targetClass.getName() + " as model where model.isValid=true and model." + propertyName + "=:propertyName  order by id desc";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("propertyName", value);
		return queryObject.list();
	}

	@Override
	public void saveList(List<T> list) {
		Session session = sessionFactory.getCurrentSession();
		try {
			  
			 for (int i = 0; i < list.size(); i++) {
			    session.save(list.get(i));
			    if(i%1000 == 0){   
			        session.flush();  
			        session.clear();  
			    }
			 }
			
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



}
