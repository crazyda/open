package com.axp.listener;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/*** 
 *<p>Title: 定时触发器</p>
 *<p>Description: </p> 
 *<p>Company: 广州爱小屏信息科技有限公司</p>
 * @author hzc* @date 2015-7-7
 */
public class InitListener implements ServletContextListener {
	private final static int HOUR = 00;
	private final static int MINUTES = 00;
	public InitListener(){
		goTimer();
		goTest();
	}
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		// context销毁时，销毁初始化数据
		
		//销毁时 执行触发积分计算功能
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		
	}

	private void goTimer() { // 每天同步 商品数据 goods()
 		Timer timmerTask = new Timer();
		Calendar calEnviron = Calendar.getInstance();
//	
	}
	
	
	
	private void goTest() { // 每个月20号同步 订单数据  已结算
 		
	}
	
}


