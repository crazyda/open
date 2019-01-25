package com.axp.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.OpenJDQueryCoponGoods2DAO;
import com.axp.dao.OpenJDQueryCoponGoodsDAO;
import com.axp.domain.OpenJDQueryCoponGoods;
import com.axp.domain.OpenJDQueryCoponGoods2;
@Repository
public class OpenJDQueryCoponGoods2DAOImpl extends BaseDaoImpl<OpenJDQueryCoponGoods2> implements OpenJDQueryCoponGoods2DAO {

	/* (non-Javadoc)
	 * @see com.axp.dao.OpenJDQueryCoponGoodsDAO#delAll()
	 */
	@Override
	public void delAll() {
		StringBuffer sb = new StringBuffer("delete OpenJDQueryCoponGoods2");
		Session session = sessionFactory.getCurrentSession();
		Query createQuery = session.createQuery(sb.toString());	
		createQuery.executeUpdate();
		
	}





}
