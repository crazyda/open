package com.axp.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractAdminUser entity provides the base persistence definition of the
 * AdminUser entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractAdminUser implements java.io.Serializable {

	// Fields
	private Users users;
	private ProvinceEnum provinceEnum;
	private ProvinceEnum provinceEnum2;
	private Double cashPoints;
	private Integer id;
	private Integer score;
	public Integer getScore() {
		if(score==null){
			return 0;
		}
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	private Integer version;
	private String loginname;
	private String username;
	public ProvinceEnum getProvinceEnum2() {
		return provinceEnum2;
	}

	public void setProvinceEnum2(ProvinceEnum provinceEnum2) {
		this.provinceEnum2 = provinceEnum2;
	}

	private String password;
	private Integer level;
	private Integer center;
	private Integer proxyOne;
	private Boolean isvalid;
	private Boolean isOpenPromoter;
	private Boolean isOpenRedpaper;
	private Boolean isOpenRebate;
	private Boolean isOpenCashshopType;
	private Boolean hasVoucher;
	private Timestamp createtime;
	private Integer highLevelCount;
	private Integer quantity;
	private Integer proxyQuantity;
	private Integer centerQuantity;
	private Integer platformQuantity;
	private String phone;
	private String address;
	private String contacts;
	private String invitecode;
	private Integer parentId;
	private String parentName;
	private Boolean isZoneLimit;
	private Double longitude;
	private Double latitude;
	private Double radius;
	private Integer isChild;
	private Double balanceCash;
	private Double sellerCash;
	private Integer sellerId;
	private Double money;
	private Boolean isOpenVipMall;
	private Integer visitors;
	private Double totalMoney;
	private Double todayTotalMoney;
	private Integer todayOrderNum;
	private News news;
	private Seller seller;
	private Slides slides;
	private AdminUser parentAdminUser;//城市代理（父级）
	private String redpaperUri;
	private String channelid;
	
	private String devicetoken;
	//da
	private Integer showScore;
	private Double showMoney;
	private Double deposit; //押金
	private Double lmUnion;//联盟合伙人占比
	private Integer scoreMax;//分配积分上限
	private Integer scoreSurplus;//总积分累计

	public Integer getScoreMax() {
		return scoreMax;
	}

	public void setScoreMax(Integer scoreMax) {
		this.scoreMax = scoreMax;
	}

	public Integer getScoreSurplus() {
		return scoreSurplus;
	}

	public void setScoreSurplus(Integer scoreSurplus) {
		this.scoreSurplus = scoreSurplus;
	}

	public Double getLmUnion() {
		return lmUnion;
	}

	public void setLmUnion(Double lmUnion) {
		this.lmUnion = lmUnion;
	}

	public Double getDeposit() {
		return deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public Integer getShowScore() {
		return showScore;
	}

	public void setShowScore(Integer showScore) {
		this.showScore = showScore;
	}

	public Double getShowMoney() {
		return showMoney;
	}

	public void setShowMoney(Double showMoney) {
		this.showMoney = showMoney;
	}

	public String getDevicetoken() {
		return devicetoken;
	}

	public void setDevicetoken(String devicetoken) {
		this.devicetoken = devicetoken;
	}

	public String getRedpaperUri() {
		return redpaperUri;
	}

	public void setRedpaperUri(String redpaperUri) {
		this.redpaperUri = redpaperUri;
	}

	private Set websiteses = new HashSet(0);
	private Set goodses = new HashSet(0);
	private Set advers = new HashSet(0);
	private Set userses = new HashSet(0);
	private Set adverpools = new HashSet(0);
	private Set goodsAdverrecordses = new HashSet(0);
	private Set<Seller> sellers = new HashSet<Seller>();
	private Set<AdminUser > children = new HashSet<AdminUser>();
	private Set<PromoterModeRecord > promoterModeRecords = new HashSet<PromoterModeRecord>();
	private Set adminUserMenuses = new HashSet(0);
	private Set adminUserMessages = new HashSet(0);
	private Set messageCenters = new HashSet(0);
	private Set cashshopTypes = new HashSet(0);
	
	private String promoterModeName;
	private Timestamp promoterModeEndTime;
	private Timestamp promoterModeStartTime;
	private Timestamp lasttime;
	
	private Seller providerSeller;
	// Constructors

	/** default constructor */
	public AbstractAdminUser() {
	}

	/** minimal constructor */
	public AbstractAdminUser(String loginname, String username,
			String password, Integer level, Integer center, Integer proxyOne,
			Boolean isvalid, Boolean isOpenPromoter, Boolean hasVoucher, Timestamp createtime, Integer highLevelCount, Integer quantity, Integer proxyQuantity, Integer centerQuantity, Integer platformQuantity,Double longitude, Boolean isZoneLimit) {
		this.loginname = loginname;
		this.username = username;
		this.password = password;
		this.level = level;
		this.center = center;
		this.highLevelCount = highLevelCount;
		this.proxyOne = proxyOne;
		this.proxyQuantity = proxyQuantity;
		this.centerQuantity = centerQuantity;
		this.platformQuantity = platformQuantity;
		this.isvalid = isvalid;
		this.isOpenPromoter = isOpenPromoter;
		this.createtime = createtime;
		this.quantity = quantity;
		this.hasVoucher = hasVoucher;
		this.isZoneLimit = isZoneLimit;
	}

	/** full constructor */
	public AbstractAdminUser(String loginname, String username,
			String password, Integer level, Integer center, Integer proxyOne,
			Boolean isvalid, Boolean isOpenPromoter, Boolean hasVoucher, Timestamp createtime, Integer highLevelCount, Integer quantity, Integer proxyQuantity, Integer centerQuantity, Integer platformQuantity,
			String phone, String address, String contacts, String invitecode,Boolean isZoneLimit,Double longitude,Double latitude, Double radius,
			Set websiteses, Set goodses, Set advers, Set userses,
			Set adverpools, Set goodsAdverrecordses,Set adminUserMenuses,Set adminUserMessages,Set messageCenters,Set cashshopTypes,Users users) {
		this.loginname = loginname;
		this.username = username;
		this.password = password;
		this.level = level;
		this.center = center;
		this.proxyOne = proxyOne;
		this.proxyQuantity = proxyQuantity;
		this.centerQuantity = centerQuantity;
		this.platformQuantity = platformQuantity;
		this.isvalid = isvalid;
		this.isOpenPromoter = isOpenPromoter;
		this.createtime = createtime;
		this.highLevelCount = highLevelCount;
		this.quantity = quantity;
		this.phone = phone;
		this.address = address;
		this.contacts = contacts;
		this.invitecode = invitecode;
		this.websiteses = websiteses;
		this.goodses = goodses;
		this.advers = advers;
		this.userses = userses;
		this.adverpools = adverpools;
		this.goodsAdverrecordses = goodsAdverrecordses;
		this.hasVoucher = hasVoucher;
		this.isZoneLimit = isZoneLimit;
		this.longitude = longitude;
		this.latitude = latitude;
		this.radius = radius;
		this.adminUserMenuses = adminUserMenuses;
		this.adminUserMessages = adminUserMessages;
		this.messageCenters = messageCenters;
		this.cashshopTypes = cashshopTypes;
		this.users = users;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getCenter() {
		return this.center;
	}

	public void setCenter(Integer center) {
		this.center = center;
	}

	public Integer getProxyOne() {
		return this.proxyOne;
	}

	public void setProxyOne(Integer proxyOne) {
		this.proxyOne = proxyOne;
	}

	public Boolean getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(Boolean isvalid) {
		this.isvalid = isvalid;
	}

	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public AdminUser getParentAdminUser() {
		return parentAdminUser;
	}

	public void setParentAdminUser(AdminUser parentAdminUser) {
		this.parentAdminUser = parentAdminUser;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return this.address;
	}

	public Set<AdminUser> getChildren() {
		return children;
	}

	public void setChildren(Set<AdminUser> children) {
		this.children = children;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContacts() {
		return this.contacts;
	}

	public Set<Seller> getSellers() {
		return sellers;
	}

	public void setSellers(Set<Seller> sellers) {
		this.sellers = sellers;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getInvitecode() {
		return this.invitecode;
	}

	public void setInvitecode(String invitecode) {
		this.invitecode = invitecode;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}



	public Set getWebsiteses() {
		return this.websiteses;
	}

	public void setWebsiteses(Set websiteses) {
		this.websiteses = websiteses;
	}

	public Set getGoodses() {
		return this.goodses;
	}

	public void setGoodses(Set goodses) {
		this.goodses = goodses;
	}

	public Set getAdvers() {
		return this.advers;
	}

	public void setAdvers(Set advers) {
		this.advers = advers;
	}

	public Set getUserses() {
		return this.userses;
	}

	public void setUserses(Set userses) {
		this.userses = userses;
	}

	public Set getAdverpools() {
		return this.adverpools;
	}

	public void setAdverpools(Set adverpools) {
		this.adverpools = adverpools;
	}

	public Set getGoodsAdverrecordses() {
		return this.goodsAdverrecordses;
	}

	public void setGoodsAdverrecordses(Set goodsAdverrecordses) {
		this.goodsAdverrecordses = goodsAdverrecordses;
	}

	public Double getCashPoints() {
		return cashPoints;
	}

	public void setCashPoints(Double cashPoints) {
		this.cashPoints = cashPoints;
	}

	public Integer getProxyQuantity() {
		return proxyQuantity;
	}

	public void setProxyQuantity(Integer proxyQuantity) {
		this.proxyQuantity = proxyQuantity;
	}

	public Integer getCenterQuantity() {
		return centerQuantity;
	}

	public void setCenterQuantity(Integer centerQuantity) {
		this.centerQuantity = centerQuantity;
	}

	public Integer getPlatformQuantity() {
		return platformQuantity;
	}

	public void setPlatformQuantity(Integer platformQuantity) {
		this.platformQuantity = platformQuantity;
	}

	public Boolean getHasVoucher() {
		return hasVoucher;
	}

	public void setHasVoucher(Boolean hasVoucher) {
		this.hasVoucher = hasVoucher;
	}

	public Boolean getIsOpenPromoter() {
		return isOpenPromoter;
	}

	public void setIsOpenPromoter(Boolean isOpenPromoter) {
		this.isOpenPromoter = isOpenPromoter;
	}

	public Boolean getIsZoneLimit() {
		return isZoneLimit;
	}

	public void setIsZoneLimit(Boolean isZoneLimit) {
		this.isZoneLimit = isZoneLimit;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}

	public ProvinceEnum getProvinceEnum() {
		return provinceEnum;
	}

	public void setProvinceEnum(ProvinceEnum provinceEnum) {
		this.provinceEnum = provinceEnum;
	}

	public Integer getHighLevelCount() {
		return highLevelCount;
	}

	public void setHighLevelCount(Integer highLevelCount) {
		this.highLevelCount = highLevelCount;
	}

	public Set<PromoterModeRecord> getPromoterModeRecords() {
		return promoterModeRecords;
	}

	public void setPromoterModeRecords(Set<PromoterModeRecord> promoterModeRecords) {
		this.promoterModeRecords = promoterModeRecords;
	}

	public Boolean getIsOpenRedpaper() {
		return isOpenRedpaper;
	}

	public void setIsOpenRedpaper(Boolean isOpenRedpaper) {
		this.isOpenRedpaper = isOpenRedpaper;
	}

	public String getPromoterModeName() {
		return promoterModeName;
	}

	public void setPromoterModeName(String promoterModeName) {
		this.promoterModeName = promoterModeName;
	}

	public Timestamp getPromoterModeEndTime() {
		return promoterModeEndTime;
	}

	public void setPromoterModeEndTime(Timestamp promoterModeEndTime) {
		this.promoterModeEndTime = promoterModeEndTime;
	}

	public Timestamp getPromoterModeStartTime() {
		return promoterModeStartTime;
	}

	public void setPromoterModeStartTime(Timestamp promoterModeStartTime) {
		this.promoterModeStartTime = promoterModeStartTime;
	}

	public Double getBalanceCash() {
		return balanceCash;
	}

	public void setBalanceCash(Double balanceCash) {
		this.balanceCash = balanceCash;
	}

	public Double getSellerCash() {
		return sellerCash;
	}

	public void setSellerCash(Double sellerCash) {
		this.sellerCash = sellerCash;
	}

	public Integer getIsChild() {
		return isChild;
	}

	public void setIsChild(Integer isChild) {
		this.isChild = isChild;
	}

	public Set getAdminUserMenuses() {
		return adminUserMenuses;
	}

	public void setAdminUserMenuses(Set adminUserMenuses) {
		this.adminUserMenuses = adminUserMenuses;
	}

	public Boolean getIsOpenRebate() {
		return isOpenRebate;
	}

	public void setIsOpenRebate(Boolean isOpenRebate) {
		this.isOpenRebate = isOpenRebate;
	}

	public Set getAdminUserMessages() {
		return adminUserMessages;
	}

	public void setAdminUserMessages(Set adminUserMessages) {
		this.adminUserMessages = adminUserMessages;
	}

	public Set getMessageCenters() {
		return messageCenters;
	}

	public void setMessageCenters(Set messageCenters) {
		this.messageCenters = messageCenters;
	}

	public Boolean getIsOpenCashshopType() {
		return isOpenCashshopType;
	}

	public void setIsOpenCashshopType(Boolean isOpenCashshopType) {
		this.isOpenCashshopType = isOpenCashshopType;
	}

	public Set getCashshopTypes() {
		return cashshopTypes;
	}

	public void setCashshopTypes(Set cashshopTypes) {
		this.cashshopTypes = cashshopTypes;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public Seller getProviderSeller() {
		return providerSeller;
	}

	public void setProviderSeller(Seller providerSeller) {
		this.providerSeller = providerSeller;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Timestamp getLasttime() {
		return lasttime;
	}

	public void setLasttime(Timestamp lasttime) {
		this.lasttime = lasttime;
	}

	public Boolean getIsOpenVipMall() {
		return isOpenVipMall;
	}

	public void setIsOpenVipMall(Boolean isOpenVipMall) {
		this.isOpenVipMall = isOpenVipMall;
	}

	public Integer getVisitors() {
		return visitors;
	}

	public void setVisitors(Integer visitors) {
		this.visitors = visitors;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Double getTodayTotalMoney() {
		return todayTotalMoney;
	}

	public void setTodayTotalMoney(Double todayTotalMoney) {
		this.todayTotalMoney = todayTotalMoney;
	}

	public Integer getTodayOrderNum() {
		return todayOrderNum;
	}

	public void setTodayOrderNum(Integer todayOrderNum) {
		this.todayOrderNum = todayOrderNum;
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public Slides getSlides() {
		return slides;
	}

	public void setSlides(Slides slides) {
		this.slides = slides;
	}

	public String getChannelid() {
		return channelid;
	}

	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}
	
	
	
}