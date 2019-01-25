package com.test;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.axp.dao.DateBaseDAO;
import com.axp.dao.OpenGoodsCurrentDAO;
import com.axp.util.CalcUtil;
import com.axp.util.DateUtil;

public class Tests {
	@Resource
	private DateBaseDAO dateBaseDAO;
	
	@Autowired 
	  public OpenGoodsCurrentDAO openGoodsCurrentDAO; 
	@Test
	public void date2() throws ParseException{
//		System.out.println("System.currentTimeMillis()-----"+System.currentTimeMillis());
//		System.out.println("Calendar.DATE");
//		//System.out.println(Calendar.getInstance());
//		System.out.println("new Date()-----"+new Date());
//		System.out.println( new SimpleDateFormat("HH:mm:ss").format(new Date()));
					SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
					 Date beginDate = format.parse("2018-11-02");
					 Date  endDate= format.parse("2018-11-03");    
		             int day=(int) ((endDate.getTime()-beginDate.getTime())/(24*60*60*1000));    
		           System.out.println("相隔的天数="+day);   
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(new Date());
//		int hour = cal.get(cal.HOUR_OF_DAY); //获得时
//		int minute = cal.get(cal.MINUTE); //获得分
//		int second = cal.get(cal.SECOND); //获得秒 
//		Calendar cal2 = Calendar.getInstance();
//		cal2.setTime(new Date());
//		cal2.set(Calendar.HOUR_OF_DAY, 9);
//		cal2.set(Calendar.MINUTE, 11);
//		cal2.set(Calendar.SECOND, 10);
//		DateFormat df3 = DateFormat.getTimeInstance();//只显示出时分秒
//		//DateUtil.addHour2Date(2, df3.parse(lm.getItemLastTime())).getTime() - cal.getTimeInMillis();
//		Long bb = cal2.getTimeInMillis()-cal.getTimeInMillis();
//		System.out.println(bb);
//		System.out.println(new SimpleDateFormat("HH:mm:ss").format(cal.getTime()));

//		
//		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()));
//		
		
		
//		System.out.println(DateUtil.getDayEnd(new Date()));
		
		
		
	}

	@Test
	public void date(){
		Calendar cale =  Calendar.getInstance();
        int month = cale.get(Calendar.MONTH);
        int year = cale.get(Calendar.YEAR);
        String startTime = year+"/"+month+"/20 00:00:00";
        String endTime = year+"/"+month+"/21 23:59:59";
    	if(DateUtil.belongCalendar(new Date(), new Date(Date.parse(startTime)), new Date(Date.parse(endTime)))){
    		System.out.println("1");
    		
    	}else{
    		System.out.println("2");
    		
    	}
	}
	@Test
	public void date1(){
		List<String> a = new ArrayList<String>();
		
		a.add("--");
		a.add("2");
		a.add("++");
		a.add("a");
		a.add("e");
		a.add("e");
		a.add("e");
		System.out.println(a.toString());
		String b = a.get(2);//中2
		String c = a.get(0);
		
		System.out.println(a.toString());
		 HashSet h = new HashSet(a);   
		    a.clear();   
		    a.addAll(h);  
		System.out.println(a.toString());
		
	}

}


