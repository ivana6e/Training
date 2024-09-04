package com.example.project2.dao;

import com.example.project2.pojo.LoginActivity;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class TestDao {

    private final List<LoginActivity> activities = new ArrayList<>();

    public void insert(String name) {
        var activity = LoginActivity.of(name, LocalDateTime.now());
        activities.add(activity);
    }

    public List<LoginActivity> findByNotNotified() {
        return activities.stream()
                .filter(Predicate.not(LoginActivity::isNotified))
                .collect(Collectors.toList());
    }
}
