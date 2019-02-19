package com.axp.domain;

import java.sql.Timestamp;

/**
 * 积分商城的商品表；
 * @author Administrator
 *
 */
public class ReGoodsOfScoreMall extends ReBaseGoods {
    private Integer countLimit = 0;//限购数量；
    private Integer exchangeArea = 0;//兑换区域；
    private String desc = "";//兑换须知；
    private Integer releaseNum;//发布推广数量
    private String scoreGoodMoney;//购买积分商品需要的金币
    private Timestamp zhidingTime;//积分商品置顶
    private Timestamp zhiding_99;//99特惠置顶
    private Timestamp zhiding_hot;//人气推荐置顶
    /**
     * 兑换区域（默认为当地）
     */
    public static final Integer dangDi = 0;//当地
    public static final Integer quanGuo = 1;//全国

    /**
     * getter and setter
     */
    
    
    public Integer getCountLimit() {
        return countLimit;
    }

    public Timestamp getZhiding_99() {
		return zhiding_99;
	}

	public void setZhiding_99(Timestamp zhiding_99) {
		this.zhiding_99 = zhiding_99;
	}

	public Timestamp getZhiding_hot() {
		return zhiding_hot;
	}

	public void setZhiding_hot(Timestamp zhiding_hot) {
		this.zhiding_hot = zhiding_hot;
	}

	public Timestamp getZhidingTime() {
		return zhidingTime;
	}

	public void setZhidingTime(Timestamp zhidingTime) {
		this.zhidingTime = zhidingTime;
	}

	public Integer getReleaseNum() {
		return releaseNum;
	}

    
	public void setReleaseNum(Integer releaseNum) {
		this.releaseNum = releaseNum;
	}

	public void setCountLimit(Integer countLimit) {
        this.countLimit = countLimit;
    }

    public Integer getExchangeArea() {
        return exchangeArea;
    }

    public void setExchangeArea(Integer exchangeArea) {
        this.exchangeArea = exchangeArea;
    }

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getScoreGoodMoney() {
		return scoreGoodMoney;
	}

	public void setScoreGoodMoney(String scoreGoodMoney) {
		this.scoreGoodMoney = scoreGoodMoney;
	}

}
