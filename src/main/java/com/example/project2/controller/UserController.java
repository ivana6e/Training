package com.example.project2.controller;

import com.example.project2.model.*;
import com.example.project2.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    // register
    @Autowired
    private RegisterApiUseCase registerApiUseCase;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody RegisterRequest request) {
        return registerApiUseCase.createUser(request);
    }


    // login
    @Autowired
    private LoginApiUseCase loginApiUseCase;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return loginApiUseCase.login(request);
    }


    // test
    @Autowired
    private TestApiUseCase testApiUseCase;

    @GetMapping("/who-am-i")
    public Map<String, Object> whoAmI(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        return testApiUseCase.whoAmI(authorization);
    }

    @GetMapping("/home")
    public String home() {
        return testApiUseCase.home();
    }


    //------------------------------------------------------------------------------------


    // read
    @Autowired
    private GetApiUseCase getApiUseCase;

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id) {
        return getApiUseCase.getUser(id);
    }

    @GetMapping("/users")
    public List<UserModel> getUsers() {
        return getApiUseCase.getUsers();
    }


    // update
    @Autowired
    private UpdateApiUseCase updateApiUseCase;

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UpdateRequest request) {
        return updateApiUseCase.updateUser(id, request);
    }


    // delete
    @Autowired
    private DeleteApiUseCase deleteApiUseCase;

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return deleteApiUseCase.deleteUser(id);
    }
}
