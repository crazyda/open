package com.axp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.axp.dao.AdminUserDAO;
import com.axp.domain.AdminUser;
import com.axp.domain.AdminUserScoreRecord;
import com.axp.domain.AdminuserCashpointRecord;
import com.axp.domain.AdminuserRedpaper;
import com.axp.domain.News;
import com.axp.domain.ProvinceEnum;
import com.axp.domain.Seller;
import com.axp.domain.ShopCategory;
import com.axp.domain.Slides;
import com.axp.domain.Users;
import com.axp.service.AdminUserService;
import com.axp.service.ISellerService;
import com.axp.util.CalcUtil;
import com.axp.util.DateUtil;
import com.axp.util.JsonResponseUtil;
import com.axp.util.MD5Util;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;
import com.axp.util.StringUtil;
import com.rongcloud.models.TokenResult;
import com.sun.jmx.snmp.Timestamp;

@Service
public class AdminUserServiceImpl extends BaseServiceImpl<AdminUser> implements AdminUserService{

	@Autowired
	private ISellerService sellerService;
	
	String url = "http://seller.aixiaoping.com";
	//String url = "http://test.aixiaoping.com";
	
	@Override
	public Map<String, Object> getAdminUser(String loginname, String password) {
				
				//获取用户； 
				List<AdminUser> List = adminUserDao.getLoginByLoginname(loginname);
				
				if (List != null && List.size() == 1) {//有此用户；
					AdminUser adminUser = List.get(0);
					List<Seller> slist = sellerDao.getSellerListByAdminId(adminUser.getId());
					Seller seller =null;
					if(slist!=null && slist.size()>0){
						seller = slist.get(0);
					}
					if (StringUtil.isEmpty(adminUser.getLoginname()) || !adminUser.getPassword().equals(MD5Util.GetMD5Code(password))) {//密码错误情况；
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("status", -1);
						map.put("message", "用户登录密码错误");
						return map;
					} else {//存在用户，且用户名和密码都正确；
						
						
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("status", 1);
						map.put("message", "登陆成功");
						Map<String, Object> m1 = new HashMap<String, Object>();
						m1.put("sellerId", seller==null?"":seller.getId());
						m1.put("adminuserId", adminUser.getId());

						map.put("data", m1);
						return map;
					}

				} else {//没有此用户；
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("status", -2);
					map.put("message", "账号不存在");
					return map;
				}

	}

	@Override
	public Map<String, Object> getSellerInfoById(String adminuserId,String sellerId, String basePath) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		
		try{
				
			Seller seller=null;
			QueryModel queryModel=new QueryModel();
			if(StringUtils.isBlank(sellerId)){
				AdminUser adminUser= adminUserDao.findById(Integer.parseInt(adminuserId));
				if(adminUser!=null){
					
					queryModel.combEquals("isValid", 1);
					queryModel.combPreEquals("adminUser.id", adminUser.getId(), "adminUserId");
					List<Seller> slist=dateBaseDAO.findPageList(Seller.class, queryModel, 0, pageSize);
					if(slist!=null && slist.size()>0){
						seller=slist.get(0);
					}
				}
			}else{
				seller =sellerDao.findById(Integer.parseInt(sellerId));
			}
				if(seller ==null){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("status", -1);
					map.put("message", "不是店铺！");
					return map;
				}
				
				
				
				Map<String,Object> data =new HashMap<String,Object>();
				List<Map<String,Object>> newsList =new ArrayList<Map<String,Object>>();
				
				queryModel.clearQuery();
				queryModel.combEquals("status", 1);
				List<News> newslist=dateBaseDAO.findPageList(News.class, queryModel, 0, pageSize);
				for (int i = 0; i < newslist.size(); i++) {
					Map<String,Object> temp =new HashMap<String, Object>();
					temp.put("title", newslist.get(i).getTitle()==null?"":newslist.get(i).getTitle());
					temp.put("uri",newslist.get(i).getUrl()==null?"":newslist.get(i).getUrl());
					newsList.add(temp);
				}
				data.put("news", newsList);
				
				
				QueryModel queryModel2 =new QueryModel();
				queryModel2.clearQuery();
				queryModel.combPreEquals("isvalid", true);
				List<Map<String,Object>> slideslist =new ArrayList<Map<String,Object>>();
				List<Slides> slidesList =dateBaseDAO.findPageList(Slides.class, queryModel2, 0, pageSize);
				for (int i = 0; i < slidesList.size(); i++) {
					Map<String, Object> temp =new HashMap<String, Object>();
					temp.put("image", basePath+slidesList.get(i).getImgurls());
					temp.put("uri", slidesList.get(i).getLinkurl()==null?"":slidesList.get(i).getLinkurl());
					temp.put("title", slidesList.get(i).getName()==null?"":slidesList.get(i).getName());
					slideslist.add(temp);
				}
				
				
				data.put("bottomBanners", slideslist);
				data.put("sellerId", seller==null?"":seller.getId()+"");
				data.put("adminuserId", seller.getAdminUser().getId()+"");
				int x=(int)(Math.random()*100);
				int sid=0;
				int todayOrderNum=0;
				double totalMoney=0;
				double todayMoney=0;
				if(seller!=null){
					sid =seller.getId();
					try{
					todayOrderNum=reGoodsorderDao.getCountBySellerId(sid);
				}catch(Exception e){
					e.printStackTrace();
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				}
					try{
					totalMoney=reGoodsorderDao.getSumBySellerId(sid);
					
				}catch(Exception e){
					e.printStackTrace();
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				}
					try{
					todayMoney=reGoodsorderDao.getSumBySellerIdForToday(sid);
					}catch(Exception e){
						e.printStackTrace();
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					}
				}
				 
				data.put("vsitors", x);
				data.put("totalMoney", todayMoney+"");
				data.put("todayTotalMoney", totalMoney+"");
				data.put("todayOrderNum", todayOrderNum+"");
				data.put("balance", seller.getAdminUser().getMoney()==null?"0.00":seller.getAdminUser().getMoney()+"");
				statusMap.put("data", data);
				statusMap.put("status", 1);
				statusMap.put("message", "请求成功");
				
		
			}catch(Exception e){
				e.printStackTrace();
				statusMap.put("status", -2);
				statusMap.put("message", "位置错误");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			return statusMap;
	}
	
	@Override
	public Map<String, Object> getAdminUserInfoById(String userId,String adminuserId,
			String sellerId, String basePath) {
		Map<String, Object> statusMap=new HashMap<String, Object>();
		
		try {
			if(StringUtils.isBlank(adminuserId)){
				statusMap.put("status", -2);
				statusMap.put("message", "还不是店铺，无法查看资料");
				return statusMap;
			}
			AdminUser adminUser=adminUserDao.findById(Integer.parseInt(adminuserId));
			//Users users = usersDao.findById(Integer.parseInt(userId));
			Seller seller = sellerDao.findById(Integer.parseInt(sellerId));;
			
			if(adminUser==null){
				statusMap.put("status", -2);
				statusMap.put("message", "还不是店铺，无法查看资料");
				return statusMap;
			}
			
			
			
			Map<String, Object> data =new HashMap<String, Object>();
			data.put("loginName", adminUser.getLoginname());
			data.put("loginNameRemark", "登录账号"+adminUser.getLoginname()+"可直接登录PC后台，登录商家app时不会与用户版账号冲突！");
			data.put("partnerName", adminUser.getParentAdminUser().getUsername()+"-"+adminUser.getParentAdminUser().getPhone());
			if(seller!=null){
				data.put("sellerLogo", basePath+seller.getLogo()==null?"":basePath+seller.getLogo());
				data.put("sellerName", seller.getName()==null?"":seller.getName());
				data.put("contacts", seller.getContacts()==null?"":seller.getContacts());
				data.put("invitecode", adminUser.getInvitecode()==null?"":adminUser.getInvitecode());
				data.put("phone", seller.getPhone()==null?"":seller.getPhone());
				data.put("address", seller.getAddress()==null?"":seller.getAddress());
				data.put("reContacts", seller.getContacts()==null?"":seller.getContacts());
				data.put("reAddress", "");
			
			}else{
				data.put("sellerLogo", "");
				data.put("sellerName", "未开通店铺！");
				data.put("contacts", "");
				data.put("invitecode", "");
				data.put("phone", "");
				data.put("address","");
				data.put("reContacts","" );
				data.put("reAddress", "");
				
			}
			//data.put("userId", users==null?"-1":users.getId());
			data.put("adminuserId", adminUser.getId());
			statusMap.put("data", data);
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功");
		} catch (Exception e) {
			e.printStackTrace();
			statusMap.put("status", -2);
			statusMap.put("message", "请求失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return statusMap;
	}
	
	
	@Override
	public Map<String, Object> getAdminUserMoney(String adminuserId,
			String basePath,Integer pageIndex ,Integer assetType) {
		
		
		Map<String, Object> statusMap = new HashMap<String, Object>();
		
		try{
			AdminUser adminUser= adminUserDao.findById(Integer.parseInt(adminuserId));
			Map<String,Object> data =new HashMap<String,Object>();
			List<Map<String,Object>> newsList =new ArrayList<Map<String,Object>>();
			QueryModel queryModel=new QueryModel();
			queryModel.clearQuery();
			queryModel.combPreEquals("isValid", true);
			queryModel.combEquals("adminUser.id", adminUser.getId());
			if(assetType ==1){ //账户未确认 记录
				queryModel.combPreEquals("type", -1);
			}else if(assetType ==2){ //账户已确认 记录
				queryModel.combPreEquals("type", 1);
				queryModel.combCondition("isDeposit<>5");
			}else if(assetType ==3){ //押金类型
				queryModel.combCondition("type<>-1 and type <>0");
				queryModel.combPreEquals("isDeposit", 5);
			}
			Integer count =  dateBaseDAO.findCount(AdminuserCashpointRecord.class, queryModel);
			Integer A = (int) CalcUtil.div(count, 10);
			Integer B = (int) CalcUtil.add(A, 1);
			List<AdminuserCashpointRecord> acrlist=dateBaseDAO.findPageList(AdminuserCashpointRecord.class, queryModel, (pageIndex-1)*10, 10);
			for (int i = 0; i < acrlist.size(); i++) {
				Map<String,Object> temp =new HashMap<String, Object>();
				if(assetType ==3 ){
					if( acrlist.get(i).getType()==-1 ){
						temp.put("remark", acrlist.get(i).getRemark()+"（未确认）");
					}else{
						temp.put("remark", acrlist.get(i).getRemark());
					}
				}else{
					temp.put("remark", acrlist.get(i).getRemark());
				}
				
				temp.put("createtime",DateUtil.formatDate("yyyy-MM-dd HH:mm:ss",acrlist.get(i).getCreateTime()));
				Double bePoint = acrlist.get(i).getBeforepoint()==null?0.0:acrlist.get(i).getBeforepoint();
				Double afPoint = acrlist.get(i).getAfterpoint()==null?0.0:acrlist.get(i).getAfterpoint();
				if(CalcUtil.sub(afPoint, bePoint) < 0){
					
					temp.put("money",acrlist.get(i).getCashpoint()+"");
				}else{
					temp.put("money",acrlist.get(i).getCashpoint()+"");
				}
				newsList.add(temp);
			}
			data.put("dataList", newsList);
			data.put("totalMoney", adminUser.getMoney()==null?"0.0":adminUser.getMoney()+"");
			data.put("totalCash", String.valueOf(adminUser.getDeposit()==null?"0.0":adminUser.getDeposit()));//押金
			data.put("totalScore", adminUser.getScore());
			queryModel.clearQuery();
			queryModel.combEquals("isValid", 1);
			queryModel.combEquals("adminUser.id", adminUser.getId());
			queryModel.combEquals("type", -1);
			double sum =  dateBaseDAO.findSum(AdminuserCashpointRecord.class,"cashpoint", queryModel);
			
			data.put("unConfirmedMoney", String.valueOf(sum));
			data.put("pageSize", count%10==0?A:B);
			data.put("pageIndex", pageIndex);
			data.put("pageItemCount", 10+"");
			
			
			statusMap.put("data", data);
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功");
			
	
		}catch(Exception e){
			e.printStackTrace();
			statusMap.put("status", -2);
			statusMap.put("message", "获取金额失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
		
		

		
	}

	@Override
	public Map<String, Object> getAdminUserRedPaper(String adminuserId,
			String basePath,Integer pageIndex) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try{
			AdminUser adminUser= adminUserDao.findById(Integer.parseInt(adminuserId));
			Seller seller = adminUser.getSeller();
			Map<String,Object> data =new HashMap<String,Object>();
			Map<String,Object> dataList =new HashMap<String,Object>();
			List<Map<String,Object>> newsList =new ArrayList<Map<String,Object>>();
			QueryModel queryModel=new QueryModel();
			queryModel.clearQuery();
			queryModel.combEquals("isValid", 1);
			queryModel.combEquals("adminUser.id", adminUser.getId());
			Integer count =  dateBaseDAO.findCount(AdminuserRedpaper.class, queryModel);
			Integer A = (int) CalcUtil.div(count, 16);
			Integer B = (int) CalcUtil.add(A, 1);
			List<AdminuserRedpaper> acrlist=dateBaseDAO.findPageList(AdminuserRedpaper.class, queryModel,(pageIndex-1)*10, 10);
			for (int i = 0; i < acrlist.size(); i++) {
				Map<String,Object> temp =new HashMap<String, Object>();
				temp.put("type", acrlist.get(i).getType());
				temp.put("createtime",DateUtil.formatDate("yyyy-MM-dd HH:mm:ss",acrlist.get(i).getCreattime()));
				temp.put("totalMoney",acrlist.get(i).getTotalMoney()+"");
				temp.put("totalQuantity",acrlist.get(i).getTotalQuantity()+"");
				int receive = acrlist.get(i).getTotalQuantity()-acrlist.get(i).getSurplusQuantity(); 
				temp.put("receiveQuantity",receive+"");
				newsList.add(temp);
			}
			data.put("totalMoney", adminUser.getMoney()==null?"0.00":adminUser.getMoney()+"");
			data.put("dataList", newsList);
			//data.put("pageSize", count%16==0?count/16:(count/16+1));
			data.put("pageSize", count%16==0?A:B);
			data.put("pageIndex", pageIndex);
			data.put("pageItemCount", 16);
			statusMap.put("data", data);
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功");
			
	
		}catch(Exception e){
			e.printStackTrace();
			statusMap.put("status", -2);
			statusMap.put("message", "获取金额失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}

	@Override
	public Map<String, Object> getAdminUserTotalMoney(String adminuserId,
			String basePath, Integer page) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try{
			AdminUser adminUser= adminUserDao.findById(Integer.parseInt(adminuserId));
			
			Map<String,Object> data =new HashMap<String,Object>();
			
			data.put("totalMoney", adminUser.getMoney()==null?"0:00":adminUser.getMoney()+"");
			
			statusMap.put("data", data);
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功");
			
	
		}catch(Exception e){
			e.printStackTrace();
			statusMap.put("status", -2);
			statusMap.put("message", "获取金额失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}

	
	/*
	 * 店铺审核资料填写业务
	 * */
	@Override
	public Map<String, Object> updateStoreInfo(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,Object> data =new HashMap<String,Object>();
		Map<String, Object> statusMap = new HashMap<String, Object>();
		String xcx = request.getParameter("xcx");
		String name = "";
		String address = "";
		String sellerIdCard = ""; //身份证
		String businessLicencePic = ""; 
		String phone = "";
		Integer typeId = 0;
		Integer zoneId = 0;
		String userId = "";
		String province = "";
		String city = "";
		String district = "";
		if(xcx != null){
			name = request.getParameter("name");
			sellerIdCard = request.getParameter("sellerIdCard");
			businessLicencePic = request.getParameter("pic");
			phone = request.getParameter("phone");
			userId = request.getParameter("userId");
			typeId = Integer.valueOf(request.getParameter("typeId"));
			province = request.getParameter("province").replaceAll(",", "");
			address = province+request.getParameter("address");
			
		}else{
			
			Parameter parameter=ParameterUtil.getParameter(request);
			if (parameter==null) {
				return JsonResponseUtil.getJson(-2,"参数data不是合法的json字符串");
			}
			
			name=parameter.getData().getString("name");
			address =parameter.getData().getString("address");
			sellerIdCard =parameter.getData().getString("sellerIdCard");
			businessLicencePic = parameter.getData().getString("businessLicencePic");
			phone = parameter.getData().getString("phone");
			typeId = parameter.getData().getIntValue("shopcategoryId");
			zoneId =parameter.getData().getIntValue("zoneId");
			userId = parameter.getUserId();
		}
		try {
			
			if(StringUtils.isBlank(address) || StringUtils.isBlank(name) || StringUtils.isBlank(phone) || StringUtils.isBlank(sellerIdCard) || StringUtils.isBlank(businessLicencePic)  ){
				return JsonResponseUtil.getJson(-2,"请将资料填写完整！");
			}
			if (address.indexOf("省")==-1 && address.indexOf("市")==-1 && 
					address.indexOf("区")==-1 && address.indexOf("县")==-1 && address.indexOf("自治区")==-1 &&
					address.indexOf("自治县") ==-1 && address.indexOf("旗")==-1) {
				return JsonResponseUtil.getJson(-2,"请将店铺地址填写完整！");
			}
			
			
			
			
			
			Users users = usersDao.findById(Integer.parseInt(userId));
			if(users.getPhone() == null){
				users.setPhone(phone);
				usersDao.saveOrUpdate(users);
			}
			
			//Seller seller=sellerService.getSellerByUsersId(users.getId());
			
		
			Seller seller=null;
			QueryModel model1=new QueryModel();
			model1.combEquals("isvalid", 1);
			model1.combPreEquals("users.id", users.getId(), "userId");
			List<Seller> slist=dateBaseDAO.findPageList(Seller.class, model1, 0, pageSize);
			if (slist!=null&&slist.size()>0) {
				return JsonResponseUtil.getJson(-2,"该用户已有店铺资料！");
			}
			
			//通过zoneId 知道对应的zone
			ProvinceEnum zone = null;
			if(xcx != null){
				if(!district.isEmpty()){
					model1.clearQuery();
					model1.combLike("name", district);
				}else if(!city.isEmpty()){
					model1.clearQuery();
					model1.combLike("name", city);
				}else{
					model1.clearQuery();
					model1.combLike("name", province);
				}
				model1.combPreEquals("isValid", true);
				List<ProvinceEnum> zones = dateBaseDAO.findLists(ProvinceEnum.class, model1);
				if(zones != null && zones.size()>0){
					zone = zones.get(0);
				}
			}else{
				
				zone = provinceEnumDao.findById(zoneId);
			}
			
			seller = new Seller();
				
			ShopCategory shopCategory=shopCategoryDao.findById(typeId);
			seller.setProvinceEnum(zone);//添加地址
			seller.setShopCategory(shopCategory);
			seller.setName(name);
			seller.setAddress(address);
			seller.setIsvalid(true);
			seller.setSellerIdCard(sellerIdCard);
			seller.setBusinessLicencePic(businessLicencePic);
			seller.setPhone(phone);
			seller.setCreatetime(new java.sql.Timestamp(System.currentTimeMillis()));
			seller.setVerifyStatus(0);//未审核状态
			seller.setUsers(users);
			seller.setHasVoucher(false);
			seller.setLevel(0);
			seller.setQuantity(0);
			seller.setScore(0);
			
			sellerDao.saveOrUpdate(seller);
			
			data.put("tips", "");
			//data.put("tips", "有问题请联系客服，电话：020-89281545");
			data.put("remind", "恭喜您，提交资料成功！");
			data.put("message", "我们会在两个工作日内审核完成并通知您，请保持电话畅通！");
			data.put("userId", users==null?"-1":users.getId());
		
			statusMap.put("data", data);
			statusMap.put("status", 1);
			statusMap.put("message", "提交成功");
			
		} catch (Exception e) {
			statusMap.put("status", -2);
			statusMap.put("message", "提交失败");
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			
		}
		return statusMap;
	}

	/*
	 * 店铺资料审核   
	 * */
	@Override
	public Map<String, Object> storeVerifyStatus(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap =new HashMap<String, Object>();
		String xcx = request.getParameter("xcx");
		String userId = "";
		String sellerId = "";
		
		if(xcx != null){
			userId = request.getParameter("userId");
			sellerId = request.getParameter("sellerId");
		}else{
			Parameter parameter=ParameterUtil.getParameter(request);
			if (parameter==null) {
				return JsonResponseUtil.getJson(-2, "参数data不是合法的json字符串");
			}
			
			userId = parameter.getUserId();
			sellerId = parameter.getSellerId();
		}
		try {
			Map<String, Object> data = new HashMap<String, Object>();
			Users user=usersDao.findById(Integer.parseInt(userId));

			if(user==null){
				return JsonResponseUtil.getJson(-2, "用户信息错误");
			}
			Seller seller=null;
			if(StringUtils.isNotBlank(sellerId) && !"-1".equals(sellerId)){
				seller = sellerDao.findById(Integer.parseInt(sellerId));
			}else{
				
				QueryModel model1=new QueryModel();
				model1.combEquals("isvalid", 1);
				model1.combPreEquals("users.id", user.getId(), "userId");
				List<Seller> slist=dateBaseDAO.findPageList(Seller.class, model1, 0, pageSize);
				if (slist!=null&&slist.size()>0) {
					seller=slist.get(0);
				}
				user.getPhone();
			}
			
			
			
			if(seller!=null){
				
				if(seller.getVerifyStatus()==null){
					data.put("verifyStatus", -1);
				}else{
					if(seller.getVerifyStatus()==0){
						data.put("remind", "恭喜您，提交资料成功！");
						data.put("message", "我们会在两个工作日内审核完成并通知您，请保持电话畅通！");
						data.put("verifyStatus", seller.getVerifyStatus());
						statusMap.put("adminuserId","");
						statusMap.put("sellerId", "");
					}else if(seller.getVerifyStatus()==2 || seller.getVerifyStatus()==1){
						data.put("remind", "恭喜您，审核已通过！");
						data.put("message", "快装修您的店铺准备开张吧！");
						data.put("verifyStatus", seller.getVerifyStatus());
						if(seller.getAdminUser()!=null ){
							data.put("sellerAccount", user.getPhone());
						}else{
							data.put("sellerAccount", "数据有误，请联系客服！");
						}
						data.put("sellerPassword", "请用当前微信或者验证码登录商家版!");
						statusMap.put("adminuserId", seller.getAdminUser().getId());
						statusMap.put("sellerId", seller.getId()+"");
					}else if(seller.getVerifyStatus()==-2){
						data.put("remind", "很遗憾，你的资质不符合开店要求！请点完成后重新上传资料");
						data.put("message", "如有问题，请联系客服解决！");
						data.put("verifyStatus", seller.getVerifyStatus());
						statusMap.put("adminuserId","");
						statusMap.put("sellerId", "");
					}
				}
			}else{
				data.put("verifyStatus", -1);
			}
			data.put("tips", "");
			//data.put("tips", "有问题请联系客服,电话：020-89281545");
			statusMap.put("userId", userId);
			statusMap.put("data", data);
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功！");
		} catch (Exception e) {
			statusMap.put("status", -2);
			statusMap.put("message", "请求失败");
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}

	
	/*
	 * 通用版首页接口业务逻辑
	 * */
	@Override
	public Map<String, Object> getSellerHomepageInfo(String userId,String adminuserId, String sellerId, String basePath) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		
		try{
				Seller seller=null;
				Map<String,Object> data =new HashMap<String,Object>();
				Integer isShowScore =0;//0不弹出积分  1弹出积分
				Integer isShowMoney =0;// 0 不弹出金钱，1是弹出金钱
				String  s_quantity = "0"; // 积分或者资金的数值（int，double）
				String  m_quantity = "0"; // 积分或者资金的数值（int，double）
				Integer isXcx=0;//小程序，公众号用户
				
				if(StringUtils.isBlank(sellerId)){
					if(StringUtils.isBlank(userId)){
						statusMap.put("status", -2);
						statusMap.put("message", "用户数据错误！");
						return statusMap;
					}
					seller = sellerService.getSellerByUsersId2(Integer.parseInt(userId));
				}else{
					seller = sellerDao.findById(Integer.parseInt(sellerId));
				}
				QueryModel queryModel=new QueryModel();
				//审核资料填写状态返回
					if (seller!=null){
						if (seller.getVerifyStatus()!=null && seller.getVerifyStatus()==1) {
							data.put("sellerId", seller.getId()+"");
							data.put("adminuserId", seller.getAdminUser()==null?"-1":seller.getAdminUser().getId()+"");
						}else{
							data.put("sellerId", "-1");
							data.put("adminuserId", "-1");
						}
						data.put("verifyStatus", seller.getVerifyStatus()==null?-1:seller.getVerifyStatus());
						data.put("balance", seller.getAdminUser()==null?"0.00":seller.getAdminUser().getMoney()+"");
						
					}else{
						data.put("verifyStatus", -1);
						data.put("balance", "0.00");
					}
				
				//相关新闻 new d
				List<Map<String,Object>> newsList =new ArrayList<Map<String,Object>>();
				
				queryModel.clearQuery();
				queryModel.combEquals("status", 1);
				List<News> newslist=dateBaseDAO.findPageList(News.class, queryModel, 0, pageSize);
				for (int i = 0; i < newslist.size(); i++) {
					Map<String,Object> temp =new HashMap<String, Object>();
					temp.put("title", newslist.get(i).getTitle()==null?"":newslist.get(i).getTitle());
					temp.put("uri",newslist.get(i).getUrl()==null?"":newslist.get(i).getUrl());
					newsList.add(temp);
				}
				data.put("news", newsList);
				
				//首页本地特产 商家广告, 分类 d
				//QueryModel queryModel2 =new QueryModel();
				queryModel.clearQuery();
				List<Map<String,Object>> slideslist =new ArrayList<Map<String,Object>>();
				List<Slides> zbslides =slidesDao.getsListByZb(1);
				for (int i = 0; i < zbslides.size(); i++) {
						Map<String, Object> temp =new HashMap<String, Object>();
						temp.put("image", basePath+zbslides.get(i).getImgurls());
						temp.put("uri", zbslides.get(i).getLinkurl()==null?"":zbslides.get(i).getLinkurl());
						temp.put("title", zbslides.get(i).getName()==null?"":zbslides.get(i).getName());
						slideslist.add(temp);
						
					
				}
				
				
				
				
				if (StringUtils.isNotBlank(adminuserId)) {
					AdminUser au = adminUserDao.findById(Integer.parseInt(adminuserId));
					//d
					if( au.getShowScore() != null && au.getShowScore()>0){
						isShowScore =1;//弹出积分
						
						s_quantity=au.getShowScore()+"";
						
						//data.put("score",au.getScore()==null?" ":au.getScore() );
					}
					if( au.getShowMoney() != null && au.getShowMoney()>0){
						isShowScore =1;//弹出现金
						//data.put("money", au.getMoney()==null?" ":au.getMoney());
						m_quantity=au.getShowMoney()+"";
					}
					
					if(au.getIsOpenPromoter()!=null && au.getIsOpenPromoter()){
						isXcx=1;
					}
					
					if(isShowScore==1){
						au.setShowMoney(0d);
						au.setShowScore(0);
						adminUserDao.update(au);
					}
					
					
					Integer aid=0;
					if( au!=null && au.getLevel()!=null && au.getLevel()==60){
						if(au.getParentAdminUser()!=null){
							if(au.getParentAdminUser().getLevel()==75){
								aid= au.getParentAdminUser().getId();
							}else if(au.getParentAdminUser().getLevel()==65){
								if(au.getParentAdminUser().getParentAdminUser().getLevel()==75){
									aid = au.getParentAdminUser().getParentAdminUser().getId();
									if(aid.intValue()==171){
										aid=1459;
									}
								}
							}
						}
						
						if(aid>0){
							List<Slides> adminslides =slidesDao.getsListByAdminUserId(aid,1);
							for (int i = 0; i < adminslides.size(); i++) {
									Map<String, Object> temp =new HashMap<String, Object>();
									temp.put("image", basePath+adminslides.get(i).getImgurls());
									temp.put("uri", adminslides.get(i).getLinkurl()==null?"":adminslides.get(i).getLinkurl());
									temp.put("title", adminslides.get(i).getName()==null?"":adminslides.get(i).getName());
									slideslist.add(temp);
								if(i==4){
									break;
								}
							}
						}
					}
				
				}
				data.put("bottomBanners", slideslist);
				data.put("userId", seller.getUsers().getId());
				int sid=0;
				int todayOrderNum=0;
				double totalMoney=0;
				double todayMoney=0;
				if(seller!=null){
					
					data.put("vsitors", seller.getLevel()==null?"0":seller.getLevel());	
					
					sid =seller.getId();
					try{
					todayOrderNum=reGoodsorderDao.getCountBySellerId(sid);
				}catch(Exception e){
					e.printStackTrace();
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				}
				try{
					totalMoney=reGoodsorderDao.getSumBySellerId(sid);
				}catch(Exception e){
					e.printStackTrace();
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				}
				try{
				todayMoney=reGoodsorderDao.getSumBySellerIdForToday(sid);
				}catch(Exception e){
					e.printStackTrace();
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				}
				}else{
					data.put("vsitors", "0");	
				}
				
				String GoodsUrl = url+"/Home/Index?id="+adminuserId;//首页商品管理
				String SellerUrl  = url+"/Admin/Index?id="+adminuserId;//首页店铺管理
				String DirectionUrl  = "http://seller.aixiaoping.com/Admin/Index/guide_jph";//新手指引
				//String DirectionUrl  = "http://jph.aixiaoping.cn:8080/jupinhuiAPI/invoke/mall/aboutGuide";
				data.put("totalMoney", totalMoney+"");
				data.put("todayTotalMoney", todayMoney+"");
				data.put("todayOrderNum", todayOrderNum+"");
				data.put("GoodsUrl", GoodsUrl);
				data.put("SellerUrl", SellerUrl);
				data.put("DirectionUrl", DirectionUrl);

				data.put("isShowScore", isShowScore);//0不显示送积分提示图片、1显示
				data.put("isShowMoney", isShowMoney);//0不显示送积分提示图片、1显示
				data.put("sQuantity", s_quantity);//所送积分数量
				data.put("mQuantity", m_quantity);//所送资金数量
				data.put("isXcx", isXcx);//1显示小程序模块  0不显示
 
				statusMap.put("data", data);
				statusMap.put("status", 1);
				statusMap.put("message", "请求成功");
				
		
			}catch(Exception e){
				e.printStackTrace();
				statusMap.put("status", -2);
				statusMap.put("message", "请求失败");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			return statusMap;
	}
	
	


	
	
	/*
	 * 用户状态确认返回
	 * */
	@Override
	public Map<String, Object> returnCheckstatus(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> statusMap =new HashMap<String, Object>();
		
		 String xcx = request.getParameter("xcx");
		 String sellerId = "";
		 String userId = "";
		 Seller seller =null;
		 if(xcx != null){
			 sellerId = request.getParameter("sellerId");
			 userId = request.getParameter("userId");
		 }else{
			 
			 Parameter parameter=ParameterUtil.getParameter(request);
			 if (parameter==null) {
				 JsonResponseUtil.getJson(-2, "参数data不是合法的json字符串");
			 }
			 sellerId=parameter.getSellerId();
			 userId= parameter.getUserId();
		 }
		
		try {
			if(!"-1".equals(sellerId) &&  StringUtils.isNotBlank(sellerId) ){
				seller = sellerDao.findById(Integer.parseInt(sellerId));
			}else if(StringUtils.isNotBlank(userId)){
				seller=sellerService.getSellerByUsersId(Integer.parseInt(userId));
			}else{
				statusMap.put("status", -2);
				statusMap.put("message", "用户信息出错");
			}
			//Users users = usersDao.findById(Integer.parseInt(userId));
			Map<String, Object> data = new HashMap<String, Object>();
			if(seller!=null){
			if (seller.getVerifyStatus()==2) {
				data.put("verifyStatus", "1".toString());
				seller.setVerifyStatus(1);
				data.put("sellerId", seller.getId()+"");
				data.put("adminuserId", seller.getAdminUser()==null?"-1":seller.getAdminUser().getId()+"");
			}else if(seller.getVerifyStatus()==-2){
				data.put("verifyStatus", "-1".toString());
				seller.setIsvalid(false);
				data.put("sellerId", "-1");
				data.put("adminuserId","-1");
			}else{
				data.put("verifyStatus",seller==null?"":seller.getVerifyStatus().toString());
				data.put("sellerId", "-1");
				data.put("adminuserId","-1");
			}
			}else{
				data.put("verifyStatus","-1");
				data.put("sellerId", "-1");
				data.put("adminuserId","-1");
			}
			sellerDao.saveOrUpdate(seller);
			data.put("userId",userId);
			statusMap.put("data", data);
			statusMap.put("status", 1);
			statusMap.put("message", "请求成功！");
			
		} catch (Exception e) {
			e.printStackTrace();
			statusMap.put("status", -2);
			statusMap.put("message", "请求失败！");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}

	
}
