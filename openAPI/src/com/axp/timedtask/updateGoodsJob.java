package com.axp.timedtask;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.axp.service.OpenGoodsService;
import com.axp.service.OpenJDGoodsService;
import com.axp.service.OpenJDQueryCoponGoodsService;
import com.axp.util.UrlUtil;


/**
 * 定时更新商品
 * @author Administrator
 *
 */
@Component  
public class updateGoodsJob extends QuartzJobBean {

	@Autowired
	public OpenGoodsService openGoodsService;
	@Autowired
	public OpenJDQueryCoponGoodsService openJDQueryCoponGoodsService;
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		try {
			/*System.out.println("30分钟商品同步启动 测试");
			openJDQueryCoponGoodsService.synCouponGoods();
			System.out.println("30分钟同步结束");*/
			
		  System.out.println("2小时商品同步启动");
		  openGoodsService.getPDDGoods();
		  System.out.println("拼多多商品同步结束");
		  Thread.sleep(5*60*1000);
		  
		  System.out.println("真惠挑商品启动");
		  String url2 = "http://seller.aixiaoping.com/Pdd/admins/sync";
			UrlUtil.getTaoKeToMapForNew(url2);
		  Thread.sleep(5*60*1000);
		  System.out.println("咖品汇商品启动");
		    String url = "http://axp.cupinn.com/Pdd/admins/sync";
			UrlUtil.getTaoKeToMapForNew(url);
			Thread.sleep(5*60*1000);
			System.out.println("荔枝客商品启动");
		    String url3 = "http://lzk.518wk.cn/moreuser.php/pdd/Admins/sync";
			UrlUtil.getTaoKeToMapForNew(url3);
			Thread.sleep(5*60*1000);
			System.out.println("正在周边商品启动");
			String url4 = "http://zzzb.518wk.cn/moreuser.php/pdd/Admins/sync";
			UrlUtil.getTaoKeToMapForNew(url4);
			Thread.sleep(5*60*1000);
			System.out.println("链豆商品启动");
			String url5 = "http://ldwd.gzyunjifen.com/Pdd/admins/sync";
			//UrlUtil.getTaoKeToMapForNew(url5);
			System.out.println("多用户商品同步启动");
			String url6= "http://hfljy.518wk.cn/Jd/Admins/sync_java";
			UrlUtil.getTaoKeToMapForNew(url6);
			Thread.sleep(5*60*1000);
			String url6_1= "http://hfljy.518wk.cn/Jd/admins/sync_bursting?type=1";
			UrlUtil.getTaoKeToMapForNew(url6_1);
			Thread.sleep(5*60*1000);
			String url6_2= "http://hfljy.518wk.cn/Jd/admins/sync_bursting?type=2";
			UrlUtil.getTaoKeToMapForNew(url6_2);
			Thread.sleep(5*60*1000);
			
			
			
			
			System.out.println("真会挑商品同步启动");
			String url7= "http://seller.aixiaoping.com/Jd/Admins/sync_java";
			UrlUtil.getTaoKeToMapForNew(url7);
			Thread.sleep(5*60*1000);
			System.out.println("荔枝客商品同步启动");
			String url8= "http://lzk.518wk.cn/moreuser.php/Jd/Admins/sync_java";
			UrlUtil.getTaoKeToMapForNew(url8);
			Thread.sleep(5*60*1000);
			String url8_1= "http://lzk.518wk.cn/moreuser.php/Jd/admins/sync_bursting?type=1";
			UrlUtil.getTaoKeToMapForNew(url8_1);
			Thread.sleep(5*60*1000);
			String url8_2= "http://lzk.518wk.cn/moreuser.php/Jd/admins/sync_bursting?type=2";
			UrlUtil.getTaoKeToMapForNew(url8_2);
			Thread.sleep(5*60*1000);
			System.out.println("金谷pdd商品启动");
			String jg = "http://shop.aixin1819.com/moreuser.php/pdd/Admins/sync";
			UrlUtil.getTaoKeToMapForNew(jg);
			Thread.sleep(5*60*1000);
			
			System.out.println("金谷jd商品同步启动");
			String url9= "http://shop.aixin1819.com/Jd/Admins/sync_java";
			UrlUtil.getTaoKeToMapForNew(url9);
			Thread.sleep(5*60*1000);
			String url9_1= "http://shop.aixin1819.com/Jd/admins/sync_bursting?type=1";
			UrlUtil.getTaoKeToMapForNew(url9_1);
			Thread.sleep(5*60*1000);
			String url9_2= "http://shop.aixin1819.com/Jd/admins/sync_bursting?type=2";
			UrlUtil.getTaoKeToMapForNew(url9_2);
			Thread.sleep(5*60*1000);
			String cupinn_1= "http://axp.cupinn.com/index.php/Jd/Admins/sync_bursting?type=2 ";
			UrlUtil.getTaoKeToMapForNew(cupinn_1);
			Thread.sleep(5*60*1000);
			String cupinn_2= "http://axp.cupinn.com/index.php/Jd/Admins/sync_bursting?type=1  ";
			UrlUtil.getTaoKeToMapForNew(cupinn_2);
			Thread.sleep(5*60*1000);
			String cupinn= "http://axp.cupinn.com/index.php/Jd/Admins/sync   ";
			UrlUtil.getTaoKeToMapForNew(cupinn);
			Thread.sleep(5*60*1000);
			
			//中泉投资
			System.out.println("中泉投资拼多多商品同步");
			String zqtz = "http://zqtz.518wk.cn/moreuser.php/pdd/Admins/sync";
			UrlUtil.getTaoKeToMapForNew(zqtz);
			Thread.sleep(5*60*1000);
			System.out.println("中泉投资商品同步启动");
			String zqtz1= "http://zqtz.518wk.cn/moreuser.php/Jd/Admins/sync_java";
			UrlUtil.getTaoKeToMapForNew(zqtz1);
			Thread.sleep(5*60*1000);
			
			
			
			System.out.println("2小时同步结束");
		  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
