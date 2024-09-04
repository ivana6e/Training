package com.example.project2.pojo;

import com.example.project2.util.UserDetailsImpl;

import lombok.Getter;

import java.util.List;

@Getter
public class RegisterResponse {

    private String jwt;
    private Long id;
    private String username;
    // private String password;
    private String name;
    // private List<UserAuthority> userAuthorities;

    public static RegisterResponse of(String jwt, UserDetailsImpl user) {
        var res = new RegisterResponse();
        res.jwt = jwt;
        res.id = user.getId();
        res.username = user.getUsername();
        // res.password = user.getPassword();
        res.name = user.getName();
        // res.userAuthorities = user.getUserAuthorities();
        return res;
    }
}
