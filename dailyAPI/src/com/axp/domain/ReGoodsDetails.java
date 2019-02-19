package com.axp.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * ReGoodsDetails entity. @author MyEclipse Persistence Tools
 */

public class ReGoodsDetails implements java.io.Serializable {

	// Fields

	private Integer id;
	private ReGoodsOfBase goods;
	private String content;
	private Boolean isValid;
	private Integer isNew;
	// Constructors

	public Integer getIsNew() {
		return isNew;
	}
	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}
	/** default constructor */
	public ReGoodsDetails() {
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ReGoodsOfBase getGoods() {
		return goods;
	}

	public void setGoods(ReGoodsOfBase goods) {
		this.goods = goods;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	
	public JSONArray getJsonContent(String basePath){
		
		JSONArray jsonArray=JSONArray.parseArray(this.content);
		for (int i = 0; i < jsonArray.size(); i++) {
			 JSONObject obj = jsonArray.getJSONObject(i);
			 if(obj.getString("detailType").equals("picture")){
				 obj.put("picture", basePath+obj.getString("picture"));
			 }
			
		}
		return jsonArray;
	}

	public Map<String,Object> getMapContent(String basePath){
		JSONArray jsonArray=JSONArray.parseArray(this.content);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> list2 = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < jsonArray.size(); i++) {
			
			 JSONObject obj = jsonArray.getJSONObject(i);
			 if(obj.getString("detailType").equals("picture")){
				 Map<String,Object> map = new HashMap<String,Object>();
				 map.put("picture", basePath+obj.getString("picture"));
				list.add(map);
				 
			 }else if(obj.getString("detailType").equals("text")){
				 Map<String,Object> map2 = new HashMap<String,Object>();
				 map2.put("text", obj.getString("text"));
				 list2.add(map2);
				
			 }
		
		}
		
		dataMap.put("list", list);
		dataMap.put("list2", list2);
		return dataMap;
		
	}



}