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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class ClockErrApiUseCase {

    private final AttendanceJpaRepository attendanceJpaRepository;
    @Autowired
    public ClockErrApiUseCase(AttendanceJpaRepository attendanceJpaRepository) {
        this.attendanceJpaRepository = attendanceJpaRepository;
    }

    @Transactional // (rollbackFor = Exception.class)
    public ResponseEntity<?> clockErr() throws Exception {
        /*var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if("anonymousUser".equals(principal)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are an anonymous user");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String datetime = now.format(formatter);

        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = now.format(dayFormatter);

        // DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss");
        // String time = now.format(timeFormatter);

        var userDetails = (UserDetailsImpl) principal;
        AttendanceUPK attendanceUPK = new AttendanceUPK();
        attendanceUPK.setAccountId(userDetails.getId());
        attendanceUPK.setDate(date);
        var attendancePo = attendanceJpaRepository.findById(attendanceUPK);

        AttendanceDo attendanceDo;
        if(attendancePo.isEmpty()) {
            attendanceDo = new AttendanceDo();
            attendanceDo.setAccountId(userDetails.getId());
            attendanceDo.setDate(date);
            attendanceDo.setClockIn(Timestamp.valueOf(LocalDateTime.now()));
            attendanceDo.setState("failed to clock in");
            attendanceJpaRepository.save(attendanceDo);

            log.info("[clock in time {}] - {} failed to clock in", datetime, userDetails.getAccount());
            log.debug("failed to clocked in");

            // manually
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new Exception("clock in"); // does not work without rollback
            // throw new RuntimeException("clock in");
        }

        return ResponseEntity.noContent().build();
        */

        return null;
    }
}
