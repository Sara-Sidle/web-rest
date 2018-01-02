package com.rest.redis;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * redis基础类
 * 
 */
public interface IBaseRedis {

	/**
	 * 确认一个key是否存在
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(String key);

	/**
	 * 删除一个key
	 * 
	 * @param key
	 * @return
	 */
	public void delete(String key);

	/**
	 * expire：设定一个key的活动时间<br>
	 * extend
	 * 
	 * @param key
	 *            key
	 * @param timeOut
	 *            存活时间
	 *            时间单位
	 * @return
	 */
	public boolean expire(String key, long timeOut);

	/**
	 * 返回数据库中名称为key的string的value
	 * 
	 * @param key
	 *            key
	 * @return
	 */
	public Object get(String key);

	/**
	 * 给数据库中名称为key的string赋予值value
	 * 
	 * @param key
	 *            key
	 * @param value
	 *            值
	 */
	public <T extends Serializable> void set(String key, T value);

	/**
	 * 向名称为key的hash中添加元素field
	 * 
	 * @param key
	 *            hash key值
	 * @param field
	 *            域
	 * @param value
	 *            值
	 */
	public <T extends Serializable> void hset(String key, String field, T value);

	/**
	 * 返回名称为key的hash中field对应的value
	 * 
	 * @param key
	 *            hash key值
	 * @param field
	 *            域
	 * @return
	 */
	public <T extends Serializable> T hget(String key, String field);

	/**
	 * 向名称为key的hash中添加多个元素field,value
	 * 
	 * @param key
	 *            hash key值
	 * @param map
	 *            map类集
	 */
	public <T extends Serializable> void hmset(String key, Map<String, T> map);

	/**
	 * 返回名称为key的hash中所有的field,value
	 * 
	 * @param key
	 *            key值
	 *            map类集列
	 */
	public <T extends Serializable> Map<String, T> hmget(String key);

	/**
	 * 设置hash列表的过期时间
	 * 
	 * @param key
	 *            key值
	 * @param timeOut
	 *            过期时间
	 * @return
	 */
	public boolean hexpire(String key, long timeOut);
	
	/**
	 * 查询 所有的key
	 * 
	 * @return
	 */
	public Set<String> Keys();

}