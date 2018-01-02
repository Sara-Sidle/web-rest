package com.rest.redis.impl;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.rest.redis.IBaseRedis;


@Service
public class BaseRedisImpl implements IBaseRedis {

	private TimeUnit timeUnit = TimeUnit.MILLISECONDS;

	@Resource
	public RedisTemplate<String, Object> redisTemplate;

	/**
	 * Hash返回名称为key的hash中所有的键（field）及其对应的value
	 * 
	 * @param key
	 *            hash表的key
	 * @return
	 */
	private BoundHashOperations<String, String, Serializable> boundHashOps(String key) {
		BoundHashOperations<String, String, Serializable> boundHashOperations = redisTemplate.boundHashOps(key);
		return boundHashOperations;
	}

	/**
	 * String返回名称为key的所有键（field）及其对应的value
	 * 
	 * @param key
	 * @return
	 */
	private BoundValueOperations<String, Object> boundValueOps(String key) {
		BoundValueOperations<String, Object> bvo = redisTemplate.boundValueOps(key);
		return bvo;
	}

	@Override
	public boolean expire(String key, long timeOut) {
		return redisTemplate.expire(key, timeOut, timeUnit);
	}

	@Override
	public boolean exists(String key) {
		return redisTemplate.hasKey(key);
	}

	@Override
	public void delete(String key) {
		redisTemplate.delete(key);
	}

	@Override
	public <T extends Serializable> T hget(String key, String field) {
		if (this.exists(key)) {
			@SuppressWarnings("unchecked")
			T t = (T) this.boundHashOps(key).get(field);
			return t;
		} else {
			return null;
		}
	}

	@Override
	public <T extends Serializable> void hset(String key, String field, T value) {
		this.boundHashOps(key).put(field, value);
	}

	@Override
	public <T extends Serializable> void set(String key, T value) {
		this.boundValueOps(key).set(value);
	}

	@Override
	public Object get(String key) {
		BoundValueOperations<String, Object> bvo = this.boundValueOps(key);
		if (bvo != null) {
			return bvo.get();
		} else {
			return null;
		}
	}

	@Override
	public <T extends Serializable> void hmset(String key, Map<String, T> map) {
		this.boundHashOps(key).putAll(map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Serializable> Map<String, T> hmget(String key) {
		Object obj = this.boundHashOps(key).entries();
		if (obj != null) {
			return (Map<String, T>) obj;
		} else {
			return null;
		}
	}

	@Override
	public boolean hexpire(String key, long timeOut) {
		return this.boundHashOps(key).expire(timeOut, timeUnit);
	}

	@Override
	public Set<String> Keys() {
		return redisTemplate.keys("*");
	}

}
