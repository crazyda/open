package com.axp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.ReGoodsorderItemDao;
import com.axp.domain.ReGoodsOfLockMall;
import com.axp.domain.ReGoodsorder;
import com.axp.domain.ReGoodsorderItem;
@Repository("reGoodsorderItemDao")
public class ReGoodsorderItemDaoImpl extends BaseDaoImpl<ReGoodsorderItem> implements ReGoodsorderItemDao{

	@Override
	public List<ReGoodsorderItem> findByParent(Integer parentId){
		return super.queryListByParameter("order.id", parentId);
	}
	
	public List<ReGoodsorderItem> getItemList(String str){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from ReGoodsorderItem where id in("+str+")");
		return query.list();
	}

	@Override
	public void updateStatusByParent(Integer status, Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Query createQuery = session.createQuery("update ReGoodsorderItem set status = "+status +"" +
				" where order.id = "+id+" and isBack in ("+ReGoodsorder.ke_tui_dan+","+ReGoodsorder.bu_ke_tui_dan+")");
		createQuery.executeUpdate();
	}

	@Override
	public void deleteByOrderId(Integer orderId) {
		StringBuffer sb = new StringBuffer("update ReGoodsorderItem set isValid=0 where order.id = "+orderId);
		Session session = sessionFactory.getCurrentSession();
		Query createQuery = session.createQuery(sb.toString());	
		createQuery.executeUpdate();
	}
	
	@Override
	public Integer getQuantitySumByGoodsObject(String goodsObject, Integer userId,Boolean isTeam) {
		Integer result = 0;
		String mallType = goodsObject.substring(0, 3);
		String goodsId = goodsObject.substring(3, goodsObject.length());
		StringBuffer sb = new StringBuffer("select sum(goodQuantity) from ReGoodsorderItem " +
				"where user.id = :userId and goodsId = :goodsId and mallClass = :mallClass and isValid = true and status >= 0");
		if(isTeam){
			sb.append(" AND isTeam=true");
		}
		Session session = sessionFactory.getCurrentSession();
		Query createQuery = session.createQuery(sb.toString());	
		createQuery.setParameter("userId", userId);
		createQuery.setParameter("goodsId", Integer.parseInt(goodsId));
		createQuery.setParameter("mallClass", mallType);
		Object o = createQuery.uniqueResult();
		if(o!=null){
			result = Integer.parseInt(o.toString());
		}
		return result;
	}

	
	@Override
	public List<ReGoodsorderItem> findByLockGoods(ReGoodsOfLockMall lm) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "";
		Query query = session.createQuery(sql);

		return query.list();
	}

	@Override
	public List<ReGoodsorderItem> findByParticipant(Integer gameType, String string) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "select * from ReGoodsorderItem where isValid=1 and gameType ="+gameType+" group by "+string;
		Query createQuery = session.createQuery(sql);	
		
		
		
		return createQuery.list();

	}
	
}
