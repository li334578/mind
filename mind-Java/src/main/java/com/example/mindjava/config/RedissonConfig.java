package com.example.mindjava.config;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Date 2023-08-10 13:27
 * @Description redisson配置
 * @Version 1.0.0
 * @Author liwenbo
 */
@Configuration
@Slf4j
public class RedissonConfig {

    private static final String redisConfTemp = "redis://{}:{}";

    @Resource
    private RedisProperties redisProperties;

    @Bean
    public Redisson redisson() {
        Config config = new Config();
        // 使用单机模式 设置地址 密码 和所用数据库
        if (StrUtil.isEmpty(redisProperties.getPassword())) {
            config.useSingleServer()
                    .setAddress(StrUtil.format(redisConfTemp, redisProperties.getHost(), redisProperties.getPort()))
                    .setDatabase(redisProperties.getDatabase())
                    .setConnectionMinimumIdleSize(4);
        } else {
            // 主从用该配置
            /*config.useMasterSlaveServers()
                    .setMasterAddress("redis://ip:port");*/
            config.useSingleServer()
                    .setAddress(StrUtil.format(redisConfTemp, redisProperties.getHost(), redisProperties.getPort()))
                    .setPassword(redisProperties.getPassword()).setDatabase(redisProperties.getDatabase())
                    .setConnectionMinimumIdleSize(4);
        }
        log.info("redisson init complete host:{} port:{} password:{}",
                redisProperties.getHost(),
                redisProperties.getPort(),
                redisProperties.getPassword());
        return (Redisson) Redisson.create(config);
    }
}
