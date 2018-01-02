package com.rest.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

public class ShiroSessionListener implements SessionListener {

	/**
	 * 当前在线人数
	 */
	public static int peopleInSystemNum = 0;

	@Override
	public void onStart(Session session) {// 会话创建时触发
		peopleInSystemNum++;
		System.out.println("      +1 ↓↓↓	在线人数：" + peopleInSystemNum);
		System.out.println("会话创建,sessionId:" + session.getId() + ";host:" + session.getHost());

	}

	@Override
	public void onExpiration(Session session) {// 会话过期时触发
		peopleInSystemNum--;
		System.out.println("      -1 ↓↓↓	在线人数：" + peopleInSystemNum);
		System.out.println("会话过期,sessionId:" + session.getId() + ";host:" + session.getHost());
	}

	@Override
	public void onStop(Session session) {// 退出/会话过期时触发
		peopleInSystemNum--;
		System.out.println("      -1 ↓↓↓	在线人数：" + peopleInSystemNum);
		System.out.println("会话停止,sessionId:" + session.getId() + ";host:" + session.getHost());
	}
}