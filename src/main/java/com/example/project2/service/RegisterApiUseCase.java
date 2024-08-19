package com.example.project2.service;

import com.example.project2.dao.UserDao;
import com.example.project2.model.RegisterResponse;
import com.example.project2.model.ResponseDto;
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

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<?> createUser(@RequestBody UserModel userModel) {
        if(userDao.findByUsername(userModel.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("{\n\"status\": -1,\n\"errorMessage\": \"Username already exists\"\n}");
        }

        var encodedPwd = passwordEncoder.encode(userModel.getPassword());
        userModel.setPassword(encodedPwd);
        userModel.setId(null);
        userDao.save(userModel);

        UserDetailsImpl user = new UserDetailsImpl(userModel);
        var jwt = jwtUtil.createLoginAccessToken(user);

        // return ResponseEntity.ok(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(RegisterResponse.of(jwt, user));

    }
}
