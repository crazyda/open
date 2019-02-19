/**
 * 2018-10-15
 * Administrator
 */
package com.axp.domain;

import java.sql.Timestamp;

/**
 * @author da
 * @data 2018-10-15下午5:32:32
 */
public class GameActivity extends AbstractGameActivity implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameActivity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GameActivity(Integer id, Timestamp createTime, Boolean isValid,
			Boolean isStart, Integer drawNum, Integer drawScore,
			Scoretypes drawYlassify, Integer score, Double chanceScore,
			ReGoodsOfSellerMall reGoodsOfSellerMall,
			CommodityType commodityType, String coverPics, String content,
			String oneScore, String detail, ProvinceEnum provinceEnum,
			AdminUser adminUser, AdminUser seller) {
		super(id, createTime, isValid, isStart, drawNum, drawScore, drawYlassify,
				score, chanceScore, reGoodsOfSellerMall, commodityType, coverPics,
				content, oneScore, detail, provinceEnum, adminUser, seller);
		// TODO Auto-generated constructor stub
	}

	

	

	


	
	
}
