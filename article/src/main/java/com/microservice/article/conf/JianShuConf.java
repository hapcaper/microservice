package com.microservice.article.conf;

import com.microservice.article.jianshuspider.JianShuPattern;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Stack;

@SpringBootConfiguration
public class JianShuConf {

//    @Bean
//    public String startUrl() {
//        return "https://www.jianshu.com/p/49d8baf5fb99";
//    }


    @Bean
    public Stack<String> proxyIpStack() {
        return new Stack<>();
    }

    @Bean
    public JianShuPattern jianShuPattern() {
        return new JianShuPattern("body>div.note>div.post>div.article>h1"
                , "body>div.note>div.post>div.article>div.show-content>div"
                , "body>div.note-bottom>div.seo-recommended-notes>div.note>a.title");
    }

}
