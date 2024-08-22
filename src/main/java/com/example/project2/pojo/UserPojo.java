package com.example.project2.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "account")
public class UserPojo {

    public UserPojo() {
        username = null;
        password = null;
        userAuthorities = null;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "username", length = 20, nullable = false)
    private String username;

    @NotBlank
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank
    @Column(name = "authorities", nullable = false)
    private List<UserAuthority> userAuthorities = new ArrayList<>();
}
