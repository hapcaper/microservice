package com.microservice.article;

import com.microservice.article.pojo.vo.RuleGroupVO;
import com.microservice.article.service.RuleGroupService;
import com.microservice.article.service.RuleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleApplicationTests {

    @Autowired
    private RuleService ruleService;
    @Autowired
    private RuleGroupService ruleGroupService;

    @Test
    public void contextLoads() {
        RuleGroupVO groupVO = new RuleGroupVO();
        groupVO.setName("jianshu");
        groupVO.setRemark("简书爬虫规则组");
        int dbRe = ruleGroupService.insert(groupVO);
//        assert dbRe > 0;
    }

}
