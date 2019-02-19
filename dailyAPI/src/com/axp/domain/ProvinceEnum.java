package com.axp.domain;

import java.sql.Timestamp;
import java.util.Set;

/**
 * ProvinceEnum entity. @author MyEclipse Persistence Tools
 */
public class ProvinceEnum extends AbstractProvinceEnum implements java.io.Serializable {

	public ProvinceEnum() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProvinceEnum(Integer id, ProvinceEnum provinceEnum, String name,
			Integer zoneId, Timestamp createTime, Boolean isValid,
			Integer level, String englishChar, Set cashshops,
			Set provinceEnums, Integer isHot, String pinYin, Integer level2,
			ProvinceEnum provinceEnum2, Integer parentId, Integer parentId2) {
		super(id, provinceEnum, name, zoneId, createTime, isValid, level, englishChar,
				cashshops, provinceEnums, isHot, pinYin, level2, provinceEnum2,
				parentId, parentId2);
		// TODO Auto-generated constructor stub
	}

	


}
