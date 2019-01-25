package com.axp.timedtask;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.axp.service.OpenJDQueryCoponGoodsService;

@Component 
public class JDGoodsJob extends QuartzJobBean {
	@Autowired
	public OpenJDQueryCoponGoodsService openJDQueryCoponGoodsService;
	
	
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		System.out.println("京东优惠券商品下载开始----");
		openJDQueryCoponGoodsService.coponGoods();
		openJDQueryCoponGoodsService.synCouponGoods();
		System.out.println("京东优惠券商品下载结束----");
		
	}
}
