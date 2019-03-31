package com.microservice.article.pojo.vo;

import com.microservice.article.pojo.domain.RuleGroupDO;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Alias("ruleGroupVO")
public class RuleGroupVO extends RuleGroupDO {

    private List<RuleVO> ruleList;

    public List<RuleVO> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<RuleVO> ruleList) {
        this.ruleList = ruleList;
    }
}
