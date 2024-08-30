package com.example.project2.controller;

import com.example.project2.pojo.ClockDo;
import com.example.project2.service.Clock2ApiUseCase;
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
    private final Clock2ApiUseCase clock2ApiUseCase;
    @Autowired
    public ClockController(ClockApiUseCase clockApiUseCase, Clock2ApiUseCase clock2ApiUseCase) {
        this.clockApiUseCase = clockApiUseCase;
        this.clock2ApiUseCase = clock2ApiUseCase;
    }

    @PostMapping("/clock")
    public ResponseEntity<?> clock() {
        return clockApiUseCase.clock();
    }

    @PostMapping("/clock2")
    public ResponseEntity<?> clock2() throws Exception {
        return clock2ApiUseCase.clock2();
    }
}
