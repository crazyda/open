package com.axp.domain;

import java.sql.Timestamp;
import java.util.List;

import com.axp.util.CalcUtil;

/**
 * ReGoodsorder entity. @author MyEclipse Persistence Tools
 */

public class ReGoodsorder implements java.io.Serializable {

	// Fields

    private Integer id;
    private Boolean isValid;
    private Timestamp createTime;
    private Timestamp  consignmentTime;//发货时间
    private String orderCode;//订单编码；（）
	private Double payPrice = 0d;//需要支付的现金；
	private Integer payScore = 0;//需要支付的积分；
	private Double payCashpoint = 0d;//需要支付的红包；
	private Double cashBack = 0d; //返现总金额
	private Integer payType;//支付类型；
	private String payAccount;//支付账户；
	private String accountName;//账户名称；
	private String realname;//真实姓名；
	private String address;//用户地址；
	private String phone;//用户电话；
	private Integer status;//订单状态；（）
	private String message;//买家留言；
	private String logisticsCompay;//物流公司；
	private Double logisticsType = 0d;//邮费（）
	private String logisticsCode;//快递单号；
	private String logistics;//配送类型；（）
	private String sellerPhone;//商家电话；
	private String sellerAddress;//商家地址；
	private String backGoodsAddress;//退货地址；
	private Timestamp confirmtime;//自动确认时间；（）
	private String code;//随机码（）；
	private String ewmImg;//二维码图片
	private Integer totalFee;
	private String transactionId;//支付订单号
	private String outTradeNo;//商户订单号
	private String timeEnd;//支付时间
	private String openid;//
	private String alipayCode;
	private String weixinCode;
	private Double walletPay=0d; 
	private Integer isMerge;
	private Integer webType=0; // 1.微信公众号
	private Boolean isTeam;   //是否拼团
	private Integer teamNum;  //已拼人数
	private Timestamp teamPayTime; //拼团支付时间
	private ReGoodsorder reGoodsorder; //拼主订单
	private String visitor; //订单来源
	private Integer adminId; //微信表id
	private Integer isGame;//幸运抽奖订单


	private Users user;//订单对应的用户；
	private Seller seller;//对应的商家；
	private List<ReGoodsorderItem> items;//总订单对应的订单明细；（冗余数据不保存到数据库）
	
	private Boolean isHasItems;//有效主订单数量
	
	
	public static Integer valid_Time=72;
    //订单
    public static Integer huan_cun  = -1;//待支付
	public static Integer dai_zhi_fu  = 0;//待支付
	public static Integer dai_pin_tuan=5;  //待拼团
	public static Integer dai_que_ren  =10;//待确认
	public static Integer dai_fa_huo  =20;//待发货
	public static Integer dai_dui_huan  =25;//待兑换
    public static Integer dai_shou_huo  =30;//待收货
    public static Integer dai_ping_jia  =40;//已完成(待评价)
    public static Integer yi_wan_cheng  =50;//已完成(已评价)
    public static Integer jifenduobao  =60;//积分夺宝 未开奖
//    public static Integer tui_hui  =60;//已完成(退货)
//    public static Integer yi_ping_jia  =70;//已完成(已评价)
    //退单
    public static Integer ke_tui_dan  = 10; //可退单(审核不通过)
    public static Integer zheng_zai_tui_dan  = 20; //正在退单
    public static Integer tong_yi_tui_dan = 30;//同意退单
    public static Integer bu_ke_tui_dan  = 40; //不可退单
    public static Integer yi_tui_dan  = 50; //已退单
    public static Integer shen_qing_tui_kuan = 60;//申请退款
    public static Integer xia_jia_tui_dan = 70;//商品下架已退积分

    //支付类型
  	public static Integer PAYTYPE_zfb = 10;//支付宝
  	public static Integer PAYTYPE_wx = 20;// 微信
  	public static Integer PAYTYPE_axp = 30;// 每天积分钱包
  	public static Integer PAYTYPE_yl = 40;// 易联
    /**
     * 运输方式
     */
    public static final Integer bao_you = 1;//包邮
    public static final Integer bu_bao_you = 2;//不包邮；
    public static final Integer shang_men_zi_qu = 3;//上门自取；
	public static final Integer dao_dian_xiao_fei = 4;//到店消费；
	
	public static String getlogisticsName(Integer type){
		switch (type) {
		case 1: return "包邮";
		case 2: return "不包邮";
		case 3: return "上门自取";
		case 4: return "到店消费";
		default:
			break;
		}
		return null;
	}
    // Constructors

  	
  	public String getPayTypeChar(){
  		switch (this.payType) {
			case 10:
				return "ALIPAY";
			case 20:
				return "WEIXIN";
			case 30:
				return "WALLET";
		}
  		return null;
  	}
  	
  	public Integer getPayTypeNum(String payType){
  		if(payType.equals("ALIPAY")){
  			return 10;
  		}else if(payType.equals("WEIXIN")){
  			return 20;
  		}else if(payType.equals("WALLET")){
  			return 30;
  		}else if(payType.equals("YILIAN")){
  			return 40;
  		}
		return -1;
  	}
  	
    /**
     * default constructor
     */
    public ReGoodsorder() {
    }


	// Property accessors
    
	public String getVisitor() {
		return visitor;
	}


	public void setVisitor(String visitor) {
		this.visitor = visitor;
	}

	public Integer getId() {
		return this.id;
	}

	public Integer getAdminId() {
		return adminId;
	}


	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Users getUser() {
		return user;
	}


	public void setUser(Users user) {
		this.user = user;
	}


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

	public String getOrderCode() {
		return this.orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
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

	public Integer getPayType() {
		return this.payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getPayAccount() {
		return this.payAccount;
	}

	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getRealname() {
		return this.realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLogisticsCompay() {
		return this.logisticsCompay;
	}

	public void setLogisticsCompay(String logisticsCompay) {
		this.logisticsCompay = logisticsCompay;
	}

	public String getLogisticsCode() {
		return this.logisticsCode;
	}

	public void setLogisticsCode(String logisticsCode) {
		this.logisticsCode = logisticsCode;
	}

	public String getLogistics() {
		return this.logistics;
	}

	public void setLogistics(String logistics) {
		this.logistics = logistics;
	}

	public String getSellerPhone() {
		return this.sellerPhone;
	}

	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

	public String getSellerAddress() {
		return this.sellerAddress;
	}

	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}

	public Double getLogisticsType() {
		return this.logisticsType;
	}

	public void setLogisticsType(Double logisticsType) {
		this.logisticsType = logisticsType;
	}

	public Timestamp getConfirmtime() {
		return this.confirmtime;
	}

	public void setConfirmtime(Timestamp confirmtime) {
		this.confirmtime = confirmtime;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public List<ReGoodsorderItem> getItems() {
		return items;
	}


	public void setItems(List<ReGoodsorderItem> items) {
		this.items = items;
	}


	public Seller getSeller() {
		return seller;
	}


	public void setSeller(Seller seller) {
		this.seller = seller;
	}


	public Timestamp getConsignmentTime() {
		return consignmentTime;
	}


	public void setConsignmentTime(Timestamp consignmentTime) {
		this.consignmentTime = consignmentTime;
	}

	public Double getCashBack() {
		return cashBack;
	}

	public void setCashBack(Double cashBack) {
		this.cashBack = cashBack;
	}


	public String getEwmImg() {
		return ewmImg;
	}


	public void setEwmImg(String ewmImg) {
		this.ewmImg = ewmImg;
	}


	public String getBackGoodsAddress() {
		return backGoodsAddress;
	}


	public void setBackGoodsAddress(String backGoodsAddress) {
		this.backGoodsAddress = backGoodsAddress;
	}


	public Boolean getIsHasItems() {
		return isHasItems;
	}


	public void setIsHasItems(Boolean isHasItems) {
		this.isHasItems = isHasItems;
	}


	public String getAlipayCode() {
		return alipayCode;
	}


	public void setAlipayCode(String alipayCode) {
		this.alipayCode = alipayCode;
	}


	public String getWeixinCode() {
		return weixinCode;
	}


	public void setWeixinCode(String weixinCode) {
		this.weixinCode = weixinCode;
	}

    public String getOpenid() {
		return openid;
	}


	public void setOpenid(String openid) {
		this.openid = openid;
	}


	public Integer getTotalFee() {
		return totalFee;
	}


	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}


	public String getTransactionId() {
		return transactionId;
	}


	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}


	public String getOutTradeNo() {
		return outTradeNo;
	}


	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}


	public String getTimeEnd() {
		return timeEnd;
	}


	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
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


	public Integer getTeamNum() {
		return teamNum;
	}
	





	public void setTeamNum(Integer teamNum) {
		this.teamNum = teamNum;
	}


	public Timestamp getTeamPayTime() {
		return teamPayTime;
	}


	public void setTeamPayTime(Timestamp teamPayTime) {
		this.teamPayTime = teamPayTime;
	}


	public ReGoodsorder getReGoodsorder() {
		return reGoodsorder;
	}


	public void setReGoodsorder(ReGoodsorder reGoodsorder) {
		this.reGoodsorder = reGoodsorder;
	}


	public Integer getWebType() {
		return webType;
	}


	public void setWebType(Integer webType) {
		this.webType = webType;
	}


	public Integer getIsMerge() {
		return isMerge;
	}


	public void setIsMerge(Integer isMerge) {
		this.isMerge = isMerge;
	}


	public Double getWalletPay() {
		return walletPay;
	}


	public void setWalletPay(Double walletPay) {
		this.walletPay = walletPay;
	}
	
	public Integer getIsGame() {
		return isGame;
	}


	public void setIsGame(Integer isGame) {
		this.isGame = isGame;
	}


	@Override
	public String toString() {
		return "ReGoodsorder [id=" + id + ", isValid=" + isValid
				+ ", createTime=" + createTime + ", consignmentTime="
				+ consignmentTime + ", orderCode=" + orderCode + ", payPrice="
				+ payPrice + ", payScore=" + payScore + ", payCashpoint="
				+ payCashpoint + ", cashBack=" + cashBack + ", payType="
				+ payType + ", payAccount=" + payAccount + ", accountName="
				+ accountName + ", realname=" + realname + ", address="
				+ address + ", phone=" + phone + ", status=" + status
				+ ", message=" + message + ", logisticsCompay="
				+ logisticsCompay + ", logisticsType=" + logisticsType
				+ ", logisticsCode=" + logisticsCode + ", logistics="
				+ logistics + ", sellerPhone=" + sellerPhone
				+ ", sellerAddress=" + sellerAddress + ", backGoodsAddress="
				+ backGoodsAddress + ", confirmtime=" + confirmtime + ", code="
				+ code + ", ewmImg=" + ewmImg + ", totalFee=" + totalFee
				+ ", transactionId=" + transactionId + ", outTradeNo="
				+ outTradeNo + ", timeEnd=" + timeEnd + ", openid=" + openid
				+ ", alipayCode=" + alipayCode + ", weixinCode=" + weixinCode
				+ ", walletPay=" + walletPay + ", isMerge=" + isMerge
				+ ", webType=" + webType + ", isTeam=" + isTeam + ", teamNum="
				+ teamNum + ", teamPayTime=" + teamPayTime + ", reGoodsorder="
				+ reGoodsorder + ", visitor=" + visitor + ", user=" + user
				+ ", seller=" + seller + ", items=" + items + ", isHasItems="
				+ isHasItems + "]";
	}

	
}