package com.example.project2.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "project2")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 20, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "authorities", nullable = false)
    private List<UserAuthority> userAuthorities = new ArrayList<>();

    /*public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUserAuthorities(List<UserAuthority> authorities) {
        this.userAuthorities = authorities;
    }

    public List<UserAuthority> getUserAuthorities() {
        return userAuthorities;
    }*/
}
