package com.microservice.user.controller;

import com.microservice.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Description:
 *
 * @author 李自豪（zihao.li01@ucarinc.com）
 * @since 2019-02-27
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;


    @RequestMapping("/getAllArticleByUserId/{userId}")
    public Object getAllArticleByUserId(@PathVariable String userId) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("userId", 1);
        List list = restTemplate.postForObject("http://127.0.0.1:8001/article/findByUserId/" + 1, null, List.class, map);
        return list;
    }

}
