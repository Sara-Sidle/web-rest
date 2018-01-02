package com.rest.filter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

/**
 * 限制登录人数<br>
 * 同时只能有一个人登录或几个人同时登录
 * 
 * @author
 * 
 */
public class KickoutSessionControlFilter extends AccessControlFilter {
	
	/**
	 * 踢出后到的地址
	 */
	private String kickoutUrl;
	
	/**
	 * 如果登录访问的为根路径 重定向指代理商选择页面
	 * 
	 */
	private String choseAgentUrl;

	/**
	 * 踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
	 */
	private boolean kickoutAfter = false;

	/**
	 * 同一个帐号最大会话数 默认1
	 */
	private int maxSession = 1;

	/**
	 * 会话管理
	 */
	private SessionManager sessionManager;

	/**
	 * 缓存管理
	 */
	private Cache<String, Deque<Serializable>> cache;
	
	
	
	public void setChoseAgentUrl(String choseAgentUrl) {
		this.choseAgentUrl = choseAgentUrl;
	}

	public void setKickoutUrl(String kickoutUrl) {
		this.kickoutUrl = kickoutUrl;
	}

	public void setKickoutAfter(boolean kickoutAfter) {
		this.kickoutAfter = kickoutAfter;
	}

	public void setMaxSession(int maxSession) {
		this.maxSession = maxSession;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cache = cacheManager.getCache("shiro-kickout-session");
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		
		Subject subject = getSubject(request, response);
		
		if (!subject.isAuthenticated() && !subject.isRemembered()) {
			// 如果没有登录，直接进行之后的流程
			return true;
		}
		
		if(!isLoginRequest(request, response)) {
			String requestURI = WebUtils.getPathWithinApplication(WebUtils.toHttp(request));
			if("/".equals(requestURI)) {
				WebUtils.issueRedirect(request, response, choseAgentUrl);
			}
		}
		
		
		Session session = subject.getSession();
		String username = (String) subject.getPrincipal();
		Serializable sessionId = session.getId();

		// 从缓存中获取登录信息，无登录缓存，则新建第一个登录信息
		Deque<Serializable> deque = cache.get(username);
		if (deque == null) {
			deque = new LinkedList<Serializable>();
		}

		// 如果队列里没有此sessionId，且用户没有被踢出；放入队列
		if (!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
			deque.push(sessionId);
			cache.put(username, deque);
		}

		// 如果队列里的sessionId数超出最大会话数，开始踢人
		while (deque.size() > maxSession) {
			Serializable kickoutSessionId = null;
			if (kickoutAfter) { // 如果踢出后者
				kickoutSessionId = deque.removeFirst();
				cache.put(username, deque);
			} else { // 否则踢出前者
				kickoutSessionId = deque.removeLast();
				cache.put(username, deque);
			}
			try {
				Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
				if (kickoutSession != null) {
					// 设置会话的kickout属性表示踢出了
					kickoutSession.setAttribute("kickout", true);
				}
			} catch (Exception e) {// ignore exception
				e.printStackTrace();
			}
		}

		// 如果被踢出了，直接退出，重定向到踢出后的地址
		if (session.getAttribute("kickout") != null) {
			// 会话被踢出了
			try {
				subject.logout();
			} catch (Exception e) { // ignore
				e.printStackTrace();
			}
			saveRequest(request);
			WebUtils.issueRedirect(request, response, kickoutUrl);
			return false;
		}
		return true;
	}
	
}