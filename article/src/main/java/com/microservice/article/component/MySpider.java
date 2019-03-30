package com.microservice.article.component;

import org.apache.commons.httpclient.URIException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author ASUS
 * @date 2019/3/6
 */
@Deprecated
public class MySpider implements Spider {

	private PatternConfig patternConfig;

	private Document document;

	private LzhHttpClient lzhHttpClient;

	public MySpider(PatternConfig patternConfig, Stack<String> proxyIpStack) {
		this.lzhHttpClient = new LzhHttpClient("", 443, LzhHttpClient.HttpProtocal.HTTPS,proxyIpStack);
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
	public void fetch(String targetUrl) throws URIException {
		lzhHttpClient.setUrl(targetUrl);
		String executeStr = lzhHttpClient.execute();
		if (executeStr == null) {
			System.out.println("爬取失败");
		} else {
			System.out.println("爬取成功");
		}
		assert executeStr != null;
		this.document = Jsoup.parse(executeStr);
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

	public LzhHttpClient getLzhHttpClient() {
		return lzhHttpClient;
	}

	public void setLzhHttpClient(LzhHttpClient lzhHttpClient) {
		this.lzhHttpClient = lzhHttpClient;
	}
}
