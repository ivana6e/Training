package com.example.project2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "asyncTaskExecutor")
    public AsyncTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor asyncTaskExecutor = new ThreadPoolTaskExecutor();
        asyncTaskExecutor.setCorePoolSize(3);
        asyncTaskExecutor.setMaxPoolSize(8);
        asyncTaskExecutor.setQueueCapacity(8);
        asyncTaskExecutor.setThreadNamePrefix("(=O.O=)-");
        asyncTaskExecutor.initialize();
        return asyncTaskExecutor;
    }

    @Bean(name = "asyncTaskExecutor1")
    public AsyncTaskExecutor asyncTaskExecutor1() {
        ThreadPoolTaskExecutor asyncTaskExecutor = new ThreadPoolTaskExecutor();
        asyncTaskExecutor.setCorePoolSize(2);
        asyncTaskExecutor.setMaxPoolSize(4);
        asyncTaskExecutor.setQueueCapacity(1);
        asyncTaskExecutor.setThreadNamePrefix("(=1.O=)-");
        asyncTaskExecutor.initialize();
        return asyncTaskExecutor;
    }
}
