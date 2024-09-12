package com.example.project2.schedule;

import com.example.project2.async.ComputeWorkingHoursTask;
import com.example.project2.async.LoginInfoTask;
import com.example.project2.dao.AttendanceJpaRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class AttendanceSchedule {

    private final AttendanceJpaRepository attendanceJpaRepository;
    private final ComputeWorkingHoursTask computeWorkingHoursTask;
    private final LoginInfoTask loginInfoTask;
    @Autowired
    public AttendanceSchedule(AttendanceJpaRepository attendanceJpaRepository,
                              ComputeWorkingHoursTask computeWorkingHoursTask,
                              LoginInfoTask loginInfoTask) {
        this.attendanceJpaRepository = attendanceJpaRepository;
        this.computeWorkingHoursTask = computeWorkingHoursTask;
        this.loginInfoTask = loginInfoTask;
    }

    // @Scheduled(cron="0 20 12 * * ?")
    @Scheduled(initialDelay = 2000)
    public void compute() throws InterruptedException {
        var attendances = attendanceJpaRepository.findAll();

        List<CompletableFuture<CompletableFuture<Void>>> futures = new ArrayList<>();

        for(var a : attendances) {
            futures.add(CompletableFuture.supplyAsync(()-> {
                try {
                    // return computeWorkingHoursTask.computeStringVer(a);
                    return computeWorkingHoursTask.computeTimeVer(a);
                } catch(InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }));
        }
        log.debug("for-loop END");

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        log.debug("join END");
    }

    /*@Scheduled(initialDelay = 4000, fixedDelay = 5000)
    public void createLoginData() {
        loginInfoTask.createLoginData();
    }*/

    // @Scheduled(cron = "*/15 * * * * ?")
    /*public void notifyLoginUser() {
        loginInfoTask.notifyLoginUser();
    }*/
}
