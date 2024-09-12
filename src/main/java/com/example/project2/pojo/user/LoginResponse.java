package com.example.project2.pojo.user;

import com.example.project2.util.UserDetailsImpl;

import lombok.Getter;

@Getter
public class LoginResponse {

    private String jwt;
    private Long id;
    private String account;
    private String name;

    public static LoginResponse of(String jwt, UserDetailsImpl user) {
        var res = new LoginResponse();
        res.jwt = jwt;
        res.id = user.getId();
        res.account = user.getAccount();
        res.name = user.getName();
        return res;
    }
}
