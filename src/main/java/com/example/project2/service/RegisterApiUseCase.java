package com.example.project2.service;

import com.example.project2.dao.UserDao;
import com.example.project2.model.RegisterRequest;
import com.example.project2.model.RegisterResponse;
import com.example.project2.model.UserModel;
import com.example.project2.util.JwtUtil;
import com.example.project2.util.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class RegisterApiUseCase {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    @Autowired
    public RegisterApiUseCase(UserDao userDao, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<?> createUser(@RequestBody RegisterRequest request) {
        // revise
        if(userDao.findByUsername(request.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("{\n\"status\": -1,\n\"errorMessage\": \"Username already exists\"\n}");
        }

        UserModel userModel = new UserModel();
        var encodedPwd = passwordEncoder.encode(request.getPassword());
        userModel.setUsername(request.getUsername());
        userModel.setPassword(encodedPwd);
        userModel.setUserAuthorities(request.getUserAuthorities());
        userDao.save(userModel);

        UserDetailsImpl user = new UserDetailsImpl(userModel);
        var jwt = jwtUtil.createLoginAccessToken(user);

        // return ResponseEntity.ok(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(RegisterResponse.of(jwt, user));
    }
}
