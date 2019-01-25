package com.axp.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.axp.dao.OpenGoodsDAO;
import com.axp.domain.OpenGoods;

@Repository
public class OpenGoodsDAOImpl extends BaseDaoImpl<OpenGoods> implements OpenGoodsDAO {

	@Override
	public void delAll() {
		StringBuffer sb = new StringBuffer("delete OpenGoods ");
		Session session = sessionFactory.getCurrentSession();
		Query createQuery = session.createQuery(sb.toString());	
		createQuery.executeUpdate();
		
	}

	@Override
	public void savebatch(List<OpenGoods> ms) {
		
		Session session = null ;
		 if(ms != null && ms.size() > 0 ){
			 session = sessionFactory.openSession();
			 try {
				 OpenGoods openGood2 = null ;
				 for(int i =0;i<ms.size();i++){
					 openGood2 = ms.get(i);
					 session.save(openGood2);
					 if(i % 10 == 0){	 //50 --time660 ,100--time430使用时间137642
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
