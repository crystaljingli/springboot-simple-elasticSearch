package com.lijing.springbootsimple.controller;

import com.lijing.springbootsimple.config.Resource;
import com.lijing.springbootsimple.entity.User;
import com.lijing.springbootsimple.utils.JsonUtils;
import com.lijing.springbootsimple.utils.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Description
 * @Author crystal
 * @CreatedDate 2018年05月13日 星期日 18时47分.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private Resource resource;
    @GetMapping("/getUser")
    public Result getUser(){
        User user  = new User();
        user.setUserName("Mark");
        user.setAge(19);
        user.setBirthDay(new Date());
        user.setDesc("I am Mark");
        return JsonUtils.ok(user);
    }
    @GetMapping("/getResource")
    public Result getResource(){
        Resource bean = new Resource();
        BeanUtils.copyProperties(resource,bean);
        return JsonUtils.ok(bean);
    }
}
