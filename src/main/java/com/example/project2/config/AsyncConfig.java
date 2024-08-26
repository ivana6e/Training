package com.example.project2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Value("${spring.async.core-pool-size}")
    private int corePoolSize; // init

    @Value("${spring.async.max-pool-size}")
    private int maxPoolSize; // if queue is full, maximum

    @Value("${spring.async.queue-capacity}")
    private int queueCapacity;

    @Bean(name = "asyncTaskExecutor")
    public AsyncTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor asyncTaskExecutor = new ThreadPoolTaskExecutor();
        asyncTaskExecutor.setCorePoolSize(corePoolSize);
        asyncTaskExecutor.setMaxPoolSize(maxPoolSize);
        asyncTaskExecutor.setQueueCapacity(queueCapacity);
        asyncTaskExecutor.setThreadNamePrefix("(=ↀᆺↀ=)-");
        asyncTaskExecutor.initialize();
        return asyncTaskExecutor;
    }
}
