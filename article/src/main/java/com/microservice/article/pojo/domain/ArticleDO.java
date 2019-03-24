package com.microservice.article.pojo.domain;

import java.io.Serializable;

/**
 * Description:
 *
 * @author 李自豪（lizihaojlex@gmail.com）
 * @since 2019-02-18
 */
public class ArticleDO implements Serializable {

    protected Long id;
    protected String title;
    protected String content;
    protected Long userId;


    @Override
    public String toString() {
        return "ArticleDO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
