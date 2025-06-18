package com.toy.project.global.config;

import com.toy.project.authorize.service.RedisService;
import com.toy.project.authorize.service.UserService;
import com.toy.project.authorize.util.JwtProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserService userService;
    private final RedisService redisService;
    private final JwtProvider jwtUtil;

    public SecurityConfig(UserService userService, RedisService redisService, JwtProvider jwtUtil) {
        this.userService = userService;
        this.redisService = redisService;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth ->
                        auth.anyRequest().permitAll());
                /*
                .authorizeHttpRequests(auth ->
                        auth
                        .requestMatchers("/api/user/signin",
                                "/api/user/signup",
                                "/api/user/refresh",
                                "/api/user/signout",
                                "/swagger-ui/index.html",
                                "/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new AccessTokenFilter(userService, redisService, jwtUtil),
                        UsernamePasswordAuthenticationFilter.class);
*/
        return http.build();
    }
}