package com.rest.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;
import org.springframework.beans.factory.annotation.Autowired;

import com.rest.redis.IBaseRedis;

public class CustomerRedisCacheManager implements CacheManager, Destroyable {
	
	private ShiroCacheManager shiroCacheManager;
	
	@Autowired
	private IBaseRedis baseRedis;
	
	@Override
	public Cache getCache(String name) throws CacheException {
		return shiroCacheManager.getCache(name, baseRedis);  
	}

	@Override
	public void destroy() throws Exception {
		shiroCacheManager.destroy();
		System.out.println("--------销毁--------");
	}

	public ShiroCacheManager getShiroCacheManager() {
		return shiroCacheManager;
	}

	public void setShiroCacheManager(ShiroCacheManager shiroCacheManager) {
		this.shiroCacheManager = shiroCacheManager;
	}
	
}
