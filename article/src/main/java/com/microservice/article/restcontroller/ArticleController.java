package com.microservice.article.restcontroller;

import com.microservice.article.pojo.domain.RuleDO;
import com.microservice.article.runable.JianShuSpiderRunnable;
import com.microservice.article.runable.MySpiderRunnable;
import com.microservice.article.service.ArticleService;
import com.microservice.article.jianshuspider.JianShuPattern;
import com.microservice.article.jianshuspider.JianShuSpider;
import com.microservice.article.jianshuspider.JianShuSpiderImpl;
import com.microservice.article.spider.SpiderHttpClient;
import com.microservice.article.spiderV2.SpiderHttpClientV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

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
    @Autowired
	private volatile JianShuPattern jianShuPattern;
    @Autowired
	private Stack<String> proxyIpStack;

	private Map<Integer, Future> futureMap = new Hashtable<>();

	private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);

    @RequestMapping("/findByUserId/{userId}")
    public Object findByUserId(@PathVariable("userId") Long userId) {
        return articleService.findByUserId(userId);
    }

    @RequestMapping("/jianshuSpiderStart")
	public Object jianshuSpiderStart(String startUrl) {
		if (startUrl == null || startUrl.isEmpty()) {
			startUrl = "https://www.jianshu.com/p/49d8baf5fb99";
		}
		SpiderHttpClient spiderHttpClient = new SpiderHttpClient(443, proxyIpStack, SpiderHttpClient.HttpProtocol.HTTPS, true);
		JianShuSpider jianShuSpider = new JianShuSpiderImpl(jianShuPattern,spiderHttpClient);
		JianShuSpiderRunnable jianShuSpiderRunnable = new JianShuSpiderRunnable(articleService, jianShuSpider, startUrl);
		Future<?> future = scheduledExecutorService.submit(jianShuSpiderRunnable);
		futureMap.put(future.hashCode(), future);
		return future.hashCode() + " 开始爬简书";
	}

	@RequestMapping("/CustomSpiderStart")
	public Object CustomSpiderStart(String url) {
		if (url == null || url.isEmpty()) {
			url = "https://www.jianshu.com/p/49d8baf5fb99";
		}
		Future future = MySpiderStart(url);
		futureMap.put(future.hashCode(), future);
		return future.hashCode() + " 开始爬简书";
	}

	@RequestMapping("/jianshuSpiderStop/{futureHashCode}")
	public Object jianshuSpiderStop(@PathVariable("futureHashCode")Integer futureHashCode) {
		if (!futureMap.containsKey(futureHashCode)) {
			return "没有该future 的hashCode对应";
		}
		Future future = futureMap.get(futureHashCode);
		if (future.isDone()) {
			return "该任务已完成 " + futureHashCode;
		} else if (!future.cancel(true)){
			return "该任务取消失败 " + futureHashCode;
		}
		if (!future.isCancelled()) {
			return "该任务取消失败 " + futureHashCode;
		}
		return "停止 "+futureHashCode+" 爬简书";
	}

	@RequestMapping("/showFutures")
	public Object showFutures() {
//		for (Map.Entry<Integer, Future> integerFutureEntry : futureMap.entrySet()) {
//			if (integerFutureEntry.getValue().isDone()||integerFutureEntry.getValue().isCancelled()) {
//				futureMap.remove(integerFutureEntry.getKey().hashCode());
//			}
//		}
		return this.futureMap;
	}

	@RequestMapping("/pushIps")
	public Object pushIps(String ips) {
		if (ips == null || ips.isEmpty()) {
			ips = "139.217.24.50:3128\n" +
					"47.93.18.195:80\n" +
					"47.93.56.0:3128\n" +
					"116.196.81.58:3128\n" +
					"203.130.46.108:9090\n" +
					"116.196.90.176:3128\n" +
					"116.196.90.181:3128\n" +
					"47.95.201.41:3128\n" +
					"39.106.35.21:3128\n" +
					"180.76.134.106:3128\n" +
					"123.56.74.221:80\n" +
					"140.143.137.103:3128\n" +
					"123.56.188.27:3128\n" +
					"211.159.171.58:80\n" +
					"140.210.4.143:53281\n" +
					"202.108.2.42:80\n" +
					"39.98.62.41:3128\n" +
					"121.69.46.178:9000\n" +
					"114.249.119.140:9000\n" +
					"111.198.154.116:8888\n" +
					"124.207.82.166:8008\n" +
					"114.249.118.242:9000\n" +
					"106.38.162.105:9000\n" +
					"222.128.9.235:33428\n" +
					"47.94.105.1:3128\n" +
					"203.93.125.238:31566\n" +
					"211.101.136.86:49784\n" +
					"111.202.37.195:45571\n" +
					"218.241.219.226:9797\n" +
					"103.61.153.100:53281\n" +
					"115.171.203.223:9000\n" +
					"115.171.203.200:9000\n" +
					"123.117.39.100:9000\n" +
					"36.110.14.186:3128\n" +
					"117.114.149.66:53281\n" +
					"218.241.219.226:9999";
		}
		int num = 0;
		for (String s : ips.trim().split("\n")) {
			proxyIpStack.push(s);
			num++;
		}
		return num;
	}

	@RequestMapping("/clearIps")
	public Object clearIps() {
		proxyIpStack.clear();
		return "清除所有的代理ip";
	}

	private Future MySpiderStart(String url) {
		List<RuleDO> rules = new ArrayList<>();
		RuleDO title = new RuleDO();
		title.setName("title");
		title.setPattern("body>div.note>div.post>div.article>h1");
		rules.add(title);
		RuleDO content = new RuleDO();
		content.setName("content");
		content.setPattern("body>div.note>div.post>div.article>div.show-content>div");
		rules.add(content);
		RuleDO urlrule = new RuleDO();
		urlrule.setName("url");//必须是url
		urlrule.setPattern("body>div.note-bottom>div.seo-recommended-notes>div.note>a.title");
		rules.add(urlrule);
		SpiderHttpClientV2 httpClientV2 = new SpiderHttpClientV2(443, proxyIpStack, SpiderHttpClientV2.HttpProtocol.HTTPS, true);
		MySpiderRunnable mySpiderRunnable = new MySpiderRunnable(httpClientV2, rules, url);
		return scheduledExecutorService.submit(mySpiderRunnable);
	}
}