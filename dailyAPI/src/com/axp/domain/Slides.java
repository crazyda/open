package com.axp.domain;

/**
 * Slides entity. @author MyEclipse Persistence Tools
 */
public class Slides extends AbstractSlides implements java.io.Serializable {
	
	public static final int HOME1 = 10;//首页
	public static final int SPECIALITY  = 20;//本地特产
	public static final int NINENINE1 = 30;//久久人1
	public static final int NINENINE2 = 40;//久久人2
	public static final int SELLER = 50;//商家广告
	public static final int ACTIVITY = 60;//活动广告
	public static final int SCOREMALL = 70;//积分兑换
	public static final int CHANGE = 90;//换货兑换
			
	// Constructors

	/** default constructor */
	public Slides() {
	}

	/** full constructor */
	public Slides(SlidesId id, Users users) {
		// super(id, users);
	}

}
