package com.example.project2.schedule;

import com.example.project2.async.ClockDiffTask;
import com.example.project2.async.TestTask;
import com.example.project2.dao.ClockDao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class ClockDiffSchedule {

    private final ClockDao clockDao;
    private final ClockDiffTask clockDiffTask;
    private final TestTask testTask;
    @Autowired
    public ClockDiffSchedule(ClockDao clockDao, ClockDiffTask clockDiffTask, TestTask testTask) {
        this.clockDao = clockDao;
        this.clockDiffTask = clockDiffTask;
        this.testTask = testTask;
    }

    // @Scheduled(cron="0 20 16 * * ?")
    @Scheduled(initialDelay = 20000)
    public void compute() throws InterruptedException {
        var clocks = clockDao.findAll();

        List<CompletableFuture<CompletableFuture<Void>>> futures = new ArrayList<>();

        for(var t : clocks) {
            futures.add(CompletableFuture.supplyAsync(()-> {
                try {
                    return clockDiffTask.compute(t);
                } catch(InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }));
        }
        log.debug("for-loop END");

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // log.debug("futures: {}", (Object) futures.toArray(new CompletableFuture[0]));
        log.debug("join END");
    }

    /* @Scheduled(initialDelay = 10000, fixedDelay = 5000)
    public void createLoginData() {
        testTask.createLoginData();
    }

     */

    // @Scheduled(cron = "*/15 * * * * ?")
    /* public void notifyLoginUser() {
        testTask.notifyLoginUser();
    }

     */
}
