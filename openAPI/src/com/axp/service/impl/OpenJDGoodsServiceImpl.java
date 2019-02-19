package com.axp.service.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



























import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.axp.dao.DateBaseDAO;
import com.axp.domain.OpenApp;
import com.axp.domain.OpenGoodsCurrent;
import com.axp.domain.OpenJDClassify;
import com.axp.domain.OpenJDGoods;
import com.axp.domain.OpenJDGoods2;
import com.axp.domain.OpenJDOrederList;
import com.axp.domain.OpenPddIncrementGoods2;
import com.axp.service.OpenJDGoodsService;
import com.axp.service.OpenJDQueryCoponGoodsService;
import com.axp.util.CalcUtil;
import com.axp.util.DateUtil;
import com.axp.util.JsonResponseUtil;
import com.axp.util.MD5Util;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;
import com.axp.util.StringUtil;
import com.axp.util.UrlUtil;



@Service
public class OpenJDGoodsServiceImpl extends BaseServiceImpl<OpenJDGoods> implements OpenJDGoodsService{
	
	
	@Resource
	private DateBaseDAO dateBaseDAO;
	 @Autowired
	 public OpenJDQueryCoponGoodsService openJDQueryCoponGoodsServcie;
	
	public static  String url = "https://api.jd.com/routerjson"; //jd请求
	public static  String v = "2.0";//京东版本
	public static String app_key = "5D0A058C5A4D0FD677A5010B01B34554";
	public static String app_secret = "4b15ca55a6f04b62928f3075d8f7336e";
	public static String http = "http://img14.360buyimg.com/n1/"; //图片前缀
	
	
	@Override
	public Map<String, Object> jdGoods(){
		
		
		 OpenGoodsCurrent ogc = openGoodsCurrentDAO.findById(2);   
	        
		 if(ogc.getOpenGoods()==1){
	        	openJDGoods2DAO.delAll();
	       
	        }else{
	        	openJDGoodsDAO.delAll();
	        }
		
		
		String method = "jingdong.union.search.goods.param.query";
		String timestamp = DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date());
		
		Integer pageSize = 100;
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<OpenJDClassify> GoodsJDClassify = openJDClassifyDAO.queryAll();
		
		for(OpenJDClassify jdCatId :GoodsJDClassify){
			
			Integer catId = jdCatId.getCatId();//获取分类id
			
			for(int i=1;i<100;i++){
				
				Map<String,String> map = new HashMap<String,String>();
				map.put("method", method);
				map.put("v", v);
				map.put("timestamp",timestamp+"");
				map.put("app_key", app_key);
				map.put("access_token", StringUtil.access_token);
				map.put("pageIndex", String.valueOf(i));
				map.put("pageSize", String.valueOf(pageSize));
				map.put("cat1Id", String.valueOf(catId));
				
				String sign = MD5Util.getSign(map,app_secret);
				
				String url = "https://api.jd.com/routerjson";
				String param = "v=2.0&method=jingdong.union.search.goods.param.query&app_key="+app_key+"&access_token="
						+StringUtil.access_token+"&360buy_param_json={\"cat1Id\":\""+String.valueOf(catId)+"\",\"owner\":\"\",\"pageIndex\":\""
						+String.valueOf(i)+"\",\"pageSize\":\""+String.valueOf(pageSize)+"\",\"sortName\":\"\",\"sort\":\"\",\"skuIds\":\"\","
						+"\"pingouPriceStart\":\"\",\"pingouPriceEnd\":\"\",\"wlCommissionShareStart\":\"\","
						+"\"wlCommissionShareEnd\":\"\"}&timestamp="+timestamp+"&sign="+sign;
				List<Map<String, Object>> goodsList = UrlUtil.sendPostForList(url, param,"jingdong_union_search_goods_param_query_responce");
				if(goodsList.size()>0 && goodsList != null){
					for(int j=0;j<goodsList.size();j++){
						Map<String, Object> gmap = goodsList.get(j);
						 
						Integer comments = null;
						 if(gmap.get("comments") != null){
							 comments = (Integer) gmap.get("comments");
						 }
						 Integer commissionshare = null;
						 if(gmap.get("commissionShare")!=null){
							 commissionshare = (Integer) gmap.get("commissionshare");
						 }
						 String startTime = null;
						 if(gmap.get("startTime")!=null){
							 startTime =  gmap.get("startTime").toString();
						 }
						 String endTime = null;
						 if(gmap.get("endTime")!=null){
							 endTime =  gmap.get("endTime").toString();
						 }
						 String goodCommentsShare = null;
						 if(gmap.get("goodCommentsShare")!=null){
							 goodCommentsShare = gmap.get("goodCommentsShare").toString();
						 }
						 String imageUrl = null;
						 if(gmap.get("imageUrl")!=null){
							 imageUrl = gmap.get("imageUrl").toString();
							 if(!imageUrl.startsWith("http")){
								 imageUrl = http+imageUrl;
							 }
						 }
						 Integer inOrderCount30Days = null;
						 if(gmap.get("inOrderCount30Days")!=null){
							 inOrderCount30Days = (Integer) gmap.get("inOrderCount30Days");
						 }
						 String pingouPrice = null;
						 if(gmap.get("pingouPrice")!=null){
							 pingouPrice =  gmap.get("pingouPrice").toString();
						 }
						 Integer pingouTmCount = null;
						 if(gmap.get("pingouTmCount")!=null){
							 pingouTmCount = (Integer) gmap.get("pingouTmCount");
						 }
						 String pingouUrl  = null;
						 if(gmap.get("pingouUrl")!=null){
							 pingouUrl = gmap.get("pingouUrl").toString();
						 }
						 String skuId = null;
						 if(gmap.get("skuId")!=null){
							 skuId = gmap.get("skuId").toString();
						 }
						 String skuName = null;
						 if(gmap.get("skuName")!=null){
							 skuName =  gmap.get("skuName").toString();
						 }
						 String wlCommissionShare = null;
						 if(gmap.get("wlCommissionShare")!=null){
							 wlCommissionShare =  gmap.get("wlCommissionShare").toString();
						 }
						 String wlPrice = null;
						 if(gmap.get("wlPrice")!=null){
							 wlPrice =  gmap.get("wlPrice").toString();
						 }
						 
						 if(ogc.getOpenGoods()==2){
							 OpenJDGoods  jdGoods = new OpenJDGoods();
							 jdGoods.setComments(comments);
							 jdGoods.setCommissionshare(wlCommissionShare);
							 jdGoods.setEndTime(endTime);
							 jdGoods.setGoodCommentsShare(goodCommentsShare);
							 jdGoods.setImageUrl(imageUrl);
							 jdGoods.setInOrderCount30Days(inOrderCount30Days);
							 jdGoods.setPingouPrice(pingouPrice);
							 jdGoods.setPingouTmCount(pingouTmCount);
							 jdGoods.setPingouUrl(pingouUrl);
							 jdGoods.setSkuId(skuId);
							 jdGoods.setSkuName(skuName);
							 jdGoods.setStartTime(startTime);
							 jdGoods.setWlCommissionShare(wlCommissionShare);
							 jdGoods.setWlPrice(wlPrice);
							 openJDGoodsDAO.save(jdGoods);
						 }else{
							 OpenJDGoods2  jdGoods = new OpenJDGoods2();
							 jdGoods.setComments(comments);
							 jdGoods.setCommissionshare(wlCommissionShare);
							 jdGoods.setEndTime(endTime);
							 jdGoods.setGoodCommentsShare(goodCommentsShare);
							 jdGoods.setImageUrl(imageUrl);
							 jdGoods.setInOrderCount30Days(inOrderCount30Days);
							 jdGoods.setPingouPrice(pingouPrice);
							 jdGoods.setPingouTmCount(pingouTmCount);
							 jdGoods.setPingouUrl(pingouUrl);
							 jdGoods.setSkuId(skuId);
							 jdGoods.setSkuName(skuName);
							 jdGoods.setStartTime(startTime);
							 jdGoods.setWlCommissionShare(wlCommissionShare);
							 jdGoods.setWlPrice(wlPrice);
							 openJDGoods2DAO.save(jdGoods);
						 }
					}
					
				}
			}
		} //分类
		
			if(ogc.getOpenGoods()==1){
	        	ogc.setOpenGoods(2);
	        }else{
	        	ogc.setOpenGoods(1);
	        }
			openGoodsCurrentDAO.saveOrUpdate(ogc);
         return dataMap;
		
	}

	
	@Override
	public Map<String, Object> getCodeBySubUnionId(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		String openId = parameter.getClient_id();
		String skuIds = parameter.getData().getString("skuIds");
		String subUnionId = parameter.getData().getString("subUnionId")==null?"":parameter.getData().getString("subUnionId");//子联盟Id
		String positionId = parameter.getData().getString("positionId")==null?"":parameter.getData().getString("positionId");//推广位Id
		String pid = parameter.getData().getString("pid")==null?"":parameter.getData().getString("pid");//推广位Id
		
		String method = "jingdong.service.promotion.wxsq.getCodeBySubUnionId";
		String timestamp = DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date());
		String proCont = "1";//京东接口默认固定单品1
		
		if(openId == null){
			return JsonResponseUtil.getJson(-0x01, "必要参数client_id不能为空");
		}
		
		QueryModel queryModel = new QueryModel();
		queryModel.clearQuery();
		queryModel.combPreEquals("clientId", openId,"client_id");
		OpenApp openApp = (OpenApp) dateBaseDAO.findOne(OpenApp.class, queryModel);
		subUnionId = openApp.getId()+subUnionId;
		
		
		
		Map<String,Object> statusMap = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("method", method);
		map.put("v", v);
		map.put("app_key", app_key);
		map.put("access_token", StringUtil.access_token);
		map.put("timestamp",timestamp+"");
		map.put("materialIds", skuIds);
		map.put("proCont", proCont);
		map.put("subUnionId", subUnionId);
		map.put("positionId", positionId);
		map.put("pid", pid);
		String sign = MD5Util.getSign(map,app_secret);
		String param = "v="+v+"&method="+method+"&app_key="+app_key+"&access_token="
				+StringUtil.access_token+"&360buy_param_json={\"materialIds\":\""+skuIds+"\",\"proCont\":\""+proCont+"\",\"subUnionId\":\""+subUnionId+"\",\"positionId\":\""+positionId+"\",\"pid\":\""+pid+"\"}&timestamp="+timestamp+"&sign="+sign;
		
		List<Map<String,Object>> goodsList = UrlUtil.jdsendPostForList(url, param,"jingdong_service_promotion_wxsq_getCodeBySubUnionId_responce","getcodebysubunionid_result");
		
		dataMap.put("goodsList", goodsList);
		
			
		statusMap.put("dataMap", dataMap);
			
		
		return statusMap;
	}


	
	@Override
	public Map<String, Object> couponGetInfo(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		String couponUrl = parameter.getData().getString("couponUrl");//优惠券领取连接
		
		String method = "jingdong.service.promotion.coupon.getInfo";
		String timestamp = DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date());
		
		Map<String,Object> statusMap = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("method", method);
		map.put("v", v);
		map.put("app_key", app_key);
		map.put("access_token", StringUtil.access_token);
		map.put("timestamp",timestamp+"");
		map.put("couponUrl", couponUrl);
		String sign = MD5Util.getSign(map,app_secret);
		String param = "v="+v+"&method="+method+"&app_key="+app_key+"&access_token="
				+StringUtil.access_token+"&360buy_param_json={\"couponUrl\":\""+couponUrl+"\"}&timestamp="+timestamp+"&sign="+sign;
		
		List<Map<String,Object>> goodsList = UrlUtil.jdsendPostForList(url, param,"jingdong_service_promotion_coupon_getInfo_responce","getinfo_result");
		
		dataMap.put("goodsList", goodsList);
		
		
		statusMap.put("dataMap", dataMap);
			
		
		return statusMap;
	}


	
	
	@Override
	public Map<String, Object> getCodeBySubUnionId2(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		String openId = parameter.getClient_id();
		
		String couponUrl = parameter.getData().getString("couponUrl");//优惠券领取连接
		String skuIds = parameter.getData().getString("skuIds");//推广物料,,单品skuId
		String subUnionId = parameter.getData().getString("subUnionId")==null?"":parameter.getData().getString("subUnionId");//子联盟Id 自定义字段
		String positionId = parameter.getData().getString("positionId")==null?"":parameter.getData().getString("positionId");//推广位Id
		String pid = parameter.getData().getString("pid")==null?"":parameter.getData().getString("pid");//推广位Id
		
		if(parameter == null){
			return JsonResponseUtil.getJson(-0x01, "必要参数不能为空");
		}
		if(openId == null){
			return JsonResponseUtil.getJson(-0x01, "必要参数client_id不能为空");
		}
		
		QueryModel queryModel = new QueryModel();
		queryModel.clearQuery();
		queryModel.combPreEquals("clientId", openId,"client_id");
		OpenApp openApp = (OpenApp) dateBaseDAO.findOne(OpenApp.class, queryModel);
		subUnionId = openApp.getId()+subUnionId;
		
		if(couponUrl == null || skuIds == null){
			return JsonResponseUtil.getJson(-0x01, "参数couponUrl和skuIds不能为空");
		}
		
		List<Map<String, Object>> goodsList = null ;
		Map<String, Object> statusMap = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String,Object>();
		String key = null ;
		try {
			String method = "jingdong.service.promotion.coupon.getCodeBySubUnionId";
			String timestamp = DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date());
			
			Map<String,String> map = new HashMap<String,String>();
			
			map.put("method", method);
			map.put("v", v);
			map.put("app_key", app_key);
			map.put("access_token", StringUtil.access_token);
			map.put("timestamp",timestamp+"");
			map.put("couponUrl", couponUrl);
			map.put("materialIds", skuIds);
			map.put("subUnionId", subUnionId);
			map.put("positionId", positionId);
			map.put("pid", pid);
			String sign = MD5Util.getSign(map,app_secret);
			
			String param = "v="+v+"&method="+method+"&app_key="+app_key+"&access_token="
					+StringUtil.access_token+"&360buy_param_json={\"materialIds\":\""+skuIds+"\",\"couponUrl\":\""+URLEncoder.encode(couponUrl)+"\",\"subUnionId\":\""+subUnionId+"\",\"positionId\":\""+positionId+"\",\"pid\":\""+pid+"\"}&timestamp="+timestamp+"&sign="+sign;
			
			goodsList = UrlUtil.jdsendPostForList(url, param,"jingdong_service_promotion_coupon_getCodeBySubUnionId_responce","getcodebysubunionid_result");
			if(goodsList.size()>0 && goodsList != null ){
				dataMap.put("url", goodsList.get(0).get(couponUrl+","+skuIds));
				dataMap.put("status", 1);
				dataMap.put("message", "请求成功");
			}else{
				dataMap.put("status", -1);
				dataMap.put("message", "没有数据");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dataMap.put("status", -1);
			dataMap.put("message", "请求异常");
		}
		
		
		statusMap.put("dataMap", dataMap);
		return statusMap;
	}

	@Override
	public Map<String, Object> queryOrderList(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		String openId = parameter.getClient_id();
		String sign = parameter.getSign();
		String startTime = parameter.getData().getString("startTime");
		String endTime = parameter.getData().getString("endTime");
		String limit = parameter.getData().getString("limit")==null?"20":parameter.getData().getString("limit");
	    String page = parameter.getData().getString("page")==null?"1":parameter.getData().getString("page");
	    Map<String, Object> statusMap = new HashMap<String,Object>();
	    Map<String,Object> dataMap = new HashMap<String,Object>();
	   List<Map<String,Object>> goodsList = new ArrayList<Map<String,Object>>();
	    
	    try {
			QueryModel queryModel = new QueryModel();
			queryModel.clearQuery();
			queryModel.combPreEquals("clientId", openId,"client_id");
			OpenApp openApp = (OpenApp) dateBaseDAO.findOne(OpenApp.class, queryModel);
			String pid = openApp.getPddPid();
			double  maid =0.0;
			if(openApp.getMaid()!=null){
				maid = CalcUtil.div(Integer.parseInt(openApp.getMaid()), 100, 2);
				
			}
			queryModel.clearQuery();
			queryModel.combCondition("orderTime >"+startTime);
			queryModel.combCondition("orderTime <"+endTime);
			
			int count = dateBaseDAO.findCount(OpenJDOrederList.class, queryModel);
			limit="2000";
			int totalPage = (count % Integer.valueOf(limit)) > 0 ? ((count / Integer.valueOf(limit)) + 1)
					: (count / Integer.valueOf(limit));
			int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
			
			List<OpenJDOrederList> openGoods = dateBaseDAO.findPageList(OpenJDOrederList.class, queryModel, start, Integer.valueOf(limit));
			
			
			if(openGoods!= null && openGoods.size()>0){
				for(OpenJDOrederList o : openGoods){
					Map<String,Object> map = new HashMap<String,Object>();
					double actualFee = CalcUtil.round(Double.valueOf(o.getActualFee()) * maid , 2);
					double  estimateFee = CalcUtil.round(Double.valueOf(o.getEstimateFee()) * maid, 2);
					map.put("actualCommission", o.getActualCommission());
					map.put("actualCosPrice", o.getActualCosPrice());
					map.put("actualFee", actualFee);
					map.put("commissionRate", o.getCommissionRate());
					map.put("estimateCommission", o.getEstimateCommission());
					map.put("estimateCosPrice", o.getEstimateCosPrice()); 
					map.put("estimateFee", estimateFee);
					map.put("finalRate", o.getFinalRate());
					map.put("finishTime", o.getFinishTime());
					map.put("firstLevel", o.getFirstLevel());
					map.put("frozenSkuNum", o.getFrozenSkuNum());
					map.put("orderEmt", o.getOrderEmt());
					map.put("orderId", o.getOrderId());
					map.put("orderTime", o.getOrderTime());
					map.put("parentId", o.getParentId());
					map.put("payMonth", o.getPayMonth());
					map.put("payPrice", o.getPayPrice());
					map.put("pid", o.getPid());
					map.put("plus", o.getPlus());
					map.put("popId", o.getPopId());
					map.put("price", o.getPrice());
					map.put("secondLeve", o.getSecondLevel());
					map.put("siteId", o.getSiteId());
					map.put("skuId", o.getSkuId());
					map.put("skuName", o.getSkuName());
					map.put("skuNum", o.getSkuNum());
					map.put("skuReturnNum", o.getSkuReturnNum());
					map.put("spId", o.getSpId());
					map.put("subSideRate", o.getSubSideRate());
					map.put("subsidyRate", o.getSubsidyRate());
					map.put("subUnionId", o.getSubUnionId());
					map.put("thirdLevel", o.getThirdLevel());
					map.put("traceType", o.getTraceType());
					map.put("unionAlias", o.getUnionAlias());
					map.put("unionId", o.getUnionId());
					map.put("unionTag", o.getUnionTag());
					map.put("unionTrafficGroup", o.getUnionTrafficGroup());
					map.put("unionUserName", o.getUnionUserName());
					map.put("validCode", o.getValidCode());
					map.put("img", o.getImg());
					goodsList.add(map);
				}
				dataMap.put("111", openGoods);
				dataMap.put("count",count);
				dataMap.put("goodsList", goodsList);
				dataMap.put("status", 1);
				dataMap.put("message", "请求成功");
			}else{
				dataMap.put("status", -1);
				dataMap.put("message", "没有数据返回");
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dataMap.put("status", -1);
			dataMap.put("message", "请求异常");
		}
	    statusMap.put("dataMap", dataMap);
		return statusMap;
	}
	
	
	
	@Override
	public Map<String, Object> synOrderListSyn(HttpServletRequest request,
			HttpServletResponse response) {
		String pageSize = "500";
		List<Map<String, Object>> goodsList = null ;
		Map<String, Object> statusMap = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String,Object>();
		Date date = new Date();
		String timestamp = DateUtil.formatDate("yyyyMMddHH", date);
		
		String time2 = DateUtil.formatDate("yyyyMMddHH", DateUtil.addHour2Date(-1, date));
		
		String [] times = {timestamp,time2};  //当前小时内  和上一个小时内的
		
		//String [] times = {"2018081310","20180807"};
		
		try {
			for(int t=0;t<times.length;t++){
				
				for(int p=1;p<=10;p++){ // 一共10页 每页 500条
					
					String method = "jingdong.UnionService.queryOrderList";
					Map<String,String> map = new HashMap<String,String>();
					map.put("method", method);
					map.put("v", StringUtil.v);
					map.put("app_key", StringUtil.app_key);
					map.put("access_token", StringUtil.access_token);
					map.put("timestamp",timestamp+"");
					map.put("time", times[t]);
					//map.put("time", String.valueOf(t));
					map.put("pageIndex", String.valueOf(p));
					map.put("pageSize", pageSize);
					map.put("unionId", StringUtil.unionId);
					
					String sign = MD5Util.getSign(map,app_secret);
					
					String param = "v="+StringUtil.v+"&method="+method+"&app_key="+StringUtil.app_key+"&access_token="
							+StringUtil.access_token+"&360buy_param_json={\"time\":\""+times[t]+"\",\"pageIndex\":\""+String.valueOf(p)+"\",\"pageSize\":\""+pageSize+"\",\"unionId\":\""+StringUtil.unionId+"\"}&timestamp="+timestamp+"&sign="+sign;
					
					goodsList = UrlUtil.jdsendPostForList(url, param,"jingdong_UnionService_queryOrderList_responce","result");
					
					if(goodsList.size()>0  && goodsList != null){
						for(int i=0;i<goodsList.size();i++){
							//保存操作
							QueryModel query = new QueryModel();
							query.clearQuery();
							query.combPreEquals("orderId", goodsList.get(i).get("orderId").toString());
							OpenJDOrederList jdOrder = (OpenJDOrederList) dateBaseDAO.findOne(OpenJDOrederList.class, query);
							
							if(jdOrder == null ){
								jdOrder = new OpenJDOrederList();
							}
							
							if(goodsList.get(i).get("finishTime")!=null){
								
								jdOrder.setFinishTime(goodsList.get(i).get("finishTime").toString());
							}
							if(goodsList.get(i).get("orderEmt")!=null){
								jdOrder.setOrderEmt(goodsList.get(i).get("orderEmt").toString());
							}
							
							if(goodsList.get(i).get("orderId")!=null){
								jdOrder.setOrderId(goodsList.get(i).get("orderId").toString());							
							}
							if(goodsList.get(i).get("orderTime")!=null){
								jdOrder.setOrderTime(goodsList.get(i).get("orderTime").toString());
							}
							if(goodsList.get(i).get("parentId")!=null){
								jdOrder.setParentId(goodsList.get(i).get("parentId").toString());
							}
							if(goodsList.get(i).get("payMonth")!=null){
								jdOrder.setPayMonth(goodsList.get(i).get("payMonth").toString());
							}
							if(goodsList.get(i).get("plus")!=null){
								jdOrder.setPlus(goodsList.get(i).get("plus").toString());
							}
							if(goodsList.get(i).get("unionId")!=null){
								jdOrder.setUnionId(goodsList.get(i).get("unionId").toString());
							}
							if(goodsList.get(i).get("unionUserName")!=null){
								jdOrder.setUnionUserName(goodsList.get(i).get("unionUserName").toString());
							}
							if(goodsList.get(i).get("validCode")!=null){
								jdOrder.setValidCode(goodsList.get(i).get("validCode").toString());
							}
							if(goodsList.get(i).get("popId")!=null){
								jdOrder.setPopId(goodsList.get(i).get("popId").toString());
							}
							String skuId = null;
							if(goodsList.get(i).get("skuList")!=null){
								jdOrder.setSkuList(goodsList.get(i).get("skuList").toString());
								List<Map<String,Object>> sku = (List<Map<String, Object>>) goodsList.get(i).get("skuList");
								for(int k = 0;k <1;k++){
									if(sku.get(k).get("skuId")!=null){
										skuId = sku.get(k).get("skuId").toString();
										jdOrder.setSkuId(skuId);
									}
									if(sku.get(k).get("skuName")!=null)jdOrder.setSkuName(sku.get(k).get("skuName").toString());
									if(sku.get(k).get("skuNum")!=null)jdOrder.setSkuNum(sku.get(k).get("skuNum").toString());
									if(sku.get(k).get("actualCommission")!=null)jdOrder.setActualCommission(sku.get(k).get("actualCommission").toString());
									if(sku.get(k).get("actualCosPrice")!=null)jdOrder.setActualCosPrice(sku.get(k).get("actualCosPrice").toString());
									if(sku.get(k).get("actualFee")!=null)jdOrder.setActualFee(sku.get(k).get("actualFee").toString());
									if(sku.get(k).get("commissionRate")!=null)jdOrder.setCommissionRate(sku.get(k).get("commissionRate").toString());
									if(sku.get(k).get("estimateCommission")!=null)jdOrder.setEstimateCommission(sku.get(k).get("estimateCommission").toString());
									if(sku.get(k).get("estimateCosPrice")!=null)jdOrder.setEstimateCosPrice(sku.get(k).get("estimateCosPrice").toString());
									if(sku.get(k).get("estimateFee")!=null)jdOrder.setEstimateFee(sku.get(k).get("estimateFee").toString());
									if(sku.get(k).get("finalRate")!=null)jdOrder.setFinalRate(sku.get(k).get("finalRate").toString());
									if(sku.get(k).get("firstLevel")!=null)jdOrder.setFirstLevel(sku.get(k).get("firstLevel").toString());
									if(sku.get(k).get("frozenSkuNum")!=null)jdOrder.setFrozenSkuNum(sku.get(k).get("frozenSkuNum").toString());
									if(sku.get(k).get("payPrice")!=null)jdOrder.setPayPrice(sku.get(k).get("payPrice").toString());
									if(sku.get(k).get("pid")!=null)	jdOrder.setPid(sku.get(k).get("pid").toString());
									if(sku.get(k).get("skuReturnNum")!=null)jdOrder.setSkuReturnNum(sku.get(k).get("skuReturnNum").toString());
									if(sku.get(k).get("price")!=null)	jdOrder.setPrice(sku.get(k).get("price").toString());
									if(sku.get(k).get("subSideRate")!=null)	jdOrder.setSubSideRate(sku.get(k).get("subSideRate").toString());
									if(sku.get(k).get("subsidyRate")!=null)	jdOrder.setSubsidyRate(sku.get(k).get("subsidyRate").toString());
									if(sku.get(k).get("traceType")!=null)jdOrder.setTraceType(sku.get(k).get("traceType").toString());
									if(sku.get(k).get("spId")!=null)jdOrder.setSpId(sku.get(k).get("spId").toString());
									if(sku.get(k).get("siteId")!=null)jdOrder.setSiteId(sku.get(k).get("siteId").toString());
									if(sku.get(k).get("unionAlias")!=null)jdOrder.setUnionAlias(sku.get(k).get("unionAlias").toString());
									if(sku.get(k).get("unionTrafficGroup")!=null)jdOrder.setUnionTrafficGroup(sku.get(k).get("unionTrafficGroup").toString());
									if(sku.get(k).get("secondLevel")!=null)jdOrder.setSecondLevel(sku.get(k).get("secondLevel").toString());
									if(sku.get(k).get("thirdLevel")!=null)jdOrder.setThirdLevel(sku.get(k).get("thirdLevel").toString());
									if(sku.get(k).get("subUnionId")!=null)jdOrder.setSubUnionId(sku.get(k).get("subUnionId").toString());
									if(sku.get(k).get("unionTag")!=null)jdOrder.setUnionTag(sku.get(k).get("unionTag").toString());
									
									Map<String,Object> info = openJDQueryCoponGoodsServcie.getgoodsInfo(request, response, skuId);
									if(Integer.valueOf(info.get("status").toString()) == 1){
										List<Map<String,Object>> imgList = (List<Map<String, Object>>) ((Map<String, Object>) info.get("dataMap")).get("goodsList");
										
										jdOrder.setImg(imgList.get(0).get("imgUrl").toString());
									}
								}
							}
							openJDOrederListDAO.saveOrUpdate(jdOrder);
						}
						
					
					}else{
						break;
						
					}
					
					
				}
				
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dataMap.put("status", -1);
			dataMap.put("message", "请求异常");
		}
		
		
		dataMap.put("goods", goodsList);
		
		statusMap.put("dataMap", dataMap);
		return statusMap;
	}


	
	
	
}
	







