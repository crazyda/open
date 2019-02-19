package com.axp.domain;

import java.sql.Timestamp;

public class AbstractReGoodsOfExtendActiviy implements
java.io.Serializable {
	
	
	
	private Integer id;
	
	private  Boolean isValid;
	
	private Timestamp createTime;
	
	private String tile;
	
	private String content;

	private Integer template;

	private String templateImg;
	
	private Boolean isRed;
	
	private AdminuserRedpaper adminuserRedpaper;
	
	private Integer redNum;
	
	private Double redPrice;
	
	private Seller seller;
	
	public AdminuserRedpaper getAdminuserRedpaper() {
		return adminuserRedpaper;
	}
	
	public String getContent() {
		return content;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public Integer getId() {
		return id;
	}

	public Boolean getIsRed() {
		return isRed;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public Integer getRedNum() {
		return redNum;
	}

	public Double getRedPrice() {
		return redPrice;
	}

	public Seller getSeller() {
		return seller;
	}

	public Integer getTemplate() {
		return template;
	}

	public String getTemplateImg() {
		return templateImg;
	}

	public String getTile() {
		return tile;
	}

	public void setAdminuserRedpaper(AdminuserRedpaper adminuserRedpaper) {
		this.adminuserRedpaper = adminuserRedpaper;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIsRed(Boolean isRed) {
		this.isRed = isRed;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public void setRedNum(Integer redNum) {
		this.redNum = redNum;
	}

	public void setRedPrice(Double redPrice) {
		this.redPrice = redPrice;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public void setTemplate(Integer template) {
		this.template = template;
	}

	public void setTemplateImg(String templateImg) {
		this.templateImg = templateImg;
	}

	public void setTile(String tile) {
		this.tile = tile;
	}
	

}
