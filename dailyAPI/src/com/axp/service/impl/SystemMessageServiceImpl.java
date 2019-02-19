package com.axp.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.axp.domain.MessageType;
import com.axp.domain.UserOrderMessage;
import com.axp.domain.UserSystemMessage;
import com.axp.service.SystemMessageService;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;


@Service
public class SystemMessageServiceImpl extends BaseServiceImpl<MessageType> implements SystemMessageService{
	@Override
	public Map<String, Object> getMessageHome(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			String app = parameter.getApp();
			String adminuserId = parameter.getAdminuserId();
			String userId = parameter.getUserId();
			String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
			Map<String, Object> data = new HashMap<String, Object>();
			QueryModel queryModel=new QueryModel();
			queryModel.clearQuery();
			queryModel.combPreEquals("isValid", true);
			//获取level=1的所有分类
			List<MessageType> typeList = dateBaseDAO.findLists(MessageType.class, queryModel);
			List<MessageType> typeList1 = new ArrayList<MessageType>();
			List<MessageType> typeList2 = new ArrayList<MessageType>();
			for (MessageType messageType:typeList) {
				if (messageType.getLevel()==1) {
					typeList1.add(messageType);
				}else{
					typeList2.add(messageType);
				}
			}
			//========================ZL=========================//
			
			if (StringUtils.isNotBlank(app) && "SELLER".equals(app)) {
				for (MessageType type:typeList1) {
					Map<String,Object> temp =new HashMap<String, Object>();
					temp.put("icon", basePath+(type.getIcon().replaceAll("\\\\", "/")));
					temp.put("title", type.getTitle());
					temp.put("typeId", type.getId());
					
					if(StringUtils.isBlank(adminuserId)){
						temp.put("unread", "0");
						temp.put("content","暂无");
						temp.put("time", "");
					}else{
						if(type.getId()==1 || type.getId()==3){
						
						queryModel.clearQuery();
						queryModel.combPreEquals("isValid", true);
						queryModel.combPreEquals("adminUser.id", Integer.parseInt(adminuserId),"adminuserId");
						queryModel.combPreEquals("systemMessageList.messageType.messageType.id", type.getId(),"typeId");
						queryModel.setOrder(" id desc");
						List<UserSystemMessage> syslist= dateBaseDAO.findLists(UserSystemMessage.class, queryModel);
						if(syslist!=null &&syslist.size()>0){
							int unread = 0;
							String content ="暂无";
							Timestamp times =null;
							for(int i =0;i<syslist.size();i++){
								if(syslist.get(i).getIsRead()==0){
									unread+=1;
								}
								if(i==(syslist.size()-1)){
									content = syslist.get(i).getSystemMessageList().getContent();
									times = syslist.get(i).getSystemMessageList().getCreatetime();
								}
							}
							temp.put("unread", unread);
							temp.put("content",content);
							temp.put("time", sdf.format(times==null?"":times));
						}else{
							temp.put("unread", "0");
							temp.put("content","暂无");
							temp.put("time", "");
						}
						
					}else if(type.getId()==2){
						queryModel.clearQuery();
						queryModel.combPreEquals("isValid", true);
						queryModel.combPreEquals("adminUser.id", Integer.parseInt(adminuserId),"adminuserId");
						queryModel.combPreEquals("orderMessageList.messageType.messageType.id", type.getId(),"typeId");
						List<UserOrderMessage> syslist= dateBaseDAO.findLists(UserOrderMessage.class, queryModel);
						queryModel.setOrder(" id desc");
						if(syslist!=null &&syslist.size()>0){
							int unread = 0;
							String content ="暂无";
							Timestamp times =null;
							for(int i =0;i<syslist.size();i++){
								if(syslist.get(i).getIsRead()==0){
									unread+=1;
								}
								if(i==(syslist.size()-1)){
									content = syslist.get(i).getOrderMessageList().getContent();
									times = syslist.get(i).getOrderMessageList().getCreatetime();
								}
							}
							temp.put("unread", unread);
							temp.put("content",content);
							temp.put("time", sdf.format(times==null?"":times));
						}else{
							temp.put("unread", "0");
							temp.put("content","暂无");
							temp.put("time", "");
						}
					}else if(type.getId()==12){ //活动消息
						queryModel.clearQuery();
						queryModel.combPreEquals("isValid", true);
						queryModel.combPreEquals("adminUser.id", Integer.parseInt(adminuserId),"adminuserId");
						queryModel.combPreEquals("systemMessageList.messageType.messageType.id", type.getId(),"typeId");
						queryModel.setOrder(" id desc");
						List<UserSystemMessage> syslist= dateBaseDAO.findLists(UserSystemMessage.class, queryModel);
						if(syslist!=null &&syslist.size()>0){
							int unread = 0;
							String content ="暂无";
							Timestamp times =null;
							for(int i =0;i<syslist.size();i++){
								if(syslist.get(i).getIsRead()==0){
									unread+=1;
								}
								if(i==(syslist.size()-1)){
									content = syslist.get(i).getSystemMessageList().getContent();
									times = syslist.get(i).getSystemMessageList().getCreatetime();
								}
							}
							temp.put("unread", unread);
							temp.put("content",content);
							temp.put("time", sdf.format(times==null?"":times));
						}else{
							temp.put("unread", "0");
							temp.put("content","暂无");
							temp.put("time", "");
						}
						
					}
					
					
				}
					list.add(temp);
				}
			}else {
				for (MessageType type:typeList1) {
					Map<String,Object> temp =new HashMap<String, Object>();
					temp.put("icon", basePath+(type.getIcon().replaceAll("\\\\", "/")));
					temp.put("title", type.getTitle());
					temp.put("typeId", type.getId());
					
					if(StringUtils.isBlank(userId)){
						temp.put("unread", "0");
						temp.put("content","暂无");
						temp.put("time", "");
					}else{
					if(type.getId()==1 || type.getId()==3){
						
						queryModel.clearQuery();
						queryModel.combPreEquals("isValid", true);
						queryModel.combPreEquals("users.id", Integer.parseInt(userId),"userId");
						queryModel.combPreEquals("systemMessageList.messageType.messageType.id", type.getId(),"typeId");
						List<UserSystemMessage> syslist= dateBaseDAO.findLists(UserSystemMessage.class, queryModel);
						if(syslist!=null &&syslist.size()>0){
							int unread = 0;
							String content ="暂无";
							Timestamp times = null;
							for(int i =0;i<syslist.size();i++){
								if(syslist.get(i).getIsRead()==0){
									unread+=1;
								}
								if(i==(syslist.size()-1)){
									content = syslist.get(i).getSystemMessageList().getContent();
									times = syslist.get(i).getSystemMessageList().getCreatetime();
								}
							}
							temp.put("unread", unread);
							temp.put("content",content);
							temp.put("time", sdf.format(times==null?"":times));
						}else{
							temp.put("unread", "0");
							temp.put("content","暂无");
							temp.put("time", "");
						}
						
					}else if(type.getId()==2){
						queryModel.clearQuery();
						queryModel.combPreEquals("isValid", true);
						queryModel.combPreEquals("users.id", Integer.parseInt(userId),"userId");
						queryModel.combPreEquals("orderMessageList.messageType.messageType.id", type.getId(),"typeId");
						List<UserOrderMessage> syslist= dateBaseDAO.findLists(UserOrderMessage.class, queryModel);
						if(syslist!=null &&syslist.size()>0){
							int unread = 0;
							String content ="暂无";
							Timestamp times =null;
							for(int i =0;i<syslist.size();i++){
								if(syslist.get(i).getIsRead()==0){
									unread+=1;
								}
								if(i==(syslist.size()-1)){
									content = syslist.get(i).getOrderMessageList().getContent();
									times = syslist.get(i).getOrderMessageList().getCreatetime();
								}
							}
							temp.put("unread", unread);
							temp.put("content",content);
							temp.put("time", sdf.format(times==null?"":times));
						}else{
							temp.put("unread", "0");
							temp.put("content","暂无");
							temp.put("time", "");
						}
					}else if(type.getId()==12){
						queryModel.clearQuery();
						queryModel.combPreEquals("isValid", true);
						queryModel.combPreEquals("users.id", Integer.parseInt(userId),"userId");
						queryModel.combPreEquals("systemMessageList.messageType.messageType.id", type.getId(),"typeId");
						List<UserSystemMessage> syslist= dateBaseDAO.findLists(UserSystemMessage.class, queryModel);
						if(syslist!=null &&syslist.size()>0){
							int unread = 0;
							String content ="暂无";
							Timestamp times =null ;
							for(int i =0;i<syslist.size();i++){
								if(syslist.get(i).getIsRead()==0){
									unread+=1;
								}
								if(i==(syslist.size()-1)){
									content = syslist.get(i).getSystemMessageList().getContent();
									times = syslist.get(i).getSystemMessageList().getCreatetime();
								}
							}
							temp.put("unread", unread);
							temp.put("content",content);
							temp.put("time", sdf.format(times== null?"":times));
						}else{
							temp.put("unread", "0");
							temp.put("content","暂无");
							temp.put("time", "");
						}
					}
					
				}
					list.add(temp);
				}
			}
			
			data.put("msgTypes", list);
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

}
