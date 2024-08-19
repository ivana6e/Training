package com.example.project2.util;

import com.example.project2.model.UserAuthority;
import com.example.project2.model.UserModel;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

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

    public Long getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public List<UserAuthority> getUserAuthorities() {
        return this.userAuthorities;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.userAuthorities
                .stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserAuthorities(List<UserAuthority> userAuthorities) {
        this.userAuthorities = userAuthorities;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }
}
