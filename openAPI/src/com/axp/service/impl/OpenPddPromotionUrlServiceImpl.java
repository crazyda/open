package com.axp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.axp.dao.DateBaseDAO;
import com.axp.domain.OpenApp;
import com.axp.domain.OpenClient;
import com.axp.domain.OpenPddPromotionUrl;
import com.axp.service.OpenAppService;
import com.axp.service.OpenClientService;
import com.axp.service.OpenPddPromotionUrlService;
import com.axp.util.JsonResponseUtil;
import com.axp.util.MD5Util;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;
import com.axp.util.StringUtil;
import com.axp.util.UrlUtil;


@Service
public class OpenPddPromotionUrlServiceImpl extends BaseServiceImpl<OpenPddPromotionUrl> implements OpenPddPromotionUrlService{

	@Resource
	private DateBaseDAO dateBaseDAO;
	@Resource
	private OpenClientService openClientService;
	
	
	
	@Override
	public Map<String, Object> GeneratePddPromotionUrl(
			HttpServletRequest request) {

		Parameter parameter = ParameterUtil.getParameter(request);
		
		String openId = parameter.getClient_id();
	    String sign = parameter.getSign();
	    String pid = parameter.getData().getString("pid");
	    String custom_parameters = parameter.getData().getString("custom_parameters");
	    String goodsIdList = parameter.getData().getString("goodsIdList");
	    
	    if(parameter == null ||openId == null || sign == null || goodsIdList == null){
	    	return JsonResponseUtil.getJson(-0x01, "必要参数不能为空");
	    }
		
	    Map<String,Object> statusMap = new HashMap<String,Object>();
		
		OpenClient oc = openClientService.getOpenClientByClientId(openId);
		
		Map<String,String> signMap1 = new HashMap<String,String>();
    	signMap1.put("client_id", openId);
		
    	if(oc != null){
    		if(!MD5Util.getSign(signMap1, oc.getClientSecret()).equals(sign)){
    			return JsonResponseUtil.getJson(-0x01, "签名验证不通过！");
    		}
    	}else{
    		return JsonResponseUtil.getJson(-0x02, "client_id错误！请联系管理员");
    	}
    	
    	QueryModel query = new QueryModel();
    	query.clearQuery();
    	query.combPreEquals("clientId", openId,"client_id");
    	OpenApp openApp = (OpenApp) dateBaseDAO.findOne(OpenApp.class, query);
    	
    	String client_id = "";
    	String client_secret = "";
//    	if(StringUtil.client_id_other.equals(openId)){
//    		client_id = StringUtil.client_id_other;
//    		client_secret = StringUtil.client_secret_other;
//    	}else{
    		client_id = StringUtil.client_id_axp;
    		client_secret = StringUtil.client_secret_axp;
//    	}
    	custom_parameters=openApp.getPddPid()+"-"+custom_parameters;
    	String time = String.valueOf(System.currentTimeMillis());
    	Map<String,String> signMap = new HashMap<String,String>();
    	signMap.put("type", "pdd.ddk.goods.promotion.url.generate");
    	signMap.put("p_id", pid);
    	signMap.put("goods_id_list", "["+goodsIdList+"]");
    	signMap.put("generate_short_url", "true");//目前默认是true
    	signMap.put("multi_group", "false");
    	signMap.put("custom_parameters", custom_parameters);
    	signMap.put("timestamp", time);
    	signMap.put("data_type", StringUtil.data_type);
    	signMap.put("client_id", client_id);
    	String sign1 = MD5Util.getSign(signMap, client_secret);
    	
    	String url = "http://gw-api.pinduoduo.com/api/router";
        String param = "type=pdd.ddk.goods.promotion.url.generate&data_type=JSON&p_id="+pid+"&sign="+sign1
        		+"&client_id="+client_id+"&goods_id_list=["+goodsIdList+"]&generate_short_url=true&multi_group=false"
        		+"&timestamp="+time+"&custom_parameters="+custom_parameters;
        List<Map<String, Object>> goodsUrlList = UrlUtil.sendPostForList(url, param,"goods_promotion_url_generate_response");
    	
        if(goodsUrlList.size()<0){
      	  return JsonResponseUtil.getJson(-0x01, "连接超时没有数据,重新连接");
        }
        List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
        Map<String,Object> dataMap = new HashMap<String,Object>();
        
        for(int i = 0; i<goodsUrlList.size();i++){
        	dataMap = new HashMap<String,Object>();
        	
        	dataMap.put("we_app_web_view_short_url", goodsUrlList.get(i).get("we_app_web_view_short_url"));//唤醒拼多多APP 短
        	dataMap.put("we_app_web_view_url", goodsUrlList.get(i).get("we_app_web_view_url"));//唤醒拼多多APP 短
        	
        	dataMap.put("mobile_short_url", goodsUrlList.get(i).get("mobile_short_url"));//唤醒拼多多APP 短
        	dataMap.put("mobile_url", goodsUrlList.get(i).get("mobile_url"));//唤醒pdd app 长
        	dataMap.put("short_url", goodsUrlList.get(i).get("short_url"));//短连接
        	dataMap.put("url", goodsUrlList.get(i).get("url"));//长连接

        	
        	
        	dataMap.put("goods_detail", goodsUrlList.get(i).get("goods_detail"));

        	data.add(dataMap);
        }
        statusMap.put("code", 1);
        statusMap.put("goods_promotion_url_list", data);
		return statusMap;
	}

public Map<String,Object> goodsDetail(String goodsIdList,Integer maid) {
		
		
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
		
		
		
		
		return goodDetail.get(0);
	}
	
	
	@Override
	public Map<String, Object> OpenMallPromUrl(HttpServletRequest request) {
		Parameter parameter = ParameterUtil.getParameter(request);
		String openId = parameter.getClient_id();
	    String sign = parameter.getSign();
		String pidList = parameter.getData().getString("pidList");//格式["",""]
		String generate_short_url = parameter.getData().getString("generate_short_url")==null?"true":parameter.getData().getString("generate_short_url");
		String generate_mobile = parameter.getData().getString("generate_mobile")==null?"false":parameter.getData().getString("generate_mobile");
		String multi_group = parameter.getData().getString("multi_group")==null?"false":parameter.getData().getString("multi_group");
		if(parameter == null ||openId == null || sign == null ){
	    	return JsonResponseUtil.getJson(-0x01, "必要参数不能为空");
	    }
		Map<String,Object> statusMap = new HashMap<String,Object>();
		
		OpenClient oc = openClientService.getOpenClientByClientId(openId);
		
		Map<String,String> signMap1 = new HashMap<String,String>();
    	signMap1.put("client_id", openId);
		
    	if(oc != null){
    		if(!MD5Util.getSign(signMap1, oc.getClientSecret()).equals(sign)){
    			return JsonResponseUtil.getJson(-0x01, "签名验证不通过！");
    		}
    	}else{
    		return JsonResponseUtil.getJson(-0x02, "client_id错误！请联系管理员");
    	}
    	
    	String client_id = "";
    	String client_secret = "";
//    	if(StringUtil.openId.equals(openId)){
//    		client_id = StringUtil.client_id_other;
//    		client_secret = StringUtil.client_secret_other;
//    	}else{
    		client_id = StringUtil.client_id_axp;
    		client_secret = StringUtil.client_secret_axp;
//    	}
    	String time = String.valueOf(System.currentTimeMillis());
    	Map<String,String> signMap = new HashMap<String,String>();
    	signMap.put("type", "pdd.ddk.cms.prom.url.generate");
    	signMap.put("p_id_list", pidList);
    	signMap.put("generate_short_url", generate_short_url);//目前默认是true
    	signMap.put("generate_mobile", generate_mobile);
    	signMap.put("multi_group", multi_group);
    	signMap.put("timestamp", time);
    	signMap.put("data_type", StringUtil.data_type);
    	signMap.put("client_id", client_id);
    	String sign1 = MD5Util.getSign(signMap, client_secret);
    	
    	String url = "http://gw-api.pinduoduo.com/api/router";
        String param = "type=pdd.ddk.cms.prom.url.generate&data_type="+StringUtil.data_type+"&p_id_list="+pidList+"&sign="+sign1
        		+"&client_id="+client_id+"&generate_short_url=true&multi_group=false&generate_mobile=false"
        		+"&timestamp="+time;
        List<Map<String, Object>> mallUrlList = UrlUtil.sendPostForListNew(url, param,"cms_promotion_url_generate_response");
       Integer total_count = 0;
       List<Map<String, Object>> urlList = new ArrayList<Map<String, Object>>();
        total_count = (Integer)mallUrlList.get(0).get("total");
		try {
			urlList = UrlUtil.jsonObjectToList(mallUrlList.get(0).get("url_list").toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		statusMap.put("total", total_count);
		statusMap.put("urlList", urlList);
		
		
		return statusMap;
	}
	

}
	
