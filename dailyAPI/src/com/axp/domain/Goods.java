package com.axp.domain;

import java.sql.Timestamp;

/**
 * Goods entity. @author MyEclipse Persistence Tools
 */
public class Goods extends AbstractGoods implements java.io.Serializable {

	private GoodsAuction goodsAuction;

	// Constructors

	/** default constructor */
	public Goods() {
	}

	/** minimal constructor */
	public Goods(String imgurl, String name, Integer needScore, Double money, Integer shopId, Boolean isvalid, Timestamp createtime,
			Integer tcount) {
		// super(imgurl, name, needScore, money, shopId, isvalid, createtime,
		// tcount);
	}

	/** full constructor */
	public Goods(String imgurl, String name, Integer needScore, Double money, Integer shopId, Boolean isvalid, Timestamp createtime,
			Integer tcount, String description, String remark) {
		// super(imgurl, name, needScore, money, shopId, isvalid, createtime,
		// / tcount, description, remark);
	}

	private String showtype;

	public void setShowtype(String showtype) {
		this.showtype = showtype;
	}

	public void setGoodsAuction(GoodsAuction goodsAuction) {
		this.goodsAuction = goodsAuction;
	}

}
