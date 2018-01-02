package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;

import com.rest.redis.IBaseRedis;
import com.rest.util.SerializableUtils;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:spring-config.xml" })
public class TestRedis {
	
	//@Resource
	//private IBaseRedis baseRedis;
	//@Resource
	//private RedisTemplate<String, Object> redisTemplate;
	
	@Test
	public void testKeys() {
		//redisTemplate.delete("flushdb");
	}
	
	@Test
	public void tesJedis() {
		Jedis jd = new Jedis("10.3.10.217", 6379);
		jd.set("123", "aaa");
		
		
		
	}
}
