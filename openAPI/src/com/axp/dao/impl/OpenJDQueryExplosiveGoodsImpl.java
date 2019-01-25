package com.axp.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.OpenJDQueryCoponGoodsDAO;
import com.axp.dao.OpenJDQueryExplosiveGoodsDAO;
import com.axp.domain.OpenJDQueryCoponGoods;
import com.axp.domain.OpenJDQueryExplosiveGoods;
@Repository
public class OpenJDQueryExplosiveGoodsImpl extends BaseDaoImpl<OpenJDQueryExplosiveGoods> implements OpenJDQueryExplosiveGoodsDAO {

	/* (non-Javadoc)
	 * @see com.axp.dao.OpenJDQueryCoponGoodsDAO#delAll()
	 */
	@Override
	public void delAll() {
		StringBuffer sb = new StringBuffer("delete OpenJDQueryCoponGoods ");
		Session session = sessionFactory.getCurrentSession();
		Query createQuery = session.createQuery(sb.toString());	
		createQuery.executeUpdate();
		
	}





}
