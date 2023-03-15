package com.lark.validation.controller;

import com.lark.validation.advice.NotControllerResponseAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @RequestMapping("hello")
    public Object hello() {
        return "hello";
    }

    /**
     * 对返回结果不进行封装
     *
     * @return
     */
    @RequestMapping("/health")
    @NotControllerResponseAdvice
    public String health() {
        return "success";
    }
}
