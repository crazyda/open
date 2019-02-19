package com.axp.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public abstract class AbstractItalkGroup implements Serializable {
//da
	// Fields
		private Integer id;
		private Integer groupId;
		private Integer groupType;
		private Users users;
		private String name;
		private String discription;
		private Boolean isValid;
		private Timestamp createTime;
		private Timestamp lastTime;
		
		
		private String imgUrl;
		// Constructors

		/** default constructor */
		public AbstractItalkGroup() {
		}
		
		public AbstractItalkGroup(Users users,Integer groupId,Integer groupType, String name, String discription, 
				Boolean isValid, Timestamp createTime, Timestamp lastTime) {
			this.users = users;
			this.groupId = groupId;
			this.groupType = groupType;
			this.name = name;
			this.discription = discription;
			this.isValid = isValid;
			this.createTime = createTime;
			this.lastTime = lastTime;
			
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Integer getGroupId() {
			return groupId;
		}

		public void setGroupId(Integer groupId) {
			this.groupId = groupId;
		}

		public Integer getGroupType() {
			return groupType;
		}

		public void setGroupType(Integer groupType) {
			this.groupType = groupType;
		}

		public Users getUsers() {
			return users;
		}

		public void setUsers(Users users) {
			this.users = users;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDiscription() {
			return discription;
		}

		public void setDiscription(String discription) {
			this.discription = discription;
		}

		public Boolean getIsValid() {
			return isValid;
		}

		public void setIsValid(Boolean isValid) {
			this.isValid = isValid;
		}

		public Timestamp getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Timestamp createTime) {
			this.createTime = createTime;
		}

		public Timestamp getLastTime() {
			return lastTime;
		}

		public void setLastTime(Timestamp lastTime) {
			this.lastTime = lastTime;
		}

		
		public String getImgUrl() {
			return imgUrl;
		}

		public void setImgUrl(String imgUrl) {
			this.imgUrl = imgUrl;
		}
		
		
		
}
