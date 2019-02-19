package com.axp.domain;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 商家独立商城的商品表；
 * @author Administrator
 *
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" }) 
public class ReGoodsOfSellerMall extends ReBaseGoods {
    private Double noStandardRedPaper;//无商品规格时的红包可抵扣；

    /**
     * getter and setter
     */
    
    
private String rightsProtect;
    

    public String getRightsProtect() {
		return rightsProtect;
	}
    
    public JSONArray getRightsProtectToJson() {
    	
    	if(StringUtils.isNotBlank(this.rightsProtect)){
    		JSONArray array=JSONArray.parseArray(this.rightsProtect);
    		return array;
    	}else{
    		return new JSONArray();
    	}
	}

	public void setRightsProtect(String rightsProtect) {
		this.rightsProtect = rightsProtect;
	}

	public Double getNoStandardRedPaper() {
        return noStandardRedPaper;
    }

    public void setNoStandardRedPaper(Double noStandardRedPaper) {
        this.noStandardRedPaper = noStandardRedPaper;
    }

    
    
}
