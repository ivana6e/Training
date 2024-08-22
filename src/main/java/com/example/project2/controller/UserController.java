package com.example.project2.controller;

import com.example.project2.pojo.*;
import com.example.project2.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final RegisterApiUseCase registerApiUseCase;
    private final LoginApiUseCase loginApiUseCase;
    private final TestApiUseCase testApiUseCase;
    private final GetApiUseCase getApiUseCase;
    private final UpdateApiUseCase updateApiUseCase;
    private final DeleteApiUseCase deleteApiUseCase;
    @Autowired
    public UserController(RegisterApiUseCase registerApiUseCase,
                          LoginApiUseCase loginApiUseCase,
                          TestApiUseCase testApiUseCase,
                          GetApiUseCase getApiUseCase,
                          UpdateApiUseCase updateApiUseCase,
                          DeleteApiUseCase deleteApiUseCase) {
        this.registerApiUseCase = registerApiUseCase;
        this.loginApiUseCase = loginApiUseCase;
        this.testApiUseCase = testApiUseCase;
        this.getApiUseCase = getApiUseCase;
        this.updateApiUseCase = updateApiUseCase;
        this.deleteApiUseCase = deleteApiUseCase;
    }

    // register
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody RegisterRequest request) {
        return registerApiUseCase.createUser(request);
    }

    // login
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return loginApiUseCase.login(request);
    }

    // test
    @GetMapping("/who-am-i")
    public Map<String, Object> whoAmI(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        return testApiUseCase.whoAmI(authorization);
    }
    @GetMapping("/home")
    public String home() {
        return testApiUseCase.home();
    }

    //read
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id) {
        return getApiUseCase.getUser(id);
    }
    @GetMapping("/users")
    public List<UserPojo> getUsers() {
        return getApiUseCase.getUsers();
    }

    // update
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UpdateRequest request) {
        return updateApiUseCase.updateUser(id, request);
    }

    // delete
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return deleteApiUseCase.deleteUser(id);
    }
}
