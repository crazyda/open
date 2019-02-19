package com.axp.service.impl;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.axp.dao.CashshopGoodsLableDAO;
import com.axp.dao.CashshopTimesDAO;
import com.axp.dao.CashshopTypeDAO;
import com.axp.dao.CityPidDAO;
import com.axp.dao.CommodityTypeDAO;
import com.axp.dao.DateBaseDAO;
import com.axp.dao.IReGoodsOfMemberMallDao;
import com.axp.dao.ReBaseGoodsDAO;
import com.axp.dao.ReGoodsDetailsDAO;
import com.axp.dao.SellerDAO;
import com.axp.dao.SlidesDAO;
import com.axp.dao.ThePartnerDAO;
import com.axp.dao.UsersPidDAO;
import com.axp.domain.AdminuserZoneTaoke;
import com.axp.domain.CashmoneyRecord;
import com.axp.domain.CashshopGoodsLable;
import com.axp.domain.CashshopTimes;
import com.axp.domain.CashshopType;
import com.axp.domain.CommodityType;
import com.axp.domain.GameActivity;
import com.axp.domain.MessageCenter;
import com.axp.domain.News;
import com.axp.domain.PartnerAdminuserPidDistribute;
import com.axp.domain.ProvinceEnum;
import com.axp.domain.ReBaseGoods;
import com.axp.domain.ReGoodsDetails;
import com.axp.domain.ReGoodsOfBase;
import com.axp.domain.ReGoodsOfLocalSpecialtyMall;
import com.axp.domain.ReGoodsOfMemberMall;
import com.axp.domain.ReGoodsOfSellerMall;
import com.axp.domain.ReGoodsOfTeamMall;
import com.axp.domain.ReGoodsSnapshot;
import com.axp.domain.ReGoodsorderItem;
import com.axp.domain.SeLive;
import com.axp.domain.Seller;
import com.axp.domain.SellerAccountNumber;
import com.axp.domain.SellerMainPage;
import com.axp.domain.Slides;
import com.axp.domain.TkldPid;
import com.axp.domain.Users;
import com.axp.service.AdminuserZoneTaokeService;
import com.axp.service.GameActivityService;
import com.axp.service.IAppPageService;
import com.axp.service.IMallListService;
import com.axp.service.IReGoodsOfBaseService;
import com.axp.service.IReGoodsOfLockMallService;
import com.axp.service.IReGoodsOfSellerMallService;
import com.axp.service.ISellerService;
import com.axp.service.IUsersService;
import com.axp.util.CacheUtil;
import com.axp.util.CalcUtil;
import com.axp.util.CityUtil;
import com.axp.util.JsonResponseUtil;
import com.axp.util.MD5Util;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;
import com.axp.util.StringUtil;
import com.axp.util.UrlUtil;

@Service
public class AppPageServiceImpl extends BaseServiceImpl<Users> implements
		IAppPageService {
	@Autowired
	public SlidesDAO slidesDao;
	@Autowired
	public SellerDAO sellerDao;
	@Autowired
	public CommodityTypeDAO commodityTypeDao;
	@Autowired
	public CashshopTimesDAO cashshopTimesDao;
	@Autowired
	public CashshopTypeDAO cashshopTypeDao;
	@Autowired
	public CashshopGoodsLableDAO cashshopGoodsLableDao;
	@Autowired
	public DateBaseDAO datebaseDao;
	@Autowired
	public IReGoodsOfMemberMallDao memberMallDao;
	@Autowired
	public ReGoodsDetailsDAO detailsDAO;
	@Autowired
	public IReGoodsOfBaseService reGoodsOfBaseService;

	@Autowired
	public AdminuserZoneTaokeService adminuserZoneTaokeService;
	@Autowired
	public ThePartnerDAO thePartnerDao;
	@Autowired
	public UsersPidDAO usersPidDao;
	@Autowired
	public CityPidDAO cityPidDao;
	@Autowired
	public ReBaseGoodsDAO reBaseGoodsDAO;
	@Autowired
	public IMallListService mallListService;
	@Autowired
	public IReGoodsOfSellerMallService reGoodsOfSellerMallService;
	@Autowired
	private ISellerService sellerService;
	@Autowired
	public IReGoodsOfLockMallService reGoodsOfLockMallService;
	@Autowired
	private GameActivityService gameActivityService;
	@Autowired
	private IUsersService usersService;
	
	
	public String path = "http://jifen.aixiaoping.cn:8080/dailyRes";
	public String RESOURCE_LOCAL_URL = "http://jifen.aixiaoping.cn:8080/dailyRes/";
	//public final String TEST_RESOURCE_LOCAL_URL="http://27.54.226.210:8080/aixiaopingRes/";

	@Override
	public Map<String, Object> getHome(Integer userid, Integer zoneid,
			Double lat, Double lng, String os, String channelId,
			String appVersion, String dip, String machineCode, Integer type,
			HttpServletRequest request) {
		RESOURCE_LOCAL_URL = request.getServletContext()
				.getAttribute("RESOURCE_LOCAL_URL").toString();
		Map<String, Object> statusMap = new HashMap<String, Object>();

		
		
		String pid="";
		String uri=StringUtil.TAOKEURI;
		
		if(userid!=null && userid>0){
			Users user = usersDao.findById(userid);
			pid = getParentUsersPidByUsers(user);
		}else{
			userid=-1;
		}	


		if ("80".equals(appVersion) || "5.4.5".equals(appVersion)) {

			if (userid != null && userid > 0) {
				Users user = usersDao.findById(userid);
				pid = getParentUsersPidByUsers(user);

			}
			if (StringUtils.isBlank(pid)) {

				pid = getCityPidByZoneId(zoneid);

			}

		}

		if(StringUtils.isBlank(pid) && zoneid!=null){
			AdminuserZoneTaoke azk = getCityTokePidByZoneId(zoneid);
			pid= azk.getBak_uri();
			
		}
		if(StringUtils.isBlank(pid)){
			pid=StringUtil.TAOKEPID;
		}
		
		Map<Integer, Map<String,Object>> ctmap=getCashshopType(pid,uri);

		statusMap.put("status", 0x01);
		statusMap.put("message", "请求成功");

		if (StringUtils.isNotBlank(pid)) {// 有pid
			statusMap.put("data", getDataForPid(type, zoneid, appVersion, pid));
		} else {// 无pid
			statusMap.put("data", getData(type, zoneid, appVersion));
		}

		
		statusMap.put("data", getDataNew(userid,type,zoneid,appVersion,ctmap,os,appVersion,pid));

		return statusMap;
	}

	public String getCityPidByZoneId(Integer zoneId) {
		List<AdminuserZoneTaoke> papdlist = adminuserZoneTaokeDao.queryAll();
		String pid = "mm_111685502_17728608_64252042";
		Map<Integer, String> userspid = new HashMap<Integer, String>();
		if (papdlist != null && papdlist.size() > 0) {
			for (AdminuserZoneTaoke papd : papdlist) {
				userspid.put(papd.getProvinceEnum().getId(), papd.getBak_uri());
			}
		}
		if (StringUtils.isNotBlank(userspid.get(zoneId))) {
			pid = userspid.get(zoneId);
		} else {
			ProvinceEnum zone = provinceEnumDao.getCurrentCity(zoneId);
			pid = userspid.get(zone.getId());
		}
		return pid;

	}

	public AdminuserZoneTaoke getCityTokePidByZoneId(Integer zoneId) {
		
		List<AdminuserZoneTaoke> papdlist = adminuserZoneTaokeDao
				.queryListByParameter("provinceEnum.id", zoneId);
		
		ProvinceEnum zone = provinceEnumDao.getCurrentCity(zoneId);
		
		
		List<AdminuserZoneTaoke> zlist = adminuserZoneTaokeDao
				.queryListByParameter("provinceEnum.id", zone.getId());  
		String pid = "mm_111685502_17728608_64252042";
		AdminuserZoneTaoke azk = null;
		if (papdlist != null && papdlist.size() > 0) {
			azk = papdlist.get(0);
		} else {
			if (zlist != null && zlist.size() > 0) {
				azk = zlist.get(0);
			} else {
				azk = new AdminuserZoneTaoke();
				azk.setBak_uri(pid);
				azk.setUri(StringUtil.TAOKEURI);
			}

		}
		return azk;

	}
	
	
	

	public String getParentUsersPidByUsers(Users users) {
		String pid = "";
		Map<Integer, String> userspid = new HashMap<Integer, String>();
		//找到所有可用的合伙人分配对象
		List<PartnerAdminuserPidDistribute> papdlist = usersPidDao
				.queryAllByIsValid2();
		
		if (papdlist != null && papdlist.size() > 0) {
			for (PartnerAdminuserPidDistribute papd : papdlist) {
				if (papd.getCityAdminuserPidDistribute() != null) {
					userspid.put(papd.getUsers().getId(), papd
							.getCityAdminuserPidDistribute()
							.getAdminuserTaokePid().getPid());
				}
			}

		}

		if (userspid.get(users.getId()) != null) {// 用户本身是城市合伙人
			pid = userspid.get(users.getId());
		} else {		//递归点
			pid = getPid(users, userspid);
		}
		return pid;
	}

	// 通过递归方式查找商家用户的pid；找到为止，不限制层级
	public String getPid(Users users, Map<Integer, String> userspid) {
		String pid = "";
		if (users.getInvitecode() != null) {
			List<Users> parentUser3 = usersDao.findUsersByInvitecode(users
					.getInvitecode());

			if (parentUser3 != null && parentUser3.size() > 0) {// 上三级
				Users pUsers3 = parentUser3.get(0);
				if (userspid.get(pUsers3.getId()) != null) {
					pid = userspid.get(pUsers3.getId());
					return pid;
				} else {
					//循环调用getPid()
					pid = getPid(pUsers3, userspid);

				}
			}
		}

		return pid;
	}

	//积分列表
	@Override
	public Map<String, Object> scoreExchangeMall(HttpServletRequest request) {
		RESOURCE_LOCAL_URL = request.getServletContext()
				.getAttribute("RESOURCE_LOCAL_URL").toString();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("scoreGoodsClassifys", getScoreGoodsClassifys());// 积分商品分类（标签）
		map.put("goodsClassifys", getGoodsClassifys());// 商品分类
		map.put("concentration", getConcentration(request));// 精选页面图文

		Map<String, Object> statusMap = new HashMap<String, Object>();
		statusMap.put("status", 0x01);
		statusMap.put("message", "请求成功");
		statusMap.put("data", map);

		return statusMap;
	}
	
	
	//新积分列表
	@Override
	public Map<String, Object> scoreExchangeMall2(HttpServletRequest request) {
		RESOURCE_LOCAL_URL = request.getServletContext()
				.getAttribute("RESOURCE_LOCAL_URL").toString();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsClassifys", getGoodsClassifys());// 商品分类
		map.put("concentration", getConcentration(request));// 精选页面图文

		Map<String, Object> statusMap = new HashMap<String, Object>();
		statusMap.put("status", 0x01);
		statusMap.put("message", "请求成功");
		statusMap.put("data", map);

		return statusMap;
	}
	

	// 久久特惠
	@Override
	public Map<String, Object> getPreferential99(HttpServletRequest request,
			HttpServletResponse response) {
		RESOURCE_LOCAL_URL = request.getServletContext()
				.getAttribute("RESOURCE_LOCAL_URL").toString();
		Map<String, Object> statusMap = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		QueryModel model = new QueryModel().setOrder("salesVolume desc");
		model.combCondition("addedTime < now()");
		model.combCondition("shelvesTime > now()");
		model.combPreEquals("isValid", true);
		model.combPreEquals("isChecked", true);
		List<ReBaseGoods> goods = datebaseDao.findPageList(
				ReGoodsOfMemberMall.class, model, 0, 8);
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (ReBaseGoods r : goods) {
			// 为多图添加前序
			String covers = r.getSnapshotGoods().getCoverPic();
			if (StringUtil.hasLength(covers) && covers.startsWith("[")) {
				JSONArray array = JSONArray.parseArray(covers);
				if (array.size() > 0) {
					covers = RESOURCE_LOCAL_URL
							+ array.getJSONObject(0).getString("imgUrl");
				}
			} else {
				covers = "";
			}
			Map<String, Object> goodMap = new HashMap<>();
			// 商品快照的id值
			goodMap.put("snapshotGoodsId", r.getSnapshotGoods().getId());
			// 对应的基础商品的商品编号；
			goodMap.put("sign", r.getSnapshotGoods().getSign());
			// 商品名称
			goodMap.put("name", r.getSnapshotGoods().getName());
			// 商品的封面图片
			goodMap.put("coverPic", covers);
			// 商品对应的商家id值
			Seller seller = r.getSnapshotGoods().getSeller();
			seller.setLogo(StringUtil.hasLength(seller.getLogo()) ? RESOURCE_LOCAL_URL
					+ seller.getLogo()
					: "");
			goodMap.put("seller", seller);
			// 商品显示价格，即原价；
			goodMap.put("costPrice", r.getDisplayPrice());
			// 商品售价（这个售价是所有规格中价格最小的那个售价）
			goodMap.put("price", r.getPrice());
			goodMap.put("score", r.getScore());
			goodMap.put("cashpoint", r.getRedPaper());
			goodMap.put("expressTactics", r.getTransportationName());
			goodMap.put("salesVolume", r.getSalesVolume());
			goodMap.put("mallType", ReGoodsOfBase.memberMall);
			if (r.getPrice() != 0 && r.getDisplayPrice() != 0) {
				double b = CalcUtil.div(r.getPrice(), r.getDisplayPrice(), 2);
				goodMap.put("discount", CalcUtil.mul(b, 10, 2) + "折");// 商品折扣
			}
			goodMap.put(
					"goodsId",
					ReBaseGoods.getMallType(ReGoodsOfBase.memberMall)
							+ r.getId());
			mapList.add(goodMap);
		}
		map.put("headBanners", getHeadBanner(Slides.NINENINE1));// 头部轮播图
		map.put("freeGoods", mapList);// 免单商品
		map.put("becomeVipBanner", getHeadBanner(Slides.NINENINE2));// 成为会员横幅广告
		statusMap.put("status", 0x01);
		statusMap.put("message", "请求成功");
		statusMap.put("data", map);
		return statusMap;
	}

	// 本地特产
	@Override
	public Map<String, Object> specialLocalProduct(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		String basePath = request.getServletContext()
				.getAttribute("RESOURCE_LOCAL_URL").toString();

		if (parameter == null) {// 错误的参数；
			return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
		}

		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("headBanners", getHeadBanner(Slides.SPECIALITY));

		Map<String, Object> bigDataMap = new HashMap<>();
		bigDataMap.put("data", dataMap);
		bigDataMap.put("status", 0x01);
		bigDataMap.put("message", "请求成功");
		return bigDataMap;
	}

	private Map<String, Object> getData(Integer type, Integer zoneId,
			String appVersion) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("headBanners", getHeadBanner(Slides.HOME1));// 首页轮播图
		Integer ids = 0;
		String show = "0";
		if (zoneId != null) {
			ProvinceEnum zone = provinceEnumDao.getCurrentCity(zoneId);
			if (zone != null) {
				ids = zone.getId();
			}

		}

		int sellerIds = reGoodsOfSellerMallDao.getSellerIdsByZones(3, ids);
		if (sellerIds > 0) {
			map.put("goodsClassifys", getGoodsClassifys());// 商品分类
		}
		map.put("concentrationSellers", getConcentrationSellers());// 精选商家
		map.put("secondKill", getSecondKill());// 显示秒杀

		map.put("live", getLive(ids));// 显示直播
		map.put("scoreGoodsClassifys", getScoreGoodsClassifys());// 积分商品分类（标签）
		map.put("costEfficients", getImageTexts(1, zoneId, ""));// 爱划算
		map.put("saleExchanges", getImageTexts(2, zoneId, ""));// 特卖汇
		map.put("showChange", "0");// 换货会 1显示 0 不显示
		if (getImageTexts(3, zoneId, "").size() != 0)
			map.put("preferential99", getImageTexts(3, zoneId, "").get(0));// 久久特惠图文
		if (getImageTexts(4, zoneId, "").size() != 0)
			map.put("specialLocalProduct", getImageTexts(4, zoneId, "").get(0));// 各地特产图文
		if (getImageTexts(5, zoneId, "").size() != 0)
			map.put("scoreMall", getImageTexts(5, zoneId, "").get(0));// 积分商城图文

		String taoke = "http://www.518wtk.com";
		AdminuserZoneTaoke azk = null;
		azk = adminuserZoneTaokeService.getAdminuserZoneTaokeByZone(zoneId);
		if (azk == null) {
			ProvinceEnum zone = provinceEnumDao.getCurrentCity(zoneId);
			azk = adminuserZoneTaokeService.getAdminuserZoneTaokeByZone(zone
					.getId());
		}
		if (azk != null) {
			taoke = azk.getUri() == null ? "http://www.518wtk.com" : azk
					.getUri();
		}

		String goodtotal = "0";
		List<Map<String, String>> datalist = null;
		Map<String, String> datamap = null;
		Map<String, String> tklist = UrlUtil.getTaoKeJson(taoke);
		String nine = "";
		String sqgw = "";
		String twenty = "";
		try {
			if ("1".equals(tklist.get("status"))) {
				String data = "[" + tklist.get("data") + "]";
				datamap = UrlUtil.jsonStringToMap(data);
				goodtotal = datamap.get("good_total");
				nine = datamap.get("nine");
				twenty = datamap.get("twenty");
				String dataliststr = datamap.get("datalist");
				datalist = UrlUtil.jsonStringToListByTaoke(dataliststr, taoke);

			}

			map.put("taokeTitle",
					getTitle(zoneId, goodtotal, taoke
							+ "?from=axp&istao=1&source=axp"));
			map.put("sqgwImage",
					getImageTexts(13, zoneId, "?from=axp&istao=1&source=axp")
							.get(0));

			map.put("taokeType", datalist);

			map.put("nineImage", getImageTexts(14, zoneId, nine).get(0));
			map.put("twentyImage", getImageTexts(15, zoneId, twenty).get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	public int getTotal() {
		int goodtotal = 0;
		List<Map<String, String>> datalist = null;
		Map<String, String> datamap = null;
		Map<String, String> tklist = UrlUtil.getTaoKeJson(StringUtil.TAOKEURI);


		try {
			if ("1".equals(tklist.get("status"))) {
				String data = "[" + tklist.get("data") + "]";
				datamap = UrlUtil.jsonStringToMap(data);
				String gl = datamap.get("good_total");
				if (StringUtils.isNotBlank(gl)) {
					goodtotal = Integer.parseInt(gl);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return goodtotal;
	}

	// 获得headBanner数据
	private Map<String, Object> getTaoke(Map<Integer, Map<String, Object>> ctmap) {// sildes
																					// 表中内容

		List<Map<String, Object>> dataList = new ArrayList<>();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapexchange = ctmap.get(26); // 省钱购物 标题图
		mapexchange.put("title", "今日商品数量：" + this.getTotal()); // 总数量
		map.put("sqgw", mapexchange);
		map.put("cjyh", ctmap.get(27)); // 品牌特惠
		map.put("9kuai9", ctmap.get(28)); // 9块9
		map.put("20yuan", ctmap.get(29)); // 20元封顶
		// 还差一张 优惠券的大图
		return map;
	}

	// 淘客版块信息
	private Map<String, Object> getTaoke2(
			Map<Integer, Map<String, Object>> ctmap) {// sildes 表中内容
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapexchange = ctmap.get(54); // 省钱购物 标题图
		map.put("sqgwTp", mapexchange);
		map.put("ppth", ctmap.get(55)); // 品牌特惠
		map.put("nineTonine", ctmap.get(56)); // 9块9
		map.put("yhj", ctmap.get(58)); // 优惠券
		map.put("sqgwCategories", getCategoriesByTaoke(ctmap)); // 十个小标题版块
		return map;
	}

	// 新版淘客版块信息
	private Map<String, Object> getTaoke3(
			Map<Integer, Map<String, Object>> ctmap,String pid) {// sildes 表中内容
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapexchange = ctmap.get(54); // 省钱购物 标题图
		map.put("ppth", ctmap.get(84)); // 品牌特惠
		map.put("nineTonine", ctmap.get(83)); // 9块9
		map.put("yhj", ctmap.get(82)); // 优惠券
		map.put("pprm", getPopularbrand(ctmap,pid));//品牌热门
		map.put("bkrx", getDetonation(ctmap,pid));//爆款热销

		//添加tbBrandDiscount da 
		//map.put("tbBrandDiscount", ctmap.get(82));//淘宝天猫优惠群
		
		

		
		map.put("tbBrandDiscount", tbBrandDiscount(ctmap));//淘宝天猫品牌

		return map;
	}
	
	private Map<String, Object> getShopping(
			Map<Integer, Map<String, Object>> ctmap) {// sildes 表中内容
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("shopping", ctmap.get(38));
		// 小屏商城
		map.put("xiaopingCentre", ctmap.get(39));
		// 当季新果
		map.put("fruit", ctmap.get(40));
		// 特色小吃
		map.put("hot", ctmap.get(41));
		// 爱生活
		map.put("life", ctmap.get(42));
		return map;
	}

	/**
	 * 一县一品 特色产品模块
	 * 
	 * @param ctmap
	 * @return
	 */
	private Map<String, Object> getFeatureType(
			Map<Integer, Map<String, Object>> ctmap) {// sildes 表中内容
		Map<String, Object> map = new HashMap<String, Object>();
		// 一县一品 小图
		  ctmap.get(67).put("uri", "");
		map.put("yxypMinPicture", ctmap.get(67));
		Map<String, Object> map7 = ctmap.get(67);
		map7.put("typeId",""+map7.get("typeId")+"");
		// 一县一品 大图
		 ctmap.get(68).put("uri", "");
		map.put("yxypMaxPicture", ctmap.get(68));
		Map<String, Object> map2 = ctmap.get(68);
		map2.put("typeId",""+map2.get("typeId")+"");
		// 农副产品
		 ctmap.get(69).put("uri", "");
		map.put("nfcp", ctmap.get(69));
		Map<String, Object> map8 = ctmap.get(69);
		map8.put("typeId",""+map8.get("typeId")+"");
		// 送亲人,送朋友
		 ctmap.get(70).put("uri", "");
		map.put("gift", ctmap.get(70));
		Map<String, Object> map3 = ctmap.get(70);
		map3.put("typeId",""+map3.get("typeId")+"");
		// 美食
		 ctmap.get(71).put("uri", "");
		map.put("chbb", ctmap.get(71));
		Map<String, Object> map4 = ctmap.get(71);
		map4.put("typeId",""+map4.get("typeId")+"");
		// 当季鲜果
		 ctmap.get(72).put("uri", "");
		map.put("fruit", ctmap.get(72));
		Map<String, Object> map5 = ctmap.get(72);
		map5.put("typeId",""+map5.get("typeId")+"");
		// 茶叶
		 ctmap.get(73).put("uri", "");
		map.put("tea", ctmap.get(73));
		Map<String, Object> map6 = ctmap.get(73);
		map6.put("typeId",""+map6.get("typeId")+"");
		return map;
	}
	
	/**
	 * 新版一县一品，特色产品模块
	 * 
	 * @param ctmap
	 * @return
	 */
	private Map<String, Object> getFeatureType2(
			Map<Integer, Map<String, Object>> ctmap) {// sildes 表中内容
		Map<String, Object> map = new HashMap<String, Object>();
		// 一县一品 小图
		  ctmap.get(88).put("uri", "");
		map.put("yxypMinPicture", ctmap.get(88));
		Map<String, Object> map7 = ctmap.get(88);
		map7.put("typeId",""+map7.get("typeId")+"");
		// 一县一品 大图
		 ctmap.get(89).put("uri", "");
		map.put("yxypMaxPicture", ctmap.get(89));
		Map<String, Object> map2 = ctmap.get(89);
		map2.put("typeId",""+map2.get("typeId")+"");
		// 农副产品
		 ctmap.get(93).put("uri", "");
		map.put("nfcp", ctmap.get(93));
		Map<String, Object> map8 = ctmap.get(93);
		map8.put("typeId",""+map8.get("typeId")+"");
		// 送亲人,送朋友
		 ctmap.get(95).put("uri", "");
		map.put("gift", ctmap.get(95));
		Map<String, Object> map3 = ctmap.get(95);
		map3.put("typeId",""+map3.get("typeId")+"");
		// 美食
		 ctmap.get(92).put("uri", "");
		map.put("chbb", ctmap.get(92));
		Map<String, Object> map4 = ctmap.get(92);
		map4.put("typeId",""+map4.get("typeId")+"");
		// 当季鲜果
		 ctmap.get(94).put("uri", "");
		map.put("fruit", ctmap.get(94));
		Map<String, Object> map5 = ctmap.get(94);
		map5.put("typeId",""+map5.get("typeId")+"");
		// 茶叶
		 ctmap.get(91).put("uri", "");
		map.put("tea", ctmap.get(91));
		Map<String, Object> map6 = ctmap.get(91);
		map6.put("typeId",""+map6.get("typeId")+"");
		return map;
	}
	
	
	
	/**
	 * 拼多多
	 * 
	 * @param ctmap
	 * @return
	 */
	private Map<String, Object> getPdd(
			Map<Integer, Map<String, Object>> ctmap,String pid) {// sildes 表中内容
		Map<String, Object> map = new HashMap<String, Object>();
		
		
		
		// 优惠券
	
		 ctmap.get(111).put("uri", "");
		map.put("yhq", ctmap.get(111));
		Map<String, Object> map7 = ctmap.get(111);
		map7.put("typeId",""+map7.get("typeId")+"");
	
		// 水果
		ctmap.get(112).put("uri", "");
		map.put("fruitPt", ctmap.get(112));
		Map<String, Object> map2 = ctmap.get(112);
		map2.put("typeId",""+14+""); //da
		// 食品
		 ctmap.get(113).put("uri", "");
		map.put("food", ctmap.get(113));
		Map<String, Object> map8 = ctmap.get(113);
		map8.put("typeId",""+map8.get("typeId")+"");
		// 化妆品
		ctmap.get(115).put("uri", "");
		map.put("cosmetics", ctmap.get(115));
		Map<String, Object> map3 = ctmap.get(115);
		map3.put("typeId",""+1282+"");//da
		// 品牌券
		
		map.put("pddBrandDiscount", pddBrandDiscount(ctmap));
		map.put("pddDiscountGoods", pddDiscountGoods(ctmap,pid));
	

		return map;
	}
	//拼多多优惠券
	private List<Map<String ,Object>> pddBrandDiscount(Map<Integer, Map<String, Object>> ctmap){
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		for(int i =116;i<131;i++){ //品牌图片的id范围  表 cashshop_type
			Map<String,Object>ctmap116 = new HashMap<String,Object>(); 
			ctmap116.put("logo", ctmap.get(i).get("image"));
			ctmap116.put("image", ctmap.get(i).get("image"));
			ctmap116.put("keyword", ctmap.get(i).get("remark"));
			ctmap116.put("name",  ctmap.get(i).get("title"));
			ctmap116.put("bid", String.valueOf(i));
			dataList.add(ctmap116);
			
		}
		
		for(int i=163;i<169;i++){
			Map<String,Object>ctmap116 = new HashMap<String,Object>(); 
			ctmap116.put("logo", ctmap.get(i).get("image"));
			ctmap116.put("image", ctmap.get(i).get("image"));
			ctmap116.put("keyword", ctmap.get(i).get("remark"));
			ctmap116.put("name",  ctmap.get(i).get("title"));
			ctmap116.put("bid", String.valueOf(i));
			dataList.add(ctmap116);
		}
		
		return dataList;
		
	}
	
	
	//淘宝品牌优惠券
	private List<Map<String ,Object>> tbBrandDiscount(Map<Integer, Map<String, Object>> ctmap){
		
		List<Map<String, Object>> List = new ArrayList<Map<String, Object>>();
		try {
	
			//Map<String, Object> map =  UrlUtil.getTaoKeToMap("http://www.518wtk.com/admin.php?s=/Interface/brand");
			
			List = UrlUtil.getStringForUrl("http://www.518wtk.com/admin.php?s=/Interface/brand");
			for(int i =0;i<List.size();i++){
				if(List.get(i).get("name").equals("暴龙") || List.get(i).get("name").equals("九牧") || List.get(i).get("name").equals("海尔")){
					List.remove(i);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return List;
		/*for(int i =115;i<136;i++){
			if(ctmap.get(i)!=null){
				list.add(ctmap.get(i));
				ctmap.get(i).put("typeId",""+ctmap.get(i).get("typeId")+"");
			}
		}
		
		
		
		return list;*/
	}
	
	private List<Map<String ,Object>> pddDiscountGoods(Map<Integer, Map<String, Object>> ctmap,String pddpid){
		
		//此处需要对接拼多少拼团、、现使用的淘宝数据
		//return getDetonation(ctmap,pddpid);
		
		//---------da 对接拼对对商品数据
		
		List<Map<String, Object>> List = new ArrayList<Map<String, Object>>();
		Map<String,String> signMap = new HashMap<String,String>();
		signMap.put("client_id", StringUtil.pdd_client_id);
		signMap.put("limit", "20");
    	signMap.put("page", "1");
		String sign = MD5Util.getSign(signMap, StringUtil.pdd_client_screct);
		String url = "http://open.aixiaoping.cn:8080/open/api/product?data={\"client_id\":\""+StringUtil.pdd_client_id+"\",\"sign\":\""+sign+"\",\"data\":{\"limit\":\"20\",\"page\":\"1\"}}";
		
		
		try {
			//把所有的字段全部改成和淘宝字段一样的
			Map<String, Object>  goodMap =   UrlUtil.getTaoKeToMap(url);
			Map<String,Object> result = (Map<String, Object>) goodMap.get("result");
			List<Map<String,Object>> good = (List<Map<String, Object>>) result.get("products");
			for(int i = 0;i<good.size();i++){
				Map<String,Object> map = new HashMap<String,Object>();

				if(good.get(i).get("coupon_total_quantity")==null || good.get(i).get("coupon_remain_quantity") == null 
						|| good.get(i).get("min_group_price") == null || good.get(i).get("promotion_rate") == null || good.get(i).get("coupon_discount") == null){
					 continue;
				}

				
				String min_group_price = good.get(i).get("min_group_price").toString();
				Integer quan_receive = Integer.valueOf((int)CalcUtil.sub(Integer.valueOf(good.get(i).get("coupon_total_quantity").toString()), Integer.valueOf(good.get(i).get("coupon_remain_quantity").toString())));
				if(quan_receive < 0){
					//把剩余券数当成总券数
					map.put("quan_surplus", good.get(i).get("coupon_total_quantity").toString());//< 优惠券剩余数量
					Integer quan_receive_va = Integer.valueOf((int)CalcUtil.sub(Integer.valueOf(good.get(i).get("coupon_remain_quantity").toString()), Integer.valueOf(good.get(i).get("coupon_total_quantity").toString())));
					map.put("quan_receive", String.valueOf(quan_receive_va));//< 已领券=总-剩余数量
				}else{
					map.put("quan_surplus", good.get(i).get("coupon_remain_quantity").toString());//< 优惠券剩余数量 
					map.put("quan_receive", String.valueOf(quan_receive));//< 已领券=总-剩余数量
				}
				Double cutPrice = Double.valueOf(good.get(i).get("coupon_discount").toString())/100;
				Double price = CalcUtil.sub(Double.valueOf(good.get(i).get("min_group_price").toString())/100, cutPrice);
				Double earnMoney = CalcUtil.mul(price, Double.valueOf(good.get(i).get("promotion_rate").toString())/1000,2);
				earnMoney = CalcUtil.mul(earnMoney, 0.4,2);
				map.put("goods_id", good.get(i).get("goods_id").toString());//<商品id
				map.put("d_title", good.get(i).get("goods_name").toString());//< 商品名称
				map.put("org_price", String.valueOf(Double.valueOf(min_group_price)/100));//  原价--最小团买价格
				map.put("quan_price", String.valueOf(Double.valueOf(good.get(i).get("coupon_discount").toString())/100));//< 优惠券金额
				map.put("price", String.valueOf(price));//现价即券后价 = 最小团价- 券价
				
				map.put("cut_price", String.valueOf(cutPrice));//券价
				map.put("pic", good.get(i).get("goods_thumbnail_url"));//< 商品图片
				map.put("earnMoney", String.valueOf(earnMoney));//< 赚多少 =最小团价-券价 )*佣金比例goods.add(map);
				
				List.add(map);
				
				
				
				
				
				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return List;
		
		
	}
	
	
	

	private Map<String, Object> getExchange(
			Map<Integer, Map<String, Object>> ctmap, Integer usersId,
			Integer zoneId) {// sildes 表中内容

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("header", ctmap.get(48));
		Map<String, Object> mapexchange = ctmap.get(49);
		mapexchange.put("uri", mapexchange.get("uri") + "?user_id=" + usersId
				+ "&zone_id=" + zoneId);
		Map<String, Object> mapexchange50 = ctmap.get(50);
		mapexchange50.put("uri", mapexchange50.get("uri") + "?user_id="
				+ usersId + "&zone_id=" + zoneId);
		Map<String, Object> mapexchange51 = ctmap.get(51);
		mapexchange51.put("uri", mapexchange51.get("uri") + "?user_id="
				+ usersId + "&zone_id=" + zoneId);
		map.put("exchange", mapexchange);
		map.put("signup", mapexchange50);
		map.put("sign", mapexchange51);

		return map;
	}

	// 换货会模板 现在只有一张图
	private Map<String, Object> getExchange2(
			Map<Integer, Map<String, Object>> ctmap, Integer usersId,
			Integer zoneId) {// sildes 表中内容
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("picture", ctmap.get(75).put("uri", ""));
		return map;
	}

	private List<Map<String, Object>> getCategories(
			Map<Integer, Map<String, Object>> ctmap) {// sildes 表中内容

		List<Map<String, Object>> dataList = new ArrayList<>();
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 30; i < 38; i++) {
			dataList.add(ctmap.get(i));
		}
		return dataList;
	}

	// 获取淘客的分类 十个小分类 (服装,化妆品)
	private List<Map<String, Object>> getCategoriesByTaoke(Map<Integer, Map<String, Object>> ctmap) {
		
		String taoke = "http://www.518wtk.com";
		Map<String, String> dataMap = new HashMap<String, String>();

		Map<String, String> tklist = UrlUtil.getTaoKeJson(taoke);
		List<Map<String, Object>> sortList = new ArrayList<Map<String, Object>>(11);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(11);
		
		if ("1".equals(tklist.get("status"))) {
			String data = "[" + tklist.get("data") + "]";
			try {
				dataMap = UrlUtil.jsonStringToMap(data);
				String dataList = dataMap.get("datalist");
				list = UrlUtil.jsonObjectToList(dataList);
				Map<Integer, Map<String, Object>> temp = new HashMap<Integer, Map<String, Object>>();
				 Map<String, Object> type =null;
				for (Map<String, Object> map : list) {
					int cid = Integer.parseInt(map.get("cid").toString());
					
					if (cid == 1) {
						 type = ctmap.get(59);
						 map.put("subTitle",type.get("remark"));
						 map.put("uri", "");
						 map.put("image",type.get("image"));
						 temp.put(6, map);
						continue;
					}
					if (cid == 2) {
						 type = ctmap.get(66);
						 map.put("subTitle",type.get("remark"));
						 map.put("uri", "");
						 map.put("image",type.get("image"));
						 temp.put(2, map);
						continue;
					}
					if (cid == 3) {
						 type = ctmap.get(60);
						 map.put("subTitle",type.get("remark"));
						 map.put("uri", "");
						 map.put("image",type.get("image"));
						temp.put(4, map);
						continue;
					}
					if (cid == 4) {
						 type = ctmap.get(61);
						 map.put("subTitle",type.get("remark"));
						 map.put("uri", "");
						 map.put("image",type.get("image"));
						temp.put(5, map);
						continue;
					}
					if (cid == 5) {
						 type = ctmap.get(63);
						 map.put("subTitle",type.get("remark"));
						 map.put("uri", "");
						 map.put("image",type.get("image"));
						temp.put(0, map);
						continue;
					}
					if (cid == 6) {
						 type = ctmap.get(81);
						 map.put("subTitle",type.get("remark"));
						 map.put("uri", "");
						 map.put("image",type.get("image"));
						temp.put(9, map);
						continue;
					}
					if (cid == 7) {
						 type = ctmap.get(62);
						 map.put("subTitle",type.get("remark"));
						 map.put("uri", "");
						 map.put("image",type.get("image"));
						temp.put(3, map);
						continue;
					}
					if (cid == 8) {
						 type = ctmap.get(65);
						 map.put("subTitle",type.get("remark"));
						 map.put("uri", "");
						 map.put("image",type.get("image"));
						temp.put(1, map);
						continue;
					}
					if (cid == 9) {
						 type = ctmap.get(80);
						 map.put("subTitle",type.get("remark"));
						 map.put("uri", "");
						 map.put("image",type.get("image"));
						temp.put(7, map);
						continue;
					}
					if (cid == 10) {
						 type = ctmap.get(64);
						 map.put("subTitle",type.get("remark"));
						 map.put("uri", "");
						 map.put("image",type.get("image"));
						temp.put(8, map);
						continue;
					}
				}

				for (int i = 0; i < 10; i++) {
					sortList.add(temp.get(i));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sortList;
	}

	
	
	
	//品牌热门
	private List<Map<String, Object>> getPopularbrand (Map<Integer, Map<String, Object>> ctmap,String pid){
		List<Map<String, Object>> List = new ArrayList<Map<String, Object>>();
		try {  
			List = UrlUtil.getTaoKeToMap2("http://www.518wtk.com/admin.php?s=/Interface/designation/designation/1"+"&pid="+pid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return List;
	}
	
	
	//爆款热销
	private List<Map<String, Object>> getDetonation (Map<Integer, Map<String, Object>> ctmap,String pid){
		List<Map<String, Object>> List = new ArrayList<Map<String, Object>>();
		try {
			List=UrlUtil.getTaoKeToMap2("http://www.518wtk.com/admin.php?s=/Interface/designation/designation/2"+"&pid="+pid);
			//List  = UrlUtil.getTaoKeToMap("http://www.518wtk.com/admin.php?s=/Interface/brand");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return List;
	}
	private Map<String, Object> getActivites(
			Map<Integer, Map<String, Object>> ctmap) {// sildes 表中内容

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("header", ctmap.get(44));
		map.put("secondKill", getSecondKills(ctmap));
		map.put("score", getScores(ctmap));

		return map;
	}

	public Map<String, Object> getSecondKills(
			Map<Integer, Map<String, Object>> ctmap) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentTime", getSecondKillByTimes(0));
		map.put("secondKill", getSecondKill());
		map.put("seckGoods", ctmap.get(46));

		return map;
	}

	public Map<String, Object> getScores(Map<Integer, Map<String, Object>> ctmap) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("scoreGoods", ctmap.get(47));

		return map;
	}

	public Map<String, Object> getLives(Integer zoneid,
			Map<Integer, Map<String, Object>> ctmap) {
		Map<String, Object> map = new HashMap<String, Object>();

		List<Map<String, Object>> live = getLive(zoneid);
		if (live.size() > 0) {
			map.put("header", ctmap.get(45));
			map.put("lives", live);// 显示直播
		}
		return map;
	}

	public Map<String, Object> getLives2(Integer zoneid,
			Map<Integer, Map<String, Object>> ctmap,Integer userId) {
		Map<String, Object> map = new HashMap<String, Object>();

		List<Map<String, Object>> live = getLive(zoneid);
		if (live.size() > 0) {
			map.put("liveHeader", ctmap.get(74).put("uri", ""));
			map.put("lives", live);// 显示直播
		}
		return map;
	}

	// ==========================特产置顶=====ZL============================//
	/*
	 * public Map<String, Object> getLocalTop(Map<Integer, Map<String,Object>>
	 * ctmap){ Map<String, Object> map = new HashMap<String, Object>();
	 * 
	 * List<Map<String, Object>> top = getlocal(); if (top.size()>0) {
	 * map.put("toplocal", top); } return map; }
	 */
	// ===================================================================//
	public Map<String, Object> getDataNew(Integer userId, Integer type,
			Integer zoneId, String appVersion,
			Map<Integer, Map<String, Object>> ctmap, String os, String v,
			String pid) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer ids = 0;
		String show = "0";

		if ("IOS".equals(os) && "5.7.1".equals(appVersion)) {
			map.put("isCheck", "0");// 审核
		} else {
			map.put("isCheck", "0");
		}

		

		map.put("sqgw", getTaoke(ctmap));//taoke 省钱购物版块三大导航
		map.put("categories", getCategories(ctmap));//taoke	//获取小分类 如服装 化妆品

		
		map.put("searchurl", StringUtil.TAOKEURI+"/index.php?s=/Index/seek&state=app&from=axp&istao=1&source=axp&pid="+pid);//淘客搜索
	
		
		
		if(zoneId!=null){

			ProvinceEnum zone = provinceEnumDao.getCurrentCity(zoneId);
			if (zone != null) {
				ids = zone.getId();
				if (ids == 1961 || ids == 1132 || ids == 1043 || zoneId == 1119
						|| ids == 844 || ids == 864 || ids == 202) {
					map.put("exchange", getExchange(ctmap, userId, zoneId));// taoke
				}

				map.put("live", getLives(ids, ctmap));
				map.put("topImages",
						getNewHeadBanner(Slides.HOME1, zone.getId()));// 首页轮播图
			}

		}
		map.put("shopping", getShopping(ctmap));
		map.put("storesTotalPage", "10");
		// map.put("topImages", getHeadBanner(Slides.HOME1));// 首页轮播图
		// map.put("adbanner", ctmap.get(43));//广告图
		map.put("activites", getActivites(ctmap));
		map.put("news", getNew(zoneId, ctmap));// 小屏新闻
		map.put("toplocal", getlocal(ctmap));// 特产置顶
		return map;
	}


	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getDataNew5142(HttpServletRequest request,Integer userId, Integer type,
			Integer zoneId, double lat,double lng,String appVersion,
			Map<Integer, Map<String, Object>> ctmap, String os, String v,
			String pid,String dip,String pddpid,Integer pageIndex) throws Exception {
		String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> moduleList = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> data1 = new HashMap<String,Object>();
		
		data1.put("moduleType",1);
		Integer ids = 0;
		GameActivity game = new GameActivity();
		if (zoneId != null) {
			ProvinceEnum zone = provinceEnumDao.getCurrentCity2(zoneId);
			if (zone != null) {
				ids = zone.getId();
				// 顶部图片map
				data1.put("homeTopImages",getNewHeadBanner(Slides.HOME1, zone.getId()));// 首页轮播图getNewHeadBanner(Slides.HOME1, zone.getId())
			}
			game = gameActivityService.getZoneId(zoneId);
		}
		moduleList.add(data1);
		
		Map<String, Object> data2 = new HashMap<String,Object>();
		
//		if(Integer.valueOf(appVersion)>1108 && "IOS".equals(os)){
//			data2.put("topClassify",getTopClassifys_107(ctmap,dip,userId));
//			
//		}else{
			if(game.getId()!= null){  //有签到的
				data2.put("topClassify", getTopClassifys(ctmap,dip,userId));
				
			}else{ //没有签到的
				data2.put("topClassify", getTopClassify(ctmap,dip,userId));
			}
			
//		}
	
		data2.put("moduleType",2);// 顶部分类
		moduleList.add(data2);
	
		Map<String, Object> data4 = new HashMap<String,Object>();
		data4.put("moduleType",4);
		data4.put("twoAdvertScoreModule", getscoreExchange(ctmap,4,lat,lng));
		moduleList.add(data4);
		
		Map<String, Object> data5 = new HashMap<String,Object>();
		data5.put("moduleType",5);
		data5.put("twoAdverts", getsellerActivity(ctmap,pid));
		data5.put("rolls", reGoodsOfLockMallService.rolls());
//		if(Integer.valueOf(appVersion)>1107 && "IOS".equals(os)){
//		}else{
			moduleList.add(data5);
//		}
		Map<String, Object> data6 = new HashMap<String,Object>();
		data6.put("moduleType",6);
		data6.put("bottomScoreModule", getscoreExchange(ctmap,6,lat,lng));
		data6.put("rolls", reGoodsOfLockMallService.rolls());
		moduleList.add(data6);
		Map<String, Object> data3 = new HashMap<String,Object>();
		data3.put("moduleType",3); 
		data3.put("oneAdvertScoreModule", getscoreExchange(ctmap,3,lat,lng));
		moduleList.add(data3);
		
		
		
		Map<String, Object> data7 = new HashMap<String,Object>();
		List<Map<String, Object>> scoreGoods = sellerService.getSellerListBySearch2(0, type, null, userId, zoneId, null, null, lat, lng, request, null);
		Map<String, Object> bottomScoreModule = new HashMap<String,Object>();
		bottomScoreModule.put("scoreGoods", scoreGoods);
		bottomScoreModule.put("moduleTitle", "周边店铺");
		data7.put("moduleType",7);
		data7.put("bottomStoreModule",bottomScoreModule);
		moduleList.add(data7);
		
		
		map.put("moduleList",moduleList);
		
		
		
		return map;
	
		
	}
	@SuppressWarnings("unchecked")
	public Map<String, Object> getDataNew104(HttpServletRequest request,Integer userId, Integer type,
			Integer zoneId, double lat,double lng,String appVersion,
			Map<Integer, Map<String, Object>> ctmap, String os, String v,
			String pid,String dip,String pddpid,Integer pageIndex) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> moduleList = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> data1 = new HashMap<String,Object>();
		data1.put("moduleType",1);
		Integer ids = 0;
		if (zoneId != null) {
			ProvinceEnum zone = provinceEnumDao.getCurrentCity2(zoneId);
			if (zone != null) {
				ids = zone.getId();
				// 顶部图片map
				data1.put("homeTopImages",getNewHeadBanner(Slides.HOME1, zone.getId()));// 首页轮播图getNewHeadBanner(Slides.HOME1, zone.getId())
			}
		}
		moduleList.add(data1);
		
		Map<String, Object> data2 = new HashMap<String,Object>();
		data2.put("topClassify", getTopClassifys(ctmap,dip,userId));
		data2.put("moduleType",2);// 顶部分类
		//moduleList.add(data2);
		Map<String, Object> data4 = new HashMap<String,Object>();
		data4.put("moduleType",4);
		data4.put("twoAdvertScoreModule", getscoreExchange(ctmap,4,lat,lng));
		moduleList.add(data4);
		Map<String, Object> data3 = new HashMap<String,Object>();
		data3.put("moduleType",3); 
		data3.put("oneAdvertScoreModule", getscoreExchange(ctmap,3,lat,lng));
		moduleList.add(data3);
		Map<String, Object> data5 = new HashMap<String,Object>();
		data5.put("moduleType",5);
		data5.put("twoAdverts", getsellerActivity(ctmap,pid));
		moduleList.add(data5);
		Map<String, Object> data6 = new HashMap<String,Object>();
		data6.put("moduleType",6);
		data6.put("bottomScoreModule", getscoreExchange(ctmap,6,lat,lng));
		moduleList.add(data6);
		Map<String, Object> data7 = new HashMap<String,Object>();
		List<Map<String, Object>> scoreGoods = sellerService.getSellerListBySearch2(0, type, null, userId, zoneId, null, null, lat, lng, request, null);
		Map<String, Object> bottomScoreModule = new HashMap<String,Object>();
		bottomScoreModule.put("scoreGoods", scoreGoods);
		bottomScoreModule.put("moduleTitle", "周边店铺");
		data7.put("moduleType",7);
		data7.put("bottomStoreModule",bottomScoreModule);
		moduleList.add(data7);
		map.put("moduleList",moduleList);
		
		return map;
	
		
	}
	
	
	/**
	 * 广播信息
	 * @return
	 */
	public Map<String,Object> broadcastContents(){
		
		Map<String,Object> statusMap = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<Map<String,Object>> newList= new ArrayList<Map<String,Object>>();
		QueryModel model = new QueryModel();
		model.clearQuery();
		model.setOrder("create_Time DESC");
		List<News> newsList = dateBaseDAO.findLists(News.class, model);
		if(newsList!=null && newsList.size()>0){
			for(News n: newsList){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("uri", n.getUrl());
				map.put("title", n.getTitle());
				newList.add(map);
			}
			
		}
		
	/*	
	 * 消息中心
	 * QueryModel queryModel = new QueryModel();
		queryModel.combPreEquals("isValid", true);
		queryModel.combPreEquals("type", 30);
		MessageCenter messageCenter = (MessageCenter) dateBaseDAO.findOne(
				MessageCenter.class, queryModel);
	*/
		
		
		statusMap.put("newList", newList);
		return statusMap;
		
	}
	
	public static int compare_date(String DATE1) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = new Date();
			if (dt1.getTime() < dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() > dt2.getTime()) {

				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	// 积分兑换
	private Map<String, Object> getscoreExchange(
			Map<Integer, Map<String, Object>> ctmap,Integer pid,double lat, double lng) throws Exception {
		Map<String, Object> topMap = new HashMap<String, Object>();
		if(pid==3){  // 没有标题的 3
			topMap.put("moduleTypeId", 333);
			topMap.put("moduleTitle", "积分商品");
			topMap.put("advert", ctmap.get(136).get("image"));
			topMap.put("scoreGoods", mallListService.getGoodsMap(2, 0, 6, pid,StringUtil.url,lat,lng));
		}else if(pid==4){ //4
			topMap.put("moduleTitle", "99特惠");
			topMap.put("scoreGoods", mallListService.getGoodsMap(2, 0, 6, pid,StringUtil.url,lat,lng));
			topMap.put("moduleTypeId", 999);
			
		}else if(pid == 6){  //pid==6
			topMap.put("moduleTitle", "人气推荐");
			topMap.put("scoreGoods", mallListService.getGoodsMap(2, 0, 6, pid,StringUtil.url,lat,lng));
			topMap.put("moduleTypeId", 666);
		}
		return topMap;
	}
	
	
	// 积分兑换
		
	private Map<String, Object> getscoreExchangeXCX(
			Map<Integer, Map<String, Object>> ctmap,Integer pid,double lat, double lng) throws Exception {
		Map<String, Object> topMap = new HashMap<String, Object>();
		if(pid==3){  // 没有标题的 3
			topMap.put("moduleTypeId", 333);
			topMap.put("moduleTitle", "积分商品");
			topMap.put("advert", ctmap.get(136).get("image"));
			topMap.put("scoreGoods", mallListService.getGoodsMap(2, 0, 6, pid,StringUtil.url,lat,lng));
		}else if(pid==4){ //4
			topMap.put("moduleTitle", "99特惠");
			topMap.put("scoreGoods", mallListService.getGoodsMap(2, 0, 6, pid,StringUtil.url,lat,lng));
			topMap.put("moduleTypeId", 999);
			
		}else if(pid == 6){  //pid==6
			topMap.put("moduleTitle", "限时抢购");
			topMap.put("scoreGoods", mallListService.getGoodsMap(2, 0, 6, pid,StringUtil.url,lat,lng));
			topMap.put("moduleTypeId", 666);
		}
		return topMap;
	}
	
	
	
	
	//	 商家活动
		private List<Map<String, Object>> getsellerActivity(
				Map<Integer, Map<String, Object>> ctmap,String pid) throws Exception {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			list.add(ctmap.get(137));
			list.add(ctmap.get(138));
			return list;
		}
	
	
	
	// 得到首页顶部4大分类 没有签到的
	private Map<String, Object> getTopClassify(Map<Integer, Map<String, Object>> ctmap,String dip,Integer userId) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> topMap = new HashMap<String, Object>();
		List<Object> navigationTop = new ArrayList<Object>();
		//夺宝,抽奖,开店,分类
        if (Integer.parseInt(dip)>=480) {
			ctmap.get(87).put("typeId", 2);			
			ctmap.get(103).put("typeId", 3);
			ctmap.get(104).put("typeId", 5);
			ctmap.get(97).put("typeId", 4);
			navigationTop.add(ctmap.get(87));
			navigationTop.add(ctmap.get(103));
			navigationTop.add(ctmap.get(104));
			navigationTop.add(ctmap.get(97));
		}else if(Integer.parseInt(dip)<480 && Integer.parseInt(dip)>=320){ 
			ctmap.get(100).put("typeId", 2);			
			ctmap.get(107).put("typeId", 3);
			ctmap.get(108).put("typeId", 5);
			ctmap.get(99).put("typeId", 4);
			navigationTop.add(ctmap.get(100));
			navigationTop.add(ctmap.get(107));
			navigationTop.add(ctmap.get(108));
			navigationTop.add(ctmap.get(99));
			
		}else{			//da  添加dip更小的
			ctmap.get(100).put("typeId", 2);			
			ctmap.get(107).put("typeId", 3);
			ctmap.get(108).put("typeId", 5);
			ctmap.get(99).put("typeId", 4);
			navigationTop.add(ctmap.get(100));
			navigationTop.add(ctmap.get(107));
			navigationTop.add(ctmap.get(108));
			navigationTop.add(ctmap.get(99));
		}
		topMap.put("seckTimes", null);//getSecondKill()
		Map<String,Object> map = usersService.isSeller(userId);
		dataMap.put("isPaiedForStore", map.get("isPaiedForStore"));
		dataMap.put("verifyStatus", map.get("verifyStatus"));
		dataMap.put("openStoreMoney", "1");
		dataMap.put("navigationTop", navigationTop);
		dataMap.put("columns", navigationTop.size());//列数
		dataMap.put("rolls", reGoodsOfLockMallService.rolls());
		
		return dataMap;
	}
	/**
	 * 小程序分类请求
	 * @param game
	 * @param ctmap
	 * @param dip
	 * @param userId
	 * @return
	 */
	private Map<String,Object> getTopClassifyXCX(GameActivity game,Map<Integer, Map<String, Object>> ctmap,String dip,Integer userId){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> topMap = new HashMap<String, Object>();
		List<Object> navigationTop = new ArrayList<Object>();
		if(game.getId() == null){
			//夺宝,抽奖,开店,分类
	        if (Integer.parseInt(dip)>=480) {
				ctmap.get(104).put("typeId", 2);
				ctmap.get(97).put("typeId", 3);
				ctmap.get(106).put("typeId", 4);
				navigationTop.add(ctmap.get(104));
				navigationTop.add(ctmap.get(97));
				navigationTop.add(ctmap.get(106));
			}else if(Integer.parseInt(dip)<480 && Integer.parseInt(dip)>=320){ 
				ctmap.get(108).put("typeId", 2);
				ctmap.get(99).put("typeId", 3);
				ctmap.get(106).put("typeId", 4);
				navigationTop.add(ctmap.get(108));
				navigationTop.add(ctmap.get(99));
				navigationTop.add(ctmap.get(106));
				
			}else{			//da  添加dip更小的
				ctmap.get(108).put("typeId", 2);
				ctmap.get(99).put("typeId", 3);
				ctmap.get(106).put("typeId", 4);
				navigationTop.add(ctmap.get(108));
				navigationTop.add(ctmap.get(99));
				navigationTop.add(ctmap.get(106));
			}
		}else{
			//签到,夺宝,抽奖,开店,分类
	        if (Integer.parseInt(dip)>=480) {
	        	ctmap.get(85).put("typeId", 1);	
				ctmap.get(104).put("typeId", 2);
				ctmap.get(97).put("typeId", 3);
				ctmap.get(106).put("typeId", 4);
				navigationTop.add(ctmap.get(85));
				navigationTop.add(ctmap.get(104));
				navigationTop.add(ctmap.get(97));
				navigationTop.add(ctmap.get(106));
			}else if(Integer.parseInt(dip)<480 && Integer.parseInt(dip)>=320){ 
				ctmap.get(98).put("typeId", 1);	
				ctmap.get(108).put("typeId", 2);
				ctmap.get(99).put("typeId", 3);
				ctmap.get(106).put("typeId", 4);
				navigationTop.add(ctmap.get(98));
				navigationTop.add(ctmap.get(108));
				navigationTop.add(ctmap.get(99));
				navigationTop.add(ctmap.get(106));
			}else{			//da  添加dip更小的
				ctmap.get(98).put("typeId", 1);	
				ctmap.get(108).put("typeId", 2);
				ctmap.get(99).put("typeId", 3);
				ctmap.get(106).put("typeId", 4);
				navigationTop.add(ctmap.get(98));
				navigationTop.add(ctmap.get(108));
				navigationTop.add(ctmap.get(99));
				navigationTop.add(ctmap.get(106));
			}
		}
		
		
		Map<String,Object> map = usersService.isSeller(userId);
		dataMap.put("isPaiedForStore", map.get("isPaiedForStore"));
		dataMap.put("verifyStatus", map.get("verifyStatus"));
		dataMap.put("openStoreMoney", "1");
		dataMap.put("navigationTop", navigationTop);
		dataMap.put("columns", navigationTop.size());//列数
		
		return dataMap;
	
		
		
	}
	 //得到首页顶部五大分类
		private Map<String, Object> getTopClassifys(Map<Integer, Map<String, Object>> ctmap,String dip,Integer userId){
			Map<String, Object> dataMap = new HashMap<String, Object>();
			Map<String, Object> topMap = new HashMap<String, Object>();
			List<Object> navigationTop = new ArrayList<Object>();
			//签到,夺宝,抽奖,开店,分类
	        if (Integer.parseInt(dip)>=480) {
	        	ctmap.get(85).put("typeId", 1);	
				ctmap.get(87).put("typeId", 2);			
				ctmap.get(103).put("typeId", 3);
				ctmap.get(104).put("typeId", 5);
				ctmap.get(97).put("typeId", 4);
				navigationTop.add(ctmap.get(85));
				navigationTop.add(ctmap.get(87));
				navigationTop.add(ctmap.get(103));
				navigationTop.add(ctmap.get(104));
				navigationTop.add(ctmap.get(97));
			}else if(Integer.parseInt(dip)<480 && Integer.parseInt(dip)>=320){ 
				ctmap.get(98).put("typeId", 1);	
				ctmap.get(100).put("typeId", 2);			
				ctmap.get(107).put("typeId", 3);
				ctmap.get(108).put("typeId", 5);
				ctmap.get(99).put("typeId", 4);
				navigationTop.add(ctmap.get(98));
				navigationTop.add(ctmap.get(100));
				navigationTop.add(ctmap.get(107));
				navigationTop.add(ctmap.get(108));
				navigationTop.add(ctmap.get(99));
				
			}else{			//da  添加dip更小的
				ctmap.get(98).put("typeId", 1);	
				ctmap.get(100).put("typeId", 2);			
				ctmap.get(107).put("typeId", 3);
				ctmap.get(108).put("typeId", 5);
				ctmap.get(99).put("typeId", 4);
				navigationTop.add(ctmap.get(98));
				navigationTop.add(ctmap.get(100));
				navigationTop.add(ctmap.get(107));
				navigationTop.add(ctmap.get(108));
				navigationTop.add(ctmap.get(99));
			}
			topMap.put("seckTimes", null);//getSecondKill()
			Map<String,Object> map = usersService.isSeller(userId);
			dataMap.put("isPaiedForStore", map.get("isPaiedForStore"));
			dataMap.put("verifyStatus", map.get("verifyStatus"));
			dataMap.put("openStoreMoney", "1");
			dataMap.put("navigationTop", navigationTop);
			dataMap.put("columns", navigationTop.size());//列数
			dataMap.put("rolls", reGoodsOfLockMallService.rolls());
			return dataMap;
		}
		 //得到首页顶部五大分类
		private Map<String, Object> getTopClassifys_107(Map<Integer, Map<String, Object>> ctmap,String dip,Integer userId){
			Map<String, Object> dataMap = new HashMap<String, Object>();
			Map<String, Object> topMap = new HashMap<String, Object>();
			List<Object> navigationTop = new ArrayList<Object>();
			//签到,夺宝,抽奖,开店,分类
	        if (Integer.parseInt(dip)>=480) {
	        	ctmap.get(85).put("typeId", 1);	
				//ctmap.get(103).put("typeId", 3);
				ctmap.get(104).put("typeId", 5);
				ctmap.get(97).put("typeId", 4);
				navigationTop.add(ctmap.get(85));
				//navigationTop.add(ctmap.get(103));
				navigationTop.add(ctmap.get(104));
				navigationTop.add(ctmap.get(97));
			}else if(Integer.parseInt(dip)<480 && Integer.parseInt(dip)>=320){ 
				ctmap.get(98).put("typeId", 1);	
				//ctmap.get(107).put("typeId", 3);
				ctmap.get(108).put("typeId", 5);
				ctmap.get(99).put("typeId", 4);
				navigationTop.add(ctmap.get(98));
			
				//navigationTop.add(ctmap.get(107));
				navigationTop.add(ctmap.get(108));
				navigationTop.add(ctmap.get(99));
				
			}else{			//da  添加dip更小的
				ctmap.get(98).put("typeId", 1);	
				//ctmap.get(107).put("typeId", 3);
				ctmap.get(108).put("typeId", 5);
				ctmap.get(99).put("typeId", 4);
				navigationTop.add(ctmap.get(98));
				//navigationTop.add(ctmap.get(107));
				navigationTop.add(ctmap.get(108));
				navigationTop.add(ctmap.get(99));
			}
			topMap.put("seckTimes", null);//getSecondKill()
			Map<String,Object> map = usersService.isSeller(userId);
			dataMap.put("isPaiedForStore", map.get("isPaiedForStore"));
			dataMap.put("verifyStatus", map.get("verifyStatus"));
			dataMap.put("openStoreMoney", "1");
			dataMap.put("navigationTop", navigationTop);
			dataMap.put("columns", navigationTop.size());//列数
			List rolls = new ArrayList();
			dataMap.put("rolls", rolls);
			return dataMap;
		}
		
		
		private Map<String, Object> getTopClassify_108(Map<Integer, Map<String, Object>> ctmap,String dip,Integer userId) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			Map<String, Object> topMap = new HashMap<String, Object>();
			List<Object> navigationTop = new ArrayList<Object>();
			//夺宝,抽奖,开店,分类
	        if (Integer.parseInt(dip)>=480) {
				ctmap.get(103).put("typeId", 3);
				ctmap.get(104).put("typeId", 5);
				ctmap.get(97).put("typeId", 4);
				navigationTop.add(ctmap.get(103));
				navigationTop.add(ctmap.get(104));
				navigationTop.add(ctmap.get(97));
			}else if(Integer.parseInt(dip)<480 && Integer.parseInt(dip)>=320){ 
				ctmap.get(107).put("typeId", 3);
				ctmap.get(108).put("typeId", 5);
				ctmap.get(99).put("typeId", 4);
				navigationTop.add(ctmap.get(107));
				navigationTop.add(ctmap.get(108));
				navigationTop.add(ctmap.get(99));
				
			}else{			//da  添加dip更小的
				ctmap.get(107).put("typeId", 3);
				ctmap.get(108).put("typeId", 5);
				ctmap.get(99).put("typeId", 4);
				navigationTop.add(ctmap.get(107));
				navigationTop.add(ctmap.get(108));
				navigationTop.add(ctmap.get(99));
			}
			topMap.put("seckTimes", null);//getSecondKill()
			Map<String,Object> map = usersService.isSeller(userId);
			dataMap.put("isPaiedForStore", map.get("isPaiedForStore"));
			dataMap.put("verifyStatus", map.get("verifyStatus"));
			dataMap.put("openStoreMoney", "1");
			dataMap.put("navigationTop", navigationTop);
			dataMap.put("columns", navigationTop.size());//列数
			dataMap.put("rolls", reGoodsOfLockMallService.rolls());
			
			return dataMap;
		}
		
	
	
	private Map<String, Object> getDataForPid(Integer type, Integer zoneId,
			String appVersion, String pid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("headBanners", getHeadBanner(Slides.HOME1));// 首页轮播图
		Integer ids = 0;
		String show = "0";
		if (zoneId != null) {
			ProvinceEnum zone = provinceEnumDao.getCurrentCity(zoneId);
			if (zone != null) {
				ids = zone.getId();
			}

		}

		int sellerIds = reGoodsOfSellerMallDao.getSellerIdsByZones(3, ids);
		if (sellerIds > 0) {
			map.put("goodsClassifys", getGoodsClassifys());// 商品分类
		}
		map.put("concentrationSellers", getConcentrationSellers());// 精选商家
		map.put("secondKill", getSecondKill());// 显示秒杀

		map.put("live", getLive(ids));// 显示直播

		map.put("scoreGoodsClassifys", getScoreGoodsClassifys());// 积分商品分类（标签）
		map.put("costEfficients", getImageTexts(1, zoneId, ""));// 爱划算
		map.put("saleExchanges", getImageTexts(2, zoneId, ""));// 特卖汇
		map.put("showChange", "0");// 换货会 1显示 0 不显示
		if (getImageTexts(3, zoneId, "").size() != 0)
			map.put("preferential99", getImageTexts(3, zoneId, "").get(0));// 久久特惠图文
		if (getImageTexts(4, zoneId, "").size() != 0)
			map.put("specialLocalProduct", getImageTexts(4, zoneId, "").get(0));// 各地特产图文
		if (getImageTexts(5, zoneId, "").size() != 0)
			map.put("scoreMall", getImageTexts(5, zoneId, "").get(0));// 积分商城图文

		String taoke = "http://www.518wtk.com";

		String goodtotal = "0";
		List<Map<String, String>> datalist = null;
		Map<String, String> datamap = null;
		Map<String, String> tklist = UrlUtil.getTaoKeJson(taoke);
		String nine = "";
		String sqgw = "";
		String twenty = "";
		try {
			if ("1".equals(tklist.get("status"))) {
				String data = "[" + tklist.get("data") + "]";
				datamap = UrlUtil.jsonStringToMap(data);
				goodtotal = datamap.get("good_total");
				nine = datamap.get("nine");
				twenty = datamap.get("twenty");
				String dataliststr = datamap.get("datalist");
				datalist = UrlUtil.jsonStringToListByTaokeForPid(dataliststr,
						taoke, pid);

			}
			map.put("taokeTitle",
					getTitle(zoneId, goodtotal, taoke
							+ "?from=axp&istao=1&source=axp&pid=" + pid));
			map.put("sqgwImage",
					getImageTextsForPid(13, zoneId,
							"?from=axp&istao=1&source=axp&pid=" + pid).get(0));

			map.put("taokeType", datalist);

			map.put("nineImage",
					getImageTextsForPid(14, zoneId, nine + "&pid=" + pid)
							.get(0));
			map.put("twentyImage",
					getImageTextsForPid(15, zoneId, twenty + "&pid=" + pid)
							.get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	// 获得积分商城精品页面图文
	private Map<String, Object> getTitle(Integer zoneId, String total,
			String uri) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("title", "省钱购物");// 标题
		map.put("total", "今日新增" + total + "优惠商品");// 精选图文，固定四个
		map.put("uri", uri);// 精选图文，固定四个
		return map;
	}

	// 获得积分商城精品页面图文
	private Map<String, Object> getTitleNew(Integer zoneId, String total,
			String uri) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bgimage", "");// 轮播图
		map.put("title", "省钱购物");// 标题
		map.put("total", "今日新增" + total + "优惠商品");// 精选图文，固定四个
		map.put("uri", uri);// 精选图文，固定四个
		return map;
	}

	private List<Map<String, Object>> getImage(Integer zoneId,
			List<Map<String, String>> dataList, String taoke) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < dataList.size(); i++) {
			Map<String, String> map = dataList.get(i);
			Map<String, Object> newmap = new HashMap<String, Object>();
			newmap.put("cid", map.get("cid"));
			newmap.put("title", "今日新增" + map.get("title"));
			newmap.put("uri", taoke.concat(map.get("uri")));
			newmap.put("image", map.get("image"));
			newmap.put("org_price", map.get("org_price"));
			newmap.put("price", map.get("price"));
			list.add(newmap);
		}
		return list;
	}

	// 获得积分商城精品页面图文
	private Map<String, Object> getConcentration(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("concentrationBanners", getHeadBanner(Slides.SCOREMALL));// 轮播图
		map.put("concentrationImageTexts", getImageTexts("6,7,8,9"));// 精选图文，固定四个
		return map;
	}

	// 获得headBanner数据
	private List<Map<String, Object>> getHeadBanner(Integer type) {// sildes
																	// 表中内容
		List<Slides> slist = slidesDao.getList(type);
		List<Map<String, Object>> dataList = new ArrayList<>();
		for (Slides slides : slist) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", slides.getImgurls() == null ? ""
					: RESOURCE_LOCAL_URL + slides.getImgurls());
			map.put("uri", slides.getLinkurl());
			map.put("name", slides.getName());
			dataList.add(map);
		}
		return dataList;
	}

	// 分类获得HeadBanner数据
	private List<Map<String, Object>> getNewHeadBanner(Integer type,
			Integer zoneId) {
		List<Slides> slist = slidesDao.getsList(type, zoneId);
		List<Slides> list = slidesDao.getsListByZb(type);

		List<Map<String, Object>> dataList = new ArrayList<>();
		int i = 0;
		for (Slides slide : slist) {
			if (i < 4) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("image", slide.getImgurls() == null ? ""
						:RESOURCE_LOCAL_URL  + slide.getImgurls());
				map.put("uri", slide.getLinkurl());
				map.put("name", slide.getName());
				dataList.add(map);
				i++;
			}
		}
		for (Slides slides : list) {
			if (i < 7) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("image", slides.getImgurls() == null ? ""
						: RESOURCE_LOCAL_URL + slides.getImgurls());
				map.put("uri", slides.getLinkurl());
				map.put("name", slides.getName());
				dataList.add(map);
				i++;
			}
		}

		return dataList;
	}

	// 获得goodsClassifys数据（商品分类）
	private List<Map<String, Object>> getGoodsClassifys() {// CommodityType表中内容
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> dataMap=new HashMap<>();
		
		List<CommodityType> sclist = commodityTypeDao.getAllType();//所有分类
		List<CommodityType> sclist1=new ArrayList<CommodityType>();
		List<CommodityType> sclist2=new ArrayList<CommodityType>();
		for(CommodityType sc1 : sclist){
			if(sc1.getLevel()==1){
				if (!sc1.getName().equals("全部")) {
					sclist1.add(sc1);
				}
			}else{
				sclist2.add(sc1);
			}
		}
		for(CommodityType sc : sclist1){
			List<Map<String,Object>> lv2List = new ArrayList<Map<String,Object>>();
			Map<String,Object> lv1map = new HashMap<String, Object>();
			lv1map.put("typeId", sc.getId());
			lv1map.put("name", sc.getName());
			lv1map.put("categoryItems", lv2List);
			for(CommodityType sc2 : sclist2){
				if(sc2.getCommodityType().getId()==sc.getId()){
					Map<String,Object> lv2map = new HashMap<String, Object>();
					lv2map.put("typeId",sc2.getId() );
					lv2map.put("name", sc2.getName());
					if(sc2.getImgUrl()==null){
						lv2map.put("image","http://jifen.aixiaoping.cn:8080/dailyRes/commodity_type/1/nomal/2513200714095138300.png");
					}else{
						lv2map.put("image", path+"/"+sc2.getImgUrl());
						
					}
					lv2List.add(lv2map);
				}
			}
			list.add(lv1map);
		}
			

		
		
		return list;

	}

	// 获得ConcentrationSellers数据
	private List<Map<String, Object>> getConcentrationSellers() {// seller表中内容
		QueryModel model = new QueryModel();
		model.combPreEquals("isvalid", true);
		int count = datebaseDao.findCount(Seller.class, model);
		int pageSize = 6;
		int A = (int) CalcUtil.div(count, pageSize);
		int B = (int) CalcUtil.add(A, 1);
		int totalPage = (count % pageSize) > 0 ? B : A;
		// int totalPage = (count % pageSize) > 0 ? ((count / pageSize) + 1) :
		// (count / pageSize);
		int page = (int) (1 + Math.random() * (totalPage - 1 + 1));
		int C = (int) CalcUtil.sub(page, 1);
		int start = (int) CalcUtil.mul(C, pageSize);
		// int start = (page - 1) * pageSize;
		List<Seller> sList = datebaseDao.findPageList(Seller.class, model,
				start, pageSize);
		List<Map<String, Object>> dataList = new ArrayList<>();
		for (Seller seller : sList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", seller.getLogo() == null ? "" : RESOURCE_LOCAL_URL
					+ seller.getLogo());
			map.put("name", seller.getName());
			map.put("sellerId", seller.getId());
			dataList.add(map);
		}
		return dataList;

	}

	// 获得getSecondKill数据
	private List<Map<String, Object>> getSecondKill() {// cashshop_time表中内容

		List<CashshopTimes> cstlist = cashshopTimesDao.findTimesOfHQ();
		List<Map<String, Object>> dataList = new ArrayList<>();
		for (CashshopTimes cashshopTimes : cstlist) {
			String startTime = cashshopTimes.getStartTime();
			String endTime = cashshopTimes.getEndTime();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("secondKillId", cashshopTimes.getId());
			// map.put("secondKillName",startTime.substring(0,
			// startTime.length()-3) + " 专场");
			map.put("secondKillName",
					startTime.substring(0,
							(int) CalcUtil.sub(startTime.length(), 3))
							+ " 专场");
			map.put("startDate", startTime);
			map.put("endDate", endTime);

			dataList.add(map);
		}
		return dataList;

	}

	// 获得getSecondKill数据
	private Map<String, Object> getSecondKillByTimes(Integer times) {// cashshop_time表中内容

		List<CashshopTimes> cstlist = cashshopTimesDao.findTimesOfHQ();
		List<Map<String, Object>> dataList = new ArrayList<>();
		CashshopTimes cashshopTimes = cstlist.get(times);
		String startTime = cashshopTimes.getStartTime();
		String endTime = cashshopTimes.getEndTime();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("secondKillId", cashshopTimes.getId());
		// map.put("secondKillName",startTime.substring(0, startTime.length()-3)
		// + " 专场");
		map.put("secondKillName",
				startTime.substring(0,
						(int) CalcUtil.sub(startTime.length(), 3))
						+ " 专场");
		map.put("startDate", startTime);
		map.put("endDate", endTime);

		return map;

	}

	private List<Map<String, Object>> getLive(Integer zoneid) {// cashshop_time表中内容
		QueryModel queryModel = new QueryModel();
		ProvinceEnum enum1 = provinceEnumDao.get(zoneid);
		StringBuffer sb = new StringBuffer();
		
			queryModel.combPreEquals("adminUser.provinceEnum.id", zoneid,
					"adminUser");
		
		
		// 获得代理的直播列表
		queryModel.combPreEquals("isvalid", true);
		queryModel.combPreEquals("istop", true);
		Map<String, Object> data = new HashMap<String, Object>();
		List<SeLive> seliveList2 = dateBaseDAO.findPageList(SeLive.class,
				queryModel, 0, 4);
		List<Map<String, Object>> dataList = new ArrayList<>();
		int i = 0;
		for (SeLive sl : seliveList2) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("liveid", sl.getId());
			dataMap.put("name", sl.getLivename());
			dataMap.put("imgae", RESOURCE_LOCAL_URL + sl.getImage());
			// dataMap.put("uri", sl.getLiveUri());
			dataMap.put("uri", "");
			dataMap.put("logo",RESOURCE_LOCAL_URL+ sl.getSellerLogo());
			dataMap.put("livename", sl.getSellerName());
			dataList.add(dataMap);
			i++;
		}

		queryModel.clearQuery();
		if (enum1.getLevel()==2) {
			sb.append("  ( adminUser.provinceEnum.provinceEnum.id="+enum1.getId()+"" +" and '区'=SUBSTRING(REVERSE(adminUser.provinceEnum.name),1,1)").
			append(")");
			queryModel.combCondition(sb.toString());
		}else{
			queryModel.combPreEquals("adminUser.provinceEnum.id",
			zoneid, "adminUser");
		}
		
		queryModel.combPreEquals("isvalid", true);
		queryModel.combPreEquals("istop", true);
		
		if (i < 4) {
			List<SeLive> seliveList = dateBaseDAO.findPageList(SeLive.class,
					queryModel, i, 4);
			
			for (SeLive sl : seliveList) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("liveid", sl.getId());
				dataMap.put("name", sl.getLivename());
				dataMap.put("imgae", RESOURCE_LOCAL_URL + sl.getImage());
				// dataMap.put("uri", sl.getLiveUri());
				dataMap.put("uri", "");
				dataMap.put("logo", RESOURCE_LOCAL_URL + sl.getSellerLogo());
				dataMap.put("livename", sl.getSellerName());
				dataList.add(dataMap);
			}
		}

		return dataList;

	}

	// ==================获取新闻===============ZL==================//
	private List<Map<String, Object>> getNew(Integer zoneid,
			Map<Integer, Map<String, Object>> ctmap) {

		QueryModel queryModel = new QueryModel();
		queryModel.clearQuery();
		queryModel.combEquals("status", 1);
		queryModel.combEquals("zoneId", zoneid);
		queryModel.orPreEquals("adminuserId", 47);
		List<News> newsList = datebaseDao.findPageList(News.class, queryModel,
				0, pageSize);
		List<Map<String, Object>> dataList = new ArrayList<>();
		if (newsList != null && newsList.size() > 0) {
			for (News news : newsList) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("title",
						news.getTitle() == null ? "" : news.getTitle());
				dataMap.put("uri", news.getUrl() == null ? "" : news.getUrl());
				dataList.add(dataMap);
			}

		} else {
			queryModel.clearQuery();
			queryModel.combEquals("status", 1);
			queryModel.combEquals("adminuserId", 47);
			List<News> newsList1 = datebaseDao.findPageList(News.class,
					queryModel, 0, pageSize);
			for (News news1 : newsList1) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("title",
						news1.getTitle() == null ? "" : news1.getTitle());
				dataMap.put("uri", news1.getUrl() == null ? "" : news1.getUrl());
				dataList.add(dataMap);
			}
		}

		return dataList;
	}

	// 各地特产置顶
	private List<Map<String, Object>> getlocal(
			Map<Integer, Map<String, Object>> ctmap) {
		List<Map<String, Object>> dataList = new ArrayList<>();
		QueryModel model = new QueryModel();
		model.clearQuery();
		model.combPreEquals("isValid", true);
		model.combPreEquals("istop", true);
		model.combCondition("shelvesTime >= sysdate()");
		List<ReGoodsOfLocalSpecialtyMall> lsmlist = datebaseDao.findPageList(
				ReGoodsOfLocalSpecialtyMall.class, model, 0, pageSize);
		for (ReGoodsOfLocalSpecialtyMall localSpecialtyMall : lsmlist) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("goodsId", localSpecialtyMall.getGoodsOrder());
			dataMap.put("name", localSpecialtyMall.getSnapshotGoods().getName());
			dataMap.put("price", localSpecialtyMall.getPrice()==null?"":""+localSpecialtyMall.getPrice()+"");

			// 解析图片url
			String covers = localSpecialtyMall.getSnapshotGoods().getCoverPic();
			if (StringUtil.hasLength(covers) && covers.startsWith("[")) {
				JSONArray array = JSONArray.parseArray(covers);
				if (array.size() > 0) {
					covers = RESOURCE_LOCAL_URL
							+ array.getJSONObject(0).getString("imgUrl");
				}
			} else {
				covers = "";
			}

			dataMap.put("image", covers);
			dataList.add(dataMap);
		}
		return dataList;
	}
	
	//拼团商品置顶列表
	private List<Map<String, Object>> getTeam(Map<Integer, Map<String, Object>> ctmap){
		List<Map<String, Object>> dataList = new ArrayList<>();
		QueryModel queryModel = new QueryModel();
		queryModel.combPreEquals("isValid", true);
		queryModel.combPreEquals("topType", 2);
		queryModel.combPreEquals("isChecked", true);
		queryModel.combCondition("shelvesTime >= sysdate()");
		queryModel.combCondition("addedTime <= sysdate()");
		List<ReGoodsOfTeamMall> teamlist = datebaseDao.findPageList(ReGoodsOfTeamMall.class, queryModel, 0, pageSize);
		ReGoodsOfTeamMall goodsOfTeamMall = null;
		for (int i = 0; i < teamlist.size(); i++) {
			goodsOfTeamMall = teamlist.get(i);
			if (i>=9) {
				break;
			}
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("goodsId", goodsOfTeamMall.getGoodsOrder());
			dataMap.put("name", goodsOfTeamMall.getName());
			dataMap.put("price", goodsOfTeamMall.getReGoodsOfSellerMall().getPrice()==null?"":""+(CalcUtil.sub(goodsOfTeamMall.getReGoodsOfSellerMall().getPrice(), goodsOfTeamMall.getDiscountPrice()))+"");
			
			// 解析图片url        
			String covers = goodsOfTeamMall.getSnapshotGoods().getCoverPic();
			if (StringUtil.hasLength(covers) && covers.startsWith("[")) {
				JSONArray array = JSONArray.parseArray(covers);
				if (array.size() > 0) {
					covers = RESOURCE_LOCAL_URL
							+ array.getJSONObject(0).getString("imgUrl");
				}
			} else {
				covers = "";
			}

			dataMap.put("image", covers);
			dataList.add(dataMap);
		}
		 return dataList;
	}
	
	
	//拼团商品轮播图
	private List<Map<String, Object>> getTeamType (Map<Integer, Map<String, Object>> ctmap){
		List<Map<String, Object>> dataList = new ArrayList<>();
		QueryModel queryModel = new QueryModel();
		queryModel.combPreEquals("isValid", true);
		queryModel.combPreEquals("topType", 1);
		queryModel.combPreEquals("isChecked", true);
		queryModel.combCondition("shelvesTime >= sysdate()");
		List<ReGoodsOfTeamMall> teamlist = datebaseDao.findPageList(ReGoodsOfTeamMall.class, queryModel, 0, pageSize);
		try {
			ReGoodsOfTeamMall goodsOfTeamMall = null;
			for (int i = 0; i < teamlist.size(); i++) {
				goodsOfTeamMall = teamlist.get(i);
				if (i>=5) {
					break;
				}
				ReGoodsSnapshot snapshot = goodsOfTeamMall.getSnapshotGoods();
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("title", goodsOfTeamMall.getName());
				
				String type = snapshot.getType();
				if (StringUtil.hasLength(type) && type.startsWith("[") ) {
					JSONArray ar=JSONArray.parseArray(type);
					JSONObject object = ar.getJSONObject(0);
					if(object.size() > 0){
						type = object.getString("parentTypeId");
					}else{
						type= "";
					}
				}else{
					JSONObject object = JSONObject.parseObject(type);
					if (object.size()>0) {
						type = object.getString("parentTypeId");
					}else{
						type= "";
					}
				}
				dataMap.put("typeId", type);
				
				
				String covers = goodsOfTeamMall.getCarouselPic();
				if(StringUtils.isBlank(covers)){
					 covers = goodsOfTeamMall.getSnapshotGoods().getCoverPic();
					if (StringUtil.hasLength(covers) && covers.startsWith("[")) {
						JSONArray array = JSONArray.parseArray(covers);
						if (array.size() > 0) {
							covers = array.getJSONObject(0).getString("imgUrl");
						}
					} else {
						covers = "";
					}
				}
				dataMap.put("image",RESOURCE_LOCAL_URL+covers);
				dataMap.put("goodsId", goodsOfTeamMall.getGoodsOrder());
				dataList.add(dataMap);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}
	
	
	//周边店铺数量
	private String getStoreCount(){
		QueryModel queryModel = new QueryModel();
		queryModel.combPreEquals("isValid", true);
		int count = datebaseDao.findCount(ReGoodsOfSellerMall.class, queryModel);
		if (count!=0) {
			return String.valueOf(count);
		}
		return "0";
		
	}

	// ===========================================================//

	// 获得goodsClassifys数据
	private List<Map<String, Object>> getScoreGoodsClassifys() {// cashshop_goods_lable表中内容
		List<CashshopGoodsLable> cglList = cashshopGoodsLableDao
				.queryListByParameter("isValid", true);
		List<Map<String, Object>> dataList = new ArrayList<>();
		for (CashshopGoodsLable cgl : cglList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", cgl.getName());// 标签名称
			map.put("typeId", cgl.getId());// 标签id
			dataList.add(map);
		}
		return dataList;
	}

	private Map<Integer, Map<String, Object>> getCashshopType(String pid,
			String uri) {
		List<CashshopType> typelist = cashshopTypeDao.findImageText();
		Map<Integer, Map<String, Object>> ctMap = new HashMap<Integer, Map<String, Object>>();

		for (CashshopType cashshopType : typelist) {
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("image", cashshopType.getImg()==null?"":RESOURCE_LOCAL_URL+cashshopType.getImg());
			map.put("title", cashshopType.getName()==null?"":cashshopType.getName());

			//===============ZL===============//
				map.put("typeId", cashshopType.getCommodityType()==null?"":cashshopType.getCommodityType().getId());
			//==============================//

			if(cashshopType.getId()>25 && cashshopType.getId()<38 && StringUtils.isNotBlank(cashshopType.getUrl())){
				String cturi = uri+cashshopType.getUrl()+"&pid="+pid;

		
				map.put("uri", cturi);
			} else {
				map.put("uri", cashshopType.getUrl() == null ? ""
						: cashshopType.getUrl());
			}
			ctMap.put(cashshopType.getId(), map);

		}
		return ctMap;
	}
	
	public Map<Integer, Map<String, Object>> getCashshopType2(String pid,
			String uri, Integer version,HttpServletRequest request) {
		Map<Integer, Map<String, Object>> ctMap = new HashMap<Integer, Map<String, Object>>();
		
		RESOURCE_LOCAL_URL = request.getServletContext()
				.getAttribute("RESOURCE_LOCAL_URL").toString();
		
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter==null) {
				JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
			}
				List<CashshopType> typelist=new ArrayList<CashshopType>(28);
				
				Object typelistCache = CacheUtil.getCacheByName("cashshopTypeList");
				if(typelistCache!=null){
					typelist=(List<CashshopType>) typelistCache;
				}else{
					typelist = cashshopTypeDao.findImageTextByVersion(version);
					CacheUtil.setCache("cashshopTypeList", typelist);
				}
				
				for (CashshopType cashshopType : typelist) {
					
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("remark",cashshopType.getRemark()==null?"":cashshopType.getRemark());
					map.put("image", cashshopType.getImg() == null ? ""
							: RESOURCE_LOCAL_URL + cashshopType.getImg());
					
					//RESOURCE_LOCAL_URL 是正式服务器的地址  现在换成测试服务器地址  
					map.put("title",
							cashshopType.getName() == null ? "" : cashshopType
									.getName());
					map.put("typeId", cashshopType.getCommodityType()== null ? ""
							: cashshopType.getCommodityType().getId());
					//==============================ZL===============================//
					if (cashshopType.getId() > 53 && cashshopType.getId() < 67|| cashshopType.getId()==80||cashshopType.getId()==81
							&& StringUtils.isNotBlank(cashshopType.getUrl())) {
						//String cturi = uri + cashshopType.getUrl() + "&pid=" + pid;
						map.put("uri","");
					} else {
						if(cashshopType.getId()==79){
							String userId = parameter.getUserId();
							String zoneId = parameter.getZoneId();
							if (StringUtil.hasLength(userId)) {
								String hhuri = cashshopType.getUrl()+"?user_id="+ userId +"&zone_id="+zoneId;
								map.put("uri", hhuri == null ? "":hhuri);
							}
						}
					}
					if(cashshopType.getId()==75){
						map.put("uri", cashshopType.getUrl());
					}
					ctMap.put(cashshopType.getId(), map);

				}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ctMap;
	}

	// 获得getImageTexts数据
	private List<Map<String, Object>> getImageTexts(int type, int zoneid,
			String taoke) {// cashshop_type 表中内容
		
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>(); // 返回值；
		List<CashshopType> typelist = null;
		if (type == 1) {
			ProvinceEnum zone = provinceEnumDao.getCurrentCity(zoneid);
			typelist = cashshopTypeDao.findImageText(type, zone.getId());
			if (typelist == null || typelist.size() == 0) {

				typelist = cashshopTypeDao.findImageText(type, zone
						.getProvinceEnum().getId());
				if (typelist == null || typelist.size() == 0) {
					typelist = cashshopTypeDao.findImageText(type);
				}
			}
		} else {
			typelist = cashshopTypeDao.findImageText(type);
		}
		AdminuserZoneTaoke azk = null;
		azk = adminuserZoneTaokeService.getAdminuserZoneTaokeByZone(zoneid);
		if (azk == null) {
			ProvinceEnum zone = provinceEnumDao.getCurrentCity(zoneid);
			azk = adminuserZoneTaokeService.getAdminuserZoneTaokeByZone(zone
					.getId());
		}
		for (CashshopType cashshopType : typelist) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", cashshopType.getImg() == null ? ""
					: RESOURCE_LOCAL_URL + cashshopType.getImg());
			map.put("name",
					cashshopType.getName() == null ? "" : cashshopType
							.getName());
			if (azk != null && (type == 3 || type == 4 || type == 5)) {
				map.put("uri", azk.getUri() + "?from=axp");

			} else {
				map.put("uri", cashshopType.getUrl() == null ? ""
						: cashshopType.getUrl());
			}

			if (type == 13 || type == 14 || type == 15) {
				String uri = "http://www.518wtk.com";
				if (azk != null) {
					uri = azk.getUri();
				}
				map.put("uri", uri + taoke);

			}

			map.put("typeId", cashshopType.getId());
			dataList.add(map);
		}
		return dataList;
	}

	// 获得getImageTexts数据
	private Map<String, Object> getImageTextsNew(String image, String name,
			String taoke, String title) {// cashshop_type 表中内容

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>(); // 返回值；
		List<CashshopType> typelist = null;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("image", image);
		map.put("name", name);
		map.put("uri", taoke);
		map.put("title", title);

		return map;
	}

	// 获得getImageTexts数据
	private List<Map<String, Object>> getImageTextsForPid(int type, int zoneid,
			String taoke) {// cashshop_type 表中内容

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>(); // 返回值；
		List<CashshopType> typelist = null;
		if (type == 1) {
			ProvinceEnum zone = provinceEnumDao.getCurrentCity(zoneid);
			typelist = cashshopTypeDao.findImageText(type, zone.getId());
			if (typelist == null || typelist.size() == 0) {

				typelist = cashshopTypeDao.findImageText(type, zone
						.getProvinceEnum().getId());
				if (typelist == null || typelist.size() == 0) {
					typelist = cashshopTypeDao.findImageText(type);
				}
			}
		} else {
			typelist = cashshopTypeDao.findImageText(type);
		}

		for (CashshopType cashshopType : typelist) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", cashshopType.getImg() == null ? ""
					: RESOURCE_LOCAL_URL + cashshopType.getImg());
			map.put("name",
					cashshopType.getName() == null ? "" : cashshopType
							.getName());

			if (type == 13 || type == 14 || type == 15) {
				String uri = "http://www.518wtk.com";

				map.put("uri", uri + taoke + "");

			}

			map.put("typeId", cashshopType.getId());
			dataList.add(map);
		}
		return dataList;
	}

	// 获得getImageTexts数据
	private List<Map<String, Object>> getImageTexts(String types) {// cashshop_type
																	// 表中内容
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>(); // 返回值；
		QueryModel model = new QueryModel();
		model.combCondition("isValid=true and imageType.id in (" + types + ")");
		List<CashshopType> typelist = datebaseDao.findLists(CashshopType.class,
				model);
		for (CashshopType cashshopType : typelist) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", cashshopType.getImg() == null ? ""
					: RESOURCE_LOCAL_URL + cashshopType.getImg());
			map.put("name",
					cashshopType.getName() == null ? "" : cashshopType
							.getName());
			map.put("url",
					cashshopType.getUrl() == null ? "" : cashshopType.getUrl());
			map.put("typeId", cashshopType.getId());
			dataList.add(map);
		}
		model = null;
		return dataList;
	}

	@Override
	public void getGoodsDetail(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {
			try {
				response.getWriter().write("暂无详情");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String goodsId = parameter.getData().getString("goodsId");
		ReBaseGoods good = (ReBaseGoods) reGoodsOfBaseService.getMall(goodsId);
		QueryModel queryModel = new QueryModel();
		queryModel.combPreEquals("goods.id", good.getBaseGoodsId(), "goodsId");
		List<ReGoodsDetails> detailList = datebaseDao.findLists(
				ReGoodsDetails.class, queryModel);
		try {
			if (detailList.size() >= 1) {
				ReGoodsDetails details = detailList.get(0);
				// response.getWriter().write(details.getContent());
				request.setAttribute("content", details.getContent());
			} else {
				request.setAttribute("content", "暂无信息");
				// response.getWriter().write("暂无信息");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, Object> secondKillMall(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		statusMap.put("status", 0x01);
		statusMap.put("message", "请求成功");
		statusMap.put("data", dataMap);
		dataMap.put("dataList", getSecondKill());
		return statusMap;
	}

	@Override
	public Map<String, Object> getSlide(HttpServletRequest request, Integer type) {
		RESOURCE_LOCAL_URL = request.getServletContext()
				.getAttribute("RESOURCE_LOCAL_URL").toString();
		Map<String, Object> statusMap = new HashMap<String, Object>();
		statusMap.put("status", 0x01);
		statusMap.put("message", "请求成功");
		statusMap.put("data", getHeadBanner(type));
		return statusMap;
	}

	@Override
	public Map<String, Object> getHomeNew(Integer userid, Integer zoneid,
			Double lat, Double lng, String os, String channelId,
			String appVersion, String dip, String machineCode, Integer type,
			HttpServletRequest request) {
		RESOURCE_LOCAL_URL = request.getServletContext()
				.getAttribute("RESOURCE_LOCAL_URL").toString();
		Map<String, Object> statusMap = new HashMap<String, Object>();

		String pid = "";
		String uri = StringUtil.TAOKEURI;

		if (userid != null && userid > 0) {
			Users user = usersDao.findById(userid);
			pid = getParentUsersPidByUsers(user);
		} else {
			userid = -1;
		}

		if (StringUtils.isBlank(pid) && zoneid != null) {
			AdminuserZoneTaoke azk = getCityTokePidByZoneId(zoneid);
			pid = azk.getBak_uri();

		}
		if (StringUtils.isBlank(pid)) {
			pid = StringUtil.TAOKEPID;
		}


		Map<Integer, Map<String,Object>> ctmap=getCashshopType(pid,uri);//增加类别Id

	

		statusMap.put("status", 0x01);
		statusMap.put("message", "请求成功");
		statusMap.put(
				"data",
				getDataNew(userid, type, zoneid, appVersion, ctmap, os,
						appVersion, pid));
		return statusMap;
	}

	
	public Map<String, Object> getHomeNew5142(Integer userid, Integer zoneid,
			Double lat, Double lng, String os, String channelId,
			String appVersion, String dip, String machineCode, Integer type,
			HttpServletRequest request,String pid,String pddpid,Integer pageIndex) throws Exception  {
		RESOURCE_LOCAL_URL = request.getServletContext()
			.getAttribute("RESOURCE_LOCAL_URL").toString();
		Map<String, Object> statusMap = new HashMap<String, Object>();
		
		 String uri = StringUtil.TAOKEURI;
		
		Map<Integer, Map<String, Object>> ctmap = getCashshopType2(pid, uri, 2,request);

		statusMap.put("status", 0x01);
		statusMap.put("message", "请求成功");
	
			statusMap.put(
					"data",
					
					
					getDataNew5142(request,userid, type, zoneid, lat, lng, appVersion, ctmap, os, appVersion, pid, dip, pddpid,pageIndex)
					);
			
		return statusMap;
	}
	
	public Map<String, Object> getHomeNew104(Integer userid, Integer zoneid,
			Double lat, Double lng, String os, String channelId,
			String appVersion, String dip, String machineCode, Integer type,
			HttpServletRequest request,String pid,String pddpid,Integer pageIndex) throws Exception  {
		RESOURCE_LOCAL_URL = request.getServletContext()
			.getAttribute("RESOURCE_LOCAL_URL").toString();
		Map<String, Object> statusMap = new HashMap<String, Object>();
		
		 String uri = StringUtil.TAOKEURI;
		
		Map<Integer, Map<String, Object>> ctmap = getCashshopType2(pid, uri, 2,request);

		statusMap.put("status", 0x01);
		statusMap.put("message", "请求成功");
	
			statusMap.put(
					"data",
					/*getDataNew5142(userid, type, zoneid,lat,lng, appVersion, ctmap, os,
							appVersion, pid,dip,pddpid,request)*/
					
					
					getDataNew104(request,userid, type, zoneid, lat, lng, appVersion, ctmap, os, appVersion, pid, dip, pddpid,pageIndex)
					);
			
		return statusMap;
	}
	
	@Cacheable(value="axpPidCache",key="#userid+#zoneId",condition="#userid!=null")
	public String findPid(Integer userid,String pid,Integer zoneId){
		if(userid!=null&&userid>0){
			Users user = usersDao.findById(userid);
			QueryModel queryModel=new QueryModel();
			queryModel.combPreEquals("isValid", true);
			queryModel.combCondition("users.id >0");
			//所有pid
			List<TkldPid> tkldPidAll = datebaseDao.findLists(TkldPid.class, queryModel);
			Map<Integer, Object> allPid=new HashMap<Integer, Object>();
			for (TkldPid t : tkldPidAll) {
				//如果用户本身就是合伙人 或者事业合伙人
				if(t.getUsers().getId().equals(userid)){
					pid=t.getpId().trim()+"-"+t.getPddPid().trim();
					return pid;
				}
				allPid.put(t.getUsers().getId(), t);
			}
				
				pid=findParentPid(user,allPid,0);
				//System.out.println("粉丝系列："+pid);
				//如果没有找到上级 就根据用户所在的地区找 
				if(StringUtils.isBlank(pid)&&zoneId!=null&&zoneId>-1){
					
						ProvinceEnum pe = provinceEnumDao.findById(zoneId);
						Integer provinceid = 1961;
						if(pe.getLevel2()==3){//区；或者城区县
							provinceid= pe.getProvinceEnumId();
						}else if(pe.getLevel2()==2){//县，县级市
							provinceid=pe.getId();
						}
						
						queryModel.clearQuery();
						//找到当前地区的事业合伙人
						queryModel.combPreEquals("isValid", true);
						queryModel.combPreEquals("provinceEnum.id", provinceid,"zoneId");
						queryModel.combEquals("level", 2);
						//事业合伙人	区级
						List<TkldPid> dList  = dateBaseDAO.findLists(TkldPid.class, queryModel);
						if(dList!=null && dList.size()>0){
							pid=dList.get(0).getpId()+"-"+dList.get(0).getPddPid();
							
							System.out.println("用户在"+pe.getName()+"未找到粉丝关系对应事业伙人，自动获取当地兜底联盟商户"+dList.get(0).getUsers().getId());
						}
				}
		}
		if(StringUtils.isBlank(pid)){
			pid="mm_111685502_17728608_64252042-1001389_11743082";
		}
		return pid.trim();
	}
	
	
	

	
	
	private Map<Integer, TkldPid> allTkldPid(){
		Map<Integer, TkldPid> map=new HashMap<Integer, TkldPid>();
		List<TkldPid> allTkldPid=tkldPidDao.queryAllByIsValid2();
		for (TkldPid tkldPid : allTkldPid) {
			map.put(tkldPid.getUsers().getId(), tkldPid);
			map.put(tkldPid.getAdminUser().getId(), tkldPid);
		}
		return map;
		
	}
	
	
	//通过递归方式查找商家用户的pid；找到为止，限制15层级
			public TkldPid getPidForNew(Users users,int times){
				TkldPid tp =null;
				
				if(times>15){
					return tp;
				}
			
				if(users.getInvitecode()!=null){
					times++;
					List<Users> parentUser3 = usersDao.findUsersByInvitecode(users.getInvitecode());
					if(parentUser3!=null && parentUser3.size()>0){
						 Users	pUsers3 = parentUser3.get(0);
						 
						 List<TkldPid> tjld = tkldPidDao.findByPropertyIsValid("users.id", pUsers3.getId());
						 
						 
							if(tjld !=null && tjld.size()>0){
								return tjld.get(0);
							}else{
								return tp=getPidForNew(pUsers3,times);
							}
						}
				}
				
				
				return tp;
			}
	
	
	
	public String findParentPid(Users users, Map<Integer, Object> map,Integer times) {
		String pid = "";
		TkldPid tp= getPidForNew(users,0);
		if(tp!=null){
			pid = tp.getpId()+"-"+tp.getPddPid();
		}
		return pid;
		
	}

/*	@Override
	public Map<String, Object> getHomeNew3(Integer userid, Integer zoneid, Double lat, Double lng, String os,
			String channelId, String appVersion, String dip, String machineCode, Integer type,
			HttpServletRequest request, String pid) {
		RESOURCE_LOCAL_URL = request.getServletContext()
				.getAttribute("RESOURCE_LOCAL_URL").toString();
			Map<String, Object> statusMap = new HashMap<String, Object>();
			
			 String uri = StringUtil.TAOKEURI;
			
			Map<Integer, Map<String, Object>> ctmap = getCashshopType2(pid, uri, 2,request);

			statusMap.put("status", 0x01);
			statusMap.put("message", "请求成功");
			statusMap.put(
					"data",
					getDataNew3(userid, type, zoneid, appVersion, ctmap, os,
							appVersion, pid,dip));
			return statusMap;
	}
	*/
	
	public Map<String, Object> getDataNew3(Integer userId, Integer type,
			Integer zoneId, String appVersion,
			Map<Integer, Map<String, Object>> ctmap, String os, String v,
			String pid,String dip) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer ids = 0;

		map.put("sqgw", getTaoke3(ctmap,pid));// taoke 省钱购物版块三大导航
		
		map.put("searchurl",StringUtil.TAOKEURI+ "/index.php?s=/NewIndex/search&state=app&from=axp&istao=1&source=axp&pid="+ pid);// 淘客搜索

		if (zoneId != null) {
			ProvinceEnum zone = provinceEnumDao.getCurrentCity2(zoneId);
			if (zone != null) {
				ids = zone.getId();
				if (ids == 1961 || ids == 1132 || ids == 1043 || zoneId == 1119
						|| ids == 844 || ids == 864 || ids == 202) {
					// 换货会 map
					//map.put("exchange", getExchange2(ctmap, userId, zoneId));// taoke
				}
				// 直播map
				map.put("livemodule", getLives2(ids, ctmap,userId));
				// 顶部图片map
				map.put("homeTopImages",
						getNewHeadBanner(Slides.HOME1, zone.getId()));// 首页轮播图
			}
		}

		// 一县一品
		map.put("featureType", getFeatureType2(ctmap));
		// 顶部分类
		map.put("topClassify", getTopClassifys(ctmap,dip,userId));
		map.put("storesTotalPage", getStoreCount());
		// 特产置顶商品
		map.put("toplocal", getlocal(ctmap));// 特产置顶
		//底部广告图
		
		map.put("homeBottomBanner",ctmap.get(90));
		//淘客PID
		map.put("pid", pid);
		//拼团置顶商品列表
		map.put("ptGoods", getTeam(ctmap));
		//拼团置顶轮播图
		map.put("ptImages", getTeamType(ctmap));
		//拼团背景图
		map.put("ptBgImageUrl", "http://www.jph.aixiaoping.cn:8080/jupinhuiRes/upload-res/basegoods_detail/498/nomal/7249660325101055819.png");
		
		//da  新增  pddyhq
		

		map.put("scoreExchange", "");		//积分兑换模块
		map.put("sellerActivity","");//商家活动模块
		return map;
	}
	
	
	//--------da
	public List<Map<String,Object>> pddDiscountGoods(){
		
		List<Map<String, Object>> List = new ArrayList<Map<String, Object>>();
		Map<String,String> signMap = new HashMap<String,String>();
		signMap.put("client_id", StringUtil.pdd_client_id);
		String sign = MD5Util.getSign(signMap, StringUtil.pdd_client_screct);
		String url = "http://open.aixiaoping.cn:8080/open/api/product?data={\"client_id\":\""+StringUtil.pdd_client_id+"\",\"sign\":\""+sign+"\",\"data\":{\"search\":\"\"}}";
		
		
		try {
			//把所有的字段全部改成和淘宝字段一样的
			Map<String, Object>  goodMap =   UrlUtil.getTaoKeToMap(url);
			Map<String,Object> result = (Map<String, Object>) goodMap.get("result");
			List<Map<String,Object>> good = (List<Map<String, Object>>) result.get("products");
			for(int i = 0;i<good.size();i++){
				Map<String,Object> map = new HashMap<String,Object>();
				if(good.get(i).get("coupon_total_quantity")==null || good.get(i).get("coupon_remain_quantity") == null 
						|| good.get(i).get("min_group_price") == null || good.get(i).get("promotion_rate") == null || good.get(i).get("coupon_discount") == null){
					 continue;
				}
				String min_group_price = good.get(i).get("min_group_price").toString();
				Integer quan_receive = Integer.valueOf((int)CalcUtil.sub(Integer.valueOf(good.get(i).get("coupon_total_quantity").toString()), Integer.valueOf(good.get(i).get("coupon_remain_quantity").toString())));
				if(quan_receive < 0){
					//把剩余券数当成总券数
					map.put("quan_surplus", good.get(i).get("coupon_total_quantity").toString());//< 优惠券剩余数量
					Integer quan_receive_va = Integer.valueOf((int)CalcUtil.sub(Integer.valueOf(good.get(i).get("coupon_remain_quantity").toString()), Integer.valueOf(good.get(i).get("coupon_total_quantity").toString())));
					map.put("quan_receive", String.valueOf(quan_receive_va));//< 已领券=总-剩余数量
				}else{
					map.put("quan_surplus", good.get(i).get("coupon_remain_quantity").toString());//< 优惠券剩余数量 
					map.put("quan_receive", String.valueOf(quan_receive));//< 已领券=总-剩余数量
				}
				Double cutPrice = Double.valueOf(good.get(i).get("coupon_discount").toString())/100;
				Double price = CalcUtil.sub(Double.valueOf(good.get(i).get("min_group_price").toString())/100, cutPrice);
				Double earnMoney = CalcUtil.mul(price, Double.valueOf(good.get(i).get("promotion_rate").toString())/1000,2);
				earnMoney = CalcUtil.mul(earnMoney, 0.4,2);
				map.put("goods_id", good.get(i).get("goods_id").toString());//<商品id
				map.put("d_title", good.get(i).get("goods_name").toString());//< 商品名称
				map.put("org_price", String.valueOf(Double.valueOf(min_group_price)/100));//  原价--最小团买价格
				map.put("quan_price", String.valueOf(Double.valueOf(good.get(i).get("coupon_discount").toString())/100));//< 优惠券金额
				map.put("price", String.valueOf(price));//现价即券后价 = 最小团价- 券价
				map.put("quan_surplus", good.get(i).get("coupon_remain_quantity").toString());//< 优惠券剩余数量 
				map.put("cut_price", String.valueOf(cutPrice));//券价
				map.put("pic", good.get(i).get("goods_thumbnail_url"));//< 商品图片
				map.put("earnMoney", String.valueOf(earnMoney));//< 赚多少 =最小团价-券价 )*佣金比例goods.add(map);
				map.put("quan_receive", String.valueOf(quan_receive));//< 已领券=总-剩余数量
				
				List.add(map);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return List;
		
	}
	
	
	

	@Override
	public Map<String, Object> getTeamGoodsList(HttpServletRequest request, HttpServletResponse response,Integer zoneId,Integer typeId,Integer pageIndex,String search) {
		Map<String, Object> statusMap = new HashMap<>();
		Map<String, Object> dataMap = new HashMap<>();
		String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
		QueryModel queryModel = new QueryModel();
		queryModel.combPreEquals("isValid", true);
		queryModel.combPreEquals("isChecked", true);
		String parentTypeId = "";
		String childTypeId ="";
		if (typeId!=0) {
			
			CommodityType commodityType = commodityTypeDao.findById(typeId);
			if (commodityType!=null && commodityType.getName().equals("全部")) {
				parentTypeId = "\"parentTypeId\""+":\""+commodityType.getCommodityType().getId().toString()+"\"";
			}else{
				parentTypeId = "\":"+typeId+",\"";
				childTypeId = "\":"+typeId+",\"";
			}
			queryModel.combCondition("( snapshotGoods.type like '%"+parentTypeId+"%' or snapshotGoods.type like '%"+childTypeId+"%')");
		}
		
		
		if (StringUtils.isNotBlank(search)) {
			queryModel.combCondition("(snapshotGoods.name like '%" + search + "%')");
		}
		int page=pageItemCount;
		int	count = 0;
		int count1 = 0;
		int count2 = 0;
		int teamCount =0;
		int teamCount1 =0;
		int teamCount2 = 0;
		int start = (pageIndex - 1) * page;
		
		if(zoneId==null || zoneId==0 || zoneId==-1){
			zoneId=1961;
		}
		ProvinceEnum zone = provinceEnumDao.findById(zoneId);
		if(zone.getLevel2()==2){
			queryModel.combCondition(" snapshotGoods.seller.provinceEnum.id= "+zone.getId());
		}else{
			queryModel.combCondition(" snapshotGoods.seller.provinceEnum.provinceEnum.id= "+zone.getProvinceEnumId());
		}
		queryModel.combCondition("( transportationType =3 or transportationType =4)");
		
		List<ReGoodsOfTeamMall> rgTeamMallList2 = new ArrayList<ReGoodsOfTeamMall>();
		List<ReGoodsOfTeamMall> rgTeamMallList3 = new ArrayList<ReGoodsOfTeamMall>();
		
		count1 = dateBaseDAO.findCount(ReGoodsOfTeamMall.class, queryModel);
		teamCount1 = dateBaseDAO.findCount(ReGoodsOfTeamMall.class, queryModel);
		rgTeamMallList2 = dateBaseDAO.findPageList(ReGoodsOfTeamMall.class, queryModel, start, page);
		
		queryModel.clearQuery();
		queryModel.combPreEquals("isValid", true);
		queryModel.combPreEquals("isChecked", true);
		queryModel.combCondition("( transportationType =1 or transportationType =2)");
		if (typeId!=0) {
			queryModel.combCondition("( snapshotGoods.type like '%"+parentTypeId+"%' and snapshotGoods.type like '%"+childTypeId+"%')");
		}
		
		count2 = dateBaseDAO.findCount(ReGoodsOfTeamMall.class, queryModel);
		teamCount2 = dateBaseDAO.findCount(ReGoodsOfTeamMall.class, queryModel);
		rgTeamMallList3 = dateBaseDAO.findPageList(ReGoodsOfTeamMall.class, queryModel, start, page);
		
		count = (int) CalcUtil.add(count1, count2);
		teamCount = (int) CalcUtil.add(teamCount1, teamCount2);
		
		rgTeamMallList2.addAll(rgTeamMallList3);
		
		List<Map<String, Object>> dataList = new ArrayList<>();
		if (rgTeamMallList2!=null && rgTeamMallList2.size()>0) {
			for (ReGoodsOfTeamMall goods : rgTeamMallList2) {
				String covers = goods.getSnapshotGoods().getCoverPic();
				if(StringUtil.hasLength(covers)&&covers.startsWith("[")){
					JSONArray array = JSONArray.parseArray(covers);
					if(array.size()>0){
						covers = basePath+array.getJSONObject(0).getString("imgUrl");
					}
				}else{
					covers="";
				}
				Map<String, Object> goodMap = new HashMap<>();
				goodMap.put("salesvolume", goods.getReGoodsOfSellerMall().getSalesVolume());//已团数量
				goodMap.put("name", goods.getSnapshotGoods().getName());
				goodMap.put("price", CalcUtil.sub(goods.getReGoodsOfSellerMall().getPrice(), goods.getDiscountPrice()));
				goodMap.put("coverPic", covers);
				goodMap.put("goodsId", goods.getGoodsOrder());
				dataList.add(goodMap);
			}
		}
			if(teamCount>0){ 
				count=teamCount;
			}
			int totalPage = (count % page) > 0 ? ((count / page) + 1) : (count / page);
			
			dataMap.put("dataList", dataList);
			dataMap.put("pageSize", totalPage);
			
			dataMap.put("pageIndex", pageIndex);
			dataMap.put("pageItemCount", pageItemCount);
	
			statusMap.put("data", dataMap);
			statusMap.put("status", 0x01);
			statusMap.put("message", "请求成功");
		return statusMap;
	}

	//获取拼团分类（小程序）
	@Override
	public Map<String,Object> getTeamGoodsOfType(HttpServletRequest request){
		RESOURCE_LOCAL_URL = request.getServletContext()
				.getAttribute("RESOURCE_LOCAL_URL").toString();
			Map<String, Object> statusMap = new HashMap<>();
			Map<String, Object> dataMap = new HashMap<>();
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>(); 
			QueryModel model = new QueryModel();
			model.combPreEquals("isValid", true);
			model.combPreEquals("level", 1);
			model.setOrder("id desc");
			
			List<CommodityType> ctlist = datebaseDao.findLists(CommodityType.class, model);
			
			List<CommodityType> ctlist1=new ArrayList<CommodityType>();
			
			for(CommodityType s : ctlist){
				if (!s.getName().equals("全部")) {
					Map<String,Object> lv1map = new HashMap<String, Object>();
					lv1map.put("typeId", s.getId());
					lv1map.put("typeName", s.getName());
					lv1map.put("typeImage", RESOURCE_LOCAL_URL+s.getImgUrl());
					list.add(lv1map);
				}
			}
			
			dataMap.put("typeList", list);
	
			statusMap.put("data", dataMap);
			statusMap.put("status", 0x01);
			statusMap.put("message", "请求成功");
			return statusMap;
		}
	
	//获取拼团页轮播图（小程序）
	@Override
	public Map<String, Object> getSlidesOfBannser(HttpServletRequest request, HttpServletResponse response, Integer adminuserId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bannerlist", getBannersList(adminuserId,request,Slides.HOME1));
		return map;
	}

	
	public List< Map<String, Object>> getBannersList (Integer adminuserId,HttpServletRequest request,Integer type){
		RESOURCE_LOCAL_URL = request.getServletContext()
				.getAttribute("RESOURCE_LOCAL_URL").toString();
		List<Slides> slist = null;
		slist = slidesDao.getsListByAdminUserId(adminuserId,type);
		if (slist.size()==0) {
			slist =slidesDao.getsListByAdminUserId(47,type);
		}
		List<Map<String, Object>> dataList = new ArrayList<>();
		Slides slides = null;
		for (int i = 0; i < slist.size(); i++) {
			slides=slist.get(i);
			if (i==5) {
				break;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", slides.getImgurls() == null ? "": RESOURCE_LOCAL_URL + slides.getImgurls());
			map.put("uri", slides.getLinkurl());
			map.put("name", slides.getName());
			dataList.add(map);
		}
	
		return dataList;
	}
	
	/**
	 * 获取热门商品关键字
	 * @param request
	 * @param response
	 * @return
	 */
	public Map<String, Object> getGoodsKeywords(HttpServletRequest request, HttpServletResponse response){
	
		
		List<String> list=new ArrayList<>(15);
		list.add("剃须刀"); list.add("Ｔ恤衫"); list.add("手表"); list.add("手机");
		list.add("香港脚"); list.add("指甲钳"); list.add("西瓜刀"); list.add("菜刀");
		list.add("口红"); list.add("益达100g"); list.add("宝刀不老"); list.add("光明顶");
		list.add("小明"); list.add("绿帽子(爆款)"); list.add("西瓜太郎专属头盔"); 
		
		Map<String, Object> map=new HashMap<>();
	 	 map.put("dataList", list);
	return	JsonResponseUtil.getJson(1, "请求成功",map);
		
		//return map;
	}

	
	
	
	
	
	@Override
	public Map<String, Object> getXCXHome(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String userId = request.getParameter("userId");
		if(userId == null || userId == ""){
			userId = "-1";
		}
		String lats = request.getParameter("lat");
		String lngs = request.getParameter("lng");
        String dip = request.getParameter("dip")==null?"360":request.getParameter("dip");
        try {
        	Map<String,Double> map = CityUtil.map_tx2bd(Double.valueOf(lats), Double.valueOf(lngs));
        	
			Double lat = map.get("lat");
			Double lng = map.get("lng");
			
			Map<String,Object> result = (Map<String, Object>) UrlUtil.getBaiduMapToXCX(String.valueOf(lat), String.valueOf(lng)).get("addressComponent");
			
			String province =  result.get("province")==null ? "":result.get("province").toString(); //省
			String city = result.get("city")==null?"":result.get("city").toString(); //城市
			String district = result.get("district")==null?"":result.get("district").toString();//区
			
			QueryModel model = new QueryModel();
			model.clearQuery();
			model.combPreLike("name", city);
			List<ProvinceEnum> enumList = dateBaseDAO.findLists(ProvinceEnum.class, model);
			ProvinceEnum enums = new ProvinceEnum();
			if(enumList!=null && enumList.size()>0){
				enums = enumList.get(0);
			}
			//获取到地址的
			GameActivity game = gameActivityService.getZoneId(enums.getId());
			Map<String,Object> data = new HashMap<String,Object>();
			List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
			
			// 顶部图片map
			Map<String, Object> homeTopImages = new HashMap<String,Object>();
			homeTopImages.put("homeTopImages",getNewHeadBanner(Slides.HOME1,enums.getId()));
			dataList.add(homeTopImages);
			
			//缓存信息
			Map<Integer, Map<String, Object>> ctmap = getCashshopType2(null, null, 2,request);
			
			//分类
			Map<String, Object> topClassify = new HashMap<String,Object>();
			topClassify.put("topClassify", getTopClassifyXCX(game, ctmap, dip, Integer.valueOf(userId)));
			dataList.add(topClassify);
			//99特惠
			Map<String, Object> twoAdvertScoreModule = new HashMap<String,Object>();
			twoAdvertScoreModule.put("twoAdvertScoreModule", getscoreExchangeXCX(ctmap,4,lat,lng));
			dataList.add(twoAdvertScoreModule);
			//分类下来的一个图片和2个商品
			Map<String, Object> oneAdvertScoreModule = new HashMap<String,Object>();
			oneAdvertScoreModule.put("oneAdvertScoreModule", getscoreExchangeXCX(ctmap,3,lat,lng));
			dataList.add(oneAdvertScoreModule);
			//中间两张图
			Map<String, Object> twoAdverts = new HashMap<String,Object>();
			twoAdverts.put("twoAdverts", getsellerActivity(ctmap,null));
			dataList.add(twoAdverts);
//			//人气推荐
			Map<String, Object> bottomScoreModule = new HashMap<String,Object>();
			bottomScoreModule.put("bottomScoreModule", getscoreExchangeXCX(ctmap,6,lat,lng));
			dataList.add(bottomScoreModule);
			//周边店铺
			Map<String, Object> scoreGoodsMap = new HashMap<String,Object>();
			List<Map<String, Object>> scoreGoodList = sellerService.getSellerListBySearch2(0, null, "xcx", Integer.valueOf(userId), 1961, null, null, lat, lng, request, null);
			scoreGoodsMap.put("scoreGoodList", scoreGoodList);
			dataList.add(scoreGoodsMap);
			data.put("city", city);
			data.put("zoneId", enums.getId());
			data.put("dataList", dataList);
			return JsonResponseUtil.getJson(1, "请求成功", data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonResponseUtil.getJson(-1, "请求处理异常");
		}
	}



	

	

	
}