package com.axp.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;



public abstract class AbstractUserFriendsInfo implements java.io.Serializable {
	
	
	private Integer id ;
	private Boolean isValid ;
	private Users user;
	private Timestamp createTime;
	private String info ;
	private String infoImg;
	private Integer concernNum;
	private Integer praiseNum;
	private Integer appreciate;
	
	
	
	public AbstractUserFriendsInfo() {
		super();
	}
	
	public AbstractUserFriendsInfo(Integer id, Boolean isValid, Users user, Timestamp createTime, String info,
			String infoImg, Integer concernNum, Integer praiseNum, Integer appreciate) {
		super();
		this.id = id;
		this.isValid = isValid;
		this.user = user;
		this.createTime = createTime;
		this.info = info;
		this.infoImg = infoImg;
		this.concernNum = concernNum;
		this.praiseNum = praiseNum;
		this.appreciate = appreciate;
	}

	public String getInfoImg() {
		return infoImg;
	}

	public void setInfoImg(String infoImg) {
		this.infoImg = infoImg;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Boolean getIsValid() {
		return isValid;
	}
	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Integer getConcernNum() {
		return concernNum;
	}
	public void setConcernNum(Integer concernNum) {
		this.concernNum = concernNum;
	}
	public Integer getPraiseNum() {
		return praiseNum;
	}
	public void setPraiseNum(Integer praiseNum) {
		this.praiseNum = praiseNum;
	}
	public Integer getAppreciate() {
		return appreciate;
	}
	public void setAppreciate(Integer appreciate) {
		this.appreciate = appreciate;
	}
	
	
	
	
}