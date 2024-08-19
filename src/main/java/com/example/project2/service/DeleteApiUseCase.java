package com.example.project2.service;

import com.example.project2.dao.UserDao;

import com.example.project2.util.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class DeleteApiUseCase {

    private final UserDao userDao;
    @Autowired
    public DeleteApiUseCase(UserDao userDao) {
        this.userDao = userDao;
    }

    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ("anonymousUser".equals(principal)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are an anonymous user");
        }

        var userDetails = (UserDetailsImpl) principal;

        userDao.deleteById(userDetails.getId());
        return ResponseEntity.noContent().build();
    }
}
