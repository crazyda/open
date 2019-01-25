package com.axp.domain;

import java.sql.Timestamp;

/**
 * AbstractOpenClient entity provides the base persistence definition of the
 * OpenClient entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractOpenApp implements java.io.Serializable {

	public Integer id ;
	public String appName;
	public String appType;
	public String appExplain;
	public String appIcon;
	public String returnUrl;
	public String officiaUrl;
	public String clientId;
	public String clientScrect;
	public Timestamp checkTime;
	public Integer checkUsers;
	public String checkRemark;
	public String maid;
	public String pddPid;
	public String tbPid;
	public String jdPid;
	public String wphPid;
	public double money;
	public Users users;//关联账户  
	public  boolean isValid;
	public Timestamp createTime;
	public boolean isStop;
	public Integer checkIsPass;
	public OpenAuthentication openAuthentication;//实名认证ID
	public double hMoney;
	public String domain;
	public String pddifys;//可获取的分类商品
	public String rate;//可获取的分佣范围 start - end 格式
	
	
	
	
	public AbstractOpenApp() {
		super();
	}
	public AbstractOpenApp(Integer id, String appName, String appType,
			String appExplain, String appIcon, String returnUrl,
			String officiaUrl, String clientId, String clientScrect,
			Timestamp checkTime, Integer checkUsers, String checkRemark,
			String maid, String pddPid, String tbPid, String jdPid,
			String wphPid, double money, Users users, boolean isValid,
			Timestamp createTime, boolean isStop, Integer checkIsPass,
			OpenAuthentication openAuthentication, double hMoney,String domain,String pddifys,String rate) {
		super();
		this.id = id;
		this.appName = appName;
		this.appType = appType;
		this.appExplain = appExplain;
		this.appIcon = appIcon;
		this.returnUrl = returnUrl;
		this.officiaUrl = officiaUrl;
		this.clientId = clientId;
		this.clientScrect = clientScrect;
		this.checkTime = checkTime;
		this.checkUsers = checkUsers;
		this.checkRemark = checkRemark;
		this.maid = maid;
		this.pddPid = pddPid;
		this.tbPid = tbPid;
		this.jdPid = jdPid;
		this.wphPid = wphPid;
		this.money = money;
		this.users = users;
		this.isValid = isValid;
		this.createTime = createTime;
		this.isStop = isStop;
		this.checkIsPass = checkIsPass;
		this.openAuthentication = openAuthentication;
		this.hMoney = hMoney;
		this.domain = domain;
		this.pddifys = pddifys;
		this.rate = rate;
		
	}
	
	
	
	public String getPddifys() {
		return pddifys;
	}
	public void setPddifys(String pddifys) {
		this.pddifys = pddifys;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getAppExplain() {
		return appExplain;
	}
	public void setAppExplain(String appExplain) {
		this.appExplain = appExplain;
	}
	public String getAppIcon() {
		return appIcon;
	}
	public void setAppIcon(String appIcon) {
		this.appIcon = appIcon;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getOfficiaUrl() {
		return officiaUrl;
	}
	public void setOfficiaUrl(String officiaUrl) {
		this.officiaUrl = officiaUrl;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientScrect() {
		return clientScrect;
	}
	public void setClientScrect(String clientScrect) {
		this.clientScrect = clientScrect;
	}
	public Timestamp getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Timestamp checkTime) {
		this.checkTime = checkTime;
	}
	public Integer getCheckUsers() {
		return checkUsers;
	}
	public void setCheckUsers(Integer checkUsers) {
		this.checkUsers = checkUsers;
	}
	public String getCheckRemark() {
		return checkRemark;
	}
	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}
	public String getMaid() {
		return maid;
	}
	public void setMaid(String maid) {
		this.maid = maid;
	}
	public String getPddPid() {
		return pddPid;
	}
	public void setPddPid(String pddPid) {
		this.pddPid = pddPid;
	}
	public String getTbPid() {
		return tbPid;
	}
	public void setTbPid(String tbPid) {
		this.tbPid = tbPid;
	}
	public String getJdPid() {
		return jdPid;
	}
	public void setJdPid(String jdPid) {
		this.jdPid = jdPid;
	}
	public String getWphPid() {
		return wphPid;
	}
	public void setWphPid(String wphPid) {
		this.wphPid = wphPid;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	public boolean getIsValid() {
		return isValid;
	}
	public void setIsValid(boolean isValid) {
		this.isValid = isValid;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public boolean getIsStop() {
		return isStop;
	}
	public void setIsStop(boolean isStop) {
		this.isStop = isStop;
	}
	public Integer getCheckIsPass() {
		return checkIsPass;
	}
	public void setCheckIsPass(Integer checkIsPass) {
		this.checkIsPass = checkIsPass;
	}
	public OpenAuthentication getOpenAuthentication() {
		return openAuthentication;
	}
	public void setOpenAuthentication(OpenAuthentication openAuthentication) {
		this.openAuthentication = openAuthentication;
	}
	public double gethMoney() {
		return hMoney;
	}
	public void sethMoney(double hMoney) {
		this.hMoney = hMoney;
	}
	
	


}