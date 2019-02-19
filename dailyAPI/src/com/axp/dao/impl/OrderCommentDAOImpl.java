package com.axp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.axp.dao.OrderCommentDAO;
import com.axp.domain.OrderComment;


@Repository
public class OrderCommentDAOImpl extends BaseDaoImpl<OrderComment> implements OrderCommentDAO {

	@Override
	public List<OrderComment> findListBySnapGoodId(Integer snapshotId){
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from OrderComment where snapshotId = :snapshotId and isvalid = true ";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("snapshotId", snapshotId);
		return queryObject.list();
	}
	
	@Override
	public List<OrderComment> findListBySnapGoodId(Integer snapshotId, Integer start, Integer size){
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from OrderComment where snapshotId = :snapshotId and isvalid = true";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("snapshotId", snapshotId);
		if(start!=null){
			queryObject.setFirstResult(start);
		}
		if(size!=null){
			queryObject.setMaxResults(size);
		}
		return queryObject.list();
	}
	
	@Override
	public Integer findCountBySnapGoodId(Integer snapshotId){
		Session session = sessionFactory.getCurrentSession();
		String queryString = "select count(id) from OrderComment where snapshotId = :snapshotId and isvalid = true ";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("snapshotId", snapshotId);
		return Integer.parseInt(queryObject.uniqueResult().toString());
	}
	
	@Override
	public Double findAvgBySnapGoodId(Integer snapshotId){
		Double ang = 10d;
		Session session = sessionFactory.getCurrentSession();
		String queryString = "select avg(score) from OrderComment where snapshotId = :snapshotId and isvalid = true ";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("snapshotId", snapshotId);
		Object result = queryObject.uniqueResult();
		if(result!=null){
			ang = Double.parseDouble(result.toString());
		}
		return ang;
	}

}
