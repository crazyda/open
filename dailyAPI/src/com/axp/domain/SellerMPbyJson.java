package com.axp.domain;
/**
 * sellerMainPage实体类的json对象
 * @author Administrator
 *
 */
public class SellerMPbyJson {

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
	@Override
	public String toString() {
		return "SellerMPbyJson [img=" + img + ", url=" + url + "]";
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
