package com.example.project2.schedule;

import com.example.project2.async.ClockDiffTask;
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
    @Autowired
    public ClockDiffSchedule(ClockDao clockDao, ClockDiffTask clockDiffTask) {
        this.clockDao = clockDao;
        this.clockDiffTask = clockDiffTask;
    }

    @Scheduled(initialDelay = 2000)
    public void compute() throws InterruptedException {
        var clocks = clockDao.findAll();

        List<CompletableFuture<CompletableFuture<Void>>> futures = new ArrayList<>();

        for(var t : clocks) {
            futures.add(CompletableFuture.supplyAsync(()-> {
                try {
                    return clockDiffTask.compute(t);
                }
                catch(InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }));
        }

        log.debug("for-loop END");

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        log.debug("futures: {}", (Object) futures.toArray(new CompletableFuture[0]));
        log.debug("join END");
    }

    @Scheduled(initialDelay = 2000)
    public void fun() throws InterruptedException {
        Thread.sleep(1000);
        log.debug("fun");
    }
}
