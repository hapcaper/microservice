package com.microservice.article.callable;

import com.microservice.article.component.MySpider;
import com.microservice.article.component.PatternConfig;
import com.microservice.article.component.Spider;
import com.microservice.article.pojo.vo.ArticleVO;
import com.microservice.article.service.ArticleService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Callable;

@Component
public class JianShuSpiderCallable implements Callable {

    @Resource
    private ArticleService articleService;
    private Spider spider;
    private String domain = "https://www.jianshu.com";
    private String startUrl;



    public JianShuSpiderCallable(String startUrl) {
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
