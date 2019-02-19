package com.axp.domain;

import java.sql.Timestamp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * ReGoodsorderItem entity. @author MyEclipse Persistence Tools
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class ReGoodsorderItem implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Boolean isValid;
	private Timestamp createTime;

	private Integer goodsId;//商品id；
	private String preferential;//优惠信息；
	private String goodName;//商品名称；
	private String goodImg;//商品图片；
	private String goodsStandardIds;//商品对应的规格明细id；
	private String goodsStandardString;//商品对应的规格明细的name；
	private Double goodPrice;//商品售价；
	private Integer goodScore;//商品积分；
	private Double goodCashpoint;//商品红包；
	private Integer goodQuantity;//商品数量；
	private Double payPrice;//需要支付的现金；
	private Integer payScore;//需要支付的积分；
	private Double payCashpoint;//需要支付的红包；
	private Integer mallId;//商城id；
	private String mallClass;//商城前缀（由三个字母组成）；
	private Integer status;//订单状态；（）
	private Integer isBack;//退单状态；（）
	private Integer logisticsType;//物流方式；
	private Double logisticsPrice;//物流费用；	
	private ReGoodsOfSellerMall reGoodsOfSellerMall; //所有商城都引用了 周边
	
	private ReShoppingCar car;//关联的购物车对象；
	private Users user;//关联的用户；
	private ReGoodsorder order;//关联的订单对象；
	private JSONObject StandardString;
	private Boolean isStockEnough = true; //是否有足够的库存
	private String time;
	private String imgUrl;
	private UserCoupons userCoupons;
	private Boolean isTeam;
	private Integer isLock;
	private Integer lotteryYards;
	private CommodityType gameType;//游戏类型 ldm
	private Integer isGame;

	
	public Integer getIsGame() {
		return isGame;
	}

	public void setIsGame(Integer isGame) {
		this.isGame = isGame;
	}

	public Integer getIsLock() {
		return isLock;
	}

	public void setIsLock(Integer isLock) {
		this.isLock = isLock;
	}

	public Integer getLotteryYards() {
		return lotteryYards;
	}

	public void setLotteryYards(Integer lotteryYards) {
		this.lotteryYards = lotteryYards;
	}

	public Boolean getIsTeam() {
		if(isTeam==null){
			isTeam=false;
		}
		return isTeam;
	}

	public void setIsTeam(Boolean isTeam) {
		this.isTeam = isTeam;
	}

	public ReGoodsOfSellerMall getReGoodsOfSellerMall() {
		return reGoodsOfSellerMall;
	}

	public void setReGoodsOfSellerMall(ReGoodsOfSellerMall reGoodsOfSellerMall) {
		this.reGoodsOfSellerMall = reGoodsOfSellerMall;
	}


	public UserCoupons getUserCoupons() {
		return userCoupons;
	}

	public void setUserCoupons(UserCoupons userCoupons) {
		this.userCoupons = userCoupons;
	}

	private String basePath = "";
	// Constructors

	/** default constructor */
	public ReGoodsorderItem() {
	}

	// Property accessors
	@JsonProperty("orderItemId")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	@JsonIgnore
	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
	@JsonIgnore
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Boolean getIsValid() {
		return this.isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
	@JsonIgnore
	public ReGoodsorder getOrder() {
		return order;
	}

	
	public void setOrder(ReGoodsorder order) {
		this.order = order;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public String getPreferential() {
		return preferential;
	}

	public void setPreferential(String preferential) {
		this.preferential = preferential;
	}

	public String getGoodName() {
		return this.goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	@JsonIgnore
	public String getGoodImg() {
		return this.goodImg;
	}
	
	public void setGoodImg(String goodImg) {
		this.goodImg = goodImg;
	}
	@JsonIgnore
	public String getGoodPic() {
		return this.goodImg;
	}
	public void setGoodPic(String basePath) {
		this.goodImg = basePath+getGoodImg();
	}
	@JsonInclude(Include.NON_NULL) 
	public String getGoodsStandardIds() {
		return goodsStandardIds;
	}

	public void setGoodsStandardIds(String goodsStandardIds) {
		this.goodsStandardIds = goodsStandardIds;
	}
	@JsonIgnore
	public String getGoodsStandardString() {
		return goodsStandardString;
	}

	public void setGoodsStandardString(String goodsStandardString) {
		this.goodsStandardString = goodsStandardString;
	}

	public Double getGoodPrice() {
		return this.goodPrice;
	}

	public void setGoodPrice(Double goodPrice) {
		this.goodPrice = goodPrice;
	}

	public Integer getGoodScore() {
		return this.goodScore;
	}

	public void setGoodScore(Integer goodScore) {
		this.goodScore = goodScore;
	}

	public Double getGoodCashpoint() {
		return this.goodCashpoint;
	}

	public void setGoodCashpoint(Double goodCashpoint) {
		this.goodCashpoint = goodCashpoint;
	}
	@JsonProperty("number")
	public Integer getGoodQuantity() {
		return this.goodQuantity;
	}

	public void setGoodQuantity(Integer goodQuantity) {
		this.goodQuantity = goodQuantity;
	}

	public Double getPayPrice() {
		return this.payPrice;
	}

	public void setPayPrice(Double payPrice) {
		this.payPrice = payPrice;
	}

	public Integer getPayScore() {
		return this.payScore;
	}

	public void setPayScore(Integer payScore) {
		this.payScore = payScore;
	}

	public Double getPayCashpoint() {
		return this.payCashpoint;
	}

	public void setPayCashpoint(Double payCashpoint) {
		this.payCashpoint = payCashpoint;
	}

	public Integer getMallId() {
		return this.mallId;
	}

	public void setMallId(Integer mallId) {
		this.mallId = mallId;
	}

	public String getMallClass() {
		return this.mallClass;
	}

	public void setMallClass(String mallClass) {
		this.mallClass = mallClass;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsBack() {
		return this.isBack;
	}

	public void setIsBack(Integer isBack) {
		this.isBack = isBack;
	}

	public Integer getLogisticsType() {
		return this.logisticsType;
	}

	public void setLogisticsType(Integer logisticsType) {
		this.logisticsType = logisticsType;
	}

	public Double getLogisticsPrice() {
		return this.logisticsPrice;
	}

	public void setLogisticsPrice(Double logisticsPrice) {
		this.logisticsPrice = logisticsPrice;
	}
	@JsonIgnore
	public ReShoppingCar getCar() {
		return car;
	}

	
	public void setCar(ReShoppingCar car) {
		this.car = car;
	}

	public Boolean getIsStockEnough() {
		return isStockEnough;
	}

	public void setIsStockEnough(Boolean isStockEnough) {
		this.isStockEnough = isStockEnough;
	}
	@JsonInclude(Include.NON_NULL) 
	public JSONObject getStandardString() {
		return StandardString;
	}

	public void setStandardString(JSONObject standardString) {
		StandardString = standardString;
	}
	@JsonInclude(Include.NON_NULL) 
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	@JsonProperty("specString")
	public String getStyle(){
		String str = "" ;
		JSONObject jsonObject = JSONObject.parseObject(this.goodsStandardString);
		JSONObject firstjson = jsonObject.getJSONObject("firstStandard");
		String name1 = firstjson.getString("name1")==null?"":firstjson.getString("name1");
		String name2 = firstjson.getString("name2")==null?"":firstjson.getString("name2");
		String name3 = firstjson.getString("name3")==null?"":firstjson.getString("name3");
		str += name1==""?"":name1+":"+jsonObject.getJSONObject("secondStandard").getString("name1")+" ";
		str += name2==""?"":name2+":"+jsonObject.getJSONObject("secondStandard").getString("name2")+" ";
		str += name3==""?"":name3+":"+jsonObject.getJSONObject("secondStandard").getString("name3")+" ";
		return str;
	}
	
	public String getImgUrl(){
		try {
			JSONArray parseArray = JSONArray.parseArray(this.goodImg);
			JSONObject jsonObject = parseArray.getJSONObject(0);
			imgUrl = basePath+jsonObject.getString("imgUrl");
		} catch (JSONException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return imgUrl;
	}
	@JsonIgnore
	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public CommodityType getGameType() {
		return gameType;
	}

	public void setGameType(CommodityType gameType) {
		this.gameType = gameType;
	}





}