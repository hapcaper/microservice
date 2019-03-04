package com.microservice.article.service;

import javax.lang.model.util.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author 李自豪（zihao.li01@ucarinc.com）
 * @since 2019-03-04
 */
public class MyHttpTest {
    public static String domain = "https://www.jianshu.com";

    public static void main(String[] args) throws IOException {

        List<String> links = initSpider(domain);
        for (int i = 0; i < 1; i++) {
            final List<String> subLinks = links.subList(i, i + 1);
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    loop(subLinks);
                }
            };
            runnable.run();
        }

    }

    private static void loop(List<String> links) {
        if (links == null) {
            return;
        }
        List<String> newLinks = new ArrayList<String>();
        for (String link : links) {
            newLinks = spider(link);
        }
        loop(newLinks);
    }

    private static List<String> spider(String url) {
        //step1、存数据
        // title
        //body > div.note > div.post > div.article > h1
        System.out.println(url);
        LzhHttpClient lzhHttpClient = new LzhHttpClient(url, 443, LzhHttpClient.HttpProtocal.HTTPS);
        Document document = Jsoup.parse(lzhHttpClient.execute());
        Elements title = document.select("body").select("div.note").select("div.post")
                .select("div.article").select("h1");
        System.out.println("title:");
        System.out.println(title.get(0).html());
        //author
        //body > div.note > div.post > div.article > div.author > div > span > a
        Elements author = document.select("body").select("div.note").select("div.post")
                .select("div.article").select("div.author").select("div").select("span").select("a");
        System.out.println("author:");
        System.out.println(author.html());

        //content
        //body > div.note > div.post > div.article > div.show-content > div
        Elements content = document.select("body").select("div.note").select("div.post")
                .select("div.article").select("div.show-content").select("div");
        System.out.println("content:");
        System.out.println(content.html());

        System.out.println("++++++++++++++");


        //step2、返回推荐列表的 urlList
        //body > div.note-bottom > div.seo-recommended-notes > div.note > a.title
        String s = "div.note-bottom>div.seo-recommended-notes>div.note>a.title";
        String[] els = s.split(">");
        Elements aList = document.select("body");
        for (String el : els) {
            aList = aList.select(el);
        }

        List<String> urls = new ArrayList<String>();
        for (Element element : aList) {
            urls.add(domain+element.attr("href"));
        }
        System.out.println(urls.size());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>");
        return urls;
    }

    private static List<String> initSpider(String url) {
        LzhHttpClient lzhHttpClient = new LzhHttpClient(url, 443, LzhHttpClient.HttpProtocal.HTTPS);

        Document document = Jsoup.parse(lzhHttpClient.execute());

        Elements titles = document.select("#list-container")
                .select("ul").select("li").select("div").select("a.title");

        System.out.println(titles.size());
        List<String> links = new ArrayList<String>();

        for (Element title : titles) {
            System.out.println(title.attr("href"));
            System.out.println(title.html());
            System.out.println(title.attr("class"));
            System.out.println("================");
            links.add(domain + title.attr("href"));
        }
        return links;
    }
}

class LzhHttpClient {

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
     */
    LzhHttpClient() {
        this("http://www.baidu.com", 80);
    }

    public LzhHttpClient(String url, Integer port) {
        this(url, port, HttpProtocal.HTTP);
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
        httpClient.getHostConfiguration().setProxy("119.101.116.173",9999);
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
