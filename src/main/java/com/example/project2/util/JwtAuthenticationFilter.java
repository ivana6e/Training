package com.example.project2.util;

import com.example.project2.pojo.UserAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtUtil jwtUtil;
    @Autowired
    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // acquire header
        var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // parse JWT
        var jwt = authHeader.substring(BEARER_PREFIX.length());
        Claims claims;
        try {
            claims = jwtUtil.parseToken(jwt);
        }
        catch(JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // build UserDetails object
        var userDetails = new UserDetailsImpl();
        userDetails.setId(Long.valueOf(claims.getSubject()));
        userDetails.setUsername(claims.get("username", String.class));

        var userAuthorities = ((List<String>) claims.get("authorities"))
                .stream()
                .map(UserAuthority::valueOf)
                .toList();
        userDetails.setUserAuthorities(userAuthorities);

        // put into Security Context
        var token = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(token);

        filterChain.doFilter(request, response);
    }
}
