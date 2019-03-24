package com.microservice.article.component;

import java.util.List;

/**
 * @author ASUS
 * @date 2019/3/6
 */
public interface Spider {



	String catchTitle();

	void fetch(String targetUrl);

	String catchContent();

	List<String> catchUrls();

	String getAllBody();

}
