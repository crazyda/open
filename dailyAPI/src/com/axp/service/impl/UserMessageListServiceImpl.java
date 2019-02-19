package com.axp.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.axp.dao.DateBaseDAO;
import com.axp.dao.IMessageTypeDao;
import com.axp.dao.IOrderMessageDao;
import com.axp.dao.ISystemMessageDao;
import com.axp.dao.IUserOrderMessageDao;
import com.axp.dao.IUserSystemMessageDao;
import com.axp.dao.IUsersDao;
import com.axp.domain.MessageType;
import com.axp.domain.OrderMessageList;
import com.axp.domain.ReGoodsorder;
import com.axp.domain.ReGoodsorderItem;
import com.axp.domain.SystemMessageList;
import com.axp.domain.UserOrderMessage;
import com.axp.domain.UserSystemMessage;
import com.axp.service.MessageListService;
import com.axp.service.UserMessageListService;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;

@Service
public class UserMessageListServiceImpl implements UserMessageListService{
	@Resource
	DateBaseDAO dateBaseDAO;
	@Resource
	IUsersDao usersDao;
	@Resource 
	ISystemMessageDao systemMessageDao;
	@Resource
	IOrderMessageDao orderMessageDao;
	@Resource
	IMessageTypeDao messageTypeDao;
	@Resource 
	IUserSystemMessageDao userSystemMessageDao;
	@Resource
	IUserOrderMessageDao userOrderMessageDao;
	@Override
	public Map<String, Object> getMessageList(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			String userId = parameter.getUserId();
			String typeId = parameter.getData().getString("typeId");
			if(StringUtils.isBlank(typeId)){
				return JsonResponseUtil.getJson(-0x02,"用户未登录！");
			}
			String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			MessageType messageType = messageTypeDao.findById(Integer.parseInt(typeId));
			List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
			QueryModel model =new QueryModel();
			model.combPreEquals("isValid", true);
				model.combEquals("parentId", Integer.parseInt(typeId));
				List<MessageType> typelist = dateBaseDAO.findLists(MessageType.class, model);
			if(messageType!=null&&messageType.getIsorder()==1){
				
 				if (typelist!=null&&typelist.size()>0) {
					for (MessageType type:typelist) {
						model.clearQuery();
						model.combPreEquals("isValid", true);
						model.combEquals("typeId", type.getId());
						model.combEquals("userId", userId);
						List<UserOrderMessage> uordlist = dateBaseDAO.findLists(UserOrderMessage.class, model);
						if (uordlist.size()>0) {
							for (UserOrderMessage userOrderMessage:uordlist) {
								model.clearQuery();
								model.combPreEquals("isValid", true);
								model.combEquals("id", userOrderMessage.getOrderMessageList().getId());
								List<OrderMessageList> ordlist = dateBaseDAO.findLists(OrderMessageList.class, model);
								Map<String, Object> temp = new HashMap<String, Object>();
								OrderMessageList oml = userOrderMessage.getOrderMessageList();
								ReGoodsorder reGoodsorder = oml.getReGoodsorder();
								
										model.clearQuery();
										model.combPreEquals("isValid", true);
										model.combPreEquals("order.id", reGoodsorder.getId(),"orderId");
										ReGoodsorderItem item=null;
										List<ReGoodsorderItem> itemlist=dateBaseDAO.findLists(ReGoodsorderItem.class, model);
										if (itemlist!=null&&itemlist.size()>0) {
											item=itemlist.get(0);
										}
										temp.put("time", sdf.format(oml.getTime()==null?"":oml.getTime()));
										temp.put("isread", userOrderMessage.getIsRead());
										temp.put("msgId", oml.getId()==null?"":oml.getId());
										temp.put("orderStatusId", reGoodsorder.getStatus()==null?"":reGoodsorder.getStatus());
										temp.put("orderFreight", reGoodsorder.getLogisticsCompay()==null?"":reGoodsorder.getLogisticsCompay());
										temp.put("orderIcon", item.getGoodImg()==null?"":basePath+item.getGoodImg());
										temp.put("orderDesc", item.getGoodName()==null?"":item.getGoodName());
										temp.put("orderMoney", reGoodsorder.getPayPrice()==null?"0.0":reGoodsorder.getPayPrice()+"");
										temp.put("orderUserName", reGoodsorder.getAccountName()==null?"":reGoodsorder.getAccountName());
										temp.put("orderNumber", reGoodsorder.getOrderCode()==null?"":reGoodsorder.getOrderCode());
										temp.put("orderFreightNumber", reGoodsorder.getLogisticsCode()==null?"":reGoodsorder.getLogisticsCode());
										if (reGoodsorder.getStatus()==ReGoodsorder.dai_shou_huo) {
											temp.put("orderStatus","订单已发货");
										}else if(reGoodsorder.getStatus()==ReGoodsorder.yi_wan_cheng){
											temp.put("orderStatus","订单已收货");
										}else if(reGoodsorder.getStatus()==ReGoodsorder.dai_fa_huo){
											temp.put("orderStatus","订单已付款");
										}else if(reGoodsorder.getStatus()==ReGoodsorder.zheng_zai_tui_dan){
											temp.put("orderStatus","订单申请退款");
										}
										temp.put("icon","");
										temp.put("title", "");
										temp.put("content", "");
										temp.put("typeId", typeId);
										list.add(temp);
									}
								}
					}
				}
 			}else if (messageType!=null&&messageType.getIsorder()==0){
				if (typelist!=null&&typelist.size()>0) {
					for (MessageType type:typelist) {
						model.clearQuery();
						model.combPreEquals("isValid", true);
						model.combEquals("typeId", type.getId());
						model.combEquals("userId", userId);
						List<UserSystemMessage> usyslist = dateBaseDAO.findLists(UserSystemMessage.class, model);
						if (usyslist.size()>0) {
							for (UserSystemMessage message:usyslist) {
								SystemMessageList sml = message.getSystemMessageList();
									Map<String, Object> temp = new HashMap<String, Object>();
									temp.put("icon", sml.getMessageType().getIcon()==null?"":sml.getMessageType().getIcon());
									temp.put("title", sml.getTitle()==null?"":sml.getTitle());
									temp.put("content", sml.getContent()==null?"":sml.getContent());
									temp.put("time", sdf.format(sml.getTime()==null?"":sml.getTime()));
									temp.put("isread", message.getIsRead());
									temp.put("msgId", sml.getId()==null?"":sml.getId());
									
									temp.put("orderStatusId", "");
									temp.put("orderStatus", "");
									temp.put("orderFreight", "");
									temp.put("orderIcon", "");
									temp.put("orderDesc", "");
									temp.put("orderMoney", "");
									temp.put("orderUserName", "");
									temp.put("orderNumber", "");
									temp.put("orderFreightNumber", "");
									temp.put("typeId", typeId);
									list.add(temp);
								
							}
						}
					}
				}
 			}
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("msgList", list);			
			statusMap.put("data", data);
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			statusMap.put("status", -2);
			statusMap.put("message", "请求失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}

	
	//删除消息
	@Override
	public Map<String, Object> delMessage(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			String typeId = parameter.getData().getString("typeId");
			if (StringUtils.isBlank(typeId)) {
				return JsonResponseUtil.getJson(-0x02,"用户未登录！");
			}
			//MessageType type = messageTypeDao.findById(Integer.parseInt(typeId));
			if ("1".equals(typeId)) {//系统消息
				statusMap = delSystemMessage(request, response);
			}else if("2".equals(typeId)){//订单消息
				statusMap = delOrderMessage(request, response);
			}else if("3".equals(typeId)){//资金消息
				statusMap = delSystemMessage(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}
	
	
	//删除系统和资金消息
	public Map<String,  Object> delSystemMessage(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			String msgId = parameter.getData().getString("msgId");
			String userId = parameter.getData().getString("userId");
			String typeId = parameter.getData().getString("typeId");
			QueryModel queryModel =new QueryModel();
			if ("-1".equals(msgId)) {
					queryModel.clearQuery();
					queryModel.combPreEquals("isValid", true);
					queryModel.combEquals("userId", userId);
					queryModel.combPreEquals("systemMessageList.messageType.messageType.id",Integer.parseInt(typeId),"typeId" );
					List<UserSystemMessage> syslist = dateBaseDAO.findLists(UserSystemMessage.class, queryModel);
					for (UserSystemMessage userSystemMessage : syslist) {
						userSystemMessage.setIsValid(false);
						userSystemMessageDao.update(userSystemMessage);
					}
			}else{
					queryModel.clearQuery();
					queryModel.combPreEquals("isValid", true);
					queryModel.combEquals("systemMessageList.id", Integer.parseInt(msgId));
					queryModel.combPreEquals("users.id", Integer.parseInt(userId),"userId");
					List<UserSystemMessage> syslist = dateBaseDAO.findLists(UserSystemMessage.class, queryModel);
					for (UserSystemMessage userSystemMessage : syslist) {
						userSystemMessage.setIsValid(false);
						userSystemMessageDao.update(userSystemMessage);
					}
			
			}
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功");
		} catch (Exception e) {
			e.printStackTrace();
			statusMap.put("status", -2);
			statusMap.put("message", "请求失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}
	
	//删除订单消息
	public Map<String, Object> delOrderMessage(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			String msgId = parameter.getData().getString("msgId");
			String userId = parameter.getData().getString("userId");
			String typeId = parameter.getData().getString("typeId");
			QueryModel queryModel =new QueryModel();
			if ("-1".equals(msgId)) {
					queryModel.clearQuery();
					queryModel.combPreEquals("isValid", true);
					queryModel.combEquals("userId", userId);
					queryModel.combPreEquals("orderMessageList.messageType.messageType.id",Integer.parseInt(typeId),"typeId" );
					List<UserOrderMessage> syslist = dateBaseDAO.findLists(UserOrderMessage.class, queryModel);
					for (UserOrderMessage userSystemMessage : syslist) {
						userSystemMessage.setIsValid(false);
						userOrderMessageDao.update(userSystemMessage);
					}
			}else{
					queryModel.clearQuery();
					queryModel.combPreEquals("isValid", true);
					queryModel.combEquals("orderMessageList.id", Integer.parseInt(msgId));
					queryModel.combPreEquals("users.id", Integer.parseInt(userId),"userId");
					List<UserOrderMessage> syslist = dateBaseDAO.findLists(UserOrderMessage.class, queryModel);
					for (UserOrderMessage userSystemMessage : syslist) {
						userSystemMessage.setIsValid(false);
						userOrderMessageDao.update(userSystemMessage);
					}
			
			}
			
			
			
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功");
		} catch (Exception e) {
			e.printStackTrace();
			statusMap.put("status", -2);
			statusMap.put("message", "请求失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}
	
}
