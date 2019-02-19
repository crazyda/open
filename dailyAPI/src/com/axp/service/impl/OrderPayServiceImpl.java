package com.axp.service.impl;

import java.io.BufferedOutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.bean.AlipaySign;
import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayNotify;
import com.alipay.util.AlipayPayUtils;
import com.alipay.util.httpClient.HttpRequest;
import com.axp.dao.AdminUserDAO;
import com.axp.dao.AdminuserCashpointRecordDAO;
import com.axp.dao.BonusDAO;
import com.axp.dao.CommodityTypeDAO;
import com.axp.dao.DLScoreMarkDAO;
import com.axp.dao.DateBaseDAO;
import com.axp.dao.IAliMessageDao;
import com.axp.dao.ICashmoneyRecordDao;
import com.axp.dao.ICashpointsRecordDao;
import com.axp.dao.INewRedPaperAddendumDao;
import com.axp.dao.INewRedPaperLogDao;
import com.axp.dao.INewRedPaperSettingDao;
import com.axp.dao.INrpOrderLogDao;
import com.axp.dao.IReGoodsOfLockMallDao;
import com.axp.dao.ISellerMoneyRecordDao;
import com.axp.dao.PartnerInformDao;
import com.axp.dao.ReBaseGoodsDAO;
import com.axp.dao.ReGoodsorderItemDao;
import com.axp.dao.SJScoreMarkDAO;
import com.axp.dao.ScoreMarkDAO;
import com.axp.dao.ScorerecordsDAO;
import com.axp.dao.SellerAccountNumberDao;
import com.axp.dao.SlidesDAO;
import com.axp.dao.ThePartnerDAO;
import com.axp.dao.UsersPidDAO;
import com.axp.domain.AdminUser;
import com.axp.domain.AdminUserRecharge;
import com.axp.domain.AdminUserScoreRecord;
import com.axp.domain.AdminuserCashpointRecord;
import com.axp.domain.AdminuserTaokePid;
import com.axp.domain.Bonus;
import com.axp.domain.CashmoneyRecord;
import com.axp.domain.CashshopTimes;
import com.axp.domain.DLScoreMark;
import com.axp.domain.NewRedPaperAddendum;
import com.axp.domain.NewRedPaperLog;
import com.axp.domain.NrpOrderLog;
import com.axp.domain.PartnerAdminuserPidDistribute;
import com.axp.domain.PartnerInform;
import com.axp.domain.ProvinceEnum;
import com.axp.domain.ReBaseGoods;
import com.axp.domain.ReGoodsOfLocalSpecialtyMall;
import com.axp.domain.ReGoodsOfLockMall;
import com.axp.domain.ReGoodsOfScoreMall;
import com.axp.domain.ReGoodsOfSeckillMall;
import com.axp.domain.ReGoodsOfSellerMall;
import com.axp.domain.ReGoodsOfTeamMall;
import com.axp.domain.ReGoodsorder;
import com.axp.domain.ReGoodsorderItem;
import com.axp.domain.SJScoreMark;
import com.axp.domain.ScoreMark;
import com.axp.domain.Scorerecords;
import com.axp.domain.Seller;
import com.axp.domain.ThePartner;
import com.axp.domain.TkldPid;
import com.axp.domain.UserCoupons;
import com.axp.domain.UserLoginRecord;
import com.axp.domain.UserScoreMark;
import com.axp.domain.Users;
import com.axp.domain.UsersMoneyRecord;
import com.axp.domain.VisitLog;
import com.axp.service.AliSendMessageService;
import com.axp.service.IOrderPayService;
import com.axp.service.IOrderService;
import com.axp.service.IReGoodsOfBaseService;
import com.axp.service.IReGoodsOfLockMallService;
import com.axp.service.IntegralService;
import com.axp.service.ScoreMarkService;
import com.axp.service.TkldPidService;
import com.axp.service.UserCouponsService;
import com.axp.service.UserSystemMessageService;
import com.axp.util.AnalyzeMallUtil;
import com.axp.util.AxpMessageUtil;
import com.axp.util.CalcUtil;
import com.axp.util.DateUtil;
import com.axp.util.JsonResponseUtil;
import com.axp.util.MD5Util;
import com.axp.util.OrderUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;
import com.axp.util.StringUtil;
import com.axp.util.SystemUtil;
import com.axp.util.UrlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.weixin.bean.OrderInfo;
import com.weixin.bean.OrderReturnInfo;
import com.weixin.bean.PayParameter;
import com.weixin.bean.SignInfo;
import com.weixin.config.WeixinConfig;
import com.weixin.util.PayUtil;
import com.weixin.util.WeixinUtil;
import com.weixin.util.XmlData;
import com.yilian.Constants;
import com.yilian.payeco.client.ConstantsClient;
import com.yilian.payeco.client.TransactionClient;
import com.yilian.payeco.client.TransactionClientSdk;
import com.yilian.payeco.tools.Base64;
import com.yilian.payeco.tools.Log;
import com.yilian.payeco.tools.Tools;
import com.yilian.payeco.tools.Xml;

@Service("orderPayService")
public class OrderPayServiceImpl extends BaseServiceImpl<Users> implements IOrderPayService {
	@Autowired
	public SlidesDAO slidesDao;
	@Autowired
	public CommodityTypeDAO commodityTypeDao;
	@Autowired
	public AnalyzeMallUtil analyzeMallUtil;
	@Autowired 
	public ICashmoneyRecordDao cashmoneyRecordDao;
	@Autowired
	public ICashpointsRecordDao cashpointsRecordDao;
	@Autowired
	public ISellerMoneyRecordDao sellerMoneyRecordDao; 
	@Autowired
	public IReGoodsOfBaseService goodsService;
	@Autowired
	public ReBaseGoodsDAO reBaseGoodsDao;
	@Autowired
	public INewRedPaperLogDao nrplDao;
	@Autowired
	public INewRedPaperAddendumDao nrpaDao;
	@Autowired
	public INewRedPaperSettingDao nrpsDao;
	@Autowired
	public INrpOrderLogDao nrpOrderLogDao;
	@Autowired
	public AdminuserCashpointRecordDAO adminuserCashpointRecordDAO;
	@Autowired
	public SellerAccountNumberDao sellerAccountNumberDao;
	@Autowired
	public IOrderService orderService;
	@Autowired
	public ThePartnerDAO thePartnerDao;
	
	@Autowired
	public UsersPidDAO usersPidDao;
	
	@Resource
	UserSystemMessageService userSystemMessageService;

	@Autowired
	public  PartnerInformDao partnerInformDao;
	@Autowired
	public UserCouponsService userCouponsService;
	@Resource
	AdminUserDAO adminUserDAO;
    @Autowired
    protected DateBaseDAO dateBaseDao;
    
    @Autowired
    public ReGoodsorderItemDao reGoodsorderItemDao;
    
    @Autowired
	IAliMessageDao aliMessageDao;
	@Autowired
	AliSendMessageService aliSendMessageService;
	
	@Autowired
	TkldPidService tkldPidService;
	
	@Autowired
	ScoreMarkDAO scoreMarkDao;
	
	@Autowired
	ScoreMarkService scoreMarkService;
	@Autowired
	BonusDAO bonusDao;
	@Autowired
	IReGoodsOfLockMallService reGoodsOfLockMallService ;
	@Autowired
	IReGoodsOfLockMallDao lockMallDao;
	@Autowired
	DLScoreMarkDAO dlScoreMarkDAO;
	@Autowired
	SJScoreMarkDAO sjScoreMarkDAO;
	@Autowired
	IntegralService integeralService;
	
    protected UrlUtil urlUtil;
    
	public Map<String, Object> getYiLianSign(HttpServletRequest request,
			HttpServletResponse response) {

		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {// 错误的参数；
			return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
		}
		String zoneId = parameter.getZoneId();
		JSONObject object = parameter.getData();
		Integer type = object.getInteger("type")==null?0:object.getInteger("type"); // 是否合并支付 1:0
		Integer userId = Integer.parseInt(parameter.getUserId());
		object.put("userId", userId);
		Map<String, Object> statusMap = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<>();
		statusMap.put("data", dataMap);
		
		StringBuffer orderIds = new StringBuffer();
		JSONArray array = JSONArray.parseArray(object.getString("orderIds"));
		for (int i = 0; i < array.size(); i++) {
			String order= array.getString(i);
			if(order.length()>10){
				order=order.substring(5, order.length()-5);
			}
			if(i==(CalcUtil.sub(array.size(), 1))){
				orderIds.append(order);
			}else{
				orderIds.append(order+",");
			}
		}

		// 检查商品状态
		Map<String, Object> checkMap = checkOrderBeforePay(request,
				orderIds.toString(), userId);
		if (Integer.parseInt(checkMap.get("status").toString()) < 0x01) {
			return checkMap;
		}

		List<ReGoodsorder> orders = reGoodsorderDao.getToBePaidOrders(userId,
				orderIds.toString());
		if (orders.size() < orderIds.toString().split(",").length) {
			statusMap.put("status", -0xa6);
			statusMap.put("message", "请求队列中存在已支付或未确认订单");
			return statusMap;
		}
		
		Users users = usersDao.findById(userId);
		
		Double totalPrice = 0.00;
		StringBuffer desc=new StringBuffer();
		for (ReGoodsorder order : orders) {
			// totalPrice = totalPrice + order.getPayPrice();
			// totalPrice = totalPrice + order.getLogisticsType();//邮费
			totalPrice = CalcUtil.add(totalPrice, order.getPayPrice(), 2);
			totalPrice = CalcUtil.add(totalPrice, order.getLogisticsType(), 2);// 邮费
			List<ReGoodsorderItem> items = reGoodsorderItemDao.findByPropertyIsValid("order.id", order.getId());
			for (int i = 0; i < items.size(); i++) {
				if (i == (CalcUtil.sub(items.size(), 1))) {
					desc.append(items.get(i).getGoodName());
				} else {
					desc.append(items.get(i).getGoodName()+"-");
				}
			}

		}
		
		
		if (type != null && type == 1) { // 合并支付
			
			if (users.getMoney() > 0 && users.getMoney() <= totalPrice) {
				totalPrice = CalcUtil.sub(totalPrice, users.getMoney()); //
				if (totalPrice <= 0) {
					statusMap.put("status", -3);
					statusMap.put("message", "钱包余额充足,请使用钱包支付");
					return statusMap;
				}
				
			}
		}
		object.put("type", type);
		object.put("totalPrice", totalPrice);
		object.put("zoneId", zoneId);
		String amount = totalPrice.toString(); //商户订单金额 必须
	
		String orderDesc=desc.toString(); 										
		String extData =object.toJSONString(); //扩展数据 通知结果时,自定义内容 原样返回
		String miscData ="||||||||"; // 以下扩展参数是按互联网金融行业填写的；其他行业请参考接口文件说明进行填写 可以不填!!!!
		if (Tools.checkAmount(amount) == false) { // 验证金额准确性 两位小数
			statusMap.put("RetCode", "E105");
			statusMap.put("RetMsg", "金额格式错!");
			return statusMap;
		}

		
		String merchOrderId = orderIds.toString(); //订单号
		String merchantId = Constants.MERCHANT_ID; // 必须 商户代码 正式要该
		String notifyUrl = Constants.MERCHANT_NOTIFY_URL; // 异步回调路径
		String tradeTime = Tools.getSysTime(); // 必须 提交时间
		String expTime =""; // 采用系统默认的订单有效时间 交易超时 时间超过订单超时时间未支付，订单作废；不提交该参数，采用系统的默认时间（从接收订单后超时时间为30分钟）
		String notifyFlag = "0";// 0：成功才通知（02状态），1：全部通知（02、04、05状态）不填默认为“1：全部通知”
		
		// 调用下单接口
		Xml retXml = new Xml();

		try {
			Log.setLogFlag(true);
			
			Log.println("---交易： 商户下订单(SDK版本)-------------------------");
			String ret = TransactionClientSdk.MerchantOrder(merchantId,
					merchOrderId, amount, orderDesc, tradeTime, expTime,
					notifyUrl, extData, miscData, notifyFlag,
					Constants.MERCHANT_RSA_PRIVATE_KEY,
					Constants.PAYECO_RSA_PUBLIC_KEY, Constants.PAYECO_URL,
					retXml);
			
			if (!"0000".equals(ret)) {
				statusMap.put("RetCode", ret);
				statusMap.put("RetMsg", "下订单接口返回错误!");
				return statusMap;
			}
		} catch (Exception e) {
			String errCode = e.getMessage();
			if ("E101".equalsIgnoreCase(errCode)) {
				statusMap.put("RetCode", "E101");
				statusMap.put("RetMsg", "下订单接口无返回数据!");
				return statusMap;
			} else if ("E102".equalsIgnoreCase(errCode)) {
				statusMap.put("RetCode", "E102");
				statusMap.put("RetMsg", "验证签名失败!");
				return statusMap;
			} else if ("E103".equalsIgnoreCase(errCode)) {
				statusMap.put("RetCode", "E103");
				statusMap.put("RetMsg", "进行订单签名失败!");
				return statusMap;
			} else {
				statusMap.put("RetCode", "E100");
				statusMap.put("RetMsg", "下订单通讯失败!");
				return statusMap;
			}
		}

		// 设置返回给手机Json数据
		dataMap.put("Version", retXml.getVersion());
		dataMap.put("MerchOrderId", retXml.getMerchOrderId());
		dataMap.put("MerchantId", retXml.getMerchantId());
		dataMap.put("Amount", retXml.getAmount());
		dataMap.put("TradeTime", retXml.getTradeTime());
		dataMap.put("OrderId", retXml.getOrderId());  //易联订单id
		
		
		
		
		
		
		dataMap.put("Sign", retXml.getSign());
		dataMap.put("ExtData",retXml.getExtData());
		statusMap.put("status", 1);
		statusMap.put("message", "下单成功");
		return statusMap;
	} 


	/**
	 * 同步易联回调
	 */
	@Override
	public Map<String, Object> synchroYiLianNotify(HttpServletRequest request,
			HttpServletResponse response) {
		
			Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {// 错误的参数；
			return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
		}
		Map<String, Object> retMap=new HashMap<String, Object>();
		retMap.put("status", 0x01);
		retMap.put("message", "请求成功");
		
				JSONObject data = parameter.getData();
				String os = parameter.getOs();
				String version=data.getString("Version")==null?"":data.getString("Version");
				String merchantId=data.getString("MerchantId")==null?"":data.getString("MerchantId");
				String merchOrderId=data.getString("MerchOrderId")==null?"":data.getString("MerchOrderId");
				String amount=data.getString("Amount")==null?"":data.getString("Amount");
				String extData=data.getString("ExtData")==null?"":data.getString("ExtData");
				String orderId=data.getString("OrderId")==null?"":data.getString("OrderId");
				String status=data.getString("Status")==null?"":data.getString("Status");
				String payTime=data.getString("PayTime")==null?"":data.getString("PayTime");
				String settleDate=data.getString("SettleDate")==null?"":data.getString("SettleDate");
				String sign=data.getString("Sign")==null?"":data.getString("Sign");
				
				try {
					
					Log.setLogFlag(true);
					Log.println("---交易： 订单结果同步通知-------------------------");
					//验证订单结果通知的签名
					boolean b = TransactionClient.bCheckNotifySign(version, merchantId, merchOrderId, 
							amount, extData, orderId, status, payTime, settleDate, sign, 
							Constants.PAYECO_RSA_PUBLIC_KEY);
					if (!b) {
						System.out.println("===============================");
						System.out.println("验证签名失败!");
						System.out.println("===============================");
						retMap.put("status", -1);
						retMap.put("message", "验证签名失败");
						return retMap;
					
					}else{
						// 签名验证成功后，需要对订单进行后续处理
						
						if ("02".equals(status)) { // 订单已支付; 订单支付成功的业务逻辑处理请在本处增加（订单通知可能存在多次通知的情况，需要做多次通知的兼容处理）；
						System.out.println("===============================");
							System.out.println("签名验证成功!");
						System.out.println("===============================");
							extData = new String(Base64.decode(extData), ConstantsClient.PAYECO_DATA_ENCODE);
							JSONObject extDateJson = JSONObject.parseObject(extData);
							Integer userId = Integer.parseInt(extDateJson.getString("userId"));
							Integer type=extDateJson.getInteger("type"); // 是否合并支付    1:0
							String zoneId = extDateJson.getString("zoneId");
							StringBuffer orderIds = new StringBuffer();
							JSONArray array = JSONArray.parseArray(extDateJson.getString("orderIds"));
							for(int i=0;i<array.size();i++){
								String order= array.getString(i);
								if(order.length()>10){
									order=order.substring(5, order.length()-5);
								}
								if(i==(CalcUtil.sub(array.size(), 1))){
									orderIds.append(order);
								}else{
									orderIds.append(order+",");
								}
							}
							
							Double total_fee = Double.parseDouble(amount);
							Integer totalfee =(int)(total_fee*100);//用于保存原始记录单位分
							String transaction_id= orderId;//微信支付宝易联支付订单号
							String timeEnd = payTime;// params.get("gmt_payment");//支付时间
							String openId="";
							String outTradeNo=merchOrderId; //商户订单号
								retMap=this.payOrdersMerge(request, userId, orderIds.toString(), "YILIAN", total_fee, transaction_id,timeEnd,openId,totalfee, outTradeNo,type,0,os,zoneId);
							if(Integer.parseInt(retMap.get("status").toString())!=1){
								retMap.put("status", -3);
								retMap.put("message", "订单业务处理失败");
								return retMap;
							}
							
							
						} else {
							// 1、订单支付失败的业务逻辑处理请在本处增加（订单通知可能存在多次通知的情况，需要做多次通知的兼容处理，避免成功后又修改为失败）；
							// 2、返回响应内容
							
							retMap.put("status", -4);
							retMap.put("message", "订单业务处理失败2");
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							return retMap;
						}
					}
				} catch (Exception e) {
				System.out.println("===============================");
				System.out.println("处理通知结果异常!e="+e.getMessage());
				System.out.println("===============================");
				retMap.put("status", -2);
				retMap.put("message", "处理通知结果异常");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return retMap;
				}
				//返回数据
				
		return retMap;
	}


	@Override
	public String payByYiLian(HttpServletRequest request,HttpServletResponse response) {
		// 结果通知参数，易联异步通知采用GET提交
				String version = request.getParameter("Version");
				String merchantId = request.getParameter("MerchantId");
				String merchOrderId = request.getParameter("MerchOrderId");
				String amount = request.getParameter("Amount");
				String extData = request.getParameter("ExtData");
				String orderId = request.getParameter("OrderId"); //易联订单id
				String status = request.getParameter("Status");
				String payTime = request.getParameter("PayTime");
				String settleDate = request.getParameter("SettleDate");
				String sign = request.getParameter("Sign");
				String os = request.getParameter("os");
				Map<String, Object> retMap=new HashMap<String, Object>();
				// 订单结果逻辑处理
				String retMsgJson = "";
				try {
					Log.setLogFlag(true);
					Log.println("---交易： 订单结果异步通知-------------------------");
					//验证订单结果通知的签名
					boolean b = TransactionClient.bCheckNotifySign(version, merchantId, merchOrderId, 
							amount, extData, orderId, status, payTime, settleDate, sign, 
							Constants.PAYECO_RSA_PUBLIC_KEY);
					if (!b) {
						retMsgJson = "{\"RetCode\":\"E101\",\"RetMsg\":\"验证签名失败!\"}";
						Log.println("验证签名失败!");
						System.out.println("===============================");
							System.out.println("验证签名失败!");
						System.out.println("===============================");
					}else{
						// 签名验证成功后，需要对订单进行后续处理
						
						if ("02".equals(status)) { // 订单已支付; 订单支付成功的业务逻辑处理请在本处增加（订单通知可能存在多次通知的情况，需要做多次通知的兼容处理）；
						System.out.println("===============================");
							System.out.println("签名验证成功!");
						System.out.println("===============================");
							extData = new String(Base64.decode(extData), ConstantsClient.PAYECO_DATA_ENCODE);
							JSONObject data = JSONObject.parseObject(extData);
							Integer userId = Integer.parseInt(data.getString("userId"));
							Integer type=data.getInteger("type"); // 是否合并支付    1:0
							String zoneId = data.getString("zoneId");
							StringBuffer orderIds = new StringBuffer();
							JSONArray array = JSONArray.parseArray(data.getString("orderIds"));
							for(int i=0;i<array.size();i++){
								String order= array.getString(i);
								if(order.length()>10){
									order=order.substring(5,order.length()-5);
								}
								
								if(i==(CalcUtil.sub(array.size(), 1))){
									orderIds.append(order);
								}else{
									orderIds.append(order+",");
								}
							}
							
							Double total_fee = Double.parseDouble(amount); 
							Integer totalfee =(int)(total_fee*100);//用于保存原始记录单位分
							String transaction_id= orderId;//微信支付宝易联支付订单号
							String timeEnd = payTime;// params.get("gmt_payment");//支付时间
							String openId="";
							String outTradeNo=merchOrderId; //商户订单号
								retMap=this.payOrdersMerge(request, userId, orderIds.toString(), "YILIAN", total_fee, transaction_id,timeEnd,openId,totalfee, outTradeNo,type,0,os,zoneId);
							if(Integer.parseInt(retMap.get("status").toString())!=1){
								return JSONObject.toJSONString(retMap);
							}
							
							
							retMsgJson = "{\"RetCode\":\"0000\",\"RetMsg\":\"订单已支付\"}";
							Log.println("订单已支付!");
						} else {
							// 1、订单支付失败的业务逻辑处理请在本处增加（订单通知可能存在多次通知的情况，需要做多次通知的兼容处理，避免成功后又修改为失败）；
							// 2、返回响应内容
							retMsgJson = "{\"RetCode\":\"E102\",\"RetMsg\":\"订单支付失败+"+status+"\"}";
							Log.println("订单支付失败!status="+status);
						}
					}
				} catch (Exception e) {
					retMsgJson = "{\"RetCode\":\"E103\",\"RetMsg\":\"处理通知结果异常\"}";
					System.out.println("处理通知结果异常!e="+e.getMessage());
					
					e.printStackTrace();
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				}
				//返回数据
				return retMsgJson;
	}
	
	
	
    
	/**
	 * 支付宝回调接口
	 */
	@Override
	public String payByAlipay(HttpServletRequest request, HttpServletResponse response) {
		try{
			
			//获取支付宝POST过来反馈信息
			Map<String,String> params = new HashMap<String,String>();
			Map<String,String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
				params.put(name, valueStr);
			}
		
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//商户订单号

			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//支付宝交易号

			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

			
			boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ali_public_key, "UTF-8",AlipayConfig.sign_type);
			
			if (verify_result) {//验证签名
			if (trade_status.equals("TRADE_FINISHED")) {
					System.out.println("签名成功");
					return "success";
				} else if (trade_status.equals("TRADE_SUCCESS")) {//支付成功
					System.out.println("支付成功");
					String body = params.get("body");
					JSONObject data = JSONObject.parseObject(body);
					
					
					Integer userId = data.getInteger("userId");
					
					Integer type=data.getInteger("type")==null?0:data.getInteger("type"); // 是否合并支付    1:0
					String zoneId = data.getString("zoneId");
					StringBuffer orderIds = new StringBuffer();
					JSONArray array = JSONArray.parseArray(data.getString("orderIds"));
					
					
					
					for(int i=0;i<array.size();i++){
						String order= array.getString(i);
						if(order.length()>10){
							order=order.substring(5,order.length()-5);
							
						}
						if(i==(CalcUtil.sub(array.size(), 1))){
							orderIds.append(order);
						}else{
							orderIds.append(order+",");
						}
					}
					
					Double total_fee = Double.parseDouble(params.get("total_amount"));
					Integer totalfee =(int)(total_fee*100);//用于保存原始记录单位分
					String transaction_id= params.get("trade_no");//微信支付订单号
					String timeEnd = params.get("gmt_payment");//支付时间
					String openId=params.get("buyer_id");//用户openid
					String outTradeNo= params.get("out_trade_no");
					String os = request.getParameter("os");
					 JSONObject.toJSONString(this.payOrdersMerge(request, userId, orderIds.toString(), "ALIPAY", total_fee, transaction_id,timeEnd,openId,totalfee, outTradeNo,type,0,os,zoneId));
					return "success";

				} else {//验证失败
					System.out.println("验证失败1");
					return "fail";
				}
			}else{
				System.out.println("验证失败2");
				return "fail";
			}
		}catch(Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "fail";
		}
		
		
	}
	
	/**
	 * 微信回调接口
	 */
	@Override
	public String payByWeixin(HttpServletRequest request, HttpServletResponse response,Map<String, String> paramss) {
		
		
		XmlData xml = new XmlData();
		try{
			
			Map<String, String> params = paramss;
			
			String return_code = params.get("return_code");
			String sign = params.get("sign");
			Double totalFee = Double.parseDouble(params.get("total_fee"));
			Integer total_fee =Integer.parseInt(params.get("total_fee"));//用于保存原始记录
			String transaction_id= params.get("transaction_id");//微信支付订单号
			String timeEnd = params.get("time_end");//支付时间
			String openId=params.get("openid");//用户openid
			String outTradeNo= params.get("out_trade_no");
			String os = request.getParameter("os");
			totalFee = CalcUtil.mul(totalFee, 0.01, 2);
			String body = params.get("body");
			
			//自主生成验证签名
			String checkSign = WeixinUtil.notifySign(params);
			
			
			if (sign.equals(checkSign)) {//验证成功
				if(return_code.equals("SUCCESS")){
					JSONObject ob = JSONObject.parseObject(body);
					Integer type=ob.getInteger("type")==null?0:ob.getInteger("type"); // 是否合并支付    1:0
					String zoneId = ob.getString("zoneId");
					Integer userId = ob.getInteger("userId");
					StringBuffer orderIds = new StringBuffer();
					String ord = "";
					if(!ob.getString("orderIds").startsWith("[")){
						ord = "["+ob.getString("orderIds")+"]";
					}else{
						ord = ob.getString("orderIds");
					}
					JSONArray array = JSONArray.parseArray(ord);
					
					for(int i=0;i<array.size();i++){
						String order= array.getString(i);
						//order=order.substring(5,order.length()-5);
						if(i==(CalcUtil.sub(array.size(), 1))){
							orderIds.append(order);
						}else{
							orderIds.append(order+",");
						}
					}
					
					
					Map<String,Object> map =new HashMap<String, Object>();
					map= this.payOrdersMerge(request, userId, orderIds.toString(), "WEIXIN", totalFee, transaction_id,timeEnd,openId,total_fee, outTradeNo,type,0,os,zoneId);	
					int status = Integer.parseInt(map.get("status").toString());
					if(status==0x01){
						//===================lbr=============
						List<ReGoodsorder> orders = reGoodsorderDao.getToBePaidOrders(userId, orderIds.toString());
						QueryModel queryModel = new QueryModel();
						for (ReGoodsorder reGoodsorder : orders) {
							
							if (reGoodsorder!=null) {
								queryModel.clearQuery();
								queryModel.combPreEquals("order.id", reGoodsorder.getId(),"orderId");
								List<ReGoodsorderItem> items = dateBaseDao.findLists(ReGoodsorderItem.class, queryModel);
								if (reGoodsorder.getStatus()>5) {
									for (ReGoodsorderItem reGoodsorderItem : items) {
										urlUtil.send(reGoodsorder.getSeller().getAdminUser().getPhone(), "【每天积分】您好，"+reGoodsorderItem.getGoodName()+"用户"+reGoodsorder.getUser().getName()+"已经成功付款，订单号："+reGoodsorder.getId()+"，请尽快确认订单，进行发货！");
									  if (reGoodsorder.getIsTeam()==true ) {
										   urlUtil.send(reGoodsorder.getReGoodsorder().getUser().getName(), "【每天积分】您好，您发起的拼团订单"+reGoodsorderItem.getOrder().getOrderCode()+"已成功！");
									  }
									
									}
								}
							}
						}
						//=====================================
						xml.setProtectData("return_code", "SUCCESS");
						xml.setProtectData("return_msg", map.get("message"));
					}else if(status==-1){
						xml.setProtectData("return_code", "FAIL");
						xml.setProtectData("return_msg", map.get("message"));
						System.out.println("逻辑处理失败");
					}else{
						xml.setProtectData("return_code", "SUCCESS");
						xml.setProtectData("return_msg", map.get("message"));
						System.out.println("订单逻辑已处理");
					}
				}else{
					xml.setProtectData("return_code", "FAIL");
					xml.setProtectData("return_msg", "FAIL");
					System.out.println("结果失败");
				}
			}else{
				xml.setProtectData("return_code", "FAIL");
				xml.setProtectData("return_msg", "FAIL");
				System.out.println("验证失败");
			}
			WeixinUtil.sendXml(WeixinConfig.HTTPS_VERIFY_URL, xml.putout());
		}catch(Exception e){
			System.out.println("支付出错");
			e.printStackTrace();
			xml.setProtectData("return_code", "FAIL");
			xml.setProtectData("return_msg", "FAIL");
			WeixinUtil.sendXml(WeixinConfig.HTTPS_VERIFY_URL, xml.putout());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return null;
	}
	
	@Override
	public String weixinNotifyXcx(HttpServletRequest request, HttpServletResponse response,Map<String, String> paramss) {
		XmlData xml = new XmlData();
		try{
			
			Map<String, String> params = paramss;
			String return_code = params.get("return_code");
			String sign = params.get("sign");
			Double totalFee = Double.parseDouble(params.get("total_fee"));
			Integer total_fee =Integer.parseInt(params.get("total_fee"));//用于保存原始记录
			String transaction_id= params.get("transaction_id");//微信支付订单号
			String timeEnd = params.get("time_end");//支付时间
			String openId=params.get("openid");//用户openid
			String outTradeNo= params.get("out_trade_no");
			totalFee = CalcUtil.mul(totalFee, 0.01, 2);
			String body = params.get("body");
			//自主生成验证签名
			String checkSign = WeixinUtil.notifySignXCX(params);
			if (sign.equals(checkSign)) {//验证成功
				if(return_code.equals("SUCCESS")){
					JSONObject ob = JSONObject.parseObject(body);
					Integer type=ob.getInteger("type")==null?0:ob.getInteger("type"); // 是否合并支付    1:0
					Integer userId = ob.getInteger("userId");
					StringBuffer orderIds = new StringBuffer();
					JSONArray array = JSONArray.parseArray(ob.getString("orderIds"));
					
					for(int i=0;i<array.size();i++){
						String order= array.getString(i);
						order=order.substring(5,order.length()-5);
						if(i==(CalcUtil.sub(array.size(), 1))){
							orderIds.append(order);
						}else{
							orderIds.append(order+",");
						}
					}
					
					
					Map<String,Object> map =new HashMap<String, Object>();
					map= this.payOrdersMerge(request, userId, orderIds.toString(), "WEIXIN", totalFee, transaction_id,timeEnd,openId,total_fee, outTradeNo,type,0,null,null);	
					int status = Integer.parseInt(map.get("status").toString());
					if(status==0x01){
						//===================lbr=============
						List<ReGoodsorder> orders = reGoodsorderDao.getToBePaidOrders(userId, orderIds.toString());
						QueryModel queryModel = new QueryModel();
						for (ReGoodsorder reGoodsorder : orders) {
							
							if (reGoodsorder!=null) {
								queryModel.clearQuery();
								queryModel.combPreEquals("order.id", reGoodsorder.getId(),"orderId");
								List<ReGoodsorderItem> items = dateBaseDao.findLists(ReGoodsorderItem.class, queryModel);
								if (reGoodsorder.getStatus()>5) {
									for (ReGoodsorderItem reGoodsorderItem : items) {
										urlUtil.send(reGoodsorder.getSeller().getAdminUser().getPhone(), "【每天积分】您好，"+reGoodsorderItem.getGoodName()+"用户"+reGoodsorder.getUser().getName()+"已经成功付款，订单号："+reGoodsorder.getId()+"，请尽快确认订单，进行发货！");
									  if (reGoodsorder.getIsTeam()==true ) {
										   urlUtil.send(reGoodsorder.getReGoodsorder().getUser().getName(), "【每天积分】您好，您发起的拼团订单"+reGoodsorderItem.getOrder().getOrderCode()+"已成功！");
									  }
									
									}
								}
							}
						}
						//=====================================
						xml.setProtectData("return_code", "SUCCESS");
						xml.setProtectData("return_msg", map.get("message"));
					}else if(status==-1){
						xml.setProtectData("return_code", "FAIL");
						xml.setProtectData("return_msg", map.get("message"));
						System.out.println("逻辑处理失败");
					}else{
						xml.setProtectData("return_code", "SUCCESS");
						xml.setProtectData("return_msg", map.get("message"));
						System.out.println("订单逻辑已处理");
					}
				}else{
					xml.setProtectData("return_code", "FAIL");
					xml.setProtectData("return_msg", "FAIL");
					System.out.println("结果失败");
				}
			}else{
				xml.setProtectData("return_code", "FAIL");
				xml.setProtectData("return_msg", "FAIL");
				System.out.println("验证失败");
			}
			WeixinUtil.sendXml(WeixinConfig.HTTPS_VERIFY_URL, xml.putout());
		}catch(Exception e){
			System.out.println("支付出错");
			e.printStackTrace();
			xml.setProtectData("return_code", "FAIL");
			xml.setProtectData("return_msg", "FAIL");
			WeixinUtil.sendXml(WeixinConfig.HTTPS_VERIFY_URL, xml.putout());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return null;
	}
	
	
	/**
	 * 钱包支付
	 */
	@Override
	public Map<String, Object> payByWallet(HttpServletRequest request, HttpServletResponse response) {
		String xcx = request.getParameter("xcx");
		String orderIds = "";
		Integer userId = 0;
		String sign = "";
		Integer type = 0;
		String zoneId =  "";
		String timeStamp = "";
		String os = "";
		StringBuffer ids = new StringBuffer();
		if(xcx != null){
			orderIds = request.getParameter("orderIds");
			userId = request.getParameter("userId")==""?-1:Integer.valueOf(request.getParameter("userId"));
			type = 0;
			System.out.println("orderIds:"+orderIds);
		}else{
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			orderIds = parameter.getData().getString("orderIds");
			userId = Integer.parseInt(parameter.getUserId());
			timeStamp = parameter.getData().getString("timeStamp");
			sign=parameter.getData().getString("sign");
			type=parameter.getData().getInteger("type");// 是否合并
			os = parameter.getOs();
			zoneId= parameter.getZoneId();
			
		}
		JSONArray array = JSONArray.parseArray(orderIds);
		for(int i=0;i<array.size();i++){
			String order= array.getString(i);
			order=order.substring(5,order.length()-5);
			if(i==(CalcUtil.sub(array.size(), 1))){
				ids.append(order);
			}else{
				ids.append(order+",");
			}
		}
		
		//检查商品状态
		
		Map<String, Object> checkMap = checkOrderBeforePay(request, ids.toString(), userId);
		if (Integer.parseInt(checkMap.get("status").toString()) < 0x01) {
			return checkMap;
		}
	
		
		//验证签名
		if(os!=null && os.equals("WEB")){
			String webSign = WeixinUtil.getWebSign(userId, type,ids.toString(), timeStamp);
			Map<String, Object> checkSgin = WeixinUtil.checkSgin(webSign, sign, timeStamp);
			Integer status= Integer.parseInt(checkSgin.get("status").toString());
			if(status!=1){
				return checkSgin;
			}
		}
		return this.payOrdersMerge(request, userId, ids.toString(), "WALLET", 0.00, "","","",0,"",0,0,os,zoneId);
		
	}
	
	
	public Map<String, Object> payOrders2(HttpServletRequest request,Integer userId,String orderIds,String payType,Double thirdParty,String transaction_id,String timeEnd,String openId,Integer totalFee,String outTradeNo) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try{
			statusMap.put("status", 1);
			statusMap.put("message", "支付成功");
			if(thirdParty==null){
				thirdParty = 0.00;
			}
			if(this.getPayCache(request, userId)){
				return JsonResponseUtil.getJson(-0x00a5,"用户支付请求处理中，请稍后");
			}else{
				this.setPayCache(request, userId, true);
			}
			double thirdPartyMoney=thirdParty;
			
			Users user = usersDao.findById(userId);
			List<ReGoodsorder> orders = reGoodsorderDao.getToBePaidOrders(userId,orderIds);
			if(orders==null||orders.size()==0){
				this.setPayCache(request, userId, false);
				return JsonResponseUtil.getJson(-0x00a6,"无可支付订单");
				
			}
			
			double cashpoint = user.getCashPoints();
			boolean ispay =true;
			double money = user.getMoney()==null?0d:user.getMoney();
			double totalMoney=0d;
			int totalScore=0;
			double totalCashpoint=0d;
			int score = user.getScore();
			
			int memCount = 0;//会员商城商品
			
			Integer freeVoucherCout = 0;
			
			if("WALLET".equals(payType)){
				if(money<=0){
					statusMap.put("status", -0x00a1);
					statusMap.put("message", "钱包余额不足");
					this.setPayCache(request, userId, false);
					return statusMap;
				}
				
				for(ReGoodsorder order : orders){
					int A = (int) CalcUtil.add(totalMoney, order.getPayPrice(), 2);
					totalMoney = CalcUtil.add(A, order.getLogisticsType(), 2);

					totalScore +=order.getPayScore()==null?0:order.getPayScore();
					totalCashpoint += order.getPayCashpoint()==null?0:order.getPayCashpoint();
					
				}
				
			}
			
			
			
			double fenyong =0;
			if(thirdParty<0){
				
				System.out.println("第三方支付现金不足");
			}else if(cashpoint<totalCashpoint){
				
				statusMap.put("status", -0x00a2);
				statusMap.put("message", "红包不足");
				this.setPayCache(request, userId, false);
				return statusMap;
			}else if(score<totalScore){
				
				statusMap.put("status", -0x00a3);
				statusMap.put("message", "积分不足");
				this.setPayCache(request, userId, false);
				return statusMap;
			}else if(money<totalMoney){
				
				statusMap.put("status", -0x00a1);
				statusMap.put("message", "钱包余额不足");
				this.setPayCache(request, userId, false);
				return statusMap;
			}else if((CalcUtil.sub(freeVoucherCout, memCount))<0){
				
				statusMap.put("status", -0x00a4);
				statusMap.put("message", "免单券不足");
				this.setPayCache(request, userId, false);
				return statusMap;
			}
			
			
			//修改订单状态
			for(ReGoodsorder order : orders){
				//如果是上门配送型，则直接将状态改成待兑换
				Integer orderStatus = -1;
				if(order.getLogistics().equals(ReGoodsorder.shang_men_zi_qu+"")
						||order.getLogistics().equals(ReGoodsorder.dao_dian_xiao_fei+"")){
					orderStatus = ReGoodsorder.dai_dui_huan;
				}else{
					orderStatus = ReGoodsorder.dai_que_ren;
				}
				//===================================
				order.setStatus(orderStatus);
				order.setPayType(order.getPayTypeNum(payType));
				order.setTotalFee(totalFee);
				order.setTransactionId(transaction_id);
				order.setOutTradeNo(outTradeNo);
				order.setTimeEnd(timeEnd);
				order.setOpenid(openId);
				order.setAlipayCode(transaction_id);
				order.setWeixinCode(transaction_id);
				reGoodsorderDao.update(order);
				List<ReGoodsorderItem> items = reGoodsorderItemDao.findByParent(order.getId());
				
				
				cashpoint = cashpoint - order.getPayCashpoint();
				score = score - order.getPayScore();
				
				//如果是第三方支付，则直接扣第三方金额
				if(payType.equals("ALIPAY")||payType.equals("WEIXIN")||payType.equals("YILIAN")){
					//thirdParty = thirdParty - order.getPayPrice();
					thirdParty = CalcUtil.sub(thirdParty, order.getPayPrice());

					if(order.getLogisticsType()!=null){
						//thirdParty = thirdParty - order.getLogisticsType();//邮费
						thirdParty = CalcUtil.sub(thirdParty, order.getLogisticsType());//邮费

					}
					
				}else{
					totalFee += (int)(CalcUtil.mul(order.getPayPrice(), 100, 2));
					//money = money - order.getPayPrice();
					money = CalcUtil.sub(money, order.getPayPrice());
					if(order.getLogisticsType()!=null){
						//money = money - order.getLogisticsType();//邮费
						//totalFee += (int)(order.getLogisticsType()*100);
						money = CalcUtil.sub(money, order.getLogisticsType());//邮费
						totalFee += (int)(CalcUtil.mul(order.getLogisticsType(), 100, 2));
					}

				}
				Seller seller = order.getSeller();//卖家获得钱
				boolean flag =true;
				
				Users txUsers=null;
				
				
					//添加明细
					if (CalcUtil.sub(user.getMoney(), money)!= 0) {
						cashmoneyRecordDao.updateRecord(user, CalcUtil.sub(money, user.getMoney()), ICashmoneyRecordDao.BUY);
					}
					
					
					txUsers=seller.getUsers();
				
				for(ReGoodsorderItem item : items){
					if("nnm".equals(item.getMallClass()) && item.getMallId()==349){//升级合伙人
						//1:找出所有事业合伙人保存到map中  2：通过递归方式找出上级用户是否有事业合伙人  3：如果有，找出事业合伙人对应的未使用的合伙人pid，将user赋予该pid 4如果没有找到，则找出当地区的事业合伙人 5 当前没有事业合伙人则进入系统默认合伙人
							
							List<TkldPid> tp =tkldPidDao.findByPropertyIsValid("users.id", userId);
							List<PartnerInform> partnerList = partnerInformDao.findByPropertyIsValid("users.id", userId);
							if(tp==null || tp.isEmpty()|| tp.size()==0 ){//非合伙人
								if(partnerList.isEmpty() || partnerList.size()==0 ){//未在补充环节
								ispay=addPartner(userId,null);
									UrlUtil.getTaoKeToMap("http://hhh.aixiaoping.cn/home/taoke/index?&user_id="+userId);
								}
								
							}
					}else if("nnm".equals(item.getMallClass()) && item.getMallId()==353 && item.getMallId()==354){
						addPartnerOrSeller(userId, null, item.getMallId());
						UrlUtil.getTaoKeToMap("http://hhh.aixiaoping.cn/home/taoke/index?&user_id="+userId);
						item.setStatus(ReGoodsorder.dai_que_ren);
						reGoodsorderItemDao.update(item);
					}else{
					
					item.setStatus(orderStatus);
					reGoodsorderItemDao.update(item);
					//添加销量
					reBaseGoodsDao.addGoodsSalesVolume(item.getMallClass()+item.getGoodsId(), item.getGoodQuantity());
					//减少库存
					updateGoodsStock(item, item.getGoodQuantity(), false);
					if(item.getMallClass().equals(ReBaseGoods.MemberMall)){
						//memCount = memCount + item.getGoodQuantity();
						memCount = (int) CalcUtil.add(memCount, item.getGoodQuantity());
					}
					
					if(ReBaseGoods.LocalSpecialtyMall.equals(item.getMallClass()) ){//99特惠，各地特产 分佣
							
						QueryModel queryModel = new QueryModel();
						queryModel.combPreEquals("users.id", user.getId(),"usersId");
						queryModel.setOrder("id desc");
						List<UserLoginRecord> ulist = dateBaseDAO.findLists(UserLoginRecord.class, queryModel);
						if(ulist!=null && ulist.size()>0){
							UserLoginRecord ur = ulist.get(0);
							fenyong =	getOutMoneyForNew(user,item,"LOCAL",ur.getZoneId(),0);
						}else{
							fenyong =	getOutMoneyForNew(user,item,"LOCAL",null,0);
						}
						      
						     
					}else if(ReBaseGoods.SellerMall.equals(item.getMallClass())){
						if(item.getMallId()==3401 || item.getMallId()==3399){
							fenyong = 140d*item.getGoodQuantity();
							QueryModel queryModel = new QueryModel();
							queryModel.combPreEquals("users.id", user.getId(),"usersId");
							queryModel.setOrder("id desc");
							List<UserLoginRecord> ulist = dateBaseDAO.findLists(UserLoginRecord.class, queryModel);
							if(ulist!=null && ulist.size()>0){
								UserLoginRecord ur = ulist.get(0);
								getOutMoneyForNew(user,item,"SELLER",ur.getZoneId(),0);
							}
						}
					}
					String remark = "销售产品"+item.getGoodName()+"获得收益";
					if(fenyong>0){
						remark.concat(",需减去分佣"+fenyong);
					}
					
					addMoneyForAdminUser(item.getPayPrice()-fenyong,seller.getAdminUser(),user,order.getCode(),remark,item,txUsers,null,null);
					if(item.getLogisticsType()==2 && flag){
						Double postage = order.getLogisticsType()==null?0d:order.getLogisticsType();
						if(postage>0){
							addMoneyForAdminUser(postage,seller.getAdminUser(),user,order.getCode(),"订单"+order.getOrderCode()+"邮费",item,txUsers,null,null);
							flag =false;
						}
					}
					
				}
				
				}
				
				
				if(seller.getMoney()==null){
					seller.setMoney(0d);
				} 
				
			}
			//修改优惠券状态
			List<UserCoupons> userAllCouponsList = userCouponsService.findCouponsByUserIsUse(user, orderIds);
			userCouponsService.updateCouponsStatus(userAllCouponsList, user);
			if(ispay){
				if (CalcUtil.sub(user.getCashPoints(), cashpoint)!= 0) {
					//cashpointsRecordDao.updateRecord(user,cashpoint-user.getCashPoints(), ICashpointsRecordDao.BUY);
					cashpointsRecordDao.updateRecord(user,CalcUtil.sub(cashpoint, user.getCashPoints()), ICashpointsRecordDao.BUY);
				}
				if (CalcUtil.sub(user.getScore(), score)!= 0) {
					//scorerecordsDao.updateRecord(user, score-user.getScore(), ScorerecordsDAO.BUY);
					scorerecordsDao.updateRecord(user, (int)CalcUtil.sub(score, user.getScore()), ScorerecordsDAO.BUY);
				}
				
				//消耗免单券
				if(memCount>0){
					int co = freeVoucherRecordDao.updateStatus(userId, memCount, 1);
				}
				
				user.setCashPoints(cashpoint);
				user.setScore(score);
				user.setMoney(money);
				
				
				//===================lbr=============
				//sendMessageByOrders(orders,user);
				
				//=====================================
				//更新用户账户余额及订单状态
				usersDao.update(user);
			}
		}catch(Exception e){
			e.printStackTrace();
			statusMap.put("status", -0x01);
			statusMap.put("message", "未知错误");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		this.setPayCache(request, userId, false);
		return statusMap;
	}
	
	//合并支付
	@Override
	public Map<String, Object> payOrdersMerge(HttpServletRequest request,
			Integer userId, String orderIds, String payType, Double thirdParty,
			String transaction_id, String timeEnd, String openId,
			Integer totalFee, String outTradeNo, Integer mergePay,
			Integer weixin_type, String os, String zoneId) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			statusMap.put("status", 1);
			statusMap.put("message", "支付成功");
			
			if (weixin_type == null) {
				weixin_type = 0;
			}
			if (this.getPayCache(request, userId)) {
				return JsonResponseUtil.getJson(-0x00a5, "用户支付请求处理中，请稍后");
			} else {
				this.setPayCache(request, userId, true);
			}
			double thirdPartyMoney = thirdParty;

			Users user = usersDao.findById(userId);
			List<ReGoodsorder> orders = reGoodsorderDao.getToBePaidOrders(userId, orderIds);

			if (orders == null || orders.size() == 0) {
				this.setPayCache(request, userId, false);
				return JsonResponseUtil.getJson(-0x00a6, "无可支付订单");
			}

			double cashpoint = user.getCashPoints();
			boolean ispay = true;
			double money = user.getMoney() == null ? 0 : user.getMoney();
			double totalMoney = 0d;
			int totalScore = 0;
			double totalCashpoint = 0d;
			int score = user.getScore();
			String timeStamp = DateUtil.getTimeStamp(userId.toString());
			int memCount = 0;// 会员商城商品

			Integer freeVoucherCout = 0;

			for (ReGoodsorder order1 : orders) {
				if ("WALLET".equals(payType)) {
					double A = CalcUtil.add(totalMoney, order1.getPayPrice(), 2);
					totalMoney = CalcUtil.add(A, order1.getLogisticsType(), 2);
					totalScore += order1.getPayScore() == null ? 0 : order1.getPayScore();
					totalCashpoint += order1.getPayCashpoint() == null ? 0: order1.getPayCashpoint();
				}

				if (order1.getPayPrice() < 0 || order1.getPayScore() < 0) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return JsonResponseUtil.getJson(-1, "支付金额异常");
				}

			}

			double fenyong = 0;

			double pingMoney = 0;
			if (thirdParty < 0) {
				System.out.println("第三方支付现金不足");
			} else if (score < totalScore) {

				statusMap.put("status", -0x00a3);
				statusMap.put("message", "积分不足");
				this.setPayCache(request, userId, false);
				return statusMap;
			} else if (money < totalMoney) {

				statusMap.put("status", -0x00a1);
				statusMap.put("message", "钱包余额不足");
				this.setPayCache(request, userId, false);
				return statusMap;
			} else if ((CalcUtil.sub(freeVoucherCout, memCount)) < 0) {

				statusMap.put("status", -0x00a4);
				statusMap.put("message", "免单券不足");
				this.setPayCache(request, userId, false);
				return statusMap;
			}

			// 修改订单状态
			for (ReGoodsorder order : orders) {
				// 如果是上门配送型，则直接将状态改成待兑换
				Integer orderStatus = -1;
				if (order.getLogistics().equals(ReGoodsorder.shang_men_zi_qu + "") || order.getLogistics().equals(ReGoodsorder.dao_dian_xiao_fei + "")) {
					orderStatus = ReGoodsorder.dai_dui_huan;
				} else  {
					orderStatus = ReGoodsorder.dai_que_ren;// da
				}
				double amount = 0d; // 钱包支付总额
				amount = CalcUtil.add(amount, order.getPayPrice());
				amount = CalcUtil.add(amount, order.getLogisticsType(), 2);
				amount = CalcUtil.sub(amount, thirdParty) <= 0 ? 0 : CalcUtil
						.sub(amount, thirdParty);

				// ===================================
				order.setIsMerge(mergePay); // 合并支付
				order.setStatus(orderStatus);
				order.setPayType(order.getPayTypeNum(payType));
				order.setTotalFee(totalFee);
				order.setTransactionId(transaction_id);
				order.setOutTradeNo(outTradeNo);
				order.setTimeEnd(timeEnd);
				order.setOpenid(openId);
				order.setWebType(weixin_type);
				if (order.getIsTeam()) {
					order.setTeamPayTime(new Timestamp(System.currentTimeMillis())); // 拼团支付时间
				}
				if ("WALLET".equals(payType)) { // 自生产交易批次号
					order.setAlipayCode(timeStamp);
					order.setWeixinCode(timeStamp);
				} else {
					order.setAlipayCode(transaction_id);
					order.setWeixinCode(transaction_id);
				}
				order.setWalletPay(amount);
				if (order.getIsTeam()) {
					updateTeamNum(order);// 修改拼团状态及人数
					orderStatus = order.getStatus();
				}
				reGoodsorderDao.update(order);

				// 如果是拼团一定只有一个订单
				/*
				 * if(order.getIsTeam()&&order.getReGoodsorder()==null){ //证明是拼主
				 * Map<String, Object> map=new HashMap<>(); //返回支付成功 分享数据
				 * 
				 * int a = CalcUtil.genDoubleRandom(); int b =
				 * CalcUtil.genDoubleRandom(); String oId =
				 * a+""+order.getId()+""+b; map.put("teamOrderId", oId);
				 * statusMap.put("data", map); }
				 */

				List<ReGoodsorderItem> items = reGoodsorderItemDao.findByParent(order.getId());

				cashpoint = cashpoint - order.getPayCashpoint();

				// 扣除用户积分
				score = score - order.getPayScore();
				// 如果是第三方支付，则直接扣第三方金额
				if (payType.equals("ALIPAY") || payType.equals("WEIXIN")|| payType.equals("YILIAN")) {
					System.out.println("第三方支付,没有处理");
				} else {

					money = CalcUtil.sub(money, order.getPayPrice());
					if (order.getLogisticsType() != null) {

						money = CalcUtil.sub(money, order.getLogisticsType());// 邮费

					}
				}
				Seller seller = order.getSeller();// 卖家获得钱
				boolean flag = true;
				Users txUsers = null;
				// --------------da

				// 添加明细
				//if (CalcUtil.sub(user.getMoney(), money) != 0 && money != 0) { // user.getMoney(), money
					if("nnm".equals(items.get(0).getMallClass())){
						cashmoneyRecordDao.updateRecord(user,CalcUtil.sub(money, user.getMoney()),ICashmoneyRecordDao.SHOP);
					}else{
						
						cashmoneyRecordDao.updateRecord(user,CalcUtil.sub(money, user.getMoney()),ICashmoneyRecordDao.BUY);
					}
					user.setMoney(money);
				//}

				if (1 == mergePay) { // 合并支付
					cashmoneyRecordDao.updateRecord(user,CalcUtil.sub(0, user.getMoney()),ICashmoneyRecordDao.BUYMERGE);
					money = 0d;
				}

				txUsers = seller.getUsers();
				user.setCashPoints(cashpoint);
				user.setScore(score);
				user.setMoney(money);
				usersDao.update(user);

				for (ReGoodsorderItem item : items) {
					if("ldm".equals(item.getMallClass())){
						orderStatus=ReGoodsorder.jifenduobao;
						item.getOrder().setStatus(orderStatus);
						reGoodsorderDao.update(item.getOrder());
					}

					if (item.getGoodQuantity() < 0) {
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						return JsonResponseUtil.getJson(-1, "支付金额异常");
					}

						if ("nnm".equals(item.getMallClass())&& item.getMallId() == 353 || item.getMallId() == 354 || item.getMallId() == 349) { //一元开店354
							cashmoneyRecordDao.updateRecord(user,thirdPartyMoney,ICashmoneyRecordDao.SHOP);
							item.setStatus(ReGoodsorder.dai_que_ren);
							reGoodsorderItemDao.update(item);

					}else {

						item.setStatus(orderStatus);
						reGoodsorderItemDao.update(item);

						double umoney = 0;
						if (item.getUserCoupons() != null) {// 推广费
							List<UserCoupons> ulist = new ArrayList<UserCoupons>();
							ulist.add(item.getUserCoupons());
							userCouponsService.updateCouponsStatus(ulist, user); // 修改状态
							UserCoupons uc = item.getUserCoupons();
							if (uc.getShare() != null) {
								umoney = uc.getCommission() == null ? 0d : uc.getCommission();

								double hhrmoney = CalcUtil.mul(umoney, 0.6);
								double syhhrmoney = CalcUtil.mul(umoney, 0.2);
								double yysmoney = CalcUtil.mul(umoney, 0.15);
								String sf = "";
								Users hhr = uc.getShare();
								List<TkldPid> tld = tkldPidDao.findByPropertyIsValid("users.id",hhr.getId());
								if (tld != null && tld.size() > 0) {
									TkldPid tp = tld.get(0);
									if (tp.getLevel() == 3) {
										if (tp.getTkldPid() != null&& tp.getTkldPid().getUsers() != null) {
											addMoneyForUsers(syhhrmoney,tp.getTkldPid().getUsers(),item.getOrder().getOrderCode(),user,
															item.getId(),"领券商品"
															+ item.getGoodName()
															+ "成交,推广者上级事业合伙人获得收益"
															+ syhhrmoney
															+ "元（预估收益），确认收货后即可使用",
															null, null, -1);
										}
										if (tp.getTkldPid() != null && tp.getTkldPid().getTkldPid() != null && tp.getTkldPid().getTkldPid()
														.getAdminUser() != null) {
											addMoneyForAdminUser(yysmoney,tp.getTkldPid().getTkldPid().getAdminUser(),user,order.getCode(),
																"领券商品"
																		+ item.getGoodName()
																		+ ",推广者所在城市运营商获得佣金收益"
																		+ yysmoney
																		+ "元（预估收益），确认收货后即可使用",
																item, null, null, null);
										}

									} else if (tp.getLevel() == 2) {
										if (tp.getTkldPid() != null&& tp.getTkldPid().getAdminUser() != null) {
											addMoneyForAdminUser(yysmoney,tp.getTkldPid().getAdminUser(),user,order.getCode(),"领券商品"
																	+ item.getGoodName()
																	+ "成交,推广者所在城市运营商获得收益"
																	+ yysmoney
																	+ "元（预估收益），确认收货后即可使用",
															item, null, null, null);
										}

										if (tp.getIsCareerPartner() != null&& tp.getIsCareerPartner() == 2) {// 高级合伙人分佣增加至cashmoneyrecord标中
											addMoneyForUsersForGJhhr(syhhrmoney,tp.getUsers(),item.getOrder().getOrderCode(),user,item.getId(),
																	"领券商品"
																			+ item.getGoodName()
																			+ "成交,推广者上级高级合伙人获得收益"
																			+ syhhrmoney
																			+ "元（预估收益），确认收货后即可使用",
																	null, null);
										} else {
											addMoneyForUsers(syhhrmoney,tp.getUsers(),item.getOrder().getOrderCode(),user,item.getId(),
															"领券商品"
																	+ item.getGoodName()
																	+ "成交,推广者上级事业合伙人获得收益"
																	+ syhhrmoney
																	+ "元（预估收益），确认收货后即可使用",
															null, null, -1);
										}

									}

								}

								if (hhrmoney > 0) {
									addMoneyForUsers(hhrmoney, uc.getShare(),
											item.getOrder().getOrderCode(),
											user, item.getId(), sf + "领券商品"
													+ item.getGoodName()
													+ "成交,推广者获得收益" + hhrmoney
													+ "元（预估收益），确认收货后即可使用",
											null, null, -1);
								}

							}
						}

						// 减少库存 销量
						
						if (item.getReGoodsOfSellerMall() != null && item.getMallClass().equals(ReBaseGoods.teamMall)) { // 新版本
							updateGoodsStockNew(item, item.getGoodQuantity(),false);
							// 添加销量
							reBaseGoodsDao.addGoodsSalesVolumeNew(item.getReGoodsOfSellerMall().getId(), item.getGoodQuantity());
						}else if(item.getMallClass().equals(ReBaseGoods.lockMall)){
							//增加参与人数
							reBaseGoodsDao.addGoodsSalesVolume(item.getMallClass() + item.getGoodsId(),item.getGoodQuantity());
						} else {
							updateGoodsStock(item, item.getGoodQuantity(),false);
							// 添加销量
							reBaseGoodsDao.addGoodsSalesVolume(item.getMallClass() + item.getGoodsId(),item.getGoodQuantity());
						}

						if (item.getMallClass().equals(ReBaseGoods.MemberMall)) {
							// memCount = memCount + item.getGoodQuantity();
							memCount = (int) CalcUtil.add(memCount,item.getGoodQuantity());
						}
						
						// 添加积分记录
						if (item.getMallClass().equals(ReBaseGoods.ScoreMall) || (item.getMallClass().equals(ReBaseGoods.lockMall) && item.getGameType().getId() != 265 )) {//
							scorerecordsDao.updateRecord(user, item.getPayScore());
							getOutMoneyForScore(user,item,"SELLER",null,0.0);
						}else if(item.getMallClass().equals(ReBaseGoods.lockMall) && item.getGameType().getId() == 265){
							scorerecordsDao.updateRecord(user, item.getPayScore());
							sellerMoney(user, item);
						}
						
						if (ReBaseGoods.LocalSpecialtyMall.equals(item.getMallClass())) {// 99特惠，各地特产 分佣

							QueryModel queryModel = new QueryModel();
							queryModel.combPreEquals("users.id", user.getId(),"usersId");
							queryModel.setOrder("id desc");
							List<UserLoginRecord> ulist = dateBaseDAO.findLists(UserLoginRecord.class,queryModel);
							Integer lastzoneid = null;
							if (ulist != null && ulist.size() > 0) {
								UserLoginRecord ur = ulist.get(0);
								lastzoneid = ur.getZoneId();

							}

							fenyong = getOutMoneyForNew(user, item, "LOCAL",lastzoneid, umoney);

						} else if (ReBaseGoods.SellerMall.equals(item.getMallClass())) {
							//这里添加现金购买的记录
							String remark = "销售产品" + item.getGoodName()
									+ "获得收益,用户支付金额:" + item.getPayPrice();
							addMoneyForAdminUser(item.getPayPrice()- fenyong,seller.getAdminUser(), user,order.getCode(), remark, item, txUsers,null, null);
							
							
						}else if(ReBaseGoods.lockMall.equals(item.getMallClass())){
							ReGoodsOfLockMall lockMall = reGoodsOfLockMallDao.findById(item.getGoodsId());
							if(lockMall.getGameType().getId()==265){
								
//								int max = lockMall.getPeopleNum()==null ? 0:lockMall.getPeopleNum();
//								Integer lotteryYards = Integer.valueOf((int)(Math.random()*100)+""+(int)(Math.random()*max));
//								item.setLotteryYards(lotteryYards);//随机抽奖码
								Integer openType = lockMall.getOpenType()==null?1:lockMall.getOpenType(); //默认够人数开奖吧
								Map<String,Object> numAndItems = reGoodsOfLockMallService.numAndItems(lockMall);
								int participantNum = (int) numAndItems.get("participantNum");
								
								
								if(participantNum == lockMall.getPeopleNum()){
									//随机开奖
									List<ReGoodsorderItem> itemList=  (List<ReGoodsorderItem>)numAndItems.get("items");
									
									int random = (int) Math.round(Math.random()*itemList.size());
									ReGoodsorderItem ritem = itemList.get(random); //中奖订单
									
									ritem.setIsLock(1);
									ReGoodsorder o = ritem.getOrder();
									if (o.getLogistics().equals(ReGoodsorder.shang_men_zi_qu + "") || o.getLogistics().equals(ReGoodsorder.dao_dian_xiao_fei + "")) {
										orderStatus = ReGoodsorder.dai_dui_huan;
									} else  {
										orderStatus = ReGoodsorder.dai_que_ren;// da
									}
									o.setStatus(orderStatus);
									ritem.setStatus(orderStatus);
									reGoodsorderItemDao.saveOrUpdate(ritem);
									reGoodsorderDao.saveOrUpdate(o);
									lockMall.setOpenYards(1);
									lockMallDao.saveOrUpdate(lockMall);
									
									//中奖了 需要处理提供商品商家的分佣问题, 提供积分的商家 用户 代理 的积分问题
									isLockItem(user, lockMall);
									//中奖了
									if(item.getUser().getUnionId() != null){
										String unionId = item.getUser().getUnionId();
										String param = "unionId="+unionId+"&linkType=2&time="+item.getOrder().getCreateTime().getTime()/1000+"&goodsName="+item.getGoodName();
										UrlUtil.sendGzhMsg(8, param);
//										通知所有人信息
									}
									
								}
								
								
							}else if(lockMall.getGameType().getId()==267){ //属于倒计时的 最后这个订单的时间
								//判断是否在购买时间范围购买
								Date date = new Date();
								lockMall.setItemLastTime(DateFormat.getTimeInstance().format(date));
							}
							reGoodsOfLockMallDao.saveOrUpdate(lockMall);
							
						}
						if (item.getIsTeam() == true) {
							pingMoney = CalcUtil.mul(item.getPayPrice(), 0.03,2);
							// 拼团商品分佣
							teamCommission(user, item);

							//
						}
					}

					if (StringUtils.isNotBlank(os) && os.equals("WEB")) {
						String web_templatecode = AxpMessageUtil.template_webbuy_code;// 网页购买短信模板code
						String order_templatecode = AxpMessageUtil.template_order_code;// 确认发货短信code

						String contect = AxpMessageUtil.webMessage(order
								.getOrderCode());


						AliNewSendMsgServiceImpl aliNewSendMsg = new AliNewSendMsgServiceImpl(
								user.getPhone(), contect, web_templatecode);
						Thread thread = new Thread(aliNewSendMsg);
						thread.start();
						// aliNewSendMsg.send();

						String ordercontect = AxpMessageUtil
								.orderApplyMessage(order.getOrderCode());
						AliNewSendMsgServiceImpl aliNewSendMsgs = new AliNewSendMsgServiceImpl(
								order.getSellerPhone(), ordercontect,
								order_templatecode);
						Thread thread2 = new Thread(aliNewSendMsgs);
						thread2.start();
						// aliNewSendMsgs.send();
					}

					
				}
				if (seller.getMoney() == null) {
					seller.setMoney(0d);
				}

			}
			if (ispay) {
				// 消耗免单券
				if (memCount > 0) {
					int co = freeVoucherRecordDao.updateStatus(userId,
							memCount, 1);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			statusMap.put("status", -0x01);
			statusMap.put("message", "未知错误");
			if (!TransactionAspectSupport.currentTransactionStatus()
					.isRollbackOnly()) {
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
			}
		} finally {
			this.removeCache(request, userId);
		}

		return statusMap;
	}
	
	
	/**
	 * 一元开店后 生成一个adminUser
	 * @param userId
	 * @param zoneId
	 * @param mallId
	 */
	private void addAdminUserOrSeller(Users user,String zoneId,
			Integer mallId) {
		AdminUser admin = new AdminUser();
		admin.setAddress(user.getAddress());
		admin.setIsvalid(true);
		admin.setUsers(user);
		admin.setLoginname(user.getPhone());
		admin.setPhone(user.getPhone());
		admin.setLevel(60);
		admin.setUsername(user.getName());
		admin.setCreatetime(new Timestamp(System.currentTimeMillis()));
		admin.setPassword(MD5Util.GetMD5Code("888888"));
		admin.setMoney(0.0);
		adminUserDao.save(admin);
		
		
		
		Seller seller = new Seller();
		seller.setCreatetime(new Timestamp(System.currentTimeMillis()));
		seller.setAdminUser(admin);
		seller.setUsers(user);
		seller.setVerifyStatus(-1);
		seller.setIsvalid(true);
		seller.setName(user.getName()+"的店铺");
		sellerDAO.save(seller);
		
		user.setSellerId(seller.getId());
		user.setAdminUser(admin);
		usersDao.update(user);
		
		
	}


	//通过递归方式查找商家用户的pid；找到为止，不限制层级
	public TkldPid getPid(Users users,int times){
		TkldPid tp =null;
		
		if(times>15){
			return tp;
		}
		if(users.getInvitecode()!=null){
			times++;
			List<Users> parentUser3 = usersDao.findUsersByInvitecode(users.getInvitecode());
			if(parentUser3!=null && parentUser3.size()>0){
				 Users	pUsers3 = parentUser3.get(0);
				 
				 List<TkldPid> tjld = tkldPidDao.findByPropertyIsValid("users.id", pUsers3.getId());
				 
				 
					if(tjld !=null && tjld.size()>0){
						return tjld.get(0);
					}else{
						tp=getPidForNew(pUsers3,times);
					}
				}
		}
		
		
		return tp;
	}
	
	
	
	/**
	 * 
	 * @param userId  用户id  
	 * @param payType  支付类型
	 * @param money	  钱包支付金额
	 * @param thirdParty 第三方支付金额
	 */
	public void addPartner(Integer userId,String payType,double money,double thirdParty){
		try {
		QueryModel queryModel=new QueryModel();
		queryModel.combPreEquals("isValid", true);
		queryModel.combEquals("level", 2);
		queryModel.combCondition("users.id is not null");
		List<TkldPid> tkldPidList= dateBaseDAO.findLists(TkldPid.class, queryModel);
		Map<Integer, Object> careerPartner=new HashMap<Integer, Object>();
		//填充所有事业合伙人
		for (TkldPid tkldPid : tkldPidList) {
				careerPartner.put(tkldPid.getUsers().getId(), tkldPid);
		}
		Users users = usersDao.findById(userId);
		//返回的事业合伙人对象
		TkldPid cause=findParentPid(users,careerPartner);
		if(cause!=null){
				//根据事业合伙人找到下级未被分配的合伙人
				queryModel.clearQuery();
				queryModel.combPreEquals("isValid", true);
				queryModel.combPreEquals("tkldPid.id", cause.getId(),"parentId");
				queryModel.combEquals("level", 3);
				queryModel.combIsNull("users.id");
				//找到可以分配的合伙人
				List<TkldPid> partnerList = dateBaseDAO.findLists(TkldPid.class, queryModel);
				if(partnerList.size()>0){
								TkldPid tkldPid = partnerList.get(0);
								tkldPid.setUsers(users);
								tkldPid.setCreateTime(new Timestamp(new Date().getTime()));
								tkldPid.setUsersRemark("每天积分合伙人");
								tkldPid.setBindingTime(new Timestamp(System.currentTimeMillis()));
								tkldPidDao.update(tkldPid);
				}else {
					//创建一张表  来记录   没有分配的情况  以后补数据
					System.out.println("没有可以分配的合伙人");
					savePartnerInform(users,cause.getUsers(),"联盟商户:"+cause.getUsers().getName()+",购买用户"+users.getName(),3,0);
				}
				if(partnerList.size()<5){
					//发送推送消息
					userSystemMessageService.saveMessage("合伙人不足","粉丝"+users.getName()+"支付成功！联盟商户"+cause.getUsers().getName()+"的合伙人数量为"+partnerList.size()+";请及时补充");
				}
				//分佣
				updateUserMoney(cause.getUsers(),users,payType,money,thirdParty);
		}else{
			//如果事业合伙人 没有找到
			boolean isDefault=false;
			queryModel.clearQuery();
			queryModel.combPreEquals("users.id",userId,"userId");
			queryModel.setOrder("lasttime desc");
			List<UserLoginRecord> userLoginRecordList = dateBaseDAO.findLists(UserLoginRecord.class, queryModel);
			if(userLoginRecordList.size()>0){
				UserLoginRecord userLoginRecord= userLoginRecordList.get(0);
				queryModel.clearQuery();
				//找到当前地区的事业合伙人
				queryModel.combPreEquals("isValid", true);
				queryModel.combPreEquals("provinceEnum.id", userLoginRecord.getZoneId(),"zoneId");
				queryModel.combEquals("level", 2);
				//事业合伙人	区级
				List<TkldPid> careerList  = dateBaseDAO.findLists(TkldPid.class, queryModel);
				if(careerList.size()>0){
					//找到了事业合伙人
					TkldPid careerTkldPid = careerList.get(0);
					queryModel.clearQuery();
					queryModel.combPreEquals("isValid", true);
					queryModel.combPreEquals("tkldPid.id", careerTkldPid.getId(),"parentId");
					queryModel.combEquals("level", 3);
					queryModel.combIsNull("users.id");
					//找到可用的合伙人
					List<TkldPid> partnerList = dateBaseDAO.findLists(TkldPid.class, queryModel);
					
					if(partnerList.size()>0){
						TkldPid tkldPid=partnerList.get(0);
						tkldPid.setUsers(users);
						tkldPid.setUsersRemark("每天积分合伙人");
						tkldPid.setCreateTime(new Timestamp(new Date().getTime()));
						tkldPid.setBindingTime(new Timestamp(System.currentTimeMillis()));
						//添加合伙人
						tkldPidDao.update(tkldPid);
					}
					else{
						System.out.println("没有可以分配的合伙人");
						savePartnerInform(users,careerTkldPid.getUsers(),"联盟商户:"+careerTkldPid.getUsers().getName()+",购买用户"+users.getName(),3,0);
					}
					if(partnerList.size()<5){
						userSystemMessageService.saveMessage("合伙人不足","粉丝"+users.getName()+"支付成功！联盟商户"+careerTkldPid.getUsers().getName()+"的合伙人数量为"+partnerList.size()+";请及时补充");
					}
					//分佣
					updateUserMoney(careerList.get(0).getUsers(),users,payType,money,thirdParty);
				}else {	//市级
					ProvinceEnum province = provinceEnumDao.findById(userLoginRecord.getZoneId());
					if(province.getProvinceEnum()!=null&&province.getProvinceEnum().getLevel()==2){
						ProvinceEnum city=province.getProvinceEnum();
						queryModel.clearQuery();
						queryModel.combPreEquals("isValid", true);
						queryModel.combPreEquals("provinceEnum.id", city.getId(),"zoneId");
						queryModel.combEquals("level", 2);
						List<TkldPid> careerLists = dateBaseDAO.findLists(TkldPid.class, queryModel);
						if(careerLists.size()>0){
							queryModel.clearQuery();
							queryModel.combPreEquals("isValid", true);
							queryModel.combPreEquals("tkldPid.id",careerLists.get(0).getId(),"parentId");
							queryModel.combEquals("level", 3);
							queryModel.combIsNull("users.id");
							//找到可用的合伙人
							List<TkldPid> partnerList = dateBaseDAO.findLists(TkldPid.class, queryModel);
							if(partnerList.size()>0){
								TkldPid tkldPid=partnerList.get(0);
								tkldPid.setUsers(users);
								tkldPid.setUsersRemark("每天积分合伙人");
								tkldPid.setCreateTime(new Timestamp(new Date().getTime()));
								tkldPid.setBindingTime(new Timestamp(System.currentTimeMillis()));
								//添加合伙人
								tkldPidDao.update(tkldPid);
							}else{
								System.out.println("记录下来 没有剩余的合伙人了");
								savePartnerInform(users,careerLists.get(0).getUsers(),"联盟商户:"+careerLists.get(0).getUsers().getName()+",购买用户"+users.getName(),3,0);
							}
							if(partnerList.size()<5){
								userSystemMessageService.saveMessage("合伙人不足","粉丝"+users.getName()+"支付成功！联盟商户"+careerLists.get(0).getUsers().getName()+"的合伙人数量为"+partnerList.size()+";请及时补充");
							}
							//分佣
							updateUserMoney(careerLists.get(0).getUsers(),users,payType,money,thirdParty);
						}else{
							isDefault=true;
						}
					}else{
						//根据地区没有找到事业合伙人
						isDefault=true;
					}
				}
			}else{
				//用户没有访问首页的记录  因为如果访问首页没有zoneId UserLoginRecord 就没有插入记录
				isDefault=true;
			}
			
			//如果进了 就说明给默认的事业合伙人   然后找到默认事业合伙人的合伙人集合 区一条给他
			if(isDefault){
				 //StringUtil.CAREERPARTNER;  默认事业合伙人
					List<TkldPid> defaultTkldPid = tkldPidDao.findByProperty("pId", StringUtil.CAREERPARTNER);
					if(defaultTkldPid!=null && defaultTkldPid.size()>0){
						queryModel.clearQuery();
						queryModel.combPreEquals("isValid", true);
						queryModel.combEquals("level", 3);
						queryModel.combPreEquals("tkldPid.id",defaultTkldPid.get(0).getId(),"parentId");
						queryModel.combIsNull("users.id");
						List<TkldPid> list = dateBaseDAO.findLists(TkldPid.class, queryModel);
						if(list.size()>0){
							TkldPid t=list.get(0);
							t.setUsers(users);
							t.setCreateTime(new Timestamp(new Date().getTime()));
							t.setUsersRemark("每天积分默认合伙人");
							t.setBindingTime(new Timestamp(System.currentTimeMillis()));
							//设置为默认合伙人
							tkldPidDao.update(t);
						}else{
							System.out.println("记录下来 没有剩余的合伙人了");
							savePartnerInform(users,defaultTkldPid.get(0).getUsers(),"默认事业合伙人:"+defaultTkldPid.get(0).getUsers().getName()+",购买用户"+users.getName(),3,0);
						}
						if(list.size()<5){
							userSystemMessageService.saveMessage("合伙人不足","粉丝"+users.getName()+"支付成功！默认事业合伙人"+defaultTkldPid.get(0).getUsers().getName()+"的合伙人数量为"+list.size()+";请及时补充");
						}
						//分佣
						updateUserMoney(defaultTkldPid.get(0).getUsers(),users,payType,money,thirdParty);
						
					}
			}					
		}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}
	
	
	
	public void updateUserMoney(Users causeUser,Users users,String payType,double money,double thirdParty){
		
	
		
		//获得分佣金额
		double fenyong=90d;
		//causeMoneyRecord  收益方记录
		CashmoneyRecord causeMoneyRecord=new CashmoneyRecord();
		causeMoneyRecord.setUsersByUserId(causeUser);
		causeMoneyRecord.setBeforeMoney(causeUser.getMoney());
		causeMoneyRecord.setMoney(fenyong);
		
		double sum=CalcUtil.add(causeUser.getMoney(),fenyong,2 );
		causeMoneyRecord.setAfterMoney(sum);
		
		causeMoneyRecord.setRemark("发展合伙人获得分佣"+fenyong);
		causeMoneyRecord.setIsValid(true);
		causeMoneyRecord.setCreateTime(new Timestamp(new Date().getTime()));
		causeMoneyRecord.setUsersByFromUsers(users);
		causeMoneyRecord.setType(1);
		
		cashmoneyRecordDao.save(causeMoneyRecord);
		//更新用户金额
		causeUser.setMoney(sum);
		usersDao.update(causeUser);
		
	}
	
	
	public void updateUserMoney(Users causeUser,Users users	){
		
	
		
		//获得分佣金额
		double fenyong=90d;
		//causeMoneyRecord  收益方记录
		CashmoneyRecord causeMoneyRecord=new CashmoneyRecord();
		causeMoneyRecord.setUsersByUserId(causeUser);
		causeMoneyRecord.setBeforeMoney(causeUser.getMoney());
		causeMoneyRecord.setMoney(fenyong);
		
		double sum=CalcUtil.add(causeUser.getMoney(),fenyong,2 );
		causeMoneyRecord.setAfterMoney(sum);
		
		causeMoneyRecord.setRemark("发展合伙人获得分佣"+fenyong);
		causeMoneyRecord.setIsValid(true);
		causeMoneyRecord.setCreateTime(new Timestamp(new Date().getTime()));
		causeMoneyRecord.setUsersByFromUsers(users);
		causeMoneyRecord.setType(1);
		
		cashmoneyRecordDao.save(causeMoneyRecord);
		//更新用户金额
		causeUser.setMoney(sum);
		usersDao.update(causeUser);
		
	}
	
	
	public TkldPid findParentPid(Users users, Map<Integer, Object> map) {
		Users currentUser = null;
		if (StringUtil.hasLength(users.getInvitecode())) {
			
			List<Users> userList = usersDao.findUsersByInvitecode(users
					.getInvitecode());
			if (userList.size() > 0) {
				currentUser = userList.get(0);
				// 如果Map包含用户的id 代表就是事业合伙人
				if (map.containsKey(currentUser.getId())) {
					
					return (TkldPid) map.get(currentUser.getId());
					//如果用户的上级就是代理 或者 事业合伙人  那么就直接找
					//如果用户只是游客 那么就根据邀请码去找  然后找到邀请码的上级  
				} else {
					return  findParentPid(currentUser, map);
				}
			}
		}
		return null;
	}
	//通过粉丝关系向上查找事业合伙人
	public TkldPid findParentPid(Users users, Map<Integer, Object> map,Map<Integer, Object> symap,int times) {
		Users currentUser = null;
		
		if(times>14){
			return null;
		}
		if (StringUtil.hasLength(users.getInvitecode())) {
			List<Users> userList = usersDao.findUsersByInvitecode(users.getInvitecode());
			if (userList.size() > 0) {
				currentUser = userList.get(0);
				// 如果Map包含用户的id 代表就是事业合伙人
				if (map.containsKey(currentUser.getId())) {
					TkldPid tp = (TkldPid) map.get(currentUser.getId());
					return tp.getTkldPid();
					//如果用户的上级就是代理 或者 事业合伙人  那么就直接找
					//如果用户只是游客 那么就根据邀请码去找  然后找到邀请码的上级  
				} else if(symap.containsKey(currentUser.getId())){ 
					return (TkldPid) symap.get(currentUser.getId());
				}else {
					return  findParentPid(currentUser, map,symap,times);
				}
			}
		}
		
		return null;
		
	}
	//通过粉丝关系向上查找合伙人或者事业合伙人
	public TkldPid findParentPidOrpid(Users users, Map<Integer, Object> map,Map<Integer, Object> symap,int times) {
		Users currentUser = null;
		
		if(times>14){
			return null;
		}
		if (StringUtil.hasLength(users.getInvitecode())) {
			List<Users> userList = usersDao.findUsersByInvitecode(users.getInvitecode());
			if (userList.size() > 0) {
				currentUser = userList.get(0);
				// 如果Map包含用户的id 代表就是事业合伙人
				if (map.containsKey(currentUser.getId())) {
					TkldPid tp = (TkldPid) map.get(currentUser.getId());
					return tp;
					//如果用户的上级就是代理 或者 事业合伙人  那么就直接找
					//如果用户只是游客 那么就根据邀请码去找  然后找到邀请码的上级  
				} else if(symap.containsKey(currentUser.getId())){ 
					return (TkldPid) symap.get(currentUser.getId());
				}else {
					return  findParentPid(currentUser, map,symap,times);
				}
			}
		}
		
		return null;
		
	}
	
	
	public void sendMessageByOrders(List<ReGoodsorder> orders,Users user){
		QueryModel queryModel = new QueryModel();
		UrlUtil uu =new UrlUtil();
		try{
		for (ReGoodsorder reGoodsorder : orders) {
			if(reGoodsorder!=null){								
				queryModel.clearQuery();
				queryModel.combPreEquals("order.id", reGoodsorder.getId(),"orderId");
				queryModel.combPreEquals("isValid", true);
				List<ReGoodsorderItem> items = dateBaseDao.findLists(ReGoodsorderItem.class, queryModel);
				for (ReGoodsorderItem reGoodsorderItem : items) {
					
					//uu.send(reGoodsorder.getSeller().getUsers().getName(), "您好，"+reGoodsorderItem.getGoodName()+"用户"+user.getName()+"已经成功付款，订单号："+reGoodsorder.getId()+"，请尽快确认订单，进行发货！");
				}
				
			}
		}
		}catch(Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}
	
	
	public Map<String, Object> payRecharge(HttpServletRequest request,Integer adminuserid,String payType,Double total,String transaction_id,String timeEnd,String openId,Integer totalFee,String outTradeNo) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		
		try{
			
			QueryModel queryModel=new QueryModel();
			queryModel.clearQuery();
			
			queryModel.combEquals("transaction_id", transaction_id);
			
			List<AdminUserRecharge> acrlist=dateBaseDAO.findPageList(AdminUserRecharge.class, queryModel, 0, 10);
			
			
			statusMap.put("status", 0x01);
			statusMap.put("message", "支付成功");
			if(acrlist==null || acrlist.size()==0){
			AdminUser au = adminUserDao.findById(adminuserid);
			Double money=au.getMoney()==null?0.0:au.getMoney();			
			
			AdminUserRecharge aur = new AdminUserRecharge();
			aur.setAdminuser(au);
			aur.setOpenId(openId);
			aur.setTransaction_id(transaction_id);
			aur.setTotalFee(totalFee);
			aur.setRecharge(total);
			aur.setTimeEnd(timeEnd);
			aur.setOutTradeNo(outTradeNo);
			aur.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
			aur.setIsValid(true);
			AdminuserCashpointRecord acr = new AdminuserCashpointRecord();
			acr.setAdminUser(au);
			acr.setCashpoint(total);
			acr.setBeforepoint(money);
			acr.setAfterpoint(CalcUtil.add(money, total, 2));
			acr.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
			acr.setRemark("充值"+total);
			acr.setIsValid(true);
			acr.setType(1);
			acr.setIsDeposit(1);
			au.setMoney(CalcUtil.add(money, total, 2));
			adminUserDao.update(au);
			adminUserRechargeDao.saveOrUpdate(aur);
			adminuserCashpointRecordDAO.save(acr);
			
				if (au!=null) {
					List<AdminUser> ulist = new ArrayList<AdminUser>();
					ulist.add(au);
					userSystemMessageService.saveMessageForAdmin("2","用户"+au.getLoginname()+"充值"+total+"元成功！", StringUtil.MESSAGE_CHONGZHI, "充值信息", ulist,"", total,1);
				}
			}else{
				System.out.println("微信交易订单号已存在,交易已完成,回调重复");
			}
			
		
		
		}catch(Exception e){
			e.printStackTrace();
			statusMap.put("status", -0x01);
			statusMap.put("message", "未知错误");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	
		return statusMap;
	}
	
	
	
	public Map<String, Object> payRechargeScore(HttpServletRequest request,Integer adminuserid,String payType,Double total,String transaction_id,String timeEnd,String openId,Integer totalFee,String outTradeNo,Integer score) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		System.out.println("进入支付:"+total+"-积分-"+score);
		try{
			//score=100;
			Timestamp createTime = new Timestamp(System.currentTimeMillis());
			QueryModel queryModel=new QueryModel();
			queryModel.clearQuery();
			
			queryModel.combEquals("transaction_id", transaction_id);
			
			List<AdminUserRecharge> acrlist=dateBaseDAO.findPageList(AdminUserRecharge.class, queryModel, 0, 10);
			
			
			statusMap.put("status", 0x01);
			statusMap.put("message", "支付成功");
			if(acrlist==null || acrlist.size()==0){
			AdminUser au = adminUserDao.findById(adminuserid);
			au.setScore(au.getScore()+score);
			au.setDeposit(au.getDeposit()+total);
			AdminuserCashpointRecord adminUserAcr = new AdminuserCashpointRecord();
			if("100".equals(payType)){
				au.setMoney(CalcUtil.sub(au.getMoney(), total));
				adminUserAcr.setRemark("钱包充值押金"+total+"元");
				
				//增加钱包减少的
				AdminuserCashpointRecord acr = new AdminuserCashpointRecord();
				acr.setRemark("使用钱包"+total+"元作为押金充值");
				acr.setAdminUser(au);
				acr.setAfterpoint(CalcUtil.sub(au.getMoney(), total));
				acr.setBeforepoint(au.getMoney());
				acr.setCashpoint(total);
				acr.setCreateTime(new Timestamp(System.currentTimeMillis()));
				acr.setIsValid(true);
				acr.setType(1);
				acr.setIsDeposit(6);
				adminuserCashpointRecordDao.save(acr);
				
			}else if("200".equals(payType)){
				adminUserAcr.setRemark("支付宝充值押金"+total+"元");
			}else if("300".equals(payType)){
				adminUserAcr.setRemark("微信充值押金"+total+"元");
			}
			//押金充值记录
			adminUserAcr.setAdminUser(au);
			adminUserAcr.setAfterpoint(CalcUtil.sub(au.getMoney(), total));
			adminUserAcr.setBeforepoint(au.getMoney());
			adminUserAcr.setCashpoint(total);
			adminUserAcr.setCreateTime(new Timestamp(System.currentTimeMillis()));
			adminUserAcr.setIsValid(true);
			adminUserAcr.setType(1);
			adminUserAcr.setIsDeposit(5);
			adminuserCashpointRecordDao.save(adminUserAcr);			
			
			
			
			AdminUserRecharge aur = new AdminUserRecharge();
			aur.setPayType(OrderUtil.getPayTypeNum(payType));
			aur.setRechargeType(AdminUserRecharge.SCORE); 
			aur.setScore(score);
			aur.setAdminuser(au);
			aur.setOpenId(openId);
			aur.setTransaction_id(transaction_id);
			aur.setTotalFee(totalFee);
			aur.setRecharge(total);
			aur.setTimeEnd(timeEnd);
			aur.setOutTradeNo(outTradeNo);
			aur.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
			aur.setIsValid(true);
			aur.setRecharge(0d);
			
			
			AdminUserScoreRecord scoreRecord=new AdminUserScoreRecord();
			scoreRecord.setAdminUser(au);
			scoreRecord.setBeforeScore(au.getScore());
			scoreRecord.setAfterScore(au.getScore()+score);
			scoreRecord.setScore(score);
			scoreRecord.setSurplusScore(score);
			scoreRecord.setType(AdminUserScoreRecord.RECHARGE);
			scoreRecord.setRemark("在线充值积分获得:"+score);
			scoreRecord.setCreateTime(createTime);
			scoreRecord.setIsValid(true);
			//有效期30天
			scoreRecord.setValidityTime(new Timestamp(DateUtil.addYear2Date(30, new Date()).getTime()));
			
			//充值积分属于哪个合伙人的  
			AdminUser audl = null;
			if(au.getParentAdminUser().getParentAdminUser().getId() == 47){ //总部了 ,说明是没有合伙人的  层级为  商家-- 代理 -- 平台总部
				System.out.println("进入 47");
				audl = au.getParentAdminUser();
				AdminuserCashpointRecord adc = new AdminuserCashpointRecord();
				adc.setAdminUser(audl);
				adc.setAfterpoint(audl.getMoney()+CalcUtil.mul(total, 0.6));
				adc.setBeforepoint(audl.getMoney());
				adc.setCashpoint(CalcUtil.mul(total, 0.6));
				adc.setCreateTime(new Timestamp(System.currentTimeMillis()));
				adc.setIsValid(true);
				adc.setIsDeposit(5);
				adc.setRemark("下级商家"+au.getLoginname()+"购买"+score+"积分");
				adminuserCashpointRecordDao.save(adc);
				
				audl.setScore(audl.getScore()-score);
				
				queryModel.clearQuery();
				queryModel.combPreEquals("isValid", true);
				queryModel.combIsNull("adminUserSeller.id");
				queryModel.combPreEquals("adminUser.id", audl.getId(),"adminUserIdDL");
				List<DLScoreMark> dlsms = dateBaseDAO.findPageList(DLScoreMark.class, queryModel, 0, score);
				List<SJScoreMark> sjsms = new ArrayList<SJScoreMark>();
				System.out.println("dlsms:"+dlsms.size());
				System.out.println("开始--"+new Timestamp(System.currentTimeMillis()));
				integeralService.saveToList(dlsms ,au);
				
				
//				
//				for(DLScoreMark dlsm : dlsms){
//					dlsm.setAdminUserSeller(au);
//					dlsm.setRefreshTime(createTime);
//					dlScoreMarkDAO.saveOrUpdate(dlsm);
//					
//					SJScoreMark sjsm = new SJScoreMark();
//					sjsm.setAdminUser(au);
//					sjsm.setCreateTime(createTime);
//					sjsm.setDlScoreMark(dlsm);
//					sjsm.setIsValid(true);
//					sjsm.setRefreshTime(createTime);
//					sjsms.add(sjsm);
//					
//				}
//				sjScoreMarkDAO.saveList(sjsms);
//				
				
				System.out.println("结束--"+new Timestamp(System.currentTimeMillis()));
				
				
				//商家都买了减少 代理的积分
				adminUserDao.saveOrUpdate(audl);
				adminUserScoreRecordDao.save(scoreRecord);
				adminUserDao.update(au);
				adminUserRechargeDao.saveOrUpdate(aur);
				
			}else{   //层级为  商家-- 合伙人 -- 代理 --  平台总部
				System.out.println("非47:");
				AdminUser auhhr = au.getParentAdminUser();
				auhhr.setMoney(auhhr.getMoney()+CalcUtil.mul(total, 0.6,4)); //合伙人分服务费60%
				AdminuserCashpointRecord adc = new AdminuserCashpointRecord();
				adc.setAdminUser(auhhr);
				adc.setAfterpoint(auhhr.getMoney()+CalcUtil.mul(total, 0.6,4));
				adc.setBeforepoint(auhhr.getMoney());
				adc.setCashpoint(CalcUtil.mul(total, 0.6,4));
				adc.setCreateTime(new Timestamp(System.currentTimeMillis()));
				adc.setIsValid(true);
				adc.setIsDeposit(5);
				adc.setRemark("下级商家"+au.getLoginname()+"购买"+score+"积分,获得分佣"+CalcUtil.mul(total, 0.6,4));
				adminuserCashpointRecordDao.save(adc);
				
				//充值的积分属于哪个代理的修改
				audl = au.getParentAdminUser().getParentAdminUser();
				audl.setScore(audl.getScore()-score);
				
				queryModel.clearQuery();
				queryModel.combPreEquals("isValid", true);
				queryModel.combIsNull("adminUserSeller.id");
				queryModel.combPreEquals("adminUser.id", audl.getId(),"adminUserIdDL");
				List<DLScoreMark> dlsms = dateBaseDAO.findPageList(DLScoreMark.class, queryModel, 1, score);
				List<SJScoreMark> sjsms = new ArrayList<SJScoreMark>();
				System.out.println("非47 dlsms:"+ dlsms.size());
				for(DLScoreMark dlsm : dlsms){
					dlsm.setAdminUserSeller(au);
					dlsm.setRefreshTime(createTime);
					dlScoreMarkDAO.saveOrUpdate(dlsm);
					
					SJScoreMark sjsm = new SJScoreMark();
					sjsm.setAdminUser(au);
					sjsm.setCreateTime(createTime);
					sjsm.setDlScoreMark(dlsm);
					sjsm.setIsValid(true);
					sjsm.setRefreshTime(createTime);
					sjsms.add(sjsm);
					
				}
				sjScoreMarkDAO.saveList(sjsms);
				//商家都买了减少 代理的积分
				adminUserDao.saveOrUpdate(audl);
				adminUserScoreRecordDao.save(scoreRecord);
				adminUserDao.update(au);
				adminUserRechargeDao.saveOrUpdate(aur);
			}
			
			}else{
				System.out.println("交易订单号已存在,交易已完成,回调重复");
			}
			
		
		
		}catch(Exception e){
			e.printStackTrace();
			statusMap.put("status", -0x01);
			statusMap.put("message", "未知错误");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	
		return statusMap;
	}
	
	
	
	
	
	
	
	
	//分佣
		private double getOutMoney(Users user,ReGoodsorderItem item,String type){
			
			
			double re = 0;//返回分佣有的值
			
			
			try{
			
			List<Users> ulist =usersDao.findUsersByInvitecode(user.getInvitecode());
			Users 	inviteUser = null;
			if(ulist.size()>0){
				inviteUser= ulist.get(0);
			}
			
			//合伙人
			Map<Integer,AdminuserTaokePid> userspid = new HashMap<Integer,AdminuserTaokePid>();
			
			List<PartnerAdminuserPidDistribute> papdlist = usersPidDao.queryAllByIsValid2();
			
			if(papdlist!=null && papdlist.size()>0){
				for(PartnerAdminuserPidDistribute papd : papdlist){
					if(papd.getCityAdminuserPidDistribute()!=null){
						userspid.put(papd.getUsers().getId(), papd.getCityAdminuserPidDistribute().getAdminuserTaokePid());
					}
				}
				
			}
			//============================================ZL=============================================//
			if("SELLER".equals(type)){
				re= CalcUtil.mul(140d, item.getGoodQuantity());
				Integer panterPid =0;
				AdminuserTaokePid pid =null;
				if(userspid.get(user.getId())!=null){//购买者为合伙人
					double umoney = CalcUtil.mul(60d, item.getGoodQuantity());
					addMoneyForUsers(umoney,user,item.getOrder().getOrderCode(),user,item.getId(),"您将有一笔新的收益"+umoney+"元（预估收益），实际到账情况根据粉丝是否成功成交该商品而定。",null,null,-1);
					//addMoneyForUsers(umoney,user,item.getOrder().getOrderCode(),user,item.getId(),"合伙人自购产品获得分佣！",null,null);
					pid = (AdminuserTaokePid) userspid.get(user.getId());
				}else{
					if(inviteUser!=null){
						if(userspid.get(inviteUser.getId())!=null){//上级粉丝为合伙人
							double umoney = CalcUtil.mul(60d, item.getGoodQuantity());
							 addMoneyForUsers(umoney,inviteUser,item.getOrder().getOrderCode(),user,item.getId(),"您将有一笔新的收益"+umoney+"元（预估收益），实际到账情况根据粉丝是否成功成交该商品而定。",null,null,-1);
							//addMoneyForUsers(umoney,inviteUser,item.getOrder().getOrderCode(),user,item.getId(),"直接推广粉丝购买产品，合伙人获得分佣！",null,null);
							pid = (AdminuserTaokePid) userspid.get(inviteUser.getId());
						}else{//其他情况
							
							panterPid =getParentUsersByUsers(inviteUser,userspid);
							double umoney = CalcUtil.mul(50d, item.getGoodQuantity());
							 addMoneyForUsers(umoney,inviteUser,item.getOrder().getOrderCode(),user,item.getId(),"您将有一笔新的收益"+umoney+"元（预估收益），实际到账情况根据粉丝是否成功成交该商品而定。",null,null,-1);
							if(panterPid>0){
								//addMoneyForUsers(umoney,inviteUser,item.getOrder().getOrderCode(),user,item.getId(),"直接推广粉丝购买产品，推荐人获得分佣",null,null);
								 Users parentUsers = usersDao.findById(panterPid) ;
								 double umoney2 = CalcUtil.mul(60d, item.getGoodQuantity());
								 addMoneyForUsers(umoney2,parentUsers,item.getOrder().getOrderCode(),user,item.getId(),"您将有一笔新的收益"+umoney2+"元（预估收益），实际到账情况根据粉丝是否成功成交该商品而定。",null,null,-1);
								 //addMoneyForUsers(umoney2,parentUsers,item.getOrder().getOrderCode(),user,item.getId(),"合伙人系列粉丝购买，合伙人获得分佣",null,null);
								 pid = (AdminuserTaokePid) userspid.get(panterPid);
							}
						}
					}
				}
				
				if(pid !=null){
					
					
					try{
					//代理分佣
						
						QueryModel queryModel=new QueryModel();
						queryModel.clearQuery();
						queryModel.combEquals("parnerName", pid.getTkLoginLoginname());
						queryModel.combEquals("isValid", 1);
						
					List<ThePartner> tplist = dateBaseDAO.findPageList(ThePartner.class, queryModel, 0, 1);
					if(tplist!=null && tplist.size()>0){
						
						ThePartner tp = tplist.get(0);
						if(tp.getUserId()!=null && tp.getUserId()>0){
							Users dl = usersDao.findById(tp.getUserId());
							double umoney = CalcUtil.mul(20d, item.getGoodQuantity());
							 addMoneyForUsers(umoney,dl,item.getOrder().getOrderCode(),user,item.getId(),"您将有一笔新的收益"+umoney+"元（预估收益），实际到账情况根据粉丝是否成功成交该商品而定。",null,null,-1);
							//addMoneyForUsers(umoney,dl,item.getOrder().getOrderCode(),user,item.getId(),"合伙人粉丝购买产品，代理商获得分佣",null,null);
						}
						
					}
					}catch(Exception e){
						e.printStackTrace();
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					}
					//============================================ZL==============================================//
					
				}else{//代理商城市分佣（暂无）
					
				}
			}else if("LOCAL".equals(type)){
				ReGoodsOfLocalSpecialtyMall rolsm=null;
				if("lsm".equals(item.getMallClass())){
					 rolsm= 	reGoodsOfLocalSpecialtyMallDao.findById(item.getMallId());
				}
				
				double total =0.3;//总分佣
				double p =0.15;//合伙人
				double u=0.05;//直接推广粉丝
				double d= 0.05;//代理商
				if(rolsm.getFenyong()!=null){
					if(rolsm.getFenyong()==1){
						total =0.1;
						p = 0.03;
						u=0.05;
						d=0.02;
					}else if(rolsm.getFenyong()==2){
						total =0.2;
						p = 0.08;
						u=0.05;
						d=0.05;
					}
				}
				
				
				
				double res=total;
				re= CalcUtil.mul(item.getPayPrice(), res, 2);
				Integer panterPid =0;
				AdminuserTaokePid pid =null;
				if(userspid.get(user.getId())!=null){//购买者为合伙人
					double umoney = CalcUtil.mul(item.getPayPrice(), p, 2);
					addMoneyForUsers(umoney,user,item.getOrder().getOrderCode(),user,item.getId(),"合伙人自购产品获得分佣！",null,null,-1);
					res= CalcUtil.sub(res, p);
					pid = (AdminuserTaokePid) userspid.get(user.getId());
				}else{
					if(inviteUser!=null){
						if(userspid.get(inviteUser.getId())!=null){//上级粉丝为合伙人
							double umoney = CalcUtil.mul(item.getPayPrice(), p, 2);
							//addMoneyForUsers(umoney,inviteUser,item.getOrder().getOrderCode(),user,item.getId(),"直接推广粉丝购买产品，合伙人获得分佣！",null,null);
							addMoneyForUsers(umoney,inviteUser,item.getOrder().getOrderCode(),user,item.getId(),"您将有一笔新的收益"+umoney+"元（预估收益），实际到账情况根据粉丝是否成功成交该商品而定。",null,null,-1);
							res= CalcUtil.sub(res, p);
							pid = (AdminuserTaokePid) userspid.get(inviteUser.getId());
						}else{//其他情况
							
							panterPid =getParentUsersByUsers(inviteUser,userspid);
							if(panterPid>0){
								double umoney = CalcUtil.mul(item.getPayPrice(), u, 2);
								//addMoneyForUsers(umoney,inviteUser,item.getOrder().getOrderCode(),user,item.getId(),"直接推广粉丝购买产品，推荐人获得分佣",null,null);
								addMoneyForUsers(umoney,inviteUser,item.getOrder().getOrderCode(),user,item.getId(),"您将有一笔新的收益"+umoney+"元（预估收益），实际到账情况根据粉丝是否成功成交该商品而定。",null,null,-1);
								//res= res-0.05;
								res= CalcUtil.sub(res, u);
								 Users parentUsers = usersDao.findById(panterPid) ;
								
								 double umoney2 = CalcUtil.mul(item.getPayPrice(),p, 2);
								 //addMoneyForUsers(umoney2,parentUsers,item.getOrder().getOrderCode(),user,item.getId(),"合伙人系列粉丝购买，合伙人获得分佣",null,null);
								 addMoneyForUsers(umoney2,parentUsers,item.getOrder().getOrderCode(),user,item.getId(),"您将有一笔新的收益"+umoney2+"元（预估收益），实际到账情况根据粉丝是否成功成交该商品而定。",null,null,-1);
								 //res= res-0.15;
								 res= CalcUtil.sub(res, p);
								 pid = (AdminuserTaokePid) userspid.get(panterPid);
							}
						}
					}
				}
				
				if(pid !=null){
					QueryModel queryModel=new QueryModel();
					queryModel.clearQuery();
					queryModel.combEquals("parnerName", pid.getTkLoginLoginname());
					queryModel.combEquals("isValid", 1);
					
					List<ThePartner> tplist = dateBaseDAO.findPageList(ThePartner.class, queryModel, 0, 1);
					if(tplist!=null && tplist.size()>0){
						ThePartner tp = tplist.get(0);
						if(tp.getUserId()!=null && tp.getUserId()>0){
							Users dl = usersDao.findById(tp.getUserId());
							double umoney = CalcUtil.mul(item.getPayPrice(),d, 2);
							
							addMoneyForUsers(umoney,dl,item.getOrder().getOrderCode(),user,item.getId(),"您将有一笔新的收益"+umoney+"元（预估收益），实际到账情况根据粉丝是否成功成交该商品而定。",null,null,-1);
							//res= res-0.05;
							res= CalcUtil.sub(res, d);
						}
						
					}
				}else{//代理商城市分佣（暂无）
					
				}
				
				
				//给平台分佣
				double tmoney = CalcUtil.mul(item.getPayPrice(), res, 2);
				if(tmoney>0){
				AdminUser aus = adminUserDao.findById(1);//平台管理员
				addMoneyForAdminUser(tmoney,aus,user,item.getOrder().getOrderCode(),res+"",item,null,null,null);
				}
			}
			
			}catch(Exception e){
				System.out.println("分佣出错");
				e.printStackTrace();
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			
			return re;
		}
		
		
		//拼团分佣
		private void teamCommission(Users user,ReGoodsorderItem item){
			
			try{
				Double pingMoney = CalcUtil.mul(item.getPayPrice(),0.03, 2);
				double hhr=CalcUtil.mul(pingMoney, 0.6,2);
				double syhhr=CalcUtil.mul(pingMoney, 0.2,2);
				double dl=CalcUtil.mul(pingMoney, 0.15,2);
				
				QueryModel queryModel = new QueryModel();
				queryModel.combPreEquals("users.id", user.getId(),"usersId");
				queryModel.setOrder("id desc");
				List<UserLoginRecord> ulist = dateBaseDAO.findLists(UserLoginRecord.class, queryModel);
				Integer lastzoneid = null;
				if(ulist!=null && ulist.size()>0){
					UserLoginRecord ur = ulist.get(0);
					lastzoneid = ur.getZoneId();
				}
				Map<String, Integer> usersMap = getUsersMap(user,lastzoneid);
				
				//发送信息
				
				if(usersMap.get("csyys")!=null && usersMap.get("csyys")>0){
					Integer csyys = usersMap.get("csyys");
					System.out.println("运营商："+csyys);
						AdminUser csyysUser = adminUserDao.findById(csyys);
						String csyyshhrRes="拼团商品"+item.getGoodName()+"成交,推广者所在城市运营商获得收益"+dl+"元（预估收益），确认收货后即可使用";
						addMoneyForAdminUser(dl, csyysUser, user, item.getOrder().getOrderCode(), csyyshhrRes, item, null, null,null);
					
					
				}
				
				Integer syhhrId = usersMap.get("syhhr");
				Users syhhrUser = usersDao.findById(syhhrId);
				String syhhrRes="拼团商品"+item.getGoodName()+"成交,推广者上级事业合伙人获得收益"+syhhr+"元（预估收益），确认收货后即可使用";
				addMoneyForUsers(syhhr,syhhrUser,item.getOrder().getOrderCode(),user,item.getId(),syhhrRes,null,null,-1);
				
				
				if(usersMap.containsKey("hhr")){
					//有合伙人
					Integer hhrUserId=usersMap.get("hhr");
					Users hhrUser = usersDao.findById(hhrUserId);
					String hhrRes="拼团商品"+item.getGoodName()+"成交,推广者获得收益"+hhr+"元（预估收益），确认收货后即可使用";
					addMoneyForUsers(hhr,hhrUser,item.getOrder().getOrderCode(),user,item.getId(),hhrRes,null,null,-1);
				}else{
					//无合伙人
					
					String syhh_hhrRes="拼团商品"+item.getGoodName()+"成交,事业合伙人推广获得收益"+hhr+"元（预估收益），确认收货后即可使用";
					addMoneyForUsers(hhr,syhhrUser,item.getOrder().getOrderCode(),user,item.getId(),syhh_hhrRes,null,null,-1);
				}
				}catch(Exception e){
					System.out.println("拼团分佣出错");
					e.printStackTrace();
				}
			
			
			
			
		}
	
	private Map<String ,Integer > getUsersMap(Users u,Integer zoneId){
		int syhhr=0;
		Map<String ,Integer> map = new HashMap<String,Integer>();
		
		List<TkldPid> tld = tkldPidDao.findByPropertyIsValid("users.id", u.getId());
		if(tld!=null && tld.size()>0){//购物者为事业合伙人或者合伙人时
			TkldPid tpid= tld.get(0);
			if(tpid.getLevel()==3){
				map.put("hhr", u.getId());
				if(tpid.getTkldPid()!=null && tpid.getTkldPid().getUsers()!=null){
					//兜底事业合伙人不给收益
					map.put("syhhr", tpid.getTkldPid().getUsers().getId());
				}
				if(tpid.getTkldPid()!=null && tpid.getTkldPid().getTkldPid()!=null && tpid.getTkldPid().getTkldPid().getAdminUser()!=null){
					map.put("csyys", tpid.getTkldPid().getTkldPid().getAdminUser().getId());
				}
				
			}else if(tpid.getLevel()==2){
				
				if(tpid.getIsCareerPartner() !=null &&tpid.getIsCareerPartner()==2){
					map.put("hhr", u.getId());
				}else{
					map.put("syhhr", u.getId());
					syhhr=50;
				}
				if(tpid.getTkldPid()!=null &&  tpid.getTkldPid().getAdminUser()!=null){
					map.put("csyys", tpid.getTkldPid().getAdminUser().getId());
				}
			}
		}else{
			map.put("gmfs", u.getId());
			List<Users> ulist =usersDao.findUsersByInvitecode(u.getInvitecode());//推荐人
			Users 	inviteUser = null;
			if(ulist.size()>0){
				inviteUser= ulist.get(0);
			}
			if(inviteUser!=null){//有要求人
			List<TkldPid> tjld = tkldPidDao.findByPropertyIsValid("users.id", inviteUser.getId());
			if(tjld!=null && tjld.size()>0){
				TkldPid tpid= tjld.get(0);
				if(tpid.getLevel()==3){
					if(tpid.getUsers()!=null){
						map.put("hhr", tpid.getUsers().getId());
					}
					
					if(tpid.getTkldPid()!=null && tpid.getTkldPid().getUsers()!=null){
						map.put("syhhr", tpid.getTkldPid().getUsers().getId());
					}
					if(tpid.getTkldPid()!=null && tpid.getTkldPid().getTkldPid()!=null && tpid.getTkldPid().getTkldPid().getAdminUser()!=null){
						map.put("csyys", tpid.getTkldPid().getTkldPid().getAdminUser().getId());
					}
				}else if(tpid.getLevel()==2){
					if( tpid.getUsers()!=null){
						if(tpid.getIsCareerPartner()!=null && tpid.getIsCareerPartner()==2){
							map.put("hhr", u.getId());
						}else{
							map.put("syhhr", u.getId());
							syhhr=50;
						}
					}
					if(tpid.getTkldPid()!=null  && tpid.getTkldPid().getAdminUser()!=null){
						map.put("csyys", tpid.getTkldPid().getAdminUser().getId());
					}
				}
			}else{
				map.put("tjr", inviteUser.getId());
				TkldPid tpid = getPidForNew(inviteUser,0);//推荐人系列有合伙人人
				if(tpid!=null){
					if(tpid.getLevel()==3){
						if(tpid.getUsers()!=null){
							map.put("hhr", tpid.getUsers().getId());
						}
						
						if(tpid.getTkldPid()!=null && tpid.getTkldPid().getUsers()!=null){
							map.put("syhhr", tpid.getTkldPid().getUsers().getId());
						}
						if(tpid.getTkldPid()!=null && tpid.getTkldPid().getTkldPid()!=null && tpid.getTkldPid().getTkldPid().getAdminUser()!=null){
							map.put("csyys", tpid.getTkldPid().getTkldPid().getAdminUser().getId());
						}
					}else if(tpid.getLevel()==2){
						if(tpid.getUsers()!=null){
							if(tpid.getIsCareerPartner() !=null && tpid.getIsCareerPartner()==2){
								map.put("hhr", u.getId());
							}else{
								map.put("syhhr", u.getId());
								syhhr=50;
							}
						}
						if(tpid.getTkldPid()!=null && tpid.getTkldPid().getAdminUser()!=null){
							map.put("csyys", tpid.getTkldPid().getAdminUser().getId());
						}
					}
				}else{
					//地区事业合伙人
					if(zoneId!=null){
						TkldPid tpid2 = getTkldPidByZoneId(zoneId);//所在地区有事业合伙人
						if(tpid2!=null){
							if(tpid2.getUsers()!=null){
								map.put("syhhr", tpid2.getUsers().getId());
							}else{
								map.put("syhhr", 48995);
							}
							if(tpid2.getTkldPid()!=null &&  tpid2.getTkldPid().getAdminUser()!=null){
								map.put("csyys", tpid2.getTkldPid().getAdminUser().getId());
							}
						}else{//默认
							map.put("syhhr", 48995);
							map.put("csyys", 1071);
						}
					}else{
						map.put("syhhr", 48995);
						map.put("csyys", 1071);
					}
					
				}
				
			}
			}else{//无邀请人
				
				if(zoneId!=null){
					TkldPid tpid2 = getTkldPidByZoneId(zoneId);//所在地区有事业合伙人
					if(tpid2!=null){
						if(tpid2.getTkldPid()!=null && tpid2.getTkldPid().getUsers()!=null){
							map.put("syhhr", tpid2.getTkldPid().getUsers().getId());
						}
						if(tpid2.getTkldPid()!=null && tpid2.getTkldPid().getTkldPid()!=null && tpid2.getTkldPid().getTkldPid().getAdminUser()!=null){
							map.put("csyys", tpid2.getTkldPid().getTkldPid().getAdminUser().getId());
						}
					}else{//默认
						map.put("syhhr", 48995);
						map.put("csyys", 1071);
					}
				}else{
					map.put("syhhr", 48995);
					map.put("csyys", 1071);
				}
				
			}
			
		}
		map.put("syhhrfybl", syhhr);
		return map; 
	}
		
	
	private TkldPid getTkldPidByZoneId(Integer zoneId){
		TkldPid tp=null;
		ProvinceEnum pe = provinceEnumDao.findById(zoneId);
		
		Integer provinceid = 1961;
		
		if(pe!=null){
			if(pe.getLevel2()==3){//区；或者城区县
				provinceid= pe.getProvinceEnumId();
			}else if(pe.getLevel2()==2){//县，县级市
				provinceid=pe.getId();
			}
		}
		
		QueryModel queryModel=new QueryModel();
		queryModel.clearQuery();
		//找到当前地区的事业合伙人
		
		queryModel.combPreEquals("isValid", true);
		queryModel.combPreEquals("provinceEnum.id", provinceid,"zoneId");
		queryModel.combEquals("level", 2);
		//事业合伙人	区级
		List<TkldPid> dList  = dateBaseDAO.findLists(TkldPid.class, queryModel);
		if(dList!=null && dList.size()>0){
			
			tp=dList.get(0);
			System.out.println("用户在"+pe.getName()+"支付购买合伙人资格；未找到粉丝关系对应事业伙人，自动获取当地兜底事业合伙人"+tp.getUsers().getId());
		}
		
		
		return tp;
	}
	
	//分佣(积分归属商家-20%,商品归属商家-16%, 奖金池+4%）
	private double getOutMoneyForScore(Users user, ReGoodsorderItem item,
			String type, Integer zoneId, double tgf) {

		double money = 0;
		try {
			int payScore = 0;
			if(item.getPayScore()>0){
				payScore = item.getPayScore(); // 剩余的就是平台的
				//商品归属商家 
				double fenyong = CalcUtil.mul(payScore, 0.15,4); //0.16
				Seller seller = item.getReGoodsOfSellerMall().getSnapshotGoods().getSeller();
				AdminUser adminUser = seller.getAdminUser();
				double cashPoint = adminUser.getMoney()==null?0.0:adminUser.getMoney();
				adminUser.setMoney(cashPoint + fenyong);
				adminUserDao.saveOrUpdate(adminUser);
				
				AdminuserCashpointRecord acr = new AdminuserCashpointRecord();
				acr.setAdminUser(adminUser);
				acr.setAfterpoint(cashPoint + fenyong);
				acr.setBeforepoint(cashPoint);
				acr.setCashpoint(fenyong);
				acr.setCreateTime(new Timestamp(System.currentTimeMillis()));
				acr.setIsValid(true);
				acr.setRemark("用户"+user.getRealname()+",兑换商品使用"+payScore+"积分,获得分佣:"+fenyong+"元");
				acr.setOrderItem(item);
				acr.setUsers(user);
				acr.setType(1);
				acr.setIsDeposit(4);
				adminuserCashpointRecordDAO.save(acr);
				
				//1% 分给该地区的前100名商家
				getMoneySeller_100(item,seller,user,1);
				
				//处理用户消费积分
				scoreMarkService.saveScoreToform_1(user,payScore,seller,1,item);
				
				if(adminUser.getUsers() != null && adminUser.getUsers().getUnionId() != null){
					String param = "unionId="+adminUser.getUsers().getUnionId()+"&linkType=2&price="+fenyong;
					UrlUtil.sendGzhMsg(9, param);
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return money;
	}
	
	
	
	private void getMoneySeller_100(ReGoodsorderItem item,Seller seller,Users user ,Integer type){
		AdminUser adminUser = seller.getAdminUser();
		double fy = CalcUtil.mul(item.getPayScore(), 0.01,4); //0.01
		QueryModel model = new QueryModel();
		model.clearQuery();
		model.combPreEquals("isValid", true); 
		model.combPreEquals("parentId", adminUser.getParentAdminUser().getId(),"parent_id");
		model.combPreEquals("level", 60);
		model.setOrder("createtime ASC");
		List<AdminUser> ads = dateBaseDAO.findPageList(AdminUser.class, model, 0, 99);
		for(AdminUser ad :ads){
			double cashPoint = ad.getMoney() == null?0.0:ad.getMoney();
			AdminuserCashpointRecord adr = new AdminuserCashpointRecord();
			adr.setAdminUser(ad);
			adr.setAfterpoint(cashPoint + fy);
			adr.setBeforepoint(cashPoint);
			adr.setCashpoint(fy);
			adr.setCreateTime(new Timestamp(System.currentTimeMillis()));
			adr.setIsValid(true);
			adr.setRemark("用户:"+user.getRealname()+",消费"+item.getPayScore()+"积分,您作为该地区前100名商家获得"+fy+"元分佣");
			adr.setUsers(user);
			adr.setOrderItem(item);
			adr.setGoodsId(item.getGoodsId());
			adr.setIsDeposit(4);
			if(type == 2){ //积分夺宝处理
				adr.setType(-1);
			}else if(type == 1){ //普通积分处理
				adr.setType(1);
				ad.setMoney(CalcUtil.add(cashPoint, fy, 4));
				adminUserDao.saveOrUpdate(ad);
			}
			adminuserCashpointRecordDAO.save(adr);
		}
	}
	
	
	
	//分佣(代理30%，事业合伙人15%，合伙人40%，推荐人5%，购买者5%）
	//分佣(代理30%，事业合伙人50%，，推荐人5%，购买者5%）	
	private double getOutMoneyForNew(Users user, ReGoodsorderItem item,
			String type, Integer zoneId, double tgf) {

		double money = 0;
		try {

			ReGoodsOfLocalSpecialtyMall rolsm = null;
			if ("lsm".equals(item.getMallClass())) {
				rolsm = reGoodsOfLocalSpecialtyMallDao.findById(item.getMallId());
			}

			double total = 0.3;// 总分佣
			if (rolsm.getFenyong() != null) {
				if (rolsm.getFenyong() == 1) {
					total = 0.1;

				} else if (rolsm.getFenyong() == 2) {
					total = 0.2;
				}
			}

			if (item.getPayPrice() > 0) {
				double paymoney = CalcUtil.sub(item.getPayPrice(), tgf);
				money = CalcUtil.mul(paymoney, total, 2);// 分佣总金额
				Map<String, Integer> map = getUsersMap(user, zoneId);
				if (map.get("gmfs") != null && map.get("gmfs") > 0) {// 自购粉丝
					Users u = usersDao.findById(map.get("gmfs"));

					double umoney = 0d;
					if ("LOCAL".equals(type)) {
						umoney = CalcUtil.mul(money, 0.05, 2);
					} else if ("SELLER".equals(type)) {
						umoney = 0;
					}
					// 粉丝自购商品
					if (umoney > 0) {
						addMoneyForUsers(umoney, u, item.getOrder().getOrderCode(), user, item.getId(), "自购商品'"
								+ item.getGoodName() + "'收益" + umoney
								+ "元（预估收益），确认收货后即可使用", null, null, -1);
					}

				}

				if (map.get("tjr") != null && map.get("tjr") > 0) {// 推荐人粉丝
					Users u = usersDao.findById(map.get("tjr"));
					double umoney = 0d;
					if ("LOCAL".equals(type)) {
						umoney = CalcUtil.mul(money, 0.05, 2);
					} else if ("SELLER".equals(type)) {
						umoney = 50 * item.getGoodQuantity();
					}
					if (umoney > 0) {
						addMoneyForUsers(umoney, u, item.getOrder().getOrderCode(), user, item.getId(),
								"下级粉丝购买商品'" + item.getGoodName() + "'收益"
										+ umoney + "元（预估收益），下级粉丝确认收货后即可使用",
								null, null, -1);
					}
				}

				if (map.get("hhr") != null && map.get("hhr") > 0) {// 合伙人粉丝
					Users u = usersDao.findById(map.get("hhr"));
					// double umoney = CalcUtil.mul(money, 0.3, 2);//粉丝自购商品

					double umoney = 0d;
					if ("LOCAL".equals(type)) {
						umoney = CalcUtil.mul(money, 0.5, 2);
					} else if ("SELLER".equals(type)) {
						umoney = 35 * item.getGoodQuantity();
					}
					if (umoney > 0) {
						addMoneyForUsers(umoney, u, item.getOrder().getOrderCode(), user, item.getId(), "粉丝购买商品'"
								+ item.getGoodName() + "'合伙人收益" + umoney
								+ "元（预估收益），粉丝确认收货后即可使用", null, null, -1);
					}

				}

				if (map.get("syhhr") != null && map.get("syhhr") > 0) {// 事业合伙人
					Users u = usersDao.findById(map.get("syhhr"));

					double fybl = 0.20;
					String str = "合伙人推广的";
					if (map.get("syhhrfybl") > 0) {
						fybl = CalcUtil.div(map.get("syhhrfybl"), 100);
						str = "自己推广的";
					}

					double umoney = 0d;
					if ("LOCAL".equals(type)) {
						umoney = CalcUtil.mul(money, fybl, 2);
					} else if ("SELLER".equals(type)) {
						umoney = 25 * item.getGoodQuantity();
					}
					if (umoney > 0) {
						List<TkldPid> tlp = tkldPidDao.findByPropertyIsValid("users.id", u.getId());
						if (tlp != null && tlp.size() > 0) {
							TkldPid tp = tlp.get(0);
							if (tp.getLevel() == 2 && tp.getIsCareerPartner() != null && tp.getIsCareerPartner() == 2) {
								addMoneyForUsersForGJhhr(umoney, u, item.getOrder().getOrderCode(), user,item.getId(),
										str + "粉丝购买商品'" + item.getGoodName()
												+ "'高级合伙人收益" + umoney
												+ "元（预估收益），确认收货后即可使用", null,null);
							} else if (tp.getLevel() == 2) {
								addMoneyForUsers(umoney, u, item.getOrder().getOrderCode(), user, item.getId(),str + "粉丝购买商品'" + item.getGoodName()
												+ "'事业合伙人收益" + umoney
												+ "元（预估收益），确认收货后即可使用", null,
										null, -1);
							}

						}

					}
				}

				if (map.get("csyys") != null && map.get("csyys") > 0) {// 城市运营商
					AdminUser au = adminUserDao.findById(map.get("csyys"));
					// double umoney = CalcUtil.mul(money, 0.3, 2);//粉丝自购商品
					double umoney = 0d;
					if ("LOCAL".equals(type)) {
						umoney = CalcUtil.mul(money, 0.2, 2);
					} else if ("SELLER".equals(type)) {
						umoney = 15 * item.getGoodQuantity();
					}
					if (umoney > 0) {
						addMoneyForAdminUser(umoney, au, user, item.getOrder().getOrderCode(), "粉丝购买商品'" + item.getGoodName()
								+ "'城市运营商收益：" + umoney + "元，确认收货后即可使用", item,
								null, null, null);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return money;
	}
	
	
	
	public Integer getParentUsersByUsers(Users users, Map<Integer,AdminuserTaokePid> userspid){
		return getPid(users,userspid,0);
	}
	
	//通过递归方式查找商家用户的pid；找到为止，不限制层级
	public Integer getPid(Users users,Map<Integer,AdminuserTaokePid> userspid,int times){
		Integer parentUserId =0;
		
		if(times>15){
			return parentUserId;
		}
		
		
		if(users.getInvitecode()!=null){
			times++;
			List<Users> parentUser3 = usersDao.findUsersByInvitecode(users.getInvitecode());
			if(parentUser3!=null && parentUser3.size()>0){
				 Users	pUsers3 = parentUser3.get(0);
					if(userspid.get(pUsers3.getId())!=null){
						return pUsers3.getId();
					}else{
						parentUserId=getPid(pUsers3,userspid,times);
					}
				}
		}
		
		
		return parentUserId;
	}
	
	
	//通过递归方式查找商家用户的pid；找到为止，不限制层级
		public TkldPid getPidForNew(Users users,int times){
			TkldPid tp =null;
			
			if(times>15){
				return tp;
			}
			if(users.getInvitecode()!=null){
				times++;
				List<Users> parentUser3 = usersDao.findUsersByInvitecode(users.getInvitecode());
				if(parentUser3!=null && parentUser3.size()>0){
					 Users	pUsers3 = parentUser3.get(0);
					 
					 List<TkldPid> tjld = tkldPidDao.findByPropertyIsValid("users.id", pUsers3.getId());
					 
					 
						if(tjld !=null && tjld.size()>0){
							return tjld.get(0);
						}else{
							tp=getPidForNew(pUsers3,times);
						}
					}
			}
			
			
			return tp;
		}
	
	
	public void addMoneyForAdminUser(double momey,AdminUser au ,Users user,String code,String res,ReGoodsorderItem item,Users tsuser,HttpServletRequest request,
			HttpServletResponse response){
		AdminuserCashpointRecord acr = new AdminuserCashpointRecord();
		acr.setAdminUser(au);
		acr.setBeforepoint(au.getMoney());
		acr.setCashpoint(momey);
		acr.setAfterpoint(CalcUtil.add(au.getMoney(), momey, 4));
		acr.setCardCode(code);
		acr.setType(-1);//未确认
		if(StringUtils.isBlank(res)){
			acr.setRemark("粉丝"+user.getRealname()+"购买产品商家;");
		}else{
			acr.setRemark(res);
		}
		acr.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
		acr.setIsValid(true);
		acr.setOrderItem(item);
		acr.setUsers(user);
		adminuserCashpointRecordDao.save(acr);
		List<AdminUser> ulist = new ArrayList<AdminUser>();
		ulist.add(au);
		if (item.getOrder().getStatus()>5) {
			
			userSystemMessageService.saveMessageForAdmin("2",res, StringUtil.MESSAGE_FENYONG, "销售信息", ulist, "", momey,1);//发消息给后台用户 
		}
		
		if(tsuser!=null){
		UrlUtil u = new UrlUtil();
		if (item.getOrder().getStatus()>5) {
			u.send(tsuser.getName(), "您好，"+item.getGoodName()+"用户"+user.getRealname()==null?user.getName():user.getRealname()+"已经成功付款，订单号："+item.getOrder().getOrderCode()+"，请尽快确认订单，进行发货！");
		}
		
		}
		
		
	}
	
	private void addMoneyForUsers(double money,Users user,String code,Users from,Integer itemid,String remark,HttpServletRequest request,
			HttpServletResponse response,Integer type){
		CashmoneyRecord cr = new CashmoneyRecord();
		cr.setBeforeMoney(0d);
		cr.setAfterMoney(0d);
		cr.setMoney(money);
		cr.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
		cr.setIsValid(true);
		cr.setUsersByUserId(user);
		cr.setUsersByFromUsers(from);
		cr.setOrderString(code);
		cr.setType(type);//未确认
		cr.setRelateId(itemid);
		cr.setRemark(remark);
		cashmoneyRecordDao.save(cr);
		try{
		if (user!=null) {
			List<Users> ulist = new ArrayList<Users>();
			ulist.add(user);
			userSystemMessageService.saveMessage("1",remark, StringUtil.MESSAGE_FENYONG, "分佣信息", ulist, "", money,1);
			
		}
		}catch(Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}
	
	//高级合伙人分佣只做记录，不做确认，待升级后才
	private void addMoneyForUsersForGJhhr(double money,Users user,String code,Users from,Integer itemid,String remark,HttpServletRequest request,
			HttpServletResponse response){
		UsersMoneyRecord cr = new UsersMoneyRecord();
		cr.setBeforeMoney(0d);
		cr.setAfterMoney(0d);
		cr.setMoney(money);
		cr.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
		cr.setIsValid(true);
		
		cr.setOrderString(code);
		cr.setType(-1);//未确认
		
		cr.setRemark(remark);
		usersMoneyRecordDao.save(cr);
		
	}
	
	
	
	public Map<String, Object> payOrders(HttpServletRequest request,Integer userId,String orderIds,String payType,Double thirdParty) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try{
			statusMap.put("status", 0x01);
			statusMap.put("message", "支付成功");
			if(thirdParty==null){
				thirdParty = 0.00;
			}
			if(this.getPayCache(request, userId)){
				return JsonResponseUtil.getJson(-0x00a5,"用户支付请求处理中，请稍后");
			}else{
				this.setPayCache(request, userId, true);
			}
			
			Users user = usersDao.findById(userId);
			List<ReGoodsorder> orders = reGoodsorderDao.getToBePaidOrders(userId,orderIds);
			
			if(orders==null||orders.size()==0){
				this.setPayCache(request, userId, false);
				return JsonResponseUtil.getJson(-0x00a6,"无可支付订单");
			}
			
			double cashpoint = user.getCashPoints();
			double money = user.getMoney();
			int score = user.getScore();
			int memCount = 0;//会员商城商品
			//免单券张数
			Integer freeVoucherCout = freeVoucherRecordDao.getCountByUserId(user.getId());
			freeVoucherCout = freeVoucherCout==null?0:freeVoucherCout;
			String remark="支付订单：";
			for(ReGoodsorder order : orders){
				//cashpoint = cashpoint - order.getPayCashpoint();
				cashpoint = CalcUtil.sub(cashpoint, order.getPayCashpoint());
				//score = score - order.getPayScore();
				score = (int) CalcUtil.sub(score, order.getPayScore());
				//如果是第三方支付，则直接扣第三方金额
				if(payType.equals("ALIPAY")||payType.equals("WEIXIN")){
					//thirdParty = thirdParty - order.getPayPrice();
					thirdParty = CalcUtil.sub(thirdParty, order.getPayPrice());
					if(order.getLogisticsType()!=null){
						//thirdParty = thirdParty - order.getLogisticsType();//邮费
						thirdParty = CalcUtil.sub(thirdParty, order.getLogisticsType());//邮费
					}
				}else{
					//remark+=order.getId()+",";
					remark= CalcUtil.add(Double.valueOf(remark), order.getId())+",";
					//money = money - order.getPayPrice();
					money = CalcUtil.sub(money, order.getPayPrice());
					if(order.getLogisticsType()!=null){
						//money = money - order.getLogisticsType();//邮费
						money = CalcUtil.sub(money, order.getLogisticsType());//邮费
					}
				}
				List<ReGoodsorderItem> items = reGoodsorderItemDao.findByParent(order.getId());
				for(ReGoodsorderItem item : items){
					if(item.getMallClass().equals(ReBaseGoods.MemberMall)){
						//memCount = memCount + item.getGoodQuantity();
						memCount = (int) CalcUtil.add(memCount, item.getGoodQuantity());
					}
				}
			}
			
			if(thirdParty<0){
				System.out.println("第三方支付现金不足");
			}else if(cashpoint<0){
				statusMap.put("status", -0x00a2);
				statusMap.put("message", "红包不足");
				this.setPayCache(request, userId, false);
				return statusMap;
			}else if(score<0){
				statusMap.put("status", -0x00a3);
				statusMap.put("message", "积分不足");
				this.setPayCache(request, userId, false);
				return statusMap;
			}else if(money<0){
				statusMap.put("status", -0x00a1);
				statusMap.put("message", "钱包余额不足");
				this.setPayCache(request, userId, false);
				return statusMap;
			//}else if((freeVoucherCout-memCount)<0){
			}else if((CalcUtil.sub(freeVoucherCout, memCount))<0){
				statusMap.put("status", -0x00a4);
				statusMap.put("message", "免单券不足");
				this.setPayCache(request, userId, false);
				return statusMap;
			}
			
			//保存商家临时收益记录
			try{
				for(ReGoodsorder order : orders){
					Seller seller = order.getSeller();//订单商家
					if(seller.getMoney()==null){
						seller.setMoney(0d);
					} 
					Double postage = order.getLogisticsType()==null?0d:order.getLogisticsType();
					Double total = CalcUtil.add(order.getPayPrice(), postage, 2);
					Double balance = CalcUtil.add(seller.getMoney(), total); //商家最终余额
					sellerMoneyRecordDao.saveRecord(total, balance, seller, 
							false, ISellerMoneyRecordDao.BUY, order.getId(), ReGoodsorder.class);
				}
			}catch(Exception e){
				e.printStackTrace();
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			
			//修改订单状态
			for(ReGoodsorder order : orders){
				//如果是上门配送型，则直接将状态改成待兑换
				Integer orderStatus = -1;
				if(order.getLogistics().equals(ReGoodsorder.shang_men_zi_qu+"")
						||order.getLogistics().equals(ReGoodsorder.dao_dian_xiao_fei+"")){
					orderStatus = ReGoodsorder.dai_dui_huan;
				}else{
					orderStatus = ReGoodsorder.dai_que_ren;
				}
				//===================================
				order.setStatus(orderStatus);
				order.setPayType(order.getPayTypeNum(payType));
				reGoodsorderDao.update(order);
				List<ReGoodsorderItem> items = reGoodsorderItemDao.findByParent(order.getId());
				for(ReGoodsorderItem item : items){
					item.setStatus(orderStatus);
					reGoodsorderItemDao.update(item);
					//添加销量
					reBaseGoodsDao.addGoodsSalesVolume(item.getMallClass()+item.getGoodsId(), item.getGoodQuantity());
					//减少库存
					updateGoodsStock(item, item.getGoodQuantity(), false);
					//saveRedPaperOrderLog(item.getId());
					//修改红包明细
					try{
						saveRedPaperOrderLog(item.getId());
					}catch(Exception e){
						e.printStackTrace();
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					}
				}
			}
		
			//添加明细
			if (CalcUtil.sub(user.getMoney(), money)!= 0) {
				//cashmoneyRecordDao.updateRecord(user, money-user.getMoney(), ICashmoneyRecordDao.BUY,remark);
				cashmoneyRecordDao.updateRecord(user, CalcUtil.sub(money, user.getMoney()), ICashmoneyRecordDao.BUY,remark);
			}
			if (CalcUtil.sub(user.getCashPoints(), cashpoint)!= 0) {
				//cashpointsRecordDao.updateRecord(user,cashpoint-user.getCashPoints(), ICashpointsRecordDao.BUY);
				cashpointsRecordDao.updateRecord(user,CalcUtil.sub(cashpoint, user.getCashPoints()), ICashpointsRecordDao.BUY);
			}
			if (CalcUtil.sub(user.getScore(), score)!= 0) {
				//scorerecordsDao.updateRecord(user, score-user.getScore(), ScorerecordsDAO.BUY);
				scorerecordsDao.updateRecord(user, (int)CalcUtil.sub(score, user.getScore()), ScorerecordsDAO.BUY);
			}
			
			//消耗免单券
			if(memCount>0){
				int co = freeVoucherRecordDao.updateStatus(userId, memCount, 1);
			}
			
			user.setCashPoints(cashpoint);
			user.setScore(score);
			user.setMoney(money);
			// 发短信
			//更新用户账户余额及订单状态
			usersDao.update(user);
		
		}catch(Exception e){
			e.printStackTrace();
			statusMap.put("status", -0x01);
			statusMap.put("message", "未知错误");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		this.setPayCache(request, userId, false);
		return statusMap;
	}
	
	
	public String getIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("x-forwarded-for");  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}  
	
	
	
	
	
	/**
	 * 前端支付前获得微信签名
	 */
	@Override
	public Map<String, Object> getWeixinScan(HttpServletRequest request, HttpServletResponse response,String t) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try{
			Map<String,Object> dataMap = new HashMap<>();
			statusMap.put("status", 0x01);
			statusMap.put("message", "请求成功");
			statusMap.put("data", dataMap);
			
			String basePath = request.getServletContext().getAttribute("BASEPATH_IN_SESSION").toString();
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			JSONObject object = parameter.getData();
			Integer userId = Integer.parseInt(parameter.getUserId());
			Integer type= parameter.getData().getInteger("type")==null?0:parameter.getData().getInteger("type"); // 是否合并支付    1:0
			String cip =parameter.getData().getString("cip")==null?getIpAddr(request):parameter.getData().getString("cip"); // ip
			
			
			String os= parameter.getOs();
			if(os==null){
				os= request.getParameter("os");
			}
		
			object.put("userId", userId);
			
			StringBuffer orderIds = new StringBuffer();
			JSONArray array = JSONArray.parseArray(object.getString("orderIds"));
			for(int i=0;i<array.size();i++){
				String order= array.getString(i);
				order=order.substring(5,order.length()-5);
				if(i==(CalcUtil.sub(array.size(), 1))){
					orderIds.append(order);
				}else{
					orderIds.append(order+",");
				}
			}
			
			//检查商品状态
			Map<String, Object> checkMap = checkOrderBeforePay(request, orderIds.toString(), userId);
			if(Integer.parseInt(checkMap.get("status").toString())<0x01){
				return checkMap;
			}
			List<ReGoodsorder> orders = reGoodsorderDao.getToBePaidOrders(userId, orderIds.toString());
			if(orders.size()<orderIds.toString().split(",").length){
				statusMap.put("status", -0xa6);
				statusMap.put("message", "请求队列中存在已支付或未确认订单");
				return statusMap;
			}
			
			Users users = usersDao.findById(userId);
			int i=0;
			Double totalPrice = 0.00;
			String orderCode = "";
			for(ReGoodsorder order : orders){
				orderCode = order.getOrderCode();
//				totalPrice = totalPrice + order.getPayPrice();
//				totalPrice = totalPrice + order.getLogisticsType();//邮费
				totalPrice = CalcUtil.add(totalPrice, order.getPayPrice(), 2);
				totalPrice = CalcUtil.add(totalPrice, order.getLogisticsType(), 2);//邮费	
			}
			
	/*		//用户优惠券集合
			List<UserCoupons> userCouponsList = userCouponsService.getUserAllCouponsList(userId);
			Double  totalDiscount = getTotalDiscount(orderIds.toString(),userCouponsList); //总优惠价
			if(totalDiscount>0){
				totalPrice=CalcUtil.sub(totalPrice, totalDiscount);
			}*/
			
			if(type!=null&&type==1){ //合并支付
				if(users.getMoney()>0&&users.getMoney()<=totalPrice){
					totalPrice=CalcUtil.sub(totalPrice, users.getMoney()); //
					if(totalPrice<=0){
						statusMap.put("status", -3);
						statusMap.put("message", "钱包余额充足,请使用钱包支付");
						return statusMap;
					}
					
				}
			}
			
			
			object.put("type", type);
			object.put("totalPrice", totalPrice);
			object.put("orderCode", orderCode);
			
			String orderjson = object.toJSONString();
			
			String notify = basePath+"invoke/order/weixinNotify";
			
			PayParameter payParameter = returnParameterByHtml(request, notify, orderjson, orderCode, totalPrice+"",cip);
			long timeStamp = new Date().getTime();
			if("IOS".equals(os)){
				timeStamp=Long.parseLong(String.valueOf(new Date().getTime()).toString().substring(0,10));		
			}else{
				timeStamp = new Date().getTime();
			}
			if("JSAPI800".equals(t)){
				
				String prrepayId[] = WeixinUtil.getPrepayIdByGzh(payParameter,"JSAPI");
				
			
				dataMap.put("prepayId", prrepayId[1]);
				dataMap.put("sign", WeixinUtil.getPaySign(payParameter,prrepayId[1],timeStamp));
				dataMap.put("partnerid", WeixinConfig.gzh_mch_id);
				
				dataMap.put("code_url",prrepayId[2]);
				dataMap.put("mweb_url",prrepayId[3]);
				
				dataMap.put("appId", WeixinConfig.gzh_appid);
				dataMap.put("nonceStr", payParameter.getNonce_str());
				dataMap.put("timeStamp",timeStamp);
				dataMap.put("package","Sign=WXPay");
				
			}else{
				String prrepayId[] = WeixinUtil.getPrepayIdByHtml(payParameter,t);
				
			
				dataMap.put("prepayId", prrepayId[1]);
				dataMap.put("sign", WeixinUtil.getPaySign(payParameter,prrepayId[1],timeStamp));
				dataMap.put("partnerid", WeixinConfig.mch_id);
				
				dataMap.put("code_url",prrepayId[2]);
				dataMap.put("mweb_url",prrepayId[3]);
				
				dataMap.put("appId", WeixinConfig.appid);
				dataMap.put("nonceStr", payParameter.getNonce_str());
				dataMap.put("timeStamp",timeStamp);
				dataMap.put("package","Sign=WXPay");
				
			}
			
			
			
			
		
		}catch(Exception e){
			
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}
	
	
	/**
	 * WEB端获取签名
	 */
	@Override
	public Map<String, Object> getWEBSign(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try{
			Map<String,Object> dataMap = new HashMap<>();
			statusMap.put("status", 0x01);
			statusMap.put("message", "请求成功");
			statusMap.put("data", dataMap);
			
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			JSONObject object = parameter.getData();
			Integer userId = Integer.parseInt(parameter.getUserId());
			Integer type= parameter.getData().getInteger("type")==null?0:parameter.getData().getInteger("type"); // 是否合并支付    1:0
			
			
			StringBuffer orderIds = new StringBuffer();
			JSONArray array = JSONArray.parseArray(object.getString("orderIds"));
			for(int i=0;i<array.size();i++){
				String order= array.getString(i);
				if(order.length()>10){
					order=order.substring(5, order.length()-5);
				}
				
				if(i==(CalcUtil.sub(array.size(), 1))){
					orderIds.append(order);
				}else{
					orderIds.append(order+",");
				}
			}
			
			//检查商品状态
			Map<String, Object> checkMap = checkOrderBeforePay(request, orderIds.toString(), userId);
			if(Integer.parseInt(checkMap.get("status").toString())<0x01){
				return checkMap;
			}
			
			
			List<ReGoodsorder> orders = reGoodsorderDao.getToBePaidOrders(userId, orderIds.toString());
			if(orders.size()<orderIds.toString().split(",").length){
				statusMap.put("status", -0xa6);
				statusMap.put("message", "请求队列中存在已支付或未确认订单");
				return statusMap;
			}
			
			Users users = usersDao.findById(userId);
			Double totalPrice = 0.00;
			for(ReGoodsorder order : orders){
				totalPrice = CalcUtil.add(totalPrice, order.getPayPrice(), 2);
				totalPrice = CalcUtil.add(totalPrice, order.getLogisticsType(), 2);//邮费	
			}
			
			
			if(type!=null&&type==1){ //合并支付
				if(users.getMoney()>0&&users.getMoney()<=totalPrice){
					totalPrice=CalcUtil.sub(totalPrice, users.getMoney()); //
					if(totalPrice<=0){
						statusMap.put("status", -3);
						statusMap.put("message", "钱包余额充足,请使用钱包支付");
						return statusMap;
					}
					
				}
			}
			
			long timeStamp = new Date().getTime();
			
			String webSign =WeixinUtil.getWebSign(userId, type, orderIds.toString(),String.valueOf(timeStamp));
			// 拼接参数 并加密MD5 
			dataMap.put("sign",webSign);
			dataMap.put("timeStamp",timeStamp);
		}catch(Exception e){
			statusMap.put("status", 0x01);
			statusMap.put("message", "请求成功");
			e.printStackTrace();
		}
		return statusMap;
	
	}
	
	/**
	 * WEB回调
	 */
	@Override
	public Map<String, Object> payByWEB(HttpServletRequest request,HttpServletResponse response) {
	
		Parameter params = ParameterUtil.getParameter(request);
		if (params == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		Map<String, Object> statusMap = new HashMap<String, Object>();
		statusMap.put("status", 1);
		statusMap.put("message", "请求成功");
			try {
				String os= params.getOs();
				if (os==null) {
					os= request.getParameter("os");
				}
				Integer userId =Integer.parseInt(params.getUserId());
				String zoneId= params.getZoneId();
				JSONObject data = params.getData();
				String payType = data.getString("payType");
				Integer type=data.getInteger("type")==null?0:data.getInteger("type"); // 是否合并支付    1:0
				StringBuffer orderIds = new StringBuffer();
				JSONArray array = JSONArray.parseArray(data.getString("orderIds"));
				String timeStamp = data.get("timeStamp").toString();
				String sign=data.getString("sign");
				for(int i=0;i<array.size();i++){
					String order= array.getString(i);
					if(order.length()>10){
						order=order.substring(5,order.length()-5);
					}
					if(i==(CalcUtil.sub(array.size(), 1))){
						orderIds.append(order);
					}else{
						orderIds.append(order+",");
					}
				}
				
				Double total_fee = data.getDouble("total_fee");
				Integer totalfee =(int)(total_fee*100);//用于保存原始记录单位分
				String transaction_id= data.getString("trade_no");//微信支付订单号
				String timeEnd = data.getString("gmt_payment");//支付时间
				String openId=data.getString("buyer_id");//用户openid
				String outTradeNo=data.getString("out_trade_no");
				Integer weixin_type=data.getInteger("weixin_type");
				
				
				String webSign = WeixinUtil.getWebSign(userId, type, orderIds.toString(), timeStamp);
				Map<String, Object> checkSgin = WeixinUtil.checkSgin(webSign, sign, timeStamp);
				Integer status= Integer.parseInt(checkSgin.get("status").toString());
				if(status!=1){
					return checkSgin;
				}
				
				
				return	this.payOrdersMerge(request, userId, orderIds.toString(), payType, total_fee, transaction_id,timeEnd,openId,totalfee, outTradeNo,type,weixin_type,os,zoneId);
				
				
			} catch (Exception e) {
				e.printStackTrace();
				statusMap.put("status", -1);
				statusMap.put("message", "回调失败");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			return statusMap;
	}
	public Map<String,Object> getXCXSign(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Map<String,Object> dataMap = new HashMap<>();
			statusMap.put("status", 0x01);
			statusMap.put("message", "请求成功");
		
			String basePath = request.getServletContext().getAttribute("BASEPATH_IN_SESSION").toString();
			String userId = request.getParameter("userId");
			String orderId = request.getParameter("orderIds"); //创建临时订单的时候返回的orderId []
			Integer type = Integer.valueOf(request.getParameter("type"));
			StringBuffer orderIds = new StringBuffer();
			if(orderId != null){
				JSONArray array = JSONArray.parseArray(orderId);
				for(int i=0;i<array.size();i++){
					String order= array.getString(i);
					if(order.length()>10){
						order=order.substring(5, order.length()-5);
					}
					
					if(i==(CalcUtil.sub(array.size(), 1))){
						orderIds.append(order);
					}else{
						orderIds.append(order+",");
					}
				}
			}
			//检查商品状态
			Map<String, Object> checkMap = checkOrderBeforePay(request, orderIds.toString(), Integer.valueOf(userId));
			if(Integer.parseInt(checkMap.get("status").toString())<0x01){
				return checkMap;
			}
			List<ReGoodsorder> orders = reGoodsorderDao.getToBePaidOrders(Integer.valueOf(userId), orderIds.toString());
			if(orders.size()<orderIds.toString().split(",").length){
				statusMap.put("status", -0xa6);
				statusMap.put("message", "请求队列中存在已支付或未确认订单");
				return statusMap;
			}
			Users users = usersDao.findById(Integer.valueOf(userId));
			int i=0;
			Double totalPrice = 0.00;
			String orderCode = "";
			for(ReGoodsorder order : orders){
				orderCode = order.getOrderCode();
				totalPrice = CalcUtil.add(totalPrice, order.getPayPrice(), 2);
				totalPrice = CalcUtil.add(totalPrice, order.getLogisticsType(), 2);//邮费	
			}
			if(type!=null&&type==1){ //合并支付
				if(users.getMoney()>0&&users.getMoney()<=totalPrice){
					totalPrice=CalcUtil.sub(totalPrice, users.getMoney()); //
					if(totalPrice<=0){
						statusMap.put("status", -3);
						statusMap.put("message", "钱包余额充足,请使用钱包支付");
						return statusMap;
					}
					
				}
			}
			JSONObject object = new JSONObject();
			object.put("userId", userId);
			object.put("fee", totalPrice);
			object.put("type", 1);
			object.put("orderIds", orderId);
			
			
			//String notify = "https://mtjf.518wk.cn/api/invoke/order/weixin/callback"; 
			String notify = basePath+"invoke/order/weixin/callback";
			OrderInfo order = new OrderInfo();
            order.setAppid(WeixinConfig.xcx_appid);
            order.setMch_id(WeixinConfig.xcx_mch_id);
            order.setNonce_str(MD5Util.getRandomStringByLength(32));
            order.setBody(object.toJSONString());
            order.setOut_trade_no(MD5Util.getRandomStringByLength(32));
            order.setTotal_fee((int)(totalPrice*100));     // 该金钱其实10 是 0.1元
            order.setSpbill_create_ip(UrlUtil.getIp(request));
            order.setNotify_url(notify+"?body="+object.toJSONString());
            order.setTrade_type("JSAPI");
            order.setOpenid(users.getOpenId());
            order.setSign_type("MD5");
            //生成签名
            
            String sign = WeixinUtil.getSign(order);
            order.setSign(sign);
            
            String result = WeixinUtil.sendPost(WeixinConfig.HTTPS_VERIFY_URL, order);
            XStream xStream = new XStream(new DomDriver());
            xStream.alias("xml", OrderReturnInfo.class);
            OrderReturnInfo returnInfo = (OrderReturnInfo)xStream.fromXML(result);
         // 二次签名
            if ("SUCCESS".equals(returnInfo.getReturn_code()) && returnInfo.getReturn_code().equals(returnInfo.getResult_code())) {
                SignInfo signInfo = new SignInfo();
                signInfo.setAppId(WeixinConfig.xcx_appid);
                long time = System.currentTimeMillis()/1000;
                signInfo.setTimeStamp(String.valueOf(time));
                signInfo.setNonceStr(MD5Util.getRandomStringByLength(32));
                signInfo.setRepay_id("prepay_id="+returnInfo.getPrepay_id());
                signInfo.setSignType("MD5");
                //生成签名
                String sign1 = WeixinUtil.getSign(signInfo);
                Map<String,Object> payInfo = new HashMap<String,Object>();
                payInfo.put("timeStamp", signInfo.getTimeStamp());
                payInfo.put("nonceStr", signInfo.getNonceStr());
                payInfo.put("package", signInfo.getRepay_id());
                payInfo.put("signType", signInfo.getSignType());
                payInfo.put("paySign", sign1);
                dataMap.put("status", 200);
                dataMap.put("message", "统一下单成功!");
                dataMap.put("data", payInfo);
 
                return dataMap;
            }
            dataMap.put("status", 500);
            dataMap.put("message", "统一下单失败!");
            dataMap.put("data", null);
            return dataMap;
		} catch (Exception e) {
			e.getMessage();
			System.out.println("请求出错");
		}
		
		return statusMap;
		
	}

	
	
	/**
	 * 前端支付前获得微信签名
	 */
	@Override
	public Map<String, Object> getWeixinSign(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try{
			Map<String,Object> dataMap = new HashMap<>();
			statusMap.put("status", 0x01);
			statusMap.put("message", "请求成功");
			statusMap.put("data", dataMap);
			
			String basePath = request.getServletContext().getAttribute("BASEPATH_IN_SESSION").toString();
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			JSONObject object = parameter.getData();
			Integer userId = Integer.parseInt(parameter.getUserId());
			Integer type= parameter.getData().getInteger("type")==null?0:parameter.getData().getInteger("type"); // 是否合并支付    1:0
			String os= parameter.getOs();
			String zoneId=parameter.getZoneId();
			if(os==null){
				os= request.getParameter("os");
			}
		
			object.put("userId", userId);
			
			StringBuffer orderIds = new StringBuffer();
			JSONArray array = JSONArray.parseArray(object.getString("orderIds"));
			for(int i=0;i<array.size();i++){
				String order= array.getString(i);
				if(order.length()>10){
					order=order.substring(5, order.length()-5);
				}
				
				if(i==(CalcUtil.sub(array.size(), 1))){
					orderIds.append(order);
				}else{
					orderIds.append(order+",");
				}
			}
			
			//检查商品状态
			Map<String, Object> checkMap = checkOrderBeforePay(request, orderIds.toString(), userId);
			if(Integer.parseInt(checkMap.get("status").toString())<0x01){
				return checkMap;
			}
			List<ReGoodsorder> orders = reGoodsorderDao.getToBePaidOrders(userId, orderIds.toString());
			if(orders.size()<orderIds.toString().split(",").length){
				statusMap.put("status", -0xa6);
				statusMap.put("message", "请求队列中存在已支付或未确认订单");
				return statusMap;
			}
			
			Users users = usersDao.findById(userId);
			int i=0;
			Double totalPrice = 0.00;
			String orderCode = "";
			for(ReGoodsorder order : orders){
				orderCode = order.getOrderCode();
//				totalPrice = totalPrice + order.getPayPrice();
//				totalPrice = totalPrice + order.getLogisticsType();//邮费
				totalPrice = CalcUtil.add(totalPrice, order.getPayPrice(), 2);
				totalPrice = CalcUtil.add(totalPrice, order.getLogisticsType(), 2);//邮费	
			}
			
	/*		//用户优惠券集合
			List<UserCoupons> userCouponsList = userCouponsService.getUserAllCouponsList(userId);
			Double  totalDiscount = getTotalDiscount(orderIds.toString(),userCouponsList); //总优惠价
			if(totalDiscount>0){
				totalPrice=CalcUtil.sub(totalPrice, totalDiscount);
			}*/
			
			if(type!=null&&type==1){ //合并支付
				if(users.getMoney()>0&&users.getMoney()<=totalPrice){
					totalPrice=CalcUtil.sub(totalPrice, users.getMoney()); //
					if(totalPrice<=0){
						statusMap.put("status", -3);
						statusMap.put("message", "钱包余额充足,请使用钱包支付");
						return statusMap;
					}
					
				}
			}
			
//			
//			object.put("type", type);
//			object.put("totalPrice", totalPrice);
//			object.put("orderCode", orderCode);
//			object.put("zoneId", zoneId);
			
			JSONObject ob = new JSONObject();
			ob.put("type", type);
			ob.put("totalPrice", totalPrice);
			ob.put("orderCode", orderCode);
			ob.put("userId", userId);
			ob.put("zoneId", zoneId);
			ob.put("orderIds", orderIds);
			String orderjson = ob.toJSONString();
			
			String notify = basePath+"invoke/order/weixinNotify";
			PayParameter payParameter = returnParameter(request, notify, orderjson, orderCode, totalPrice+"");
			
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
			
		
		}catch(Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}
	
	@Override
	public PayParameter returnParameter(HttpServletRequest request,String notifyPath,
			String body,String orderCode,String totalPrice){
		PayParameter payParameter = new PayParameter();
		payParameter.setAttach(" ");
		payParameter.setBaseIp(UrlUtil.getIp(request));
		payParameter.setBody(" ");
		payParameter.setNotifyUrl(notifyPath+"?body="+body);
		payParameter.setOrderCode(orderCode);
		payParameter.setTotalPrice(totalPrice+"");
		return payParameter;
	}
	
	
	
	public PayParameter returnParameterByHtml(HttpServletRequest request,String notifyPath,
			String body,String orderCode,String totalPrice,String ip){
		PayParameter payParameter = new PayParameter();
		payParameter.setAttach(" ");
		payParameter.setBaseIp(ip);
		payParameter.setBody(" ");
		payParameter.setNotifyUrl(notifyPath+"?body="+body);
		payParameter.setOrderCode(orderCode);
		payParameter.setTotalPrice(totalPrice+"");
		return payParameter;
	}
	
	
	
	/**
	 * 获得支付宝签名
	 */
	@Override
	public Map<String, Object> getAlipaySign(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		Map<String,Object> dataMap = new HashMap<>();
		statusMap.put("status", 0x01);
		statusMap.put("message", "请求成功");
		statusMap.put("data", dataMap);
		//支付宝签名对象封装信息
		AlipaySign sign = new AlipaySign();
		
		String basePath = request.getServletContext().getAttribute("BASEPATH_IN_SESSION").toString();
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
	
		String zoneId = parameter.getZoneId();
		
		JSONObject object = parameter.getData();
		
		Integer type=object.getInteger("type")==null?0:object.getInteger("type"); // 是否合并支付    1:0
		Integer userId = Integer.parseInt(parameter.getUserId());
		object.put("userId", userId);
		StringBuffer orderIds = new StringBuffer();
		JSONArray array = JSONArray.parseArray(object.getString("orderIds"));
		for(int i=0;i<array.size();i++){
			String order= array.getString(i);
			if(order.length()>10){
				order=order.substring(5, order.length()-5);
			}
			
			if(i==(CalcUtil.sub(array.size(), 1))){
				orderIds.append(order);
			}else{
				orderIds.append(order+",");
			}
		}
		Users users = usersDao.findById(userId);
		//检查商品状态
		Map<String, Object> checkMap = checkOrderBeforePay(request, orderIds.toString(), userId);
		if(Integer.parseInt(checkMap.get("status").toString())<0x01){
			return checkMap;
		}		
		
		List<ReGoodsorder> orders = reGoodsorderDao.getToBePaidOrders(userId, orderIds.toString());
		if(orders.size()<orderIds.toString().split(",").length){
			statusMap.put("status", -0xa6);
			statusMap.put("message", "请求队列中存在已支付或未确认订单");
			return statusMap;
		}
		Double totalPrice = 0.00;
		for(ReGoodsorder order : orders){
//			totalPrice = totalPrice + order.getPayPrice();
//			totalPrice = totalPrice + order.getLogisticsType();//邮费
			totalPrice = CalcUtil.add(totalPrice, order.getPayPrice(), 2);
			totalPrice = CalcUtil.add(totalPrice, order.getLogisticsType(), 2);//邮费
			
		} 
		
		if(type!=null&&type==1){ //合并支付
			if(users.getMoney()>0&&users.getMoney()<=totalPrice){
				totalPrice=CalcUtil.sub(totalPrice, users.getMoney()); //
				if(totalPrice<=0){
					statusMap.put("status", -3);
					statusMap.put("message", "钱包余额充足,请使用钱包支付");
					return statusMap;
				}
				
			}
		}
		object.put("type", type);
		object.put("totalPrice", totalPrice);
		object.put("zoneId", zoneId);
		// 回调接口url
		String alipayNotify = basePath +"invoke/order/alipayNotify";
		sign.setGoodName("用户"+userId+"支付订单");
		sign.setNotifyUrl(alipayNotify);
		sign.setTotalFee(totalPrice);
		sign.setBody(object.toJSONString());
		
		dataMap.put("sign", sign.getSign());
		
		sign = null;
		return statusMap;
	}
	
	@Override
	public void closeWeixinOrder(String orderCode,String totalPrice,HttpServletRequest request){
		PayParameter payParameter = new PayParameter();
		payParameter.setAttach(orderCode);
		payParameter.setBaseIp(UrlUtil.getIp(request));
		payParameter.setBody(orderCode);
		payParameter.setNotifyUrl("");
		payParameter.setOrderCode(orderCode);
		payParameter.setTotalPrice(totalPrice);
		WeixinUtil.closeOrder(payParameter);
	}
	
	//支付前的商品检查
	public Map<String, Object> checkOrderBeforePay(HttpServletRequest request, String orderIds, Integer userId){
		Map<String, Object> statusMap = new HashMap<String, Object>();
		statusMap.put("status", 0x01);
		statusMap.put("message", "验证通过");
		try{
			Integer score = 0;
			
			Double cashpoint = 0d;
			Users user = usersDao.findById(userId);
			QueryModel model = new QueryModel();
			model.combCondition("order.id in ("+orderIds+")");
			model.combPreEquals("isValid", true);
			List<ReGoodsorderItem> items = dateBaseDAO.findLists(ReGoodsorderItem.class, model);
			for(ReGoodsorderItem item : items){
				if(item.getIsGame() == null ||item.getIsGame() != 1){
					String goodsId = item.getMallClass()+item.getGoodsId();
					score = (int) CalcUtil.add(score, item.getPayScore(), 2);
					cashpoint = CalcUtil.add(cashpoint, item.getPayCashpoint(), 2);
					ReBaseGoods good = (ReBaseGoods) goodsService.getMall(goodsId);
					Timestamp now = new Timestamp(DateUtil.getNow().getTime());
					//判断商品是否仍存在
					if(!good.getIsValid()
		//					||!good.getIsChecked()
							||good.getAddedTime().after(now)
							||good.getShelvesTime().before(now)
							){
						statusMap.put("status", -0x00e0);
						statusMap.put("message", "商品【"+good.getSnapshotGoods().getName()+"】已下架");
						return statusMap;
					}
					//检查库存
					Integer store = this.getGoodsStock(item);//库存
					if((store-item.getGoodQuantity())<0){
						statusMap.put("status", -0x00e1);
						statusMap.put("message", "商品【"+good.getSnapshotGoods().getName()+"】库存不足");
						return statusMap;
					}
				}
				if(user.getScore()<score){
					statusMap.put("status", -0x00e0);
					statusMap.put("message", "用户积分不足");
					return statusMap;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}
	
	//用户生成临时订单时根据用户账号状态修改最终款项
	@Override
	public Map<String, Object> checkOrderPrice(HttpServletRequest request,ReGoodsorder order,Users user){
		Map<String, Object> statusMap = new HashMap<String, Object>();
		statusMap.put("status", 1);
		statusMap.put("message", "验证通过");
		//订单项列表
		List<ReGoodsorderItem> items = reGoodsorderItemDao.findByParent(order.getId());
		order.setItems(items);
		double payCashpoint = 0.00;
		double payMoney = 0.00;
		int payScore = 0;
		for(int i=0;i<items.size();i++){
			ReGoodsorderItem item = items.get(i);
			//先检查库存
			if(item.getIsGame() == null || item.getIsGame() != 1 ){
				
				Integer store = this.getGoodsStock(item);//库存
				if((store-item.getGoodQuantity())<0){
					item.setIsStockEnough(false);
					statusMap.put("status", -0x00e3);
					statusMap.put("message", "库存不足");
				}
			}
			//后根据用户账户更新订单金额与用户账号余额
			this.checkItemPrice(items.get(i), user);

			
			
			payMoney = CalcUtil.add(payMoney, items.get(i).getPayPrice(), 2);
			payScore = (int) CalcUtil.add(payScore, items.get(i).getPayScore(), 2);
		}
		
		order.setPayScore(payScore);
		order.setPayPrice(payMoney);
		return statusMap;
	}

	//单个订单项支付
	private void checkItemPrice(ReGoodsorderItem item,Users user){
		if(item.getMallClass().endsWith(ReBaseGoods.SellerMall)){
			double cashpoint = user.getCashPoints();//用户红包
			double goodsCp = item.getGoodPrice();//商品红包单价
			
			if(goodsCp>0){
				int count = item.getGoodQuantity();//商品个数
				//向下取整获得用户红包余额足够支付折扣的商品个数
				int ye = (int) Math.floor(CalcUtil.div(cashpoint, goodsCp));
				ye = ye>count?count:ye;
				if(ye>0){
					double payCashpoint = item.getPayCashpoint();
					double payMoeny = item.getPayPrice();
					payCashpoint = CalcUtil.sub(payCashpoint, goodsCp*(count-ye));
					//payMoeny = CalcUtil.sub(payMoeny, payCashpoint);
					item.setPayCashpoint(0d);
					item.setPayPrice(payMoeny);
					user.setCashPoints(CalcUtil.sub(cashpoint, goodsCp*ye));
					user.setMoney(CalcUtil.sub(user.getMoney(), payMoeny));
				}else{
					item.setPayCashpoint(0.00);
					user.setMoney(CalcUtil.sub(user.getMoney(), item.getPayPrice()));
				}
			}else{
				item.setPayCashpoint(0.00);
				user.setMoney(CalcUtil.sub(user.getMoney(), item.getPayPrice()));
			}
			
		}else{
			user.setMoney(CalcUtil.sub(user.getMoney(), item.getPayPrice()));
			user.setCashPoints(CalcUtil.sub(user.getCashPoints(), item.getPayCashpoint()));
			user.setScore(user.getScore()-item.getPayScore());
		}
	}

	@Override
	public void updateGoodsStock(ReGoodsorderItem item, Integer value, boolean isAdd) throws Exception{
		ReBaseGoods good = (ReBaseGoods)reBaseGoodsDao.getMallObjByGoodsOrder(item.getMallClass()+item.getGoodsId());
		ReBaseGoods newGoods = new ReBaseGoods();
		newGoods.setStandardDetails(good.getStandardDetails());
		newGoods.setNoStandardRepertory(good.getNoStandardRepertory());
		JSONObject spec = null;
		JSONObject object = JSONObject.parseObject(item.getGoodsStandardString());
		if(object!=null){
			spec = object.getJSONObject("secondStandard");
		}
		//订单项规格集合
		if(spec!=null&&spec.size()>0){
			Integer id1 = spec.getInteger("id1");
			Integer id2 = spec.getInteger("id2");
			Integer id3 = spec.getInteger("id3");
			newGoods.editGoodsRepertory(id1, id2, id3, value, isAdd);
		}else{
			Integer repertory = good.getNoStandardRepertory();
			if(isAdd){
				//repertory += value;
				repertory = (int) CalcUtil.add(repertory, value);
			}else{
				//repertory -= value;
				repertory = (int) CalcUtil.sub(repertory, value);
			}
			newGoods.setNoStandardRepertory(repertory);
		}
		reBaseGoodsDao.updateRepertory(newGoods.getStandardDetails(),newGoods.getNoStandardRepertory(),
				item.getMallClass()+item.getGoodsId());
		good = null;
		newGoods = null;
	}
	
	/**
	 * 新版本修改库存   
	 * 如果是积分秒杀拼团 则修改发布数量 并修改周边库存
	 * 如果是周边及全国 则直接修改周边库存
	 * @param item
	 * @param value
	 * @param isAdd
	 * @throws Exception
	 */
	public void updateGoodsStockNew(ReGoodsorderItem item, Integer value, boolean isAdd) throws Exception{
		ReBaseGoods good = (ReBaseGoods)reBaseGoodsDao.getMallObjByGoodsOrder(item.getMallClass()+item.getGoodsId());
		//修改发布数量
		boolean flag=updatePublishNum(item, value, isAdd);
		ReGoodsOfSellerMall sellerMall = good.getReGoodsOfSellerMall();
		if(sellerMall!=null){
			ReBaseGoods newGoods = new ReBaseGoods();
			newGoods.setStandardDetails(sellerMall.getStandardDetails());
			newGoods.setNoStandardRepertory(sellerMall.getNoStandardRepertory());
			JSONObject spec = null;
			JSONObject object = JSONObject.parseObject(item.getGoodsStandardString());
			if(object!=null){
				spec = object.getJSONObject("secondStandard");
			}
			//订单项规格集合
			if(spec!=null&&spec.size()>0){
				Integer id1 = spec.getInteger("id1");
				Integer id2 = spec.getInteger("id2");
				Integer id3 = spec.getInteger("id3");
				newGoods.editGoodsRepertory(id1, id2, id3, value, isAdd);
			}else{
				
				//if(flag){
					Integer repertory = sellerMall.getNoStandardRepertory();
					if(isAdd){
						repertory = (int) CalcUtil.add(repertory, value);
					}else{
						repertory = (int) CalcUtil.sub(repertory, value);
					}
					newGoods.setNoStandardRepertory(repertory);
				//}
			}
			//修改库存
			reBaseGoodsDao.updateRepertory(newGoods.getStandardDetails(),newGoods.getNoStandardRepertory(),
					ReBaseGoods.SellerMall+sellerMall.getId());
			good = null;
			newGoods = null;
		}
		
	}
	
	/**
	 * 修改发布数量
	 * @param item
	 * @param value
	 * @param isAdd
	 * @return true 代表修改周边 false不修改
	 */
	private boolean updatePublishNum(ReGoodsorderItem item,Integer value,Boolean isAdd){
		boolean flag=true;
		if(item.getMallClass().equals(ReBaseGoods.ScoreMall)){
			ReGoodsOfScoreMall mall = reGoodsOfScoreMallDao.findById(item.getGoodsId());
			int num=mall.getReleaseNum();
			if(isAdd){
				num=+value;
			}else{
				num=num-value;
			}
			mall.setReleaseNum(num);
			reGoodsOfScoreMallDao.update(mall);
			flag=false;
		}else if(item.getMallClass().equals(ReBaseGoods.SeckillMall)){
			ReGoodsOfSeckillMall mall=reGoodsOfSeckillMallDao.findById(item.getGoodsId());
			int num=mall.getReleaseNum();
			if(isAdd){
				num=+value;
			}else{
				num=num-value;
			}
			mall.setReleaseNum(num);
			mall.setReleaseNum(mall.getReleaseNum()-1);
			flag=false;
		}else if(item.getMallClass().equals(ReBaseGoods.teamMall)){
			ReGoodsOfTeamMall teamMall = reGoodsOfTeamMallDao.findById(item.getMallId());
			if(teamMall!=null){
					int num=teamMall.getReleaseNum();
					if(isAdd){
						num=+value;
					}else{
						num=num-value;
					}
					teamMall.setReleaseNum(num);
					reGoodsOfTeamMallDao.update(teamMall);
			}
			flag=false;
		}
		return flag;
	}
	
	
	
	//获取秒杀 积分 拼团 发布数量
	private Integer getGoodsPublishNum(ReGoodsorderItem item){
		Integer stock = 0;
		if(item.getMallClass().equals(ReBaseGoods.SeckillMall)){
			ReGoodsOfSeckillMall mall=(ReGoodsOfSeckillMall) analyzeMallUtil.getMall(item.getMallClass()+item.getGoodsId());
			stock=mall.getReleaseNum();
		}else if(item.getMallClass().equals(ReBaseGoods.ScoreMall)){
			ReGoodsOfScoreMall mall=(ReGoodsOfScoreMall) analyzeMallUtil.getMall(item.getMallClass()+item.getGoodsId());
			stock=mall.getReleaseNum();
		}else if(item.getMallClass().equals(ReBaseGoods.teamMall)){
			ReGoodsOfTeamMall mall=(ReGoodsOfTeamMall) analyzeMallUtil.getMall(item.getMallClass()+item.getGoodsId());
			stock=mall.getReleaseNum();
		}
		return stock;
	}
	
	
	//秒杀 积分 拼团 新版本库存获取的是发布数量
	private Integer getGoodsStock(ReGoodsorderItem item){
		Integer stock = null;
		//获得商品父类
		ReBaseGoods good =null;
		//新版本
		//如果是秒杀积分拼团 就取发布数量
		if(item.getReGoodsOfSellerMall()!=null&&(item.getMallClass().equals(ReBaseGoods.teamMall)
				||item.getMallClass().equals(ReBaseGoods.ScoreMall)
				||item.getMallClass().equals(ReBaseGoods.SeckillMall))){
			return getGoodsPublishNum(item);
		}
		
		
		if(item.getReGoodsOfSellerMall()!=null){
			good=item.getReGoodsOfSellerMall();
		}else{
			good = (ReBaseGoods)analyzeMallUtil.getMall(item.getMallClass()+item.getGoodsId());
		}
		JSONObject spec = null;
		JSONObject ob = JSONObject.parseObject(item.getGoodsStandardString());
		if(ob!=null){
			spec = ob.getJSONObject("secondStandard");
		}
		
		//订单项规格集合
		if(spec!=null&&spec.size()>0){
			Integer id1 = spec.getInteger("id1");
			Integer id2 = spec.getInteger("id2");
			Integer id3 = spec.getInteger("id3");
			//获得商品当前库存
			stock = good.getRepertoryByIds(id1, id2, id3);
		}else{
			stock = good.getNoStandardRepertory();
		}
		
		return stock;
	}
	
	/**
	 * 获得用户支付状态
	 * @param request
	 * @param orderId
	 * @param stock
	 */
	private void setPayCache(HttpServletRequest request,Integer userId,Boolean status){
		SystemUtil.setCache(request, "USER_PAY_STATUS", userId.toString(), status);
	}
	
	/**
	 * 设置用户支付状态,为true则支付状态中
	 * @param request
	 * @param orderId
	 * @return
	 */
	private Boolean getPayCache(HttpServletRequest request,Integer userId){
		Object o = SystemUtil.getCache(request, "USER_PAY_STATUS", userId.toString());
		return o==null?false:(Boolean)o;
	}
  
	
	/**
	 * 清除用户支付状态
	 * @param request
	 * @param orderId
	 * @return
	 */
	private void removeCache(HttpServletRequest request,Integer userId){
		 SystemUtil.removeCache(request, "USER_PAY_STATUS", userId.toString());
		
	}
	
	/**
	 * 修改红包状态
	 * @param orderId
	 */
	private void saveRedPaperOrderLog(Integer orderItemId) {

		ReGoodsorderItem userCR = reGoodsorderItemDao.findById(orderItemId);
		//需付红包金额
		double payValue = userCR.getPayCashpoint();
		//当前红包已经支付金额
		double alreadyPayValue = 0;

		//获得用户领取红包列表记录

		NewRedPaperLog nrpl;
		Map<Integer,Double> addMap = new HashMap<Integer, Double>();
		Map<Integer,Integer> settingMap = new HashMap<Integer, Integer>();
		
		int count = - 1;
		while(count<0){
			NrpOrderLog nodl = new NrpOrderLog();
			List<NewRedPaperLog> logs = nrplDao.getMinNewRedPaperLog(userCR.getUser().getId(),userCR.getCreateTime());
			if(logs.size()>0){
				nrpl = logs.get(0);
			}else{
				break;
			}
			nodl.setRelateId(orderItemId);
			nodl.setRelateBean("ReGoodsorderItem");
			nodl.setCreateTime(userCR.getCreateTime());
			nodl.setIsvalid(true);
			//nodl.setUserCR(userCR);
			nodl.setStatus(NrpOrderLog.STATUS_PAY);
			nodl.setNrpl(nrpl);
			if(payValue>alreadyPayValue+nrpl.getAvail()){
				alreadyPayValue+=nrpl.getAvail();
				nodl.setUserMoney(nrpl.getAvail());
				nrpl.setStatus(NewRedPaperLog.STATUS_ALLPAY);
				nrpl.setAvail(0.00);	
				nrplDao.update(nrpl);
			}else{
				//nodl.setUserMoney(payValue-alreadyPayValue);
				nodl.setUserMoney(CalcUtil.sub(payValue, alreadyPayValue));
				nrpl.setStatus(NewRedPaperLog.STATUS_SPLITPAY);
				//nrpl.setAvail(nrpl.getAvail()-nodl.getUserMoney());
				nrpl.setAvail(CalcUtil.sub(nrpl.getAvail(), nodl.getUserMoney()));
				nrplDao.update(nrpl);
				count =1;
			}
			if(settingMap.get(nrpl.getSetting().getId())==null){
				settingMap.put(nrpl.getSetting().getId(), 1);
			}else{
				settingMap.put(nrpl.getSetting().getId(), settingMap.get(nrpl.getSetting().getId())+1);
			}
			if(addMap.get(nrpl.getAddendum().getId())==null){
				addMap.put(nrpl.getAddendum().getId(),nodl.getUserMoney());	
			}else{
				//addMap.put(nrpl.getAddendum().getId(),addMap.get(nrpl.getAddendum().getId())+nodl.getUserMoney());
				addMap.put(nrpl.getAddendum().getId(),CalcUtil.add(addMap.get(nrpl.getAddendum().getId()), nodl.getUserMoney(), 2));
			}
				nrpOrderLogDao.save(nodl);
				
			}
			for(Map.Entry<Integer, Integer> entry: settingMap.entrySet()){
				nrpsDao.updateAllNunUsed(entry.getKey(),entry.getValue(),"+");
			}
			for (Map.Entry<Integer, Double> entry : addMap.entrySet()) {  
				nrpaDao.updateOneAvail(entry.getKey(),entry.getValue());
			}  
		
	}
	/**
	 * 退款返还红包
	 * @param userCRId
	 */
	private void drawbackRedPaper(Integer orderItemId) {
		QueryModel qm = new QueryModel();
		//修改红包支付记录
		qm.combPreEquals("relateId", orderItemId);
		qm.combPreEquals("relateBean", "ReGoodsorderItem");
		List<NrpOrderLog> nrpOrderLogList = dateBaseDAO.findLists(NrpOrderLog.class, qm);	
		NewRedPaperLog nrpl;
		Map<Integer,Integer> settingMap = new HashMap<Integer, Integer>();
			
		for(int i=0;i<nrpOrderLogList.size();i++){		
			//修改红包支付记录
			nrpOrderLogList.get(i).setStatus(NrpOrderLog.STATUS_DRAWBACK);
				
			//获取当前红包领取记录
			qm.clearQuery();
			qm.combPreEquals("id",nrpOrderLogList.get(i).getNrpl().getId());
			nrpl = dateBaseDAO.findLists(NewRedPaperLog.class, qm).get(0);			
			//判断当前红包是否有余额
			//if(nrpl.getMoney()==nrpl.getAvail()+nrpOrderLogList.get(i).getUserMoney()){
			if(nrpl.getMoney()==CalcUtil.add(nrpl.getAvail(), nrpOrderLogList.get(i).getUserMoney(), 2)){
				nrpl.setStatus(NewRedPaperLog.STATUS_NOTUSR);
			}else{			
				nrpl.setStatus(NewRedPaperLog.STATUS_SPLITPAY);
			}
			//nrpl.setAvail(nrpl.getAvail()+nrpOrderLogList.get(i).getUserMoney());
			nrpl.setAvail(CalcUtil.add(nrpl.getAvail(), nrpOrderLogList.get(i).getUserMoney(), 2));
			//获取红包附表记录
			qm.clearQuery();
			qm.combEquals("id", nrpl.getAddendum().getId());
			NewRedPaperAddendum addendum = dateBaseDAO.findLists(NewRedPaperAddendum.class, qm).get(0);
			if(settingMap.get(nrpl.getSetting().getId())==null){
				settingMap.put(nrpl.getSetting().getId(), 1);
			}else{
				settingMap.put(nrpl.getSetting().getId(), settingMap.get(nrpl.getSetting().getId())+1);
			}
			//addendum.setAvail(addendum.getAvail()+nrpOrderLogList.get(i).getUserMoney());
			addendum.setAvail(CalcUtil.add(addendum.getAvail(), nrpOrderLogList.get(i).getUserMoney(), 2));
			nrpOrderLogDao.update(nrpOrderLogList.get(i));
			nrplDao.update(nrpl);
			nrpaDao.update(addendum);					
			}
			for(Map.Entry<Integer, Integer> entry: settingMap.entrySet()){
				nrpsDao.updateAllNunUsed(entry.getKey(),entry.getValue(),"-");
			}

	}
	
	@Override
	public void updateItemByAuto(String ids){
		try{
			List<ReGoodsorder> orders = reGoodsorderDao.findByIds(ids);
			//更新主订单状态
			StringBuffer hql = new StringBuffer();
			hql.append("update ReGoodsorder set status = "+ReGoodsorder.dai_ping_jia+" where id in ("+ids+")");
			dateBaseDAO.updateByHQL(hql.toString());
			//更新子订单状态
			hql.setLength(0);
			hql.append("update ReGoodsorderItem set status = "+ReGoodsorder.dai_ping_jia+",isBack = "+ReGoodsorder.bu_ke_tui_dan +
					" where order.id in ("+ids+") and status = "+ReGoodsorder.dai_shou_huo+" and " +
							"isBack in ("+ReGoodsorder.ke_tui_dan+","+ReGoodsorder.bu_ke_tui_dan+")");
			dateBaseDAO.updateByHQL(hql.toString());
			//更新临时收益状态
			for(ReGoodsorder order : orders){
				Seller seller = order.getSeller();//订单商家
				sellerMoneyRecordDao.activateRecord(seller, null, order.getId(), ReGoodsorder.class);
			}
		}catch(Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}

	
	
	
	
	
	@Override
	public Map<String, Object> getAlipaySignByRecharge(
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		Map<String,Object> dataMap = new HashMap<>();
		statusMap.put("status", 0x01);
		statusMap.put("message", "请求成功");
		statusMap.put("data", dataMap);
		
		String basePath = request.getServletContext().getAttribute("BASEPATH_IN_SESSION").toString();
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		
		JSONObject object = parameter.getData();
		Integer adminuserId = parameter.getData().getInteger("adminuserId");
		object.put("adminuserId", adminuserId);
		Double totalPrice = parameter.getData().getDouble("total")==null?0.0:parameter.getData().getDouble("total");
		String orderCode = OrderUtil.getOrderCode(200);
		if(totalPrice<0.01){
			return JsonResponseUtil.getJson(-0x02,"交易金额不能小于0.01");
		}
		object.put("orderCode", orderCode);
		object.put("total", totalPrice);
		// 回调接口url
		String alipayNotify = basePath +"invoke/recharge/alipayRechargeNotify";
		//支付宝签名对象封装信息
		AlipaySign sign = new AlipaySign();
		sign.setGoodName("用户"+adminuserId+"支付订单");
		sign.setNotifyUrl(alipayNotify);
		sign.setTotalFee(totalPrice);
		sign.setBody(object.toJSONString());
		
		dataMap.put("sign", sign.getSign());
		
		sign = null;
		return statusMap;
	}
	
	@Override
	public Map<String, Object> getWeixinSignByRechargeScore(
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try{
			Map<String,Object> dataMap = new HashMap<>();
			statusMap.put("status", 0x01);
			statusMap.put("message", "请求成功");
			statusMap.put("data", dataMap);
			
			String basePath = request.getServletContext().getAttribute("BASEPATH_IN_SESSION").toString();
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			
			Integer adminUserId = parameter.getData().getInteger("adminUserId");
			String os= parameter.getOs();
			if(os==null){
				os= request.getParameter("os");
			}
			
			JSONObject object = parameter.getData();
			object.put("adminUserId", adminUserId);
			Double total = parameter.getData().getDouble("rechargeMoney")==null?0.0:parameter.getData().getDouble("rechargeMoney");
			String orderCode = OrderUtil.getOrderCode(200);
			object.put("total", total);
			String orderjson = object.toJSONString();
			String notify = basePath+"invoke/recharge/weixinRechargeScoreNotify";
			PayParameter payParameter = returnParameter(request, notify, orderjson, orderCode+getCode(), total+"");
			
			String prrepayId = WeixinUtil.getPrepayIdBySeller(payParameter);
			long timeStamp = new Date().getTime();
			if("IOS".equals(os) || "ios".equals(os)){
				timeStamp=Long.parseLong(String.valueOf(new Date().getTime()).toString().substring(0,10));		
			}else{
				timeStamp = new Date().getTime();
			}
			
			
			dataMap.put("prepayId", prrepayId);
			dataMap.put("sign", WeixinUtil.getPaySignBySeller(payParameter,prrepayId,timeStamp));
			dataMap.put("partnerid", WeixinConfig.s_mch_id);
			dataMap.put("appId", WeixinConfig.s_appid);
			dataMap.put("nonceStr", payParameter.getNonce_str_seller());
			dataMap.put("timeStamp",timeStamp);
			dataMap.put("package","Sign=WXPay");
		
		}catch(Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}
	
	
	//易联积分充值签名
		public Map<String, Object> getYiLianSignByRechargeScore(HttpServletRequest request,
				HttpServletResponse response) {

			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {// 错误的参数；
				return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
			}
			String basePath = request.getServletContext().getAttribute("BASEPATH_IN_SESSION").toString();
			JSONObject object = parameter.getData();
			Double total=object.getDouble("rechargeMoney");
			
			Integer adminUserId = object.getInteger("adminUserId");
			Map<String, Object> statusMap = new HashMap<String, Object>();
			Map<String, Object> dataMap = new HashMap<>();
			statusMap.put("data", dataMap);
			
			object.put("adminUserId", adminUserId);
			object.put("total", total);
			String amount = total.toString(); //商户订单金额 必须
		
			String orderDesc="充值积分数量:"+CalcUtil.mul(total, 5,0)+""; 										
			String extData =object.toJSONString(); //扩展数据 通知结果时,自定义内容 原样返回
			String miscData ="||||||||"; // 以下扩展参数是按互联网金融行业填写的；其他行业请参考接口文件说明进行填写 可以不填!!!!
			if (Tools.checkAmount(amount) == false) { // 验证金额准确性 两位小数
				statusMap.put("RetCode", "E105");
				statusMap.put("RetMsg", "金额格式错!");
				return statusMap;
			}

			String orderCode = OrderUtil.getOrderCode(Integer.parseInt(OrderUtil.getCode()));
			String merchOrderId =orderCode; //订单号
			String merchantId = Constants.MERCHANT_ID; // 必须 商户代码 正式要该
			String notifyUrl = basePath +"invoke/recharge/yiLianRechargeScoreNotify"; // 异步回调路径
			String tradeTime = Tools.getSysTime(); // 必须 提交时间
			String expTime =""; // 采用系统默认的订单有效时间 交易超时 时间超过订单超时时间未支付，订单作废；不提交该参数，采用系统的默认时间（从接收订单后超时时间为30分钟）
			String notifyFlag = "0";// 0：成功才通知（02状态），1：全部通知（02、04、05状态）不填默认为“1：全部通知”
			
			// 调用下单接口
			Xml retXml = new Xml();

			try {
				Log.setLogFlag(true);
				
				Log.println("---交易： 商户下订单(SDK版本)-------------------------");
				String ret = TransactionClientSdk.MerchantOrder(merchantId,
						merchOrderId, amount, orderDesc, tradeTime, expTime,
						notifyUrl, extData, miscData, notifyFlag,
						Constants.MERCHANT_RSA_PRIVATE_KEY,
						Constants.PAYECO_RSA_PUBLIC_KEY, Constants.PAYECO_URL,
						retXml);
				
				if (!"0000".equals(ret)) {
					statusMap.put("RetCode", ret);
					statusMap.put("RetMsg", "下订单接口返回错误!");
					return statusMap;
				}
			} catch (Exception e) {
				String errCode = e.getMessage();
				if ("E101".equalsIgnoreCase(errCode)) {
					statusMap.put("RetCode", "E101");
					statusMap.put("RetMsg", "下订单接口无返回数据!");
					return statusMap;
				} else if ("E102".equalsIgnoreCase(errCode)) {
					statusMap.put("RetCode", "E102");
					statusMap.put("RetMsg", "验证签名失败!");
					return statusMap;
				} else if ("E103".equalsIgnoreCase(errCode)) {
					statusMap.put("RetCode", "E103");
					statusMap.put("RetMsg", "进行订单签名失败!");
					return statusMap;
				} else {
					statusMap.put("RetCode", "E100");
					statusMap.put("RetMsg", "下订单通讯失败!");
					return statusMap;
				}
			}

			// 设置返回给手机Json数据
			dataMap.put("Version", retXml.getVersion());
			dataMap.put("MerchOrderId", retXml.getMerchOrderId());
			dataMap.put("MerchantId", retXml.getMerchantId());
			dataMap.put("Amount", retXml.getAmount());
			dataMap.put("TradeTime", retXml.getTradeTime());
			dataMap.put("OrderId", retXml.getOrderId());  //易联订单id
			dataMap.put("Sign", retXml.getSign());
			dataMap.put("ExtData",retXml.getExtData());
			statusMap.put("status", 1);
			statusMap.put("message", "下单成功");
			return statusMap;
		} 
	
		@Override
		public String weixinRechargeScoreNotify(HttpServletRequest request,
				HttpServletResponse response) {
			XmlData xml = new XmlData();
			try{
				
				Map<String, String> params = WeixinUtil.getParams(request, response);
				String return_code = params.get("return_code");
				String sign = params.get("sign");
				Double totalFee = Double.parseDouble(params.get("total_fee"));
				Integer total_fee =Integer.parseInt(params.get("total_fee"));//用于保存原始记录
				String transaction_id= params.get("transaction_id");//微信支付订单号
				String timeEnd = params.get("time_end");//支付时间
				String openId=params.get("openid");//用户openid
				String outTradeNo= params.get("out_trade_no");
				totalFee = CalcUtil.mul(totalFee, 0.01, 2);
				String body = params.get("body");
				//自主生成验证签名
				String checkSign = WeixinUtil.notifySignSeller(params);
				
				if (return_code.equals("SUCCESS")&&sign.equals(checkSign)) {//验证成功
					
					JSONObject ob = JSONObject.parseObject(body);
					Integer adminUserId = ob.getInteger("adminUserId");
					
					Double  total = totalFee;
					Integer score=(int)(total*5);
					Map<String,Object> map = this.payRechargeScore(request, adminUserId, "300", total, transaction_id, timeEnd, openId, total_fee, outTradeNo, score);
					
					xml.setProtectData("return_code", "SUCCESS");
					xml.setProtectData("return_msg", map.get("message"));
			
				}else{
					xml.setProtectData("return_code", "FAIL");
					xml.setProtectData("return_msg", "FAIL");
					System.out.println("验证失败");
				}
				WeixinUtil.sendXml(WeixinConfig.HTTPS_VERIFY_URL, xml.putout());
			}catch(Exception e){
				System.out.println("支付出错");
				e.printStackTrace();
				xml.setProtectData("return_code", "FAIL");
				xml.setProtectData("return_msg", "FAIL");
				WeixinUtil.sendXml(WeixinConfig.HTTPS_VERIFY_URL, xml.putout());
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			return null;
		}
		
		
		
	@Override
	public Map<String, Object> getAlipaySignByRechargeScore(
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		Map<String,Object> dataMap = new HashMap<>();
		statusMap.put("status", 0x01);
		statusMap.put("message", "请求成功");
		statusMap.put("data", dataMap);
		
		String basePath = request.getServletContext().getAttribute("BASEPATH_IN_SESSION").toString();
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		
		JSONObject object = parameter.getData();
		Integer adminUserId = parameter.getData().getInteger("adminUserId");
		Double total = parameter.getData().getDouble("rechargeMoney")==null?0.0:parameter.getData().getDouble("rechargeMoney");
		String orderCode = OrderUtil.getOrderCode(200);
		object.put("adminUserId", adminUserId);
		object.put("orderCode", orderCode);
		object.put("total", total);
		// 回调接口url
		String alipayNotify = basePath +"invoke/recharge/alipayRechargeScoreNotify";
		//支付宝签名对象封装信息
		AlipaySign sign = new AlipaySign();
		sign.setGoodName("用户"+adminUserId+"充值积分");
		sign.setNotifyUrl(alipayNotify);
		sign.setTotalFee(total);
		sign.setBody(object.toJSONString());
		
		dataMap.put("sign", sign.getSign());
		
		sign = null;
		return statusMap;
	}
	//余额充值积分
	@Override
	public Map<String, Object> getWalletByRechargeScore(
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try{
			Map<String,Object> dataMap = new HashMap<>();
			statusMap.put("status", 0x01);
			statusMap.put("message", "请求成功");
			
			
			String basePath = request.getServletContext().getAttribute("BASEPATH_IN_SESSION").toString();
			Parameter parameter = ParameterUtil.getParameter(request);
			Double total = parameter.getData().getDouble("rechargeMoney")==null?0.0:parameter.getData().getDouble("rechargeMoney");
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			
			Integer adminuserId = Integer.valueOf(parameter.getData().getString("adminUserId"));
			String os= parameter.getOs();
			if(os==null){
				os= request.getParameter("os");
			}
			AdminUser adminUser = adminUserDAO.findById(adminuserId);
			if(total > adminUser.getMoney()){
				return JsonResponseUtil.getJson(-0x02,"账户余额不足,请先充值或使用其他支付方式");
			}
			int score = (int) (total*5);//获得积分
			String timeEnd = String.valueOf(System.currentTimeMillis());
			String transaction_id = getOrderCode(adminuserId);
			
			Map<String,Object> map = this.payRechargeScore(request, adminuserId,  "100", total, transaction_id,timeEnd,"",null, "",score);
			if(Integer.parseInt(map.get("status").toString())==1){
				statusMap.put("status", 0x01);
				statusMap.put("message", "充值成功");
				return statusMap;
				
			}else{
				statusMap.put("status", -0x02);
				statusMap.put("message", "充值失败");
				return statusMap;
			}
				
			
		}catch(Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}

	@Override
	public Map<String, Object> getWeixinSignByRecharge(
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try{
			Map<String,Object> dataMap = new HashMap<>();
			statusMap.put("status", 0x01);
			statusMap.put("message", "请求成功");
			statusMap.put("data", dataMap);
			
			String basePath = request.getServletContext().getAttribute("BASEPATH_IN_SESSION").toString();
			Parameter parameter = ParameterUtil.getParameter(request);
			
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			
			Integer adminuserId = parameter.getData().getInteger("adminuserId");
			String os= parameter.getOs();
			if(os==null){
				os= request.getParameter("os");
			}
			

			JSONObject object = parameter.getData();
			object.put("adminuserId", adminuserId);
			Double totalPrice = parameter.getData().getDouble("total")==null?0.0:parameter.getData().getDouble("total");
			if(totalPrice<0.01){
				
				return JsonResponseUtil.getJson(-0x02,"交易金额不能小于0.01");
			}
			String orderCode = getOrderCode(adminuserId);
			object.put("totalPrice", totalPrice);
			String orderjson = object.toJSONString();
			
			String notify = basePath+"invoke/recharge/weixinRechargeNotify";
			PayParameter payParameter = returnParameter(request, notify, orderjson, orderCode+getCode(), totalPrice+"");
			String prrepayId = WeixinUtil.getPrepayIdBySeller(payParameter);
			long timeStamp = new Date().getTime();
			if("IOS".equals(os) || "ios".equals(os)){
				timeStamp=Long.parseLong(String.valueOf(new Date().getTime()).toString().substring(0,10));		
			}else{
				timeStamp = new Date().getTime();
			}
				dataMap.put("prepayId", prrepayId);
				dataMap.put("sign", WeixinUtil.getPaySignBySeller(payParameter,prrepayId,timeStamp));
				dataMap.put("partnerid", WeixinConfig.s_mch_id);
				dataMap.put("appId", WeixinConfig.s_appid);
				dataMap.put("nonceStr", payParameter.getNonce_str_seller());
				dataMap.put("timeStamp",timeStamp);
				dataMap.put("package","Sign=WXPay");
			
		}catch(Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}

	
	/**
	 * 订单编码
	 * @param orderId
	 * @return
	 */
	public static String getOrderCode(Integer orderId){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddhhmmss");
		String format = sdf.format(new Date());
		DecimalFormat f = new DecimalFormat("00");
		StringBuffer sb = new StringBuffer(format);
		sb.append(f.format(orderId));
		return sb.toString();		
	}
	
	/**
	 * 获取随机码
	 * @return
	 */
	public static String getCode(){
		int max = 999;
	    int min = 100;
	    Random random = new Random();
	    return Integer.toString(random.nextInt(max)%(max-min+1) + min);
	}
	
	
	@Override
	public String alipayRechargeScoreNotify(HttpServletRequest request,
			HttpServletResponse response) {
		try{
			//获取支付宝返回数据集合
			Map<String, String> params = AlipayPayUtils.getParams(request, response);
			String trade_status = params.get("trade_status");//支付状态
			
			boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ali_public_key, "UTF-8",AlipayConfig.sign_type);
			
			//String trade_status = params.get("trade_status");//支付状态
			if (verify_result) {//验证签名
				if (trade_status.equals("TRADE_FINISHED")) {
					
					return "success";
				} else if (trade_status.equals("TRADE_SUCCESS")) {//支付成功
					
					String body = params.get("body");
					
					System.out.println(params.toString());
					JSONObject data = JSONObject.parseObject(body);
					Integer adminuserId = Integer.parseInt(data.getString("adminUserId"));
					Double total = Double.parseDouble(data.getString("total"));
					Double total_fee = Double.parseDouble(data.getString("total"));
					Integer totalfee =(int)(total_fee*100);//用于保存原始记录单位分
					String transaction_id= params.get("trade_no");//微信支付订单号
					String timeEnd = params.get("gmt_payment");//支付时间
					String openId=params.get("buyer_id");//用户openid
					String outTradeNo= params.get("out_trade_no");
					Integer score=(int)(total*5);
						Map<String,Object> map = this.payRechargeScore(request, adminuserId,  "200", total, transaction_id,timeEnd,openId,totalfee, outTradeNo,score);
						if(Integer.parseInt(map.get("status").toString())==1){
							return "success";
						}else{
							System.out.println("支付宝充值积分失败");
							return "fail";
						}
							
				
				} else {//验证失败
					System.out.println("验证失败");
					return "fail";
				}
			}else{
				return "fail";
			}
		}catch(Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "fail";
		}
	}
	
	
	@Override
	public String yiLianRechargeScoreNotify(HttpServletRequest request,HttpServletResponse response) {
		// 结果通知参数，易联异步通知采用GET提交
				String version = request.getParameter("Version");
				String merchantId = request.getParameter("MerchantId");
				String merchOrderId = request.getParameter("MerchOrderId");
				String amount = request.getParameter("Amount");
				String extData = request.getParameter("ExtData");
				String orderId = request.getParameter("OrderId"); //易联订单id
				String status = request.getParameter("Status");
				String payTime = request.getParameter("PayTime");
				String settleDate = request.getParameter("SettleDate");
				String sign = request.getParameter("Sign");
				String os = request.getParameter("os");
				Map<String, Object> retMap=new HashMap<String, Object>();
				// 订单结果逻辑处理
				String retMsgJson = "";
				try {
					Log.setLogFlag(true);
					Log.println("---交易： 订单结果异步通知-------------------------");
					//验证订单结果通知的签名
					boolean b = TransactionClient.bCheckNotifySign(version, merchantId, merchOrderId, 
							amount, extData, orderId, status, payTime, settleDate, sign, 
							Constants.PAYECO_RSA_PUBLIC_KEY);
					if (!b) {
						retMsgJson = "{\"RetCode\":\"E101\",\"RetMsg\":\"验证签名失败!\"}";
						Log.println("验证签名失败!");
						System.out.println("===============================");
							System.out.println("验证签名失败!");
						System.out.println("===============================");
					}else{
						// 签名验证成功后，需要对订单进行后续处理
						
						if ("02".equals(status)) { // 订单已支付; 订单支付成功的业务逻辑处理请在本处增加（订单通知可能存在多次通知的情况，需要做多次通知的兼容处理）；
						System.out.println("===============================");
							System.out.println("签名验证成功!");
						System.out.println("===============================");
						
							extData = new String(Base64.decode(extData), ConstantsClient.PAYECO_DATA_ENCODE);
							
							JSONObject data = JSONObject.parseObject(extData);
							
							Double total = Double.parseDouble(amount); 
							Integer score=(int)(total*5);
							Integer totalfee =(int)(total*100);//用于保存原始记录单位分
							String transaction_id= orderId;//微信支付宝易联支付订单号
							String timeEnd = payTime;// params.get("gmt_payment");//支付时间
							String openId="";
							String outTradeNo=merchOrderId; //商户订单号
							Integer adminUserId = data.getInteger("adminUserId");
							
							retMap=	payRechargeScore(request, adminUserId, "400", total, transaction_id, timeEnd, openId, totalfee, outTradeNo,score);
								//
							if(Integer.parseInt(retMap.get("status").toString())!=1){
								return JSONObject.toJSONString(retMap);
							}
							
							
							retMsgJson = "{\"RetCode\":\"0000\",\"RetMsg\":\"订单已支付\"}";
							Log.println("订单已支付!");
						} else {
							// 1、订单支付失败的业务逻辑处理请在本处增加（订单通知可能存在多次通知的情况，需要做多次通知的兼容处理，避免成功后又修改为失败）；
							// 2、返回响应内容
							retMsgJson = "{\"RetCode\":\"E102\",\"RetMsg\":\"订单支付失败+"+status+"\"}";
							Log.println("订单支付失败!status="+status);
						}
					}
				} catch (Exception e) {
					retMsgJson = "{\"RetCode\":\"E103\",\"RetMsg\":\"处理通知结果异常\"}";
					System.out.println("处理通知结果异常!e="+e.getMessage());
					
					e.printStackTrace();
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				}
				//返回数据
				return retMsgJson;
	}

	@Override
	public String payByAlipayByRecharge(HttpServletRequest request,
			HttpServletResponse response) {
		
		try{
			//获取支付宝返回数据集合
			Map<String, String> params = AlipayPayUtils.getParams(request, response);
			String trade_status = params.get("trade_status");//支付状态
			
			boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ali_public_key, "UTF-8",AlipayConfig.sign_type);
			
			
			
			if (verify_result) {//验证签名
				
				if (trade_status.equals("TRADE_FINISHED")) {
					
					return "success";
				} else if (trade_status.equals("TRADE_SUCCESS")) {//支付成功
					
					String body = params.get("body");
					JSONObject data = JSONObject.parseObject(body);
					Integer adminuserId = Integer.parseInt(data.getString("adminuserId"));
					Double total = Double.parseDouble(data.getString("total"));
					Double total_fee = Double.parseDouble(params.get("total_amount"));
					
					
						
						Integer totalfee =(int)(total_fee*100);//用于保存原始记录单位分
						String transaction_id= params.get("trade_no");//微信支付订单号
						String timeEnd = params.get("gmt_payment");//支付时间
						String openId=params.get("buyer_id");//用户openid
						String outTradeNo= params.get("out_trade_no");
						
						Map<String,Object> map = this.payRecharge(request, adminuserId,  "ALIYPA", total_fee, transaction_id,timeEnd,openId,totalfee, outTradeNo);
						
							return "success";
						
				
				} else {//验证失败
					System.out.println("验证失败");
					return "fail";
				}
			}else{
				return "fail";
			}
		}catch(Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "fail";
		}
	}

	@Override
	public String payByWeixinByRecharge(HttpServletRequest request,
			HttpServletResponse response) {

		
		XmlData xml = new XmlData();
		try{
			
			Map<String, String> params = WeixinUtil.getParams(request, response);
			
			String return_code = params.get("return_code");
			String sign = params.get("sign");
			Double totalFee = Double.parseDouble(params.get("total_fee"));
			Integer total_fee =Integer.parseInt(params.get("total_fee"));//用于保存原始记录
			String transaction_id= params.get("transaction_id");//微信支付订单号
			String timeEnd = params.get("time_end");//支付时间
			String openId=params.get("openid");//用户openid
			String outTradeNo= params.get("out_trade_no");
			totalFee = CalcUtil.mul(totalFee, 0.01, 2);
			String body = params.get("body");
			//自主生成验证签名
			String checkSign = WeixinUtil.notifySignSeller(params);
			
			if (return_code.equals("SUCCESS")&&sign.equals(checkSign)) {//验证成功
				
				JSONObject ob = JSONObject.parseObject(body);
				Integer adminuserId = ob.getInteger("adminuserId");
				Double  total = ob.getDouble("total");
				AdminUser adminUser = adminUserDao.findById(adminuserId);
				
				//Map<String,Object> map = this.payOrders(request, userId, orderIds.toString(), "WEIXIN", totalFee);
				Map<String,Object> map = this.payRecharge(request, adminuserId,  "WEIXIN", totalFee, transaction_id,timeEnd,openId,total_fee, outTradeNo);
				
						
						xml.setProtectData("return_code", "SUCCESS");
						xml.setProtectData("return_msg", map.get("message"));
						
					
		
			}else{
				xml.setProtectData("return_code", "FAIL");
				xml.setProtectData("return_msg", "FAIL");
				System.out.println("验证失败");
			}
			WeixinUtil.sendXml(WeixinConfig.HTTPS_VERIFY_URL, xml.putout());
		}catch(Exception e){
			System.out.println("支付出错");
			e.printStackTrace();
			xml.setProtectData("return_code", "FAIL");
			xml.setProtectData("return_msg", "FAIL");
			WeixinUtil.sendXml(WeixinConfig.HTTPS_VERIFY_URL, xml.putout());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return null;
	
	}

	@Override
	public void pushsingle(Integer userId, String channelId,String title, String message) {
		try {
			
					//appInformation.pushMessage(userId + "",
						//	channelId,user.getUserid(), title, message);
			
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}

	
	
	/**
	 * 保存发展合伙人 失败 记录
	 */
	@Override
	public  boolean savePartnerInform(Users users,Users causeUser,String remark,Integer level ,Integer mode){
		try {
			PartnerInform from=new PartnerInform();
			from.setUsers(users);
			from.setCauseUsers(causeUser);
			from.setRemark(remark);
			from.setCreatetime(new Timestamp(System.currentTimeMillis()));
			from.setIsValid(true);
			from.setLevel(level);
			from.setMode(mode);
			partnerInformDao.save(from);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}
	
	
	public double getTotalDiscount(String orderIds,List<UserCoupons> userCouponsList){
		double totalDiscount=0.0d;
		QueryModel model = new QueryModel();
		model.combCondition("order.id in ("+orderIds+")");
		model.combPreEquals("isValid", true);
		List<ReGoodsorderItem> item = dateBaseDAO.findLists(ReGoodsorderItem.class, model);
		for(ReGoodsorderItem temp: item){
			if(userCouponsList!=null&&userCouponsList.size()>0){
				for (int i = 0; i < userCouponsList.size(); i++) {
					Integer mallTypeId = ReBaseGoods.getMallTypeId(userCouponsList.get(i).getGoodsMall().substring(0,3));
					Integer goodId =Integer.parseInt( userCouponsList.get(i).getGoodsMall().substring(3));
					//找到对应优惠券的商品
					if(mallTypeId==ReBaseGoods.getMallTypeId(temp.getMallClass())&&goodId.equals(temp.getMallId())){
						//totalDiscount=CalcUtil.add(totalDiscount,userCouponsList.get(i).getTicketprice(),2); //全部订单总优惠
						totalDiscount=CalcUtil.add(totalDiscount, userCouponsList.get(i).getTicketprice());
						break;
					}
				}
			}else{
				return totalDiscount;
			}
		}
		return totalDiscount;
	}
	
	
	
	public double getTotalDiscount(List<UserCoupons> userCouponsList){
		double totalDiscount=0.0d;
		
		for (UserCoupons userCoupons : userCouponsList) {
			totalDiscount=CalcUtil.add(totalDiscount,userCoupons.getTicketprice());
		}
		
		return totalDiscount;
	}
	
		

	

	/**
	 * 
	 * @param userId  用户id  
	 * @param payType  支付类型
	 * @param money	  钱包支付金额
	 * @param thirdParty 第三方支付金额
	 */
	public boolean addPartner(Integer userId ,String zoneId){
		boolean flag = true;
		try {
		QueryModel queryModel=new QueryModel();
		queryModel.combPreEquals("isValid", true);
		queryModel.combCondition("users.id is not null");
		List<TkldPid> tkldPidList= dateBaseDAO.findLists(TkldPid.class, queryModel);
		Map<Integer, Object> partner=new HashMap<Integer, Object>();
		Map<Integer, Object> syPartner=new HashMap<Integer, Object>();
		Map<Integer, Object> dlPartner=new HashMap<Integer, Object>();
		//填充所有事业合伙人
		for (TkldPid tkldPid : tkldPidList) {
				if(tkldPid.getLevel()==1){
					dlPartner.put(tkldPid.getUsers().getId(), tkldPid);
				}else if(tkldPid.getLevel()==2){
					syPartner.put(tkldPid.getUsers().getId(), tkldPid);
				}else{
					partner.put(tkldPid.getUsers().getId(), tkldPid);
				}	
		}
		Users users = usersDao.findById(userId);
		//返回的事业合伙人对象
		TkldPid cause=null;
			cause=	findParentPid(users,partner,syPartner,0);
		if(cause ==null){
			Integer i_zoneId=1961;
			if(StringUtils.isNotBlank(zoneId)){
				i_zoneId= Integer.parseInt(zoneId);
			}else{
				queryModel.clearQuery();
				queryModel.combPreEquals("users.id",userId,"userId");
				queryModel.setOrder("lasttime desc");
				List<UserLoginRecord> userLoginRecordList = dateBaseDAO.findLists(UserLoginRecord.class, queryModel);
				if(userLoginRecordList.size()>0){
					UserLoginRecord userLoginRecord= userLoginRecordList.get(0);
					i_zoneId =userLoginRecord.getZoneId();
					System.out.println("查找到用户所在地区"+i_zoneId);
				}else{
					System.out.println("未查找到用户所在地区"+i_zoneId);
				}
				
			}
			ProvinceEnum province = provinceEnumDao.findById(i_zoneId);
			if(province!=null){
				Integer provinceid = 1961;
				if(province.getLevel2()==3){//区；或者城区县
					provinceid= province.getProvinceEnumId();
				}else if(province.getLevel2()==2){//县，县级市
					provinceid=province.getId();
				}
				
				queryModel.clearQuery();
				//找到当前地区的事业合伙人
				queryModel.combPreEquals("isValid", true);
				queryModel.combPreEquals("provinceEnum.id", provinceid,"zoneId");
				queryModel.combEquals("level", 2);
				//事业合伙人	区级
				List<TkldPid> dList  = dateBaseDAO.findLists(TkldPid.class, queryModel);
				if(dList!=null && dList.size()>0){
					cause=dList.get(0);
					System.out.println("用户"+users.getId()+"在"+province.getName()+"支付购买合伙人资格；未找到粉丝关系对应事业伙人，自动获取当地兜底事业合伙人"+cause.getUsers().getId());
				}
			}
			if(cause==null){
				cause =tkldPidDao.findById(502);
				System.out.println("未找到任何事业合伙人！自动获取总部兜底事业合伙人");
			}
			
			
		}
		
		if(cause!=null){
			savePid(cause,users);//绑定
		}else{
			System.out.println("未找到任何事业合伙人！");
		}
		
	
		} catch (Exception e) {
			flag =false;
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return flag;
	}
	
	public void savePid(TkldPid cause,Users users){
		// ----------da
		String pddPid = tkldPidService.gainPddPid()==null?StringUtil.pdd_pid:tkldPidService.gainPddPid();

		QueryModel queryModel=new QueryModel();
		queryModel.clearQuery();
		queryModel.combPreEquals("isValid", true);
		queryModel.combPreEquals("tkldPid.id", cause.getId(),"parentId");
		queryModel.combEquals("level", 3);
		queryModel.combIsNull("users.id");
		//找到可以分配的合伙人
		List<TkldPid> partnerList = dateBaseDAO.findLists(TkldPid.class, queryModel);
		if(partnerList.size()>0){
						TkldPid tkldPid = partnerList.get(0);
						tkldPid.setUsers(users);
						tkldPid.setUsersRemark(users.getRealname());
						tkldPid.setBindingTime(new Timestamp(System.currentTimeMillis()));
						tkldPid.setIsCareerPartner(2);
						// da
						tkldPid.setPddPid(pddPid);
						tkldPidDao.update(tkldPid);
		}else {
			
			queryModel.clearQuery();
			queryModel.combPreEquals("isValid", true);
			queryModel.combPreEquals("causeUsers.id", cause.getId(),"cId");
			queryModel.combPreEquals("users.id", users.getId(),"uId");
			List<PartnerInform> pList = dateBaseDAO.findLists(PartnerInform.class, queryModel);
			//创建一张表  来记录   没有分配的情况  以后补数据
			if(pList==null || pList.size()==0){
				savePartnerInform(users,cause.getUsers(),"事业合伙人:"+cause.getUsers().getName()+",购买用户"+users.getName(),3,0);
			}
			
			
		}
		if(partnerList.size()<5){
			userSystemMessageService.saveMessage("合伙人不足","粉丝"+users.getName()+"支付成功！事业合伙人"+cause.getUsers().getName()+"的合伙人数量为"+partnerList.size()+";请及时补充");
		}
		//分佣
		
		queryModel.clearQuery();
		queryModel.combPreEquals("isValid", true);
		queryModel.combPreEquals("usersByUserId.id", cause.getId(),"cId");
		queryModel.combPreEquals("usersByFromUsers.id", users.getId(),"fId");
		queryModel.combEquals("money", 90);
		List<CashmoneyRecord> cList = dateBaseDAO.findLists(CashmoneyRecord.class, queryModel);
		if(cList==null || cList.size()==0){
			updateUserMoney(cause.getUsers(),users);
		}
		
		
	}
	
	
	public void savePidNew(TkldPid cause,Users users){
		
		// ----------da
		String pddPid = tkldPidService.gainPddPid()==null?StringUtil.pdd_pid:tkldPidService.gainPddPid();
				
		List<TkldPid> tkldPidList=new ArrayList<>();
	
		tkldPidList= tkldPidDao.findByPropertyIsValid("users.id", users.getId());
		
		if(tkldPidList!=null && tkldPidList.size()>0){
			System.out.println("重复设置pid");
		}else{
			QueryModel queryModel=new QueryModel();
			queryModel.clearQuery();
			queryModel.combPreEquals("isValid", true);
			queryModel.combPreEquals("tkldPid.id", 0,"parent_pid_id");
			queryModel.combEquals("level", 3);
			queryModel.setOrder("createtime desc");
			//List<TkldPid> tklist =  dateBaseDAO.findLists(TkldPid.class, queryModel);
			//找到可以分配的合伙人
			List<TkldPid> partnerList = dateBaseDAO.findLists(TkldPid.class, queryModel);
			if(partnerList.size()>0){
						if(partnerList.size()<15){
							userSystemMessageService.saveMessage("合伙人不足","粉丝"+users.getName()+"支付成功！预设合伙人数据为"+partnerList.size()+";请及时补充");
						}
						TkldPid tkldPid = partnerList.get(0);
						tkldPid.setUsers(users);
						tkldPid.setUsersRemark(users.getRealname());
						tkldPid.setBindingTime(new Timestamp(System.currentTimeMillis()));
						tkldPid.setIsCareerPartner(2);
						tkldPid.setAdminUser(null);
						tkldPid.setTkldPid(cause);
						tkldPid.setLevel(3);
						//da
						tkldPid.setPddPid(pddPid);
						tkldPidDao.update(tkldPid);
			}else {
				savePartnerInform(users,cause.getUsers(),"购买用户"+users.getName()+"支付成为合伙人，但数据不足",3,1);
				userSystemMessageService.saveMessage("预设合伙人不足","粉丝"+users.getName()+"支付成功！但预设合伙人数据不足");
				
				
			}
			
			
		}
		
		
		
		
		
		
		
	}
	

		/**
		 * 更改拼团人数及状态
		 * @param reGoodsorder
		 * @return
		 */
		 public void updateTeamNum(ReGoodsorder reGoodsorder){
			 if(reGoodsorder.getReGoodsorder()==null){ //如果当前订单是拼主 那么人数为1
				 reGoodsorder.setTeamNum(1);
				 reGoodsorder.setStatus(ReGoodsorder.dai_pin_tuan);
			 }else{
				 ReGoodsorder pinzhu = reGoodsorder.getReGoodsorder();
				 //所有拼员
				 QueryModel model=new QueryModel();
				 model.combEquals("isValid", 1);
				 model.combCondition(" status>0");
				 model.combPreEquals("reGoodsorder.id",pinzhu.getId(),"goodsId");
				 List<ReGoodsorder> pinYuanList = (List<ReGoodsorder>) dateBaseDAO.findList(ReGoodsorder.class, model);
				 
				 if(pinYuanList.size()==1){ //说明是该订单是拼员 达到了2人数  修改订单状态
					 
					 updateTeamStatus(pinzhu);
					 
					 List<ReGoodsorderItem> pinZhuItem = reGoodsorderItemDao.findByPropertyIsValid("order.id", pinzhu.getId());
					 pinZhuItem.get(0).setStatus(pinzhu.getStatus());
					 reGoodsorderItemDao.update( pinZhuItem.get(0));
				 }
				 pinzhu.setTeamNum(pinzhu.getTeamNum()+1); //修改拼主
				 reGoodsorderDao.update(pinzhu);
				 
				 for (ReGoodsorder pinYuan : pinYuanList) {
					 pinYuan.setTeamNum(pinzhu.getTeamNum()+1);
					 reGoodsorderDao.update(pinYuan);
				}
				 updateTeamStatus(reGoodsorder);
			 }
		 }

		//更改当前订单状态
		 public void updateTeamStatus(ReGoodsorder reGoodsorder){
			 if(Integer.parseInt(reGoodsorder.getLogistics())==ReGoodsorder.bao_you||Integer.parseInt(reGoodsorder.getLogistics())==ReGoodsorder.bu_bao_you){
				 reGoodsorder.setStatus(ReGoodsorder.dai_que_ren);
			 }else{
				 reGoodsorder.setStatus(ReGoodsorder.dai_dui_huan);
			 }
		 }

    /**
     * 保存接口所有参数
     * @param request
     */
	public Map<String, String> sevaPayNotify(HttpServletRequest request,HttpServletResponse response,Integer type,String payType){
		Map<String, String> params=null;
		
		try {
		StringBuffer orderIds = new StringBuffer();
		Integer userId=null;
		String body = null;
		
		if(type==VisitLog.PAY){  //退款再说
			if(payType.equals(VisitLog.WEIXIN)){
				params = WeixinUtil.getParams(request, response);
				body=params.get("body");
			}else if (payType.equals(VisitLog.ALIPAY)) {
				params = AlipayPayUtils.getParams(request, response);
				body=params.get("body");
			}else if(payType.equals(VisitLog.YILIAN)){
				String extData = request.getParameter("ExtData");
				body = new String(Base64.decode(extData), ConstantsClient.PAYECO_DATA_ENCODE);
			}else if(payType.equals("111")){
				params = WeixinUtil.getParams(request, response);
				body=params.get("body");
			}
		
			JSONObject ob = JSONObject.parseObject(body);
			userId = Integer.parseInt(ob.getString("userId"));
			JSONArray array = JSONArray.parseArray(ob.getString("orderIds"));
			for(int i=0;i<array.size();i++){
				String order= array.getString(i);
				order=order.substring(5,order.length()-5);
				if(i==(CalcUtil.sub(array.size(), 1))){
					orderIds.append(order);
				}else{
					orderIds.append(order+",");
				}
			}
			JSONObject obj=new JSONObject();
			String data="";
			if(payType.equals(VisitLog.WEIXIN)){
				obj=JSONObject.parseObject(body);
				data=params.toString();
			}else{
				Enumeration<String> enu = request.getParameterNames();
				while(enu.hasMoreElements()){  
					String paraName=(String)enu.nextElement();  
					
					obj.put(paraName,request.getParameter(paraName));
				}
				data=obj.toString();
			}
			
			
			
			VisitLog log=new VisitLog();
			log.setCreateTime(new Timestamp(System.currentTimeMillis()));
			log.setData(data);
			log.setPayType(payType);
			log.setType(type);
			log.setUserId(userId);
			log.setOrderId(Integer.parseInt(orderIds.toString()));
			visitLogDao.save(log);
		}
		
		} catch (Exception e) {
			System.out.println("保存回调记录失败!");
		}
		return params;
		
	}
		 

	public void BuyToGetScore(ReGoodsorder order,Integer userId,HttpServletRequest request){
		try {
			Users users = null;
			Seller seller = null;
			AdminUser adminUser = null;
			if (userId!=null) {
				 users=usersDao.findById(userId);
			}
			
			QueryModel model = new QueryModel();
			model.combPreEquals("isValid", true);
			model.combPreEquals("order.id", order.getId(), "order_id");
			List<ReGoodsorderItem> itemlist = dateBaseDAO.findLists(ReGoodsorderItem.class, model);
			Integer sendScoreNum = 0;
			int score = 0;
			ReGoodsOfSellerMall sellerMall = null;
			
			if (itemlist.size()>0) {
				for (ReGoodsorderItem item : itemlist) {
					
					sellerMall = item.getReGoodsOfSellerMall();
					if (sellerMall!=null && sellerMall.getSendScoreNum()!=null && sellerMall.getSendScoreNum()!=0) {
						sendScoreNum = sellerMall.getSendScoreNum();
						seller = sellerMall.getSnapshotGoods().getSeller();
						if (seller!=null) {
							adminUser = seller.getAdminUser();
							if (sendScoreNum<adminUser.getScore()) {
								score = (int) CalcUtil.sub(adminUser.getScore(), sendScoreNum);
								adminUser.setScore(score);
								adminUserDao.saveOrUpdate(adminUser);
								AdminUserScoreRecord scoreRecord2 = new AdminUserScoreRecord();
								scoreRecord2.setAdminUser(adminUser);
								scoreRecord2.setBeforeScore(adminUser.getScore());
								scoreRecord2.setAfterScore(score);
								scoreRecord2.setSurplusScore(score);
								scoreRecord2.setCreateTime(new Timestamp(System.currentTimeMillis()));
								scoreRecord2.setScore(-sendScoreNum);
								scoreRecord2.setIsValid(true);
								scoreRecord2.setType(17);
								scoreRecord2.setFromAdminUser(adminUser);
								scoreRecord2.setItem(item);
								scoreRecord2.setRemark(users.getRealname()+"用户购买商品"+sellerMall.getSnapshotGoods().getName()+"成功,将扣除您"+sendScoreNum+"积分");
								adminUserScoreRecordDao.save(scoreRecord2);
								
								
							}
						}
						if (users!=null) {
							   score = (int) CalcUtil.add(users.getScore(), sendScoreNum, 0);
							   users.setScore(score);
							   usersDao.saveOrUpdate(users);
							    Scorerecords scorerecords = new Scorerecords();
								scorerecords.setBeforeScore(users.getScore());
								scorerecords.setAfterScore(score);
								scorerecords.setIsvalid(true);
								scorerecords.setCreatetime(new Timestamp(System.currentTimeMillis()));
								scorerecords.setValidityTime(new Timestamp(DateUtil.addDay2Date(180, new Date()).getTime()));
								scorerecords.setScore(sendScoreNum);
								scorerecords.setScoretype("购买"+sellerMall.getSnapshotGoods().getName()+"获得了"+seller.getName()+"赠送的"+sendScoreNum+"积分");
								scorerecords.setRemark("购买"+sellerMall.getSnapshotGoods().getName()+"获得了"+seller.getName()+"赠送的"+sendScoreNum+"积分");
								scorerecords.setType(17);
								scorerecords.setAdminuserId(adminUser.getId());
								scorerecords.setForeignId(userId);
								scorerecords.setUsers(users);
								scorerecordsDao.save(scorerecords);
								
								
								
						}
					}
					model.clearQuery();
					model.combPreEquals("isValid", true);
					model.combPreEquals("newHands", "s-"+adminUser.getId());
					model.setOrder("refreshTime ASC");
					List<ScoreMark> scoreMarks = dateBaseDAO.findPageList(ScoreMark.class, model, 0, sendScoreNum);
					Timestamp validityTime = new Timestamp(DateUtil.addMonth2Date(2, new Date()).getTime());
					for(ScoreMark s: scoreMarks){
						s.setRemark(s.getRemark()+"-y-"+users.getId());
						s.setNewHands("y-"+users.getId());
						s.setIsValid(true);
						s.setValidityTime(validityTime);
						s.setRefreshTime(new Timestamp(System.currentTimeMillis()));
						scoreMarkDao.update(s);
					}
				}
				
				
				
				 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		
	public String addUsersScore(Users users,String reamrk,Integer score,Users fusers){
		
			int 	a_score = (int) CalcUtil.add(users.getScore(), score, 0);
			   users.setScore(a_score);
			   usersDao.saveOrUpdate(users);
			    Scorerecords scorerecords = new Scorerecords();
				scorerecords.setBeforeScore(users.getScore());
				scorerecords.setAfterScore(a_score);
				scorerecords.setIsvalid(true);
				scorerecords.setCreatetime(new Timestamp(System.currentTimeMillis()));
				scorerecords.setValidityTime(new Timestamp(DateUtil.addDay2Date(180, new Date()).getTime()));
				scorerecords.setScore(score);
				scorerecords.setScoretype(reamrk);
				scorerecords.setRemark(reamrk);
				scorerecords.setType(17);
				scorerecords.setAdminuserId(47);
				scorerecords.setForeignId(fusers.getId());
				scorerecords.setUsers(users);
				scorerecordsDao.save(scorerecords);
		
		return "成功";
	 
	}
	
	
	
	/*
	 *购买普通合伙人/商家合伙人 
	 * */
	public void addPartnerOrSeller(Integer userId,String zoneId,Integer mallId){
		Users users = usersDao.findById(userId);
		Random random = new Random();
		int score=random.nextInt(200)+100;
		
			
		
		QueryModel queryModel = new QueryModel();
		
		queryModel.clearQuery();
		queryModel.combPreEquals("isValid", true);
		queryModel.combCondition("users.id is not null");
		List<TkldPid> tkldPidList= dateBaseDAO.findLists(TkldPid.class, queryModel);
		Map<Integer, Object> partner=new HashMap<Integer, Object>();
		Map<Integer, Object> syPartner=new HashMap<Integer, Object>();
		Map<Integer, Object> dlPartner=new HashMap<Integer, Object>();
		//填充所有事业合伙人
		for (TkldPid tkldPids : tkldPidList) {
				if(tkldPids.getLevel()==1){
					dlPartner.put(tkldPids.getUsers().getId(), tkldPids);
				}else if(tkldPids.getLevel()==2){
					syPartner.put(tkldPids.getUsers().getId(), tkldPids);
				}else{
					partner.put(tkldPids.getUsers().getId(), tkldPids);
				}	
		}
		
		
		TkldPid tp=findParentPidOrpid(users,partner,syPartner,0);;//推荐人
		
		String reamrk="粉丝"+users.getName()+"成为";
		AdminUser adminUser = null;
		TkldPid tkldPid = null;
		
		boolean falg = false;
		
		if(mallId!=null && mallId==354){//高级合伙人
			// ----------da
			String pddPid = tkldPidService.gainPddPid()==null?StringUtil.pdd_pid:tkldPidService.gainPddPid();
			
			reamrk+="高级合伙人,奖励推荐人积分："+score;
			falg = true;
				if(partner.get(users.getId())!=null){
					tkldPid= (TkldPid) partner.get(users.getId());
					tkldPid.setUsers(users);
					tkldPid.setIsCareerPartner(2);
					tkldPid.setLevel(2);
					tkldPid.setAdminUser(null);
					tkldPid.setBindingTime(new Timestamp(System.currentTimeMillis()));
					//da
					tkldPid.setPddPid(pddPid);
					tkldPidDao.update(tkldPid);
				}else{
					queryModel.clearQuery();
					queryModel.combPreEquals("isValid", true);
					queryModel.combPreEquals("tkldPid.id", 0,"parent_pid_id");
					queryModel.combEquals("level", 3);
					queryModel.setOrder("createtime desc");
					List<TkldPid> tklist =  dateBaseDAO.findLists(TkldPid.class, queryModel);
					if (tklist.size()>0) {
						
						if(tklist.size()<15){
							userSystemMessageService.saveMessage("预设合伙人预警","预设合伙人数量剩余"+tklist.size()+"请及时补充");
						}
						
						tkldPid=tklist.get(0);
						tkldPid.setUsers(users);
						tkldPid.setIsCareerPartner(2);
						tkldPid.setTkldPid(null);
						tkldPid.setLevel(2);
						tkldPid.setBindingTime(new Timestamp(System.currentTimeMillis()));
						//da
						tkldPid.setPddPid(pddPid);
						tkldPidDao.update(tkldPid);
					}else{
						savePartnerInform(users,null,"购买用户"+users.getName()+"支付成为高级合伙人，但数据不足",2,1);
						userSystemMessageService.saveMessage("预设合伙人不足","粉丝"+users.getName()+"支付成功！但预设合伙人数据不足");
					}
				}
				
				
			
		}else if(mallId==353){
			reamrk+="合伙人,奖励推荐人积分："+score;
			//返回的事业合伙人对象
			TkldPid cause=null;
			
				cause=	findParentPid(users,partner,syPartner,0);
			if(cause ==null){
				Integer i_zoneId=1961;
				if(zoneId!=null){
					i_zoneId=Integer.parseInt(zoneId);
				}else{
					queryModel.clearQuery();
					queryModel.combPreEquals("users.id",userId,"userId");
					queryModel.setOrder("lasttime desc");
					List<UserLoginRecord> userLoginRecordList = dateBaseDAO.findLists(UserLoginRecord.class, queryModel);
					if(userLoginRecordList.size()>0){
						UserLoginRecord userLoginRecord= userLoginRecordList.get(0);
						i_zoneId =userLoginRecord.getZoneId();
						System.out.println("查找到用户所在地区"+i_zoneId);
					}else{
						System.out.println("未查找到用户所在地区"+i_zoneId);
					}
					
				}
				ProvinceEnum province = provinceEnumDao.findById(i_zoneId);
				if(province!=null){
					Integer provinceid = 1961;
					if(province.getLevel2()==3){//区；或者城区县
						provinceid= province.getProvinceEnumId();
					}else if(province.getLevel2()==2){//县，县级市
						provinceid=province.getId();
					}
					
					queryModel.clearQuery();
					//找到当前地区的事业合伙人
					queryModel.combPreEquals("isValid", true);
					queryModel.combPreEquals("provinceEnum.id", provinceid,"zoneId");
					queryModel.combEquals("level", 2);
					//事业合伙人	区级
					List<TkldPid> dList  = dateBaseDAO.findLists(TkldPid.class, queryModel);
					if(dList!=null && dList.size()>0){
						cause=dList.get(0);
						
						System.out.println("用户"+users.getId()+"在"+province.getName()+"支付购买合伙人资格；未找到粉丝关系对应事业伙人，自动获取当地兜底事业合伙人"+cause.getUsers().getId());
					}
				}
				if(cause==null){
					cause =tkldPidDao.findById(502);
					 
					System.out.println("未找到任何事业合伙人！自动获取总部兜底事业合伙人");
				}
				
				
			}
			
			if(cause!=null){
				savePidNew(cause,users);//绑定
				
				
			}else{
				System.out.println("未找到任何事业合伙人！");
			}
		}
		
		if(tp!=null  ){
			addUsersScore(tp.getUsers(),reamrk,score, users);//推荐人奖励
			if(falg){
				addMoneyForUsers(100d, tp.getUsers(), "", users, null, "粉丝"+users.getName()+"成为高级合伙人，推荐人奖励100", null, null,1);
			}
		}
		
		
	}


	@Override
	public boolean isDeposit(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		Double total = parameter.getData().getDouble("rechargeMoney")==null?0.0:parameter.getData().getDouble("rechargeMoney");
		Integer adminuserId = parameter.getData().getInteger("adminuserId");
		AdminUser adminUser = adminUserDAO.findById(adminuserId);
		int score = (int) (total*5);//充值的积分数
		
		double needDeposit = needDeposit(score);//需要的押金 金额
		if(needDeposit > adminUser.getDeposit()){
			return false;
		}else{
			return true;
		}
	}
	
	private Integer needDeposit(Integer score){
		
		Integer needDeposit = 0;
		if(score < 1000){
			needDeposit = 500;
		}else if(score > 1000 && score < 2000){
			needDeposit = 1000;
		}else if(score > 2000 && score < 4000){
			needDeposit = 2000;
		}else if(score > 4000 && score < 8000){
			needDeposit = 4000;
		}else if(score > 8000 && score < 16000){
			needDeposit = 8000;
		}else if(score > 16000 && score < 20000){
			needDeposit = 10000;
		}
		
		return needDeposit;
		
	}
	
	
	

	@Override
	public boolean isScore(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		Double total = parameter.getData().getDouble("rechargeMoney")==null?0.0:parameter.getData().getDouble("rechargeMoney");
		String adminuserId = parameter.getAdminuserId();
		AdminUser au = adminUserDAO.findById(Integer.valueOf(adminuserId));
		int score = (int) (total*5);//充值的积分数
		//充值的积分属于哪个代理的修改 没有合伙人的
		AdminUser audl = null;
		if(au.getParentAdminUser().getParentAdminUser().getId() == 47){
			audl = au.getParentAdminUser();
		}else{
			audl = au.getParentAdminUser().getParentAdminUser();
			
		}
		
		if(audl.getScore()<score){
			return false;
		}
		return true;
	}





	
	@Override
	public void xcxNotify(HttpServletRequest request,
			HttpServletResponse response, Map<String, String> params1) throws Exception{
        String resXml = "";
        String returnCode = (String) params1.get("return_code");
        if("SUCCESS".equals(returnCode)){
            //验证签名是否正确
            Map<String, String> validParams = PayUtil.paraFilter(params1);  //回调验签时需要去除sign和空值参数
            String validStr = PayUtil.createLinkString(validParams);//把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            String sign = PayUtil.sign(validStr, WeixinConfig.xcx_api_key, "utf-8").toUpperCase();//拼装生成服务器端验证的签名
            // 因为微信回调会有八次之多,所以当第一次回调成功了,那么我们就不再执行逻辑了
           
            //根据微信官网的介绍，此处不仅对回调的参数进行验签，还需要对返回的金额与系统订单的金额进行比对等
            if(sign.equals(params1.get("sign"))){
                /**此处添加自己的业务逻辑代码start**/
            	Map<String, String> params = validParams;
    			Double totalFee = Double.parseDouble(params.get("total_fee"));
    			Integer total_fee =Integer.parseInt(params.get("total_fee"));//用于保存原始记录
    			String transaction_id= params.get("transaction_id");//微信支付订单号
    			String timeEnd = params.get("time_end");//支付时间
    			String openId=params.get("openid");//用户openid
    			String outTradeNo= params.get("out_trade_no");
    			totalFee = CalcUtil.mul(totalFee, 0.01, 2);
    			String body = params1.get("body");
    			JSONObject ob = JSONObject.parseObject(body);
				Integer type=ob.getInteger("type")==null?0:ob.getInteger("type"); // 是否合并支付    1:0
				Integer userId = ob.getInteger("userId");
				StringBuffer orderIds = new StringBuffer();
				JSONArray array = JSONArray.parseArray(ob.getString("orderIds"));
				
				for(int i=0;i<array.size();i++){
					String order= array.getString(i);
					order=order.substring(5,order.length()-5);
					if(i==(CalcUtil.sub(array.size(), 1))){
						orderIds.append(order);
					}else{
						orderIds.append(order+",");
					}
				}
				
				Map<String,Object> map1 =new HashMap<String, Object>();
				map1=payOrdersMerge(request, userId, orderIds.toString(), "WEIXIN", totalFee, transaction_id,timeEnd,openId,total_fee, outTradeNo,type,0,null,null);	
				List<ReGoodsorder> orders = reGoodsorderDao.getToBePaidOrders(userId, orderIds.toString());
				QueryModel queryModel = new QueryModel();
				for (ReGoodsorder reGoodsorder : orders) {
					if (reGoodsorder!=null) {
						queryModel.clearQuery();
						queryModel.combPreEquals("order.id", reGoodsorder.getId(),"orderId");
						List<ReGoodsorderItem> items = dateBaseDao.findLists(ReGoodsorderItem.class, queryModel);
						if (reGoodsorder.getStatus()>5) {
							for (ReGoodsorderItem reGoodsorderItem : items) {
								urlUtil.send(reGoodsorder.getSeller().getAdminUser().getPhone(), "【每天积分】您好，"+reGoodsorderItem.getGoodName()+"用户"+reGoodsorder.getUser().getName()+"已经成功付款，订单号："+reGoodsorder.getId()+"，请尽快确认订单，进行发货！");
							  if (reGoodsorder.getIsTeam()==true ) {
								   urlUtil.send(reGoodsorder.getReGoodsorder().getUser().getName(), "【每天积分】您好，您发起的拼团订单"+reGoodsorderItem.getOrder().getOrderCode()+"已成功！");
							  }
							
							}
						}
					}
				}
    			
                /**此处添加自己的业务逻辑代码end**/
                //通知微信服务器已经支付成功
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            } else {
                System.out.println("微信支付回调失败!签名不一致");
            }
        }else{
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        System.out.println("微信支付回调数据结束");
 
        BufferedOutputStream out = new BufferedOutputStream(
                response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    
		
	}
	

	private double sellerMoney(Users user, ReGoodsorderItem item) {
		ReGoodsOfLockMall lockMall = reGoodsOfLockMallDao.findById(item.getGoodsId());
		double money = 0;
		try {
			int payScore = 0;
			if(item.getPayScore()>0){
				payScore = item.getPayScore(); // 剩余的就是平台的
				//商品归属商家   活动没有结束 不添加分佣
				double fenyong = CalcUtil.mul(payScore, 0.15,4);//0.16
				Seller seller = item.getReGoodsOfSellerMall().getSnapshotGoods().getSeller();
				AdminUser adminUser = seller.getAdminUser();
				double cashPoint = adminUser.getMoney()==null?0.0:adminUser.getMoney();
				
				AdminuserCashpointRecord acr = new AdminuserCashpointRecord();
				acr.setAdminUser(adminUser);
				acr.setAfterpoint(cashPoint + fenyong);
				acr.setBeforepoint(cashPoint);
				acr.setCashpoint(fenyong);
				acr.setCreateTime(new Timestamp(System.currentTimeMillis()));
				acr.setIsValid(true);
				acr.setRemark("用户"+user.getName()+",兑换商品使用"+payScore+"积分,活动结束可获得分佣:"+fenyong+"元");
				acr.setOrderItem(item);
				acr.setUsers(user);
				acr.setType(-1); //使用-1区分 未确认 金额
				acr.setIsDeposit(4);
				acr.setGoodsId(item.getGoodsId());
				adminuserCashpointRecordDAO.save(acr);
				
				//分出去1%
				getMoneySeller_100(item, seller, user,2);
				
				QueryModel model = new QueryModel();
				model.clearQuery();
				model.combPreEquals("isValid", true);
				model.combPreEquals("users.id", user.getId(),"usersId");
				model.combCondition("reGoodsorder is null"); //没有使用过的
				model.setOrder("validityTime ASC");
				List<UserScoreMark> usms = dateBaseDAO.findPageList(UserScoreMark.class, model, 0, payScore);
				Map<Integer ,Integer> sellerToScoreNum = new HashMap<Integer ,Integer>();
				//处理积分走向 用户和商家的清除, 代理的分配字段置空
				for(UserScoreMark  usm: usms){
					Integer sellerId = usm.getAdminUser().getId();
					if(sellerToScoreNum != null && sellerToScoreNum.size()>0){
						if(sellerToScoreNum.containsKey(sellerId)){
							sellerToScoreNum.put(sellerId, sellerToScoreNum.get(sellerId)+1);
						}else{
							sellerToScoreNum.put(sellerId, 1);
						}
					}else{
						sellerToScoreNum.put(sellerId, 1);
					}
					usm.setReGoodsorder(item.getOrder());
					usm.setLockMall(lockMall);
					usm.setRefreshTime(new Timestamp(System.currentTimeMillis()));
					userScoreMarkDAO.saveOrUpdate(usm);
					
				}
				//处理积分分佣情况
				Set keys = sellerToScoreNum.keySet();
				for(Object obj:keys){
					Integer key = (Integer)obj; // 商家id
					Integer value = (Integer) sellerToScoreNum.get(key); //商家对应的积分
					//处理商家
					AdminUser adseller = adminUserDao.findById(key);
					double maidsj = CalcUtil.mul(value, 0.2,4); //积分消费了扣除提供积分的商家押金20%
					double cashPoints = 0;
					if(adseller.getDeposit()!=null){
						cashPoints = adseller.getDeposit();
					}
					AdminuserCashpointRecord smr = new AdminuserCashpointRecord();
					smr.setAdminUser(adseller);
					smr.setAfterpoint(adseller.getDeposit()- maidsj);
					smr.setBeforepoint(cashPoints);
					smr.setCashpoint(maidsj);
					smr.setIsValid(true);
					smr.setType(-1);
					smr.setIsDeposit(5);
					smr.setUsers(user);
					smr.setOrderItem(item);
					smr.setGoodsId(item.getGoodsId());
					smr.setCreateTime(new Timestamp(System.currentTimeMillis()));
					smr.setRemark("用户:"+user.getRealname()+"消耗"+value+"积分,活动结束将扣除押金"+maidsj+"元并回收积分和清除记录");
					adminuserCashpointRecordDao.save(smr);
					
					//商家对应的代理
					AdminUser dl = adseller.getParentAdminUser();
					AdminUserScoreRecord adcr = new AdminUserScoreRecord();
					adcr.setAdminUser(dl);
					adcr.setAfterScore(dl.getScore()+value);
					adcr.setBeforeScore(dl.getScore());
					adcr.setRemark("用户:"+user.getRealname()+"消费了"+adseller.getLoginname()+"商家,赠送的"+value+"积分,活动结束积分回收");
					adcr.setFromAdminUser(adseller);
					adcr.setScore(value);
					adcr.setType(-1);
					adcr.setIsValid(true);
					adcr.setItem(item);
					adcr.setGoodsId(item.getGoodsId());
					adcr.setCreateTime(new Timestamp(System.currentTimeMillis()));
					adminUserScoreRecordDao.save(adcr);
					
				}
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return money;
	}
	
	
	 // 已经开奖了
	private void isLockItem(Users user, ReGoodsOfLockMall lockMall) {
		QueryModel model = new  QueryModel();
		model.clearQuery();
		model.combPreEquals("isValid", true);
		model.combPreEquals("isDeposit", 4);
		model.combPreEquals("type", -1);
		model.combPreEquals("goodsId", lockMall.getId(),"goodsId");
		List<AdminuserCashpointRecord> acr = dateBaseDAO.findLists(AdminuserCashpointRecord.class, model);
		double cashMoney = 0.0;
		List<Users> userList = new ArrayList<Users>();
		for(AdminuserCashpointRecord a : acr){
			a.setRemark("用户"+a.getUsers().getRealname()+",兑换商品使用"+lockMall.getScore()+"积分,获得分佣:"+a.getCashpoint()+"元");
			a.setType(1);
			adminuserCashpointRecordDAO.saveOrUpdate(a);
			cashMoney += a.getCashpoint();
			userList.add(a.getUsers());
		}
		Seller seller = lockMall.getReGoodsOfSellerMall().getSnapshotGoods().getSeller();
		AdminUser adminUser = seller.getAdminUser();
		double cashPoint = adminUser.getMoney()==null?0.0:adminUser.getMoney();
		adminUser.setMoney(cashPoint + cashMoney);
		adminUserDao.saveOrUpdate(adminUser);
		
		//
		for(Users u:userList){
			
			model.clearQuery();
			model.combPreEquals("isValid", true);
			model.combPreEquals("users.id", u.getId(),"usersId");
			model.combPreEquals("lockMall.id", lockMall.getId(),"lockMallId");
			model.setOrder("validityTime ASC");
			List<UserScoreMark> usms = dateBaseDAO.findPageList(UserScoreMark.class, model, 0, lockMall.getScore());
			Map<Integer ,Integer> sellerToScoreNum = new HashMap<Integer ,Integer>();
			//处理积分走向 用户和商家的清除, 代理的分配字段置空
			for(UserScoreMark  usm: usms){
				Integer sellerId = usm.getAdminUser().getId();
				if(sellerToScoreNum != null && sellerToScoreNum.size()>0){
					if(sellerToScoreNum.containsKey(sellerId)){
						sellerToScoreNum.put(sellerId, sellerToScoreNum.get(sellerId)+1);
					}else{
						sellerToScoreNum.put(sellerId, 1);
					}
				}else{
					sellerToScoreNum.put(sellerId, 1);
				}
				DLScoreMark dlsm = usm.getSjScoreMark().getDlScoreMark();
				dlsm.setAdminUserSeller(null);
				dlsm.setRefreshTime(new Timestamp(System.currentTimeMillis()));
				dlScoreMarkDAO.saveOrUpdate(dlsm);
				SJScoreMark sjsm = usm.getSjScoreMark();
				sjScoreMarkDAO.delete(sjsm);
				userScoreMarkDAO.delete(usm);
			}

			model.clearQuery();
			model.combPreEquals("isValid", true);
			model.combPreEquals("isDeposit", 5);
			model.combPreEquals("goodsId", lockMall.getId(),"goodsId");
			model.combPreEquals("users.id", u.getId(),"fromUser");
			model.combPreEquals("type", -1);
			List<AdminuserCashpointRecord> smrs = dateBaseDAO.findLists(AdminuserCashpointRecord.class, model);
			for(AdminuserCashpointRecord smr :smrs){
				smr.setCashpoint(smr.getCashpoint());
				smr.setType(1);
				smr.setIsDeposit(5);
				smr.setUsers(user);
				smr.setRemark("用户:"+smr.getUsers().getRealname()+"消耗"+lockMall.getScore()+"积分,扣除押金"+smr.getCashpoint()+"元并回收积分和清除记录");
				
				double cashPoints = 0;
				AdminUser au = smr.getAdminUser();
				if(au.getDeposit()!=null){
					cashPoints = au.getDeposit();
				}
				au.setDeposit(CalcUtil.sub(cashPoints, smr.getCashpoint()));  //押金减少
				adminUserDao.saveOrUpdate(au);
				adminuserCashpointRecordDao.save(smr);
			}
			//处理积分分佣情况
			AdminUser zb = adminUserDAO.findById(47);
			Set keys = sellerToScoreNum.keySet();
			for(Object obj:keys){
				Integer key = (Integer)obj; // 商家id
				Integer value = (Integer) sellerToScoreNum.get(key); //商家对应的积分
				AdminUser adseller = adminUserDao.findById(key);
				//商家对应的代理
				AdminUser dl = adseller.getParentAdminUser();
				model.clearQuery();
				model.combPreEquals("isValid", true);
				model.combPreEquals("goodsId", lockMall.getId());
				model.combPreEquals("fromAdminUser.id", key,"fromUser");
				model.combPreEquals("adminUser.id", dl.getId(),"adminuserId");
				model.combPreEquals("type", -1);
				List<AdminUserScoreRecord> adcrs = dateBaseDAO.findLists(AdminUserScoreRecord.class, model);
				Integer score = 0;
				
				for(AdminUserScoreRecord adcr :adcrs){
					adcr.setRemark("用户:"+adcr.getFromAdminUser().getId()+"消费了"+adcr.getAdminUser().getLoginname()+"商家,赠送的"+value+"积分,积分回收");
					adcr.setType(1);
					adminUserScoreRecordDao.save(adcr);
					score += adcr.getScore();
				}
				double maidhhr = CalcUtil.mul(value, 0.03,4); //合伙人 收益
				double dlMaid = CalcUtil.mul(value, 0.005,4); //代理 和总部增加收益
				double bounsMaid = CalcUtil.mul(value, 0.04, 6);
				dl.setMoney(CalcUtil.add((dl.getMoney()==null?0.0:dl.getMoney()), dlMaid, 4));
				dl.setScore(dl.getScore()+score);
				dl.setLasttime(new Timestamp(System.currentTimeMillis()));
				adminUserDao.update(dl);
				
				Bonus bonus = new Bonus();
				bonus.setAdminUser(dl);//归属代理
				bonus.setCreateTime(new Timestamp(System.currentTimeMillis()));
				bonus.setMaid(bounsMaid);//奖金池分分佣
				bonus.setMaiddl(dlMaid);
				bonus.setMaidhhr(maidhhr);
				bonus.setScore(value);
				bonus.setUser(user);
				bonus.setAdminUserBuy(adseller);
				bonus.setRecoveryScore(value);
				//bonus.setItem(lockMall);
				bonusDao.save(bonus);
				
				
				zb.setMoney(CalcUtil.add(zb.getMoney(), dlMaid, 4));
				adminUserDAO.saveOrUpdate(zb);
				
				
			}
		}
		
		reGoodsOfLockMallService.delLockMallGoods(lockMall);
	}
		 
}

