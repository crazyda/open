package com.axp.dao.impl;import java.util.List;import org.hibernate.Query;import org.hibernate.Session;import org.springframework.stereotype.Repository;import com.axp.dao.IReGoodsOfScoreMallDao;import com.axp.domain.ReGoodsOfScoreMall;@SuppressWarnings("unchecked")@Repository public class ReGoodsOfScoreMallDaoImpl extends BaseDaoImpl<ReGoodsOfScoreMall> implements IReGoodsOfScoreMallDao {		@Override	public String getSellerIdsOfHomePage(Integer goodsSize) {		Session session = sessionFactory.getCurrentSession();		String queryString = "select snapshotGoods.seller.id as goodsId,count(id) as goodsCount from ReGoodsOfScoreMall " +				"where addedTime < now() and shelvesTime > now() and isValid = true and isChecked = true " +				"group by snapshotGoods.seller.id";		Query queryObject = session.createQuery(queryString);		List<Object[]> list = queryObject.list();		String ids = "-1";		for (Object[] data : list) {			if(Integer.parseInt(data[1].toString())>goodsSize){				ids += "," + data[0].toString();			}		}		return ids;	}	}