package com.lark.readconfig.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 采用 @PropertySource + @Value 注解读取方式
 * <p>
 * 注意：@PropertySource 不支持 yml 文件读取。
 */
@Data
@Component
@PropertySource(value = {"config/db-config.properties"})
public class DBConfig1 {
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;
}
