package com.axp.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.axp.dao.AdminuserCashpointRecordDAO;
import com.axp.dao.ICashmoneyRecordDao;
import com.axp.domain.AbstractOrderComment.InnerOrderComment;
import com.axp.domain.AdminUser;
import com.axp.domain.CashmoneyRecord;
import com.axp.domain.OrderComment;
import com.axp.domain.ReBackOrder;
import com.axp.domain.ReGoodsorder;
import com.axp.domain.ReGoodsorderItem;
import com.axp.domain.Scorerecords;
import com.axp.domain.UserCoupons;
import com.axp.domain.Users;
import com.axp.service.IOrderService;
import com.axp.service.IReBackOrderService;
import com.axp.service.UserSystemMessageService;
import com.axp.util.CalcUtil;
import com.axp.util.DateUtil;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;
import com.axp.util.StringUtil;
import com.axp.util.UrlUtil;

@Service
public class ReBackOrderServiceImpl extends BaseServiceImpl<ReBackOrder> implements IReBackOrderService{
	
	@Resource
	UserSystemMessageService userSystemMessageService;
	@Autowired
	ICashmoneyRecordDao cashmoneyRecordDao;
	
	@Autowired
	IOrderService orderService;
	
	@Autowired
	AdminuserCashpointRecordDAO adminuserCashpointRecordDAO;
	@Override
	public Map<String, Object> applyBackOrder(HttpServletRequest request){
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		Integer userId = Integer.parseInt(parameter.getUserId());
		Users user = usersDao.findById(userId);
		JSONArray dataList = parameter.getData().getJSONArray("dataList");
		Map<String, Object> statusMap = new HashMap<String, Object>();
		QueryModel queryModel = new QueryModel();
		String show ="";
		for(int i=0;i<dataList.size();i++){
			JSONObject obj = dataList.getJSONObject(i);
			Integer orderItemId = obj.getInteger("orderItemId");//订单项Id	
			String reason = obj.getString("content");//退单原因	
			Integer drawbackWay = obj.getInteger("drawbackWay");//退款方式	0：原路返回;100:钱包;
			JSONArray images = obj.getJSONArray("images");
			ReGoodsorderItem item = reGoodsorderItemDao.findById(orderItemId);	
			ReGoodsorder rgo =item.getOrder();
			
			boolean isTeam=false;
			Integer itemStatus=ReGoodsorder.zheng_zai_tui_dan;
			Integer reBackOrderStatus=ReBackOrder.BACKSTATE_daishenhe;
			//如果该订单是待分享 那么就直接待支付
			if(rgo.getStatus()==ReGoodsorder.dai_pin_tuan){
				isTeam=true;
				itemStatus=ReGoodsorder.tong_yi_tui_dan;
				reBackOrderStatus=ReBackOrder.BACKSTATE_yishenhe;
				adminuserCashpointRecordDAO.updateMoneyById(item.getId());//删除分佣
			}
			
			
			if(item.getReGoodsOfSellerMall()!=null && item.getReGoodsOfSellerMall().getIsSendScore()!=null && item.getReGoodsOfSellerMall().getSendScoreNum()!=null){
				if(item.getReGoodsOfSellerMall().getIsSendScore() && item.getReGoodsOfSellerMall().getSendScoreNum()>0){
					Integer score = item.getReGoodsOfSellerMall().getSendScoreNum();
					if(user.getScore()<score){
						statusMap.put("status", -0x01);
						statusMap.put("message", "订单所增积分已使用，不可退单");
						return statusMap;
					}
					int uscore = (int) CalcUtil.sub(user.getScore(), score);
						user.setScore(uscore);
					    usersDao.saveOrUpdate(user);
					    Scorerecords scorerecords = new Scorerecords();
						scorerecords.setBeforeScore(user.getScore());
						scorerecords.setAfterScore(uscore);
						scorerecords.setIsvalid(true);
						scorerecords.setCreatetime(new Timestamp(System.currentTimeMillis()));
						scorerecords.setValidityTime(new Timestamp(DateUtil.addDay2Date(180, new Date()).getTime()));
						scorerecords.setScore(-score);
						scorerecords.setScoretype("商品"+item.getReGoodsOfSellerMall().getSnapshotGoods().getName()+"退单扣除对应赠送的"+score+"积分");
						scorerecords.setRemark("商品"+item.getReGoodsOfSellerMall().getSnapshotGoods().getName()+"退单扣除对应赠送的"+score+"积分");
						scorerecords.setType(17);
						scorerecords.setAdminuserId(47);
						scorerecords.setForeignId(userId);
						scorerecords.setUsers(user);
						scorerecordsDao.save(scorerecords);
				}
			}
			
			
			
			//不可退单的订单不会生成退单记录
			if(item.getIsBack()!=ReGoodsorder.bu_ke_tui_dan){
				JSONArray imagesList = new JSONArray();
				if(images!=null){ 
					for(int j=0;j<images.size();j++){
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("imgUrl", images.get(j));
						imagesList.add(jsonObject);
					}
				}
			
				reBackOrderDao.saveBackOrder(item, user, reason, drawbackWay, imagesList.toJSONString(),reBackOrderStatus);
				item.setIsBack(itemStatus);
				reGoodsorderItemDao.update(item);
				
				rgo.setPayPrice(rgo.getPayPrice()-item.getPayPrice());
				queryModel.clearQuery();
				queryModel.combPreEquals("order.id", rgo.getId(),"orderId");
				queryModel.combCondition("(isBack = "+ReGoodsorder.ke_tui_dan+" or isBack = "+ReGoodsorder.bu_ke_tui_dan+")");
				int count = dateBaseDAO.findCount(ReGoodsorderItem.class, queryModel);
				if(count==0){	
					rgo.setIsHasItems(false);
					rgo.setStatus(50);
				}
				reGoodsorderDao.update(rgo);
				
				queryModel.clearQuery();
				queryModel.combPreEquals("isValid", true);
				queryModel.combPreEquals("relateId", item.getId(), "relateId");
				queryModel.combPreEquals("usersByUserId.id", userId, "userId");
				List<CashmoneyRecord> recordList = dateBaseDAO.findLists(CashmoneyRecord.class, queryModel);
				if (recordList!=null&&recordList.size()>0) {
					if (recordList!=null&&recordList.size()>0) {
						for (CashmoneyRecord cashmoneyRecord:recordList) {
							cashmoneyRecord.setIsValid(false);
							cashmoneyRecordDao.update(cashmoneyRecord);
						}
					}
				}
				//============================ZL==================================//
				try{
				AdminUser au = item.getOrder().getSeller().getAdminUser();
				//Users  u = item.getOrder().getSeller().getUsers();
				if (au!=null&&!isTeam) {
					List<AdminUser> ulist = new ArrayList<AdminUser>();
					ulist.add(au);
					userSystemMessageService.saveMessageForAdmin("2","粉丝"+user.getRealname()+"为订单号："+item.getOrder().getOrderCode()+"的商品申请退单，请及时审核！", StringUtil.MESSAGE_DINGDAN, "用户申请退单！", ulist, item.getOrder().getId()+"",0d,1);
					
					//短信不可用了
					//UrlUtil uu = new UrlUtil();
					//uu.send(au.getUsername(), "您好，"+item.getUser().getRealname()+"的"+item.getGoodName()+"向您申请退单，订单号:"+item.getOrder().getOrderCode()+"，请确认！");
					
				}
				}catch(Exception e){
					e.printStackTrace();
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				}
				//===============================================================//
			}else{
				show="，但是"+item.getGoodName()+"不可退单！";
			}
		}
		
		statusMap.put("status", 0x01);
		statusMap.put("message", "提交申请成功"+show);
		return statusMap;
	}
	
	@Override
	public Map<String, Object> getBackOrder(HttpServletRequest request){
		String basePath = (String)request.getServletContext().getAttribute("RESOURCE_LOCAL_URL");
		String xcx = request.getParameter("xcx");
		Integer backOrderId = 0;
		if(xcx != null){
			backOrderId = Integer.valueOf(request.getParameter("backOrderId"));
		}else{
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			backOrderId = parameter.getData().getInteger("backOrderItemId");//退单Id	
		}
		
		ReBackOrder backOrder = reBackOrderDao.findById(backOrderId);
		ReGoodsorderItem item = backOrder.getOrderItem();//退单的订单商品详情
		UserCoupons coupons=null;
		
		backOrder.setBasePath(basePath);
		backOrder.getOrderItem().setBasePath(basePath);
		backOrder.getSeller().setBasePath(basePath);
		
		Map<String,Object> userMap = new HashMap<>();
		Map<String, Object> detailMap = null;
		Users user = backOrder.getOrderItem().getUser();
		userMap.put("userId",user.getId() );
		userMap.put("name", user.getName());
		userMap.put("phone", user.getPhone());
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("backOrderItemId",backOrder.getId());
		dataMap.put("backOrderItem",getItemMap(backOrder.getOrderItem(), basePath));
		dataMap.put("user", userMap);
		dataMap.put("backOrderStatus", backOrder.getBackOrderStatus());
		backOrder.getSeller().setLogo(basePath+backOrder.getSeller().getLogo());
		dataMap.put("seller", backOrder.getSeller());
		dataMap.put("backOrderInfo", backOrder);
		dataMap.put("backOrderVerify", backOrder.getBackOrderVerify());
		dataMap.put("drawbackDate", DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", backOrder.getAuditTime()));
		
		ReGoodsorder goodsorder = reGoodsorderDao.findById(backOrder.getOrderItem().getOrder().getId());
		dataMap.put("orderNumber", goodsorder.getOrderCode());
		dataMap.put("orderDate",DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", goodsorder.getCreateTime()));
		
		List<Map<String, Object>> detailList = new ArrayList<>();
		detailMap = new HashMap<>();
		detailMap.put("name", "优惠券抵扣");
		detailMap.put("price", goodsorder.getPayCashpoint());
		detailList.add(detailMap);
		detailMap = new HashMap<>();
		detailMap.put("name","返现");
		detailMap.put("price", goodsorder.getCashBack());
		detailList.add(detailMap);
		detailMap = new HashMap<>();
		detailMap.put("name", "运费");
		detailMap.put("price", goodsorder.getLogisticsType());
		detailList.add(detailMap);  
		dataMap.put("details", detailList);
		double realmoney =backOrder.getOrderItem().getPayPrice();	
		//如果退单商品有使用优惠券，退单金额要从相应商品价格减掉优惠券的价格
		/*if (item.getUserCoupons()!=null) {
			coupons = item.getUserCoupons();//商品对应优惠券
			realmoney = coupons.getTicketprice();//优惠券金额
			realmoney = CalcUtil.sub(backOrder.getOrderItem().getPayPrice(), realmoney);
			
		}else{
		}*/
		//realmoney= 
	
		//订单需要支付的现金
		dataMap.put("money", realmoney);
		//订单需要支付的积分
		dataMap.put("score", backOrder.getOrderItem().getPayScore());
		//订单需要支付的红包
		dataMap.put("cashpoint", 0);
		//订单实际支付金额
		dataMap.put("realityMoney", realmoney);
		
		Map<String,Object> orderStatusMap = new HashMap<>();
		orderStatusMap.put("name", goodsorder.getStatus());
		orderStatusMap.put("statusId",goodsorder.getStatus() );
		dataMap.put("status", orderStatusMap);

		dataMap.put("exchangeCode", goodsorder.getCode());//兑换码
		dataMap.put("username", goodsorder.getRealname());//收货人姓名
		dataMap.put("phone", goodsorder.getPhone());//收货人电话
		dataMap.put("address", goodsorder.getAddress());//收货人地址
		dataMap.put("expressName", goodsorder.getLogisticsCompay());//快递公司
		dataMap.put("expressNumber", goodsorder.getOrderCode());//快递单号
		
		
		
		Map<String, Object> statusMap = new HashMap<String, Object>();
		statusMap.put("status", 0x01);
		statusMap.put("message", "请求成功");
		statusMap.put("data", dataMap);
		return statusMap;
	}
	
	@Override
	public Map<String, Object> getBackOrderList(HttpServletRequest request){
		String basePath = (String)request.getServletContext().getAttribute("RESOURCE_LOCAL_URL");
		String xcx = request.getParameter("xcx");
		Integer userId = null;
		Integer pageIndex = 1;
		Integer wx_sellerId = null;
		if(xcx != null){
			userId = Integer.valueOf(request.getParameter("userId"));
			pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		}else{
			
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			userId = Integer.parseInt(parameter.getUserId());
			pageIndex = parameter.getData().getInteger("pageIndex");
			wx_sellerId=parameter.getData().getInteger("wx_sellerId");
		}
		QueryModel model = new QueryModel().setOrder("createTime desc");
		model.combPreEquals("user.id", userId, "userId");
		model.combCondition("orderItem.isBack != "+ReGoodsorder.bu_ke_tui_dan);
		model.combPreEquals("isValid", true);
		if(wx_sellerId!=null){
			model.combPreEquals("seller.id", wx_sellerId,"sellerId");
		}
		int	count = dateBaseDAO.findCount(ReBackOrder.class, model);
		int totalPage = (count % pageItemCount) > 0 ? ((count / pageItemCount) + 1)
				: (count / pageItemCount);
		int start = (pageIndex - 1) * pageItemCount;

		//查询结果；
		List<ReBackOrder> rgSellerMallList = dateBaseDAO.findPageList(ReBackOrder.class, model,start,pageItemCount);
		List<Map<String, Object>> dataMapList = new ArrayList<Map<String,Object>>();
		
		for(ReBackOrder backOrder:rgSellerMallList){		
			backOrder.setBasePath(basePath);
			backOrder.getOrderItem().setBasePath(basePath);
			backOrder.getSeller().setBasePath(basePath);
			
			Map<String, Object> dataMap = new HashMap<String, Object>();
			Map<String, Object> detailMap = null;
			dataMap.put("backOrderItemId",backOrder.getId());
			dataMap.put("backOrderItem", getItemMap(backOrder.getOrderItem(), basePath));
			dataMap.put("backOrderStatus", backOrder.getBackOrderStatus());
			backOrder.getSeller().setLogo(basePath+backOrder.getSeller().getLogo());
			dataMap.put("seller", backOrder.getSeller());
			dataMap.put("backOrderInfo", backOrder);
			dataMap.put("backOrderVerify", backOrder.getBackOrderVerify());
			dataMap.put("drawbackDate", DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", backOrder.getAuditTime()));
			
			ReGoodsorder goodsorder = reGoodsorderDao.findById(backOrder.getOrderItem().getOrder().getId());
			dataMap.put("orderNumber", goodsorder.getOrderCode());
			dataMap.put("orderDate",DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", goodsorder.getCreateTime()));
			
			List<Map<String, Object>> detailList = new ArrayList<>();
			detailMap = new HashMap<>();
			detailMap.put("name", "红包抵扣");
			detailMap.put("price", goodsorder.getPayCashpoint());
			detailList.add(detailMap);
			detailMap = new HashMap<>();
			detailMap.put("name","返现");
			detailMap.put("price", goodsorder.getCashBack());
			detailList.add(detailMap);
			detailMap = new HashMap<>();
			detailMap.put("name", "运费");
			detailMap.put("price", goodsorder.getLogisticsType());
			detailList.add(detailMap);  
			dataMap.put("details", detailList);
			
			//订单需要支付的现金
			dataMap.put("money", backOrder.getOrderItem().getPayPrice());
			//订单需要支付的积分
			dataMap.put("score", backOrder.getOrderItem().getPayScore());
			//订单需要支付的红包
			dataMap.put("cashpoint", backOrder.getOrderItem().getPayCashpoint());
			//订单实际支付金额
			dataMap.put("realityMoney", backOrder.getOrderItem().getPayPrice());
			
			
			Map<String,Object> orderStatusMap = new HashMap<>();
			orderStatusMap.put("name", goodsorder.getStatus());
			orderStatusMap.put("statusId",goodsorder.getStatus() );
			dataMap.put("status", orderStatusMap);

			dataMap.put("exchangeCode", goodsorder.getCode());//兑换码
			dataMap.put("username", goodsorder.getRealname());//收货人姓名
			dataMap.put("phone", goodsorder.getPhone());//收货人电话
			dataMap.put("address", goodsorder.getAddress());//收货人地址
			dataMap.put("expressName", goodsorder.getLogisticsCompay());//快递公司
			dataMap.put("expressNumber", goodsorder.getOrderCode());//快递单号
			
			dataMapList.add(dataMap);
		}
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("dataList", dataMapList);
		dataMap.put("pageSize", totalPage);
		dataMap.put("pageIndex", pageIndex);
		dataMap.put("pageItemCount", pageItemCount);
		
		Map<String, Object> statusMap = new HashMap<String, Object>();
		statusMap.put("status", 0x01);
		statusMap.put("message", "请求成功");
		statusMap.put("data", dataMap);
		
		return statusMap;
		
	}
	
	//返回item信息
		private Map<String,Object> getItemMap(ReGoodsorderItem item,String basePath){
			item.setBasePath(basePath);
			Map<String,Object> itemMap = new HashMap<>();
			itemMap.put("orderItemId",item.getId());//订单项id
			itemMap.put("number",item.getGoodQuantity());//数量
			itemMap.put("specString",item.getStyle());//前台用于显示的规格信息
			itemMap.put("money",item.getPayPrice());//订单需要支付的现金
			itemMap.put("score",item.getPayScore());//订单需要支付的积分
			itemMap.put("cashpoint",item.getPayCashpoint());//订单需要支付的红包
			itemMap.put("isBack",item.getIsBack()==ReGoodsorder.bu_ke_tui_dan?false:true);//是否可退单
			
			Map<String,Object> goodsMap = new HashMap<>();
			goodsMap.put("goodsId", item.getGoodsId());
			goodsMap.put("mallType", item.getMallClass());
			goodsMap.put("name", item.getGoodName());
			goodsMap.put("price", item.getGoodPrice());
			goodsMap.put("score", item.getGoodScore());
			goodsMap.put("cashpoint", item.getGoodCashpoint());
			goodsMap.put("costPrice",item.getCar().getDisplayPrice());
			goodsMap.put("coverPic", item.getImgUrl());
			goodsMap.put("expressTactics", ReGoodsorder.getlogisticsName(item.getLogisticsType()));
			goodsMap.put("mallType",item.getMallClass());
			itemMap.put("goods", goodsMap);//商品信息
			
			QueryModel queryModel = new QueryModel();
			queryModel.clearQuery();
			queryModel.combPreEquals("reGoodsorderItem.id",item.getId(),"itemId");
			OrderComment orderComment = (OrderComment) dateBaseDAO.findOne(OrderComment.class, queryModel);
			if(orderComment != null) {
				InnerOrderComment comment = orderComment.new InnerOrderComment();
				comment.setCommentImages(basePath);
				itemMap.put("userComment",comment  );
			}
			
			return itemMap;
		}
	
}