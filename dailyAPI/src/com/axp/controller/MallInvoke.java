package com.axp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.axp.dao.DateBaseDAO;
import com.axp.domain.ScoreMark;
import com.axp.domain.TkldPid;
import com.axp.service.UserLoginRecordService;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;

@Controller
@RequestMapping("invoke/mall")
public class MallInvoke extends BaseController {
	@Resource
	UserLoginRecordService userLoginRecordService;
	@Autowired 
	DateBaseDAO dateBaseDao;
	
   


  //获取首页内容
    @RequestMapping("/getHomeNew")
    @ResponseBody
    public Map<String, Object> getHomeNew(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map;
        try {
            Parameter parameter = ParameterUtil.getParameter(request);
            if (parameter == null) {
                return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
            }
         
            Integer userId = StringUtils.isEmpty(parameter.getUserId()) ? null : Integer.parseInt(parameter.getUserId());
            Integer zoneId = StringUtils.isEmpty(parameter.getZoneId()) ? -1 : Integer.parseInt(parameter.getZoneId());
            Double lat = StringUtils.isEmpty(parameter.getLat()) ? null : Double.parseDouble(parameter.getLat());
            Double lng = StringUtils.isEmpty(parameter.getLng()) ? null : Double.parseDouble(parameter.getLng());
            String dip = parameter.getDip();
            String os = parameter.getOs();
            String v = parameter.getAppVersion();
          
        	//==========访问记录===ZL=======//
            userLoginRecordService.commitRecord(request);
            
            String version = "";
            char[] charArray = v.toCharArray();
        	for (int i = 0; i < charArray.length; i++) {
				if(".".equals(String.valueOf(charArray[i]))){
					continue;
				}
				   version+=charArray[i];
			}
        	
        	if(Integer.valueOf(version)>=104){
        		
        		map = appPageService.getHomeNew5142(userId, zoneId, lat, lng, os, null, version, dip, null, null, request, null,null,0);
        	}else{
        		map = appPageService.getHomeNew104(userId, zoneId, lat, lng, os, null, v, dip, null, null, request, null,null,0);
        	}
    		
        }
            catch (Exception e) {
            map = JsonResponseUtil.getJson(-0x02, "数据处理异常");
            e.printStackTrace();
        }
        return map;
    }
    
    
    /**
     * 获取99特惠商城的商品列表；
     * 排序方式：销量倒序排序；
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/preferential99")
    @ResponseBody
    public Map<String, Object> getPreferential99(HttpServletRequest request, HttpServletResponse response) {

    	
        return appPageService.getPreferential99(request, response);
    }

    /**
     * 获取商品详情
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getGoods")
    @ResponseBody
    public Map<String, Object> getGoods(HttpServletRequest request, HttpServletResponse response) {
        return MallList.getGoods(request, response);
    }

    /**
     * 获取商品列表
     * @param request
     * @param response
     * @return
     */

    @RequestMapping("/getGoodsList")
    @ResponseBody
    public Map<String, Object> getGoodsList(HttpServletRequest request, HttpServletResponse response) {
        //独立商城
        return MallList.getGoodsList(request, response,false);
    }
    
    /**
     * 获取商品列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getGoodsListForWeb")
    @ResponseBody
    public Map<String, Object> getGoodsListForWeb(HttpServletRequest request, HttpServletResponse response) {
        //独立商城
        return MallList.getGoodsList(request, response,true);
    }

    //收藏店铺
    @RequestMapping("/shopConcern")
    @ResponseBody
    public Map<String, Object> getShopConcern(HttpServletRequest request, HttpServletResponse response) {
        return MallList.getShopConcern(request, response);
    }

    //获取关注商家列表
    @RequestMapping("/getConcernSellerList")
    @ResponseBody
    public Map<String, Object> getConcernSellerList(HttpServletRequest request, HttpServletResponse response) {
        return MallList.getConcernSellerList(request, response);
    }

    //关注商品
    @RequestMapping("/goodsConcern")
    @ResponseBody
    public Map<String, Object> getGoodsConcern(HttpServletRequest request, HttpServletResponse response) {
        return MallList.getGoodsConcern(request, response);
    }

    //获取关注商品列表
    @RequestMapping("/getConcernGoodsList")
    @ResponseBody
    public Map<String, Object> getConcernGoodsList(HttpServletRequest request, HttpServletResponse response) {
        return MallList.getConcernGoodsList(request, response);
    }

    //会员免单商城
    @RequestMapping("/freeMall")
    @ResponseBody
    public Map<String, Object> freeMall(HttpServletRequest request, HttpServletResponse response) {
        return MallList.freeMall(request, response);

    }

    //分类
    @RequestMapping("/scoreExchangeMall")
    @ResponseBody
    public Map<String, Object> scoreExchangeMall(HttpServletRequest request, HttpServletResponse response) {
//    	Parameter parameter = ParameterUtil.getParameter(request);
//    	String v = parameter.getAppVersion();
//    	String version = "";
//        char[] charArray = v.toCharArray();
//     	for (int i = 0; i < charArray.length; i++) {
//			if(".".equals(String.valueOf(charArray[i]))){
//				continue;
//			}
//			version+=charArray[i];
//		}
//     	
     	return appPageService.scoreExchangeMall2(request);
		
       
    }

    //本地特产
    @RequestMapping("/specialLocalProduct")
    @ResponseBody
    public Map<String, Object> specialLocalProduct(HttpServletRequest request, HttpServletResponse response) {
        return appPageService.specialLocalProduct(request, response);

    }

    //秒杀商城
    @RequestMapping("/secondKillMall")
    @ResponseBody
    public Map<String, Object> secondKillMall(HttpServletRequest request,
                                              HttpServletResponse response) {
        return appPageService.secondKillMall(request, response);

    }

    //网页URL
    @RequestMapping("/goodsDetail")
    public String goodsDetail(HttpServletRequest request, HttpServletResponse response) {
        appPageService.getGoodsDetail(request, response);
        return "shareWeb/mall/goodsDetail";
    }
  
    
    //获取拼团商品列表（小程序）
    @RequestMapping("/getTeamGoodsList")
    @ResponseBody
    public Map<String, Object> getTeamGoodsList(HttpServletRequest request, HttpServletResponse response,Integer zoneId,Integer typeId,Integer pageIndex,String search){
    	
    	return appPageService.getTeamGoodsList(request, response, zoneId,typeId,pageIndex,search);
    	
    }
    
    //获取轮播广告（小程序）
    @RequestMapping("/getSlidesOfBannser")
    @ResponseBody
    public Map<String, Object> getSlidesOfBannser(HttpServletRequest request, HttpServletResponse response,Integer adminuserId){
    	return appPageService.getSlidesOfBannser(request, response, adminuserId);
    }
    
    //获取拼团分类（小程序）
    @RequestMapping("/getTeamGoodsOfType")
    @ResponseBody
    public Map<String,Object> getTeamGoodsOfType(HttpServletRequest request, HttpServletResponse response){
    	return appPageService.getTeamGoodsOfType(request);
    }
 
    //获取拼团热门关键字
    @RequestMapping("/getGoodsKeywords")
    @ResponseBody
    public Map<String,Object> getGoodsKeywords(HttpServletRequest request, HttpServletResponse response){
    	return appPageService.getGoodsKeywords(request,response);
    }
    
    
    
    //上级合伙人
    @RequestMapping("/findPidUser")
    @ResponseBody
    public Map<String, Object> findPidUser(HttpServletRequest request,HttpServletResponse response){
    	Map<String, Object> statusMap = new HashMap<String, Object>();
    	Map<String, Object> data = new HashMap<String, Object>();
    	try {
    		Parameter parameter = ParameterUtil.getParameter(request);
        	String userId = parameter.getUserId();
			String pid=appPageService.findPid(Integer.parseInt(userId),"",-1);
			QueryModel queryModel = new QueryModel();
			queryModel.combPreEquals("isValid", true);
			queryModel.combPreEquals("pid", pid);
			List<TkldPid> tklist = dateBaseDao.findLists(TkldPid.class, queryModel);
			TkldPid tkldPid = null;
			Integer pidByUserId = null;
			if (tklist.size()>0) {
				tkldPid = tklist.get(0);
				if (tkldPid.getUsers()!=null) {
					pidByUserId = tkldPid.getUsers().getId();
				}
				
			}
			if (pidByUserId==null) {
				pidByUserId=48995;
			}
			data.put("pidByUserId", pidByUserId);
			statusMap.put("data", data);
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return statusMap;
    }
    /**
     * 获取所有商城 商品信息, 拼团,特色,秒杀,积分,优惠(有无优惠券) ,
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getSellerGoodsList")
    @ResponseBody
    public Map<String,Object> getSellerGoodsList(HttpServletRequest request,HttpServletResponse response){
		return MallList.getSellerGoodsList(request, response);
		
    	
    }
    /**
     * 获取爱小屏的积分商品
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getAxpScoreGoods")
    @ResponseBody
    public Map<String,Object> getAxpScoreGoods(HttpServletRequest request,HttpServletResponse response){
		return MallList.getAxpScoreGoods(request, response);
    	
    }
    
    public String goodsDetailWeb(HttpServletRequest request,HttpServletResponse response){
		
    	
    	
    	
    	return "/goods/goodsDetail";
    	
    }
    
    
    
}
