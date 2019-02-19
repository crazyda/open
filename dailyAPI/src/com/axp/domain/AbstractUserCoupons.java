package com.axp.domain;

import java.sql.Timestamp;

/**
 * AbstractUserCoupons entity provides the base persistence definition of the
 * UserCoupons entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractUserCoupons implements java.io.Serializable {

	// Fields

	private Integer id;
	private Boolean isValid;
	private Timestamp createTime;
	private Users users;
	private Seller seller;
	private ReGoodsofextendmall ticket;

	private Boolean isUse;

	private String goodsName;

	private Double ticketprice;

	private Timestamp validtime;

	private String goodsMall;

	private Integer transportationType;
	private String sellerAddress;
	private Integer sign;
	private Double commission;
	private Users share;
	private Boolean isRed;
	private AdminuserRedpaper adminuserRedpaper;
	private ReGoodsOfExtendActiviy reGoodsOfExtendActiviy;
	/** default constructor */
	public AbstractUserCoupons() {
	}
	public AbstractUserCoupons(Integer id, Boolean isValid,
			Timestamp createTime, Users users, Seller seller,
			ReGoodsofextendmall ticket, Boolean isUse, String goodsName,
			Double ticketprice, Timestamp validtime, String goodsMall,
			Integer transportationType, String sellerAddress, Integer sign) {
		super();
		this.id = id;
		this.isValid = isValid;
		this.createTime = createTime;
		this.users = users;
		this.seller = seller;
		this.ticket = ticket;
		this.isUse = isUse;
		this.goodsName = goodsName;
		this.ticketprice = ticketprice;
		this.validtime = validtime;
		this.goodsMall = goodsMall;
		this.transportationType = transportationType;
		this.sellerAddress = sellerAddress;
		this.sign = sign;
	}
	public AdminuserRedpaper getAdminuserRedpaper() {
		return adminuserRedpaper;
	}
	public Double getCommission() {
		return commission;
	}
	public Timestamp getCreateTime() {
		return this.createTime;
	}
	public String getGoodsMall() {
		return this.goodsMall;
	}
	
	public String getGoodsName() {
		return this.goodsName;
	}

	public Integer getId() {
		return this.id;
	}

	public Boolean getIsRed() {
		return isRed;
	}

	public Boolean getIsUse() {
		return this.isUse;
	}

	public Boolean getIsValid() {
		return this.isValid;
	}

	public ReGoodsOfExtendActiviy getReGoodsOfExtendActiviy() {
		return reGoodsOfExtendActiviy;
	}

	public Seller getSeller() {
		return seller;
	}

	// Constructors

	

	// Property accessors

	public String getSellerAddress() {
		return this.sellerAddress;
	}

	public Users getShare() {
		return share;
	}

	public Integer getSign() {
		return sign;
	}

	public ReGoodsofextendmall getTicket() {
		return ticket;
	}

	public Double getTicketprice() {
		return this.ticketprice;
	}

	public Integer getTransportationType() {
		return this.transportationType;
	}


	public Users getUsers() {
		return users;
	}


	public Timestamp getValidtime() {
		return this.validtime;
	}


	public void setAdminuserRedpaper(AdminuserRedpaper adminuserRedpaper) {
		this.adminuserRedpaper = adminuserRedpaper;
	}


	public void setCommission(Double commission) {
		this.commission = commission;
	}


	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}


	public void setGoodsMall(String goodsMall) {
		this.goodsMall = goodsMall;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	

	

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIsRed(Boolean isRed) {
		this.isRed = isRed;
	}

	public void setIsUse(Boolean isUse) {
		this.isUse = isUse;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public void setReGoodsOfExtendActiviy(
			ReGoodsOfExtendActiviy reGoodsOfExtendActiviy) {
		this.reGoodsOfExtendActiviy = reGoodsOfExtendActiviy;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}

	public void setShare(Users share) {
		this.share = share;
	}

	public void setSign(Integer sign) {
		this.sign = sign;
	}

	public void setTicket(ReGoodsofextendmall ticket) {
		this.ticket = ticket;
	}

	public void setTicketprice(Double ticketprice) {
		this.ticketprice = ticketprice;
	}

	public void setTransportationType(Integer transportationType) {
		this.transportationType = transportationType;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public void setValidtime(Timestamp validtime) {
		this.validtime = validtime;
	}

}