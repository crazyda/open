package com.axp.domain;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * OrderEvaluate entity. @author MyEclipse Persistence Tools
 */
public class OrderEvaluate extends AbstractOrderEvaluate implements java.io.Serializable {

	private ArrayList<String> imgUrls;

	// Constructors

	/** default constructor */
	public OrderEvaluate() {
	}

	/** full constructor */
	public OrderEvaluate(Cashshop cashshop, Users users, String content, String imgUrl, Boolean isValid, Timestamp createTime, Integer score) {
		super(cashshop, users, content, imgUrl, isValid, createTime, score);
	}

}
