package com.axp.domain;

/**
 * 当地特产商城的商品表；
 * @author Administrator
 *
 */
public class ReGoodsOfLocalSpecialtyMall extends ReBaseGoods {
	private Double cashBack = 0d;//返现
	private String placeOfProduction;//所在产地
	private ProvinceEnum provinceEnum;//所在产地Id
	private String pack;//包装
	private Integer sales;//默认销量
	private Integer fenyong;
	private Boolean istop;
	private CommodityType commodityType;//所在类别
	
	
	
	
	public CommodityType getCommodityType() {
		return commodityType;
	}

	public void setCommodityType(CommodityType commodityType) {
		this.commodityType = commodityType;
	}

	public Boolean getIstop() {
		return istop;
	}

	public void setIstop(Boolean istop) {
		this.istop = istop;
	}

	public Integer getFenyong() {
		return fenyong;
	}

	public void setFenyong(Integer fenyong) {
		this.fenyong = fenyong;
	}

	public Double getCashBack() {
		return cashBack==null?0d:cashBack;
	}

	public void setCashBack(Double cashBack) {
		this.cashBack = cashBack;
	}

	public String getPlaceOfProduction() {
		return placeOfProduction;
	}

	public void setPlaceOfProduction(String placeOfProduction) {
		this.placeOfProduction = placeOfProduction;
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public ProvinceEnum getProvinceEnum() {
		return provinceEnum;
	}

	public void setProvinceEnum(ProvinceEnum provinceEnum) {
		this.provinceEnum = provinceEnum;
	}

	public Integer getSales() {
		return sales;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
	}
	
}
