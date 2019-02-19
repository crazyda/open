package com.axp.service.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONObject;
import com.axp.dao.CashshopTypeDAO;
import com.axp.dao.DateBaseDAO;
import com.axp.dao.IReBackOrderDao;
import com.axp.dao.IReGoodsOfLocalSpecialtyMallDao;
import com.axp.dao.IReGoodsOfScoreMallDao;
import com.axp.dao.IReGoodsOfSeckillMallDao;
import com.axp.dao.IReGoodsOfSellerMallDao;
import com.axp.dao.ISellerMoneyRecordDao;
import com.axp.dao.IUsersDao;
import com.axp.dao.ProvinceEnumDAO;
import com.axp.dao.ReBaseGoodsDAO;
import com.axp.dao.ReGoodsorderDao;
import com.axp.dao.ScoreMarkDAO;
import com.axp.dao.SellerDAO;
import com.axp.dao.SellerMainPageDAO;
import com.axp.dao.ShopConcernDao;
import com.axp.domain.AdminUser;
import com.axp.domain.AdminUserScoreRecord;
import com.axp.domain.AdminuserCashpointRecord;
import com.axp.domain.Bonus;
import com.axp.domain.CashshopTimes;
import com.axp.domain.CommodityType;
import com.axp.domain.DLScoreMark;
import com.axp.domain.GameActivity;
import com.axp.domain.Genocoding;
import com.axp.domain.ProvinceEnum;
import com.axp.domain.ReBaseGoods;
import com.axp.domain.ReGoodsDetails;
import com.axp.domain.ReGoodsOfBase;
import com.axp.domain.ReGoodsOfChangeMall;
import com.axp.domain.ReGoodsOfExtendActiviy;
import com.axp.domain.ReGoodsOfLocalSpecialtyMall;
import com.axp.domain.ReGoodsOfScoreMall;
import com.axp.domain.ReGoodsOfSeckillMall;
import com.axp.domain.ReGoodsOfSellerMall;
import com.axp.domain.ReGoodsOfTeamMall;
import com.axp.domain.ReGoodsSnapshot;
import com.axp.domain.ReGoodsStandard;
import com.axp.domain.ReGoodsofextendmall;
import com.axp.domain.ReGoodsorder;
import com.axp.domain.ReGoodsorderItem;
import com.axp.domain.SJScoreMark;
import com.axp.domain.ScoreMark;
import com.axp.domain.Scorerecords;
import com.axp.domain.SeLive;
import com.axp.domain.Seller;
import com.axp.domain.SellerMPbyJson;
import com.axp.domain.SellerMainPage;
import com.axp.domain.SellerMainPbyJson;
import com.axp.domain.ShopCategory;
import com.axp.domain.ShopConcern;
import com.axp.domain.TkldPid;
import com.axp.domain.UserScoreMark;
import com.axp.domain.Users;
import com.axp.service.IMallListService;
import com.axp.service.IReGoodsOfBaseService;
import com.axp.service.IReGoodsOfChangeMallService;
import com.axp.service.ISellerService;
import com.axp.service.ReGoodsOfTeamMallService;
import com.axp.service.ReGoodsofextendmallService;
import com.axp.service.ScoreMarkService;
import com.axp.service.TkldPidService;
import com.axp.util.CacheUtil;
import com.axp.util.CalcUtil;
import com.axp.util.DateUtil;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;
import com.axp.util.StringUtil;
import com.axp.util.UrlUtil;
import com.gexin.fastjson.JSON;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 * 
 */

@Service("scoreMarkService")
public class ScoreMarkServiceImpl extends BaseServiceImpl<ScoreMark> implements
		ScoreMarkService {
	
	
	@Override
	public List<Object[]> findBySql(String remark,String newHands,String orderBy,Integer score) {
		List<Object[]> info = scoreMarkDao.findGroup(remark, newHands, orderBy, score);
		
				
		return  info;
	}

	@Override
	public void appointByScore( Users user, Integer score,Integer type,GameActivity game) {
		//记录这个积分跑哪里去了
		AdminUser adminUser = null;
//		if(type == 1){
//			adminUser = adminUserDao.findById(game.getSeller().getId()); //模拟发积分商家
//		}else if(type == 2){
//			
			adminUser = adminUserDao.findById(1786); //模拟发积分商家
		//}
		int surplusScore = adminUser.getScore()-score;
		adminUser.setScore(surplusScore);
		adminUserDao.saveOrUpdate(adminUser);
		
		AdminUserScoreRecord scoreRecord2 = new AdminUserScoreRecord();
		scoreRecord2.setAdminUser(adminUser);
		scoreRecord2.setBeforeScore(surplusScore+score);
		scoreRecord2.setAfterScore(adminUser.getScore());
		scoreRecord2.setSurplusScore(surplusScore);
		scoreRecord2.setCreateTime(new Timestamp(System.currentTimeMillis()));
		scoreRecord2.setScore(-score);
		scoreRecord2.setIsValid(true);
		scoreRecord2.setType(14);
		if(type == 1){
			scoreRecord2.setRemark("用户登录签到获得"+score+"积分,从您积分中扣除");
			
		}else if(type == 2){
			scoreRecord2.setRemark("用户参加抽奖获得"+score+"积分,从您积分中扣除");
		}
		adminUserScoreRecordDao.save(scoreRecord2);
		QueryModel model = new QueryModel();
		model.clearQuery();
		model.combPreEquals("isValid", true);
		model.combPreEquals("adminUser.id", adminUser.getId(),"adminUserSellerId");
		model.combIsNull("users.id");
		List<SJScoreMark> sjsms = dateBaseDAO.findPageList(SJScoreMark.class, model, 0, Integer.valueOf(score));
		
		Timestamp validityTime =  new Timestamp(DateUtil.addDay2Date(30, new Date()).getTime());
		List<UserScoreMark> usms = new ArrayList<UserScoreMark>();
		for(SJScoreMark sjsm : sjsms){
			sjsm.setUsers(user);
			sjsm.setRefreshTime(new Timestamp(System.currentTimeMillis()));
			sjScoreMarkDAO.saveOrUpdate(sjsm);
			
			UserScoreMark usm = new UserScoreMark();
			usm.setRefreshTime(new Timestamp(System.currentTimeMillis()));
			usm.setCreateTime(new Timestamp(System.currentTimeMillis()));
			usm.setIsValid(true);
			usm.setSjScoreMark(sjsm);
			usm.setUsers(user);
			usm.setValidityTime(validityTime);
			usm.setAdminUser(adminUser);
			usms.add(usm);
		}
		userScoreMarkDAO.saveList(usms);
		
		
		
	}
	/**
	 * 现用的方式 
	 * @param user
	 * @param payScore
	 * @param seller
	 * @param typeId
	 */
	@Override
	public void saveScoreToform_1(Users user, Integer payScore ,Seller seller ,Integer typeId ,ReGoodsorderItem item){
		QueryModel model = new QueryModel();
		model.clearQuery();
		model.combPreEquals("isValid", true);
		model.combPreEquals("users.id", user.getId(),"usersId");
		model.setOrder("validityTime ASC");
		List<UserScoreMark> usms = dateBaseDAO.findPageList(UserScoreMark.class, model, 0, payScore);
		Map<Integer ,Integer> sellerToScoreNum = new HashMap<Integer ,Integer>();
		//处理积分走向 用户和商家的清除, 代理的分配字段置空
		for(UserScoreMark  usm: usms){
			Integer sellerId = usm.getAdminUser().getId();
			if(sellerToScoreNum != null && sellerToScoreNum.size()>0){
				if(sellerToScoreNum.containsKey(sellerId)){
					sellerToScoreNum.put(sellerId, sellerToScoreNum.get(sellerId)+1);
				}else{
					sellerToScoreNum.put(sellerId, 1);
				}
			}else{
				sellerToScoreNum.put(sellerId, 1);
			}
			DLScoreMark dlsm = usm.getSjScoreMark().getDlScoreMark();
			dlsm.setAdminUserSeller(null);
			dlsm.setRefreshTime(new Timestamp(System.currentTimeMillis()));
			dlScoreMarkDAO.saveOrUpdate(dlsm);
			SJScoreMark sjsm = usm.getSjScoreMark();
			sjScoreMarkDAO.delete(sjsm);
			userScoreMarkDAO.delete(usm);
			
		}
		Scorerecords scorerecords = new Scorerecords();
		scorerecords.setRemark("用户:"+user.getRealname()+",消费了"+payScore+"积分,用户积分记录表清除了"+payScore+"条记录");
		scorerecords.setCreatetime(new Timestamp(System.currentTimeMillis()));
		scorerecords.setIsvalid(true);
		scorerecords.setAfterScore(user.getScore()-payScore);
		scorerecords.setScore(payScore);
		scorerecords.setBeforeScore(user.getScore());
		scorerecords.setUsers(user);
		scorerecords.setAdminuserId(1);
		scorerecords.setType(20);
		scorerecords.setScoretype("消费了积分,清除记录");
		scorerecordsDao.save(scorerecords);
		
		if(user.getUnionId() != null && user.getUnionId() != ""){
			String param = "unionId="+user.getUnionId()+"&linkType=2&integral="+payScore;
			UrlUtil.sendGzhMsg(6, param);
		}
		
		//处理积分分佣情况
		AdminUser zb = adminUserDao.findById(47);
		Set keys = sellerToScoreNum.keySet();
		for(Object obj:keys){
			Integer key = (Integer)obj; // 商家id
			Integer value = (Integer) sellerToScoreNum.get(key); //商家对应的积分
			//处理商家
			AdminUser adseller = adminUserDao.findById(key);
			double maidsj = CalcUtil.mul(value, 0.2,4); //积分消费了扣除提供积分的商家押金20%
			
			double cashPoints = 0;
			if(adseller.getDeposit()!=null){
				cashPoints = adseller.getDeposit();
			}
			
			adseller.setDeposit(CalcUtil.sub(cashPoints, maidsj));  //押金减少
			adminUserDao.saveOrUpdate(adseller);
			AdminuserCashpointRecord smr = new AdminuserCashpointRecord();
			smr.setAdminUser(adseller);
			smr.setAfterpoint(adseller.getDeposit()- maidsj);
			smr.setBeforepoint(cashPoints);
			smr.setCashpoint(maidsj);
			smr.setIsValid(true);
			smr.setType(1);
			smr.setIsDeposit(5);
			smr.setUsers(user);
			smr.setCreateTime(new Timestamp(System.currentTimeMillis()));
			if(typeId==1){
				smr.setRemark("用户:"+user.getRealname()+"消耗"+value+"积分,扣除押金"+maidsj+"元并回收积分和清除记录");
			}else if(typeId==2){
				smr.setRemark("用户:"+user.getRealname()+"参加活动消耗"+value+"积分,扣除押金"+maidsj+"元并回收积分和清除记录");
			}
			adminuserCashpointRecordDao.save(smr);
			
			//商家对应的代理
			AdminUser dl = adseller.getParentAdminUser();
			AdminUserScoreRecord adcr = new AdminUserScoreRecord();
			adcr.setAdminUser(dl);
			adcr.setAfterScore(dl.getScore()+value);
			adcr.setBeforeScore(dl.getScore());
			adcr.setRemark("用户:"+user.getRealname()+"消费了"+adseller.getLoginname()+"商家,赠送的"+value+"积分,积分回收代理");
			adcr.setFromAdminUser(adseller);
			adcr.setScore(value);
			adcr.setType(1);
			adcr.setIsValid(true);
			adcr.setCreateTime(new Timestamp(System.currentTimeMillis()));
			adminUserScoreRecordDao.save(adcr);
			
			double maiddl = CalcUtil.mul(value, 0.005,4); //代理分佣
			double maidhhr = CalcUtil.mul(value, 0.03,4); //合伙人总分佣
			
			double bounsMaid = CalcUtil.mul(value, 0.04, 6);
			
			dl.setScore(dl.getScore()+value);
			dl.setDeposit(maiddl);
			dl.setLasttime(new Timestamp(System.currentTimeMillis()));
			adminUserDao.update(dl);
			
			
			Bonus bonus = new Bonus();
			bonus.setAdminUser(dl);//归属代理
			bonus.setCreateTime(new Timestamp(System.currentTimeMillis()));
			bonus.setMaid(bounsMaid);
			bonus.setMaiddl(maiddl);//代理分佣
			bonus.setMaidhhr(maidhhr);//合伙人总分佣
			bonus.setScore(value);
			bonus.setUser(user);
			bonus.setAdminUserBuy(adseller);
			bonus.setRecoveryScore(value);
			bonus.setItem(item);
			bonusDao.save(bonus);
			
			
			
			zb.setMoney(CalcUtil.add(zb.getMoney(), maiddl, 4));
			adminUserDao.saveOrUpdate(zb);
			
			
		}
		
		
		
	}
	
	
	
	
	/**
	 * @param user
	 * @param payScore
	 * @return
	 */
	private List<Object[]> findBySQL(Users user, Integer payScore) {
		// TODO Auto-generated method stu
		return userScoreMarkDAO.findBySQL(user,payScore);
	}

	/**
	 * 用户消耗积分统一处理的方法
	 * @param user
	 * @param score
	 * @param typeId
	 */
	@Override
	public void saveScoreToform(Users user, Integer payScore ,Seller seller ,Integer typeId){ // typeId 1 买商品   2 参与活动消耗
		String newHands = "y-"+String.valueOf(user.getId());
		List<Object[]> scoreList = findBySql("remark", newHands, "refreshTime ASC", payScore);
		Integer dlId = 0;
		for(int i=0;i<scoreList.size();i++){
			String remark = scoreList.get(i)[0].toString();
			//对应的积分 走势
			Integer scoreNum = Integer.valueOf(scoreList.get(i)[1].toString());
			//积分对应商家 id
			Integer sellerId = Integer.valueOf(remark.substring(remark.indexOf("s")+2, remark.indexOf("y")-1));
			AdminUser adseller = adminUserDao.findById(sellerId);
			double maidsj = CalcUtil.mul(scoreNum, 0.2,4); //积分消费了扣除提供积分的商家押金20%
			double cashPoints = 0;
			if(adseller.getDeposit()!=null){
				cashPoints = adseller.getDeposit();
			}
			adseller.setDeposit(CalcUtil.sub(adseller.getDeposit(), maidsj));  //押金减少
			adminUserDao.saveOrUpdate(adseller);
			AdminuserCashpointRecord smr = new AdminuserCashpointRecord();
			smr.setAdminUser(adseller);
			smr.setAfterpoint(adseller.getDeposit()- maidsj);
			smr.setBeforepoint(cashPoints);
			smr.setCashpoint(maidsj);
			smr.setIsValid(true);
			smr.setType(1);
			smr.setIsDeposit(5);
			smr.setUsers(user);
			smr.setCreateTime(new Timestamp(System.currentTimeMillis()));
			if(typeId==1){
				
				smr.setRemark("用户消耗"+scoreNum+"积分,扣除押金"+maidsj+"元并回收积分");
				
			}else if(typeId==2){
				smr.setRemark("用户参加活动消耗"+scoreNum+"积分,扣除押金"+maidsj+"元并回收积分");
			}
			adminuserCashpointRecordDao.save(smr);
			
			double bounsMaid = CalcUtil.mul(scoreNum, 0.04,4); //奖金池子增加收益
			Bonus bonus = new Bonus();
			if(remark.contains("h")){//存在合伙人的
				bonus.setAdminUser(adseller.getParentAdminUser().getParentAdminUser());//归属代理
			}else{
				dlId = adseller.getParentAdminUser().getId();
				bonus.setAdminUser(adseller.getParentAdminUser());//归属代理
			}
			bonus.setCreateTime(new Timestamp(System.currentTimeMillis()));
			bonus.setMaid(bounsMaid);//奖金池分分佣
			bonus.setScore(scoreNum);
			bonus.setUser(user);
			bonus.setRecoveryScore(scoreNum);
			bonusDao.save(bonus);
		}
		//回收积分 到积分池 清除积分信息
		QueryModel model = new QueryModel();
		model.clearQuery();
		model.combPreEquals("isValid", true);
		model.combPreEquals("newHands", newHands);
		
		model.setOrder("refreshTime ASC");
		List<ScoreMark> scoreMarks = dateBaseDAO.findPageList(ScoreMark.class, model, 0, payScore);
		for(ScoreMark s: scoreMarks){
			s.setRemark("");
			//s.setNewHands("d-"+String.valueOf(dlId));
			s.setNewHands("z-47");
			s.setRefreshTime(new Timestamp(System.currentTimeMillis()));
			scoreMarkDao.update(s);
		}
	}
	
	
	
	public void userScoreToGame(Users user, Integer score) {
		
		String newHands = "y-"+String.valueOf(user.getId());
		List<Object[]> scoreList = findBySql("remark", newHands, "refreshTime ASC", score);
		Integer dlId = 0;
		
		
	}

	
	@Override
	public List<Object[]> findByOverdue(Integer day) {
		
		return scoreMarkDao.findByOverdue(day);
	}

	@Override
	public void timingTask() {
    	Timestamp refreshTime = new Timestamp(System.currentTimeMillis());
    	QueryModel model = new QueryModel();
    	model.clearQuery();
    	model.combPreEquals("isValid", true);
    	model.combCondition("validityTime < now()");
    	List<UserScoreMark> userToScore = dateBaseDAO.findLists(UserScoreMark.class, model);
    	if(userToScore != null && userToScore.size()>0){
    		
    	
    	Map<Integer ,Integer> sellerToScoreNum = new HashMap<Integer ,Integer>();
    	Map<Integer,Integer> userToScoreNum = new HashMap<Integer ,Integer>();
    	for(UserScoreMark  usm: userToScore){
			Integer sellerId = usm.getAdminUser().getId();
			if(sellerToScoreNum != null && sellerToScoreNum.size()>0){
				if(sellerToScoreNum.containsKey(sellerId)){
					sellerToScoreNum.put(sellerId, sellerToScoreNum.get(sellerId)+1);
				}else{
					sellerToScoreNum.put(sellerId, 1);
				}
			}else{
				sellerToScoreNum.put(sellerId, 1);
			}
			
			Integer userId = usm.getUsers().getId();
			if(userToScoreNum != null && userToScoreNum.size()>0){
				if(userToScoreNum.containsKey(userId)){
					userToScoreNum.put(userId, userToScoreNum.get(userId)+1);
				}else{
					userToScoreNum.put(userId, 1);
				}
			}else{
				userToScoreNum.put(userId, 1);
			}
			
			SJScoreMark sjsm = usm.getSjScoreMark();
			sjsm.setUsers(null);
			sjsm.setRefreshTime(refreshTime);
			sjScoreMarkDAO.saveOrUpdate(sjsm);
			userScoreMarkDAO.delete(usm);
		}
    	
    	Set sellerKeys = sellerToScoreNum.keySet();
		for(Object obj:sellerKeys){
			Integer key = (Integer)obj; // 商家id
			Integer value = (Integer) sellerToScoreNum.get(key); //商家对应的积分
			AdminUser adseller = adminUserDao.findById(key);
			adseller.setScore(adseller.getScore()+value);
			adminUserDao.saveOrUpdate(adseller);
			
			AdminUserScoreRecord adcr = new AdminUserScoreRecord();
			adcr.setAdminUser(adseller);
			adcr.setAfterScore(adseller.getScore());
			adcr.setBeforeScore(adseller.getScore()-value);
			adcr.setRemark("回收用户积分");
			adcr.setFromAdminUser(adseller);
			adcr.setScore(value);
			adcr.setType(1);
			adcr.setIsValid(true);
			adcr.setCreateTime(refreshTime);
			adminUserScoreRecordDao.save(adcr);
		}
		
		Set userKeys = userToScoreNum.keySet();
		for(Object obj:userKeys){
			Integer key = (Integer)obj; // 用户
			Integer value = (Integer) userToScoreNum.get(key); //用户对应的积分
			Users user = userDao.findById(key);
			user.setScore(user.getScore()-value);
			user.setLasttime(refreshTime);
			userDao.saveOrUpdate(user);
			
			Scorerecords scorerecords = new Scorerecords();
			scorerecords.setRemark("用户:"+user.getRealname()+"过期积分"+value+"被回收");
			scorerecords.setCreatetime(refreshTime);
			scorerecords.setIsvalid(true);
			scorerecords.setAfterScore(user.getScore());
			scorerecords.setScore(value);
			scorerecords.setBeforeScore(user.getScore()-value);
			scorerecords.setUsers(user);
			scorerecords.setAdminuserId(1);
			scorerecords.setType(20);
			scorerecords.setScoretype("回收过期积分到商家");
			scorerecordsDao.save(scorerecords);
    	
		}
		
    }
		System.out.println("回收积分"+userToScore.size());	
    }
		
	// 积分夺宝商品下架不在销售了 把积分退回给用户并减少商家收益
	
	
	
	
	//用户中奖了  
	
	
}
