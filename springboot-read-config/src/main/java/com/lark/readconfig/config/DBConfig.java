package com.lark.readconfig.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 读取 db-config.properties 配置文件，采用 @PropertySource + @ConfigurationProperties 注解读取方式
 * 注意：@PropertySource 不支持 yml 文件读取。
 */
@Data
@Component
@ConfigurationProperties(prefix = "db")
@PropertySource(value = {"config/db-config.properties"})
public class DBConfig {
    private String username;
    private String password;
}
