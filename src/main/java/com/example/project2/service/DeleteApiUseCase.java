package com.example.project2.service;

import com.example.project2.dao.UserDao;
import com.example.project2.util.UserDetailsImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class DeleteApiUseCase {

    private final UserDao userDao;
    @Autowired
    public DeleteApiUseCase(UserDao userDao) {
        this.userDao = userDao;
    }

    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if("anonymousUser".equals(principal)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are an anonymous user");
        }

        var userDetails = (UserDetailsImpl) principal;
        userDao.deleteById(userDetails.getId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String time = now.format(formatter);
        log.info("[{}] - {} is deleted", time, userDetails.getUsername());

        return ResponseEntity.noContent().build();
    }
}
