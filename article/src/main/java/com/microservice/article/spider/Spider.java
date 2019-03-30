package com.microservice.article.spider;

import org.jsoup.nodes.Document;

import java.util.List;

public interface Spider {

    Document fetchDocument(String targetUrl);

    List<String> pickNextUrl();

}
