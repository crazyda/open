package com.axp.service.impl;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.axp.dao.DateBaseDAO;
import com.axp.domain.OpenApp;
import com.axp.domain.OpenClassify;
import com.axp.domain.OpenClient;
import com.axp.domain.OpenGoods;
import com.axp.domain.OpenGoods2;
import com.axp.domain.OpenGoodsAll;
import com.axp.domain.OpenGoodsAll2;
import com.axp.domain.OpenGoodsCurrent;
import com.axp.domain.OpenPddGoods;
import com.axp.domain.OpenPddIncrementGoods2;
import com.axp.domain.OpenPddIncrementGoodsHistory;
import com.axp.domain.PidRelation;
import com.axp.service.OpenClientService;
import com.axp.service.OpenGoodsService;
import com.axp.util.CalcUtil;
import com.axp.util.CreateQRCode;
import com.axp.util.DateUtil;
import com.axp.util.JsonResponseUtil;
import com.axp.util.MD5Util;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;
import com.axp.util.StringUtil;
import com.axp.util.UrlUtil;

@Service
public class OpenGoodsServiceImpl extends BaseServiceImpl<OpenGoods> implements OpenGoodsService{
	
	@Resource
	private DateBaseDAO dateBaseDAO;
	
	@Autowired
	OpenClientService openClientService;
	
	
	@Override
	public Map<String, Object> getPDDGoodsList() {
        Map<String ,String> map = new HashMap<String ,String >();
        OpenGoodsCurrent ogc = openGoodsCurrentDAO.findById(7); //拼多多全部商品标识  
        if(ogc.getOpenGoods()==1){
        	openGoodsAll2DAO.delAll();
       
        }else{
        	openGoodsAllDAO.delAll();
        }
        
        List<OpenClassify> GoodsClassify = openClassifyDAO.queryAll();
        
        long statr = System.currentTimeMillis();
        long l = System.currentTimeMillis();
        Integer optId = 0;
        List<OpenGoodsAll> openGoodsList = new ArrayList<OpenGoodsAll>();
        List<OpenGoodsAll2> openGoods2List = new ArrayList<OpenGoodsAll2>();
        for(OpenClassify classify : GoodsClassify){
        
       optId = classify.getCategoryId();
	    for(int i=1;i<100;i++){
	    	
    	  map.put("type", "pdd.ddk.goods.search");
          map.put("data_type", StringUtil.data_type);
          map.put("timestamp",l+"");
          map.put("client_id", StringUtil.client_id_axp);
          map.put("sort_type", "0");//综合排序
          map.put("with_coupon","true");
          map.put("page",i+"");
          if(optId!=0){
        	  map.put("opt_id", optId+"");
          }
          
          String sign = MD5Util.getSign(map,StringUtil.client_secret_axp);
          String url = "http://gw-api.pinduoduo.com/api/router";
          String param = "type=pdd.ddk.goods.search&sort_type=0&page="+i+"&with_coupon=true&data_type=JSON&timestamp="+l+"&client_id="+StringUtil.client_id_axp+"&sign="+sign
        		  			;
          if(optId!=0)param+="&opt_id="+optId;
          List<Map<String, Object>> goodslist = UrlUtil.sendPostForList(url, param,"goods_search_response");
          
          
          if(goodslist!=null &&goodslist.size()>0){
        	  for(int j=0;j<goodslist.size();j++){
        		  Map<String, Object> gmap = goodslist.get(j);
        		  try{
        			  String categoryId=null;
        		  if(gmap.get("category_id")!=null){
        			  categoryId =gmap.get("category_id")+"";
        		  }
        		  
        		  String categoryName =null;
        		  if(gmap.get("category_name")!=null){
        			  categoryName =gmap.get("category_name")+"";
        		  }
        		  String couponDiscount =null;
        		  if(gmap.get("coupon_discount")!=null){
        			   couponDiscount =gmap.get("coupon_discount")+"";
        		  }
        		  String couponEndTime =null;
        		  if(gmap.get("coupon_end_time")!=null){
        			   couponEndTime =gmap.get("coupon_end_time")+"";
        		  }
        		  
        		  
        		  String couponMinOrderAmount =null;
        		  if(gmap.get("coupon_min_order_amount")!=null){
        			  couponMinOrderAmount =gmap.get("coupon_min_order_amount")+"";
        		  }
        		  String couponRemainQuantity =null;
        		  if(gmap.get("coupon_end_time")!=null){
        			  couponRemainQuantity =gmap.get("coupon_min_order_amount")+"";
        		  }
        		  String couponStartTime =null;
        		  if(gmap.get("coupon_start_time")!=null){
        			  couponStartTime =gmap.get("coupon_start_time")+"";
        		  }
        		  String couponTotalQuantity =null;
        		  if(gmap.get("coupon_total_quantity")!=null){
        			  couponTotalQuantity =gmap.get("coupon_total_quantity")+"";
        		  }
        		  String goodsEvalCount =null;
        		  if(gmap.get("goods_eval_count")!=null){
        			  goodsEvalCount =gmap.get("goods_eval_count")+"";
        		  }
        		  String goodsId =null;
        		  if(gmap.get("goods_id")!=null){
        			  goodsId =gmap.get("goods_id")+"";
        		  }
        		  String minGroupPrice =null;
        		  if(gmap.get("min_group_price")!=null){
        			  minGroupPrice =gmap.get("min_group_price")+"";
        		  }
        		  
        		  String minNormalPrice =null;
        		  if(gmap.get("min_normal_price")!=null){
        			  minNormalPrice =gmap.get("min_normal_price")+"";
        		  }
        		  
        		  String promotionRate =null;
        		  if(gmap.get("promotion_rate")!=null){
        			  promotionRate =gmap.get("promotion_rate")+"";
        		  }
        		  
        		  String soldQuantity =null;
        		  if(gmap.get("sold_quantity")!=null){
        			  soldQuantity =gmap.get("sold_quantity")+"";
        		  }
        		  
        		  
        		  String goodsImageUrl =null;
        		  if(gmap.get("goods_image_url")!=null){
        			  goodsImageUrl =gmap.get("goods_image_url")+"";
        		  }
        		  
        		  String goodsName =null;
        		  if(gmap.get("goods_name")!=null){
        			  goodsName =gmap.get("goods_name")+"";
        		  }
        		  
        		  
        		  String goodsThumbnailUrl =null;
        		  if(gmap.get("goods_thumbnail_url")!=null){
        			  goodsThumbnailUrl =gmap.get("goods_thumbnail_url")+"";
        		  }
        		  
        		  String mallName =null;
        		  if(gmap.get("mall_name")!=null){
        			  mallName =gmap.get("mall_name")+"";
        		  }
				  if(ogc.getOpenGoods()==2){
            		  OpenGoodsAll  goods = new OpenGoodsAll();
            		  goods.setCategoryId(categoryId);
            		  goods.setCategoryName(categoryName);
            		  goods.setCouponDiscount(couponDiscount+"");
            		  goods.setCouponEndTime(couponEndTime+"");
            		  goods.setCouponMinOrderAmount(couponMinOrderAmount+"");
            		  goods.setCouponRemainQuantity(couponRemainQuantity);
            		  goods.setCouponStartTime(couponStartTime+"");
            		  goods.setCouponTotalQuantity(couponTotalQuantity);
            		  goods.setGoodsEvalCount(goodsEvalCount+"");
            		  goods.setGoodsEvalScore(5+"");
            		  goods.setGoodsId(goodsId+"");
            		  goods.setGoodsImageUrl(goodsImageUrl);
            		  goods.setGoodsName(goodsName);
            		  goods.setGoodsThumbnailUrl(goodsThumbnailUrl);
            		  goods.setMallName(mallName);
            		  goods.setMinGroupPrice(minGroupPrice);
            		  goods.setMinNormalPrice(minNormalPrice);
            		  goods.setPromotionRate(promotionRate+"");
            		  goods.setSoldQuantity(soldQuantity);
            		  goods.setOptId(optId+"");
            		 // openGoodsDAO.save(goods);
            		  openGoodsList.add(goods);
            	  }else{
            		  OpenGoodsAll2  goods = new OpenGoodsAll2();
            		  goods.setCategoryId(categoryId);
            		  goods.setCategoryName(categoryName);
            		  goods.setCouponDiscount(couponDiscount+"");
            		  goods.setCouponEndTime(couponEndTime+"");
            		  goods.setCouponMinOrderAmount(couponMinOrderAmount+"");
            		  goods.setCouponRemainQuantity(couponRemainQuantity);
            		  goods.setCouponStartTime(couponStartTime+"");
            		  goods.setCouponTotalQuantity(couponTotalQuantity);
            		  goods.setGoodsEvalCount(goodsEvalCount+"");
            		  goods.setGoodsEvalScore(5+"");
            		  goods.setGoodsId(goodsId+"");
            		  goods.setGoodsImageUrl(goodsImageUrl);
            		  goods.setGoodsName(goodsName);
            		  goods.setGoodsThumbnailUrl(goodsThumbnailUrl);
            		  goods.setMallName(mallName);
            		  goods.setMinGroupPrice(minGroupPrice);
            		  goods.setMinNormalPrice(minNormalPrice);
            		  goods.setPromotionRate(promotionRate+"");
            		  goods.setSoldQuantity(soldQuantity);
            		  goods.setOptId(optId+"");
            		//  openGoods2DAO.save(goods);
            		  openGoods2List.add(goods);
            		  
            	  }
        	  }
        	  catch(Exception e){
        		  System.out.println("第"+i+"页面第"+j+"商品同步出错");
        		  e.printStackTrace();
        		  continue;
        	  }
          } 


         }
	    }
	   
	}

        
        
        if(ogc.getOpenGoods()==1){
        	openGoodsAll2DAO.saveList(openGoods2List);
        	ogc.setOpenGoods(2);
        }else{
        	openGoodsAllDAO.saveList(openGoodsList);
        	ogc.setOpenGoods(1);
        }
        openGoodsCurrentDAO.saveOrUpdate(ogc);
        Map<String ,Object> statusMap = new HashMap<String ,Object >();
        statusMap.put("表修改为", ogc.getOpenGoods());
        statusMap.put("开始时间",DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date(statr)));
        statusMap.put("结束时间",DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));
        return statusMap;
	}
	
	//dada
	@Override
	public Map<String, Object> getPDDGoods() {
        Map<String ,String> map = new HashMap<String ,String >();
        OpenGoodsCurrent ogc = openGoodsCurrentDAO.findById(1);   
        if(ogc.getOpenGoods()==1){
        	openGoods2DAO.delAll();
       
        }else{
        	openGoodsDAO.delAll();
        }
        
        List<OpenClassify> GoodsClassify = openClassifyDAO.queryAll();
        
        long statr = System.currentTimeMillis();
        long l = System.currentTimeMillis();
        Integer optId = 0;
        
        for(OpenClassify classify : GoodsClassify){
        
       optId = classify.getCategoryId();
		        for(int i=1;i<100;i++){
		        	
		        	  map.put("type", "pdd.ddk.goods.search");
		              map.put("data_type", StringUtil.data_type);
		              map.put("timestamp",l+"");
		              map.put("client_id", StringUtil.client_id_axp);
		              map.put("sort_type", "0");//综合排序
		              map.put("with_coupon","true");
		              map.put("page",i+"");
		              if(optId!=0){
		            	  map.put("opt_id", optId+"");
		              }
		              String rlist = "[{\"range_id\":2,\"range_from\":250,\"range_to\":900}]";
		              map.put("range_list", rlist);
		              
		              String sign = MD5Util.getSign(map,"3ef2184d38ba2d741679b67a2506780b94562394");
		              String url = "http://gw-api.pinduoduo.com/api/router";
		              String param = "type=pdd.ddk.goods.search&sort_type=0&page="+i+"&with_coupon=true&data_type=JSON&range_list="+rlist+"&timestamp="+l+"&client_id="+StringUtil.client_id_axp+"&sign="+sign
		            		  			;
		              if(optId!=0)param+="&opt_id="+optId;
		              List<Map<String, Object>> goodslist = UrlUtil.sendPostForList(url, param,"goods_search_response");
		             
		              if(goodslist!=null &&goodslist.size()>0){
		            	  for(int j=0;j<goodslist.size();j++){
		            		  Map<String, Object> gmap = goodslist.get(j);
		            		  try{

		            			  String categoryId=null;
		            		  if(gmap.get("category_id")!=null){
		            			  categoryId =gmap.get("category_id")+"";
		            		  }
		            		  
		            		  String categoryName =null;
		            		  if(gmap.get("category_name")!=null){
		            			  categoryName =gmap.get("category_name")+"";
		            		  }
		            		  String couponDiscount =null;
		            		  if(gmap.get("coupon_discount")!=null){
		            			   couponDiscount =gmap.get("coupon_discount")+"";
		            		  }
		            		  String couponEndTime =null;
		            		  if(gmap.get("coupon_end_time")!=null){
		            			   couponEndTime =gmap.get("coupon_end_time")+"";
		            		  }
		            		  
		            		  
		            		  String couponMinOrderAmount =null;
		            		  if(gmap.get("coupon_min_order_amount")!=null){
		            			  couponMinOrderAmount =gmap.get("coupon_min_order_amount")+"";
		            		  }
		            		  String couponRemainQuantity =null;
		            		  if(gmap.get("coupon_end_time")!=null){
		            			  couponRemainQuantity =gmap.get("coupon_min_order_amount")+"";
		            		  }
		            		  String couponStartTime =null;
		            		  if(gmap.get("coupon_start_time")!=null){
		            			  couponStartTime =gmap.get("coupon_start_time")+"";
		            		  }
		            		  String couponTotalQuantity =null;
		            		  if(gmap.get("coupon_total_quantity")!=null){
		            			  couponTotalQuantity =gmap.get("coupon_total_quantity")+"";
		            		  }
		            		  String goodsEvalCount =null;
		            		  if(gmap.get("goods_eval_count")!=null){
		            			  goodsEvalCount =gmap.get("goods_eval_count")+"";
		            		  }
		            		  String goodsId =null;
		            		  if(gmap.get("goods_id")!=null){
		            			  goodsId =gmap.get("goods_id")+"";
		            		  }
		            		  String minGroupPrice =null;
		            		  if(gmap.get("min_group_price")!=null){
		            			  minGroupPrice =gmap.get("min_group_price")+"";
		            		  }
		            		  
		            		  String minNormalPrice =null;
		            		  if(gmap.get("min_normal_price")!=null){
		            			  minNormalPrice =gmap.get("min_normal_price")+"";
		            		  }
		            		  
		            		  String promotionRate =null;
		            		  if(gmap.get("promotion_rate")!=null){
		            			  promotionRate =gmap.get("promotion_rate")+"";
		            		  }
		            		  
		            		  String soldQuantity =null;
		            		  if(gmap.get("sold_quantity")!=null){
		            			  soldQuantity =gmap.get("sold_quantity")+"";
		            		  }
		            		  
		            		  
		            		  String goodsImageUrl =null;
		            		  if(gmap.get("goods_image_url")!=null){
		            			  goodsImageUrl =gmap.get("goods_image_url")+"";
		            		  }
		            		  
		            		  String goodsName =null;
		            		  if(gmap.get("goods_name")!=null){
		            			  goodsName =gmap.get("goods_name")+"";
		            		  }
		            		  
		            		  
		            		  String goodsThumbnailUrl =null;
		            		  if(gmap.get("goods_thumbnail_url")!=null){
		            			  goodsThumbnailUrl =gmap.get("goods_thumbnail_url")+"";
		            		  }
		            		  
		            		  String mallName =null;
		            		  if(gmap.get("mall_name")!=null){
		            			  mallName =gmap.get("mall_name")+"";
		            		  }
		            		  
		            		  
		            	

		            				  if(ogc.getOpenGoods()==2){
		                        		  OpenGoods  goods = new OpenGoods();
		                        		  goods.setCategoryId(categoryId);
		                        		  goods.setCategoryName(categoryName);
		                        		  goods.setCouponDiscount(couponDiscount+"");
		                        		  goods.setCouponEndTime(couponEndTime+"");
		                        		  goods.setCouponMinOrderAmount(couponMinOrderAmount+"");
		                        		  goods.setCouponRemainQuantity(couponRemainQuantity);
		                        		  goods.setCouponStartTime(couponStartTime+"");
		                        		  goods.setCouponTotalQuantity(couponTotalQuantity);
		                        		  goods.setGoodsEvalCount(goodsEvalCount+"");
		                        		  goods.setGoodsEvalScore(5+"");
		                        		  goods.setGoodsId(goodsId+"");
		                        		  goods.setGoodsImageUrl(goodsImageUrl);
		                        		  goods.setGoodsName(goodsName);
		                        		  goods.setGoodsThumbnailUrl(goodsThumbnailUrl);
		                        		  goods.setMallName(mallName);
		                        		  goods.setMinGroupPrice(minGroupPrice);
		                        		  goods.setMinNormalPrice(minNormalPrice);
		                        		  goods.setPromotionRate(promotionRate+"");
		                        		  goods.setSoldQuantity(soldQuantity);
		                        		  goods.setOptId(optId+"");
		                        		  openGoodsDAO.save(goods);
		                        		 // openGoodsList.add(goods);
		                        	  }else{
		                        		  OpenGoods2  goods = new OpenGoods2();
		                        		  goods.setCategoryId(categoryId);
		                        		  goods.setCategoryName(categoryName);
		                        		  goods.setCouponDiscount(couponDiscount+"");
		                        		  goods.setCouponEndTime(couponEndTime+"");
		                        		  goods.setCouponMinOrderAmount(couponMinOrderAmount+"");
		                        		  goods.setCouponRemainQuantity(couponRemainQuantity);
		                        		  goods.setCouponStartTime(couponStartTime+"");
		                        		  goods.setCouponTotalQuantity(couponTotalQuantity);
		                        		  goods.setGoodsEvalCount(goodsEvalCount+"");
		                        		  goods.setGoodsEvalScore(5+"");
		                        		  goods.setGoodsId(goodsId+"");
		                        		  goods.setGoodsImageUrl(goodsImageUrl);
		                        		  goods.setGoodsName(goodsName);
		                        		  goods.setGoodsThumbnailUrl(goodsThumbnailUrl);
		                        		  goods.setMallName(mallName);
		                        		  goods.setMinGroupPrice(minGroupPrice);
		                        		  goods.setMinNormalPrice(minNormalPrice);
		                        		  goods.setPromotionRate(promotionRate+"");
		                        		  goods.setSoldQuantity(soldQuantity);
		                        		  goods.setOptId(optId+"");
		                        		  openGoods2DAO.save(goods);
		                        		  //openGoods2List.add(goods);
		                        		  
		                        	  }
		            	  }
		            	  catch(Exception e){
		            		  System.out.println("第"+i+"页面第"+j+"商品同步出错");
		            		  e.printStackTrace();
		            		  continue;
		            	  }
		              } 

		            	  try {
		  					Thread.sleep(1000);
		  				} catch (InterruptedException e) {
		  					// TODO Auto-generated catch block
		  					e.printStackTrace();
		  				}

		             }
		        }

		        
		        try {
  					Thread.sleep(4000);
  				} catch (InterruptedException e) {
  					// TODO Auto-generated catch block
  					e.printStackTrace();
  				}
	        }
        
		
        
        if(ogc.getOpenGoods()==1){
        	ogc.setOpenGoods(2);
        }else{
        	ogc.setOpenGoods(1);
        }
        openGoodsCurrentDAO.saveOrUpdate(ogc);
        long end =  System.currentTimeMillis();
        System.out.println("使用时间"+(end-statr));//使用时间45133,使用时间41459 十万条2198744
        Map<String ,Object> statusMap = new HashMap<String ,Object >();
        statusMap.put("表修改为", ogc.getOpenGoods());
        statusMap.put("时间", end-statr);
        return statusMap;
	}
	

	
	
	
	
	@Override
	public Map<String, Object> order(HttpServletRequest request) {
		Parameter parameter = ParameterUtil.getParameter(request);
		
		String openId = parameter.getClient_id();
	    String sign = parameter.getSign();
	    String pids = parameter.getData().getString("pddPids");//传多个pid上来请求多次请求  不不管单个和多个传都用这个参数
	    String pid = parameter.getData().getString("pid");//标识pid
	    String page = parameter.getData().getString("page")==null?"1":parameter.getData().getString("page");
	    String time_type = parameter.getData().getString("time_type")==null?"1":parameter.getData().getString("time_type");//0--创建时间，1--支付时间， 9--最后更新时间 （默认0）
	    Integer order_status = Integer.valueOf(parameter.getData().getString("order_status"));//1 待确认 2审核中 4 审核失败 5已结算 10 查询全部
	    String page_size = parameter.getData().getString("pageSize")==null?"10":parameter.getData().getString("pageSize");
	    String  isGzh = parameter.getData().getString("isGzh");//是否公众号 1 2 普通用户 公众号写另外一个方法中
	    
	    String lastSn = parameter.getData().getString("lastSn");
	    
	    if(parameter == null || openId == null || sign == null ){
	    	return JsonResponseUtil.getJson(-0x01, "必要参数不能为空");
	    }
	    String client_id = "";
	    String client_secret = "";
//	    if(StringUtil.openId.equals(openId)){
//	    	client_id = StringUtil.client_id_other;
//	    	client_secret = StringUtil.client_id_other;
//	    }else{
	    	client_id = StringUtil.client_id_axp;
	    	client_secret = StringUtil.client_secret_axp;
//	    }
	    
	    Map<String,Object> statusMap = new HashMap<String,Object>();
	    
	    OpenClient oc = openClientService.getOpenClientByClientId(openId);
	    Map<String,String> signMap = new HashMap<String,String>();
    	
    	signMap.put("client_id", openId);
    	if(oc != null){
    		if(!MD5Util.getSign(signMap, oc.getClientSecret()).equals(sign)){
    			return JsonResponseUtil.getJson(-0x01, "签名验证不通过！");
    		}
    	}else{
    		return JsonResponseUtil.getJson(-0x02, "client_id错误！请联系管理员");
    	}
    	

    	QueryModel query = new QueryModel();
    	//openApp 
    	query.clearQuery();
    	query.combPreEquals("clientId", openId);
    	OpenApp openApp = (OpenApp) dateBaseDAO.findOne(OpenApp.class, query);
    	double maid = Double.valueOf(openApp.getMaid());
    		
    	JSONArray pidJson = new JSONArray();
    	List<String> pidList = new ArrayList<String>();
    	String pidString = null;
    	if(isGzh.equals("1")){
	    	//这里处理公众号的处理逻辑
    		query.clearQuery();
        	query.combPreEquals("pddUpPid", openApp.getPddPid());
        	List<PidRelation> PidRelationList = dateBaseDAO.findLists(PidRelation.class, query);
        	pidList = new ArrayList<String>();
        	pidJson.add(openApp.getPddPid());
        	for(PidRelation p:PidRelationList){
        		pidJson.add(p.getPddPid());
        	}
        	for(int i=0;i<pidJson.size();i++){
    			if(!pidJson.contains(pid)){ //pid不在pids里面的  全部
    				 
    				pidList.add( "\'"+pidJson.get(i).toString()+"\'");
    				pidString = pidList.toString().replace("[","(").replace("]",")");
    				
    				
    			}
        	}
	    }
    	if(isGzh.equals("2")){
    	//传两个以上的Pid
    		if(pids == null ){
    			return JsonResponseUtil.getJson(-0x02, "查询pid不能为空");
    		}
		pidJson = JSONArray.fromObject(pids);
		
		for(int i=0;i<pidJson.size();i++){
			if(!pidJson.contains(pid)){ //pid不在pids里面的  全部
				pidList.add(pidJson.get(i).toString());
				 
				pidString = pidList.toString().replace("[","(").replace("]",")");
				
			}
			if(pidJson.get(i).equals(pid)){ //pid存在pids 里面的 之后这个pid-- 最后的
				pidList = pidJson.subList(i, pidJson.size());
				pidString =pidList.toString().replace("[","(").replace("]",")");;
				
				break;
			}
    	}
    	
    	}
    	
		Date end = DateUtil.getNow();
    	Date start = DateUtil.cutDay2Date(90, DateUtil.getNow());
    	String end_time = DateUtil.formatDate(end);//当前时间
    	String start_time =DateUtil.formatDate(start);
    	String time = String.valueOf(System.currentTimeMillis());
    	
    	
    	//已结算 在本地本地判断返回即可 这个可以合并 只访问一次数据库
    	List<List<OpenPddGoods>>  kejisuans = new ArrayList<List<OpenPddGoods>>();
    	Map<String,Object> data = new HashMap<String,Object>();
        	if(5==order_status){
        		int count = 0;
        		int totalPage = 0 ;
        		int num = 0;
        			query.clearQuery();
        			if(pidString != null)query.combCondition("pid in "+pidString);
        			query.combPreEquals("isWithdraw", true);//可提现的  分页展示和三个月时间---
//        			query.combCondition("orderReceiveTime>"+end.getTime());
//        			query.combCondition("orderReceiveTime<"+start.getTime());
        			count = dateBaseDAO.findCount(OpenPddGoods.class, query);
        			totalPage = (count%Integer.valueOf(page_size))>0?(count%Integer.valueOf(page_size)+1):count%Integer.valueOf(page_size);
        			int start1 = (Integer.valueOf(page)-1)*Integer.valueOf(page_size);
        			List<OpenPddGoods>  kejisuan = dateBaseDAO.findPageList(OpenPddGoods.class, query, start1, Integer.valueOf(page_size));
        			num = num + count ;
        		
        			if(kejisuan.size() <= 0){
        				statusMap.put("code", 0);
        				return statusMap;
        			}
        		statusMap.put("code", 1);
        		statusMap.put("num", num);
        		statusMap.put("data", kejisuan);
        		return statusMap;
        		
        		
        	}
        	
        
        	
        	//用户pid(推广位Id) 通过pdd接口找到所有的订单
        	List<List<Map<String,Object>>> goodsListOver = new ArrayList<List<Map<String,Object>>>();
        	List<Map<String,Object>> goodsList = null ;
        	List<Map<String,Object>> orderList = null;
        	boolean flag = false;//该pid最后一个订单的判断
        	Integer total_count = 0;
        
        	int goodsListNum = 0 ; 
        	for(int i = 0;i<pidList.size();i++){
        		Map<String,String> signMap1 = new HashMap<String,String>();
        		
        		signMap1.put("client_id", client_id);
        		signMap1.put("start_time", start_time);
        		signMap1.put("end_time", end_time);
        		//signMap1.put("page_size", page_size);
        		//if(page!=null)signMap1.put("page", page);
        		signMap1.put("p_id", pidList.get(i));
        		signMap1.put("type", "pdd.ddk.order.list.range.get");
        		signMap1.put("data_type", StringUtil.data_type);
        		signMap1.put("timestamp", time);
        		if(time_type != null)signMap1.put("time_type", time_type);
        		String sign1 = MD5Util.getSign(signMap1,client_secret);
        		
        		String url = "http://gw-api.pinduoduo.com/api/router";
        		String param = "type=pdd.ddk.order.list.range.get&data_type=JSON&timestamp="
        				+time+"&client_id="+client_id+"&start_time="+start_time+"&end_time="
        				+end_time+"&sign="+sign1;
        		if(pids!=null)param += "&p_id="+pidList.get(i);
        		if(time_type != null)param += "&time_type="+time_type;
        			
        			goodsList = UrlUtil.sendPostForListNew(url, param,"order_list_get_response");//查询多个pid sendPostForList
        			
        			if(goodsList.size()== 0 || goodsList == null){
        				continue;
        			}
        			
        			if(order_status == 10)total_count += (Integer)goodsList.get(0).get("total_count"); //当满足了pageSize的时候就退出了,
        			try {
        				orderList = UrlUtil.jsonObjectToList(goodsList.get(0).get("order_list").toString());
        				
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		 //查到所有的订单后, 分类别 处理
        		if(order_status != 10){
        			List<Map<String, Object>> orders = new ArrayList<Map<String, Object>>();
        			for(int g =0;g<orderList.size();g++){
        				if(order_status.equals(orderList.get(g).get("order_status"))){
        					orders.add(orderList.get(g));
        				}
        			}
        			orderList = orders;
        			total_count += orderList.size();
        		}
        		 if(pidJson.size()>=1){
        			//第二次传上来有pid 和lastSn 做对比的
        			 if(pidList.get(i).equals(pid)){
        				 List<String> orders = new ArrayList<String>();
        				 for(int j=0;j<orderList.size();j++){
        					 orders.add(orderList.get(j).get("order_sn").toString());
        					 if(orderList.get(j).get("order_sn").equals(lastSn)){
        						 if(j==orderList.size()-1){//这个pid最后一条记录
        							 Map<String,Object> goodsMap = orderList.get(orderList.size()-1);
        							 orderList.clear();
        							 orderList.add(goodsMap);
        							 
        						 }else{
        							 if(j + 1 + Integer.valueOf(page_size) > orderList.size()){ //这个pid后面数量小于页大小了,就要去到下一个pid取
        								 orderList = orderList.subList(j+1, orderList.size());//之前最后一个pid中如果只有取了一节再取最后一节
        								 
        							 }else {
        								 //后面还有页大小 的数量, 直接取了返回
        								 orderList = orderList.subList(j+1, j+1+Integer.valueOf(page_size));
        								 //goodsListOver.add(orderList);
        								 break;
        							 }
        							 
        						 }
        						 
        					 }
        					 
        				 }//for --
        			 }
        			//判断数量,
        			 
        			 //----------------
        				 if(goodsListNum + orderList.size() < Integer.valueOf(page_size)){
        					 
        					 if(orderList.size() < Integer.valueOf(page_size)){
        						 goodsListNum += orderList.size();//
        						 goodsListOver.add(orderList);
        					 }
        					 
        				 }else{
        					 if(goodsListNum < Integer.valueOf(page_size)){
        						 orderList = orderList.subList(0, Integer.valueOf(page_size) - goodsListNum);
        						 goodsListNum += orderList.size();//
        						 goodsListOver.add(orderList);
        						 
        					 }else{
        						 continue;
        					 }
        					
        				 }
        				 //-------------------------
        		 }
        	}// pidList --for
        	
        	
        	
        	
        	List<Map<String,Object>> orderlist = new ArrayList<Map<String,Object>>();
        
	        	for(int i=0;i<goodsListOver.size();i++){
	        		for(int j = 0;j<goodsListOver.get(i).size();j++){
	        			orderlist.add(goodsListOver.get(i).get(j));
	        		Integer promotion_rate = (int) (Integer.valueOf(goodsListOver.get(i).get(j).get("promotion_rate").toString()) * maid/100);
	        		Integer promotion_amount = 	(Integer.valueOf(goodsListOver.get(i).get(j).get("order_amount").toString()) * promotion_rate)/1000;
	        		goodsListOver.get(i).get(j).put("promotion_rate", promotion_rate);	
	        		goodsListOver.get(i).get(j).put("promotion_amount", promotion_amount);	
	        		}
	        	}
	        	if(total_count <= 0){
	        		return JsonResponseUtil.getJson(-0x02, "没有查询到");
	        	}
	        	if(pidJson.size()>=1){
	        		System.out.println(Math.ceil(total_count.doubleValue()/Integer.valueOf(page_size)));
	        		try {
	        			if((int)Math.ceil(total_count.doubleValue()/Integer.valueOf(page_size)) - Integer.valueOf(page) <= 0  ){
	        				statusMap.put("lastSn", null);
							statusMap.put("lastPid", null);
	        			}else{
	        				pid =  orderlist.get(orderlist.size()-1).get("p_id").toString()==null?null:orderlist.get(orderlist.size()-1).get("p_id").toString();
	        				lastSn =  orderlist.get(orderlist.size()-1).get("order_sn").toString()==null?null:orderlist.get(orderlist.size()-1).get("order_sn").toString();
	        				statusMap.put("lastSn", lastSn);
	        				statusMap.put("lastPid", pid);
	        			}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
						return JsonResponseUtil.getJson(-0x02, "没有查询到");
					}
        	}
        	statusMap.put("result",  orderlist);
        	return statusMap;
}
	
	
	@Override
	public Map<String, Object> orderNew(HttpServletRequest request) {
		Parameter parameter = ParameterUtil.getParameter(request);
		
		String openId = parameter.getClient_id();
	    String sign = parameter.getSign();
	    String pids = parameter.getData().getString("pddPids");//传多个pid上来请求多次请求  不不管单个和多个传都用这个参数
	    String pid = parameter.getData().getString("pid");//标识pid
	    String page = parameter.getData().getString("page")==null?"1":parameter.getData().getString("page");
	    String time_type = parameter.getData().getString("time_type")==null?"1":parameter.getData().getString("time_type");//0--创建时间，1--支付时间， 9--最后更新时间 （默认0）
	    Integer order_status = Integer.valueOf(parameter.getData().getString("order_status"));//1 待确认 2审核中 4 审核失败 5已结算 10 查询全部
	    String page_size = parameter.getData().getString("pageSize")==null?"10":parameter.getData().getString("pageSize");
	    String  isGzh = parameter.getData().getString("isGzh");//是否公众号 1 2 普通用户 公众号写另外一个方法中
	    
	    String lastSn = parameter.getData().getString("lastSn");
	    
	    if(parameter == null || openId == null || sign == null ){
	    	return JsonResponseUtil.getJson(-0x01, "必要参数不能为空");
	    }
	    
	    Map<String,Object> statusMap = new HashMap<String,Object>();
	    
	    OpenClient oc = openClientService.getOpenClientByClientId(openId);
	    Map<String,String> signMap = new HashMap<String,String>();
    	
    	signMap.put("client_id", openId);
    	if(oc != null){
    		if(!MD5Util.getSign(signMap, oc.getClientSecret()).equals(sign)){
    			return JsonResponseUtil.getJson(-0x01, "签名验证不通过！");
    		}
    	}else{
    		return JsonResponseUtil.getJson(-0x02, "client_id错误！请联系管理员");
    	}
    	

    	QueryModel query = new QueryModel();
    	//openApp 
    	query.clearQuery();
    	query.combPreEquals("clientId", openId);
    	OpenApp openApp = (OpenApp) dateBaseDAO.findOne(OpenApp.class, query);
    	double maid = Double.valueOf(openApp.getMaid());
		
    	JSONArray pidJson = new JSONArray();
    	List<String> pidList = new ArrayList<String>();
    	String pidString = null;
    	if(isGzh.equals("1")){
	    	//这里处理公众号的处理逻辑
    		query.clearQuery();
        	query.combPreEquals("pddUpPid", openApp.getPddPid());
        	List<PidRelation> PidRelationList = dateBaseDAO.findLists(PidRelation.class, query);
        	pidList = new ArrayList<String>();
        	pidJson.add(openApp.getPddPid());
        	for(PidRelation p:PidRelationList){
        		pidJson.add(p.getPddPid());
        	}
        	for(int i=0;i<pidJson.size();i++){
    			if(!pidJson.contains(pid)){ //pid不在pids里面的  全部
    				pidList.add( "\'"+pidJson.get(i).toString()+"\'");
    				pidString = pidList.toString().replace("[","(").replace("]",")");
    			}
        	}
	    }
    	if(isGzh.equals("2")){
    	//传两个以上的Pid
    		if(pids == null ){
    			return JsonResponseUtil.getJson(-0x02, "查询pid不能为空");
    		}
		pidJson = JSONArray.fromObject(pids);
		
		for(int i=0;i<pidJson.size();i++){
			if(!pidJson.contains(pid)){ //pid不在pids里面的  全部
				pidList.add(pidJson.get(i).toString());
				pidString = pidList.toString().replace("[","(").replace("]",")");
			}
			if(pidJson.get(i).equals(pid)){ //pid存在pids 里面的 之后这个pid-- 最后的
				pidList = pidJson.subList(i, pidJson.size());
				pidString =pidList.toString().replace("[","(").replace("]",")");;
				
				break;
			}
    	}
    	
    	}
    	
    	
    	int count = 0;
    	List<OpenPddIncrementGoods2> orderlist = new ArrayList<OpenPddIncrementGoods2>();
    	
    	Map<String,Object> map = new HashMap<String ,Object>();
    	
        OpenGoodsCurrent ogc = openGoodsCurrentDAO.findById(1);  
        query.clearQuery();
        query.combCondition("pid in"+pidString);
        
       
        
	       if(order_status == 10){
	    	   query.combCondition(" orderStatus in (1,2,3,4)");
	       }else if (order_status==5){
	    	   query.combPreEquals("orderStatus", 5,"order_status");
	    	   Calendar cale = null;
	           cale = Calendar.getInstance();
	    	   
	           int month = cale.get(Calendar.MONTH) + 1;
	           int day = cale.get(Calendar.DATE);
	           
	           if(day>20){//每月后  查询条件为 本月16号前订单状态未已结算（拼多多状态为5），
	        	   
	           }
	           
	           
	    	   
	    	   
	       }else{
	    	   query.combPreEquals("orderStatus", order_status,"order_status");
	       }
	        count = dateBaseDAO.findCount(OpenPddIncrementGoods2.class, query);
		 	int start1 = (Integer.valueOf(page)-1)*Integer.valueOf(page_size);
	        List<OpenPddIncrementGoods2> openGoods2 = dateBaseDAO.findPageList(OpenPddIncrementGoods2.class, query, start1, Integer.valueOf(page_size));
	        if(openGoods2!=null && openGoods2.size()>0){
	        	
	        	for(OpenPddIncrementGoods2 opig2 :openGoods2){
	        		
	        		Integer rate =  (int) (Integer.parseInt(opig2.getPromotionRate())*maid/100);
	        		opig2.setPromotionRate(rate+"");
	        		Double account = opig2.getPromotionAmount()*maid/1000;
	        		opig2.setPromotionAmount(account);
	        		orderlist.add(opig2);
	        	}
	        	
	        	
	        	
	        }
        
    	
        	statusMap.put("result",  orderlist);
        	return statusMap;
}
	
    	
		
	
	@Override
	public Map<String, Object> orderCount(HttpServletRequest request) {
		
		Parameter parameter = ParameterUtil.getParameter(request);
    	String openId = parameter.getClient_id();
    	String sign = parameter.getSign();
    	//String pid = parameter.getData().getString("pid");
    	String time_type = parameter.getData().getString("time_type")==null?"1":parameter.getData().getString("time_type");//0--创建时间，1--支付时间， 9--最后更新时间 （默认1）
		if(openId == null ||  sign == null ){
			return JsonResponseUtil.getJson(-0x01, "必要参数不能为空");
		}
		
		 Map<String,Object> statusMap = new HashMap<String,Object>();
		   
		 	OpenClient oc = openClientService.getOpenClientByClientId(openId);
		    Map<String,String> signMap = new HashMap<String,String>();
	    	signMap.put("client_id", openId);
	    	
	    	
	    	
	    	if(oc != null){
	    		if(!MD5Util.getSign(signMap, oc.getClientSecret()).equals(sign)){
	    			return JsonResponseUtil.getJson(-0x01, "签名验证不通过！");
	    		}
	    	}else{
	    		return JsonResponseUtil.getJson(-0x02, "client_id错误！请联系管理员");
	    	}
	    	
	    	//判断该pid下的所有子pid
	    	QueryModel query = new QueryModel();
	    	query.clearQuery();
	    	query.combPreEquals("clientId", openId,"client_id");
	    	List<OpenApp> openApps = dateBaseDAO.findLists(OpenApp.class, query);
	    	OpenApp openApp = openApps.get(0);
	    	
	    	query.clearQuery();
	    	query.combPreEquals("pddUpPid", openApp.getPddPid());
	    	List<PidRelation> pidList = dateBaseDAO.findLists(PidRelation.class, query);
	    	List<String> pids = new ArrayList<String>();
	    	for(PidRelation p:pidList){
	    		pids.add(p.getPddPid());
	    	}
	    	int goodsNum = 0;
	    	double promotion_amount = 0.0;
	    	for(String p:pids){
		    	query.clearQuery();
		    	query.combPreEquals("isWithdraw", true);
		    	query.combPreEquals("pid", p,"p_id");
		    	List<OpenPddGoods> goodsList = dateBaseDAO.findLists(OpenPddGoods.class, query);
		    	for(OpenPddGoods g:goodsList){
		    		promotion_amount += g.getPromotionAmount();
		    	}
		    	goodsNum += goodsList.size();
	    	
	    	
	}
	    	//更新openApp中的money 字段
	    	openApp.setMoney((promotion_amount/1000)+openApp.getMoney());
	    	openAppDAO.saveOrUpdate(openApp);
	    	//业务处理查询到订单条数 判断是否有订单,然后只要返回条数就好了
	    	//封装的
	    	Map<String,Object> data = new HashMap<String,Object>();
	    	data.put("totalNum", goodsNum);
	    	data.put("money", promotion_amount/1000);
	    	statusMap.put("code", 1);
	    	statusMap.put("result", data);
			return statusMap;
	    	
}
	@Override
	public Map<String, Object> findByIncome(String extUserId, String openId,
			Integer startTime, Integer endTime, String sign) {
		if(openId == null || sign == null){
			return JsonResponseUtil.getJson(-0x01, "必要参数不能为空");
		}
		if(startTime == null){
			Date date  = DateUtil.getMonthBegin(String.valueOf(Calendar.getInstance().get(Calendar.MONTH )+1));
			startTime = (int) date.getTime();
		}
		if(endTime == null ){
			 endTime = (int) System.currentTimeMillis();
		}
		
		Map<String,Object> statusMap = new HashMap<String,Object>();
		
		
		OpenClient oc = openClientService.getOpenClientByClientId(openId);
	    Map<String,String> signMap = new HashMap<String,String>();
    	signMap.put("openId", openId);
    	if(extUserId != null)signMap.put("extUserId", extUserId);
    	if(startTime != null)signMap.put("startTime", startTime.toString());
    	if(endTime != null)signMap.put("endTime", endTime.toString());
    	
    	
    	if(oc != null){
    		if(!MD5Util.getSign(signMap, oc.getClientSecret()).equals(sign)){
    			return JsonResponseUtil.getJson(-0x01, "签名验证不通过！");
    		}
    	}
		//用户查询业绩  收入数据
    	
    	
		
    	statusMap.put("code", 1);
    	statusMap.put("result", "income");//添加的应该是json对象
		return statusMap;
	}
	
	
	@Override
	public Map<String, Object> findByProduct(HttpServletRequest request) {
		Parameter parameter = ParameterUtil.getParameter(request);
		String client_id = parameter.getClient_id();
	    String sign = parameter.getSign();
	    String search = parameter.getData().getString("search")==null?null:parameter.getData().getString("search");


	    String optId = parameter.getData().getString("categoryId")==null?"0":parameter.getData().getString("categoryId");


	    String limit = parameter.getData().getString("limit")==null?"20":parameter.getData().getString("limit");

	    String page = parameter.getData().getString("page")==null?"1":parameter.getData().getString("page");
	    
	    if(parameter == null || client_id == null || sign == null){
	    	return JsonResponseUtil.getJson(-0x01, "必要参数不能为空");
	    }
	    
	    int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
	    
	    if(start > 10000){
	    	return JsonResponseUtil.getJson(-0x01, "当前最多只能查询前10000条商品");
	    }
	    
	    OpenClient oc = openClientService.getOpenClientByClientId(client_id);
	    Map<String,String> signMap = new HashMap<String,String>();
    	signMap.put("client_id", client_id);
    	signMap.put("limit", limit);
    	signMap.put("page", page);
    	
    	
    	if(oc != null){
    		if(!MD5Util.getSign(signMap, oc.getClientSecret()).equals(sign)){
    			return JsonResponseUtil.getJson(-0x01, "签名验证不通过！");
    		}
    	}else if(oc==null ){
    		return JsonResponseUtil.getJson(-0x02, "client_id错误！请联系管理员");
    	}
    	QueryModel query = new QueryModel();
    	query.clearQuery();
    	query.combPreEquals("clientId", client_id,"client_id");
    	OpenApp openApp = (OpenApp) dateBaseDAO.findOne(OpenApp.class, query);
    	Integer maid = Integer.valueOf(openApp.getMaid());
    	
    	
    	OpenGoodsCurrent ogc = openGoodsCurrentDAO.findById(1);
    	
    	
    	//查询商品列表
    	query.clearQuery();
    	
    	if(search != null){
    		query.combPreLike("goodsName", search);//搜索商品名称
    		
    	}
    	
    	query.combEquals("optId", Integer.parseInt(optId));
    	
    	query.combCondition("promotionRate >99");
    	query.setOrder("id ASC");//排序字段

    	

		Map<String,Object> statusMap = new HashMap<String,Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		if(ogc.getOpenGoods() == 1){
			
			
			List<OpenGoods> openGoodsList = dateBaseDAO.findPageList(OpenGoods.class, query, start, Integer.valueOf(limit));
			//封装的
			for(OpenGoods openGood :openGoodsList){
				
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("goods_eval_count", openGood.getGoodsEvalCount()+"");
					map.put("goods_eval_score", openGood.getGoodsEvalScore()+"");
					map.put("promotion_rate", Integer.valueOf(openGood.getPromotionRate()) *maid/100);
					map.put("coupon_end_time", openGood.getCouponEndTime());
					map.put("coupon_start_time", openGood.getCouponStartTime());
					map.put("coupon_remain_quantity", openGood.getCouponRemainQuantity());
					map.put("coupon_total_quantity", openGood.getCouponTotalQuantity());
					map.put("coupon_discount",openGood.getCouponDiscount());
					map.put("coupon_min_order_amount",openGood.getCouponMinOrderAmount() );
					map.put("category_name", openGood.getCategoryName());
					map.put("category_id",openGood.getOptId() );
					map.put("mall_name",openGood.getMallName() );
					map.put("min_normal_price", openGood.getMinNormalPrice());
					map.put("min_group_price", openGood.getMinGroupPrice());
					map.put("sold_quantity",openGood.getSoldQuantity() );
					map.put("goods_image_url", openGood.getGoodsImageUrl());
					map.put("goods_thumbnail_url", openGood.getGoodsThumbnailUrl());
					map.put("goods_name",openGood.getGoodsName() );
					map.put("goods_id",openGood.getGoodsId() );
					
					//map.put("--------id", openGood.getCategoryId());
					list.add(map);
				
			}
			
			dataMap.put("totalNum", openGoodsList.size());
		}else{
			List<OpenGoods2> openGoodsList = dateBaseDAO.findPageList(OpenGoods2.class, query, start, Integer.valueOf(limit));
			//封装的
		
			for(OpenGoods2 openGood :openGoodsList){
				
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("goods_eval_count", openGood.getGoodsEvalCount());
					map.put("goods_eval_score", openGood.getGoodsEvalScore());
					map.put("promotion_rate", Integer.valueOf(openGood.getPromotionRate()) *maid/100);
					map.put("coupon_end_time", openGood.getCouponEndTime());
					map.put("coupon_start_time", openGood.getCouponStartTime());
					map.put("coupon_remain_quantity", openGood.getCouponRemainQuantity());
					map.put("coupon_total_quantity", openGood.getCouponTotalQuantity());
					map.put("coupon_discount",openGood.getCouponDiscount());
					map.put("coupon_min_order_amount",openGood.getCouponMinOrderAmount() );
					map.put("category_name", openGood.getCategoryName());
					map.put("category_id",openGood.getOptId() );
					map.put("mall_name",openGood.getMallName() );
					map.put("min_normal_price", openGood.getMinNormalPrice());
					map.put("min_group_price", openGood.getMinGroupPrice());
					map.put("sold_quantity",openGood.getSoldQuantity() );
					map.put("goods_image_url", openGood.getGoodsImageUrl());
					map.put("goods_thumbnail_url", openGood.getGoodsThumbnailUrl());
					map.put("goods_name",openGood.getGoodsName() );
					map.put("goods_id",openGood.getGoodsId() );
					
					//map.put("--------id", openGood.getp);
					list.add(map);
				
			}
				dataMap.put("totalNum", openGoodsList.size());
		}
    			
    			
    			dataMap.put("products", list);
    			statusMap.put("code", 1);
    	    	statusMap.put("result", dataMap);
    		    
    		    
    		    
    			return statusMap;
	}
	
	
	@Override
	public Map<String, Object> findByProductByPdd(HttpServletRequest request) {
		Parameter parameter = ParameterUtil.getParameter(request);
		String clientId = parameter.getClient_id();
	    String sign = parameter.getSign();
	    String search = parameter.getData().getString("search")==null?null:parameter.getData().getString("search");
	    String optId = parameter.getData().getString("categoryId")==null?"0":parameter.getData().getString("categoryId");
	    String limit = parameter.getData().getString("limit")==null?"20":parameter.getData().getString("limit");
	    String page = parameter.getData().getString("page")==null?"1":parameter.getData().getString("page");
	    
	    if(parameter == null || clientId == null || sign == null){
	    	return JsonResponseUtil.getJson(-0x01, "必要参数不能为空");
	    }
	    
	    int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
	    
	    if(start > 10000){
	    	return JsonResponseUtil.getJson(-0x01, "当前最多只能查询前10000条商品");
	    }
	    
	    Map<String ,String> map = new HashMap<String ,String >();
	    long statr = System.currentTimeMillis();
        long l = System.currentTimeMillis();
        
    	
  	  	map.put("type", "pdd.ddk.goods.search");
        map.put("data_type", StringUtil.data_type);
        map.put("timestamp",l+"");
        map.put("client_id", StringUtil.client_id_axp);
        map.put("sort_type", "0");//综合排序
        map.put("with_coupon","true");
        map.put("page",Integer.parseInt(page)+"");
        if(!"0".equals(optId)){
      	  map.put("opt_id", optId+"");
        }
        String rlist = "[{\"range_id\":2,\"range_from\":250,\"range_to\":500}]";
        map.put("range_list", rlist);
        
        String sign2 = MD5Util.getSign(map,"3ef2184d38ba2d741679b67a2506780b94562394");
        String url = "http://gw-api.pinduoduo.com/api/router";
        String param = "type=pdd.ddk.goods.search&sort_type=0&page="+Integer.parseInt(page)+"&with_coupon=true&data_type=JSON&range_list="+rlist+"&timestamp="+l+"&client_id=cbde395c33244e979908d4d305d5ea8b&sign="+sign2
      		  			;
        if(!"0".equals(optId))param+="&opt_id="+optId;
        List<Map<String, Object>> goodslist = UrlUtil.sendPostForList(url, param,"goods_search_response");
        Map<String,Object> statusMap = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
    			
		dataMap.put("products", goodslist);
		statusMap.put("code", 1);
    	statusMap.put("result", dataMap);
	    
	    
	    
		return statusMap;
	}
	
	@Override
	public Map<String, Object> findByPoster(HttpServletRequest request) {
		Parameter parameter = ParameterUtil.getParameter(request);
		
		String openId = parameter.getClient_id();
	    String sign = parameter.getSign();
	    String extUserId = parameter.getData().getString("extUserId");
		String itemId = parameter.getData().getString("itemId");
		String source = parameter.getData().getString("source");
		String pid = parameter.getData().getString("pid");
		
		
		 if(parameter == null || openId == null || sign == null || itemId == null){
		    	return JsonResponseUtil.getJson(-0x01, "必要参数不能为空");
		    }
		    OpenClient oc = openClientService.getOpenClientByClientId(openId);
		    Map<String,String> signMap = new HashMap<String,String>();
	    	signMap.put("openId", openId);
	    	signMap.put("extUserId", extUserId);
	    	signMap.put("itemId", itemId);
	    	signMap.put("source", source);
	    	signMap.put("pid", pid);
	    	
	    	if(oc != null){
	    		if(!MD5Util.getSign(signMap, oc.getClientSecret()).equals(sign)){
	    			return JsonResponseUtil.getJson(-0x01, "签名验证不通过！");
	    		}
	    	}else{
	    		return JsonResponseUtil.getJson(-0x02, "client_id错误！请联系管理员");
	    	}
	    	
	    	//商品详情和生成海报
	    	
	    	
	    	
	    	
	    	//封装结果
	    	Map<String,Object> statusMap = new HashMap<String,Object>();
	    
	    	Map<String,Object> map = new HashMap<String,Object>();
	    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	    	for(int i=0;i<3;i++){
	    		map.put("item_id","商品 ID");
	    		map.put("org_price","商品原价");
	    		map.put("introduce","商品介绍");
	    		map.put("title","标题");
	    		map.put("price","券后价");
	    		map.put("coupon_price","优惠券加个");
	    		map.put("pic_url","商品主图连接");
	    		map.put("goods_gallery_urls","[轮播图 ]");
	    		map.put("commission","佣金比例");
	    		map.put("mall_name","店铺名");
	    		map.put("sales_num","销量");
	    		map.put("coupon_totalNum","优惠券总数");
	    		map.put("coupon_surplus","优惠券剩余量");
	    		map.put("coupon_startFee","使用门槛");
	    		map.put("coupon_start_time","优惠券开始时间");
	    		map.put("coupon_end_time","结束时间");
	    		map.put("coupon_url","优惠券连接");//和qrcode_url 一样
	    		map.put("qrcode_url","用于生成推广二维码连接");
	    		map.put("goods_eval_count","评论数");
	    		map.put("goods_eval_score","评分");
	    		map.put("comment_url","评论连接");
	    		map.put("detail_url","详情连接");
	    				
	    		
	    				
	    	}
	    			
	    			
	    			
	    			statusMap.put("code", 1);
	    	    	statusMap.put("result", map);
	    		    
	    		    
	    		    
	    			return statusMap;	    	
	    	
	    	
	}
	@Override
	public Map<String, Object> ModificationGoodsIsWithdraw(
			HttpServletRequest request) {
		Parameter parameter = ParameterUtil.getParameter(request);
		String orderSn = parameter.getData().getString("orderSn");
		String openId = parameter.getClient_id();
		Map<String,Object> statusMap = new HashMap<String,Object>();
		try {
			QueryModel query = new QueryModel();
			query.clearQuery();
			query.combPreEquals("orderSn", orderSn,"order_sn");
			query.combPreEquals("isWithdraw", true);
			OpenPddGoods openPddGood = (OpenPddGoods) dateBaseDAO.findOne(OpenPddGoods.class, query);
			if(openPddGood != null){
				openPddGood.setIsWithdraw(false);
				openPddGoodsDAO.saveOrUpdate(openPddGood);
				//修改openApp的money
				
				query.clearQuery();
				query.combPreEquals("clientId", openId,"client_id");
				OpenApp openApp = (OpenApp) dateBaseDAO.findOne(OpenApp.class, query);
				
				double money = Math.ceil(openPddGood.getPromotionAmount()*
										(Integer.valueOf(openPddGood.getPromotionRate())*
										Integer.valueOf(openApp.getMaid())/100)/1000);
				openApp.setMoney(money);
				openApp.sethMoney(openApp.gethMoney()+money);
				openAppDAO.saveOrUpdate(openApp);
				return JsonResponseUtil.getJson(0x01, "修改成功");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonResponseUtil.getJson(-0x02, "修改失败");
		}
		return JsonResponseUtil.getJson(-0x02, "修改失败");
	}
	
	@Override
	public Map<String, Object> locaOrder(Integer pageSize,Integer page,String endTime) {
		
		if(pageSize == null)pageSize = 50; else pageSize = pageSize;
		if(page == null)page = 1; else page = page;
		
		List<Map<String,Object>> orderList = new ArrayList<Map<String,Object>>();
		Integer total_count = null;
		OpenGoodsCurrent ogc = openGoodsCurrentDAO.findById(1);
		String time = String.valueOf(System.currentTimeMillis());
			String startTime =ogc.getOrderModifyAt() ;
			Map<String,String> signMap = new HashMap<String,String>();
			signMap.put("type", "pdd.ddk.order.list.increment.get");
			signMap.put("start_update_time",  startTime);
			signMap.put("end_update_time",endTime);
			signMap.put("page", String.valueOf(page));
			signMap.put("page_size",String.valueOf(pageSize));
			signMap.put("data_type", StringUtil.data_type);
			signMap.put("timestamp", time);
			signMap.put("client_id", StringUtil.client_id_axp);
			String sign1 = MD5Util.getSign(signMap, StringUtil.client_secret_axp);

	    	String url = "http://gw-api.pinduoduo.com/api/router";
	    	String param = "type=pdd.ddk.order.list.increment.get&data_type="+StringUtil.data_type+"&start_update_time="+startTime+"&end_update_time="+endTime
	    									+"&client_id="+StringUtil.client_id_axp+"&sign="+sign1+"&timestamp="+time+"&page="+String.valueOf(page)+"&page_size="+String.valueOf(pageSize);
	    	
	    	
	    	
	    	List<Map<String,Object>> goodsList = UrlUtil.sendPostForListNew(url, param, "order_list_get_response");
	    	total_count = (Integer)goodsList.get(0).get("total_count");
	    	
	    	try {
				orderList = UrlUtil.jsonObjectToList(goodsList.get(0).get("order_list").toString());
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH"); 
	    		for(int i=0;i<orderList.size();i++){
	    			
	    			QueryModel query = new QueryModel();
	    			query.combPreEquals("orderSn", orderList.get(i).get("order_sn").toString(),"order_sn");
	    			List<OpenPddIncrementGoods2>  opicgs =  dateBaseDAO.findLists(OpenPddIncrementGoods2.class, query);
	    			OpenPddIncrementGoods2 opicg=new OpenPddIncrementGoods2();
	    			if(opicgs!=null && opicgs.size()>0){
	    				opicg = opicgs.get(0);
	    			}
	    			String os = orderList.get(i).get("order_status")+"";
	    			if("-1".equals(os) || "8".equals(os)){
	    				continue;
	    			}
	    			
	    			
	    			opicg.setCustomParameters(orderList.get(i).get("custom_parameters").toString());
	    			opicg.setGoodsId(orderList.get(i).get("goods_id").toString());//把所有的数据set进去
    				opicg.setOrderSn(orderList.get(i).get("order_sn").toString());
    				opicg.setGoodsName(orderList.get(i).get("goods_name").toString());
    				opicg.setGoodsPrice(orderList.get(i).get("goods_price").toString());
    				opicg.setGoodsQuantity((Integer)orderList.get(i).get("goods_quantity"));
    				opicg.setGoodsThumbnailUrl(orderList.get(i).get("goods_thumbnail_url").toString());
    				opicg.setOrderAmount(orderList.get(i).get("order_amount").toString());
	    			opicg.setOrderCreateTime(DateUtil.StringToTimestamp(orderList.get(i).get("order_create_time").toString()));
    				opicg.setOrderGroupSuccessTime(orderList.get(i).get("order_group_success_time").toString());
    				opicg.setOrderModifyAt(orderList.get(i).get("order_modify_at").toString());
    				opicg.setOrderPayTime(orderList.get(i).get("order_pay_time").toString());
    				opicg.setOrderReceiveTime(orderList.get(i).get("order_receive_time").toString());
    				opicg.setOrderStatus(Integer.parseInt(os));
    				opicg.setOrderStatusDesc(orderList.get(i).get("order_status_desc").toString());
    				opicg.setOrderVerifyTime(orderList.get(i).get("order_verify_time").toString());
    				opicg.setPid(orderList.get(i).get("p_id").toString());
    				Integer pa = (Integer) orderList.get(i).get("promotion_amount");
    				opicg.setPromotionAmount(pa*0.95);
    				opicg.setPromotionRate(orderList.get(i).get("promotion_rate").toString());
    				opicg.setType(orderList.get(i).get("type").toString());
    				opicg.setUpPid("abc"+pa+"xyz");
    				opicg.setIsWithdraw("0");
    				openPddIncrementGoods2DAO.saveOrUpdate(opicg);
	    		}
	    	//判断总数量循环保存
	    	Integer totalPage = (int) Math.ceil(total_count.doubleValue() / Integer.valueOf(pageSize));
	    	if(totalPage - Integer.valueOf(page) > 0){
	    		this.locaOrder(pageSize,page+1,endTime);
	    	}else{
	    		
	    		ogc.setOrderModifyAt(endTime);
	    		openGoodsCurrentDAO.update(ogc);
	    	}
		
    	return null;
	}
	
	
	
	/**
	 * 定时保存超过三个月以上的订单
	 */
	@Override
	public void historyGoods() {
		
		OpenGoodsCurrent ogc = openGoodsCurrentDAO.findById(1);
		Long l = DateUtil.addDay2Date(-90, DateUtil.getNow()).getTime();//当前时间90天前的long
		
			QueryModel query = new QueryModel();
			query.clearQuery();
			query.combCondition("orderModifyAt <"+l);
			List<OpenPddIncrementGoods2> openGoods2 = dateBaseDAO.findLists(OpenPddIncrementGoods2.class, query);
			System.out.println(DateUtil.getNow()+"删除了"+openGoods2.size()+"条订单数据");
			//保存到历史表中
			for(OpenPddIncrementGoods2 t:openGoods2){
				OpenPddIncrementGoodsHistory opicg = new OpenPddIncrementGoodsHistory();
				opicg.setGoodsId(t.getGoodsId());//把所有的数据set进去
				opicg.setOrderSn(t.getOrderSn());
				opicg.setCustomParameters(t.getCustomParameters());
				opicg.setGoodsName(t.getGoodsName());
				opicg.setGoodsPrice(t.getGoodsPrice());
				opicg.setGoodsQuantity(t.getGoodsQuantity());
				opicg.setGoodsThumbnailUrl(t.getGoodsThumbnailUrl());
				opicg.setOrderAmount(t.getOrderAmount());
				opicg.setOrderCreateTime(t.getOrderCreateTime());
				opicg.setOrderGroupSuccessTime(t.getOrderGroupSuccessTime());
				opicg.setOrderModifyAt(t.getOrderModifyAt());
				opicg.setOrderPayTime(t.getOrderPayTime());
				opicg.setOrderReceiveTime(t.getOrderReceiveTime());
				opicg.setOrderStatus(t.getOrderStatus());
				opicg.setOrderStatusDesc(t.getOrderStatusDesc());
				opicg.setOrderVerifyTime(t.getOrderVerifyTime());
				opicg.setPid(t.getPid());
				opicg.setPromotionAmount(t.getPromotionAmount());
				opicg.setPromotionRate(t.getPromotionRate());
				opicg.setType(t.getType());
				openPddIncrementGoodsHistoryDAO.saveOrUpdate(opicg);
				openPddIncrementGoods2DAO.delete(t);
				
			}

	

		
	}
	@Override
	public void orderCount() {
		
		Date oDay = DateUtil.addMonth2Date(-2, DateUtil.getMonthDayStart(DateUtil.getNow()));
		Date eDay = DateUtil.getDayEnd(DateUtil.cutDay2Date(1, DateUtil.getMonthDayStart(DateUtil.getNow())));
		String oneDay = DateUtil.formatDateTime(oDay);//2个月前的第一天
    	String endDay = DateUtil.formatDateTime(eDay);//前一个月的最后一天
    	//上个月16号
    	Date start16 =  DateUtil.addDay2Date(16, DateUtil.addMonth2Date(-1, DateUtil.getMonthDayStart(DateUtil.getNow())));
    	//本月15号
    	Date lDay = DateUtil.addMonth2Date(15, DateUtil.getMonthDayStart(DateUtil.getNow()));
    	//pdd 签名验证
    	String time = String.valueOf(System.currentTimeMillis());
    	
    	QueryModel query = new QueryModel();
		int goodNum = 0;
    	Map<String,String> pddSignMap = new HashMap<String,String>();
		
		pddSignMap.put("type", "pdd.ddk.order.list.range.get");
    	pddSignMap.put("data_type", StringUtil.data_type);
    	pddSignMap.put("timestamp", time);
    	pddSignMap.put("client_id", StringUtil.client_id_axp);
    	pddSignMap.put("start_time", oneDay);
    	pddSignMap.put("end_time", endDay);
    	pddSignMap.put("time_type", "1");
		String sign1 = MD5Util.getSign(pddSignMap, StringUtil.client_secret_axp);
		
		String url = "http://gw-api.pinduoduo.com/api/router";
    	String param = "type=pdd.ddk.order.list.range.get&data_type="+StringUtil.data_type+"&time_type=1&timestamp="+time+"&client_id="+StringUtil.client_id_axp+"&start_time="+oneDay+"&end_time="+endDay+"&sign="+sign1;
    	
    	List<Map<String,Object>> goodsList = UrlUtil.sendPostForList(url, param,"order_list_get_response");
    	if(goodsList.size()<=0){
	    	//return JsonResponseUtil.getJson(-0x02, "没有查询到相关记录");
    	}else{
    		for(int i = 0;i<goodsList.size();i++){
    			if((start16.getTime()/1000)<=(Integer)goodsList.get(i).get("order_receive_time") 
    					&& (Integer)goodsList.get(i).get("order_receive_time")<=(lDay.getTime()/1000)
    					&& (Integer)goodsList.get(i).get("order_status")==5){//已结算 保存本地数据
    				//保存已结算商品 把所有的字段全部保存起来
    				query.clearQuery();
    				query.combPreEquals("goodsId", goodsList.get(i).get("goods_id").toString());
    				OpenPddGoods openPddGood = (OpenPddGoods) dateBaseDAO.findOne(OpenPddGoods.class, query);
    				
    				if(openPddGood == null){
    					OpenPddGoods pddg = new OpenPddGoods();
	    				pddg.setGoodsId(goodsList.get(i).get("goods_id").toString());
	    				pddg.setGoodsQuantity((Integer)goodsList.get(i).get("goods_quantity"));
	    				pddg.setOrderAmount(goodsList.get(i).get("order_amount").toString());
	    				pddg.setOrderCreateTime(Timestamp.valueOf(DateUtil.stampToDate(goodsList.get(i).get("order_create_time").toString())));
	    				pddg.setOrderStatus((Integer)goodsList.get(i).get("order_status"));
	    				pddg.setPid(goodsList.get(i).get("p_id").toString());
	    				pddg.setPromotionAmount(Double.valueOf(goodsList.get(i).get("promotion_amount").toString()));
	    				pddg.setPromotionRate(goodsList.get(i).get("promotion_rate").toString());
	    				pddg.setType(goodsList.get(i).get("type").toString());
	    				pddg.setCustomParameters(goodsList.get(i).get("custom_parameters").toString());
	    				pddg.setGoodsName(goodsList.get(i).get("goods_name").toString());
	    				pddg.setGoodsPrice(goodsList.get(i).get("goods_price").toString());
	    				pddg.setGoodsThumbnailUrl(goodsList.get(i).get("goods_thumbnail_url").toString());
	    				pddg.setOrderGroupSuccessTime(goodsList.get(i).get("order_group_success_time").toString());
	    				pddg.setOrderModifyAt(goodsList.get(i).get("order_modify_at").toString());
	    				pddg.setOrderPayTime(goodsList.get(i).get("order_pay_time").toString());
	    				pddg.setOrderReceiveTime(goodsList.get(i).get("order_receive_time").toString());
	    				pddg.setOrderSn(goodsList.get(i).get("order_sn").toString());
	    				pddg.setOrderStatusDesc(goodsList.get(i).get("order_status_desc").toString());
	    				pddg.setOrderVerifyTime(goodsList.get(i).get("order_verify_time").toString());
	    				pddg.setIsWithdraw(true);
	    				openPddGoodsDAO.saveOrUpdate(pddg);
    				}
    				
    			}
    		}
    	}
		
		
		
	}
	@Override
	public Map<String, Object> catsGet(HttpServletRequest request) {
		Parameter parameter = ParameterUtil.getParameter(request);
		String parent_cat_id = parameter.getData().getString("parent_cat_id")==null?"0":parameter.getData().getString("parent_cat_id");
	    
		Map<String,Object> dataMap = new HashMap<String,Object>();
		
		String time = String.valueOf(System.currentTimeMillis());
		Map<String,String> signMap = new HashMap<String,String>();
		
		signMap.put("type", "pdd.goods.cats.get");
		signMap.put("data_type", StringUtil.data_type);
		signMap.put("timestamp", time);
		signMap.put("client_id", StringUtil.client_id_axp);
		signMap.put("parent_cat_id", parent_cat_id);
		String sign1 = MD5Util.getSign(signMap, StringUtil.client_secret_axp);
		
		String url = "http://gw-api.pinduoduo.com/api/router";
    	String param = "type=pdd.goods.cats.get&data_type="+StringUtil.data_type+"&parent_cat_id="+parent_cat_id+"&timestamp="+time+"&client_id="+StringUtil.client_id_axp+"&sign="+sign1;
    	
		List<Map<String,Object>> catsList = UrlUtil.sendPostForList(url, param, "goods_cats_get_response");
		dataMap.put("catsList", catsList);
	
		return dataMap;
	}


	@Override
	public Map<String, Object> getCard(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Parameter parameter = ParameterUtil.getParameter(request);
		String pid = parameter.getData().getString("pid")==null?"":parameter.getData().getString("pid");
		String headimage = parameter.getData().getString("headimage")==null?"":parameter.getData().getString("headimage");
		String qrcode = parameter.getData().getString("qrcode")==null?"":parameter.getData().getString("qrcode");
		String openId = parameter.getClient_id();
		
		String path ="/2t/jboss/open.war/";
		//String path="E:/xampp/tomcat/webapps/open/";
		String filename = MD5Util.GetMD5Code(pid)+".png";
		
		String cardPt = path+"/image/card/"+openId+"/";//文件夹路径
		String readPath = cardPt+filename;//文件路径
		
		String card ="http://open.aixiaoping.cn:8080/open/image/card/"+openId+"/"+filename;
		Map<String,Object> dataMap = new HashMap<String,Object>();
		if(StringUtils.isBlank(pid)){
			return JsonResponseUtil.getJson(-0x02, "pid错误");
		}
		try{
		
		File file = new File(readPath);
		if(file.exists()){
		//存在
		}else{//生成图片
			  String sourceFilePath = path+"/image/"+openId+".png";
			  File dtfile = new File(sourceFilePath);
			  BufferedImage buffImg = ImageIO.read(dtfile);
			  
			  if(StringUtils.isBlank(headimage) || StringUtils.isBlank(qrcode)){
				  card ="http://open.aixiaoping.cn:8080/open/image/"+openId+"。png";
				  dataMap.put("card", card);
				  return dataMap;
			  }
			  
			  
			  URL txurl = new URL(headimage);
			  URL ewmurl = new URL(qrcode);
			  
			  BufferedImage txImg = ImageIO.read(txurl);
			  BufferedImage qrImg = ImageIO.read(ewmurl);
			  
			  
		        // 获取层图
		       // BufferedImage waterImg = ImageIO.read(waterFile);
		       // 创建Graphics2D对象，用在底图对象上绘图
		        Graphics2D g2d = buffImg.createGraphics();  
		        int txImgWidth = txImg.getWidth();// 获取层图的宽度
		        int txImgHeight = txImg.getHeight();
		        
		        int qrImgWidth = qrImg.getWidth();// 获取层图的宽度
		        int qrImgHeight = qrImg.getHeight();
		         // 在图形和图像中实现混合和透明效果
		        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
		         // 绘制
		         g2d.drawImage(txImg, 331,117, txImgWidth, txImgHeight, null);
		         g2d.drawImage(qrImg, 185, 470, qrImgWidth, qrImgHeight, null);
		         
		         g2d.dispose();// 释放图形上下文使用的系统资源
		         
		         File files =new File(cardPt);
		         if(!files.exists()){
		        	 files.mkdirs();
		         }
		         
		         generateWaterFile(buffImg,readPath);
		        
		
		}
		
		}catch(Exception e){
			card ="http://open.aixiaoping.cn:8080/open/image/"+openId+"。png";
			e.printStackTrace();
			dataMap.put("card", card);
			return dataMap;
		}
		dataMap.put("card", card);
		return dataMap;
	}


	 private void generateWaterFile(BufferedImage buffImg, String savePath) {
         int temp = savePath.lastIndexOf(".") + 1;
         try {
             ImageIO.write(buffImg, savePath.substring(temp), new File(savePath));
         } catch (IOException e1) {
             e1.printStackTrace();
         }
    }
	@Override
	public Map<String, Object> findIncrementList(HttpServletRequest request) {
		Parameter parameter = ParameterUtil.getParameter(request);
		String openId = parameter.getClient_id();
		String sign = parameter.getSign();
		String startTime = parameter.getData().getString("startTime");
		String endTime = parameter.getData().getString("endTime");
		String limit = parameter.getData().getString("limit")==null?"20":parameter.getData().getString("limit");
	    String page = parameter.getData().getString("page")==null?"1":parameter.getData().getString("page");
		
		QueryModel queryModel = new QueryModel();
		queryModel.clearQuery();
		queryModel.combPreEquals("clientId", openId,"client_id");
		OpenApp openApp = (OpenApp) dateBaseDAO.findOne(OpenApp.class, queryModel);
		double  maid =0.95;
		String pid = openApp.getPddPid();
		if(openApp.getMaid()!=null){
			maid = CalcUtil.div(Integer.parseInt(openApp.getMaid()), 100, 2);
			
		}
    	queryModel.clearQuery();

    	queryModel.combCondition("orderModifyAt >"+startTime);
    	queryModel.combCondition("orderModifyAt <" + endTime);
    	queryModel.combCondition("orderStatus >-1 and customParameters like '"+pid+"%'");
    	

    	
    	int count = dateBaseDAO.findCount(OpenPddIncrementGoods2.class, queryModel);
    	limit="2000";
		int totalPage = (count % Integer.valueOf(limit)) > 0 ? ((count / Integer.valueOf(limit)) + 1)
				: (count / Integer.valueOf(limit));
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		
		List<OpenPddIncrementGoods2> openGoods = dateBaseDAO.findPageList(OpenPddIncrementGoods2.class, queryModel, start, Integer.valueOf(limit));
		 HashSet<OpenPddIncrementGoods2> h = new HashSet<OpenPddIncrementGoods2>(openGoods);   
		 openGoods.clear();   
		 openGoods.addAll(h);  
    	Map<String,Object> dataMap = new HashMap<String,Object>();
    	
    	dataMap.put("openGoods", openGoods);
    	return dataMap;
	}
	@Override
	public Map<String, Object> getImage(HttpServletRequest request) {
		
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		Parameter parameter = ParameterUtil.getParameter(request);
		String pid = parameter.getData().getString("pid")==null?"":parameter.getData().getString("pid");
		String headimage = parameter.getData().getString("headimage")==null?"":parameter.getData().getString("headimage");
		String qrcode = parameter.getData().getString("qrcode")==null?"":parameter.getData().getString("qrcode");
		String openId = parameter.getClient_id();
		String goodsId = parameter.getData().getString("goodsId")==null?"":parameter.getData().getString("goodsId");
		//String goodsName = parameter.getData().getString("goodsName")==null?"":parameter.getData().getString("goodsName");
		String quan = parameter.getData().getString("quan")==null?"":parameter.getData().getString("quan");
		String price = parameter.getData().getString("price")==null?"":parameter.getData().getString("price");
		//String goodsImage = parameter.getData().getString("goodsImage")==null?"":parameter.getData().getString("goodsImage");
		String yprice = parameter.getData().getString("yprice")==null?"":parameter.getData().getString("yprice");
		//System.out.println(headimage);
		//System.out.println(headimage.replaceAll("//", "/"));
		String goodsName="";
    	String goodsImage="";
		
		
		//String path="E:/xampp/tomcat/webapps/open/";
		String path ="/2t/jboss/open.war/";
		//String path ="E:/myEclipse/work/SVN//open/WebContent/";
		String filename =MD5Util.GetMD5Code(goodsId)+MD5Util.GetMD5Code(pid)+".png";
		
		String cardPt = path+"/image/goods/"+openId+"/";//文件夹路径
		String readPath = cardPt+filename;//文件路径
		String qrpath = path+"/image/qr/"+openId+"/";//文件夹路径
		String qrPaths = qrpath+filename;//文件路径
		String goods ="http://open.aixiaoping.cn:8080/open/image/goods/"+openId+"/"+filename;
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		if(StringUtils.isBlank(pid)){
			return JsonResponseUtil.getJson(-0x02, "pid错误");
		}
		try{
		
		File file = new File(readPath);
		if(file.exists()){
		//存在
		}else{//生成图片
			  String sourceFilePath = path+"/image/goods.png";
			  if(StringUtils.isBlank(goodsId) || StringUtils.isBlank(qrcode)){
				  goods ="http://open.aixiaoping.cn:8080/open/image/goods.png";
				  dataMap.put("card", goods);
				  return dataMap;
			  }
			  
			  
			  OpenGoodsCurrent ogc = openGoodsCurrentDAO.findById(1);
		    	
				QueryModel query = new QueryModel();
		    	//查询商品列表
		    	query.clearQuery();
		    	
		    	
		    	query.combEquals("goodsId", Integer.parseInt(goodsId));
		    	query.combCondition("promotionRate >99");
		    	query.setOrder("id ASC");//排序字段

		    	
		    	
				if(ogc.getOpenGoods() == 1){
					List<OpenGoods> openGoodsList = dateBaseDAO.findPageList(OpenGoods.class, query, 0, 1);
					if(openGoodsList!=null && openGoodsList.size()>0){
						OpenGoods openGood = openGoodsList.get(0);
						goodsName = openGood.getGoodsName();
						goodsImage = openGood.getGoodsThumbnailUrl();
					}else{
						 goods ="http://open.aixiaoping.cn:8080/open/image/goods.png";
						  dataMap.put("card", goods);
						  return dataMap;
					}
					
				}else{
					List<OpenGoods2> openGoodsList = dateBaseDAO.findPageList(OpenGoods2.class, query, 0, Integer.valueOf(1));
					//封装的
				
					
						if(openGoodsList!=null && openGoodsList.size()>0){
							OpenGoods2 openGood = openGoodsList.get(0);
							goodsName = openGood.getGoodsName();
							goodsImage = openGood.getGoodsThumbnailUrl();
						}else{
							 goods ="http://open.aixiaoping.cn:8080/open/image/goods.png";
							  dataMap.put("card", goods);
							  return dataMap;
						}
						
					
						
				}
			  
			  
			  File qfile = new File(qrpath);
				if(!qfile.exists()){
					qfile.mkdirs();
				}
			   String ewmurls = CreateQRCode.getQR(qrcode, readPath, qrPaths);
		        URL spurl = new URL(goodsImage);
		        
		        File qrfile = new File(qrPaths);
		        
		        
		        
		        BufferedImage waterImg2 = ImageIO.read(qrfile); 
		        BufferedImage waterImg3 = ImageIO.read(spurl); 
	            // 读取原图片信息
	            File srcImgFile = new File(sourceFilePath);//得到文件
	            Image srcImg = ImageIO.read(srcImgFile);//文件转化为图片
	            
	            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
	            int srcImgHeight = srcImg.getHeight(null);//获取图片的高
	            // 加水印
	            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
	            Graphics2D g = bufImg.createGraphics();
	            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
	           

	            //设置水印的坐标
	            Font font0 = new Font("黑体", Font.ITALIC, 35); 
	            Color color0=new Color(233,150,122,250);  
	            g.setColor(color0); //根据图片的背景设置水印颜色
	            g.setFont(font0);              //设置字体
	             //画出水印
	            if(goodsName.length()>21){
	            	g.drawString(goodsName.substring(0, 20), 30, 1150);
	            	if(goodsName.length()>40){
	            		g.drawString(goodsName.substring(21, 40), 30, 1200);
	            	}else{
	            		g.drawString(goodsName.substring(21, goodsName.length()), 30, 1200);
	            	}
	            	 
	            }else{
	            	g.drawString(goodsName, 30, 1150);
	            }
	            
	            Font font1 = new Font("微软雅黑", Font.BOLD+Font.ITALIC, 70);
	            Color color1=new Color(255 ,250 ,250,250);
	            g.setColor(color1); //根据图片的背景设置水印颜色
	            g.setFont(font1);
	            
	            int point = 70;
	            if(quan.length()==1){
	            	 point = 105;
	            }else if(quan.length()==2){
	            	 point = 110;
	            }else if(quan.length()==3){
	            	 point = 75;
	            }
	            
	            g.drawString(quan, point, 1350);
	            
	            Font font2 = new Font("微软雅黑", Font.BOLD, 55);
	            Color color2=new Color(255,64,128,250);
	            g.setColor(color2); //根据图片的背景设置水印颜色
	            g.setFont(font2);
	            
	            g.drawString(price, 155, 1470);
	            
	            
	            Font font3 = new Font("微软雅黑", Font.BOLD, 30);
	            Color color3=new Color(0,0,0,88);
	            g.setColor(color3); //根据图片的背景设置水印颜色
	            g.setFont(font3);
	            
	            g.drawString(yprice, 432, 1466);
	            
	            if(StringUtils.isNotBlank(headimage)){
					   URL headimageurl = new URL(headimage);
					   BufferedImage waterImg = ImageIO.read(headimageurl); 
					   g.drawImage(waterImg, 34, 34, 120, 120, null);
				   }
	           
	            g.drawImage(waterImg2, 830, 1106, 232, 232, null);
	            g.drawImage(waterImg3, 95, 182, 900, 900, null);
	            
	            g.dispose();  
		         
		         
		         
		         
		         File files =new File(cardPt);
		         if(!files.exists()){
		        	 files.mkdirs();
		         }
		         
		         generateWaterFile(bufImg,readPath);
		        
		
		}
		
		}catch(Exception e){
			goods ="http://open.aixiaoping.cn:8080/open/image/goods.png";
			e.printStackTrace();
			dataMap.put("card", goods);
			return dataMap;
		}
		dataMap.put("card", goods);
		return dataMap;
	}
	@Override
	public Map<String, Object> update(HttpServletRequest request) {
		
		 List<OpenPddIncrementGoods2> oplist = openPddIncrementGoods2DAO.queryAll();
		    
			Map<String ,String > pidmap = new HashMap<String,String>();
			
			Map<String ,String > omap = new HashMap<String,String>();
			omap.put("1001389_14247718", "68bf2d0655473ec3ff6670394fbf5f24");
			omap.put("1001389_11743081", "68bf2d0655473ec3ff6670394fbf5f25");
			omap.put("1001389_14247709", "68bf2d0655473ec3ff6670394fbf5f26");
			omap.put("1001389_14247705", "68bf2d0655473ec3ff6670394fbf5f27");
			omap.put("1001389_14894927", "8abe02f1fb5375ed833becf14297b97a");
			List<PidRelation> pidList = pidRelationDAO.queryAll();
			for(PidRelation p :pidList){
				pidmap.put(p.getPddPid(), p.getPddUpPid());
			}
			
			
			
			for(OpenPddIncrementGoods2 op : oplist){
				
				String pid = op.getPid().substring(8);
				
				if(omap.get(op.getPid())!=null){
					op.setCustomParameters(op.getPid()+"----"+pid);
				}else{
					op.setCustomParameters(pidmap.get(op.getPid())+"----"+pid);
				}
				
				
				openPddIncrementGoods2DAO.update(op);
			}
			
			
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("card", "goods");
			return dataMap;
	}

	@Override
	public Map<String, Object> goodsDetail(HttpServletRequest request) {
		Parameter parameter = ParameterUtil.getParameter(request);
		String openId = parameter.getClient_id();
		String sign = parameter.getSign();
		String goodsIdList = parameter.getData().getString("goodsId");
		
		if(openId == null || sign == null || goodsIdList == null ){
			return JsonResponseUtil.getJson(-0x02, "必要参数不能为空");
		}
		
		QueryModel query = new QueryModel();
		query.combPreEquals("clientId", openId,"client_id");
		OpenApp oc = (OpenApp) dateBaseDAO.findOne(OpenApp.class, query);
		if(oc == null){
			return JsonResponseUtil.getJson(-0x02, "client_id错误,请联系管理员");
		}
		Integer maid = Integer.valueOf(oc.getMaid());
		Map<String,String> signMap = new HashMap<String,String>();
		signMap.put("client_id", openId);
		if(!MD5Util.getSign(signMap, oc.getClientScrect()).equals(sign)){
			return JsonResponseUtil.getJson(-0x02, "签名不通过");
		}
		
		String time = String.valueOf(System.currentTimeMillis()/1000);
		String client_id = "";
    	String client_secret = "";
//    	if(StringUtil.openId.equals(openId)){
//    		client_id = StringUtil.client_id_other;
//    		client_secret = StringUtil.client_secret_other;
//    	}else{
    		client_id = StringUtil.client_id_axp;
    		client_secret = StringUtil.client_secret_axp;
//    	}
		Map<String,String> sign1 = new HashMap<String,String>();
		sign1.put("client_id", client_id);
		sign1.put("type", "pdd.ddk.goods.detail");
		sign1.put("timestamp", String.valueOf(time));
		sign1.put("data_type", StringUtil.data_type);
		sign1.put("goods_id_list", "["+goodsIdList+"]");
		String sign2 = MD5Util.getSign(sign1,client_secret);
		
		List<Map<String,Object>> orderList = new ArrayList<Map<String,Object>>();
		String url = "http://gw-api.pinduoduo.com/api/router";
		String param = "type=pdd.ddk.goods.detail&data_type=JSON&timestamp="+time+"&client_id="+client_id+"&goods_id_list=["+goodsIdList+"]&sign="+sign2;
		
		
		List<Map<String,Object>> goodDetail = UrlUtil.sendPostForListNew(url, param, "goods_detail_response");
		try {
			if(goodDetail != null && goodDetail.size()>0){
				orderList = UrlUtil.jsonObjectToList(goodDetail.get(0).get("goods_details").toString());
				for(int i =0;i<orderList.size();i++){
					Integer promotion_rate = (Integer)orderList.get(i).get("promotion_rate")*maid/100;
					orderList.get(i).put("promotion_rate", promotion_rate);
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		Map<String,Object> statusMap = new HashMap<String,Object>();
		statusMap.put("orderList", orderList);
		return statusMap;
	}
	
	
	
	public List<Map<String, Object>> goodsDetail(String goodsIdList,Integer maid) {
		
		
		String time = String.valueOf(System.currentTimeMillis()/1000);
		
		Map<String,String> sign1 = new HashMap<String,String>();
		sign1.put("client_id", StringUtil.client_id_axp);
		sign1.put("type", "pdd.ddk.goods.detail");
		sign1.put("timestamp", String.valueOf(time));
		sign1.put("data_type", StringUtil.data_type);
		sign1.put("goods_id_list", "["+goodsIdList+"]");
		String sign2 = MD5Util.getSign(sign1,StringUtil.client_secret_axp);
		
		List<Map<String,Object>> orderList = new ArrayList<Map<String,Object>>();
		String url = "http://gw-api.pinduoduo.com/api/router";
		String param = "type=pdd.ddk.goods.detail&data_type=JSON&timestamp="+time+"&client_id="+StringUtil.client_id_axp+"&goods_id_list=["+goodsIdList+"]&sign="+sign2;
		
		
		List<Map<String,Object>> goodDetail = UrlUtil.sendPostForListNew(url, param, "goods_detail_response");
		try {
			if(goodDetail != null && goodDetail.size()>0){
				orderList = UrlUtil.jsonObjectToList(goodDetail.get(0).get("goods_details").toString());
				for(int i =0;i<orderList.size();i++){
					Integer promotion_rate = (Integer)orderList.get(i).get("promotion_rate")*maid/100;
					orderList.get(i).put("promotion_rate", promotion_rate);
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		return orderList;
	}
	
	
	
}
	
