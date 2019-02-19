package com.axp.service.impl;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.domain.OrderDetail;
import com.axp.dao.AdminuserCashpointRecordDAO;
import com.axp.dao.DateBaseDAO;
import com.axp.dao.ICashmoneyRecordDao;
import com.axp.dao.ICashpointsRecordDao;
import com.axp.dao.INewRedPaperAddendumDao;
import com.axp.dao.INewRedPaperLogDao;
import com.axp.dao.INewRedPaperSettingDao;
import com.axp.dao.INrpOrderLogDao;
import com.axp.dao.IReBackOrderDao;
import com.axp.dao.IReShoppingCarDao;
import com.axp.dao.ISellerMoneyRecordDao;
import com.axp.dao.ReBaseGoodsDAO;
import com.axp.dao.ReGoodsorderDao;
import com.axp.dao.ReGoodsorderItemDao;
import com.axp.dao.ScorerecordsDAO;
import com.axp.domain.AbstractOrderComment.InnerOrderComment;
import com.axp.domain.AdminUser;
import com.axp.domain.AdminUserScoreRecord;
import com.axp.domain.AdminuserCashpointRecord;
import com.axp.domain.CashmoneyRecord;
import com.axp.domain.CashshopTimes;
import com.axp.domain.FreeVoucherRecord;
import com.axp.domain.NewRedPaperAddendum;
import com.axp.domain.NewRedPaperLog;
import com.axp.domain.NrpOrderLog;
import com.axp.domain.OrderComment;
import com.axp.domain.ReBackOrder;
import com.axp.domain.ReBaseGoods;
import com.axp.domain.ReGoodsOfBase;
import com.axp.domain.ReGoodsOfLocalSpecialtyMall;
import com.axp.domain.ReGoodsOfLockMall;
import com.axp.domain.ReGoodsOfNineNineMall;
import com.axp.domain.ReGoodsOfScoreMall;
import com.axp.domain.ReGoodsOfSeckillMall;
import com.axp.domain.ReGoodsOfSellerMall;
import com.axp.domain.ReGoodsOfTeamMall;
import com.axp.domain.ReGoodsofextendmall;
import com.axp.domain.ReGoodsorder;
import com.axp.domain.ReGoodsorderItem;
import com.axp.domain.ReShoppingCar;
import com.axp.domain.Scorerecords;
import com.axp.domain.Seller;
import com.axp.domain.UserCoupons;
import com.axp.domain.Users;
import com.axp.domain.UsersMoneyRecord;
import com.axp.service.IOrderPayService;
import com.axp.service.IOrderService;
import com.axp.service.IReGoodsOfBaseService;
import com.axp.service.IReShoppingCarService;
import com.axp.service.UserCouponsService;
import com.axp.service.UserSystemMessageService;
import com.axp.util.AnalyzeMallUtil;
import com.axp.util.CalcUtil;
import com.axp.util.DateUtil;
import com.axp.util.JsonResponseUtil;
import com.axp.util.OrderUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;
import com.axp.util.StringUtil;
import com.axp.util.UrlUtil;
import com.google.gson.JsonArray;
import com.yilian.Constants;

@Service
public class OrderServiceImpl extends BaseServiceImpl<ReGoodsorder> implements IOrderService{

	@Autowired
	private AnalyzeMallUtil analyzeMallUtil;
	@Autowired
	private IReGoodsOfBaseService goodsService;
	@Autowired
	private IReShoppingCarDao reShoppingCarDao;
	@Autowired
	private IReShoppingCarService shoppingCarService;
	@Autowired
	private ReGoodsorderDao reGoodsorderDao;
	@Autowired
	private ReGoodsorderItemDao reGoodsorderItemDao;
	@Autowired
	private IReBackOrderDao reBackOrderDao;
	@Autowired
	private IOrderPayService orderPayService;
	@Autowired
	public INewRedPaperLogDao nrplDao;
	@Autowired
	public INewRedPaperAddendumDao nrpaDao;
	@Autowired
	public INewRedPaperSettingDao nrpsDao;
	@Autowired
	public INrpOrderLogDao nrpOrderLogDao;
	@Autowired
	public IReGoodsOfBaseService reGoodsOfBaseService;
	@Autowired
	public ReBaseGoodsDAO reBaseGoodsDao;
	@Autowired
	private ICashpointsRecordDao cashpointsRecordDao;
	@Autowired 
	public ICashmoneyRecordDao cashmoneyRecordDao;
	@Autowired
	public ScorerecordsDAO scorerecordsDao;
	@Resource
	UserSystemMessageService userSystemMessageService;
	@Autowired
	public AdminuserCashpointRecordDAO adminuserCashpointRecordDAO;
	@Autowired
	public UserCouponsService userCouponsService;
	
	
    @Autowired
    protected DateBaseDAO dateBaseDao;
	protected UrlUtil urlUtil;
	@Override
	public Map<String, Object> createTempOrderList(HttpServletRequest request,
			HttpServletResponse response) {
		String xcx = request.getParameter("xcx");
		String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
		JSONArray shoppingCarItemIds = new JSONArray();
		String goodsId = null;
		String userId = null;
		String type = null;
		Integer adminId = null;
		String os = null;
		Users cacheUser = null;
		Boolean isTeam = false;
		String teamOrders = null;
		String isGame = null;
		String goodsOrder = null;
		Integer number = 1;
		String o = null;
		Parameter parameter = null;
		JSONArray specs = new JSONArray();
		String[] sps = null;
		
		Map<String,Object> retMap = new HashMap<>();
		
		if(xcx != null){
			userId = request.getParameter("userId");
			type = request.getParameter("type");
			adminId = request.getParameter("adminId")==""?-1:Integer.valueOf(request.getParameter("adminId"));
			teamOrders = request.getParameter("teamOrders");
			isGame = request.getParameter("isGame");
			goodsOrder = request.getParameter("goodsId");
			o = request.getParameter("orderId");
			
		}else{
			
			parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			userId = parameter.getUserId();
			type = parameter.getData().getString("type"); 
			
			adminId = parameter.getData().getInteger("adminId")==null?null:parameter.getData().getInteger("adminId");
			os = parameter.getOs();
			isTeam = parameter.getData().getBoolean("isTeam")==null?false:parameter.getData().getBoolean("isTeam"); //是否拼团
			isGame = parameter.getData().getString("isGame");
			goodsOrder = parameter.getData().getString("goodsId");
			teamOrders =parameter.getData().getString("teamOrderId");
			//根据该商品规格获取商品信息  规格是通过商品详情传递过来的  
			specs = JSONArray.parseArray(parameter.getData().getString("specs")==null?"":parameter.getData().getString("specs"));
			o = parameter.getData().getString("orderId");
		
		}
		cacheUser = usersDao.findById(Integer.parseInt(userId));
		Integer teamOrderId=null;
		if(StringUtils.isNotBlank(teamOrders)){
			teamOrderId=Integer.parseInt(teamOrders.substring(5,teamOrders.length()-5));
		}
		ReGoodsorder teamOrder=null;
		if(teamOrderId!=null){
		 teamOrder = reGoodsorderDao.findById(teamOrderId);
		}
		Users users = new Users();
		Integer score1 = 0;
		if(cacheUser.getScore()!=null  ){
			score1 = cacheUser.getScore();
		}
		users.setScore(score1);
		users.setCashPoints(cacheUser.getCashPoints()==null?0.0d:cacheUser.getCashPoints());
		users.setMoney(cacheUser.getMoney());
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		//删除所有缓存数据
		reGoodsorderDao.deleteCache(Integer.parseInt(userId));
		reGoodsorderDao.deleteOrder(Integer.parseInt(userId));
		
		//返回前端集合
		Map<String,Object> dataMap = new HashMap<>();
		List<Object> dataList = new ArrayList<>();
		List<Integer> discountList=new ArrayList<Integer>(); //记录使用了那些优惠券 
		Map<String,Object> orderMap = null;
		ReShoppingCar car = null;
		//拼接购物车ID
		
		double totalDiscount=0d;//总优惠
		double totalMoney =0d;//总金额
		Integer totalScore =0;//总积分
		double totalCashpoint =0d;//总红包
		//如果是WEB进来 同一商品删除待支付 如有卷 
		//String os = (String) parameter.getOs();
		//if("WEB".equals(os)){
			updateOrderUserCoupons(Integer.parseInt(userId), goodsId);
		//}
		
		//用户优惠券集合
		List<UserCoupons> userCouponsList = userCouponsService.getUserAllCouponsList(Integer.parseInt(userId));
		Map<String,UserCoupons> umap =new HashMap<String,UserCoupons>();
		for(UserCoupons u:userCouponsList){
			if(u.getUsers()!=null && u.getGoodsMall()!=null){
				umap.put(u.getUsers().getId()+"$"+u.getGoodsMall(), u);
			}
		}
		
		
		
		if(type.equals("1")){	//购物车支付
			StringBuffer sb = new StringBuffer();
			if(xcx != null){
				String shoppingCarItemId = request.getParameter("shoppingCarItemIds");
				if(shoppingCarItemId != null){
					shoppingCarItemIds = JSONArray.parseArray(shoppingCarItemId);
				}
			}else{
				
				shoppingCarItemIds = (JSONArray) parameter.getData().get("shoppingCarItemIds");
			}
			for(int i = 0 ; i < shoppingCarItemIds.size(); i++){
				if(i==0){
					sb =sb.append(shoppingCarItemIds.get(0));
				}else{
					sb =sb.append(","+shoppingCarItemIds.get(i));
				}
			}
			//获取所有sellerId
			List<Integer> sellerIds = reShoppingCarDao.getSellerIdByShoppingCar(sb.toString());  //通过购物车id找到所有卖家id
			List<ReShoppingCar> reShoppingCar = reShoppingCarDao.getReShoppingCar(sb.toString()); //通过购物车id 找到所有购物车对象
			Set<Map<String,Object>> set = new HashSet<>();
			Map<String,Object> carMap = null;
			for(int i =0;i<reShoppingCar.size();i++){
//				reShoppingCar.get(i).setIsValid(false);
				//限购判断
				Map<String,Object> limitCheck = this.checkLimit(reShoppingCar.get(i), Integer.parseInt(userId),isTeam);
				if(!limitCheck.get("status").equals(1)){
					return limitCheck;
				}
				carMap = new HashMap<>();
				for(int sellerId:sellerIds){
					if(reShoppingCar.get(i).getSeller().getId()==sellerId){
						carMap.put("sellerId", sellerId);
						if(reShoppingCar.get(i).getLoginsticsType()==ReBaseGoods.shang_men_zi_qu||reShoppingCar.get(i).getLoginsticsType()==ReBaseGoods.dao_dian_xiao_fei){
							carMap.put("type3", ReBaseGoods.shang_men_zi_qu);
							carMap.put("type4", ReBaseGoods.dao_dian_xiao_fei);
						}else{
							carMap.put("type1", ReBaseGoods.bao_you);
							carMap.put("type2", ReBaseGoods.bu_bao_you);
						}
						set.add(carMap);
					}
				}
			}
			
			for(Map<String,Object> map:set){
				set.add(map);
			}
			List<ReShoppingCar> reShoppingCarList = reShoppingCarDao.getReShoppingCar(sb.toString());
			
			for(Map<String,Object> map :set){
				orderMap = new HashMap<>();
				List<Object> detailsList = new ArrayList<>();
				ReGoodsorder goodsorder = new ReGoodsorder();
				goodsorder.setIsTeam(isTeam);
				goodsorder.setReGoodsorder(teamOrder);
				reGoodsorderDao.save(goodsorder);
				ReGoodsorderItem item = null;
				for(ReShoppingCar r : reShoppingCarList){
					if(r.getSeller().getId().equals(map.get("sellerId"))){
						boolean pd =false;
						if(r.getLoginsticsType()==ReBaseGoods.bao_you){
							pd = map.get("type1")==null?false:true;
						}else if(r.getLoginsticsType()==ReBaseGoods.bu_bao_you){
							pd = map.get("type2")==null?false:true;
						}else if(r.getLoginsticsType()==ReBaseGoods.shang_men_zi_qu){
							pd = map.get("type3")==null?false:true;
						}else if(r.getLoginsticsType()==ReBaseGoods.dao_dian_xiao_fei){
							pd = map.get("type4")==null?false:true;
						}	
						car = r;
						if(pd){//运输方式  1包邮 2不包邮 3上门自取  4到店消费 
						
						//goodsorder.setPayPrice(goodsorder.getPayPrice()+r.getGoodsPrice()*r.getGoodsQuantity());//订单现金
						//goodsorder.setPayScore(goodsorder.getPayScore()+r.getScore()*r.getGoodsQuantity());//订单积分
						//goodsorder.setPayCashpoint(goodsorder.getPayCashpoint()+r.getRedPaper()*r.getGoodsQuantity());//订单红包
							
						int A = (int) CalcUtil.mul(r.getGoodsPrice(), r.getGoodsQuantity(), 2);//现金
						int B =(int) CalcUtil.mul(r.getScore(), r.getGoodsQuantity(), 2);//积分
						double C=0;
						
						
						
						if(r.getLoginsticsType()==ReBaseGoods.bu_bao_you){
							goodsorder.setLogisticsType(goodsorder.getLogisticsType()>r.getLogisticsPrice()?goodsorder.getLogisticsType():r.getLogisticsPrice());
						}
						item = getitem(r,goodsorder,nowTime);
						item.setIsTeam(isTeam);
						Map<String, Object> checkGoods = checkGoods(item);
						if(!checkGoods.get("status").equals(1)){
							return checkGoods;
						}
						
						goodsorder.setPayPrice(CalcUtil.add(goodsorder.getPayPrice(), A, 2));//订单现金
						goodsorder.setPayScore((int)CalcUtil.add(goodsorder.getPayScore(), B, 2));//订单积分
						if(goodsorder.getPayPrice()<0||goodsorder.getPayScore()<0||item.getGoodQuantity()<0){
							retMap.put("status", -1);
							retMap.put("message","支付金额异常");
							
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							
							return retMap;
						}
						
						
						
						
						
						
						String key = userId+"$"+r.getGoodsOrder();
						if(umap.get(key)!=null){//有优惠券
							UserCoupons uc = umap.get(key);
							List<ReGoodsorderItem> ilist = reGoodsorderItemDao.findByPropertyIsValid("userCoupons.id",uc.getId() );
							if(ilist ==null || ilist.isEmpty()){
								item.setUserCoupons(uc);
								C = uc.getTicketprice();
								item.setPayPrice(CalcUtil.sub(item.getPayPrice(), C));
								item.setPayCashpoint(C);
							}
						}
						
					/*	if(item.getIsTeam()){
							ReGoodsOfTeamMall teamMall = reGoodsOfTeamMallDao.findById(item.getMallId());
								item.setPayCashpoint(teamMall.getDiscountPrice());
						}*/
						
						goodsorder.setPayCashpoint(CalcUtil.add(goodsorder.getPayCashpoint(), item.getPayCashpoint(), 2));//订单红包
						
						
					detailsList.add(getItemMap(item, basePath));
					
					
					reGoodsorderItemDao.save(item);	
					
						//orderCashback(r,goodsorder);
						}
					}
				}	
				goodsorder.setIsHasItems(true);//判断是否有有效子订单
				goodsorder.setUser(car.getUser());//粉丝Id
				goodsorder.setSeller(car.getSeller());
				goodsorder.setCreateTime(nowTime);//创建时间
				goodsorder.setIsValid(true);//
				goodsorder.setOrderCode(OrderUtil.getOrderCode(goodsorder.getId()));//订单编码
				goodsorder.setStatus(ReGoodsorder.huan_cun);//订单状态
				goodsorder.setLogistics(car.getLoginsticsType().toString());//配送类型
				goodsorder.setSellerPhone(car.getSeller().getPhone());//商家电话
				goodsorder.setSellerAddress(car.getSeller().getAddress());//商家地址
				goodsorder.setCode(OrderUtil.getCode());
				goodsorder.setVisitor(os);
				goodsorder.setAdminId(adminId);
				//reGoodsorderDao.update(goodsorder);
				Map<String,Object> checkMap = orderPayService.checkOrderPrice(request, goodsorder,users);
				if(!checkMap.get("status").equals(1)){
					return checkMap;
				}
				
				orderMap.put("orderItems", detailsList);		
				//返回订单需要支付金额，红包，积分
				Map<String,Object> moneyMap = getOrderMoneyMap(goodsorder);
				double money = (double) moneyMap.get("money");//需要支付的现金
				
				Integer score = (Integer) moneyMap.get("score");//订单需要支付的积分
				double cashpoint = (double) moneyMap.get("cashpoint");//订单需要支付的红包
				
				orderMap = getOrderMap(orderMap,goodsorder,car.getSeller(),basePath,money,score,cashpoint);
				//totalMoney += goodsorder.getLogisticsType();
				totalMoney = CalcUtil.add(totalMoney, goodsorder.getLogisticsType(), 2);
				//totalMoney += goodsorder.getPayPrice();
				totalMoney = CalcUtil.add(totalMoney, goodsorder.getPayPrice(), 2);
			
				totalCashpoint = CalcUtil.add(0, goodsorder.getPayCashpoint(), 2);
				
				//totalScore += goodsorder.getPayScore();
				totalScore = (int) CalcUtil.add(totalScore, goodsorder.getPayScore(), 2);
				//totalCashpoint += goodsorder.getPayCashpoint();
				
				dataList.add(orderMap);
			}
		}else if(type.equals("2")){//直接支付接口
			if(xcx != null){
				goodsId = request.getParameter("goodsId")==""?"":request.getParameter("goodsId");
				number = Integer.valueOf(request.getParameter("number"));
			}else{
				goodsId = (String) (parameter.getData().get("goodsId")==null?"":parameter.getData().get("goodsId"));
				//商品数量
				number = parameter.getData().getInteger("number");
			}
			
			//判断不能再次购买
//			String mall = goodsOrder.substring(0, 3);
//			Integer goodId = Integer.parseInt(goodsOrder.substring(3));
//			if("ldm".equals(mall)){
//				QueryModel model = new QueryModel();
//				model.clearQuery();
//				model.combPreEquals("isValid", true);
//				model.combPreEquals("mallClass", mall);
//				model.combPreEquals("goodsId", goodId);
//				model.combPreEquals("status", ReGoodsorder.jifenduobao);
//				model.combPreEquals("user.id", Integer.valueOf(userId),"user_id");
//				List<ReGoodsorderItem> items = dateBaseDAO.findLists(ReGoodsorderItem.class, model);
//				if(items != null && items.size()>0){
//					retMap.put("status", -1);
//					retMap.put("message","您已经参加过该商品夺宝,静候开奖吧!");
//					return retMap;
//				}
//				
//				
//			}
			String spec = request.getParameter("specs")=="\"\""?null:request.getParameter("specs");
			if(spec != null && spec != "" && !("\"\"").equals(spec)){
				specs = JSONArray.parseArray(spec);
			}
			List<Integer> specsList = new ArrayList<>();
				if(specs!=null){
					for(int i=0;i<specs.size();i++){
						specsList.add(specs.getInteger(i));
					}
				}
			car = shoppingCarService.saveShoppingCar(Integer.parseInt(userId),goodsOrder,number,specsList,basePath,isTeam,isGame);
			
			if(car==null){
				retMap.put("status", -1);
				retMap.put("message","商品信息异常");
				return retMap;
			}
			//限购判断
			Map<String,Object> limitCheck = this.checkLimit(car, Integer.parseInt(userId),isTeam);
			if(!limitCheck.get("status").equals(1)){
				return limitCheck;
			}
			orderMap = new HashMap<>();
			List<Object> detailsList = new ArrayList<>();
			ReGoodsorder goodsorder = new ReGoodsorder(); 
			reGoodsorderDao.save(goodsorder);
			ReGoodsorderItem item = null;
			goodsorder.setPayPrice(goodsorder.getPayPrice()+car.getGoodsPrice()*car.getGoodsQuantity());//订单现金
			goodsorder.setPayScore(goodsorder.getPayScore()+car.getScore()*car.getGoodsQuantity());//订单积分
			int A = (int) CalcUtil.mul(car.getGoodsPrice(), car.getGoodsQuantity(), 2);//现金
			int B =(int) CalcUtil.mul(car.getScore(), car.getGoodsQuantity(), 2);//积分
			if(car.getLoginsticsType()==ReBaseGoods.bu_bao_you){
				goodsorder.setLogisticsType(goodsorder.getLogisticsType()>car.getLogisticsPrice()?goodsorder.getLogisticsType():car.getLogisticsPrice());
			}
			
			//orderCashback(car,goodsorder);
			goodsorder.setIsHasItems(true);//判断是否有有效子订单
			goodsorder.setUser(car.getUser());//粉丝Id
			goodsorder.setSeller(car.getSeller());
			goodsorder.setCreateTime(nowTime);//创建时间
			goodsorder.setIsValid(true);//
			goodsorder.setOrderCode(OrderUtil.getOrderCode(goodsorder.getId()));//订单编码
			
			goodsorder.setStatus(ReGoodsorder.dai_zhi_fu);
			goodsorder.setLogistics(car.getLoginsticsType().toString());//配送类型
			goodsorder.setSellerPhone(car.getSeller().getPhone());//商家电话
			goodsorder.setSellerAddress(car.getSeller().getAddress());//商家地址
			goodsorder.setCode(OrderUtil.getCode());	
			goodsorder.setIsTeam(isTeam);
			goodsorder.setReGoodsorder(teamOrder);
			goodsorder.setAdminId(adminId);
			goodsorder.setVisitor(os);
			item = getitem(car,goodsorder,nowTime);
			item.setIsTeam(isTeam);
			if("1".equals(isGame)){
				item.setGameType(commodityTypeDao.findById(266));
				item.setIsGame(1);
				item.setIsLock(1);
			}
			
			
			if((A<0&&goodsorder.getPayPrice()<0)||(B<0&&goodsorder.getPayScore()<0)||item.getGoodQuantity()<0){
				retMap.put("status", -1);
				retMap.put("message","支付金额异常");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return retMap;
			}
			
			double C=0d;
			String key = userId+"$"+car.getGoodsOrder();
			if(umap.get(key)!=null){//有优惠券
				UserCoupons uc = umap.get(key);
				List<ReGoodsorderItem> ilist = reGoodsorderItemDao.findByPropertyIsValid("userCoupons.id",uc.getId() );
				if(ilist ==null || ilist.isEmpty()){
					item.setUserCoupons(uc);
					C = uc.getTicketprice();
					item.setPayPrice(CalcUtil.sub(item.getPayPrice(), C));
					 item.setPayCashpoint(C);
				}
			}
			
			if(item.getIsTeam()){
				ReGoodsOfTeamMall teamMall = reGoodsOfTeamMallDao.findById(item.getMallId());
					item.setPayCashpoint(item.getGoodQuantity()*teamMall.getDiscountPrice());
			}
			
			goodsorder.setPayCashpoint(CalcUtil.add(goodsorder.getPayCashpoint(), item.getPayCashpoint(), 2));//订单红包		
			
			
			
			Map<String, Object> itemMap = getItemMap(item, basePath);
			detailsList.add(itemMap);
			Map<String, Object> checkGoods = checkGoods(item);
			if(!checkGoods.get("status").equals(1)){
				return checkGoods;
			}
			reGoodsorderItemDao.save(item);	
			
			//reGoodsorderItemDao.save(item);
			Map<String,Object> checkMap = orderPayService.checkOrderPrice(request, goodsorder,users);
			if(!checkMap.get("status").equals(1)){
				return checkMap;
			}
			orderMap.put("orderItems", detailsList);
			
			//返回订单需要支付金额，红包，积分
			Map<String,Object> moneyMap = getOrderMoneyMap(goodsorder);
			double money = (double) moneyMap.get("money");//需要支付的现金
			Integer score = (Integer) moneyMap.get("score");//订单需要支付的积分
			double cashpoint = (double) moneyMap.get("cashpoint");//订单需要支付的红包
			orderMap = getOrderMap(orderMap,goodsorder,car.getSeller(),basePath,money,score,cashpoint);
			//totalMoney += goodsorder.getLogisticsType();
			totalMoney = CalcUtil.add(totalMoney, goodsorder.getLogisticsType(), 2);//邮费,
			//totalMoney += goodsorder.getPayPrice();
		
			totalMoney = CalcUtil.add(totalMoney, goodsorder.getPayPrice(), 2);
			totalCashpoint = CalcUtil.add(totalCashpoint, goodsorder.getPayCashpoint(), 2);
			
			//totalScore += goodsorder.getPayScore();
			totalScore = (int) CalcUtil.add(totalScore, goodsorder.getPayScore(), 2);
			dataList.add(orderMap);		
		}else if(type.equals("3")){ //订单列表里面支付的订单  来源是 点击支付又取消的数据 产生的订单
			System.out.println("o:"+o);
			Integer orderId= Integer.parseInt(o.substring(5, o.length()-5));
			System.out.println(orderId);
			orderMap = new HashMap<>();
			List<Object> detailsList = new ArrayList<>();
			
			QueryModel queryModel = new QueryModel();
			queryModel.combPreEquals("id", orderId);
			ReGoodsorder goodsorder = (ReGoodsorder) dateBaseDAO.findOne(ReGoodsorder.class, queryModel);
			goodsorder.setStatus(-1);
			reGoodsorderDao.update(goodsorder);
			
			queryModel.clearQuery();
			queryModel.combPreEquals("order.id", orderId,"orderId");
			List<ReGoodsorderItem> itemList  =  dateBaseDAO.findLists(ReGoodsorderItem.class, queryModel);
			for(ReGoodsorderItem item:itemList){
				Map<String, Object> checkGoods = checkGoods(item);
				if(!checkGoods.get("status").equals(1)){
					return checkGoods;
				}
				item.setStatus(-1);
				reGoodsorderItemDao.update(item);
				//===========chy 优惠券============
				Map<String, Object> itemMap = getItemMap(item, basePath);
				
				//===========end 优惠券============
				detailsList.add(itemMap);
				
			}
			orderMap.put("orderItems", detailsList);
			//返回订单需要支付金额，红包，积分
			Map<String,Object> moneyMap = getOrderMoneyMap(goodsorder);
			double money = (double) moneyMap.get("money");//需要支付的现金
			Integer score = (Integer) moneyMap.get("score");//订单需要支付的积分
			double cashpoint = (double) moneyMap.get("cashpoint");//订单需要支付的红包
			orderMap = getOrderMap(orderMap,goodsorder,goodsorder.getSeller(),basePath,money,score,cashpoint);
			
			//totalMoney += goodsorder.getLogisticsType();
			totalMoney = CalcUtil.add(totalMoney, goodsorder.getLogisticsType(), 2);
			//totalMoney += goodsorder.getPayPrice();
			totalMoney = CalcUtil.add(totalMoney, goodsorder.getPayPrice(), 2);
			totalCashpoint = CalcUtil.add(totalCashpoint, goodsorder.getPayCashpoint(), 2);
			
			//totalScore += goodsorder.getPayScore();
			totalScore = (int) CalcUtil.add(totalScore, goodsorder.getPayScore(), 2);
			//totalCashpoint += goodsorder.getPayCashpoint();
			
			dataList.add(orderMap);		
		}
		dataMap.put("discountList", discountList); //使用的优惠卷
		dataMap.put("totalDiscount", totalDiscount);//总优惠
		dataMap.put("totalMoney", totalMoney);
		dataMap.put("totalScore", totalScore);
		dataMap.put("totalCashpoint", 0);
		dataMap.put("dataList", dataList);
		dataMap.put("Science", Constants.Science);//00测试环境，01正式环境
		
		retMap.put("status", 0x01);
		retMap.put("message", "请求成功");
		retMap.put("data", dataMap);
		return retMap;
		
	}
	
	
	/**
	 *检查商品时效性 
	 */
	public Map<String, Object> checkGoods(ReGoodsorderItem item){
		Map<String, Object> statusMap=new HashMap<>();
		statusMap.put("status", 1);
		Integer score = 0;
		String goodsId = item.getMallClass()+item.getGoodsId();
		score = (int) CalcUtil.add(score, item.getPayScore(), 2);
		ReBaseGoods good = (ReBaseGoods) goodsService.getMall(goodsId);
		Timestamp now = new Timestamp(DateUtil.getNow().getTime());
		//判断商品是否仍存在
		
		if(!good.getIsValid()
//					||!good.getIsChecked()
				||good.getAddedTime().after(now)
				||good.getShelvesTime().before(now)
				){
			statusMap.put("status", -1);
			statusMap.put("message", "商品【"+good.getSnapshotGoods().getName()+"】已下架");
			return statusMap;
		}
		return statusMap;
	}
	
	
	
	@Override
	public Map<String, Object> confirmOrder(HttpServletRequest request,
			HttpServletResponse response) {
		
		String xcx = request.getParameter("xcx");
		Integer userId = null;
		String phone = null;
		String name = null;
		String address = null;
		JSONArray orders = null;
		
		if(xcx != null){
			userId = Integer.valueOf(request.getParameter("userId"));
			phone = request.getParameter("phone");
			name = request.getParameter("name");
			address = request.getParameter("address");
			String order = request.getParameter("orders");
			if(order != null){
				orders = JSONArray.parseArray(order);
				
			}
		}else{
			
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			
			userId = Integer.parseInt(parameter.getUserId());
			phone = parameter.getData().getString("phone");
			name = parameter.getData().getString("name");
			address = parameter.getData().getString("address");
			orders = JSONArray.parseArray(parameter.getData().getString("orderIds"));
		}
		
		
		StringBuffer buffer = new StringBuffer();
		for(int i=0;i<orders.size();i++){
			String order= orders.getString(i);
			order=order.substring(5,order.length()-5);
			if(i==(orders.size()-1)){
				buffer.append(order);
			}else{
				buffer.append(order+",");
			}
		}
		List<ReGoodsorder> sessionOrders = reGoodsorderDao.getOrders(buffer.toString(),userId);
		
		if(sessionOrders.size()==0){
			return JsonResponseUtil.getJson(-0x5a,"无临时订单");
		}
		
		for(ReGoodsorder order : sessionOrders){
			List<ReGoodsorderItem> items = reGoodsorderItemDao.findByParent(order.getId());
			for(ReGoodsorderItem item : items){
				
				if(item.getMallId()==6){
					QueryModel model = new QueryModel();
					model.combPreEquals("users.id",userId,"userId");
					model.combPreEquals("isValid", true);
					model.combPreEquals("status", 0);
					model.combCondition("endTime > '"+DateUtil.formatDate("yyyy-MM-dd HH:mm:ss",DateUtil.getNow())+"'");
						
					int count = 0;
					try {
						count = dateBaseDAO.findCount(FreeVoucherRecord.class, model);
					} catch (Exception e) {
						e.printStackTrace();
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					}
					if(item.getGoodQuantity()>count){
						return JsonResponseUtil.getJson(-0x6a,"免单券不足");
					}
				}
				
				if(item.getCar()!=null){
					ReShoppingCar car = reShoppingCarDao.findById(item.getCar().getId());
					car.setIsValid(false);
				}
				
				//积分商城商品不可退单
				if (item.getMallClass()==ReBaseGoods.ScoreMall) {
					item.setIsBack(40);
					reGoodsorderItemDao.update(item);
				}
			}
		}

		reGoodsorderDao.updateSessionOrders(buffer.toString(), "0",name,phone,address);
		reGoodsorderDao.updateSessionOrderItems(buffer.toString(), "0");
		reGoodsorderDao.deletOrderCar(buffer.toString());//删除购物车
		return JsonResponseUtil.getJson(0x01,"请求成功");
	}

	@Override
	public Map<String, Object> getOrderList(HttpServletRequest request,
			HttpServletResponse response) {
		String xcx = request.getParameter("xcx");
		String userId = "";
		Integer pageIndex = 1;
		Integer status = 0;
		Integer wx_sellerId = null;
		Integer appVersion = null;
		String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
		if(xcx != null){
			userId = request.getParameter("userId");
			pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
			status = Integer.valueOf(request.getParameter("status"));
		}else{
			Parameter parameter = ParameterUtil.getParameter(request);
			
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			userId = parameter.getUserId();
			pageIndex = parameter.getData().getInteger("pageIndex");
			status = parameter.getData().getInteger("status");
			wx_sellerId=parameter.getData().getInteger("wx_sellerId");
			appVersion = StringUtil.getAppVersion(parameter.getAppVersion());
		}
		
		QueryModel queryModel = new QueryModel();
		int count=0;
		List<ReGoodsorder> order =new ArrayList<>();
		Object[] obj=null;
		obj = reGoodsorderDao.findOrderListByStatus(Integer.parseInt(userId), status,pageIndex,8, appVersion,wx_sellerId);
		
		if(obj!=null){
			count=Integer.parseInt(obj[0].toString());
			order= (List<ReGoodsorder>) obj[1];
			
		}
		
		Map<String,Object> dataMap = new HashMap<>();
		List<Map<String,Object>> orderList = new ArrayList<>();
		Map<String,Object> orderMap = null;
		for(ReGoodsorder i :order){
			
			orderMap = new HashMap<>();
			queryModel.clearQuery();
			queryModel.combPreEquals("order.id",i.getId(),"orderId");
			queryModel.combPreEquals("status",i.getStatus());
			queryModel.combPreEquals("user.id",Integer.parseInt(userId),"userId");
			queryModel.combCondition("(isBack = "+ReGoodsorder.ke_tui_dan+" or  isBack = "+ReGoodsorder.bu_ke_tui_dan+")");
		//	queryModel.combPreNotEquals("mallClass", "ldm","mall_class");
			List<ReGoodsorderItem> itemlist = dateBaseDAO.findLists(ReGoodsorderItem.class, queryModel);
			List<Object> list = new ArrayList<>();
			//不返回没有子订单数据的主订单
			for (ReGoodsorderItem item : itemlist) {
				
				list.add(getItemMap(item, basePath));
			}
			if(list.size()>0){
				
			orderMap.put("orderItems", list);
			// 返回订单需要支付金额，红包，积分
			Map<String, Object> moneyMap = getOrderMoneyMap(i);
				double money = (double) moneyMap.get("money");// 需要支付的现金
				Integer score = (Integer) moneyMap.get("score");// 订单需要支付的积分
				double cashpoint = (double) moneyMap.get("cashpoint");// 订单需要支付的红包
				orderMap = getOrderMap(orderMap, i, i.getSeller(), basePath, money, score, cashpoint);
				orderList.add(orderMap);
			}
			
		}
		dataMap.put("dataList", orderList);
		dataMap.put("pageSize", count%8==0?count/8:(count/8+1));
		dataMap.put("pageIndex", pageIndex);
		dataMap.put("pageItemCount", 8);
		Map<String,Object> map = new HashMap<>();
		map.put("data", dataMap);
		map.put("status", 0x01);
		map.put("message", "请求成功");
		return map;
	}
	
	@Override
	public Map<String, Object> getOrder(HttpServletRequest request, HttpServletResponse response){
		String xcx = request.getParameter("xcx");
		String o = "";
		String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
		if(xcx != null){
			o = request.getParameter("orderId");
		}else{
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			o =parameter.getData().getString("orderId");
			
		}
		if(o.length()>10){
			o=o.substring(5, o.length()-5);
		}
		Integer orderId= Integer.parseInt(o);
		ReGoodsorder order = reGoodsorderDao.findById(orderId);
		
		Map<String,Object> orderMap = new HashMap<>();
		List<Object> detailsList = new ArrayList<>();
		
		QueryModel queryModel = new QueryModel();	
		queryModel.clearQuery();
		queryModel.combCondition("(isBack = "+ReGoodsorder.ke_tui_dan+" or  isBack = "+ReGoodsorder.bu_ke_tui_dan+")");
		queryModel.combPreEquals("order.id", orderId,"orderId");
		List<ReGoodsorderItem> itemList  =  dateBaseDAO.findLists(ReGoodsorderItem.class, queryModel);
		for(ReGoodsorderItem item:itemList){
			detailsList.add(getItemMap(item, basePath));
		}
		orderMap.put("orderItems", detailsList);
		
		//返回订单需要支付金额，红包，积分
		Map<String,Object> moneyMap = getOrderMoneyMap(order);
		double money = (double) moneyMap.get("money");//需要支付的现金
		Integer score = (Integer) moneyMap.get("score");//订单需要支付的积分
		double cashpoint = (double) moneyMap.get("cashpoint");//订单需要支付的红包
		orderMap = getOrderMap(orderMap,order,order.getSeller(),basePath,money,score,cashpoint);
		
		Map<String,Object> bigDataMap = new HashMap<>();
		bigDataMap.put("data", orderMap);
		bigDataMap.put("status", 0x01);
		bigDataMap.put("message", "请求成功");
		return bigDataMap;
	}

	
	
	@Override
	public Map<String, Object> cancelOrder(HttpServletRequest request,
			HttpServletResponse response) {
		String xcx = request.getParameter("xcx");
		String orderId ="";
		if(xcx != null){
			orderId = request.getParameter("orderId");
		}else{
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			
			orderId =parameter.getData().getString("orderId");
		}
		
		orderId= orderId.substring(5, orderId.length()-5);
		
		
		
		/*QueryModel queryModel = new QueryModel();
		queryModel.combPreEquals("order.id", orderId,"orderId");
		List<ReGoodsorderItem> itemList = dateBaseDAO.findLists(ReGoodsorderItem.class, queryModel);
		OrderPayServiceImpl orderPay = new OrderPayServiceImpl();
		try {
			for(ReGoodsorderItem item:itemList){
			//减少销量
			reBaseGoodsDao.addGoodsSalesVolume(item.getMallClass()+item.getGoodsId(),-item.getGoodQuantity());
			//增加库存
			orderPay.updateGoodsStock(item, item.getGoodQuantity(), true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		reGoodsorderItemDao.deleteByOrderId(Integer.parseInt(orderId));
		reGoodsorderDao.deleteById(Integer.parseInt(orderId));
		return JsonResponseUtil.getJson(0x01,"请求成功");
	}

	//获取商家订单列表
	@Override
	public Map<String, Object> getSellerOrderList(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		QueryModel queryModel = new QueryModel();
		Integer pageIndex = parameter.getData().getInteger("pageIndex");
		String status = parameter.getData().getString("status");
		String userId = parameter.getUserId();
		String sellerId = parameter.getSellerId();
		int sId =0;
		if(StringUtils.isNotBlank(sellerId)){
			sId = Integer.parseInt(sellerId);
		}
		
		Seller seller =null;
		if(sId>0){
			seller =sellerDAO.findById(sId);
		}else{
			if(StringUtils.isNotBlank(userId)){
			queryModel.clearQuery();
			queryModel.combPreEquals("users.id", Integer.parseInt(userId),"userId");
			queryModel.combPreEquals("isvalid", true);
			 List<Seller>	sellerlist = (List<Seller>) dateBaseDAO.findList(Seller.class, queryModel);
			 if(sellerlist!=null && sellerlist.size()>0){
				 seller = sellerlist.get(0);
			 }
			}
		}
		if(seller==null){
			return JsonResponseUtil.getJson(-0x02,"无店铺信息，没有订单！"); 
		}
		queryModel.clearQuery();
		queryModel.combPreEquals("isValid", true);
		queryModel.combPreEquals("isHasItems", true);
		queryModel.combPreEquals("seller.id",seller.getId(),"sellerId");
		queryModel = getOrderModel(status,queryModel);
		int count = dateBaseDAO.findCount(ReGoodsorder.class, queryModel);
		List<ReGoodsorder> orderList = dateBaseDAO.findPageList(ReGoodsorder.class, queryModel, (pageIndex-1)*8, 8);
		List<Map<String,Object>> orderDataList = new ArrayList<>();
		Map<String,Object> orderMap = null;
		for(ReGoodsorder order : orderList){
			orderMap = new HashMap<>();
			queryModel.clearQuery();
			queryModel.combPreEquals("isValid", true);
			queryModel.combPreEquals("status",order.getStatus());
			queryModel.combPreEquals("order.id", order.getId(),"orderId");
			queryModel.combCondition("(isBack = "+ReGoodsorder.ke_tui_dan+" or  isBack = "+ReGoodsorder.bu_ke_tui_dan+")");
			
			List<ReGoodsorderItem> itemList = dateBaseDAO.findLists(ReGoodsorderItem.class, queryModel);	
			if(itemList.size()>0){
				List<Map<String,Object>> itemMapList = new ArrayList<>();
				
				orderMap.put("orderItems", itemMapList);
				//返回订单需要支付金额，红包，积分
				Map<String,Object> moneyMap = getOrderMoneyMap(order);
				double money = (double) moneyMap.get("money");//需要支付的现金
				Integer score = (Integer) moneyMap.get("score");//订单需要支付的积分
				double cashpoint = (double) moneyMap.get("cashpoint");//订单需要支付的红包
				
				for(ReGoodsorderItem item : itemList){
					if("ldm".equals(item.getMallClass())){
						if(item.getIsLock() != null && item.getIsLock() ==1){
							itemMapList.add(getItemMap(item, basePath));
						}
					}else{
						itemMapList.add(getItemMap(item, basePath));
					}
				}
				if(itemMapList != null && itemMapList.size()>0){
					
					orderMap = getOrderMap(orderMap,order,seller,basePath,money,score,cashpoint);
					orderDataList.add(orderMap);
				}
				
			}
		}
		Map<String,Object> dataMap = new HashMap<>();
		dataMap.put("dataList", orderDataList);
		dataMap.put("pageSize", orderDataList.size()%8==0?orderDataList.size()/8:(orderDataList.size()/8+1));
		dataMap.put("pageIndex", pageIndex);
		dataMap.put("pageItemCount", 8);
		Map<String,Object> bigDataMap = new HashMap<>();
		bigDataMap.put("data", dataMap);
		bigDataMap.put("status", 0x01);
		bigDataMap.put("message", "请求成功");
		
		return bigDataMap;
	}
	
	
	

	
	//商家确认兑换
	@Override
	public Map<String, Object> sellerConfirmExchange(
			HttpServletRequest request, HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		String  o =parameter.getData().getString("orderId");
		
		Integer orderId= Integer.parseInt(o.substring(5, o.length()-5));
		String code = parameter.getData().getString("exchangeCode");
		QueryModel queryModel = new QueryModel();
		String usersid= parameter.getUserId();
		Seller seller = null;
		String sellerId= parameter.getSellerId();
		if(StringUtils.isBlank(sellerId)){
		
			queryModel.clearQuery();
			queryModel.combEquals("isvalid",1);
			queryModel.combEquals("users.id",Integer.parseInt(usersid));
			
			List<Seller> sellerList = dateBaseDAO.findPageList(Seller.class, queryModel, 0 , 1);
			if(sellerList.size()>0){
				seller= sellerList.get(0);
			}
		}else{
			
			seller = sellerDao.findById(Integer.parseInt(sellerId));
		}
		
		if(seller==null){
			return JsonResponseUtil.getJson(-0x02, "您不是商家，不能确认兑换");
		}
		
		
		queryModel.clearQuery();
		queryModel.combPreEquals("id", orderId);
		ReGoodsorder order = (ReGoodsorder) dateBaseDAO.findOne(ReGoodsorder.class, queryModel);
		
		if(seller.getId()!=order.getSeller().getId()){
			return JsonResponseUtil.getJson(-0x02, "您不是该订单商家，不能确认兑换");
		}
		

		if(order.getStatus()>=ReGoodsorder.dai_ping_jia){
			return JsonResponseUtil.getJson(-0x02, "已兑换");
		}
		if(order.getCode().equals(code)){
			String re = comfirmMoney(order);
			if("-8".equals(re)){
				return JsonResponseUtil.getJson(-0xa8,"此订单已申请退单");
			}else if("-9".equals(re)){
				return JsonResponseUtil.getJson(-0xa9,"有订单子项处于退单审核状态，不可确认");
			}else{
				return JsonResponseUtil.getJson(0x01, "兑换成功！");
			}
			
		}
		
		return JsonResponseUtil.getJson(-0x02, "兑换码错误");
	}
	

	
	public String comfirmMoney(ReGoodsorder order){
		QueryModel queryModel = new QueryModel();

		if(!order.getIsHasItems()){
			return "-8";
		}else{
			queryModel.clearQuery();
			queryModel.combPreEquals("order.id",order.getId(),"orderId");
			List<ReGoodsorderItem> itemList = dateBaseDAO.findLists(ReGoodsorderItem.class, queryModel);
			for(ReGoodsorderItem item : itemList){
				if(item.getIsBack()==20 || item.getIsBack()==30 || item.getIsBack()==50 ){
					return "-9";
				}
				
				
					queryModel.clearQuery();
					queryModel.combPreEquals("relateId", item.getId());
					queryModel.combPreEquals("type", -1);
					List<CashmoneyRecord> moneyList =dateBaseDAO.findPageList(CashmoneyRecord.class, queryModel,0,Integer.MAX_VALUE);
					if(moneyList.size()>0){
						for(CashmoneyRecord cr :moneyList){
						cr.setRelateName(cr.getRemark()+",确认完成,收货时间"+DateUtil.formatDate("yyyy-MM-dd HH:mm:ss",new Date()));
						cr.setIsValid(true);
						cr.setType(1);
						Users user  =usersDao.findById(cr.getUsersByUserId().getId());
						double money =0d;
						if(user.getMoney()!=null){
							money=user.getMoney();
						}
						
						//-===============ZL=================================//
						double a = CalcUtil.add(money, cr.getMoney(), 2);
						user.setMoney(a);
						//user.setMoney(money+cr.getMoney());
						
						cashmoneyRecordDao.update(cr);
						usersDao.update(user);
						}
					}
					
					queryModel.clearQuery();
					queryModel.combPreEquals("orderItem.id", item.getId(),"orderItemId");
					queryModel.combPreEquals("type", -1);
					
					if(item.getMallClass().equals(ReBaseGoods.ScoreMall)){
						ReGoodsOfScoreMall scoreMall = reGoodsOfScoreMallDao.findById(item.getMallId());
						AdminUser adminUser = scoreMall.getSnapshotGoods().getSeller().getAdminUser();
						AdminUserScoreRecord scoreRecord=new AdminUserScoreRecord();
						scoreRecord.setAdminUser(adminUser);
						scoreRecord.setBeforeScore(adminUser.getScore());
						scoreRecord.setAfterScore(adminUser.getScore()+item.getPayScore());
						scoreRecord.setScore(item.getPayScore());
						scoreRecord.setSurplusScore(item.getPayScore());
						scoreRecord.setType(AdminUserScoreRecord.VENDITION);
						String  msg="销售产品"+item.getGoodName()+" 获得积分,用户支付积分"+item.getPayScore()+"(用户确认收货)";
						scoreRecord.setRemark(msg);
						scoreRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
						scoreRecord.setIsValid(true);
						adminUserScoreRecordDao.save(scoreRecord);
						Integer sc = adminUser.getScore()==null?0:adminUser.getScore();
						//adminUser.setScore(sc+item.getPayScore()); //积分不增加
						adminUserDao.update(adminUser);
					}
					
					
					List<AdminuserCashpointRecord> adminList =dateBaseDAO.findPageList(AdminuserCashpointRecord.class, queryModel,0,Integer.MAX_VALUE);
					if(adminList.size()>0){
						for(AdminuserCashpointRecord cr:adminList){
						//cr.setRemark("订单"+item.getOrder().getOrderCode()+"确认完成.收货时间"+DateUtil.formatDate("yyyy-MM-dd-HH-mm-ss", new Date()));
							cr.setRemark(cr.getRemark()+",确认完成,收货时间"+DateUtil.formatDate("yyyy-MM-dd HH:mm:ss",new Date()));	
							AdminUser au = adminUserDao.findById(cr.getAdminUser().getId());
							double money =au.getMoney()==null?0d:au.getMoney();
							double m = CalcUtil.add(money, cr.getCashpoint(), 2);
							
							au.setMoney(m);
							cr.setType(1);
							cr.setIsDeposit(6);
							adminuserCashpointRecordDAO.update(cr);
							adminUserDao.saveOrUpdate(au);
						}
					}
				}
				
				
			}
			
		
			order.setStatus(ReGoodsorder.dai_ping_jia);
			AdminUser au=order.getSeller().getAdminUser();
			reGoodsorderItemDao.updateStatusByParent(ReGoodsorder.dai_ping_jia, order.getId());
			if(au!=null){
				List<AdminUser> ulist = new ArrayList<AdminUser>();
				ulist.add(au);
				userSystemMessageService.saveMessageForAdmin("2","订单"+order.getOrderCode()+"已确认收货！", StringUtil.MESSAGE_DINGDAN, "订单信息", ulist,order.getId()+"", 0,1);
			
			}
			if(order.getUser().getUnionId() != null){
				
			String unionId = order.getUser().getUnionId();
			String param = "unionId="+unionId+"&status=40&orderSn="+order.getOrderCode();
			UrlUtil.sendGzhMsg(2, param);
			}
			
			
		
		
		return "1";
		
	}
	
	
	
	//商家确认发货
	
	@Override
	public Map<String, Object> sellerConfirmReceipt(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		
		String o = parameter.getData().getString("orderId");
		
		Integer orderId= Integer.parseInt(o.substring(5, o.length()-5));
		
		
		
		String sendGoodsAddress = parameter.getData().getString("sendGoodsAddress");
		String backGoodsAddress = parameter.getData().getString("backGoodsAddress");
		String expressName = parameter.getData().getString("expressName");
		String expressNumber = parameter.getData().getString("expressNumber");
		
		QueryModel queryModel = new QueryModel();
		queryModel.clearQuery();
		queryModel.combPreEquals("id", orderId);
		ReGoodsorder order = (ReGoodsorder) dateBaseDAO.findOne(ReGoodsorder.class, queryModel);
		order.setSellerAddress(sendGoodsAddress);
		order.setBackGoodsAddress(backGoodsAddress);
		order.setLogisticsCompay(expressName);
		order.setLogisticsCode(expressNumber);
		order.setStatus(ReGoodsorder.dai_shou_huo);
		order.setConfirmtime(new java.sql.Timestamp(getDateAfter(new Date(),7).getTime()));
		reGoodsorderItemDao.updateStatusByParent(ReGoodsorder.dai_shou_huo, orderId);
		//========================ZL================================//
		try{
		Users users = order.getUser();
		if (users!=null) {
			List<Users> ulist = new ArrayList<Users>();
			ulist.add(users);
			userSystemMessageService.saveMessage("1","订单"+order.getOrderCode()+"已发货！", StringUtil.MESSAGE_DINGDAN, "订单消息", ulist, String.valueOf(orderId),0d,1);
			if(users.getUnionId()!= null){
				
			String unionId = users.getUnionId();
			String param = "unionId="+unionId+"&status=30&createTime="+order.getCreateTime().getTime()/1000+"&orderSn="+order.getOrderCode()+"&addressSn="+expressNumber;
			UrlUtil.sendGzhMsg(2, param);
			}
			
		}
		
		}catch(Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		//=============================================================//
		return JsonResponseUtil.getJson(0x01, "发货成功");
	}
	
	
	
	public  Date getDateAfter(Date d, int day) {   
        Calendar now = Calendar.getInstance();   
        now.setTime(d);   
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);   
        return now.getTime();   
  }
	
	//商家确认订单
	@Override
	public Map<String, Object> sellerConfirmOrder(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		String o = parameter.getData().getString("orderId");
		o = o.substring(5,o.length()-5);
		Integer orderId = Integer.parseInt(o);
		reGoodsorderDao.updateStatus(ReGoodsorder.dai_fa_huo, orderId);
		reGoodsorderItemDao.updateStatusByParent(ReGoodsorder.dai_fa_huo, orderId);
		
		return JsonResponseUtil.getJson(0x01, "已确认订单");
	}
	//商家退单审核
	@Override
	public Map<String, Object> sellerBackOrderVerify(
			HttpServletRequest request, HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		Integer itemBackId = parameter.getData().getInteger("backOrderItemId");//退单Id
		//Double money = parameter.getData().getDouble("money");//审核通过退款金额
		String replyContent = parameter.getData().getString("replyContent");//回复内容
		/*String date = parameter.getData().getString("date");*/
		Integer result = parameter.getData().getInteger("result");//0：拒绝退单,1：接受退单
		
		QueryModel queryModel = new QueryModel();
		queryModel.clearQuery();
		queryModel.combPreEquals("id", itemBackId);
		ReBackOrder backOrder = (ReBackOrder) dateBaseDAO.findOne(ReBackOrder.class, queryModel);
		backOrder.setCheckMessage(replyContent);
		backOrder.setAuditTime(new Timestamp(System.currentTimeMillis()));
		
		queryModel.clearQuery();
		queryModel.combPreEquals("id", backOrder.getOrderItem().getId());
		ReGoodsorderItem item = (ReGoodsorderItem) dateBaseDAO.findOne(ReGoodsorderItem.class, queryModel);
		Double money =item.getPayPrice();
		if(result==(Integer)10){
			backOrder.setBackstate(ReBackOrder.BACKSTATE_butongguo);//-15 不通过
			item.setIsBack(ReGoodsorder.ke_tui_dan);// 审核不通过可退单
			ReGoodsorder goodsorder = reGoodsorderDao.findById(item.getOrder().getId());
			goodsorder.setIsHasItems(true);
			goodsorder.setStatus(backOrder.getExOrderStatus());//审核不通过，订单修改成申请退单前的状态
			
			//goodsorder.setPayPrice(goodsorder.getPayPrice()+item.getPayPrice());
			goodsorder.setPayPrice(CalcUtil.add(goodsorder.getPayPrice(), item.getPayPrice(), 2));
			
			queryModel.clearQuery();
			queryModel.combPreEquals("isValid", true);
			queryModel.combPreEquals("relateId", item.getId(), "relateId");
			List<CashmoneyRecord> recordList = dateBaseDAO.findLists(CashmoneyRecord.class, queryModel);
			if (recordList!=null&&recordList.size()>0) {
				StringBuffer sb = new StringBuffer();
				for(int i=0;i<recordList.size();i++){
					if(i==(recordList.size()-1)){
						sb.append(recordList.get(i).getRelateId().toString());
					}else{
						sb.append(recordList.get(i).getRelateId().toString()+",");
					}
				}
				dateBaseDAO.updateByHQL("UPDATE UsersMoneyRecord SET IsValid="+true+" where relateId in("+sb.toString()+")");
				
			}
			
			if(item.getReGoodsOfSellerMall()!=null && item.getReGoodsOfSellerMall().getIsSendScore()!=null && item.getReGoodsOfSellerMall().getSendScoreNum()!=null){
				if(item.getReGoodsOfSellerMall().getIsSendScore() && item.getReGoodsOfSellerMall().getSendScoreNum()>0){
					Integer score = item.getReGoodsOfSellerMall().getSendScoreNum();
					Users user = item.getUser();
					
						int uscore = (int) CalcUtil.add(user.getScore(), score);
						user.setScore(uscore);
					    usersDao.saveOrUpdate(user);
					    Scorerecords scorerecords = new Scorerecords();
						scorerecords.setBeforeScore(user.getScore());
						scorerecords.setAfterScore(uscore);
						scorerecords.setIsvalid(true);
						scorerecords.setCreatetime(new Timestamp(System.currentTimeMillis()));
						scorerecords.setValidityTime(new Timestamp(DateUtil.addDay2Date(180, new Date()).getTime()));
						scorerecords.setScore(score);
						scorerecords.setScoretype("商品"+item.getReGoodsOfSellerMall().getSnapshotGoods().getName()+"退单审核不通过，返还赠送的"+score+"积分");
						scorerecords.setRemark("商品"+item.getReGoodsOfSellerMall().getSnapshotGoods().getName()+"退单审核不通过，返还赠送的"+score+"积分");
						scorerecords.setType(17);
						scorerecords.setAdminuserId(47);
						scorerecords.setForeignId(user.getId());
						scorerecords.setUsers(user);
						scorerecordsDao.save(scorerecords);
				}
			}
			
			
			
			
			return JsonResponseUtil.getJson(0x01, "审核不通过");
		}
		if(result==(Integer)30){//同意退单
			
			if(item.getReGoodsOfSellerMall()!=null && item.getReGoodsOfSellerMall().getIsSendScore()!=null && item.getReGoodsOfSellerMall().getSendScoreNum()!=null){
				if(item.getReGoodsOfSellerMall().getIsSendScore() && item.getReGoodsOfSellerMall().getSendScoreNum()>0){
					Integer score = item.getReGoodsOfSellerMall().getSendScoreNum();
					Users user = item.getUser();
					
					List<AdminUserScoreRecord> ausrlist= adminUserScoreRecordDao.findByPropertyIsValid("item.id", item.getId());
					if(ausrlist!=null && ausrlist.size()>0){
						for(AdminUserScoreRecord ausr :ausrlist){
							AdminUser adminUser = ausr.getAdminUser();
							
							
							adminUser.setScore((int)CalcUtil.add(adminUser.getScore(), score));		
							adminUserDao.saveOrUpdate(ausr.getAdminUser());
							AdminUserScoreRecord scoreRecord2 = new AdminUserScoreRecord();
							scoreRecord2.setAdminUser(adminUser);
							scoreRecord2.setBeforeScore(adminUser.getScore());
							scoreRecord2.setAfterScore(score);
							scoreRecord2.setSurplusScore(score);
							scoreRecord2.setCreateTime(new Timestamp(System.currentTimeMillis()));
							scoreRecord2.setScore(score);
							scoreRecord2.setIsValid(true);
							scoreRecord2.setType(17);
							scoreRecord2.setFromAdminUser(adminUser);
							scoreRecord2.setItem(item);
							scoreRecord2.setRemark(user.getName()+"购买的积分赠送商品"+item.getReGoodsOfSellerMall().getSnapshotGoods().getName()+"退单已通过,返还所送"+score+"积分");
							adminUserScoreRecordDao.save(scoreRecord2);
						}
						
					}
				}
			}
			
			
			
			
			
			
			backOrder.setBackmoney(money==null?0.0:money);
			//========================ZL============================//
			if (money==0) {
				backOrder.setBackstate(ReBackOrder.BACKSTATE_yizhifu);
				if (backOrder.getOrderItem().getPayScore()>0) {
					Users users = backOrder.getOrderItem().getOrder().getUser();
					if (users!=null) {
						scorerecordsDao.updateRecord(users, backOrder.getOrderItem().getPayScore(), ScorerecordsDAO.BUY);
						users.setScore(users.getScore()==null?0:users.getScore()+backOrder.getOrderItem().getPayScore());
						usersDao.update(users);
					}
					
				}
			}else{
				backOrder.setBackstate(ReBackOrder.BACKSTATE_yishenhe);
				item.setIsBack(ReGoodsorder.tong_yi_tui_dan);
			}
			//===================================================//
			reGoodsorderItemDao.update(item);
			//如果主订单没有不可退单记录，那就将主订单isValid=false
			updatMoneyRecord(item);
			queryModel.clearQuery();
			queryModel.combCondition("(isBack = "+ReGoodsorder.ke_tui_dan+" or isBack = "+ReGoodsorder.bu_ke_tui_dan+")");
			int count = dateBaseDAO.findCount(ReGoodsorderItem.class, queryModel);
			ReGoodsorder order = item.getOrder();
			if(count <1){	
				order.setIsHasItems(false);
			}else{
				order.setIsHasItems(true);
			}
			//处理商家，用户，平台 分佣记录
			return JsonResponseUtil.getJson(0x01, "已审核退单,等待总部确认");
		}
		if(result==(Integer)40){//不可退单
			backOrder.setBackstate(ReBackOrder.BACKSTATE_buketuidan);
			item.setIsBack(ReGoodsorder.bu_ke_tui_dan);	
			ReGoodsorder goodsorder = reGoodsorderDao.findById(item.getOrder().getId());
			goodsorder.setIsHasItems(true);
			goodsorder.setStatus(backOrder.getExOrderStatus());//不可退单，订单修改成申请退单前的状态
			//goodsorder.setPayPrice(goodsorder.getPayPrice()+item.getPayPrice());
			goodsorder.setPayPrice(CalcUtil.add(goodsorder.getPayPrice(), item.getPayPrice(), 2));
			
			queryModel.clearQuery();
			queryModel.combPreEquals("isValid", true);
			queryModel.combPreEquals("relateId", item.getId(), "relateId");
			List<CashmoneyRecord> recordList = dateBaseDAO.findLists(CashmoneyRecord.class, queryModel);
			if (recordList!=null&&recordList.size()>0) {
				StringBuffer sb = new StringBuffer();
				for(int i=0;i<recordList.size();i++){
					if(i==(recordList.size()-1)){
						sb.append(recordList.get(i).getRelateId().toString());
					}else{
						sb.append(recordList.get(i).getRelateId().toString()+",");
					}
				}
				dateBaseDAO.updateByHQL("UPDATE CashmoneyRecord SET IsValid="+true+" where relateId in("+sb.toString()+")");
				
			}
			
			if(item.getReGoodsOfSellerMall()!=null && item.getReGoodsOfSellerMall().getIsSendScore()!=null && item.getReGoodsOfSellerMall().getSendScoreNum()!=null){
				if(item.getReGoodsOfSellerMall().getIsSendScore() && item.getReGoodsOfSellerMall().getSendScoreNum()>0){
					Integer score = item.getReGoodsOfSellerMall().getSendScoreNum();
					Users user = item.getUser();
					
						int uscore = (int) CalcUtil.add(user.getScore(), score);
						user.setScore(uscore);
					    usersDao.saveOrUpdate(user);
					    Scorerecords scorerecords = new Scorerecords();
						scorerecords.setBeforeScore(user.getScore());
						scorerecords.setAfterScore(uscore);
						scorerecords.setIsvalid(true);
						scorerecords.setCreatetime(new Timestamp(System.currentTimeMillis()));
						scorerecords.setValidityTime(new Timestamp(DateUtil.addDay2Date(180, new Date()).getTime()));
						scorerecords.setScore(score);
						scorerecords.setScoretype("商品"+item.getReGoodsOfSellerMall().getSnapshotGoods().getName()+"退单审核不通过，返还赠送的"+score+"积分");
						scorerecords.setRemark("商品"+item.getReGoodsOfSellerMall().getSnapshotGoods().getName()+"退单审核不通过，返还赠送的"+score+"积分");
						scorerecords.setType(17);
						scorerecords.setAdminuserId(47);
						scorerecords.setForeignId(user.getId());
						scorerecords.setUsers(user);
						scorerecordsDao.save(scorerecords);
				}
			}
			
			
			return JsonResponseUtil.getJson(0x01, "已拒绝退单");
		}
		
		//==============================ZL=============================//
		Users users = backOrder.getUser();
		if (users!=null) {
			List<Users> ulist = new ArrayList<Users>();
			ulist.add(users);
			if (result==(Integer)10) {
				userSystemMessageService.saveMessage("1","订单"+backOrder.getBackCode()+"不通过！", StringUtil.MESSAGE_DINGDAN, "退单信息", ulist, String.valueOf(itemBackId),0d,1);
			}else if(result==(Integer)40){
				userSystemMessageService.saveMessage("1","订单"+backOrder.getBackCode()+"不可退单！", StringUtil.MESSAGE_DINGDAN, "退单信息", ulist, String.valueOf(itemBackId),0d,1);
			}
		}
		//==================================================================//
		return JsonResponseUtil.getJson(-0x02, "退单失败");
	}
	
	private void updatMoneyRecord(ReGoodsorderItem item){
		cashmoneyRecordDao.updateMoneyById(item.getId());//删除分佣
		adminuserCashpointRecordDAO.updateMoneyById(item.getId());//删除分佣
	}
	
	
	
	
	//获取商家的退单列表
	@Override
	public Map<String, Object> getSellerBackOrderList(HttpServletRequest request, HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		Integer pageIndex = parameter.getData().getInteger("pageIndex");
		Integer backType = parameter.getData().getInteger("backType")==null?-1:parameter.getData().getInteger("backType");
		String userId = parameter.getUserId();
		QueryModel queryModel = new QueryModel();
		int sId=	parameter.getData().getIntValue("sellerId");
		Seller seller =null;
		if(sId>0){
			seller =sellerDAO.findById(sId);
		}else{
			queryModel.clearQuery();
			queryModel.combPreEquals("users.id", Integer.parseInt(userId),"userId");
			queryModel.combPreEquals("isvalid", true);
		 List<Seller>	sellerlist = (List<Seller>) dateBaseDAO.findList(Seller.class, queryModel);
		 if(sellerlist!=null && sellerlist.size()>0){
			 seller = sellerlist.get(0);
		 }
		}     
		
		if(seller==null){
			return JsonResponseUtil.getJson(-0x02,"该粉丝没有店铺账号"); 
		}
		
		queryModel.clearQuery();
		queryModel.combPreEquals("seller.id", seller.getId(),"sellerId");
		queryModel.combPreEquals("isValid", true);

		if(backType!=-1){
			if(backType==20){  //20和0  == 前端的 待支付
				queryModel.combCondition("(backstate="+backType+"  or backstate="+0+")");
			}else if(backType==30){
				queryModel.combCondition("(backstate="+backType+"  or backstate="+40+")");
			}else{
				queryModel.combPreEquals("backstate", backType);
			}
		}else{
			queryModel.combPreEquals("backstate", ReBackOrder.BACKSTATE_daishenhe);
		}
		
		
		int count = dateBaseDAO.findCount(ReBackOrder.class, queryModel);
		int A = (int) CalcUtil.sub(pageIndex, 1);
		List<ReBackOrder>  backList = dateBaseDAO.findPageList(ReBackOrder.class, queryModel, (int)CalcUtil.mul(A, 16), 16);
		//List<ReBackOrder>  backList = dateBaseDAO.findPageList(ReBackOrder.class, queryModel, (pageIndex-1)*16, 16);
		List<Map<String, Object>> backDataList = new ArrayList<>();
		Map<String, Object> backMap = null;
		for(ReBackOrder back : backList){
			backMap = new HashMap<>();
			//退单ID
			backMap.put("backOrderItemId", back.getId());
			//申请退单的订单项
			backMap.put("backOrderItem", getItemMap(back.getOrderItem(), basePath));
			//订单需要支付的现金
			backMap.put("money",back.getOrderItem().getPayPrice());
			//订单需要支付的积分
			backMap.put("score",back.getOrderItem().getPayScore());
			//订单需要支付的红包
			backMap.put("cashpoint",back.getOrderItem().getPayCashpoint());
			//订单实际支付金额
			backMap.put("realityMoney",back.getOrderItem().getPayPrice());
			//退单状态
			Map<String, Object> orderStatusMap = new HashMap<>();
			orderStatusMap.put("name",back.getStatusName(backType));
			orderStatusMap.put("statusId", back.getBackstate());
			backMap.put("backOrderStatus", orderStatusMap);
			
			Map<String,Object> userMap = new HashMap<>();
			Users user = back.getOrderItem().getUser();
			userMap.put("userId",user.getId() );
			userMap.put("name", user.getName());
			userMap.put("phone", user.getPhone());
			backMap.put("user", userMap);
			//订单商家信息
			Map<String, Object> sellerMap = new HashMap<>();
			sellerMap.put("sellerId", seller.getId());
			sellerMap.put("sellerName", seller.getName());
			sellerMap.put("sellerIcon", basePath+seller.getHeadImg());
			sellerMap.put("sellerAddress", seller.getAddress());
			sellerMap.put("sellerPhone", seller.getPhone());
			backMap.put("seller", sellerMap);
			//退单信息
			Map<String,Object> backOrderInfoMap = new HashMap<>();
			backOrderInfoMap.put("backOrderItemId", back.getId());
			if(back.getPayScore()==0){
				backOrderInfoMap.put("score", back.getPayScore());
			}else{
				backOrderInfoMap.put("money", back.getPaymoney());
			}

			backOrderInfoMap.put("content",back.getReason());
			backOrderInfoMap.put("data",DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date(back.getCreateTime().getTime())));
			List<String> imgList = new ArrayList<>();
			for(String img :  back.getImgList() ){
				imgList.add(basePath+img);
			}
			backOrderInfoMap.put("images",imgList);
			backOrderInfoMap.put("drawbackWay", back.getBacktype());
			backMap.put("backOrderInfo", backOrderInfoMap);
			//审核信息
			Map<String, Object> backOrderVerifyMap = new HashMap<>();
			backOrderVerifyMap.put("backOrderItemId", back.getId());
			backOrderVerifyMap.put("replyContent", back.getCheckMessage());
			Long auditLong = back.getAuditTime()==null?null:back.getAuditTime().getTime();
			if(auditLong!=null)
			backOrderVerifyMap.put("date", DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date(auditLong)));
			//backOrderVerifyMap.put("result", back.getReply());
			backMap.put("backOrderVerify", backOrderVerifyMap);
			//退款时间
			backMap.put("drawbackDate",null);
			//订单号
			backMap.put("orderNumber", back.getOrderItem().getOrder().getOrderCode());
			//订单创建时间
			backMap.put("orderDate", DateUtil.formatDate("yyyy-MM-dd HH:mm:ss",back.getOrderItem().getOrder().getCreateTime()));
			//订单费用明细
			Map<String, Object> detailMap = null;
			List<Map<String, Object>> detailList = new ArrayList<>();
			detailMap = new HashMap<>();
			detailMap.put("name", "红包抵扣");
			detailMap.put("price", back.getOrderItem().getOrder().getPayCashpoint());
			detailList.add(detailMap);
			detailMap = new HashMap<>();
			detailMap.put("name","返现");
			detailMap.put("price", back.getOrderItem().getOrder().getCashBack());
			detailList.add(detailMap);
			detailMap = new HashMap<>();
			detailMap.put("name", "运费");
			detailMap.put("price", back.getOrderItem().getOrder().getLogisticsType());
			detailList.add(detailMap);  
			backMap.put("details", detailList);
			//订单状态
			Map<String,Object> statusMap = new HashMap<>();
			statusMap.put("name", getStatusName(back.getOrderItem().getOrder().getStatus()));
			statusMap.put("statusId",back.getOrderItem().getOrder().getStatus() );
			backMap.put("status", statusMap);
			//兑换码
			backMap.put("exchangeCode", back.getOrderItem().getOrder().getCode());
			//收货人姓名
			backMap.put("username", back.getOrderItem().getOrder().getRealname());
			//收货人电话
			backMap.put("phone", back.getOrderItem().getOrder().getPhone());
			//收货人地址
			backMap.put("address", back.getOrderItem().getOrder().getAddress());
			//快递公司
			backMap.put("expressName", back.getOrderItem().getOrder().getLogisticsCompay());
			//快递单号
			backMap.put("expressNumber", back.getOrderItem().getOrder().getLogisticsCode());
			backDataList.add(backMap);
		}
		Map<String,Object> dataMap = new HashMap<>();
		int a =(int) CalcUtil.div(count, 16, 2);
		int b = (int) CalcUtil.add(a, 1);
		dataMap.put("pageSize", count%16==0?a:b);
		//dataMap.put("pageSize", count%16==0?count/16:(count/16+1));
		dataMap.put("pageIndex", pageIndex);
		dataMap.put("pageItemCount", 16);
		dataMap.put("dataList", backDataList);
		Map<String,Object> bigDataMap = new HashMap<>();
		bigDataMap.put("status", 0x01);
		bigDataMap.put("message", "请求成功");
		bigDataMap.put("data", dataMap);
		return bigDataMap;
	}
//============================================================================
	//返回主订单需要支付金额，红包，积分集合
	private Map<String,Object> getOrderMoneyMap(ReGoodsorder reGoodsorder){
		Map<String,Object> map = new HashMap<>();
		double money = 0d;//需要支付的现金
		Integer score = 0;//订单需要支付的积分
		double cashpoint = 0d;//订单需要支付的红包
		QueryModel queryModel = new QueryModel();
		queryModel.clearQuery();
		queryModel.combPreEquals("order.id",reGoodsorder.getId(),"orderId");
		queryModel.combPreEquals("isValid",true);
		queryModel.combCondition("(isBack = "+ReGoodsorder.ke_tui_dan+" or  isBack = "+ReGoodsorder.bu_ke_tui_dan+")");
		List<ReGoodsorderItem> itemList = dateBaseDAO.findLists(ReGoodsorderItem.class, queryModel);
		for(ReGoodsorderItem item : itemList){
				money = CalcUtil.add(money, item.getPayPrice(), 2);
				score = (int) CalcUtil.add(score, item.getPayScore(), 2);
				cashpoint = CalcUtil.add(cashpoint, item.getPayCashpoint(), 2);
		}
		map.put("money", money);
		map.put("score", score);
		map.put("cashpoint", cashpoint);
		return map;
	}
	
	
	
	private ReGoodsorderItem getitem(ReShoppingCar r,ReGoodsorder goodsorder,Timestamp nowTime){
		
		ReGoodsorderItem item = new  ReGoodsorderItem();
		item.setUser(r.getUser());
		item.setCreateTime(nowTime);
		item.setIsValid(true);
		item.setOrder(goodsorder);//主订单Id
		item.setGoodsId(r.getGoodsId());//唯一商品id
		item.setPreferential("");//优惠信息
		item.setGoodName(r.getGoodsName());//商品名
		item.setGoodImg(r.getGoodsImage());//商品封面	
		item.setGoodsStandardIds(r.getGoodsStandardIds());//商品规格
		item.setGoodsStandardString(r.getGoodsStandardString());
		item.setGoodPrice(r.getGoodsPrice());//商品现金
		item.setGoodScore(r.getScore());//商品积分
		item.setGoodCashpoint(r.getRedPaper());//商品红包
		item.setGoodQuantity(r.getGoodsQuantity());//商品数量
		item.setPayPrice(CalcUtil.mul(r.getGoodsPrice(), r.getGoodsQuantity(), 2));//订单现金
		//item.setPayScore(r.getScore()*r.getGoodsQuantity());//订单积分
		item.setPayScore((int)CalcUtil.mul(r.getScore(), r.getGoodsQuantity(), 2));//订单积分
		item.setPayCashpoint(CalcUtil.mul(r.getRedPaper(), r.getGoodsQuantity(), 2));//订单红包
		item.setMallId(r.getGoodsId());//模块id
		item.setMallClass(r.getGoodsOrder().substring(0,3));//模块
		item.setStatus(ReGoodsorder.huan_cun);//订单状态
		item.setIsBack(ReGoodsorder.ke_tui_dan);//退单状态
		item.setLogisticsType(r.getLoginsticsType());//配送方式
		item.setLogisticsPrice(r.getLogisticsPrice());//邮费
		item.setCar(r);//购物车id
		item.setGoodPic("");
		item.setStandardString(JSONObject.parseObject(r.getGoodsStandardString()));
		item.setTime(DateUtil.formatDate(item.getCreateTime()));
		
		if("ldm".equals(r.getGoodsOrder().substring(0,3))){ //属于游戏模块的  添加有的模块的 类别 
			ReGoodsOfLockMall lockMall = reGoodsOfLockMallDao.findById(r.getGoodsId());
			item.setGameType(lockMall.getGameType());
			
			
		}
		
		
		
		//区别新老商品
		ReGoodsOfSellerMall mall=null;
		ReBaseGoods good = (ReBaseGoods)analyzeMallUtil.getMall(item.getMallClass()+item.getGoodsId());
		if(good.getReGoodsOfSellerMall()!=null){
			mall=good.getReGoodsOfSellerMall();
		}
		item.setReGoodsOfSellerMall(mall);  
		
		return item;
	}
	// 粉丝确认收货
	@Override
	public Map<String, Object> confirmReceipt(HttpServletRequest request){
		String xcx = request.getParameter("xcx");
		String o = "";
		if(xcx != null){
			o = request.getParameter("orderId");
		}else{
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			o =parameter.getData().getString("orderId");
		}
		
		UrlUtil uu =new UrlUtil();
		try{
			Integer orderId= Integer.parseInt( o.substring(5, o.length()-5));
			ReGoodsorder order = reGoodsorderDao.findById(orderId);
			if(order.getStatus()<ReGoodsorder.dai_shou_huo){
				return JsonResponseUtil.getJson(-0xa7,"该订单商家未发货，不可确认");
			}
			
			if(!order.getIsHasItems()){
				return JsonResponseUtil.getJson(-0xa8,"此订单已申请退单");
			}else{
				String re = comfirmMoney(order);
				if("-8".equals(re)){
					return JsonResponseUtil.getJson(-0xa8,"此订单已申请退单");
				}else if("-9".equals(re)){
					return JsonResponseUtil.getJson(-0xa9,"有订单子项处于退单审核状态，不可确认");
				}
					
			}
			
			

			return JsonResponseUtil.getJson(0x01,"确认成功");
		}catch(Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return JsonResponseUtil.getJson(-0x01,"确认失败！");
		}
		
	}

	
	
	/**
	 * 返回主订单map
	 * @param orderMap  	主订单map
	 * @param reGoodsorder  主订单
	 * @param seller 		商家
	 * @param basePath
	 * @param money			订单需要支付的现金
	 * @param score			订单需要支付的积分
	 * @param cashpoint		订单需要支付的红包
	 * @return
	 */
	public Map<String, Object> getOrderMap(Map<String, Object> orderMap,ReGoodsorder reGoodsorder,Seller seller,String basePath, double money,Integer score,double cashpoint){
		DecimalFormat df = new DecimalFormat("#.00"); 
		Map<String, Object> sellerMap = new HashMap();
		Map<String, Object> detailMap = null;
		Map<String,Object> userMap = new HashMap<>();
		List<Map<String, Object>> detailList = new ArrayList<>();
		detailMap = new HashMap<>();
		
		Double deduction=0d;
		if(reGoodsorder.getIsTeam()){
			detailMap.put("name", "拼团优惠");
			deduction=reGoodsorder.getPayCashpoint();
			detailMap.put("price", String.valueOf(deduction));
		}else if(score>0){
			
			detailMap.put("name", "积分抵扣");
			reGoodsorder.getPayScore();
			detailMap.put("price", reGoodsorder.getPayScore().toString());
		}else{
			detailMap.put("name", "优惠券抵扣");
			deduction=reGoodsorder.getPayCashpoint();
			detailMap.put("price", String.valueOf(deduction));
		}
		
		
		detailList.add(detailMap);
		detailMap = new HashMap<>();
		detailMap.put("name","返现");
		detailMap.put("price", String.valueOf(reGoodsorder.getCashBack()));
		detailList.add(detailMap);
		detailMap = new HashMap<>();
		detailMap.put("name", "运费");
		detailMap.put("price", String.valueOf(reGoodsorder.getLogisticsType()));
		detailList.add(detailMap);  
		Users users = usersDao.findById(reGoodsorder.getUser().getId());
		userMap.put("userId", users.getId());
		userMap.put("username", users.getName());
		userMap.put("phone", users.getPhone());
		orderMap.put("user", userMap);
		
		sellerMap.put("sellerId", seller.getId());
		sellerMap.put("sellerName", seller.getName());
		sellerMap.put("sellerIcon", basePath+seller.getHeadImg());
		sellerMap.put("sellerAddress", seller.getAddress());
		sellerMap.put("sellerPhone", seller.getPhone());
		
		int a = CalcUtil.genDoubleRandom();
		int b = CalcUtil.genDoubleRandom();
		String oId = a+""+reGoodsorder.getId()+""+b;
		orderMap.put("orderId", oId);//主订单ID
		orderMap.put("orderNumber", reGoodsorder.getOrderCode());//订单号
		orderMap.put("seller", sellerMap);//商家信息
		orderMap.put("orderDate", DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date(reGoodsorder.getCreateTime().getTime())));//下定时间
		orderMap.put("details", detailList);
		orderMap.put("money",String.format("%.2f",CalcUtil.add(money, reGoodsorder.getPayCashpoint())));
		orderMap.put("cashpoint", cashpoint);
		orderMap.put("score", score);
		double realityMoney = CalcUtil.add(reGoodsorder.getPayPrice(), reGoodsorder.getLogisticsType());
		
		orderMap.put("realityMoney", realityMoney);
		orderMap.put("exchangeCode", reGoodsorder.getCode());
		Map<String,Object> orderStatusMap = new HashMap<>();
		orderStatusMap.put("name", getStatusName(reGoodsorder.getStatus()));
		orderStatusMap.put("statusId",reGoodsorder.getStatus());
		orderMap.put("status", orderStatusMap);
		
		//如果是拼单订单
		if(reGoodsorder.getIsTeam()){
			//拼团价
			//orderMap.put("teamPrice",getTeamPrice(reGoodsorder));  //要么拼团拿这个价 要么拿原始字段
			 //orderMap.put("realityMoney",getTeamPrice(reGoodsorder));
			if(reGoodsorder.getStatus()>5){
				//取于订单相关人员的头像及拼单成功时间 拼员的拼团支付时间为 拼单成功时间 拼主在第一个
				getOrderTeamStatusInfo(orderMap, reGoodsorder, basePath, true);
			}else if(reGoodsorder.getStatus()>0){ //待拼团
				getOrderTeamStatusInfo(orderMap, reGoodsorder, basePath, false);
			}
		}
		
		orderMap.put("isTeam", reGoodsorder.getIsTeam());//是否拼团订单
		//收货人姓名
		orderMap.put("username", reGoodsorder.getRealname());
		//收货人电话
		orderMap.put("phone", reGoodsorder.getPhone());
		//收货人地址
		orderMap.put("address", reGoodsorder.getAddress());
		//快递公司
		orderMap.put("expressName", reGoodsorder.getLogisticsCompay());
		//快递单号
		orderMap.put("expressNumber", reGoodsorder.getLogisticsCode());	
		return orderMap;
	}
	
	private Double getTeamPrice(ReGoodsorder reGoodsorder){
		Double teamPrice=0d;
		List<ReGoodsorderItem> items = reGoodsorderItemDao.findByParent(reGoodsorder.getId());
		if(!items.isEmpty()){
			ReGoodsOfTeamMall teamMall = reGoodsOfTeamMallDao.findById(items.get(0).getMallId());
			double A= teamMall.getDiscountPrice();
			//double B= items.get(0).getGoodPrice();
			//购买数量乘拼团优惠价
			teamPrice=CalcUtil.mul(items.get(0).getGoodQuantity(),A ,2); 
		}
		return teamPrice;
	}
	
	//获取订单状态名称
	private String getStatusName(int status){
		switch (status) {
		case -1:return "临时订单";//待支付
		case 0: return "待支付";//待支付
		case 10:return "待确认";//待确认
		case 20:return "待发货";//待发货
		case 25:return "待兑换";//待兑换
		case 30:return "待收货";//待收货
		case 40:return "待评价";//待评价
		case 50:return "已完成";//已完成
		case 70:return "已评价";//已评价
		default:
			break;
		}
		return "";
	}
	
	//商家状态
	private QueryModel getOrderModel(String status,QueryModel queryModel){
		switch (status) {
		case "0": queryModel.combPreEquals("status", 0); return queryModel;//待支付
		case "10":queryModel.combPreEquals("status", 10);return queryModel;//待确认
		case "20":queryModel.combPreEquals("status", 20);return queryModel;//待发货
		case "25":queryModel.combPreEquals("status", 25);
				  //queryModel.combCondition("(logistics= "+ReGoodsorder.shang_men_zi_qu+" or logistics= "+ReGoodsorder.dao_dian_xiao_fei+")");
				  return queryModel;//待兑换
		case "30":queryModel.combPreEquals("status", 30);
				 // queryModel.combCondition("(logistics= "+ReGoodsorder.bao_you+" or logistics= "+ReGoodsorder.bu_bao_you+")");
				  return queryModel;//待收货
		case "40":queryModel.combPreEquals("status", 40);return queryModel;//待评价
//		case "50":queryModel.combPreEquals("status", 50);return queryModel;//已完成
		case "50":queryModel.combCondition("status in (40,50)");return queryModel;//已完成
		case "70":queryModel.combPreEquals("status", 50);return queryModel;//已评价
		default:
			break;
		}
		return queryModel;
	}
	
	//计算总订单返现金额
	private void orderCashback(ReShoppingCar r,ReGoodsorder goodsorder){
		if(r.getGoodsOrder().contains("lsm")){
			ReGoodsOfLocalSpecialtyMall localMall = (ReGoodsOfLocalSpecialtyMall) analyzeMallUtil.getMall(r.getGoodsOrder());
			goodsorder.setCashBack(goodsorder.getCashBack()+CalcUtil.mul(r.getGoodsPrice(), localMall.getCashBack(), 2));
		}else if(r.getGoodsOrder().contains("nnm")){
			ReGoodsOfNineNineMall nnMall = (ReGoodsOfNineNineMall) analyzeMallUtil.getMall(r.getGoodsOrder());
			goodsorder.setCashBack(goodsorder.getCashBack()+CalcUtil.mul(r.getGoodsPrice(), nnMall.getCashBack(), 2));
		}
	}
	
	//返回item信息
	private Map<String,Object> getItemMap(ReGoodsorderItem item,String basePath){
		item.setBasePath(basePath);
		Map<String,Object> itemMap = new HashMap<>();
		itemMap.put("orderItemId",item.getId());//订单项id
		itemMap.put("number",item.getGoodQuantity());//数量
		itemMap.put("specString",item.getStyle());//前台用于显示的规格信息
		//订单需要支付的现金
		itemMap.put("score",item.getPayScore());//订单需要支付的积分
		
		if(item.getUserCoupons()!=null && item.getUserCoupons().getTicketprice()!=null){
			itemMap.put("cashpoint",item.getUserCoupons().getTicketprice());//优惠券金额
			//itemMap.put("money",CalcUtil.sub(item.getPayPrice(), item.getUserCoupons().getTicketprice()));
		}else{					
			itemMap.put("cashpoint",0);   
			//itemMap.put("money",item.getPayPrice());
		}
		itemMap.put("money",item.getPayPrice());   //优惠券金额在创建临时订单的时候就减过了
		
		if("mem".equals(item.getMallClass())){
			itemMap.put("isBack",false);
		}else{
			itemMap.put("isBack",item.getIsBack()==ReGoodsorder.bu_ke_tui_dan?false:true);//是否可退单
		}
		Map<String,Object> goodsMap = new HashMap<>();
		goodsMap.put("goodsId", item.getMallClass()+item.getGoodsId());
		goodsMap.put("mallType", ReBaseGoods.getMallTypeId(item.getMallClass()));
		goodsMap.put("name", item.getGoodName());
		goodsMap.put("price", item.getGoodPrice());
		goodsMap.put("pay_price", item.getPayPrice());
		goodsMap.put("score", item.getGoodScore());
		goodsMap.put("cashpoint", item.getGoodCashpoint());
		goodsMap.put("costPrice",item.getCar().getDisplayPrice());
		goodsMap.put("coverPic", item.getImgUrl());
		goodsMap.put("expressTactics", ReGoodsorder.getlogisticsName(item.getLogisticsType()));
		goodsMap.put("mallType",item.getMallClass());
		goodsMap.put("teamMsg", "还差一人拼单成功");
		itemMap.put("goods", goodsMap);//商品信息
		QueryModel queryModel = new QueryModel();
		queryModel.clearQuery();
		queryModel.combPreEquals("reGoodsorderItem.id",item.getId(),"itemId");
		List<OrderComment> orderComments = dateBaseDAO.findPageList(OrderComment.class, queryModel, 0, 1);
		if(orderComments.size()>0) {
			OrderComment orderComment = orderComments.get(0);
			InnerOrderComment comment = orderComment.new InnerOrderComment();
			comment.setCommentImages(basePath);
			itemMap.put("userComment",comment  );
		}
		
		return itemMap;
	}
	
	//返回item信息  重载 计算优惠券
		private Map<String,Object> getItemMap(ReGoodsorderItem item,String basePath,List<UserCoupons> userCouponsList,List<Integer> discountList){
			item.setBasePath(basePath);
			Map<String,Object> itemMap = new HashMap<>();
			itemMap.put("orderItemId",item.getId());//订单项id
			itemMap.put("number",item.getGoodQuantity());//数量
			itemMap.put("specString",item.getStyle());//前台用于显示的规格信息
			itemMap.put("money",item.getPayPrice());//订单需要支付的现金
			itemMap.put("score",item.getPayScore());//订单需要支付的积分
			itemMap.put("cashpoint",item.getPayCashpoint());//订单需要支付的红包
			if("mem".equals(item.getMallClass())){
				itemMap.put("isBack",false);
			}else{
				itemMap.put("isBack",item.getIsBack()==ReGoodsorder.bu_ke_tui_dan?false:true);//是否可退单
			}
			Map<String,Object> goodsMap = new HashMap<>();
			goodsMap.put("goodsId", item.getGoodsId());
			goodsMap.put("mallType", ReBaseGoods.getMallTypeId(item.getMallClass()));
			goodsMap.put("name", item.getGoodName());
			//goodsMap.put("price", item.getGoodPrice());  //转换成折扣后的价格
			goodsMap.put("score", item.getGoodScore());
			goodsMap.put("cashpoint", item.getGoodCashpoint());
			goodsMap.put("costPrice",item.getCar().getDisplayPrice());
			goodsMap.put("coverPic", item.getImgUrl());
			goodsMap.put("expressTactics", ReGoodsorder.getlogisticsName(item.getLogisticsType()));
			goodsMap.put("mallType",item.getMallClass());
			
			if(userCouponsList!=null&&userCouponsList.size()>0){
				for (int i = 0; i < userCouponsList.size(); i++) {
					Integer mallTypeId = ReBaseGoods.getMallTypeId(userCouponsList.get(i).getGoodsMall().substring(0,3));
					Integer goodId =Integer.parseInt( userCouponsList.get(i).getGoodsMall().substring(3));
					//找到对应优惠券的商品
					if(mallTypeId==ReBaseGoods.getMallTypeId(item.getMallClass())&&goodId.equals(item.getMallId())){
						itemMap.put("shopDiscount",  userCouponsList.get(i).getTicketprice()); //单个购物车优惠价
						goodsMap.put("price", CalcUtil.sub(item.getPayPrice(),userCouponsList.get(i).getTicketprice()));
						//totalDiscount=CalcUtil.add(totalDiscount,userCouponsList.get(i).getTicketprice(),2); //全部订单总优惠
						discountList.add(userCouponsList.get(i).getTicket().getId()); //优惠券id
						userCouponsList.remove(i);
						break;
					}
				}
			}
			itemMap.put("goods", goodsMap);//商品信息
			
			QueryModel queryModel = new QueryModel();
			queryModel.clearQuery();
			queryModel.combPreEquals("reGoodsorderItem.id",item.getId(),"itemId");
			List<OrderComment> orderComments = dateBaseDAO.findPageList(OrderComment.class, queryModel, 0, 1);
			if(orderComments.size()>0) {
				OrderComment orderComment = orderComments.get(0);
				InnerOrderComment comment = orderComment.new InnerOrderComment();
				comment.setCommentImages(basePath);
				itemMap.put("userComment",comment  );
			}
			
			return itemMap;
		}
	
	
	//返回退单对象
	private ReBackOrder getReBlackOrder(ReBackOrder reBackOrder,ReGoodsorderItem item){
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		reBackOrder = new ReBackOrder();
		reBackOrderDao.save(reBackOrder);
		reBackOrder.setBackCode(OrderUtil.getBlackOrderCode(reBackOrder.getId()));//
		reBackOrder.setIsValid(true);//
		reBackOrder.setCreateTime(nowTime);//
		reBackOrder.setUser(item.getUser());//粉丝Id
		reBackOrder.setOrderItem(item);//订单项id
		reBackOrder.setGoodid(item.getGoodsId());//唯一商品id
		reBackOrder.setGoodQuantity(item.getGoodQuantity());//退单商品数量
		//reBackOrder.setSellerCode(sellerCode);//商家编码
		reBackOrder.setMallClass(item.getMallClass());//模块
		reBackOrder.setMallId(item.getMallId());//模块id
		reBackOrder.setCheckMessage("");//审核意见
		reBackOrder.setSeller(item.getCar().getSeller());//商家id
		//reBackOrder.setBackmoney(item.getPayPrice());//退单金额
		reBackOrder.setPayCashpoint(item.getPayCashpoint());
		reBackOrder.setPayScore(item.getPayScore());
		reBackOrder.setPaymoney(item.getPayPrice());
		//reBackOrder.setPaymoney(paymoney);//实际支付金额
		//reBackOrder.setBacktype(backtype);//退款类型
		reBackOrder.setBackstate(ReBackOrder.BACKSTATE_daishenhe);//退单状态
		reBackOrder.setPaytype(item.getOrder().getPayType());//支付类型
		reBackOrder.setPayAccout(item.getOrder().getPayAccount());//支付账号
		reBackOrder.setAccountName(item.getOrder().getRealname());//支付账号真实姓名
		reBackOrder.setReason("");//退单原因
		reBackOrder.setImage(item.getGoodImg());//退单图片
		//reBackOrder.setType(type);//退款途径
		return reBackOrder;
	}
	
	/**
	 * 退款返还红包
	 * @param userCRId
	 */
	private void drawbackRedPaper(Integer orderId) {
		QueryModel qm = new QueryModel();
			
		//修改红包支付记录
		qm.clearQuery();
		qm.combPreEquals("relateId", orderId);
		qm.combPreEquals("relateBean", NrpOrderLog.RELATEBEAN_reGoodsorderItem);
		List<NrpOrderLog> nrpOrderLogList = dateBaseDAO.findLists(NrpOrderLog.class, qm);	
		NewRedPaperLog nrpl;
		Map<Integer,Integer> settingMap = new HashMap<Integer, Integer>();
			
		for(int i=0;i<nrpOrderLogList.size();i++){		
			//修改红包支付记录
			nrpOrderLogList.get(i).setStatus(NrpOrderLog.STATUS_DRAWBACK);
				
			//获取当前红包领取记录
			qm.clearQuery();
			qm.combPreEquals("id",nrpOrderLogList.get(i).getNrpl().getId());
			nrpl = dateBaseDAO.findLists(NewRedPaperLog.class, qm).get(0);			
			//判断当前红包是否有余额
			//if(nrpl.getMoney()==nrpl.getAvail()+nrpOrderLogList.get(i).getUserMoney()){
			if(nrpl.getMoney()==CalcUtil.add(nrpl.getAvail(), nrpOrderLogList.get(i).getUserMoney(), 2)){
				nrpl.setStatus(NewRedPaperLog.STATUS_NOTUSR);
			}else{			
				nrpl.setStatus(NewRedPaperLog.STATUS_SPLITPAY);
			}
			//nrpl.setAvail(nrpl.getAvail()+nrpOrderLogList.get(i).getUserMoney());
			nrpl.setAvail(CalcUtil.add(nrpl.getAvail(), nrpOrderLogList.get(i).getUserMoney(), 2));
			//获取红包附表记录
			qm.clearQuery();
			qm.combEquals("id", nrpl.getAddendum().getId());
			NewRedPaperAddendum addendum = dateBaseDAO.findLists(NewRedPaperAddendum.class, qm).get(0);
			if(settingMap.get(nrpl.getSetting().getId())==null){
				settingMap.put(nrpl.getSetting().getId(), 1);
			}else{
				settingMap.put(nrpl.getSetting().getId(), settingMap.get(nrpl.getSetting().getId())+1);
			}
			//addendum.setAvail(addendum.getAvail()+nrpOrderLogList.get(i).getUserMoney());
			addendum.setAvail(CalcUtil.add(addendum.getAvail(), nrpOrderLogList.get(i).getUserMoney(), 2));
			nrpOrderLogDao.update(nrpOrderLogList.get(i));
			nrplDao.update(nrpl);
			nrpaDao.update(addendum);					
			}
			for(Map.Entry<Integer, Integer> entry: settingMap.entrySet()){
				nrpsDao.updateAllNunUsed(entry.getKey(),entry.getValue(),"-");
			}

	}
	
	 public Integer getNumberOfAlreadyBuy(String goodsOrder,Integer userId){
		 int count = 0;
		 if(goodsOrder.length()>3){
			 QueryModel queryModel = new QueryModel();
			 queryModel.combPreEquals("user.id", userId,"userId");
			 queryModel.combPreEquals("isValid", true);
			 queryModel.combPreEquals("mallId", Integer.parseInt(goodsOrder.substring(3)));
			 queryModel.combPreEquals("mallClass", goodsOrder.substring(0,3));
			 queryModel.combCondition("status !="+ReGoodsorder.huan_cun);
			 queryModel.combCondition("(isBack ="+ReGoodsorder.ke_tui_dan+" or isBack ="+ReGoodsorder.bu_ke_tui_dan+")");
			 count = dateBaseDAO.findCount(ReGoodsorderItem.class, queryModel);
		 }
		 return count;
	 }
	 
	 public Map<String,Object> checkLimit(ReShoppingCar car,Integer userId,Boolean isTeam){
		 Map<String,Object> map = new HashMap<String, Object>();
		 map.put("status", 1);
		 map.put("message", "验证通过");
		 String goodsObject = car.getGoodsOrder();
		 if(!goodsObject.startsWith(ReBaseGoods.ScoreMall)
				 &&!goodsObject.startsWith(ReBaseGoods.SeckillMall)
				 &&!goodsObject.startsWith(ReBaseGoods.teamMall)
				 &&!goodsObject.startsWith(ReBaseGoods.lockMall)){
			 return map;
		 }
		 
		 ReBaseGoods goods = (ReBaseGoods)reGoodsOfBaseService.getMall(goodsObject);
		 Integer count = reGoodsorderItemDao.getQuantitySumByGoodsObject(goodsObject, userId,isTeam);
		 Integer limitCount = 0;
		 if(goodsObject.startsWith(ReBaseGoods.ScoreMall)){
			 ReGoodsOfScoreMall mall = (ReGoodsOfScoreMall)goods;
			 limitCount = mall.getCountLimit();
		 }else if(goodsObject.startsWith(ReBaseGoods.SeckillMall)){
			 ReGoodsOfSeckillMall mall = (ReGoodsOfSeckillMall)goods;
			 limitCount = mall.getSeckillCountLimit();
		 }else if(goodsObject.startsWith(ReBaseGoods.teamMall)){ 
			 	ReGoodsOfTeamMall mall=(ReGoodsOfTeamMall)goods;
			 	limitCount=mall.getRestrictNum();
		 }else if(goodsObject.startsWith(ReBaseGoods.lockMall)){ //判断是否能够购买
			 ReGoodsOfLockMall mall=(ReGoodsOfLockMall)goods;
			 if(mall.getGameType().getId()==265){ // 
				 if(mall.getOpenYards() != null){
					 map.put("status", -0x0b0);
					 map.put("message", "该商品已开奖,不能参与了!");
					 return map;
				 }
			 }else if(mall.getGameType().getId()==267){
				 try {
					 DateFormat df3 = DateFormat.getTimeInstance();//只显示出时分秒
					 long countTimeMil = DateUtil.addHour2Date(2, df3.parse(mall.getItemLastTime())).getTime() - System.currentTimeMillis();
					 if(countTimeMil < 0){
						 map.put("status", -0x0b0);
						 map.put("message", "该商品倒计时已经结束, 不能购买");
						 return map;
					 }
					 } catch (ParseException e) {
						 // TODO Auto-generated catch block
						 e.printStackTrace();
					 }
			 }
			 
		 }
		 limitCount = limitCount==null?0:limitCount;
		 if(limitCount>0&&(CalcUtil.add(count, car.getGoodsQuantity())) > limitCount){
			 //int limit = limitCount - count; 
			 int limit = (int) CalcUtil.sub(limitCount, count);
			 limit = limit<0?0:limit;
			 map.put("status", -0x0b0);
			 map.put("message", "该商品有限购，您还能兑换"+limit+"个");
			 return map;
		 }
		 return map;
	 }
	 
	 
	 //删除待支付的优惠价 
	 public void updateOrderUserCoupons(Integer userId,String goodsId){
		 QueryModel queryModel=new QueryModel();
			queryModel.clearQuery();
			queryModel.combPreEquals("goodsMall", goodsId,"goodsId");	
			queryModel.combEquals("isValid", 1);
			List<ReGoodsofextendmall> reGoodsofextendmallList= (List<ReGoodsofextendmall>) dateBaseDAO.findList(ReGoodsofextendmall.class,queryModel);
			if(reGoodsofextendmallList.size()>0){
				//找到订单表
				queryModel.clearQuery();
				queryModel.combEquals("isValid",1);
				queryModel.combPreEquals("user.id", userId,"userId");
				queryModel.combPreEquals("order.status", ReGoodsorder.dai_zhi_fu,"status");
				queryModel.combPreEquals("userCoupons.ticket.id",reGoodsofextendmallList.get(0).getId(),"userCouponsId");
				List<ReGoodsorderItem> goodsOrderItemList = (List<ReGoodsorderItem>) dateBaseDao.findList( ReGoodsorderItem.class,queryModel);
				if(goodsOrderItemList.size()>0){ //如果存在待支付 并有同一张优惠价
					ReGoodsorderItem reGoodsorderItem = goodsOrderItemList.get(0);
					reGoodsorderItem.setIsValid(false);
					reGoodsorderItem.setUserCoupons(null);
					ReGoodsorder order = reGoodsorderItem.getOrder();
					order.setIsValid(false);
					reGoodsorderItemDao.update(reGoodsorderItem);
					reGoodsorderDao.update(order);
				}   	    
		}			
	 }          
	 
	 

	/**
	 * 获取商品详情待拼单信息
	 */
	@Override
	public Map<String, Object> getGoodsTeamInfo(Integer userId,Integer goodsId,Map<String, Object> map,String basePath,Integer appVersion) {
		
			List<ReGoodsorder> orderList = reGoodsorderDao.getReGoodsOrderTeamInfo(goodsId,appVersion);
			List<Map<String, Object>> list=new ArrayList<>();
			if(orderList.size()>0){
				for (ReGoodsorder order : orderList) {
					if(userId!=null&&order.getUser().getId().intValue()==userId.intValue()){
						
						continue;
					}
					
					
					
					Map<String, Object> m=new HashMap<>();
					m.put("userHead",StringUtil.getUserDefaultHead(order.getUser(), basePath));
					//剩余人数
					m.put("surplusNum","还少1人"); //暂时先写死 以后拼团人数变化再说
					
					
					Date date=DateUtil.addHour2Date(ReGoodsorder.valid_Time,new Date(order.getTeamPayTime().getTime()));
					
					m.put("surplusTime", (date.getTime()-new Date().getTime()));
					m.put("OrderUserId",order.getUser().getId() );
					int a = CalcUtil.genDoubleRandom();
					int b = CalcUtil.genDoubleRandom(); 
					String oId = a+""+order.getId()+""+b;
					m.put("teamOrderId", oId);
					m.put("endTime",DateUtil.formatDate("yyyy-MM-dd HH:mm:ss",date));
					m.put("teamUserId", order.getUser().getId());
					m.put("userName", order.getUser().getRealname());
					
					if(date.getTime()-new Date().getTime()<5000){
						continue;
					}
					
					list.add(m);
					
				}
			}
			map.put("teamOrderList",list);
		return map; 
	}
	 
	/**
	 * 获取订单拼单成功信息  拼团有效时间好像会变  要写入数据库中
	 * status true拼团成功 false 待拼团
	 */
	public Map<String, Object> getOrderTeamStatusInfo(Map<String, Object> map,ReGoodsorder reGoodsorder,String basePath,Boolean status) {
		List<Map<String, Object>> list=new ArrayList<>();
			if(status){
				
				//订单详情中拼主和拼员的头像 拼成功时间
				List<ReGoodsorder> orderList = reGoodsorderDao.getOrderTeamStatusInfo(reGoodsorder);
				if(orderList.size()>0){ 
					for (ReGoodsorder order : orderList) {
						Map<String, Object> m=new HashMap<>();
						m.put("userHead",StringUtil.getUserDefaultHead(order.getUser(), basePath));
						m.put("teamSuccessTime",DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", orderList.get(orderList.size()-1).getTeamPayTime()));
						list.add(m);
					}
				}
			}else{
				Map<String, Object> m=new HashMap<>();
				//头像
				m.put("userHead", StringUtil.getUserDefaultHead(reGoodsorder.getUser(), basePath));
				//拼单剩余时间
				Date date=DateUtil.addHour2Date(ReGoodsorder.valid_Time,new Date(reGoodsorder.getTeamPayTime().getTime()));
				m.put("surplusTime",date.getTime()-reGoodsorder.getTeamPayTime().getTime());
				m.put("surplusNum", "还差1人");
				int a = CalcUtil.genDoubleRandom();
				int b = CalcUtil.genDoubleRandom(); 
				String oId = a+""+reGoodsorder.getId()+""+b;
				List<ReGoodsorderItem> items = reGoodsorderItemDao.findByPropertyIsValid("order.id", reGoodsorder.getId());
		       // String shareTargetUrl="http://seller.aixiaoping.com/Home/Coupon/goods?goodsMall="+items.get(0).getMallClass()+items.get(0).getMallId()+"&user_id="+reGoodsorder.getUser().getId();
				String shareTargetUrl="http://seller.aixiaoping.com/Share/Index/shareFriendBuy?goodsId="+items.get(0).getMallClass()+items.get(0).getMallId()+"&teamOrderId="+oId;
				m.put("shareTargetUrl",shareTargetUrl);
				m.put("shareTitle","拼团限时购    "+items.get(0).getPayPrice()+"元");
				JSONArray jsonArray=JSONArray.parseArray(items.get(0).getGoodImg());
				JSONObject jsonObj=jsonArray.getJSONObject(0);
				m.put("shareIcon", basePath+jsonObj.getString("imgUrl"));
				m.put("shareContent",items.get(0).getGoodName());
				m.put("endTime",DateUtil.formatDate("yyyy-MM-dd HH:mm:ss",date));
				
				//说明拼主订单已失效或退款
				if(items.get(0).getIsBack()!=ReGoodsorder.ke_tui_dan){
					m.put("isValid", false);
				}else{
					m.put("isValid", true);
				}
				
				if(date.getTime()-new Date().getTime()>5000){
					list.add(m);
				}
			}
			map.put("teamOrderList",list);
		return map;
	}
	
	
	/**
	 * 获得拼团成功分享页面数据
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getTeamShare(HttpServletRequest request, HttpServletResponse response){
	
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
		
		Map<String,Object> map=new HashMap<>();
		map.put("status", 1);
		map.put("message", "请求成功");
		String teamOrderId = parameter.getData().getString("teamOrderId");
		
		Integer orderId=Integer.parseInt(teamOrderId.substring(5,teamOrderId.length()-5));
		List<ReGoodsorder> orderList = reGoodsorderDao.findByPropertyIsValid("id", orderId);
		
		boolean status=false;
		
		if(orderList.size()>0){
			if(orderList.get(0).getStatus()>5){
				status=true;
			}
			getOrderTeamStatusInfo(map, orderList.get(0), basePath, status);
			List<Map<String, Object>>  list = (List<Map<String, Object>>) map.get("teamOrderList");
				
			if("WEB".equals(parameter.getOs())||"XCX".equals( parameter.getOs())){
				map.put("data", list);
			}else{
				if(list.size()>0){
					map.put("data", list.get(0));
					map.remove("teamOrderList");
				}else{
					map.put("data", "");
				}
			}
		}
		return map;
	}
	
}
