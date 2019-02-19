package com.axp.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AbstractProvinceEnum entity provides the base persistence definition of the
 * ProvinceEnum entity. @author MyEclipse Persistence Tools
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public abstract class AbstractProvinceEnum implements java.io.Serializable {

	// Fields

	private Integer id;
	private ProvinceEnum provinceEnum;
	private String name;
	private Integer zoneId;
	private Timestamp createTime;
	private Boolean isValid;
	private Integer level;
	private String englishChar;
	private Set cashshops = new HashSet(0);
	private Set provinceEnums = new HashSet(0);
	private Integer isHot;
	private String pinYin;
	private Integer level2;
	private ProvinceEnum provinceEnum2; 
	private Integer parentId;
	private Integer parentId2;
	
	
	
	
	



	public AbstractProvinceEnum(Integer id, ProvinceEnum provinceEnum,
			String name, Integer zoneId, Timestamp createTime, Boolean isValid,
			Integer level, String englishChar, Set cashshops,
			Set provinceEnums, Integer isHot, String pinYin, Integer level2,
			ProvinceEnum provinceEnum2, Integer parentId, Integer parentId2) {
		super();
		this.id = id;
		this.provinceEnum = provinceEnum;
		this.name = name;
		this.zoneId = zoneId;
		this.createTime = createTime;
		this.isValid = isValid;
		this.level = level;
		this.englishChar = englishChar;
		this.cashshops = cashshops;
		this.provinceEnums = provinceEnums;
		this.isHot = isHot;
		this.pinYin = pinYin;
		this.level2 = level2;
		this.provinceEnum2 = provinceEnum2;
		this.parentId = parentId;
		this.parentId2 = parentId2;
	}



	// Constructors

	public Integer getLevel2() {
		return level2;
	}



	public Integer getParentId() {
		return parentId;
	}



	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}



	public Integer getParentId2() {
		return parentId2;
	}



	public void setParentId2(Integer parentId2) {
		this.parentId2 = parentId2;
	}



	public void setLevel2(Integer level2) {
		this.level2 = level2;
	}


	@JsonIgnore
	public ProvinceEnum getProvinceEnum2() {
		return provinceEnum2;
	}



	public void setProvinceEnum2(ProvinceEnum provinceEnum2) {
		this.provinceEnum2 = provinceEnum2;
	}



	public String getPinYin() {
		return pinYin;
	}



	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}



	public Integer getIsHot() {
		return isHot;
	}



	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}



	/** default constructor */
	public AbstractProvinceEnum() {
	}

	

	// Property accessors
	@JsonProperty("zoneId")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@JsonIgnore
	public ProvinceEnum getProvinceEnum() {
		return this.provinceEnum;
	}
	
	public void setProvinceEnum(ProvinceEnum provinceEnum) {
		this.provinceEnum = provinceEnum;
	}
	@JsonProperty("name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@JsonIgnore
	public Integer getZoneId() {
		return this.zoneId;
	}

	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}
	@JsonIgnore
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	@JsonIgnore
	public Boolean getIsValid() {
		return this.isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
	@JsonProperty("level")
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	@JsonProperty("englishChar")
	public String getEnglishChar() {
		return this.englishChar;
	}

	public void setEnglishChar(String englishChar) {
		this.englishChar = englishChar;
	}
	@JsonIgnore
	public Set getCashshops() {
		return this.cashshops;
	}

	public void setCashshops(Set cashshops) {
		this.cashshops = cashshops;
	}
	@JsonIgnore
	public Set getProvinceEnums() {
		return this.provinceEnums;
	}

	public void setProvinceEnums(Set provinceEnums) {
		this.provinceEnums = provinceEnums;
	}

	@JsonProperty("parentZoneId")
	public Integer getProvinceEnumId(){
		if(this.provinceEnum==null){
			return -1;
		}else{
			return this.provinceEnum.getId();
		}
	}
	
	/*@JsonProperty("parentZoneId2")*/
	@JsonIgnore
	public Integer getProvinceEnumId2(){
		if(this.provinceEnum2==null){
			return -1;
		}else{
			return this.provinceEnum2.getId();
		}
	}
}