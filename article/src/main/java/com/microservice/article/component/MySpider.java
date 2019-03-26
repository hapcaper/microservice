package com.microservice.article.component;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ASUS
 * @date 2019/3/6
 */
public class MySpider implements Spider {

	private PatternConfig patternConfig;

	private Document document;

	public MySpider(PatternConfig patternConfig) {
		this.patternConfig = patternConfig;
	}

	public PatternConfig getPatternConfig() {
		return patternConfig;
	}

	public void setPatternConfig(PatternConfig patternConfig) {
		this.patternConfig = patternConfig;
	}


	/**
	 * titlePattern eg : body > div.note > div.post > div.article > h1
	 * @return
	 */
	@Override
	public String catchTitle() {
		if (patternConfig.getTitlePattern() == null) {
			return "";
		}
		String[] list = patternConfig.getTitlePattern().trim().split(">");
		Elements el = document.getAllElements();
		for (String s : list) {
			el = el.select(s);
		}
		return el.get(0).html();
	}

	@Override
	public void fetch(String targetUrl) {
		LzhHttpClient httpClient = new LzhHttpClient(targetUrl, 443, LzhHttpClient.HttpProtocal.HTTPS);
		this.document = Jsoup.parse(httpClient.execute());
	}

	@Override
	public String catchContent() {
		if (patternConfig.getContentPattern() == null) {
			return "";
		}
		String[] list = patternConfig.getContentPattern().trim().split(">");
		Elements el = document.getAllElements();
		for (String s : list) {
			el = el.select(s);
		}
		return el.html();
	}

	@Override
	public List<String> catchUrls() {
		if (patternConfig.getUrlPattern() == null) {
			return null;
		}
		String[] list = patternConfig.getUrlPattern().trim().split(">");
		Elements elements = document.getAllElements();
		for (String s : list) {
			elements = elements.select(s);
		}
		List<String> urls = new ArrayList<String>();

		for (Element el : elements) {
			urls.add(el.attr("href"));
		}
		return urls;
	}

	@Override
	public String getAllBody() {
		return document.html();
	}
}
