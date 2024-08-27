package com.example.project2.service;

import com.example.project2.dao.UserDao;
import com.example.project2.pojo.RegisterRequest;
import com.example.project2.pojo.RegisterResponse;
import com.example.project2.pojo.UserDo;
import com.example.project2.util.JwtUtil;
import com.example.project2.util.UserDetailsImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
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

        UserDo userDo = new UserDo();
        var encodedPwd = passwordEncoder.encode(request.getPassword());
        userDo.setUsername(request.getUsername());
        userDo.setPassword(encodedPwd);
        userDo.setUserAuthorities(request.getUserAuthorities());
        userDao.save(userDo);
        UserDetailsImpl user = new UserDetailsImpl(userDo);
        var jwt = jwtUtil.createLoginAccessToken(user);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String time = now.format(formatter);
        log.info("[{}] - {} registers", time, request.getUsername());

        // return ResponseEntity.ok(userDo);
        return ResponseEntity.status(HttpStatus.OK).body(RegisterResponse.of(jwt, user));
    }
}
