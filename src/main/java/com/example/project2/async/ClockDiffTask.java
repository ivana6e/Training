package com.example.project2.async;

import com.example.project2.dao.ClockDiffDao;
import com.example.project2.pojo.ClockDiffDo;
import com.example.project2.pojo.ClockDo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class ClockDiffTask {

    private final ClockDiffDao clockDiffDao;
    @Autowired
    public ClockDiffTask(ClockDiffDao clockDiffDao) {
        this.clockDiffDao = clockDiffDao;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @Async("asyncTaskExecutor")
    public CompletableFuture<Void> compute(ClockDo clockDo) throws InterruptedException {
        /* LocalDateTime inTime = clockDo.getClockIn().toLocalDateTime();
        String sameDateIn = inTime.format(formatter);
        LocalDateTime outTime = clockDo.getClockOut().toLocalDateTime();
        String sameDateOut = outTime.format(formatter);

        // log.debug("inTime = {}, outTime = {}", inTime, outTime);
        // log.debug("sameDateIn = {}, sameDateOut = {}", sameDateIn, sameDateOut);

        if(sameDateOut.equals(sameDateIn) && clockDo.getMessage() == null) {
            long in = clockDo.getClockIn().getTime();
            long out = clockDo.getClockOut().getTime();

            int hh = (int) ((out - in) / (1000 * 60 * 60));
            int mm = (int) (((out - in) / 1000 - hh * (60 * 60)) / 60);
            int ss = (int) ((out - in) / 1000 - hh * (60 * 60) - mm * 60);

            ClockDiffDo clockDiffDo = new ClockDiffDo();
            clockDiffDo.setId(clockDo.getId());
            clockDiffDo.setDate(clockDo.getDate());
            clockDiffDo.setClockDiff(hh + ":" + mm + ":" + ss);
            clockDiffDao.save(clockDiffDo);

            log.debug("hh:mm:ss = {}:{}:{}", hh, mm, ss);
        }
        else {
            ClockDiffDo clockDiffDo = new ClockDiffDo();
            clockDiffDo.setId(clockDo.getId());
            clockDiffDo.setDate(clockDo.getDate());
            clockDiffDo.setClockDiff("error");
            clockDiffDao.save(clockDiffDo);

            log.debug("error");
        }

         */

        if(clockDo.getClockIn() != null && clockDo.getClockOut() != null && clockDo.getMessage() == null) {
            long in = clockDo.getClockIn().getTime();
            long out = clockDo.getClockOut().getTime();

            int hh = (int) ((out - in) / (1000 * 60 * 60));
            int mm = (int) (((out - in) / 1000 - hh * (60 * 60)) / 60);
            int ss = (int) ((out - in) / 1000 - hh * (60 * 60) - mm * 60);

            ClockDiffDo clockDiffDo = new ClockDiffDo();
            clockDiffDo.setId(clockDo.getId());
            clockDiffDo.setDate(clockDo.getDate());
            clockDiffDo.setClockDiff(hh + ":" + mm + ":" + ss);
            clockDiffDao.save(clockDiffDo);

            log.debug("{} hh:mm:ss = {}:{}:{}", clockDo.getId(), hh, mm, ss);
        } else {
            ClockDiffDo clockDiffDo = new ClockDiffDo();
            clockDiffDo.setId(clockDo.getId());
            clockDiffDo.setDate(clockDo.getDate());
            clockDiffDo.setClockDiff("error");
            clockDiffDao.save(clockDiffDo);

            log.debug("error");
        }

        return null;
    }
}
