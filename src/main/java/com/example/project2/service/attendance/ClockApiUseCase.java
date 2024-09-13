package com.example.project2.service.attendance;

import com.example.project2.dao.AttendanceJpaRepository;
import com.example.project2.pojo.attendance.AttendanceDo;
import com.example.project2.pojo.attendance.AttendanceUPK;
import com.example.project2.util.UserDetailsImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class ClockApiUseCase {

    private final AttendanceJpaRepository attendanceJpaRepository;
    @Autowired
    public ClockApiUseCase(AttendanceJpaRepository attendanceJpaRepository) {
        this.attendanceJpaRepository = attendanceJpaRepository;
    }

    public ResponseEntity<?> clock() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if("anonymousUser".equals(principal)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are an anonymous user");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String datetime = now.format(formatter);

        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = now.format(dayFormatter);

        var userDetails = (UserDetailsImpl) principal;
        AttendanceUPK attendanceUPK = new AttendanceUPK();
        attendanceUPK.setAccountId(userDetails.getId());
        attendanceUPK.setDate(Date.valueOf(date));
        var attendancePo = attendanceJpaRepository.findById(attendanceUPK);

        AttendanceDo attendanceDo;
        if(attendancePo.isEmpty()) {
            attendanceDo = new AttendanceDo();
            attendanceDo.setAccountId(userDetails.getId());
            attendanceDo.setDate(Date.valueOf(date));
            if(LocalTime.now().isBefore(LocalTime.parse("12:00:00"))) {
                attendanceDo.setClockIn(Timestamp.valueOf(LocalDateTime.now()));

                log.info("[clock in time {}] - {} clocked in successfully", datetime, userDetails.getAccount());
                log.debug("clocked in successfully");
            } else {
                attendanceDo.setClockOut(Timestamp.valueOf(LocalDateTime.now()));

                log.info("[clock out time {}] - {} clocked out successfully(no clock in)", datetime, userDetails.getAccount());
                log.debug("clocked out successfully(no clock in)");
            }
            attendanceJpaRepository.save(attendanceDo);
        } else {
            attendanceDo = attendancePo.get();

            attendanceDo.setClockOut(Timestamp.valueOf(LocalDateTime.now()));
            attendanceJpaRepository.save(attendanceDo);

            log.info("[clock out time {}] - {} clocked out successfully", datetime, userDetails.getAccount());
            log.debug("clocked out successfully");
        }

        return ResponseEntity.ok(attendanceDo);
    }
}
