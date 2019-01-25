package com.axp.domain;

/**
 * OpenClient entity. @author MyEclipse Persistence Tools
 */
public class Aritcles extends AbstractArticles implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public Aritcles() {
	}


	/** full constructor 
	 * @return */
	public  Aritcles(Integer id, String title, String url,
			String author, Integer cid, Integer sendTime, String content) {
		super(id,title,url,author, cid,  sendTime,  content);
		
	}
	
}
