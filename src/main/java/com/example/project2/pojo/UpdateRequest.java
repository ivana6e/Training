package com.example.project2.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateRequest {

    private String username;
    private String password;
    private String name;
    // private List<UserAuthority> userAuthorities;
}
