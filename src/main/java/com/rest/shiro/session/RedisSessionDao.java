	package com.rest.shiro.session;

import java.io.Serializable;

import com.rest.util.SerializableUtils;
import com.rest.util.SerializableUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import com.rest.redis.IBaseRedis;
import com.rest.util.SerializableUtils;

public class RedisSessionDao extends CachingSessionDAO{
	
	@Autowired
	private IBaseRedis baseRedis;
	
	@Override
	protected void doUpdate(Session session) {
		if(session instanceof ValidatingSession && !((ValidatingSession)session).isValid()) {  
	        return; //如果会话过期/停止 没必要再更新了  
	    }
		Serializable sessionId = generateSessionId(session);
        String sessId = sessionId.toString();
	    baseRedis.expire(sessId, session.getTimeout());
	}

	@Override
	protected void doDelete(Session session) {
		Serializable sessionId = generateSessionId(session);
		baseRedis.delete(sessionId.toString());
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        //org.apache.shiro.session.mgt.SimpleSession
        baseRedis.set(sessionId.toString(), SerializableUtils.serialize(session));
        baseRedis.expire(sessionId.toString(), 10000L);
		return session.getId();
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		String sessionStr = (String) baseRedis.get(sessionId.toString());
		if(sessionStr == null) return null;
		return (Session)SerializableUtils.deserialize(sessionStr);
	}

}
