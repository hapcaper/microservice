package com.microservice.article.pojo.domain;

import org.apache.ibatis.type.Alias;

@Alias("ruleGroupDO")
public class RuleGroupDO {
    protected Long id;

    protected String name;

    protected String remark;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
