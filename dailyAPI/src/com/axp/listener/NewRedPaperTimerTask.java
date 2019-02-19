package com.axp.listener;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.axp.dao.DateBaseDAO;
import com.axp.dao.ICashmoneyRecordDao;
import com.axp.dao.UsersDAO;
import com.axp.dao.impl.AdminUserCashpointRecordImpl;
import com.axp.domain.AdminUser;
import com.axp.domain.AdminuserCashpointRecord;
import com.axp.domain.Bonus;
import com.axp.domain.CashmoneyRecord;
import com.axp.domain.ScoreMark;
import com.axp.domain.ScoreModel;
import com.axp.domain.Users;
import com.axp.service.IReGoodsOfLockMallService;
import com.axp.service.IRedpaperService;
import com.axp.service.ScoreMarkService;
import com.axp.service.impl.ReGoodsOfLockMallServiceImpl;
import com.axp.util.CalcUtil;
import com.axp.util.DateUtil;
import com.axp.util.QueryModel;
import com.axp.util.ToolSpring;
import com.axp.util.UrlUtil;
@Component
@EnableScheduling
public class NewRedPaperTimerTask {	
	@Autowired
	public DateBaseDAO dateBaseDAO;
	@Autowired
	public ScoreMarkService scoreMarkService;
	@Autowired
	public UsersDAO userDAO;	
	@Autowired
	public ICashmoneyRecordDao cashmoneyRecordDAO;
	@Autowired
	public IReGoodsOfLockMallService reGoodsOfLockMallService;
	/*
	 * 被调用具体的方法
	 */

	@Scheduled(cron="0 0 12 * * ?")
	public void userScoreTimerTask(){
		System.out.println("提醒用户积分到期开始");
		List<Object[]> time3 = scoreMarkService.findByOverdue(3);
		List<Object[]> time7 = scoreMarkService.findByOverdue(7);
		for(int i=0;i<time3.size();i++){
			Users user = userDAO.findById(Integer.valueOf(time3.get(i)[0].toString()));
			if(user.getUnionId() != null ){
				String param = "unionId="+user.getUnionId()+"&linkType=3&integral="+time3.get(i)[2]+"&time="+Timestamp.parse(time3.get(i)[1].toString())/1000;
				UrlUtil.sendGzhMsg(6, param);
			}
		}
		for(int i=0;i<time7.size();i++){
			Users user = userDAO.findById(Integer.valueOf(time7.get(i)[0].toString()));
			if(user.getUnionId() != null ){
				String param = "unionId="+user.getUnionId()+"&linkType=3&integral="+time7.get(i)[2]+"&time="+Timestamp.parse(time7.get(i)[1].toString())/1000;
				UrlUtil.sendGzhMsg(6, param);
			}
		}
		System.out.println("提醒用户积分到期结束");
	}
	
	
	@Scheduled(cron="0 20 17 * * ?")
	public void userScoreRegression(){
		System.out.println("商品下架,积分退回给用户--开始");
		reGoodsOfLockMallService.userScoreRegression();
		System.out.println("商品下架,积分退回给用户--结束");
	}
	
	
}