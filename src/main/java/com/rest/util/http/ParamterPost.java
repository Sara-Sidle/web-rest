package com.rest.util.http;

import java.io.InputStream;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

/**
 * 
 * @author 武庆超
 *  该类为 组装
 *  MultipartEntityBuilder 参数
 *  工具类
 *  
 */
public class ParamterPost {
	
	private MultipartEntityBuilder builder;
	
	public ParamterPost (){
		builder = MultipartEntityBuilder.create();
	}
	
	public void addParam(String key, String value) {
		builder.addTextBody(key, value, ContentType.create("text/plain", "UTF-8"));
	}
	
	public void addParam(String key, InputStream value) {
		builder.addBinaryBody(key, value);
	}
	
	public void addParam(String key, InputStream value, String fileName) {
		builder.addBinaryBody(key, value, ContentType.MULTIPART_FORM_DATA, fileName);
	}
	
	public MultipartEntityBuilder getMultipart(){
		return builder;
	}
	
}
