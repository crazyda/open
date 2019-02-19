package com.axp.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.axp.dao.CommodityTypeDAO;
import com.axp.domain.CommodityType;

@Repository
public class CommodityTypeDAOImpl extends BaseDaoImpl<CommodityType> implements CommodityTypeDAO {
	
	@Override
	public List<CommodityType> getListOfLv1(){
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(CommodityType.class);
		criteria.add(Restrictions.eq("isValid", true));
		criteria.add(Restrictions.eq("level", 1));
		return criteria.list();
	}
	
	@Override
	public List<CommodityType> getListByParentId(Integer parentId){
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(CommodityType.class);
		criteria.add(Restrictions.eq("isValid", true));
		criteria.add(Restrictions.eq("commodityType.id", parentId));
		return criteria.list();
	}
	
	@Override
	public List<CommodityType> getList(Integer modelId){
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(CommodityType.class);
		criteria.add(Restrictions.eq("isValid", true));
		criteria.add(Restrictions.eq("modelId", modelId));
		return criteria.list();
	}

	@Override
	public List<CommodityType> getAllType() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(CommodityType.class);
		criteria.add(Restrictions.eq("isValid", true));
		criteria.add(Restrictions.ne("id", 264));
		
		return criteria.list();
	}


	
	
	
	
}
