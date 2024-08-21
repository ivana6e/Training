package com.example.project2.controller;

import com.example.project2.util.I18nUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class JustController {

    private final HttpServletRequest request;

    @GetMapping("/i18n")
    public String i18n() {
        String message1 = I18nUtil.getMessage("A00001", request.getHeader("Accept-Language"));
        String message2 = I18nUtil.getMessage("A00002", request.getHeader("Accept-Language"));

        return message1+message2;
    }
}
