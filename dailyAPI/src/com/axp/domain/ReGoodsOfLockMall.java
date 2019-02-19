package com.axp.domain;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;



/**
 * 游戏商城
 *
 * @author Administrator
 */
public class ReGoodsOfLockMall extends ReBaseGoods {

    private Integer peopleNum;//设定参与人数
  
    private String LockDesc;//抽奖须知
    private Integer releaseNum;//需要积分
    private Integer openYards;//开奖号码
    private CommodityType gameType;//游戏类型
    private String startTime267;
    private String endTime267;
    private String itemLastTime;//这个商品最后下单的时间
    private Set<CashshopTimes> timesId = new HashSet<>();  //倒计时时间范围
    private Integer openType; //开奖方式
    
    public Integer getOpenType() {
		return openType;
	}

	public void setOpenType(Integer openType) {
		this.openType = openType;
	}

	public Set<CashshopTimes> getTimesId() {
		return timesId;
	}

	public void setTimesId(Set<CashshopTimes> timesId) {
		this.timesId = timesId;
	}



	public String getItemLastTime() {
		return itemLastTime;
	}

	public void setItemLastTime(String itemLastTime) {
		this.itemLastTime = itemLastTime;
	}

	public String getStartTime267() {
		return startTime267;
	}

	public void setStartTime267(String startTime267) {
		this.startTime267 = startTime267;
	}

	public String getEndTime267() {
		return endTime267;
	}

	public void setEndTime267(String endTime267) {
		this.endTime267 = endTime267;
	}


	public CommodityType getGameType() {
		return gameType;
	}

	public void setGameType(CommodityType gameType) {
		this.gameType = gameType;
	}

	public Integer getOpenYards() {
		return openYards;
	}

	public void setOpenYards(Integer openYards) {
		this.openYards = openYards;
	}

	/**
     * 秒杀区域（默认为当地）
     */
    public static final Integer dangDi = 0;
    public static final Integer quanGuo = 1;

    
    

    public Integer getReleaseNum() {
		return releaseNum;
	}

	public void setReleaseNum(Integer releaseNum) {
		this.releaseNum = releaseNum;
	}
    
    public Integer getPeopleNum() {
        return peopleNum;
    }

	public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }


	public String getLockDesc() {
		return LockDesc;
	}

	public void setLockDesc(String lockDesc) {
		LockDesc = lockDesc;
	}

   
}
