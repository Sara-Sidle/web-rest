package com.rest.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.redis.IBaseRedis;

public class RedisCacheManager implements ShiroCacheManager {
	
	@Override
	public Cache getCache(String name, IBaseRedis baseRedis) {
		return new RedisCache(name, baseRedis);
	}

	@Override
	public void destroy() {
		
	}

}
