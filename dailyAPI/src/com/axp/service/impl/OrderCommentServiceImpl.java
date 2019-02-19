package com.axp.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.axp.dao.DateBaseDAO;
import com.axp.dao.IReGoodsSnapshotDao;
import com.axp.dao.IUsersDao;
import com.axp.dao.OrderCommentDAO;
import com.axp.dao.ReGoodsorderDao;
import com.axp.dao.ReGoodsorderItemDao;
import com.axp.dao.SellerDAO;
import com.axp.dao.SellerMainPageDAO;
import com.axp.dao.impl.BaseDaoImpl;
import com.axp.domain.OrderComment;
import com.axp.domain.OrderCommentByJson;
import com.axp.domain.ReBaseGoods;
import com.axp.domain.ReGoodsSnapshot;
import com.axp.domain.ReGoodsorder;
import com.axp.domain.ReGoodsorderItem;
import com.axp.domain.Seller;
import com.axp.domain.SellerMainPage;
import com.axp.domain.Users;
import com.axp.service.IReGoodsOfBaseService;
import com.axp.service.OrderCommentService;
import com.axp.util.DateUtil;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;
import com.axp.util.StringUtil;
import com.axp.util.UrlUtil;
import com.vdurmont.emoji.EmojiParser;

@Service
public class OrderCommentServiceImpl extends BaseServiceImpl<OrderComment> implements
		OrderCommentService {
	@Autowired
	private OrderCommentDAO orderCommentDAO;
	@Autowired
	private ReGoodsorderItemDao reGoodsorderItemDao;
	@Autowired
	private DateBaseDAO dateBaseDAO;
	@Autowired
	private ReGoodsorderDao reGoodsorderDao;
	@Autowired
	private IUsersDao usersDao;
	@Autowired
	private SellerDAO sellerDao;
	@Resource
	private IReGoodsOfBaseService reGoodsOfBaseService;
	@Autowired
	private IReGoodsSnapshotDao snapshotDao;
	
	@Autowired
	private SellerMainPageDAO sellerMainPageDao;
	
	@Override
	public Map<String, Object> getComment(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
		if (parameter == null) {//错误的参数；
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		Integer commentId = parameter.getData().getInteger("commentId");
		OrderComment comment = orderCommentDAO.findById(commentId);
		Map<String,Object> dataMap = new HashMap<>();
		dataMap.put("data", getCommentMap(comment,basePath));
		dataMap.put("status", 0x01);
		dataMap.put("message", "请求成功");
		return dataMap;
	}
	
	
	
	
	public Map<String, Object> getOrderComment(HttpServletRequest request,
			HttpServletResponse response) {
				Parameter parameter = ParameterUtil.getParameter(request);
				String basePath = request.getServletContext().getAttribute("BASEPATH_IN_SESSION").toString();
				if (parameter == null) {//错误的参数；
					return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
				}
			Map<String, Object> ocMap = new HashMap<String, Object>();
			String comment = (String) (parameter.getData().get("comment")==null?"":parameter.getData().get("comment"));
			Double score = (Double) (parameter.getData().get("score")==null?"":parameter.getData().get("score"));
			String imgUrl = (String) (parameter.getData().get("imgUrl")==null?"":parameter.getData().get("imgUrl"));

			List<Map<String, Object>> commentList = new ArrayList<>();
			if(comment!=null){
				JSONArray commentJson = JSONArray.fromObject(comment);
				List<OrderCommentByJson> ocbjList = JSONArray.toList(commentJson,OrderCommentByJson.class);
				Map<String, Object> ocbjMap = new HashMap<>();
				for(OrderCommentByJson ocbj :ocbjList){
					ocbjMap.put("comment",ocbj.getComment());
					commentList.add(ocbjMap);
				}

			List<Map<String, Object>> imgUrlList = new ArrayList<>();
			if(imgUrl!=null){
				JSONArray imgUrlJson = JSONArray.fromObject(imgUrl);
				List<OrderCommentByJson> ocbjList1 = JSONArray.toList(imgUrlJson,OrderCommentByJson.class);
				for(OrderCommentByJson ocbj :ocbjList1){
					ocbjMap.put("image", basePath+ocbj.getImg());
					imgUrlList.add(ocbjMap);
				}
			}
			ocMap.put("comment", commentList);
			ocMap.put("imgUrl", imgUrlList);
			//1.存评论
			OrderComment oc = new OrderComment();
			oc.setComment(comment);
			oc.setScore(score);
			oc.setImgurl(imgUrl);
			orderCommentDAO.save(oc);
			}
		return JsonResponseUtil.getJson(0x01, "请求成功");
	}

		private Map<String,Object> getSeller(Seller s,String basePath){
			Map<String,Object> sellerMap = new HashMap<>();
			sellerMap.put("sellerId", s.getId());
			sellerMap.put("sellerName", s.getName());
			sellerMap.put("sellerIcon", basePath+s.getHeadImg());
			return sellerMap;
		}
		private Map<String,Object> getItemMap(ReGoodsorderItem item,String basePath){
			Map<String,Object> itemMap = new HashMap<>();
			itemMap.put("orderItemId",item.getId());
			itemMap.put("number",item.getGoodQuantity());
			itemMap.put("specString",item.getStyle());
			Map<String,Object> goodsMap = new HashMap<>();
			goodsMap.put("goodsId", item.getGoodsId());
			goodsMap.put("name", item.getGoodName());
			goodsMap.put("price", item.getGoodPrice());
			goodsMap.put("score", item.getGoodScore());
			goodsMap.put("cashpoint", item.getGoodCashpoint());
			goodsMap.put("costPrice",item.getCar().getDisplayPrice());
			itemMap.put("goods", goodsMap);
			return itemMap;
		}

		@Override
		public Map<String, Object> getCommentList(HttpServletRequest request,HttpServletResponse response) {
			String xcx = request.getParameter("xcx");
			String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
			String goodsId  = "";
			Integer pageIndex = 1;
			if(xcx != null){
				goodsId = request.getParameter("goodsId");
				pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
			}else{
				
				Parameter parameter = ParameterUtil.getParameter(request);
				if (parameter == null) {//错误的参数；
					return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
				}
				goodsId = parameter.getData().getString("goodsId");
				pageIndex = parameter.getData().getInteger("pageIndex");
			}
			QueryModel model = new QueryModel().setOrder("id desc");
			if(ReBaseGoods.getMallTypeId(goodsId.substring(0, 3))>0){
				ReBaseGoods good = (ReBaseGoods)reGoodsOfBaseService.getMall(goodsId);
				model.combPreEquals("snapshotId", good.getSnapshotGoods().getId());
			}else{
				ReGoodsorderItem item = reGoodsorderItemDao.findById(Integer.parseInt(goodsId));
				model.combPreEquals("snapshotId", item.getCar().getSnapshot().getId());
			}
			model.combPreEquals("isvalid", true);
			
			int	count = dateBaseDAO.findCount(OrderComment.class, model);
			int totalPage = (count % pageItemCount) > 0 ? ((count / pageItemCount) + 1)
					: (count / pageItemCount);
			int start = (pageIndex - 1) * pageItemCount;

			//查询结果；
			List<OrderComment> comment = dateBaseDAO.findPageList(OrderComment.class, model, start, pageItemCount);
			
			Map<String,Object> dataMap = new HashMap<>();
			List<Map<String ,Object>> commentList = new ArrayList<>();
			for(OrderComment oc : comment){
				commentList.add(getCommentMap(oc,basePath));
			}
			//好评率
			double sum = dateBaseDAO.findSum(OrderComment.class,"score", model);
			int all = dateBaseDAO.findCount(OrderComment.class, model);
			
			dataMap.put("goodCommentPraise", (float)sum/(all*10));
			dataMap.put("commentCount", all);
			
			dataMap.put("dataList", commentList);
			dataMap.put("pageSize", totalPage);
			dataMap.put("pageIndex", pageIndex);
			dataMap.put("pageItemCount", pageItemCount);
			Map<String,Object> map = new HashMap<>();
			map.put("data", dataMap);
			map.put("status", 0x01);
			map.put("message", "请求成功");
			return map;
		}
		
		@Override
		public Map<String, Object> putCommentList(HttpServletRequest request,
				HttpServletResponse response) {
			Parameter parameter = ParameterUtil.getParameter(request);
			String basePath = request.getServletContext().getAttribute("BASEPATH_IN_SESSION").toString();
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			int orderItemId = (Integer) (parameter.getData().get("orderItemId")==null?"":parameter.getData().get("orderItemId"));
			int commentGoal = (Integer) (parameter.getData().get("commentGoal")==null?"":parameter.getData().get("commentGoal"));//评分
			String commentContent = (String) (parameter.getData().get("commentContent")==null?"":parameter.getData().get("commentContent"));
			String commentImages = (String) (parameter.getData().get("commentImages")==null?"":parameter.getData().get("commentImages"));
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<Map<String,Object>> dataList = new ArrayList<>();

			Map<String,Object> orderItemMap = new HashMap<String,Object>();
			ReGoodsorderItem item = reGoodsorderItemDao.findById(orderItemId);
			orderItemMap.put("orderItemId",item.getId() );
			dataMap.put("orderItem", item);
			dataMap.put("commentGoal", commentGoal);
			dataMap.put("commentContent", commentContent);

			List<Map<String,Object>> imagesList = new ArrayList<>();
			Map<String,Object> imagesMap = new HashMap<>();
			imagesMap.put("commentImages", commentImages);
			imagesList.add(imagesMap);
			if(item.getUser().getUnionId() != null){
				
				String unionId = item.getUser().getUnionId();
				String param = "unionId="+unionId+"&linkType=1";
				UrlUtil.sendGzhMsg(1, param);
			}
			
			dataList.add(dataMap);
			Map<String,Object> map = new HashMap<>();
			map.put("data", dataMap);
			map.put("status", 0x01);
			map.put("message", "请求成功");
			return map;
		}
		
		@Override
		public Map<String, Object> putComment(HttpServletRequest request,
				HttpServletResponse response) {
			String basePath = request.getServletContext().getAttribute("BASEPATH_IN_SESSION").toString();
			com.alibaba.fastjson.JSONArray dataList  = null;
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {//错误的参数；
				return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
			}
			dataList = parameter.getData().getJSONArray("dataList");
				
			
			//当前时间
			Timestamp nowTime = new Timestamp(System.currentTimeMillis());
			//获取子订单ID集合
			Set<Integer> orderSet = new HashSet<>();
			for(int i=0;i<dataList.size();i++){
				JSONObject data = dataList.getJSONObject(i);
				Integer itemId = data.getJSONObject("orderItem").getInteger("orderItemId");
				Double commentGoal = data.getDouble("commentGoal");
				String commentContent = data.getString("commentContent");
				String commentImages = data.getString("commentImages");
				
				ReGoodsorderItem item = reGoodsorderItemDao.findById(itemId);
				ReBaseGoods goods = (ReBaseGoods)reGoodsOfBaseService.getMall(item.getMallClass()+item.getGoodsId());
				item.setStatus(ReGoodsorder.yi_wan_cheng);
				//保存评论内容
				OrderComment comment = new OrderComment();
				comment.setSnapshotId(goods.getSnapshotGoods().getId());
				
				comment.setUserComment(StringUtil.saveEmoji(commentContent));
				comment.setImgList(commentImages);
				comment.setScore(commentGoal);
				comment.setCommentDate(nowTime);
				comment.setIsValid(true);
				comment.setReGoodsorderItem(item);
				orderCommentDAO.save(comment);
				
				orderSet.add(item.getOrder().getId());
			}
		
			QueryModel queryModel = new QueryModel();
			for(Integer orderId : orderSet){
				queryModel.clearQuery();
				queryModel.combPreEquals("order.id", orderId,"orderId");
				queryModel.combPreEquals("status", ReGoodsorder.dai_ping_jia);
				int count = dateBaseDAO.findCount(ReGoodsorderItem.class, queryModel);
				if(count==0){
					reGoodsorderDao.updateStatus(ReGoodsorder.yi_wan_cheng,orderId);
				}
			}
			return JsonResponseUtil.getJson(0x01, "添加成功");
		}
		
		@Override
		public Map<String,Object> getCommentMap(OrderComment comment,String basePath){		
		
			Map<String,Object> dataMap = new HashMap<>();
			
			//发表评论的用户
			Users user = usersDao.findById(comment.getReGoodsorderItem().getUser().getId());
			Map<String,Object> userMap = new HashMap<>();
			userMap.put("userId", user.getId());
			userMap.put("username", user.getRealname());
			userMap.put("phone", user.getPhone());
			userMap.put("headImage", user.getHeadimage());
			dataMap.put("userInfo",userMap);
			
			//商品所属商家
			Seller seller = sellerDao.findById(comment.getReGoodsorderItem().getOrder().getSeller().getId());
			
			
		
			Map<String,Object> sellerMap = new HashMap<>();
			sellerMap.put("sellerId", seller.getId());
			sellerMap.put("sellerName", seller.getName());
			if(seller!=null){
				SellerMainPage smp = sellerMainPageDao.findOneBySellerId(seller.getId());
				if(StringUtils.isBlank(seller.getImgUrl())){
					sellerMap.put("sellerIcon",basePath+smp.getSellerLogo());
				}else{
					sellerMap.put("sellerIcon",basePath+seller.getImgUrl());
				}
			}
			
			sellerMap.put("sellerAddress", seller.getAddress());
			sellerMap.put("sellerPhone", seller.getPhone());
			dataMap.put("seller",sellerMap);
			
			//评论的订单项
			ReGoodsorderItem item = comment.getReGoodsorderItem();
			Map<String,Object> itemMap = new HashMap<>();
			itemMap.put("orderItemId",item.getId());//订单项id
			itemMap.put("number",item.getGoodQuantity());//数量
			itemMap.put("specString",item.getStyle());//前台用于显示的规格信息
			itemMap.put("money",item.getPayPrice());//订单需要支付的现金
			itemMap.put("score",item.getPayScore());//订单需要支付的积分
			itemMap.put("cashpoint",item.getPayCashpoint());//订单需要支付的红包
			itemMap.put("isBack",item.getIsBack()==ReGoodsorder.bu_ke_tui_dan?false:true);//是否可退单
			dataMap.put("orderItem", itemMap);
			
			//评论得分(总分十分)
			dataMap.put("commentGoal", comment.getScore());	
			//评论时间
			dataMap.put("commentDate", DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date(comment.getCommentDate().getTime())));
			//评论内容
			dataMap.put("commentContent", StringUtil.getEmoji(comment.getUserComment()));
			//评论图片
			comment.setBasePath(basePath);
			dataMap.put("commentImages", comment.getImgListNew());
			return dataMap;
		}




		@Override
		public Map<String, Object> putCommentXCX(HttpServletRequest request, HttpServletResponse response) {

			String basePath = request.getServletContext().getAttribute("BASEPATH_IN_SESSION").toString();
			//当前时间
			Timestamp nowTime = new Timestamp(System.currentTimeMillis());
			//获取子订单ID集合
			Set<Integer> orderSet = new HashSet<>();
			String itemId = request.getParameter("itemId");
			String commentGoal = request.getParameter("commentGoal");
			String commentContent = request.getParameter("commentContent");
			String commentImage = request.getParameter("commentImages");
			com.alibaba.fastjson.JSONArray commentImages = new com.alibaba.fastjson.JSONArray();
			if(commentImage != null){
				commentImages = com.alibaba.fastjson.JSONArray.parseArray(commentImage);
			}	
			ReGoodsorderItem item = reGoodsorderItemDao.findById(Integer.valueOf(itemId));
			ReBaseGoods goods = (ReBaseGoods)reGoodsOfBaseService.getMall(item.getMallClass()+item.getGoodsId());
			item.setStatus(ReGoodsorder.yi_wan_cheng);
			//保存评论内容
			OrderComment comment = new OrderComment();
			comment.setSnapshotId(goods.getSnapshotGoods().getId());
			comment.setUserComment(commentContent);
			comment.setImgList(commentImages.toJSONString());
			comment.setScore(Double.valueOf(commentGoal));
			comment.setCommentDate(nowTime);
			comment.setIsValid(true);
			comment.setReGoodsorderItem(item);
			
			orderCommentDAO.save(comment);
			
			QueryModel queryModel = new QueryModel();
			queryModel.clearQuery();
			queryModel.combPreEquals("order.id", item.getOrder().getId(),"orderId");
			queryModel.combPreEquals("status", ReGoodsorder.dai_ping_jia);
			int count = dateBaseDAO.findCount(ReGoodsorderItem.class, queryModel);
			if(count==0){
				reGoodsorderDao.updateStatus(ReGoodsorder.yi_wan_cheng,item.getOrder().getId());
			}
		
			return JsonResponseUtil.getJson(0x01, "添加成功");
		
		}
	}
