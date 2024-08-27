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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class ClockApiUseCase {

    private final ClockDao clockDao;
    @Autowired
    public ClockApiUseCase(ClockDao clockDao) {
        this.clockDao = clockDao;
    }

    public ResponseEntity<?> clock(ClockDo request) {
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
            clockDao.save(clockDo);

            log.info("[{}] - {} clocks in successfully", time, userDetails.getUsername());

            return ResponseEntity.ok(clockDo);
        }

        ClockDo clockDo = clockPo.get();
        clockDo.setClockOut(request.getClockOut());
        clockDao.save(clockDo);

        log.info("[{}] - {} clocks out successfully", time, userDetails.getUsername());

        return ResponseEntity.ok(clockDo);
    }
}
