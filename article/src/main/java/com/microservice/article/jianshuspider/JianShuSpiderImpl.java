package com.microservice.article.jianshuspider;

import com.microservice.article.jianshuspider.JianShuPattern;
import com.microservice.article.jianshuspider.JianShuSpider;
import com.microservice.article.spider.SpiderHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class JianShuSpiderImpl implements JianShuSpider {

    private JianShuPattern pattern;

    private Document document;

    private SpiderHttpClient httpClient;

    public JianShuSpiderImpl(JianShuPattern pattern, SpiderHttpClient httpClient) {
        this.pattern = pattern;
        this.httpClient = httpClient;
    }

    @Override
    public String pickTitle() {
        if (pattern.getTitlePattern() == null) {
            return "";
        }
        String[] list = pattern.getTitlePattern().trim().split(">");
        Elements el = document.getAllElements();
        for (String s : list) {
            el = el.select(s);
        }
        return el.html();
    }

    @Override
    public String pickContent() {
        if (pattern.getContentPattern() == null) {
            return "";
        }
        String[] list = pattern.getContentPattern().trim().split(">");
        Elements el = document.getAllElements();
        for (String s : list) {
            el = el.select(s);
        }
        return el.html();
    }

    @Override
    public List<String> pickNextUrl() {
        if (pattern.getUrlPattern() == null) {
            return null;
        }
        String[] list = pattern.getUrlPattern().trim().split(">");
        Elements elements = document.getAllElements();
        for (String s : list) {
            elements = elements.select(s);
        }
        List<String> urls = new ArrayList<>();

        for (Element el : elements) {
            urls.add(el.attr("href"));
        }
        return urls;
    }

    @Override
    public Document fetchDocument(String targetUrl) {
        httpClient.setUrl(targetUrl);
        String executeStr = httpClient.execute();
        if (executeStr == null) {
            System.out.println("爬取失败");
        } else {
            System.out.println("爬取成功");
        }
        assert executeStr != null;
        this.document = Jsoup.parse(executeStr);
        return this.document;
    }
}
