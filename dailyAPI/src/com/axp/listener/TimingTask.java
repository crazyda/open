package com.axp.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.axp.service.ScoreMarkService;

@Component
@EnableScheduling
public class TimingTask extends QuartzJobBean {
	
	@Autowired
	ScoreMarkService scoreMarkService;

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		
	}
	@Scheduled(cron = "0 0 0 * * ?")
	public void timingTask(){
		
		scoreMarkService.timingTask();
		
	}
	
}