package com.microservice.article.component;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Stack;

/**
 * @author ASUS
 * @date 2019/3/6
 */
@Deprecated
public class LzhHttpClient {

	private String url;
	private Integer port;
	private HttpProtocal protocal;
	private HttpMethod httpMethod;
	private HttpClient httpClient;
	//代理ip
	private Stack<String> proxyIpStack;
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
		this(url, port, LzhHttpClient.HttpProtocal.HTTP, new Stack<>());
	}

	public LzhHttpClient(String url, Integer port, HttpProtocal protocal,Stack<String> proxyIpStack) {
		this(url, port, protocal, new GetMethod(),proxyIpStack);
	}

	public LzhHttpClient(String url, Integer port, HttpProtocal protocal, HttpMethod httpMethod,Stack<String> proxyIpStack) {
		this.url = url;
		this.port = port;
		this.protocal = protocal;
		this.httpMethod = httpMethod;
		this.proxyIpStack = proxyIpStack;
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
		reloadProxy();
//		httpClient.getHostConfiguration().setProxy("203.93.125.238",31566);
	}

	public String execute() {
		StringBuilder responseBody = new StringBuilder();
		try {
			httpMethod.getParams().setSoTimeout(10000);
			if (httpClient.getHostConfiguration().isProxySet()) {
				System.out.println("代理ip : " + httpClient.getHostConfiguration().getProxyHost() + "代理端口 : " + httpClient.getHostConfiguration().getPort());
			} else {
				System.out.println("没有使用代理");
			}
			httpClient.executeMethod(httpMethod);
			String s = new String(httpMethod.getResponseBody());
			responseBody.append(s);
		} catch (ConnectException e) {
//			e.printStackTrace();
			reloadProxy();
			return execute();
		} catch (HttpException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		} catch (Exception e) {
			reloadProxy();
			return execute();
		}
		return responseBody.toString();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) throws URIException {
		this.url = url;
		httpMethod.setURI(new HttpsURL(url));
	}

	public Stack<String> getProxyIpStack() {
		return proxyIpStack;
	}

	public void setProxyIpStack(Stack<String> proxyIpStack) {
		this.proxyIpStack = proxyIpStack;
	}

	private void reloadProxy() {
		if (proxyIpStack.isEmpty()) {
			throw new RuntimeException("没有代理ip了");
		}
		//newIpPort eg: 123.123.123.123:8880
		String newIpPort = proxyIpStack.pop();
		String ip = newIpPort.split(":")[0];
		Integer port = Integer.valueOf(newIpPort.split(":")[1]);
		httpClient.getHostConfiguration().setProxy(ip, port);
	}
}
