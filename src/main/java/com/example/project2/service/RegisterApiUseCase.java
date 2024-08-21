package com.example.project2.service;

import com.example.project2.dao.UserDao;
import com.example.project2.model.RegisterRequest;
import com.example.project2.model.RegisterResponse;
import com.example.project2.model.UserModel;
import com.example.project2.util.I18nUtil;
import com.example.project2.util.JwtUtil;
import com.example.project2.util.UserDetailsImpl;

import jakarta.servlet.http.HttpServletRequest;
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
    private final HttpServletRequest httpServletRequest;
    @Autowired
    public RegisterApiUseCase(UserDao userDao, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, HttpServletRequest httpServletRequest) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.httpServletRequest = httpServletRequest;
    }

    @Autowired
    private MessageSource messageSource;

    public ResponseEntity<?> createUser(@RequestBody RegisterRequest request) {
        // revise
        if(userDao.findByUsername(request.getUsername()) != null) {
            String msg = I18nUtil.getMessage("username.is.taken", httpServletRequest.getHeader("Accept-Language"));
            // String msg = messageSource.getMessage(
            //         "username.is.taken",
            //         new String[]{request.getUsername()},
            //         LocaleContextHolder.getLocale());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
        }

        UserModel userModel = new UserModel();
        var encodedPwd = passwordEncoder.encode(request.getPassword());
        userModel.setUsername(request.getUsername());
        userModel.setPassword(encodedPwd);
        userModel.setUserAuthorities(request.getUserAuthorities());
        userDao.save(userModel);

        UserDetailsImpl user = new UserDetailsImpl(userModel);
        var jwt = jwtUtil.createLoginAccessToken(user);

        // return ResponseEntity.ok(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(RegisterResponse.of(jwt, user));
    }
}
