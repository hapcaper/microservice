package com.microservice.article.spiderV2;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ResolverImpl implements Resolver {

    private Document document;

    public ResolverImpl() {
    }

    @Override
    public void initDocument(String document) {
        this.document = Jsoup.parse(document);
    }

    @Override
    public String resolve(String rule) {
        if (document == null) {
            throw new RuntimeException("document 没有设置");
        }
        String[] list = rule.trim().split(">");
        Elements el = document.getAllElements();
        for (String s : list) {
            el = el.select(s);
        }
        return el.html();
    }
}
