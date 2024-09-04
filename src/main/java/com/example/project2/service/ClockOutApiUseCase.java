package com.example.project2.service;

import com.example.project2.dao.ClockDao;
import com.example.project2.pojo.ClockDo;
import com.example.project2.pojo.ClockUPK;
import com.example.project2.util.UserDetailsImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class ClockOutApiUseCase {

    private final ClockDao clockDao;
    @Autowired
    public ClockOutApiUseCase(ClockDao clockDao) {
        this.clockDao = clockDao;
    }

    public ResponseEntity<?> clockOut() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if("anonymousUser".equals(principal)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are an anonymous user");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String datetime = now.format(formatter);

        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String date = now.format(dayFormatter);

        // DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss");
        // String time = now.format(timeFormatter);

        var userDetails = (UserDetailsImpl) principal;
        ClockUPK clockUPK = new ClockUPK();
        clockUPK.setId(userDetails.getId());
        clockUPK.setDate(date);
        var clockPo = clockDao.findById(clockUPK);

        ClockDo clockDo;
        if(clockPo.isEmpty()) {
            clockDo = new ClockDo();
            clockDo.setId(userDetails.getId());
            clockDo.setDate(date);
            clockDo.setClockOut(Timestamp.valueOf(LocalDateTime.now()));
            // clockDo.setClockIn(Timestamp.valueOf(time));

            clockDao.save(clockDo);

            log.info("[{}] - {} clocked out successfully(no clock in)", datetime, userDetails.getUsername());
            log.debug("clocked out successfully(no clock in)");
        } else {
            clockDo = clockPo.get();

            if(clockDo.getClockOut() == null) {
                clockDo.setClockOut(Timestamp.valueOf(LocalDateTime.now()));
                clockDao.save(clockDo);

                log.info("[{}] - {} clocked out successfully", datetime, userDetails.getUsername());
                log.debug("clocked out successfully");

            } else {
                log.info("[{}] - {} already clocked out", datetime, userDetails.getUsername());
                log.debug("already clocked out");
            }
        }

        return ResponseEntity.ok(clockDo);
    }
}
