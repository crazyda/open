package com.aliyun;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.constant.Constants;
import com.aliyun.constant.HttpSchema;
import com.aliyun.enums.Method;

public class SingleSendSms {
	  private final static String APP_KEY = "24535327"; //AppKey从控制台获取
	    private final static String APP_SECRET = "46c5645a4d20f4f41dd4b8786985f331"; //AppSecret从控制台获取
	    private final static String SIGN_NAME = "每天积分"; // 签名名称从控制台获取，必须是审核通过的
	    private final static String TEMPLATE_CODE = "SMS_77255068"; //模板CODE从控制台获取，必须是审核通过的
	    private final static String HOST = "sms.market.alicloudapi.com"; //API域名从控制台获取
	   
	    private final static String ERRORKEY = "errorMessage";  //返回错误的key

	    // @phoneNum: 目标手机号，多个手机号可以逗号分隔;
	    // @params: 短信模板中的变量，数字必须转换为字符串，如短信模板中变量为${no}",则参数params的值为{"no":"123456"}
	    public String sendMsg(String phoneNum, String params){
	        String path = "/singleSendSms";

	        Request request =  new Request(Method.GET, HttpSchema.HTTP + HOST, path, APP_KEY, APP_SECRET, Constants.DEFAULT_TIMEOUT);

	        //请求的query
	        Map<String, String> querys = new HashMap<String, String>();
	        querys.put("SignName", SIGN_NAME);
	        querys.put("TemplateCode", TEMPLATE_CODE);
	        querys.put("RecNum", phoneNum);
	        querys.put("ParamString", params);
	        request.setQuerys(querys);

	        try {
	            Map<String, String> bodymap = new HashMap<String, String>();
	            Response response = Client.execute(request);
	            //根据实际业务需要，调整对response的处理
	            if (null == response) {
	                System.out.println("no response");
	            } else if (200 != response.getStatusCode()) {
	                System.out.println("StatusCode:" + response.getStatusCode());
	                System.out.println("ErrorMessage:"+response.getErrorMessage());
	                System.out.println("RequestId:"+response.getRequestId());
	            } else {
	                bodymap = ReadResponseBodyContent(response.getBody());
	                if (null != bodymap.get(ERRORKEY)) {
	                    //当传入的参数不合法时，返回有错误说明
	                	System.out.println("失败信息："+bodymap);
	                    System.out.println(bodymap.get(ERRORKEY));
	                    return "error";
	                } else {
	                    //成功返回map，对应的key分别为：message、success等
	                    System.out.println(JSON.toJSONString(bodymap));
	                    return "success";
	                }
	            }
	            return "success";
	        }catch (Exception e){
	        	e.printStackTrace();
	            System.out.println(e.getMessage());
	            return "fail";
	        }
	    }

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


	    public  static void main(String agrs[]){
	        SingleSendSms app = new SingleSendSms();
	        app.sendMsg("15999721872","{\"captcha\":\"321456\"}");
	    }
}
