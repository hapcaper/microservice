package com.microservice.article.component;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;

/**
 * @author ASUS
 * @date 2019/3/6
 */
public class LzhHttpClient {

	private String url;
	private Integer port;
	private HttpProtocal protocal;
	private HttpMethod httpMethod;

	private HttpClient httpClient;
	public enum HttpProtocal {
		HTTP, HTTPS,
	}


	/**
	 * 默认访问百度 443
	 * ??我当初为什么要默认百度呢？？能爬百度么？？
	 */
	LzhHttpClient() {
		this("http://www.baidu.com", 80);
	}

	public LzhHttpClient(String url, Integer port) {
		this(url, port, LzhHttpClient.HttpProtocal.HTTP);
	}

	public LzhHttpClient(String url, Integer port, HttpProtocal protocal) {
		this(url, port, protocal, new GetMethod());
	}

	public LzhHttpClient(String url, Integer port, HttpProtocal protocal, HttpMethod httpMethod) {
		this.url = url;
		this.port = port;
		this.protocal = protocal;
		this.httpMethod = httpMethod;
		init();

	}

	private void init() {
		if (protocal == HttpProtocal.HTTP) {
			try {
				httpMethod.setURI(new HttpURL(url));
			} catch (URIException e) {
				e.printStackTrace();
			}
		} else if (protocal == HttpProtocal.HTTPS) {
			try {
				httpMethod.setURI(new HttpsURL(url));
			} catch (URIException e) {
				e.printStackTrace();
			}
		}
		httpClient = new HttpClient();
//		httpClient.getHostConfiguration().setProxy("119.101.116.173",9999);
	}

	public String execute() {
		StringBuilder responseBody = new StringBuilder();
		try {
			httpClient.executeMethod(httpMethod);
			String s = new String(httpMethod.getResponseBody());
			responseBody.append(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseBody.toString();
	}

}
