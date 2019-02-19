package com.axp.service;



import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;

import com.axp.domain.AdminUser;
import com.axp.domain.GameActivity;
import com.axp.domain.ReGoodsorderItem;
import com.axp.domain.SeLive;
import com.axp.domain.Seller;
import com.axp.domain.Users;


public interface ScoreMarkService{

	
	public List<Object[]> findBySql(String remark,String newHands,String orderBy,Integer score) ;

	/**
	 * 特定商家提供的积分走向
	 * @param adminUser 固定商家
	 * @param user 收入的用户
	 * @param score  多少积分
	 *  type 1 签到, 2 抽奖
	 * @param game  签到提供积分的商家
	 */ 
	public void appointByScore(Users user,Integer score,Integer type, GameActivity game);

	/**
	 * 用户非购买商品消耗积分
	 * @param user
	 * @param score
	 */
	public void userScoreToGame(Users user, Integer score);
	/**
	 * 用户消耗积分统一处理的方法
	 * @param user
	 * @param score
	 * @param typeId
	 */
	public void saveScoreToform(Users user, Integer payScore ,Seller seller ,Integer typeId);
	/**
	 * 提醒用户还有几天积分到期
	 * @param day
	 * @return
	 */
	List<Object[]> findByOverdue(Integer day);

	/**
	 * 新的用户消耗积分统一处理
	 * @param user
	 * @param payScore
	 * @param seller
	 * @param typeId
	 * 
	 * 
	 */
	public void saveScoreToform_1(Users user, Integer payScore, Seller seller,
			Integer typeId ,ReGoodsorderItem item);
	
	void timingTask();
	
	
	
}
