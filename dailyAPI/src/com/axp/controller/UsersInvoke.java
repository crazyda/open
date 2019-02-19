package com.axp.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.axp.domain.AdminUser;
import com.axp.domain.Seller;
import com.axp.domain.SellerMainPage;
import com.axp.domain.Users;
import com.axp.service.RongCloudService;
import com.axp.util.DateUtil;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.StringUtil;
import com.axp.util.UrlUtil;
import com.rongcloud.methods.Group;
import com.rongcloud.models.CodeSuccessResult;
import com.rongcloud.models.GroupInfo;
import com.rongcloud.models.TokenResult;

@Controller
@RequestMapping("invoke/users")
public class UsersInvoke extends BaseController {
	
    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> login(HttpServletResponse response, HttpServletRequest request) {
        try {
            //获取参数；
            Parameter parameter = ParameterUtil.getParameter(request); 
            
            if (parameter == null) {//错误的参数；
                return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
            }
	        String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
	        Map<String, Object> map = new HashMap<String, Object>();
            String username = null;
            String password = null;
            String openId = null;
            String loginname = null;
            String tokenId = null;
            String channelId = null;
            String app = null;
            String appVersion=null;
            String captcha=null;
            String Invitecode=null;
            String os=null;
            String unionId = null; 
            String lat = null;
            String lng = null;
            boolean isUserVersion = false; 
           
            try {
            	
                username = parameter.getData().getString("name");
                password = parameter.getData().getString("pwd");
                openId = parameter.getData().getString("openId");
                loginname = parameter.getData().getString("loginname");
                tokenId = parameter.getTokenId();
                channelId = parameter.getChannelId();
                app = parameter.getApp();
                appVersion = parameter.getAppVersion();
                captcha=parameter.getData().getString("verifyCode");
                Invitecode=parameter.getData().getString("Invitecode");
                unionId = parameter.getData().getString("unionId");//微信登录的id
                os=parameter.getOs();
                lat=parameter.getLat();
                lng = parameter.getLng();
                //测试账号
                if("18700004848".equals(username)){
                	password = "888888";
                	
                }
                if(app==null){
                	if("1.0.1".equals(appVersion)  || "5".equals(appVersion)){
                		app="SELLER";
                	}else{
                		app= "USERS";
                	}
                }
            } catch (Exception e) {
                e.printStackTrace();
                return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
            }
            
       	 	Users user=null;
            if(app.equals("SELLER")){
            	if(StringUtils.isNotBlank(openId)){
            		synchronized (this) {
                		  user = usersService.loginByThirdMethod(openId,unionId,app);
    				}
            		
            		if (user == null) {//第三方登录，一定会成功，即使没有用户也会创建用户，所以为空即为报错；
                    	return JsonResponseUtil.getJson(-0x02, "该微信还没有开通店铺,请先登录用户版并开通店铺!");
                    	
                    } else {
                        map.put("status", 1);
                        map.put("message", "登陆成功");
                        Map<String, Object> m1 = new HashMap<String, Object>();
                        Seller seller = sellerService.getSellerByUsersId(user.getId());
                        if(seller !=null){
                        	SellerMainPage sellerMainPage = sellerMainPageDAO.findOneBySellerId(seller.getId());
    	    				String sellerLogo=StringUtil.sellerHead;
    	    				if(sellerMainPage!=null  ){
    		    				if(StringUtils.isBlank(sellerMainPage.getSellerLogo())||StringUtils.isNotBlank(sellerMainPage.getSellerLogo())&&sellerMainPage.getSellerLogo().length()<50){
    		    					sellerLogo=StringUtil.sellerHead;
    		    				}else{
    		    					sellerLogo=basePath+sellerMainPage.getSellerLogo();
    		    				}
    	    				}
    						TokenResult tokenResult = rongCloudService.getToken("axp"+seller.getAdminUser().getId().toString(),seller.getName(),sellerLogo);
    						m1.put("token",tokenResult.getToken());
    						m1.put("sellerLogo",sellerLogo);
    						m1.put("sellerName",seller.getName()==null?"商家":seller.getName());
    	                    m1.put("sellerId", seller.getId()+"");
                        	
                            m1.put("sellerId", seller.getId()+"");
                            m1.put("adminuserId", seller.getAdminUser().getId()+"");
        	                m1.put("axpAdminUserId", "axp"+seller.getAdminUser().getId()+"");
                        }else{
                        	
                        	m1.put("token","-1");
    						m1.put("sellerLogo","");
    						m1.put("sellerName","");
    						map.put("data", m1);
    						map.put("status", -15);
                            map.put("message", "此账号未开通店铺,请先去用户版开通店铺");
    					    return map;
                        }
                        map.put("data", m1);
                      //商家版单点登录
                        AdminUser adminUser = seller.getAdminUser();
    					usersService.sellerSingleSignOn(adminUser, channelId, tokenId);	
                        return map;
                    }
            	}else{
            		
            	}
            	
            }else{
            //第三方登录逻辑；
            if (StringUtils.isNotBlank(openId)) {
            
            	synchronized (this) {
            		  user = usersService.loginByThirdMethod(openId,unionId,app);
				}
              
                if (user == null) {//第三方登录，一定会成功，即使没有用户也会创建用户，所以为空即为报错；
                	return JsonResponseUtil.getJson(-0x02, "第三方登录错误");
                	
                } else {//第三方登录成功；
                    
                    map.put("status", 1);
                    map.put("message", "登陆成功");

                    Map<String, Object> m1 = new HashMap<String, Object>();
                    Seller seller = sellerService.getSellerByUsersId(user.getId());
                    if(seller !=null){
                    	if(seller.getAdminUser()!=null){
                    		m1.put("adminuserId", seller.getAdminUser().getId()+"");
                    	}else{
                    		m1.put("adminuserId", "");
                    	}
                    	
                        m1.put("sellerId", seller.getId()+"");
                    }else{
                    	m1.put("adminuserId", "");
                    	m1.put("sellerId", "");
                    	
                    }
                    
                    if(user.getId()!=null && user.getId()>0){
                    	m1.put("userId", user.getId());
                    }else{
                    	m1.put("userId", "-1");
                    	
                    }
                    m1.put("loginname", "axp"+openId.substring(openId.length()-7));

					String imgUrl =StringUtil.getUserDefaultHead(user, basePath);
            		TokenResult tokenResult = rongCloudService.getToken(user.getId().toString(),user.getName(),imgUrl);	
            		m1.put("token",tokenResult.getToken());
            		m1.put("userHead",imgUrl);
                    map.put("data", m1);
                    //保存头像
                    if(user.getImgUrl()==null&&user.getHeadimage()==null){
                    	String headimage =  parameter.getData().getString("headimage"); 
                    	user.setHeadimage(headimage);
                    }
                    //昵称
                    if(user.getRealname()==null){
                    	String realname = parameter.getData().getString("realname");
                    	String a = StringUtil.filterEmoji(realname);
                    	user.setRealname(a);
                    }
                    //用户登录名
                    if (user.getLoginname()==null) {
						user.setLoginname("axp"+openId.substring(openId.length()-7));
					}
                    //性别
                    if(user.getSex()==null){
                    	
                    	String sex = parameter.getData().getString("sex");
                    	
                    	if("男".equals(sex)){
                			user.setSex(1);
                		}else if("女".equals(sex)){
                			user.setSex(2);
                		}else{
                			user.setSex(3);
                		}
                    }
                    if(StringUtils.isEmpty(user.getMycode())){
                    	user.setMycode("6"+user.getId()); //修改的邀请码
                    }
                    //微信unionId 
                    if(user.getUnionId()==null){
                    	user.setUnionId(unionId);
                    }
                   user.setLat(lat);
                   user.setLng(lng);
                   // usersService.update(user);
                    //单点登录
                    usersService.singleSignOn(user, channelId,tokenId);
                    
                    //监控注册
//                   usersService.registorUsersMonitor(user);
                    return map;
                }
            }
            
            }
            //检查username,password；
            Boolean emptyCheck = ParameterUtil.EmptyCheck(username);   
            if (!emptyCheck && "".equals(captcha)) { 
                return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
            }
            
            //处理登录逻辑，返回json
            try {
                return usersService.loginByUsernameAndPassword(request,username, password, channelId,app,tokenId,captcha,Invitecode,os);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //未知错误；
            return JsonResponseUtil.getJson(-0x01, "未知错误");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    @RequestMapping("/wxusers")
    @ResponseBody
    public Map<String,Object> wxusers(HttpServletRequest request, HttpServletResponse response){
    	
    	return usersService.wxusers(request,response);
    }
    
    
    
    
    /**
     * 自动创建群,根据级别分配,事业合伙人level==2,合伙人level==3,da
     */
    @RequestMapping("/group")
    @ResponseBody
    public Map<String,Object> group(HttpServletRequest request, HttpServletResponse response){
    	
    	return usersService.group(request,response);
    }
    
    
    /**
     * 用户加入群组
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/joinGroup")
    @ResponseBody
    public Map<String,Object> joinGroup(HttpServletRequest request, HttpServletResponse response){
    	return rongCloudService.getJoinGroup(request);
    	
    }
    
    
    
    /**
     *  同步用户所属群组方法 da
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/syncGroup")
    @ResponseBody
    public Map<String,Object> syncGroup(HttpServletRequest request, HttpServletResponse response){
    	
    	 Parameter parameter = ParameterUtil.getParameter(request);
    	if(parameter == null){
    		return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
    	}
    	String userId = null;
    	String [] groupId = null;
    	 try {
	         userId = parameter.getUserId();
	         groupId =   parameter.getData().getString("groupId").split(",");
	         
         } catch (Exception e) {
        	 e.printStackTrace();
             return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
             
         }
    	 GroupInfo[] groupInfo = new GroupInfo [groupId.length];
    	
    	 
    	 
    	 Map<String,Object> groupCode = new HashMap<String,Object>();
    	 
    	CodeSuccessResult syncGroup =  rongCloudService.getSyncGroup(userId, groupInfo);
    	
    	if(syncGroup.getCode() == 200){
       	 groupCode.put("code",syncGroup.getCode()); 
        }else{
       	 groupCode.put("code", syncGroup.getCode());
       	 groupCode.put("message", syncGroup.getErrorMessage());
        }
    	
    	return groupCode;
    	
    }

    
    
    
    
    /**
     * 用户注册；
     *
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public Map<String, Object> register(HttpServletRequest request, HttpServletResponse response) {

        //获取参数；
        Parameter parameter = ParameterUtil.getParameter(request);
        if (parameter == null) {//错误的参数；
            return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
        }

        //获取具体需要的参数值，并检查空值；
        String username = null;
        String loginname = null;
        String password = null;
        String captcha = null;
        String invitecode = null;
        String headimage = null;
        String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
        try {
        	username = parameter.getData().getString("name");
        	loginname = parameter.getData().getString("axp"+username.substring(username.length()-7));
            password = parameter.getData().getString("pwd");
            captcha = parameter.getData().getString("captcha");
            invitecode = parameter.getData().getString("invitecode");
             headimage = parameter.getData().getString("headimage");
            invitecode = invitecode == null ? "" : invitecode;
            //检查参数空值；
            Boolean emptyCheck = ParameterUtil.EmptyCheck(username, password, captcha);
            if (!emptyCheck) {
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
        }

        //处理注册的业务逻辑；
        return usersService.register(loginname,username, password, captcha, invitecode,headimage,basePath);
    }

    /**
     * 个人中心
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getBaseInfo")
    @ResponseBody
    public Map<String, Object> getBaseInfo(HttpServletRequest request, HttpServletResponse response) {
    	String xcx = request.getParameter("xcx");
    	String adminuserId = "";
    	String sellerId = "";
    	Integer userId = 0;
    	String appVersion = "";
    	 String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
    	 Map<String, Object> map =  null;
    	if(xcx != null){
    		adminuserId = request.getParameter("adminuserId");
    		sellerId = request.getParameter("sellerId");
    		userId = Integer.parseInt(request.getParameter("userId"));
    		
    		map = usersService.getUserMap(userId,basePath);
    		
    	}else{
    		 //获取参数；
            Parameter parameter = ParameterUtil.getParameter(request);
            if (parameter == null) {//错误的参数；
                return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
            }

            adminuserId = parameter.getAdminuserId();
            sellerId= parameter.getSellerId();
           
            if(parameter.getUserId()!=""){
            	
            	 userId = Integer.valueOf(parameter.getUserId());
            }
           
            appVersion = parameter.getAppVersion();
            map = usersService.getUserMap(adminuserId,sellerId,userId, basePath,0,appVersion);
    	}
       
        return map;
    }
    
    /*
     * 
     * 申请合伙人
     * @author ZL
     * */
    @RequestMapping("/ApplyPartner")
    @ResponseBody
    public Map<String, Object> ApplyPartner(HttpServletRequest request,HttpServletResponse response){
    	Map<String, Object> statusMap = new HashMap<String, Object>();
    	Parameter parameter = ParameterUtil.getParameter(request);
    	if (parameter==null) {
			return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
		}
    	try {
    		
    		Map<String, Object> maps = new HashMap<String, Object>();
    		
				String userId = parameter.getUserId()==null?"-1":parameter.getUserId();
				String zoneId = parameter.getZoneId()==null?"-1":parameter.getZoneId();
				
				String status = String.valueOf(1);//标识 1 APP  2 WEB 固定参数
				String hhhuri1 = "http://seller.aixiaoping.com/Share/Index/partner?user_id="+userId+"&zone_id="+zoneId+"&status="+status+"&div="+String.valueOf(1);
				String hhhuri2 = "http://seller.aixiaoping.com/Share/Index/partner?user_id="+userId+"&zone_id="+zoneId+"&status="+status+"&div="+String.valueOf(2);
				String hhhuri3 = "http://seller.aixiaoping.com/Share/Index/partner?user_id="+userId+"&zone_id="+zoneId+"&status="+status+"&div="+String.valueOf(3);
				String hhhuri4 = "http://seller.aixiaoping.com/Share/Index/partner?user_id="+userId+"&zone_id="+zoneId+"&status="+status+"&div="+String.valueOf(4);
				List<String> mapList1 =new ArrayList<String>();
				mapList1.add(hhhuri1);
				mapList1.add(hhhuri2);
				mapList1.add(hhhuri3);
				mapList1.add(hhhuri4);
				
				
				
				List<List<String>> mapList2 =new ArrayList<List<String>>();
				List<String> list1 = new ArrayList<String>();
				list1.add("如果想了解粉丝更多信息");
				list1.add("请咨询城市运营商18922223333（徐盛）");
				list1.add("地址：江西省江南西路河源大街2233号临江大厦");
				mapList2.add(list1);
				
				List<String> list2 = new ArrayList<String>();
				list2.add("如果想了解粉丝更多信息");
				list2.add("请咨询城市运营商18922223333（徐盛）");
				list2.add("地址：江西省江南西路河源大街2233号临江大厦");
				mapList2.add(list2);
				
				List<String> list3 = new ArrayList<String>();
				list3.add("如果想了解粉丝更多信息");
				list3.add("请咨询城市运营商18922223333（徐盛）");
				list3.add("地址：江西省江南西路河源大街2233号临江大厦");
				mapList2.add(list3);
				
				List<String> list4 = new ArrayList<String>();
				list4.add("如果想了解粉丝更多信息");
				list4.add("请咨询城市运营商18922223333（徐盛）");
				list4.add("地址：江西省江南西路河源大街2233号临江大厦");
				mapList2.add(list4);
				
				List<String> mapList3 =new ArrayList<String>();
				mapList3.add("18933334443");
				mapList3.add("18933334443");
				mapList3.add("18933334443");
				mapList3.add("18933334443");
				
				String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
				
				String  shareuri= "http://seller.aixiaoping.com/Share/Index/partner?user_id="+userId+"&zone_id="+zoneId+"&status="+String.valueOf(2);
				
				maps.put("links", mapList1);
				maps.put("tips", mapList2);
				maps.put("tipsPhone", mapList3);
				maps.put("shareTargetUrl", shareuri);//分享链接
				maps.put("shareTitle", "分享合伙人");
				maps.put("shareContent", "分享合伙人的分享内容");
				maps.put("shareIconUrl",basePath+"images/icon_app.png");
				
				
			statusMap.put("data", maps);
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功");
		} catch (Exception e) {
			e.printStackTrace();
			statusMap.put("status", -1);
			statusMap.put("message", "请求失败");
		}
    	return statusMap;
    }
    
    //商家中心接口
    @RequestMapping("/getSellerInfo")
    @ResponseBody
    public Map<String,Object> getSellerInfo(HttpServletRequest request, HttpServletResponse response){
    	return sellerService.getSellerInfo(request, response);
    }

    @RequestMapping("/sendCaptcha")
    @ResponseBody
    public Map<String, Object> sendCaptcha(HttpServletRequest request, HttpServletResponse response) {
        //获取参数；
        Parameter parameter = ParameterUtil.getParameter(request);
        if (parameter == null) {//错误的参数；
            return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
        }

        
        String phone = parameter.getData().getString("phone");
        if (!StringUtil.hasLength(phone)) {
            return JsonResponseUtil.getJson(-0x0051, "电话号码为空");
        }
        
        
        return captchaService.sendCaptcha(phone);
    }

    @RequestMapping("/changePassword")
    @ResponseBody
    public Map<String, Object> changePassword(HttpServletRequest request, HttpServletResponse response) {
        //获取参数；
        Parameter parameter = ParameterUtil.getParameter(request);
        if (parameter == null) {//错误的参数；
            return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
        }

        Integer userId = Integer.parseInt(parameter.getUserId());
        String oldpwd = parameter.getData().getString("oldpwd");
        String newpwd = parameter.getData().getString("newpwd");

        return usersService.changePassword(userId, oldpwd, newpwd);
    }
    
    
  //验证码校验
    @RequestMapping("/checkCaptcha")
    @ResponseBody
    public Map<String,Object> checkCaptcha(HttpServletRequest request, HttpServletResponse response){
    	  Parameter parameter = ParameterUtil.getParameter(request);
          if (parameter == null) {//错误的参数；
              return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
          } 
    	String phone = parameter.getData().getString("phone");
         String captcha = parameter.getData().getString("captcha");
    	Map<String, Object> checkmap =  captchaService.checkCaptcha(phone, captcha);
  		
		return checkmap;
    }
    
    
    
    
    //忘记密码
    @RequestMapping("/forgetPassword")
    @ResponseBody
    public Map<String, Object> forgetPassword(HttpServletRequest request, HttpServletResponse response) {
        //获取参数；
        Parameter parameter = ParameterUtil.getParameter(request);
        if (parameter == null) {//错误的参数；
            return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
        }
        String app=parameter.getApp();
        String confirmpwd = parameter.getData().getString("confirmpwd");
        String newpwd = parameter.getData().getString("newpwd");
        String phone = parameter.getData().getString("phone");
       
        
     
        if("USERS".equals(app)){
        	return usersService.forgetPassword( confirmpwd, newpwd, phone, null);
        }
        return usersService.forgetPasswordFromSeller( confirmpwd, newpwd, phone, null);
    }

    //添加收货地址
    @RequestMapping("/putAddress")
    @ResponseBody
    public Map<String, Object> putAddress(HttpServletRequest request, HttpServletResponse response) {
        return usersService.putAddress(request, response);
    }

    //获取用户地址列表
    @RequestMapping("/getAddressList")
    @ResponseBody
    public Map<String, Object> getAddressList(HttpServletRequest request, HttpServletResponse response) {
        return usersService.getAddressList(request, response);
    }

    //获取用户的默认收货地址
    @RequestMapping("/getDefaultAddress")
    @ResponseBody
    public Map<String, Object> getDefaultAddress(HttpServletRequest request, HttpServletResponse response) {
        return usersService.getDefaultAddress(request, response);
    }

    //设置默认收货地址
    @RequestMapping("/setDefaultAddress")
    @ResponseBody
    public Map<String, Object> setDefaultAddress(HttpServletRequest request, HttpServletResponse response) {
        return usersService.setDefaultAddress(request, response);
    }

    //修改收货地址
    @RequestMapping("/updateAddress")
    @ResponseBody
    public Map<String, Object> updateAddress(HttpServletRequest request, HttpServletResponse response) {
        return usersService.updateAddress(request, response);
    }

    //删除收货地址
    @RequestMapping("/removeAddress")
    @ResponseBody
    public Map<String, Object> removeAddress(HttpServletRequest request, HttpServletResponse response) {
        return usersService.deleteAddress(request, response);
    }

    //获取粉丝列表
    @RequestMapping("/getFansList")
    @ResponseBody
    public Map<String, Object> getFansList(HttpServletRequest request, HttpServletResponse response) {
        return usersService.getFansList(request, response);
    }

    //绑定联系人
    @RequestMapping("/bangdingContacts")
    @ResponseBody
    public Map<String, Object> bangdingContacts(HttpServletRequest request, HttpServletResponse response) {
        return usersService.bangdingContacts(request, response);
    }

    //获取兑换券列表
    @RequestMapping("/getExemptList")
    @ResponseBody
    public Map<String, Object> getExemptList(HttpServletRequest request, HttpServletResponse response) {
        return usersService.getExemptList(request, response);
    }

    //意见反馈
    @RequestMapping("/feedback")
    @ResponseBody
    public Map<String, Object> feedback(HttpServletRequest request, HttpServletResponse response) {
        return usersService.feedback(request, response);
    }
    
    //意见反馈小提示
    @RequestMapping("/feedbackTips")
    @ResponseBody
    public Map<String, Object> feedbackTips(HttpServletRequest request,HttpServletResponse response){
    	return usersService.feedbackTips(request, response);
    }
    //修改个人资料
    @RequestMapping("/changeBaseInfo")
    @ResponseBody
    public Map<String,Object> changeBaseInfo(HttpServletRequest request, HttpServletResponse response){
        return usersService.changeBaseInfo(request,response);
    }

    //图片上传；
    @RequestMapping("/uploadFilePic")
    @ResponseBody
    public Map<String, Object> uploadFile(HttpServletResponse r, HttpServletRequest request) {

        //返回值；
        Map<String, Object> map = new HashMap<>();
        map.put("status", -0x01);

        //资源服务器地址；
       String basePathOfResource = (String) request.getServletContext().getAttribute("RESOURCE_LOCAL_URL");
        //String basePathOfResource = "http://jifen.aixiaoping.cn:8080/jupinhuiRes/";
       
        //获取上传的文件；
        MultipartFile file;
        try {
            MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) request;
            file = mreq.getFile("uploadFile");
            
            //检查是否是图片；
            String imageName = file.getOriginalFilename();
            
            int i = imageName.lastIndexOf(".");
            if (i == -1) {//非图片文件；
                map.put("message", "an error when get image from request,the file is not a picture;");
            } else {
                String pic = "png,jpg,jpeg,gif,PNG,JPG,JPEG,GIF";
                String end = imageName.substring(i + 1);
                if (!pic.contains(end)) {//有后缀，但不是指定的图片文件；
                    map.put("message", "an error when get image from request,check the key of image;");
                    return map;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message", "an error when get image from request,check the key of image;");
            return map;
        }

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            //设置资源服务器地址；
            HttpPost httppost = new HttpPost(basePathOfResource + "fileHandle/upload");

            //存放数据，发送请求；
            InputStreamBody streamBody = new InputStreamBody(file.getInputStream(), file.getOriginalFilename());
            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("uploadFile", streamBody)
                    .addTextBody("dirName", "phoneUploadPics")
                    .build();
            httppost.setEntity(reqEntity);
            
            //获取请求的响应结果；
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    //获取返回的内容；
                    Long contentLength = resEntity.getContentLength();
                    InputStream content = resEntity.getContent();
                    byte[] b = new byte[Integer.parseInt(contentLength.toString())];
                    content.read(b);

                    //拼接返回值；
                    JSONObject jsonObject = JSONObject.parseObject(new String(b));
                    String picPath = ((String) jsonObject.get("picPath")).replaceAll("\\\\", "/");
                    map.put("status", 0x01);
                    map.put("message", "success");
                    Map<String, String> dataMap = new HashMap<>();
                    dataMap.put("oppositeUrl", picPath);
                    dataMap.put("absoluteUrl", basePathOfResource + picPath);
                    map.put("data", dataMap);
                    try {
                        content.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return map;
                }
                EntityUtils.consume(resEntity);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("message", "an error when parsing response from resource server;");
                return map;
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message", "an error when the requests resources server;");
            return map;
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        map.put("message", "unknow error");
        return map;
    }
    
    //用户安装协议
    @RequestMapping("/userInstallationProtocol")
    public String userInstallationProtocol(HttpServletRequest request){
    	usersService.userInstallationProtocol(request);
    	return "messageCenter/userInstallationProtocol";
    }
    
    //必看攻略
    @RequestMapping("/seeStrategy")
    public String seeStrategy(HttpServletRequest request){
    	usersService.seeStrategy(request);
    	return "messageCenter/seeStrategy";
    }
    
    //关于每天积分
    @RequestMapping("/about")
    public String about(HttpServletRequest request){
    	usersService.about(request);
    	return "messageCenter/about";
    }
    
    //合伙人身份说明
    @RequestMapping("/hhrExplain")
    public String hhrExplain(HttpServletRequest request,Integer type ){
    	request.setAttribute("type",type);
    	return "messageCenter/hhrExplain";
    	
    }
  //下载页面
    @RequestMapping("/download")
    public String download(HttpServletRequest request,Integer type){
    	request.setAttribute("type", type);
    	
    	return "shareWeb/download/download";
    }
    @RequestMapping("/downloadSeller")
    public String downloadSeller(HttpServletRequest request,Integer type){
    	return "shareWeb/download/download_seller";
    }
    
    
    //关于每天积分
    @RequestMapping("/notReceivedIdentifyingCode")
    public String notReceivedIdentifyingCode(HttpServletRequest request){
    	usersService.notReceivedIdentifyingCode(request);
    	return "messageCenter/seeStrategy";
    }
    
    @RequestMapping("/getMonitorAccount")
    @ResponseBody
    public Map<String, Object> getMonitorAccount(HttpServletRequest request){
    	return usersService.getMonitorAccount(request);
    }
    
    //验证手机号
    @RequestMapping("/getCheckPhone")
    @ResponseBody
    public Map<String, Object> getCheckPhone(HttpServletRequest request){
    	return usersService.getCheckPhone(request);
    }
    
    
    //判定手机号
    @RequestMapping("/bindingPhone")
    @ResponseBody
    public Map<String, Object> bindingPhone(HttpServletRequest request){
    	return usersService.bindingPhone(request);
    }
    @RequestMapping("/getUsersM")
    @ResponseBody
    public Map<String, Object> getUsersMoney(HttpServletRequest request){
    	return usersService.getUsersMoney(request);
    }
    
    @RequestMapping("/applyWithdrawals")
    @ResponseBody
    public Map<String, Object> applyWithdrawals(HttpServletRequest request){
    	return usersService.applyWithdrawals(request);
    }
    
    //用户提现记录 (未完成)
    @RequestMapping("/getwithdrawalsInfo")
    @ResponseBody
    public Map<String, Object> getwithdrawalsInfo(HttpServletRequest request){
    	return usersService.getwithdrawalsInfo(request);
    }
    //用户提现记录 (已完成)
    @RequestMapping("/getwithdrawalsInfoForPay")
    @ResponseBody
    public Map<String, Object> getwithdrawalsInfoForPay(HttpServletRequest request){
    	return usersService.getwithdrawalsInfoForPay(request);
    }
    
    @RequestMapping("/withdrawals")
    @ResponseBody
    public Map<String, Object> withdrawals(HttpServletRequest request){
    	return usersService.withdrawals(request);
    }
    @RequestMapping("/getDefaultMsg")
    @ResponseBody
    public Map<String, Object> getDefaultMsg(HttpServletRequest request,HttpServletResponse response){
    	return usersService.getDefaultMsg(request, response);
    }
    
    
    //上传日志
    @RequestMapping("/uploadLog")
    @ResponseBody
    public Map<String, Object> uploadLog(HttpServletRequest request,HttpServletResponse response){
    	return usersService.uploadLog(request, response);
    }
    
    //获取用户三个基本信息
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public Map<String, Object> getUserInfo(HttpServletRequest request,HttpServletResponse response){
    	return usersService.getUserInfo(request, response);
    }
    
    
  //获取粉丝列表2
    @RequestMapping("/getFansList2")
    @ResponseBody
    public Map<String, Object> getFansList2(HttpServletRequest request, HttpServletResponse response) {
        return usersService.getFansList2(request, response);
    }
 
    //获取订单四种状态条数s
    @RequestMapping("/getOrderStatusNum")
    @ResponseBody
    Map<String, Object> getOrderStatusNum(HttpServletRequest request, HttpServletResponse response){
    	return usersService.getOrderStatusNum(request, response);
    }
    
  //游戏列表
    @RequestMapping("/getGameList")
    @ResponseBody
    Map<String, Object> getGameList(HttpServletRequest request, HttpServletResponse response){
    	return reGoodsOfLockMallService.getGameList(request, response);
    }
    
  //游戏详情
    @RequestMapping("/getGameListByType")
    @ResponseBody
    Map<String, Object> getGameListByType(HttpServletRequest request, HttpServletResponse response){
    	return reGoodsOfLockMallService.getGameListByType(request, response);
    }
    
  //用户签到页面
    @RequestMapping("/getSignedInfo")
    @ResponseBody
    Map<String,Object> getSignedInfo(HttpServletRequest request, HttpServletResponse response){
		
    	return usersService.signCalc(request, response);
    	
    }
    
    
    //用户签到
    @RequestMapping("/signIn")
    @ResponseBody
    Map<String,Object> signIn(HttpServletRequest request, HttpServletResponse response){
		
    	return usersService.userBySignCalc(request, response);
    	
    }
    
    //获取抽奖信息接口  有剩余抽奖数的
    @RequestMapping("/getPrizeInfo")
    @ResponseBody
    Map<String,Object> getPrizeInfo(HttpServletRequest request, HttpServletResponse response){
		
    	return usersService.getPrizeInfo(request, response);
    	
    }
  //翻牌抽奖接口 点击牌子之后
    @RequestMapping("/openCards")
    @ResponseBody
    Map<String,Object> openCards(HttpServletRequest request, HttpServletResponse response){
		
    	return usersService.openCards_1(request, response);
    	
    }
    
  //抽奖记录
    @RequestMapping("/getRecordList")
    @ResponseBody
    Map<String,Object> getRecordList(HttpServletRequest request, HttpServletResponse response){
		
    	return usersService.getRecordList(request, response);
    	
    }
    //个人中心展示收益的
    @RequestMapping("/profit")
    String profit(Integer id ,HttpServletRequest request, HttpServletResponse response){
    	usersService.profit(id,request, response);
    	return "money/index";
    }
    
    
    //填写银行信息
    @RequestMapping("/webWithdrawals")
    @ResponseBody
    public Map<String,Object> webWithdrawals(HttpServletRequest request, HttpServletResponse response,RedirectAttributes attr){
    	String id = request.getParameter("id");
    	Map<String,Object> withdrawals = usersService.webWithdrawals(Integer.valueOf(id),request,response);
    	 attr.addAttribute("id", withdrawals.get("id"));
    	 
    	return withdrawals;
    }
    //获取所有的验证信息
    @RequestMapping("/doCash")
    @ResponseBody
    String doCash(Integer id,Integer money ,HttpServletRequest request, HttpServletResponse response){
    	usersService.doCash(id,request,response);
    	request.setAttribute("money", money);
    	return "money/webWithdrawals";
    }
  
    @RequestMapping("/getLauncherImg")
    @ResponseBody
    public Map<String,Object> getLauncherImg(HttpServletRequest request, HttpServletResponse response){
		Map<String , Object> status = new HashMap<String,Object>();
    	Map<Integer, Map<String, Object>> ctmap = appPageService.getCashshopType2("", "", 2,request);
    	Map<String , Object> data = new HashMap<String,Object>();
    	data.put("launcherImg", ctmap.get(150).get("image"));
		data.put("countTime", Integer.valueOf(ctmap.get(150).get("remark").toString()));
			 

    	status.put("status", 1);
    	status.put("message", "请求成功");
    	status.put("data", data);
    	
    	return status;
    	
    }
    
    
}
