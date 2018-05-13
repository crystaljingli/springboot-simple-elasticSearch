package com.lijing.springbootsimple.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author crystal
 * @CreatedDate 2018年05月13日 星期日 18时20分.
 */
@RestController
@RequestMapping("/hello")
public class HelloController {


    @RequestMapping("/hello")
    public String helloSpringBoot(){
        return "Hello";
    }
}
