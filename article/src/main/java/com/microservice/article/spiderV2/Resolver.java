package com.microservice.article.spiderV2;

public interface Resolver {

    String resolve(String rule);

    void initDocument(String document);

}