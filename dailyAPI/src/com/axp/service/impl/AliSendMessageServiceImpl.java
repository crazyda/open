package com.axp.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.Client;
import com.aliyun.Request;
import com.aliyun.Response;
import com.aliyun.constant.Constants;
import com.aliyun.constant.HttpSchema;
import com.aliyun.enums.Method;
import com.axp.dao.AliMessageListDao;
import com.axp.dao.IAliMessageDao;
import com.axp.dao.impl.AliMessageListDaoImpl;
import com.axp.dao.impl.IAliMessageDaoImpl;
import com.axp.domain.AliMessage;
import com.axp.domain.AliMessageList;
import com.axp.service.AliSendMessageService;
import com.axp.util.ToolSpring;
@Service("sendMessage")
public class AliSendMessageServiceImpl extends BaseServiceImpl<AliMessage> implements AliSendMessageService,Runnable{
	
	IAliMessageDao aliMessageDao;
	
	AliMessageListDao aliMessageListDao;
	public final static String HOST = "sms.market.alicloudapi.com"; //API域名
	public String APP_KEY="";
	public String APP_SECRET ="";
	public String SIGE_NAME ="";
    private final static String ERRORKEY = "errorMessage";  //返回错误的key

    private String result;
    public String phoneNum;
    public String params;
    public String template_code;
	private SessionFactory sessionFactory= null;
    
	   private Map<String, String> ReadResponseBodyContent(String body) {
	        Map<String, String> map = new HashMap<String, String>();    
	        try {
	            JSONObject jsonObject = JSON.parseObject(body);
	            if (null != jsonObject) {               
	                for(Entry<String, Object> entry : jsonObject.entrySet()){
	                    map.put(entry.getKey(), entry.getValue().toString());
	                }               
	            }
	            if ("false".equals(map.get("success"))) {
	                map.put(ERRORKEY, map.get("message"));
	            }
	        } catch (Exception e) {
	            map.put(ERRORKEY, body);
	        }
	        return map;
	    }

	   public AliSendMessageServiceImpl(){};
	public AliSendMessageServiceImpl(String phoneNum, String params,
			String template_code) {
		super();
		this.phoneNum = phoneNum;
		this.params = params;
		this.template_code = template_code;
		sessionFactory=(SessionFactory) ToolSpring.getBean(SessionFactory.class);
	}

	@Override
	public void run() {
		AliMessageList aliMessageList = new AliMessageList();
		aliMessageDao=new IAliMessageDaoImpl();
		aliMessageListDao=new AliMessageListDaoImpl();
		AliMessage aliMessage=getAppKey(template_code);
    	if (aliMessage!=null) {
			APP_KEY =aliMessage.getAppKey();
			APP_SECRET = aliMessage.getAppSecret();
			SIGE_NAME = aliMessage.getSignName();
		
        String path = "/singleSendSms";
        Request request =  new Request(Method.GET, HttpSchema.HTTP + HOST, path, APP_KEY, APP_SECRET, Constants.DEFAULT_TIMEOUT);
        
        //请求的query
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("SignName", SIGE_NAME);
        querys.put("TemplateCode", template_code);
        querys.put("RecNum", phoneNum);
        querys.put("ParamString", params);
        request.setQuerys(querys);
    	
        try {
            Map<String, String> bodymap = new HashMap<String, String>();
            Response response = Client.execute(request);
            if (null == response) {                                        
                System.out.println("no response");
            } else if (200 != response.getStatusCode()) {
                System.out.println("StatusCode:" + response.getStatusCode());
                System.out.println("ErrorMessage:"+response.getErrorMessage());
                System.out.println("RequestId:"+response.getRequestId());
            } else {
                bodymap = ReadResponseBodyContent(response.getBody());
                if (null != bodymap.get(ERRORKEY)) {
                    System.out.println("发送失败了！！！！！"+bodymap.get(ERRORKEY)+APP_KEY);
                    result= "error";
                    
                } else {
                	aliMessageList.setContent(params);
                	aliMessageList.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
                	aliMessageList.setIsValid(true);
                	aliMessageList.setReceivePhone(phoneNum);
                	Session session = sessionFactory.openSession();
            		session.save(aliMessageList);
            		session.close();
                    System.out.println("发送成功了！！！！！！"+JSON.toJSONString(bodymap)+APP_KEY);
                    result= "success";
                }
            }
            result="success";
        }catch (Exception e){
        	e.printStackTrace();
            System.out.println(e.getMessage());
            result= "fail";
        }
      }
    	result="success";
	}
	
	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}
	
	public AliMessage getAppKey(String template_code) {
		Session session = sessionFactory.openSession(); 
    	Query query = session.createQuery("from AliMessage where isValid=1 and templateCode=:template_code");
    	query.setString("template_code", template_code);
    	AliMessage ali=(AliMessage) query.uniqueResult();
    	session.close();
		return ali;
	}
	
	
}
