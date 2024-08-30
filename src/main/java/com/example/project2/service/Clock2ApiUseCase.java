package com.example.project2.service;

import com.example.project2.dao.ClockDao;
import com.example.project2.pojo.ClockDo;
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
public class Clock2ApiUseCase {

    private final ClockDao clockDao;
    @Autowired
    public Clock2ApiUseCase(ClockDao clockDao) {
        this.clockDao = clockDao;
    }

    public ResponseEntity<?> clock2() throws Exception{
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if("anonymousUser".equals(principal)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are an anonymous user");
        }

        var userDetails = (UserDetailsImpl) principal;
        var clockPo = clockDao.findById(userDetails.getId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String time = now.format(formatter);

        if(clockPo.isEmpty()) {
            ClockDo clockDo = new ClockDo();
            clockDo.setId(userDetails.getId());
            clockDo.setUsername(userDetails.getUsername());
            clockDo.setClockIn(Timestamp.valueOf(LocalDateTime.now()));
            clockDo.setMessage("failed to clock in");
            clockDao.save(clockDo);

            log.info("[{}] - {} failed to clock in", time, userDetails.getUsername());

            throw new Exception("clock in");
        }

        ClockDo clockDo = clockPo.get();
        if(clockDo.getClockOut() == null) {
            clockDo.setClockOut(Timestamp.valueOf(LocalDateTime.now()));
            clockDo.setMessage("failed to clock out");
            clockDao.save(clockDo);

            log.info("[{}] - {} failed to clocked out", time, userDetails.getUsername());

            throw new Exception("clock out");
        }
        else {
            log.info("[{}] - {} already clocked out", time, userDetails.getUsername());

            return ResponseEntity.ok(clockDo);
        }
    }
}
