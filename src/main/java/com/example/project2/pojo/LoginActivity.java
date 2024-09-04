package com.example.project2.pojo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoginActivity {

    private String name;
    private LocalDateTime loginTime;
    @Getter
    private boolean notified = false;

    public static LoginActivity of(String name, LocalDateTime loginTime) {
        var activity = new LoginActivity();
        activity.name = name;
        activity.loginTime = loginTime;
        return activity;
    }
}