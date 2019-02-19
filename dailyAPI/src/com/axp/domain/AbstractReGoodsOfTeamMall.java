package com.axp.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class AbstractReGoodsOfTeamMall extends ReBaseGoods implements Serializable{

	
	
	private String coverPic; //轮播图
	
	private Timestamp createTime;

	private String descriptionPics; //简述图
	
	private Double discountPrice;

	private Integer topType; //1 大图 2小图

	private Integer id;


	private String carouselPic;
	
	public String getCarouselPic() {
		return carouselPic;
	}
	public void setCarouselPic(String carouselPic) {
		this.carouselPic = carouselPic;
	}

	private Boolean isRestrict; //是否限购

	private String name;
	
	private Integer releaseNum; //发布数量

	private Integer restrictNum; //限购数量

	private Seller seller;
	
	private Integer teamNum; //最低拼团人数 

	public AbstractReGoodsOfTeamMall(){
		
	}
	public Integer getTopType() {
		return topType;
	}

	public void setTopType(Integer topType) {
		this.topType = topType;
	}


	public String getCoverPic() {
		return coverPic;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public String getDescriptionPics() {
		return descriptionPics;
	}

	public Double getDiscountPrice() {
		return discountPrice;
	}

	public String getCoverPicOne() {
	    if (StringUtils.isEmpty(coverPic))
	        return "";
	    JSONArray list = JSONObject.parseArray(coverPic);
	    if (list.size() == 0)
	        return "";
	    return list.getJSONObject(0).getString("imgUrl");
	}
	
	public   String getCutPic() {
        if (StringUtils.isEmpty(coverPic))
            return "";
        JSONArray list = JSONObject.parseArray(coverPic);
        if (list.size() == 0)
            return "";
        
        
      //  String img ="upload-res/basegoods_detail/377/240/2592961207111217811.jpg";
        
        String img=list.getJSONObject(0).getString("imgUrl");
        
        int last=img.lastIndexOf("/");
        
        String lastStr=img.substring(last,img.length());  //拿到最后的
        
        String tempStr=img.substring(0,last);
        
        int start = tempStr.lastIndexOf("/");
        
        String startStr=img.substring(0,start+1);
        
        String imgPath=startStr+"cut"+lastStr;
        
        
        return imgPath;
        		
    }
	

	public Boolean getIsRestrict() {
		return isRestrict;
	}

	public String getName() {
		return name;
	}


	public Integer getReleaseNum() {
		return releaseNum;
	}

	public Integer getRestrictNum() {
		return restrictNum==null?0:restrictNum;
	}


	public Seller getSeller() {
		return seller;
	}


	public Integer getTeamNum() {
		return teamNum;
	}



	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}


	public void setDescriptionPics(String descriptionPics) {
		this.descriptionPics = descriptionPics;
	}

	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}

	
	public void setIsRestrict(Boolean isRestrict) {
		this.isRestrict = isRestrict;
	}
	
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	public void setReleaseNum(Integer releaseNum) {
		this.releaseNum = releaseNum;
	}
	
	public void setRestrictNum(Integer restrictNum) {
		this.restrictNum = restrictNum;
	}
	
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	
	public void setTeamNum(Integer teamNum) {
		this.teamNum = teamNum;
	}
	
}
