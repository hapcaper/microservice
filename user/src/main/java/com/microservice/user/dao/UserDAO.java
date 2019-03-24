package com.microservice.user.dao;

import com.microservice.user.pojo.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Description:
 *
 * @author 李自豪（lizihaojlex@gmail.com）
 * @since 2019-02-27
 */
@Mapper
public interface UserDAO {

    List<UserVO> findAll();
}
