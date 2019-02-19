package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.axp.dao.DateBaseDAO;
import com.axp.domain.CashshopTimes;
import com.axp.util.AESUtil;
import com.axp.util.CalcUtil;
import com.axp.util.CityUtil;
import com.axp.util.DateUtil;
import com.axp.util.MD5Util;
import com.axp.util.OrderUtil;
import com.axp.util.QueryModel;
import com.axp.util.StringUtil;
import com.axp.util.UrlUtil;
import com.vdurmont.emoji.EmojiParser;
import com.weixin.config.WeixinConfig;
@Component("taskJob") 
public class Testda {

	@Resource
	private DateBaseDAO dateBaseDAO;
	
	
	@Test
	public void date5(){
		 String str = "Here is a boy: \ue159"; 
		 System.out.println("原始字符为：\n" + str); 
		 System.out.println("to aliases 之后："); 
		 System.out.println(EmojiParser.parseToAliases(str)); 
		 System.out.println(EmojiParser.parseToAliases(str, EmojiParser.FitzpatrickAction.PARSE)); 
		 System.out.println(EmojiParser.parseToAliases(str, EmojiParser.FitzpatrickAction.REMOVE)); 
		 System.out.println(EmojiParser.parseToAliases(str, EmojiParser.FitzpatrickAction.IGNORE));
		 
		
	}
	
	
	
	@Test
	public void date3(){
		for(int i =0;i<100;i++){
			
			int random = (int) Math.round(Math.random()*1);
		System.out.println(random);
		}
		
	}
	
	
	@Test
	public void date2() throws ParseException{
		CityUtil.map_tx2bd(23.12908, 113.26436);
		CityUtil.Convert_BD09_To_GCJ02(23.12908, 113.26436);
	}
	
	@Test  
	public void date4() throws ParseException{
		
		System.out.println((int) Math.round(Math.random()*2));
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		
		
		//方法1
	
		for(int i=1;i<10;i++){
			 int a = (int)(1+Math.random()*(4-1+1));
			 if(map != null && map.size()>0){
				 if(map.containsKey(a)){
					 map.put(a, map.get(a)+1);
				 }
				 
			 }else{
				 map.put(1, 1);
			 }
			
		}
		Set keys = map.keySet();
		for(Object obj:keys){
			Integer key = (Integer)obj; // 商家id
			Integer value = (Integer) map.get(key);
			System.out.println(key +"-----"+value);
		}
	}
	
	
	@Test
	public void date() throws Exception{
		
		
		System.out.println("13229479739".replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
		String time = "0810";
		String st= "0130";
		
		
		
		
		
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
		long result = 0;
		try {
			result = sdf.parse(time).getTime() - sdf.parse(st).getTime()+sdf1.parse("1970").getTime();
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//加上1970年

		System.out.println(result);
		
//		Date toDate = new Date();
//		
//		System.out.println(DateUtil.belongCalendar(new Date(), DateUtil.getDayStart(new Date()), DateUtil.getDayEnd(new Date())));
//		System.out.println(DateUtil.getDiffDays(new SimpleDateFormat("yyyy-MM-dd").parse("2018-10-11"), new SimpleDateFormat("yyyy-MM-dd").parse("2018-10-13")));
//		Random random = new Random();
//		System.out.println(random.nextInt(89999)+10000);
//		int start = random.nextInt(100)+0;
//		int pageSize = random.nextInt(8)+0;
//		System.out.println(start+"-"+pageSize);
//		
//		System.out.println(DateFormat.getTimeInstance().format(new Date()));
//		System.out.println((int)(Math.random()*100)+""+(int)(Math.random()*100));
		System.out.println(Math.random());
		List<Object[]> a = new ArrayList<Object[]>();
		Object[] A = {1,2,3,4};
		Object[] b = {7,8,9,10};
		a.add(A);
		a.add(b);
		System.out.println(a.size());
		for(int i=0;i<a.size();i++){
			
			for(int j=0;j<a.get(i).length;j++){
				System.out.println(a.get(i)[0]);
				
			}
		}
		
		
	
	}
}