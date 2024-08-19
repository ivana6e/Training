package com.example.project2.service;

import com.example.project2.model.LoginRequest;
import com.example.project2.model.LoginResponse;
import com.example.project2.util.JwtUtil;
import com.example.project2.util.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class LoginApiUseCase {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public LoginResponse login(@RequestBody LoginRequest request) {
        var token = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        var auth = authenticationManager.authenticate(token);
        var user = (UserDetailsImpl) auth.getPrincipal();

        var jwt = jwtUtil.createLoginAccessToken(user);

        return LoginResponse.of(jwt, user);
    }
}
