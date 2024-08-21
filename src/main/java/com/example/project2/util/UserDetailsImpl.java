package com.example.project2.util;

import com.example.project2.model.UserAuthority;
import com.example.project2.model.UserModel;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Setter
@Getter
public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private List<UserAuthority> userAuthorities;

    public UserDetailsImpl() {
    }

    public UserDetailsImpl(UserModel userModel) {
        this.id = userModel.getId();
        this.username = userModel.getUsername();
        this.password = userModel.getPassword();
        this.userAuthorities = userModel.getUserAuthorities();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.userAuthorities
                .stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
