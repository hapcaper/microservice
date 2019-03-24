package com.microservice.user.service;

import com.microservice.user.dao.UserDAO;
import com.microservice.user.pojo.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description:
 *
 * @author 李自豪（lizihaojlex@gmail.com）
 * @since 2019-02-27
 */
@Service
public class UserService {
    @Resource
    private UserDAO userDAO;

    public List<UserVO> findAll() {
        return userDAO.findAll();
    }
}
