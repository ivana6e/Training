package com.example.project2.util;

import com.example.project2.pojo.UserAuthority;
import com.example.project2.pojo.UserPojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Setter
@Getter
public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private List<UserAuthority> userAuthorities;

    public UserDetailsImpl() {
    }

    public UserDetailsImpl(UserPojo userPojo) {
        this.id = userPojo.getId();
        this.username = userPojo.getUsername();
        this.password = userPojo.getPassword();
        this.userAuthorities = userPojo.getUserAuthorities();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.userAuthorities
                .stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
