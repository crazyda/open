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

	private String axp;
	private String times;
	private String userId;
  
	
	private String sellerId;

	private String adminuserId;

	private String bankId;
	private String lng;
	private String lat;
	private String channelId;
	private String os;
	private String appVersion;
	private String dip;
	private String zoneId;
	private String machineCode;
	private String basePath;
	private JSONObject data;
	private String tokenId;
	private String app;
	/**
	 * 使用狗仔函数，对每一个成员变量进行赋值；
	 * @param map
	 */
	public Parameter(Map<String, Object> map) {
//		this.userId = (String) map.get("userId");
//		this.lng = (String) map.get("lng");
//		this.lat = (String) map.get("lat");
		this.userId = map.get("userId")==null?"":map.get("userId").toString();
		this.lng = map.get("lng")==null?"":map.get("lng").toString();
		this.lat = map.get("lat")==null?"":map.get("lat").toString();
		this.channelId = (String) map.get("channelId");
		this.os = map.get("os")==null?"":map.get("os").toString();
		this.tokenId = (String) map.get("tokenId");
		this.appVersion = (String) map.get("appVersion");
		this.dip = (String) map.get("dip");
		this.app = (String) map.get("app");
//		this.zoneId = (String) map.get("zoneId");
		this.zoneId = map.get("zoneId")==null?"":map.get("zoneId").toString();
		this.sellerId=map.get("sellerId")==null?"":map.get("sellerId").toString();
		this.bankId=map.get("bankId")==null?"":map.get("bankId").toString();
		this.adminuserId=map.get("adminuserId")==null?"":map.get("adminuserId").toString();
		this.machineCode = (String) map.get("machineCode");
		this.data = (JSONObject) map.get("data");
		this.times=map.get("times")==null?"":map.get("times").toString();
		this.axp=map.get("axp")==null?"":map.get("axp").toString();
	}
	public String getAdminuserId() {
		return adminuserId;
	}
	public String getApp() {
		return app;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public String getAxp() {
		return axp;
	}
	public String getBankId() {
		return bankId;
	}

	public String getBasePath() {
		return basePath;
	}

	public String getChannelId() {
		return channelId;
	}

	public JSONObject getData() {
		return data;
	}

	public String getDip() {
		return dip;
	}

	public String getLat() {
		return lat;
	}

	public String getLng() {
		return lng;
	}

	public String getMachineCode() {
		return machineCode;
	}

	public String getOs() {
		return os;
	}

	public String getSellerId() {
		return sellerId;
	}

	public String getTimes() {
		return times;
	}

	public String getTokenId() {
		return tokenId;
	}

	public String getUserId() {
		return userId;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setAdminuserId(String adminuserId) {
		this.adminuserId = adminuserId;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public void setAxp(String axp) {
		this.axp = axp;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	
}
