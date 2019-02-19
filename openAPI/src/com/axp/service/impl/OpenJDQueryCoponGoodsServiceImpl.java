package com.axp.service.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;























import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.axp.dao.DateBaseDAO;
import com.axp.domain.OpenGoodsCurrent;
import com.axp.domain.OpenJDClassify;
import com.axp.domain.OpenJDCoponGoods;
import com.axp.domain.OpenJDCoponGoods2;
import com.axp.domain.OpenJDOrederList;
import com.axp.domain.OpenJDQueryCoponGoods;
import com.axp.domain.OpenJDQueryCoponGoods2;
import com.axp.domain.OpenJDQueryExplosiveGoods;
import com.axp.domain.OpenJDQueryExplosiveGoods2;
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
public class OpenJDQueryCoponGoodsServiceImpl extends BaseServiceImpl<OpenJDQueryCoponGoods> implements OpenJDQueryCoponGoodsService{

	public static  String url = "https://api.jd.com/routerjson"; //jd请求
	public static  String v = "2.0";//京东版本
	public static String app_key = "5D0A058C5A4D0FD677A5010B01B34554";
	public static String app_secret = "4b15ca55a6f04b62928f3075d8f7336e";
	
	public static String http = "http://img14.360buyimg.com/n1/";
	
	@Resource
	private DateBaseDAO dateBaseDAO;
	
	@Override
	public Map<String, Object> coponGoods() {
		
		
		 OpenGoodsCurrent ogc = openGoodsCurrentDAO.findById(3); //优惠商品标志
		 OpenGoodsCurrent ogc1 = openGoodsCurrentDAO.findById(4); //爆款商品标志
		 
		 if(ogc.getOpenGoods()==1){
	        	openJDQueryCoponGoodsDAO.delAll();
	       
	      }else{
	        	
				openJDQueryCoponGoods2DAO.delAll();
	      }
		 if(ogc1.getOpenGoods()==1){
			 openJDQueryExplosiveGoodsDAO.delAll();
	       
	      }else{
	    	  openJDQueryExplosiveGoods2DAO.delAll();
	      }
		Map<String,Object> statusMap = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		
		String method = "jingdong.UnionThemeGoodsService.queryCouponGoods";
		String timestamp = DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date());
		Integer pageSize = 100;
		String sign ;
		String param;
		List<Map<String, Object>> goodsList = new ArrayList<Map<String,Object>>();
		Map<String,String> map = new HashMap<String,String>();
		
		for(int i=0;i<10000;i+=100){
			
			//获取优惠商品
			map.clear();
			map.put("method", method);
			map.put("v", v);
			map.put("app_key", app_key);
			map.put("access_token", StringUtil.access_token);
			map.put("pageSize", String.valueOf(pageSize));
			map.put("timestamp",timestamp+"");
			map.put("from",i+"");
			sign = MD5Util.getSign(map,app_secret);
		
			param = "v="+v+"&method=jingdong.UnionThemeGoodsService.queryCouponGoods&app_key="+app_key+"&access_token="
					+StringUtil.access_token+"&360buy_param_json={\"from\":\""+i+"\",\"pageSize\":\""+String.valueOf(pageSize)+"\"}&timestamp="+timestamp+"&sign="+sign;
			goodsList.clear();
			goodsList = UrlUtil.jdsendPostForList(url, param,"jingdong_UnionThemeGoodsService_queryCouponGoods_responce","queryCouponGoods_result");
			
			jdGoods(goodsList,ogc.getOpenGoods(),1);
			dataMap.put("优惠券", goodsList);
			
			//获取爆款商品
			map.clear();
			map.put("method", "jingdong.UnionThemeGoodsService.queryExplosiveGoods");
			map.put("v", v);
			map.put("app_key", app_key);
			map.put("access_token", StringUtil.access_token);
			map.put("pageSize", String.valueOf(pageSize));
			map.put("timestamp",timestamp+"");
			map.put("from",i+"");
			sign = MD5Util.getSign(map,app_secret);
			param = "v="+v+"&method=jingdong.UnionThemeGoodsService.queryExplosiveGoods&app_key="+app_key+"&access_token="
					+StringUtil.access_token+"&360buy_param_json={\"from\":\""+i+"\",\"pageSize\":\""+String.valueOf(pageSize)+"\"}&timestamp="+timestamp+"&sign="+sign;
			goodsList.clear();
			goodsList = UrlUtil.jdsendPostForList(url, param,"jingdong_UnionThemeGoodsService_queryExplosiveGoods_responce","queryExplosiveGoods_result");
			
			
			jdGoods(goodsList,ogc.getOpenGoods(),2);
			
			
			dataMap.put("爆款", goodsList);
		
		}
		
		if(ogc.getOpenGoods()==1){
        	ogc.setOpenGoods(2);
        }else{
        	ogc.setOpenGoods(1);
        }
		if(ogc1.getOpenGoods()==1){
        	ogc1.setOpenGoods(2);
        }else{
        	ogc1.setOpenGoods(1);
        }
		
		openGoodsCurrentDAO.saveOrUpdate(ogc);
		openGoodsCurrentDAO.saveOrUpdate(ogc1);
		statusMap.put("data", dataMap);
		return statusMap;
	}
	//保存商品统一调用的方法
	
	public Map<String ,Object> jdGoods(List<Map<String, Object>> goodsList,Integer partId,Integer goodsTable){
		//List<OpenJDQueryCoponGoods> openJDCoponGoods = new ArrayList<OpenJDQueryCoponGoods>();
		//List<OpenJDQueryCoponGoods2> openJDCoponGoods2 = new ArrayList<OpenJDQueryCoponGoods2>();
		
		if(goodsList.size()>0 && goodsList != null){
		
			for(int j=0;j<goodsList.size();j++){
				
				try {
					Map<String, Object> gmap = goodsList.get(j);
					
					String skuId = null;
					if(gmap.get("skuId")!=null){
						skuId =  gmap.get("skuId").toString();
					 }
					String skuName = null;
					if(gmap.get("skuName")!=null){
						skuName =  gmap.get("skuName").toString();
					 }
					String skuUrl = null;
					if(gmap.get("skuUrl")!=null){
						skuUrl =  gmap.get("skuUrl").toString();
					 }
					String shopName = null;
					if(gmap.get("shopName")!=null){
						shopName =  gmap.get("shopName").toString();
					 }
					String shopUrl = null;
					if(gmap.get("shopUrl")!=null){
						shopUrl =  gmap.get("shopUrl").toString();
					 }
					String imgUrl = null;
					if(gmap.get("imgUrl")!=null){
						imgUrl =  gmap.get("imgUrl").toString();
						if(!imgUrl.startsWith(http)){ //有的商品是没有域名的,
							imgUrl = http + imgUrl;
						}
					 }
					String pcPrice = null;
					if(gmap.get("pcPrice")!=null){
						pcPrice =  gmap.get("pcPrice").toString();
					 }
					String wlPrice = null;
					if(gmap.get("wlPrice")!=null){
						wlPrice =  gmap.get("wlPrice").toString();
					 }
					String exPrice = null;
					if(gmap.get("exPrice")!=null){
						exPrice =  gmap.get("exPrice").toString();
					 }
					String couponTab = null;
					if(gmap.get("couponTab")!=null){
						couponTab =  gmap.get("couponTab").toString();
					 }
					String couponNote = null;
					if(gmap.get("couponNote")!=null){
						couponNote =  gmap.get("couponNote").toString();
					 }
					String discountPrice = null;
					if(gmap.get("discountPrice")!=null){
						discountPrice =  gmap.get("discountPrice").toString();
					 }
					String discountRate = null;
					if(gmap.get("discountRate")!=null){
						discountRate =  gmap.get("discountRate").toString();
					 }
					String startTime = null;
					if(gmap.get("startTime")!=null){
						startTime =  gmap.get("startTime").toString();
					 }
					String endTime = null;
					if(gmap.get("endTime")!=null){
						endTime =  gmap.get("endTime").toString();
					 }
					String isLock = null;
					if(gmap.get("isLock")!=null){
						isLock =  gmap.get("isLock").toString();
					 }
					String manJianNote = null;
					if(gmap.get("manJianNote")!=null){
						manJianNote =  gmap.get("manJianNote").toString();
					 }
					String pcCommission = null;
					if(gmap.get("pcCommission")!=null){
						pcCommission =  gmap.get("pcCommission").toString();
					 }
					String pcCommissionShare = null;
					if(gmap.get("pcCommissionShare")!=null){
						pcCommissionShare =  gmap.get("pcCommissionShare").toString();
					 }
					String inOrderComm = null;
					if(gmap.get("inOrderComm")!=null){
						inOrderComm =  gmap.get("inOrderComm").toString();
					 }
					String inOrderCount = null;
					if(gmap.get("inOrderCount")!=null){
						inOrderCount =  gmap.get("inOrderCount").toString();
					 }
					String plan = null;
					if(gmap.get("plan")!=null){
						plan =  gmap.get("plan").toString();
					 }
					String isPlan = null;
					if(gmap.get("isPlan")!=null){
						isPlan =  gmap.get("isPlan").toString();
					 }
					String prmTab = null;
					if(gmap.get("prmTab")!=null){
						prmTab =  gmap.get("prmTab").toString();
					 }
					String realRate = null;
					if(gmap.get("realRate")!=null){
						realRate =  gmap.get("realRate").toString();
					 }
					String adowner = null;
					if(gmap.get("adowner")!=null){
						adowner =  gmap.get("adowner").toString();
					 }
					String vid = null;
					if(gmap.get("vid")!=null){
						vid =  gmap.get("vid").toString();
					 }
					String wlCommission = null;
					if(gmap.get("wlCommission")!=null){
						wlCommission =  gmap.get("wlCommission").toString();
					 }
					if(goodsTable == 1){ // 保存优惠商品
						if(partId==2){
							
							OpenJDQueryCoponGoods jdGoods = new OpenJDQueryCoponGoods();
							jdGoods.setAdowner(adowner);
							jdGoods.setCouponNote(couponNote);
							jdGoods.setCouponTab(couponTab);
							jdGoods.setDiscountPrice(discountPrice);
							jdGoods.setDiscountRate(discountRate);
							jdGoods.setEndTime(endTime);
							jdGoods.setExPrice(exPrice);
							jdGoods.setImgUrl(StringUtils.deleteWhitespace(imgUrl));
							jdGoods.setInOrderComm(inOrderComm);
							jdGoods.setInOrderCount(inOrderCount);
							jdGoods.setIsLock(isLock);
							jdGoods.setIsPlan(isPlan);
							jdGoods.setManJianNote(manJianNote);
							jdGoods.setPcCommission(pcCommission);
							jdGoods.setPcCommissionShare(pcCommissionShare);
							jdGoods.setPcPrice(pcPrice);
							jdGoods.setPlan(plan);
							jdGoods.setPrmTab(prmTab);
							jdGoods.setRealRate(realRate);
							jdGoods.setShopName(shopName);
							jdGoods.setShopUrl(shopUrl);
							jdGoods.setSkuId(skuId);
							jdGoods.setSkuName(skuName);
							jdGoods.setSkuUrl(skuUrl);
							jdGoods.setStartTime(startTime);
							jdGoods.setVid(vid);
							jdGoods.setWlCommission(wlCommission);
							jdGoods.setWlPrice(wlPrice);
							
							//openJDCoponGoods.add(jdGoods);
							openJDQueryCoponGoodsDAO.save(jdGoods);
							
						}else{
							OpenJDQueryCoponGoods2 jdGoods = new OpenJDQueryCoponGoods2();
							jdGoods.setAdowner(adowner);
							jdGoods.setCouponNote(couponNote);
							jdGoods.setCouponTab(couponTab);
							jdGoods.setDiscountPrice(discountPrice);
							jdGoods.setDiscountRate(discountRate);
							jdGoods.setEndTime(endTime);
							jdGoods.setExPrice(exPrice);
							jdGoods.setImgUrl(StringUtils.deleteWhitespace(imgUrl));
							jdGoods.setInOrderComm(inOrderComm);
							jdGoods.setInOrderCount(inOrderCount);
							jdGoods.setIsLock(isLock);
							jdGoods.setIsPlan(isPlan);
							jdGoods.setManJianNote(manJianNote);
							jdGoods.setPcCommission(pcCommission);
							jdGoods.setPcCommissionShare(pcCommissionShare);
							jdGoods.setPcPrice(pcPrice);
							jdGoods.setPlan(plan);
							jdGoods.setPrmTab(prmTab);
							jdGoods.setRealRate(realRate);
							jdGoods.setShopName(shopName);
							jdGoods.setShopUrl(shopUrl);
							jdGoods.setSkuId(skuId);
							jdGoods.setSkuName(skuName);
							jdGoods.setSkuUrl(skuUrl);
							jdGoods.setStartTime(startTime);
							jdGoods.setVid(vid);
							jdGoods.setWlCommission(wlCommission);
							jdGoods.setWlPrice(wlPrice);
							//openJDCoponGoods2.add(jdGoods);
							
							openJDQueryCoponGoods2DAO.save(jdGoods);
						}
					}else if(goodsTable == 2){ //保存爆款商品
						if(partId==2){
							
							OpenJDQueryExplosiveGoods jdGoods = new OpenJDQueryExplosiveGoods();
							jdGoods.setAdowner(adowner);
							jdGoods.setCouponNote(couponNote);
							jdGoods.setCouponTab(couponTab);
							jdGoods.setDiscountPrice(discountPrice);
							jdGoods.setDiscountRate(discountRate);
							jdGoods.setEndTime(endTime);
							jdGoods.setExPrice(exPrice);
							jdGoods.setImgUrl(StringUtils.deleteWhitespace(imgUrl));
							jdGoods.setInOrderComm(inOrderComm);
							jdGoods.setInOrderCount(inOrderCount);
							jdGoods.setIsLock(isLock);
							jdGoods.setIsPlan(isPlan);
							jdGoods.setManJianNote(manJianNote);
							jdGoods.setPcCommission(pcCommission);
							jdGoods.setPcCommissionShare(pcCommissionShare);
							jdGoods.setPcPrice(pcPrice);
							jdGoods.setPlan(plan);
							jdGoods.setPrmTab(prmTab);
							jdGoods.setRealRate(realRate);
							jdGoods.setShopName(shopName);
							jdGoods.setShopUrl(shopUrl);
							jdGoods.setSkuId(skuId);
							jdGoods.setSkuName(skuName);
							jdGoods.setSkuUrl(skuUrl);
							jdGoods.setStartTime(startTime);
							jdGoods.setVid(vid);
							jdGoods.setWlCommission(wlCommission);
							jdGoods.setWlPrice(wlPrice);
							openJDQueryExplosiveGoodsDAO.save(jdGoods);
							
						}else{
							OpenJDQueryExplosiveGoods2 jdGoods = new OpenJDQueryExplosiveGoods2();
							jdGoods.setAdowner(adowner);
							jdGoods.setCouponNote(couponNote);
							jdGoods.setCouponTab(couponTab);
							jdGoods.setDiscountPrice(discountPrice);
							jdGoods.setDiscountRate(discountRate);
							jdGoods.setEndTime(endTime);
							jdGoods.setExPrice(exPrice);
							jdGoods.setImgUrl(StringUtils.deleteWhitespace(imgUrl));
							jdGoods.setInOrderComm(inOrderComm);
							jdGoods.setInOrderCount(inOrderCount);
							jdGoods.setIsLock(isLock);
							jdGoods.setIsPlan(isPlan);
							jdGoods.setManJianNote(manJianNote);
							jdGoods.setPcCommission(pcCommission);
							jdGoods.setPcCommissionShare(pcCommissionShare);
							jdGoods.setPcPrice(pcPrice);
							jdGoods.setPlan(plan);
							jdGoods.setPrmTab(prmTab);
							jdGoods.setRealRate(realRate);
							jdGoods.setShopName(shopName);
							jdGoods.setShopUrl(shopUrl);
							jdGoods.setSkuId(skuId);
							jdGoods.setSkuName(skuName);
							jdGoods.setSkuUrl(skuUrl);
							jdGoods.setStartTime(startTime);
							jdGoods.setVid(vid);
							jdGoods.setWlCommission(wlCommission);
							jdGoods.setWlPrice(wlPrice);
							openJDQueryExplosiveGoods2DAO.save(jdGoods);
						}
					}
//					if(partId==2){
//						openJDQueryCoponGoodsDAO.saveList(openJDCoponGoods);
//					}else{
//						openJDQueryCoponGoods2DAO.saveList(openJDCoponGoods2);
//						
//					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		return null;
		
	}
	
	//作为接口访问,数据处理
	@Override
	public Map<String, Object> searchCouponGoods(HttpServletRequest request,
			HttpServletResponse response) {
		
		Parameter parameter = ParameterUtil.getParameter(request);
		//skuId集合，长度最大30，可为空。如果传值，忽略其他查询条件。
		String skuIds = parameter.getData().getString("skuIds") == null ? "" : parameter.getData().getString("skuIds");
		String pageIndex = parameter.getData().getString("pageIndex") == null ? "1" : parameter.getData().getString("pageIndex");//页码
		String pageSize = parameter.getData().getString("pageSize") == null ?"30":parameter.getData().getString("pageSize");
		String goodsKeyword = parameter.getData().getString("goodsKeyword") == null ? "" : parameter.getData().getString("goodsKeyword");
		String cid3 = parameter.getData().getString("cid3") == null ? "" : parameter.getData().getString("cid3");//三级类目
		String priceFrom = parameter.getData().getString("priceFrom") == null ? "" : parameter.getData().getString("priceFrom");//价格下限
		String priceTo = parameter.getData().getString("priceTo") == null ? "" : parameter.getData().getString("priceTo");//价格上限
		
		String timestamp = DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date());
		String method = "jingdong.union.search.queryCouponGoods" ;
		
		if(parameter == null ){
			return JsonResponseUtil.getJson(-0x01, "必要参数不能为空");
		}
		if("".equals(pageSize)){
			pageSize = "30";
		}
		
		Map<String, Object> statusMap = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String,Object>();
		try {
			Map<String,String> map = new HashMap<String,String>();
			if(!"".equals(skuIds)){ 
				map.put("skuIdList", skuIds);
			}else{
				map.put("pageIndex", pageIndex);
				map.put("pageSize", pageSize);
				map.put("goodsKeyword", goodsKeyword);
				map.put("priceFrom", priceFrom);
				map.put("priceTo", priceTo);
				map.put("cid3", cid3);
			}
			
			map.put("method", method);
			map.put("v", v);
			map.put("app_key", app_key);
			map.put("access_token", StringUtil.access_token);
			map.put("timestamp",timestamp+"");
			
			String sign = MD5Util.getSign(map,app_secret);
			
			String param = "v="+v+"&method="+method+"&app_key="+app_key+"&access_token="
					+StringUtil.access_token+"&360buy_param_json={\"skuIdList\":\""+skuIds+"\",\"pageIndex\":\""+pageIndex+"\",\"pageSize\":\""+pageSize+"\",\"goodsKeyword\":\""+URLEncoder.encode(goodsKeyword, "UTF-8")+"\",\"priceFrom\":\""+priceFrom+"\",\"priceTo\":\""+priceTo+"\",\"cid3\":\""+cid3+"\"}&timestamp="+timestamp+"&sign="+sign;
			
			List<Map<String,Object>> goodsList = UrlUtil.jdsendPostForList(url, param,"jingdong_union_search_queryCouponGoods_responce","query_coupon_goods_result");
			if(goodsList != null && goodsList.size()>0){
				//数据处理 图片加上前缀
				for(int j=0;j<goodsList.size();j++){
					Map<String, Object> gmap = goodsList.get(j);
					String imageurl = null;
					if(gmap.get("imageurl")!=null){
						imageurl =  gmap.get("imageurl").toString();
						if(!imageurl.startsWith(http)){ 
							gmap.put("imageurl", http + imageurl);
						}
					 }
					String materiaUrl = "";
					if(gmap.get("materiaUrl")!=null){
						materiaUrl =  gmap.get("materiaUrl").toString();
						if(!materiaUrl.startsWith("http://")){ 
							gmap.put("materiaUrl", "http://" + materiaUrl);
						}
					}
					List<Map<String,Object>> couponList = null;
					String link = null;
					if(gmap.get("couponList")!=null){
						couponList =  (List<Map<String, Object>>) gmap.get("couponList");
						for(int f=0;f<couponList.size();f++){
							Map<String,Object> map1 = couponList.get(f);
							if(map1.get("link") != null){
								link =  map1.get("link").toString();
								if(!link.startsWith("http:")){
									link = "http:" + link;
									map1.put("link", link);
									couponList.set(f, map1);
									gmap.put("couponList", couponList);
								}
							}
						}
						
					 }
				}
				dataMap.put("goodsList", goodsList);
				statusMap.put("message", "请求成功");
				statusMap.put("status", "1");
			}else{
				statusMap.put("status", "-1");
				statusMap.put("message", "没有查询到");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		statusMap.put("dataMap", dataMap);
		return statusMap;
	}
	

	//作为同步商品使用
	
	@Override
	public Map<String, Object> synCouponGoods() {
		
		 OpenGoodsCurrent ogc = openGoodsCurrentDAO.findById(5); //优惠券商品标志
		 
		 if(ogc.getOpenGoods()==1){
	        openJDCoponGoodsDAO.delAll();
	      }else{
			openJDCoponGoods2DAO.delAll();
	      }
		 
		String timestamp = DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date());
		String method = "jingdong.union.search.queryCouponGoods" ;
		
		Map<String,Object> statusMap = new HashMap<String,Object>();
		Map<String,String> map = new HashMap<String,String>();
		
		QueryModel query = new QueryModel();
		query.clearQuery();
		query.combPreEquals("grade",0);//一级分类列表 用于自定义分类
		List<OpenJDClassify> gradeList = dateBaseDAO.findLists(OpenJDClassify.class, query);
//		
//		query.clearQuery();
//		query.combPreEquals("grade", 1);
//		query.combCondition("classify is not null");
//		List<OpenJDClassify> classList_1 = dateBaseDAO.findLists(OpenJDClassify.class, query);
		List<OpenJDClassify> classList_2 = new ArrayList<OpenJDClassify>();
		for(OpenJDClassify c:gradeList){
			query.clearQuery();
			query.combPreEquals("parentId",c.getCatId());
			query.combPreEquals("grade",1);
			List<OpenJDClassify> classList = dateBaseDAO.findPageList(OpenJDClassify.class, query,0,5);
			classList_2.addAll(classList);
			
		}
		
		List<OpenJDClassify> classList = new ArrayList<OpenJDClassify>();
		List<Integer> class_2 = new  ArrayList<Integer>();
		for(OpenJDClassify c:classList_2){
			class_2.add(c.getCatId());
			query.clearQuery();
			query.combPreEquals("grade",2);
			query.combPreEquals("parentId",c.getCatId());
			List<OpenJDClassify> class_3 = dateBaseDAO.findPageList(OpenJDClassify.class, query,0,6);
			classList.addAll(class_3);
		}
//		List<OpenJDClassify> classList = dateBaseDAO.findLists(OpenJDClassify.class, query);
		
		List<OpenJDCoponGoods2> OpenJDCoponGoods2 = new ArrayList<OpenJDCoponGoods2>();
		List<OpenJDCoponGoods> OpenJDCoponGoods = new ArrayList<OpenJDCoponGoods>();
		
		try {
			for(OpenJDClassify c :classList){
				String catId = String.valueOf(c.getCatId());
				//String catId = "16776";
					int count = 0;
					for(int i=1;i<300;i++){  //京东起始页默认是1 
						map.clear();
						map.put("method", method);
						map.put("v", v);
						map.put("app_key", app_key);
						map.put("access_token", StringUtil.access_token);
						map.put("timestamp",timestamp+"");
						map.put("skuIdList", "");
						map.put("pageIndex", i+"");
						map.put("pageSize", "30");
						map.put("goodsKeyword", "");
						map.put("priceFrom", "");
						map.put("priceTo", "");
						map.put("cid3", catId);
						String sign = MD5Util.getSign(map,app_secret);
						String param1 = "v="+v+"&method="+method+"&app_key="+app_key+"&access_token="
								+StringUtil.access_token+"&360buy_param_json={\"skuIdList\":\""+""+"\",\"pageIndex\":\""+i+"\",\"pageSize\":\""+"30"+"\",\"goodsKeyword\":\""+"\",";
						String param2 = "\"priceFrom\":\""+""+"\",\"priceTo\":\""+""+"\",";
						String param3 = "\"cid3\":\""+catId+"\"}&timestamp="+timestamp+"&sign="+sign;
						
						
						String param = param1+param2+param3;
						
						List<Map<String,Object>> goodsList = UrlUtil.jdsendPostForList(url, param,"jingdong_union_search_queryCouponGoods_responce","query_coupon_goods_result");
						if(goodsList.size()>0 && goodsList!=null){
							for(int j=0;j<goodsList.size();j++){
								Map<String, Object> gmap = goodsList.get(j);
								String wlCommissionShare = null;
								if(gmap.get("wlCommissionShare")!=null){
									if(Double.valueOf(gmap.get("wlCommissionShare").toString())<5){ //无线佣金 <5 的跳过
										continue;
									}else{
										wlCommissionShare =  gmap.get("wlCommissionShare").toString();
									}
								}
								String pcPrice = null;
								if(gmap.get("pcPrice")!=null){
//									if(Double.valueOf(gmap.get("pcPrice").toString())<0){
//										continue;
//									}else{
										pcPrice =  gmap.get("pcPrice").toString();
//									}
								}
								String wlPrice = null;
								if(gmap.get("wlPrice")!=null){
//									if(Double.valueOf(gmap.get("wlPrice").toString())<0){
//										continue;
//									}else{
										wlPrice =  gmap.get("wlPrice").toString();
//										
//									}
								}
								
								List<Map<String,Object>> couponList = null;
								String link = null;
								String bindType = null;
								String discount = null;
								String quota = null;
								String platformType = null;
								String beginTime = null ;
								String endTime = null;
								if(gmap.get("couponList")!=null){
									couponList =  (List<Map<String, Object>>) gmap.get("couponList");
									for(int f=0;f<couponList.size();f++){
										Map<String,Object> map1 = couponList.get(f);
										
										if(map1.get("link") != null){
											link =  map1.get("link").toString();
											if(!link.startsWith("http:")){
												link = "https:" + link;
												map1.put("link", link);
												couponList.set(f, map1);
												bindType =  couponList.get(f).get("bindType").toString();
												discount =  couponList.get(f).get("discount").toString();
												quota =  couponList.get(f).get("quota").toString();
												platformType =  couponList.get(f).get("platformType").toString();
												beginTime =  couponList.get(f).get("beginTime").toString();
												endTime =  couponList.get(f).get("endTime").toString();
											}else{
												continue;
											}
										}
									}
								
								}
								
								
								
								String cid = null;
								Integer diyCid = null;
								if(gmap.get("cid")!=null){
									cid =  gmap.get("cid").toString();
									
									for(OpenJDClassify m :gradeList){
										if(Integer.valueOf(cid).equals(m.getCatId())){
											diyCid = m.getClassify();
											break;
										}
									}
									
								}
							
								String cidName = null;
								if(gmap.get("cidName")!=null){
									cidName =  gmap.get("cidName").toString();
								}
								String skuId = null;
								if(gmap.get("skuId")!=null){
									skuId =  gmap.get("skuId").toString();
								}
								String skuName = null;
								if(gmap.get("skuName")!=null){
									skuName =  gmap.get("skuName").toString();
								}
								
								String inOrderCount = null;
								if(gmap.get("inOrderCount")!=null){
									inOrderCount =  gmap.get("inOrderCount").toString();
								}
								
								String isJdSale = null;
								if(gmap.get("isJdSale")!=null){
									isJdSale =  gmap.get("isJdSale").toString();
								}
								String materiaUrl = null;
								if(gmap.get("materiaUrl")!=null){
									materiaUrl =  gmap.get("materiaUrl").toString();
									if(!materiaUrl.startsWith("http://")){ //有的商品是没有域名的,
										materiaUrl = "http://" + materiaUrl;
									}
								}
								String commissionShare = null;
								if(gmap.get("commissionShare")!=null){
									commissionShare =  gmap.get("commissionShare").toString();
								}
								String cid3 = null;
								if(gmap.get("cid3")!=null){
									cid3 =  gmap.get("cid3").toString();
								}
								String cid2 = null;
								if(gmap.get("cid2")!=null){
									cid2 =  gmap.get("cid2").toString();
								}
								String cid2Name = null;
								if(gmap.get("cid2Name")!=null){
									cid2Name =  gmap.get("cid2Name").toString();
								}
								String isSeckill = null;
								if(gmap.get("isSeckill")!=null){
									isSeckill =  gmap.get("isSeckill").toString();
								}
								
								String cid3Name = null;
								if(gmap.get("cid3Name")!=null){
									cid3Name =  gmap.get("cid3Name").toString();
								}
								String imageurl = null;
								if(gmap.get("imageurl")!=null){
									imageurl =  gmap.get("imageurl").toString();
									if(!imageurl.startsWith(http)){ //有的商品是没有域名的,
										imageurl = http + imageurl;
									}
								}
								String vid = null;
								if(gmap.get("vid")!=null){
									vid =  gmap.get("vid").toString();
								}
								if(ogc.getOpenGoods()==1){
									OpenJDCoponGoods2 g = new OpenJDCoponGoods2();
									g.setCid(cid);
									g.setCid2(cid2);
									g.setCid2Name(cid2Name);
									g.setCid3(cid3);
									g.setCid3Name(cid3Name);
									g.setCidName(cidName);
									g.setCommissionShare(commissionShare);
									g.setLink(link);
									g.setBeginTime(beginTime);
									g.setBindType(bindType);
									g.setDiscount(discount);
									g.setQuota(quota);
									g.setPlatformType(platformType);
									g.setEndTime(endTime);
									g.setImageurl(imageurl);
									g.setInOrderCount(inOrderCount);
									g.setIsJdSale(isJdSale);
									g.setIsSeckill(isSeckill);
									g.setMateriaUrl(materiaUrl);
									g.setPcPrice(pcPrice);
									g.setSkuId(skuId);
									g.setSkuName(skuName);
									g.setVid(vid);
									g.setWlCommissionShare(wlCommissionShare);
									g.setWlPrice(wlPrice);
									if(couponList != null){
										
										g.setCouponList(couponList.toString());
									}
									g.setRandom(CalcUtil.genRandom());
									g.setClassify(diyCid);
									
									
									 OpenJDCoponGoods2.add(g);
									//openJDCoponGoods2DAO.save(g);
								}else{
									OpenJDCoponGoods g = new OpenJDCoponGoods();
									g.setCid(cid);
									g.setCid2(cid2);
									g.setCid2Name(cid2Name);
									g.setCid3(cid3);
									g.setCid3Name(cid3Name);
									g.setCidName(cidName);
									g.setCommissionShare(commissionShare);
									g.setLink(link);
									g.setBeginTime(beginTime);
									g.setBindType(bindType);
									g.setDiscount(discount);
									g.setQuota(quota);
									g.setPlatformType(platformType);
									g.setEndTime(endTime);
									g.setImageurl(imageurl);
									g.setInOrderCount(inOrderCount);
									g.setIsJdSale(isJdSale);
									g.setIsSeckill(isSeckill);
									g.setMateriaUrl(materiaUrl);
									g.setPcPrice(pcPrice);
									g.setSkuId(skuId);
									g.setSkuName(skuName);
									g.setVid(vid);
									g.setWlCommissionShare(wlCommissionShare);
									g.setWlPrice(wlPrice);
									if(couponList != null){
										
										g.setCouponList(couponList.toString());
									}
									g.setRandom(CalcUtil.genRandom());
									g.setClassify(diyCid);
									
									
									OpenJDCoponGoods.add(g);
									
								//	openJDCoponGoodsDAO.save(g);
								}
							}
							
						}else{
							count ++;
						}
						
						if(count > 2){
							break;
						}
					}
					
			} //for 分类
			if(ogc.getOpenGoods()==1){
				//System.out.println(OpenJDCoponGoods2.size());
				openJDCoponGoods2DAO.saveList(OpenJDCoponGoods2);
			}else{
			//	System.out.println(OpenJDCoponGoods.size());
				openJDCoponGoodsDAO.saveList(OpenJDCoponGoods);
			}
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(ogc.getOpenGoods()==1){
        	ogc.setOpenGoods(2);
        }else{
        	ogc.setOpenGoods(1);
        }
		
		
		openGoodsCurrentDAO.saveOrUpdate(ogc);
		statusMap.put("statr", timestamp);
		statusMap.put("end", DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));
		
		return statusMap;
	}

	
	
	
	
	
	
	
	@Override
	public Map<String, Object> getgoodsInfo(HttpServletRequest request,
			HttpServletResponse response,String skuId) {
		
		String skuIds = null;
		if(skuId == null){  //作为接口给外部调用,
			Parameter parameter = ParameterUtil.getParameter(request);
			skuIds = parameter.getData().getString("skuIds");
			if(parameter == null || skuIds == null ){
				return JsonResponseUtil.getJson(-0x01, "必要参数不能为空");
			}
			
		}else{ //内部调用,同步订单的时候 获取
			skuIds = skuId;
		}
		
		
		Map<String,Object> statusMap = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		Map<String,String> map = new HashMap<String,String>();
		
		
		String timestamp = DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date());
		String method = "jingdong.service.promotion.goodsInfo" ;
		
		map.put("method", method);
		map.put("v", v);
		map.put("app_key", app_key);
		map.put("access_token", StringUtil.access_token);
		map.put("timestamp",timestamp+"");
		map.put("skuIds", skuIds);
		String sign = MD5Util.getSign(map,app_secret);
		String param = "v="+v+"&method="+method+"&app_key="+app_key+"&access_token="
				+StringUtil.access_token+"&360buy_param_json={\"skuIds\":\""+skuIds+"\"}&timestamp="+timestamp+"&sign="+sign;
		
		List<Map<String,Object>> goodsList = UrlUtil.jdsendPostForList(url, param,"jingdong_service_promotion_goodsInfo_responce","getpromotioninfo_result");
		
		if(goodsList.size()>0 && goodsList != null){
			for(int i=0;i<goodsList.size();i++){
				   String imgUrl = goodsList.get(i).get("imgUrl").toString();
				   if(!imgUrl.startsWith(http)){
					   goodsList.get(i).put("imgUrl", http+imgUrl);
				   }
			}
			dataMap.put("goodsList", goodsList);
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功");
		}else{
			statusMap.put("status", -1);
			statusMap.put("message", "请求失败");
		}
		statusMap.put("dataMap", dataMap);
		return statusMap;
	}

	//爆款商品
	@Override
	public Map<String, Object> findExplosiveGoods(HttpServletRequest request, HttpServletResponse response) {

		
		Parameter parameter = ParameterUtil.getParameter(request);
		String from = parameter.getData().getString("from");
		String pageSize = parameter.getData().getString("pageSize");
		
		Map<String,Object> statusMap = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		
		String method = "jingdong.UnionThemeGoodsService.queryExplosiveGoods";
		String timestamp = DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date());
		
		List<Map<String, Object>> goodsList = new ArrayList<Map<String,Object>>();
		Map<String,String> map = new HashMap<String,String>();
		
		try {
			//获取爆款商品
			map.clear();
			map.put("method", method);
			map.put("v", v);
			map.put("app_key", app_key);
			map.put("access_token", StringUtil.access_token);
			map.put("pageSize", pageSize);
			map.put("timestamp",timestamp+"");
			map.put("from",from);
			String sign = MD5Util.getSign(map,app_secret);
			String param = "v="+v+"&method="+method+"&app_key="+app_key+"&access_token="
					+StringUtil.access_token+"&360buy_param_json={\"from\":\""+from+"\",\"pageSize\":\""+pageSize+"\"}&timestamp="+timestamp+"&sign="+sign;
			goodsList.clear();
			goodsList = UrlUtil.jdsendPostForList(url, param,"jingdong_UnionThemeGoodsService_queryExplosiveGoods_responce","queryExplosiveGoods_result");
			if(goodsList.size()>0 && goodsList != null){
				dataMap.put("goodsList", goodsList);
				statusMap.put("status", 1);
				statusMap.put("message", "请求成功");
			}else{
				statusMap.put("status", -1);
				statusMap.put("message", "数据为空");
			}
		
			
		} catch (Exception e) {
			statusMap.put("status", -1);
			statusMap.put("message", "连接异常");
			e.printStackTrace();
		}
		statusMap.put("data", dataMap);
		return statusMap;
	
	}

	//优惠商品
	@Override
	public Map<String, Object> findCoponGoods(HttpServletRequest request, HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		String from = parameter.getData().getString("from");
		String pageSize = parameter.getData().getString("pageSize");
		
		Map<String,Object> statusMap = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		
		String method = "jingdong.UnionThemeGoodsService.queryCouponGoods";
		String timestamp = DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date());
		
		List<Map<String, Object>> goodsList = new ArrayList<Map<String,Object>>();
		Map<String,String> map = new HashMap<String,String>();
		
		try {
			//获取优惠商品
			map.clear();
			map.put("method", method);
			map.put("v", v);
			map.put("app_key", app_key);
			map.put("access_token", StringUtil.access_token);
			map.put("pageSize", pageSize);
			map.put("timestamp",timestamp+"");
			map.put("from",from);
			String sign = MD5Util.getSign(map,app_secret);
		
			String param = "v="+v+"&method="+method+"&app_key="+app_key+"&access_token="
					+StringUtil.access_token+"&360buy_param_json={\"from\":\""+from+"\",\"pageSize\":\""+pageSize+"\"}&timestamp="+timestamp+"&sign="+sign;
			
			goodsList = UrlUtil.jdsendPostForList(url, param,"jingdong_UnionThemeGoodsService_queryCouponGoods_responce","queryCouponGoods_result");
			if(goodsList.size()>0 && goodsList != null){
				dataMap.put("goodsList", goodsList);
				statusMap.put("status", 1);
				statusMap.put("message", "请求成功");
			}else{
				statusMap.put("status", -1);
				statusMap.put("message", "数据为空");
			}
			
			
		} catch (Exception e) {
			statusMap.put("status", -1);
			statusMap.put("message", "连接异常");
			e.printStackTrace();
		}
		statusMap.put("data", dataMap);
		return statusMap;
	
	}

	
	//查询本地优惠券商品 
	@Override
	public Map<String, Object> getCoponGoods(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		String skuIds = parameter.getData().getString("skuId")==""?null:parameter.getData().getString("skuId");
		String classifyId = parameter.getData().getString("classifyId")==""?null:parameter.getData().getString("classifyId") ;//自定义的分类id
		String pageSize = parameter.getData().getString("pageSize")==null?"100":parameter.getData().getString("pageSize");
	    String page = parameter.getData().getString("page")==null?"0":parameter.getData().getString("page");
		
	    Map<String, Object> statusMap = new HashMap<String,Object>();
	    Map<String,Object> dataMap = new HashMap<String,Object>();
	    
	    try {
	    	OpenGoodsCurrent ogc = openGoodsCurrentDAO.findById(5);
	    	
	    	
			QueryModel model = new QueryModel();
			model.clearQuery();
			if(StringUtils.isNotEmpty(skuIds)){
				model.combPreEquals("skuId", skuIds);
			}else{
				
				if(StringUtils.isNotEmpty(classifyId)){
					
					model.combPreEquals("classify", Integer.valueOf(classifyId));
				}
			}
			if(StringUtils.isEmpty(pageSize)){
				
				pageSize = "100";
			}
			if(StringUtils.isEmpty(page)){
				
				page = "0";
			}
			model.combNotEqual("link");
			model.combCondition("endTime>"+System.currentTimeMillis());//结束时间大于当前时间
			
			model.setOrder("random DESC");
			if(ogc.getOpenGoods() == 2){ //使用2表
				int count = dateBaseDAO.findCount(OpenJDCoponGoods2.class, model);
				int totalPage = (count % Integer.valueOf(pageSize)) > 0 ? ((count / Integer.valueOf(pageSize)) + 1)
						: (count / Integer.valueOf(pageSize));
				int start = (Integer.valueOf(page) - 1) * Integer.valueOf(pageSize);
				
//			    int	start = 1+(int)(Math.random()*50) ;
				List<OpenJDCoponGoods2> goodsList = dateBaseDAO.findPageList(OpenJDCoponGoods2.class, model, start, Integer.valueOf(pageSize));
				if(goodsList != null && goodsList.size()>0){
					dataMap.put("count",count);
					dataMap.put("goodsList", goodsList);
					dataMap.put("status", 1);
					dataMap.put("message", "请求成功");
				}else{
					dataMap.put("status", -1);
					dataMap.put("message", "没有数据返回");
				}
			}else{
				int count = dateBaseDAO.findCount(OpenJDCoponGoods.class, model);
				int totalPage = (count % Integer.valueOf(pageSize)) > 0 ? ((count / Integer.valueOf(pageSize)) + 1)
						: (count / Integer.valueOf(pageSize));
				int start = (Integer.valueOf(page) - 1) * Integer.valueOf(pageSize);
				List<OpenJDCoponGoods> goodsList = dateBaseDAO.findPageList(OpenJDCoponGoods.class, model, start, Integer.valueOf(pageSize));
				if(goodsList != null && goodsList.size()>0){
					dataMap.put("count",count);
					dataMap.put("goodsList", goodsList);
					dataMap.put("status", 1);
					dataMap.put("message", "请求成功");
				}else{
					dataMap.put("status", -1);
					dataMap.put("message", "没有数据返回");
				}
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


	
	
}
	
