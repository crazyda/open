package com.axp.dao.impl;


import java.sql.Timestamp;
import java.util.Random;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.IReBackOrderDao;
import com.axp.domain.ReBackOrder;
import com.axp.domain.ReGoodsorderItem;
import com.axp.domain.Users;
import com.axp.util.DateUtil;
@Repository
public class ReBackOrderDaoImpl  extends BaseDaoImpl<ReBackOrder> implements IReBackOrderDao{

	@Override
	public Integer getNotHandledCountBySeller(Integer sellerId){
		Session session = sessionFactory.getCurrentSession();
		String queryString = "select count(id) from ReBackOrder where seller.id = :sellerId and backstate = :backstate and isValid = true ";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("sellerId", sellerId);
		queryObject.setParameter("backstate", ReBackOrder.BACKSTATE_daishenhe);
		return Integer.parseInt(queryObject.uniqueResult().toString());
	}
	
	@Override
	public void saveBackOrder(ReGoodsorderItem item, Users user, String reason, Integer type, String imageJson,Integer backstate ){
		String backCode = DateUtil.formatDate("yyyyMMddHHmmssss", DateUtil.getNow());
		backCode = backCode + (new Random().nextInt(90)+10);
		
		ReBackOrder back = new ReBackOrder();
		back.setBackCode(backCode);
		back.setBackstate(backstate);
		back.setCreateTime(new Timestamp(System.currentTimeMillis()));
		back.setGoodid(item.getGoodsId());
		back.setGoodQuantity(item.getGoodQuantity());
		back.setImage(item.getGoodPic());
		back.setIsValid(true);
		back.setBackmoney(item.getPayPrice());
		back.setMallClass(item.getMallClass());
		back.setMallId(item.getMallId());
		back.setOrderItem(item);
		back.setPayCashpoint(item.getPayCashpoint());
		back.setPaymoney(item.getPayPrice());
		back.setPayScore(item.getPayScore());
		back.setPaytype(item.getOrder().getPayType());
		back.setReason(reason);
		back.setSeller(item.getOrder().getSeller());
		back.setSellerCode(item.getOrder().getSeller().getId());
		back.setType(type);
		back.setUser(user);
		back.setImage(imageJson);
		back.setExOrderStatus(item.getStatus());//申请退单的原单状态
		save(back);
	}
	
}
