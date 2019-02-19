package com.axp.service.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONObject;
import com.axp.dao.CashshopTypeDAO;
import com.axp.dao.DateBaseDAO;
import com.axp.dao.IReBackOrderDao;
import com.axp.dao.IReGoodsOfLocalSpecialtyMallDao;
import com.axp.dao.IReGoodsOfScoreMallDao;
import com.axp.dao.IReGoodsOfSeckillMallDao;
import com.axp.dao.IReGoodsOfSellerMallDao;
import com.axp.dao.ISellerMoneyRecordDao;
import com.axp.dao.IUsersDao;
import com.axp.dao.ProvinceEnumDAO;
import com.axp.dao.ReBaseGoodsDAO;
import com.axp.dao.ReGoodsorderDao;
import com.axp.dao.SellerDAO;
import com.axp.dao.SellerMainPageDAO;
import com.axp.dao.ShopConcernDao;
import com.axp.domain.AdminUser;
import com.axp.domain.CashshopTimes;
import com.axp.domain.CommodityType;
import com.axp.domain.Genocoding;
import com.axp.domain.ProvinceEnum;
import com.axp.domain.ReBaseGoods;
import com.axp.domain.ReGoodsDetails;
import com.axp.domain.ReGoodsOfBase;
import com.axp.domain.ReGoodsOfChangeMall;
import com.axp.domain.ReGoodsOfExtendActiviy;
import com.axp.domain.ReGoodsOfLocalSpecialtyMall;
import com.axp.domain.ReGoodsOfLockMall;
import com.axp.domain.ReGoodsOfScoreMall;
import com.axp.domain.ReGoodsOfSeckillMall;
import com.axp.domain.ReGoodsOfSellerMall;
import com.axp.domain.ReGoodsOfTeamMall;
import com.axp.domain.ReGoodsSnapshot;
import com.axp.domain.ReGoodsStandard;
import com.axp.domain.ReGoodsofextendmall;
import com.axp.domain.ReGoodsorder;
import com.axp.domain.SeLive;
import com.axp.domain.Seller;
import com.axp.domain.SellerMPbyJson;
import com.axp.domain.SellerMainPage;
import com.axp.domain.SellerMainPbyJson;
import com.axp.domain.ShopCategory;
import com.axp.domain.ShopConcern;
import com.axp.domain.TkldPid;
import com.axp.domain.Users;
import com.axp.service.IMallListService;
import com.axp.service.IReGoodsOfBaseService;
import com.axp.service.IReGoodsOfChangeMallService;
import com.axp.service.IReGoodsOfLockMallService;
import com.axp.service.ISellerService;
import com.axp.service.ReGoodsOfTeamMallService;
import com.axp.service.ReGoodsofextendmallService;
import com.axp.service.TkldPidService;
import com.axp.util.CacheUtil;
import com.axp.util.CalcUtil;
import com.axp.util.CityUtil;
import com.axp.util.DateUtil;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;
import com.axp.util.StringUtil;
import com.axp.util.UrlUtil;
import com.gexin.fastjson.JSON;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 * 
 */
@Service("sellerService")
public class SellerServiceImpl extends BaseServiceImpl<Seller> implements
		ISellerService {

	@Autowired
	ReGoodsOfTeamMallService reGoodsOfTeamMallService;
	@Autowired
	private DateBaseDAO dateBaseDAO;
	@Autowired
	private SellerDAO sellerDao;
	@Autowired
	private SellerMainPageDAO sellerMainPageDao;
	@Autowired
	private ShopConcernDao shopConcernDao;
	@Autowired
	private ISellerMoneyRecordDao sellerMoneyRecordDao;
	@Autowired
	private ReGoodsorderDao reGoodsorderDao;
	@Autowired
	private IReBackOrderDao reBackOrderDao;
	@Autowired
	private IUsersDao usersDao;
	@Autowired
	private IReGoodsOfBaseService reGoodsOfBaseService;
	@Autowired
	private IReGoodsOfScoreMallDao reGoodsOfScoreMallDao;
	@Autowired
	private ReGoodsofextendmallService reGoodsofextendmallService;
	@Autowired
	private IReGoodsOfSellerMallDao reGoodsOfSellerMallDao;
	@Autowired
	TkldPidService tkldPidService;
	@Autowired
	private IReGoodsOfLocalSpecialtyMallDao reGoodsOfLocalSpecialtyMallDao;
	@Autowired
	private IReGoodsOfSeckillMallDao reGoodsOfSeckillMallDao;
	@Autowired
	public ProvinceEnumDAO provinceEnumDao;
	@Autowired
	public ReBaseGoodsDAO reBaseGoodsDAO;
	@Autowired
	public IReGoodsOfChangeMallService reGoodsOfChangeMallService;
	@Autowired
	public IMallListService mallListService;
	
	@Autowired
	public CashshopTypeDAO cashShopTypeDAO;
	
	@Autowired
	public IReGoodsOfLockMallService reGoodsOfLockMallService;
	
	
	
	private Integer pageItemCount = 10;
	String sellerUrl = "http://seller.aixiaoping.com";
	public String path = "http://39.104.160.116:8080/";
	// String sellerUrl = "http://test.aixiaoping.com";//优惠券

	public Integer intoShopNum = 0;

	@Override
	public Map<String, Object> getSellerMainPageDate(
			HttpServletRequest request, HttpServletResponse response) {
		
		
		String basePath = request.getServletContext()
				.getAttribute("RESOURCE_LOCAL_URL").toString();
		Integer sellerId = 0;
		String userId = "0";
		Double lat = 0d;
		Double lng = 0d;
		String v = null;
		String version = "";
		String xcx = request.getParameter("xcx");
		if(xcx != null ){
			sellerId = Integer.valueOf(request.getParameter("sellerId"));
			userId = request.getParameter("userId");
			lat = Double.valueOf(request.getParameter("lat")==""?"0.0":request.getParameter("lat"));
			lng = Double.valueOf(request.getParameter("lng")==""?"0.0":request.getParameter("lng"));
			Map<String,Double> map = CityUtil.Convert_BD09_To_GCJ02(lat, lng);
			lat = map.get("lat");
			lng = map.get("lng");
		}else{
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter != null) {
				sellerId = Integer.parseInt(parameter.getData().getString(
						"sellerId"));
				userId = parameter.getUserId();
				lat = StringUtils.isEmpty(parameter.getLat()) ? null : Double
						.parseDouble(parameter.getLat());
				lng = StringUtils.isEmpty(parameter.getLng()) ? null : Double
						.parseDouble(parameter.getLng());
				
				// 获取版本号 da
				v = parameter.getAppVersion();
				
			}else{
				return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
			}
			char[] charArray = v.toCharArray();
			for (int i = 0; i < charArray.length; i++) {
				if (".".equals(String.valueOf(charArray[i]))) {
					continue;
				}
				version += charArray[i];
			}
			if (sellerId == 0) {
				sellerId = Integer.parseInt(request.getParameter("sellerId"));
			}
		}

		Seller seller = sellerDao.findById(sellerId);

		if (seller == null) {
			return JsonResponseUtil.getJson(-0x0051, "店铺不存在");
		}
		// 增加进店人数 da
		seller.setIntoShopNum(seller.getIntoShopNum() == null ? 1 : seller
				.getIntoShopNum() + 1);
		sellerDao.saveOrUpdate(seller);
		SellerMainPage sellerMainPage = sellerMainPageDao
				.findOneBySellerId(sellerId);

		// ===============================================da
		Map<String, Object> statusMap = new HashMap<String, Object>();

		Map<String, Object> goods = new HashMap<String, Object>();
		QueryModel queryModel = new QueryModel();
		List<Integer> sellerMallId = new ArrayList<Integer>();

		Map<String, Object> dataMap = null;
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		List<ReGoodsOfSellerMall> sellerListScore = new ArrayList<ReGoodsOfSellerMall>();
		dataMap = new HashMap<String, Object>();
		goods = new HashMap<String, Object>();

		queryModel.clearQuery();
		queryModel.combPreEquals("snapshotGoods.seller.id", sellerId,"sellerId");
		queryModel.combPreEquals("isValid", true);
		queryModel.combPreEquals("isChecked", true);
		queryModel.combCondition("shelvesTime >= now()");// 下架时间
		queryModel.combCondition("addedTime <= now()");// 上架时间

		// 商家店铺
		List<ReGoodsOfSellerMall> sellerList = dateBaseDAO.findLists(
				ReGoodsOfSellerMall.class, queryModel);
		if (sellerList.size() > 0) {
			// dataMap = new HashMap<String,Object>();
			dataList = new ArrayList<Map<String, Object>>();
			for (ReGoodsOfSellerMall sellers : sellerList) {
				goods = new HashMap<String, Object>();
				//
				if (sellers.getIsSendScore() != null) {
					if (sellers.getIsSendScore()) {
						sellerListScore.add(sellers);// 是否有送积分的商品
					}
				}
				sellerMallId.add(sellers.getId());
				
			}
			
		}

		
				//dataMap = new HashMap<String,Object>();
				// goods = new HashMap<String,Object>();
				queryModel.clearQuery();
				queryModel.combPreEquals("snapshotGoods.seller.id", sellerId, "smId");// sellerId
				queryModel.combPreEquals("isValid", true);
				queryModel.combPreEquals("isChecked", true);
				queryModel.combCondition("shelvesTime >= now()");// 下架时间
				queryModel.combCondition("addedTime <= now()");// 上架时间
				// 积分商城
				List<ReGoodsOfScoreMall> scoreMallList = dateBaseDAO.findLists(
						ReGoodsOfScoreMall.class, queryModel);
				if (scoreMallList.size() > 0) {
					Map<String,Object> dataMap1 = new HashMap<String, Object>();
					dataList = new ArrayList<Map<String, Object>>();
					for (ReGoodsOfScoreMall score : scoreMallList) {
						Map<String,Object> goods1 = new HashMap<String, Object>();
						goods1.put("goodImg",
								score.getImageJsonForPhone(basePath).get(0)
										.get("image"));
						goods1.put("goodName", score.getSnapshotGoods()
								.getName());
						goods1.put("goodPrice", String.valueOf(score.getPrice()));
						goods1.put("goodOriPrice",String.valueOf(score.getDisplayPrice()));
						goods1.put("goodId", score.getGoodsOrder());
						goods1.put("sendScoreNum", 0);// 赠送积分数
						goods1.put("scoreNum",
								score.getScore() == 0 ? 0 : score.getScore());
						goods1.put(
								"postage",
								score.getTransportationPrice() == null ? "0"
										: String.valueOf(score
												.getTransportationPrice()));
						goods1.put("transportationType",
								score.getTransportationType() == null ? 1
										: score.getTransportationType());
						dataList.add(goods1);
						dataMap1.put("goods", dataList);
					}

					dataMap1.put(
							"topImg",
							"http://jifen.aixiaoping.cn:8080/dailyRes/cashshop_type/1/nomal/410040120040223700.png");
					statusMap.put("scoreExchangePrefecture", dataMap1);
				}
				// 秒杀
				
				List<ReGoodsOfSeckillMall> killMallList = dateBaseDAO
						.findLists(ReGoodsOfSeckillMall.class, queryModel);
				if (killMallList.size() > 0) {
					Map<String,Object> dataMap2 = new HashMap<String, Object>();
					dataList = new ArrayList<Map<String, Object>>();
					for (ReGoodsOfSeckillMall kill : killMallList) {
						goods = new HashMap<String, Object>();
						goods.put(
								"goodImg",
								kill.getImageJsonForPhone(basePath).get(0)
										.get("image"));
						goods.put("goodName", kill.getSnapshotGoods().getName());
						goods.put("goodPrice", String.valueOf(kill.getPrice()));
						goods.put("goodOriPrice",
								String.valueOf(kill.getDisplayPrice()));
						goods.put("goodId", kill.getGoodsOrder());
						goods.put("sendScoreNum", 0);// 赠送积分数
						goods.put("scoreNum",
								kill.getScore() == 0 ? 0 : kill.getScore());
						goods.put(
								"postage",
								kill.getTransportationPrice() == 0 ? "0"
										: String.valueOf(kill
												.getTransportationPrice()));
						goods.put("transportationType",
								kill.getTransportationType() == null ? 1
										: kill.getTransportationType());
						dataList.add(goods);
						
						Set<CashshopTimes> cts = kill.getTimes();
						
						String st = "";
						String et = "";
						
						if(cts.size()>0){
							for(CashshopTimes ct :cts){
								//System.out.println(ct.getStartTime()+"---"+ct.getEndTime());
								st= ct.getStartTime();
								et = ct.getEndTime();
							}
						}
						goods.put("startKillTime", st);//秒杀开始时间
						goods.put("endKillTime", et);//秒杀结束时间
						dataMap2.put("goods", dataList);
					}
					dataMap2.put(
							"topImg",
							"http://jifen.aixiaoping.cn:8080/dailyRes/cashshop_type/1/nomal/410020505122639800.png");
					statusMap.put("secondKillPrefecture", dataMap2);
				}
				
				// 拼团
				List<ReGoodsOfTeamMall> teamMallList = dateBaseDAO.findLists(
						ReGoodsOfTeamMall.class, queryModel);
				if (teamMallList.size() > 0) {
					Map<String,Object> dataMap3 = new HashMap<String, Object>();
					dataList = new ArrayList<Map<String, Object>>();
					for (ReGoodsOfTeamMall team : teamMallList) {
						goods = new HashMap<String, Object>();
						goods.put(
								"goodImg",
								team.getImageJsonForPhone(basePath).get(0)
										.get("image"));
						goods.put("goodName", team.getSnapshotGoods().getName());
						goods.put("goodPrice", String.valueOf(CalcUtil.sub(team.getReGoodsOfSellerMall().getPrice(),team.getDiscountPrice())));//拼团价格,
						goods.put("goodOriPrice",team.getReGoodsOfSellerMall().getDisplayPrice());
						
						goods.put("goodId", team.getGoodsOrder());
						goods.put("sendScoreNum", 0);// 赠送积分数
						goods.put("scoreNum",
								team.getScore() == 0 ? 0 : team.getScore());
						goods.put(
								"postage",
								team.getTransportationPrice() == 0 ? "0"
										: String.valueOf(team
												.getTransportationPrice()));
						goods.put("transportationType",
								team.getTransportationType() == null ? 1
										: team.getTransportationType());
						dataList.add(goods);
						dataMap3.put("goods", dataList);
					}
					dataMap3.put(
							"topImg",
							"http://jifen.aixiaoping.cn:8080/dailyRes/cashshop_type/1/nomal/410071110110533100.png");
					statusMap.put("ptPrefecture", dataMap3);
				}

				
		
		
		// 活动 优惠券
		queryModel.clearQuery();
		queryModel.combPreEquals("isValid", true);
		queryModel.combPreEquals("sign", 1);// 换券中,未下架标识
		queryModel.combPreEquals("seller.id", sellerId, "sellerId");
		queryModel.combPreEquals("isActivity", 1);
		List<ReGoodsofextendmall> extendMallList = dateBaseDAO
				.findLists(ReGoodsofextendmall.class, queryModel);
		if (extendMallList.size() > 0) {
			dataMap = new HashMap<String, Object>();
			dataList = new ArrayList<Map<String, Object>>();
			for (ReGoodsofextendmall extend : extendMallList) {
				goods = new HashMap<String, Object>();
				String covers = extend.getCoverPic();
				if (StringUtil.hasLength(covers)
						&& covers.startsWith("[")) {
					com.alibaba.fastjson.JSONArray array = JSONObject
							.parseArray(covers);
					if (array.size() > 0) {
						covers = array.getJSONObject(0).getString(
								"imgUrl");
					} else {
						covers = "";
					}
				}
				goods.put("goodImg", basePath + covers);
				goods.put("goodName", extend.getName());
				goods.put("goodPrice",
						String.valueOf(extend.getPrice()));
				goods.put("goodOriPrice",
						String.valueOf(extend.getTicketprice()));
				goods.put("goodId", extend.getId());
				goods.put("quan_price",
						String.valueOf(extend.getTicketprice()));
				goods.put("earnMoney",
						String.valueOf((extend.getCommission() * 0.6)));
				dataList.add(goods);
			}
			dataMap.put("goods", dataList);
			dataMap.put(
					"topImg",
					"http://jifen.aixiaoping.cn:8080/dailyRes/cashshop_type/1/nomal/410030510073243700.png");

			statusMap.put("couponPrefecture", dataMap);
		}
		
		
		
		
		// 送积分的
		if (sellerListScore.size() > 0) {
			dataMap = new HashMap<String, Object>();
			dataList = new ArrayList<Map<String, Object>>();
			for (ReGoodsOfSellerMall sscore : sellerListScore) {
				//不是换货会的才显示为送积分的
				
				goods = new HashMap<String, Object>();
				goods.put(
						"goodImg",
						sscore.getImageJsonForPhone(basePath).get(0)
								.get("image"));
				goods.put("goodName", sscore.getSnapshotGoods().getName());
				goods.put("goodPrice", String.valueOf(sscore.getPrice()));
				goods.put("goodOriPrice",
						String.valueOf(sscore.getDisplayPrice()));
				goods.put("goodId", sscore.getGoodsOrder());
				goods.put("sendScoreNum", sscore.getSendScoreNum());// 赠送积分数
				goods.put("scoreNum",
						sscore.getScore() == 0 ? 0 : sscore.getScore());
				goods.put(
						"postage",
						sscore.getTransportationPrice() == 0.0 ? "0.0" : String
								.valueOf(sscore.getTransportationPrice()));
				goods.put(
						"transportationType",
						sscore.getTransportationType() == null ? 1 : sscore
								.getTransportationType());
				dataList.add(goods);
			}
			dataMap.put("goods", dataList);
			dataMap.put(
					"topImg",
					"http://jifen.aixiaoping.cn:8080/dailyRes/cashshop_type/1/nomal/410091028075932500.png");
			statusMap.put("sendScorePrefecture", dataMap);
		}
		// 第三方优惠券 还没有完善的
		
		
		
		
		
		Map<String, Object> otherCoupons = new HashMap<String, Object>();
		// 拼多多 优惠券
		goods = new HashMap<String, Object>();
		dataList = new ArrayList<Map<String, Object>>();
		goods.put("title", "拼多多 优惠券");
		goods.put("remark", "拼多多 优惠券");
		goods.put(
				"image",
				"http://jifen.aixiaoping.cn:8080/dailyRes/cashshop_type/1/nomal/410190616062228100.png");
		goods.put("typeId", "1");
		otherCoupons.put("pddCoupons", goods);
		// 京东优惠券
		goods = new HashMap<String, Object>();
		dataList = new ArrayList<Map<String, Object>>();
		goods.put("title", "京东 优惠券");
		goods.put("remark", "京东 优惠券");//淘宝优惠券
		goods.put(
				"image",
				"http://jifen.aixiaoping.cn:8080/dailyRes/cashshop_type/1/nomal/410201004041757300.png");
		goods.put("typeId", "1");
		otherCoupons.put("jdCoupons", goods);
		// 淘宝优惠券
		goods = new HashMap<String, Object>();
		dataList = new ArrayList<Map<String, Object>>();
		goods.put("title", "淘宝 优惠券");
		goods.put("remark", "淘宝 优惠券");//天猫优惠券
		goods.put(
				"image",
				"http://jifen.aixiaoping.cn:8080/dailyRes/cashshop_type/1/nomal/410200120013159600.png");
		goods.put("typeId", "1");
		otherCoupons.put("tbCoupons", goods);

		// ================================================da

		// QueryModel model = new QueryModel();
		//
		// //查找出所有的积分商品
		// model.clearQuery();
		// model.combCondition("addedTime < now()");
		// model.combCondition("shelvesTime > now()");
		// model.combPreEquals("isValid", true);
		// model.combPreEquals("isChecked", true);
		// model.combPreEquals("snapshotGoods.seller.id",sellerId,"sellerId");
		// model.setOrder("id desc");
		//
		// List<ReGoodsOfScoreMall> mallList2 =
		// dateBaseDAO.findPageList(ReGoodsOfScoreMall.class, model, 0 , 5);
		// List<Object> goodsList2 = new ArrayList<>();
		// for(ReGoodsOfScoreMall mall :mallList2){
		// goodsList2.add(getGoodsData2(mall, basePath));
		// }
		if (sellerMainPage == null) {
			return JsonResponseUtil.getJson(-0x0052, "无店铺信息");
		}
		// String logo =
		// StringUtil.hasLength(sellerMainPage.getSellerLogo())?basePath+sellerMainPage.getSellerLogo():"";
		// sellerMainPage.setSellerLogo(logo);

		Map<String, Object> sellerMap = getSellerMainPageMap(
				sellerMainPage,
				basePath,
				seller.getId(),
				StringUtils.isBlank(userId) ? (Integer) 0 : Integer
						.parseInt(userId), lng, lat);

		// 获取分店信息
		List<Integer> brachedIds = sellerDao.getBrachesIds(seller
				.getAdminUser().getId());
		StringBuffer ids = new StringBuffer("-1");
		for (Integer id : brachedIds) {
			ids.append("," + id);
		}
		List<SellerMainPage> mainPageList = sellerMainPageDao
				.getSellerMainPageIds(ids.toString());
		List<Map<String, Object>> brachesList = new ArrayList<>();

		for (SellerMainPage main : mainPageList) {
			brachesList.add(getSellerMainPageMap(
					main,
					basePath,
					sellerId,
					StringUtils.isBlank(userId) ? (Integer) 0 : Integer
							.parseInt(userId), lng, lat));
		}
		// System.out.println(goodsList2);
		sellerMap.put("branchs", brachesList);
		// sellerMap.put("recommendScoreGoodsList", goodsList2);
		sellerMap.put("sellerActivities", statusMap);

		sellerMap.put("otherCoupons", otherCoupons);
		
		return JsonResponseUtil.getJson(0x01, "请求成功", sellerMap);
	}

	@Override
	public Map<String, Object> getSellerListDate(Integer pageIndex,
			Integer typeId, String local, Integer userId, Integer zoneId,
			Integer areaId, String search, Double lat, Double lng,
			HttpServletRequest request, HttpServletResponse response) {
		String basePath = request.getServletContext()
				.getAttribute("RESOURCE_LOCAL_URL").toString();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Integer goodsLimit = 2;
		if (local != null && !local.equals("HOME")) {
			goodsLimit = 0;
		}

		ProvinceEnum zone = provinceEnumDao.getCurrentCity(zoneId);
		Integer ids = 0;
		if (zoneId != null && zone != null) {
			ids = zone.getId();
		}
		String sellerIds = reGoodsOfSellerMallDao.getSellerIdsOfHomePage(
				goodsLimit, ids);
		QueryModel model = new QueryModel();
		model.combCondition("seller.id in (" + sellerIds + ")");
		if (areaId != null)
			model.combPreEquals("seller.adminUser.id", areaId, "adminUserId");
		if (StringUtil.hasLength(search))
			model.combCondition("seller.name like '%" + search + "%'");

		model.combPreEquals("isValid", true);
		// 店铺分类================lxh==========start

		if (typeId != null && typeId > 0) {
			model.combPreEquals("seller.shopCategory.id", typeId,
					"shopCategoryId");
		}
		// 城市类别选择=============lxh==========start

		if (lat != null && lng != null) {
			String distance = "(round(6378.138*2*asin(sqrt(pow(sin( (" + lat
					+ "*pi()/180-seller.latitude*pi()/180)/2),2)+" + "cos("
					+ lat
					+ "*pi()/180)*cos(seller.latitude*pi()/180)*pow(sin( ("
					+ lng
					+ "*pi()/180-seller.longitude*pi()/180)/2),2)))*1000)) asc";
			model.setOrder(distance);
		} else {
			model.setOrder("id desc");
		}

		int count = dateBaseDAO.findCount(SellerMainPage.class, model);
		int totalPage = (count % pageItemCount) > 0 ? ((count / pageItemCount) + 1)
				: (count / pageItemCount);
		int start = (pageIndex - 1) * pageItemCount;
		int pagecounet = pageItemCount;
		if ("HOME".equals(local)) {
			count = 6;
			totalPage = 1;
			start = 0;
			pagecounet = 6;
		}

		List<SellerMainPage> slist = new ArrayList<SellerMainPage>();
		// 美电贝尔
		List<SellerMainPage> seller = dateBaseDAO.findPageList(
				SellerMainPage.class, model, start, pagecounet);

		slist = seller;

		List<Object> sellerList = new ArrayList<>();
		if (StringUtils.isBlank(search)) {
			SellerMainPage mdbe = sellerMainPageDao.findById(195);// 美电贝尔
			SellerMainPage ydzy = sellerMainPageDao.findById(481);// 远东纸业
			if (start == 0) {
				if (mdbe != null && zone.getId() != 1114) {
					SellerMainPage.InnerSellerMainPage page = mdbe.new InnerSellerMainPage(
							lat, lng);
					page.setBasePath(basePath);
					// 判断该商品是否被关注
					model.clearQuery();
					model.combPreEquals("seller.id", mdbe.getSellerId(),
							"sellerId");
					model.combPreEquals("users.id", userId, "userId");
					model.combPreEquals("isfocus", true);
					int cou = dateBaseDAO.findCount(ShopConcern.class, model);
					page.setConcern(cou < 1 ? false : true);
					// 查找出所有的积分商品
					model.clearQuery();
					model.combCondition("addedTime < now()");
					model.combCondition("shelvesTime > now()");
					model.combPreEquals("isValid", true);
					model.combPreEquals("isChecked", true);
					model.combPreEquals("snapshotGoods.seller.id",
							page.getSellerId(), "sellerId");
					model.setOrder("id desc");
					List<ReGoodsOfSellerMall> mallList = dateBaseDAO
							.findPageList(ReGoodsOfSellerMall.class, model, 0,
									3);
					List<Object> goodsList = new ArrayList<>();
					for (ReGoodsOfSellerMall mall : mallList) {
						goodsList.add(getGoodsData(mall, basePath));
					}
					page.setRecommendSellerGoodsList(goodsList);

					sellerList.add(page);
					// 找出是否关注
					model.clearQuery();
					model.combPreEquals("seller.id", mdbe.getSellerId(),
							"sellerId");
					model.combPreEquals("users.id", userId, "userId");
					List<ShopConcern> cern = dateBaseDAO.findPageList(
							ShopConcern.class, model, 0, 1);
					if (cern.size() > 0 && cern.get(0).getIsfocus()) {
						mdbe.setConcern(true);
					} else {
						mdbe.setConcern(false);
					}
					cern = null;

				}

				if (ydzy != null) {

					SellerMainPage.InnerSellerMainPage page = ydzy.new InnerSellerMainPage(
							lat, lng);
					page.setBasePath(basePath);
					// 判断该商品是否被关注
					model.clearQuery();
					model.combPreEquals("seller.id", ydzy.getSellerId(),
							"sellerId");
					model.combPreEquals("users.id", userId, "userId");
					model.combPreEquals("isfocus", true);
					int cou = dateBaseDAO.findCount(ShopConcern.class, model);
					page.setConcern(cou < 1 ? false : true);
					// 查找出所有的积分商品
					model.clearQuery();
					model.combCondition("addedTime < now()");
					model.combCondition("shelvesTime > now()");
					model.combPreEquals("isValid", true);
					model.combPreEquals("isChecked", true);
					model.combPreEquals("snapshotGoods.seller.id",
							page.getSellerId(), "sellerId");
					model.setOrder("id desc");
					List<ReGoodsOfSellerMall> mallList = dateBaseDAO
							.findPageList(ReGoodsOfSellerMall.class, model, 0,
									3);
					List<Object> goodsList = new ArrayList<>();
					for (ReGoodsOfSellerMall mall : mallList) {
						goodsList.add(getGoodsData(mall, basePath));
					}
					page.setRecommendSellerGoodsList(goodsList);

					sellerList.add(page);
					// 找出是否关注
					model.clearQuery();
					model.combPreEquals("seller.id", ydzy.getSellerId(),
							"sellerId");
					model.combPreEquals("users.id", userId, "userId");
					List<ShopConcern> cern = dateBaseDAO.findPageList(
							ShopConcern.class, model, 0, 1);
					if (cern.size() > 0 && cern.get(0).getIsfocus()) {
						ydzy.setConcern(true);
					} else {
						ydzy.setConcern(false);
					}
					cern = null;

				}
			}
		}

		Map<Integer, SellerMainPage> map = new HashMap<Integer, SellerMainPage>();

		// 新增搜索规则=======lxh========start
		if (StringUtil.hasLength(search)) {
			model.clearQuery();
			model.combCondition("addedTime < now()");
			model.combCondition("shelvesTime > now()");
			model.combPreEquals("isValid", true);
			model.combPreEquals("isChecked", true);
			model.combCondition("snapshotGoods.name like '%" + search + "%'");
			model.combCondition("snapshotGoods.seller.id in (" + sellerIds
					+ ")");
			// model.combCondition("1=1  group by snapshotGoods.seller.id");
			model.setOrder("id desc");
			List<ReGoodsOfSellerMall> mallList = dateBaseDAO.findPageList(
					ReGoodsOfSellerMall.class, model, 0, 3);
			List<Object> goodsList = new ArrayList<>();
			if (mallList != null && mallList.size() > 0) {
				for (ReGoodsOfSellerMall mall : mallList) {
					SellerMainPage smp = sellerMainPageDao
							.findOneBySellerId(mall.getSnapshotGoods()
									.getSeller().getId());
					map.put(mall.getSnapshotGoods().getSeller().getId(), smp);
				}
			}

		}

		for (Integer sid : map.keySet()) {
			slist.add(map.get(sid));
		}
		for (SellerMainPage s : slist) {

			if (s.getId() == 195) {
				continue;
			}

			SellerMainPage.InnerSellerMainPage page = s.new InnerSellerMainPage(
					lat, lng);
			page.setBasePath(basePath);
			// 判断该商品是否被关注
			model.clearQuery();
			model.combPreEquals("seller.id", s.getSellerId(), "sellerId");
			model.combPreEquals("users.id", userId, "userId");
			model.combPreEquals("isfocus", true);
			int cou = dateBaseDAO.findCount(ShopConcern.class, model);
			page.setConcern(cou < 1 ? false : true);
			// 查找出所有的积分商品
			model.clearQuery();
			model.combCondition("addedTime < now()");
			model.combCondition("shelvesTime > now()");
			model.combPreEquals("isValid", true);
			model.combPreEquals("isChecked", true);
			model.combPreEquals("snapshotGoods.seller.id", page.getSellerId(),
					"sellerId");
			model.setOrder("id desc");
			List<ReGoodsOfSellerMall> mallList = dateBaseDAO.findPageList(
					ReGoodsOfSellerMall.class, model, 0, 3);
			List<Object> goodsList = new ArrayList<>();
			for (ReGoodsOfSellerMall mall : mallList) {
				goodsList.add(getGoodsData(mall, basePath));
			}
			page.setRecommendSellerGoodsList(goodsList);

			sellerList.add(page);
			// 找出是否关注
			model.clearQuery();
			model.combPreEquals("seller.id", s.getSellerId(), "sellerId");
			model.combPreEquals("users.id", userId, "userId");
			List<ShopConcern> cern = dateBaseDAO.findPageList(
					ShopConcern.class, model, 0, 1);
			if (cern.size() > 0 && cern.get(0).getIsfocus()) {
				s.setConcern(true);
			} else {
				s.setConcern(false);
			}
			cern = null;
		}

		// 获取店铺类别列表================lxh
		List<Map<String, Object>> catelist = getShopType();

		dataMap.put("pageSize", totalPage);
		dataMap.put("pageIndex", pageIndex);
		dataMap.put("pageItemCount", pagecounet);
		dataMap.put("dataList", sellerList);
		dataMap.put("cityList", getCityList());
		dataMap.put("typeList", catelist);
		return JsonResponseUtil.getJson(0x01, "请求成功", dataMap);
	}

	private List<Map<String, Object>> getCityList() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<ProvinceEnum> ctlist = provinceEnumDao.getListByLevel(1);
		List<ProvinceEnum> ctlist1 = provinceEnumDao.getListByLevel(2);

		for (ProvinceEnum s : ctlist) {
			List<Map<String, Object>> lv2List = new ArrayList<Map<String, Object>>();
			Map<String, Object> lv1map = new HashMap<String, Object>();
			lv1map.put("provinceId", s.getId());
			lv1map.put("provinceName", s.getName());
			lv1map.put("cityItems", lv2List);
			for (ProvinceEnum s2 : ctlist1) {
				if (s2.getProvinceEnum() != null) {
					if (s2.getProvinceEnum().getId() == s.getId()) {
						Map<String, Object> lv2map = new HashMap<String, Object>();
						lv2map.put("cityId", s2.getId());
						lv2map.put("cityName", s2.getName());
						lv2List.add(lv2map);
					}
				}
			}
			list.add(lv1map);
		}
		return list;
	}

	private List<Map<String, Object>> getShopType() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<ShopCategory> s1 = new ArrayList<ShopCategory>();
		List<ShopCategory> s2 = new ArrayList<ShopCategory>();
		for (ShopCategory s : shopCategoryDao.getList()) {
			if (s.getLevel() == 1) {
				s1.add(s);
			} else {
				s2.add(s);
			}
		}
		for (ShopCategory v1 : s1) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<Map<String, Object>> lv2List = new ArrayList<Map<String, Object>>();
			Map<String, Object> lv1map = new HashMap<String, Object>();
			lv1map.put("typeId", v1.getId());
			lv1map.put("typeName", v1.getName());
			lv1map.put("typeItems", lv2List);
			for (ShopCategory v2 : s2) {
				if (v2.getShopCategory().getId() == v1.getId()) {
					Map<String, Object> lv2map = new HashMap<String, Object>();
					lv2map.put("typeId", v2.getId());
					lv2map.put("typeName", v2.getName());
					lv2List.add(lv2map);
				}
			}
			list.add(lv1map);
		}
		return list;
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
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
		return 0;
	}

	@Override
	public Map<String, Object> getSellerListDateForNew(Integer pageIndex,
			Integer typeId, String local, Integer userId, Integer zoneId,
			Integer areaId, String search, Double lat, Double lng,
			HttpServletRequest request, HttpServletResponse response) {
		String basePath = request.getServletContext()
				.getAttribute("RESOURCE_LOCAL_URL").toString();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Integer goodsLimit = 0;

		if (pageIndex < 1) {
			pageIndex = 1;
		}

		if (zoneId > 0) {

			ProvinceEnum zone = provinceEnumDao.getCurrentCity(zoneId);
			Integer ids = 0;
			if (zoneId != null && zone != null) {
				ids = zone.getId();
			}
			String sellerIds = reGoodsOfSellerMallDao.getSellerIdsOfHomePage(
					goodsLimit, ids);
			QueryModel model = new QueryModel();
			model.combPreEquals("isValid", true);
			model.combCondition("seller.id in (" + sellerIds + ")");
			if (areaId != null) {
				model.combPreEquals("seller.adminUser.id", areaId,
						"adminUserId");
			}
			if (typeId != null) {
				model.combPreEquals("seller.shoptypes.id", typeId,
						"shoptypesId");
			}

			if (lat != null && lng != null) {
				String distance = "(round(6378.138*2*asin(sqrt(pow(sin( ("
						+ lat
						+ "*pi()/180-seller.latitude*pi()/180)/2),2)+"
						+ "cos("
						+ lat
						+ "*pi()/180)*cos(seller.latitude*pi()/180)*pow(sin( ("
						+ lng
						+ "*pi()/180-seller.longitude*pi()/180)/2),2)))*1000)) asc";
				model.setOrder(distance);
			} else {
				model.setOrder("id desc");
			}

			int count = dateBaseDAO.findCount(SellerMainPage.class, model);
			int totalPage = (count % pageItemCount) > 0 ? ((count / pageItemCount) + 1)
					: (count / pageItemCount);
			int start = (pageIndex - 1) * pageItemCount;
			int pagecounet = pageItemCount;
			List<SellerMainPage> slist = dateBaseDAO.findPageList(
					SellerMainPage.class, model, start, pagecounet);
			List<Object> sellerList = new ArrayList<>();
			for (SellerMainPage s : slist) {

				if (s.getId() == 195 || s.getId() == 481) {
					continue;
				}
				SellerMainPage.InnerSellerMainPage page = s.new InnerSellerMainPage(
						lat, lng);
				page.setBasePath(basePath);
				// 判断该商品是否被关注
				model.clearQuery();
				model.combPreEquals("seller.id", s.getSellerId(), "sellerId");
				model.combPreEquals("users.id", userId, "userId");
				model.combPreEquals("isfocus", true);
				int cou = dateBaseDAO.findCount(ShopConcern.class, model);
				page.setConcern(cou < 1 ? false : true);
				// 查找出所有的积分商品
				model.clearQuery();
				model.combCondition("addedTime < now()");
				model.combCondition("shelvesTime > now()");
				model.combPreEquals("isValid", true);
				model.combPreEquals("isChecked", true);
				model.combPreEquals("snapshotGoods.seller.id",
						page.getSellerId(), "sellerId");
				model.combPreEquals("snapshotGoods.isValid", true,
						"snapisValid");
				if (StringUtil.hasLength(search)) {
					model.combCondition("(snapshotGoods.seller.name like '%"
							+ search + "%'  or snapshotGoods.name like '%"
							+ search + "%' )");
				}
				model.setOrder("id desc");
				List<ReGoodsOfSellerMall> mallList = dateBaseDAO.findPageList(
						ReGoodsOfSellerMall.class, model, 0, 3);
				List<Object> goodsList = new ArrayList<>();
				for (ReGoodsOfSellerMall mall : mallList) {
					goodsList.add(getGoodsData(mall, basePath));
				}
				if (goodsList.size() > 0) {
					page.setRecommendSellerGoodsList(goodsList);
					sellerList.add(page);
				}
				// 找出是否关注
				model.clearQuery();
				model.combPreEquals("seller.id", s.getSellerId(), "sellerId");
				model.combPreEquals("users.id", userId, "userId");
				List<ShopConcern> cern = dateBaseDAO.findPageList(
						ShopConcern.class, model, 0, 1);
				if (cern.size() > 0 && cern.get(0).getIsfocus()) {
					s.setConcern(true);
				} else {
					s.setConcern(false);
				}
				cern = null;
			}
			List<SellerMainPage> quangou = sellerMainPageDao
					.findByIds("195,481");

			if (pageIndex == totalPage || (pageIndex == 1 && totalPage == 0)) {

				if (quangou != null && quangou.size() > 0) {
					for (SellerMainPage smp : quangou) {
						SellerMainPage.InnerSellerMainPage page = smp.new InnerSellerMainPage(
								lat, lng);
						page.setBasePath(basePath);
						// 判断该商品是否被关注
						model.clearQuery();
						model.combPreEquals("seller.id", smp.getSellerId(),
								"sellerId");
						model.combPreEquals("users.id", userId, "userId");
						model.combPreEquals("isfocus", true);
						int cou = dateBaseDAO.findCount(ShopConcern.class,
								model);
						page.setConcern(cou < 1 ? false : true);
						// 查找出所有的积分商品
						model.clearQuery();
						model.combCondition("addedTime < now()");
						model.combCondition("shelvesTime > now()");
						model.combPreEquals("isValid", true);
						model.combPreEquals("isChecked", true);
						model.combPreEquals("snapshotGoods.seller.id",
								page.getSellerId(), "sellerId");
						model.setOrder("id desc");
						List<ReGoodsOfSellerMall> mallList = dateBaseDAO
								.findPageList(ReGoodsOfSellerMall.class, model,
										0, 3);
						List<Object> goodsList = new ArrayList<>();
						for (ReGoodsOfSellerMall mall : mallList) {
							goodsList.add(getGoodsData(mall, basePath));
						}
						page.setRecommendSellerGoodsList(goodsList);
						sellerList.add(page);
						// 找出是否关注
						model.clearQuery();
						model.combPreEquals("seller.id", smp.getSellerId(),
								"sellerId");
						model.combPreEquals("users.id", userId, "userId");
						List<ShopConcern> cern = dateBaseDAO.findPageList(
								ShopConcern.class, model, 0, 1);
						if (cern.size() > 0 && cern.get(0).getIsfocus()) {
							smp.setConcern(true);
						} else {
							smp.setConcern(false);
						}
						cern = null;
					}
				}
			}

			// 获取店铺类别列表================lxh
			List<Map<String, Object>> catelist = getShopType();

			dataMap.put("pageSize", totalPage);
			dataMap.put("pageIndex", pageIndex);
			dataMap.put("pageItemCount", pagecounet);
			dataMap.put("dataList", sellerList);
			dataMap.put("cityList", getCityList());
			dataMap.put("typeList", catelist);
		}
		return JsonResponseUtil.getJson(0x01, "请求成功", dataMap);
	}

	public List<Map<String,Object>> getSellerListBySearch2(Integer pageIndex,
		Integer typeId, String local, Integer userId, Integer zoneId,
		Integer areaId, String search, Double lat, Double lng,
		HttpServletRequest request, HttpServletResponse response){
		Integer goodsLimit = 0;
		String basePath = request.getServletContext()
			.getAttribute("RESOURCE_LOCAL_URL").toString();
		List<Map<String,Object>> sells = new ArrayList<Map<String,Object>>();
		if(zoneId >0){
			
			ProvinceEnum zone = provinceEnumDao.findById(zoneId);
			String sellerIds = reGoodsOfSellerMallDao.getSellerIdsOfHomePage2(
					goodsLimit, zone, lat, lng, search, typeId);
			QueryModel model = new QueryModel();

			model.combPreEquals("isValid", true);

			model.combCondition("seller.id in (" + sellerIds + ") ");

			if (lat != null && lng != null) {
				String distance = "(round(6378.138*2*asin(sqrt(pow(sin( ("
						+ lat
						+ "*pi()/180-seller.latitude*pi()/180)/2),2)+"
						+ "cos("
						+ lat
						+ "*pi()/180)*cos(seller.latitude*pi()/180)*pow(sin( ("
						+ lng
						+ "*pi()/180-seller.longitude*pi()/180)/2),2)))*1000)) asc";
				model.setOrder("seller.zhidingTime desc ,"+distance );
			} else {
				model.setOrder("id desc");

			}
			//model.setOrder("seller.zhidingTime desc");
		int count = dateBaseDAO.findCount(SellerMainPage.class, model);
		int totalPage = (count % pageItemCount) > 0 ? ((count / pageItemCount) + 1)
				: (count / pageItemCount);
		int start = (pageIndex - 1) * pageItemCount;
		int pagecounet = pageItemCount;
		List<SellerMainPage> slist = dateBaseDAO.findPageList(
				SellerMainPage.class, model, start, pagecounet);
		
		for (SellerMainPage s : slist) {
			if (s.getId() == 195 || s.getId() == 481) {
				continue;
			}
			SellerMainPage.InnerSellerMainPage page = s.new InnerSellerMainPage(lat, lng);
			page.setBasePath(basePath);
			page.setSellerIntroduce(s.getRemark());
			
			// 查找出所有的积分商品
			model.clearQuery();
			model.combCondition("addedTime < now()");
			model.combCondition("shelvesTime > now()");
			model.combPreEquals("isValid", true);
			model.combPreEquals("snapshotGoods.seller.id",
					page.getSellerId(), "sellerId");
			model.combPreEquals("snapshotGoods.isValid", true,
					"snapisValid");
			model.setOrder("id desc");
			int scoreCount = dateBaseDAO.findCount(ReGoodsOfScoreMall.class,model);
			List<Object> tags =  new ArrayList<Object>();
			if (scoreCount > 0) {
				tags.add("积分");
			}
			
			Map<String,Object> sell = new HashMap<String,Object>();
			sell.put("goodsId", s.getSellerId());
			sell.put("name", s.getSellerName());
			//sell.put("coverPic", "http://192.168.200.182:8080/daily_ref/cashshop_type/1/nomal/41860425062830966.png");
			sell.put("coverPic", basePath+s.getSellerLogo());
			sell.put("goodDescription", s.getRemark());
			sell.put("sellerActiveTags", tags);
			if("xcx".equals(local)){
				sell.put("distance", CalcUtil.div(page.getDistance(),1000,2));
			}else{
				
				sell.put("distance", page.getDistance());
			}
			sell.put("intoShopNum", s.getSeller().getIntoShopNum()==null?0:s.getSeller().getIntoShopNum() );
			sells.add(sell);
		
		}
	
		}
			return sells;
		
	}
	
	
	
	
	
	
	public Map<String, Object> getSellerListBySearch(String xcx,Integer pageIndex,
			Integer typeId, String local, Integer userId, Integer zoneId,
			Integer areaId, String search, Double lat, Double lng,
			HttpServletRequest request, HttpServletResponse response) {
		String basePath = request.getServletContext()
				.getAttribute("RESOURCE_LOCAL_URL").toString();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Integer goodsLimit = 0;

		if (zoneId > 0) {
			ProvinceEnum zone = provinceEnumDao.findById(zoneId);
			String sellerIds = reGoodsOfSellerMallDao.getSellerIdsOfHomePage2(
					goodsLimit, zone, lat, lng, search, typeId);
			QueryModel model = new QueryModel();

			model.combPreEquals("isValid", true);

			model.combCondition("seller.id in (" + sellerIds + ") ");

			if (lat != null && lng != null) {
				String distance = "(round(6378.138*2*asin(sqrt(pow(sin( ("
						+ lat
						+ "*pi()/180-seller.latitude*pi()/180)/2),2)+"
						+ "cos("
						+ lat
						+ "*pi()/180)*cos(seller.latitude*pi()/180)*pow(sin( ("
						+ lng
						+ "*pi()/180-seller.longitude*pi()/180)/2),2)))*1000)) asc";
				model.setOrder(distance);
			} else {
				model.setOrder("id desc");

			}
			int count = dateBaseDAO.findCount(SellerMainPage.class, model);
			int totalPage = (count % pageItemCount) > 0 ? ((count / pageItemCount) + 1)
					: (count / pageItemCount);
			int start = (pageIndex - 1) * pageItemCount;
			int pagecounet = pageItemCount;
			List<SellerMainPage> slist = dateBaseDAO.findPageList(
					SellerMainPage.class, model, start, pagecounet);
			List<Object> sellerList = new ArrayList<>();
			for (SellerMainPage s : slist) {

				if (s.getId() == 195 || s.getId() == 481) {
					continue;
				}

				SellerMainPage.InnerSellerMainPage page = s.new InnerSellerMainPage(
						lat, lng);
				page.setBasePath(basePath);
				page.setSellerIntroduce(s.getRemark());
				page.setDistance(10000.1);
				// 判断该商品是否被关注
				model.clearQuery();
				model.combPreEquals("seller.id", s.getSellerId(), "sellerId");
				model.combPreEquals("users.id", userId, "userId");
				model.combPreEquals("isfocus", true);
				int cou = dateBaseDAO.findCount(ShopConcern.class, model);
				page.setConcern(cou < 1 ? false : true);
				// 查找出所有的积分商品
				model.clearQuery();
				model.combCondition("addedTime < now()");
				model.combCondition("shelvesTime > now()");
				model.combPreEquals("isValid", true);
				model.combPreEquals("isChecked", true);
				model.combPreEquals("snapshotGoods.seller.id",
						page.getSellerId(), "sellerId");
				model.combPreEquals("snapshotGoods.isValid", true,
						"snapisValid");
				// 限制周边不出现换货会商品 记得把以前数据的默认值改掉
				
				model.setOrder("id desc");
				//int scoreCount = dateBaseDAO.findCount(ReGoodsOfSellerMall.class, model);
				int scoreCount = dateBaseDAO.findCount(ReGoodsOfScoreMall.class,model);
				
				List<Object> tags =  new ArrayList<Object>();
				if (scoreCount > 0) {
					tags.add("积分");
					
				}

				// 查找出所有优惠券的商品
				model.clearQuery();
				model.combPreEquals("isValid", true);
				model.combPreEquals("sign", 1);// 换券中,未下架标识
				model.combPreEquals("seller.id", page.getSellerId(), "sellerid");
				model.combPreEquals("isActivity", 1);
				Integer extenCount = dateBaseDAO.findCount(
						ReGoodsofextendmall.class, model);

				
				if (extenCount > 0) {
					tags.add("优惠券");
				}
				if(tags.size()>0){
					page.setSellerActiveTags(tags);
				}
				
				
				model.clearQuery();
				model.combCondition("addedTime < now()");
				model.combCondition("shelvesTime > now()");
				model.combPreEquals("isValid", true);
				model.combPreEquals("isChecked", true);
				model.combPreEquals("snapshotGoods.seller.id",
						page.getSellerId(), "sellerId");
				model.setOrder("id desc");
				
				List<ReGoodsOfSellerMall> mallList = dateBaseDAO.findPageList(
						ReGoodsOfSellerMall.class, model, 0, 3);
				
				List<Object> goodsList = new ArrayList<>();
				for (ReGoodsOfSellerMall mall : mallList) {
					goodsList.add(getGoodsData(mall, basePath));
					

				}
				page.setRecommendSellerGoodsList(goodsList);
				
				sellerList.add(page);
				// 找出是否关注
				model.clearQuery();
				model.combPreEquals("seller.id", s.getSellerId(), "sellerId");
				model.combPreEquals("users.id", userId, "userId");
				List<ShopConcern> cern = dateBaseDAO.findPageList(
						ShopConcern.class, model, 0, 1);
				if (cern.size() > 0 && cern.get(0).getIsfocus()) {
					s.setConcern(true);
				} else {
					s.setConcern(false);
				}
				cern = null;
			}
			List<SellerMainPage> quangou = sellerMainPageDao
					.findByIds("195,481");
  
			if (pageIndex == totalPage || (pageIndex == 1 && totalPage == 0)) {

				if (quangou != null && quangou.size() > 0) {
					for (SellerMainPage smp : quangou) {
						SellerMainPage.InnerSellerMainPage page = smp.new InnerSellerMainPage(
								lat, lng);
						page.setBasePath(basePath);
						page.setSellerIntroduce(smp.getRemark());
						// 判断该商品是否被关注
						model.clearQuery();
						model.combPreEquals("seller.id", smp.getSellerId(),
								"sellerId");
						model.combPreEquals("users.id", userId, "userId");
						model.combPreEquals("isfocus", true);
						int cou = dateBaseDAO.findCount(ShopConcern.class,
								model);
						page.setConcern(cou < 1 ? false : true);
						// 查找出所有的积分商品
						model.clearQuery();
						model.combCondition("addedTime < now()");
						model.combCondition("shelvesTime > now()");
						model.combPreEquals("isValid", true);
						model.combPreEquals("isChecked", true);
						model.combPreEquals("snapshotGoods.seller.id",
								page.getSellerId(), "sellerId");
						model.setOrder("id desc");
						List<ReGoodsOfSellerMall> mallList = dateBaseDAO
								.findPageList(ReGoodsOfSellerMall.class, model,
										0, 3);
						
						int scoreCount = dateBaseDAO.findCount(ReGoodsOfScoreMall.class,model);
						List<Object> tags =  new ArrayList<Object>();
						if (scoreCount > 0) {
							
							tags.add("积分");
							
						}
						
						// 查找出所有优惠券的商品
						model.clearQuery();
						model.combPreEquals("isValid", true);
						model.combPreEquals("sign", 1);// 换券中,未下架标识
						model.combPreEquals("seller.id", page.getSellerId(), "sellerid");
						model.combPreEquals("isActivity", 1);
						Integer extenCount = dateBaseDAO.findCount(
								ReGoodsofextendmall.class, model);

						
						if (extenCount > 0) {
							tags.add("优惠券");
						}
						if(tags.size()>0){
							page.setSellerActiveTags(tags);
						}
						List<Object> goodsList = new ArrayList<>();
						for (ReGoodsOfSellerMall mall : mallList) {
							goodsList.add(getGoodsData(mall, basePath));
							

						}
						page.setRecommendSellerGoodsList(goodsList);
						double dis = page.getDistance();
						page.setDistance(CalcUtil.div(dis, 1000, 2));
						// 加入到page中(店铺介绍)
						sellerList.add(page);
						// 找出是否关注
						model.clearQuery();
						model.combPreEquals("seller.id", smp.getSellerId(),
								"sellerId");
						model.combPreEquals("users.id", userId, "userId");
						List<ShopConcern> cern = dateBaseDAO.findPageList(
								ShopConcern.class, model, 0, 1);
						if (cern.size() > 0 && cern.get(0).getIsfocus()) {
							smp.setConcern(true);
						} else {
							smp.setConcern(false);
						}
						cern = null;
					}
				}

			}
			if(xcx == null){
				
				// 缓存地区和标签导航栏 每次都是一样的结果
				Object typeList = CacheUtil.getCacheByName("zbTypeList");
				if (typeList != null) {
					dataMap.put("typeList", typeList);
					dataMap.put("cityList", CacheUtil.getCacheByName("zbCityList"));
				} else {
					// 获取店铺类别列表================lxh
					List<Map<String, Object>> shopType = getShopType();
					List<Map<String, Object>> cityList = getCityList();
					dataMap.put("typeList", shopType);
					dataMap.put("cityList", cityList);
					CacheUtil.setCache("zbTypeList", shopType);
					CacheUtil.setCache("zbCityList", cityList);
				}
			}

			dataMap.put("pageSize", totalPage);
			dataMap.put("pageIndex", pageIndex);
			dataMap.put("pageItemCount", pagecounet);
			dataMap.put("dataList", sellerList);
			
			
			
			
		}
		return JsonResponseUtil.getJson(0x01, "请求成功", dataMap);
	}

	// 商家中心
	@Override
	public Map<String, Object> getSellerInfo(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {// 错误的参数；
			return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
		}
		Map<String, Object> statusMap = new HashMap<String, Object>();
		statusMap.put("status", 0x01);
		statusMap.put("message", "请求成功");

		Integer sellerId = parameter.getData().getInteger("sellerId");
		Seller seller = sellerDao.findById(sellerId);// 商家对象
		AdminUser admin = adminUserDao.getBySellerId(sellerId);
		String inviteCode = seller.getInviteCode();
		if (admin != null) {
			inviteCode = admin.getInvitecode();
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("seller", seller);
		dataMap.put("inviteCode", inviteCode);
		dataMap.put("availableMoney", seller.getMoney());// 商家的可用现金
		dataMap.put("unavailableMoney",
				sellerMoneyRecordDao.getDisComfirmedSum(sellerId));// 商家的冻结现金
		dataMap.put("pendingExchangeNumber", reGoodsorderDao.getCountByStatus(
				sellerId, ReGoodsorder.dai_dui_huan));// 待兑换订单数量
		dataMap.put("pendingConfirmationNumber", reGoodsorderDao
				.getCountByStatus(sellerId, ReGoodsorder.dai_que_ren));// 待确认订单数量
		dataMap.put("pendingReceiptNumber", reGoodsorderDao.getCountByStatus(
				sellerId, ReGoodsorder.dai_fa_huo));// 待发货订单数量
		dataMap.put("backAndCustomerServiceNumber",
				reBackOrderDao.getNotHandledCountBySeller(sellerId));// 退单售后订单数量
		dataMap.put("fansNumber", usersDao.getFansCount(seller.getInviteCode()));// 粉丝数量
		statusMap.put("data", dataMap);
		return statusMap;
	}

	/**
	 * 根据积分商品对象获取需要返回的数据；
	 * 
	 * @param goodsObject
	 * @param basePath
	 * @return
	 */
	private Map<String, Object> getGoodsData(ReGoodsOfSellerMall g,
			String basePath) {

		// 返回值；
		Map<String, Object> map = new HashMap<>();
		map.put("concern", false);// 表示是否关注了该产品，先默认都为false，后期再改；

		if (g.getPrice() > 0 && g.getDisplayPrice() > 0) {
			double b = CalcUtil.div(g.getPrice(), g.getDisplayPrice(), 2);
			map.put("discount", CalcUtil.mul(b, 10, 2) + "折");// 商品折扣
		}

		// 为多图添加前序
		String covers = g.getSnapshotGoods().getCoverPic();
		if (StringUtil.hasLength(covers) && covers.startsWith("[")) {
			com.alibaba.fastjson.JSONArray array = com.alibaba.fastjson.JSONArray
					.parseArray(covers);
			if (array.size() > 0) {
				covers = basePath + array.getJSONObject(0).getString("imgUrl");
			}
		} else {
			covers = "";
		}

		// 商品是否带积分
		if (g.getIsSendScore() != null && g.getIsSendScore() == true
				&& g.getSendScoreNum() != null && g.getSendScoreNum() != 0) {
			map.put("canGetScore", true);
		} else {
			map.put("canGetScore", false);
		}
		Seller seller = g.getSnapshotGoods().getSeller();
	
		seller.setBasePath(basePath);
		map.put("seller", seller);// 商家信息

		map.put("goodsId", "sem" + g.getId());
		map.put("name", g.getSnapshotGoods().getName());
		map.put("price", g.getPrice());
		int score = g.getNoStandardScore() == null ? 0 : g.getNoStandardScore();
		score = score == 0 ? g.getScore() : score;

		// map.put("score", score);
		// map.put("cashpoint", g.getRedPaper());
		map.put("costPrice", g.getDisplayPrice());
		map.put("coverPic", covers);
		map.put("expressTactics", g.getTransportationName());
		if(g.getFirstStandardJsonForPhone()!=null){
			map.put("spec", g.getFirstStandardJsonForPhone());
		}else{
			map.put("spec", "");
		}
		
		map.put("specNotes", g.getSecondStandardJsonForPhone(null));
		map.put("mallType", 1);
		map.put("salesVolume", g.getSalesVolume());
		map.put("commentCount", g.getCommentCount());
	
		return map;
	}

	/**
	 * 根据积分商品对象获取需要返回的数据；
	 */
	private Map<String, Object> getGoodsData2(ReGoodsOfScoreMall g,
			String basePath) {

		// 返回值；
		Map<String, Object> map = new HashMap<>();
		map.put("concern", false);// 表示是否关注了该产品，先默认都为false，后期再改；

		if (g.getPrice() > 0 && g.getDisplayPrice() > 0) {
			double b = CalcUtil.div(g.getPrice(), g.getDisplayPrice(), 2);
			map.put("discount", CalcUtil.mul(b, 10, 2) + "折");// 商品折扣
		}

		// 为多图添加前序
		String covers = g.getSnapshotGoods().getCoverPic();
		if (StringUtil.hasLength(covers) && covers.startsWith("[")) {
			com.alibaba.fastjson.JSONArray array = com.alibaba.fastjson.JSONArray
					.parseArray(covers);
			if (array.size() > 0) {
				covers = basePath + array.getJSONObject(0).getString("imgUrl");
			}
		} else {
			covers = "";
		}

		Seller seller = g.getSnapshotGoods().getSeller();
		seller.setBasePath(basePath);
		map.put("seller", seller);// 商家信息

		map.put("goodsId", "scm" + g.getId());
		map.put("name", g.getSnapshotGoods().getName());
		// map.put("price", g.getPrice());
		int score = g.getNoStandardScore() == null ? 0 : g.getNoStandardScore();
		score = score == 0 ? g.getScore() : score;

		map.put("score", score);
		// map.put("cashpoint", g.getRedPaper());
		map.put("costPrice", g.getDisplayPrice());
		map.put("coverPic", covers);
		map.put("expressTactics", g.getTransportationName());
		map.put("spec", g.getFirstStandardJsonForPhone());
		map.put("specNotes", g.getSecondStandardJsonForPhone(null));
		map.put("mallType", "scm");
		map.put("salesVolume", g.getSalesVolume());
		map.put("commentCount", g.getCommentCount());
		return map;
	}

	// ===============================================================================================
	private Map<String, Object> getSellerMainPageMap(
			SellerMainPage sellerMainPage, String basePath, Integer sellerId,
			Integer userId, double lng, double lat) {
		Map<String, Object> sellerMap = new HashMap<String, Object>();
		sellerMap.put("sellerId", sellerMainPage.getSellerId());
		sellerMap.put("sellerName", sellerMainPage.getSellerName());
		sellerMap.put("sellerIcon",
				StringUtil.hasLength(sellerMainPage.getSellerLogo()) ? basePath
						+ sellerMainPage.getSellerLogo() : "");
		sellerMap.put("sellerAddress", sellerMainPage.getSeller().getAddress());
		sellerMap.put("sellerPhone", sellerMainPage.getSeller().getPhone());
		sellerMap.put("axpAdminUserId", "axp"+sellerMainPage.getSeller().getAdminUser().getId());
		SellerMainPage.InnerSellerMainPage page = sellerMainPage.new InnerSellerMainPage(
				lat, lng);
		sellerMap.put("distance", CalcUtil.div(page.getDistance(),1000,2));
		// 店铺介绍轮播图
		String adStr = sellerMainPage.getTopCarouselAd();
		List<Map<String, Object>> adList = new ArrayList<>();
		if (adStr != null) {
			JSONArray adJson = JSONArray.fromObject(adStr);
			List<SellerMPbyJson> smpList = JSONArray.toList(adJson,
					SellerMPbyJson.class);

			Map<String, Object> smpMap = null;
			for (SellerMPbyJson smp : smpList) {
				if (!StringUtil.hasLength(smp.getImg()))
					continue;
				smpMap = new HashMap<>();
				smpMap.put("image", basePath + smp.getImg());
				smpMap.put("uri", smp.getUrl());
				adList.add(smpMap);
			}
		}
		sellerMap.put("images", adList);
		// 店铺介绍视频
		String viewStr = sellerMainPage.getSellerView();
		List<Map<String, Object>> viewList = new ArrayList<>();
		if (sellerMainPage.getHasVideo()) {
			if (StringUtils.isNotEmpty(viewStr)) {
				JSONArray viewJson = JSONArray.fromObject(viewStr);
				List<SellerMPbyJson> smpList = JSONArray.toList(viewJson,
						SellerMPbyJson.class);

				Map<String, Object> smpMap = null;
				for (SellerMPbyJson smp : smpList) {
					if (!StringUtil.hasLength(smp.getImg()))
						continue;
					smpMap = new HashMap<>();
					smpMap.put("image", basePath + smp.getImg());
					smpMap.put("video", smp.getUrl());
					viewList.add(smpMap);
				}
			}
		}
		sellerMap.put("videos", viewList);

		// 横幅广告
		String bannerStr = sellerMainPage.getBannerAd();
		List<Map<String, Object>> bannerList = new ArrayList<>();
		if (StringUtils.isNotBlank(bannerStr)) {
			JSONArray bannerJson = JSONArray.fromObject(bannerStr);
			List<SellerMPbyJson> smpList = JSONArray.toList(bannerJson,
					SellerMPbyJson.class);

			Map<String, Object> smpMap = null;
			for (SellerMPbyJson smp : smpList) {
				if (!StringUtil.hasLength(smp.getImg()))
					continue;
				smpMap = new HashMap<>();
				smpMap.put("image", basePath + smp.getImg());
				smpMap.put("uri", smp.getUrl());
				bannerList.add(smpMap);
			}
		}
		sellerMap.put("banner", bannerList);
		// 营业时间
		String beginTime = sellerMainPage.getBeginTime();
		String endTime = sellerMainPage.getEndTime();
		sellerMap.put("shopHours", beginTime + "-" + endTime);
		// 是否收藏
		boolean concern = shopConcernDao.isConcern(sellerId, userId);
		sellerMap.put("concern", concern);
		sellerMap.put("sellerIntroduce", sellerMainPage.getRemark());
		
		// 店铺所在纬度
		sellerMap.put("lat", sellerMainPage.getSeller().getLatitude());
		// 店铺所在经度
		sellerMap.put("lng", sellerMainPage.getSeller().getLongitude());
		// 优惠策略
		List<Map<String, Object>> preferentialsList = new ArrayList<Map<String, Object>>();
		Map<String, Object> preferentialsMap = new HashMap<String, Object>();
		preferentialsMap.put("name", "更多优惠");
		preferentialsMap.put("image",
				"http://7viiz2.com1.z0.glb.clouddn.com/icon_app.png");
		preferentialsList.add(preferentialsMap);
		sellerMap.put("preferentials", preferentialsList);
		return sellerMap;
	}

	@Override
	public Map<String, Object> saveSellerAddress(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		Parameter parameter = ParameterUtil.getParameter(request);
		String basePath = request.getServletContext()
				.getAttribute("RESOURCE_LOCAL_URL").toString();
		Integer sellerId = Integer.parseInt(parameter.getData().getString(
				"sellerId"));
		String sellerAddress = parameter.getData().getString("sellerAddress");
		Double lat = StringUtils.isEmpty(parameter.getData().getString("lat")) ? null
				: Double.parseDouble(parameter.getData().getString("lat"));
		Double lng = StringUtils.isEmpty(parameter.getData().getString("lng")) ? null
				: Double.parseDouble(parameter.getData().getString("lng"));
		if (StringUtils.isBlank(sellerAddress)) {
			return JsonResponseUtil.getJson(-0x01, "请获取地址再提交");
		}

		if (lat == null || lng == null) {
			return JsonResponseUtil.getJson(-0x01, "请开定位！");
		}
		Seller seller = sellerDao.findById(sellerId);
		if (seller != null) {
			seller.setAddress(sellerAddress);
			seller.setLatitude(lat);
			seller.setLongitude(lng);

			// SellerMainPage sellerMainPage =
			// sellerMainPageDao.findOneBySellerId(seller.getId());
			// sellerMainPage.setSellerAddress(sellerAddress);

			sellerDao.update(seller);
			// sellerMainPageDao.update(sellerMainPage);

			return JsonResponseUtil.getJson(0x01, "保存成功");
		} else {
			return JsonResponseUtil.getJson(-0x01, "保存失败");
		}

	}

	@Override
	public Seller getSellerByAdmin(Integer current_user_id) {
		QueryModel queryModel = new QueryModel();
		queryModel.clearQuery();
		queryModel.combPreEquals("edition", Seller.EDITION_NEW);
		queryModel.combPreEquals("level", Seller.LEVEL_MAIN);
		queryModel.combPreEquals("adminUser.id", current_user_id, "adId");
		Seller seller = (Seller) dateBaseDAO.findOne(Seller.class, queryModel);
		return seller;
	}

	@Override
	public Seller findById(Integer sellerId) {
		return sellerDAO.findById(sellerId);
	}

	public String[] removeRepeat(String[] nums) {
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < nums.length; i++) {
			if (StringUtils.isNotBlank(nums[i])) {
				set.add(nums[i]);
			}
		}
		String[] num = new String[set.size()];
		int i = 0;
		for (String str : set) {
			num[i] = str;
			i++;
		}
		return num;
	}

	public List<ProvinceEnum> getZoneById(Integer id, Integer level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SeLive getSeliveBySeller(Integer current_seller_id) {
		QueryModel queryModel = new QueryModel();
		queryModel.clearQuery();
		queryModel.combPreEquals("seller.id", current_seller_id, "seId");
		SeLive seLive = (SeLive) dateBaseDAO.findOne(SeLive.class, queryModel);
		return seLive;
	}

	@Override
	public Seller getSellerByUsersId(Integer usersId) {
		QueryModel queryModel = new QueryModel();
		queryModel.clearQuery();
		queryModel.combPreEquals("users.id", usersId, "user_id");
		queryModel.combPreEquals("isValid", true);
		List<Seller> seLive = (List<Seller>) dateBaseDAO.findList(Seller.class,
				queryModel);
		Seller seller = null;
		if (seLive != null && seLive.size() > 0) {
			seller = seLive.get(0);
		}

		return seller;
	}

	@Override
	public Seller getSellerByUsersId2(Integer usersId) {

		Seller seller = null;
		QueryModel model1 = new QueryModel();
		model1.combEquals("isvalid", 1);
		model1.combPreEquals("users.id", usersId, "userId");
		List<Seller> slist = dateBaseDAO.findPageList(Seller.class, model1, 0,
				pageSize);
		if (slist != null && slist.size() > 0) {
			seller = slist.get(0);
		}

		return seller;
	}

	@Override
	public Map<String, Object> applyGoods(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		Integer MType = Integer.parseInt(parameter.getData().getString("type"));// 推广类别：全国
																				// 100
																				// ；普通券200；活动券300；积分：400；秒杀500；拼团600
																				// 换货700  游戏商城(265,267)
																				// ；
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 1);
		map.put("message", "请求成功");
		switch (MType) {
		case 100:
			applyGoods100(parameter, map);
			break;
		case 200:
			applyGoods200(parameter, map);
			break;
		case 300:
			applyGoods300(parameter, map);
			break;
		case 400:
			applyGoods400(parameter, map);
			break;
		case 500:
			applyGoods500(parameter, map);
			break;
		case 600:
			applyGoods600(parameter, map);
			break;
		case 700:
			applyGoods700(parameter, map);
			break;
		case 265:
			applyGoods800(parameter, map);
			break;
		case 267:
			applyGoods800(parameter, map);
			break;
		default:
			break;
		}
		return map;
	}

	/**
	 * @author ZhangLu 全国推广
	 * */
	private void applyGoods100(Parameter parameter, Map<String, Object> map) {
		String goodsOrder = parameter.getData().getString("goodsOrder");// 周边店铺
		String sellerId = parameter.getSellerId();
		try {

			// 下架时间在审核通过后赋予
			ReGoodsOfSellerMall goodsOfSellerMall = null;
			ReGoodsOfLocalSpecialtyMall goodsOfLocalSpecialtyMall = null;
			String str = goodsOrder.substring(0, 3);
			String goodsId = goodsOrder.substring(3, goodsOrder.length());// 商品ID
			if (!str.equals("sem")) {
				goodsOfLocalSpecialtyMall = reGoodsOfLocalSpecialtyMallDao
						.findById(Integer.parseInt(goodsId));
				goodsOfSellerMall = goodsOfLocalSpecialtyMall
						.getReGoodsOfSellerMall();
			} else {
				String sellerMallId = goodsOrder.substring(3,
						goodsOrder.length());// 周边店铺数据id
				goodsOfSellerMall = reGoodsOfSellerMallDao.findById(Integer
						.parseInt(sellerMallId));
				QueryModel queryModel = new QueryModel();
				queryModel.combPreEquals("isValid", true);
				queryModel.combPreEquals("sellerMallId", sellerMallId);
				List<ReGoodsOfLocalSpecialtyMall> locallist = dateBaseDAO
						.findLists(ReGoodsOfLocalSpecialtyMall.class,
								queryModel);
				if (locallist.size() > 0) {
					return;
				} else {
					goodsOfLocalSpecialtyMall = new ReGoodsOfLocalSpecialtyMall();
				}

			}

			if (goodsOfSellerMall != null) {
				int ctId = 0;
				CommodityType commodityType = null;
				String ctType = goodsOfSellerMall.getSnapshotGoods().getType();
				if (StringUtils.isNotBlank(ctType)) {
					com.alibaba.fastjson.JSONArray array = com.alibaba.fastjson.JSONArray
							.parseArray(ctType);
					JSONObject object = array.getJSONObject(0);
					ctId = object.getIntValue("childTypeId");
					commodityType = commodityTypeDao.findById(ctId);
					goodsOfLocalSpecialtyMall.setCommodityType(commodityType);
				}

				goodsOfLocalSpecialtyMall
						.setPrice(goodsOfSellerMall.getPrice());
				goodsOfLocalSpecialtyMall.setDefaultRepertory(goodsOfSellerMall
						.getDefaultRepertory());
				goodsOfLocalSpecialtyMall
						.setReGoodsOfSellerMall(goodsOfSellerMall);
				goodsOfLocalSpecialtyMall.setDisplayPrice(goodsOfSellerMall
						.getDisplayPrice());
				goodsOfLocalSpecialtyMall.setAddedTime(new java.sql.Timestamp(
						System.currentTimeMillis()));
				goodsOfLocalSpecialtyMall.setBaseGoodsId(goodsOfSellerMall
						.getBaseGoodsId());
				goodsOfLocalSpecialtyMall.setCreateTime(new java.sql.Timestamp(
						System.currentTimeMillis()));
				goodsOfLocalSpecialtyMall.setIsChecked(false);
				goodsOfLocalSpecialtyMall.setSnapshotGoods(goodsOfSellerMall
						.getSnapshotGoods());
				goodsOfLocalSpecialtyMall.setTransportationType(1);
				goodsOfLocalSpecialtyMall.setTransportationPrice(0d);
				goodsOfLocalSpecialtyMall.setIsNoStandard(goodsOfSellerMall
						.getIsNoStandard());
				goodsOfLocalSpecialtyMall.setIsValid(true);
				goodsOfLocalSpecialtyMall.setIsChange(goodsOfSellerMall
						.getIsChange());
				goodsOfLocalSpecialtyMall.setNoStandardPrice(goodsOfSellerMall
						.getNoStandardPrice());
				goodsOfLocalSpecialtyMall.setNoStandardScore(goodsOfSellerMall
						.getNoStandardScore());
				goodsOfLocalSpecialtyMall
						.setNoStandardRepertory(goodsOfSellerMall
								.getNoStandardRepertory());
				goodsOfLocalSpecialtyMall.setIsNoStandard(goodsOfSellerMall
						.getIsNoStandard());
				goodsOfLocalSpecialtyMall.setSnapshotGoods(goodsOfSellerMall
						.getSnapshotGoods());
				goodsOfLocalSpecialtyMall.setStandardDetails(goodsOfSellerMall
						.getStandardDetails());
				goodsOfLocalSpecialtyMall.setRightsProtect(goodsOfSellerMall
						.getRightsProtect());
				reGoodsOfLocalSpecialtyMallDao
						.saveOrUpdate(goodsOfLocalSpecialtyMall);

				goodsOfLocalSpecialtyMall
						.setGoodsOrder(ReBaseGoods.LocalSpecialtyMall
								+ goodsOfLocalSpecialtyMall.getId());
				reGoodsOfLocalSpecialtyMallDao
						.update(goodsOfLocalSpecialtyMall);

				ReGoodsOfBase baseGoods = reGoodsOfBaseDao
						.findById(goodsOfSellerMall.getBaseGoodsId());
				try {
					String changeLaunchMall = baseGoods.changeLaunchMall(
							baseGoods.getLaunchMall() == null ? "000000000"
									: baseGoods.getLaunchMall(), 4, true);
					baseGoods.setLaunchMall(changeLaunchMall);
					reGoodsOfBaseDao.update(baseGoods);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			map.put("status", 1);
			map.put("message", "操作成功");
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			e.printStackTrace();
			map.put("status", -1);
			map.put("message", "操作失败");
		}

	}

	/**
	 * @author ZhangLu 普通优惠券
	 */
	private void applyGoods200(Parameter parameter, Map<String, Object> map) {
		try {
			String ticketprice = parameter.getData().getString("ticketprice");// 价格
			String ticketnum = parameter.getData().getString("ticketnum");// 数量
			String goodsOrder = parameter.getData().getString("goodsOrder");// 周边店铺
			String sellerMallId = goodsOrder.substring(3, goodsOrder.length());// 周边店铺数据id
			String zoneId = parameter.getZoneId();
			String commission = parameter.getData().getString("commission");// 佣金
			String display = parameter.getData().getString("display");// 发布在0 周边
																		// 1 全国
			String validtime = parameter.getData().getString("validtime");// 券有效期

			map = UrlUtil.getTaoKeToMap(sellerUrl
					+ "/Json/Index/addCoupon?&goodsOrder=" + goodsOrder
					+ "&ticketprice=" + ticketprice + "&ticketnum=" + ticketnum
					+ "&zone_id=" + zoneId + "&commission=" + commission
					+ "&display=" + display + "&validtime=" + validtime);

		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			e.printStackTrace();
			map.put("status", -1);
			map.put("message", "操作失败");
		}
	}

	/**
	 * "ticketnum": "2", "type": 200, "commission": "1", "validtime": 3,
	 * "baseGoodsId": 6928, "goodsOrder": "sem7519", "ticketprice": "8",
	 * "display": 0
	 */

	/**
	 * @author ZhangLu 活动优惠券
	 */
	private void applyGoods300(Parameter parameter, Map<String, Object> map) {
		try {
			String ticketprice = parameter.getData().getString("ticketprice");// 价格
			String ticketnum = parameter.getData().getString("ticketnum");// 价格
			String goodsOrder = parameter.getData().getString("goodsOrder");// 周边店铺
			String sellerMallId = goodsOrder.substring(3, goodsOrder.length());// 周边店铺数据id
			String titleOne = parameter.getData().getString("titleOne");// 活动标题1
			String contentOne = parameter.getData().getString("contentOne");// 活动内容
			String template = parameter.getData().getString("template");// 活动模板
			String template_img = parameter.getData().getString("template_img");// 模板图片
			String isRed = parameter.getData().getString("isRed");// 是否有红包
			String redNum = parameter.getData().getString("redNum");// 红包数量
			String redPrice = parameter.getData().getString("redPrice");// 红包金额
			String zoneId = parameter.getZoneId();
			String commission = parameter.getData().getString("commission");// 佣金
			String cid = parameter.getData().getString("cid");// 类别Id
			String display = parameter.getData().getString("display");// 发布在0 周边
																		// 1 全国
			String validtime = parameter.getData().getString("validtime");// 券有效期

			map = UrlUtil.getTaoKeToMap(sellerUrl
					+ "/Json/Index/addActivity?&goodsOrder=" + goodsOrder
					+ "&ticketprice=" + ticketprice + "&ticketnum=" + ticketnum
					+ "&titleOne=" + titleOne + "&contentOne=" + contentOne
					+ "&template=" + template + "&template_img=" + template_img
					+ "&isRed" + isRed + "&redNum=" + redNum + "&redPrice="
					+ redPrice + "&zoneId=" + zoneId + "&commission"
					+ commission + "&cid=" + cid + "&display=" + display
					+ "&validtime=" + validtime);

		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			e.printStackTrace();
			map.put("status", -1);
			map.put("message", "操作失败");
		}

	}

	/**
	 * @author ZhangLu 积分推广
	 * */
	private void applyGoods400(Parameter parameter, Map<String, Object> map) {
		String stock = parameter.getData().getString("stock");// 发布数量
		String score = parameter.getData().getString("score") == null ? "0"
				: parameter.getData().getString("score"); // 所需积分数
		String purchaseNum = parameter.getData().getString("purchaseNum");// 限购数量
		String startedTime = parameter.getData().getString("startedTime");// 活动开始时间
		String endTime = parameter.getData().getString("endTime");// 活动结束时间
		String goodsOrder = parameter.getData().getString("goodsOrder");// 周边店铺数据
		Boolean isRestrict = parameter.getData().getBoolean("isRestrict"); // 是否限购
		//da
		String scoreGoodMoney = parameter.getData().getString("scoreGoodMoney");//购买积分商品所需要的现金
		Integer transportationType = parameter.getData().getInteger(
				"transportationType");
		Double transportationPrice = parameter.getData().getDouble(
				"transportationPrice");
		String sellerId = parameter.getSellerId();
		
		

		try {
			ReGoodsOfScoreMall goodsOfScoreMall = null;
			ReGoodsOfSellerMall goodsOfSellerMall = null;
			String str = goodsOrder.substring(0, 3);// 商品id前缀
			if (!str.equals("sem")) {
				String goodsId = goodsOrder.substring(3, goodsOrder.length());
				goodsOfScoreMall = reGoodsOfScoreMallDao.findById(Integer
						.parseInt(goodsId));
				goodsOfSellerMall = goodsOfScoreMall.getReGoodsOfSellerMall();
			} else {
				String sellerMallId = goodsOrder.substring(3,
						goodsOrder.length());// 周边店铺数据id
				goodsOfScoreMall = new ReGoodsOfScoreMall();
				goodsOfSellerMall = reGoodsOfSellerMallDao.findById(Integer
						.parseInt(sellerMallId));
			}

			if (goodsOfSellerMall != null) {
				if (isRestrict) {
					goodsOfScoreMall.setCountLimit(Integer
							.parseInt(purchaseNum));
				}

				goodsOfScoreMall.setScore(Integer.parseInt(score));
				goodsOfScoreMall.setNoStandardScore(Integer.parseInt(score));
				goodsOfScoreMall.setBaseGoodsId(goodsOfSellerMall
						.getBaseGoodsId());
				goodsOfScoreMall.setIsValid(true);
				goodsOfScoreMall.setCreateTime(new java.sql.Timestamp(System
						.currentTimeMillis()));
				goodsOfScoreMall.setReGoodsOfSellerMall(goodsOfSellerMall);
				goodsOfScoreMall.setDisplayPrice(goodsOfSellerMall
						.getDisplayPrice());
				goodsOfScoreMall.setIsChange(goodsOfSellerMall.getIsChange());

				if (transportationType == null) {
					transportationType = goodsOfSellerMall
							.getTransportationType();
					transportationPrice = goodsOfSellerMall
							.getTransportationPrice();
				}
				goodsOfScoreMall.setTransportationType(transportationType);
				goodsOfScoreMall.setTransportationPrice(transportationPrice);
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				Date d = sf.parse(startedTime);
				Date d2 = sf.parse(endTime);
				goodsOfScoreMall.setAddedTime(new Timestamp(d.getTime()));
				goodsOfScoreMall.setShelvesTime(new Timestamp(d2.getTime()));
				goodsOfScoreMall.setReleaseNum(Integer.parseInt(stock));
				goodsOfScoreMall.setIsNoStandard(goodsOfSellerMall
						.getIsNoStandard());
				goodsOfScoreMall.setNoStandardPrice(goodsOfSellerMall
						.getNoStandardPrice());
				goodsOfScoreMall.setNoStandardRepertory(goodsOfSellerMall
						.getNoStandardRepertory());
				goodsOfScoreMall.setDefaultRepertory(goodsOfSellerMall
						.getDefaultRepertory());
				goodsOfScoreMall.setRightsProtect(goodsOfSellerMall
						.getRightsProtect());
				goodsOfScoreMall.setStandardDetails(goodsOfSellerMall
						.getStandardDetails());
				goodsOfScoreMall.setSnapshotGoods(goodsOfSellerMall
						.getSnapshotGoods());
				goodsOfScoreMall.setIsChecked(false);
				//da
				if(StringUtils.isEmpty(scoreGoodMoney)){
					scoreGoodMoney = "0.0";
				}
				goodsOfScoreMall.setScoreGoodMoney(scoreGoodMoney);
				
				reGoodsOfScoreMallDao.saveOrUpdate(goodsOfScoreMall);

				goodsOfScoreMall.setGoodsOrder(ReBaseGoods.ScoreMall
						+ goodsOfScoreMall.getId());
				
				reGoodsOfScoreMallDao.update(goodsOfScoreMall);

				/*
				 * surplusStock = (int)
				 * CalcUtil.sub(goodsOfSellerMall.getDefaultRepertory(),
				 * Integer.parseInt(stock));
				 * goodsOfSellerMall.setDefaultRepertory(surplusStock);
				 * reGoodsOfSellerMallDao.update(goodsOfSellerMall);
				 */

				ReGoodsOfBase baseGoods = reGoodsOfBaseDao
						.findById(goodsOfSellerMall.getBaseGoodsId());
				try {
					String changeLaunchMall = baseGoods.changeLaunchMall(
							baseGoods.getLaunchMall() == null ? "000000000"
									: baseGoods.getLaunchMall(), 2, true);
					baseGoods.setLaunchMall(changeLaunchMall);
					reGoodsOfBaseDao.update(baseGoods);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			map.put("status", 1);
			map.put("message", "操作成功");
		} catch (Exception e) {
			if (!TransactionAspectSupport.currentTransactionStatus()
					.isRollbackOnly()) {
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
			}
			e.printStackTrace();
			map.put("status", -1);
			map.put("message", "操作失败");
		}

	}

	/**
	 * @author ZhangLu 秒杀推广
	 * */
	private void applyGoods500(Parameter parameter, Map<String, Object> map) {
		String price = parameter.getData().getString("price");// 秒杀价格
		String stock = parameter.getData().getString("stock");// 发布数量
		String transportationType = parameter.getData().getString(
				"transportationType");// 配送方式
		String secondskilltimeId = parameter.getData().getString(
				"secondskilltimeId");// 秒杀时段
		String startedTime = parameter.getData().getString("startedTime");// 活动开始时间
		String endedTime = parameter.getData().getString("endedTime");// 活动结束时间
		String goodsOrder = parameter.getData().getString("goodsOrder");// 周边店铺数据
		String sellerId = parameter.getSellerId();

		try {
			ReGoodsOfSeckillMall goodsOfSeckillMall = null;
			ReGoodsOfSellerMall goodsOfSellerMall = null;
			String str = goodsOrder.substring(0, 3);// 商品id前缀
			if (!str.equals("sem")) {
				String goodsId = goodsOrder.substring(3, goodsOrder.length());
				goodsOfSeckillMall = reGoodsOfSeckillMallDao.findById(Integer
						.parseInt(goodsId));
				goodsOfSellerMall = goodsOfSeckillMall.getReGoodsOfSellerMall();
			} else {
				String sellerMallId = goodsOrder.substring(3,
						goodsOrder.length());// 周边店铺数据id
				goodsOfSeckillMall = new ReGoodsOfSeckillMall();
				goodsOfSellerMall = reGoodsOfSellerMallDao.findById(Integer
						.parseInt(sellerMallId));
			}

			/*
			 * if
			 * (Integer.parseInt(stock)>goodsOfSellerMall.getDefaultRepertory())
			 * { map.put("status", -1); map.put("message", "推广数量不能超过现有库存"); }
			 */

			CashshopTimes times = cashshopTimesDAO.findById(Integer
					.parseInt(secondskilltimeId));
			Set<CashshopTimes> cashtime = new HashSet<CashshopTimes>();
			cashtime.add(times);

			if (goodsOfSellerMall != null) {

				goodsOfSeckillMall.setNoStandardPrice(Double.valueOf(price));
				goodsOfSeckillMall.setPrice(Double.valueOf(price));
				goodsOfSeckillMall.setReleaseNum(Integer.parseInt(stock));
				goodsOfSeckillMall.setDisplayPrice(goodsOfSellerMall
						.getDisplayPrice());
				goodsOfSeckillMall.setTransportationType(Integer
						.valueOf(transportationType));
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				Date d = sf.parse(startedTime);
				Date d2 = sf.parse(endedTime);
				goodsOfSeckillMall.setAddedTime(new Timestamp(d.getTime()));
				goodsOfSeckillMall.setShelvesTime(new Timestamp(d2.getTime()));
				goodsOfSeckillMall.setBaseGoodsId(goodsOfSellerMall
						.getBaseGoodsId());
				goodsOfSeckillMall.setIsValid(true);
				goodsOfSeckillMall.setCreateTime(new java.sql.Timestamp(System
						.currentTimeMillis()));
				goodsOfSeckillMall.setReGoodsOfSellerMall(goodsOfSellerMall);
				goodsOfSeckillMall.setStandardDetails(goodsOfSellerMall
						.getStandardDetails());
				goodsOfSeckillMall.setTimes(cashtime);
				goodsOfSeckillMall.setNoStandardPrice(goodsOfSeckillMall
						.getNoStandardPrice());
				goodsOfSeckillMall.setNoStandardRepertory(goodsOfSellerMall
						.getNoStandardRepertory());
				goodsOfSeckillMall.setNoStandardScore(goodsOfSellerMall
						.getNoStandardScore());
				goodsOfSeckillMall.setIsNoStandard(goodsOfSellerMall
						.getIsNoStandard());
				goodsOfSeckillMall.setIsChecked(false);
				goodsOfSeckillMall.setStandardDetails(goodsOfSellerMall
						.getStandardDetails());
				goodsOfSeckillMall.setSnapshotGoods(goodsOfSellerMall
						.getSnapshotGoods());
				goodsOfSeckillMall.setRightsProtect(goodsOfSellerMall
						.getRightsProtect());
				reGoodsOfSeckillMallDao.saveOrUpdate(goodsOfSeckillMall);

				goodsOfSeckillMall.setGoodsOrder(ReBaseGoods.SeckillMall
						+ goodsOfSeckillMall.getId());
				reGoodsOfSeckillMallDao.update(goodsOfSeckillMall);

				/*
				 * surplusStock = (int)
				 * CalcUtil.sub(goodsOfSellerMall.getDefaultRepertory(),
				 * Integer.parseInt(stock));
				 * goodsOfSellerMall.setDefaultRepertory(surplusStock);
				 * reGoodsOfSellerMallDao.update(goodsOfSellerMall);
				 */

				ReGoodsOfBase baseGoods = reGoodsOfBaseDao
						.findById(goodsOfSellerMall.getBaseGoodsId());
				try {
					String changeLaunchMall = baseGoods.changeLaunchMall(
							baseGoods.getLaunchMall() == null ? "000000000"
									: baseGoods.getLaunchMall(), 3, true);
					baseGoods.setLaunchMall(changeLaunchMall);
					reGoodsOfBaseDao.update(baseGoods);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			e.printStackTrace();
			map.put("status", -1);
			map.put("message", "操作失败");
		}

	}

	/**
	 * @author ZhangLu 获取商品规格
	 * */
	public Map<String, Object> getStandardDetails(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		Map<String, Object> statusMap = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			String sellerId = parameter.getSellerId();
			QueryModel queryModel = new QueryModel();
			queryModel.combPreEquals("isValid", true);
			queryModel.combPreEquals("sellerId", Integer.parseInt(sellerId));
			AdminUser adminUser = (AdminUser) dateBaseDAO.findOne(
					AdminUser.class, queryModel);

			List<Map<String, Object>> standardDetails = new ArrayList<Map<String, Object>>();
			if (adminUser != null) {
				queryModel.clearQuery();
				queryModel.combPreEquals("isValid", true);
				queryModel.combPreEquals("adminUserId", adminUser.getId());
				queryModel.combCondition(" parentStandardId is not null");
				List<ReGoodsStandard> standlist = dateBaseDAO.findLists(
						ReGoodsStandard.class, queryModel);
				for (ReGoodsStandard goodsStandard : standlist) {
					Map<String, Object> maps = new HashMap<String, Object>();
					maps.put("standardId", goodsStandard.getId());
					maps.put("standardName", goodsStandard.getName());
					standardDetails.add(maps);
				}
				data.put("standardDetails", standardDetails);
				statusMap.put("data", data);
				statusMap.put("status", 1);
				statusMap.put("message", "请求成功");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			statusMap.put("status", -2);
			statusMap.put("message", "请求失败");
			if (!TransactionAspectSupport.currentTransactionStatus()
					.isRollbackOnly()) {
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
			}
		}
		return statusMap;
	}

	/**
	 * @author ZhangLu 获取时间段
	 * */
	public Map<String, Object> getCashOfTime(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		List<Map<String, Object>> timelists = new ArrayList<Map<String, Object>>();
		try {
			QueryModel model = new QueryModel();
			model.combPreEquals("isValid", true);
			List<CashshopTimes> cashtimelist = dateBaseDAO.findLists(
					CashshopTimes.class, model);
			for (CashshopTimes times : cashtimelist) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("timeId", times.getId().toString());
				map.put("startTime", times.getStartTime());
				map.put("endTime", times.getEndTime());
				timelists.add(map);
			}
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("secondskilltime", timelists);
			statusMap.put("data", data);
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功");
		} catch (Exception e) {
			e.printStackTrace();
			statusMap.put("status", -1);
			statusMap.put("message", "请求失败");
		}
		return statusMap;
	}

	/**
	 * @author ZhangLu 店铺管理信息
	 */
	@SuppressWarnings("deprecation")
	public Map<String, Object> getstoreInfo(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		Map<String, Object> sellerMap = new HashMap<String, Object>();
		String basePath = request.getServletContext()
				.getAttribute("RESOURCE_LOCAL_URL").toString();
		Integer sellerId = 0;
		try {
			if (parameter != null) {
				sellerId = Integer.parseInt(parameter.getSellerId());
			}
			Seller seller = sellerDao.findById(sellerId);
			if (seller == null) {
				return JsonResponseUtil.getJson(-0x0051, "店铺不存在");
			}
			SellerMainPage sellerMainPage = sellerMainPageDAO
					.findOneBySellerId(sellerId);
			if (sellerMainPage != null) {
				sellerMap.put("sellerId", String.valueOf(sellerId) == null ? ""
						: String.valueOf(sellerId));
				sellerMap
						.put("sellerLogo", StringUtils.isBlank(sellerMainPage
								.getSellerLogo()) ? "" : basePath
								+ sellerMainPage.getSellerLogo());
				sellerMap.put("sellerName", StringUtils.isBlank(seller
						.getName()) ? "" : seller.getName());
				// 店铺所在纬度
				sellerMap.put("lat", seller.getLatitude() == null ? "" : seller
						.getLatitude().toString());
				// 店铺所在经度
				sellerMap.put("lng", seller.getLongitude() == null ? ""
						: seller.getLongitude().toString());
				sellerMap.put("remark", StringUtils.isBlank(sellerMainPage
						.getRemark()) ? "" : sellerMainPage.getRemark());
				sellerMap.put(
						"phone",
						StringUtils.isBlank(seller.getPhone()) ? "" : seller
								.getPhone());
				sellerMap.put(
						"zone",
						StringUtils.isBlank(seller.getZone()) ? "" : seller
								.getZone()); // 省市区
				sellerMap.put("address", StringUtils.isBlank(seller
						.getAddress()) ? "" : seller.getAddress());
				sellerMap.put(
						"sellerView",
						sellerMainPage.getImgMap(basePath,
								sellerMainPage.getSellerView(), false));
				// 底部广告
				sellerMap.put(
						"bottomAds",
						sellerMainPage.getImgMap(basePath,
								sellerMainPage.getBannerAd(), true));

				// 顶部轮播
				String topAds = sellerMainPage.getTopCarouselAd();
				try {
					if (StringUtils.isNotBlank(topAds) && !topAds.equals("{}")
							&& !topAds.equals("[]")) {
						List<SellerMainPbyJson> list = JSONArray.toList(
								JSONArray.fromObject(topAds),
								SellerMainPbyJson.class);
						List<Map<String, String>> listMap = new ArrayList<>();
						int size = 0;
						if (StringUtils.isNotBlank(sellerMainPage
								.getSellerView())) {
							size = sellerMainPage.getSellerView().length() > 30 ? 6
									: 5;
						} else {
							size = 5;
						}

						for (int i = 0; i < size && i < list.size(); i++) {
							Map<String, String> map = new HashMap<>();
							map.put("imageUrl",
									StringUtils.isNotBlank(list.get(i)
											.getImageUrl()) ? basePath
											+ list.get(i).getImageUrl()
											: (StringUtils.isNotBlank(list.get(
													i).getImg()) ? basePath
													+ list.get(i).getImg() : ""));
							map.put("linkUrl",
									StringUtils.isNotBlank(list.get(i)
											.getLinkUrl()) ? list.get(i)
											.getLinkUrl()
											: StringUtils.isNotBlank(list
													.get(i).getUrl()) ? list
													.get(i).getUrl() : "");
							listMap.add(map);
						}

						sellerMap.put("topAds", listMap);
					} else {
						String[] listMap = new String[0];
						sellerMap.put("topAds", listMap);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				// 营业时间
				sellerMap.put("beginTime", sellerMainPage.getBeginTime());
				sellerMap.put("endTime", sellerMainPage.getEndTime());
				sellerMap.put("hasVideo", sellerMainPage.getHasVideo());
				sellerMap.put("sellerMainPageId",
						String.valueOf(sellerMainPage.getId()) == null ? ""
								: String.valueOf(sellerMainPage.getId()));
			} else {
				sellerMap.put("sellerId", String.valueOf(sellerId) == null ? ""
						: String.valueOf(sellerId));
				sellerMap.put("sellerLogo", "");
				sellerMap.put("sellerName", "");
				// 店铺所在纬度
				sellerMap.put("lat", "");
				// 店铺所在经度
				sellerMap.put("lng", "");
				sellerMap.put("remark", "");
				sellerMap.put("phone", "");
				sellerMap.put("zone", ""); // 省市区
				sellerMap.put("address", "");
				sellerMap.put("hasVideo", false);
				// 视频
				Map<String, String> ViewMap = new HashMap<>();
				ViewMap.put("imageUrl", "");
				ViewMap.put("linkUrl", ""); // 外部视频 不需要加资源地址

				Map<String, String> bannerMap = new HashMap<>();
				bannerMap.put("imageUrl", "");
				bannerMap.put("linkUrl", "");

				String[] topAds = new String[0];

				sellerMap.put("sellerView", ViewMap);
				// 底部广告
				sellerMap.put("bottomAds", bannerMap);
				// 顶部轮播
				sellerMap.put("topAds", topAds);
				// 营业时间
				sellerMap.put("beginTime", "");
				sellerMap.put("endTime", "");
				sellerMap.put("sellerMainPageId", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponseUtil.getJson(-1, "请求失败");
		}
		return JsonResponseUtil.getJson(1, "请求成功", sellerMap);
	}

	/**
	 * 店铺管理
	 */
	@Override
	public Map<String, Object> shopManage(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		JSONObject data = parameter.getData();
		Integer sellerId = Integer.parseInt(parameter.getSellerId());
		String sellerLogo = data.getString("sellerLogo");
		String sellerName = data.getString("sellerName");
		String beginTime = data.getString("beginTime");
		String endTime = data.getString("endTime");
		String remark = data.getString("remark");
		String phone = data.getString("phone");
		String bannerAd = data.getString("bottomAds");
		Integer sellerMainPageId = data.getInteger("sellerMainPageId");
		String topCarouselAd = data.getString("topAds"); // 顶部五张广告图 img和url
		String sellerView = data.getString("sellerView");
		Double latiude = data.getDouble("lat");
		Double longitude = data.getDouble("lng");
		String address = data.getString("address");
		String zone = data.getString("zone");
		Boolean hasVideo = data.getBoolean("hasVideo");
		try {

			if (StringUtils.isBlank(zone)) {
				return JsonResponseUtil.getJson(-1, "请选择区域");
			}

			/*if (parameter.getOs().equals("ANDROID")) {

				String[] split = zone.split("-"); // 省-市-区
				Genocoding genocoding = UrlUtil.genocoding(address, split[1]); // 市
				if (genocoding == null) {
					return JsonResponseUtil.getJson(-1, "请重新输入正确地址");
				}
				latiude = genocoding.getLat();
				longitude = genocoding.getLng();
			}*/

			if (StringUtils.isBlank(bannerAd)
					|| StringUtils.isNotBlank(bannerAd)
					&& bannerAd.equals("[]")) {
				bannerAd = "";
			}

			Seller seller = sellerDao.findById(sellerId);
			SellerMainPage sellerMainPage = null;
			if (sellerMainPageId != null) {
				sellerMainPage = sellerMainPageDao.findById(sellerMainPageId);
			}

			if (sellerMainPage == null) {
				sellerMainPage = new SellerMainPage();
			}
			sellerMainPage.setBeginTime(beginTime);
			sellerMainPage.setEndTime(endTime);

			sellerMainPage.setTopCarouselAd(StringUtil
					.replaceJsonArray(topCarouselAd));
			sellerMainPage.setHasVideo(hasVideo);
			sellerMainPage.setSellerView(StringUtil
					.replaceJsonObject(sellerView));
			sellerMainPage.setIsValid(true);
			sellerMainPage.setSeller(seller);
			sellerMainPage.setSellerLogo(sellerLogo);
			sellerMainPage.setRemark(remark);
			sellerMainPage.setBannerAd(StringUtil.replaceJsonObject(bannerAd));
			sellerMainPageDao.saveOrUpdate(sellerMainPage);
			seller.setLogo(sellerLogo);
			// seller.setZone(zone.replaceAll("-", ""));
			seller.setZone(zone);
			// seller.setAddress(zone.replaceAll("-", "")+address);
			seller.setAddress(address);
			seller.setPhone(phone);
			seller.setName(sellerName);
			seller.setLatitude(latiude);
			seller.setLongitude(longitude);
			sellerDao.save(seller);

		} catch (Exception e) {
			if (!TransactionAspectSupport.currentTransactionStatus()
					.isRollbackOnly()) {
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
			}
			e.printStackTrace();
			return JsonResponseUtil.getJson(-1, "操作失败");
		}
		return JsonResponseUtil.getJson(1, "操作成功");
	}

	/**
	 * 商品列表 商品管理首页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public Map<String, Object> getGodosListByStatus(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {// 错误的参数；
			return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("status", 1);
		map.put("message", "操作成功");
		try {

			String basePath = request.getServletContext()
					.getAttribute("RESOURCE_LOCAL_URL").toString();
			JSONObject jsonData = parameter.getData();
			Integer statusId = jsonData.getInteger("statusId");

			if (StringUtils.isBlank(parameter.getSellerId())) {
				map.put("status", -1);
				map.put("message", "参数错误");
				return map;
			}

			Integer sellerId = Integer.parseInt(parameter.getSellerId());
			Integer pageIndex = jsonData.getInteger("pageIndex") == null ? 1
					: jsonData.getInteger("pageIndex");
			Integer activityId = jsonData.getInteger("activityId");
			Integer pageSizes = 4;
			Integer sellingCount = 0;
			Integer checkingCount = 0;
			Integer waitingCount = 0;
			boolean isList = statusId != null;
			Integer adminId = Integer.parseInt(parameter.getAdminuserId());
			List<Map<String, Object>> sList = new ArrayList<>();
			Seller seller = sellerDAO.findById(sellerId);
			List<Map<String, Object>> list = new ArrayList<>();
			if (activityId != null) {
				statusId = 1000;
			}
			QueryModel queryModel = new QueryModel();
			queryModel.combEquals("isValid", 1);
			queryModel.combCondition(" addedTime<=now()"
					+ " and snapshotGoods.seller.id=" + sellerId + "");
			queryModel.setOrder("id asc");
			QueryModel model = new QueryModel(); // 优惠券
			model.combEquals("isValid", 1);
			model.combCondition("validtime>now()");
			model.combPreEquals("seller.id", sellerId, "sellerId");
			model.setOrder("id asc");
			QueryModel teamQueryModel = new QueryModel(); // 拼团
			teamQueryModel.combEquals("isValid", 1);
			teamQueryModel.combPreEquals("seller.id", sellerId, "sellerId");
			teamQueryModel.setOrder("id desc");
			if (statusId.intValue() == 1000) {
				// queryModel.combCondition("  shelvesTime>now() ");
				queryModel.combPreEquals("isChecked", true);
				model.combPreEquals("isChecked", true);
				teamQueryModel.combPreEquals("isChecked", true);

			} else if (statusId.intValue() == 1001) {
				queryModel
						.combCondition(" (isChecked=false or isChecked is null )");
				model.combCondition(" (isChecked=false or isChecked is null )");
				teamQueryModel
						.combCondition(" (isChecked=false or isChecked is null )");
			}

			// typeId activityId（ 全部 0 全国特产1 周边店铺 2，积分商品3，普通优惠券4
			// 活动优惠券5，秒杀6，拼团7,换货会 8 )
			// statusId审核中1001 审核失败 1002 待处理 1003
			if (activityId == null) {

				// 查询各类总数
				sellingCount = getGoodsCountByStatus(1000, sellerId);
				checkingCount = getGoodsCountByStatus(1001, sellerId);
				waitingCount = reGoodsOfSellerMallDao.findLastSellerMall(
						sellerId).size(); // 待处理数量
			}
			// 如果是待处理 //只查周边
			if (statusId.intValue() == 1003 && activityId == null) {
				List<ReGoodsOfSellerMall> sellerMallList = reGoodsOfSellerMallDao
						.findLastSellerMall(sellerId);

				for (ReGoodsOfSellerMall reGoodsOfSellerMall : sellerMallList) {
					ReGoodsOfBase goodsOfBase = reGoodsOfBaseDao
							.findById(reGoodsOfSellerMall.getBaseGoodsId());
					if (goodsOfBase.getLaunchMall().equals("0000000")) {
						goodsOfBase.setLaunchMall("000000000");
						reGoodsOfBaseDao.merge(goodsOfBase);
					}
					Map<String, Object> goodsMap = getGoodsDetailed(
							reGoodsOfSellerMall, 2, statusId, 0, basePath, 0d);
					list.add(goodsMap);
				}
				map.put("data", list);
			} else {
				List<ReGoodsOfSellerMall> sellerMallList = (List<ReGoodsOfSellerMall>) dateBaseDAO
						.findList(ReGoodsOfSellerMall.class, queryModel);
				List<ReGoodsOfSeckillMall> seckillMallList = (List<ReGoodsOfSeckillMall>) dateBaseDAO
						.findList(ReGoodsOfSeckillMall.class, queryModel);
				List<ReGoodsOfScoreMall> scoreMallList = (List<ReGoodsOfScoreMall>) dateBaseDAO
						.findList(ReGoodsOfScoreMall.class, queryModel);
				List<ReGoodsOfLocalSpecialtyMall> localMallList = (List<ReGoodsOfLocalSpecialtyMall>) dateBaseDAO
						.findList(ReGoodsOfLocalSpecialtyMall.class, queryModel);
				List<ReGoodsofextendmall> extendMallList = (List<ReGoodsofextendmall>) dateBaseDAO
						.findList(ReGoodsofextendmall.class, model);
				List<ReGoodsOfTeamMall> teamMallList = (List<ReGoodsOfTeamMall>) dateBaseDAO
						.findList(ReGoodsOfTeamMall.class, teamQueryModel);
				List<ReGoodsOfChangeMall> changeList = dateBaseDAO.findLists(
						ReGoodsOfChangeMall.class, queryModel);
				List<ReGoodsOfLockMall> lockMallList = (List<ReGoodsOfLockMall>) dateBaseDAO
						.findList(ReGoodsOfLockMall.class, queryModel);
				// 查询是否有推广优惠券权限
				AdminUser adminUser = adminUserDao.findById(adminId);
//				List<TkldPid> tkldPidList = new ArrayList<>();
//				if (seller != null && seller.getUsers() != null) {
//					tkldPidList = tkldPidDao.findByPropertyIsValid("users.id",
//							seller.getUsers().getId());
//				}

				// 有顺序的
				Map<String, Object> couponMap = new HashMap<>();
				couponMap.put("permission", true);
				couponMap.put("desc", "");
				Map<String, Object> activityCouponMap = new HashMap<>();
				activityCouponMap.put("permission", true);
				activityCouponMap.put("desc", "");

				// 全国权限
				boolean nationwide = adminUserDao.isNationwide(adminUser
						.getRePermissionRoleId());

				boolean copy = nationwide;
				Map<String, Object> scoreMap = new HashMap<>();

				// Boolean isCareerPartner =
				// tkldPidService.isCareerPartner(adminUser);

				// if(isCareerPartner){
				scoreMap.put("permission", true);// 积分权限
				// }else{
				// scoreMap.put("permission", false);
				// }

				scoreMap.put("desc", "暂未开通此功能,敬请关注");
				Map<String, Object> secKillMap = new HashMap<>();
				secKillMap.put("permission", true);
				secKillMap.put("desc", "可以推广秒杀");

				Map<String, Object> teamMap = new HashMap<>();
				teamMap.put("permission", adminUser.getIsTeam() == null ? false
						: adminUser.getIsTeam());
				teamMap.put("desc", "请先开通拼团权限");

				Map<String, Object> changeMap = new HashMap<>();
				changeMap.put("permission", true);
				changeMap.put("desc", "");
				if (isList) {
					// 周边
					for (ReGoodsOfSellerMall sellerMall : sellerMallList) {
						nationwide = copy;
						Map<String, Object> goodsMap = null;
						String msg = "你没有推广全国权限";
						goodsMap = getGoodsDetailed(sellerMall, 2, statusId, 0,
								basePath, 0d);
						List<Map<String, Object>> permissionList = new ArrayList<>();
						if (nationwide) {
							if (sellerMall.getTransportationType().intValue() != 1) {
								nationwide = false;
								msg = "该产品不是包邮产品,不能投放到全国";
							}
						}
						Map<String, Object> nationwideMap = new HashMap<>();
						if (nationwide) {
							nationwide = reGoodsOfSellerMallDao
									.isLocalClassify(sellerMall
											.getBaseGoodsId());
							if (!nationwide) {
								nationwide = false;
								msg = "该产品不是全国特产分类,请重新选择分类";
							}
						}

						nationwideMap.put("permission", nationwide);
						nationwideMap.put("desc", msg);
						// permissionList 是有顺序要求的
						permissionList.add(nationwideMap);
						permissionList.add(couponMap); //十积分
						permissionList.add(activityCouponMap);//倒计时
						permissionList.add(scoreMap);
						permissionList.add(secKillMap);
						permissionList.add(teamMap);
						permissionList.add(changeMap);
						goodsMap.put("permission", permissionList);
						// 判断是否重复推广
						goodsMap.put("permission2",
								getGoodsPublishStatus(sellerMall.getId()));
						sList.add(goodsMap);
					}
				}

				// 只有事业合伙人才能发布积分商品 记得改
				// 优惠价只会在各地特产和周边店铺存在 //优惠券不兼容全国商品 以前全国转优惠券的 一律不展示
				if (activityId != null && (activityId == 0 || activityId == 3)
						|| isList) {
					// 积分商品
					for (ReGoodsOfScoreMall scoreMall : scoreMallList) {
						Map<String, Object> goodsMap = getGoodsDetailed(
								scoreMall, 3, statusId, 0, basePath, 0d);
						goodsMap.put("stock", scoreMall.getReleaseNum()==null?0:scoreMall.getReleaseNum());
						list.add(goodsMap);
					}
					
					for(ReGoodsOfLockMall lockMall : lockMallList){
						Map<String, Object> goodsMap = getGoodsDetailed(lockMall, 3, statusId, 0, basePath, 0d);
						goodsMap.put("stock", lockMall.getReleaseNum()==null?0:lockMall.getReleaseNum());
						list.add(goodsMap);
					}
					
					
				}

				String deductPrice = ""; // 劵后价
				if (activityId != null
						&& (activityId == 0 || activityId == 4 || activityId == 5)
						|| isList) {
					// 优惠券
					for (ReGoodsofextendmall extendMall : extendMallList) {
						ReBaseGoods g;
						int typeId = 2;
						if (extendMall.getMall() == ReGoodsOfBase.localSpecialtyMall) {
							typeId = 1;
							g = reGoodsOfLocalSpecialtyMallDao
									.findById(extendMall.getMallId());
						} else {
							g = reGoodsOfSellerMallDao.findById(extendMall
									.getMallId());
						}
						int isActivity = extendMall.getIsActivity() == null ? 0
								: extendMall.getIsActivity();

						if (extendMall.getValidtime().before(new Date())) {
							String goodsOrder = extendMall.getGoodsMall();
							String str = goodsOrder.substring(0, 3);
							String Id = goodsOrder.substring(3,
									goodsOrder.length());
							delGoods(String.valueOf(isActivity == 1 ? 5 : 4),
									statusId.toString(), g.getBaseGoodsId(),
									str, Id, goodsOrder);
							continue;
						}

						Map<String, Object> goodsMap = null;
						goodsMap = getGoodsDetailed(g, typeId, statusId, 0,
								basePath, 0d);
						if (!g.getIsNoStandard()) {
							deductPrice = reGoodsofextendmallService
									.computeDeductPrice(g.getSpecifications(),
											extendMall.getTicketprice());
							// specifications = sellerMall.getSpecifications();
						} else {
							deductPrice = String.valueOf(CalcUtil.sub(
									g.getNoStandardPrice(),
									extendMall.getTicketprice()));
						}

						// 活动优惠券
						if (isActivity == 1) {
							goodsMap.put("typeId", 5);
							goodsMap.put("ticketsName", "活动优惠券");
						} else {
							goodsMap.put("typeId", 4);
							goodsMap.put("ticketsName", "普通优惠券");
						}
						// 优惠券商品
						if (extendMall.getIsChecked() == null) {
							goodsMap.put("statusId", 1002);
						}
						goodsMap.put("ticketsNumber", extendMall.getTicketnum()); // 劵数
						goodsMap.put("deductPrice", deductPrice);
						goodsMap.put("checkFailedMsg",
								extendMall.getCheckDesc());// 审核说明
						list.add(goodsMap);
					}
				}

				if (activityId != null && (activityId == 0 || activityId == 7)
						|| isList) {
					// 拼团商品
					for (ReGoodsOfTeamMall teamMall : teamMallList) {

						// ReGoodsOfSellerMall sellerMall =
						// teamMall.getReGoodsOfSellerMall();
						Map<String, Object> goodsMap = null;
						goodsMap = getGoodsDetailed(teamMall, 7, statusId, 0,
								basePath, teamMall.getDiscountPrice());
						goodsMap.put("ptPeopleNumber", teamMall.getTeamNum());// 最低拼团人数
						goodsMap.put("stock", teamMall.getReleaseNum()); // 库存
						goodsMap.put("checkFailedMsg", teamMall.getCheckDesc());// 审核说明
						/*
						 * if(teamMall.getIsChecked()==null||teamMall.getIsChecked
						 * ()==false){ goodsMap.put("statusId",1002);//审核失败 }
						 * goodsMap.put("statusId",statusId);//审核中
						 */list.add(goodsMap);
					}
				}
				if (activityId != null && (activityId == 0 || activityId == 1)
						|| isList) {
					// 全国特产
					for (ReGoodsOfLocalSpecialtyMall local : localMallList) {

						Integer sals = local.getSales() == null ? 0 : local
								.getSales();

						Map<String, Object> goodsMap = getGoodsDetailed(local,
								1, statusId, sals, basePath, 0d);
						list.add(goodsMap);
					}
				}

				if (activityId != null && (activityId == 0 || activityId == 6)
						|| isList) {
					// 秒杀
					for (ReGoodsOfSeckillMall seckiMall : seckillMallList) {
						Map<String, Object> goodsMap = getGoodsDetailed(
								seckiMall, 6, statusId, 0, basePath, 0d);
						Set<CashshopTimes> times = seckiMall.getTimes();
						String time = "";
						for (CashshopTimes cashshopTimes : times) {
							time += cashshopTimes.getStartTime() + "/"
									+ cashshopTimes.getEndTime();
						}
						goodsMap.put("rushTimeSlot",
								time.substring(0, time.length()));
						goodsMap.put("rushNumber", seckiMall.getReleaseNum()); // 销量还是库存量
																				// 暂定销量
						list.add(goodsMap);
					}
				}

				if (activityId != null && (activityId == 0 || activityId == 8)
						|| isList) {
					// 换货会
					for (ReGoodsOfChangeMall changeMall : changeList) {
						Map<String, Object> goodsMap = getGoodsDetailed(
								changeMall, 8, statusId, 0, basePath, 0d);
						goodsMap.put("soldNumber", changeMall.getSalesVolume());
						list.add(goodsMap);
					}
				}
			}

			Map<String, Object> detailList = new HashMap<>();

			sList.addAll(list);
			List<Map<String, Object>> pageList = new ArrayList<Map<String, Object>>();

			int size = (pageIndex - 1) * pageSizes;

			for (int i = 0; i < pageSizes; i++) {
				int k = i + size;
				if (k < sList.size()) {
					pageList.add(sList.get(i + size));
				} else {
					break;
				}
			}

			Integer totalPage = sList.size() % pageSizes == 0 ? sList.size()
					/ pageSizes : sList.size() / pageSizes + 1;

			detailList.put("pageSize", totalPage);
			detailList.put("pageIndex", pageIndex);
			detailList.put("pageItemCount", pageSizes);
			detailList.put("detailList", pageList);
			detailList.put("sellingCount", sellingCount);
			detailList.put("checkingCount", checkingCount);
			detailList.put("waitingCount", waitingCount);
			map.put("data", detailList);
		} catch (Exception e) {
			map.put("status", -1);
			map.put("message", "请求失败");
			e.printStackTrace();
			if (!TransactionAspectSupport.currentTransactionStatus()
					.isRollbackOnly()) {
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
			}
		}
		return map;
	}

	public Map<String, Object> getGoodsDetailed(ReBaseGoods baseGood,
			Integer typeId, Integer statusId, Integer sales, String basePath,
			Double discountPrice) {

		Map<String, Object> map = new HashMap<>();

		map.put("typeId", typeId);
		map.put("statusId", statusId);

		if (baseGood.getShelvesTime() != null
				&& baseGood.getShelvesTime().before(new Date())
				&& (statusId == 1000 || statusId == 1001)) {
			String goodsOrder = baseGood.getGoodsOrder();
			String str = goodsOrder.substring(0, 3);
			String Id = goodsOrder.substring(3, goodsOrder.length());
			delGoods(typeId.toString(), statusId.toString(),
					baseGood.getBaseGoodsId(), str, Id, goodsOrder);
		}

		// 商品状态 出售中 1000 审核中1001 审核失败 1002 待处理 1003
		map.put("checkFailedMsg", baseGood.getCheckDesc() == null ? ""
				: baseGood.getCheckDesc());

		if (baseGood.getIsChecked() == null) {
			map.put("statusId", 1002);
		}
		try {

			discountPrice = discountPrice == null ? 0 : discountPrice;
			ReGoodsOfBase goodsOfBase = reGoodsOfBaseDao.findById(baseGood
					.getBaseGoodsId());

			String categoryName = "";
			if (goodsOfBase.getFirstType() != null) {
				JSONObject firstType = goodsOfBase.getFirstType();
				String parentName = firstType.getString("parentTypeName");
				String childTypeName = firstType.getString("childTypeName");
				if (StringUtils.isNotBlank(parentName)
						|| StringUtils.isNotBlank(childTypeName)) {
					categoryName = parentName + "-" + childTypeName;
				}
			}

			map.put("cateName", categoryName);
			map.put("displayPrice", baseGood.getDisplayPrice());
			map.put("createTime", baseGood.getCreateTime());
			map.put("baseGoodsId", baseGood.getBaseGoodsId());
			map.put("goodsOrder", baseGood.getGoodsOrder());
			map.put("sellerMallId",
					baseGood.getReGoodsOfSellerMall() == null ? "" : baseGood
							.getReGoodsOfSellerMall().getId());
			map.put("goodsIcon", basePath
					+ baseGood.getSnapshotGoods().getCoverPicForUse());
			map.put("goodsName", baseGood.getSnapshotGoods().getName());
			map.put("transportationType", baseGood.getTransportationType());
			map.put("transportationPrice", baseGood.getTransportationPrice());
			map.put("rightsProtect", baseGood.getRightsProtectToJson());
			map.put("coverPics",
					baseGood.getSnapshotGoods().getPrefixCoverPic(basePath));
			map.put("ptCommissionPercent", "0.03"); // 佣金率
			String price = "";
			Integer stock = 0;
			String score = "";
			List<Double> priceList = new ArrayList<>();
			List<Integer> scoreList = new ArrayList<>();

			if (baseGood.getIsNoStandard()) {
				price = String.valueOf(CalcUtil.sub(
						baseGood.getNoStandardPrice(), discountPrice));
				stock = baseGood.getNoStandardRepertory();
				score = baseGood.getNoStandardScore().toString();
			} else {
				List<Map<String, Object>> standardDetailsList = null;
				if (baseGood.getReGoodsOfSellerMall() != null
						&& !baseGood.getGoodsOrder().startsWith(
								ReBaseGoods.changeMall)) {
					standardDetailsList = baseGood.getReGoodsOfSellerMall()
							.getStandardDetailsList();
				} else {
					standardDetailsList = baseGood.getStandardDetailsList();
				}
				Double A = 0d;
				Integer B = 0;
				Integer C = 0;
				for (Map<String, Object> map2 : standardDetailsList) {

					A = map2.get("price") == null ? 0d : Double
							.parseDouble(map2.get("price").toString());
					B = map2.get("score") == null ? 0 : Integer.parseInt(map2
							.get("score").toString());
					C = map2.get("repertory") == null ? 0 : Integer
							.parseInt(map2.get("repertory").toString());
					stock += C;
					if (A > 0d) {
						if (typeId == 7) {
							priceList.add(CalcUtil.sub(A, discountPrice)); // 拼团价
						}
						priceList.add(A); // 累加金额 然后排序得到最小最大 10-90;
					}
					if (B > 0) {
						scoreList.add(B);
					}
				}
				// 去重
				Set<Double> set = new HashSet<>();
				List<Double> newList = new ArrayList();
				for (Double p : priceList) {
					if (set.add(p)) {
						newList.add(p);
					}
				}
				priceList = newList;

				Collections.sort(priceList);
				Collections.sort(scoreList);

				if (priceList.size() >= 2) {
					price = priceList.get(0) + "-"
							+ priceList.get(priceList.size() - 1);
				} else if (priceList.size() > 0) {
					price = priceList.get(0).toString();
				} else {
					price = "0";
				}
				if (scoreList.size() >= 2) {
					score = scoreList.get(0) + "-"
							+ scoreList.get(scoreList.size() - 1);
				} else if (scoreList.size() > 0) {
					score = scoreList.get(0).toString();
				} else {
					score = "0";
				}
			}

			if (baseGood.getGoodsOrder().startsWith("scm")) {
				if (score.equals("0")) {
					score = baseGood.getScore().toString();
				}
				// score=baseGood.getNoStandardScore().();
			}

			map.put("cashPrice", price); // 积分售价
			map.put("rushPrice", price); // 秒杀商品售价
			map.put("price", price);
			map.put("stock", stock == null ? 0 : stock.toString()); // 库存
			map.put("score", score);
			Integer soldNumber = 0;
			if (baseGood.getReGoodsOfSellerMall() != null) {
				soldNumber = baseGood.getReGoodsOfSellerMall().getSalesVolume();
			} else {
				soldNumber = baseGood.getSalesVolume();
			}

			map.put("soldNumber", soldNumber + sales);
			if (typeId == 1) {
				map.put("areaName", "全国特产");
			} else if (typeId == 2 || typeId == 8) {
				map.put("areaName", "周边店铺");
				map.put("hasSpecStr", !baseGood.getIsNoStandard()); // 是否有规格
				List<Map<String, Object>> standarList = new ArrayList<>();
				if (!baseGood.getIsNoStandard()) {
					standarList = baseGood.getSpecifications();
				} else {
					standarList = baseGood.getIsNotSpecifications();
				}
				map.put("specifications", standarList); // 规格
			} else if (typeId == 3) {
				map.put("areaName", "积分商品");
			} else if (typeId == 4) {
				map.put("areaName", "普通优惠券");
			} else if (typeId == 5) {
				map.put("areaName", "活动优惠券");
			} else if (typeId == 6) {
				map.put("areaName", "秒杀活动");
			} else if (typeId == 7) {
				map.put("areaName", "拼团活动");
			}
			if (typeId == 8) {
				map.put("areaName", "换货商品");
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (!TransactionAspectSupport.currentTransactionStatus()
					.isRollbackOnly()) {
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
			}
		}
		return map;
	}

	private Integer getGoodsCountByStatus(Integer statusId, Integer sellerId) {
		QueryModel queryModel = new QueryModel();
		QueryModel model = new QueryModel(); // 计算优惠券
		QueryModel model2 = new QueryModel(); // 计算拼团
		Integer count = 0;

		queryModel.combEquals("isValid", 1);
		queryModel.combCondition(" addedTime<now()  "
				+ " and snapshotGoods.seller.id=" + sellerId + "");

		model.combEquals("isValid", 1);
		model.combCondition("validtime>now()");
		model.combPreEquals("seller.id", sellerId, "sellerId");

		model2.combEquals("isValid", 1);
		model2.combPreEquals("seller.id", sellerId, "sellerId");
		if (statusId.intValue() == 1000) {
			model2.combPreEquals("isChecked", true);
			model.combPreEquals("isChecked", true);
			queryModel.combPreEquals("isChecked", true);
			queryModel.combCondition("  shelvesTime>now() ");
		} else if (statusId.intValue() == 1001) {
			queryModel
					.combCondition(" (isChecked=false or isChecked is null )");
			model.combCondition(" (isChecked=false or isChecked is null )");
			model2.combCondition(" (isChecked=false or isChecked is null )");
		}

		int _A = dateBaseDAO.findCount(ReGoodsOfSellerMall.class, queryModel);
		int _B = dateBaseDAO.findCount(ReGoodsOfSeckillMall.class, queryModel);
		int _C = dateBaseDAO.findCount(ReGoodsOfScoreMall.class, queryModel);
		int _D = dateBaseDAO.findCount(ReGoodsOfLocalSpecialtyMall.class,
				queryModel);
		int _E = dateBaseDAO.findCount(ReGoodsofextendmall.class, model);
		int _F = dateBaseDAO.findCount(ReGoodsOfTeamMall.class, model2);
		int _G = dateBaseDAO.findCount(ReGoodsOfChangeMall.class, queryModel);
		int _H = dateBaseDAO.findCount(ReGoodsOfLockMall.class, queryModel);
		count = _A + _B + _C + _D + _E + _F + _G + _H;

		return count;

	}

	/**
	 * @author ZhangLu 删除推广
	 * */
	@Override
	public Map<String, Object> delMallOfGoods(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			String typeId = parameter.getData().getString("typeId");// 全国特产1|周边店铺2，积分商品3，普通优惠券4|活动优惠券5，秒杀6，拼团7
			String goodsOrder = parameter.getData().getString("goodsOrder");
			String str = goodsOrder.substring(0, 3);
			String Id = goodsOrder.substring(3, goodsOrder.length());
			String statusId = parameter.getData().getString("statusId");// 商品状态（出售中
																		// 1000，待审核
																		// 1001，待处理
																		// 1003）
			String baseGoodsId = parameter.getData().getString("baseGoodsId");
			statusMap = delGoods(typeId, statusId,
					Integer.parseInt(baseGoodsId), str, Id, goodsOrder);

		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			e.printStackTrace();
			statusMap.put("status", -1);
			statusMap.put("message", "删除失败");
		}
		return statusMap;
	}

	public Map<String, Object> delGoods(String typeId, String statusId,
			Integer baseGoodsId, String str, String Id, String goodsOrder) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		ReGoodsOfBase reGoodsOfBase = reGoodsOfBaseDao.findById(baseGoodsId);
		try {
			if (StringUtils.isNotBlank(statusId) && statusId.equals("1003")) {
				if (reGoodsOfBase != null) {
					reGoodsOfBase.setIsValid(false);
					reGoodsOfBaseDao.update(reGoodsOfBase);
				}
			} else if (statusId.equals("1000") || StringUtils.isBlank(statusId)) {
				if (typeId.equals("2") && StringUtils.isNotBlank(str)
						&& str.equals(ReBaseGoods.SellerMall)) {
					ReGoodsOfSellerMall sellerMall = reGoodsOfSellerMallDao
							.findById(Integer.parseInt(Id));
					if (sellerMall != null) {
						sellerMall.setIsValid(false);
						reGoodsOfSellerMallDao.update(sellerMall);
						try {
							String changeLaunchMall = reGoodsOfBase
									.changeLaunchMall(
											reGoodsOfBase.getLaunchMall() == null ? "000000000"
													: reGoodsOfBase
															.getLaunchMall(),
											1, false);
							reGoodsOfBase.setLaunchMall(changeLaunchMall);
							reGoodsOfBaseDao.update(reGoodsOfBase);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				if (typeId.equals("1") && StringUtils.isNotBlank(str)
						&& str.equals(ReBaseGoods.LocalSpecialtyMall)) {
					ReGoodsOfLocalSpecialtyMall goodsOfLocalSpecialtyMall = reGoodsOfLocalSpecialtyMallDao
							.findById(Integer.parseInt(Id));
					if (goodsOfLocalSpecialtyMall != null) {
						goodsOfLocalSpecialtyMall.setIsValid(false);
						reGoodsOfLocalSpecialtyMallDao
								.update(goodsOfLocalSpecialtyMall);
						try {
							String changeLaunchMall = reGoodsOfBase
									.changeLaunchMall(
											reGoodsOfBase.getLaunchMall() == null ? "000000000"
													: reGoodsOfBase
															.getLaunchMall(),
											4, false);
							reGoodsOfBase.setLaunchMall(changeLaunchMall);
							reGoodsOfBaseDao.update(reGoodsOfBase);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}

				} else if (StringUtils.isNotBlank(str)
						&& str.equals(ReBaseGoods.ScoreMall)) {
					ReGoodsOfScoreMall goodsOfScoreMall = reGoodsOfScoreMallDao
							.findById(Integer.parseInt(Id));
					if (goodsOfScoreMall != null) {
						/*
						 * ReGoodsOfSellerMall goodsOfSellerMall =
						 * reGoodsOfSellerMallDao
						 * .findById(goodsOfScoreMall.getSellerMallId()); if
						 * (goodsOfSellerMall!=null) { int sellerNewRepertory =
						 * (int)
						 * CalcUtil.add(goodsOfSellerMall.getDefaultRepertory(),
						 * goodsOfScoreMall.getDefaultRepertory());
						 * goodsOfSellerMall
						 * .setDefaultRepertory(sellerNewRepertory);
						 * reGoodsOfSellerMallDao.update(goodsOfSellerMall); }
						 */
						goodsOfScoreMall.setIsValid(false);
						reGoodsOfScoreMallDao.update(goodsOfScoreMall);
						try {
							String changeLaunchMall = reGoodsOfBase
									.changeLaunchMall(reGoodsOfBase.getLaunchMall() == null ? "000000000"
													: reGoodsOfBase.getLaunchMall(),2, false);
							reGoodsOfBase.setLaunchMall(changeLaunchMall);
							reGoodsOfBaseDao.update(reGoodsOfBase);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}  else if (StringUtils.isNotBlank(str)&& str.equals(ReBaseGoods.lockMall)) {
					ReGoodsOfLockMall goodsOfLockMall = reGoodsOfLockMallDao.findById(Integer.parseInt(Id));
					if (goodsOfLockMall != null && goodsOfLockMall.getOpenYards() != null) {
						goodsOfLockMall.setIsValid(false);
						reGoodsOfLockMallDao.update(goodsOfLockMall);
						try {
							String changeLaunchMall = reGoodsOfBase.changeLaunchMall(reGoodsOfBase.getLaunchMall() == null ? "000000000"
													: reGoodsOfBase.getLaunchMall(),8, false);
							reGoodsOfBase.setLaunchMall(changeLaunchMall);
							reGoodsOfBaseDao.update(reGoodsOfBase);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}else{
						statusMap.put("status", -1);
						statusMap.put("message", "商品还未开奖不能删除!");
						return statusMap;
					}

				} else if (StringUtils.isNotBlank(str)
						&& str.equals(ReBaseGoods.SeckillMall)) {
					ReGoodsOfSeckillMall goodsOfSeckillMall = reGoodsOfSeckillMallDao
							.findById(Integer.parseInt(Id));
					if (goodsOfSeckillMall != null) {
						/*
						 * ReGoodsOfSellerMall goodsOfSellerMall =
						 * reGoodsOfSellerMallDao
						 * .findById(goodsOfSeckillMall.getSellerMallId()); if
						 * (goodsOfSellerMall!=null) { int sellerNewRepertory =
						 * (int)
						 * CalcUtil.add(goodsOfSellerMall.getDefaultRepertory(),
						 * goodsOfSeckillMall.getDefaultRepertory());
						 * goodsOfSellerMall
						 * .setDefaultRepertory(sellerNewRepertory);
						 * reGoodsOfSellerMallDao.update(goodsOfSellerMall); }
						 */
						goodsOfSeckillMall.setIsValid(false);
						reGoodsOfSeckillMallDao.update(goodsOfSeckillMall);
						try {
							String changeLaunchMall = reGoodsOfBase
									.changeLaunchMall(
											reGoodsOfBase.getLaunchMall() == null ? "000000000"
													: reGoodsOfBase
															.getLaunchMall(),
											3, false);
							reGoodsOfBase.setLaunchMall(changeLaunchMall);
							reGoodsOfBaseDao.update(reGoodsOfBase);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else if (str.equals(ReBaseGoods.teamMall)
						&& StringUtils.isNotBlank(typeId) && typeId.equals("7")) {
					/*
					 * QueryModel queryModel = new QueryModel();
					 * queryModel.combPreEquals("isValid", true);
					 * queryModel.combPreEquals("goodsOrder", goodsOrder);
					 * ReGoodsOfTeamMall goodsOfTeamMall = (ReGoodsOfTeamMall)
					 * dateBaseDAO.findOne(ReGoodsOfTeamMall.class, queryModel);
					 */
					ReGoodsOfTeamMall goodsOfTeamMall = reGoodsOfTeamMallDao
							.findById(Integer.parseInt(Id));
					if (goodsOfTeamMall != null) {
						goodsOfTeamMall.setIsValid(false);
						reGoodsOfTeamMallDao.update(goodsOfTeamMall);
					}
				} else if (str.equals(ReBaseGoods.changeMall)
						&& StringUtils.isNotBlank(typeId) && typeId.equals("8")) {// 换货会
					ReGoodsOfChangeMall goodsOfChangeMall = reGoodsOfChangeMallDao
							.findById(Integer.parseInt(Id));
					goodsOfChangeMall.setIsValid(false);
					reGoodsOfChangeMallDao.update(goodsOfChangeMall);

					try {
						String changeLaunchMall = reGoodsOfBase
								.changeLaunchMall(
										reGoodsOfBase.getLaunchMall() == null ? "000000000"
												: reGoodsOfBase.getLaunchMall(),
										7, false);
						reGoodsOfBase.setLaunchMall(changeLaunchMall);
						reGoodsOfBaseDao.update(reGoodsOfBase);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (StringUtils.isNotBlank(typeId) && typeId.equals("5")) {// 活动优惠券
					statusMap = UrlUtil.getTaoKeToMap(sellerUrl
							+ "/Json/Index/delCoup?&goodsOrder=" + goodsOrder
							+ "&typeId=" + typeId);
				} else if (StringUtils.isNotBlank(typeId) && typeId.equals("4")) {// 普通优惠券
					statusMap = UrlUtil.getTaoKeToMap(sellerUrl
							+ "/Json/Index/delCoup?&goodsOrder=" + goodsOrder
							+ "&typeId=" + typeId);
				}
			} else if (statusId.equals("1001")) {
				/*
				 * if (reGoodsOfBase!=null) { reGoodsOfBase.setIsValid(false);
				 * reGoodsOfBaseDao.update(reGoodsOfBase); }
				 */
				if (typeId.equals("2") && StringUtils.isNotBlank(str)
						&& str.equals(ReBaseGoods.SellerMall)) {
					ReGoodsOfSellerMall sellerMall = reGoodsOfSellerMallDao
							.findById(Integer.parseInt(Id));
					if (sellerMall != null) {
						sellerMall.setIsValid(false);
						reGoodsOfSellerMallDao.update(sellerMall);
						try {
							String changeLaunchMall = reGoodsOfBase
									.changeLaunchMall(
											reGoodsOfBase.getLaunchMall() == null ? "000000000"
													: reGoodsOfBase
															.getLaunchMall(),
											1, false);
							reGoodsOfBase.setLaunchMall(changeLaunchMall);
							
							reGoodsOfBaseDao.update(reGoodsOfBase);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				if (typeId.equals("1") && StringUtils.isNotBlank(str)
						&& str.equals(ReBaseGoods.LocalSpecialtyMall)) {
					ReGoodsOfLocalSpecialtyMall goodsOfLocalSpecialtyMall = reGoodsOfLocalSpecialtyMallDao
							.findById(Integer.parseInt(Id));
					if (goodsOfLocalSpecialtyMall != null) {
						goodsOfLocalSpecialtyMall.setIsValid(false);
						reGoodsOfLocalSpecialtyMallDao
								.update(goodsOfLocalSpecialtyMall);
						try {
							String changeLaunchMall = reGoodsOfBase
									.changeLaunchMall(
											reGoodsOfBase.getLaunchMall() == null ? "000000000"
													: reGoodsOfBase
															.getLaunchMall(),
											4, false);
							reGoodsOfBase.setLaunchMall(changeLaunchMall);
							reGoodsOfBaseDao.update(reGoodsOfBase);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}

				} else if (StringUtils.isNotBlank(str)
						&& str.equals(ReBaseGoods.ScoreMall)) {
					ReGoodsOfScoreMall goodsOfScoreMall = reGoodsOfScoreMallDao
							.findById(Integer.parseInt(Id));
					if (goodsOfScoreMall != null) {
						goodsOfScoreMall.setIsValid(false);
						reGoodsOfScoreMallDao.update(goodsOfScoreMall);
						try {
							String changeLaunchMall = reGoodsOfBase
									.changeLaunchMall(
											reGoodsOfBase.getLaunchMall() == null ? "000000000"
													: reGoodsOfBase
															.getLaunchMall(),
											2, false);
							reGoodsOfBase.setLaunchMall(changeLaunchMall);
							reGoodsOfBaseDao.update(reGoodsOfBase);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				} else if (StringUtils.isNotBlank(str)
						&& str.equals(ReBaseGoods.lockMall)) {
					ReGoodsOfLockMall goodsOfLockMall = reGoodsOfLockMallDao
							.findById(Integer.parseInt(Id));
					if (goodsOfLockMall != null) {
						
						goodsOfLockMall.setIsValid(false);
						reGoodsOfLockMallDao.update(goodsOfLockMall);
						try {
							String changeLaunchMall = reGoodsOfBase
									.changeLaunchMall(
											reGoodsOfBase.getLaunchMall() == null ? "000000000"
													: reGoodsOfBase
															.getLaunchMall(),
											8, false);
							reGoodsOfBase.setLaunchMall(changeLaunchMall);
							reGoodsOfBaseDao.update(reGoodsOfBase);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				} else if (StringUtils.isNotBlank(str)
						&& str.equals(ReBaseGoods.SeckillMall)) {
					ReGoodsOfSeckillMall goodsOfSeckillMall = reGoodsOfSeckillMallDao
							.findById(Integer.parseInt(Id));
					if (goodsOfSeckillMall != null) {
						goodsOfSeckillMall.setIsValid(false);
						reGoodsOfSeckillMallDao.update(goodsOfSeckillMall);
						try {
							String changeLaunchMall = reGoodsOfBase
									.changeLaunchMall(
											reGoodsOfBase.getLaunchMall() == null ? "000000000"
													: reGoodsOfBase
															.getLaunchMall(),
											3, false);
							reGoodsOfBase.setLaunchMall(changeLaunchMall);
							reGoodsOfBaseDao.update(reGoodsOfBase);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else if (str.equals(ReBaseGoods.teamMall)
						&& StringUtils.isNotBlank(typeId) && typeId.equals("7")) {
					/*
					 * QueryModel queryModel = new QueryModel();
					 * queryModel.combPreEquals("isValid", true);
					 * queryModel.combPreEquals("goodsMall", goodsOrder);
					 * ReGoodsOfTeamMall goodsOfTeamMall = (ReGoodsOfTeamMall)
					 * dateBaseDAO.findOne(ReGoodsOfTeamMall.class, queryModel);
					 */
					ReGoodsOfTeamMall goodsOfTeamMall = reGoodsOfTeamMallDao
							.findById(Integer.parseInt(Id));
					if (goodsOfTeamMall != null) {
						goodsOfTeamMall.setIsValid(false);
						reGoodsOfTeamMallDao.update(goodsOfTeamMall);
						String changeLaunchMall = reGoodsOfBase
								.changeLaunchMall(
										reGoodsOfBase.getLaunchMall() == null ? "000000000"
												: reGoodsOfBase.getLaunchMall(),
										8, false);
						reGoodsOfBase.setLaunchMall(changeLaunchMall);
						reGoodsOfBaseDao.update(reGoodsOfBase);
					}
				} else if (str.equals(ReBaseGoods.changeMall)
						&& StringUtils.isNotBlank(typeId) && typeId.equals("8")) {
					ReGoodsOfChangeMall goodsOfChangeMall = reGoodsOfChangeMallDao
							.findById(Integer.parseInt(Id));
					if (goodsOfChangeMall != null) {
						goodsOfChangeMall.setIsValid(false);
						String changeLaunchMall = reGoodsOfBase
								.changeLaunchMall(
										reGoodsOfBase.getLaunchMall() == null ? "000000000"
												: reGoodsOfBase.getLaunchMall(),
										7, false);
						reGoodsOfBase.setLaunchMall(changeLaunchMall);
						reGoodsOfBaseDao.update(reGoodsOfBase);
					}

				}

				if (StringUtils.isNotBlank(typeId) && typeId.equals("5")) {// 活动优惠券
					statusMap = UrlUtil.getTaoKeToMap(sellerUrl
							+ "/Json/Index/delCoup?&goodsOrder=" + goodsOrder
							+ "&typeId=" + typeId);
				} else if (StringUtils.isNotBlank(typeId) && typeId.equals("4")) {// 普通优惠券
					statusMap = UrlUtil.getTaoKeToMap(sellerUrl
							+ "/Json/Index/delCoup?&goodsOrder=" + goodsOrder
							+ "&typeId=" + typeId);
				}
			}
			if (statusMap != null) {
				statusMap.put("status", 1);
				statusMap.put("message", "删除成功");
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			e.printStackTrace();
			statusMap.put("status", -1);
			statusMap.put("message", "删除失败");
		}
		return statusMap;
	}

	/**
	 * @author ZhangLu 补充库存
	 * */
	@Override
	public Map<String, Object> reStock(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		try {
			Integer reStockNum = parameter.getData().getInteger("reStockNum");// 添加的库存数
			String goodsOrder = parameter.getData().getString("goodsOrder");//
			String baseGoodsId = parameter.getData().getString("baseGoodsId");
			Boolean hasSpecStr = parameter.getData().getBoolean("hasSpecStr");
			String specifications = parameter.getData().getString(
					"specifications");
			String str = goodsOrder.substring(0, 3);
			String Id = goodsOrder.substring(3, goodsOrder.length());

			ReGoodsOfSellerMall sellerMall = reGoodsOfSellerMallDao
					.findById(Integer.parseInt(Id));

			if (hasSpecStr) {
				JSONArray jsonArray = JSONArray.fromObject(specifications);
				JSONObject json = JSONObject.parseObject(sellerMall
						.getStandardDetails());
				JSONObject jsonData = json.getJSONObject("data");
				com.alibaba.fastjson.JSONArray parentStandardArray = jsonData
						.getJSONArray("parentStandard");
				List<Map<String, Object>> parentlist = new ArrayList<>();

				Map<String, Object> parentMap = new HashMap<>();

				JSONObject parentObj = parentStandardArray.getJSONObject(0);

				parentMap
						.put("standardId", parentObj.getIntValue("standardId"));
				parentMap.put("standardName",
						parentObj.getString("standardName"));

				parentlist.add(parentMap);

				com.alibaba.fastjson.JSONArray standardArray = jsonData
						.getJSONArray("standardDetails");
				List<Map<String, Object>> list = new ArrayList<>();
				for (int i = 0; i < standardArray.size(); i++) { // 商品规格
					Map<String, Object> map = new HashMap<String, Object>();
					JSONObject obj = standardArray.getJSONObject(i);
					int id = obj.getInteger("id1");

					for (int j = 0; j < jsonArray.size(); j++) { // 传递过来的规格
						net.sf.json.JSONObject jsonObj = jsonArray
								.getJSONObject(j);
						int id1 = jsonObj.getInt("specId");

						if (id1 == id) {
							map.put("repertory", jsonObj.getInt("stock"));
							break;
						}
					}

					map.put("id1", id);
					map.put("name1", obj.getString("name1"));
					map.put("price", obj.getDouble("price"));
					map.put("redPaper", obj.getDouble("redPaper"));
					map.put("score", obj.getIntValue("score"));

					list.add(map);
				}
				sellerMall.setStandardDetails(1, "操作成功", parentlist, list);
			} else {
				sellerMall.setNoStandardRepertory(reStockNum);
			}

			reGoodsOfSellerMallDao.update(sellerMall);

		} catch (Exception e) {
			if (!TransactionAspectSupport.currentTransactionStatus()
					.isRollbackOnly()) {
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
			}

			e.printStackTrace();
			return JsonResponseUtil.getJson(-1, "添加失败");
		}
		return JsonResponseUtil.getJson(1, "添加成功");
	}

	/**
	 * 发布换货商品
	 * 
	 * @param parameter
	 * @param map
	 */
	private void applyGoods700(Parameter parameter, Map<String, Object> map) {
		try {
			Integer adminUserId = Integer.parseInt(parameter.getAdminuserId());
			JSONObject jsonData = parameter.getData();
			String changeDesc = jsonData.getString("changeDesc"); // 换货描述
			String want = jsonData.getString("want"); // 想换什么标签 可以有多个 用-号分隔
			Integer stock = jsonData.getInteger("stock") == null ? 0 : jsonData
					.getInteger("stock");
			com.alibaba.fastjson.JSONArray standardArray = jsonData
					.getJSONArray("specifications"); // 规格
			String goodsOrder = jsonData.getString("goodsOrder");
			Integer goodsId = Integer.parseInt(goodsOrder.substring(3,
					goodsOrder.length()));

			ReGoodsOfChangeMall changeMall = null;
			ReGoodsOfSellerMall reGoodsOfSellerMall = null;
			if (goodsOrder.startsWith(ReBaseGoods.changeMall)) { // 修改
				changeMall = reGoodsOfChangeMallDao.findById(goodsId);
				reGoodsOfSellerMall = changeMall.getReGoodsOfSellerMall();
			} else { // 新增
				changeMall = new ReGoodsOfChangeMall();
				reGoodsOfSellerMall = reGoodsOfSellerMallDao.findById(goodsId);
			}
			ReGoodsOfChangeMall chaMall = reGoodsOfChangeMallService
					.buildChangeMall(reGoodsOfSellerMall, changeMall,
							standardArray, goodsOrder, want, stock,
							adminUserId, changeDesc);
			reGoodsOfChangeMallDao.saveOrUpdate(chaMall);
			changeMall.setGoodsOrder(ReBaseGoods.changeMall + chaMall.getId());
			reGoodsOfChangeMallDao.merge(chaMall);

			ReGoodsOfBase baseGoods = reGoodsOfBaseDao
					.findById(reGoodsOfSellerMall.getBaseGoodsId());

			String changeLaunchMall = baseGoods.changeLaunchMall(
					baseGoods.getLaunchMall(), 7, true);
			baseGoods.setLaunchMall(changeLaunchMall);
			reGoodsOfBaseDao.update(baseGoods);

		} catch (Exception e) {
			map.put("status", -1);
			map.put("message", "操作失败");
			if (!TransactionAspectSupport.currentTransactionStatus()
					.isRollbackOnly()) {
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
			}
			e.printStackTrace();
		}
	}

	/**
	 * 发布拼团
	 * 
	 * @param parameter
	 * @param map
	 */
	private void applyGoods600(Parameter parameter, Map<String, Object> map) {

		JSONObject jsonData = parameter.getData();
		Integer teamNumber = jsonData.getInteger("teamNumber");// 拼团人数
		// Integer transportationType=jsonData.getInteger("transportationType");
		// //默认包邮
		// Integer standardId=jsonData.getInteger("standardId"); //规格id
		Integer repertory = jsonData.getInteger("stock"); // 库存
		String goodsName = jsonData.getString("goodsName");
		String startTime = jsonData.getString("startTime");
		String endTime = jsonData.getString("endTime");
		String goodsOrder = jsonData.getString("goodsOrder");
		// String goodsMall=jsonData.getString("goodsId");
		Boolean isRestrict = jsonData.getBoolean("isRestrict"); // 是否限购
		Integer restrictNum = jsonData.getInteger("restrictNum") == null ? 0
				: jsonData.getInteger("restrictNum"); // 限购数量
		Integer transportationType = jsonData.getInteger("transportationType");
		Double transportationPrice = jsonData.getDouble("transportationPrice");
		// Double teamPrice=jsonData.getDouble("teamPrice");
		Boolean isChecked = jsonData.getBoolean("isChecked");
		
		try {
			ReGoodsOfSellerMall reGoodsOfSellerMall = null;
			ReGoodsOfTeamMall goodsOfTeamMall = null;
			Integer goodsId = Integer.parseInt(goodsOrder.substring(3,
					goodsOrder.length()));
			if (goodsOrder.startsWith("tim")) {
				goodsOfTeamMall = reGoodsOfTeamMallDao.findById(goodsId);
				reGoodsOfSellerMall = goodsOfTeamMall.getReGoodsOfSellerMall();

			} else {
				goodsOfTeamMall = new ReGoodsOfTeamMall();
				reGoodsOfSellerMall = reGoodsOfSellerMallDao.findById(goodsId);
			}

			ReGoodsOfBase baseGoods = reGoodsOfBaseDao
					.findById(reGoodsOfSellerMall.getBaseGoodsId());
			ReGoodsSnapshot reGoodsSnapshot = reGoodsOfSellerMall
					.getSnapshotGoods();
			// ReGoodsStandard reGoodsStandard =
			// reGoodsStandardDao.findById(standardId);
			goodsName = reGoodsSnapshot.getName();
			Seller seller = baseGoods.getSeller();
			transportationType = 1; // 默认包邮 应该是获取周边的运输方式
			transportationPrice = 0d;
			isChecked = false;


			ReGoodsOfTeamMall teamMall = reGoodsOfTeamMallService
					.buildReGoodsOfTeamMall(goodsOfTeamMall, goodsName,
							baseGoods, reGoodsOfSellerMall, teamNumber,
							transportationType, transportationPrice,
							repertory, startTime, endTime, seller,
							isChecked, reGoodsSnapshot, isRestrict,
							restrictNum, 0.0);
			reGoodsOfTeamMallDao.saveOrUpdate(teamMall);
			teamMall.setGoodsOrder(ReBaseGoods.teamMall + teamMall.getId());
			reGoodsOfSellerMallDao.update(reGoodsOfSellerMall);

			if (baseGoods.getLaunchMall().length() == 7) {
				baseGoods.setLaunchMall(baseGoods.getLaunchMall() + "0");
			}

			String changeLaunchMall = baseGoods.changeLaunchMall(
					baseGoods.getLaunchMall() == null ? "000000000"
							: baseGoods.getLaunchMall(), 8, true);
			baseGoods.setLaunchMall(changeLaunchMall);
			reGoodsOfBaseDao.update(baseGoods);
	

		} catch (Exception e) {
			map.put("status", -1);
			map.put("message", "操作失败");
			if (!TransactionAspectSupport.currentTransactionStatus()
					.isRollbackOnly()) {
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
			}
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 发布游戏
	 * 
	 * @param parameter
	 * @param map
	 */
	private void applyGoods800(Parameter parameter, Map<String, Object> map) {

		JSONObject jsonData = parameter.getData();
		Integer peopleNum = jsonData.getInteger("peopleNum");// 参与人数
		String startTime = jsonData.getString("startTime");
		String endTime = jsonData.getString("endTime"); //游戏结束时间
		String goodsOrder = jsonData.getString("goodsOrder");
		Integer transportationType = jsonData.getInteger("transportationType");
		Double transportationPrice = jsonData.getDouble("transportationPrice");
		Integer score = jsonData.getInteger("score"); //参与需要的积分
		String LockDesc = jsonData.getString("lockDesc");//商品中奖说明
		Integer gameType = jsonData.getInteger("type");//游戏类型
		String buytimeId = jsonData.getString("buytimeId");//购买时间段
		String way = jsonData.getString("way"); //1 时间范围结束, 2 参与人数结束 
		
		try {
			ReGoodsOfLockMall goodsOfLockMall = null;
			ReGoodsOfSellerMall goodsOfSellerMall = null;
			String str = goodsOrder.substring(0, 3);// 商品id前缀
			if (str.equals("ldm")) {
				String goodsId = goodsOrder.substring(3, goodsOrder.length());
				goodsOfLockMall = reGoodsOfLockMallDao.findById(Integer.parseInt(goodsId));
				goodsOfSellerMall = goodsOfLockMall.getReGoodsOfSellerMall();
			} else {
				String sellerMallId = goodsOrder.substring(3,goodsOrder.length());// 周边店铺数据id
				goodsOfLockMall = new ReGoodsOfLockMall();
				goodsOfSellerMall = reGoodsOfSellerMallDao.findById(Integer.parseInt(sellerMallId));
			}

			if (goodsOfSellerMall != null) {
				goodsOfLockMall.setScore(score);
				goodsOfLockMall.setBaseGoodsId(goodsOfSellerMall.getBaseGoodsId());
				goodsOfLockMall.setIsValid(true);
				goodsOfLockMall.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
				goodsOfLockMall.setReGoodsOfSellerMall(goodsOfSellerMall);
			

				if (transportationType == null) {
					transportationType = goodsOfSellerMall.getTransportationType();
					transportationPrice = goodsOfSellerMall.getTransportationPrice();
				}
				goodsOfLockMall.setTransportationType(transportationType);
				goodsOfLockMall.setTransportationPrice(transportationPrice);
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date d = sf.parse(startTime);
				Date d2 = sf.parse(endTime);
				
				goodsOfLockMall.setAddedTime(new Timestamp(d.getTime()));
				goodsOfLockMall.setShelvesTime(new Timestamp(d2.getTime()));
				
				goodsOfLockMall.setSnapshotGoods(goodsOfSellerMall.getSnapshotGoods());
				goodsOfLockMall.setIsChecked(false);
				goodsOfLockMall.setIsNoStandard(goodsOfSellerMall.getIsNoStandard());
				
				CommodityType type = commodityTypeDao.findById(gameType);
				goodsOfLockMall.setGameType(type);
				goodsOfLockMall.setLockDesc(LockDesc);
				if(gameType==267){
					CashshopTimes times = cashshopTimesDAO.findById(Integer.parseInt(buytimeId));
					Set<CashshopTimes> cashtime=  new HashSet<CashshopTimes>();
					cashtime.add(times);
					goodsOfLockMall.setTimesId(cashtime);
				}else if(gameType==265){
					goodsOfLockMall.setOpenType(Integer.valueOf(way));
					goodsOfLockMall.setPeopleNum(peopleNum);
					goodsOfLockMall.setEndTime(new Timestamp(d2.getTime()));// 游戏结束时间
					
				}
				
				reGoodsOfLockMallDao.saveOrUpdate(goodsOfLockMall);
				goodsOfLockMall.setGoodsOrder(ReBaseGoods.lockMall+ goodsOfLockMall.getId());
				reGoodsOfLockMallDao.update(goodsOfLockMall);
				
				ReGoodsOfBase baseGoods = reGoodsOfBaseDao.findById(goodsOfSellerMall.getBaseGoodsId());
				try {
					String changeLaunchMall = baseGoods.changeLaunchMall(baseGoods.getLaunchMall() == null ? "000000000": baseGoods.getLaunchMall(), 8, true);
					baseGoods.setLaunchMall(changeLaunchMall);
					reGoodsOfBaseDao.update(baseGoods);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			map.put("status", 1);
			map.put("message", "操作成功");
		} catch (Exception e) {
			map.put("status", -1);
			map.put("message", "操作失败");
			if (!TransactionAspectSupport.currentTransactionStatus()
					.isRollbackOnly()) {
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
			}
			e.printStackTrace();
		}
	}

	
	
	/**
	 * 发布商品
	 */
	@Override
	public Map<String, Object> publishGoods(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {
			return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
		}
		Map<String, Object> dataMap = new HashMap<>();

		JSONObject data = parameter.getData();
		String goodsName = data.getString("goodsName");
		Integer categoryId = data.getInteger("categoryId");
		String change = data.getString("isNotChange");

		CommodityType commodityType = commodityTypeDao.findById(categoryId);
		if (commodityType == null) {
			dataMap.put("status", -1);
			dataMap.put("message", "请选择类别");
			return dataMap;
		}

		String isSendScore = data.getString("isSendScore")==null?"0":data.getString("isSendScore");// 是否赠送积分
		String sendScoreNum = data.getString("sendScoreNum")==null?"0":data.getString("sendScoreNum");// 赠送的积分数量
		Double orieignPrice = data.getDouble("orieignPrice");
		// 获取版本号 da
		String version = "";
		String v = parameter.getAppVersion();
		char[] charArray = v.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if (".".equals(String.valueOf(charArray[i]))) {
				continue;
			}
			version += charArray[i];
		}
		
		boolean isChange = false;
		//if(Integer.parseInt(version)>=1114){
			if("0".equals(change)){
				isChange = true;
			}
//				}else{
//					if (data.getBoolean("isChange") != null) {
//						isChange = data.getBoolean("isChange").booleanValue();
//					}
//				}
		com.alibaba.fastjson.JSONArray standardArray = data
				.getJSONArray("specifications");// 发布的商品规格
		com.alibaba.fastjson.JSONArray goodsDetailArray = data
				.getJSONArray("goodsDetail");// 商品详情
		com.alibaba.fastjson.JSONArray coverPicArray = data
				.getJSONArray("topPics");// 商品轮播图
		Integer sellerId = Integer.parseInt(parameter.getSellerId());
		Integer baseGoodsId = data.getInteger("baseGoodsId");
		Integer adminuserId = Integer.parseInt(parameter.getAdminuserId());
		Seller seller = sellerDao.findById(sellerId);
		String rightsProtect = data.getString("rightsProtect"); // 权益保障
		Integer transportationType = data.getInteger("transportationType");
		Double transportationPrice = data.getDouble("transportationPrice") == null ? 0d
				: data.getDouble("transportationPrice");
		boolean hasSpecStr = data.getBoolean("hasSpecStr");
		ReGoodsOfBase reGoodsOfBase = null;
		if ("1".equals(isSendScore)){
			reGoodsOfBase = reGoodsOfBaseService.publishGoods(adminuserId,
					baseGoodsId, goodsName, categoryId, goodsDetailArray,
					standardArray, coverPicArray, seller, transportationType,
					transportationPrice, orieignPrice, rightsProtect,
					hasSpecStr, isChange, Integer.parseInt(sendScoreNum),
					Integer.parseInt(isSendScore));
		} else {
			reGoodsOfBase = reGoodsOfBaseService.publishGoods(adminuserId,
					baseGoodsId, goodsName, categoryId, goodsDetailArray,
					standardArray, coverPicArray, seller, transportationType,
					transportationPrice, orieignPrice, rightsProtect,
					hasSpecStr, isChange, Integer.parseInt(sendScoreNum),
					Integer.parseInt(isSendScore));
		}
		if (reGoodsOfBase == null) {
			dataMap.put("status", -1);
			dataMap.put("message", "请完善商品信息");
			return dataMap;
		}

		dataMap.put("status", 1);
		dataMap.put("message", "操作成功");
		return dataMap;
	}

	public Map<String, Object> getGoodsStandard(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> dataMap = new HashMap<>();
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {
			return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
		}
		return dataMap;
	}

	/**
	 * @author ZhangLu 商品下架
	 * */
	@Override
	public Map<String, Object> soldOutGoods(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {
			return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
		}
		try {
			String typeId = parameter.getData().getString("typeId");
			String goodsOrder = parameter.getData().getString("goodsOrder");
			String sellerMallId = goodsOrder.substring(3, goodsOrder.length());// 周边店铺Id
			String baseGoodsId = parameter.getData().getString("baseGoodsId");
			ReGoodsOfSellerMall sellerMall = reGoodsOfSellerMallDao
					.findById(Integer.parseInt(sellerMallId));
			ReGoodsOfBase baseGoods = reGoodsOfBaseDao.findById(Integer
					.parseInt(baseGoodsId));
			if (sellerMall != null) {
				sellerMall.setIsValid(false);
				sellerMall.setIsChecked(false);
				sellerMall.setShelvesTime(null);
				reGoodsOfSellerMallDao.update(sellerMall);
				try {
					String changeLaunchMall = baseGoods.changeLaunchMall(
							baseGoods.getLaunchMall() == null ? "000000000"
									: baseGoods.getLaunchMall(), 1, false);
					baseGoods.setLaunchMall(changeLaunchMall);
					reGoodsOfBaseDao.update(baseGoods);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			QueryModel queryModel = new QueryModel();
			queryModel.combPreEquals("isValid", true);
			queryModel.combPreEquals("sellerMallId",
					Integer.parseInt(sellerMallId));
			ReGoodsOfLocalSpecialtyMall specialtyMall = (ReGoodsOfLocalSpecialtyMall) dateBaseDAO
					.findOne(ReGoodsOfLocalSpecialtyMall.class, queryModel);
			if (specialtyMall != null) {
				specialtyMall.setIsValid(false);
				reGoodsOfLocalSpecialtyMallDao.update(specialtyMall);
				try {
					String changeLaunchMall = baseGoods.changeLaunchMall(
							baseGoods.getLaunchMall() == null ? "000000000"
									: baseGoods.getLaunchMall(), 4, false);
					baseGoods.setLaunchMall(changeLaunchMall);
					reGoodsOfBaseDao.update(baseGoods);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			ReGoodsOfScoreMall scoreMall = (ReGoodsOfScoreMall) dateBaseDAO
					.findOne(ReGoodsOfScoreMall.class, queryModel);
			if (scoreMall != null) {
				scoreMall.setIsValid(false);
				reGoodsOfScoreMallDao.update(scoreMall);

				try {
					String changeLaunchMall = baseGoods.changeLaunchMall(
							baseGoods.getLaunchMall() == null ? "000000000"
									: baseGoods.getLaunchMall(), 2, false);
					baseGoods.setLaunchMall(changeLaunchMall);
					reGoodsOfBaseDao.update(baseGoods);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			ReGoodsOfSeckillMall seckillMall = (ReGoodsOfSeckillMall) dateBaseDAO
					.findOne(ReGoodsOfSeckillMall.class, queryModel);
			if (seckillMall != null) {
				seckillMall.setIsValid(false);
				reGoodsOfSeckillMallDao.update(seckillMall);
				try {
					String changeLaunchMall = baseGoods.changeLaunchMall(
							baseGoods.getLaunchMall() == null ? "000000000"
									: baseGoods.getLaunchMall(), 3, false);
					baseGoods.setLaunchMall(changeLaunchMall);
					reGoodsOfBaseDao.update(baseGoods);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			ReGoodsOfTeamMall teamMall = (ReGoodsOfTeamMall) dateBaseDAO
					.findOne(ReGoodsOfTeamMall.class, queryModel);
			if (teamMall != null) {
				teamMall.setIsValid(false);
				reGoodsOfTeamMallDao.update(teamMall);

				if (baseGoods.getLaunchMall().length() == 7) {
					baseGoods.setLaunchMall(baseGoods.getLaunchMall() + "0");
				}

				String changeLaunchMall = baseGoods.changeLaunchMall(
						baseGoods.getLaunchMall() == null ? "000000000"
								: baseGoods.getLaunchMall(), 8, false);
				baseGoods.setLaunchMall(changeLaunchMall);
				reGoodsOfBaseDao.update(baseGoods);
			}

			ReGoodsOfChangeMall changeMall = (ReGoodsOfChangeMall) dateBaseDAO
					.findOne(ReGoodsOfChangeMall.class, queryModel);
			if (changeMall != null) {
				changeMall.setIsValid(false);
				reGoodsOfChangeMallDao.update(changeMall);

				try {
					String changeLaunchMall = baseGoods.changeLaunchMall(
							baseGoods.getLaunchMall() == null ? "000000000"
									: baseGoods.getLaunchMall(), 7, false);
					baseGoods.setLaunchMall(changeLaunchMall);
					reGoodsOfBaseDao.update(baseGoods);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			statusMap = UrlUtil.getTaoKeToMap(sellerUrl
					+ "/Json/Index/delCoup?&goodsOrder=sem" + sellerMallId);// 下架之后
																			// 删除推广的优惠券
			if (statusMap != null) {
				statusMap.put("status", 1);
				statusMap.put("message", "下架成功");
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			e.printStackTrace();
			statusMap.put("status", -1);
			statusMap.put("message", "下架失败");
		}
		return statusMap;

	}

	/**
	 * @author ZhangLu 商品上架
	 * */
	@Override
	public Map<String, Object> soldUpGoods(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {
			return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
		}
		try {
			String goodsOrder = parameter.getData().getString("goodsOrder");
			String baseGoodsId = parameter.getData().getString("baseGoodsId");
			String str = goodsOrder.substring(3, goodsOrder.length());
			ReGoodsOfSellerMall sellerMall = reGoodsOfSellerMallDao
					.findById(Integer.parseInt(str));
			if (sellerMall != null) {
				ReGoodsOfBase baseGoods = reGoodsOfBaseDao.findById(Integer
						.parseInt(baseGoodsId));

				ReGoodsSnapshot snapshot = new ReGoodsSnapshot();
				snapshot.setBaseGoodsId(Integer.parseInt(baseGoodsId));
				snapshot.setCheckStatus(0);
				snapshot.setCreateTime(new java.sql.Timestamp(System
						.currentTimeMillis()));
				snapshot.setName(baseGoods.getName());
				snapshot.setCoverPic(baseGoods.getCoverPic());
				snapshot.setDescriptionPics(baseGoods.getDescriptionPics());
				snapshot.setLables(baseGoods.getLables());
				snapshot.setType(baseGoods.getType());
				snapshot.setSign(baseGoods.getSign());
				snapshot.setSeller(baseGoods.getSeller());
				snapshot.setIsValid(true);
				reGoodsSnapshotDao.save(snapshot);

				ReGoodsOfSellerMall goodsOfSellerMall = new ReGoodsOfSellerMall();
				goodsOfSellerMall.setBaseGoodsId(sellerMall.getBaseGoodsId());
				goodsOfSellerMall.setAddedTime(new java.sql.Timestamp(System
						.currentTimeMillis()));
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.DAY_OF_YEAR, 180);
				goodsOfSellerMall.setShelvesTime(new Timestamp(calendar
						.getTime().getTime()));
				goodsOfSellerMall.setIsChecked(true);
				goodsOfSellerMall.setIsValid(true);
				goodsOfSellerMall.setCreateTime(new java.sql.Timestamp(System
						.currentTimeMillis()));
				goodsOfSellerMall.setDisplayPrice(sellerMall.getDisplayPrice());
				goodsOfSellerMall.setDefaultRepertory(sellerMall
						.getDefaultRepertory());
				goodsOfSellerMall.setTransportationType(sellerMall
						.getTransportationType());
				goodsOfSellerMall.setIsNoStandard(sellerMall.getIsNoStandard());
				goodsOfSellerMall.setNoStandardScore(sellerMall
						.getNoStandardScore());
				goodsOfSellerMall.setNoStandardPrice(sellerMall
						.getNoStandardPrice());
				goodsOfSellerMall.setNoStandardRepertory(sellerMall
						.getNoStandardRepertory());
				goodsOfSellerMall.setPrice(sellerMall.getPrice());
				goodsOfSellerMall.setStandardDetails(sellerMall
						.getStandardDetails());
				goodsOfSellerMall.setTransportationPrice(sellerMall
						.getTransportationPrice());
				goodsOfSellerMall.setSnapshotGoods(snapshot);
				goodsOfSellerMall.setRightsProtect(sellerMall
						.getRightsProtect());
				goodsOfSellerMall.setIsNotChange(sellerMall.getIsNotChange());
				goodsOfSellerMall.setIsSendScore(sellerMall.getIsSendScore());
				goodsOfSellerMall.setSendScoreNum(sellerMall.getSendScoreNum());
				goodsOfSellerMall.setWeChatTypeId(sellerMall.getWeChatTypeId());
				reGoodsOfSellerMallDao.save(goodsOfSellerMall);

				goodsOfSellerMall.setReGoodsOfSellerMall(goodsOfSellerMall);
				// sellerMall.setReGoodsOfSellerMall(sellerMall);
				reGoodsOfSellerMallDao.update(goodsOfSellerMall);
				goodsOfSellerMall.setGoodsOrder(ReBaseGoods.SellerMall
						+ goodsOfSellerMall.getId());

				try {
					String changeLaunchMall = baseGoods.changeLaunchMall(
							baseGoods.getLaunchMall() == null ? "000000000"
									: baseGoods.getLaunchMall(), 1, true);
					baseGoods.setLaunchMall(changeLaunchMall);
					baseGoods.setSnapshotId(snapshot.getId());
					reGoodsOfBaseDao.update(baseGoods);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			statusMap.put("status", 1);
			statusMap.put("message", "上架成功");
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			e.printStackTrace();
			statusMap.put("status", -1);
			statusMap.put("message", "上架失败");
		}

		return statusMap;
	}

	/**
	 * @author ZhangLu 我的推广
	 * */
	@Override
	public Map<String, Object> getMyextension(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {
			return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
		}
		Integer typeId = parameter.getData().getInteger("typeId");
		String sellerId = parameter.getSellerId();
		try {
			List<Map<String, Object>> list = new ArrayList<>();
			QueryModel queryModel = new QueryModel();
			queryModel.combPreEquals("isValid", true);
			queryModel.combPreEquals("sellerId", Integer.parseInt(sellerId));
			queryModel.combCondition(" sellerMallId is not null");
			if (typeId.equals(0)) {
				List<ReGoodsOfLocalSpecialtyMall> locallist = dateBaseDAO
						.findLists(ReGoodsOfLocalSpecialtyMall.class,
								queryModel);
				List<ReGoodsOfScoreMall> scorelist = dateBaseDAO.findLists(
						ReGoodsOfScoreMall.class, queryModel);
				List<ReGoodsOfSeckillMall> seckilllist = dateBaseDAO.findLists(
						ReGoodsOfSeckillMall.class, queryModel);
				List<ReGoodsofextendmall> extendlist = dateBaseDAO.findLists(
						ReGoodsofextendmall.class, queryModel);

			} else if (typeId.equals(1)) {
				List<ReGoodsOfLocalSpecialtyMall> locallist = dateBaseDAO
						.findLists(ReGoodsOfLocalSpecialtyMall.class,
								queryModel);
				for (ReGoodsOfLocalSpecialtyMall local : locallist) {

				}
			} else if (typeId.equals(2)) {
				queryModel.combPreEquals("isActivity", 0);
				List<ReGoodsofextendmall> extendlist = dateBaseDAO.findLists(
						ReGoodsofextendmall.class, queryModel);
			} else if (typeId.equals(3)) {
				queryModel.combPreEquals("isActivity", 1);
				List<ReGoodsofextendmall> extendlist = dateBaseDAO.findLists(
						ReGoodsofextendmall.class, queryModel);
			} else if (typeId.equals(4)) {
				List<ReGoodsOfScoreMall> scorelist = dateBaseDAO.findLists(
						ReGoodsOfScoreMall.class, queryModel);
			} else if (typeId.equals(5)) {
				List<ReGoodsOfSeckillMall> seckilllist = dateBaseDAO.findLists(
						ReGoodsOfSeckillMall.class, queryModel);
			} else if (typeId.equals(8)) {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return JsonResponseUtil.getJson(0x01, "操作成功");
	}

	/**
	 * 获取周边店铺已有的规格
	 */
	@Override
	public Map<String, Object> getStandardDetail(HttpServletRequest request,
			HttpServletResponse response) {

		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {
			return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
		}
		Map<String, Object> statusMap = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		try {

			JSONObject jsonData = parameter.getData();
			// 传过来的必定是周边店铺sem4416 ID
			// 因为要取周边店铺定义的规格
			Integer semId = ReGoodsOfBase.getGoodsIdByGoodsOrder(jsonData
					.getString("goodsOrder"));

			ReGoodsOfSellerMall sellerMall = reGoodsOfSellerMallDao
					.findById(semId);
			// List<Map<String, Object>> standardDetailsList =
			// sellerMall.getSecondStandardJsonForPhone(null);

			if (sellerMall.getIsNoStandard()) {
				data.put("spec", sellerMall.getFirstStandardJsonForPhone());
				data.put("specNotes",
						sellerMall.getSecondStandardJsonForPhone(null));
			}

			// data.put("standardDetails", standardDetailsList);
			statusMap.put("data", data);
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			statusMap.put("status", -1);
			statusMap.put("message", "请求失败");
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
		return statusMap;

	}

	/**
	 * 商家版 获取商品信息
	 */
	@Override
	public Map<String, Object> getGoodsdetails(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {
			return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
		}
		Map<String, Object> statusMap = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		statusMap.put("data", data);
		try {
			String basePath = request.getServletContext()
					.getAttribute("RESOURCE_LOCAL_URL").toString();
			JSONObject jsonData = parameter.getData();

			Integer semId = ReGoodsOfBase.getGoodsIdByGoodsOrder(jsonData
					.getString("goodsOrder"));
			ReGoodsOfSellerMall sellerMall = reGoodsOfSellerMallDao
					.findById(semId);
			ReGoodsOfBase baseGoods = reGoodsOfBaseDao.findById(sellerMall
					.getBaseGoodsId());

			 List<ReGoodsDetails> reGoodsDetailsList =
			 reGoodsDetailsDao.findByPropertyIsValid("goods.id",
			sellerMall.getBaseGoodsId());
			 ReGoodsDetails
			 details=reGoodsDetailsList.size()>0?reGoodsDetailsList.get(0):new
			 ReGoodsDetails();

			data.put("baseGoodsId", sellerMall.getBaseGoodsId());
			String categoryName = "";
			Integer categoryId = -1;

			if (baseGoods.getFirstType() != null) {
				JSONObject firstType = baseGoods.getFirstType();
				String parentName = firstType.getString("parentTypeName");
				String childTypeName = firstType.getString("childTypeName");
				if (StringUtils.isNotBlank(parentName)
						|| StringUtils.isNotBlank(childTypeName)) {
					categoryName = parentName + "-" + childTypeName;
					categoryId = firstType.getInteger("childTypeId");
				}
			}
			//----da
			
			 data.put("isChange", sellerMall.getIsNotChange());
		      if (sellerMall.getIsNotChange().booleanValue()) {
		        data.put("isNotChange", "1");
		      } else {
		        data.put("isNotChange", "0");
		      }
		      if (sellerMall.getIsSendScore() == null) {
		        data.put("isSendScore", Integer.valueOf(0));
		      } else if (sellerMall.getIsSendScore().booleanValue()) {
		        data.put("isSendScore", Integer.valueOf(1));
		      } else {
		        data.put("isSendScore", Integer.valueOf(0));
		      }
			data.put("sendScoreNum", sellerMall.getSendScoreNum());
			
			//----da
			data.put("categoryId", categoryId);
			data.put("categoryName", categoryName);
			data.put("transportationType", sellerMall.getTransportationType());
			data.put("transportationPrice", sellerMall.getTransportationPrice());
			data.put("topPics", baseGoods.getPrefixCoverPic(basePath));
			data.put("goodsName", baseGoods.getName());
			data.put("orieignPrice", sellerMall.getDisplayPrice() == null ? ""
					: sellerMall.getDisplayPrice().toString());
			data.put("hasSpecStr", (!sellerMall.getIsNoStandard()));
			data.put("rightsProtect", sellerMall.getRightsProtectToJson());
			if (sellerMall.getIsNoStandard()) {
				List<Map<String, Object>> list = new ArrayList<>();
				Map<String, Object> obj = new HashMap<>();
				obj.put("id", "");
				obj.put("price", sellerMall.getNoStandardPrice() == 0d ? "0"
						: sellerMall.getNoStandardPrice().toString());
				obj.put("stock", sellerMall.getNoStandardRepertory().toString());
				obj.put("specStr", "");
				list.add(obj);
				data.put("specifications", list);
			} else {
				data.put("specifications", sellerMall.getSpecifications());
			}
			
			List<Map<String,Object>> content = new ArrayList<Map<String,Object>>();
			Map<String,Object> map = new HashMap<String,Object>();
			if(details.getContent() != null){
				
				com.gexin.fastjson.JSONArray list = JSON.parseArray(details.getContent());
				
				for(int i=0;i<list.size();i++){
					map = (Map<String, Object>) list.get(i);
					if(map.get("picture") != null){
						
						map.put("picture", basePath + map.get("picture").toString());
					}
					content.add(map);
				}
			}
			data.put("goodsDetail",content);
			
		
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功");
		
		} catch (Exception e) {
			e.printStackTrace();
			statusMap.put("status", -1);
			statusMap.put("message", "请求失败");
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}

		

		return statusMap;
	}

	public List<Map<String, Object>> getGoodsPublishStatus(Integer sellerMallId) {

		// 有顺序的
		List<Map<String, Object>> list = new ArrayList<>();

		Map<String, Object> data1 = new HashMap<String, Object>();
		Map<String, Object> data2 = new HashMap<String, Object>();
		Map<String, Object> data3 = new HashMap<String, Object>();
		Map<String, Object> data4 = new HashMap<String, Object>();
		Map<String, Object> data5 = new HashMap<String, Object>();
		Map<String, Object> data6 = new HashMap<String, Object>();
		Map<String, Object> data7 = new HashMap<String, Object>();
		

		List<Object[]> countList = reGoodsOfSellerMallDao
				.findPutStatus(sellerMallId);

		int count = Integer.parseInt(countList.get(0)[0].toString());

		data1.put("status", count == 0);
		data1.put("message", "请勿重复推广该商品");
		list.add(data1);

		count = Integer.parseInt(countList.get(0)[1].toString());

		data2.put("status", count == 0);
		data2.put("message", "请勿重复推广该商品");
		list.add(data2);

		count = Integer.parseInt(countList.get(0)[2].toString());

		data3.put("status", count == 0);
		data3.put("message", "请勿重复推广该商品");
		list.add(data3);

		count = Integer.parseInt(countList.get(0)[3].toString());

		data4.put("status", count == 0);
		data4.put("message", "请勿重复推广该商品");
		list.add(data4);

		count = Integer.parseInt(countList.get(0)[4].toString());

		data5.put("status", count == 0);
		data5.put("message", "请勿重复推广该商品");
		list.add(data5);

		count = Integer.parseInt(countList.get(0)[5].toString());
		data6.put("status", count == 0);
		data6.put("message", "请勿重复推广该商品");
		list.add(data6);

		count = Integer.parseInt(countList.get(0)[6].toString());
		data7.put("status", count == 0);
		data7.put("message", "请勿重复推广该商品");
		list.add(data7);
		
		return list;

	}

	@Override
	public Map<String, Object> getExtensionGoods(HttpServletRequest request,
			HttpServletResponse response) {

		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {
			return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
		}
		Map<String, Object> statusMap = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();

		return null;
	}

	@Override
	public Map<String, Object> obtainUserIdentity(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> partnerInfoMap = new HashMap<String, Object>();
		Map<String, Object> sellerInfoMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			String userId = parameter.getUserId();
			boolean isPartner = false;
			boolean isOpenedStore = false;
			if (StringUtils.isBlank(userId)) {
				return JsonResponseUtil.getJson(-0x02, "用户未登录！");
			}
			Users users = usersDao.findById(Integer.parseInt(userId));
			if (users == null) {
				return JsonResponseUtil.getJson(-0x02, "用户未登录！");
			}
			QueryModel queryModel = new QueryModel();
			queryModel.combEquals("isValid", 1);
			queryModel.combPreEquals("level", 2);
			// queryModel.combPreEquals("isCareerPartner",0);
			queryModel.combPreEquals("users.id", users.getId(), "user_id");
			List<TkldPid> sellertklList = dateBaseDAO.findLists(TkldPid.class,
					queryModel);
			if (sellertklList.size() > 0) {
				isOpenedStore = true;
				isPartner = true;
			}

			queryModel.clearQuery();
			queryModel.combEquals("isValid", 1);
			queryModel.combPreEquals("level", 3);
			queryModel.combPreEquals("users.id", users.getId(), "userId");
			List<TkldPid> tkldPidList = dateBaseDAO.findLists(TkldPid.class,
					queryModel);
			if (tkldPidList.size() > 0) {
				isPartner = true;
			}

			partnerInfoMap.put("isPartner", isPartner);
			partnerInfoMap.put("isOpenedStore", isOpenedStore);
			partnerInfoMap.put("applyCost", "99");
			partnerInfoMap.put("introductionTitle1", "什么是创业会员?");
			partnerInfoMap.put("introductionTitle2", "创业会员怎么赚钱?");
			partnerInfoMap.put("introductionTitle3", "怎么结算?");
			if (isPartner == false) {
				partnerInfoMap
						.put("bannerImg",
								"http://jph.aixiaoping.cn:8080/jupinhuiRes/cashshop_type/1/nomal/998260312093509100.png");
			} else {
				partnerInfoMap
						.put("bannerImg",
								"http://jph.aixiaoping.cn:8080/jupinhuiRes/cashshop_type/1/nomal/998260312093509100.png");
			}

			partnerInfoMap.put("bannerType", "0");
			partnerInfoMap
					.put("introductionContent1",
							"申请成为创业会员就是平台千万商品的代理，即刻享受各种优惠商品的推广权益（淘宝、天猫 、拼多多，平台实体商家的各种商品等 ）。");
			partnerInfoMap.put("introductionContent2",
					"推荐粉丝注册，永久获得粉丝消费购物现金奖励。通过微信、qq等各种渠道分享商品有佣金得。");
			partnerInfoMap.put("introductionContent3",
					"所有商品消费者确认收货后，15天没有退款退货的，奖励就会进入创业会员结算账户，奖励金额可商城购物，金额满100元以上可随时提现至银行卡。");

			list.add(partnerInfoMap);

			sellerInfoMap.put("isPartner", isPartner);
			sellerInfoMap.put("isOpenedStore", isOpenedStore);
			sellerInfoMap.put("applyCost", "299");
			sellerInfoMap.put("introductionTitle1", "高级合伙人有哪些好处?");
			sellerInfoMap.put("introductionTitle2", "申请店铺的步骤：");
			sellerInfoMap.put("introductionTitle3", "");
			if (isOpenedStore == false) {
				sellerInfoMap
						.put("bannerImg",
								"http://jph.aixiaoping.cn:8080/jupinhuiRes/cashshop_type/1/nomal/9658210722011711023.jpg");
			} else {
				sellerInfoMap
						.put("bannerImg",
								"http://jph.aixiaoping.cn:8080/jupinhuiRes/cashshop_type/1/nomal/9658220208034627023.jpg");
			}
			sellerInfoMap.put("bannerType", "1");
			sellerInfoMap
					.put("introductionContent1",
							"1.在享受合伙人所有权限之外，高级合伙人还拥有自己的线上店铺，享受线上平台售卖功能 ；\n"
									+ "\n2.免费享受平台各种线上线下推广功能，进行各类活动推广，帮您促销商品，增加曝光，拓 展客源提高销量，排忧解难 ；"
									+ "\n3.企业闲置商品发布换货交易，解决商企库存问题；");
			// +"\n4.另外，活动期间，前100名可以额外赠送1000积分呢，积分可直接换购商品，也可以促销送 给顾客！（需上架产品后获得全部奖励积分）");

			sellerInfoMap.put("introductionContent2", "1.申请开店需是高级合伙人的身份\n"
					+ "\n2.提交申请店铺资料信息，等待总部审核（2个工作日内）；\n"
					+ "\n3.审核成功，完善店铺资料、发布商品及其他推广营销活动即可。\n"
			// +"\n活动期间成功上架商品最高可获得1000积分奖励。\n"

					);
			sellerInfoMap.put("introductionContent3", "");
			list.add(sellerInfoMap);
			data.put("tips", "");
			//data.put("tips", "020-89281545");
			data.put("bannerInfos", list);
			statusMap.put("data", data);
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功！");
		} catch (Exception e) {
			e.printStackTrace();
			statusMap.put("status", -1);
			statusMap.put("message", "请求失败！");
		}
		return statusMap;
	}

	@Override
	public Map<String, Object> getSellerActivities(HttpServletRequest request,
			HttpServletResponse response) {

 		Parameter parameter = ParameterUtil.getParameter(request);
		String searchValue = parameter.getData().getString("searchValue");

		String basePath = request.getServletContext()
				.getAttribute("RESOURCE_LOCAL_URL").toString();

		String userId = "0";
		Double lat = 0d;
		Double lng = 0d;
		String v = null;
		String version = "";

		if (parameter != null) {
			userId = parameter.getUserId();
			lat = StringUtils.isEmpty(parameter.getLat()) ? null : Double
					.parseDouble(parameter.getLat());
			lng = StringUtils.isEmpty(parameter.getLng()) ? null : Double
					.parseDouble(parameter.getLng());
			// 获取版本号 da
			v = parameter.getAppVersion();
		}
		char[] charArray = v.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if (".".equals(String.valueOf(charArray[i]))) {
				continue;
			}
			version += charArray[i];
		}

		Map<String, Object> statusMap = new HashMap<String, Object>();

		Map<String, Object> goods = null;
		QueryModel queryModel = new QueryModel();
		Map<String, Object> dataMap =  new HashMap<String, Object>();
		List<Map<String, Object>> dataList = null;
		
		queryModel.clearQuery();
		queryModel.combPreEquals("isChecked", true);
		queryModel.combPreEquals("isValid", true);
		queryModel.combCondition("shelvesTime >= now()");// 下架时间
		queryModel.combCondition("addedTime <= now()");// 上架时间
		if (searchValue != null) {
			queryModel.combCondition("snapshotGoods.name like '%" + searchValue
					+ "%'");
		}
		// 积分商城 猕猴桃
		List<ReGoodsOfScoreMall> scoreMallList = dateBaseDAO.findLists(
				ReGoodsOfScoreMall.class, queryModel);
		if (scoreMallList.size() > 0) {
			dataMap = new HashMap<String, Object>();
			dataList = new ArrayList<Map<String,Object>>();
			for (ReGoodsOfScoreMall score : scoreMallList) {
				goods = new HashMap<String, Object>();
				goods.put("goodImg", score.getImageJsonForPhone(basePath)
						.get(0).get("image"));
				goods.put("goodName", score.getSnapshotGoods().getName());
				goods.put("goodPrice", String.valueOf(score.getPrice()));
				goods.put("goodOriPrice",
						String.valueOf(score.getDisplayPrice()));
				goods.put("goodId", score.getGoodsOrder());
				goods.put("sendScoreNum", 0);// 赠送积分数
				goods.put("scoreNum",
						score.getScore() == 0 ? 0 : score.getScore());
				goods.put(
						"postage",
						score.getTransportationPrice() == null ? "0" : String
								.valueOf(score.getTransportationPrice()));
				goods.put(
						"transportationType",
						score.getTransportationType() == null ? "" : score
								.getTransportationType());
				//goods.put("积分", 1);
				// goods.put("店铺id",
				// score.getSnapshotGoods().getSeller().getId());
				dataList.add(goods);
			}
			dataMap.put("goods", dataList);
			dataMap.put(
					"topImg",
					"http://jph.aixiaoping.cn:8080/jupinhuiRes/cashshop_type/1/nomal/11682250716090054853.png");
			statusMap.put("scoreExchangePrefecture", dataMap);
		}

		queryModel.clearQuery();
		queryModel.combPreEquals("isChecked", true);
		queryModel.combPreEquals("isValid", true);
		queryModel.combCondition("shelvesTime >= now()");// 下架时间
		queryModel.combCondition("addedTime <= now()");// 上架时间
		
		if (searchValue != null) {

			queryModel.combCondition("snapshotGoods.name like '%" + searchValue
					+ "%'");
		}
		// 秒杀 可以筛选 猕猴桃
		List<ReGoodsOfSeckillMall> killMallList = dateBaseDAO.findLists(
				ReGoodsOfSeckillMall.class, queryModel);
		if (killMallList.size() > 0) {
				dataMap = new HashMap<String, Object>();
				dataList = new ArrayList<Map<String,Object>>();
			for (ReGoodsOfSeckillMall kill : killMallList) {
				goods = new HashMap<String, Object>();
				goods.put("goodImg", kill.getImageJsonForPhone(basePath).get(0)
						.get("image"));
				goods.put("goodName", kill.getSnapshotGoods().getName());
				goods.put("goodPrice", String.valueOf(kill.getPrice()));
				goods.put("goodOriPrice",
						String.valueOf(kill.getDisplayPrice()));
				goods.put("goodId", kill.getGoodsOrder());
				goods.put("sendScoreNum", 0);// 赠送积分数
				goods.put("scoreNum",
						kill.getScore() == 0 ? 0 : kill.getScore());
				goods.put("postage", kill.getTransportationPrice() == 0 ? "0"
						: String.valueOf(kill.getTransportationPrice()));
				goods.put(
						"transportationType",
						kill.getTransportationType() == null ? "" : kill
								.getTransportationType());
				Set<CashshopTimes> cts = kill.getTimes();
				
				String st = "";
				String et = "";
				
				if(cts.size()>0){
					for(CashshopTimes ct :cts){
						//System.out.println(ct.getStartTime()+"---"+ct.getEndTime());
						st= ct.getStartTime();
						et = ct.getEndTime();
					}
				}
				
				
				
				goods.put("startKillTime", st);//秒杀开始时间
				goods.put("endKillTime", et);//秒杀结束时间
				dataList.add(goods);
			}
			dataMap.put("goods", dataList);
			dataMap.put(
					"topImg",
					"http://jph.aixiaoping.cn:8080/jupinhuiRes/cashshop_type/1/nomal/11682270430101430853.png");
			statusMap.put("secondKillPrefecture", dataMap);
		}

		queryModel.clearQuery();
		queryModel.combPreEquals("isChecked", true);
		queryModel.combPreEquals("isValid", true);
		queryModel.combCondition("shelvesTime >= now()");// 下架时间
		queryModel.combCondition("addedTime <= now()");// 上架时间
		if (searchValue != null) {

			queryModel.combCondition("name like '%" + searchValue + "%'");
		}
		// 拼团 可以筛选
		List<ReGoodsOfTeamMall> teamMallList = dateBaseDAO.findLists(
				ReGoodsOfTeamMall.class, queryModel);

		if (teamMallList.size() > 0) {
				dataMap = new HashMap<String, Object>();//
				dataList = new ArrayList<Map<String,Object>>();
			for (ReGoodsOfTeamMall team : teamMallList) {
				goods = new HashMap<String, Object>();
				if(team.getImageJsonForPhone(basePath)!=null && team.getImageJsonForPhone(basePath).size()>0 ){
					goods.put("goodImg", team.getImageJsonForPhone(basePath).get(0)
							.get("image"));
				}else{
					goods.put("goodImg", "");
				}
				
				goods.put("goodName", team.getSnapshotGoods().getName());
				//拼团价
				goods.put("goodPrice", String.valueOf(CalcUtil.sub(team.getDisplayPrice(), team.getDiscountPrice())));
				//原价
				goods.put("goodOriPrice",String.valueOf(team.getDisplayPrice()));
						
				goods.put("goodId", team.getGoodsOrder());
				goods.put("sendScoreNum", 0);// 赠送积分数
				goods.put("scoreNum",
						team.getScore() == 0 ? 0 : team.getScore());
				goods.put("postage", team.getTransportationPrice() == 0 ? "0"
						: String.valueOf(team.getTransportationPrice()));
				goods.put(
						"transportationType",
						team.getTransportationType() == null ? "" : team
								.getTransportationType());
				//goods.put("团", 1);
				dataList.add(goods);
			}
			dataMap.put("goods", dataList);
			dataMap.put(
					"topImg",
					"http://jph.aixiaoping.cn:8080/jupinhuiRes/cashshop_type/1/nomal/11682270717055429853.png");
			statusMap.put("ptPrefecture", dataMap);
		}

		// 活动 优惠券 -- 可以筛选 薯条
		queryModel.clearQuery();
		queryModel.combPreEquals("isValid", true);
		queryModel.combPreEquals("sign", 1);// 换券中,未下架标识
		queryModel.combPreEquals("isActivity", 1);
		queryModel.combCondition("validtime >now()");
		
		
		if (searchValue != null) {
			queryModel.combCondition("name like '%" + searchValue + "%'");
		}
		List<ReGoodsofextendmall> extendMallList = dateBaseDAO.findLists(
				ReGoodsofextendmall.class, queryModel);
		if (extendMallList.size() > 0) {
				dataMap = new HashMap<String, Object>();//
				dataList = new ArrayList<Map<String,Object>>();
				
			for (ReGoodsofextendmall extend : extendMallList) {
				goods = new HashMap<String, Object>();
				String covers = extend.getCoverPic();
				if (StringUtil.hasLength(covers)
						&& covers.startsWith("[")) {
					com.alibaba.fastjson.JSONArray array = JSONObject
							.parseArray(covers);
					if (array.size() > 0) {
						covers = array.getJSONObject(0).getString(
								"imgUrl");
					} else {
						covers = "";
					}
				}
				goods.put("goodImg", basePath + covers);
				
				goods.put("goodName", extend.getName());
				goods.put("goodPrice", String.valueOf(extend.getPrice()));

				goods.put("goodOriPrice",String.valueOf(extend.getTicketprice()));
				goods.put("goodId", extend.getId());

				goods.put("quan_price", String.valueOf(extend.getTicketprice()));
				goods.put("earnMoney",
						String.valueOf((extend.getCommission() * 0.6)));
				//goods.put("券", 1);
				dataList.add(goods);
			}
			dataMap.put("goods", dataList);
			dataMap.put(
					"topImg",
					"http://jph.aixiaoping.cn:8080/jupinhuiRes/cashshop_type/1/nomal/11682280903042233853.png");
			statusMap.put("couponPrefecture", dataMap);

		}
		// 送积分的
		queryModel.clearQuery();
		queryModel.combPreEquals("isValid", true);
		queryModel.combPreEquals("isChecked", true);
		queryModel.combCondition("shelvesTime >= now()");// 下架时间
		queryModel.combCondition("addedTime <= now()");// 上架时间
		queryModel.combPreEquals("isSendScore", true);
		if (searchValue != null) {
			queryModel.combCondition("snapshotGoods.name like '%" + searchValue
					+ "%'");
		}
		List<ReGoodsOfSellerMall> sendScore = dateBaseDAO.findLists(
				ReGoodsOfSellerMall.class, queryModel);
		if (sendScore.size() > 0) {
				dataMap = new HashMap<String, Object>();//
				dataList = new ArrayList<Map<String,Object>>();
			for (ReGoodsOfSellerMall s : sendScore) {
				goods = new HashMap<String, Object>();
				
				String covers = s.getSnapshotGoods().getCoverPic();
				if (StringUtil.hasLength(covers) && covers.startsWith("[")) {
					com.alibaba.fastjson.JSONArray array = JSONObject
							.parseArray(covers);
					if (array.size() > 0) {
						covers = array.getJSONObject(0).getString("imgUrl");
					} else {
						covers = "";
					}
				}
				goods.put("goodImg", basePath + covers);
				goods.put("goodName", s.getSnapshotGoods().getName());
				goods.put("goodPrice", String.valueOf(s.getPrice()));
				goods.put("goodOriPrice", String.valueOf(s.getDisplayPrice()));
				goods.put("goodId", s.getGoodsOrder());
				goods.put("sendScoreNum", s.getSendScoreNum());// 赠送积分数
				goods.put("scoreNum", s.getScore() == 0 ? 0 : s.getScore());
				goods.put("postage", s.getTransportationPrice() == 0.0 ? "0.0"
						: String.valueOf(s.getTransportationPrice()));
				goods.put(
						"transportationType",
						s.getTransportationType() == null ? "" : s
								.getTransportationType());
				//goods.put("送积分", 1);
				dataList.add(goods);
			}
			dataMap.put("goods", dataList);
			dataMap.put(
					"topImg",
					"http://jph.aixiaoping.cn:8080/jupinhuiRes/cashshop_type/1/nomal/11682280307010751853.png");
			
			statusMap.put("sendScorePrefecture", dataMap);
		}

		// ================================================da
		
		if(statusMap.isEmpty()){
			return JsonResponseUtil.getJson(-0x01, "没有查询到", statusMap);
		}
		return JsonResponseUtil.getJson(0x01, "请求成功", statusMap);
	}

	@Override
	public Map<String, Object> getSendScoreGoods(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		String page = parameter.getData().getString("pageIndex");
		String basePath = request.getServletContext()
				.getAttribute("RESOURCE_LOCAL_URL").toString();

		String userId = "0";
		Double lat = 0d;
		Double lng = 0d;
		String v = null;
		String version = "";

		if (parameter != null) {
			userId = parameter.getUserId();
			lat = StringUtils.isEmpty(parameter.getLat()) ? null : Double
					.parseDouble(parameter.getLat());
			lng = StringUtils.isEmpty(parameter.getLng()) ? null : Double
					.parseDouble(parameter.getLng());
			// 获取版本号 da
			v = parameter.getAppVersion();
		}
		char[] charArray = v.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if (".".equals(String.valueOf(charArray[i]))) {
				continue;
			}
			version += charArray[i];
		}
		
		QueryModel query = new QueryModel();
		query.clearQuery();
		query.combPreEquals("isValid", true);
		query.combPreEquals("isChecked", true);
		query.combCondition("shelvesTime >= now()");// 下架时间
		query.combCondition("addedTime <= now()");// 上架时间
		query.combPreEquals("isSendScore", true);
		
		int count = dateBaseDAO.findCount(ReGoodsOfSellerMall.class, query);
		int totalPage = (count % pageSize) > 0 ?((count / pageSize)+1):(count/pageSize);
		int start = (Integer.valueOf(page) -1) * pageSize;
		List<ReGoodsOfSellerMall> reGoodsList = dateBaseDAO.findPageList(ReGoodsOfSellerMall.class, query, start, pageSize);
		
		if(reGoodsList.size()<=0 || reGoodsList == null){
			
			return JsonResponseUtil.getJson(-0x02, "没有查询到相关商品");
		}
		Map<String,Object> statusMap = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<Map<String,Object>> goods = new ArrayList<Map<String,Object>>();
		
		for(ReGoodsOfSellerMall s : reGoodsList){
			Map<String,Object> good = new HashMap<String,Object>();
			good = new HashMap<String, Object>();
			
			String covers = s.getSnapshotGoods().getCoverPic();
			if (StringUtil.hasLength(covers) && covers.startsWith("[")) {
				com.alibaba.fastjson.JSONArray array = JSONObject
						.parseArray(covers);
				if (array.size() > 0) {
					covers = array.getJSONObject(0).getString("imgUrl");
				} else {
					covers = "";
				}
			}
			good.put("goodImg", basePath + covers);
			good.put("goodName", s.getSnapshotGoods().getName());
			good.put("goodPrice", String.valueOf(s.getPrice()));
			good.put("goodOriPrice", String.valueOf(s.getDisplayPrice()));
			good.put("goodId", s.getGoodsOrder());
			good.put("sendScoreNum", s.getSendScoreNum());// 赠送积分数
			good.put("scoreNum", s.getScore() == 0 ? 0 : s.getScore());
			good.put("postage", s.getTransportationPrice() == 0.0 ? "0.0"
					: String.valueOf(s.getTransportationPrice()));
			good.put("transportationType",s.getTransportationType() == null ? "" : s.getTransportationType());
			
			goods.add(good);
		}
		
		
		
		dataMap.put("pageSize", totalPage);
		dataMap.put("goods", goods);
		statusMap.put("status",1);
		statusMap.put("message", "请求成功");
		statusMap.put("data",dataMap);
		
		
		
		return statusMap;
	}

	
	@Override
	public Map<String, Object> getMapBaiDu(HttpServletRequest request,
			HttpServletResponse response) {
		
		Parameter parameter = ParameterUtil.getParameter(request);
		if(parameter == null){
			return JsonResponseUtil.getJson(-0x02, "参数有误");
		}
		
		Map<String,Object> statusMap = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		String lat = parameter.getData().getString("lat");
		String lng = parameter.getData().getString("lng");
		String output  = "json";//输出格式
		String ak = "EalGGkvau6k05uQL1MeRk11v2QHdioz9"; //百度AK 
		String mcode = "55:95:95:79:CC:D0:38:89:7B:DB:70:93:6E:7E:5C:D8:FC:3B:E5:9F;com.axp.axpseller";
		String url = "http://api.map.baidu.com/geocoder/v2/?location="+lat+","+lng+"&ak="+ak+"&output="+output+"&mcode="+mcode;
		Map<String,Object> baiduMap = UrlUtil.getTaoKeToMap(url);
		if(baiduMap == null){
			return JsonResponseUtil.getJson(-0x02, "地址有误请重新选择");
		}
		Map<String,Object> result = (Map<String, Object>) baiduMap.get("result");
		String formatted_address =  result.get("formatted_address")==null ? "":result.get("formatted_address").toString();
		String sematic_description = result.get("sematic_description")==null?"":result.get("sematic_description").toString();
		
		dataMap.put("formatted_address", formatted_address);
		dataMap.put("sematic_description", sematic_description);
		statusMap.put("data", dataMap);
		statusMap.put("status",1);
		statusMap.put("message", "请求成功");
		
		return statusMap;
	}

	
	@Override
	public Map<String, Object> getSellerTypeAndCity(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,Object> dataMap = new HashMap<String,Object>();
		// 缓存地区和标签导航栏 每次都是一样的结果
		Object typeList = CacheUtil.getCacheByName("zbTypeList");
		if (typeList != null) {
			dataMap.put("typeList", typeList);
			dataMap.put("cityList", CacheUtil.getCacheByName("zbCityList"));
		} else {
			// 获取店铺类别列表================lxh
			List<Map<String, Object>> shopType = getShopType();
			List<Map<String, Object>> cityList = getCityList();
			dataMap.put("typeList", shopType);
			dataMap.put("cityList", cityList);
			CacheUtil.setCache("zbTypeList", shopType);
			CacheUtil.setCache("zbCityList", cityList);
		}
		return dataMap;
	}

	@Override
	public Map<String, Object> getSeller(HttpServletRequest request, HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		Map<String,Object> statusMap = new HashMap<String,Object>();
		Map<String,Object> status = new HashMap<String,Object>();
		if(parameter == null){
			return JsonResponseUtil.getJson(-0x02, "参数有误");
		}
		String adminUserId = parameter.getAdminuserId();
		String sellerId = parameter.getSellerId();
		AdminUser adminUser = adminUserDao.findById(Integer.valueOf(adminUserId));
		Seller seller = sellerDao.findById(Integer.valueOf(sellerId));
		if(adminUser == null || seller == null){
			return JsonResponseUtil.getJson(-0x03, "没有查询到相关商家信息");
			
		}
		try {
			status.put("headImg", StringUtil.isEmpty(seller.getUsers().getHeadimage())?StringUtil.url+StringUtil.userHead:seller.getUsers().getHeadimage());
			status.put("nickName", StringUtil.isEmpty(seller.getUsers().getRealname())?seller.getName():seller.getUsers().getRealname());
			statusMap.put("status",1);
			statusMap.put("message", "请求成功");
			statusMap.put("data",status);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			statusMap.put("status",-1);
			statusMap.put("message", "请求失败");
		}
		
		
		
		return statusMap;
	}
}
