package com.axp.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.OpenJDQueryCoponGoodsDAO;
import com.axp.domain.OpenJDQueryCoponGoods;
@Repository
public class OpenJDQueryCoponGoodsDAOImpl extends BaseDaoImpl<OpenJDQueryCoponGoods> implements OpenJDQueryCoponGoodsDAO {

	/* (non-Javadoc)
	 * @see com.axp.dao.OpenJDQueryCoponGoodsDAO#delAll()
	 */
	@Override
	public void delAll() {
		StringBuffer sb = new StringBuffer("delete OpenJDQueryCoponGoods");
		Session session = sessionFactory.getCurrentSession();
		Query createQuery = session.createQuery(sb.toString());	
		createQuery.executeUpdate();
		
	}





}
