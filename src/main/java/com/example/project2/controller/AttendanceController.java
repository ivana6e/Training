package com.example.project2.controller;

import com.example.project2.service.attendance.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AttendanceController {

    private final ClockInApiUseCase clockInApiUseCase;
    private final ClockOutApiUseCase clockOutApiUseCase;
    private final ClockErrApiUseCase clockErrApiUseCase;
    private final ClockApiUseCase clockApiUseCase;
    @Autowired
    public AttendanceController(ClockInApiUseCase clockInApiUseCase,
                                ClockOutApiUseCase clockOutApiUseCase,
                                ClockErrApiUseCase clockErrApiUseCase,
                                ClockApiUseCase clockApiUseCase) {
        this.clockInApiUseCase = clockInApiUseCase;
        this.clockOutApiUseCase = clockOutApiUseCase;
        this.clockErrApiUseCase = clockErrApiUseCase;
        this.clockApiUseCase = clockApiUseCase;
    }

    @PostMapping("/clock-in")
    public ResponseEntity<?> clockIn() {
        return clockInApiUseCase.clockIn();
    }

    @PostMapping("/clock-out")
    public ResponseEntity<?> clockOut() { return clockOutApiUseCase.clockOut(); }

    @PostMapping("/clock-err")
    public ResponseEntity<?> clockErr() throws Exception { return clockErrApiUseCase.clockErr(); }

    @PostMapping("/clock")
    public ResponseEntity<?> clock() {
        return clockApiUseCase.clock();
    }
}
