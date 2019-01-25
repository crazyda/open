package com.axp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axp.domain.OpenClient;
import com.axp.domain.Agent;
import com.axp.service.OpenClientService;
import com.axp.service.AgentService;
import com.axp.service.OpenGoodsService;
import com.axp.util.JsonResponseUtil;
import com.axp.util.MD5Util;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;

@Service
public class AgentServiceImpl extends BaseServiceImpl<Agent> implements AgentService{
	
	@Autowired 
	private OpenClientService openClientService; 
	
	@Override
	public Map<String, Object> crateAgent(HttpServletRequest request) {
		
		Parameter parameter = ParameterUtil.getParameter(request);
    	
        String openId = parameter.getClient_id();
        String sign = parameter.getSign();
    	String extUserId = parameter.getData().getString("extUserId");// 代理用戶
    	String parentExtUserId = parameter.getData().getString("parentExtUserId");// 代理上级用户id，不传默认最高级
    	String agentsSeries  = parameter.getData().getString("agentsSeries");//代理等级 默认2，2：普通，3：雇员，//4：主管，5：经理，6：总裁
    	
    	if(parameter == null || openId == null || sign == null ){
    		return JsonResponseUtil.getJson(-0x01, "必要参数不能为空");
    	}
    	if(parentExtUserId == null ){
    		parentExtUserId = "6";
    	}	
    	if(agentsSeries == null ){
    		agentsSeries = "2";
    	}
    	Map<String,Object> statusMap = new HashMap<String,Object>();
    	statusMap.put("code", 1);
    	statusMap.put("result", true);
    	
    	Map<String,String> signMap = new HashMap<String,String>();
    	signMap.put("openId", openId);
    	signMap.put("extUserId", extUserId);
    	signMap.put("parentExtUserId", parentExtUserId);
    	signMap.put("agentsSeries", agentsSeries);
    	OpenClient oc = openClientService.getOpenClientByClientId(openId);
    	

    	if(oc != null){
    		if(!MD5Util.getSign(signMap, oc.getClientSecret()).equals(sign)){
    			return JsonResponseUtil.getJson(-0x01, false);
    		}
    		
    		//开始创建代理 并绑定pid
    		
    		
    	}
    	
    	
    	
    	
		return statusMap;
	}

	

	@Override
	public Map<String, Object> detail(String openId, String extUserId,String sign) {
		if(openId == null || extUserId == null|| sign == null ){
			return JsonResponseUtil.getJson(-0x01, "必要参数不能为空");
		}
		
		Map<String,Object> statusMap = new HashMap<String,Object>();
    	
    	
    	Map<String,String> signMap = new HashMap<String,String>();
    	signMap.put("openId", openId);
    	signMap.put("extUserId", extUserId);
    	OpenClient oc = openClientService.getOpenClientByClientId(openId);

    	if(oc != null){
    		if(!MD5Util.getSign(signMap, oc.getClientSecret()).equals(sign)){
    			return JsonResponseUtil.getJson(-0x01, "签名验证不通过！");
    		}
    		
    		//代理分成明细查询 返回map
    		
    		
    		
    		
    		//封装的
    		Map<String,Object> map = new HashMap<String,Object>();
    		List<Map<String,Object>> commissions = new ArrayList<Map<String,Object>>();
    		for(int i=0;i<3;i++){
    			map.put("shareExtUserId", 50);
    			map.put("rate", 50);
    			commissions.add(map);
    			
    		}
    		Map<String,Object> dataMap = new HashMap<String,Object>();
    		dataMap.put("agentsSeries", 1);
    		dataMap.put("headAgent", 1);
    		dataMap.put("parentExtUserId", 1);
    		dataMap.put("commissions", commissions);
    		dataMap.put("tbPid", 1);
    		dataMap.put("gid", 1);
    		dataMap.put("pddPid", 1);
    		statusMap.put("code", 1);
        	statusMap.put("result", dataMap);
    	}
    	
    	
    	
		return statusMap;
	

	}



	@Override
	public Map<String, Object> changeAgent(HttpServletRequest request) {
		
		Parameter parameter = ParameterUtil.getParameter(request);
		
		String openId = parameter.getClient_id();
	    String sign = parameter.getSign();
	    String type = parameter.getType();//1：解绑上级代理，2：修改代理等级，3：绑定上级代理，4：设置头部代理
	    String headAgent = parameter.getData().getString("headAgent");//1 设置头部代理 2 取消, type =4 时必传 
	    String afterAgentsSeries = parameter.getData().getString("afterAgentsSeries");//修改后的登记, tyep =2 必传
	    String extUserId = parameter.getData().getString("extUserId");// 代理用戶
	    String parentExtUserId = parameter.getData().getString("parentExtUserId");// type=1或3 是必传
	    
	    if(parameter == null || openId == null || sign == null || type == null){
    		return JsonResponseUtil.getJson(-0x01, "必要参数不能为空");
    	}
	    if(type == "1" || type == "3"){
	    	if(parentExtUserId == null){
	    		return JsonResponseUtil.getJson(-0x01, "代理上级用户Id不能为空");
	    	}
	    }
	    if(type == "2"){
	    	if(afterAgentsSeries == null){
	    		return JsonResponseUtil.getJson(-0x01, "修改后的代理等级不能为空");
	    	}
	    }
	    if(type == "4"){
	    	if(headAgent == null){
	    		return JsonResponseUtil.getJson(-0x01, "头部代理不能为空");
	    	}
	    }
	    
	    Map<String,Object> statusMap = new HashMap<String,Object>();
	    
	    OpenClient oc = openClientService.getOpenClientByClientId(openId);
	    Map<String,String> signMap = new HashMap<String,String>();
    	signMap.put("openId", openId);
    	signMap.put("type", type);
    	signMap.put("headAgent", headAgent);
    	signMap.put("afterAgentsSeries", afterAgentsSeries);
    	signMap.put("extUserId", extUserId);
    	signMap.put("parentExtUserId", parentExtUserId);
    	if(oc != null){
    		if(!MD5Util.getSign(signMap, oc.getClientSecret()).equals(sign)){
    			return JsonResponseUtil.getJson(-0x01, "签名验证不通过！");
    		}
    	}
	    //修改代理业务
	    
    	
    	
	    
	    
    	//封装的
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String,Object>> commissions = new ArrayList<Map<String,Object>>();
		for(int i=0;i<3;i++){
			map.put("shareExtUserId", 50);
			map.put("rate", 50);
			commissions.add(map);
			
		}
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("extUserId", 1);
		dataMap.put("agentsSeries", 1); //int 类型
		dataMap.put("headAgent", 1);
		dataMap.put("commissions", commissions);
		dataMap.put("parentExtUserId", 1);
		dataMap.put("tbPid", 1);
		dataMap.put("gid", 1);
		dataMap.put("pddPid", 1);
		
		statusMap.put("code", 1);
    	statusMap.put("result", dataMap);
	    
	    
	    
		return statusMap;
	}
}
	
