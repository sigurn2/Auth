package com.neusoft.ehrss.liaoning.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.neo4j.cypher.internal.compiler.v2_1.functions.Str;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * HttpClient4.3工具类
 *
 * @author xinghy
 * @date 2018-06-07
 */

@Component
public class HttpClientTools {
	private static Logger logger = LoggerFactory.getLogger(HttpClientTools.class); // 日志记录

	/**
	 * post请求传输json参数
	 *
	 * @param url
	 *            url地址
	 * @param params
	 *            参数
	 * @return
	 */



	public static JSONObject httpPost(String url, Map<String, String> params) {
		// post请求返回结果
		CloseableHttpClient httpClient = HttpClients.createDefault();
		JSONObject jsonResult = null;
		HttpPost httpPost = new HttpPost(url);
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
		httpPost.setConfig(requestConfig);
		try {
			if (null != params) {
				// 创建参数队列
				List<NameValuePair> nameValuePairs = new ArrayList<>();
				for (String key : params.keySet()) {
					nameValuePairs.add(new BasicNameValuePair(key, params.get(key)));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			}
			CloseableHttpResponse result = httpClient.execute(httpPost);
			// 请求发送成功，并得到响应
			if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String str = "";
				try {
					// 读取服务器返回过来的json字符串数据
					str = EntityUtils.toString(result.getEntity(), "utf-8");
					// 把json字符串转换成json对象
					jsonResult = JSONObject.parseObject(str);
				} catch (Exception e) {
					logger.error("post请求提交失败:" + url, e);
				}
			}
		} catch (IOException e) {
			logger.error("post请求提交失败:" + url, e);
		} finally {
			httpPost.releaseConnection();
		}
		return jsonResult;
	}

	/**
	 * post请求传输json参数
	 *
	 * @param url
	 *            url地址
	 * @param params
	 *            参数
	 * @return
	 */
	public static JSONObject httpPost(String url, JSONObject params) {
		// post请求返回结果
		CloseableHttpClient httpClient = HttpClients.createDefault();
		JSONObject jsonResult = null;
		HttpPost httpPost = new HttpPost(url);
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
		httpPost.setConfig(requestConfig);
		try {
			if (null != params) {
				// 解决中文乱码问题
				StringEntity entity = new StringEntity(params.toString(), "utf-8");
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json");
				httpPost.setEntity(entity);
			}
			CloseableHttpResponse result = httpClient.execute(httpPost);
			// 请求发送成功，并得到响应
			if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String str = "";
				try {
					// 读取服务器返回过来的json字符串数据
					str = EntityUtils.toString(result.getEntity(), "utf-8");
					// 把json字符串转换成json对象
					jsonResult = JSONObject.parseObject(str);
				} catch (Exception e) {
					logger.error("post请求提交失败:" + url, e);
				}
			}
		} catch (IOException e) {
			logger.error("post请求提交失败:" + url, e);
		} finally {
			httpPost.releaseConnection();
		}
		return jsonResult;
	}

	/**
	 * post请求传输String参数 例如：name=Jack&sex=1&type=2
	 * Content-type:application/x-www-form-urlencoded
	 *
	 * @param url
	 *            url地址
	 * @param params
	 *            参数
	 * @return
	 */
	public static JSONObject httpPost(String url, String params) {
		// post请求返回结果
		CloseableHttpClient httpClient = HttpClients.createDefault();
		JSONObject jsonResult = null;
		HttpPost httpPost = new HttpPost(url);
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
		httpPost.setConfig(requestConfig);
		try {
			if (null != params) {
				// 解决中文乱码问题
				StringEntity entity = new StringEntity(params, "utf-8");
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/x-www-form-urlencoded");
				httpPost.setEntity(entity);
			}
			CloseableHttpResponse result = httpClient.execute(httpPost);
			// 请求发送成功，并得到响应
			if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String str = "";
				try {
					// 读取服务器返回过来的json字符串数据
					str = EntityUtils.toString(result.getEntity(), "utf-8");
					// 把json字符串转换成json对象
					jsonResult = JSONObject.parseObject(str);
				} catch (Exception e) {
					logger.error("post请求提交失败:" + url, e);
				}
			}
		} catch (IOException e) {
			logger.error("post请求提交失败:" + url, e);
		} finally {
			httpPost.releaseConnection();
		}
		return jsonResult;
	}

	/**
	 * 发送get请求
	 *
	 * @param url
	 *            路径
	 * @return
	 */
	public static JSONObject httpGet(String url,String host, String port) {

		logger.debug("host = {} , port = {}", host, port);
		// get请求返回结果
		JSONObject jsonResult = null;
		// 发送get请求
		HttpGet request = new HttpGet(url);
		// 设置请求和传输超时时间
		//RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).setProxy(new HttpHost("59.197.168.234", 3128, null)).build();
		if ("noproxy".equals(host)){
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
			request.setConfig(requestConfig);
			//HttpClient client = HttpClients.custom().setDefaultRequestConfig(request.getConfig()).build();
			HttpClient client = CertificateValidationIgnored.getNoCertificateHttpClient(null);
			//client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
			jsonResult = getHttpJsonResult(url, jsonResult, request, client);
		}else {
//			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).setProxy(new HttpHost(host, Integer.parseInt(port), null)).build();
//			request.setConfig(requestConfig);
			HttpHost proxy = new HttpHost(host,Integer.parseInt(port));
			HttpClient client = CertificateValidationIgnored.getNoCertificateHttpClient(null);
			client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
			jsonResult = getHttpJsonResult(url, jsonResult, request, client);
		}
		logger.debug("jsonResult = {}",jsonResult);
//

		return jsonResult;
	}

	private static JSONObject getHttpJsonResult(String url, JSONObject jsonResult, HttpGet request, HttpClient client) {
		try {
			Date startDate = new Date();
			HttpResponse response = client.execute(request);
			Date endDate = new Date();
			logger.debug("endDate = {}, startDate = {}", endDate, startDate);
			// 请求发送成功，并得到响应
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 读取服务器返回过来的json字符串数据
				HttpEntity entity = response.getEntity();
				String strResult = EntityUtils.toString(entity, "utf-8");
				// 把json字符串转换成json对象
				jsonResult = JSONObject.parseObject(strResult);
			} else {
				logger.error("get请求提交失败:" + url);
			}
		} catch (IOException e) {
			logger.error("get请求提交失败:" + url, e);
		} finally {
			request.releaseConnection();
		}
		return jsonResult;
	}

	/**
	 * 发送get请求
	 *
	 * @param url
	 *            路径
	 * @return
	 */
	public static String httpGetStr(String url, String charset) {
		String strResult = null;

		CloseableHttpClient client = HttpClients.createDefault();
		// 发送get请求
		HttpGet request = new HttpGet(url);
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
		request.setConfig(requestConfig);
		try {
			CloseableHttpResponse response = client.execute(request);

			// 请求发送成功，并得到响应
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 读取服务器返回过来的json字符串数据
				HttpEntity entity = response.getEntity();
				strResult = EntityUtils.toString(entity, charset);

			} else {
				logger.error("get请求提交失败:" + url);
			}
		} catch (IOException e) {
			logger.error("get请求提交失败:" + url, e);
		} finally {
			request.releaseConnection();
		}
		return strResult;
	}

	public static String httpPostForIds(String url, Map<String, String> params) {
		// post请求返回结果
		CloseableHttpClient httpClient = HttpClients.createDefault();

		HttpPost httpPost = new HttpPost(url);
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
		httpPost.setConfig(requestConfig);
		try {
			if (null != params) {
				// 创建参数队列
				List<NameValuePair> nameValuePairs = new ArrayList<>();
				for (String key : params.keySet()) {
					nameValuePairs.add(new BasicNameValuePair(key, params.get(key)));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			}
			CloseableHttpResponse result = httpClient.execute(httpPost);
			// 请求发送成功，并得到响应
			if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String str = "";
				try {
					// 读取服务器返回过来的json字符串数据
					str = EntityUtils.toString(result.getEntity(), "utf-8");
					return str;
				} catch (Exception e) {
					logger.error("post请求提交失败:" + url, e);
				}
			}
		} catch (IOException e) {
			logger.error("post请求提交失败:" + url, e);
		} finally {
			httpPost.releaseConnection();
		}
		return "";
	}

	public static void main(String[] args) {
		// TODO 测试代码
		System.out.println(Boolean.getBoolean("true"));
	}

	public static String httpPostToLc(String url, JSONObject params, String appKey, boolean isGS) {
		// post请求返回结果
		CloseableHttpClient httpClient = HttpClients.createDefault();

		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		httpPost.setHeader("Appkey", appKey);

		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
		httpPost.setConfig(requestConfig);
		try {
			if (null != params) {
				// 创建参数队列
				List<NameValuePair> nameValuePairs = new ArrayList<>();
				if (isGS) {
					nameValuePairs.add(new BasicNameValuePair("param", params.toJSONString()));
				} else {
					for (String key : params.keySet()) {
						nameValuePairs.add(new BasicNameValuePair(key, params.getString(key)));
					}
				}

				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			}
			CloseableHttpResponse result = httpClient.execute(httpPost);
			// 请求发送成功，并得到响应
			if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String str = "";
				try {
					// 读取服务器返回过来的json字符串数据
					str = EntityUtils.toString(result.getEntity(), "utf-8");
					return str;
				} catch (Exception e) {
					logger.error("post请求提交失败:" + url, e);
				}
			}
		} catch (IOException e) {
			logger.error("post请求提交失败:" + url, e);
		} finally {
			httpPost.releaseConnection();
		}
		return "";
	}


	public static String httpPostToApp(String url, String params,String host,String port) {
		// post请求返回结果
		CloseableHttpClient httpClient = HttpClients.createDefault();
		logger.debug("host = {} , port = {}, url = {}", host, port,url);
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "text/plain");
//		httpPost.setHeader("Appkey", appKey);
		// 设置请求和传输超时时间
		if (!"noproxy".equals(host)){
			logger.debug("有代理 host= {}",host);
			HttpHost proxy = new HttpHost(host,Integer.parseInt(port));
//			httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(120000).setConnectTimeout(120000).setProxy(proxy).build();
			httpPost.setConfig(requestConfig);
			//logger.debug("httpclient = {}",httpClient.getParams().getParameter(ConnRouteParams.DEFAULT_PROXY));
		}else {
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(120000).setConnectTimeout(120000).build();
			httpPost.setConfig(requestConfig);
		}


		try {
			if (null != params) {
				httpPost.setEntity(new StringEntity(params, "UTF-8"));
			}
			// 设置请求和传输超时时间
			Date startDate = new Date();
			CloseableHttpResponse result = httpClient.execute(httpPost);
			Date endDate = new Date();
			logger.debug("请求开始时间 = {}, 请求返回时间 = {}", startDate,endDate);
			logger.debug("请求返回时间 = {}",new Date());
			// 请求发送成功，并得到响应
			if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String str = "";
				try {
					// 读取服务器返回过来的json字符串数据
					str = EntityUtils.toString(result.getEntity(), "utf-8");
					return str;
				} catch (Exception e) {
					logger.error("post请求提交失败:" + url, e);
				}
			}
		} catch (IOException e) {
			logger.error("post请求提交失败:" + url, e);
		} finally {
			httpPost.releaseConnection();
		}
		return "";
	}



}