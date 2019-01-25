package com.axp.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.OpenJDQueryCoponGoodsDAO;
import com.axp.dao.OpenJDQueryExplosiveGoods2DAO;
import com.axp.domain.OpenJDQueryCoponGoods;
import com.axp.domain.OpenJDQueryExplosiveGoods2;
@Repository
public class OpenJDQueryExplosiveGoods2Impl extends BaseDaoImpl<OpenJDQueryExplosiveGoods2> implements OpenJDQueryExplosiveGoods2DAO {

	/* (non-Javadoc)
	 * @see com.axp.dao.OpenJDQueryCoponGoodsDAO#delAll()
	 */
	@Override
	public void delAll() {
		StringBuffer sb = new StringBuffer("delete OpenJDQueryExplosiveGoods2 ");
		Session session = sessionFactory.getCurrentSession();
		Query createQuery = session.createQuery(sb.toString());	
		createQuery.executeUpdate();
		
	}





}
