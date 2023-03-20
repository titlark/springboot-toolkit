package com.lark.readconfig.controller;

import com.lark.readconfig.config.DBConfig;
import com.lark.readconfig.config.DBConfig1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.EventListener;

@RestController
@RequestMapping("/api/config")
public class ConfigController {

    @Autowired
    private DBConfig dbConfig;
    @Autowired
    private DBConfig1 dbConfig1;

    /**
     * Environment 读取配置文件
     */
    @Autowired
    private Environment environment;

    @RequestMapping("readConfig")
    public Object readConfig() {
        System.out.println("dbConfig.getUsername() + \" \" + dbConfig.getPassword() = " + dbConfig.getUsername() + " " + dbConfig.getPassword());
        System.out.println("dbConfig1.getUsername() + \" \" + dbConfig1.getPassword() = " + dbConfig1.getUsername() + " " + dbConfig1.getPassword());
        String username = environment.getProperty("db.username");
        String password = environment.getProperty("db.password");
        System.out.println("username + \" \" + password = " + username + " " + password);
        return "success";
    }
}
