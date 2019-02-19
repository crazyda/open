package com.axp.domain;

public class SellerMainPbyJson {
	private String imageUrl;
	private String linkUrl;
	private String img;
	private String url;
	
	
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	@Override
	public String toString() {
		return "SellerMainPbyJson [imageUrl=" + imageUrl + ", linkUrl=" + linkUrl + ", img=" + img + ", url=" + url
				+ "]";
	}
	
	
	
}
