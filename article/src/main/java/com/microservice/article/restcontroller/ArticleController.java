package com.microservice.article.restcontroller;

import com.microservice.article.callable.JianShuSpiderCallable;
import com.microservice.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
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
	private JianShuSpiderCallable jianShuSpiderCallable;
	private Map<Integer, Future> futureMap = new HashMap<>();
	private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);


    @RequestMapping("/findByUserId/{userId}")
    public Object findByUserId(@PathVariable("userId") Long userId) {
        return articleService.findByUserId(userId);
    }

    @RequestMapping("/jianshuSpiderStart")
	public Object jianshuSpiderStart(String url) {
		if (url == null || url.isEmpty()) {
			url = "https://www.jianshu.com/p/49d8baf5fb99";
		}
		jianShuSpiderCallable.setStartUrl(url);
		Future submit = scheduledExecutorService.submit(jianShuSpiderCallable);
		futureMap.put(submit.hashCode(), submit);
		return submit.hashCode()+" 开始爬简书";
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
}