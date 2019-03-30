package com.microservice.article.pojo.domain;

import org.apache.ibatis.type.Alias;

@Alias("rule")
public class RuleDO {

    private Long id;

    private String name;

    private String pattern;

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
}
