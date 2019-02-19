package test;

import java.net.InetSocketAddress;
import java.util.concurrent.Future;

import com.axp.domain.Users;

import net.spy.memcached.CASResponse;
import net.spy.memcached.CASValue;
import net.spy.memcached.MemcachedClient;


public class Memcached {

	
	public static void main(String[] args) {
		
		
		try {
			
			
			MemcachedClient mcc=new MemcachedClient(new InetSocketAddress("127.0.0.1",11211));
			System.out.println("成功连接至Memcached");
				
				
			mcc.set("haiyang",900, "123");
			
			System.out.println(mcc.get("haiyang"));
		
			
			mcc.shutdown();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}
}
