package com.lark.validation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/test")
public class TestController {

    @RequestMapping("success")
    public String success() {
        return "success";
    }
}
