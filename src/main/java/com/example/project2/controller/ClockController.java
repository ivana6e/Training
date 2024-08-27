package com.example.project2.controller;

import com.example.project2.pojo.ClockDo;
import com.example.project2.service.ClockApiUseCase;

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

    private final ClockApiUseCase clockApiUseCase;
    @Autowired
    public ClockController(ClockApiUseCase clockApiUseCase) {
        this.clockApiUseCase = clockApiUseCase;
    }

    @PostMapping("/clock")
    public ResponseEntity<?> clock(@RequestBody ClockDo request) {
        return clockApiUseCase.clock(request);
    }
}
