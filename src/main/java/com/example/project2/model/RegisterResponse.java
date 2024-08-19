package com.example.project2.model;

import com.example.project2.util.UserDetailsImpl;

import java.util.List;

public class RegisterResponse {
    private String jwt;
    private Long id;
    private String username;
    private String password;
    private List<UserAuthority> userAuthorities;

    public static RegisterResponse of(String jwt, UserDetailsImpl user) {
        var res = new RegisterResponse();
        res.jwt = jwt;
        res.id = user.getId();
        res.username = user.getUsername();
        res.password = user.getPassword();
        res.userAuthorities = user.getUserAuthorities();

        return res;
    }

    public String getJwt() {
        return jwt;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<UserAuthority> getUserAuthorities() {
        return userAuthorities;
    }
}
