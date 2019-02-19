package com.axp.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONObject;
import com.alipay.bean.AlipaySign;
import com.axp.dao.IMembersDao;
import com.axp.domain.AdminUser;
import com.axp.domain.CashmoneyRecord;
import com.axp.domain.Members;
import com.axp.domain.MembersConfig;
import com.axp.domain.Users;
import com.axp.service.IFreeVoucherService;
import com.axp.service.IMembersService;
import com.axp.service.IOrderPayService;
import com.axp.util.DateUtil;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.weixin.bean.PayParameter;
import com.weixin.config.WeixinConfig;
import com.weixin.util.WeixinUtil;

@Service
public class MembersServiceImpl extends BaseServiceImpl<MembersConfig> implements IMembersService {
	
	@Autowired
	IOrderPayService orderPayService;
	@Autowired
	IMembersDao membersDao;
	@Autowired
	IFreeVoucherService freeVoucherService;
	
	@Override
	public List<MembersConfig> getVipConfig(){
		return membersConfigDao.getVipConfigList();
	}
	
	@Override
	public Map<String, Object> confirmVip(Integer userId, Integer vipConfigId, Double realMoney,
			String accountCode, Integer accountType){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Users user = usersDao.findById(userId);
			MembersConfig vipConfig = membersConfigDao.findById(vipConfigId);
			Members au = membersDao.findByUserId(user.getId(), vipConfig.getId());
			if(au!=null&&au.getIsActivate()){
				map.put("status", 0x01);
				map.put("message", "已经成功注册，不可重复申请");
				return map;
			}
			membersDao.confirmMember(user, vipConfig, realMoney, accountCode, accountType);
			
			//推荐人分佣
			if(au!=null&& StringUtils.isNotBlank(au.getInviteCode())){
				List<Users> auserlist = usersDao.findUsersByInvitecode(au.getInviteCode());
				if(auserlist.size()>0){
					Users users = auserlist.get(0);//推荐人
					addMoneyForUsers(users,user,0);
					users.setMoney((users.getMoney()==null?0.0d:users.getMoney())+20);
				
				}
			}
			//赠送免单券
			freeVoucherService.addMembersVoucher(user, 1, 3);
			map.put("status", 0x01);
			map.put("message", "注册成功");
		}catch(Exception e){
			e.printStackTrace();
			map.put("status", -0x01);
			map.put("message", e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return map;
	}
	
	
	private void addMoneyForUsers(Users user,Users from,Integer orderid){
		CashmoneyRecord cr = new CashmoneyRecord();
		cr.setBeforeMoney(0d);
		cr.setAfterMoney(0d);
		cr.setMoney(20d);
		cr.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
		cr.setIsValid(true);
		cr.setUsersByUserId(user);
		cr.setUsersByFromUsers(from);
		cr.setOrderString("members");
		cr.setType(20);//确认
		cr.setRelateId(orderid);
		cr.setRemark("推荐的粉丝"+from.getRealname()+"申请为会员，获得20元奖励");
		
	}
	
	
	@Override
	public Map<String, Object> register(Integer configId,Integer userId,
			Integer payType, String inviteCode, HttpServletRequest request){
		MembersConfig config = membersConfigDao.findById(configId);
		if(config==null)
			return JsonResponseUtil.getJson(-0xb1, "会员设置不存在"); 
		Members members = membersDao.findByUserId(userId);
		if(members!=null)
			return JsonResponseUtil.getJson(-0xb2, "已是会员身份");
		
		Users user = usersDao.findById(userId);
		Parameter parameter = ParameterUtil.getParameter(request);
		String os = parameter.getOs();
	
		//保存临时会员信息
		membersDao.saveTemporaryMember(user, config, inviteCode);
		
		if(payType.toString().equals(WEIXIN)){
			return weixinBody(request, userId, config, os);
		}else if (payType.toString().equals(ALIPAY)){
			return alipayBody(request, userId, config);
		}
		
		return null;
	}
	
	private Map<String, Object> alipayBody(HttpServletRequest request, Integer userId, MembersConfig config){
		String basePath = (String)request.getServletContext().getAttribute("BASEPATH_IN_SESSION");
		Map<String, Object> statusMap = new HashMap<String, Object>();
		statusMap.put("status", 0x01);
		statusMap.put("message", "请求成功");
		
		//AlipayBody
		JSONObject object = new JSONObject();
		object.put("userId", userId);
		object.put("configId", config.getId());
		object.put("fee", config.getPayMoney());
		
		// 回调接口url
		String alipayNotify = basePath +"invoke/users/alipayNotify";
		//支付宝签名对象封装信息
		AlipaySign sign = new AlipaySign();
		sign.setGoodName("用户"+userId+"注册会员订单");
		sign.setNotifyUrl(alipayNotify);
		sign.setTotalFee(config.getPayMoney());
		sign.setBody(object.toJSONString());
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		statusMap.put("data", dataMap);
		dataMap.put("sign", sign.getSign());
		
		sign = null;
		
		return statusMap;
	}
	
	private Map<String, Object> weixinBody(HttpServletRequest request, Integer userId,MembersConfig config, String os){
		String basePath = (String)request.getServletContext().getAttribute("BASEPATH_IN_SESSION");
		Map<String, Object> statusMap = new HashMap<String, Object>();
		statusMap.put("status", 0x01);
		statusMap.put("message", "请求成功");
		
		String orderCode = DateUtil.formatDate("yyyyMMddHHmmssss", DateUtil.getNow());
		orderCode = orderCode + new Random().nextInt(100);
		
		JSONObject object = new JSONObject();
		object.put("userId", userId);
		object.put("configId", config.getId());
		object.put("orderCode", orderCode);
		object.put("fee", config.getPayMoney());
		String orderjson = object.toJSONString();
		String notify = basePath + "invoke/users/weixinNotify";
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		statusMap.put("data", dataMap);
		
		PayParameter payParameter = orderPayService.returnParameter(request, notify, orderjson, orderCode, config.getPayMoney()+"");
		
		String prrepayId = WeixinUtil.getPrepayId(payParameter);
		long timeStamp = new Date().getTime();
		if("IOS".equals(os)){
			timeStamp=Long.parseLong(String.valueOf(new Date().getTime()).toString().substring(0,10));		
		}else{
			timeStamp = new Date().getTime();
		}
		
		dataMap.put("prepayId", prrepayId);
		dataMap.put("sign", WeixinUtil.getPaySign(payParameter,prrepayId,timeStamp));
		dataMap.put("partnerid", WeixinConfig.mch_id);
		dataMap.put("appId", WeixinConfig.appid);
		dataMap.put("nonceStr", payParameter.getNonce_str());
		dataMap.put("timeStamp",timeStamp);
		dataMap.put("package","Sign=WXPay");
		
		return statusMap;
	}

}