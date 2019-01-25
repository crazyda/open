package com.axp.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.axp.dao.OpenGoods2DAO;
import com.axp.domain.OpenGoods;
import com.axp.domain.OpenGoods2;
@Repository
public class OpenGoods2DAOImpl extends BaseDaoImpl<OpenGoods2> implements OpenGoods2DAO {

	@Override
	public void delAll() {
		
			StringBuffer sb = new StringBuffer("delete OpenGoods2 ");
			Session session = sessionFactory.getCurrentSession();
			Query createQuery = session.createQuery(sb.toString());	
			createQuery.executeUpdate();
		
	}

	@Override
	public void savebatch(List<OpenGoods2> ms) {
		Session session = null ;
		 if(ms != null && ms.size() > 0 ){
			 session = sessionFactory.openSession();
			
			 try {
				 OpenGoods2 openGood = null ;
				 for(int i =0;i<ms.size();i++){
					 openGood = ms.get(i);
					 session.save(openGood);
					 if(i % 1000 == 0){	 //50 --time660 ,100--time430  使用时间137613  使用时间139767
						 session.beginTransaction().commit();
						 session.clear();
					 }
				 }
				
			} catch (HibernateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}finally{
				session.close();
			}
		 }
		
	}

}
