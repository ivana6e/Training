package com.example.project2.service;

import com.example.project2.dao.UserDao;
import com.example.project2.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class GetApiUseCase {

    @Autowired
    private UserDao userDao;

    @GetMapping("/users/{id}")
    public ResponseEntity<UserModel> getUser(@PathVariable long id) {
        var userPO = userDao.findById(id);
        return userPO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/users")
    public List<UserModel> getUsers() {
        return userDao.findAll();
    }
}
