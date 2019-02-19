package test;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.thrift.transport.TIOStreamTransport;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import com.alibaba.fastjson.JSONObject;
import com.axp.domain.ReBaseGoods;
import com.axp.domain.ReGoodsOfBase;
import com.axp.domain.ReGoodsOfScoreMall;
import com.axp.domain.Users;
import com.axp.util.CalcUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mchange.lang.ByteUtils;

public class xx {

/*	public static void main(String[] args) {
		System.getProperties().list(System.out);;
		
		
		Jedis jedis=new Jedis("localhost");
		System.out.println("连接成功");
		System.out.println("服务正在运行:"+ jedis.ping());
		
		
		
	}*/
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		String userToString = "{\"nickName\":\"Band\",\"gender\":1,\"language\":\"zh_CN\",\"city\":\"Guangzhou\",\"province\":\"Guangdong\",\"country\":\"CN\",\"avatarUrl\":\"http://wx.qlogo.cn/mmopen/vi_32/1vZvI39NWFQ9XM4LtQpFrQJ1xlgZxx3w7bQxKARol6503Iuswjjn6nIGBiaycAjAtpujxyzYsrztuuICqIM5ibXQ/0\"}HyVFkGl5F5OQWJZZaNzBBg==";
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		map = gson.fromJson(userToString, map.getClass());
		//System.out.println(map.get("nickName"));
	}
	
	/*public static void main(String[] args) {
		try {
			
		
			String url="http://www.baidu.com";
			HttpClient httpClient=new DefaultHttpClient();
			
			HttpGet httpGet=new HttpGet(url);
			
			HttpResponse response = httpClient.execute(httpGet);
			
			HttpEntity entity = response.getEntity();
			
			byte[] byteArray = EntityUtils.toByteArray(entity);
			String result=new String(byteArray,"utf8");
			System.out.println(result);
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}*/
	

	
}



