package com.example.mindjava.config.thread;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Date 2023-08-10 15:46
 * @Description 全局线程池
 * @Version 1.0.0
 * @Author liwenbo
 */
@Component
public class ThreadPool {

    @Bean("executorService")
    public ExecutorService execute() {
        DIYMyBlockingQueue<Runnable> instance = DIYMyBlockingQueue.getInstance(50, 100);
        // 线程工厂 名称含义 global thead pool
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNamePrefix("gtp-%d--").build();
        DIYMyThreadPool diyMyThreadPool = new DIYMyThreadPool(5, 10,
                10, TimeUnit.SECONDS, instance, namedThreadFactory, new ThreadPoolExecutor.DiscardPolicy());
        instance.setDiyMyThreadPool(diyMyThreadPool);
        return diyMyThreadPool;
    }
}
