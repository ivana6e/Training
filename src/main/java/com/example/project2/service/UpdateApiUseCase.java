package com.example.project2.service;

import com.example.project2.dao.UserDao;
import com.example.project2.pojo.UpdateRequest;
import com.example.project2.util.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
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
        if(userPO.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var userPojo = userPO.get();
        var encodedPwd = passwordEncoder.encode(request.getPassword());
        // userModel.setUsername(request.getUsername());
        userPojo.setPassword(encodedPwd);
        userPojo.setUserAuthorities(request.getUserAuthorities());
        userDao.save(userPojo);

        return ResponseEntity.ok(userPojo);
    }
}
