package com.axp.timedtask;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.axp.service.OpenAppService;
import com.axp.service.OpenGoodsService;
import com.axp.service.OpenJDGoodsService;
import com.axp.service.OpenJDQueryCoponGoodsService;
import com.axp.util.QueryModel;
import com.axp.util.UrlUtil;


/**
 * 定时更新商品
 * @author Administrator
 *
 */
@Component  
public class IncrementGoodsJob extends QuartzJobBean {

	@Autowired
	public OpenGoodsService openGoodsService;
	@Autowired
	public OpenAppService openAppService;
	@Autowired
	public OpenJDGoodsService openJDGoodsService;
	
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		try {
			
			int endtime = (int) (System.currentTimeMillis() / 1000);
			
		  System.out.println("2分钟订单数据同步启动----"+endtime);
		  openGoodsService.locaOrder(50, 1,endtime+"");
		  Thread.sleep(4000);
		  //openJDGoodsService.synOrderListSyn(null, null);
		  	Thread.sleep(4000);
		 
		    String url3 = "http://seller.aixiaoping.com//Pdd/admins/allOrderSync.html";
			UrlUtil.getTaoKeToMapForNew(url3);
			Thread.sleep(4000);
		    String url4 = "http://axp.cupinn.com/Pdd/admins/allOrderSync.html";
			UrlUtil.getTaoKeToMapForNew(url4);
			Thread.sleep(4000);
		    String url5 = "http://lzk.518wk.cn/moreuser.php/Pdd/admins/allOrderSync.html";
			UrlUtil.getTaoKeToMapForNew(url5);
			Thread.sleep(4000);
			//String axpPddUrl = "http://www.aixiaoping.com:8080/aixiaopingAPI/invoke/order/findIncrement";
			//UrlUtil.getTaoKeToMapForNew(axpPddUrl);
			Thread.sleep(4000);
			String zzzbUrl = "http://zzzb.518wk.cn/moreuser.php/Pdd/admins/allOrderSyncByAdmin.html";
			UrlUtil.getTaoKeToMapForNew(zzzbUrl);
			Thread.sleep(4000);
			String axpjdUrl = "http://zzzb.518wk.cn/moreuser.php/jd/Admins/jd_allOrderSync";
			UrlUtil.getTaoKeToMapForNew(axpjdUrl);
			
			Thread.sleep(4000);
			String zhtjdUrl = "http://seller.aixiaoping.com/jd/Admins/jd_allOrderSync";
			UrlUtil.getTaoKeToMapForNew(zhtjdUrl);
			Thread.sleep(4000);
			String lzkjdUrl = "http://lzk.518wk.cn/moreuser.php/jd/Admins/jd_allOrderSync";
			UrlUtil.getTaoKeToMapForNew(lzkjdUrl);
			
			String jgpdd = "http://shop.aixin1819.com/moreuser.php/Pdd/admins/allOrderSync.html";
			UrlUtil.getTaoKeToMapForNew(jgpdd);
			Thread.sleep(4000);
			
			String jgjd = "http://shop.aixin1819.com/moreuser.php/jd/Admins/jd_allOrderSync";
			UrlUtil.getTaoKeToMapForNew(jgjd);
			Thread.sleep(4000);
			
			String cupinn_jd = "http://axp.cupinn.com/index.php/Jd/Admins/jd_allOrderSync";
			UrlUtil.getTaoKeToMapForNew(cupinn_jd);
			
			String zqtz_pdd = "http://zqtz.518wk.cn/moreuser.php/Pdd/admins/allOrderSync.html";
			UrlUtil.getTaoKeToMapForNew(zqtz_pdd);
			Thread.sleep(4000);
			String zqtz_jd = "http://zqtz.518wk.cn/moreuser.php/jd/Admins/jd_allOrderSync";
			UrlUtil.getTaoKeToMapForNew(zqtz_jd);
			
			
			System.out.println("订单数据同步结束----");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

}
