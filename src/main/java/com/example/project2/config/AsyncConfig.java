package com.example.project2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "ComputeWorkingHoursTaskExecutor")
    public AsyncTaskExecutor ComputeWorkingHoursTaskExecutor() {
        int corePoolSie = Runtime.getRuntime().availableProcessors();
        double taskCostTime = 0.1;
        int responseTime = 1;

        ThreadPoolTaskExecutor asyncTaskExecutor = new ThreadPoolTaskExecutor();
        // CPU intensive: CPU+1, I/O intensive: CPU*2
        asyncTaskExecutor.setCorePoolSize(corePoolSie*2);
        asyncTaskExecutor.setMaxPoolSize(corePoolSie*3);
        asyncTaskExecutor.setQueueCapacity((int) (corePoolSie/taskCostTime*responseTime));
        asyncTaskExecutor.setThreadNamePrefix("WorkingHours - ");
        asyncTaskExecutor.initialize();
        return asyncTaskExecutor;
    }

    @Bean(name = "LoginInfoTaskExecutor")
    public AsyncTaskExecutor asyncTaskExecutor1() {
        int corePoolSie = Runtime.getRuntime().availableProcessors();
        double taskCostTime = 0.1;
        int responseTime = 1;

        ThreadPoolTaskExecutor asyncTaskExecutor = new ThreadPoolTaskExecutor();
        // CPU intensive: CPU+1, I/O intensive: CPU*2
        asyncTaskExecutor.setCorePoolSize(corePoolSie*2);
        asyncTaskExecutor.setMaxPoolSize(corePoolSie*3);
        asyncTaskExecutor.setQueueCapacity((int) (corePoolSie/taskCostTime*responseTime));
        asyncTaskExecutor.setThreadNamePrefix("LoginInfo - ");
        asyncTaskExecutor.initialize();
        return asyncTaskExecutor;
    }
}
