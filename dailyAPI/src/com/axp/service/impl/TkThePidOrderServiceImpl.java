package com.axp.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axp.domain.CommissionRate;
import com.axp.domain.TkThePidOrder;
import com.axp.domain.TkThePidOrderInfo;
import com.axp.domain.TkldPid;
import com.axp.domain.Users;
import com.axp.domain.UsersMoneyRecord;
import com.axp.service.CommissionRateService;
import com.axp.service.TkThePidOrderService;
import com.axp.service.TkldPidService;
import com.axp.util.CalcUtil;
import com.axp.util.QueryModel;
@Service
public class TkThePidOrderServiceImpl  extends BaseServiceImpl<TkThePidOrder> implements TkThePidOrderService{

	
	@Autowired
	private CommissionRateService commissionRateService;
	
	//分配收益
	public void distributeEarnings(TkThePidOrder tkThePidOrder,CommissionRate commissionRate){
		 TkldPid tkldPid = tkThePidOrder.getTkldPid();
		 TkThePidOrderInfo orderInfo3=null; //合伙人
		 TkThePidOrderInfo orderInfo2=null; //事业
		 TkThePidOrderInfo orderInfo1=null; //代理
		 Double money=tkThePidOrder.getDistributeMoney();
		 Double hhr=CalcUtil.div(CalcUtil.mul(money,commissionRate.getPartner()), 100, 2);
		 Double sy= CalcUtil.div(CalcUtil.mul(money,commissionRate.getCareer()), 100, 2);
		 Double sy2= CalcUtil.div(CalcUtil.mul(money,commissionRate.getCareer2()), 100, 2);
		 Double dl= CalcUtil.div(CalcUtil.mul(money,commissionRate.getAgent()), 100, 2);
		 hhr=hhr>0.01?hhr:0d;
		 sy=sy>0.01?sy:0d;
		 sy2=sy2>0.01?sy2:0d;
		 dl=dl>0.01?dl:0d;
		 if(tkldPid.getLevel()==3&&tkldPid.getTkldPid()!=null&&tkldPid.getTkldPid().getTkldPid()!=null){
			 orderInfo3=buildOrderInfo(tkThePidOrder,hhr,tkldPid);
			 orderInfo2=buildOrderInfo(tkThePidOrder,sy,tkldPid.getTkldPid());
			 orderInfo1=buildOrderInfo(tkThePidOrder,dl,tkldPid.getTkldPid().getTkldPid());
			 
			 saveOrUpdateRrcored(tkldPid,dl,tkThePidOrder.getOrderNumber());
			 saveOrUpdateRrcored(tkldPid.getTkldPid(),sy,tkThePidOrder.getOrderNumber());
			 saveOrUpdateRrcored(tkldPid.getTkldPid().getTkldPid(),hhr,tkThePidOrder.getOrderNumber());
			 
		 }else if(tkldPid.getLevel()==3&&tkldPid.getTkldPid()!=null){
			 orderInfo3=buildOrderInfo(tkThePidOrder,hhr,tkldPid);
			 orderInfo2=buildOrderInfo(tkThePidOrder,sy,tkldPid.getTkldPid());
			 
			 saveOrUpdateRrcored(tkldPid,hhr,tkThePidOrder.getOrderNumber());
			 saveOrUpdateRrcored(tkldPid.getTkldPid(),sy,tkThePidOrder.getOrderNumber());
			 
		 }else if(tkldPid.getLevel()==2&&tkldPid.getTkldPid()!=null){
			 orderInfo2=buildOrderInfo(tkThePidOrder,sy2,tkldPid);
			 orderInfo1=buildOrderInfo(tkThePidOrder,dl,tkldPid.getTkldPid());
			 
			 saveOrUpdateRrcored(tkldPid,sy2,tkThePidOrder.getOrderNumber());
			 saveOrUpdateRrcored(tkldPid.getTkldPid(),dl,tkThePidOrder.getOrderNumber());
			 
		 }else if(tkldPid.getLevel()==2&&tkldPid.getTkldPid()==null){
			 orderInfo2=buildOrderInfo(tkThePidOrder, sy2,tkldPid);
			 
			 saveOrUpdateRrcored(tkldPid,sy2,tkThePidOrder.getOrderNumber());
			 
		 }else if(tkldPid.getLevel()==1){
			 money=dl+sy+hhr;
			 orderInfo1=buildOrderInfo(tkThePidOrder,money,tkldPid);
			 
			 saveOrUpdateRrcored(tkldPid,money,tkThePidOrder.getOrderNumber());
		 }
		 
		 if(orderInfo3!=null){ //合伙人
			 orderInfo3.setCommissionRate(commissionRate);
			 tkThePidOrderInfoDao.save(orderInfo3);
		 }
		 if(orderInfo2!=null){ //事业
			 orderInfo2.setCommissionRate(commissionRate);
			 tkThePidOrderInfoDao.save(orderInfo2);
		 }
		 if(orderInfo1!=null){ //代理
			 orderInfo1.setCommissionRate(commissionRate);
			 tkThePidOrderInfoDao.save(orderInfo1);
		 }
		 
		 tkThePidOrder.setIsAmount(1);
		 tkThePidOrderDao.update(tkThePidOrder);
		 
	}

	
	//构建OrderInfo对象
	private TkThePidOrderInfo buildOrderInfo(TkThePidOrder order,Double money,TkldPid tkldPid){
		
		TkThePidOrderInfo orderInfo=new TkThePidOrderInfo();
		orderInfo.setTkldPid(tkldPid);
		orderInfo.setOrderNumber(order.getOrderNumber());
		orderInfo.setPayMoney(order.getPayMoney());
		orderInfo.setDistributeMoney(money);
		orderInfo.setOrderStatus(order.getOrderStatus());
		orderInfo.setOrderDate(order.getOrderDate());
		orderInfo.setOrderName(order.getOrderName());
		orderInfo.setOrderPic(order.getOrderPic());
		orderInfo.setIsAmount(1);
		orderInfo.setLevel(order.getLevel());
		orderInfo.setStatusDesc(order.getStatusDesc());
		orderInfo.setUserAccount(order.getUserAccount());
		orderInfo.setUserName(order.getUserName());
		orderInfo.setUserIcon(order.getUserIcon());
		orderInfo.setGoodsId(order.getGoodsId());
		orderInfo.setSortTime(order.getSortTime());
		orderInfo.setOneself(order.getOneself());
		return orderInfo;
	}
	
	//da  通过users 添加交易记录
	public void saveOrUpdateRrcored(TkldPid tkldPid,Double money,String orderNumber){ 
		Users user = tkldPid.getUsers();
		if(user == null){
			return;
		}
		QueryModel query = new QueryModel();
		UsersMoneyRecord moneyRecord = new UsersMoneyRecord();
		
		query.clearQuery();
		query.combPreEquals("orderString", orderNumber);
		List<UsersMoneyRecord> recordL = dateBaseDAO.findLists(UsersMoneyRecord.class, query);
		
		if(recordL.size()>0 && recordL != null){
			moneyRecord = recordL.get(0);
			moneyRecord.setType(1);
		}else{
			moneyRecord.setIsValid(false);
			moneyRecord.setType(0);
		}
		moneyRecord.setIsValid(true);
		moneyRecord.setUsers(user);
		moneyRecord.setBeforeMoney((user.getMoney()==null)?0.0:user.getMoney());
		moneyRecord.setAfterMoney(CalcUtil.add(user.getMoney(), money));
		moneyRecord.setMoney(money);
		moneyRecord.setOrderString(orderNumber);
		moneyRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
		moneyRecord.setFromUsers(user);//从哪个用户获得
		moneyRecord.setRemark("购买产品获得分佣"+money+"元");
		
		usersMoneyRecordDao.saveOrUpdate(moneyRecord);
		
	}
	
	
	
	//保存订单
	public void saveOrUpdateOrder(Integer pid){
		CommissionRate commissionRate = commissionRateService.findCommissionRate();
		if(commissionRate==null){
			return; 
		}
		QueryModel model=new QueryModel();
		 String ids = findNextLevelPid(pid,new StringBuffer());
		 model.combInStr("id", ids);
		 List<TkldPid> list = (List<TkldPid>) dateBaseDAO.findList(TkldPid.class, model);
		 for (int i = 0; i < list.size(); i++) {
			 model.clearQuery();
			 model.combPreEquals("tkldPid.id",list.get(i).getId(),"pid");    
			 model.combEquals("isAmount",0);
			 List<TkThePidOrder> savePidOrderList = dateBaseDAO.findLists(TkThePidOrder.class, model);
			 model.clearQuery();
			 model.combPreEquals("tkldPid.id",list.get(i).getId(),"pid");
			 model.combEquals("isAmount",1);
			 List<TkThePidOrder> updatePidOrderList = dateBaseDAO.findLists(TkThePidOrder.class, model);
			 for (TkThePidOrder tkThePidOrder : savePidOrderList) {
			 	 distributeEarnings(tkThePidOrder,commissionRate);
			 }
			 for (TkThePidOrder tkThePidOrder : updatePidOrderList) {
				 	 updateOrderInfo(tkThePidOrder);
			 }
			
				 
		 }
	}
	
	
	//保存所有订单
		public void saveOrderAll(){
			CommissionRate commissionRate = commissionRateService.findCommissionRate();
			if(commissionRate==null){
				return; 
			}
			QueryModel model=new QueryModel();
			model.combEquals("isAmount", 0);
			List<TkThePidOrder> orderList = (List<TkThePidOrder>) dateBaseDAO.findList(TkThePidOrder.class, model);
			for (TkThePidOrder ordr : orderList) {
				model.clearQuery();
			 String ids = findNextLevelPid(ordr.getTkldPid().getId(),new StringBuffer());
			 model.combInStr("id", ids);
			 List<TkldPid> list = (List<TkldPid>) dateBaseDAO.findList(TkldPid.class, model);  //找到当前pid的所有下级
			 for (int i = 0; i < list.size(); i++) {
				 model.clearQuery();
				 model.combPreEquals("tkldPid.id",list.get(i).getId(),"pid");      
				 model.combEquals("isAmount",0);
				 List<TkThePidOrder> savePidOrderList = dateBaseDAO.findLists(TkThePidOrder.class, model);  
					 for (TkThePidOrder tkThePidOrder : savePidOrderList) {
					 	 distributeEarnings(tkThePidOrder,commissionRate);
					 }
			 	}
			}
		}
	
	 public String findNextLevelPid(Integer pid,StringBuffer sb){
			sb.append(",").append(pid);
			QueryModel model=new QueryModel();
			 model.combPreEquals("tkldPid.id", pid,"pid");
			 model.combEquals("isValid", 1);
			 List<TkldPid> list = (List<TkldPid>) dateBaseDAO.findList(TkldPid.class, model);
			 if(list.size()>0){
				 for (TkldPid tkldPid : list) {
					  findNextLevelPid(tkldPid.getId(),sb);
				}
			 }
				 return sb.substring(1,sb.length());
	}
	
	
		//更新orderInfo
		public void  updateOrderInfo(TkThePidOrder order){
			QueryModel model=new QueryModel();
			model.combEquals("orderNumber",order.getOrderNumber());
			List<TkThePidOrderInfo> orderInfoList = dateBaseDAO.findLists(TkThePidOrderInfo.class, model);
			if(orderInfoList.size()>0){
				for (TkThePidOrderInfo tkThePidOrderInfo : orderInfoList) {
					if(order.getOrderNumber().equals(tkThePidOrderInfo.getOrderNumber())&&!tkThePidOrderInfo.getOrderStatus().equals(order.getOrderStatus())){
							tkThePidOrderInfo.setOrderStatus(order.getOrderStatus());
							tkThePidOrderInfo.setStatusDesc(order.getStatusDesc());
							tkThePidOrderInfoDao.update(tkThePidOrderInfo);
					}
				}
			}
		}     
}
