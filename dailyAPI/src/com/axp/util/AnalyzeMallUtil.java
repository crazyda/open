package com.axp.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.axp.dao.DateBaseDAO;
import com.axp.domain.ReGoodsOfLocalSpecialtyMall;
import com.axp.domain.ReGoodsOfLockMall;
import com.axp.domain.ReGoodsOfMemberMall;
import com.axp.domain.ReGoodsOfNineNineMall;
import com.axp.domain.ReGoodsOfScoreMall;
import com.axp.domain.ReGoodsOfSeckillMall;
import com.axp.domain.ReGoodsOfSellerMall;
import com.axp.domain.ReGoodsOfTeamMall;

/**
 * 解析板块
 * @author Administrator
 *
 */
@Repository
public class AnalyzeMallUtil {

	@Autowired
	private DateBaseDAO dateBaseDAO;

	public static final String SellerMall = "sem";
	public static final String ScoreMall = "scm";
	public static final String SeckillMall = "skm";
	public static final String LocalSpecialtyMall = "lsm";
	public static final String NineNineMall = "nnm";
	public static final String MemberMall = "mem";
	public static final String teamMall="tiAm";
	public static final String LockMall = "ldm";
	public static final Map<String,Class> classMap = null;
	
	@SuppressWarnings("rawtypes")
	public Map<String,Class> getClassName(){
		if(classMap==null){
			classMap.put(SellerMall, ReGoodsOfSellerMall.class);
			classMap.put(ScoreMall, ReGoodsOfScoreMall.class);
			classMap.put(SeckillMall, ReGoodsOfSeckillMall.class);
			classMap.put(LocalSpecialtyMall, ReGoodsOfLocalSpecialtyMall.class);
			classMap.put(NineNineMall, ReGoodsOfNineNineMall.class);
			classMap.put(MemberMall, ReGoodsOfMemberMall.class);
		}
		return classMap;
	}

	/**
	 * 根据六个商城商品的goodsOrder字段获取唯一的商城商品对象；
	 * 步骤：
	 * 1，首先根绝goodsOrder的前三个字符获取商城；
	 * 2，获取这个商品对象；
	 * @param id
	 * @return
	 */
	public Object getMall(String goodsOrder) {

		String mall = getMallType(goodsOrder);
		Integer goodsId = getMallValue(goodsOrder);

		QueryModel queryModel = new QueryModel();
		switch (mall) {
		case SellerMall:
			queryModel.combPreEquals("id", goodsId);
			return dateBaseDAO.findOne(ReGoodsOfSellerMall.class, queryModel);
		case ScoreMall:
			queryModel.combPreEquals("id", goodsId);
			return dateBaseDAO.findOne(ReGoodsOfScoreMall.class, queryModel);
		case SeckillMall:
			queryModel.combPreEquals("id", goodsId);
			return dateBaseDAO.findOne(ReGoodsOfSeckillMall.class, queryModel);
		case LocalSpecialtyMall:
			queryModel.combPreEquals("id", goodsId);
			return dateBaseDAO.findOne(ReGoodsOfLocalSpecialtyMall.class, queryModel);
		case NineNineMall:
			queryModel.combPreEquals("id", goodsId);
			return dateBaseDAO.findOne(ReGoodsOfNineNineMall.class, queryModel);
		case MemberMall:
			queryModel.combPreEquals("id", goodsId);
			return dateBaseDAO.findOne(ReGoodsOfMemberMall.class, queryModel);
		case teamMall:
			queryModel.combPreEquals("id", goodsId);
			return dateBaseDAO.findOne(ReGoodsOfTeamMall.class, queryModel);
		case LockMall:
			queryModel.combPreEquals("id", goodsId);
			return dateBaseDAO.findOne(ReGoodsOfLockMall.class, queryModel);
		default:
			break;
		}
		return null;
	}

	/**
	 * 获取代表商家类型的字符串；
	 * @param id
	 * @return
	 */
	public String getMallType(String goodsOrder) {
		return goodsOrder.substring(0, 3);
	}

	/**
	 * 获取商品id值；
	 * @param id
	 * @return
	 */
	public Integer getMallValue(String id) {
		return Integer.parseInt(id.substring(3));
	}
}
