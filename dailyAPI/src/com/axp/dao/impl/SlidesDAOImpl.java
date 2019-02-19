package com.axp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.SlidesDAO;
import com.axp.domain.Slides;

@SuppressWarnings("unchecked")
@Repository
public class SlidesDAOImpl extends BaseDaoImpl<Slides> implements SlidesDAO {

	@Override
	public List<Slides> getList(Integer type){
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from Slides where  type = :type and isvalid = :isvalid ";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("isvalid", true);
		queryObject.setParameter("type", type);
		return queryObject.list();
	}

	@Override
	public List<Slides> getsList(Integer type, Integer zoneId) {
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from Slides as s where  s.adminUser.provinceEnum.id= :zoneId and type = :type and isvalid = :isvalid order by id desc ";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("isvalid", true);
		queryObject.setParameter("type", type);
		queryObject.setParameter("zoneId", zoneId);
		return queryObject.list();
	}

	@Override
	public List<Slides> getsListByZb(Integer type) {
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from Slides as s where  s.adminUser.level >94 and type = :type and isvalid = :isvalid ";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("isvalid", true);
		queryObject.setParameter("type", type);

		return queryObject.list();
	}

	@Override
	public List<Slides> getsListByAdminUserId(Integer aid,Integer type) {
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from Slides as s where  s.adminUser.id =:aid and type = :type and isvalid = :isvalid ";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("isvalid", true);
		queryObject.setParameter("type", type);
		queryObject.setParameter("aid", aid);
		return queryObject.list();
	}

}
