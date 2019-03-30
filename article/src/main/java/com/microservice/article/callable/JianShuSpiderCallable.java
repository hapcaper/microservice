package com.microservice.article.callable;

import com.microservice.article.component.Spider;
import com.microservice.article.pojo.vo.ArticleVO;
import com.microservice.article.service.ArticleService;
import org.apache.commons.httpclient.URIException;

import java.util.List;
import java.util.concurrent.Callable;

@Deprecated
public class JianShuSpiderCallable implements Callable {

    private ArticleService articleService;

    private Spider jianshuSpider;

    public JianShuSpiderCallable(Spider jianshuSpider,ArticleService articleService) {
        this.jianshuSpider = jianshuSpider;
        this.articleService = articleService;
    }
    private final String domain = "https://www.jianshu.com";
    private String startUrl = "https://www.jianshu.com/p/49d8baf5fb99";

    @Override
    public Object call() {
        try {
            String nextUrl = startUrl;
            while (nextUrl != null) {
                nextUrl = fetch(nextUrl);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    private String fetch(String url) throws URIException {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getId()+" 正在爬取数据");
        System.out.println(url);
        ArticleVO articleVO = new ArticleVO();
        try {
            jianshuSpider.fetch(url);
        } catch (URIException e) {
            System.out.println("爬取失败");
            e.printStackTrace();
            throw e;
        }

        List<String> list = null;
        try {
            articleVO.setContent(jianshuSpider.catchContent());
            articleVO.setTitle(jianshuSpider.catchTitle());
//        articleService.insert(articleVO);
            list = jianshuSpider.catchUrls();
        } catch (Exception e) {
            System.out.println("按照获取规则无法获取到内容");
            e.printStackTrace();
            throw e;
        }

        int random = (int) (Math.random() * 100);
        if (list.size() == 0) {
            System.out.println("没有拿到继续下去的urls");
            return null;
        }
        System.out.println("拿取下标为 "+random % list.size()+" 的url");
        return domain + list.get(random % list.size());
//        fetch(domain + list.get(random % list.size()));
    }

    public String getStartUrl() {
        return startUrl;
    }

    public void setStartUrl(String startUrl) {
        this.startUrl = startUrl;
    }
}
