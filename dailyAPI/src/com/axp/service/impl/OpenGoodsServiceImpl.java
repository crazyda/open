package com.axp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.axp.dao.DateBaseDAO;
import com.axp.domain.OpenGoods;
import com.axp.service.OpenGoodsService;
import com.axp.util.MD5Util;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;
import com.axp.util.UrlUtil;

@Service
public class OpenGoodsServiceImpl extends BaseServiceImpl<OpenGoods> implements OpenGoodsService{
	
	@Resource
	private DateBaseDAO dateBaseDAO;

	@Override
	public Map<String, Object> saveGoods(Map<String, Object> statusMap) {
		List<Map<String,Object>>  products = (List<Map<String, Object>>) ((Map<String, Object>) statusMap.get("result")).get("products");
		List<Object> goodsId = new ArrayList<Object>();
		for(int i=0;i<products.size();i++){
			goodsId.add("'"+products.get(0).get("goods_id")+"'");
		}
		QueryModel query = new QueryModel();
		query.clearQuery();
		query.combCondition("goodsId in"+"("+goodsId+")");
		dateBaseDAO.findLists(OpenGoods.class, query);
		for(int i=0;i<products.size();i++){
			query.clearQuery();
			query.combPreEquals("goodsId", products.get(0).get("goods_id").toString(),"goods_id");
			if(dateBaseDAO.findOne(OpenGoods.class, query) == null){
				OpenGoods  goods = new OpenGoods();
				goods.setCategoryId((Integer)products.get(i).get("category_id"));
				goods.setCategoryName((String)products.get(i).get("category_name"));
				goods.setCouponDiscount((String)products.get(i).get("coupon_discount"));
				goods.setCouponEndTime((String)products.get(i).get("coupon_end_time"));
				goods.setCouponMinOrderAmount((String)products.get(i).get("coupon_min_order_amount"));
				goods.setCouponRemainQuantity((Integer)products.get(i).get("coupon_remain_quantity"));
				goods.setCouponStartTime((String)products.get(i).get("coupon_start_time"));
				goods.setCouponTotalQuantity((Integer)products.get(i).get("coupon_total_quantity"));
				goods.setGoodsEvalCount((String)products.get(i).get("goods_eval_count"));
				goods.setGoodsEvalScore("5"+"");//商品评分
				goods.setGoodsId((String)products.get(i).get("goods_id"));
				goods.setGoodsImageUrl((String)products.get(i).get("goods_image_url"));
				goods.setGoodsName((String)products.get(i).get("goods_name"));
				goods.setGoodsThumbnailUrl((String)products.get(i).get("goods_thumbnail_url"));
				goods.setMallName((String)products.get(i).get("mall_name"));
				goods.setMinGroupPrice((Integer)products.get(i).get("min_group_price"));
				goods.setMinNormalPrice((Integer)products.get(i).get("min_normal_price"));
				goods.setPromotionRate((Integer)products.get(i).get("promotion_rate")+"");
				goods.setSoldQuantity((Integer)products.get(i).get("sold_quantity"));
				openGoodsDao.save(goods);
			}else{
				
			}
			
		}
		
		
		return null;
	}

	@Override
	public Map<String, Object> products(HttpServletRequest request) {
		
		//Parameter parameter = ParameterUtil.getParameter(request);
		//Integer pageIndex=(parameter.getData().getInteger("pageIndex")==null?1:parameter.getData().getInteger("pageIndex"));
		Map<String, Object> statusMap = new HashMap<String, Object>();
		
		Map<String,String> signMap = new HashMap<String,String>();
		String client_id = "68bf2d0655473ec3ff6670394fbf5f26";
		String client_screct = "bcffc94d10b6d4f9913af7c52a88a130";
		signMap.put("client_id", client_id);
		signMap.put("limit", "1000");
		signMap.put("page","1");
		String sign = MD5Util.getSign(signMap, client_screct);		
		statusMap = UrlUtil.getTaoKeToMap("http://open.aixiaoping.cn:8080/openAPI/api/product?data={\"client_id\":\""+client_id+"\",\"sign\":\""+sign+"\",\"data\":{\"limit\":\"1000\",\"page\":\"1\"}}");
		List<Map<String,Object>>  products = (List<Map<String, Object>>) ((Map<String, Object>) statusMap.get("result")).get("products");
		
		for(int i=0;i<products.size();i++){
				OpenGoods  goods = new OpenGoods();
				goods.setCategoryId((Integer)products.get(i).get("category_id"));
				goods.setCategoryName((String)products.get(i).get("category_name"));
				goods.setCouponDiscount((String)products.get(i).get("coupon_discount"));
				goods.setCouponEndTime((String)products.get(i).get("coupon_end_time"));
				goods.setCouponMinOrderAmount((String)products.get(i).get("coupon_min_order_amount"));
				goods.setCouponRemainQuantity((Integer)products.get(i).get("coupon_remain_quantity"));
				goods.setCouponStartTime((String)products.get(i).get("coupon_start_time"));
				goods.setCouponTotalQuantity((Integer)products.get(i).get("coupon_total_quantity"));
				goods.setGoodsEvalCount((String)products.get(i).get("goods_eval_count"));
				goods.setGoodsEvalScore("5"+"");//商品评分
				goods.setGoodsId((String)products.get(i).get("goods_id"));
				goods.setGoodsImageUrl((String)products.get(i).get("goods_image_url"));
				goods.setGoodsName((String)products.get(i).get("goods_name"));
				goods.setGoodsThumbnailUrl((String)products.get(i).get("goods_thumbnail_url"));
				goods.setMallName((String)products.get(i).get("mall_name"));
				goods.setMinGroupPrice((Integer)products.get(i).get("min_group_price"));
				goods.setMinNormalPrice((Integer)products.get(i).get("min_normal_price"));
				goods.setPromotionRate((Integer)products.get(i).get("promotion_rate")+"");
				goods.setSoldQuantity((Integer)products.get(i).get("sold_quantity"));
				openGoodsDao.saveOrUpdate(goods);;
				
			}
			
		
		return null;
	}

	@Override
	public Map<String, Object> findGoods(HttpServletRequest request) {
		Parameter parameter = ParameterUtil.getParameter(request);
		Integer pageIndex=(parameter.getData().getInteger("pageIndex")==null?1:parameter.getData().getInteger("pageIndex"));
		Integer pageSize=(parameter.getData().getInteger("pageSize")==null?1:parameter.getData().getInteger("pageSize"));
		String keyword = parameter.getData().getString("keyword");
		Map<String, Object> statusMap = new HashMap<String, Object>();
		
		QueryModel query = new QueryModel();
		query.clearQuery();
		query.combPreLike("goodsName", keyword);
		List<OpenGoods> goodsList = dateBaseDAO.findPageList(OpenGoods.class, query, pageIndex, pageSize);
		statusMap.put("goodsList", goodsList);
		
		return statusMap;
	}
	
	

}
	
