package com.axp.dao.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.axp.dao.ICashmoneyRecordDao;
import com.axp.domain.CashmoneyRecord;
import com.axp.domain.ReGoodsorder;
import com.axp.domain.Users;

@Repository
public class CashmoneyRecordDaoImpl extends BaseDaoImpl<CashmoneyRecord> implements ICashmoneyRecordDao {
	
	private static Map<Integer  , String > descripConfig; 
	private static Map<Integer  , String > titleDescripConfig; //标题
	
	@PostConstruct
	private void init() {
		if(descripConfig==null){
			descripConfig = new HashMap<Integer, String>();
			descripConfig.put(BACK, "退货返还现金");
			descripConfig.put(BUY, "购买商品扣除钱包余额");
			descripConfig.put(CASH, "提现扣除钱包余额");
			descripConfig.put(BUYMERGE, "购买商品合并支付扣除钱包余额");
			descripConfig.put(SHOP, "一元开店");
		}
		if(titleDescripConfig == null){
			titleDescripConfig = new HashMap<Integer, String>();
			titleDescripConfig.put(BUY, "购物");
			titleDescripConfig.put(BACK, "退货");
			titleDescripConfig.put(CASH, "提现");
			titleDescripConfig.put(BUYMERGE, "购物合并支付");
		}
	}
	
	@Override
	public CashmoneyRecord updateRecord(Users user,Double updateMoney,Integer type){
		if(updateMoney==null||updateMoney==0){
			return null;
		}
		
		CashmoneyRecord record = new CashmoneyRecord();
		record.setBeforeMoney(user.getMoney());
		record.setMoney(updateMoney);
		record.setAfterMoney(user.getMoney()+updateMoney);
		record.setCreateTime(new Timestamp(System.currentTimeMillis()));
		record.setIsValid(true);
		record.setRemark(descripConfig.get(type));
		record.setUsersByUserId(user);
		record.setType(type);
		this.save(record);
		return record;
	}
	

	@Override
	public CashmoneyRecord updateRecordForBuy(Users user,Double updateMoney,ReGoodsorder order){
		CashmoneyRecord record = updateRecord(user,updateMoney,BUY);
		record.setRelateId(order.getId());
		record.setRelateName(order.getClass().getName());
		this.update(record);
		return record;
	}
	
	@Override
	public CashmoneyRecord updateRecordByThirdParty(Users user,Double updateMoney,ReGoodsorder order){
		CashmoneyRecord record = updateRecordForBuy(user,updateMoney,order);
		record.setAccount(order.getPayAccount());
		record.setAccountType(order.getPayTypeChar());
		this.update(record);
		return record;
	}

	@Override
	public CashmoneyRecord updateRecord(Users user, Double updateMoney,
			Integer type, String remark) {
		if(updateMoney==null||updateMoney==0){
			return null;
		}
		CashmoneyRecord record = new CashmoneyRecord();
		record.setMoney(updateMoney);
		record.setAfterMoney(user.getMoney()+updateMoney);
		record.setCreateTime(new Timestamp(System.currentTimeMillis()));
		record.setIsValid(true);
		record.setRemark(descripConfig.get(type));
		record.setUsersByUserId(user);
		record.setType(type);
		record.setRelateName(remark);
		this.save(record);
		return record;
	}

	@Override
	public double getSumMoneyByType(Users user, Integer type) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void updateMoneyById(Integer itemId) {
			Session session = getSessionFactory().getCurrentSession();
			
			String queryString = "update CashmoneyRecord set isValid = false where relateId =:itemId ";
			Query queryObject = session.createQuery(queryString);
			
			queryObject.setParameter("itemId", itemId);

			queryObject.executeUpdate();
	}
	
	
	
}