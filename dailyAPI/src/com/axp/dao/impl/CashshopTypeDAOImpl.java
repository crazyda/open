package com.axp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.CashshopTypeDAO;
import com.axp.domain.CashshopTimes;
import com.axp.domain.CashshopType;
import com.axp.util.StringUtil;

@Repository
@SuppressWarnings("unchecked")
public class CashshopTypeDAOImpl extends BaseDaoImpl<CashshopType> implements
		CashshopTypeDAO {
	@Override
	public List<CashshopType> findImageText(){
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from CashshopType as model where model.isValid = :isValid";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("isValid", true);
		return queryObject.list();
	}
	
	
	
	@Override
	public List<CashshopType> findImageText(Integer type){
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from CashshopType as model where model.imageType.id = :typeId and model.isValid = :isValid and model.adminUser.id in (1,47)";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("isValid", true);
		queryObject.setParameter("typeId", type);
		return queryObject.list();
	}

	@Override
	public List<CashshopType> findImageText(Integer type, Integer zoneid) {
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from CashshopType as model where model.imageType.id = :typeId and model.isValid = :isValid and model.adminUser.provinceEnum.id = :zoneid";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("isValid", true);
		queryObject.setParameter("typeId", type);
		queryObject.setParameter("zoneid", zoneid);
		return queryObject.list();
	}


	@Override
	public List<CashshopType> findImageTextByVersion(Integer Version) {
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from CashshopType as model where model.isValid = :isValid and model.appVersion=:version";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("isValid", true);
		queryObject.setParameter("version", Version);
		return queryObject.list();
	}
}
