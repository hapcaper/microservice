package com.microservice.article.runable;

import com.microservice.article.pojo.vo.ArticleVO;
import com.microservice.article.service.ArticleService;
import com.microservice.article.jianshuspider.JianShuSpider;

import java.util.List;

public class JianShuSpiderRunnable implements Runnable {

    private ArticleService articleService;

    private JianShuSpider jianshuSpider;

    private String startUrl;

    private final String domain = "https://www.jianshu.com";

    public JianShuSpiderRunnable(ArticleService articleService, JianShuSpider jianshuSpider) {
        this(articleService, jianshuSpider, "https://www.jianshu.com");
    }

    public JianShuSpiderRunnable(ArticleService articleService, JianShuSpider jianshuSpider, String startUrl) {
        this.articleService = articleService;
        this.jianshuSpider = jianshuSpider;
        this.startUrl = startUrl;
    }

    @Override
    public void run() {
        String nextUrl = startUrl;
        try {
            while (nextUrl != null) {
                nextUrl = fetch(nextUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String fetch(String url) {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getId()+" 正在爬取数据");
        System.out.println(url);
        ArticleVO articleVO = new ArticleVO();
        jianshuSpider.fetchDocument(url);

        List<String> list = null;
        try {
            articleVO.setContent(jianshuSpider.pickContent());
            articleVO.setTitle(jianshuSpider.pickTitle());
//        articleService.insert(articleVO);
            list = jianshuSpider.pickNextUrl();
        } catch (Exception e) {
            System.out.println("按照获取规则无法获取到内容");
            e.printStackTrace();
            throw e;
        }
        int random = (int) (Math.random() * 100);
        if (list.size() == 0) {
            System.out.println("没有拿到继续下去的urls");
            return null;
        }
        System.out.println("拿取下标为 "+random % list.size()+" 的url");
        return domain + list.get(random % list.size());
    }

}
