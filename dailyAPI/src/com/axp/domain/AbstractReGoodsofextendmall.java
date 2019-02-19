package com.axp.domain;

import java.sql.Timestamp;


/**
 * AbstractReGoodsofextendmall entity provides the base persistence definition
 * of the ReGoodsofextendmall entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractReGoodsofextendmall  extends ReBaseGoods implements
		java.io.Serializable {

	// Fields

	private Integer id;
	private Boolean isValid;
	private Timestamp createTime;
	private String name;
	private String coverPic;
	private String descriptionPics;
	private Double price;
	private Boolean isChecked;
	private String type;
	private String lables;
	private Double ticketprice;
	private Integer ticketnum;
	private Integer coupons;
	private Timestamp validtime;
	private Integer mall;
	private Integer mallId;
	private ProvinceEnum provinceEnum;
	
	private Seller seller;

	private Double commission;

	private Integer sign;

	private String goodsMall;

	private CommodityType commodityType;
	
	private Integer isActivity;

	private AdminuserRedpaper adminuserRedpaper;

	private Integer isRed;

	private ReGoodsofextendmall reGoodsofextendmall;
	
	private String checkDesc;//审核描述；
	
	//da
	private String goodsOrder;
	
	
	
	
	public String getGoodsOrder() {
		return goodsOrder;
	}

	public void setGoodsOrder(String goodsOrder) {
		this.goodsOrder = goodsOrder;
	}

	public String getCheckDesc() {
		return checkDesc;
	}

	public void setCheckDesc(String checkDesc) {
		this.checkDesc = checkDesc;
	}

	public Integer getIsActivity() {
		return isActivity;
	}

	public Boolean getIsChecked() {
		return isChecked;
	}




	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}



	public void setIsActivity(Integer isActivity) {
		this.isActivity = isActivity;
	}




	public AdminuserRedpaper getAdminuserRedpaper() {
		return adminuserRedpaper;
	}




	public void setAdminuserRedpaper(AdminuserRedpaper adminuserRedpaper) {
		this.adminuserRedpaper = adminuserRedpaper;
	}




	public Integer getIsRed() {
		return isRed;
	}




	public void setIsRed(Integer isRed) {
		this.isRed = isRed;
	}




	public ReGoodsofextendmall getReGoodsofextendmall() {
		return reGoodsofextendmall;
	}




	public void setReGoodsofextendmall(ReGoodsofextendmall reGoodsofextendmall) {
		this.reGoodsofextendmall = reGoodsofextendmall;
	}




	public AbstractReGoodsofextendmall(Integer id, Boolean isValid,
			Timestamp createTime, String name, String coverPic,
			String descriptionPics, Double price, String type, String lables,
			Double ticketprice, Integer ticketnum, Integer coupons,
			Timestamp validtime, Integer mall, Integer mallId,
			ProvinceEnum provinceEnum, Seller seller, Double commission,
			Integer sign, String goodsMall, CommodityType commodityType,String goodsOrder) {
		super();
		this.id = id;
		this.isValid = isValid;
		this.createTime = createTime;
		this.name = name;
		this.coverPic = coverPic;
		this.descriptionPics = descriptionPics;
		this.price = price;
		this.type = type;
		this.lables = lables;
		this.ticketprice = ticketprice;
		this.ticketnum = ticketnum;
		this.coupons = coupons;
		this.validtime = validtime;
		this.mall = mall;
		this.mallId = mallId;
		this.provinceEnum = provinceEnum;
		this.seller = seller;
		this.commission = commission;
		this.sign = sign;
		this.goodsMall = goodsMall;
		this.commodityType = commodityType;
		this.goodsOrder = goodsOrder;
	}




	public CommodityType getCommodityType() {
		return commodityType;
	}




	public void setCommodityType(CommodityType commodityType) {
		this.commodityType = commodityType;
	}




	/** default constructor */
	public AbstractReGoodsofextendmall() {
	}

	


	public Double getCommission() {
		return this.commission;
	}
	
	public Integer getCoupons() {
		return this.coupons;
	}
	public String getCoverPic() {
		return this.coverPic;
	}
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	// Constructors

	public String getDescriptionPics() {
		return this.descriptionPics;
	}

	
	// Property accessors

	public String getGoodsMall() {
		return this.goodsMall;
	}

	public Integer getId() {
		return this.id;
	}

	public Boolean getIsValid() {
		return this.isValid;
	}

	public String getLables() {
		return this.lables;
	}

	public Integer getMall() {
		return this.mall;
	}

	public Integer getMallId() {
		return this.mallId;
	}

	public String getName() {
		return this.name;
	}

	public Double getPrice() {
		return this.price;
	}

	public ProvinceEnum getProvinceEnum() {
		return provinceEnum;
	}

	public Seller getSeller() {
		return seller;
	}

	public Integer getSign() {
		return this.sign;
	}

	public Integer getTicketnum() {
		return this.ticketnum;
	}

	public Double getTicketprice() {
		return this.ticketprice;
	}

	public String getType() {
		return this.type;
	}

	public Timestamp getValidtime() {
		return this.validtime;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}


	public void setCoupons(Integer coupons) {
		this.coupons = coupons;
	}

	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public void setDescriptionPics(String descriptionPics) {
		this.descriptionPics = descriptionPics;
	}

	public void setGoodsMall(String goodsMall) {
		this.goodsMall = goodsMall;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public void setLables(String lables) {
		this.lables = lables;
	}

	public void setMall(Integer mall) {
		this.mall = mall;
	}

	public void setMallId(Integer mallId) {
		this.mallId = mallId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setProvinceEnum(ProvinceEnum provinceEnum) {
		this.provinceEnum = provinceEnum;
	}

	

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public void setSign(Integer sign) {
		this.sign = sign;
	}

	public void setTicketnum(Integer ticketnum) {
		this.ticketnum = ticketnum;
	}

	public void setTicketprice(Double ticketprice) {
		this.ticketprice = ticketprice;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValidtime(Timestamp validtime) {
		this.validtime = validtime;
	}

	
}