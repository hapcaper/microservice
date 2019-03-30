package com.microservice.article.spiderV2;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Stack;

public class SpiderHttpClientV2 {
    private Integer port;
    //代理ip 若是进行http代理则需要
    private Stack<String> proxyIpStack = new Stack<>();
    /**
     * 是否需要进行http代理 默认不需要
     */
    private boolean proxy = false;
    private HttpProtocol protocol;

    private HttpMethod httpMethod;
    private HttpClient httpClient;

    public SpiderHttpClientV2(Integer port, HttpProtocol protocol) {
        this(port, new Stack<String>(), protocol, false);
    }

    public SpiderHttpClientV2(Integer port, Stack<String> proxyIpStack, HttpProtocol protocol, boolean proxy) {
        this(port, proxyIpStack, protocol, proxy,new GetMethod());
    }

    public SpiderHttpClientV2(Integer port, Stack<String> proxyIpStack, HttpProtocol protocol, boolean proxy, HttpMethod httpMethod) {
        this.port = port;
        this.proxyIpStack = proxyIpStack;
        this.proxy = proxy;
        this.protocol = protocol;
        this.httpMethod = httpMethod;
        init();
    }

    private void init() {
        httpClient = new HttpClient();
        //若是设置了代理 则进行代理配置
        if (proxy) {
            loadProxy();
        }
    }
    /**
     * 装载一个代理
     */
    private void loadProxy() {
        if (proxyIpStack.isEmpty()) throw new RuntimeException("代理池用光了,没有代理ip了");
        //newIpPort eg: 123.123.123.123:8880
        String newIpPort = proxyIpStack.pop();
        String ip = newIpPort.split(":")[0];
        int port = Integer.parseInt(newIpPort.split(":")[1]);
        httpClient.getHostConfiguration().setProxy(ip, port);
    }

    public String execute(String url) {
        String responseBody = null;
        try {
            if (protocol == HttpProtocol.HTTP) {
                httpMethod.setURI(new HttpURL(url));
            } else if (protocol == HttpProtocol.HTTPS) {
                httpMethod.setURI(new HttpsURL(url));
            }
            httpMethod.getParams().setSoTimeout(10000);
            httpClient.setConnectionTimeout(10000);
            httpClient.setTimeout(10000);
            if (httpClient.getHostConfiguration().isProxySet()) {
                System.out.println("代理ip : " + httpClient.getHostConfiguration().getProxyHost() + "代理端口 : " + httpClient.getHostConfiguration().getPort());
            } else {
                System.out.println("没有使用代理");
            }
            httpClient.executeMethod(httpMethod);
            responseBody = new String(httpMethod.getResponseBody());
        } catch (ConnectException e) {
            System.out.println("请求异常1");
            if (proxy) {
                loadProxy();
                return execute(url);
            }else e.printStackTrace();
        } catch (HttpException e) {
            System.out.println("请求异常2");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("请求异常3");
            if (proxy) {
                loadProxy();
                return execute(url);
            }else e.printStackTrace();
        }
        return responseBody;
    }
    public enum HttpProtocol {
        HTTP, HTTPS,
    }
}
