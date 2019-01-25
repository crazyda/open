package com.axp.domain;

/**
 * AbstractOpenClient entity provides the base persistence definition of the
 * OpenClient entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractArticles implements java.io.Serializable {

	// Fields

	private Integer id;
	private String title;
	private String url;
	private String author;
	private Integer cid;
	private Integer sendTime;
	private String content;
	
	
	
	public AbstractArticles() {
		super();
	}

	public AbstractArticles(Integer id, String title, String url,
			String author, Integer cid, Integer sendTime, String content) {
		super();
		this.id = id;
		this.title = title;
		this.url = url;
		this.author = author;
		this.cid = cid;
		this.sendTime = sendTime;
		this.content = content;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getSendTime() {
		return sendTime;
	}

	public void setSendTime(Integer sendTime) {
		this.sendTime = sendTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	

}