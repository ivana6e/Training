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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class ClockErrApiUseCase {

    private final ClockDao clockDao;
    @Autowired
    public ClockErrApiUseCase(ClockDao clockDao) {
        this.clockDao = clockDao;
    }

    @Transactional // (rollbackFor = Exception.class)
    public ResponseEntity<?> clockErr() throws Exception {
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
            clockDo.setClockIn(Timestamp.valueOf(LocalDateTime.now()));
            clockDo.setMessage("failed to clock in");
            clockDao.save(clockDo);

            log.info("[{}] - {} failed to clock in", datetime, userDetails.getUsername());
            log.debug("failed to clocked in");

            // manually
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new Exception("clock in"); // does not work without rollback
            // throw new RuntimeException("clock in");
        }

        return ResponseEntity.noContent().build();
    }
}
