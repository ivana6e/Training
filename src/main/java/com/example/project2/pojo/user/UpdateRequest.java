package com.example.project2.pojo.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequest {

    private String account;
    private String password;
    private String name;
}
