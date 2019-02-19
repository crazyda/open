package com.axp.domain;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
/**
 *  换货会帖子
 */
public class ChangeNote implements java.io.Serializable  {

	
	private List<ReGoodsOfChangeMall> changeMallList;  //没有映射 用来储存商品列表
	
	private String content;  //内容
	
	private Timestamp createTime; 
	
	private String goodsIds;  //商品列表
	
	private Integer id;

	private Boolean isValid;

	private Timestamp lastTopTime;  //最后置顶时间
	
	private Users sendUsers;  //发帖人
	
	private Boolean checkStatus;
	
	private String coverPic;
	
	private String checkRemark;
	
	
	
	
	
	
	
	public String getCheckRemark() {
		return checkRemark;
	}

	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}

	public String getCoverPic() {
		return coverPic;
	}

	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}

	public List<ReGoodsOfChangeMall> getChangeMallList() {
		return changeMallList;
	}
	
	public String getContent() {
		return content;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public String getGoodsIds() {
		return goodsIds;
	}

	public Integer getId() {
		return id;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public Timestamp getLastTopTime() {
		return lastTopTime;
	}

	public Users getSendUsers() {
		return sendUsers;
	}


	public void setChangeMallList(List<ReGoodsOfChangeMall> changeMallList) {
		this.changeMallList = changeMallList;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public void setGoodsIds(String goodsIds) {
		this.goodsIds = goodsIds;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public void setLastTopTime(Timestamp lastTopTime) {
		this.lastTopTime = lastTopTime;
	}

	public void setSendUsers(Users sendUsers) {
		this.sendUsers = sendUsers;
	}

	public Boolean getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Boolean checkStatus) {
		this.checkStatus = checkStatus;
	}

	
}
