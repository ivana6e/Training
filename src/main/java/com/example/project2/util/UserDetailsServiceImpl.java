package com.example.project2.util;

import com.example.project2.dao.UserJpaRepository;
import com.example.project2.pojo.user.UserDo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserJpaRepository userJpaRepository;
    @Autowired
    public UserDetailsServiceImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        UserDo userDo = userJpaRepository.findByAccount(account);
        if(userDo == null) {
            throw new UsernameNotFoundException("Can't find user: " + account);
        }

        /*List<SimpleGrantedAuthority> authorities = userDo.getUserAuthorities()
                .stream()
                .map(auth -> new SimpleGrantedAuthority(auth.name()))
                .toList();

        return User
                .withUsername(username)
                .password(userDo.getPassword())
                .authorities(authorities)
                .build();
        */

        return new UserDetailsImpl(userDo);
    }
}
