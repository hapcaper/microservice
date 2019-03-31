package com.microservice.article.pojo.domain;

import org.apache.ibatis.type.Alias;

@Alias("ruleDO")
public class RuleDO {

    protected Long id;

    protected String name;

    protected String pattern;

    protected Long ruleGroupId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Long getRuleGroupId() {
        return ruleGroupId;
    }

    public void setRuleGroupId(Long ruleGroupId) {
        this.ruleGroupId = ruleGroupId;
    }
}
