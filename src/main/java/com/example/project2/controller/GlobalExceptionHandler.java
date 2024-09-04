package com.example.project2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.ofNullable(ErrorResponse.builder(e, HttpStatus.NOT_ACCEPTABLE, "clock").build());
    }
}
