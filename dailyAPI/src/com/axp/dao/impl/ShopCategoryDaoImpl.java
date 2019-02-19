package com.axp.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.axp.dao.ShopCategoryDao;
import com.axp.domain.CommodityType;
import com.axp.domain.ShopCategory;

@Repository
public class ShopCategoryDaoImpl extends BaseDaoImpl<ShopCategory> implements ShopCategoryDao{

	@Override
	public List<ShopCategory> getList() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ShopCategory.class);
		criteria.add(Restrictions.eq("isValid", true));
		
		return criteria.list();
	}
}
