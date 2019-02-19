package com.axp.service.impl;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;


import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.axp.dao.DateBaseDAO;
import com.axp.dao.IMessageTypeDao;
import com.axp.dao.IUserOrderMessageDao;
import com.axp.dao.IUserSystemMessageDao;
import com.axp.dao.OrderMessageListDao;
import com.axp.dao.ReGoodsorderDao;
import com.axp.dao.SystemMessageListDao;
import com.axp.dao.UsersDAO;
import com.axp.domain.AdminUser;
import com.axp.domain.MessageType;
import com.axp.domain.OrderMessageList;
import com.axp.domain.ReGoodsorder;
import com.axp.domain.SystemMessageList;
import com.axp.domain.UserOrderMessage;
import com.axp.domain.UserSystemMessage;
import com.axp.domain.Users;
import com.axp.service.UserSystemMessageService;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;
import com.axp.util.StringUtil;
import com.axp.util.UrlUtil;
import com.axp.util.push.ImpAppInformation;


@Service
public class UserSystemMessageServiceImpl implements UserSystemMessageService {

	@Resource
	SystemMessageListDao systemMessageListDao;
	@Resource
	OrderMessageListDao orderMessageListDao;
	@Resource
	IUserOrderMessageDao userOrderMessageDao;
	@Resource 
	IUserSystemMessageDao userSystemMessageDao;
	@Resource
	UsersDAO usersDAO;
	@Resource
	IMessageTypeDao messageTypeDao;
	@Resource
	ReGoodsorderDao  regoodsorderDAO;
	@Resource
	DateBaseDAO dateBaseDAO;
	@Override
	public Map<String, Object> saveallmessage(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			String typeId =  parameter.getData().getString("typeId");//当前消息对应的子类Id;
	    
	 MessageType messageType = messageTypeDao.findById(Integer.parseInt(typeId));	
			
        if(3==messageType.getMessageType().getId()){//资金消息
			statusMap =saveAssetsmessage(request,response);
		}else if(2==messageType.getMessageType().getId()){//订单消息
			statusMap =saveordmessage(request,response);
		}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			
		}
		return statusMap;
	}

	@Override
	public Map<String, Object> savesysmessage(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
	    String typeId =  parameter.getData().getString("typeId");	    
	    String content= parameter.getData().getString("content");
	    String title  = parameter.getData().getString("title");
		MessageType messageType=new MessageType();
		messageType.setId(Integer.parseInt(typeId));
		SystemMessageList sml = new SystemMessageList();
		
        sml.setMessageType(messageType);
        sml.setContent(content);
        sml.setIsValid(true);
		sml.setTime(new java.sql.Timestamp(System.currentTimeMillis()));
		sml.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
		sml.setTitle(title);
		systemMessageListDao.save(sml);

		
		List<Users> ulist = usersDAO.findAllUsers();
		
		for (Users user :ulist) {	
	            	UserSystemMessage usm=new UserSystemMessage();
		            usm.setUsers(user);
		            usm.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
					usm.setIsValid(true);
					usm.setSystemMessageList(sml);
					usm.setIsRead(0);			
					userSystemMessageDao.save(usm);
		}
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
	@Override
	public Map<String, Object> saveordmessage(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
		    String typeId  = parameter.getData().getString("typeId");	    
		    String content = parameter.getData().getString("content");
		    String title   = parameter.getData().getString("title");
		    String userId  = parameter.getData().getString("userId");
		    String orderId = parameter.getData().getString("orderId");
			MessageType messageType=new MessageType();
			messageType =messageTypeDao.findById(Integer.parseInt(typeId));	
			OrderMessageList orm=new OrderMessageList();   
			UserOrderMessage uom=new UserOrderMessage();
			orm.setMessageType(messageType);		
			orm.setContent(content);
			orm.setTime(new java.sql.Timestamp(System.currentTimeMillis()));
			orm.setIsValid(true);
			orm.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
			orm.setTitle(title);
			
		    Users user = usersDAO.findById(Integer.parseInt(userId));			
			orm.setUsers(user);
			
			ReGoodsorder regoodsorder =regoodsorderDAO.findById(Integer.parseInt(orderId));					
			orm.setReGoodsorder(regoodsorder);		
			orderMessageListDao.save(orm);
			
			uom.setUsers(user);
			uom.setIsRead(0);
			uom.setIsValid(true);
			uom.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
			uom.setOrderMessageList(orm);
			uom.setMessageType(messageType);
			userOrderMessageDao.save(uom);
			
			push(orm.getContent(),orm.getTitle(),user.getUserid(),request);
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
	@Override
	public Map<String, Object> saveAssetsmessage(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
		    String typeId  = parameter.getData().getString("typeId");	    
		    String content = parameter.getData().getString("content");
		    String title   = parameter.getData().getString("title");
		    String userId  = parameter.getData().getString("userId");
			MessageType messageType=new MessageType();
			messageType =messageTypeDao.findById(Integer.parseInt(typeId));	
			SystemMessageList sml = new SystemMessageList();
			UserSystemMessage usm=new UserSystemMessage();
	        sml.setMessageType(messageType);
	        sml.setContent(content);
	        sml.setIsValid(true);
			sml.setTime(new java.sql.Timestamp(System.currentTimeMillis()));
			sml.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
			sml.setTitle(title);
			systemMessageListDao.save(sml);
			Users user = usersDAO.findById(Integer.parseInt(userId));
			usm.setUsers(user);	        
			usm.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
			usm.setIsValid(true);
			usm.setSystemMessageList(sml);
			usm.setIsRead(0);	
			usm.setMessageType(messageType);
			userSystemMessageDao.save(usm);	
			//push(sml.getContent(),sml.getTitle(),user.getUserid(),request);
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
	
	
	@Override
	public void push(String content,String title,String cid,HttpServletRequest request) {
			try {
				List<Users> user = usersDAO.findInIds("id", cid);
				List<String> cidIOS  =new ArrayList<String>();
				for (int i = 0; i < user.size(); i++) {
					cidIOS.add(user.get(i).getUserid());
				}
				ImpAppInformation push = new ImpAppInformation();
				push.pushSystemMessageToListAPP(title, content, cidIOS);
				push.pushSystemMessageCenterToApp(title, content);
				System.out.println("推送成功");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("推送失败");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			
	}

	public static void push_message(String content,String title,String cid) {
	    
		try {
			List<String> cidIOS = new ArrayList<String>();
			cidIOS.add(cid);
			ImpAppInformation push = new ImpAppInformation();
			push.pushSystemMessageToListAPP(title, content, cidIOS);
			push.pushSystemMessageCenterToApp(title, content);
			System.out.println("推送成功");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("推送失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
}

	
	@Override
	public Map<String, Object> Test(HttpServletRequest request,
			HttpServletResponse response) {
		 push("测试内容","测试标题","46100",request);
		 Map<String, Object> test =new HashMap<String, Object>();
		return test;
		// TODO Auto-generated method stub
	}

	
	@Override
	public Map<String, Object> saveMessage(String userType,String content, Integer type,
			String title, List<Users> ulist,String orderId,double money,Integer state) {
			Map<String,  Object> statusMap = new HashMap<String, Object>();
			MessageType messageType=new MessageType();
			for(Users user:ulist){
				messageType =messageTypeDao.findById(type);	
				if (messageType.getIsorder()==0) {
					SystemMessageList sml = new SystemMessageList();
					UserSystemMessage usm=new UserSystemMessage();
			        sml.setMessageType(messageType);
			        sml.setContent(content);
			        sml.setIsValid(true);
					sml.setTime(new java.sql.Timestamp(System.currentTimeMillis()));
					sml.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
					sml.setTitle(title);
					sml.setMoney(money);
					sml.setMoneyState(state==null?1:state);
					systemMessageListDao.save(sml);
					usm.setUsers(user);	        
					usm.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
					usm.setIsValid(true);
					usm.setSystemMessageList(sml);
					usm.setIsRead(0);	
					usm.setMessageType(messageType);
					userSystemMessageDao.save(usm);	
					
					SendMessage gd=new SendMessage(userType,user.getId()+"", user.getUserid(), title, content,sml.getId()+"",messageType.getMessageType().getId()+"","1","用户消息",user.getDevicetoken()); 
		            gd.start();
					
					//ImpAppInformation push = new ImpAppInformation();
					//push.pushMessageForUsers(user.getId()+"", user.getUserid(), title, content,sml.getId()+"",messageType.getMessageType().getId()+"","1","用户消息",user.getDevicetoken());
				}else if (messageType.getIsorder()==1){
					OrderMessageList oml = new OrderMessageList();
					UserOrderMessage uom = new UserOrderMessage();
					ReGoodsorder rgs = regoodsorderDAO.findById(Integer.parseInt(orderId));
					oml.setReGoodsorder(rgs);
					oml.setMessageType(messageType);
					oml.setContent(content);
					oml.setIsValid(true);
					oml.setTime(new java.sql.Timestamp(System.currentTimeMillis()));
					oml.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
					oml.setTitle(title);
					orderMessageListDao.save(oml);
					uom.setUsers(user);
					uom.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
					uom.setIsValid(true);
					uom.setIsRead(0);
					uom.setMessageType(messageType);
					uom.setOrderMessageList(oml);
					userOrderMessageDao.save(uom);
					SendMessage gd=new SendMessage(userType,user.getId()+"", user.getUserid(), title, content,oml.getId()+"",messageType.getMessageType().getId()+"","1","用户消息",user.getDevicetoken()); 
		            gd.start();
					
				}
			}
	
		return statusMap;
	}
	
	@Override
	public Map<String, Object> saveMessageForAdmin(String userType,String content, Integer type,
			String title, List<AdminUser> ulist,String orderId,double money,Integer state) {
			Map<String,  Object> statusMap = new HashMap<String, Object>();
			MessageType messageType=new MessageType();
			int random = (int) ((Math.random() * 9 + 1) * 100000);
		
			
			for(AdminUser user:ulist){
				messageType =messageTypeDao.findById(type);	
				if (messageType.getIsorder()==0) {
					SystemMessageList sml = new SystemMessageList();
					UserSystemMessage usm=new UserSystemMessage();
			        sml.setMessageType(messageType);
			        sml.setContent(content);
			        sml.setIsValid(true);
					sml.setTime(new java.sql.Timestamp(System.currentTimeMillis()));
					sml.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
					sml.setTitle(title);
					sml.setMoney(money);
					sml.setMoneyState(state==null?1:state);
					systemMessageListDao.save(sml);
					usm.setAdminUser(user);        
					usm.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
					usm.setIsValid(true);
					usm.setSystemMessageList(sml);
					usm.setIsRead(0);	
					usm.setMessageType(messageType);
					userSystemMessageDao.save(usm);	
					
					SendMessage gd=new SendMessage(userType,user.getId()+"", user.getChannelid(), title, content,sml.getId()+"",messageType.getMessageType().getId()+"","1","商家消息",user.getDevicetoken()); 
		            gd.start();
					
					//ImpAppInformation push = new ImpAppInformation();
					//push.pushMessageForAdmin(user.getId()+"", user.getChannelid(), title, content,sml.getId()+"",messageType.getMessageType().getId()+"","1","商家消息",user.getDevicetoken());
				}else if (messageType.getIsorder()==1){
					OrderMessageList oml = new OrderMessageList();
					UserOrderMessage uom = new UserOrderMessage();
					ReGoodsorder rgs = regoodsorderDAO.findById(Integer.parseInt(orderId));
					if(rgs!=null){
						oml.setReGoodsorder(rgs);
						oml.setMessageType(messageType);
						oml.setContent(content);
						oml.setIsValid(true);
						oml.setTime(new java.sql.Timestamp(System.currentTimeMillis()));
						oml.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
						oml.setTitle(title);
						orderMessageListDao.save(oml);
						uom.setAdminUser(user);
						uom.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
						uom.setIsValid(true);
						uom.setIsRead(0);
						uom.setMessageType(messageType);
						uom.setOrderMessageList(oml);
						userOrderMessageDao.save(uom);
						
						SendMessage gd=new SendMessage(userType,user.getId()+"", user.getChannelid(), title, content,oml.getId()+"",messageType.getMessageType().getId()+"","1","商家消息",user.getDevicetoken()); 
			            gd.start();
						
						//ImpAppInformation push = new ImpAppInformation();
						//push.pushMessageForAdmin(user.getId()+"", user.getChannelid(), title, content,oml.getId()+"",messageType.getMessageType().getId()+"","1","商家消息",user.getDevicetoken());
					}
				}
				
					UrlUtil uu = new UrlUtil();
					/*if(!user.getId().toString().equals(user.getName())){
						//uu.sendM(user.getName(), send);
					}*/
			}
	
		return statusMap;
	}


	@Override
	public Map<String, Object> saveMessage(String userType,String content, Integer type,
			String title, List<Users> ulist, double money) {
		MessageType messageType=new MessageType();
		for(Users user:ulist){
		SystemMessageList sml = new SystemMessageList();
		UserSystemMessage usm=new UserSystemMessage();
        sml.setMessageType(messageType);
        sml.setContent(content);
        sml.setIsValid(true);
		sml.setTime(new java.sql.Timestamp(System.currentTimeMillis()));
		sml.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
		sml.setTitle(title);
		sml.setMoney(money);
		sml.setMoneyState(1);
		systemMessageListDao.save(sml);
		usm.setUsers(user);	        
		usm.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
		usm.setIsValid(true);
		usm.setSystemMessageList(sml);
		usm.setIsRead(0);	
		usm.setMessageType(messageType);
		userSystemMessageDao.save(usm);	
		
		SendMessage gd=new SendMessage(userType,user.getId()+"", user.getUserid(), title, content,sml.getId()+"",messageType.getMessageType().getId()+"","1","商家消息",user.getDevicetoken()); 
        gd.start();
		
		
		//ImpAppInformation push = new ImpAppInformation();
		//push.pushMessage(user.getId()+"", user.getUserid(), title, content,sml.getId()+"",type+"","1","商家消息",user.getDevicetoken());
		}
		return null;
	}

	
	//=========================合伙人消息======ZL===========================//
	@Override
	public Map<String, Object> saveMessage(String title,String content) {
		List<Integer> ulist = new ArrayList<Integer>();
		ulist.add(1104);//目前是自己是遍历自己的账号
		try {
			MessageType mt = messageTypeDao.findById(4);
			SystemMessageList messageList = new SystemMessageList();
			messageList.setIsValid(true);
			messageList.setTitle(title);
			messageList.setContent(content);
			messageList.setMessageType(mt);
			messageList.setTime(new java.sql.Timestamp(System.currentTimeMillis()));
			messageList.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
			systemMessageListDao.save(messageList);
			for (Integer user : ulist) {
				Users users = usersDAO.findById(user);
				if (users!=null) {
					UserSystemMessage systemMessage = new UserSystemMessage();
					systemMessage.setMessageType(mt);
					systemMessage.setUsers(users);
					systemMessage.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
					systemMessage.setIsRead(0);
					systemMessage.setSystemMessageList(messageList);
					systemMessage.setIsValid(true);
					userSystemMessageDao.save(systemMessage);
					
				}
			}
		} catch (Exception e) {
			System.out.println("访问错误！");
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return null;
	}
	
//------------da
	
	@Override
	public Map<String, Object> sendMessage(String userType, String content,
			Integer type, String title, Users user, AdminUser adminUser,
			String orderId, double money, Integer state) {
		// TODO Auto-generated method stub
		
		MessageType messageType=new MessageType();
		SystemMessageList sml = new SystemMessageList();
		UserSystemMessage usm=new UserSystemMessage();
        sml.setMessageType(messageType);
        sml.setContent(content);
        sml.setIsValid(true);
		sml.setTime(new java.sql.Timestamp(System.currentTimeMillis()));
		sml.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
		sml.setTitle(title);
		sml.setMoney(money);
		sml.setMoneyState(1);
		systemMessageListDao.save(sml);
		usm.setUsers(user);	        
		usm.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
		usm.setIsValid(true);
		usm.setSystemMessageList(sml);
		usm.setIsRead(0);	
		usm.setMessageType(messageType);
		userSystemMessageDao.save(usm);	
		SendMessage gd=new SendMessage(userType,user.getId()+"", user.getUserid(), title, content,sml.getId()+"",messageType.getMessageType().getId()+"","1","商家消息",user.getDevicetoken()); 
        gd.start();
		
		
		
		
		
		
		
		
		return null;
	}
}


class SendMessage extends Thread{  
    private String userId; 
    private String cid;
    private String title;
    private String content;
    private String mId;
    private String str1;
    private String str2;
    private String token;
    private String typeId;
    private String userType; //1 用户 2 商家
    
    
    public SendMessage(String userType,String userId,String cid,String title,String content,String mId,String typeId,String str1,String str2,String token) {  
       this.userId=userId; 
       this.cid = cid;
       this.title=title;
       this.content=content;
       this.mId= mId;
       this.typeId= typeId;
       this.str1=str1;
       this.str2=str2;
       this.token=token;
       this.userType=userType;
    }  
    public void run() {  
    	  try  
          {  
              Thread.sleep(3000);  
          }  
          catch (InterruptedException e)  
          {  
        	  TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
          }  
    	ImpAppInformation push =new ImpAppInformation();
    	if(userType.equals("1")){
    		push.pushMessageForUsers(userId, cid, title, content,mId,typeId,str1,str2,token);
    	}else{
    		push.pushMessageForAdmin(userId, cid, title, content, mId, typeId, str1, str2, token);
    	}
    }  
    
} 

