package com.axp.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SellerMainPage entity. @author MyEclipse Persistence Tools
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public abstract class AbstractSellerMainPage implements java.io.Serializable {

	// Fields

	private Integer id;
	private Seller seller;
	private String beginTime;
	private String endTime;
	private String topCarouselAd;
	private String sellerView;
	private String bannerAd;
	private Boolean isValid;
	private String sellerLogo;
	private String remark;
	private Boolean hasVideo;
	


	private Integer sellerId;
	private String sellerName;
	private String sellerAddress;
	private String sellerPhone;
	private Boolean concern;
	
	private Integer intoShopNum;//进店人数统计
	
	private ReGoodsSnapshot reGoodsSnapshot;

	// Constructors

	public ReGoodsSnapshot getReGoodsSnapshot() {
		return reGoodsSnapshot;
	}
	public void setReGoodsSnapshot(ReGoodsSnapshot reGoodsSnapshot) {
		this.reGoodsSnapshot = reGoodsSnapshot;
	}
	/** default constructor */
	public AbstractSellerMainPage() {
	}
	public AbstractSellerMainPage(Integer id, Seller seller,
			String beginTime, String endTime, String topCarouselAd,
			String sellerView, String bannerAd, Boolean isValid,
			String sellerLogo, String remark) {
		this.id = id;
		this.seller = seller;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.topCarouselAd = topCarouselAd;
		this.sellerView = sellerView;
		this.bannerAd = bannerAd;
		this.isValid = isValid;
		this.sellerLogo = sellerLogo;
		this.remark = remark;
	}
	public Boolean getHasVideo() {
		if(this.hasVideo==null){
			return false;
		}
		return hasVideo;
	}
	public void setHasVideo(Boolean hasVideo) {
		this.hasVideo = hasVideo;
	}
	
	@JsonIgnore
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@JsonIgnore
	public Seller getSeller() {
		return seller;
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	@JsonIgnore
	public String getTopCarouselAd() {
		return topCarouselAd;
	}
	public void setTopCarouselAd(String topCarouselAd) {
		this.topCarouselAd = topCarouselAd;
	}
	@JsonIgnore
	public String getSellerView() {
		return sellerView;
	}
	public void setSellerView(String sellerView) {
		this.sellerView = sellerView;
	}
	@JsonIgnore
	public String getBannerAd() {
		return bannerAd;
	}
	
	public Map<String, Object> getImgMap(String basePath,String str,boolean isImg) {
		String img="";
		String url="";
		Map<String, Object> smpMap = new HashMap<>();
		if(StringUtils.isNotBlank(str) && !str.equals("{}")&&str.length()>8){
			if(str.startsWith("[")){
				JSONArray jsonArray = JSONArray.parseArray(str);
				JSONObject jsonObj = jsonArray.getJSONObject(0);
				if(StringUtils.isNotBlank(jsonObj.getString("img"))){
					JSONObject obj=jsonArray.getJSONObject(0);
					if(isImg){
						img=basePath+obj.getString("img");
						url=basePath+obj.getString("url");
					}else{
						img=basePath+obj.getString("img");
						url=obj.getString("url");
					}
				}
			}else{
				if(isImg){
					JSONObject obj=JSONObject.parseObject(str);
					img=basePath+obj.getString("img");
					url=basePath+obj.getString("url");
				}else{
					JSONObject obj=JSONObject.parseObject(str);
					img=basePath+obj.getString("img");
					url=obj.getString("url");
				}
			}
		}
		smpMap.put("imageUrl",img);
		smpMap.put("linkUrl",url);
		return smpMap;
	}
	
	
	
	
	
	
	
	public Integer getIntoShopNum() {
		return intoShopNum;
	}
	public void setIntoShopNum(Integer intoShopNum) {
		this.intoShopNum = intoShopNum;
	}
	public void setBannerAd(String bannerAd) {
		this.bannerAd = bannerAd;
	}
	@JsonIgnore
	public Boolean getIsValid() {
		return isValid;
	}
	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
	@JsonProperty("sellerIcon")
	public String getSellerLogo() {
		if(sellerLogo==null||sellerLogo.length()==0&&
				StringUtils.isNotBlank(seller.getLogo())&&seller.getLogo().length()>50){
				
				sellerLogo = this.seller.getLogo();
		}
		return sellerLogo;
	}
	public void setSellerLogo(String sellerLogo) {
		this.sellerLogo = sellerLogo;
	}
	@JsonIgnore
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getSellerId() {
		return this.seller.getId();
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerName() {
		return this.seller.getName();
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getSellerAddress() {
		return this.seller.getAddress();
	}
	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}
	public String getSellerPhone() {
		return this.seller.getPhone();
	}
	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}
	public Boolean getConcern() {
		return concern;
	}
	public void setConcern(Boolean concern) {
		this.concern = concern;
	}


	public  class InnerSellerMainPage implements java.io.Serializable{
		
		private Integer sellerId = seller.getId();
		private String sellerName = seller.getName();
		private String sellerIcon;
		private String sellerAddress = seller.getAddress();
		private String sellerPhone = seller.getPhone();
		private String shopHours;//经营时间
		private Double lat = seller.getLatitude();
		private Double lng = seller.getLongitude();
		private String basePath;
		private Boolean concern;
		private List<Object> recommendScoreGoodsList;
		private List<Object> recommendSellerGoodsList;
		private Double lat1;
		private Double lng1;
		private Double distance;//经纬度
		//da
		private String sellerIntroduce = seller.getRemark();//商店介绍
		private Integer intoShopNum = seller.getIntoShopNum()==null?0:seller.getIntoShopNum();//进店人数
		private List<Object> sellerActiveTags;// 0 没有积分没有优惠券,1 有积分 2 有优惠券 3两者都有
		

		public List<Object> getSellerActiveTags() {
			return sellerActiveTags;
		}

		public void setSellerActiveTags(List<Object> sellerActiveTags) {
			this.sellerActiveTags = sellerActiveTags;
		}




		public Double getDistance() {
			if(lng!=null && lat!=null && lng1!=null && lat1!=null){
				return getDistances(lng,lat,lng1,lat1);
			}else{
				return 0d;
			}
			
		}
		
		
		
		
		public Integer getIntoShopNum() {
			return intoShopNum;
		}




		public void setIntoShopNum(Integer intoShopNum) {
			this.intoShopNum = intoShopNum;
		}




		public String getSellerIntroduce() {
			return sellerIntroduce;
		}

		public void setSellerIntroduce(String sellerIntroduce) {
			this.sellerIntroduce = sellerIntroduce;
		}

		public void setDistance(Double distance) {
			
			this.distance = distance;
		}

		public InnerSellerMainPage() {
		}
		
		public InnerSellerMainPage(Double lat1,Double lng1) {
			this.lat1=lat1;
			this.lng1=lng1;
			
		}
		
	
		 /**
	     * 根据两个位置的经纬度，来计算两地的距离（单位为KM）
	     * 参数为String类型
	     * @param lat1 用户经度
	     * @param lng1 用户纬度
	     * @param lat2 商家经度
	     * @param lng2 商家纬度
	     * @return
	     */
	    public  Double getDistances(double long1, double lat1, double long2,  
	            double lat2) {
	  
	    	 double a, b, R;  
	         R = 6378137; // 地球半径  
	         lat1 = lat1 * Math.PI / 180.0;  
	         lat2 = lat2 * Math.PI / 180.0;  
	         a = lat1 - lat2;  
	         b = (long1 - long2) * Math.PI / 180.0;  
	         double d;  
	         double sa2, sb2;  
	         sa2 = Math.sin(a / 2.0);  
	         sb2 = Math.sin(b / 2.0);  
	         d = 2  
	                 * R  
	                 * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)  
	                         * Math.cos(lat2) * sb2 * sb2));  
	         
	         return d;  
	    	
	    	
	      
	    }
		
		
		
		
		public List<Object> getRecommendSellerGoodsList() {
			return recommendSellerGoodsList;
		}
		public void setRecommendSellerGoodsList(List<Object> recommendSellerGoodsList) {
			this.recommendSellerGoodsList = recommendSellerGoodsList;
		}
		public Integer getSellerId() {
			return sellerId;
		}
		public void setSellerId(Integer sellerId) {
			this.sellerId = sellerId;
		}
		public String getSellerName() {
			return sellerName;
		}
		public void setSellerName(String sellerName) {
			this.sellerName = sellerName;
		}
		public String getSellerIcon() {
			return basePath+AbstractSellerMainPage.this.getSellerLogo();
		}
		public void setSellerIcon(String sellerIcon) {
			this.sellerIcon = sellerIcon;
		}
		public String getSellerAddress() {
			return sellerAddress;
		}
		public void setSellerAddress(String sellerAddress) {
			this.sellerAddress = sellerAddress;
		}
		public String getSellerPhone() {
			return sellerPhone;
		}
		public void setSellerPhone(String sellerPhone) {
			this.sellerPhone = sellerPhone;
		}
		
		public String getShopHours() {
			shopHours = beginTime + " - " + endTime;
			return shopHours;
		}
		public void setShopHours(String shopHours) {
			this.shopHours = shopHours;
		}
		public Double getLat() {
			return lat;
		}
		public void setLat(Double lat) {
			this.lat = lat;
		}
		public Double getLng() {
			return lng;
		}
		public void setLng(Double lng) {
			this.lng = lng;
		}
		public List<Object> getRecommendScoreGoodsList() {
			return recommendScoreGoodsList;
		}
		public void setRecommendScoreGoodsList(
				List<Object> recommendScoreGoodsList) {
			this.recommendScoreGoodsList = recommendScoreGoodsList;
		}

		public Boolean getConcern() {
			return concern;
		}
		public void setConcern(Boolean concern) {
			this.concern = concern;
		}
		@JsonIgnore
		public String getBasePath() {
			return basePath;
		}
		public void setBasePath(String basePath) {
			this.basePath = basePath;
		}
		
	}
	
}