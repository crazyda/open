package com.axp.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.OpenJDCoponGoods2DAO;
import com.axp.dao.OpenJDQueryCoponGoods2DAO;
import com.axp.dao.OpenJDQueryCoponGoodsDAO;
import com.axp.domain.OpenJDCoponGoods;
import com.axp.domain.OpenJDCoponGoods2;
import com.axp.domain.OpenJDQueryCoponGoods;
import com.axp.domain.OpenJDQueryCoponGoods2;
@Repository
public class OpenJDCoponGoods2DAOImpl extends BaseDaoImpl<OpenJDCoponGoods2> implements OpenJDCoponGoods2DAO {

	/* (non-Javadoc)
	 * @see com.axp.dao.OpenJDQueryCoponGoodsDAO#delAll()
	 */
	@Override
	public void delAll() {
		StringBuffer sb = new StringBuffer("delete OpenJDCoponGoods2");
		Session session = sessionFactory.getCurrentSession();
		Query createQuery = session.createQuery(sb.toString());	
		createQuery.executeUpdate();
		
	}





}
