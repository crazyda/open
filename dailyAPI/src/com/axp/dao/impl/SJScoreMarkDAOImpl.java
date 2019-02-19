package com.axp.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.axp.dao.DLScoreMarkDAO;
import com.axp.dao.SJScoreMarkDAO;
import com.axp.domain.AdminUser;
import com.axp.domain.DLScoreMark;
import com.axp.domain.SJScoreMark;


@Repository
public class SJScoreMarkDAOImpl extends BaseDaoImpl<SJScoreMark> implements SJScoreMarkDAO{
	@Autowired
	DLScoreMarkDAO dlScoreMarkDAO;
	
	@Override
	public List<Object[]> findMonToSun() {
		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT COUNT(1),"+
					"(SELECT count(1) FROM score_mark_sj WHERE isValid=1 AND userId is not NULL AND DATEDIFF(refreshTime,NOW())=0),"+
					"(SELECT count(1) FROM score_mark_sj WHERE isValid=1 AND userId is not NULL AND DATEDIFF(refreshTime,NOW())=1),"+
					"(SELECT count(1) FROM score_mark_sj WHERE isValid=1 AND userId is not NULL AND DATEDIFF(refreshTime,NOW())=2),"+
					"(SELECT count(1) FROM score_mark_sj WHERE isValid=1 AND userId is not NULL AND DATEDIFF(refreshTime,NOW())=3),"+
					"(SELECT count(1) FROM score_mark_sj WHERE isValid=1 AND userId is not NULL AND DATEDIFF(refreshTime,NOW())=4),"+
					"(SELECT count(1) FROM score_mark_sj WHERE isValid=1 AND userId is not NULL AND DATEDIFF(refreshTime,NOW())=5),"+
					"(SELECT count(1) FROM score_mark_sj WHERE isValid=1 AND userId is not NULL AND DATEDIFF(refreshTime,NOW())=6)" +
					"FROM  score_mark_sj WHERE isValid=1 AND userId is not NULL ";

		 SQLQuery createSQLQuery = session.createSQLQuery(sql);
		return createSQLQuery.list();
	}

	@Override
	public void saveDlsmsTemp(List<DLScoreMark> dlsmsTemp,AdminUser au) {
		Timestamp createTime = new Timestamp(System.currentTimeMillis());
		try {
			Session session = sessionFactory.getCurrentSession();
			//Transaction tx= session.beginTransaction(); 
			 for (int i = 0; i < dlsmsTemp.size(); i++) {
				 DLScoreMark dlsm = dlsmsTemp.get(i);
				 	dlsm.setAdminUserSeller(au);
					dlsm.setRefreshTime(createTime);
					dlScoreMarkDAO.saveOrUpdate(dlsm);
					
					SJScoreMark sjsm = new SJScoreMark();
					sjsm.setAdminUser(au);
					sjsm.setCreateTime(createTime);
					sjsm.setDlScoreMark(dlsm);
					sjsm.setIsValid(true);
					sjsm.setRefreshTime(createTime);
	                session.save(dlsmsTemp.get(i));
	                if(i%500 == 0){   //每一千条刷新并写入数据库  
	                    session.flush();  
	                    session.clear();  
	                }
	                
	            }
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
		}

		
	}

}
