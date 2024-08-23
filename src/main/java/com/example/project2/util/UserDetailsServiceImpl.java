package com.example.project2.util;

import com.example.project2.dao.UserDao;
import com.example.project2.pojo.UserDo;

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
        UserDo userDo = userDao.findByUsername(username);
        if(userDo == null) {
            throw new UsernameNotFoundException("Can't find user: " + username);
        }

        /*List<SimpleGrantedAuthority> authorities = userDo.getUserAuthorities()
                .stream()
                .map(auth -> new SimpleGrantedAuthority(auth.name()))
                .toList();

        return User
                .withUsername(username)
                .password(userDo.getPassword())
                .authorities(authorities)
                .build();*/

        return new UserDetailsImpl(userDo);
    }
}
