package com.rest.shiro.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.rest.util.SerializableUtils;
import com.rest.util.SerializableUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rest.redis.IBaseRedis;
import com.rest.util.SerializableUtils;

public class RedisCache implements Cache {
	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	private IBaseRedis baseRedis;
	
	private String name;

    public RedisCache(String name, IBaseRedis baseRedis) {
        this.name = name;
        this.baseRedis = baseRedis;
    }
	
	@Override
	public void clear() throws CacheException {
		//清除redis
	}

	@Override
	public Object get(Object key) throws CacheException {
		String value = null;
		value = (String) baseRedis.get((String)key);
		if(value != null) {
			return SerializableUtils.deserialize(value);
		} else {
			return value;
		}
	}

	@Override
	public Object put(Object key, Object value) throws CacheException {
		baseRedis.set((String)key, SerializableUtils.serialize(value));
		if(value instanceof Session) {
			Session se = (Session)value;
			baseRedis.expire((String)key, se.getTimeout());
 		}
		return value;
	}

	@Override
	public Object remove(Object key) throws CacheException {
		System.out.println(key.getClass());
		String name ="";
		if(key instanceof String) {
			name = (String)key;
		} else if(key instanceof SimplePrincipalCollection) {
			SimplePrincipalCollection a = (SimplePrincipalCollection)key;
			name = (String)a.getPrimaryPrincipal();
			
		}

		baseRedis.delete(name);
		Object value = get(name);
		return value;
	}

	@Override
	public int size() {
		Set<Object> key = keys();
		if (CollectionUtils.isEmpty(key)) {
			return 0;
		}
        return key.size();
	}
	
	@Override
	public Set<Object> keys() {
		Set<String> keys = baseRedis.Keys();
		Set<Object> keySet = new HashSet<Object>();
		for(String str : keys) {
			keySet.add(str);
		}
		return keySet;
	}

	@Override
	public Collection<Object> values() {
		Set<Object> keySet = keys();
		if(CollectionUtils.isEmpty(keys())) {
			List values = new ArrayList(keySet.size());
			for(Object k :keySet) {
				Object v = get(k);
				values.add(v);
			}
			return Collections.unmodifiableList(values);
		} else {
			return Collections.emptyList();
		}
	}

	public IBaseRedis getBaseRedis() {
		return baseRedis;
	}

	public void setBaseRedis(IBaseRedis baseRedis) {
		this.baseRedis = baseRedis;
	}
	
}
