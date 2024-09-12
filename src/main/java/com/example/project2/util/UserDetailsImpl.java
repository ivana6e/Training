package com.example.project2.util;

import com.example.project2.pojo.user.UserAuthority;
import com.example.project2.pojo.user.UserDo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String account;
    private String password;
    private String name;
    // private List<UserAuthority> userAuthorities;

    public UserDetailsImpl() {
    }

    public UserDetailsImpl(UserDo userDo) {
        this.id = userDo.getId();
        this.account = userDo.getAccount();
        this.password = userDo.getPassword();
        this.name = userDo.getName();
        // this.userAuthorities = userDo.getUserAuthorities();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        /* return this.userAuthorities
                .stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .toList();
        */
        return null;
    }

    @Override
    public String getUsername() {
        return "no username";
    }
}
