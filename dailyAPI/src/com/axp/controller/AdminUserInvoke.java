package com.axp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.StringUtil;

@Controller
@RequestMapping("invoke/adminUser")
public class AdminUserInvoke extends BaseController {

    @RequestMapping("/getM")
    @ResponseBody
    public Map<String, Object> getMoney(HttpServletResponse response, HttpServletRequest request) {
   
            //获取参数；
            Parameter parameter = ParameterUtil.getParameter(request);      
            if (parameter == null) {//错误的参数；
                return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
            }
            String adminUserId = null;
        adminUserId = parameter.getAdminuserId();
        String basePath=request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
        Integer pageIndex = Integer.parseInt(parameter.getData().getString("pageIndex"));
        //已确认 未确认 押金
        Integer assetType = Integer.valueOf(parameter.getData().getString("assetType"))==null?0:Integer.valueOf(parameter.getData().getString("assetType")); 
        
        return adminUserService.getAdminUserMoney(adminUserId, basePath,pageIndex,assetType);
    }
    
    
    @RequestMapping("/getTotalM")
    @ResponseBody
    public Map<String, Object> getTotalM(HttpServletResponse response, HttpServletRequest request) {
   
            //获取参数；
            Parameter parameter = ParameterUtil.getParameter(request);      
            if (parameter == null) {//错误的参数；
                return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
            }
            String adminUserId = null;
        adminUserId = parameter.getAdminuserId();
        String basePath=request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
        Integer pageIndex = Integer.parseInt(parameter.getData().getString("pageIndex"));
        
        
        return adminUserService.getAdminUserTotalMoney(adminUserId, basePath,pageIndex);
    }
    
    
    
    @RequestMapping("/getR")
    @ResponseBody
    public Map<String, Object> getRepaper(HttpServletResponse response, HttpServletRequest request) {
   
            //获取参数；
            Parameter parameter = ParameterUtil.getParameter(request);      
            if (parameter == null) {//错误的参数；
                return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
            }
            String adminUserId = null;
        adminUserId = parameter.getAdminuserId();
        String basePath=request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
        Integer pageIndex = Integer.parseInt(parameter.getData().getString("pageIndex"));
        return adminUserService.getAdminUserRedPaper(adminUserId, basePath,pageIndex);
    }
    
    @RequestMapping("/agreement")
    public String agreement(HttpServletResponse response, HttpServletRequest request) {
        return "agreement/mtjfAgreem";
    }
    
    @RequestMapping("/agreementSeller")
    public String agreementSeller(HttpServletResponse response, HttpServletRequest request) {
        return "agreement/mtjfShopAgreem";
    }

}
