package com.axp.timedtask;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.axp.service.OpenGoodsService;
import com.axp.util.MD5Util;
import com.axp.util.Parameter;


/**
 * 定时更新商品
 * @author Administrator
 *
 */
@Component  
public class orderCountJob extends QuartzJobBean {

	@Autowired
	public OpenGoodsService openGoodsService;
	@Autowired
	private  HttpServletRequest request;
	
	private static String client_id ="cbde395c33244e979908d4d305d5ea8b";
	private static String client_secret ="3ef2184d38ba2d741679b67a2506780b94562394";
	
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		try {
		  System.out.println("商品结算同步启动----");
		  //未完成
		  openGoodsService.orderCount();
		  System.out.println("商品结算同步结束----");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

}
