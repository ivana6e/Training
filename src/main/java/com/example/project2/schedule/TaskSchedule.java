package com.example.project2.schedule;

import com.example.project2.async.ClockDiffTask;
import com.example.project2.dao.ClockDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.concurrent.CompletableFuture;

@Component
public class TaskSchedule {

    private final ClockDao clockDao;
    private final ClockDiffTask clockDiffTask;
    @Autowired
    public TaskSchedule(ClockDao clockDao, ClockDiffTask clockDiffTask) {
        this.clockDao = clockDao;
        this.clockDiffTask = clockDiffTask;
    }

    private final Logger logger = LoggerFactory.getLogger(ClockDiffSchedule.class);
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    // @Async("asyncTaskExecutor")
    @Scheduled(initialDelay = 5000)
    public CompletableFuture<String> compute() throws InterruptedException {
        var clocks = clockDao.findAll();

        for(var t : clocks) {
            clockDiffTask.compute(t);
        }

        logger.info("for-loop END");

        return CompletableFuture.completedFuture("compute for");
    }

    // @Async("asyncTaskExecutor")
    @Scheduled(initialDelay = 5000)
    public void fun() throws InterruptedException {
        logger.info("fun");
        logger.info("Thread name: {}", Thread.currentThread().getName());
    }
}
