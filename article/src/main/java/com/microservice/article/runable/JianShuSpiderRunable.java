package com.microservice.article.runable;

import com.microservice.article.component.MySpider;
import com.microservice.article.component.PatternConfig;
import com.microservice.article.component.Spider;
import com.microservice.article.pojo.vo.ArticleVO;
import com.microservice.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author ASUS
 * @date 2019/3/6
 */
public class JianShuSpiderRunable implements Runnable {

	private Spider spider;

	@Autowired
	private ArticleService articleService;

	private String domain = "https://www.jianshu.com";

	public JianShuSpiderRunable(ArticleService articleService) {
		this.spider = new MySpider(new PatternConfig("body>div.note>div.post>div.article>h1"
				, "body>div.note>div.post>div.article>div.show-content>div"
				, "body>div.note-bottom>div.seo-recommended-notes>div.note>a.title"));
	}

	@Override
	public void run() {
		fetch("https://www.jianshu.com/p/49d8baf5fb99");
	}

	public void fetch(String startUrl) {
		ArticleVO articleVO = new ArticleVO();
		spider.fetch(startUrl);
		articleVO.setContent(spider.catchContent());
		articleVO.setTitle(spider.catchTitle());
		articleService.insert(articleVO);
		List<String> list = spider.catchUrls();
		for (String url : list) {
			fetch(domain + url);
		}
	}
}
