package com.axp.domain;

import java.sql.Timestamp;
import java.util.Set;

/**
 * Users entity. @author MyEclipse Persistence Tools
 */
public class Users extends AbstractUsers implements java.io.Serializable {

	// Constructors

	public Users(){
		
	}
	
	
	
	public Users(Integer id, AdminUser adminUser, Seller parentSeller,
			Levels levels, String loginname, String name, String phone,
			String pwd, Boolean isvalid, Boolean isVisitor,
			Timestamp createtime, Timestamp lasttime, String address,
			Integer score, String invitecode, Double savemoney,
			String realname, Timestamp birthday, Integer sex, Double money,
			String lat, String lng, Integer proxyzoneId, Integer proxyId,
			String mycode, Integer level, Boolean islogin, String channelid,
			String userid, String devicetoken, String ccpsubaccountid,
			String ccpsubaccountpwd, String ccpvoipaccountid,
			Boolean isChangeAgreement, Integer jphScore, String unionId,
			Timestamp signCalcTime, Integer isSignRemaid, Integer continueDays,
			String gzhOpenId, String ccpvoipaccountpwd, String ccpcallsid,
			Double cashPoints, String imgUrl, Integer visitorId, String sign,
			Integer version, Double redPaperSum, String headimage,
			Set messagesesForFromUserId, Set goodsAdversettingses,
			Set userrecores, Set wxUserses, Set feeRecordsesForUserId,
			Set messagesesForUserId, Set scoreExchangeses,
			Set inviteRecordsesForUserId, Set voucherRecords,
			Set cashpointsRecords, Set proxyinfoses, Set extendResultses,
			Set applies, Set userProfitses, Set orderses, Set scorerecordses,
			Set inviteRecordsesForInviteUserId, Set userDrawCashRecords,
			Set feeRecordsesForOpUserId, Set plans, Set proxyuserses,
			Set userSettingses, Set userCashshopRecords, Set machines,
			Set sellers, Set userScoretypeses, Set promoters,
			Set<SellerMallShoppingCar> sellerMallShoppingCar, Integer sellerId,
			Boolean isSeller, String openId, OrderMessageList ordermessageList) {
		super(id, adminUser, parentSeller, levels, loginname, name, phone, pwd,
				isvalid, isVisitor, createtime, lasttime, address, score, invitecode,
				savemoney, realname, birthday, sex, money, lat, lng, proxyzoneId,
				proxyId, mycode, level, islogin, channelid, userid, devicetoken,
				ccpsubaccountid, ccpsubaccountpwd, ccpvoipaccountid, isChangeAgreement,
				jphScore, unionId, signCalcTime, isSignRemaid, continueDays, gzhOpenId,
				ccpvoipaccountpwd, ccpcallsid, cashPoints, imgUrl, visitorId, sign,
				version, redPaperSum, headimage, messagesesForFromUserId,
				goodsAdversettingses, userrecores, wxUserses, feeRecordsesForUserId,
				messagesesForUserId, scoreExchangeses, inviteRecordsesForUserId,
				voucherRecords, cashpointsRecords, proxyinfoses, extendResultses,
				applies, userProfitses, orderses, scorerecordses,
				inviteRecordsesForInviteUserId, userDrawCashRecords,
				feeRecordsesForOpUserId, plans, proxyuserses, userSettingses,
				userCashshopRecords, machines, sellers, userScoretypeses, promoters,
				sellerMallShoppingCar, sellerId, isSeller, openId, ordermessageList);
		// TODO Auto-generated constructor stub
	}



	/** minimal constructor */
	public Users(String loginname,String name, String pwd, Boolean isvalid, Timestamp createtime, Integer score, String invitecode,Integer jphScore,String unionId
			,Timestamp signCalcTime,Boolean isSignRemaid,Integer continueDays) {
		super(loginname,name, pwd, isvalid, createtime, score, invitecode,jphScore,unionId,signCalcTime, isSignRemaid, continueDays);
	}

	public Users(String loginname, String name, String pwd, Boolean isvalid,
			Timestamp createtime, Integer score, String invitecode,
			Integer jphScore, String unionId) {
		super(loginname, name, pwd, isvalid, createtime, score, invitecode, jphScore,
				unionId);
		// TODO Auto-generated constructor stub
	}

	public Users(Integer id){
		super(id);
	}

	public Users(AdminUser adminUser, Levels levels, String loginname,
			String name, String phone, String pwd, Boolean isvalid,
			Boolean isVisitor, Timestamp createtime, Timestamp lasttime,
			String address, Integer score, String invitecode, Double savemoney,
			String realname, Timestamp birthday, Integer sex, Double money,
			String lat, String lng, Integer proxyzoneId, Integer proxyId,
			String mycode, Integer level, Boolean islogin, String channelid,
			String userid, String ccpsubaccountid, String ccpsubaccountpwd,
			String ccpvoipaccountid, String ccpvoipaccountpwd,
			String ccpcallsid, Double cashPoints, String sign,
			Integer jphScore, Set messagesesForFromUserId,
			Set goodsAdversettingses, Set userrecores, Set wxUserses,
			Set feeRecordsesForUserId, Set messagesesForUserId,
			Set scoreExchangeses, Set inviteRecordsesForUserId,
			Set voucherRecords, Set cashpointsRecords, Set proxyinfoses,
			Set extendResultses, Set applies, Set userProfitses, Set orderses,
			Set scorerecordses, Set inviteRecordsesForInviteUserId,
			Set userDrawCashRecords, Set feeRecordsesForOpUserId, Set plans,
			Set proxyuserses, Set userSettingses, Set userCashshopRecords,
			Set machines, Set sellers, Set userScoretypeses) {
		super(adminUser, levels, loginname, name, phone, pwd, isvalid, isVisitor,
				createtime, lasttime, address, score, invitecode, savemoney, realname,
				birthday, sex, money, lat, lng, proxyzoneId, proxyId, mycode, level,
				islogin, channelid, userid, ccpsubaccountid, ccpsubaccountpwd,
				ccpvoipaccountid, ccpvoipaccountpwd, ccpcallsid, cashPoints, sign,
				jphScore, messagesesForFromUserId, goodsAdversettingses, userrecores,
				wxUserses, feeRecordsesForUserId, messagesesForUserId,
				scoreExchangeses, inviteRecordsesForUserId, voucherRecords,
				cashpointsRecords, proxyinfoses, extendResultses, applies,
				userProfitses, orderses, scorerecordses,
				inviteRecordsesForInviteUserId, userDrawCashRecords,
				feeRecordsesForOpUserId, plans, proxyuserses, userSettingses,
				userCashshopRecords, machines, sellers, userScoretypeses);
		// TODO Auto-generated constructor stub
	}



	public Users(Integer id, AdminUser adminUser, Seller parentSeller,
			Levels levels, String loginname, String name, String phone,
			String pwd, Boolean isvalid, Boolean isVisitor,
			Timestamp createtime, Timestamp lasttime, String address,
			Integer score, String invitecode, Double savemoney,
			String realname, Timestamp birthday, Integer sex, Double money,
			String lat, String lng, Integer proxyzoneId, Integer proxyId,
			String mycode, Integer level, Boolean islogin, String channelid,
			String userid, String devicetoken, String ccpsubaccountid,
			String ccpsubaccountpwd, String ccpvoipaccountid,
			Boolean isChangeAgreement, Integer jphScore, String unionId,
			Timestamp signCalcTime, Integer isSignRemaid, Integer continueDays,
			String gzhOpenId, String xcxOpenId, String ccpvoipaccountpwd,
			String ccpcallsid, Double cashPoints, String imgUrl,
			Integer visitorId, String sign, Integer version,
			Double redPaperSum, String headimage, Set messagesesForFromUserId,
			Set goodsAdversettingses, Set userrecores, Set wxUserses,
			Set feeRecordsesForUserId, Set messagesesForUserId,
			Set scoreExchangeses, Set inviteRecordsesForUserId,
			Set voucherRecords, Set cashpointsRecords, Set proxyinfoses,
			Set extendResultses, Set applies, Set userProfitses, Set orderses,
			Set scorerecordses, Set inviteRecordsesForInviteUserId,
			Set userDrawCashRecords, Set feeRecordsesForOpUserId, Set plans,
			Set proxyuserses, Set userSettingses, Set userCashshopRecords,
			Set machines, Set sellers, Set userScoretypeses, Set promoters,
			Set<SellerMallShoppingCar> sellerMallShoppingCar, Integer sellerId,
			Boolean isSeller, String openId, OrderMessageList ordermessageList) {
		super(id, adminUser, parentSeller, levels, loginname, name, phone, pwd,
				isvalid, isVisitor, createtime, lasttime, address, score, invitecode,
				savemoney, realname, birthday, sex, money, lat, lng, proxyzoneId,
				proxyId, mycode, level, islogin, channelid, userid, devicetoken,
				ccpsubaccountid, ccpsubaccountpwd, ccpvoipaccountid, isChangeAgreement,
				jphScore, unionId, signCalcTime, isSignRemaid, continueDays, gzhOpenId,
				xcxOpenId, ccpvoipaccountpwd, ccpcallsid, cashPoints, imgUrl,
				visitorId, sign, version, redPaperSum, headimage,
				messagesesForFromUserId, goodsAdversettingses, userrecores, wxUserses,
				feeRecordsesForUserId, messagesesForUserId, scoreExchangeses,
				inviteRecordsesForUserId, voucherRecords, cashpointsRecords,
				proxyinfoses, extendResultses, applies, userProfitses, orderses,
				scorerecordses, inviteRecordsesForInviteUserId, userDrawCashRecords,
				feeRecordsesForOpUserId, plans, proxyuserses, userSettingses,
				userCashshopRecords, machines, sellers, userScoretypeses, promoters,
				sellerMallShoppingCar, sellerId, isSeller, openId, ordermessageList);
		// TODO Auto-generated constructor stub
	}





	
	


}
