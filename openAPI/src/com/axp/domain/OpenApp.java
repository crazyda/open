package com.axp.domain;

import java.sql.Timestamp;

/**
 * OpenClient entity. @author MyEclipse Persistence Tools
 */
public class OpenApp extends AbstractOpenApp implements
		java.io.Serializable {

	public OpenApp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OpenApp(Integer id, String appName, String appType,
			String appExplain, String appIcon, String returnUrl,
			String officiaUrl, String clientId, String clientScrect,
			Timestamp checkTime, Integer checkUsers, String checkRemark,
			String maid, String pddPid, String tbPid, String jdPid,
			String wphPid, double money, Users users, boolean isValid,
			Timestamp createTime, boolean isStop, Integer checkIsPass,
			OpenAuthentication openAuthentication, double hMoney,String domain,String pddifys,String rate) {
		super(id, appName, appType, appExplain, appIcon, returnUrl, officiaUrl,
				clientId, clientScrect, checkTime, checkUsers, checkRemark, maid,
				pddPid, tbPid, jdPid, wphPid, money, users, isValid, createTime,
				isStop, checkIsPass, openAuthentication, hMoney,domain,pddifys,rate);
		// TODO Auto-generated constructor stub
	}

	
}
