package com.microservice.article.runable;

import com.microservice.article.pojo.domain.RuleDO;
import com.microservice.article.spiderV2.Resolver;
import com.microservice.article.spiderV2.ResolverImpl;
import com.microservice.article.spiderV2.SpiderHttpClientV2;

import java.util.HashMap;
import java.util.List;

public class MySpiderRunable implements Runnable {
    private SpiderHttpClientV2 httpClient;
    private Resolver resolver;
    private List<RuleDO> rules;

    HashMap<String, String> map = new HashMap<>();

    private String startUrl = "www.jianshu.com";

    public MySpiderRunable(SpiderHttpClientV2 httpClient, List<RuleDO> rules, String startUrl) {
        this(httpClient, new ResolverImpl(), rules, startUrl);
    }

    public MySpiderRunable(SpiderHttpClientV2 httpClient, Resolver resolver, List<RuleDO> rules, String startUrl) {
        this.httpClient = httpClient;
        this.resolver = resolver;
        this.rules = rules;
        this.startUrl = startUrl;
    }

    @Override
    public void run() {
        String url = startUrl;
        while (true) {
            try {
                String respond = httpClient.execute(url);
                resolver.initDocument(respond);
                for (RuleDO rule : rules) {
                    String title = resolver.resolve(rule.getPattern());
                    map.put(rule.getName(), title);
                }
                url = map.get("url");
            } catch (Exception e) {
                return;
            }
        }
    }
}
