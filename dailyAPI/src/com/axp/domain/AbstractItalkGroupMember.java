package com.axp.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public abstract class AbstractItalkGroupMember implements Serializable {
	// Fields da

		private Integer id;
		private ItalkGroup italkGroup;
		private Users users;
		private Integer type;
		private Boolean isValid;
		private Boolean isForbid;
		private Timestamp createTime;
		
		
		public AbstractItalkGroupMember() {
			super();
		}


		public AbstractItalkGroupMember(Integer id, 
				ItalkGroup italkGroup, Users users, Integer type,
				Boolean isValid, Boolean isForbid,Timestamp createTime) {
			super();
			this.id = id;
			
			this.italkGroup = italkGroup;
			this.users = users;
			this.type = type;
			this.isValid = isValid;
			this.isForbid = isForbid;
			this.createTime = createTime;
		}


		public Boolean getIsForbid() {
			return isForbid;
		}


		public void setIsForbid(Boolean isForbid) {
			this.isForbid = isForbid;
		}


		public Integer getId() {
			return id;
		}


		public void setId(Integer id) {
			this.id = id;
		}


		

		public ItalkGroup getItalkGroup() {
			return italkGroup;
		}


		public void setItalkGroup(ItalkGroup italkGroup) {
			this.italkGroup = italkGroup;
		}


		public Users getUsers() {
			return users;
		}


		public void setUsers(Users users) {
			this.users = users;
		}


		public Integer getType() {
			return type;
		}


		public void setType(Integer type) {
			this.type = type;
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
		
		
}
