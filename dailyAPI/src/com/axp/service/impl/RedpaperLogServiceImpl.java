package com.axp.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.axp.dao.INewRedPaperLogDao;
import com.axp.dao.IRedpaperOrderLogDao;
import com.axp.domain.NewRedPaperLog;
import com.axp.domain.NrpOrderLog;
import com.axp.domain.Users;
import com.axp.service.RedpaperLogService;


public class RedpaperLogServiceImpl extends BaseServiceImpl<NewRedPaperLog> implements RedpaperLogService{
	
//	@Autowired INewRedPaperLogDao nrplDao;
//	@Autowired IRedpaperOrderLogDao nrpoDao;
//
//	public void saveRedPaperOrderLog(Users user, Double payValue, Integer relateId, String relateBean) {
//
//		//当前红包已经支付金额
//		double alreadyPayValue = 0;
//
//		//获得用户领取红包列表记录
//		NewRedPaperLog nrpl = null;
//		Map<Integer,Double> addMap = new HashMap<Integer, Double>();
//		Map<Integer,Integer> settingMap = new HashMap<Integer, Integer>();
//		try {
//			int count = - 1;
//			while(count<0){
//				nrpl = nrplDao.getMinNewRedPaperLog(user.getId());
//				
//				NrpOrderLog nodl = new NrpOrderLog();
//				nodl.setCreateTime(new Timestamp(System.currentTimeMillis()));
//				nodl.setIsvalid(true);
//				nodl.setRelateBean(relateBean);
//				nodl.setRelateId(relateId);
//				nodl.setStatus(NrpOrderLog.STATUS_PAY);
//				nodl.setNrpl(nrpl);
//				if(payValue>alreadyPayValue+nrpl.getAvail()){
//					alreadyPayValue+=nrpl.getAvail();
//					nodl.setUserMoney(nrpl.getAvail());
//					nrpl.setStatus(NewRedPaperLog.STATUS_ALLPAY);
//					nrpl.setAvail(0.00);					
//					nrplDao.update(nrpl);
//				}else{
//					nodl.setUserMoney(payValue-alreadyPayValue);
//					nrpl.setStatus(NewRedPaperLog.STATUS_SPLITPAY);
//					nrpl.setAvail(nrpl.getAvail()-nodl.getUserMoney());
//					nrplDao.update(nrpl);
//					count =1;
//				}
//				if(settingMap.get(nrpl.getSetting().getId())==null){
//					settingMap.put(nrpl.getSetting().getId(), 1);
//				}else{
//					settingMap.put(nrpl.getSetting().getId(), settingMap.get(nrpl.getSetting().getId())+1);
//				}
//				if(addMap.get(nrpl.getAddendum().getId())==null){
//					addMap.put(nrpl.getAddendum().getId(),nodl.getUserMoney());	
//				}else{
//					addMap.put(nrpl.getAddendum().getId(),addMap.get(nrpl.getAddendum().getId())+nodl.getUserMoney());
//				}
//				nrpoDao.save(nodl);
//				
//			}
//			for(Map.Entry<Integer, Integer> entry: settingMap.entrySet()){
//				nrpsDao.updateAllNunUsed(entry.getKey(),entry.getValue(),"+");
//			}
//			for (Map.Entry<Integer, Double> entry : addMap.entrySet()) {  
//				nrpaDao.updateOneAvail(entry.getKey(),entry.getValue());
//			}  
//			Utility.transactionCommit();
//		    Utility.closeSession();
//		} catch (Exception e) {
//			e.printStackTrace();
//			Utility.transactionRollback();
//			Utility.closeSession();
//		}
//		
//	}
//
//	@Override
//	public void drawbackRedPaper(Integer userCRId) {
//		Utility.transactionBegin();
//		try {
//			QueryModel qm = new QueryModel();
//			
//			//修改红包支付记录
//			qm.clearQuery();
//			qm.combPreEquals("userCR.id", userCRId,"userCRID");
//			List<NrpOrderLog> nrpOrderLogList = DateBaseDAO.findLists(NrpOrderLog.class, qm);	
//			NewRedPaperSettingDAO nrpsDao = new NewRedPaperSettingDAO();
//			NewRedPaperLog nrpl;
//			Map<Integer,Integer> settingMap = new HashMap<Integer, Integer>();
//			
//			for(int i=0;i<nrpOrderLogList.size();i++){		
//				//修改红包支付记录
//				nrpOrderLogList.get(i).setStatus(NrpOrderLog.STATUS_DRAWBACK);
//				
//				//获取当前红包领取记录
//				qm.clearQuery();
//				qm.combPreEquals("id",nrpOrderLogList.get(i).getNrpl().getId());
//				nrpl = DateBaseDAO.findLists(NewRedPaperLog.class, qm).get(0);			
//				//判断当前红包是否有余额
//				if(nrpl.getMoney()==nrpl.getAvail()+nrpOrderLogList.get(i).getUserMoney()){
//					nrpl.setStatus(NewRedPaperLog.STATUS_NOTUSR);
//				}else{			
//					nrpl.setStatus(NewRedPaperLog.STATUS_SPLITPAY);
//				}
//				nrpl.setAvail(nrpl.getAvail()+nrpOrderLogList.get(i).getUserMoney());
//				//获取红包附表记录
//				qm.clearQuery();
//				qm.combEquals("id", nrpl.getAddendum().getId());
//				NewRedPaperAddendum addendum = DateBaseDAO.findLists(NewRedPaperAddendum.class, qm).get(0);
//				if(settingMap.get(nrpl.getSetting().getId())==null){
//					settingMap.put(nrpl.getSetting().getId(), 1);
//				}else{
//					settingMap.put(nrpl.getSetting().getId(), settingMap.get(nrpl.getSetting().getId())+1);
//				}
//				addendum.setAvail(addendum.getAvail()+nrpOrderLogList.get(i).getUserMoney());
//				Utility.getSession().update(nrpOrderLogList.get(i));
//				Utility.getSession().update(nrpl);
//				Utility.getSession().update(addendum);					
//			}
//			for(Map.Entry<Integer, Integer> entry: settingMap.entrySet()){
//				nrpsDao.updateAllNunUsed(entry.getKey(),entry.getValue(),"-");
//			}
//			Utility.transactionCommit();
//			Utility.closeSession();
//		} catch (Exception e) {
//			Utility.transactionRollback();
//			Utility.closeSession();
//		}
//		
//	}

}
