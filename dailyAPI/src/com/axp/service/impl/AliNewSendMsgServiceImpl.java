package com.axp.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.axp.domain.AliMessage;
import com.axp.domain.AliMessageList;
import com.axp.service.AliNewSendMsgService;
import com.axp.util.ToolSpring;
public class AliNewSendMsgServiceImpl extends BaseServiceImpl<AliMessage> implements AliNewSendMsgService,Runnable{
	
	//产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";
	
    public static final String accessKeyId = "LTAIfDxCIQ3uK1q7";
    public static final String accessKeySecret = "yElWHjwdlKL7PJ5sfas2XscweZlsFf";
    
    
    public static String phoneNum="";
    public static String params="";
    public static String template_code="";
    public static String SIGE_NAME="广东每天积分";
    private SessionFactory sessionFactory= null;
    
    public static SendSmsResponse sendSms(String phoneNum, String params,
			String template_code) throws ClientException {
    	
    	System.out.println("我进来了！！！！！！！");
    	   //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        
   	    //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
		
	        //必填:待发送手机号
	        request.setPhoneNumbers(phoneNum);
	        //必填:短信签名-可在短信控制台中找到
	        request.setSignName(SIGE_NAME);
	        //必填:短信模板-可在短信控制台中找到
	        request.setTemplateCode(template_code);
	        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
	        request.setTemplateParam(params);

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        
        //hint 此处可能会抛出异常，注意catch
       SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
       	System.out.println("我要出去了！！！！！");
        return sendSmsResponse;
    }
    
    
    
    public static QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber(phoneNum);
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        
        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }
    
	
	   public AliNewSendMsgServiceImpl(){};
	public AliNewSendMsgServiceImpl(String phoneNum, String params,
			String template_code) {
		super();
	
		AliNewSendMsgServiceImpl.phoneNum = phoneNum;
		AliNewSendMsgServiceImpl.params = params;
		AliNewSendMsgServiceImpl.template_code = template_code;
		sessionFactory=(SessionFactory) ToolSpring.getBean(SessionFactory.class);
	}
	
	
	public void send(){
		try {
			
			SendSmsResponse response = sendSms(phoneNum,params,template_code);
			System.out.println("短信接口返回的数据----------------");
	        System.out.println("Code=" + response.getCode());
//	        System.out.println("Message=" + response.getMessage());
//	        System.out.println("RequestId=" + response.getRequestId());
//	        System.out.println("BizId=" + response.getBizId());
//	        
			if (response.getCode()!=null && response.getCode().equals("ok")) {
				QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(response.getBizId());
	            System.out.println("短信明细查询接口返回数据----------------");
	            System.out.println("Code=" + querySendDetailsResponse.getCode());
//	            System.out.println("Message=" + querySendDetailsResponse.getMessage());
	            
	            int i = 0;
	            for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
	            {
	                System.out.println("SmsSendDetailDTO["+i+"]:");
	                System.out.println("Content=" + smsSendDetailDTO.getContent());
	                System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
	                System.out.println("OutId=" + smsSendDetailDTO.getOutId());
	                System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
	                System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
	                System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
	                System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
	                System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
	            }
	            System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
	            System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
	            
	           
        		
			}
			AliMessageList aliMessageList = new AliMessageList();
	        aliMessageList.setContent(params);
         	aliMessageList.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
         	aliMessageList.setIsValid(true);
         	aliMessageList.setReceivePhone(phoneNum);
         	Session session = sessionFactory.openSession();
     		session.save(aliMessageList);
     		session.close();
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}



	@Override
	public void run() {
		try {
			System.out.println("zzzzzzzzzz");
			SendSmsResponse response = sendSms(phoneNum,params,template_code);
			
			System.out.println("短信接口返回的数据----------------");
	        System.out.println("Code=" + response.getCode());
	        System.out.println("Message=" + response.getMessage());
	        System.out.println("RequestId=" + response.getRequestId());
	        System.out.println("BizId=" + response.getBizId());
	        
			if (response.getCode()!=null && response.getCode().equals("ok")) {
				QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(response.getBizId());
	            System.out.println("短信明细查询接口返回数据----------------");
	            System.out.println("Code=" + querySendDetailsResponse.getCode());
	            System.out.println("Message=" + querySendDetailsResponse.getMessage());
	            
	            int i = 0;
	            for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
	            {
	                System.out.println("SmsSendDetailDTO["+i+"]:");
	                System.out.println("Content=" + smsSendDetailDTO.getContent());
	                System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
	                System.out.println("OutId=" + smsSendDetailDTO.getOutId());
	                System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
	                System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
	                System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
	                System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
	                System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
	            }
	            System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
	            System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
	            
	           
        		
			}
			AliMessageList aliMessageList = new AliMessageList();
	        aliMessageList.setContent(params);
         	aliMessageList.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
         	aliMessageList.setIsValid(true);
         	aliMessageList.setReceivePhone(phoneNum);
         	Session session = sessionFactory.openSession();
     		session.save(aliMessageList);
     		session.close();
			
			System.out.println("ssssssssss");
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

}
