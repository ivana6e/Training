package com.example.project2.service.user;

import com.example.project2.dao.UserJpaRepository;
import com.example.project2.pojo.user.UpdateRequest;
import com.example.project2.pojo.user.UserDo;
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

    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UpdateApiUseCase(UserJpaRepository userJpaRepository, PasswordEncoder passwordEncoder) {
        this.userJpaRepository = userJpaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UpdateRequest request) {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if("anonymousUser".equals(principal)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are an anonymous user");
        }

        var userDetails = (UserDetailsImpl) principal;
        var userPO = userJpaRepository.findById(userDetails.getId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String time = now.format(formatter);

        if(userPO.isEmpty()) {
            log.info("[update time {}] - user does not exist", time);
            return ResponseEntity.notFound().build();
        }

        UserDo userDo = userPO.get();
        var encodedPwd = passwordEncoder.encode(request.getPassword());
        userDo.setAccount(request.getAccount());
        userDo.setPassword(encodedPwd);
        userDo.setName(request.getName());
        // userDo.setUserAuthorities(request.getUserAuthorities());
        userJpaRepository.save(userDo);

        log.info("[update time {}] - Information of {} is updated", time, request.getAccount());
        return ResponseEntity.ok(userDo);
    }
}
