package com.axp.listener;

import java.util.TimerTask;


import org.hibernate.annotations.Source;

import com.axp.service.OpenGoodsService;

import org.springframework.beans.factory.annotation.Autowired;


import com.axp.util.UrlUtil;


public class OpenGoodsOrder extends TimerTask {
	@Source
	public OpenGoodsService openGoodsService;
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		syncOrder();
		 
	}
	
	private void syncOrder(){

//		String url = "http://localhost:8080/open/api/goods";

		

	}
	
	
	
}
