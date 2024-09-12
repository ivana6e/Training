package com.example.project2.async;

import com.example.project2.dao.LoginInfoJpaRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class LoginInfoTask {

    private final LoginInfoJpaRepository loginInfoJpaRepository;
    @Autowired
    public LoginInfoTask(LoginInfoJpaRepository loginInfoJpaRepository) {
        this.loginInfoJpaRepository = loginInfoJpaRepository;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
    private int count = 1;

    @Async("LoginInfoTaskExecutor")
    public void createLoginData() {
        var name = "User_" + count;
        loginInfoJpaRepository.insert(name);

        log.debug("createLoginData: {}", count);
        count++;
    }

    @Async("LoginInfoTaskExecutor")
    public void notifyLoginUser() {
        log.debug("send login info");

        var activities = loginInfoJpaRepository.findByNotNotified();
        activities.forEach(act -> {
            String timeStr = act.getLoginTime().format(formatter);
            log.debug("dear {}, you log in to the service at {}", act.getName(), timeStr);

            act.setNotified(true);
        });
    }
}
