package com.rest.shiro.cache;

import org.apache.shiro.cache.Cache;

import com.rest.redis.IBaseRedis;

public interface ShiroCacheManager {

   Cache getCache(String name, IBaseRedis baseRedis);

    void destroy();

}
