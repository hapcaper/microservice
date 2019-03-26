package com.microservice.article.callable;

import com.microservice.article.component.Spider;
import com.microservice.article.pojo.vo.ArticleVO;
import com.microservice.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Callable;

@Component
public class JianShuSpiderCallable implements Callable {
    @Resource
    private ArticleService articleService;
    @Autowired
    private Spider jianshuSpider;

    private final String domain = "https://www.jianshu.com";
    private String startUrl = "https://www.jianshu.com/p/49d8baf5fb99";

    @Override
    public Object call() throws Exception {
        fetch(startUrl);
        return null;
    }

    private void fetch(String url) throws InterruptedException {
        Thread.sleep(1000L);
        System.out.println(Thread.currentThread().getId()+" 正在爬取数据");
        System.out.println(url);
        ArticleVO articleVO = new ArticleVO();
        jianshuSpider.fetch(url);
        articleVO.setContent(jianshuSpider.catchContent());
        articleVO.setTitle(jianshuSpider.catchTitle());
        articleService.insert(articleVO);
        List<String> list = jianshuSpider.catchUrls();
        int random = (int) (Math.random() * 100);
        System.out.println(random % list.size());
        fetch(domain + list.get(random % list.size()));
    }

    public String getStartUrl() {
        return startUrl;
    }

    public void setStartUrl(String startUrl) {
        this.startUrl = startUrl;
    }
}
