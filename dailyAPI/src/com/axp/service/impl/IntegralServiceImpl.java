package com.axp.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONObject;
import com.axp.dao.AdminUserDAO;
import com.axp.dao.AdminuserCashpointRecordDAO;
import com.axp.dao.DLScoreMarkDAO;
import com.axp.dao.DateBaseDAO;
import com.axp.dao.IAdminUserScoreRecordDao;
import com.axp.dao.SJScoreMarkDAO;
import com.axp.dao.ScoreMarkDAO;
import com.axp.dao.ScorerecordsDAO;
import com.axp.dao.SellerDAO;
import com.axp.dao.IVoucherDao;
import com.axp.dao.UserScoreMarkDAO;
import com.axp.dao.UsersDAO;
import com.axp.domain.AdminUser;
import com.axp.domain.AdminUserScoreRecord;
import com.axp.domain.AdminuserCashpointRecord;
import com.axp.domain.DLScoreMark;
import com.axp.domain.SJScoreMark;
import com.axp.domain.ScoreMark;
import com.axp.domain.Scorerecords;
import com.axp.domain.Seller;
import com.axp.domain.TkldPid;
import com.axp.domain.UserScoreMark;
import com.axp.domain.Users;
import com.axp.domain.Voucher;
import com.axp.service.IntegralService;
import com.axp.util.CalcUtil;
import com.axp.util.DateUtil;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;
import com.axp.util.UrlUtil;




@Service
public class IntegralServiceImpl implements IntegralService{
	
	@Autowired
	public AdminUserDAO adminUserDao;
	@Autowired
	public UsersDAO userDao;
	@Autowired
	public DateBaseDAO dateBaseDao;
	@Autowired 
	public SellerDAO sellerDao;
	@Autowired
	public ScorerecordsDAO scorerecordsDao;
	@Autowired
	private IVoucherDao voucherDao;
	@Autowired
	private IAdminUserScoreRecordDao adminUserScoreRecordDao;
	@Autowired
	public ScoreMarkDAO scoreMarkDao;
	@Autowired
	public AdminuserCashpointRecordDAO adminuserCashpointRecordDao;
	@Autowired
	public SJScoreMarkDAO sjScoreMarkDao;
	@Autowired
	public UserScoreMarkDAO userScoreMarkDao;
	@Autowired
	public DLScoreMarkDAO dlScoreMarkDao;
	
	@Override
	public Map<String, Object> getIntegralList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			String adminuserId = parameter.getAdminuserId();
			
			Integer pageIndex = parameter.getData().getInteger("pageIndex");
			boolean isCareerPartner=false;
			
			if (StringUtils.isBlank(adminuserId)) {
				return JsonResponseUtil.getJson(-0x02, "用户未登录！");
			}
			
			AdminUser adminUser = adminUserDao.findById(Integer.parseInt(adminuserId));
			if (adminUser==null) {
				return JsonResponseUtil.getJson(-0x02, "用户未登录！");
			}
			
			QueryModel queryModel = new QueryModel();
			queryModel.combPreEquals("isValid", true);
			queryModel.combPreEquals("adminuserId", adminuserId);
			int page=10;
			int count = 0;
			count = dateBaseDao.findCount(AdminUserScoreRecord.class, queryModel);
			int start = (pageIndex - 1) * page;
			int totalPage = (count % page) > 0 ? ((count / page) + 1) : (count / page);
			List<AdminUserScoreRecord> srList = dateBaseDao.findPageList(AdminUserScoreRecord.class, queryModel, start, page);
			AdminUserScoreRecord record = null;
			for (int i = 0; i < srList.size(); i++) {
				record= srList.get(i);
				Map<String, Object> recordMap = new HashMap<>();
				recordMap.put("detailScore", record.getScore());
				recordMap.put("datailRemark", record.getRemark());
				recordMap.put("detailTime", record.getCreateTime().toString());
				list.add(recordMap);
			}
			Seller seller=null;
			Integer sellerId = adminUser.getSellerId();
			if(sellerId!=null){
				 seller = sellerDao.findById(sellerId);
			}
			if(seller!=null){
				Users users = seller.getUsers();
				if(users!=null){
					queryModel.clearQuery();
					queryModel.combEquals("isValid", 1);
					queryModel.combPreEquals("level",2);
					queryModel.combPreEquals("users.id",users.getId(),"userId");
					List<TkldPid> tkldPidList = (List<TkldPid>) dateBaseDao.findList(TkldPid.class, queryModel);
					if(tkldPidList.size()>0){
						isCareerPartner=true;
					}
				}
			}
			Map<String, Object> partnerMap=new HashMap<>();
			partnerMap.put("isCareerPartner", true);
			partnerMap.put("desc", "你还不是联盟商户,是否开通此权限");
			data.put("partner",partnerMap);
			
			Integer score = adminUser.getScore()==null?0:adminUser.getScore();
			double depositAble = CalcUtil.mul(score, 5d, 2);//可退押金, 商家手上的积分对应的押金 score *5
			double deposit = CalcUtil.sub(depositAble, adminUser.getDeposit()==null?0.0:adminUser.getDeposit());//剩余押金
			data.put("depositAble", depositAble);
			data.put("deposit", deposit);
			data.put("totalCash", String.valueOf(adminUser.getDeposit()));
			data.put("totalScore", String.valueOf(score)); //积分数
			data.put("scoreMap", list);
			data.put("pageSize", totalPage);
			statusMap.put("data", data);
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功");
		} catch (Exception e) {
			e.printStackTrace();
			statusMap.put("message", "请求失败");
			statusMap.put("status", -1);
		} 
		return statusMap;
	}
	@Override
	public Map<String, Object> checkPresenters(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		List<Map<String, Object>> usersInfos = new ArrayList<Map<String,Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> infoMap = new HashMap<>();
		Map<String, Object> infoMap2 = new HashMap<>();
		
		
		
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			String presenterName = parameter.getData().getString("presenterName");
			String adminuserId =parameter.getAdminuserId();
			String userId = parameter.getUserId();
			
			Users users = userDao.findByNameOrPhone(presenterName);
			Seller seller = null;
			AdminUser adminUser = null;
			
			QueryModel queryModel = new QueryModel();
			if (users!=null) {
				queryModel.combPreEquals("isValid", true);
				queryModel.combPreEquals("user_id", users.getId());
				queryModel.setOrder("id desc");
				
				infoMap2.put("presenterType", "1");
				infoMap2.put("presenterId", users.getId().toString());
				infoMap2.put("presenterPhone", users.getPhone());
				infoMap2.put("presenterNickName", users.getName());
				infoMap2.put("scoreDuration", "30天");
				usersInfos.add(infoMap2);
			}else{
				statusMap.put("status", -2);
				statusMap.put("message", "找不到该用户，请重新输入");
				return statusMap;
			}
			AdminUser adminUser2 = null;
			Users users2 = null;
			adminUser2 = adminUserDao.findById(Integer.parseInt(adminuserId));
			data.put("scoreBanlance",adminUser2.getScore() ==null?String.valueOf(0):String.valueOf(adminUser2.getScore()));
			data.put("usersInfos", usersInfos);
			
			statusMap.put("data", data);
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			statusMap.put("status", -1);
			statusMap.put("message", "请求失败");
		}
		return statusMap;
	}
	@Override
	public Map<String, Object> sendScore(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			String presenterType = parameter.getData().getString("presenterType");//被赠送用户类型
			String presenterId = parameter.getData().getString("presenterId");//被赠送用户Id
			String scoreNum = parameter.getData().getString("scoreNum");//积分赠送数
			String adminuserId = parameter.getAdminuserId();
			int score = 0;
			AdminUser adminUser = null;
			if (StringUtils.isNotBlank(adminuserId)) {
				adminUser = adminUserDao.findById(Integer.parseInt(adminuserId));
			}else{
				statusMap.put("status", -2);
				statusMap.put("message", "用户未登录");
				return statusMap;
			}
			
			if (adminUser!=null) {
				score =adminUser.getScore();
				if (score < Integer.parseInt(scoreNum)) {
					statusMap.put("status", -3);
					statusMap.put("message", "您的积分不足，请重新输入");
					return statusMap;
				}
			}
			
			AdminUser adminUserdl = adminUser.getParentAdminUser();
			Timestamp validityTime =  new Timestamp(DateUtil.addDay2Date(30, new Date()).getTime());
			QueryModel model = new QueryModel();
			model.clearQuery();
			model.combPreEquals("isValid", true);
			model.combPreEquals("adminUser.id", adminUser.getId(),"adminUserSellerId");
			model.combIsNull("users.id");
			List<SJScoreMark> sjsms = dateBaseDao.findPageList(SJScoreMark.class, model, 0, Integer.valueOf(scoreNum));
			
			
			
			AdminUser presentAdminInfo = null;
			Users presentUserInfo =null;
			int NewScore = 0;
			if (presenterType.equals("1")) {//赠送给普通粉丝
				presentUserInfo = userDao.findById(Integer.parseInt(presenterId));
				NewScore = (int) CalcUtil.add(presentUserInfo.getScore(), Integer.parseInt(scoreNum));
				presentUserInfo.setScore(NewScore);
				userDao.saveOrUpdate(presentUserInfo);
				
				
				adminUser.setScore((int)CalcUtil.sub(adminUser.getScore(), Integer.parseInt(scoreNum)));
				adminUserDao.saveOrUpdate(adminUser);
				
				
				Scorerecords scorerecords = new Scorerecords();
				scorerecords.setBeforeScore(presentUserInfo.getScore());
				scorerecords.setAfterScore((int)CalcUtil.add(presentUserInfo.getScore(), Integer.parseInt(scoreNum)));
				scorerecords.setIsvalid(true);
				scorerecords.setCreatetime(new Timestamp(System.currentTimeMillis()));
				scorerecords.setValidityTime(new Timestamp(DateUtil.addDay2Date(30, new Date()).getTime()));
				scorerecords.setScore(Integer.parseInt(scoreNum));
				scorerecords.setScoretype(adminUser.getUsername()==null?"":adminUser.getLoginname()+"成功赠送了您"+scoreNum+"积分");
				scorerecords.setRemark(adminUser.getUsername()==null?"":adminUser.getLoginname()+"成功赠送了您"+scoreNum+"积分");
				scorerecords.setType(17);
				scorerecords.setAdminuserId(adminUser.getId());
				scorerecords.setForeignId(Integer.parseInt(presenterId));
				scorerecords.setUsers(presentUserInfo);
				scorerecordsDao.save(scorerecords);
				
				
				AdminUserScoreRecord scoreRecord2 = new AdminUserScoreRecord();
				int surplusScore = (int) CalcUtil.sub(adminUser.getScore(), Double.valueOf(scoreNum));
				scoreRecord2.setAdminUser(adminUser);
				scoreRecord2.setBeforeScore(adminUser.getScore());
				scoreRecord2.setAfterScore(surplusScore);
				scoreRecord2.setSurplusScore(surplusScore);
				scoreRecord2.setCreateTime(new Timestamp(System.currentTimeMillis()));
				scoreRecord2.setScore(-Integer.parseInt(scoreNum));
				scoreRecord2.setIsValid(true);
				scoreRecord2.setType(14);
				scoreRecord2.setFromAdminUser(presentAdminInfo);
				scoreRecord2.setRemark(presentUserInfo.getRealname()==null?"您赠送了："+scoreNum+"积分给"+presentUserInfo.getName():"您赠送了："+scoreNum+"积分给"+presentUserInfo.getRealname());
				adminUserScoreRecordDao.save(scoreRecord2);
				
				
				List<UserScoreMark> usms = new ArrayList<UserScoreMark>();
				for(SJScoreMark sjsm : sjsms){
					sjsm.setUsers(presentUserInfo);
					sjsm.setRefreshTime(new Timestamp(System.currentTimeMillis()));
					sjScoreMarkDao.saveOrUpdate(sjsm);
					
					UserScoreMark usm = new UserScoreMark();
					usm.setCreateTime(new Timestamp(System.currentTimeMillis()));
					usm.setIsValid(true);
					usm.setSjScoreMark(sjsm);
					usm.setUsers(presentUserInfo);
					usm.setValidityTime(validityTime);
					usm.setAdminUser(adminUser);
					usm.setRefreshTime(new Timestamp(System.currentTimeMillis()));
					usms.add(usm);
				}
				userScoreMarkDao.saveList(usms);
				
				 if(presentUserInfo.getUnionId() != null && presentUserInfo.getUnionId() != ""){
						String param = "unionId="+presentUserInfo.getUnionId()+"&linkType=1&integral="+scoreNum;
						UrlUtil.sendGzhMsg(6, param);
				}
				
				
				
			}else if(presenterType.equals("2")){//赠送给商家
				statusMap.put("status", -5);
				statusMap.put("message", "商家之间不能相互赠送");
				return statusMap;
			}
			
			statusMap.put("status", 1);
			statusMap.put("message", "赠送成功");
		} catch (Exception e) {
			if(!TransactionAspectSupport.currentTransactionStatus().isRollbackOnly()){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			e.printStackTrace();
			statusMap.put("status", -1);
			statusMap.put("message", "赠送失败");
		    
		}
		return statusMap;
	}
	
	
	
	
	@Override
	public Map<String, Object> depositOnLine(HttpServletRequest request,
			HttpServletResponse response){
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		
		Map<String, Object> statusMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		statusMap.put("status", 1);
		statusMap.put("message", "请求成功");
		
		Integer adminuserId =Integer.parseInt( parameter.getAdminuserId());
		
		AdminUser adminUser = adminUserDao.findById(adminuserId);
		
		data.put("deposit", adminUser.getDeposit().toString());
		
		Map<String, Object> map1=new HashMap<>();
		map1.put("rechargeMoney", "2000");
		map1.put("score", "4000");
		Map<String, Object> map2=new HashMap<>();
		map2.put("rechargeMoney", "3000");
		map2.put("score", "6000");
		Map<String, Object> map3=new HashMap<>();
		map3.put("rechargeMoney", "4000");
		map3.put("score", "8000");
		Map<String, Object> map4=new HashMap<>();
		map4.put("rechargeMoney", "5000");
		map4.put("score", "10000");
		
		Map<String, Object> map5=new HashMap<>();
		map5.put("rechargeMoney", "6000");
		map5.put("score", "12000");
		Map<String, Object> map6=new HashMap<>();
		map6.put("rechargeMoney", "7000");
		map6.put("score", "14000");
		list.add(map1);
		list.add(map2);
		list.add(map3);
		list.add(map4);
		list.add(map5);
		list.add(map6);
		data.put("rechargeMoneyItems",list);
		statusMap.put("data", data);
				return null;
		
	}
	@Override
	public Map<String, Object> rechargeOnLine(HttpServletRequest request,
			HttpServletResponse response) {
		
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		
		Map<String, Object> statusMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		
		statusMap.put("status", 1);
		statusMap.put("message", "请求成功");
		
		
		Integer adminuserId =Integer.parseInt( parameter.getAdminuserId());
		
		AdminUser adminUser = adminUserDao.findById(adminuserId);
		
		
		data.put("scorePoolBalance", adminUser.getParentAdminUser().getScore()); //应该是展示代理的
		data.put("scoreBanlance", adminUser.getScore().toString());//个人的
		data.put("activeImg", "http://www.aixiaoping.com:8080/aixiaopingRes/upload-res/message_icon/1/nomal/6014510504124952819.png");
		data.put("scoreProportion","5");
		
		
		
		Map<String, Object> map1=new HashMap<>();
		map1.put("rechargeMoney", "100");
		map1.put("scoreReceive", "500");
		Map<String, Object> map2=new HashMap<>();
		map2.put("rechargeMoney", "500");
		map2.put("scoreReceive", "2500");
		Map<String, Object> map3=new HashMap<>();
		map3.put("rechargeMoney", "1000");
		map3.put("scoreReceive", "5000");
		Map<String, Object> map4=new HashMap<>();
		map4.put("rechargeMoney", "2000");
		map4.put("scoreReceive", "10000");
		
		Map<String, Object> map5=new HashMap<>();
		map5.put("rechargeMoney", "5000");
		map5.put("scoreReceive", "25000");
		Map<String, Object> map6=new HashMap<>();
		map6.put("rechargeMoney", "10000");
		map6.put("scoreReceive", "50000");
		list.add(map1);
		list.add(map2);
		list.add(map3);
		list.add(map4);
		list.add(map5);
		list.add(map6);
		data.put("rechargeMoneyItems",list);
		statusMap.put("data", data);
		
		return statusMap;
	}
	@Override
	public Map<String, Object> recharge(HttpServletRequest request,
			HttpServletResponse response) {
		
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		
		Map<String, Object> statusMap = new HashMap<String, Object>();
		
		JSONObject object = parameter.getData();
		String cardNum = object.getString("cardNum");
		String password = object.getString("password");
		String app = parameter.getApp();
		Integer score=0;
		List<Voucher> voucherList = voucherDao.findByPropertyIsValid("code", cardNum);
		Voucher voucher=null;
		if(voucherList.size()>0){
			voucher=voucherList.get(0);
		}else{
			statusMap.put("status", -2);
			statusMap.put("message", "无效积分卡");
			return statusMap;
		}
		
		 if(voucher.getIsRecharge()){
			 statusMap.put("status", -3);
			 statusMap.put("message", "该卡已被使用");
			 return statusMap;
		 }
		 
		 if(!voucher.getPassword().equals(password)){
			 statusMap.put("status", -4);
			 statusMap.put("message", "密码错误");
			 return statusMap;
		 }
		
		if("SELLER".equals(app)){
		 int adminUserId = Integer.parseInt(parameter.getAdminuserId());
		 AdminUser adminUser = adminUserDao.findById(adminUserId);
		
		 adminUser.setScore(adminUser.getScore()+voucher.getFaceValue().intValue());
		 
		 AdminUserScoreRecord scoreRecord=new AdminUserScoreRecord();
		 	scoreRecord.setAdminUser(adminUser);
			scoreRecord.setBeforeScore(adminUser.getScore());
			scoreRecord.setAfterScore(adminUser.getScore()+voucher.getFaceValue().intValue());
			scoreRecord.setScore(voucher.getFaceValue().intValue());
			scoreRecord.setSurplusScore(voucher.getFaceValue().intValue());
			scoreRecord.setType(AdminUserScoreRecord.SHIKA);
			scoreRecord.setCardCode(cardNum);
			scoreRecord.setRemark("您已经成功充值了"+voucher.getFaceValue()+"积分");
			scoreRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
			scoreRecord.setIsValid(true);
			//有效期2年
			scoreRecord.setValidityTime(new Timestamp(DateUtil.addYear2Date(2, new Date()).getTime()));
		 
			adminUserScoreRecordDao.save(scoreRecord);

			adminUserDao.merge(adminUser);
			
			voucher.setIsRecharge(true);
			
			voucherDao.merge(voucher);
			score=voucher.getFaceValue().intValue();
		}else{
				Integer userId =Integer.parseInt(parameter.getUserId());
				Users users = userDao.findByIdIgnoreValid(userId)	;
				Scorerecords scorerecords=new Scorerecords();
				scorerecords.setUsers(users);
				scorerecords.setBeforeScore(users.getScore());
				scorerecords.setScore(voucher.getFaceValue().intValue());
				scorerecords.setAfterScore(voucher.getFaceValue().intValue()+users.getScore());
				scorerecords.setRemark("您已经成功充值了"+voucher.getFaceValue().intValue()+"积分");
				scorerecords.setType(Scorerecords.SHIKA);
				scorerecords.setIsvalid(true);
				scorerecords.setCreatetime(new Timestamp(System.currentTimeMillis()));
				scorerecords.setValidityTime(new Timestamp(DateUtil.addDay2Date(180, new Date()).getTime()));
				scorerecords.setAdminuserId(-1);
				scorerecords.setForeignId(userId);
				scorerecords.setScoretype(voucher.getFaceValue().intValue()+"");
				scorerecords.setSurplusScore(voucher.getFaceValue().intValue());
				scorerecordsDao.save(scorerecords);
				
				users.setScore(users.getScore()+voucher.getFaceValue().intValue());
				userDao.merge(users);
				
				voucher.setIsRecharge(true);
				voucherDao.merge(voucher);
				score=voucher.getFaceValue().intValue();
		}
			
		Map<String, Object> data=new HashMap<>();
		data.put("score", score);
		statusMap.put("data", data);
		statusMap.put("status", 1);
		statusMap.put("message", "请求成功");
		
		
		return statusMap;
	}
	@Override
	public Map<String, Object> receiveQRScore(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			String xcx = request.getParameter("xcx");
			String app = "";
			String scoreNum = "";
			String presenterId = "";
			String isUserTimes = "";
			String adminuserId = "";
			String userId = "";
			if(xcx != null){
				app = "USERS";
				scoreNum = request.getParameter("scoreNum");
				presenterId = request.getParameter("presenterId");
				isUserTimes = request.getParameter("time");
				userId = request.getParameter("userId");
			}else{
				
				Parameter parameter = ParameterUtil.getParameter(request);
				app = parameter.getApp();
				scoreNum =parameter.getData().getString("scoreNum");
				presenterId = parameter.getData().getString("presenterId");//被扫码用户Id
				isUserTimes=parameter.getData().getString("time");//扫码标识
				//扫码人Id（收）
				adminuserId = parameter.getAdminuserId();
				userId = parameter.getUserId();
			}
			
			
			if (app.equals("SELLER")) {
				
				
				
				QueryModel queryModel = new QueryModel();
				queryModel.combPreEquals("isValid", true);
				queryModel.combPreEquals("isUserTimes", isUserTimes);
				AdminUserScoreRecord adminUserScoreRecord = (AdminUserScoreRecord) dateBaseDao.findOne(AdminUserScoreRecord.class, queryModel);
				if (adminUserScoreRecord!=null) {
					statusMap.put("status", -4);
					statusMap.put("message", "该二维码已使用，请重新生成");
					return statusMap;
				}
				
				if (StringUtils.isNotBlank(adminuserId)) {
					AdminUser adminUser = adminUserDao.findById(Integer.parseInt(adminuserId));//扫码人(收)
					if (StringUtils.isNotBlank(presenterId)) {
						AdminUser adminUser2 = adminUserDao.findById(Integer.parseInt(presenterId));//被扫码人(送)
						
						AdminUser adminUserdl = adminUser2.getParentAdminUser();
						Timestamp validityTime =  new Timestamp(DateUtil.addDay2Date(30, new Date()).getTime());
						QueryModel model = new QueryModel();
						model.clearQuery();
						model.combPreEquals("isValid", true);
						model.combPreEquals("newHands", "s-"+presenterId);
						model.setOrder("refreshTime ASC");
						List<ScoreMark> scoreMarks = dateBaseDao.findPageList(ScoreMark.class, model, 0, Integer.valueOf(scoreNum));
//						
						
						
						if (adminUser2!=null) {
							//赠送者记录
							AdminUserScoreRecord scoreRecord2 = new AdminUserScoreRecord();
							
							int surplusScore = (int) CalcUtil.sub(adminUser2.getScore(), Double.valueOf(scoreNum));
							scoreRecord2.setAdminUser(adminUser2);
							scoreRecord2.setBeforeScore(adminUser2.getScore());
							scoreRecord2.setAfterScore(surplusScore);
							scoreRecord2.setSurplusScore(surplusScore);
							scoreRecord2.setCreateTime(new Timestamp(System.currentTimeMillis()));
							scoreRecord2.setScore(-Integer.parseInt(scoreNum));
							scoreRecord2.setIsValid(true);
							scoreRecord2.setType(AdminUserScoreRecord.QRRECEIVE);
							scoreRecord2.setFromAdminUser(adminUser);
							scoreRecord2.setIsUserTimes(isUserTimes);
							scoreRecord2.setRemark(adminUser.getUsername()==null?"您赠送了"+scoreNum+"积分给"+adminUser.getLoginname():"您赠送了"+scoreNum+"积分给"+adminUser.getUsername());
							adminUserScoreRecordDao.save(scoreRecord2);
							
							adminUser2.setScore((int)CalcUtil.sub(adminUser2.getScore(), Integer.parseInt(scoreNum)));
							adminUserDao.saveOrUpdate(adminUser2);
							
							//接收者记录
							AdminUserScoreRecord scoreRecord = new AdminUserScoreRecord();
							scoreRecord.setAdminUser(adminUser);
							scoreRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
							scoreRecord.setBeforeScore(adminUser.getScore());
							scoreRecord.setAfterScore((int)CalcUtil.add(adminUser.getScore(), Integer.parseInt(scoreNum)));
							scoreRecord.setType(20);
							scoreRecord.setScore(Integer.parseInt(scoreNum));
							scoreRecord.setSurplusScore(Integer.parseInt(scoreNum));
							scoreRecord.setIsValid(true);
							scoreRecord.setFromAdminUser(adminUser2);
							scoreRecord.setRemark(adminUser2.getUsername()==null?adminUser2.getUsername()+"成功赠送了您"+scoreNum+"积分":adminUser2.getLoginname()+"成功赠送了您"+scoreNum+"积分");
							adminUserScoreRecordDao.save(scoreRecord);
							
							adminUser.setScore((int)CalcUtil.add(adminUser.getScore(),Integer.parseInt(scoreNum)));
							adminUserDao.saveOrUpdate(adminUser);
							for(ScoreMark scoreMark:scoreMarks){
								scoreMark.setAdminUser(adminUserdl);
								scoreMark.setCreateTime(new Timestamp(System.currentTimeMillis()));
								scoreMark.setNewHands("s-"+adminUser.getId());
								scoreMark.setRefreshTime(new Timestamp(System.currentTimeMillis()));
								scoreMark.setValidityTime(validityTime);
								scoreMark.setRemark(scoreMark.getRemark()+"-s-"+adminUser.getId());
								scoreMarkDao.update(scoreMark);
							}
							
							
						}else{
							statusMap.put("status", -3);
							statusMap.put("message", "未读取到扫码用户信息");
							return statusMap;
						}
						
					}else{
						statusMap.put("status", -3);
						statusMap.put("message", "未读取到扫码用户信息");
						return statusMap;
					}
					
				}else{
					statusMap.put("status", -2);
					statusMap.put("message", "用户未登录");
					return statusMap;
				}
				
			}else if(app.equals("USERS")){
				QueryModel queryModel = new QueryModel();
				queryModel.combPreEquals("isValid", true);
				queryModel.combPreEquals("isUserTimes", isUserTimes);
				AdminUserScoreRecord adminUserScoreRecord = (AdminUserScoreRecord) dateBaseDao.findOne(AdminUserScoreRecord.class, queryModel);
				if (adminUserScoreRecord!=null) {
					statusMap.put("status", -4);
					statusMap.put("message", "该二维码已使用，请重新生成");
					return statusMap;
				}
				if (StringUtils.isNotBlank(userId)) {
					Users users = userDao.findById(Integer.parseInt(userId));//扫码用户
					if (StringUtils.isNotBlank(presenterId)) {
						
						AdminUser adminUser = adminUserDao.findById(Integer.parseInt(presenterId));//被扫码用户
						
						Timestamp validityTime =  new Timestamp(DateUtil.addDay2Date(30, new Date()).getTime());
						QueryModel model = new QueryModel();
						model.clearQuery();
						model.combPreEquals("isValid", true);
						model.combPreEquals("adminUser.id", adminUser.getId(),"adminUserSellerId");
						model.combIsNull("users.id");
						List<SJScoreMark> sjsms = dateBaseDao.findPageList(SJScoreMark.class, model, 1, Integer.valueOf(scoreNum));
						
						
						
						AdminUserScoreRecord scoreRecord2 = new AdminUserScoreRecord();
						int surplusScore = (int) CalcUtil.sub(adminUser.getScore(), Double.valueOf(scoreNum));
						scoreRecord2.setAdminUser(adminUser);
						scoreRecord2.setBeforeScore(adminUser.getScore());
						scoreRecord2.setAfterScore(surplusScore);
						scoreRecord2.setSurplusScore(surplusScore);
						scoreRecord2.setCreateTime(new Timestamp(System.currentTimeMillis()));
						scoreRecord2.setScore(-Integer.parseInt(scoreNum));
						scoreRecord2.setIsValid(true);
						scoreRecord2.setType(AdminUserScoreRecord.QRRECEIVE);
						scoreRecord2.setFromAdminUser(adminUser);
						scoreRecord2.setIsUserTimes(isUserTimes);
						scoreRecord2.setRemark(users.getRealname()==null?"您赠送了"+scoreNum+"积分给"+users.getName():"您赠送了"+scoreNum+"积分给"+users.getRealname());
						adminUserScoreRecordDao.save(scoreRecord2);
						
						
						users.setScore((int)CalcUtil.add(users.getScore(), Integer.parseInt(scoreNum)));
						userDao.saveOrUpdate(users);
						
						
						adminUser.setScore((int)CalcUtil.sub(adminUser.getScore(),Integer.parseInt(scoreNum)));
						adminUserDao.saveOrUpdate(adminUser);
						
						
						Scorerecords scorerecords = new Scorerecords();
						scorerecords.setBeforeScore(users.getScore());
						scorerecords.setAfterScore((int)CalcUtil.add(users.getScore(), Integer.parseInt(scoreNum)));
						scorerecords.setIsvalid(true);
						scorerecords.setCreatetime(new Timestamp(System.currentTimeMillis()));
						scorerecords.setValidityTime(new Timestamp(DateUtil.addDay2Date(180, new Date()).getTime()));
						scorerecords.setScore(Integer.parseInt(scoreNum));
						scorerecords.setRemark(adminUser.getUsername()==null?adminUser.getUsername()+"成功赠送了您"+scoreNum+"积分":adminUser.getLoginname()+"成功赠送了您"+scoreNum+"积分");
						scorerecords.setScoretype(adminUser.getUsername()==null?adminUser.getUsername()+"成功赠送了您"+scoreNum+"积分":adminUser.getLoginname()+"成功赠送了您"+scoreNum+"积分");
						scorerecords.setType(17);
						scorerecords.setAdminuserId(adminUser.getId());
						scorerecords.setForeignId(Integer.parseInt(presenterId));
						scorerecords.setIsUserTimes(isUserTimes);
						scorerecords.setUsers(users);
						scorerecordsDao.save(scorerecords);
						
						
						List<UserScoreMark> usms = new ArrayList<UserScoreMark>();
						for(SJScoreMark sjsm : sjsms){
							sjsm.setUsers(users);
							sjsm.setRefreshTime(new Timestamp(System.currentTimeMillis()));
							sjScoreMarkDao.saveOrUpdate(sjsm);
							
							UserScoreMark usm = new UserScoreMark();
							usm.setCreateTime(new Timestamp(System.currentTimeMillis()));
							usm.setIsValid(true);
							usm.setSjScoreMark(sjsm);
							usm.setUsers(users);
							usm.setValidityTime(validityTime);
							usm.setAdminUser(adminUser);
							usm.setRefreshTime(new Timestamp(System.currentTimeMillis()));
							usms.add(usm);
						}
						userScoreMarkDao.saveList(usms);
						
						if(users.getUnionId() != null){
							
							String unionId = users.getUnionId();
							String param = "unionId="+unionId+"&linkType=1&integral="+scoreNum;
							UrlUtil.sendGzhMsg(6, param);
						}
						
						
					}else{
						statusMap.put("status", -3);
						statusMap.put("message", "未读取到扫码用户信息");
						return statusMap;
					}
					
				}else{
					statusMap.put("status", -2);
					statusMap.put("message", "用户未登录");
					return statusMap;
				}
				
			}
			
			statusMap.put("status", 1);
			statusMap.put("message", "扫码充值积分成功");
		} catch (Exception e) {
			e.printStackTrace();
			statusMap.put("status", -1);
			statusMap.put("message", "扫码充值积分失败");
		}
		return statusMap;
	}
	
	
	@Override
	public Map<String, Object> reDeposit(HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String,Object> statusMap = new HashMap<String,Object>();
		
		Parameter parameter = ParameterUtil.getParameter(request);
		
		try {
			String adminuserId = parameter.getAdminuserId();
			double total = Double.valueOf(parameter.getData().getString("deposit"));
			if(total < 0 || total == 0){
				statusMap.put("status", -1);
				statusMap.put("message", "退押金不能小于或等于0哦!");
				return statusMap;
			}
			if(total < 100){
				statusMap.put("status", -1);
				statusMap.put("message", "退押金不能小于100元");
				return statusMap;
			}
			
			Integer score = (int)CalcUtil.mul(Double.valueOf(total), 5d); //退押金的对应积分
			AdminUser adminUser = adminUserDao.findById(Integer.valueOf(adminuserId));
			if(total > adminUser.getDeposit()){
				statusMap.put("status", -1);
				statusMap.put("message", "退押金金额大于账户押金");
				return statusMap;
			}
			if(score > adminUser.getScore()){
				Double deposit = CalcUtil.div(adminUser.getScore(), 5d, 4);
				statusMap.put("status", -1);
				statusMap.put("message", "目前账户积分:"+adminUser.getScore()+",能退押金:"+deposit+"元.请重新输入!");
				return statusMap;
			}
			adminUser.setDeposit(CalcUtil.sub(adminUser.getDeposit(), total));
			//adminUser.setMoney(CalcUtil.add(adminUser.getMoney(), total)); //申请退押金的时候先不要加到钱里面
			adminUser.setScore(adminUser.getScore()-score);
			AdminuserCashpointRecord acr = new AdminuserCashpointRecord();
			acr.setAdminUser(adminUser);
			acr.setAfterpoint(CalcUtil.add(adminUser.getMoney(), total));
			acr.setBeforepoint(adminUser.getMoney());
			acr.setCashpoint(total);
			acr.setIsValid(true);
			acr.setType(-1);
			acr.setCreateTime(new Timestamp(System.currentTimeMillis()));
			acr.setRemark("申请退押金"+total);
			acr.setIsDeposit(1);
			adminuserCashpointRecordDao.save(acr);

			AdminUserScoreRecord asr = new AdminUserScoreRecord();
			asr.setAdminUser(adminUser);
			asr.setAfterScore(adminUser.getScore()-score);
			asr.setBeforeScore(adminUser.getScore());
			asr.setCreateTime(new Timestamp(System.currentTimeMillis()));
			asr.setFromAdminUser(adminUser);
			asr.setIsValid(true);
			asr.setScore(-score);
			asr.setRemark("申请退押金,回收积分"+score);
			adminUserScoreRecordDao.save(asr);
			
			adminUserDao.saveOrUpdate(adminUser);
			
			AdminUser dl = adminUser.getParentAdminUser();
			dl.setScore(dl.getScore()+score);
			dl.setLasttime(new Timestamp(System.currentTimeMillis())) ;
			adminUserDao.saveOrUpdate(dl);
			
			
			QueryModel queryModel = new QueryModel();
			queryModel.clearQuery();
			queryModel.combPreEquals("isValid", true);
			queryModel.combPreEquals("adminUser.id", adminUser.getId(),"adminUserSellerId");
			queryModel.combCondition("users is null");
			List<SJScoreMark> sjsms = dateBaseDao.findPageList(SJScoreMark.class, queryModel, 0, score);
			
			for(SJScoreMark s: sjsms){
				
				DLScoreMark dlsm = s.getDlScoreMark();
				dlsm.setRefreshTime(new Timestamp(System.currentTimeMillis()));
				dlsm.setAdminUserSeller(null);
				dlScoreMarkDao.saveOrUpdate(dlsm);
				sjScoreMarkDao.delete(s);
				
			}
			
			statusMap.put("status", 1);
			statusMap.put("message", "退押金成功,通过钱包提现吧!");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			statusMap.put("status", -1);
			statusMap.put("message", "退押金请求异常!");
		}
		
		
		
		return statusMap;
	}
	@Override
	public void saveToList(List<DLScoreMark> dlsms,AdminUser au) {
		Timestamp createTime = new Timestamp(System.currentTimeMillis());
		int p = 0;
		if (dlsms.size() % 500 == 0) {
			p = dlsms.size() / 500;
		} else {
			p = dlsms.size() / 500 + 1;
		}
		List<DLScoreMark> dlsmsTemp = new ArrayList<DLScoreMark>();
		List<SJScoreMark> sjsmsTemp = new ArrayList<SJScoreMark>();
		System.out.println("共" + p + "批");
		
		for (int k = 1; k <= p; k++) {
			if (k == p) {
				System.out.println("第" + k + "批的500条开始");
				for (int l = (k - 1) * 500; l < dlsms.size(); l++) {
					DLScoreMark fl = dlsms.get(l);
					fl.setAdminUserSeller(au);
					fl.setRefreshTime(createTime);
					fl.setScoreId(123);
					dlsmsTemp.add(fl);
					
					SJScoreMark sjsm = new SJScoreMark();
					sjsm.setAdminUser(au);
					sjsm.setCreateTime(createTime);
					sjsm.setDlScoreMark(fl);
					sjsm.setIsValid(true);
					sjsm.setRefreshTime(createTime);
					sjsmsTemp.add(sjsm);
					if (l == dlsms.size() - 1) {
						dlScoreMarkDao.saveScoreList(dlsmsTemp);
						sjScoreMarkDao.saveScoreList(sjsmsTemp);
						dlsmsTemp.clear();
						sjsmsTemp.clear();
					}
				}
				System.out.println("第" + k + "批的500条结束");
			} else {
				System.out.println("第" + k + "批的500条开始");
				for (int l = (k - 1) * 500; l < k * 500; l++) {
					DLScoreMark fl = dlsms.get(l);
					dlsmsTemp.add(fl);
					

					SJScoreMark sjsm = new SJScoreMark();
					sjsm.setAdminUser(au);
					sjsm.setCreateTime(createTime);
					sjsm.setDlScoreMark(fl);
					sjsm.setIsValid(true);
					sjsm.setRefreshTime(createTime);
					sjsmsTemp.add(sjsm);
					if (l == k * 500 - 1) {
						dlScoreMarkDao.saveList(dlsmsTemp);
						sjScoreMarkDao.saveList(sjsmsTemp);
						dlsmsTemp.clear();
						sjsmsTemp.clear();
					}
 
				}
				System.out.println("第" + k + "批的500条结束");
			}
		}	

	}
	
	
	
	

}
