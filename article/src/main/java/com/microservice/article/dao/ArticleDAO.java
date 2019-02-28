package com.microservice.article.dao;

import com.microservice.article.pojo.vo.ArticleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description:
 *
 * @author 李自豪（zihao.li01@ucarinc.com）
 * @since 2019-02-28
 */
@Mapper
public interface ArticleDAO {

    List<ArticleVO> findByUserId(@Param("userId") Long userId);

}
