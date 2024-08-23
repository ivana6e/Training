package com.example.project2.controller;

import com.example.project2.dao.ClockDao;
import com.example.project2.pojo.ClockDo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<?> clock(@RequestBody ClockDo request) {

        var clockPo = clockDao.findById(request.getId());
        if(clockPo.isEmpty()) {
            clockDao.save(request);
            return ResponseEntity.ok(request);
        }

        ClockDo clockDo = clockPo.get();
        clockDo.setClockOut(request.getClockOut());
        clockDao.save(clockDo);
        return ResponseEntity.ok(clockDo);
    }
}
