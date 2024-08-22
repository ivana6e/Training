package com.example.project2.service;

import com.example.project2.dao.UserDao;
import com.example.project2.pojo.RegisterRequest;
import com.example.project2.pojo.RegisterResponse;
import com.example.project2.pojo.UserPojo;
import com.example.project2.util.JwtUtil;
import com.example.project2.util.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class RegisterApiUseCase {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final MessageSource messageSource;
    @Autowired
    public RegisterApiUseCase(UserDao userDao,
                              PasswordEncoder passwordEncoder,
                              JwtUtil jwtUtil,
                              MessageSource messageSource) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.messageSource = messageSource;
    }

    public ResponseEntity<?> createUser(@RequestBody RegisterRequest request) {
        // revise
        if(userDao.findByUsername(request.getUsername()) != null) {
            String msg = messageSource.getMessage(
                    "username.is.taken",
                    new String[]{request.getUsername()},
                    LocaleContextHolder.getLocale()); // get Accept-Language
            return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
        }

        UserPojo userPojo = new UserPojo();
        var encodedPwd = passwordEncoder.encode(request.getPassword());
        userPojo.setUsername(request.getUsername());
        userPojo.setPassword(encodedPwd);
        userPojo.setUserAuthorities(request.getUserAuthorities());
        userDao.save(userPojo);
        UserDetailsImpl user = new UserDetailsImpl(userPojo);
        var jwt = jwtUtil.createLoginAccessToken(user);

        // return ResponseEntity.ok(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(RegisterResponse.of(jwt, user));
    }
}
