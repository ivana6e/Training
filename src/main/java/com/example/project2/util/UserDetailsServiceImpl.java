package com.example.project2.util;

import com.example.project2.dao.UserDao;
import com.example.project2.pojo.UserPojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userDao;
    @Autowired
    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPojo userPojo = userDao.findByUsername(username);
        if(userPojo == null) {
            throw new UsernameNotFoundException("Can't find user: " + username);
        }

        /*List<SimpleGrantedAuthority> authorities = userModel.getUserAuthorities()
                .stream()
                .map(auth -> new SimpleGrantedAuthority(auth.name()))
                .toList();

        return User
                .withUsername(username)
                .password(userModel.getPassword())
                .authorities(authorities)
                .build();*/

        return new UserDetailsImpl(userPojo);
    }
}
