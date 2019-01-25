package com.axp.service.impl;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.axp.dao.DateBaseDAO;
import com.axp.domain.Aritcles;
import com.axp.domain.Money;
import com.axp.domain.OpenApp;
import com.axp.domain.OpenClient;
import com.axp.domain.OpenPddIncrementGoods2;
import com.axp.domain.PidRelation;
import com.axp.service.OpenAppService;
import com.axp.service.OpenClientService;
import com.axp.sms.SmsDemo;
import com.axp.util.CalcUtil;
import com.axp.util.DateUtil;
import com.axp.util.JsonResponseUtil;
import com.axp.util.MD5Util;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;
import com.axp.util.StringUtil;
import com.axp.util.UrlUtil;

import net.sf.json.JSONArray;

@Service
public class OpenClientServiceImpl extends BaseServiceImpl<OpenClient> implements OpenClientService{
	
	@Resource
	private DateBaseDAO dateBaseDAO;
	@Resource
	private OpenAppService openAppService;
	
	
	
	
	
	
	
	
	@Override
	public List<OpenClient> getOpenClientListByClientId(String cid) {
		// TODO Auto-generated method stub
		return openClientDAO.findByProperty("clientId", cid);
	}

	@Override
	public OpenClient getOpenClientByClientId(String cid) {
		List<OpenClient> oclist=	openClientDAO.findByProperty("clientId", cid);
		OpenClient oc= null;
		if(oclist!=null && oclist.size()>0){
			oc = oclist.get(0);
		}
		
		return oc;
	}

	@Override
	public Map<String, Object> updataByArticle(HttpServletRequest request) {
		Parameter parameter = ParameterUtil.getParameter(request);
		//未完成
		/**
		 * articles:[{"title": 文 章 标 题 ,"url": 文 章 链 接 ,"author": 文 章 作
						者,"cid":分类 id,"sendTime":发布时间戳,"content":文章内容}]
						
		 */
		String articles =  (String) parameter.getData().get("articles");
		JSONArray json = JSONArray.fromObject(articles);
		List<Aritcles> aritcles = new ArrayList<Aritcles>();
		
		for(int i=0;i<json.size();i++){
			if(json.getJSONObject(i).get("title").toString() == null){
				return JsonResponseUtil.getJson(-0x01, "文章标题不能为空");
			}
		}
		
		for(int i=0;i<json.size();i++){
			Aritcles aritcle = new Aritcles();
			aritcle.setTitle(json.getJSONObject(i).get("title").toString());
			aritcle.setAuthor(json.getJSONObject(i).get("author").toString()==null?" ":json.getJSONObject(i).get("author").toString());
			aritcle.setUrl(json.getJSONObject(i).get("url").toString()==null?" ":json.getJSONObject(i).get("url").toString());
			aritcle.setCid(new Integer((json.getJSONObject(i).get("cid").toString())==null?"1":json.getJSONObject(i).get("cid").toString()));
			aritcle.setSendTime(new Integer((json.getJSONObject(i).get("sendTime").toString())==null?"1":json.getJSONObject(i).get("sendTime").toString()));
			aritcle.setTitle(json.getJSONObject(i).get("content").toString()==null?"1":json.getJSONObject(i).get("content").toString());
		//可以执行保存 
			//aritcles.add(aritcle);
		}
		
		
		
		
		Map<String,Object> statusMap = new HashMap<String,Object>();
		statusMap.put("code", 1);
	    statusMap.put("result", true);
	    //statusMap.put("list", aritcles);测试
		return statusMap;
	}

	@Override
	public Map<String, Object> findArticleByCidAndSearch(String cid,
			String search) {
		//没有sign 签名比对
		
		
		
		
		 Map<String,Object> statusMap = new HashMap<String,Object>();
		
		 Map<String,Object> dataMap = new HashMap<String,Object>();
		 
		 List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		 
		 Map<String,Object> map = new HashMap<String,Object>();
		
		 //查询相关文章内容
		 
		 //for
		 map.put("title", "标题");
		 map.put("url", "url");
		 map.put("author", "作者");
		 map.put("content", "内容");
		 map.put("sendTime", "时间");
		 map.put("cid", "类别id");
		 list.add(map);
		 
		 dataMap.put("totalNum", "文章数量");
		 dataMap.put("list", list);
		 
		statusMap.put("code", 1);
	    statusMap.put("result", dataMap);
		return statusMap;
	}

	public Map<String,Object> findMoney(String openId) throws Exception{
		Map<String, Object> map = new HashMap<String,Object>();
		//获取账户余额处理
    	QueryModel query = new QueryModel();
    	query.clearQuery();
    	query.combPreEquals("clientId", openId,"client_id");
    	List<OpenApp> openApps = dateBaseDAO.findLists(OpenApp.class, query);
    	
    	if(openApps==null){
    		return null;
    	}
    	OpenApp openApp = openApps.get(0);
    	//double maid = Double.valueOf(openApp.getMaid());
    	double money = 0.0;
    	String pid = openApp.getPddPid();
    	List<OpenPddIncrementGoods2> opengoods2 =getOrderList(pid);
    	for(OpenPddIncrementGoods2 moneys : opengoods2){
    		money += CalcUtil.mul(moneys.getPromotionAmount(), 0.01,2);
    	}
    	

    	map.put("money", CalcUtil.mul(money,1.0,2));
    	//map.put("pidList", pids);
    	Map<String,Object> orderList = getOrderList2(pid);
    	List<OpenPddIncrementGoods2> estimateGoods = (List<OpenPddIncrementGoods2>) orderList.get("estimateGoods");//预估收入
    	List<OpenPddIncrementGoods2> waitBalance = (List<OpenPddIncrementGoods2>) orderList.get("waitBalance");//待结算
    	double eMoney = 0.0;
    	for(OpenPddIncrementGoods2 moneys : estimateGoods){
    		eMoney += CalcUtil.mul(moneys.getPromotionAmount(), 0.01,2);
    	}
    	double wMoney = 0.0;
    	for(OpenPddIncrementGoods2 moneys : waitBalance){
    		wMoney += CalcUtil.mul(moneys.getPromotionAmount(), 0.01,2);
    	}
    	//(20-23 是两个相加的
    	Calendar cale =  Calendar.getInstance();
        int month = cale.get(Calendar.MONTH)+1;
        int year = cale.get(Calendar.YEAR);
        String startTime = year+"/"+month+"/20 00:00:00";
        String endTime = year+"/"+month+"/22 23:59:59";
    	if(DateUtil.belongCalendar(new Date(), new Date(Date.parse(startTime)), new Date(Date.parse(endTime)))){
    		map.put("eMoney", CalcUtil.add(CalcUtil.mul(eMoney,1.0,2),CalcUtil.mul(wMoney,1.0,2)));
    		
    	}else{
    		map.put("eMoney", CalcUtil.mul(eMoney,1.0,2));
    		map.put("wMoney", CalcUtil.mul(wMoney,1.0,2));
    		
    	}
		return map;
		
	}
	
	public List<OpenPddIncrementGoods2> getOrderList(String pid) throws Exception{
		
    	Calendar cale = null;
        cale = Calendar.getInstance();
        int day = cale.get(Calendar.DATE);
        int month = cale.get(Calendar.MONTH);
        int year = cale.get(Calendar.YEAR);
       
        QueryModel query = new QueryModel();
        query.clearQuery();

    	if(day > 22){
    		month +=1;
    	}
    		
    	
    	String time=year+"-"+month+"-16 00:00:00";
    	SimpleDateFormat format =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
    		Date date = format.parse(time);
    	 long t = (long)CalcUtil.div(date.getTime(),1000);
    	//可提现的 
    	query.combCondition("orderVerifyTime <" +t);
    	query.combEquals("isWithdraw", "0");
    	query.combCondition(" (orderStatus=3 or orderStatus=5) and customParameters like '"+pid+"-%' ");
    	List<OpenPddIncrementGoods2> opengoods2 = dateBaseDAO.findLists(OpenPddIncrementGoods2.class, query);
 
    	return opengoods2;
	}
	
	
	public Map<String ,Object> getOrderList2(String pid) throws Exception{
		Calendar cale = null;
        cale = Calendar.getInstance();
        int day = cale.get(Calendar.DATE);
        int month = cale.get(Calendar.MONTH);
        int year = cale.get(Calendar.YEAR);
       
        QueryModel query = new QueryModel();
        query.clearQuery();

    	
    	//预估收入的 0 1 2 
    	query.clearQuery();
    	query.combCondition(" (orderStatus=1 or orderStatus=2 or orderStatus=3) and customParameters like '"+pid+"-%' ");
    	List<OpenPddIncrementGoods2> estimateGoods = dateBaseDAO.findLists(OpenPddIncrementGoods2.class, query);
    	//待结算的 订单在 本月15号之后的 审核通过或者已结算的 
    	if(day > 22){
    		month +=1;
    	}
    	String time15 = year+"-"+month+"-16 00:00:00";
    	long t15 = (long) CalcUtil.div(new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).parse(time15).getTime(),1000);
    	query.clearQuery();
    	query.combCondition("orderVerifyTime >" +t15);
    	query.combEquals("isWithdraw", "0");
    	query.combCondition("orderStatus=5 and customParameters like '"+pid+"-%' ");
    	List<OpenPddIncrementGoods2> waitBalance = dateBaseDAO.findLists(OpenPddIncrementGoods2.class, query);
    	
    	Map<String,Object> dataMap = new HashMap<String,Object>();
    	
    	dataMap.put("estimateGoods", estimateGoods);
    	dataMap.put("waitBalance", waitBalance);
		return dataMap;
	}
	
	
	@Override
	public Map<String, Object> findBalanceByOpenId(String openId, String sign) {
		if(openId == null || sign == null){
			return JsonResponseUtil.getJson(-0x01, "必要参数不能为空");
		}
		
		Map<String,Object> statusMap = new HashMap<String,Object>();
		
		QueryModel queryModel = new QueryModel();
		queryModel.clearQuery();
		queryModel.combPreEquals("clientId", openId,"client_id");
		OpenApp openApp = (OpenApp) dateBaseDAO.findOne(OpenApp.class, queryModel);
		double appMoney = openApp.getMoney();
		String name="";
		if(openApp == null){
			return JsonResponseUtil.getJson(-0x02, "client_id错误,请联系管理员");
		}else{
			if(openApp.getOpenAuthentication()!=null){
				if(openApp.getOpenAuthentication().getType()==1){
					name = openApp.getOpenAuthentication().getUserName();
				}else if(openApp.getOpenAuthentication().getType()==2){
					name = openApp.getOpenAuthentication().getCompany();
				}
			}
			
			Map<String,String> signMap = new HashMap<String,String>();
			signMap.put("client_id", openId);
			
			if(!(MD5Util.getSign(signMap, openApp.getClientScrect()).equals(sign))){
				
				return JsonResponseUtil.getJson(-0x02, "签名验证不通过");
			}
		}
		
		try{
    	//------
    	Map<String,Object> money = findMoney(openId);
    	
    	
    	
    	//封装数据
    	Map<String,Object> dataMap = new HashMap<String,Object>();
    	dataMap.put("balance_pdd", money.get("money")+"");
    	dataMap.put("eMoney", money.get("eMoney")+"");
    	dataMap.put("wMoney", money.get("wMoney")+"");
    	dataMap.put("name", name);//余额
    	statusMap.put("code", 1);
    	statusMap.put("result", dataMap);
		}catch(Exception e){
			e.printStackTrace();
		}
    	
		return statusMap;
	}

	@Override
	public Map<String, Object> updatawithdraw(HttpServletRequest request) {
		Parameter parameter = ParameterUtil.getParameter(request);
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String openId = parameter.getClient_id();
	    String sign = parameter.getSign();
	    String money = parameter.getData().getString("money")==""?"0":parameter.getData().getString("money");
	    String bank_card = parameter.getData().getString("bank_card");
	    String bank_name = parameter.getData().getString("bank_name");
	    String bank_address = parameter.getData().getString("bank_address");
	    String name = parameter.getData().getString("name");
	    String orderId = parameter.getData().getString("order_id")==null?"":parameter.getData().getString("order_id");
	    String presentation_class = parameter.getData().getString("presentation_class")==null?"0":parameter.getData().getString("presentation_class");
	    Map<String,Object> statusMap = new HashMap<String,Object>();
	    
	    if(parameter == null ||openId == "" || sign == ""){
	    	return JsonResponseUtil.getJson(-0x01, "必要参数不能为空");
	    }
	    if(bank_card=="" || bank_name == "" ||bank_address =="" 
	    		|| name == "" ){
	    	return JsonResponseUtil.getJson(-0x01, "银行相关信息不能为空");
	    }
	    if(Double.parseDouble(money)<0){
	    	return JsonResponseUtil.getJson(-0x01, "提现不能小于0");
	    }
	    

	    
	    QueryModel queryModel = new QueryModel();
		queryModel.clearQuery();
		queryModel.combPreEquals("clientId", openId,"client_id");
		OpenApp openApp = (OpenApp) dateBaseDAO.findOne(OpenApp.class, queryModel);
		
		
		
		if(openApp == null){
			statusMap.put("code", -1);
	    	statusMap.put("p_id", "");
	    	statusMap.put("result", "OpenId错误,请联系管理员");
	    	return statusMap;
		}else{
			
			if(openApp.getOpenAuthentication()==null || openApp.getOpenAuthentication().getIsAdopt()==0 || openApp.getOpenAuthentication().getIsAdopt()==2){
				statusMap.put("code", -1);
		    	statusMap.put("p_id", "");
		    	statusMap.put("result", "请在爱小屏开放平台进行实名认证！");
		    	return statusMap;
			}
			
			if(openApp.getOpenAuthentication()!=null && openApp.getOpenAuthentication().getIsAdopt()==1 ){
				if(openApp.getOpenAuthentication().getType()==1){
					if(!openApp.getOpenAuthentication().getUserName().equals(name)){
						statusMap.put("code", -1);
				    	statusMap.put("p_id", "");
				    	statusMap.put("result", "银行账户名需与实名认证用户名相同！认证用户名为："+openApp.getOpenAuthentication().getUserName());
				    	return statusMap;
					}
				}else if(openApp.getOpenAuthentication().getType()==2){
					if(!openApp.getOpenAuthentication().getCompany().equals(name)){
						statusMap.put("code", -1);
				    	statusMap.put("p_id", "");
				    	statusMap.put("result", "银行账户名需与实名认证用户名相同！认证用户名为："+openApp.getOpenAuthentication().getCompany());
				    	return statusMap;
					}
				}else{
					statusMap.put("code", -1);
			    	statusMap.put("p_id", "");
			    	statusMap.put("result", "请在爱小屏开放平台进行实名认证！或认证资料有误");
			    	return statusMap;
				}
			}
			
			
			Map<String,String> signMap = new HashMap<String,String>();
			signMap.put("client_id", openId);
			
			if(!(MD5Util.getSign(signMap, openApp.getClientScrect()).equals(sign))){
				statusMap.put("code", -1);
		    	statusMap.put("p_id", openApp.getPddPid());
		    	statusMap.put("result", "签名验证不通过");
		    	return statusMap;
			
			}
		}
		
		//Map<String,Object> moneyAll =  findMoney(openId);
    	
    	queryModel.clearQuery();
    	queryModel.combPreEquals("clientId", openId,"client_id");
    	
    	List<Money> moneyList = dateBaseDAO.findLists(Money.class, queryModel);
    	if(moneyList != null && moneyList.size()>0  ){
    		
    		try {
				if(moneyList.get(0).getIsCheck() == 0){ // 未审核的
					
					statusMap.put("code", -1);
			    	statusMap.put("p_id", openApp.getPddPid());
			    	statusMap.put("result", "您上一笔提现还在处理中,不能重复提现");
			    	return statusMap;
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
    	}
    	

    	
    
    	
    	List<OpenPddIncrementGoods2> openGoods =null;
		try {
			openGoods = getOrderList(openApp.getPddPid());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		double moneys =0d;
		
    	for(OpenPddIncrementGoods2 p :openGoods){
    		moneys += CalcUtil.mul(p.getPromotionAmount(), 0.01,2);
    		p.setIsWithdraw("6");
    		openPddIncrementGoods2DAO.saveOrUpdate(p);
    		try{
    		SmsDemo.sendSms();
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	}
    	if(moneys > 0){
    		//提现记录信息
        	Money moneyRecord = new Money();
        	moneyRecord.setMoney(moneys);
        	moneyRecord.setClientId(openId);
        	moneyRecord.setBankName(bank_name);
        	moneyRecord.setBankAddress(bank_address);
        	moneyRecord.setBankCard(bank_card);
        	moneyRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
        	moneyRecord.setName(name);
        	moneyRecord.setRemark("用户提交的金额为："+money+";计算金额为："+moneys);
        	moneyRecord.setIsCheck(0);
        	moneyRecord.setIsPay(0);
        	moneyRecord.setOrderId(orderId);
        	moneyRecord.setPresentationClass(Integer.valueOf(presentation_class));
        	moneyDAO.save(moneyRecord);
        	//封装
        	statusMap.put("code", 1);
        	statusMap.put("p_id", openApp.getPddPid());
        	statusMap.put("result", "恭喜你"+openApp.getAppName()+"成功提现"+moneys);
    	}else{
    		statusMap.put("code", -1);
        	statusMap.put("p_id", openApp.getPddPid());
        	statusMap.put("result", "订单异常");
    	}
    	
    	
		return statusMap;
	}

	@Override
	public Map<String, Object> createPddPids(HttpServletRequest request) {
		Parameter parameter = ParameterUtil.getParameter(request);
		
		String openId = parameter.getClient_id();
	    String sign = parameter.getSign();
	    String number = parameter.getData().getString("number")==null?"1":parameter.getData().getString("number");
	    
	    
	    if(parameter == null ||openId == null || sign == null ){
	    	return JsonResponseUtil.getJson(-0x01, "必要参数不能为空");
	    }
	    
	    Map<String,Object> statusMap = new HashMap<String,Object>();
		
		OpenClient oc = getOpenClientByClientId(openId);
		long l = System.currentTimeMillis();
		
		Map<String,String> signMap = new HashMap<String,String>();
    	signMap.put("number", number);
    	signMap.put("client_id", openId);
    	
    	if(oc != null){
    		if(!MD5Util.getSign(signMap, oc.getClientSecret()).equals(sign)){
    			return JsonResponseUtil.getJson(-0x01, "签名验证不通过！");
    		}
    	}
    	String client_id = "";
    	String client_secret = "";
//    	if(StringUtil.openId.equals(openId)){
//    		client_id = StringUtil.client_id_other;
//    		client_secret = StringUtil.client_id_other;
//    	}else{
    		client_id = StringUtil.client_id_axp;
    		client_secret = StringUtil.client_secret_axp;
//    	}
    	
    	//随机生成可以用pdd代理Pids
    	 signMap.put("timestamp", l+"");
    	 signMap.put("type", "pdd.ddk.goods.pid.generate");
    	 signMap.put("data_type", StringUtil.data_type);
    	 signMap.put("client_id", client_id);
    	 
    	 
    	  String sign1 = MD5Util.getSign(signMap,client_secret);
    	
    	  String url = "http://gw-api.pinduoduo.com/api/router";
          String param = "type=pdd.ddk.goods.pid.generate&data_type=JSON&timestamp="+l+"&sign="+sign1+"&client_id="+client_id+"&number="+number;
          
          List<Map<String, Object>> pidsList = UrlUtil.sendPostForList(url, param,"p_id_generate_response");
          
          if(pidsList.size()<=0){
        	  return JsonResponseUtil.getJson(-0x01, "连接超时没有数据,重新连接");
          }
    	//已经提交上来有了pid 然后通过再去请求的时候就生成新的pid 然后保存在关系表中
          
          QueryModel query = new QueryModel();
          query.clearQuery();
          query.combPreEquals("clientId", oc.getClientId());
          OpenApp openApp = (OpenApp) dateBaseDAO.findOne(OpenApp.class, query);
         
          for(int i=0;i<pidsList.size();i++){
        	  PidRelation pidRe = new PidRelation();
        	  pidRe.setPddPid(pidsList.get(i).get("p_id").toString());
        	  pidRe.setPddUpPid(openApp.getPddPid());
        	  pidRe.setCreateTime(new Timestamp(System.currentTimeMillis()));
        	  pidRelationDAO.save(pidRe);
          }
          
    	//封装
    	statusMap.put("code", 1);
    	statusMap.put("p_id_list", pidsList);
		return statusMap;
    	
    	
	}

	@Override
	public Map<String, Object> findPddPid(HttpServletRequest request) {
		Parameter parameter = ParameterUtil.getParameter(request);
		String openId = parameter.getClient_id();
		
		Map<String ,Object> statusMap = new HashMap<String,Object>();
		if(StringUtil.isEmpty(openId)){
		
			return JsonResponseUtil.getJson(-0x01, "必要参数不能为空");
		}
		QueryModel query = new QueryModel();
		query.clearQuery();
		query.combPreEquals("clientId", openId,"client_id");
		query.combPreEquals("isStop", true);
		query.combPreEquals("isValid", true);
		OpenApp openApp = (OpenApp) dateBaseDAO.findOne(OpenApp.class, query);
		if(openApp == null){
			return JsonResponseUtil.getJson(-0x01, "没有查询到该公众号");
		}
		
		statusMap.put("code", 1);
		statusMap.put("pid", openApp.getPddPid());
		return statusMap;
	}

	@Override
	public Map<String, Object> createPddPid(HttpServletRequest request) {
		Parameter parameter = ParameterUtil.getParameter(request);
		
		//String number = parameter.getData().getString("number")==null?"1":parameter.getData().getString("number");
	    
	    Map<String,Object> statusMap = new HashMap<String,Object>();
		
		long l = System.currentTimeMillis();
		
		Map<String,String> signMap = new HashMap<String,String>();
    	
    	//随机生成可以用pdd代理Pids
    	 signMap.put("timestamp", l+"");
    	 signMap.put("type", "pdd.ddk.goods.pid.generate");
    	 signMap.put("data_type", StringUtil.data_type);
    	 signMap.put("client_id", StringUtil.client_id_axp);
    	 signMap.put("number", "1");
    	  String sign1 = MD5Util.getSign(signMap,StringUtil.client_secret_axp);
    	
    	  String url = "http://gw-api.pinduoduo.com/api/router";
          String param = "type=pdd.ddk.goods.pid.generate&data_type=JSON&timestamp="+l+"&sign="+sign1+"&client_id="+StringUtil.client_id_axp+"&number=1";
          
          List<Map<String, Object>> pidsList = UrlUtil.sendPostForList(url, param,"p_id_generate_response");
          
          if(pidsList == null){
        	  return JsonResponseUtil.getJson(-0x01, "连接超时没有数据,重新连接");
          }
          
    	//封装
    	statusMap.put("code", 1);
    	statusMap.put("p_id_list", pidsList.get(0).get("p_id"));
		return statusMap;
	}
	
	
	@Override
	public Map<String,Object> saveUpPid(){
		
		QueryModel query = new QueryModel();
		query.clearQuery();
		List<OpenPddIncrementGoods2> openGoods = dateBaseDAO.findLists(OpenPddIncrementGoods2.class, query);
		PidRelation upPid = new PidRelation();
		for(OpenPddIncrementGoods2 p:openGoods){
			try {
				query.clearQuery();
				query.combPreEquals("pddPid", p.getPid(),"pdd_pid");
				upPid = (PidRelation) dateBaseDAO.findOne(PidRelation.class, query);
				p.setUpPid(upPid.getPddUpPid());
				openPddIncrementGoods2DAO.saveOrUpdate(p);
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				}
			
		}
		
		
		
		return null;
		
	}

	//通过pid 查询到对应的提现list
	@Override
	public Map<String, Object> getWithdrawList(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		String openId = parameter.getClient_id();
		String sign = parameter.getSign();
		
		
		 if(parameter == null ||openId == null || sign == null ){
	    	return JsonResponseUtil.getJson(-0x01, "必要参数不能为空");
	    }
	    
	    Map<String,Object> statusMap = new HashMap<String,Object>();
	    Map<String,Object> dataMap = new HashMap<String,Object>();
		OpenClient oc = getOpenClientByClientId(openId);
		long l = System.currentTimeMillis();
		
		Map<String,String> signMap = new HashMap<String,String>();
    	signMap.put("client_id", openId);
    	
    	if(oc != null){
    		if(!MD5Util.getSign(signMap, oc.getClientSecret()).equals(sign)){
    			return JsonResponseUtil.getJson(-0x01, "签名验证不通过！");
    		}
    	}
	    QueryModel model = new QueryModel();
	    model.clearQuery();
	    model.combPreEquals("clientId", openId);
	    model.setOrder("createTime DESC");
	    List<Money> moneyList = dateBaseDAO.findLists(Money.class, model);
	    if(moneyList.size()>0 && moneyList != null){
	    	
	    	statusMap.put("status", 1);
	    	statusMap.put("message", "请求成功");
	    	statusMap.put("data", moneyList);
	    }else{
	    	statusMap.put("status", -1);
	    	statusMap.put("message", "没有数据返回");
	    }
	    	
	   	
	    	
		return statusMap;
		
	}

	
	


}
	
