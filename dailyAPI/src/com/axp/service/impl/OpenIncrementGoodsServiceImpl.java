package com.axp.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axp.domain.AdminUser;
import com.axp.domain.AdminuserCashpointRecord;
import com.axp.domain.CashmoneyRecord;
import com.axp.domain.OpenIncrementGoods;
import com.axp.domain.SystemConfig;
import com.axp.domain.TkldPid;
import com.axp.domain.Users;
import com.axp.service.OpenIncrementGoodsService;
import com.axp.service.UserSystemMessageService;
import com.axp.util.CalcUtil;
import com.axp.util.DateUtil;
import com.axp.util.JsonResponseUtil;
import com.axp.util.MD5Util;
import com.axp.util.QueryModel;
import com.axp.util.StringUtil;
import com.axp.util.UrlUtil;


//------da

@Service
public class OpenIncrementGoodsServiceImpl extends BaseServiceImpl<OpenIncrementGoods> implements OpenIncrementGoodsService{
	@Autowired
	public UserSystemMessageService userSystemMessageService;
	@Override
	public Map<String, Object> findIncrementList() {
		
		Map<String,Object> statusMap = new HashMap<String,Object>();
		
		SystemConfig scf = systemConfigDao.findById(5);
		
		
		String endTime = String.valueOf(System.currentTimeMillis()/1000);//同样的处理方式
		String startTime = scf.getPddOrderModifyAt();
		
		Map<String,String> signMap = new HashMap<String,String>();
		signMap.put("client_id", StringUtil.pdd_client_id);
		String sign = MD5Util.getSign(signMap,StringUtil.pdd_client_screct);
		Map<String, Object> map = new HashMap<String, Object>();
		map = UrlUtil.getTaoKeToMap("http://open.aixiaoping.cn:8080/open/api/findIncrementList?data={\"client_id\":\""+StringUtil.pdd_client_id+"\",\"sign\":\""+sign+"\",\"data\":{\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\"}}");
		
		if(map.get("openGoods")  == null){
			
			return JsonResponseUtil.getJson(-0x02, "没有查询到相关信息");
			
		}else{//有信息的 要么保存要么更新
			
			List<Map<String,Object>> openGoods = (List<Map<String, Object>>) map.get("openGoods");
			for(int i = 0;i<openGoods.size();i++){
				double pa=0d;
				Map<String, Object> gmap = openGoods.get(i);
				Integer gid=null;
      		  	if(gmap.get("id")!=null){
      		  		gid =(Integer)gmap.get("id");
      		  	}
      		  	String orderReceiveTime=null;
    		  	if(gmap.get("orderReceiveTime")!=null){
    		  		orderReceiveTime =gmap.get("orderReceiveTime")+"";
    		  	}
    		  	String customParameters=null;
    		  	if(gmap.get("customParameters")!=null){
    		  		customParameters =gmap.get("customParameters")+"";
    		  	}
    		  	String type=null;
    		  	if(gmap.get("type")!=null){
    		  		type =gmap.get("type")+""; //属于订单来源的
    		  	}
    		  	String orderVerifyTime=null;
    		  	if(gmap.get("orderVerifyTime")!=null){
    		  		orderVerifyTime =gmap.get("orderVerifyTime")+"";
    		  	}
    		  	String orderPayTime=null;
    		  	if(gmap.get("orderPayTime")!=null){
    		  		orderPayTime =gmap.get("orderPayTime")+"";
    		  	}
    		  	String orderGroupSuccessTime=null;
    		  	if(gmap.get("orderGroupSuccessTime")!=null){
    		  		orderGroupSuccessTime =gmap.get("orderGroupSuccessTime")+"";
    		  	}
    		  	String orderModifyAt=null;
    		  	if(gmap.get("orderModifyAt")!=null){
    		  		orderModifyAt =gmap.get("orderModifyAt")+"";
    		  	}
    		  	String orderStatusDesc=null;
    		  	if(gmap.get("orderStatusDesc")!=null){
    		  		orderStatusDesc =gmap.get("orderStatusDesc")+"";
    		  	}
    		  	String pid=null;
    		  	if(gmap.get("pid")!=null){
    		  		pid =gmap.get("pid")+"";
    		  	}
    		  	String orderStatus=null;
      		  	if(gmap.get("orderStatus")!=null){
      		  		orderStatus =gmap.get("orderStatus")+"";
      		  	}
      		  	String promotionAmount=null;
    		  	if(gmap.get("promotionAmount")!=null){
    		  		promotionAmount =gmap.get("promotionAmount")+"";
    		  		 pa = CalcUtil.mul(Double.parseDouble(promotionAmount), 0.95,2);
    		  				 
    		  	}
    		  	String promotionRate=null;
    		  	if(gmap.get("promotionRate")!=null){
    		  		promotionRate =gmap.get("promotionRate")+"";
    		  	}
    		  	String orderCreateTime=null;
    		  	if(gmap.get("orderCreateTime")!=null){
    		  		orderCreateTime =gmap.get("orderCreateTime")+"";
    		  	}
    		  	String orderAmount=null;
    		  	if(gmap.get("orderAmount")!=null){
    		  		orderAmount =gmap.get("orderAmount")+"";
    		  	}
    		  	String goodsPrice=null;
    		  	if(gmap.get("goodsPrice")!=null){
    		  		goodsPrice =gmap.get("goodsPrice")+"";
    		  	}
    		  	String goodsQuantity=null;
    		  	if(gmap.get("goodsQuantity")!=null){
    		  		goodsQuantity =gmap.get("goodsQuantity")+"";
    		  	}
    		  	String goodsThumbnailUrl=null;
    		  	if(gmap.get("goodsThumbnailUrl")!=null){
    		  		goodsThumbnailUrl =gmap.get("goodsThumbnailUrl")+"";
    		  	}
    		  	String goodsName=null;
    		  	if(gmap.get("goodsName")!=null){
    		  		goodsName =gmap.get("goodsName")+"";
    		  	}
    		  	String goodsId=null;
    		  	if(gmap.get("goodsId")!=null){
    		  		goodsId =gmap.get("goodsId")+"";
    		  	}
    		  	String orderSn=null;
    		  	if(gmap.get("orderSn")!=null){
    		  		orderSn =gmap.get("orderSn")+"";
    		  	}
    		  	String upPid=null;
    		  	if(gmap.get("upPid")!=null){
    		  		upPid =gmap.get("upPid")+"";
    		  	}
    		  	String isWithdraw=null;
    		  	if(gmap.get("isWithdraw")!=null){
    		  		isWithdraw =gmap.get("isWithdraw")+"";
    		  	}
    		  	
    		  	
    		  	
    		  	//保存订单信息
    		  	QueryModel query = new QueryModel();
    		  	query.clearQuery();
    			query.combPreEquals("orderSn", orderSn,"order_sn");
    			OpenIncrementGoods opicg = (OpenIncrementGoods) dateBaseDAO.findOne(OpenIncrementGoods.class, query);
    			List<AdminUser> adminUserList = new ArrayList<AdminUser>();
    			List<Users> userList = new ArrayList<Users>();
    			
    			if(opicg==null){
    				opicg=new OpenIncrementGoods();
    				String [] cpts = customParameters.split("-");
    				String cpt = cpts[0].substring(0, 8);
    				
    				try {
						if(cpts[1] != null && StringUtils.isNotBlank(cpts[1])){
							//代理级别的
							query.clearQuery();
							query.combPreEquals("isValid", true);
							query.combPreEquals("pddPid", cpt+cpts[1]);
							List<TkldPid> tks =  dateBaseDAO.findLists(TkldPid.class, query);
							AdminuserCashpointRecord adminUserChRecord = new AdminuserCashpointRecord();
							if(tks != null && tks.size()>0){
								TkldPid tk = tks.get(0);
								double beforeMoney = tk.getAdminUser().getMoney();//之前的钱
								double money = pa /100 * StringUtil.dlfy;
								adminUserChRecord.setType(-1);
								adminUserChRecord.setAfterpoint(CalcUtil.add(beforeMoney, money));
								adminUserChRecord.setBeforepoint(beforeMoney);
								adminUserChRecord.setCashpoint(money);
								adminUserChRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
								adminUserChRecord.setRemark("团队推广员:"+tk.getUsers().getRealname()+",推广了商品:"+goodsName+
														",获得分佣"+money+"元,确认收货拼多多审核通过后方可提现,订单状态("+orderStatusDesc+")");
								adminUserChRecord.setAdminUser(tk.getAdminUser());
								adminuserCashpointRecordDao.saveOrUpdate(adminUserChRecord);
								
								adminUserList.add(tk.getAdminUser());
								userSystemMessageService.saveMessageForAdmin("2", adminUserChRecord.getRemark(), StringUtil.MESSAGE_FENYONG, "销售信息",adminUserList, "", money, null);
							}
							
							
							
						}
						if(cpts[3] != null && StringUtils.isNotBlank(cpts[3])){
							//上级
							query.clearQuery();
							query.combPreEquals("isValid", true);
							query.combPreEquals("pddPid", cpt+cpts[3]);
							List<TkldPid> tks = dateBaseDAO.findLists(TkldPid.class, query);
							CashmoneyRecord userMoneyRecord =  new CashmoneyRecord();
							if(tks != null && tks.size()>0){
								TkldPid tk = tks.get(0);
								double beforeMoney = tk.getUsers().getMoney();//之前的钱
								double money = 0.0;
								money = pa /100 * StringUtil.sjfy;
								userMoneyRecord.setType(-1);
								userMoneyRecord.setAfterMoney(CalcUtil.add(beforeMoney, money));
								userMoneyRecord.setBeforeMoney(beforeMoney);
								userMoneyRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
								userMoneyRecord.setMoney(money);
								userMoneyRecord.setRemark("下级推广者"+tk.getUsers().getRealname()+"购买商品:"+goodsName+",获得分佣"+money+"元,确认收货拼多多审核通过后方可提现,订单状态("+orderStatusDesc+")");
								userMoneyRecord.setOrderSn(orderSn);
								userMoneyRecord.setUsersByFromUsers(tk.getUsers());
								userMoneyRecord.setUsersByUserId(tk.getUsers());
								userMoneyRecord.setIsValid(true);
								cashmoneyRecordDao.saveOrUpdate(userMoneyRecord);
								
								userList.add(tk.getUsers());
								userSystemMessageService.saveMessage("1",userMoneyRecord.getRemark(), StringUtil.MESSAGE_FENYONG, "销售信息", userList, "", money, null);
								
							}
							
						}
						if(cpts[4] != null && StringUtils.isNotBlank(cpts[4])){
							//自己
							query.clearQuery();
							query.combPreEquals("isValid", true);
							query.combPreEquals("pddPid", cpt+cpts[4]);
							List<TkldPid> tks = dateBaseDAO.findLists(TkldPid.class, query);
							CashmoneyRecord userMoneyRecord =  new CashmoneyRecord();
							if(tks != null && tks.size()>0){
								TkldPid tk = tks.get(0);
								double beforeMoney = tk.getUsers().getMoney();//之前的钱
								double money = 0.0;
								money = pa /100 * StringUtil.fy;
								userMoneyRecord.setType(-1);
								userMoneyRecord.setAfterMoney(CalcUtil.add(beforeMoney, money));
								userMoneyRecord.setBeforeMoney(beforeMoney);
								userMoneyRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
								userMoneyRecord.setMoney(money);
								userMoneyRecord.setRemark("您购买了商品:"+goodsName+",获得分佣"+money+"元,确认收货拼多多审核通过后方可提现,订单状态("+orderStatusDesc+")");
								userMoneyRecord.setOrderSn(orderSn);
								userMoneyRecord.setUsersByFromUsers(tk.getUsers());
								userMoneyRecord.setUsersByUserId(tk.getUsers());
								cashmoneyRecordDao.saveOrUpdate(userMoneyRecord);
								userMoneyRecord.setIsValid(true);
								userList.add(tk.getUsers());
								userSystemMessageService.saveMessage("1",userMoneyRecord.getRemark(), StringUtil.MESSAGE_FENYONG, "销售信息", userList, "", money, null);
								
							}
							
						}
						
						
						
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return JsonResponseUtil.getJson(-0x02,"添加交易记录错误");
						
					}
    			}else{
    				//找到对应的记录,然后修改状态
    				try {
						if(opicg.getOrderStatus() == 2 && "3".equals(orderStatus)){
							query.clearQuery();
							query.combPreEquals("orderSn", orderSn);
							query.combPreEquals("type", -1);
							//代理级别
							List<AdminuserCashpointRecord> adminUserChRList =  dateBaseDAO.findLists(AdminuserCashpointRecord.class, query);
							if(adminUserChRList != null && adminUserChRList.size()>0){
								AdminuserCashpointRecord adminUserChRecord = adminUserChRList.get(0);
								adminUserChRecord.setType(1);
								adminuserCashpointRecordDao.saveOrUpdate(adminUserChRecord);
								AdminUser adminUser = adminUserChRecord.getAdminUser();
								adminUser.setMoney(CalcUtil.add(adminUser.getMoney(), adminUserChRecord.getCashpoint()));
								adminUserDao.saveOrUpdate(adminUser);
								
								String remark = "订单:"+orderSn+",确认收货,拼多多审核通过,佣金增加"+adminUserChRecord.getCashpoint()+"元,账户余额:"+adminUser.getMoney()+"可通过资金管理查看并提现";
								adminUserList.add(adminUser);
								userSystemMessageService.saveMessageForAdmin("2", remark, 3, "资金信息",adminUserList, "", adminUserChRecord.getCashpoint(), null);
							
							}
							
							//非代理的
							List<CashmoneyRecord> userMoneyRecord = dateBaseDAO.findLists(CashmoneyRecord.class, query);
							for(CashmoneyRecord umr : userMoneyRecord){
								umr.setType(1);
								cashmoneyRecordDao.saveOrUpdate(umr);
								Users user = umr.getUsersByUserId();
								user.setMoney(CalcUtil.add(user.getMoney(), umr.getMoney()));
								usersDao.saveOrUpdate(user);
								
								String remark = "订单:"+orderSn+",确认收货,拼多多审核通过,佣金增加"+umr.getMoney()+"元,账户余额:"+user.getMoney()+"元,可通过资金管理查看并提现";
								userList.add(user);
								userSystemMessageService.saveMessage("1",remark, 3, "资金信息", userList, "", umr.getMoney(), null);
								
							}
							
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return JsonResponseUtil.getJson(-0x02,"更新交易记录错误");
					}
    				
    			}
    			try {
					opicg.setCustomParameters(customParameters);
					opicg.setGoodsId(goodsId);
					opicg.setOrderSn(orderSn);
					opicg.setGoodsName(goodsName);
					opicg.setGoodsPrice(goodsPrice);
					opicg.setGoodsQuantity(Integer.parseInt(goodsQuantity));
					opicg.setGoodsThumbnailUrl(goodsThumbnailUrl);
					opicg.setOrderAmount(orderAmount);
					opicg.setOrderCreateTime(orderCreateTime);
					opicg.setOrderGroupSuccessTime(orderGroupSuccessTime);
					opicg.setOrderModifyAt(orderModifyAt);
					opicg.setOrderPayTime(orderPayTime);
					opicg.setOrderReceiveTime(orderReceiveTime);
					opicg.setOrderStatus(Integer.parseInt(orderStatus));
					opicg.setOrderStatusDesc(orderStatusDesc);
					opicg.setOrderVerifyTime(orderVerifyTime);
					opicg.setPid(pid);
					opicg.setPromotionAmount(CalcUtil.mul(pa, 1.0, 2));
					opicg.setPromotionRate(promotionRate);
					opicg.setType(type);
					opicg.setIsWithdraw(0+"");
					openIncrementGoodsDao.saveOrUpdate(opicg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return JsonResponseUtil.getJson(-0x02,"订单更新有误");
				}
			}
			scf.setPddOrderModifyAt(endTime);
			systemConfigDao.saveOrUpdate(scf);
			
			
		}
		
		statusMap.put("endTime", endTime);
		
		
		return statusMap;
	}
	
	
	
	
	
	
	
}
	
