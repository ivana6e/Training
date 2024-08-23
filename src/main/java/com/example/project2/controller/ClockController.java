package com.example.project2.controller;

import com.example.project2.dao.ClockDao;
import com.example.project2.pojo.ClockDo;
import com.example.project2.util.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ClockController {

    private final ClockDao clockDao;
    @Autowired
    public ClockController(ClockDao clockDao) {
        this.clockDao = clockDao;
    }

    @PostMapping("/clock")
    public ResponseEntity<?> clock(ClockDo request) {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if("anonymousUser".equals(principal)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are an anonymous user");
        }

        var userDetails = (UserDetailsImpl) principal;
        var clockPo = clockDao.findById(userDetails.getId());
        if(clockPo.isEmpty()) {
            ClockDo clockDo = new ClockDo();
            clockDo.setId(userDetails.getId());
            clockDo.setUsername(userDetails.getUsername());
            clockDao.save(clockDo);
            return ResponseEntity.ok(clockDo);
        }

        ClockDo clockDo = clockPo.get();
        clockDo.setClockOut(request.getClockOut());
        clockDao.save(clockDo);
        return ResponseEntity.ok(clockDo);
    }
}
