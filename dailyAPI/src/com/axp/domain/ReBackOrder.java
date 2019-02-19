package com.axp.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * ReBlackOrder entity. @author MyEclipse Persistence Tools
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class ReBackOrder implements java.io.Serializable {

	// Fields

	private Integer id;
	private String backCode;//退单编码
	private Boolean isValid;
	private Timestamp createTime;//创建时间
	private Timestamp auditTime;//审核时间
	private Timestamp drawbackTime;//退款时间
	private Users user;
	private ReGoodsorderItem orderItem;
	private Integer goodid;//唯一商品id
	private Integer goodQuantity;//退单商品数量
	private Integer sellerCode;//商家编码
	private Integer mallId;//模块id
	private String mallClass;//模块
	private Double backmoney=0d;//实际支付金额
	private Double paymoney=0d;//退单金额
	private Integer payScore =0;//订单积分
	private Double payCashpoint=0d;//订单红包
	private String checkMessage;//审核意见
	private Seller seller;//
	private Integer backtype;//退款类型
	private Integer backstate;//退单状态
	private Integer paytype;//支付类型
	private String payAccout;//支付账号
	private String accountName;//支付账号真实姓名
	private String reason;//退单原因
	private String image;//退单图片
	private Integer type;//退款途径
	private Integer exOrderStatus;//退单前订单状态
	
	private String basePath = "";
	
	private BackOrderStatus backOrderStatus;
	private BackOrderVerify backOrderVerify;
	
	private Integer drawbackWay;
	
	//退款类型
	public static Integer BACKTYPR_yuanlufanhui = 0;// 原路返回
	public static Integer BACKTYPR_axp = 100;// 每天积分钱包
	public static Integer BACKTYPR_zhifubao = 200;// 支付宝
	public static Integer BACKTYPR_weixin = 300;// 微信

	//退单状态
	public static Integer BACKSTATE_buketuidan = -30;//不可退单
	public static Integer BACKSTATE_butongguo  = -15;//审核不通过
	public static Integer BACKSTATE_weiqueren = 0;//商家未确认，直接退款
	public static Integer BACKSTATE_daishenhe = 10;//待审核
	public static Integer BACKSTATE_yishenhe = 20;// 已审核
	public static Integer BACKSTATE_yizhifu = 30;// 已支付
	public static Integer BACKSTATE_tuidanwancheng = 40;// 退单完成
	//支付类型
	public static Integer PAYTYPE_zfb = 10;//支付宝
	public static Integer PAYTYPE_wx = 20;// 微信
	public static Integer PAYTYPE_axp = 30;// 每天积分钱包
	//退款途径
	public static Integer TYPE_yuanlufanhui = 10;//原路返回
	public static Integer TYPE_axp = 20;// 我的钱包

	public String getStatusName(Integer status){
		if(status==null){
			return null;
		}else if(status==BACKSTATE_butongguo){
			return "审核不通过";
		}else if(status==BACKSTATE_weiqueren){
			return "商家未确认，直接退款";
		}else if(status==BACKSTATE_daishenhe){
			return "待审核";
		}else if(status==BACKSTATE_yishenhe){
			return "已审核";
		}else if(status==BACKSTATE_yizhifu){
			return "已支付";
		}else if(status==BACKSTATE_tuidanwancheng){
			return "退单完成";
		}
		return null;
	}
	
	
	// Constructors

	/** default constructor */
	public ReBackOrder() {
	}

	
	// Property accessors
	@JsonProperty("backOrderItemId")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBackCode() {
		return this.backCode;
	}

	public void setBackCode(String backCode) {
		this.backCode = backCode;
	}
	@JsonIgnore
	public Boolean getIsValid() {
		return this.isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
	@JsonProperty("date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")  
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	@JsonIgnore
	public Users getUser() {
		return user;
	}


	public void setUser(Users user) {
		this.user = user;
	}
	@JsonIgnore
	public ReGoodsorderItem getOrderItem() {
		return orderItem;
	}


	public void setOrderItem(ReGoodsorderItem orderItem) {
		this.orderItem = orderItem;
	}

	@JsonIgnore
	public Integer getGoodid() {
		return this.goodid;
	}

	public void setGoodid(Integer goodid) {
		this.goodid = goodid;
	}
	@JsonIgnore
	public Integer getGoodQuantity() {
		return this.goodQuantity;
	}

	public void setGoodQuantity(Integer goodQuantity) {
		this.goodQuantity = goodQuantity;
	}
	@JsonIgnore
	public Integer getSellerCode() {
		return this.sellerCode;
	}

	public void setSellerCode(Integer sellerCode) {
		this.sellerCode = sellerCode;
	}
	@JsonIgnore
	public Integer getMallId() {
		return this.mallId;
	}

	public void setMallId(Integer mallId) {
		this.mallId = mallId;
	}
	@JsonIgnore
	public String getMallClass() {
		return this.mallClass;
	}

	public void setMallClass(String mallClass) {
		this.mallClass = mallClass;
	}
	@JsonProperty("money")
	public Double getBackmoney() {
		return backmoney;
	}

	public void setBackmoney(Double backmoney) {
		this.backmoney = backmoney;
	}
	@JsonIgnore
	public Double getPaymoney() {
		return this.paymoney;
	}

	public void setPaymoney(Double paymoney) {
		this.paymoney = paymoney;
	}
	@JsonIgnore
	public String getCheckMessage() {
		return this.checkMessage;
	}

	public void setCheckMessage(String checkMessage) {
		this.checkMessage = checkMessage;
	}
	@JsonIgnore
	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	@JsonIgnore
	public Integer getBacktype() {
		return this.backtype;
	}

	public void setBacktype(Integer backtype) {
		this.backtype = backtype;
	}
	
	public Integer getExOrderStatus() {
		return exOrderStatus;
	}


	public void setExOrderStatus(Integer exOrderStatus) {
		this.exOrderStatus = exOrderStatus;
	}


	@JsonInclude(Include.NON_NULL) 
	public Integer getDrawbackWay() {
		if(this.type!=null){ //退款方式	0：原路返回;100:钱包;  以前是10 20 30  现在是 0 和100
			if(paytype.equals(ReGoodsorder.PAYTYPE_zfb))
				drawbackWay=200;
			else if(paytype.equals(ReGoodsorder.PAYTYPE_wx))
				drawbackWay=300;
			else if(paytype.equals(ReGoodsorder.PAYTYPE_axp))
				drawbackWay=100;
			else if(paytype.equals(ReGoodsorder.PAYTYPE_yl))
				drawbackWay=400;
		}
		return drawbackWay;
	}


	public void setDrawbackWay(Integer drawbackWay) {
		this.drawbackWay = drawbackWay;
	}

	@JsonIgnore
	public Integer getBackstate() {
		return this.backstate;
	}

	public void setBackstate(Integer backstate) {
		this.backstate = backstate;
	}
	@JsonIgnore
	public Integer getPaytype() {
		return this.paytype;
	}

	public void setPaytype(Integer paytype) {
		this.paytype = paytype;
	}
	@JsonIgnore
	public String getPayAccout() {
		return this.payAccout;
	}

	public void setPayAccout(String payAccout) {
		this.payAccout = payAccout;
	}
	@JsonIgnore
	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	@JsonProperty("content")
	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	@JsonIgnore
	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	@JsonIgnore
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@JsonProperty("score")
	public Integer getPayScore() {
		return payScore;
	}


	public void setPayScore(Integer payScore) {
		this.payScore = payScore;
	}

	@JsonIgnore
	public Double getPayCashpoint() {
		return payCashpoint;
	}


	public void setPayCashpoint(Double payCashpoint) {
		this.payCashpoint = payCashpoint;
	}
	
	@JsonIgnore
	public Timestamp getAuditTime() {
		return auditTime;
	}


	public void setAuditTime(Timestamp auditTime) {
		this.auditTime = auditTime;
	}

	@JsonIgnore
	public Timestamp getDrawbackTime() {
		return drawbackTime;
	}


	public void setDrawbackTime(Timestamp drawbackTime) {
		this.drawbackTime = drawbackTime;
	}
	@JsonInclude(Include.NON_NULL) 
	@JsonProperty("images")
	public List<String> getImgList(){
		if(this.image!=null){
			List<JSONObject> list = JSONArray.parseArray(this.image,JSONObject.class);
			List<String> imgList = new ArrayList<>();
			for(JSONObject jsonObject :list ){
				imgList.add(basePath+jsonObject.getString("imgUrl"));
			}
			return imgList;
		}
		return null;
		
	}
	
	@JsonIgnore
	public BackOrderStatus getBackOrderStatus() {
		if(backOrderStatus == null){
			backOrderStatus = new BackOrderStatus();
		}
		return backOrderStatus;
	}


	public void setBackOrderStatus(BackOrderStatus backOrderStatus) {
		this.backOrderStatus = backOrderStatus;
	}

	@JsonIgnore
	public BackOrderVerify getBackOrderVerify() {
		if(backOrderVerify == null){
			backOrderVerify = new BackOrderVerify();
		}
		return backOrderVerify;
	}


	public void setBackOrderVerify(BackOrderVerify backOrderVerify) {
		this.backOrderVerify = backOrderVerify;
	}



	//退单审核信息对象
	public class BackOrderVerify{
		private Integer backOrderItemId;
		private String replyContent;
		private Timestamp date;
		private Integer result;
		private Double money;
		
		public BackOrderVerify(){
			this.backOrderItemId = id;
			this.replyContent = checkMessage;
			this.date = auditTime;
			this.money = backmoney;
			
			if(backstate==BACKSTATE_butongguo){
				this.result = 10;
			}else if(backstate==BACKSTATE_buketuidan){
				this.result = 40;
			}else if(backstate>=BACKSTATE_yishenhe){
				this.result = 30;
			}
		}
		
		public Double getMoney() {
			return money;
		}

		public void setMoney(Double money) {
			this.money = money;
		}

		public Integer getBackOrderItemId() {
			return backOrderItemId;
		}
		public void setBackOrderItemId(Integer backOrderItemId) {
			this.backOrderItemId = backOrderItemId;
		}
		@JsonInclude(Include.NON_NULL) 
		public String getReplyContent() {
			return replyContent;
		}
		public void setReplyContent(String replyContent) {
			this.replyContent = replyContent;
		}
		@JsonInclude(Include.NON_NULL) 
		@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
		public Timestamp getDate() {
			return date;
		}
		public void setDate(Timestamp date) {
			this.date = date;
		}
		@JsonInclude(Include.NON_NULL) 
		public Integer getResult() {
			return result;
		}
		public void setResult(Integer result) {
			this.result = result;
		}
	}
	
	//退单状态
	public class BackOrderStatus{
		private String name;
		private Integer statusId;
		
		public BackOrderStatus(){
			this.name = getStatusName(backstate);
			this.statusId = backstate;
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getStatusId() {
			return statusId;
		}
		public void setStatusId(Integer statusId) {
			this.statusId = statusId;
		}
		
	}
	@JsonIgnore
	public String getBasePath() {
		return basePath;
	}


	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	
	
	
	/*public String getUserReason(){
		if(this.reason!=null&&StringUtils.isNotBlank(this.reason)){
			JSONObject jsonObject = JSONObject.parseObject(this.reason);
			return jsonObject.getString("reason");
		}
		return null;
	}
	public String getReply(){
		if(this.reason!=null&&StringUtils.isNotBlank(this.reason)){
			JSONObject jsonObject = JSONObject.parseObject(this.reason);
			return jsonObject.getString("reply");
		}
		return null;
	}*/
}