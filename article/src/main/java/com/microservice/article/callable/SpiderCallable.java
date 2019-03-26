package com.microservice.article.callable;

import com.microservice.article.component.MySpider;
import com.microservice.article.component.PatternConfig;
import com.microservice.article.component.Spider;
import com.microservice.article.pojo.vo.ArticleVO;
import com.microservice.article.service.ArticleService;
import org.apache.commons.lang.math.RandomUtils;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class SpiderCallable implements Callable {

    private ArticleService articleService;
    private Spider spider;
    private String domain = "https://www.jianshu.com";
    private String startUrl;



    public SpiderCallable(ArticleService articleService,String startUrl) {
        this.articleService = articleService;
        this.startUrl = startUrl;
        this.spider = new MySpider(new PatternConfig("body>div.note>div.post>div.article>h1"
                , "body>div.note>div.post>div.article>div.show-content>div"
                , "body>div.note-bottom>div.seo-recommended-notes>div.note>a.title"));
    }

    @Override
    public Object call() throws Exception {
        fetch(startUrl);
        return null;
    }

    private void fetch(String startUrl) throws InterruptedException {
        Thread.sleep(1000L);
        System.out.println(Thread.currentThread().getId()+" 正在爬取数据");
        ArticleVO articleVO = new ArticleVO();
        spider.fetch(startUrl);
        articleVO.setContent(spider.catchContent());
        articleVO.setTitle(spider.catchTitle());
        articleService.insert(articleVO);
        List<String> list = spider.catchUrls();
        int random = (int) Math.random() * 100;
        fetch(domain + list.get(random % list.size()));
    }
}
