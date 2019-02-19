package com.axp.listener;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.scheduling.config.Task;

import com.axp.domain.ScoreModel;

/*** 
 *<p>Title: 定时触发器</p>
 *<p>Description: </p> 
 *<p>Company: 广州每天积分信息科技有限公司</p>
 * @author hzc* @date 2015-7-7
 */
public class InitListener implements ServletContextListener {
	private final static int HOUR = 00;
	private final static int MINUTES = 00;
	
	public InitListener(){
		goTimer();
	}
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		ServletContext servletContext = event.getServletContext();
		Map<Integer, List<ScoreModel>> userScore=new HashMap<Integer, List<ScoreModel>>();
		servletContext.setAttribute("userScore",userScore);
		
	}

	private void goTimer() {
 		Timer timmerTask = new Timer();
    	
		Calendar calEnviron = Calendar.getInstance();
		// 每天的开始执行时间
		calEnviron.set(Calendar.HOUR_OF_DAY, HOUR);
		calEnviron.set(Calendar.MINUTE, MINUTES);
		// date为制定时间
		Date dateSetter = new Date();
		dateSetter = calEnviron.getTime();
		// nowDate为当前时间
		Date nowDateSetter = new Date();
		// 所得时间差为，距现在待触发时间的间隔
		long intervalEnviron = dateSetter.getTime() - nowDateSetter.getTime();
		if (intervalEnviron < 0) {
			calEnviron.add(Calendar.DAY_OF_MONTH, 1);
			dateSetter = calEnviron.getTime();
			intervalEnviron = dateSetter.getTime() - nowDateSetter.getTime();
		}
		// 每24小时执行一次
		//消息预警
		int times = 1 * 1000 * 60 * 60 * 24;
		//会员商城自动结单
	//	timmerTask.schedule(new MallCloseOrderTimerTask(), intervalEnviron, times);
		//更新红包当天个数
	//	timmerTask.schedule(new NewRedPaperTimerTask(), intervalEnviron, times);
		
		
	}
	
}
