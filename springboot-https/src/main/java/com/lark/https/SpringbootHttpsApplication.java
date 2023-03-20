package com.lark.https;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 如何同时支持？
 * 如果你需要同时支持 HTTP 和 HTTPS 这两个协议，就需要把另外一个协议用程序化的方式来配置。
 * <p>
 * 因为通过程序的方式配置 HTTP 协议更加简单一点，所以，Spring Boot 推荐的做法是把 HTTPS 配置在配置文件，HTTP 通过程序来配置。
 */
@SpringBootApplication
public class SpringbootHttpsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootHttpsApplication.class, args);
    }

}
