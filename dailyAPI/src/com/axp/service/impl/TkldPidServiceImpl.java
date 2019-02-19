package com.axp.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.axp.dao.DateBaseDAO;
import com.axp.dao.TkldPidDao;
import com.axp.domain.AdminUser;
import com.axp.domain.AdminuserRedpaper;
import com.axp.domain.CommodityType;
import com.axp.domain.ProvinceEnum;
import com.axp.domain.ReGoodsofextendmall;
import com.axp.domain.Seller;
import com.axp.domain.TkPartnerSettlements;
import com.axp.domain.TkThePartnerIndex;
import com.axp.domain.TkldPid;
import com.axp.domain.Users;
import com.axp.service.IAppPageService;
import com.axp.service.TkldPidService;
import com.axp.util.CalcUtil;
import com.axp.util.DateUtil;
import com.axp.util.MD5Util;
import com.axp.util.QueryModel;
import com.axp.util.StringUtil;
import com.axp.util.UrlUtil;

@Service
public class TkldPidServiceImpl  extends BaseServiceImpl<TkldPid> implements TkldPidService{

	
	
	@Autowired
	IAppPageService appPageService;
	
	
	
	@Override
	@Cacheable(value="axpCache",key="#i")
	public List<TkldPid> findAll(String i) {
		
			List<TkldPid> list= tkldPidDao.queryAll();
			
		return list;
	}

	@Override
	@CacheEvict(value="axpCache",key="#i")
	public void clearCache(String i) {
		
		System.out.println("清除参数为"+i+"的缓存");
		
	}

	public Boolean isCareerPartner(AdminUser adminUser){
		boolean isCareerPartner=false;
		QueryModel queryModel=new QueryModel();
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
				List<TkldPid> tkldPidList = (List<TkldPid>) dateBaseDAO.findList(TkldPid.class, queryModel);
				if(tkldPidList.size()>0){
					isCareerPartner=true;
					
				}
			}
		}
		
		return isCareerPartner;
	}
	
	/**
	 * 查找事业合伙人 
	 */
	public TkldPid findCareerPartner(Integer userId, Integer zoneId){
		String pid="";
		TkldPid sy=null;
		pid = appPageService.findPid(userId,pid,zoneId);
		if(pid.equals("mm_111685502_17728608_64252042")){
			pid=StringUtil.CAREERPARTNER;
		}
		List<TkldPid> tkldPidList = tkldPidDao.findByPropertyIsValid("pId", pid);
		TkldPid tkldPid=tkldPidList.get(0);
		if(tkldPid.getLevel()==1){
				sy = tkldPid.getTkldPid();
			if(sy==null){
				sy = tkldPidDao.findByPropertyIsValid("pId", StringUtil.CAREERPARTNER).get(0);
			}
		}else{
			 sy=tkldPid;
		}
		return sy;
	}

	@Override
	public List<TkldPid> findLevelByUserId(Integer UserId) {
		
		return tkldPidDao.findByUserId(UserId);
	}

	@Override
	public boolean addPddPid(HttpServletRequest request, HttpServletResponse response) {
		List<TkldPid> list= tkldPidDao.queryAll();
		Map<String, String> signMap = new HashMap<String,String>();
		signMap.put("number", "1");
    	signMap.put("client_id", StringUtil.pdd_client_id);
    	String sign = MD5Util.getSign(signMap, StringUtil.pdd_client_screct);
		try {
			for(TkldPid tk : list){
				Map<String,Object> map = new HashMap<String,Object>();
				map = UrlUtil.getTaoKeToMap("http://open.aixiaoping.cn:8080/open/api/agent/pddPids?data={\"client_id\":\""+StringUtil.pdd_client_id+"\",\"sign\":\""+sign+"\",\"data\":{}}");
				List<Map<String,Object>> pidList = (List<Map<String, Object>>) map.get("p_id_list");
				tk.setPddPid(pidList.get(0).get("p_id").toString());
				tkldPidDao.saveOrUpdate(tk);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
//-------da
	@Override
	public String gainPddPid() {
		String pddPid = null;
		Map<String, String> signMap = new HashMap<String, String>();
		signMap.put("number", "1");
		signMap.put("client_id", StringUtil.pdd_client_id);
		String sign = MD5Util.getSign(signMap, StringUtil.pdd_client_screct);
		Map<String, Object> map = new HashMap<String, Object>();
		map = UrlUtil
				.getTaoKeToMap("http://open.aixiaoping.cn:8080/open/api/agent/pddPids?data={\"client_id\":\""
						+ StringUtil.pdd_client_id
						+ "\",\"sign\":\""
						+ sign
						+ "\",\"data\":{}}");
		List<Map<String, Object>> pidList = (List<Map<String, Object>>) map
				.get("p_id_list");
		if(pidList != null && pidList.size() >0){
			pddPid = pidList.get(0).get("p_id").toString();
			
		}else{
			pddPid = "";
		}
		
		return pddPid;
	}
//-----da
	@Override
	public String findUp(String pid) {
		String customParameters = "";
		String upid = "";
		String dlupid = "";
		String fpid=pid;
		pid = pid.substring(8, pid.length());
		customParameters = "---"+pid;
		
		QueryModel query = new QueryModel();
		query.combPreEquals("isValid", true);
		query.combPreEquals("pddPid", fpid,"pdd_pid");

		List<TkldPid> tkList =  dateBaseDAO.findLists(TkldPid.class, query);
		if(tkList != null && tkList.size()>0){
			TkldPid tk = tkList.get(0);
			if(tk.getLevel() == 3 ){// 有上级的
				TkldPid tkld = tk.getTkldPid();//上级
				if(tkld != null){
					upid = tkld.getPddPid().substring(8, tkld.getPddPid().length());
					TkldPid dltkld = tkld.getTkldPid();//上上级  即 代理
					if(dltkld != null){
						dlupid = dltkld.getPddPid().substring(8, tkld.getPddPid().length());
					}

				}
				customParameters = dlupid+"--"+upid+"-"+pid;

			}else if(tk.getLevel() == 2 && tk.getTkldPid().getId() != null){
				TkldPid tkld = tk.getTkldPid();//上级

				upid = tkld.getPddPid().substring(8, tkld.getPddPid().length());
				customParameters = upid+"---"+pid;
				
			}else if(tk.getLevel() == 1){
				customParameters = pid+"---";
			}
		}else{
			return customParameters;
		}
		return customParameters;
	}
	
	
	@Override
	public Map<String,Object> getPartenerInfo(Integer userId,Integer areaType,Integer sortType,String zoneId){
		
		Map<String ,Object> statusMap = new HashMap<String,Object>();
		Map<String ,Object> dataMap = new HashMap<String,Object>();
		QueryModel model = new QueryModel();
		model.clearQuery();
		model.combPreEquals("isValid", true);
		model.combPreEquals("users.id", userId,"userId");
		List<TkldPid> tkldpids = dateBaseDAO.findLists(TkldPid.class, model);
		if(tkldpids == null && tkldpids.size() <0 ){
			model.clearQuery();
			model.combPreEquals("userId", userId);
			model.combPreEquals("isValid", 1);
			List<AdminUser> adminUser = dateBaseDAO.findLists(AdminUser.class, model);
			model.clearQuery();
			model.combPreEquals("adminuserId", adminUser.get(0).getId());
			model.combPreEquals("isValid", 1);
			model.combPreEquals("level", 1);
			tkldpids = dateBaseDAO.findLists(TkldPid.class, model);
             
		}
		TkldPid tkldpid = tkldpids.get(0);
		Integer pidId = tkldpid.getId();
		String ldLoginName = tkldpid.getLdLoginName();
		
		model.clearQuery();
		model.combPreEquals("pidId", pidId);
		model.combPreEquals("isValid", 1);
		List<TkThePartnerIndex> reportInfos = dateBaseDAO.findLists(TkThePartnerIndex.class, model); //只取第一条信息
		TkThePartnerIndex reportInfo  = null;
		
		if(reportInfos == null && reportInfos.size()==0){
			statusMap.put("status", -1);
			statusMap.put("message", "没有查询到数据");
			
			return statusMap;
		}else{
			reportInfo = reportInfos.get(0);
		}
		
		dataMap.put("isPartner", String.valueOf(tkldpid.getLevel()));//等级
		//文字说明页面
		dataMap.put("direction", "http://hhh.aixiaoping.cn/Application/Home/View/Partner/frsm.html");//合伙人说明
		dataMap.put("profitDirection", "http://hhh.aixiaoping.cn/Application/Home/View/Partner/profitDirection.html");//预估收入 
		dataMap.put("realProfitDirection", "http://hhh.aixiaoping.cn/Application/Home/View/Partner/realProfitDirection.html");//结算收入
		dataMap.put("settleDirection", "http://hhh.aixiaoping.cn/Application/Home/View/Partner/settleDirection.html");//可结算
		
		Map<String,Object> profit = new HashMap<String,Object>();
		double lastMonthPrice = reportInfo.getLast_month_price();
		double thismonthPrice = reportInfo.getThis_month_price();
		String settlementAmount = String.valueOf(tkldpid.getSettlementAmount());//已提现金额
		double expectedProfit = reportInfo.getAllFeeThismonth();
		double settlementProfit = reportInfo.getAllFeeLastmonth();
		
		Calendar cale = null;
        cale = Calendar.getInstance();
        int day = cale.get(Calendar.DATE);//获取当前日期 天 
		
		profit.put("expectedProfit", String.valueOf(CalcUtil.add(expectedProfit, settlementProfit)));
		
		if(day<20){
			profit.put("realProfit", String.valueOf(CalcUtil.add(lastMonthPrice, thismonthPrice)));
		}else{
			profit.put("realProfit",String.valueOf(thismonthPrice));
		}
		
		model.clearQuery();
		model.combPreEquals("is_settlement", 0);
		model.combPreEquals("pid_id", pidId);
		model.setGroup("dates");
		List<TkPartnerSettlements> order_info = dateBaseDAO.findLists(TkPartnerSettlements.class, model);
		double settlement_amount = 0;
		if(order_info != null && order_info.size()>0){
			for(TkPartnerSettlements t : order_info){
				settlement_amount = CalcUtil.add(settlement_amount, t.getSettlementAmount());
			}
		}
		profit.put("settlementProfit", settlement_amount);
		dataMap.put("profit", profit);
		
		//收益明细  profitDetail
		Map<String,Object> profitDetail = new HashMap<String,Object>();
		profitDetail.put("personalToday", reportInfo.getSelfFee_today());
		profitDetail.put("personalLastday", reportInfo.getSelfFeeYesterday());
		profitDetail.put("personalThisMonth", reportInfo.getSelfFeeThismonth());
		profitDetail.put("personalLastMonth", reportInfo.getSelfFeeLastmonth());
		profitDetail.put("partnerToday", CalcUtil.sub(reportInfo.getAllFee_today(), reportInfo.getSelfFee_today()));
		profitDetail.put("partnerLastday", CalcUtil.sub(reportInfo.getAllFeeYesterday(), reportInfo.getSelfFeeYesterday()));
		profitDetail.put("partnerThisMonth", CalcUtil.sub(reportInfo.getAllFee_today(), reportInfo.getSelfFee_today()));
		profitDetail.put("partnerLastMonth", CalcUtil.sub(reportInfo.getAllFeeLastmonth(), reportInfo.getSelfFeeLastmonth()));
		dataMap.put("profitDetail", profitDetail);
		
		//订单明细    orderDetail
		Map<String,Object> orderDetail = new HashMap<String,Object>();
		orderDetail.put("personalToday", reportInfo.getSelfCount_today());
		orderDetail.put("personalLastday", reportInfo.getSelfCountYesterday());
		orderDetail.put("personalThisMonth", reportInfo.getSelfCount_thismonth());
		orderDetail.put("personalLastMonth", reportInfo.getSelfCount_lastmonth());
		orderDetail.put("partnerToday", CalcUtil.sub(reportInfo.getOrderCount_today(),reportInfo.getSelfCount_today()));
		orderDetail.put("partnerLastday", CalcUtil.sub(reportInfo.getOrderCountYesterday(),reportInfo.getSelfCountYesterday()));
		orderDetail.put("partnerThisMonth", CalcUtil.sub(reportInfo.getOrderCount_thismonth(),reportInfo.getSelfCount_thismonth()));
		orderDetail.put("partnerLastMonth", CalcUtil.sub(reportInfo.getOrderCount_lastmonth(),reportInfo.getSelfCount_lastmonth()));
		dataMap.put("orderDetail", orderDetail);
		
		
		int assignedPlaceUnassignPlace = 0;
		int assignedPlace = 0;
		int dailiOne = 0;
		int dailiTwo = 0;
		int unassignPlace = 0;
		if(tkldpid.getLevel() == 1){
			
			model.clearQuery();
			model.combPreEquals("level", 2);
			model.combPreEquals("isValid", 1);
			model.combPreEquals("parentPidId", tkldpid.getId());
			model.combNotEqual("usersId");
			dailiOne = dateBaseDAO.findCount(TkldPid.class, model);//事业代理人
			
			List<TkldPid> shiyePids = dateBaseDAO.findLists(TkldPid.class, model);
			for(TkldPid t: shiyePids){
				model.clearQuery();
				model.combPreEquals("level", 3);
				model.combPreEquals("isValid", 1);
				model.combPreEquals("parentPidId", t.getId());
				model.combNotEqual("usersId");
				int putong_pid = dateBaseDAO.findCount(TkldPid.class, model);//普通代理人
				dailiTwo += putong_pid;
			}
			
		}else if(tkldpid.getLevel() == 2){
			cale.setTime(new Date());
			cale.set(Calendar.HOUR_OF_DAY, 0);
			cale.set(Calendar.MINUTE, 0);
			cale.set(Calendar.SECOND, 0);
			String data1 =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cale.getTime());
			String data7 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtil.addDay2Date(-7, cale.getTime()));
			cale.set(Calendar.HOUR_OF_DAY, 23);
			cale.set(Calendar.MINUTE, 59);
			cale.set(Calendar.SECOND, 59);
			String data2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cale.getTime());
			
			model.clearQuery();
			model.combPreEquals("level", 3);
			model.combPreEquals("isValid", 1);
			model.combPreEquals("parentPidId", tkldpid.getId());
			model.combNotEqual("usersId");
			assignedPlaceUnassignPlace = dateBaseDAO.findCount(TkldPid.class, model);
			model.orCondition(data1+"<=bindingTime<="+data2);
			assignedPlace = dateBaseDAO.findCount(TkldPid.class, model);
			
			model.clearQuery();
			model.combPreEquals("level", 3);
			model.combPreEquals("isValid", 1);
			model.combPreEquals("parentPidId", tkldpid.getId());
			model.combNotEqual("usersId");
			model.orCondition(data7+"<=bindingTime<="+data2);
			unassignPlace = dateBaseDAO.findCount(TkldPid.class, model);
			
			
		}else if(tkldpid.getLevel() == 3){
			assignedPlaceUnassignPlace = 0;
			assignedPlace = 0;
		}
		
		Map<String,Object> partnerInfo = new HashMap<String,Object>();
		 if(tkldpid.getLevel()==1){
			int daili_z = dailiOne + dailiTwo; 
			partnerInfo.put("partnerPlace", String.valueOf(daili_z));
			partnerInfo.put("assignedPlace", String.valueOf(dailiOne));
			partnerInfo.put("unassignPlace", String.valueOf(dailiTwo));
		 }else{
			partnerInfo.put("partnerPlace", String.valueOf(assignedPlaceUnassignPlace));
			partnerInfo.put("assignedPlace", String.valueOf(assignedPlace));
			partnerInfo.put("unassignPlace", String.valueOf(unassignPlace));
		 }
		 dataMap.put("partnerInfo", partnerInfo);
		 int mall = 0 ;
		 int modelId = 2 ;
		 
		 if(areaType== null){
			 areaType = 0;
		 }else if(areaType== 1){
			 mall = 1;
			 modelId = 1;
		 }else{
			 mall = 4;
			 modelId = 2;
			 
		 }
		model.clearQuery();
		model.combPreEquals("isValid", 1);
		model.combPreEquals("level",1);
		model.combPreEquals("modelId", modelId);
		model.setOrder("FIELD(id,183) desc");
		List<CommodityType> commodityTypes = dateBaseDAO.findLists(CommodityType.class, model);
		Map<String,Object> ticketTypes = new HashMap<String,Object>();
		Map<String,Object> shopCategoryInfo = new HashMap<String,Object>();
		for(CommodityType c: commodityTypes){
			ticketTypes.put("typeName", c.getName());
			ticketTypes.put("typeId", c.getId());
			
			shopCategoryInfo.put("typeId", c.getId());
			
			model.clearQuery();
			model.combPreEquals("isValid", 1);
			model.combPreEquals("level",2);
			model.combPreEquals("modelId", modelId);
			model.combPreEquals("parentId", c.getId());
			model.setOrder("FIELD(id,183) desc");
			List<CommodityType> shopCategory2 = dateBaseDAO.findLists(CommodityType.class, model);
			
			Map<String,Object> shopCategoryInf2 = new HashMap<String,Object>();
			
			for(CommodityType o:shopCategory2){
				shopCategoryInf2.put("typeId", o.getId());
				shopCategoryInf2.put("typeName", o.getName());
			}
			
			
		}
		
		dataMap.put("ticketTypes", commodityTypes);
		dataMap.put("typeList", shopCategoryInfo);
		
		//好券优惠商品显示
		int typeId = 183;
		if(sortType == null){
			sortType = 0;
		}
		if(StringUtils.isEmpty(zoneId)){
			statusMap.put("status", -1);
			statusMap.put("message", "zone地区id没有传递");
			
			return statusMap;
		}
		model.clearQuery();
		model.combPreEquals("id", zoneId);
		ProvinceEnum provinceEnum = (ProvinceEnum) dateBaseDAO.findList(ProvinceEnum.class, model);
		
		List<ProvinceEnum> zongData = null ;
		List<String> zongNum = new ArrayList<String>();
		if(provinceEnum.getLevel() == 3){
			model.clearQuery();
			model.combPreEquals("isValid", 1);
			model.combPreEquals("parentId", provinceEnum.getParentId());
			zongData = dateBaseDAO.findLists(ProvinceEnum.class, model);
		}else if(provinceEnum.getLevel() == 2){
			model.clearQuery();
			model.combPreEquals("isValid", 1);
			model.combPreEquals("parentId", provinceEnum.getId());
			zongData = dateBaseDAO.findLists(ProvinceEnum.class, model);
		}else{
			statusMap.put("status", -1);
			statusMap.put("message", "zone地区id没有传递");
			return statusMap;
		}
		for(ProvinceEnum p:zongData){
			zongNum.add(String.valueOf(p.getId())); //保存所有的地址id
		}
		
		String orderData = "";
		String orderDatas = "";
		if(sortType == 1){
			orderData = "a.commission asc";
			orderDatas = "a.commission asc";
		}else if(sortType == -1){
			orderData = "a.commission desc";
			orderDatas = "a.commission desc";
		}else if(sortType == 3){
			orderData = "a.isActivity desc,a.id desc";
			orderDatas = "a.isActivity desc,a.id desc";
		}else if(sortType==4){
			orderData = "a.isActivity asc,a.id desc";
			orderDatas = "a.isActivity asc,a.id desc";
		}else{
			orderData = "a.isTop DESC ,a.createTime DESC";
			orderDatas = "a.isTop DESC ,a.createTime DESC";
		}
		List<List<ReGoodsofextendmall>> cards = new ArrayList<List<ReGoodsofextendmall>>();
		if(typeId ==183){ // php 作为参数传入,
			if(areaType == 1){
           
	           List<ReGoodsofextendmall> gdtc = reGoodsofextendmallDao.findByGdtc(mall,orderData,-1);
	           List<ReGoodsofextendmall> zbsc = reGoodsofextendmallDao.findByZbsc(mall,orderDatas,zoneId,-1);
	           cards.add(zbsc);
	           cards.add(gdtc);
           
			}else if(areaType == 2){
				List<ReGoodsofextendmall> gdtc = reGoodsofextendmallDao.findByGdtc(mall,orderData,-1);
	            List<ReGoodsofextendmall> zbsc = reGoodsofextendmallDao.findByZbsc(mall,orderDatas,zoneId,-1);
	            cards.add(zbsc);
	            cards.add(gdtc);
			}else if(areaType == 0){
				List<ReGoodsofextendmall> gdtc = reGoodsofextendmallDao.findByGdtc(-1,orderData,-1);
	            List<ReGoodsofextendmall> zbsc = reGoodsofextendmallDao.findByZbsc(-1,orderDatas,zoneId,-1);
	            cards.add(zbsc);
	            cards.add(gdtc);
			}
		}else{
			if(areaType == 1){
		           
		           List<ReGoodsofextendmall> gdtc = reGoodsofextendmallDao.findByGdtc(mall,orderData,typeId);
		           List<ReGoodsofextendmall> zbsc = reGoodsofextendmallDao.findByZbsc(mall,orderDatas,zoneId,typeId);
		           cards.add(zbsc);
		           cards.add(gdtc);
	           
				}else if(areaType == 2){
					List<ReGoodsofextendmall> gdtc = reGoodsofextendmallDao.findByGdtc(mall,orderData,typeId);
		            List<ReGoodsofextendmall> zbsc = reGoodsofextendmallDao.findByZbsc(mall,orderDatas,zoneId,typeId);
		            cards.add(zbsc);
		            cards.add(gdtc);
				}else if(areaType == 0){
					List<ReGoodsofextendmall> gdtc = reGoodsofextendmallDao.findByGdtc(-1,orderData,-1);
		            List<ReGoodsofextendmall> zbsc = reGoodsofextendmallDao.findByZbsc(-1,orderDatas,zoneId,-1);
		            cards.add(zbsc);
		            cards.add(gdtc);
				}
		}
		
		List<Map<String,Object>> output = new ArrayList<Map<String,Object>>();
		for(int i=0;i<cards.size();i++){
			List<ReGoodsofextendmall> rc = cards.get(i);
			
			for(ReGoodsofextendmall r: rc){
				Map<String,Object> ks1 = new HashMap<String,Object>();
				
				if((r.getIsActivity()==0 && sortType==3) || (r.getIsActivity()==1 && sortType==4) ){
					continue;
				}
				
				if(r.getTicketnum() == r.getCoupons() || r.getValidtime().before(new Date())
						|| !r.getIsValid() || r.getSign() != 1){
					continue;
				}
				
				ks1.put("ticketId", r.getId());
				ks1.put("goodsName", r.getName());
				
				String covers = r.getCoverPic();
				if(StringUtil.hasLength(covers)&&covers.startsWith("[")){
					JSONArray array = JSONArray.parseArray(covers);
					if(array.size()>0){
						covers = "http://jph.aixiaoping.cn:8080/jupinhuiRes/"+array.getJSONObject(0).getString("imgUrl");
					}
				}else{
					covers="";
				}
				
				
				ks1.put("goodsIcon", covers);//图片需要做处理的
				
				if(r.getMall() ==4){
					
					ks1.put("goodsSoldPerMonty", "");
				}else if(r.getMall() ==1){
					ks1.put("goodsSoldPerMonty", String.valueOf(r.getSalesVolume())+"件");
				}
				
				ks1.put("goodsPrice", String.valueOf(r.getPrice()));
				ks1.put("ticketPrice", String.valueOf(r.getTicketprice()));
				String goodsPriceAfterTicket = String.valueOf((CalcUtil.sub(r.getPrice()*1000, r.getTicketprice()*1000)/1000) );
				ks1.put("goodsPriceAfterTicket", goodsPriceAfterTicket);
				ks1.put("ticketVacancy",String.valueOf( CalcUtil.sub(r.getTicketnum(),r.getCoupons())));
				String ticketSentPercent = String.valueOf(CalcUtil.div(r.getCoupons(), r.getTicketnum(),2));
				ks1.put("ticketSentPercent", ticketSentPercent);
				ks1.put("profitMoney", String.valueOf(CalcUtil.div(r.getCommission()*60, 100, 2)));
				if(r.getTransportationType() ==1){
					ks1.put("consumeTack", String.valueOf(r.getTransportationType()));
					
				}else{
					ks1.put("consumeTack", "0");
					
				}
				ks1.put("transportationtype", r.getTransportationType());
				
				if(r.getIsRed()==1){
					model.clearQuery();
					model.combPreEquals("redid", r.getIsRed());
					//model.combPreEquals("activityId", r.getA)
					List<AdminuserRedpaper> red = dateBaseDAO.findLists(AdminuserRedpaper.class, model);
					if(red.get(0)!= null ){
						ks1.put("reCash", red.get(0).getTotalMoney()==null?"0":red.get(0).getTotalMoney());
					}else{
						ks1.put("reCash","0");
					}
					
				}else{
					ks1.put("reCash","0");
				}
			
				
				output.add(ks1);
			}
		}
		
		dataMap.put("ticketAll", output.subList(0, 5));
		statusMap.put("data", dataMap);
		statusMap.put("status", 1);
		statusMap.put("message", "请求成功");
		return null;
		
	}
	
	

}
