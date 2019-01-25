package com.axp.timedtask;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.axp.service.OpenClientService;
import com.axp.service.OpenGoodsService;
import com.axp.service.OpenThemeService;
import com.axp.util.UrlUtil;


/**
 * 定时查看订单是否超过三个月了, 超过就删除并不保存到历史订单,反之
 * @author Administrator
 *
 */
@Component  
public class HistoryGoodsJob extends QuartzJobBean {

	@Autowired
	public OpenGoodsService openGoodsService;
	@Autowired
	public OpenClientService openClientService;
	@Autowired
	public OpenThemeService openThemeService;
	
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		try {
		 
		 /*   String url2 = "http://seller.aixiaoping.com/Pdd/admins/sync";
			UrlUtil.getTaoKeToMapForNew(url2);
		  Thread.sleep(4000);
		    String url = "http://axp.cupinn.com/Pdd/admins/sync";
			UrlUtil.getTaoKeToMapForNew(url);
		  Thread.sleep(4000);
		    String url3 = "http://lzk.aixiaoping.cn/Pdd/admins/sync";
			UrlUtil.getTaoKeToMapForNew(url3);
			Thread.sleep(4000);
			String url4 = "http://zzzb.aixiaoping.cn/Pdd/admins/sync";
			UrlUtil.getTaoKeToMapForNew(url4);*/
		 

		  System.out.println("添加上级pid----");
		  //openClientService.saveUpPid();
		  openGoodsService.getPDDGoods();
		  openThemeService.synThmemList(null);
		  System.out.println("添加上级pid结束----");
		  

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

}
