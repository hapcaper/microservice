package com.microservice.article.conf;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class Conf {

    @Bean
    public String startUrl() {
        return "https://www.jianshu.com/p/49d8baf5fb99";
    }

}
