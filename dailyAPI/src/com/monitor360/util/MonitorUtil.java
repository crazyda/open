package com.monitor360.util;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.axp.util.MD5Util;
import com.monitor360.bean.TTGService;
import com.weixin.sign.MD5;

public class MonitorUtil{
	//public static final String IP = "115.159.34.109";//测试服务器地址 
	public static final String IP = "47.104.210.139";//正式服务器地址 
	public static final int PORT = 8080;//服务器端口号  
	public static final String companyCode = "axp";
	public static final String SUC = "0";//成功
	public static final String EXC = "-1";//出错
	public static final String EXIST  = "-2";//已存在
	public static final String EXC3 = "-3";//密码错误
	public static final String EXIST4  = "-4";//访问过多
	
    public static String addUser(String name, String pwd) { 
    	String result = "-1";
    	pwd = MD5.getNonceStr(pwd);
    	TTransport transport = new TSocket(IP, PORT,20000);
		try {
			
			// 创建一个流套接字并将其连接到指定主机上的指定端口号
			TProtocol protocol = new TBinaryProtocol(transport);
			TTGService.Client client = new TTGService.Client(protocol);
			if(!transport.isOpen()){
				transport.open();
			}
			int re =client.register_service("axp", MD5Util.GetMD5Code("axp888888"));
				
			result = client.add_user(name, pwd, companyCode,re)+"";
		
			transport.close();
		} catch (Exception e) {
			transport.close();
			System.out.println("监控用户注册异常:" + e.getMessage());
			e.printStackTrace();
			result = EXC;
		} 
		if(transport.isOpen()){
			transport.close();
		}
		
        return result;
    }  
    
    public static void  main(String[] arge){
		
    	MonitorUtil mu = new MonitorUtil();
    	mu.addUser("15515515528", "axp888888");
    	//System.out.println(MD5Util.GetMD5Code("axp888888"));
    }
}