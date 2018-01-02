package com.rest.agent.log;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogRecordAspect {

	private static final Logger logger = LoggerFactory.getLogger(LogRecordAspect.class);
	
	

	public LogRecordAspect() {
		System.err.println("LogRecordAspect initing1!");
	}

	// Controller层切点 execution (public * com.ryx.agent.controller.*.*(..))
	//	* com.xyz.service..*.*(..)
	@Pointcut(" execution(* com.rest.agent.controller..*.*(..))")
	public void controllerAspect() {

	}

	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) {
		System.out.println("==========执行controller前置通知===============");
		if (logger.isInfoEnabled()) {
			logger.info("before " + joinPoint);
		}
	}

	/**
	 * 后置通知 用于拦截Controller层记录用户的操作
	 * 
	 * @param joinPoint
	 *            切点
	 */
	@After("controllerAspect()")
	public void after(JoinPoint joinPoint) {

		/*
		 * HttpServletRequest request = ((ServletRequestAttributes)
		 * RequestContextHolder.getRequestAttributes()).getRequest();
		 * HttpSession session = request.getSession();
		 */
		// 读取session中的用户
		// User user = (User) session.getAttribute("user");
		// 请求的IP
		// String ip = request.getRemoteAddr();
		try {

			String targetName = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			Object[] arguments = joinPoint.getArgs();
			Class targetClass = Class.forName(targetName);
			Method[] methods = targetClass.getMethods();
			String operationType = "";
			String operationName = "";
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					Class[] clazzs = method.getParameterTypes();
					if (clazzs.length == arguments.length) {
						break;
					}
				}
			}
			// *========控制台输出=========*//
			System.out.println("=====controller后置通知开始=====");
			System.out.println("请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()") + "." + operationType);
			System.out.println("方法描述:" + operationName);
			// *========数据库日志=========*//
			// 保存数据库
			System.out.println("=====controller后置通知结束=====");
		} catch (Exception e) {
			// 记录本地异常日志
			logger.error("==后置通知异常==");
			logger.error("异常信息:{}", e.getMessage());
		}
	}

	// 配置后置返回通知,使用在方法aspect()上注册的切入点
	@AfterReturning("controllerAspect()")
	public void afterReturn(JoinPoint joinPoint) {
		System.out.println("=====执行controller后置返回通知=====");
		if (logger.isInfoEnabled()) {
			logger.info("afterReturn " + joinPoint);
		}
	}

}
