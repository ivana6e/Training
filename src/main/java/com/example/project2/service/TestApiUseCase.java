package com.example.project2.service;

import com.example.project2.util.JwtUtil;
import com.example.project2.util.UserDetailsImpl;

import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@Service
public class TestApiUseCase {

    private final JwtUtil jwtUtil;
    @Autowired
    public TestApiUseCase(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    private static final String BEARER_PREFIX = "Bearer ";

    public Map<String, Object> whoAmI(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        var jwt = authorization.substring(BEARER_PREFIX.length());
        try {
            return jwtUtil.parseToken(jwt);
        }
        catch(JwtException e) {
            throw new BadCredentialsException(e.getMessage(), e);
        }
    }

    public String home() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if("anonymousUser".equals(principal)) {
            return "You are an anonymous user";
        }

        var userDetails = (UserDetailsImpl) principal;
        return String.format("Hi, your\nid: %s\nusername: %s\nauthority：%s",
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getAuthorities()
        );
    }
}
