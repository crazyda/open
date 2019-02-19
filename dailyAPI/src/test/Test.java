package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.axp.controller.BaseController;
import com.axp.controller.RedpaperInvoke;
import com.axp.controller.RongCloudController;
import com.axp.controller.StaticInfoInvoke;
import com.axp.controller.UsersInvoke;
import com.axp.dao.AdminUserDAO;
import com.axp.dao.CashshopTimesDAO;
import com.axp.dao.DateBaseDAO;
import com.axp.dao.IAliMessageDao;
import com.axp.dao.ICaptchaDao;
import com.axp.dao.IReGoodsOfSeckillMallDao;
import com.axp.dao.ReGoodsorderDao;
import com.axp.dao.ScoreMarkDAO;
import com.axp.dao.SellerDAO;
import com.axp.dao.SystemMessageListDao;
import com.axp.dao.TkldPidDao;
import com.axp.domain.AdminUser;
import com.axp.domain.CashshopTimes;
import com.axp.domain.ReGoodsOfSeckillMall;
import com.axp.domain.ReGoodsorder;
import com.axp.domain.ScoreMark;
import com.axp.domain.Seller;
import com.axp.domain.SystemMessageList;
import com.axp.domain.TkldPid;
import com.axp.domain.Users;
import com.axp.service.AliSendMessageService;
import com.axp.service.TkldPidService;
import com.axp.service.impl.UsersServiceImpl;
import com.axp.util.CalcUtil;
import com.axp.util.MD5Util;
import com.axp.util.QueryModel;
import com.axp.util.ToolSpring;
import com.rongcloud.RongCloud;
import com.rongcloud.methods.Wordfilter;
import com.rongcloud.models.CodeSuccessResult;
import com.rongcloud.models.ListWordfilterResult;

@Transactional  						//defaultRollback=true  代表方法执行完成 自动回滚
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)  
@RunWith(SpringJUnit4ClassRunner.class)  
@WebAppConfiguration    
@ContextConfiguration(locations={"classpath:application.xml","classpath:springMVC.xml"})
public class Test extends BaseController {
	
	private TkldPidDao tkldPidDao;
	@Autowired
	private TkldPidService tkldPidService;
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Autowired
	StaticInfoInvoke staticInfoInvoke;
	@Autowired
	RongCloudController rongCloudController;
	@Autowired
	RedpaperInvoke redpaperInvoke;
	@Autowired
	ScoreMark scoreMark;
	@Autowired
	ScoreMarkDAO scoreMarkDao;
	@Autowired
	IReGoodsOfSeckillMallDao reGoodsOfSeckillMallDao;
	
	@Autowired
	CashshopTimesDAO cashshopTimesDAO;
	private final String appKey = "m7ua80gbmwh9m";//替换成您的appkey
	private final String appSecret = "DI6CgSw4gC";//替换成匹配上面key的secret
	
	@Autowired
	UsersInvoke usersInvoke;
	    private MockMvc mockMvc;  
	
	    @Autowired
	    DateBaseDAO dateBaseDao;
	    @Autowired
	    ReGoodsorderDao reGoodsorderDao;
	    @Autowired
	    AdminUserDAO adminUserDAO;
	    @Autowired
	    SellerDAO sellerDao;
	    @org.junit.Test
	    public void aa(){
	    	dateBaseDao.updateByHQL("update ReGoodsOfChangeMall set pageView=pageView+1 where id="+13);

			
	    }
	    
	    
	@org.junit.Test
	public void getZones() throws Exception{
		try {
			
		
		Map<String, Object> map=new HashMap<String, Object>();
		Map<String, String> data=new HashMap<String, String>();
			map.put("axp","af2ff466ec605ba369ae878a15652417");
			map.put("sellerId","");
			map.put("channelId","-1");
			map.put("zoneId","1132");
			map.put("adminuserId","");
			map.put("dip","480");
			map.put("os","IOS");
			map.put("userId","49489");
			map.put("lat","23.184787");
			map.put("app","USERS");
			map.put("tokenId","-1");
			map.put("times","axp451442017-10-17-09-34");
			map.put("lng","113.426128");
			map.put("appVersion","5.9.1");
			data.put("version","0");
			data.put("settingId","91215");
			map.put("data",data);
			System.out.println(JSON.toJSONString(map));
		
			String path="invoke/config/getZoneList";
			 ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(path)
			 .accept(MediaType.APPLICATION_JSON).param("data",JSON.toJSONString(map)));
			 MvcResult mvcResult = resultActions.andReturn();  
			  String result = mvcResult.getResponse().getContentAsString();
			
			  System.out.println("=====客户端获得反馈数据:" + result);  
			
		} catch (Exception e) {
		
				e.printStackTrace();
		}
	}
	
	
	@org.junit.Test
	public void ss2s(){
		try {
			
			
			
			
		} catch (Exception e) {
		e.printStackTrace();
		}
	
	}
	
	
	@org.junit.Test
	public void sss(){
		QueryModel model = new QueryModel();
		model.clearQuery();
		model.combPreEquals("isValid", true);
		model.combPreEquals("newHands", "y-1741"); //属于该用户的,
		model.setOrder("refreshTime ASC");//有限使用快过期的
		
		List<ScoreMark> scoreList = dateBaseDao.findPageList(ScoreMark.class, model, 0, 6); //处理存在积分来源是多个商家的
		for(ScoreMark s:scoreList){
			System.out.println(s.getRemark());
		}
		
		
	}
	
	
	
/*	@Autowired
	private SharePartnerDao sharePartnerDao;*/
	/*@org.junit.Test
	public void test() {
		TkldPid findById = tkldPidDao.findById(12);
		SharePartner sha = sharePartnerDao.findById(1);
		System.out.println(sha);
	}*/

	/*@Autowired
	private SessionFactory sessionFactory;
	@org.junit.Test
	public void test01(){
		//批量添加2张表
	Session	session=sessionFactory.openSession();	
	Transaction transaction = session.getTransaction();
	
	try {
		transaction.begin();
		Users users= (Users) session.createQuery("from Users where id="+811).uniqueResult();
		long start=	System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			TkldPid tkldPid=new TkldPid();
			tkldPid.setpId("测试1");
			tkldPid.setUsers(users);
			SharePartner partner=new SharePartner();
			partner.setUsers(users);
			session.save(tkldPid);
			session.save(partner);
			System.out.println("添加第"+i+"次");
			if(i%1000==0){
				session.flush();
				session.clear();
				System.out.println("添加第"+i+"次刷新");
			}
		}
		transaction.commit();
		System.out.println("添加完成");
		long end=System.currentTimeMillis();
		System.out.println("总计时间"+((end-start)/1000));
	} catch (Exception e) {
		e.printStackTrace();
		transaction.rollback();
		System.out.println("批量新增失败");
	}
	finally{
		session.close();
		
	}
	}*/
	
	
	@Transactional
	@org.junit.Test
	public void test03(){
		try {
		//	Integer count = usersDAO.findCountByQueryChar("isvalid = true and userid is not null and length(userid) = 32");
			/*TkldPid t=new TkldPid();
			for(int i=0;i<10;i++){
				t.setpId(""+i);
				//tkldPidDao.save(t);
				//t.setId(null);
			}*/
			
			
			System.out.println(MD5Util.GetMD5Code("123456").equals("e10adc3949ba59abbe56e057f20f883e"));
			
		} catch (Exception e) {
		e.printStackTrace();
		}
		

	}
	
	
	
	
		@org.junit.Test
		public void xx(){
			//,key="#userName.concat(#password)  组合key
			//,key="#userId + 'findById'" >//将缓存保存进cache，并使用参数中的userId加上一个字符串(这里使用方法名称)作为缓存的key    
			List<TkldPid> list= tkldPidService.findAll("1");
			//EhcacheUtil.put("1", list);
			tkldPidService.clearCache("2");
			List<TkldPid> list3= tkldPidService.findAll("1");
			
			
			//List<TkldPid> list2=(List<TkldPid>) EhcacheUtil.get("1");
			
			System.out.println(list3.size());
			
		}
		
		@org.junit.Test
		public void procedure(){
			
			Session session = sessionFactory.getCurrentSession();
			
			String procedureName="call  xx3(?,?,?);";
			
			SQLQuery sqlQuery = session.createSQLQuery(procedureName);
			sqlQuery.setParameter(0, "@1");
			sqlQuery.setParameter(1, "@2");
			sqlQuery.setParameter(2, "@3");
			List<Object[]> list = sqlQuery.list();
			for (Object[] obj : list) {
				System.out.println(obj[0].toString()); 
			}
			
			System.out.println(list.size());
			
		}
		
		
		public static void main(String[] args) throws ClassNotFoundException {

			//创建一个X G的文件
			
			/*		  1字符=3字节
							  1024字节=1kb  
							 1024kb=1mb
							 1024mb=1g*/
			try{
/*		File file=new File("d:/xx.txt");
				
				file.createNewFile();
		

				String str="我们是供餐注意的接班人";
				double bit=21474836480d;
						//36 
				//Double bit=new Double(1024*1024*1024*20);  // 1024*1024*1024; //1G
				Double strs=(Double)bit/36; 
				PrintWriter writer=new PrintWriter(file);
				
				for (int i = 0; i <strs ; i++) {
					if(i%100==0){
						writer.write("\r\n");
					}
					writer.write(str+i+"-");
					if(i%10000==0){
						writer.flush();
					}
				}
				
				writer.flush();
				writer.close();*/
				
				readTxt();
				
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		
		
		public static void readTxt(){
			try {
				
				String path="d:/xx.txt";
				File file=new File(path);
				BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
				String line;
				
				FileInputStream s=new FileInputStream(file);
				System.out.println(file.length());
				System.out.println(s.available());
				
				
				while((line=reader.readLine())!=null){
					
					System.out.println(line);
					
				}
				reader.close();
				
				//System.out.println(sb.toString());
				
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			
			
			//找出所有数字
			
			
			
		}
		
		
		
		@Autowired
		 IAliMessageDao aliMessageDao;
		@Autowired
		AliSendMessageService aliSendMessageService;
		
	
		@Autowired
		SystemMessageListDao systemMessageListDao;
	
		@Autowired
		ICaptchaDao captchaDao; 
	
		@org.junit.Test
		public void savess(){
			for(int i=0;i<10;i++){
			SystemMessageList messageList = new SystemMessageList();
			messageList.setIsValid(true);
			systemMessageListDao.save(messageList);
			System.out.println(messageList.getId());
			
			SystemMessageList findById = systemMessageListDao.findById(messageList.getId());
			System.out.println(findById.getId());
				//System.out.println(systemMessageListDao.findById(6586));
			}
		}
		
		@Autowired
		UsersServiceImpl userServiceImpl;
		@org.junit.Test
		public void getFansList(){
			HttpServletRequest request = null;
			HttpServletResponse response = null;
			userServiceImpl.getFansList2(request, response);
		}
	
		
		
	}
	
	
