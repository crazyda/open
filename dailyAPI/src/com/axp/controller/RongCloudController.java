package com.axp.controller;


import java.io.Reader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.rongcloud.RongCloud;
import com.rongcloud.models.TokenResult;

@Controller
@RequestMapping("invoke/rongcloud")
public class RongCloudController extends BaseController{

	
		
	
		 String appKey = "appkey";//替换成您的appkey
		 String appSecret = "secret";//替换成匹配上面key的secret
	

		Reader reader = null ;
		RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
	
	
	    @RequestMapping("/getToken")
	    @ResponseBody
	    public Object getToken(HttpServletResponse response, HttpServletRequest request) {
		  //获取参数；
          Parameter parameter = ParameterUtil.getParameter(request);      
          if (parameter == null) {//错误的参数；
              return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
          }
          try {
        	  return rongCloudService.getToken("", "", "");
		} catch (Exception e) {
				e.printStackTrace();
		}
          return null;
	    }
	
	
	
	
	
}
