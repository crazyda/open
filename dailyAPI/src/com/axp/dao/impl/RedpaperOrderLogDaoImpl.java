package com.axp.dao.impl;

import com.axp.dao.IRedpaperOrderLogDao;
import com.axp.domain.NrpOrderLog;

public class RedpaperOrderLogDaoImpl extends BaseDaoImpl<NrpOrderLog> implements IRedpaperOrderLogDao {
//
//	public void updateLogStatus(Integer relateId, String relateBean){
//		StringBuffer sb = new StringBuffer("update nrp_order_log set status= :status where relateId = :relateId and relateBean = ");
//		SQLQuery query = getSession().createSQLQuery(sb.toString());
//		query.setParameter(0, 0);
//		query.setParameter(1, userCRId);
//		try {
//			query.executeUpdate();
//			
//		} catch (HibernateException e) {
//			e.printStackTrace();
//		}
//				
//	}
//	
//	public List<Object[]> getNrplIdList(Integer userCRId){
//		StringBuffer sb = new StringBuffer("select log_id,user_money from nrp_order_log where user_cr_id = ? and status =?");
//		SQLQuery query = getSession().createSQLQuery(sb.toString());
//		query.setParameter(0, userCRId);
//		query.setParameter(1, NrpOrderLog.STATUS_PAY);
//		return query.list();
//	}
//	
//	public List<Double> getUserMoney(Integer logId,Integer userCRId){
//		StringBuffer sb = new StringBuffer("select user_money from nrp_order_log where user_cr_id =? and log_id =? and status = ?");
//		SQLQuery query = getSession().createSQLQuery(sb.toString());
//		query.setParameter(0, userCRId);
//		query.setParameter(1, logId);
//		query.setParameter(2, NrpOrderLog.STATUS_PAY);
//		
//		return query.list();
//		
//	}
}
