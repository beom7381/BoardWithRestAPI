package com.toy.project.authorize.filter;

import com.toy.project.authorize.entity.User;
import com.toy.project.authorize.service.RedisService;
import com.toy.project.authorize.service.UserService;
import com.toy.project.authorize.util.JwtProvider;
import com.toy.project.global.exception.AccessTokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
public class AccessTokenFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final RedisService redisService;
    private final JwtProvider jwtProvider;

    public AccessTokenFilter(
            UserService userService,
            RedisService redisService,
            JwtProvider jwtProvider) {
        this.userService = userService;
        this.redisService = redisService;
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtProvider.resolveAccessToken(request.getHeader("Authorization"));

            if (token != null) {
                Claims tokenData = jwtProvider.parseToken(token);

                if(!redisService.isAccessTokenBlocked(token)){
                    String userId = jwtProvider.getUserId(tokenData);

                    User authenUser = userService.getUserFromUserId(userId);

                    UsernamePasswordAuthenticationToken authenticationToken
                            = new UsernamePasswordAuthenticationToken(authenUser, null, null);

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (JwtException exception) {
            SecurityContextHolder.clearContext();

            throw new AccessTokenExpiredException();
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return List.of(
                "/api/user/signup",
                "/api/user/signin",
                "/api/user/signout",
                "/api/user/refresh").contains(path);
    }
}
