package com.rest.util.http;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class HttpUtils {

	private static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class);

	public static String postForJSON(String url, String jsonParams, ContentType contentType) {
		String result = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		httppost.addHeader("Content-type", contentType.toString());
		// 设置 超时时间
		RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(8000).setConnectTimeout(8000).setConnectionRequestTimeout(8000).build();
		httpclient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
		try {
			httppost.setEntity(new StringEntity(jsonParams, Charset.forName("UTF-8")));
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, "UTF-8");
					if(result.length() > 0) {
						Map<String, String> resultMap = JSONObject.parseObject(result, Map.class);
						resultMap.put("http", String.valueOf(response.getStatusLine().getStatusCode()));
						result = JSONObject.toJSONString(resultMap);
					}
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("httpPostUtil收发消息出现异常：" + url + jsonParams , e);
			throw new RuntimeException("收发消息出现异常：" + e.getMessage());
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				LOG.error("关闭通道失败：" + e.getMessage(), e);
				throw new RuntimeException("关闭通道失败", e);
			}
		}
		return result;
	}
	
	public static String getForAnd(String url) {
		String result = "";
		CloseableHttpClient httpclient = null;
		try {
			httpclient = HttpClients.createDefault();
			HttpGet get = new HttpGet(url);
			CloseableHttpResponse response = httpclient.execute(get);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, "UTF-8");
					Map<String, String> resultMap = JSONObject.parseObject(result, Map.class);
					resultMap.put("http", String.valueOf(response.getStatusLine().getStatusCode()));
					result = JSONObject.toJSONString(resultMap);
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("httpPostUtil收发消息出现异常：" + url, e);
			throw new RuntimeException("收发消息出现异常：" + e.getMessage());
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				LOG.error("关闭通道失败：" + url, e);
				throw new RuntimeException("关闭通道失败", e);
			}
		}
		return result;
	}
	
	public static String httpsForPost(String url, String jsonParams, ContentType contentType) {
		String result = "";
		CloseableHttpClient httpclient = null;
		try {
			httpclient = HttpClients.custom().setConnectionManager(ConnectionManagerUtil.getConManagerContext()).build();
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("Content-type", contentType.toString());
			//httpPost.setHeader("Accept", "*/*");
			httpPost.setEntity(new StringEntity(jsonParams, Charset.forName("UTF-8")));
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, "UTF-8");
					Map<String, Object> resultMap = JSONObject.parseObject(result, Map.class);
					resultMap.put("http", String.valueOf(response.getStatusLine().getStatusCode()));
					result = JSONObject.toJSONString(resultMap);
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("httpPostUtil收发消息出现异常：" + url + jsonParams , e);
			throw new RuntimeException("收发消息出现异常：" + e.getMessage());
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				LOG.error("关闭通道失败：" + e.getMessage(), e);
				throw new RuntimeException("关闭通道失败", e);
			}
		}
		return result;
	}
	
	public static String httpsForGet(String url) {
		String result = "";
		CloseableHttpClient httpclient = null;
		try {
			httpclient = HttpClients.custom().setConnectionManager(ConnectionManagerUtil.getConManagerContext()).build();
			HttpGet get = new HttpGet(url);
			CloseableHttpResponse response = httpclient.execute(get);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, "UTF-8");
					Map<String, String> resultMap = JSONObject.parseObject(result, Map.class);
					resultMap.put("http", String.valueOf(response.getStatusLine().getStatusCode()));
					result = JSONObject.toJSONString(resultMap);
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("httpPostUtil收发消息出现异常：" + url, e);
			throw new RuntimeException("收发消息出现异常：" + e.getMessage());
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				LOG.error("关闭通道失败：" + e.getMessage(), e);
				throw new RuntimeException("关闭通道失败", e);
			}
		}
		return result;
	}
	
	public static String upload(String url, ParamterPost par) {
		String result = "";
		CloseableHttpClient httpclient = null;
		try {
			httpclient = HttpClients.createDefault();
			HttpPost post = new HttpPost(url);
			MultipartEntityBuilder builder = par.getMultipart();
			HttpEntity entity = builder.build();
			post.setEntity(entity);
			HttpResponse response = httpclient.execute(post);// 执行提交
			HttpEntity resultResponse = response.getEntity();
			if(resultResponse != null) {
				result = EntityUtils.toString(resultResponse, "UTF-8");
			}
		} catch (Exception e) {
			LOG.error("httpPostUtil收发消息出现异常：" + e.getMessage(), e);
			throw new RuntimeException("收发消息异常", e);
			
		} finally {
			try {
				httpclient.close();
			} catch (Exception e) {
				LOG.error("关闭通道失败：" + e.getMessage(), e);
				throw new RuntimeException("关闭通道失败", e);
			}
		}
		return result;
	}
	
	public static String httpsForForm(String url, Map<String, String> mapParams, ContentType contentType) {
		String result = "";
		CloseableHttpClient httpclient = null;
		try {
			httpclient = HttpClients.custom().setConnectionManager(ConnectionManagerUtil.getConManagerContext()).build();
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("Content-type", contentType.toString());
	        //装填参数  
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
	        if(mapParams!=null){  
	            for (Entry<String, String> entry : mapParams.entrySet()) {  
	                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));  
	            }  
	        }  
	      //设置参数到请求对象中  
	        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));  
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, "UTF-8");
					Map<String, String> resultMap = JSONObject.parseObject(result, Map.class);
					resultMap.put("http", String.valueOf(response.getStatusLine().getStatusCode()));
					result = JSONObject.toJSONString(resultMap);
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("httpPostUtil收发消息出现异常：" + e.getMessage(), e);
			throw new RuntimeException("收发消息出现异常：" + e.getMessage());
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				LOG.error("关闭通道失败：" + e.getMessage(), e);
				throw new RuntimeException("关闭通道失败", e);
			}
		}
		return result;
	}
	
	public static String httpForForm(String url, Map<String, String> mapParams, ContentType contentType) {
		String result = "";
		CloseableHttpClient httpclient = null;
		try {
			httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("Content-type", contentType.toString());
			
	        //装填参数  
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
	        if(mapParams!=null){  
	            for (Entry<String, String> entry : mapParams.entrySet()) {  
	                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));  
	            }  
	        }  
	      //设置参数到请求对象中  
	        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));  
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, "UTF-8");
					Map<String, Object> resultMap = JSONObject.parseObject(result, Map.class);
					resultMap.put("http", String.valueOf(response.getStatusLine().getStatusCode()));
					result = JSONObject.toJSONString(resultMap);
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("httpPostUtil收发消息出现异常：" + e.getMessage(), e);
			throw new RuntimeException("收发消息出现异常：" + e.getMessage());
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				LOG.error("关闭通道失败：" + e.getMessage(), e);
				throw new RuntimeException("关闭通道失败", e);
			}
		}
		return result;
	}
	
}
