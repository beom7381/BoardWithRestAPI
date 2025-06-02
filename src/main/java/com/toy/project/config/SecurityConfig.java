package com.toy.project.config;

import com.toy.project.authorize.filter.JwtAuthorizationFilter;
import com.toy.project.authorize.service.UserService;
import com.toy.project.authorize.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public SecurityConfig(UserService userService, JwtUtil jwtUtil){
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/user/signin", "/api/user/signup").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthorizationFilter(userService, jwtUtil),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}