package com.example.project2.schedule;

import com.example.project2.dao.ClockDao;
import com.example.project2.dao.ClockDiffDao;
import com.example.project2.pojo.ClockDiffDo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.concurrent.CompletableFuture;

@Component
public class ClockDiffSchedule {

    /* private final ClockDao clockDao;
    private final ClockDiffDao clockDiffDao;
    @Autowired
    public ClockDiffSchedule(ClockDao clockDao, ClockDiffDao clockDiffDao) {
        this.clockDao = clockDao;
        this.clockDiffDao = clockDiffDao;
    }

    private final Logger logger = LoggerFactory.getLogger(ClockDiffSchedule.class);
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    private final long start = System.currentTimeMillis();

    @Async("asyncTaskExecutor")
    @Scheduled(initialDelay = 5000)
    public CompletableFuture<String> compute() throws InterruptedException {
        var clocks = clockDao.findAll();

        for(var t : clocks) {
            String sameDateIn = sdf.format(t.getClockIn());
            String sameDateOut = sdf.format(t.getClockOut());

            if(sameDateOut.equals(sameDateIn)) {
                long in = t.getClockIn().getTime();
                long out = t.getClockOut().getTime();

                int hh = (int) ((out-in)/(1000*60*60));
                int mm = (int) (((out-in)/1000-hh*(60*60))/60);
                int ss = (int) ((out-in)/1000-hh*(60*60)-mm*60);

                ClockDiffDo clockDiffDo = new ClockDiffDo();
                clockDiffDo.setId(t.getId());
                clockDiffDo.setUsername(t.getUsername());
                clockDiffDo.setClockDiff(hh+":"+mm+":"+ss);
                clockDiffDao.save(clockDiffDo);

                logger.info("hh:mm:ss = {}:{}:{}", hh, mm, ss);
            }
            else {
                logger.info("not at the same day");
            }

            var end = System.currentTimeMillis();

            logger.info("cpt time: {}", end-start);
            logger.info("Thread name: {}", Thread.currentThread().getName());
            Thread.sleep(3000);
        }

        return CompletableFuture.completedFuture("compute");
    }

    @Async("asyncTaskExecutor")
    @Scheduled(initialDelay = 5000)
    public void fun() throws InterruptedException {
        Thread.sleep(2000);
        var end = System.currentTimeMillis();

        logger.info("fun time: {}", end-start);
        logger.info("Thread name: {}", Thread.currentThread().getName());
    }

     */
}
