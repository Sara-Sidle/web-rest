package com.rest.agent.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogInterceptor {


    public LogInterceptor() {
        System.err.println("LogInterceptor LogInterceptor");
    }

    //	com.ryx.agent.controller.*.*(..))")
//	@Pointcut("execution(public * com.ryx.agent..*.add(..))")
    @Pointcut("execution(String com.rest.agent.controller.CommonLoginController.getLogin())")
    public void myMethod() {
    }

    ;

    /*
     * @Before(
     * "execution(public void com.oumyye.dao.impl.UserDAOImpl.save(com.oumyye.model.User))"
     * )
     */
    @Before("myMethod()")
    public void before() {
        System.out.println("method staet");
    }

    @After("myMethod()")
    public void after() {
        System.out.println("method after");
    }

    @AfterReturning("myMethod()")
    public void AfterReturning() {
        System.out.println("method AfterReturning");
    }

    @AfterThrowing("myMethod()")
    public void AfterThrowing() {
        System.out.println("method AfterThrowing");
    }
}