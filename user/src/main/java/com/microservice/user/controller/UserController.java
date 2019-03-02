package com.microservice.user.controller;

import com.microservice.user.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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
    @HystrixCommand(fallbackMethod = "error")
    public Object getAllArticleByUserId(@PathVariable String userId) {

        List list = restTemplate.postForObject("http://article/article/findByUserId/" + userId, null, List.class);
        return list;
    }

    public String error() {
        return "error";
    }

}
