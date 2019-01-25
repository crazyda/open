package com.axp.util;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * 这个类是为了请求参数抽象出来的，所有的请求参数是固定不变的，变化的参数，都在data中；
 * 
 * @author Administrator
 *
 */
public class Parameter {

	private String client_id;
	private String client_secret;
	private String sign;
	private String data_type;
	private String timestamp;
	private JSONObject data;
	private String app_id;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	/**
	 * 使用狗仔函数，对每一个成员变量进行赋值；
	 * @param map
	 */
	public Parameter(Map<String, Object> map) {

		this.client_id = map.get("client_id")==null?"":map.get("client_id").toString();
		this.client_secret = map.get("client_secret")==null?"":map.get("client_secret").toString();
		this.sign = map.get("sign")==null?"":map.get("sign").toString();
		this.data_type = (String) map.get("data_type");
		this.app_id =(String) map.get("app_id");
		this.data = (JSONObject) map.get("data");
		this.timestamp=map.get("timestamp")==null?"":map.get("timestamp").toString();
		this.type =(String) map.get("type");
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getClient_secret() {
		return client_secret;
	}

	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}
	

	
}
