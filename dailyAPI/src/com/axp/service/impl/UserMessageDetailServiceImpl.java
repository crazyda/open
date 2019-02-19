package com.axp.service.impl;

import java.sql.Timestamp;
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
import com.axp.domain.MessageType;
import com.axp.domain.OrderMessageList;
import com.axp.domain.ReBackOrder;
import com.axp.domain.ReGoodsorder;
import com.axp.domain.ReGoodsorderItem;
import com.axp.domain.SystemMessageList;
import com.axp.domain.UserOrderMessage;
import com.axp.domain.UserSystemMessage;
import com.axp.service.UserMessageDetailService;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;

@Service
public class UserMessageDetailServiceImpl implements UserMessageDetailService{
	
	@Resource
	ISystemMessageDao systemMessageDao;
	@Resource
	IOrderMessageDao orderMessageDao;
	@Resource
	DateBaseDAO dateBaseDAO;
	@Resource
	IMessageTypeDao messageTypeDao;
	@Resource 
	IUserSystemMessageDao userSystemMessageDao;
	@Resource
	IUserOrderMessageDao userOrderMessageDao;
	@Override
	public Map<String, Object> msgDetail(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			String typeId =  parameter.getData().getString("typeId");
            
			//MessageType type = messageTypeDao.findById(Integer.parseInt(typeId));
			if ("1".equals(typeId)) {//系统消息详情				
				statusMap =	msgDetailSystem(request,response);
			}else if("3".equals(typeId)){//资金消息详情
				statusMap = msgDetailAssets(request,response);
			}else if("2".equals(typeId)){//订单消息详情
				statusMap = msgDetailOrder(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}

	//系统消息详情
	public Map<String, Object> msgDetailSystem(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			String msgId =  parameter.getData().getString("msgId");
			String usersId = parameter.getUserId();
			if(StringUtils.isBlank(usersId)){
				return JsonResponseUtil.getJson(-0x02,"用户信息错误");
			}
			
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Map<String,Object> temp =new HashMap<String, Object>();
			
			SystemMessageList systemMessageList = systemMessageDao.findById(Integer.parseInt(msgId));
			
			
			QueryModel queryModel = new QueryModel();
				
				queryModel.clearQuery();
				queryModel.combPreEquals("isValid", true);
				queryModel.combEquals("systemMessageList.id", systemMessageList.getId());
				queryModel.combPreEquals("users.id", Integer.parseInt(usersId),"userId");
				
				List<UserSystemMessage> syslist = dateBaseDAO.findLists(UserSystemMessage.class, queryModel);
				if (syslist!=null&&syslist.size()>0) {
					UserSystemMessage systemMessage=syslist.get(0);
					systemMessage.setIsRead(1);
					systemMessage.setReadtime(new Timestamp(System.currentTimeMillis()));
					userSystemMessageDao.update(systemMessage);
				}
				queryModel.clearQuery();
				queryModel.combPreEquals("isValid", true);
				queryModel.combEquals("isread", 0);
				queryModel.combPreEquals("users.id", Integer.parseInt(usersId),"userId");
				int count = dateBaseDAO.findCount(UserSystemMessage.class, queryModel);
				temp.put("title", systemMessageList.getTitle()==null?"":systemMessageList.getTitle());
				temp.put("content",systemMessageList.getContent()==null?"":systemMessageList.getContent());
				temp.put("time", sdf.format(systemMessageList.getTime()==null?"":systemMessageList.getTime()));
				temp.put("sign", "每天积分");
				temp.put("unread", count);
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("msgDetail", temp);
			statusMap.put("data", data);
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			statusMap.put("status", -2);
			statusMap.put("message", "请求失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}
	
	//资金消息详情
	public Map<String, Object> msgDetailAssets(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			String typeId =  parameter.getData().getString("typeId");
			String msgId =  parameter.getData().getString("msgId");
			String usersId = parameter.getUserId();
			if(StringUtils.isBlank(usersId)){
				return JsonResponseUtil.getJson(-0x02,"用户数据错误");
			}
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm");
			//List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
			List<Object> list =new ArrayList<>();
			Map<String,Object> temp =new HashMap<String, Object>();
			QueryModel queryModel =new QueryModel();
			
			MessageType messageType = null;
			
			SystemMessageList systemMessageList = systemMessageDao.findById(Integer.parseInt(msgId));
			messageType = systemMessageList.getMessageType();
				
				queryModel.clearQuery();
				queryModel.combPreEquals("isValid", true);
				queryModel.combEquals("systemMessageList.id", systemMessageList.getId());
				queryModel.combPreEquals("users.id", Integer.parseInt(usersId),"userId");
				UserSystemMessage systemMessage =null;
				List<UserSystemMessage> syslist = dateBaseDAO.findLists(UserSystemMessage.class, queryModel);
				if (syslist!=null&&syslist.size()>0) {
					systemMessage=syslist.get(0);
					systemMessage.setIsRead(1);
					systemMessage.setReadtime(new Timestamp(System.currentTimeMillis()));
					userSystemMessageDao.update(systemMessage);
				}
				queryModel.clearQuery();
				queryModel.combPreEquals("isValid", true);
				queryModel.combEquals("isread", 0);
				int count = dateBaseDAO.findCount(UserSystemMessage.class, queryModel);
				temp.put("nav_title", messageType.getTitle()==null?"":messageType.getTitle());
				
				temp.put("title", systemMessageList.getTitle()==null?"":systemMessageList.getTitle());
				temp.put("content",systemMessageList.getContent()==null?"":systemMessageList.getContent());
				temp.put("time", sdf.format(systemMessageList.getTime()==null?"":systemMessageList.getTime()));
				temp.put("userName", systemMessageList.getUsers().getLoginname()==null?"":systemMessageList.getUsers().getLoginname());
				temp.put("money", systemMessageList.getMoney()==null?"0.0":systemMessageList.getMoney()+"");
				temp.put("moneySucceed", "");
				temp.put("goodsName", "");
				temp.put("tips", "如需帮助，请联系总部客服:400-001-001");
				temp.put("unread", String.valueOf(count));
			
			//list.add(temp);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("msgDetail", temp);
			statusMap.put("data", data);			
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			statusMap.put("status", -2);
			statusMap.put("message", "请求失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}
	
	//订单消息详情
	public Map<String, Object> msgDetailOrder(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			String msgId =  parameter.getData().getString("msgId");
			
			String usersId = parameter.getUserId();
			if(StringUtils.isBlank(usersId)){
				return JsonResponseUtil.getJson(-0x02,"用户数据错误");
			}
			
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm");
			List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
			Map<String,Object> goodlist =new HashMap<String, Object>();
			Map<String, Object> data = new HashMap<String, Object>();
			QueryModel queryModel =new QueryModel();
			OrderMessageList orderMessageList = orderMessageDao.findById(Integer.parseInt(msgId));
			UserOrderMessage userOrderMessage =null;
			ReGoodsorder reGoodsorder=null;
			ReGoodsorderItem item=null;
			ReBackOrder reBackOrder =null;
			queryModel.clearQuery();
			queryModel.combPreEquals("isValid", true);
			queryModel.combEquals("orderMessageList.id", orderMessageList.getId());
			queryModel.combPreEquals("users.id", Integer.parseInt(usersId),"userId");
			List<UserOrderMessage> ordlist = dateBaseDAO.findLists(UserOrderMessage.class, queryModel);
			if (ordlist!=null&&ordlist.size()>0) {
				userOrderMessage=ordlist.get(0);
				userOrderMessage.setIsRead(1);
				userOrderMessageDao.update(userOrderMessage);
			}
			queryModel.clearQuery();
			queryModel.combPreEquals("isValid", true);
			queryModel.combEquals("isread", 0);
			queryModel.combPreEquals("users.id", Integer.parseInt(usersId),"userId");
			int count = dateBaseDAO.findCount(UserOrderMessage.class, queryModel);
			
			
		
			
				
				reGoodsorder=orderMessageList.getReGoodsorder();
				queryModel.clearQuery();
				queryModel.combPreEquals("isValid", true);
				queryModel.combPreEquals("order.id", reGoodsorder.getId(),"orderId");
				List<ReGoodsorderItem> itemlist=dateBaseDAO.findLists(ReGoodsorderItem.class, queryModel);
				if (itemlist!=null&&itemlist.size()>0) {
					item=itemlist.get(0);
					Map<String,Object> goods =new HashMap<String, Object>();
					goods.put("icon", item.getGoodImg()==null?"":item.getGoodImg());
					goods.put("name", item.getGoodName()==null?"":item.getGoodName());
					goods.put("price", item.getPayPrice()==null?"0.0":item.getPayPrice()+"");
					goods.put("count", item.getGoodQuantity()==null?"":item.getGoodQuantity()+"");
					goods.put("freightPrice", item.getLogisticsPrice()==null?"0.0":item.getLogisticsPrice()+"");
					goods.put("goodsId", item.getMallClass()+item.getGoodsId());
					list.add(goods);
				}
				
			
			goodlist.put("orderNumber", reGoodsorder.getOrderCode()==null?"":reGoodsorder.getOrderCode());
			if (reGoodsorder.getStatus()==ReGoodsorder.dai_shou_huo) {
				goodlist.put("orderStatus","订单已发货");
			}
			goodlist.put("orderStatusId", reGoodsorder.getStatus()==null?"":reGoodsorder.getStatus()+"");
			goodlist.put("orderFreightNumber", reGoodsorder.getLogisticsCode()==null?"":reGoodsorder.getLogisticsCode());
			if (reGoodsorder.getStatus()==10) {
				queryModel.clearQuery();
				queryModel.combPreEquals("isValid", true);
				queryModel.combEquals("goodid", reGoodsorder.getId());
				List<ReBackOrder> backlist = dateBaseDAO.findLists(ReBackOrder.class, queryModel);
				if (backlist.size()>0) {
					reBackOrder = backlist.get(0);
				}
				goodlist.put("orderId", "");
				goodlist.put("backOrderId", reBackOrder.getId()==null?"":reBackOrder.getId()+"");
			}
			goodlist.put("orderId", reGoodsorder.getId()==null?"":reGoodsorder.getId()+"");
			goodlist.put("backOrderId", "");
			List<Object> listc =new ArrayList<>();
			listc.add( orderMessageList.getContent()==null?"":orderMessageList.getContent());
			goodlist.put("content",listc);
			goodlist.put("time", sdf.format(orderMessageList.getTime()==null?"":orderMessageList.getTime()));
			goodlist.put("unread", String.valueOf(count));
 		    goodlist.put("goods", list);
			data.put("msgDetail", goodlist);
			statusMap.put("data", data);
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			statusMap.put("status", -2);
			statusMap.put("message", "请求失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}


	
}
