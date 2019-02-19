package com.axp.util.push;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.axp.domain.MessageCenter;

@Service
public class ImpAppInformation implements AppInformation {

	@Override
	public String pushMessageCenterToApp(MessageCenter messages) {
		// TODO Auto-generated method stub
		return PushMessageToApp.pushMessage(MessageToJson(messages),appId,appkey,master,host);
	}

	@Override
	public String pushLoginMessageSingle(String user_id,String lastCid,String beforeCid) {
		// TODO Auto-generated method stub
		System.out.println("单点登录开始+99999999999999999999");
		String taskCode = "1000";
		String title = "单点登录";
		String message = "此账号在另一台设备中登录，被迫下线，如非本人操作，请尽快修改密码";
		String data = UserToJson(user_id, lastCid, message, title, taskCode);
		String result = PushMessageToApp.pushMessageSingle(data,appId,appkey,master,host, beforeCid);
		System.out.println("单点登录结束");
		return result;
	}
	
	/**
	 * 商家版单点登录
	 */
	public String pushLoginSellerMessageSingle(String user_id,String lastCid,String beforeCid) {
		// TODO Auto-generated method stub
		System.out.println("商家版单点登录开始+99999999999999999999");
		String taskCode = "1000";
		String title = "单点登录";
		String message = "此账号在另一台设备中登录，被迫下线，如非本人操作，请尽快修改密码";
		String data = UserToJson(user_id, lastCid, message, title, taskCode);
		String result = PushMessageToApp.pushMessageSingle(data,"6NRip6zMGq72dc4av0WOw6",s_appkey,s_master,s_host, beforeCid);
		System.out.println("商家版单点登录结束");
		return result;
	}
	
	@Override
	public String pushMessageSingleToList(MessageCenter messages,List<String> cid) {
		// TODO Auto-generated method stub
		return PushMessageToApp.pushMessageSingleToList(MessageToJson(messages),appId,appkey,master,host, cid);
	}


	@Override
	public String MessageToJson(MessageCenter messages) {
		// TODO Auto-generated method stub
		return MessageToJsonUtil.MessageToJson(messages);
	}

	@Override
	public String UserToJson(String user_id, String userid) {
		// TODO Auto-generated method stub
		return MessageToJsonUtil.UserToJson(user_id, userid);
	}
	
	@Override
	public String UserToJson(String userId,String channelId,String message,String title,String taskCode) {
		// TODO Auto-generated method stub
		return MessageToJsonUtil.UserToJson(userId, channelId, message, title, taskCode);
	}

	@Override
	public void pushLoginMessageSingleIOS(String user_id, String cid) {
		// TODO Auto-generated method stub
		PushMessageToApp.pushMessageSingleIOS(UserToJson(user_id, cid),appId,appkey,master,host, cid);
	}

	@Override
	public void pushMessageSingleToListIOS(MessageCenter messages,
			List<String> cid) {
		// TODO Auto-generated method stub
		PushMessageToApp.pushMessageSingleToListIOS(messages, appId, appkey, master, host, cid);
		
	}

	@Override
	public String pushMessage(String user_id, String lastCid, String title, String message,String msgId,String typeId,String msg_unread_count,String sellermessage ,String tokenId) {
		System.out.println("开始用户版推送"+lastCid);
		String taskCode = "3000";
		String data = MessageToJsonUtil.MessageToJson(user_id, lastCid, message, title, taskCode,msgId,typeId,msg_unread_count,sellermessage);
		
		String result = PushMessageToApp.pushMessageSingle(data,appId,appkey,master,host, lastCid);
		
		
		PushMessageToApp.pushMessage(data,appId,appkey,master,host);//穿透消息
		
		if(StringUtils.isBlank(tokenId)){
			PushMessageToApp.pushMessageToList(title, message, appId,appkey,master,host, lastCid);//通知消息
		}else if(tokenId.length()==64){
			PushMessageToApp.apnpush(title, message,data, appId,appkey,master,host, tokenId);//苹果版推送
		}
		System.out.println("结束用户版推送"+lastCid);	
			
			return result;
	}
	
	@Override
	public String pushMessageForUsers(String user_id, String lastCid, String title, String message,String msgId,String typeId,String msg_unread_count,String sellermessage ,String tokenId ) {
			System.out.println("-------------------------s-----------"+lastCid);
			String taskCode = "3000";
			String data = MessageToJsonUtil.MessageToJson(user_id, lastCid, message, title, taskCode,msgId,typeId,msg_unread_count,sellermessage);	
			String result = PushMessageToApp.pushMessageSingleForUsers(data,appId,appkey,master,host, lastCid,title,sellermessage);
			
			System.out.println("-------------------------e-----------"+lastCid);
			return result;
	}
	
	
	public  static void main(String [] args){
		ImpAppInformation ia = new ImpAppInformation();
		ia.pushMessageForUsers("811", "902a68fca1f80b00991b3b3cf119d539", "显示标题", "测试内容", "1", "1", "1", "显示信息","");
	}
	
	@Override
	public String pushMessageForAdmin(String adminId, String lastCid, String title, String message,String msgId,String typeId,String msg_unread_count,String sellermessage ,String tokenId) {
		System.out.println("-------------------------ss-----------"+lastCid);
		String taskCode = "3000";
		String data = MessageToJsonUtil.MessageToJson(adminId, lastCid, message, title, taskCode,msgId,typeId,msg_unread_count,sellermessage);	
		String result = PushMessageToApp.pushMessageSingleForUsers(data,s_appId,s_appkey,s_master,s_host, lastCid,title,sellermessage);
		System.out.println("-------------------------se-----------"+lastCid);
		return result;
	}
	
	
	@Override
	public String pushMessageForSeller(String adminId, String lastCid, String title, String message,String msgId,String typeId,String msg_unread_count,String sellermessage ,String tokenId) {
		System.out.println("开始商家版推送"+lastCid);
			String taskCode = "3000";
			String data = MessageToJsonUtil.MessageToJson(adminId, lastCid, message, title, taskCode,msgId,typeId,msg_unread_count,sellermessage);
			String content =MessageToJsonUtil.MessageToJson(data);
			String result = PushMessageToApp.pushMessageSingle(data,s_appId,s_appkey,s_master,s_host, lastCid);
			
			
			PushMessageToApp.pushMessage(data,s_appId,s_appkey,s_master,s_host);//穿透消息
			
			if(StringUtils.isBlank(tokenId)){
				PushMessageToApp.pushMessageToList(title, message, s_appId,s_appkey,s_master,s_host, lastCid);//通知消息
			}else if(tokenId.length()==64){
				PushMessageToApp.apnpush(title, message,data, s_appId,s_appkey,s_master,s_host, tokenId);//苹果版推送
			}
			
			System.out.println("结束商家版推送"+lastCid);
			return result;
	}
	
	
	
	
	
	
	//==================================ZL===============================================//
		//消息推送
		@Override
		public void pushSystemMessageToListAPP(String title ,String content,
				List<String> cid) {
			PushMessageToApp.pushSystemMessageToListAPP(title, content, appId, appkey, master,host, cid);
		}
		
		@Override
		public String pushSystemMessageCenterToApp(String title ,String content) {
			return PushMessageToApp.pushSystemMessage(content, appId, appkey, master,host);
		}

		@Override
		public String pushMessage(String user_id, String lastCid,
				String beforeCid, String title, String message) {
			// TODO Auto-generated method stub
			return null;
		}

		//========================================end=======================================//

}
