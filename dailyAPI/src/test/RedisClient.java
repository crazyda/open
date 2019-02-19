package test;

import javax.servlet.http.Cookie;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisClient {

	
	private Jedis jedis;  //非切片客户端连接
	
	private JedisPool jedisPool; //非切片连接池
	 
	private ShardedJedis shardedJedis;  //切片客户端连接
	
	private ShardedJedisPool shardedJedisPool; //切片连接池
	
	
	
	public RedisClient(){
		
	}
	
	
	/**
	 * 初始化非切片连接池
	 */
	private void initialPool(){
		
		JedisPoolConfig config=new JedisPoolConfig();
	}
	
	
}
