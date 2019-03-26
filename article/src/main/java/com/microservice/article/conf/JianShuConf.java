package com.microservice.article.conf;

import com.microservice.article.component.MySpider;
import com.microservice.article.component.PatternConfig;
import com.microservice.article.component.Spider;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class JianShuConf {

//    @Bean
//    public String startUrl() {
//        return "https://www.jianshu.com/p/49d8baf5fb99";
//    }

    @Bean
    public Spider jianshuSpider() {
        return new MySpider(new PatternConfig("body>div.note>div.post>div.article>h1"
                , "body>div.note>div.post>div.article>div.show-content>div"
                , "body>div.note-bottom>div.seo-recommended-notes>div.note>a.title"));
    }

}
