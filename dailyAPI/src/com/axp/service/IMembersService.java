package com.axp.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.axp.domain.MembersConfig;

public interface IMembersService extends IBaseService<MembersConfig>{
	
	public static final String WALLET = "100";//钱包
	public static final String ALIPAY = "200";//支付宝
	public static final String WEIXIN = "300";//微信

	Map<String, Object> confirmVip(Integer userId, Integer vipConfigId, Double realMoney,
			String accountCode, Integer accountType);

	List<MembersConfig> getVipConfig();

	Map<String, Object> register(Integer configId, Integer userId,
			Integer payType, String inviteCode, HttpServletRequest request);


}