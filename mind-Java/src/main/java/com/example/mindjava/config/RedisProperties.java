package com.example.mindjava.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Date 2023-08-10 13:27
 * @Description redisson配置
 * @Version 1.0.0
 * @Author liwenbo
 */
@Data
@Configuration
public class RedisProperties {
    @Value(value = "${spring.redis.host}")
    private String host;
    @Value(value = "${spring.redis.port}")
    private Integer port;
    @Value(value = "${spring.redis.password}")
    private String password;
    @Value(value = "${spring.redis.database}")
    private Integer database;

}
