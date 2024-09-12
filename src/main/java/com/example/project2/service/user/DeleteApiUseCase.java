package com.example.project2.service.user;

import com.example.project2.dao.UserJpaRepository;
import com.example.project2.util.UserDetailsImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class DeleteApiUseCase {

    private final UserJpaRepository userJpaRepository;
    @Autowired
    public DeleteApiUseCase(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if("anonymousUser".equals(principal)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are an anonymous user");
        }

        var userDetails = (UserDetailsImpl) principal;
        userJpaRepository.deleteById(userDetails.getId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String time = now.format(formatter);
        log.info("[delete time {}] - {} is deleted", time, userDetails.getAccount());

        return ResponseEntity.noContent().build();
    }
}
