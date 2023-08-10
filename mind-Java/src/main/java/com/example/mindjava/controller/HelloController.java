package com.example.mindjava.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date 2023-08-10 13:10
 * @Description 控制层
 * @Version 1.0.0
 * @Author liwenbo
 */
@RequestMapping("/hello")
@RestController
@SaCheckLogin
public class HelloController {
    @GetMapping("/world")
    public String helloWorld() {
        return "Hello World!";
    }
}
