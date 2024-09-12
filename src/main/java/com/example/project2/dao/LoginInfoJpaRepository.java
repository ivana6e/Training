package com.example.project2.dao;

import com.example.project2.pojo.LoginInfoDo;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class LoginInfoJpaRepository {

    private final List<LoginInfoDo> activities = new ArrayList<>();

    public void insert(String name) {
        var activity = LoginInfoDo.of(name, LocalDateTime.now());
        activities.add(activity);
    }

    public List<LoginInfoDo> findByNotNotified() {
        return activities.stream()
                .filter(Predicate.not(LoginInfoDo::isNotified))
                .collect(Collectors.toList());
    }
}
