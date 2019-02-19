package com.axp.domain;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.axp.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AbstractSeller entity provides the base persistence definition of the Seller
 * entity. @author MyEclipse Persistence Tools
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public abstract class AbstractSeller implements java.io.Serializable {

	// Fields
	private ProvinceEnum provinceEnum;
	private ProvinceEnum provinceEnum2;
	private Integer id;
	private Integer version;
	private Users users;//与商家对应的一级粉丝；
	private AdminUser adminUser;
	private Shoptypes shoptypes;
	private String category;//店铺种类
	private String shopcategoryId;
	private ShopCategory shopCategory;//店铺种类实体
	private Zones zones;
	private String name;
	private String sellerIdCard;
	private Integer verifyStatus;//开店审核资料审核状态
	private String businessLicencePic;
	private String imgUrl = "";
	private String loginname;
	private String password;
	private String websites = "";
	private String inviteCode = "";
	private String websitespc = "";
	private String qr = "";
	private String phone = "";
	private String voucherRemark = "";
	private String address = "";
	private String remark = "";
	private String contacts;
	private Boolean isvalid;
	private Boolean hasVoucher;
	private Timestamp createtime;
	private String logo = "";
	private Boolean istop;
	private String code;
	private Double latitude;
	private Double longitude;
	private String detailedAddress;
	private Double cashPoints;
	private Double money = 0d;
	private Integer score = 0;
	private Integer quantity = 0;
	private String zone; //省市区
	private String axpAdminUserId ="";
	private Double alreadyWithdraw;// 商家已提现总额；
	private Double totalRedpaper;// 商家总发放红包金额；
	private Set scaninfos = new HashSet(0);
	private Set plans = new HashSet(0);
	private Set adverses = new HashSet(0);
	private Set userCashshopRecords = new HashSet(0);
	private Set cashshops = new HashSet(0);
	private Set playses = new HashSet(0);
	private Set distributions = new HashSet(0);
	private Set goodses = new HashSet(0);
	private Set shopcategroys =new HashSet(0);
	private Integer level; //粉丝评分数量
	private Float sellerRating = (float) 10; // 粉丝评分
	private Integer count; // 粉丝评分数量

	private Boolean openSellerMall;// 是否开启了商家商城；
	private String headImg;// 显示在商城的头像；

	private SellerMallIndexModel indexModel;// 商家商城的首页模板；
	private Integer listDisplayType;// 列表显示界面的类型；
	private Integer goodsDisplayType;// 商品显示界面的类型；
	private Set<SellerMallGoodsType> goodsTypes = new HashSet<SellerMallGoodsType>();// 此商家对应的所有一级分类；
	private Set<SellerMallDefinedAttribute> attributes = new HashSet<SellerMallDefinedAttribute>();// 此商家定义的商品属性；
	private Set<SellerMallOrder> orders = new HashSet<SellerMallOrder>();// 此商家对应的商家商城的订单对象；
	private String basePath = "";
	public static Integer EDITION_NEW = 1;//新版本，seller相当于店铺

	public static Integer LEVEL_MAIN = 0;//主店
	public static Integer LEVEL_BRANCH = 1;//分店
	
	private Timestamp zhidingTime;//置顶时间
	private Integer intoShopNum;//进店人数 da
	
	/** default constructor */
	public AbstractSeller() {
	}
	
	
	// Constructors

	/** full constructor */


	public AbstractSeller(ProvinceEnum provinceEnum, Integer id, Users users,
			AdminUser adminUser, Shoptypes shoptypes, Zones zones, String name,
			String sellerIdCard,String businessLicencePic,Integer verifyStatus,
			String imgUrl,String websites, String websitespc, String qr, String phone,
			String voucherRemark, String address, String remark,
			String contacts, Boolean isvalid, Boolean hasVoucher,
			Timestamp createtime, String logo, Boolean istop, String code,
			Double latitude, Double longitude, String detailedAddress,
			Double cashPoints, Integer score, Integer quantity,
			Double alreadyWithdraw, Double totalRedpaper, Set scaninfos,
			Set plans, Set adverses, Set userCashshopRecords, Set cashshops,
			Set playses, Set distributions, Set goodses, Float sellerRating,
			Integer count,Integer intoShopNum) {
		super();
		this.provinceEnum = provinceEnum;
		this.id = id;
		this.users = users;
		this.adminUser = adminUser;
		this.shoptypes = shoptypes;
		this.zones = zones;
		this.name = name;
		this.sellerIdCard = sellerIdCard;
		this.businessLicencePic =businessLicencePic;
		this.verifyStatus = verifyStatus;
		this.imgUrl = imgUrl;
		this.websites = websites;
		this.websitespc = websitespc;
		this.qr = qr;
		this.phone = phone;
		this.voucherRemark = voucherRemark;
		this.address = address;
		this.remark = remark;
		this.contacts = contacts;
		this.isvalid = isvalid;
		this.hasVoucher = hasVoucher;
		this.createtime = createtime;
		this.logo = logo;
		this.istop = istop;
		this.code = code;
		this.latitude = latitude;
		this.longitude = longitude;
		this.detailedAddress = detailedAddress;
		this.cashPoints = cashPoints;
		this.score = score;
		this.quantity = quantity;
		this.alreadyWithdraw = alreadyWithdraw;
		this.totalRedpaper = totalRedpaper;
		this.scaninfos = scaninfos;
		this.plans = plans;
		this.adverses = adverses;
		this.userCashshopRecords = userCashshopRecords;
		this.cashshops = cashshops;
		this.playses = playses;
		this.distributions = distributions;
		this.goodses = goodses;
		this.sellerRating = sellerRating;
		this.count = count;
		this.intoShopNum = intoShopNum;
	}

	
	
	
	public AbstractSeller(ProvinceEnum provinceEnum,
			ProvinceEnum provinceEnum2, Integer id, Integer version,
			Users users, AdminUser adminUser, Shoptypes shoptypes,
			String category, String shopcategoryId, ShopCategory shopCategory,
			Zones zones, String name, String sellerIdCard,
			Integer verifyStatus, String businessLicencePic, String imgUrl,
			String loginname, String password, String websites,
			String inviteCode, String websitespc, String qr, String phone,
			String voucherRemark, String address, String remark,
			String contacts, Boolean isvalid, Boolean hasVoucher,
			Timestamp createtime, String logo, Boolean istop, String code,
			Double latitude, Double longitude, String detailedAddress,
			Double cashPoints, Double money, Integer score, Integer quantity,
			String zone, String axpAdminUserId, Double alreadyWithdraw,
			Double totalRedpaper, Set scaninfos, Set plans, Set adverses,
			Set userCashshopRecords, Set cashshops, Set playses,
			Set distributions, Set goodses, Set shopcategroys, Integer level,
			Float sellerRating, Integer count, Boolean openSellerMall,
			String headImg, SellerMallIndexModel indexModel,
			Integer listDisplayType, Integer goodsDisplayType,
			Set<SellerMallGoodsType> goodsTypes,
			Set<SellerMallDefinedAttribute> attributes,
			Set<SellerMallOrder> orders, String basePath,
			Timestamp zhidingTime, Integer intoShopNum) {
		super();
		this.provinceEnum = provinceEnum;
		this.provinceEnum2 = provinceEnum2;
		this.id = id;
		this.version = version;
		this.users = users;
		this.adminUser = adminUser;
		this.shoptypes = shoptypes;
		this.category = category;
		this.shopcategoryId = shopcategoryId;
		this.shopCategory = shopCategory;
		this.zones = zones;
		this.name = name;
		this.sellerIdCard = sellerIdCard;
		this.verifyStatus = verifyStatus;
		this.businessLicencePic = businessLicencePic;
		this.imgUrl = imgUrl;
		this.loginname = loginname;
		this.password = password;
		this.websites = websites;
		this.inviteCode = inviteCode;
		this.websitespc = websitespc;
		this.qr = qr;
		this.phone = phone;
		this.voucherRemark = voucherRemark;
		this.address = address;
		this.remark = remark;
		this.contacts = contacts;
		this.isvalid = isvalid;
		this.hasVoucher = hasVoucher;
		this.createtime = createtime;
		this.logo = logo;
		this.istop = istop;
		this.code = code;
		this.latitude = latitude;
		this.longitude = longitude;
		this.detailedAddress = detailedAddress;
		this.cashPoints = cashPoints;
		this.money = money;
		this.score = score;
		this.quantity = quantity;
		this.zone = zone;
		this.axpAdminUserId = axpAdminUserId;
		this.alreadyWithdraw = alreadyWithdraw;
		this.totalRedpaper = totalRedpaper;
		this.scaninfos = scaninfos;
		this.plans = plans;
		this.adverses = adverses;
		this.userCashshopRecords = userCashshopRecords;
		this.cashshops = cashshops;
		this.playses = playses;
		this.distributions = distributions;
		this.goodses = goodses;
		this.shopcategroys = shopcategroys;
		this.level = level;
		this.sellerRating = sellerRating;
		this.count = count;
		this.openSellerMall = openSellerMall;
		this.headImg = headImg;
		this.indexModel = indexModel;
		this.listDisplayType = listDisplayType;
		this.goodsDisplayType = goodsDisplayType;
		this.goodsTypes = goodsTypes;
		this.attributes = attributes;
		this.orders = orders;
		this.basePath = basePath;
		this.zhidingTime = zhidingTime;
		this.intoShopNum = intoShopNum;
	}


	public Timestamp getZhidingTime() {
		return zhidingTime;
	}


	public void setZhidingTime(Timestamp zhidingTime) {
		this.zhidingTime = zhidingTime;
	}


	@JsonProperty("sellerAddress")
	@JsonInclude(Include.NON_NULL) 
	public String getAddress() {
		return this.address;
	}


	@JsonIgnore
	public AdminUser getAdminUser() {
		return this.adminUser;
	}

	@JsonIgnore
	public Set getAdverses() {
		return this.adverses;
	}
	@JsonIgnore
	public Double getAlreadyWithdraw() {
		return alreadyWithdraw;
	}

	@JsonIgnore
	public Set<SellerMallDefinedAttribute> getAttributes() {
		return attributes;
	}

	public String getAxpAdminUserId() {
		return axpAdminUserId;
	}

	@JsonIgnore
	public String getBasePath() {
		return basePath;
	}
	public String getBusinessLicencePic() {
		return businessLicencePic;
	}

	@JsonIgnore
	public Double getCashPoints() {
		return this.cashPoints;
	}
	@JsonIgnore
	public Set getCashshops() {
		return this.cashshops;
	}

	public String getCategory() {
		return category;
	}

	@JsonIgnore
	public String getCode() {
		return this.code;
	}
	@JsonInclude(Include.NON_NULL) 
	public String getContacts() {
		return this.contacts;
	}

	@JsonIgnore
	public Integer getCount() {
		return count;
	}
	@JsonIgnore
	public Timestamp getCreatetime() {
		return this.createtime;
	}

	@JsonIgnore
	public String getDetailedAddress() {
		return this.detailedAddress;
	}
	@JsonIgnore
	public Set getDistributions() {
		return this.distributions;
	}

	@JsonIgnore
	public Integer getGoodsDisplayType() {
		return goodsDisplayType;
	}
	@JsonIgnore
	public Set getGoodses() {
		return this.goodses;
	}
	@JsonIgnore
	public Set<SellerMallGoodsType> getGoodsTypes() {
		return goodsTypes;
	}

	@JsonIgnore
	public Boolean getHasVoucher() {
		return hasVoucher;
	}

	@JsonIgnore
	public String getHeadImg() {
		String head = headImg;
		if(headImg==null||headImg.length()==0){
			headImg = logo;
			head=basePath+headImg;
		}
			
		return head;
	}
	@JsonProperty("sellerId")
	public Integer getId() {
		return this.id;
	}

	// Property accessors
	@JsonIgnore
	public String getImgUrl() {
		if(imgUrl==null||imgUrl.length()==0)
			imgUrl = logo;
		return imgUrl;
	}
	
	@JsonIgnore
	public SellerMallIndexModel getIndexModel() {
		return indexModel;
	}

	@JsonIgnore
	public String getInviteCode() {
		return inviteCode;
	}
	@JsonIgnore
	public Boolean getIstop() {
		return this.istop;
	}

	@JsonIgnore
	public Boolean getIsvalid() {
		return this.isvalid;
	}

	@JsonProperty("lat")
	@JsonInclude(Include.NON_NULL) 
	public Double getLatitude() {
		return this.latitude;
	}

	@JsonIgnore
	public Integer getLevel() {
		return level;
	}

	@JsonIgnore
	public Integer getListDisplayType() {
		return listDisplayType;
	}

	@JsonIgnore
	public String getLoginname() {
		return loginname;
	}

	@JsonProperty("sellerIcon")
	@JsonInclude(Include.NON_NULL) 
	public String getLogo() {
		if(!StringUtil.isEmpty(logo)&&!logo.startsWith("http")){
			return basePath+logo;
		}else{
			return StringUtil.sellerHead;
		}
	}

	@JsonProperty("lng")
	@JsonInclude(Include.NON_NULL) 
	public Double getLongitude() {
		return this.longitude;
	}
	
	@JsonIgnore
	public Double getMoney() {
		return money;
	}

	@JsonProperty("sellerName")
	@JsonInclude(Include.NON_NULL) 
	public String getName() {
		return this.name;
	}
	@JsonIgnore
	public Boolean getOpenSellerMall() {
		return openSellerMall;
	}

	@JsonIgnore
	public Set<SellerMallOrder> getOrders() {
		return orders;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty("sellerPhone")
	@JsonInclude(Include.NON_NULL) 
	public String getPhone() {
		return this.phone;
	}
	@JsonIgnore
	public Set getPlans() {
		return this.plans;
	}

	@JsonIgnore
	public Set getPlayses() {
		return this.playses;
	}

	@JsonIgnore
	public ProvinceEnum getProvinceEnum() {
		return provinceEnum;
	}

	@JsonIgnore
	public ProvinceEnum getProvinceEnum2() {
		return provinceEnum2;
	}

	@JsonInclude(Include.NON_NULL) 
	public String getQr() {
		return this.qr;
	}

	@JsonIgnore
	public Integer getQuantity() {
		return quantity;
	}

	@JsonInclude(Include.NON_NULL) 
	public String getRemark() {
		return this.remark;
	}

	@JsonIgnore
	public Set getScaninfos() {
		return this.scaninfos;
	}
	@JsonIgnore
	public Integer getScore() {
		return score;
	}

	public String getSellerIdCard() {
		return sellerIdCard;
	}

	public Float getSellerRating() {
		return sellerRating;
	}

	public ShopCategory getShopCategory() {
		return shopCategory;
	}

	public String getShopcategoryId() {
		return shopcategoryId;
	}

	public Set getShopcategroys() {
		return shopcategroys;
	}

	@JsonIgnore
	public Shoptypes getShoptypes() {
		return this.shoptypes;
	}

	@JsonIgnore
	public Double getTotalRedpaper() {
		return totalRedpaper;
	}
	@JsonIgnore
	public Set getUserCashshopRecords() {
		return this.userCashshopRecords;
	}

	@JsonIgnore
	public Users getUsers() {
		return this.users;
	}
	public Integer getVerifyStatus() {
		return verifyStatus;
	}

	@JsonIgnore
	public Integer getVersion() {
		return version;
	}
	@JsonIgnore
	public String getVoucherRemark() {
		return voucherRemark;
	}

	@JsonInclude(Include.NON_NULL) 
	public String getWebsites() {
		return this.websites;
	}
	@JsonInclude(Include.NON_NULL) 
	public String getWebsitespc() {
		return this.websitespc;
	}
	
	public String getSellerAddres(){
		
		if(StringUtils.isNotBlank(this.zone)){
			return zone;
		}else{
			return address;
		}
		
	}
	

	public String getZone() {
		return zone;
	}
	@JsonIgnore
	public Zones getZones() {
		return this.zones;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}

	public void setAdverses(Set adverses) {
		this.adverses = adverses;
	}
	public void setAlreadyWithdraw(Double alreadyWithdraw) {
		this.alreadyWithdraw = alreadyWithdraw;
	}

	public void setAttributes(Set<SellerMallDefinedAttribute> attributes) {
		this.attributes = attributes;
	}
	public void setAxpAdminUserId(String axpAdminUserId) {
		this.axpAdminUserId = axpAdminUserId;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	public void setBusinessLicencePic(String businessLicencePic) {
		this.businessLicencePic = businessLicencePic;
	}

	public void setCashPoints(Double cashPoints) {
		this.cashPoints = cashPoints;
	}
	public void setCashshops(Set cashshops) {
		this.cashshops = cashshops;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public void setDetailedAddress(String detailedAddress) {
		this.detailedAddress = detailedAddress;
	}

	public void setDistributions(Set distributions) {
		this.distributions = distributions;
	}
	public void setGoodsDisplayType(Integer goodsDisplayType) {
		this.goodsDisplayType = goodsDisplayType;
	}

	public void setGoodses(Set goodses) {
		this.goodses = goodses;
	}
	public void setGoodsTypes(Set<SellerMallGoodsType> goodsTypes) {
		this.goodsTypes = goodsTypes;
	}

	public void setHasVoucher(Boolean hasVoucher) {
		this.hasVoucher = hasVoucher;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public void setIndexModel(SellerMallIndexModel indexModel) {
		this.indexModel = indexModel;
	}
	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public void setIstop(Boolean istop) {
		this.istop = istop;
	}
	public void setIsvalid(Boolean isvalid) {
		this.isvalid = isvalid;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}

	public void setListDisplayType(Integer listDisplayType) {
		this.listDisplayType = listDisplayType;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}


	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public void setMoney(Double money) {
		this.money = money;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOpenSellerMall(Boolean openSellerMall) {
		this.openSellerMall = openSellerMall;
	}
	public void setOrders(Set<SellerMallOrder> orders) {
		this.orders = orders;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setPlans(Set plans) {
		this.plans = plans;
	}
	public void setPlayses(Set playses) {
		this.playses = playses;
	}

	public void setProvinceEnum(ProvinceEnum provinceEnum) {
		this.provinceEnum = provinceEnum;
	}
	public void setProvinceEnum2(ProvinceEnum provinceEnum2) {
		this.provinceEnum2 = provinceEnum2;
	}

	public void setQr(String qr) {
		this.qr = qr;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setScaninfos(Set scaninfos) {
		this.scaninfos = scaninfos;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public void setSellerIdCard(String sellerIdCard) {
		this.sellerIdCard = sellerIdCard;
	}

	public void setSellerRating(Float sellerRating) {
		this.sellerRating = sellerRating;
	}

	public void setShopCategory(ShopCategory shopCategory) {
		this.shopCategory = shopCategory;
	}

	public void setShopcategoryId(String shopcategoryId) {
		this.shopcategoryId = shopcategoryId;
	}
	public void setShopcategroys(Set shopcategroys) {
		this.shopcategroys = shopcategroys;
	}

	public void setShoptypes(Shoptypes shoptypes) {
		this.shoptypes = shoptypes;
	}

	public void setTotalRedpaper(Double totalRedpaper) {
		this.totalRedpaper = totalRedpaper;
	}

	public void setUserCashshopRecords(Set userCashshopRecords) {
		this.userCashshopRecords = userCashshopRecords;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public void setVoucherRemark(String voucherRemark) {
		this.voucherRemark = voucherRemark;
	}

	public void setWebsites(String websites) {
		this.websites = websites;
	}

	public void setWebsitespc(String websitespc) {
		this.websitespc = websitespc;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public void setZones(Zones zones) {
		this.zones = zones;
	}
	public Integer getIntoShopNum() {
		return intoShopNum;
	}
	public void setIntoShopNum(Integer intoShopNum) {
		this.intoShopNum = intoShopNum;
	}

}