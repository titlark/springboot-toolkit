package com.lark.json.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lark.json.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * jackson-databind 包里面有一个 com.fasterxml.jackson.databind.ObjectMapper 类可以完成对象和 Json 数据的互转，下面是一个简单的合作示例。
 * <p>
 * ObjectMapper objectMapper = new ObjectMapper();
 * <p>
 * String userJsonStr = objectMapper.writeValueAsString(user);
 * <p>
 * User jsonUser = objectMapper.readValue(userJsonStr, User.class);
 */
@RestController
@RequestMapping("/api/json")
public class JsonController {

    @RequestMapping("/hello")
    public Object hello() throws JsonProcessingException {
        User user = User.builder().userName("hzy").id(1L).age(30).address(null).memo(null).build();

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(user);
    }
}
