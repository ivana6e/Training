package com.example.project2.service;

import com.example.project2.dao.UserDao;
import com.example.project2.pojo.UpdateRequest;
import com.example.project2.pojo.UserDo;
import com.example.project2.util.UserDetailsImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class UpdateApiUseCase {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UpdateApiUseCase(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UpdateRequest request) {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if("anonymousUser".equals(principal)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are an anonymous user");
        }

        var userDetails = (UserDetailsImpl) principal;
        var userPO = userDao.findById(userDetails.getId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String time = now.format(formatter);

        if(userPO.isEmpty()) {
            log.info("[{}] - user does not exist", time);

            return ResponseEntity.notFound().build();
        }

        UserDo userDo = userPO.get();
        var encodedPwd = passwordEncoder.encode(request.getPassword());
        userDo.setUsername(request.getUsername());
        userDo.setPassword(encodedPwd);
        // userDo.setUserAuthorities(request.getUserAuthorities());
        userDao.save(userDo);

        log.info("[{}] - Information of {} is updated", time, request.getUsername());

        return ResponseEntity.ok(userDo);
    }
}
