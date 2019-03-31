package com.microservice.article.restcontroller;

import com.microservice.article.pojo.vo.RuleGroupVO;
import com.microservice.article.pojo.vo.RuleVO;
import com.microservice.article.service.RuleGroupService;
import com.microservice.article.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.util.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rule")
public class RuleController {

    @Autowired
    private RuleService ruleService;
    @Autowired
    private RuleGroupService ruleGroupService;

    @RequestMapping("/addRuleGroup")
    public Object addRuleGroup(RuleGroupVO ruleGroupVO) {

        RuleGroupVO tmp = new RuleGroupVO();
        tmp.setName("jianshu");
        tmp.setRemark("简书爬取规则");

        int dbResult = ruleGroupService.insert(tmp);
        if (dbResult <= 0) {
            return new RuntimeException("插入规则组失败");
        }
        return "插入规则组成功";
    }

    @RequestMapping("/addRules")
    public Object addRules(@Nullable ArrayList<RuleVO> rules) {

        RuleVO title = new RuleVO();
        title.setName("title");
        title.setRuleGroupId(1L);
        title.setPattern("body>div.note>div.post>div.article>h1");
        rules.add(title);

        RuleVO content = new RuleVO();
        content.setName("content");
        content.setRuleGroupId(1L);
        content.setPattern("body>div.note>div.post>div.article>div.show-content>div");
        rules.add(content);

        RuleVO url = new RuleVO();
        url.setName("url");
        url.setPattern("body>div.note-bottom>div.seo-recommended-notes>div.note>a.title");
        url.setRuleGroupId(1L);
        rules.add(url);

        int dbResult = ruleService.insertList(rules);
        if (dbResult <= 0) {
            return new RuntimeException("插入规则列表失败");
        }
        return "插入规则列表成功" + rules;
    }




}
