package com.example.project2.pojo.user;

import com.example.project2.util.UserDetailsImpl;

import lombok.Getter;

@Getter
public class RegisterResponse {

    private String jwt;
    private Long id;
    private String account;
    private String name;

    public static RegisterResponse of(String jwt, UserDetailsImpl user) {
        var res = new RegisterResponse();
        res.jwt = jwt;
        res.id = user.getId();
        res.account = user.getAccount();
        res.name = user.getName();
        return res;
    }
}
