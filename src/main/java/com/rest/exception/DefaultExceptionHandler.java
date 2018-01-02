package com.rest.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;

@ControllerAdvice
public class DefaultExceptionHandler {
	
    /**
     * 总异常
     * 后续根据不同的需求定制即可
     */
    @ExceptionHandler({Exception.class})
    public ModelAndView processUnauthenticatedException(NativeWebRequest request, Exception e) {
    	ModelAndView mv = new ModelAndView();	
		/* 使用FastJson提供的FastJsonJsonView视图返回，不需要捕获异常 */
    	e.printStackTrace();
		FastJsonJsonView view = new FastJsonJsonView();
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("code", "1000");
		attributes.put("msg", "未处理异常");
		view.setAttributesMap(attributes);
		mv.setView(view);
        return mv;
    }
    
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ModelAndView processDefault(NativeWebRequest request, Exception e) {
    	ModelAndView mv = new ModelAndView("/agentManager/error/error");	
		/* 使用FastJson提供的FastJsonJsonView视图返回，不需要捕获异常 */
    	System.out.println(e);
		mv.getModel().put("code", "1000");
		mv.getModel().put("msg", "未支持的请求方式");
        return mv;
    }
    
}
