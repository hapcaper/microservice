package com.microservice.article.jianshuspider;

import com.microservice.article.spider.Spider;

public interface JianShuSpider extends Spider {
    String pickTitle();

    String pickContent();
}
