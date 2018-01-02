package com.rest.agent.handler;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 
 * @author
 * 
 */
public class DefaultHandler extends HandlerInterceptorAdapter {

	private List<String> excludedUrls;

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludedUrls = excludeUrls;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean flag = false;
		String requestUri = request.getServletPath();
		Map<String, String[]> paramMap = request.getParameterMap();
//		for (String url : excludedUrls) {
//			flag = requestUri.startsWith(url);
//			break;
//		}

//		if (flag) {
//			// 登录认证
//			HttpSession session = request.getSession();
//			if (session.getAttribute("userInfo") == null) {
//				response.sendRedirect("login");
//				return false;
//			} else {
//				return true;
//			}
//		} else {
//			return true;
//		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		String a = "/pub/user/info";
		String b = "/index";
		System.out.println(a.startsWith(b));
	}

}
