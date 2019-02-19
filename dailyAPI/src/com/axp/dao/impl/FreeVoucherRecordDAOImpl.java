package com.axp.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.axp.dao.FreeVoucherRecordDAO;
import com.axp.domain.FreeVoucherRecord;
import com.axp.util.DateUtil;

@Repository
public class FreeVoucherRecordDAOImpl extends BaseDaoImpl<FreeVoucherRecord> implements FreeVoucherRecordDAO {

	@Override
	public Integer getCountByUserId(Integer userId){
		Session session = sessionFactory.getCurrentSession();
		String queryString = "select count(id) from FreeVoucherRecord " +
				"where endTime > now() and users.id =:userId and isValid = true and status = 0";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("userId", userId);
		Object o = queryObject.uniqueResult();
		if(o!=null){
			return Integer.parseInt(o.toString());
		}else{
			return 0;
		}
	}
	
	@Override
	public Integer updateStatus(Integer userId,Integer count,Integer status){
		Session session = sessionFactory.getCurrentSession();
		String queryString = "select distinct id from FreeVoucherRecord " +
				"where endTime > now() and users.id =:userId and isValid = true and status = 0 order by id desc";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("userId", userId);
		queryObject.setFirstResult(0);
		queryObject.setMaxResults(count);
		
		String ids = String.valueOf(queryObject.list()).replace(" ", "");
		ids = ids.substring(1, ids.length()-1);
		ids = StringUtils.isEmpty(ids)?"-1":ids;
		
		queryString = "update FreeVoucherRecord set status = "+status+" where id in ("+ids.toString()+")";
		Query updateObject = session.createQuery(queryString);
		return updateObject.executeUpdate();
	}
	
}
