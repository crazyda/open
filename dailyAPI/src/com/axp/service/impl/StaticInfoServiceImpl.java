package com.axp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.axp.dao.ProvinceEnumDAO;
import com.axp.domain.AdminUser;
import com.axp.domain.AppInformation;
import com.axp.domain.GameActivity;
import com.axp.domain.ProvinceEnum;
import com.axp.domain.Redpaper;
import com.axp.domain.Seller;
import com.axp.domain.TkldPid;
import com.axp.domain.Users;
import com.axp.service.GameActivityService;
import com.axp.service.IMembersService;
import com.axp.service.IStaticInfoService;
import com.axp.service.TkldPidService;
import com.axp.util.CacheUtil;
import com.axp.util.CityUtil;
import com.axp.util.DateUtil;
import com.axp.util.HanyuPinyinHelper;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;
import com.axp.util.StringUtil;

@Service
public class StaticInfoServiceImpl extends BaseServiceImpl<ProvinceEnum>  implements IStaticInfoService {
	@Autowired 
	IMembersService vipService;
	@Autowired
	public GameActivityService gameActivityService;
	
	@Autowired
	private CityUtil cityUtil;
	@Autowired
	private TkldPidService tkldPidService;
	private AppVersion androidVersion;
	private AppVersion iosVersion;
	
	@Autowired
	ProvinceEnumDAO enumDAO;
	@Override
	//运营参数
	public Map<String, Object> getConfig(Parameter parameter) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		statusMap.put("status", 0x01);
		statusMap.put("message", "请求成功");
		statusMap.put("data", getConfigMap(parameter));
		return statusMap;
	}

	@Override
	public Map<String, Object> getZones(HttpServletRequest request, HttpServletResponse response) {
		String xcx = request.getParameter("xcx");
		if(xcx == null){
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			
		}
		
		Map<String,Object> map = new HashMap<String, Object>();
		List<ProvinceEnum> pEnum = new ArrayList<ProvinceEnum>(3280);
		Map<String, List<ProvinceEnum>> zoneAll = CacheUtil.getZoneAll();
		List<ProvinceEnum> hotEnum=new ArrayList<ProvinceEnum>(6);
		if(!zoneAll.containsKey("zoneAll")){
			pEnum=enumDAO.findLevel();
			for (ProvinceEnum temp : pEnum) {
				
				temp.setPinYin(HanyuPinyinHelper.toHanyuPinyin(temp.getName()));
			}
			
			hotEnum = enumDAO.findByIsHot(1);
//			QueryModel model = new QueryModel();
//			model.clearQuery();
//			model.combPreEquals("isValid", true);
//			model.combPreEquals("isHot", 1);
//			hotEnum = dateBaseDAO.findLists(ProvinceEnum.class, model);
			
			for (ProvinceEnum temp : hotEnum) {
				temp.setPinYin(HanyuPinyinHelper.toHanyuPinyin(temp.getName()));
			}
		
			zoneAll.put("zoneAll", pEnum);
			zoneAll.put("hotDataList", hotEnum);
			
		}else{
			pEnum=zoneAll.get("zoneAll");
			hotEnum=zoneAll.get("hotDataList");
		}
			map.put("hotDataList", hotEnum);
			map.put("dataList", pEnum);
			map.put("version","0");
			Map<String, Object> bigMap = new HashMap<>();
			bigMap.put("data", map);
			bigMap.put("status", 0x01);
			bigMap.put("message", "请求成功");

			return bigMap;
		
	}

	
	private Map<String, Object> getConfigMap(Parameter parameter){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		AppVersion newVersion;
		String targetUrl="";
		String os = parameter.getOs();
		String v = parameter.getAppVersion();//前端传的APP版本号
		String app = parameter.getApp();   
		if( "SELLER".equals(app)){
			if("10".equals(v)){
				v="1.7.0";
			}else if("11".equals(v)){
				v="1.8.0";
			}else if("12".equals(v)){
				v="1.9.0";
			}
			
		}
		
		String version = "";
        char[] charArray = v.toCharArray();
      	for (int i = 0; i < charArray.length; i++) {
				if(".".equals(String.valueOf(charArray[i]))){
					continue;
				}
				 version+=charArray[i];
		}
      	
		if(os.equals("IOS")){
			newVersion = iosVersion;
		//	targetUrl= parameter.getBasePath()+"invoke/download";
			targetUrl  = "http://mtjf.518wk.cn:8080/dailyAPI/invoke/users/download";
		}else{
			newVersion = androidVersion;
			//targetUrl= parameter.getBasePath()+"invoke/download";
			targetUrl  = "http://mtjf.518wk.cn:8080/dailyAPI/invoke/users/download";
		}
		
		boolean isCareerPartner=false;//是否是事业合伙人
		boolean isSeller=false;
		String uname="";
		String userId = parameter.getUserId();
		Users user = null;
		Integer uId=0;
		String adminuserId = parameter.getAdminuserId();
		 Integer zoneId = StringUtils.isEmpty(parameter.getZoneId()) ? -1 : Integer.parseInt(parameter.getZoneId());
		if(StringUtils.isNotBlank(userId) && !"-1".equals(userId)){
			uId=Integer.parseInt(userId);
			user = usersDao.findById(uId);
			QueryModel queryModel = new QueryModel();
			if(user!=null){
				if(user.getRealname()!=null){
					uname=user.getRealname();
				}
				int isSignRemaid = user.getIsSignRemaid()==null ?0:user.getIsSignRemaid();
				GameActivity game = gameActivityService.getZoneId(zoneId);
				if(game.getId() != null ){
					
					if(user.getSignCalcTime()!=null){
						if(DateUtil.belongCalendar(DateUtil.transToDate(user.getSignCalcTime()), DateUtil.getDayStart(new Date()), DateUtil.getDayEnd(new Date()))){
							//签到时间不能为空,并且签到时间是今天  不提醒
							dataMap.put("isSigned", false);
						}else{
							//签到时间不能为空,并且签到时间不是今天
							user.setIsSignRemaid(isSignRemaid+1);
							dataMap.put("isSigned", true);
						}
					}else{
						user.setIsSignRemaid(isSignRemaid+1);
						dataMap.put("isSigned", true);
					}
					
					if(isSignRemaid>=3){
						dataMap.put("isSigned", false);
					}
					
					
				}else{
					dataMap.put("isSigned", false);
				}
				
				
				usersDao.saveOrUpdate(user);
				
				queryModel.clearQuery();
				queryModel.combEquals("isValid", 1);
				queryModel.combPreEquals("level",2);
				queryModel.combPreEquals("users.id",user.getId(),"userId");
				List<TkldPid> tkldPidList = (List<TkldPid>) dateBaseDAO.findList(TkldPid.class, queryModel);
				if(tkldPidList.size()>0){
					isCareerPartner=true;
				}
				
			}
			queryModel.clearQuery();
			queryModel.combPreEquals("isValid", true);
			queryModel.combPreEquals("user_id", Integer.parseInt(userId));
			queryModel.setOrder("id desc");
			List<Seller> slist = dateBaseDAO.findLists(Seller.class, queryModel);
			if (slist.size()>0 && StringUtils.isNotBlank(adminuserId)) {
				AdminUser adminUser = adminUserDao.findById(Integer.parseInt(adminuserId));
				isSeller = true;
				dataMap.put("userScoreNum", adminUser.getScore()==null?String.valueOf(0):String.valueOf(adminUser.getScore()));
			}
			
			
		}
		
		dataMap.put("maxscore", 3);// 滑屏、解锁、品牌官网随机生成的最大积分
		dataMap.put("minscore", 1);// 滑屏、解锁、品牌官网随机生成的最小积分
		dataMap.put("toadyscore", 100);// 每天可获得的最大积分
		dataMap.put("screenAdvertLoadTime", 30 * 60 * 1000);// 滑屏广告加载间隔时间，单位：毫秒	
		dataMap.put("vipLevels", vipService.getVipConfig());//会员的可选级别	[{vipLevel},{ vipLevel }]
		if(Integer.valueOf(version)>106){
			dataMap.put("homeShareContent",getShareContent("HOME",parameter.getBasePath(),targetUrl,user,os));// 首页分享内容
			dataMap.put("shoppingCarShareContent", getShareContent("SHOPPINGCAR",parameter.getBasePath(),targetUrl,user,os));// 购物车分享内容
			dataMap.put("goodsShareContent", getShareContent("GOODS",parameter.getBasePath(),targetUrl,user,os));// 商品分享
			dataMap.put("sellerShareContent", getShareContent("SELLER",parameter.getBasePath(),targetUrl,user,os));// 商品分享
			dataMap.put("personalCenterShareContent", getShareContent("PERSONAL",parameter.getBasePath(),uname,user,os));// 商品分享
			dataMap.put("brandShareContent", getShareContent("BRAND",parameter.getBasePath(),uname,user,os));//品牌分享
		}else{
			dataMap.put("homeShareContent",getShareContent("HOME",parameter.getBasePath(),targetUrl,user));// 首页分享内容
			dataMap.put("shoppingCarShareContent", getShareContent("SHOPPINGCAR",parameter.getBasePath(),targetUrl,user));// 购物车分享内容
			dataMap.put("goodsShareContent", getShareContent("GOODS",parameter.getBasePath(),targetUrl,user));// 商品分享
			dataMap.put("sellerShareContent", getShareContent("SELLER",parameter.getBasePath(),targetUrl,user));// 商品分享
			dataMap.put("personalCenterShareContent", getShareContent("PERSONAL",parameter.getBasePath(),uname,user));// 商品分享
			dataMap.put("brandShareContent", getShareContent("BRAND",parameter.getBasePath(),uname,user));//品牌分享
		}
		
		
		
		
		
		
		dataMap.put("newVersion", newVersion.getVersion());// 最新版本	
		dataMap.put("newVersionMessage", newVersion.getMessage());// 最版本内容
		dataMap.put("newVersionDownload", newVersion.getUrl());// 最版本下载链接apk
		dataMap.put("download",newVersion.getDounload());// 最版本下载链接
//		dataMap.put("redPaperProbability", getRedPaperProbability());// 红包获得概率
		dataMap.put("redPaperProbability",10);// 红包获得概率
		dataMap.put("redPaperMode","1");// 红包模式  0为红包抵扣（旧） 1为现金红包（新）
		dataMap.put("sNewVersion", newVersion.getsVersion());// 最新版本	
		dataMap.put("sNewVersionMessage", newVersion.getsMessage());// 最版本内容
		dataMap.put("sNewVersionDownload", newVersion.getsUrl());// 最版本下载链接apk
		dataMap.put("serviceTime",new Date().getTime());
		dataMap.put("isSeller", isSeller);
		dataMap.put("isCareerPartner", isCareerPartner);//是否事业合伙人
		
		Map<String, Object> shareContent = getShareContent("HOME",parameter.getBasePath(),targetUrl,user,os);
		
		//=============================ZL=========================================//
		
		
      	AppInformation appInformation = null;
		QueryModel model = new QueryModel();
      	if (StringUtils.isNotBlank(app) && "SELLER".equals(app)) {
    		model.combPreEquals("isValid", true);
    		model.combPreEquals("appType", 2);
    		
		}else{
			
			//==========查找事业合伙人============//
			
			//事业合伙人
			//TkldPid tkldPid=tkldPidService.findCareerPartner(uId, zoneId);
			//事业合伙人对应的用户
			//Users users = tkldPid.getUsers();
			//==========查找事业合伙人============//
			
			model.clearQuery();
			model.combPreEquals("isValid",true);
			model.combPreEquals("appType",1);
		}
      	
      	dataMap.put("agreementUri", "http://jifen.aixiaoping.cn:8080/dailyAPI/invoke/adminUser/agreement");
      	
      	dataMap.put("isCheck","");//http://www.aixiaoping.com
		List<AppInformation> list = dateBaseDAO.findLists(AppInformation.class, model);
		if (list.size()>0) {
			appInformation = list.get(0);
			String appVersion = appInformation.getAppVersion();
			String version1 = "";//数据库版本号
	        char[] charArray1 = appVersion.toCharArray();
	      	for (int i = 0; i < charArray1.length; i++) {
					if(".".equals(String.valueOf(charArray1[i]))){
						continue;
					}
					version1+=charArray1[i];
			}
	      	List<String> list1 =new ArrayList<String>();
	      	
			if (Integer.valueOf(version) < Integer.valueOf(version1)) {//判断版本号大小
				
				dataMap.put("hasNewVersion", "1");//前端版本号大于数据库版本号，说明有版本更新
				dataMap.put("newVersionDownload", appInformation.getADirectUrl());// 最版本下载链接apk
				List<String> s=new ArrayList<>();
				JSONArray arr=JSONArray.parseArray(appInformation.getINewVersionContents());
				if (arr!=null) {
					for (int i = 0; i < arr.size(); i++) {
						s.add(arr.getString(i));
					}
					dataMap.put("newVersionContents",s);//版本更新内容描述
				}else{
					dataMap.put("newVersionContents", list1);
				}
				
				dataMap.put("newVersion", appVersion);//更新版本号
			}else{
				
				dataMap.put("hasNewVersion", "0");
				dataMap.put("newVersionContents", list1);//无版本更新内容描述
				dataMap.put("newVersion", "");//无更新版本号
				
			}
		}
		
		return dataMap;
	}
	
	private Map<String, Object> getShareContent(String type,String basePath,String targetUrl,Users users){
		Map<String, Object> map = new HashMap<String, Object>();
		String uname="";
		
		if(type.equals("HOME")){
			JSONObject ob = new JSONObject();
			JSONObject data = new JSONObject();
			data.put("pageIndex", "1");
			ob.put("data", data);
			ob.put("os", "ANDROID");
			ob.put("appVersion", "89");
			ob.put("dip", "250");
			map.put("iconUrl", StringUtil.shareImg);
			map.put("title", "各种优惠活动玩疯了！");
			map.put("targetUrl", "http://ldwd.gzyunjifen.com/Pdd/Index/storeIndex?admin_id=7");
			map.put("content", "每天积分每天各种优惠活动，购买超值划算商品，还可以赚钱，惊喜不断！");
		}else if(type.equals("SHOPPINGCAR")){
			map.put("iconUrl", StringUtil.shareImg);
			map.put("title", "每天积分");
			map.put("targetUrl", "http://ldwd.gzyunjifen.com/Pdd/Index/storeIndex?admin_id=7");
			map.put("content", "爱生活！每天积分");
		}else if(type.equals("GOODS")){
			map.put("iconUrl", basePath+"images/icon_app.png");
			map.put("title", "每天积分");
			map.put("targetUrl", "http://ldwd.gzyunjifen.com/Pdd/Index/storeIndex?admin_id=7");
			map.put("content", "爱生活！每天积分");
		}else if(type.equals("SELLER")){
			map.put("iconUrl", basePath+"images/icon_app.png");
			map.put("title", "每天积分商家店铺");
			map.put("targetUrl", "http://ldwd.gzyunjifen.com/Pdd/Index/storeIndex?admin_id=7");
			map.put("content", "店铺详情");
		}else if(type.equals("PERSONAL")){
			map.put("iconUrl", StringUtil.shareImg);
			map.put("title", "好友"+uname+"邀你共享福利");
			map.put("targetUrl", "http://ldwd.gzyunjifen.com/Pdd/Index/storeIndex?admin_id=7");
			map.put("content", "每天积分每天各种优惠活动，购买超值划算商品，还可以获利，赶快加入我们吧！");
		}else if(type.equals("BRAND")){
			map.put("iconUrl", StringUtil.shareImg);
			map.put("title", "千万购物券  最低1折起");
			map.put("targetUrl", "http://www.518wtk.com/index.php?s=/NewIndex/brand&pid=");
			map.put("content", "天猫淘宝内容购物优惠券领取中，每天万款商品在等你！");
		}
		return map;
	}
	private Map<String, Object> getShareContent(String type,String basePath,String targetUrl,Users users,String os){
		Map<String, Object> map = new HashMap<String, Object>();
		String uname="";
		if(users!=null){
			uname=users.getRealname()==null?"每天积分好友":users.getRealname();
			map.put("iconUrl", StringUtil.shareImg);
			map.put("title", "各种优惠活动玩疯了！");
			map.put("targetUrl", "http://mtjf.518wk.cn:8080/dailyAPI/invoke/users/download");
			map.put("content", "每天积分每天各种优惠活动，购买超值划算商品，还可以赚钱，惊喜不断！");
			map.put("userName","gh_bdbfd0767739");
			map.put("path", "/pages/index/index?invitecode="+users.getMycode());
		}else{
			map.put("iconUrl", StringUtil.shareImg);
			map.put("title","各种优惠活动玩疯了！");
			map.put("targetUrl","http://mtjf.518wk.cn:8080/dailyAPI/invoke/users/download");
			map.put("content","每天积分每天各种优惠活动，购买超值划算商品，还可以赚钱，惊喜不断！");
			map.put("userName","gh_bdbfd0767739");
			map.put("path","/pages/index/index");
		}
		
		
		return map;
	}
	
	private List<Map<String, Object>>  getAllZones(int zoneid){
		
		List<Map<String, Object>> dataList = new ArrayList<>();
		try{
		List<ProvinceEnum> plist =new ArrayList<ProvinceEnum> ();
		if(zoneid>0){
			plist = provinceEnumDao.getListByParentCity(zoneid);
		}else{
			plist = provinceEnumDao.getListByLevel(2);
		}
		for(ProvinceEnum zone : plist){
			HashMap<String, Object> hashMapTmp = new HashMap<String, Object>();
			// 地区id
			hashMapTmp.put("zoneId", zone.getId() + "");
			// 地区名
			hashMapTmp.put("name", zone.getName());
			//地区等级
			hashMapTmp.put("level",zone.getLevel()+"");
			//地区名拼音首字母
			hashMapTmp.put("englishchar", zone.getEnglishChar());
			
			hashMapTmp.put("parentZoneId", zone.getProvinceEnum().getId()==null?"-1":zone.getProvinceEnum().getId()+"");
			
			dataList.add(hashMapTmp);
			
		}
		}catch(Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return dataList;
	}
	
	private Double getRedPaperProbability(){
		Double probability = 0d;
		QueryModel model = new QueryModel().setOrder("id desc");
		model.combPreEquals("isValid", Boolean.TRUE);
		model.combPreEquals("status", Boolean.TRUE);
		List<Redpaper> list = dateBaseDAO.findPageList(Redpaper.class, model, 0, 1);
		if (list.size() > 0) {
			probability = list.get(0).getProbability();
		}
		return probability;
	}
	
	@PostConstruct
	public void init() {
		if(iosVersion==null){
			iosVersion = new AppVersion();
		}
		if(androidVersion==null){
			androidVersion = new AppVersion();
		}
	}

	public class AppVersion{
		
		String version = "91";
		String message = "1:合伙人推广；2：用户开店，3：，其他已知问题";
		String url = "http://jifen.aixiaoping.cn:8080/jupinhuiRes/app/LoveSmallScreen-5.11.0.apk";
		String dounload = "http://www.anzhi.com/pkg/b8c1_com.weslide.jupinhui.html";// da 安智页面
		
		String sVersion="7";
		String sMessage="1：添加推广优惠券功能；2，添加新手指引  3：修复已知问题";
		String sUrl = "http://www.anzhi.com/pkg/b8c1_com.weslide.jupinhui.html";//da 安智页面
		
		public String getsVersion() {
			return sVersion;
		}
		public void setsVersion(String sVersion) {
			this.sVersion = sVersion;
		}
		public String getsMessage() {
			return sMessage;
		}
		public void setsMessage(String sMessage) {
			this.sMessage = sMessage;
		}
		public String getsUrl() {
			return sUrl;
		}
		public void setsUrl(String sUrl) {
			this.sUrl = sUrl;
		}
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getDounload() {
			return dounload;
		}
		public void setDounload(String dounload) {
			this.dounload = dounload;
		}
	}

	@Override
	public Map<String, Object> getIsShow(Parameter parameter) {
		
		Integer version = Integer.parseInt(parameter.getZoneId());
		String isshow="0";
		ProvinceEnum zone =provinceEnumDao.getCurrentCity(version);
		
			Map<String,Object> map = new HashMap<String, Object>();

			map.put("showChange", isshow);
			Map<String, Object> bigMap = new HashMap<>();
			bigMap.put("data", map);
			bigMap.put("status", 0x01);
			bigMap.put("message", "请求成功");
			return bigMap;
		
	}

}
