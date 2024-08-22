package com.example.project2.pojo;

import com.example.project2.util.UserDetailsImpl;

import lombok.Getter;

import java.util.List;

@Getter
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
}
