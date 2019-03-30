package com.microservice.article.component;

import org.apache.commons.httpclient.URIException;

import java.util.List;

/**
 * @author ASUS
 * @date 2019/3/6
 */
@Deprecated
public interface Spider {



	String catchTitle();

	void fetch(String targetUrl) throws URIException;

	String catchContent();

	List<String> catchUrls();

	String getAllBody();

}
