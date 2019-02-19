package com.axp.domain;

public abstract class AbstractSellerAccountNumber  implements java.io.Serializable{
	private Integer id;
	private String name;
	private String sellerId;
	private String userId;
	private Seller seller;
	private Users user;
	private Integer level;
	private Boolean isValid;
	public static Integer LEVEL_MAIN = 0;
	public static Integer LEVEL_SECOND =1;
	public AbstractSellerAccountNumber() {
	}
	
	public AbstractSellerAccountNumber(Integer id, String name,String sellerId,String userId, Seller seller,
			Users user, Integer level, Boolean isValid) {
		this.id = id;
		this.name = name;
		this.sellerId = sellerId;
		this.userId =userId;
		this.seller = seller;
		this.user = user;
		this.level = level;
		this.isValid = isValid;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Seller getSeller() {
		return seller;
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Boolean getIsValid() {
		return isValid;
	}
	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
