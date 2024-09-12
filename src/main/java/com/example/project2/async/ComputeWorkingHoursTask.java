package com.example.project2.async;

import com.example.project2.dao.AttendanceJpaRepository;
import com.example.project2.pojo.attendance.AttendanceDo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class ComputeWorkingHoursTask {

    private final AttendanceJpaRepository attendanceJpaRepository;
    @Autowired
    public ComputeWorkingHoursTask(AttendanceJpaRepository attendanceJpaRepository) {
        this.attendanceJpaRepository = attendanceJpaRepository;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @Async("ComputeWorkingHoursTaskExecutor")
    public CompletableFuture<Void> computeStringVer(AttendanceDo attendanceDo) throws InterruptedException {
        /* if(attendanceDo.getClockIn() != null && attendanceDo.getClockOut() != null) {
            long in = attendanceDo.getClockIn().getTime();
            long out = attendanceDo.getClockOut().getTime();

            int hh = (int) ((out - in) / (1000 * 60 * 60));
            int mm = (int) (((out - in) / 1000 - hh * (60 * 60)) / 60);
            int ss = (int) ((out - in) / 1000 - hh * (60 * 60) - mm * 60);

            attendanceDo.setWorkingHours(hh + ":" + mm + ":" + ss);
            if(hh >= 9) {
                attendanceDo.setState("overtime");
            } else if(hh < 8){
                attendanceDo.setState("excused");
            } else {
                attendanceDo.setState("present");
            }
            attendanceJpaRepository.save(attendanceDo);

            log.debug("{} hh:mm:ss = {}:{}:{}", attendanceDo.getAccountId(), hh, mm, ss);
        } else if(attendanceDo.getClockIn() == null) {
            attendanceDo.setState("forget to clock in");
            attendanceJpaRepository.save(attendanceDo);

            log.debug("forget to clock in");
        } else {
            attendanceDo.setState("forget to clock out");
            attendanceJpaRepository.save(attendanceDo);

            log.debug("forget to clock out");
        }

         */

        return null;
    }

    @Async("ComputeWorkingHoursTaskExecutor")
    public CompletableFuture<Void> computeTimeVer(AttendanceDo attendanceDo) throws InterruptedException {
        if(attendanceDo.getClockIn() != null && attendanceDo.getClockOut() != null) {
            long in = attendanceDo.getClockIn().getTime();
            long out = attendanceDo.getClockOut().getTime();

            int hh = (int) ((out - in) / (1000 * 60 * 60));
            int mm = (int) (((out - in) / 1000 - hh * (60 * 60)) / 60);
            int ss = (int) ((out - in) / 1000 - hh * (60 * 60) - mm * 60);

            attendanceDo.setWorkingHours(Time.valueOf(hh-16 + ":" + mm + ":" + ss));
            if(hh >= 9) {
                attendanceDo.setState("overtime");
            } else if(hh < 8){
                attendanceDo.setState("excused");
            } else {
                attendanceDo.setState("present");
            }
            attendanceJpaRepository.save(attendanceDo);

            log.debug("{} hh:mm:ss = {}:{}:{}", attendanceDo.getAccountId(), hh, mm, ss);
        } else if(attendanceDo.getClockIn() == null) {
            attendanceDo.setState("forget to clock in");
            attendanceJpaRepository.save(attendanceDo);

            log.debug("forget to clock in");
        } else {
            attendanceDo.setState("forget to clock out");
            attendanceJpaRepository.save(attendanceDo);

            log.debug("forget to clock out");
        }

        return null;
    }
}
