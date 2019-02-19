package com.axp.service;

import java.util.List;

import com.axp.domain.ProvinceEnum;
import com.axp.domain.Shoptypes;


public interface IProfessionalService extends IBaseService{

	 //获取所有商品类型
	 public List<Shoptypes> getShopTypeList();
	
	 public Integer getZoneId(String province,String city,String county);
	 //获取城市省份
	 public List<ProvinceEnum> getZone(Integer level);
	 public List<ProvinceEnum> getZoneById(Integer id,Integer level);
	 
}
