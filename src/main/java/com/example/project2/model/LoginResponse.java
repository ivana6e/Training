package com.example.project2.model;

import com.example.project2.util.UserDetailsImpl;

import java.util.List;

public class LoginResponse {
    private String jwt;
    private Long id;
    private String username;
    private List<UserAuthority> userAuthorities;

    public static LoginResponse of(String jwt, UserDetailsImpl user) {
        var res = new LoginResponse();
        res.jwt = jwt;
        res.id = user.getId();
        res.username = user.getUsername();
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

    public List<UserAuthority> getUserAuthorities() {
        return userAuthorities;
    }
}
