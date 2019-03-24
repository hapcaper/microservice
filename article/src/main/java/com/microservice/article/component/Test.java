package com.microservice.article.component;

/**
 * @author ASUS
 * @date 2019/3/6
 */
public class Test {
	public static void main(String[] args) {
		Spider spider = new MySpider(new PatternConfig("body>div.note>div.post>div.article>h1"
				,"body>div.note>div.post>div.article>div.show-content>div"
				,"body>div.note-bottom>div.seo-recommended-notes>div.note>a.title"));

		spider.fetch("https://www.jianshu.com/p/49d8baf5fb99");
		System.out.println(spider.catchContent());
		System.out.println(spider.catchTitle());
		System.out.println(spider.catchUrls());

	}
}
