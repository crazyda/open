package com.axp.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;

/**
 * 商家商家商城的商品表；
 *
 * @author Administrator
 */
public class ReGoodsOfChangeMall extends ReBaseGoods {

    private Double noStandardRedPaper;//无商品规格时的红包可抵扣；
    private String want;
    private Integer startQuantity;
    private Integer isChange;
    private String changeDesc;
    private Integer pageView; //访问量
    


	public Integer getPageView() {
		return pageView;
	}

	public void setPageView(Integer pageView) {
		this.pageView = pageView;
	}

	public String getChangeDesc() {
		return changeDesc;
	}

	public void setChangeDesc(String changeDesc) {
		this.changeDesc = changeDesc;
	}

	public Integer getIsChange() {
		return isChange;
	}

	public void setIsChange(Integer isChange) {
		this.isChange = isChange;
	}

	public String getWant() {
		return want;
	}

	public List<String> getWantArray(){
		if(StringUtils.isBlank(this.want)){
			return new ArrayList<>();
		}else{
			List<String> list=new ArrayList<>();
			String str=this.want.replace("[","");
			str=str.replaceAll("]", "");
			String[] split = str.split(",");
			
			for (String string : split) {
				
				list.add(string.replaceAll("\"", ""));
			}
			if(list.size()==0||list.size()>0&&list.get(0).length()<2){
				list.set(0,"都可以");
			}
			return list;
		}
	}
	
	public void setWant(String want) {
		this.want = want;
	}

	public Integer getStartQuantity() {
		return startQuantity;
	}

	public void setStartQuantity(Integer startQuantity) {
		this.startQuantity = startQuantity;
	}

	/**
     * getter and setter
     */

    public Double getNoStandardRedPaper() {
        return noStandardRedPaper;
    }

    public void setNoStandardRedPaper(Double noStandardRedPaper) {
        this.noStandardRedPaper = noStandardRedPaper;
    }
}
