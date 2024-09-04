package com.example.project2.controller;

import com.example.project2.service.ClockErrApiUseCase;
import com.example.project2.service.ClockInApiUseCase;
import com.example.project2.service.ClockOutApiUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ClockController {

    private final ClockInApiUseCase clockInApiUseCase;
    private final ClockOutApiUseCase clockOutApiUseCase;
    private final ClockErrApiUseCase clockErrApiUseCase;
    @Autowired
    public ClockController(ClockInApiUseCase clockInApiUseCase,
                           ClockOutApiUseCase clockOutApiUseCase,
                           ClockErrApiUseCase clockErrApiUseCase) {
        this.clockInApiUseCase = clockInApiUseCase;
        this.clockOutApiUseCase = clockOutApiUseCase;
        this.clockErrApiUseCase = clockErrApiUseCase;
    }

    @PostMapping("/clock-in")
    public ResponseEntity<?> clockIn() {
        return clockInApiUseCase.clockIn();
    }

    @PostMapping("/clock-out")
    public ResponseEntity<?> clockOut() { return clockOutApiUseCase.clockOut(); }

    @PostMapping("/clock-err")
    public ResponseEntity<?> clockErr() throws Exception { return clockErrApiUseCase.clockErr(); }
}
