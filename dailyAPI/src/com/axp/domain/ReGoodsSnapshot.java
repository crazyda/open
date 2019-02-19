package com.axp.domain;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 基础商品表的快照表；
 * 规则：
 * 1，当基础商品表新增一条信息时，此处新增一条信息；
 * 2，当基础商品修改非核心属性时，此处同步更改相应属性；
 * 3，当基础商品更改敏感属性时，基础商品更改属性，快照表新增一条快照，原来的快照失效；
 *
 * @author Administrator
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" }) 
public class ReGoodsSnapshot extends ReBaseDomain {

    private String sign;//对应的基础商品的商品编号；
    private String name;//商品名称；
    private String coverPic;//商品的封面图片；
    private String type;//商品的类别；1，商品的类别是由我们定好的，商家不能自定义类别；2，最多可以选择三个商品类别；3，使用json的格式记录商品的类别；
    private String lables;//商品的标签；1，标签也是预先定义好的；2，同样使用json的格式记录标签数据；
    private String descriptionPics;//商品简述图；1，最多五张；2，使用“&&&”符号隔开，拼成字符串保存，不关联任何表；
    private Seller seller;//商品对应的商家id值；
    private String intro;//商品的描述文字；
    private String details;//商品的详细介绍内容；
    private Integer baseGoodsId;//快照商品对应的基础商品id值；
    private Integer checkStatus = 0;//审核状态；0：表示未审核，1：审核通过；2：审核未通过；
    
    private Integer salesVolume = 0;
    
    private String dpi = "nomal";

    //=================get/set==================
    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverPic() {
        return coverPic.replace("nomal/", dpi+"/");
    }

    public String getCoverPicForUse() {
        String s = "";
        try {
            JSONArray jsonArray = JSONObject.parseArray(coverPic.replace("nomal/", dpi+"/"));
            s = jsonArray.getJSONObject(0).get("imgUrl").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public void setCoverPic(String coverPic) {
    	this.coverPic = coverPic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLables() {
        return lables;
    }

    public void setLables(String lables) {
        this.lables = lables;
    }

    public String getDescriptionPics() {
        return descriptionPics.replace("nomal/", dpi+"/");
    }

    public void setDescriptionPics(String descriptionPics) {
        this.descriptionPics = descriptionPics;
    }


    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getBaseGoodsId() {
        return baseGoodsId;
    }

    public void setBaseGoodsId(Integer baseGoodsId) {
        this.baseGoodsId = baseGoodsId;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

	public Integer getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(Integer salesVolume) {
		this.salesVolume = salesVolume;
	}

	public String getDpi() {
		return dpi;
	}

	public void setDpi(String dpi) {
		this.dpi = dpi;
	}

	/**
	 * 添加资源路径前缀
	 * @param prefix
	 * @return
	 */
	public List<String> getPrefixCoverPic(String prefix){
		
		JSONArray picArray=JSONArray.parseArray( this.coverPic);
		List<String> list=new ArrayList<>();
		for (int i = 0; i < picArray.size(); i++) {
			JSONObject obj = picArray.getJSONObject(i);
			list.add(prefix+obj.getString("imgUrl"));
		}
		return list;
	}
}
