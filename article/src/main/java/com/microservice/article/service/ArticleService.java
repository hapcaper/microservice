package com.microservice.article.service;

import com.microservice.article.dao.ArticleDAO;
import com.microservice.article.pojo.vo.ArticleVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Description:
 *
 * @author lizihao
 * @since 2019-02-18
 */
@Service
public class ArticleService {

    @Resource
    private ArticleDAO articleDAO;

    public List<ArticleVO> find(ArticleVO articleVO) {
        return Collections.singletonList(articleVO);
    }

    public List<ArticleVO> findByUserId( Long userId) {
        return articleDAO.findByUserId(userId);
    }

}
