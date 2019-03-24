package com.microservice.article.component;

/**
 * @author ASUS
 * @date 2019/3/6
 */
public class PatternConfig {

	private String titlePattern;

	private String contentPattern;

	private String urlPattern;


	public PatternConfig(String titlePattern, String contentPattern, String urlPattern) {
		this.titlePattern = titlePattern;
		this.contentPattern = contentPattern;
		this.urlPattern = urlPattern;
	}


	public String getTitlePattern() {
		return titlePattern;
	}

	public void setTitlePattern(String titlePattern) {
		this.titlePattern = titlePattern;
	}

	public String getContentPattern() {
		return contentPattern;
	}

	public void setContentPattern(String contentPattern) {
		this.contentPattern = contentPattern;
	}

	public String getUrlPattern() {
		return urlPattern;
	}

	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}
}
