package com.microservice.article.restcontroller;

import com.microservice.article.component.MySpider;
import com.microservice.article.component.PatternConfig;
import com.microservice.article.component.Spider;
import com.microservice.article.pojo.vo.ArticleVO;
import com.microservice.article.runable.JianShuSpiderRunable;
import com.microservice.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description:
 *
 * @author 李自豪（lizihaojlex@gmail.com）
 * @since 2019-02-27
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
	private Spider spider;
	private String domain = "https://www.jianshu.com";


	private Thread thread = new Thread(new JianShuSpiderRunable(articleService));

    @RequestMapping("/findByUserId/{userId}")
    public Object findByUserId(@PathVariable("userId") Long userId) {
        return articleService.findByUserId(userId);
    }

    @RequestMapping("/jianshuSpiderStart")
	public Object jianshuSpiderStart() {
//	    if (thread == null) {
//	    	thread = new Thread(new JianShuSpiderRunable(articleService));
//	    }
//		thread.run();
	    fetch("https://www.jianshu.com/p/49d8baf5fb99");
	    return "开始爬简书";
	}
	@RequestMapping("/jianshuSpiderStop")
	public Object jianshuSpiderStop() {
//		if (thread == null) {
//			return "线程不存在";
//		}
//		thread.stop();
		return "停止爬简书";
	}

	private void fetch(String startUrl) {
		this.spider = new MySpider(new PatternConfig("body>div.note>div.post>div.article>h1"
				, "body>div.note>div.post>div.article>div.show-content>div"
				, "body>div.note-bottom>div.seo-recommended-notes>div.note>a.title"));
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
