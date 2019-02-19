package com.axp.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.axp.dao.AdminuserRedpaperUsersReceiveDAO;
import com.axp.dao.DateBaseDAO;
import com.axp.dao.ICashmoneyRecordDao;
import com.axp.dao.INewRedPaperAddendumDao;
import com.axp.dao.INewRedPaperAssetDao;
import com.axp.dao.INewRedPaperLogDao;
import com.axp.dao.INewRedPaperLogDetailDao;
import com.axp.dao.INewRedPaperSettingDao;
import com.axp.dao.SellerMainPageDAO;
import com.axp.dao.impl.RedpaperDaoImpl;
import com.axp.domain.AdminUser;
import com.axp.domain.AdminuserRedpaper;
import com.axp.domain.AdminuserRedpaperUsersReceive;
import com.axp.domain.CashmoneyRecord;
import com.axp.domain.NewRedPaperAddendum;
import com.axp.domain.NewRedPaperLog;
import com.axp.domain.NewRedPaperLogDetail;
import com.axp.domain.NewRedPaperSetting;
import com.axp.domain.NrpOrderLog;
import com.axp.domain.ProvinceEnum;
import com.axp.domain.ReGoodsOfLocalSpecialtyMall;
import com.axp.domain.ReGoodsOfSellerMall;
import com.axp.domain.ReGoodsorderItem;
import com.axp.domain.RedPaperDto;
import com.axp.domain.Redpaper;
import com.axp.domain.Seller;
import com.axp.domain.SellerMainPage;
import com.axp.domain.UserCashshopRecord;
import com.axp.domain.Users;
import com.axp.service.IRedpaperService;
import com.axp.util.CalcUtil;
import com.axp.util.DateUtil;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;
import com.axp.util.StringUtil;

@Service("redpaperService")
public class RedpaperServiceImpl extends BaseServiceImpl<Redpaper> implements IRedpaperService{
	
	@Autowired
	private DateBaseDAO dateBaseDAO;
	@Autowired
	private INewRedPaperAddendumDao nrpAddendumDao;
	@Autowired
	private INewRedPaperSettingDao	nrpSettingDao;
	@Autowired
	private INewRedPaperLogDetailDao nrpLogDetailDao;
	@Autowired
	private INewRedPaperAssetDao nrpAssetDao;
	@Autowired
	private INewRedPaperLogDao nrpLogDao ;
	
	@Autowired
	private ICashmoneyRecordDao moneyDao ;
	
	@Autowired
	private  SellerMainPageDAO sellerMainDao;
	
	@Autowired
	private AdminuserRedpaperUsersReceiveDAO adminuserRedpaperUsersReceiveDao;
	
	/**
	 * 获得红包配置列表
	 */
	@Override
	public Map<String, Object> getConfig(HttpServletRequest request) {
		//参数检查；
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null)
			return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
		
//		Integer userId = Integer.parseInt(parameter.getUserId());
//		Users user = usersDao.findById(userId);
		List<Redpaper> list = redpaperDao.getList();
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("dataList", list);
		Map<String, Object> statusMap = new HashMap<String, Object>();
		statusMap.put("status", 0x01);
		statusMap.put("message", "请求成功");
		statusMap.put("data", dataMap);
		
		return statusMap;
	}
	
	
	
	

	@Override
	public String openNewRedPaper(HttpServletRequest request) {
	  	try{
	  		Parameter parameter = ParameterUtil.getParameter(request);
			String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
			if (parameter == null) {//错误的参数；
				return "";
			}
	  		
	  		
	    	NewRedPaperLog nrpl = new NewRedPaperLog();
			String userId = parameter.getUserId();
			String settingId = parameter.getData().getString("settingId");
			//最小面额，最大面额，剩余金额，随机数
			int min,max,surplus,randNumber;
			
			QueryModel qm = new QueryModel();
			
			//拿到当前红包设定表信息
			qm.clearQuery();
			qm.combEquals("id", Integer.valueOf(settingId));	
			NewRedPaperSetting nrps = null;
			List<NewRedPaperSetting> list = dateBaseDAO.findLists(NewRedPaperSetting.class, qm);
			if(list.size()>0){
				nrps = list.get(0);
			}
			request.setAttribute("headImg", basePath+nrps.getHeadimg());
			request.setAttribute("description", nrps.getDescription());
			request.setAttribute("link", nrps.getLink());
			//1.判断红包是否有效
			if(nrps.getBeginTime().after(DateUtil.getNow())
					||nrps.getEndTime().before(DateUtil.getNow())
					||nrps.getIsValid()!=true
					||nrps.getStatus()!=RedpaperDaoImpl.PASS){
				request.setAttribute("pd", "0");
				request.setAttribute("remark", "抱歉，非有效红包");
				return "newRedPaper/detail";
			}
			
			
			request.setAttribute("shopName", nrps.getName());
			
			
			
			//获得用户信息
			qm.clearQuery();
			qm.combEquals("id",userId);
			Users user = (Users) dateBaseDAO.findList(Users.class, qm).get(0);
			
			//1.判断是否有剩余红包
			if(nrps.getAllNum()<=nrps.getAllNumColl()){
				request.setAttribute("pd", "0");
				request.setAttribute("remark", "已经抢完了");
				return "newRedPaper/detail";
			}
			
			//拿到最近领取红包的时间
			/*
			qm.clearQuery();
			qm.combPreEquals("users.id",Integer.valueOf(userId),"userId");
			qm.combPreEquals("setting.id",Integer.valueOf(settingId),"settingId");
			qm.setOrder("createTime desc");
			List<NewRedPaperLog> nrpls = null;
			nrpls =  DateBaseDAO.findLists(NewRedPaperLog.class, qm);*/
			//=============
			qm.clearQuery();
			qm.combPreEquals("setting.id",Integer.valueOf(settingId),"settingId");
			qm.combPreEquals("users.id",Integer.valueOf(userId),"userId");
			qm.combPreEquals("isValid",true);
			List<NewRedPaperLogDetail> nrpls = null;
			nrpls =  dateBaseDAO.findLists(NewRedPaperLogDetail.class, qm);
			//=============
			Timestamp findMaxTime = null;
			Long maxTime =null;
			
				
			//判断是否第一次领取红包
			Double A = (Double) CalcUtil.mul(nrps.getCyc(), 1000, 60, 60);
			if(nrpls.size()>0){
				//findMaxTime=nrpls.get(0).getCreateTime();
				findMaxTime=nrpls.get(0).getLastTime();
				//maxTime = findMaxTime.getTime()+Double.valueOf(nrps.getCyc()*1000*60*60).longValue();	
				maxTime = findMaxTime.getTime()+Double.valueOf(A).longValue();	
			}else{
				//maxTime = Double.valueOf(nrps.getCyc()*1000*60*60).longValue();	
				maxTime = Double.valueOf(A).longValue();	
			}
				
			//2.判断当前是否到了领取时间
			if(System.currentTimeMillis()<maxTime){
				request.setAttribute("pd", "0");
				request.setAttribute("remark", "已经抢过了");	
				return "newRedPaper/detail";
			}
			//获得用户在该商家这次发放红包中领到红包的列表
			/*
			qm.clearQuery();
			qm.combPreEquals("users.id",Integer.valueOf(userId),"userId");
			qm.combPreEquals("setting.id",Integer.valueOf(settingId),"settingId");
			nrpls = DateBaseDAO.findLists(NewRedPaperLog.class, qm);	*/
					
			//判断用户领取红包是否达到上限
			//if(nrps.getLimits()<nrpls.size()){	
			if(nrpls.size()>0&&nrps.getLimits()<=nrpls.get(0).getAmount()){	
				request.setAttribute("pd", "0");
				request.setAttribute("remark", "已经抢过了");
				return "newRedPaper/detail";
			}	
				
			/*surplus =(int)((nrps.getAllMoney()-nrps.getAllMoneyUsed())*100);
		    min = (int)(nrps.getMinParvalue()*100);
		    max = (int)(nrps.getMaxParvalue()*100);*/
			
		    int M = (int) CalcUtil.sub(nrps.getAllMoney(), nrps.getAllMoneyUsed());
		    surplus =(int)(CalcUtil.mul(M, 100, 2));
		    min = (int)CalcUtil.mul(nrps.getMinParvalue(), 100, 2);
		    max = (int)CalcUtil.mul(nrps.getMaxParvalue(), 100, 2);
		    //3.判断剩余金额还能发放红包
		    if(nrps.getMinParvalue()>(CalcUtil.sub(nrps.getAllMoney(), nrps.getAllMoneyUsed()))){
				request.setAttribute("pd", "0");
				request.setAttribute("remark", "已经抢完了");
				/*
				NewRedPaperSettingDAO nrpsDao = new NewRedPaperSettingDAO();
				Utility.transactionBegin();
				nrpsDao.updateEndTime(new Timestamp(new Date().getTime()), nrps.getId());
				Utility.transactionCommit();
				Utility.closeSession();	*/
				return "newRedPaper/detail";
		    }
		    //获得红包金额
		   if(nrps.getMinParvalue() == nrps.getMaxParvalue()){
		   		nrpl.setMoney(nrps.getMinParvalue());
		   }else{
		   		Random r= new Random();	
		   		randNumber = r.nextInt(max - min + 1) + min;
		   		while(true){
		   			if(surplus<randNumber){
		   				randNumber = r.nextInt(max - min + 1) + min;
		   			}else{
		   				break;
		   			}
		   		}
		   		//nrpl.setMoney(((double)randNumber)/100);
		   		nrpl.setMoney(CalcUtil.div((double)randNumber, 100, 2));
		   }
		   
		   //增加用户红包余额
		   //user.setCashPoints(user.getCashPoints()+nrpl.getMoney());
		   user.setCashPoints(CalcUtil.add(user.getCashPoints(), nrpl.getMoney(), 2));
		   usersDao.update(user);
		   
		   //当前时间
		   Timestamp nowTimestamp = new Timestamp(System.currentTimeMillis());
		   //结束时间
		   Calendar calendar = Calendar.getInstance();  
		   calendar.set(Calendar.DAY_OF_MONTH, 1);   
		   calendar.set(Calendar.HOUR_OF_DAY, 0);    
		   calendar.set(Calendar.MINUTE, 0);  
		   calendar.set(Calendar.SECOND,0);   
		   calendar.set(Calendar.MILLISECOND, 0);    
		   calendar.add(Calendar.MONTH, 4);  
		   calendar.add(Calendar.MILLISECOND, -1000);  	
		   Timestamp endTimestamp = new Timestamp(calendar.getTime().getTime());	   
		   
		   //封装红包领取记录
		   nrpl.setUsers(user);
		   nrpl.setAvail(nrpl.getMoney());
		   nrpl.setCreateTime(nowTimestamp);
		   nrpl.setEndTime(endTimestamp);
		   		
		   //红包附表	
		   List<NewRedPaperAddendum> nrpas = redpaperDao.findByUserIdAndEndTime(userId, settingId, nrpl.getEndTime());
		   //判断红包附表是否已经有记录
		   NewRedPaperAddendum nrpa = null;
		   if(nrpas.size()<1){
			   nrpa = new NewRedPaperAddendum();
		   		nrpa.setUsers(user);
		   		nrpa.setSetting(nrps);
		   		nrpa.setCreateTime(nowTimestamp);
		   		nrpa.setEndTime(endTimestamp);				
		   		nrpa.setAvail(nrpl.getMoney());
		   		nrpa.setIsValid(true);
		   }else{
		   		nrpa = nrpas.get(0);
		   		//nrpa.setAvail(nrpa.getAvail()+nrpl.getMoney());
		   		nrpa.setAvail(CalcUtil.add(nrpa.getAvail(), nrpl.getMoney(), 2));
		   }
		    //修改红包设置表
		    /*nrps.setAllMoneyUsed(nrps.getAllMoneyUsed()+nrpl.getMoney());
		    nrps.setAllNumColl(nrps.getAllNumColl()+1);
		    nrps.setTodaySurplus(nrps.getTodaySurplus()-1);*/
		    
		    nrps.setAllMoneyUsed(CalcUtil.add(nrps.getAllMoneyUsed(), nrpl.getMoney(), 2));
		    nrps.setAllNumColl((int)CalcUtil.add(nrps.getAllNumColl(), 1, 2));
		    nrps.setTodaySurplus((int)CalcUtil.sub(nrps.getTodaySurplus(), 1));
		    nrpAddendumDao.saveOrUpdate(nrpa);
		    nrpSettingDao.update(nrps);
		    nrpl.setAddendum(nrpa);
		    nrpl.setSetting(nrps);
		    nrpl.setIsvalid(true);
		    nrpl.setStatus(0);
		    //保存或更新汇总
		    NewRedPaperLogDetail detail = null;
		    if(nrpls.size()==0){
		    	detail = new NewRedPaperLogDetail();
		    	detail.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
		    	detail.setIsValid(true);
		    	detail.setSetting(nrps);
		    	detail.setUsers(user);
		    	detail.setAmount(1);
		    	detail.setLastTime(null);
		    	nrpLogDetailDao.save(detail);
		    }else{
		    	detail = nrpls.get(0);
		    	//detail.setAmount(detail.getAmount()+1);
		    	detail.setAmount((int)CalcUtil.add(detail.getAmount(), 1, 2));
		    	detail.setLastTime(null);
		    	nrpLogDetailDao.update(detail);
		    }
		    //=============
		    nrpLogDao.save(nrpl);
		    
		    request.setAttribute("remark", nrpl.getMoney());
		    request.setAttribute("pd", "1");
	    	}catch(Exception e){
	    		e.printStackTrace();
	    		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	    	}

		return "newRedPaper/detail";
	}

	@Override
	public Map<String, Object> verifyNewRedpaper(HttpServletRequest request) {
		//ArrayList<HashMap<String, String>> mapList = new ArrayList<HashMap<String, String>>();
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		int finalRedpaperId = 0;
    	try{
    		List<NewRedPaperSetting> finalList = new ArrayList<NewRedPaperSetting>();	
    		int userId = Integer.parseInt(parameter.getUserId());
    		Users user = usersDao.findById(userId);
    		//找出粉丝最后领取记录，用于循环筛选红包
    		QueryModel model = new QueryModel();
    		model.combPreEquals("users.id", user.getId(), "userId");
    		model.setOrder("createTime desc");
    		List<NewRedPaperLog> records = dateBaseDAO.findPageList(NewRedPaperLog.class, model, 0, 1);
    		int recordNO = 0;
    		//获取指定地区的有效红包
    		Integer zoneId = -1;
    		if(StringUtils.isNotEmpty(parameter.getZoneId())){
    			zoneId = Integer.parseInt(parameter.getZoneId());
    			ProvinceEnum zone = provinceEnumDao.findById(zoneId);
    			if(zone.getLevel().intValue()==3){
    				zoneId = zone.getProvinceEnumId();
    			}
    		}
    		List<NewRedPaperSetting> redpaperList = getValidRedpaperList(user,zoneId);
    		for(NewRedPaperSetting set : redpaperList){
    			if(checkDrawLimit(user, set)){
    				finalList.add(set);
    				if(records.size()>0&&set==records.get(0).getSetting()){
    					recordNO = finalList.size();
    				}
    			}
    		}
    		if(finalList.size()>0&&recordNO<finalList.size()){//找出下个红包
    			finalRedpaperId = finalList.get(recordNO).getId();
    		}else if(finalList.size()>0){
    			finalRedpaperId = finalList.get(0).getId();
    		}else{//无有效红包
    			return JsonResponseUtil.getJson(-0x02, "无有效红包");
    		}
    		Map<String, Object> idMap = new HashMap<>();
    		idMap.put("settingId", finalRedpaperId);
    		//mapList.add(idMap);
    	}catch(Exception e){
    		e.printStackTrace();
    		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    		return JsonResponseUtil.getJson(-0x02, "程序错误");
    	}
    	
    	Map<String,Object> dataMap = new HashMap<>();
    	dataMap.put("settingId", finalRedpaperId);
    	Map<String,Object> bigDataMap = new HashMap<>();
    	bigDataMap.put("data", dataMap);
    	bigDataMap.put("status",0x01 );
    	bigDataMap.put("message", "请求成功");
    	return bigDataMap;
	}
	
	/**
     * 获得所有有效且可发放的红包
     * @param user
     * @return
     */
    private List<NewRedPaperSetting> getValidRedpaperList(Users user,Integer zoneId){
    	QueryModel model = new QueryModel();

		//先找可领商家红包
		model.combPreCompare("endTime",new java.sql.Timestamp(System.currentTimeMillis()),QueryModel.GREATER);
		model.combPreCompare("beginTime",new java.sql.Timestamp(System.currentTimeMillis()),QueryModel.LESS);
		model.combCondition("allMoneyUsed < allMoney");
		model.combCondition("allNumColl < allNum");
		model.combPreCompare("todaySurplus",0,QueryModel.GREATER);
		model.combPreEquals("isValid", true);
		model.combPreEquals("status", RedpaperDaoImpl.PASS);
		model.combPreEquals("zoneId", zoneId);
		//model.setOrder("rangeid desc");
		List<NewRedPaperSetting> setList = dateBaseDAO.findLists(NewRedPaperSetting.class, model);
		return setList;
    }
    
    /**
     * 判断该粉丝是否有权获得该有效发放的红包
     * @return
     */
    private boolean checkDrawLimit(Users user,NewRedPaperSetting set){
    	boolean isPass = false;
    	//在城市代理之内的要做粉丝的从属判断
    	/*AdminuserDAO adao = new AdminuserDAO();
    	AdminUser admin = adao.findById(set.getRangeid());
    	if(admin.getLevel()<=StringUtil.ADVERONE&&user.getAdminUser().getParentId()!=set.getRangeid()){
    		return false;
    	}*/
    	//寻找该用户领取该红包的记录汇总
    	QueryModel model = new QueryModel();
    	model.combPreEquals("setting.id",set.getId(),"settingId");
    	model.combPreEquals("users.id",user.getId(),"userId");
   		model.combPreEquals("isValid",true);
   		List<NewRedPaperLogDetail> logList = dateBaseDAO.findLists(NewRedPaperLogDetail.class, model);
   		if(logList.size()>0){
   			NewRedPaperLogDetail log = logList.get(0);
   			Timestamp lastTime = new Timestamp(System.currentTimeMillis()-(long)(set.getCyc()*60*60*1000));
   	   		if(log.getLastTime().before(lastTime)&&log.getAmount()<set.getLimits()){//符合时间间隔并领取数少于个人领取最大值
   	   			isPass = true;
   	   		}else{
   	   			isPass = false;
   	   		}
   		}else{
   			isPass = true;
   		}
    	return isPass;
    }
    
  

	@Override
	public void redPaperList(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
		if (parameter == null) {//错误的参数；
			return ;
		}
		List<RedPaperDto> usable = new ArrayList<RedPaperDto>();
		List<RedPaperDto> used = new ArrayList<RedPaperDto>();
		List<Object[]> overTime = new ArrayList<Object[]>();
		
		String userId = parameter.getUserId();
		if(userId==null||StringUtils.isBlank(userId)){
			return ;
		}
		SimpleDateFormat sdf;
		//我的红包-可用
		QueryModel queryModel = new QueryModel();
		queryModel.combPreEquals("users.id", Integer.parseInt(userId),"userId");
		queryModel.combCompare("endTime", "now()", 1);
		queryModel.combEquals("isvalid", 1);
		double sum = dateBaseDAO.findSum(NewRedPaperLog.class, "money", queryModel);
		request.setAttribute("sum", sum);
		// 可用红包个数
		int redNum = dateBaseDAO.findCount(NewRedPaperLog.class, queryModel);
		request.setAttribute("redNum", redNum);
		queryModel.setOrder("createTime desc");
		List<NewRedPaperLog> records = dateBaseDAO.findPageList(NewRedPaperLog.class, queryModel,0,10);
		sdf = new SimpleDateFormat("yyyy.MM.dd");
		// 获取前10条数据显示
		for (NewRedPaperLog redpaperlog : records) {
			// 如果是null，表示是总部红包，有值则是对应商家红包
			NewRedPaperSetting setting = redpaperlog.getSetting();

			RedPaperDto dto = new RedPaperDto();
			dto.setSellerName(setting.getSeller()!=null?setting.getSeller().getName():setting.getAdminUser().getUsername());
			dto.setEndTime(sdf.format(redpaperlog.getEndTime()));
			dto.setMoney(redpaperlog.getMoney());
			dto.setAddress(setting.getSeller()!=null?setting.getSeller().getWebsites():"");
			// 当天领取
			if (sdf.format(new Date()).equals(sdf.format(redpaperlog.getCreateTime()))){
				dto.setIsToday(0);
			}else{
				dto.setIsToday(1);
			}
			usable.add(dto);
		}
		
		//我的红包-已使用
		queryModel.clearQuery();
		queryModel.combEquals("nrpl.users.id", request.getParameter("userId"));
		queryModel.combPreEquals("isvalid", true);
		double sum2 = dateBaseDAO.findSum(NrpOrderLog.class, "userMoney", queryModel);
		int redNum2 = dateBaseDAO.findCount(NrpOrderLog.class, queryModel);
		queryModel.setOrder("createTime desc");
		List<NrpOrderLog> usedPapers = dateBaseDAO.findPageList(NrpOrderLog.class, queryModel,0,10);
		
		sdf = new SimpleDateFormat("MM-dd HH:mm");
		// 获取前10条数据显示
		for (NrpOrderLog nr : usedPapers) {
			NewRedPaperLog redpaperlog = nr.getNrpl();
			UserCashshopRecord record = nr.getUserCR();
			// 如果是null，表示是总部红包，有值则是对应商家红包
			NewRedPaperSetting setting = redpaperlog.getSetting();
			RedPaperDto dto = new RedPaperDto();
			dto.setSellerName(setting.getSeller()!=null?setting.getSeller().getName():setting.getAdminUser().getUsername());
			dto.setSellerImg(setting.getHeadimg());
			dto.setEndTime(sdf.format(redpaperlog.getEndTime()));
/*			dto.setMoney(record.getPayCash()+record.getPayValue());
			dto.setAvail(record.getPayCash());*/
			dto.setAddress(setting.getSeller()!=null?setting.getSeller().getWebsites():"");
			// 当天领取
			used.add(dto);
		}
		
		
		//我的红包-已过期
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sql = "select endtime, count(1) as num,sum(money) as sum from new_red_paper_log where endTime<now() and isvalid=1 and userid='"
				+ request.getParameter("userId")
				+ "' group by date_format(endTime,'%Y-%m-%d') order by endtime desc limit 0,10";

		List<Object[]> info = nrpLogDao.findBySql(sql);
		// 控制显示10条
		for (Object[] obj : info) {
			obj[0] = sdf.format((Date)obj[0]);
			overTime.add(obj);
		}
		
		request.setAttribute("usable", usable);
		request.setAttribute("used", used);
		request.setAttribute("redNum2", redNum2);
		request.setAttribute("sum2", sum2);
		request.setAttribute("overTime", overTime);

	}
	
	@Override
	public void updateByAuto(){
		//更新所有发放中红包的当天剩余个数
		nrpSettingDao.updateDaySurplus();
		//更新所有过期额度的status为5（已过期）
		nrpAssetDao.updateOutTime();
	}

	@Override
	public void moneyList(HttpServletRequest request,HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		Parameter parameter = ParameterUtil.getParameter(request);
		String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
		if (parameter == null) {//错误的参数；
			return ;
		}
		String userId = parameter.getUserId();
		Users user = usersDao.findById(Integer.parseInt(userId));
		if(userId==null||StringUtils.isBlank(userId) || user==null){
			return ;
		}
		
		List<CashmoneyRecord> crlist = moneyDao.queryListByParameter("usersByUserId.id", Integer.parseInt(userId));
		int outmoney=0;
		for(CashmoneyRecord cr:crlist){
			
			if(cr.getType()<0){
				//======================ZL===========================//
				outmoney = (int) CalcUtil.add(outmoney, cr.getMoney(), 2);
				//outmoney+=cr.getMoney();
			}
			
		}
		
		request.setAttribute("sum", user.getMoney());
		request.setAttribute("records", crlist);
		request.setAttribute("outmoney", outmoney);

		
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> checkOrderRedPaper(HttpServletRequest request) {
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {// 错误的参数；
			return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
		}
		int finalRedpaperId = 0;
		Map<String, Object> bigDataMap = new HashMap<>();
		bigDataMap.put("status", 0x01);
		bigDataMap.put("message", "请求成功");
		try {

			// 百分之20 机率
			if (StringUtil.redRandom()) {
				StringBuffer orderIds = new StringBuffer();
				JSONObject object = parameter.getData();
				JSONArray array = JSONArray.parseArray(object
						.getString("orderIds"));
				for (int i = 0; i < array.size(); i++) {
					String order = array.getString(i);
					if (order.length() > 10) {
						order = order.substring(5, order.length() - 5);
					}

					if (i == (CalcUtil.sub(array.size(), 1))) {
						orderIds.append(order);
					} else {
						orderIds.append(order + ",");
					}
				}

				QueryModel queryModel = new QueryModel();
				queryModel.combEquals("isValid", 1);
				queryModel.combInStr("order.id", orderIds.toString());
				queryModel.combPreEquals("order.isValid", true, "orderIsValid");
				queryModel.combCondition("userCoupons.isRed=1 AND userCoupons.isUse=1"
						+ " AND userCoupons.validtime>now() AND userCoupons.reGoodsOfExtendActiviy.id>0");
						
				queryModel.combCondition("userCoupons.adminuserRedpaper.isvalid=1  "
						+ " AND userCoupons.adminuserRedpaper.surplusQuantity>0 "
						+ " AND userCoupons.adminuserRedpaper.surplusMoney>0.07");
				queryModel.combCondition(" userCoupons.reGoodsOfExtendActiviy.id>0  "); //>0代表订单红包
				queryModel.setOrder("id Desc"); // 最后一笔订单获得红包
				
				List<ReGoodsorderItem> reGoodsorderItemList = (List<ReGoodsorderItem>) dateBaseDAO
						.findList(ReGoodsorderItem.class, queryModel);

				if (reGoodsorderItemList.size() > 0) {
					ReGoodsorderItem reGoodsorderItem = reGoodsorderItemList.get(0);
					// 找到红包
					finalRedpaperId = Integer.parseInt(reGoodsorderItem
							.getUserCoupons().getAdminuserRedpaper().getName());
				}
			}
		} catch (Exception e) {
			bigDataMap.put("status", -1);
			bigDataMap.put("message", "请求失败");
			e.printStackTrace();
		}

		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("settingId", finalRedpaperId);
		bigDataMap.put("data", dataMap);

		return bigDataMap;
	}
	
	
	
	

	@Override
	public Map<String, Object> verifyAdminNewRedpaper(HttpServletRequest request) {
		//ArrayList<HashMap<String, String>> mapList = new ArrayList<HashMap<String, String>>();
		Parameter parameter = ParameterUtil.getParameter(request);
		Map<String,Object> bigDataMap = new HashMap<>();
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		int finalRedpaperId = 0;
    	try{
    		int userId = Integer.parseInt(parameter.getUserId());
    		Users user = usersDao.findById(userId);
    		QueryModel model = new QueryModel();
    		if(user!=null){
    			Map<Integer,Double> map =getUserReceiveForTotay(user.getId());  //用户今天已经获得的20个红包记录 
    			if(StringUtil.hasLength(parameter.getZoneId())){
    				int zoneid = Integer.parseInt(parameter.getZoneId());
    				ProvinceEnum e=provinceEnumDao.findById(zoneid);
    				if(e.getLevel2()==3){
    					
    					
    				}else{
    					
    				}
    				
    				if(zoneid==635 || zoneid==636){
    					model.combCondition(" 1=1 and (provinceEnum.id=635 or provinceEnum.id=636) ");
    				}else{
    					model.combPreEquals("provinceEnum.id", zoneid, "zoneId");
    				}
    				
    				
    	    		
    	    		model.combPreEquals("isvalid", true);
    	    		model.combCompare("surplusQuantity", 0,1);  //剩余量大于0
    	    		model.combCompare("surplusMoney", 0.07,1);  //剩余金额大于0.07
    	    		model.combPreEquals("isCountry", false); 	//是否全国红包
    	    		model.combCondition(" (reGoodsOfExtendActiviy.id is null or  reGoodsOfExtendActiviy.id=0)  "); //默认值为0  代表是有活动的 区分 滑屏红包和订单红包
    	    		model.setOrder("creattime desc");
    	    		List<AdminuserRedpaper> records = dateBaseDAO.findPageList(AdminuserRedpaper.class, model, 0, Integer.MAX_VALUE);
    	    		
    	    		if(records!=null && records.size()>0){//找出下个红包
    	    			for(AdminuserRedpaper ar:records){
    	    				if(map.get(ar.getId())==null){
    	    					finalRedpaperId=Integer.parseInt(ar.getName());
    	    					break;
    	    				}
    	    			}
    	    			
    	    		}//无有效红包	
    			}

    		
    		if(finalRedpaperId==0){//全国红包
        			model.clearQuery();   //最近时间的全国红包
            		model.combPreEquals("isvalid", true);
            		model.combCompare("surplusQuantity", 0,1);
            		model.combCompare("surplusMoney", 0.07,1);
            		model.combPreEquals("isCountry", true);
            		model.combCondition(" id >117");
            		model.combCondition(" (reGoodsOfExtendActiviy.id is null or reGoodsOfExtendActiviy.id=0)  "); //>0代表订单红包 默认值为0
            		model.setOrder("creattime desc");
            		List<AdminuserRedpaper> records2 = dateBaseDAO.findPageList(AdminuserRedpaper.class, model, 0, Integer.MAX_VALUE);
            		if(records2!=null && records2.size()>0){
            			for(AdminuserRedpaper ar:records2){
            				if(map.get(ar.getId())==null){
            					finalRedpaperId=Integer.parseInt(ar.getName());
            					break;
            				}
            			}
            		}
    		}
    		
    		if(finalRedpaperId==0){//总部红包
	    			int num= todayRecordDao.getTodayRedpaperNum(userId);
	        		if(num<2){
	        			AdminuserRedpaper ar = adminuserRedpaperDao.findById(117);
	        			if(ar.getReGoodsOfExtendActiviy()!=null&&ar.getSurplusMoney()>0.07 && ar.getSurplusQuantity()>0){
	        				finalRedpaperId=Integer.parseInt(ar.getName());
	        			}	
	        			}
	    			}
    		}
    	}catch(Exception e){
    		
    		e.printStackTrace();
    		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    	}
    	
    	
    	
    	Map<String,Object> dataMap = new HashMap<>();
    	dataMap.put("settingId", finalRedpaperId);
    
    	bigDataMap.put("data", dataMap);
    	bigDataMap.put("status",0x01 );
    	bigDataMap.put("message", "请求成功");
    	return bigDataMap;
	}

	
	public Map<Integer,Double> getUserReceiveForTotay(Integer usersId){
		
		Map<Integer,Double> map =new HashMap<Integer,Double>();
		QueryModel model = new QueryModel();
		map.clear();
		model.combPreEquals("isvalid", true);
		model.combPreEquals("userid",usersId);
		model.setOrder("id desc");
		
		//model.combPreCompare("createtime",new java.sql.Timestamp(getTimeOf12(1,0,0,0)),QueryModel.LESS);
		
		List<AdminuserRedpaperUsersReceive> records = dateBaseDAO.findPageList(AdminuserRedpaperUsersReceive.class, model, 0,50);
		for(AdminuserRedpaperUsersReceive arur :records){
			if(arur.getRedpaper()!=null){
				if(isToday(arur.getCreatetime())){
					map.put(arur.getRedpaper().getId(), arur.getMoney());
				}
				
			}
		}
		return map;
	}
	
	
	private static long getTimeOf12(int day,int hour,int minute,int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_MONTH, day);
        return  cal.getTimeInMillis();
        		
    }
	
	
	

	
	private Boolean isToday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		Calendar now = Calendar.getInstance();
		now.setTime(new Date());

		int cal_year = cal.get(Calendar.YEAR);
		int cal_month = cal.get(Calendar.MONTH) + 1;
		int cal_day = cal.get(Calendar.DAY_OF_MONTH);

		int now_year = now.get(Calendar.YEAR);
		int now_month = now.get(Calendar.MONTH) + 1;
		int now_day = now.get(Calendar.DAY_OF_MONTH);

		if (cal_day == now_day && cal_month == now_month
				&& cal_year == now_year) {
			return true;
		}
		return false;
	}
	



	@Override
	public Map<String, Object> openAdminNewRedPaper(HttpServletRequest request) {
	  		Parameter parameter = ParameterUtil.getParameter(request);
			String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			String userId = parameter.getUserId();
			String zoneid = parameter.getZoneId();
			String settingId = parameter.getData().getString("settingId");
			
			QueryModel model = new QueryModel();
			
			
			
			
    		AdminuserRedpaper adminuserRedpaper=null;
			String module = parameter.getData().getString("Module"); //--Order 订单红包 -- Ad 滑屏 
    		model.combPreEquals("name",settingId);
    		model.combPreEquals("isValid", true);
    		model.combCompare("surplusQuantity", 0,1);
    		model.combCompare("surplusMoney", 0.07,1);
    		model.setOrder("creattime desc");
    		List<AdminuserRedpaper> records = dateBaseDAO.findPageList(AdminuserRedpaper.class, model, 0, 1);
			
			if(records!=null && records.size()>0){
				adminuserRedpaper=records.get(0);
			}
			
			if(adminuserRedpaper==null){
				System.out.println("获取红包失败"+settingId);
				return JsonResponseUtil.getJson(-0x03,"红包被抢完了");
				
			}
			
			
    		
			//订单红包只允许领取一次 //防止恶意使用url重复打开红包
			if(adminuserRedpaper.getReGoodsOfExtendActiviy()!=null&&adminuserRedpaper.getReGoodsOfExtendActiviy().getId()>0){
				model.clearQuery();
				model.combEquals("isvalid", 1);
				model.combPreEquals("redpaper.id", adminuserRedpaper.getId(),"redId");
				
				model.combEquals("userid", Integer.parseInt(userId));
				Integer count = dateBaseDAO.findCount(AdminuserRedpaperUsersReceive.class,model);
				if(count>0){
					return JsonResponseUtil.getJson(-5,"该红包已领取过");
				}
			}
    		
			
			
    		
			List<Object> goodsList = new ArrayList<>();
			Map<String,Object> dataMap = new HashMap<>();
			AdminUser au = adminuserRedpaper.getAdminUser();
			if(au==null){
				au=adminUserDao.findById(adminuserRedpaper.getId());
			}
			String headImg ="";
			String name ="";
			
			double money=0.08;
			int sellerId =0;
			if(au!=null){
				name =au.getUsername()==null?au.getLoginname():au.getUsername();
				Seller seller=null;
				List<Seller> slist = sellerDao.queryListByParameterByIsvalid("adminUser.id", au.getId());
				
				
				if(slist.size()>0){
					seller=slist.get(0);
				}
				if(seller!=null){
					SellerMainPage smp = sellerMainDao.findOneBySellerId(seller.getId());
					name =seller.getName()==null?name:seller.getName();
						if(smp!=null&&StringUtils.isNotBlank(smp.getSellerLogo())){
							headImg=basePath+smp.getSellerLogo();
						}
						if(StringUtils.isBlank(headImg)){
							headImg=StringUtil.sellerHead;
						}
						
						sellerId=seller.getId();
					
						model.clearQuery();
						model.combCondition("addedTime < now()");
						model.combCondition("shelvesTime > now()");
						model.combPreEquals("isValid", true);
						model.combPreEquals("isChecked", true);
						model.combPreEquals("snapshotGoods.seller.id", sellerId,"sellerId");
						model.setOrder("id desc");

						//总部商城
						List<ReGoodsOfLocalSpecialtyMall> mallList1 = dateBaseDAO.findPageList(ReGoodsOfLocalSpecialtyMall.class, model, 0 , 3);
						for(ReGoodsOfLocalSpecialtyMall mall :mallList1){	
							goodsList.add(getGoodsOfData(mall, basePath));
						}
						List<ReGoodsOfSellerMall> mallList = dateBaseDAO.findPageList(ReGoodsOfSellerMall.class, model, 0 , 3);
						for(ReGoodsOfSellerMall mall :mallList){	
							goodsList.add(getGoodsData(mall, basePath));
						}
					
					
					
				}
				
			}
			Users user = usersDao.findById(Integer.parseInt(userId));
			boolean iszongbu=false;
			if(adminuserRedpaper.getId()==117){
				
				int num= todayRecordDao.getTodayRedpaperNum(Integer.parseInt(userId));
				
	    		if(num>=2){
	    			return JsonResponseUtil.getJson(-0x02, "超出当天最大值");
	    		}
				
				Random random = new Random();
				int v = random.nextInt(100);
				if(v==0){
					money = CalcUtil.add(genDoubleRandom(0.12), 0.3);
				}else if(v==1){
					money = CalcUtil.add(genDoubleRandom(0.12), 0.1);
				}else{
					money = genDoubleRandom(0.12);
				}
				iszongbu=true;
				
				
			}else{
				if(adminuserRedpaper.getType()==10){//随机红包
					double averagemoney = CalcUtil.div(adminuserRedpaper.getSurplusMoney(), adminuserRedpaper.getSurplusQuantity(), 2);
					Random random = new Random();
					int v = random.nextInt(10);
					
					if(averagemoney>0.08){
						if(v<8){
							money=genDoubleRandom(averagemoney);
						}else{
							money=CalcUtil.add(genDoubleRandom(averagemoney), averagemoney);
						}
					}else{
						money=averagemoney;
					}
					
					if(adminuserRedpaper.getSurplusQuantity()==1){
						money =adminuserRedpaper.getSurplusMoney();
					}
					
				}else if(adminuserRedpaper.getType()==50){
					money=CalcUtil.div(adminuserRedpaper.getTotalMoney(), adminuserRedpaper.getTotalQuantity(), 2);
					
				}
			}
			if(money<0.08 ){
				money =0.08;
			}
			if(money>adminuserRedpaper.getSurplusMoney()){
				money = adminuserRedpaper.getSurplusMoney();
			}
			
			
			money=CalcUtil.round(money, 2);
			if(iszongbu){
				todayRecordDao.updateRedPaper(Integer.parseInt(userId), 1);
			}
			adminuserRedpaper.setSurplusMoney(adminuserRedpaper.getSurplusMoney()-money);
			adminuserRedpaper.setSurplusQuantity(adminuserRedpaper.getSurplusQuantity()-1);
			adminuserRedpaperDao.saveOrUpdate(adminuserRedpaper);
			AdminuserRedpaperUsersReceive arur = new AdminuserRedpaperUsersReceive();
			arur.setUserid(user.getId());
			arur.setRedpaper(adminuserRedpaper);
			arur.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
			arur.setIsvalid(true);
			if(StringUtils.isNotBlank(zoneid)){
				try {
					arur.setZoneid(Integer.parseInt(zoneid));
				} catch (Exception e) {
					
				}
			}
			
			arur.setMoney(money);
			adminuserRedpaperUsersReceiveDao.save(arur);
			
			CashmoneyRecord cr = new CashmoneyRecord();
			cr.setUsersByUserId(user);
			cr.setUsersByFromUsers(user);
			cr.setBeforeMoney(user.getMoney()==null?0.0:user.getMoney());
			cr.setMoney(money);
			//cr.setAfterMoney(user.getMoney()==null?0.0:user.getMoney()+money);
			cr.setAfterMoney(user.getMoney()==null?0.0:CalcUtil.add(user.getMoney(), money, 2));
			cr.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
			cr.setIsValid(true);
			String remark="";
			if("Order".equals(module)){
				remark="优惠券获得红包金额";
			}else{
				remark="滑屏获得红包金额";
			}
			cr.setRemark(remark+money);
			cr.setType(1);
			cr.setAccount(adminuserRedpaper.getId()+"");
			moneyDao.save(cr);
			//user.setMoney(user.getMoney()==null?0.0:user.getMoney()+money);
			user.setMoney(user.getMoney()==null?0.0:CalcUtil.add(user.getMoney(), money, 2));
			usersDao.update(user);
	    	dataMap.put("settingId", adminuserRedpaper.getId());
	    	dataMap.put("name", name);
	    	dataMap.put("headImg", headImg);
	    	dataMap.put("money",  money);
	    	dataMap.put("description", adminuserRedpaper.getReamrk());
	    	dataMap.put("sellerId", sellerId);
	    	dataMap.put("goodsList", goodsList);
	    	
	    	if(StringUtils.isNotBlank(au.getRedpaperUri())){//跳转uri
	    		dataMap.put("sellerId", 0);
		    	dataMap.put("uri", au.getRedpaperUri());
	    	}else{
	    		dataMap.put("uri", au.getRedpaperUri()==null?"":au.getRedpaperUri());
	    	}
	    	
	    	Map<String,Object> bigDataMap = new HashMap<>();
	    	bigDataMap.put("data", dataMap);
	    	bigDataMap.put("status",0x01 );
	    	bigDataMap.put("message", "请求成功");
			return bigDataMap;
			
			
			
	}
	
	 public static double genDoubleRandom(double max){
	        Random random = new Random();
	        //产生一个[0,499]的double数值
	        try{
	        int a =(int)(CalcUtil.mul(max, 100));
	        
	        int num = random.nextInt(a)+1;
	        return CalcUtil.mul(num, 0.01);
	        }catch(Exception e){
	        	e.printStackTrace();
	        	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	        }
	        return 0;
	       
	    }
	
	 /**
     *根据积分商品对象获取需要返回的数据；
     *
     * @param goodsObject
     * @param basePath
     * @return
     */
    private Map<String, Object> getGoodsData(ReGoodsOfSellerMall g, String basePath) {

        //返回值；
        Map<String, Object> map = new HashMap<>();
        map.put("concern", false);//表示是否关注了该产品，先默认都为false，后期再改；

        if(g.getPrice()>0&&g.getDisplayPrice()>0){
        	  double b = CalcUtil.div(g.getPrice(), g.getDisplayPrice(), 2);
              map.put("discount", CalcUtil.mul(b, 10, 2) + "折");// 商品折扣
        }
      //为多图添加前序
		String covers = g.getSnapshotGoods().getCoverPic();
		if(StringUtil.hasLength(covers)&&covers.startsWith("[")){
			com.alibaba.fastjson.JSONArray array = com.alibaba.fastjson.JSONArray.parseArray(covers);
			if(array.size()>0){
				covers = basePath+array.getJSONObject(0).getString("imgUrl");
			}
		}else{
			covers="";
		}

        Seller seller = g.getSnapshotGoods().getSeller();
        seller.setBasePath(basePath);
        map.put("seller", seller);//商家信息

        map.put("goodsId", "sem"+g.getId());
        map.put("name", g.getSnapshotGoods().getName());
        map.put("price", g.getPrice());
        int score = g.getNoStandardScore()==null?0:g.getNoStandardScore();
        score = score==0?g.getScore():score;
        map.put("costPrice", g.getDisplayPrice());
        map.put("coverPic", covers);
        map.put("expressTactics", g.getTransportationName());
        map.put("spec", g.getFirstStandardJsonForPhone());
        map.put("specNotes", g.getSecondStandardJsonForPhone(null));
        map.put("mallType", 1);
        map.put("salesVolume", g.getSalesVolume());
        map.put("commentCount", g.getCommentCount());
        return map;
	}

//======================================ZL======================================================//
    private Map<String, Object> getGoodsOfData(ReGoodsOfLocalSpecialtyMall g,String basePath){
    	//返回值；
        Map<String, Object> map = new HashMap<>();
        map.put("concern", false);//表示是否关注了该产品，先默认都为false，后期再改；

        if(g.getPrice()>0&&g.getDisplayPrice()>0){
        	  double b = CalcUtil.div(g.getPrice(), g.getDisplayPrice(), 2);
              map.put("discount", CalcUtil.mul(b, 10, 2) + "折");// 商品折扣
        }
      //为多图添加前序
		String covers = g.getSnapshotGoods().getCoverPic();
		if(StringUtil.hasLength(covers)&&covers.startsWith("[")){
			com.alibaba.fastjson.JSONArray array = com.alibaba.fastjson.JSONArray.parseArray(covers);
			if(array.size()>0){
				covers = basePath+array.getJSONObject(0).getString("imgUrl");
			}
		}else{
			covers="";
		}

        Seller seller = g.getSnapshotGoods().getSeller();
        seller.setBasePath(basePath);
        map.put("seller", seller);//商家信息

        map.put("goodsId", "lsm"+g.getId());
        map.put("name", g.getSnapshotGoods().getName());
        map.put("price", g.getPrice());
        int score = g.getNoStandardScore()==null?0:g.getNoStandardScore();
        score = score==0?g.getScore():score;
        map.put("costPrice", g.getDisplayPrice());
        map.put("coverPic", covers);
        map.put("expressTactics", g.getTransportationName());
        map.put("spec", g.getFirstStandardJsonForPhone());
        map.put("specNotes", g.getSecondStandardJsonForPhone(null));
        map.put("mallType", 4);
        map.put("salesVolume", g.getSalesVolume());
        map.put("commentCount", g.getCommentCount());
    	return map;
    }
//====================================END=================================================//
}