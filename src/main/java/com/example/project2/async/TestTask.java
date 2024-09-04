package com.example.project2.async;

import com.example.project2.dao.TestDao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class TestTask {

    private final TestDao testDao;
    @Autowired
    public TestTask(TestDao testDao) {
        this.testDao = testDao;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
    private int count = 1;

    @Async("asyncTaskExecutor1")
    public void createLoginData() {
        var name = "User_" + count;
        testDao.insert(name);

        log.debug("createLoginData: {}", count);
        count++;
    }

    @Async("asyncTaskExecutor1")
    public void notifyLoginUser() {
        log.debug("send login info");

        var activities = testDao.findByNotNotified();
        activities.forEach(act -> {
            String timeStr = act.getLoginTime().format(formatter);
            log.debug("dear {}, you log in to the service at {}", act.getName(), timeStr);

            act.setNotified(true);
        });
    }
}
