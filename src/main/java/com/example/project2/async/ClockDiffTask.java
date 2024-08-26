package com.example.project2.async;

import com.example.project2.dao.ClockDiffDao;
import com.example.project2.pojo.ClockDiffDo;
import com.example.project2.pojo.ClockDo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.concurrent.CompletableFuture;

@Component
public class ClockDiffTask {

    private final ClockDiffDao clockDiffDao;
    @Autowired
    public ClockDiffTask(ClockDiffDao clockDiffDao) {
        this.clockDiffDao = clockDiffDao;
    }

    private final Logger logger = LoggerFactory.getLogger(ClockDiffTask.class);
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    @Async("asyncTaskExecutor")
    public CompletableFuture<String> compute(ClockDo clockDo) throws InterruptedException {
        String sameDateIn = sdf.format(clockDo.getClockIn());
        String sameDateOut = sdf.format(clockDo.getClockOut());

        if(sameDateOut.equals(sameDateIn)) {
            long in = clockDo.getClockIn().getTime();
            long out = clockDo.getClockOut().getTime();

            int hh = (int) ((out - in) / (1000 * 60 * 60));
            int mm = (int) (((out - in) / 1000 - hh * (60 * 60)) / 60);
            int ss = (int) ((out - in) / 1000 - hh * (60 * 60) - mm * 60);

            ClockDiffDo clockDiffDo = new ClockDiffDo();
            clockDiffDo.setId(clockDo.getId());
            clockDiffDo.setUsername(clockDo.getUsername());
            clockDiffDo.setClockDiff(hh + ":" + mm + ":" + ss);
            clockDiffDao.save(clockDiffDo);

            logger.info("hh:mm:ss = {}:{}:{}", hh, mm, ss);
        }
        else {
            logger.info("not at the same day");
        }

        logger.info("Thread name: {}", Thread.currentThread().getName());

        return CompletableFuture.completedFuture("computeTask");
    }
}
