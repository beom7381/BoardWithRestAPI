package com.toy.project.global.interceptor;

import com.toy.project.authorize.entity.User;
import com.toy.project.authorize.service.RedisService;
import com.toy.project.authorize.service.UserService;
import com.toy.project.authorize.util.JwtProvider;
import com.toy.project.global.annotation.RequireAccessToken;
import com.toy.project.global.exception.AccessTokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtAuthInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            var handlerMethod = (HandlerMethod) handler;

            var annotation = handlerMethod.getMethodAnnotation(RequireAccessToken.class);

            if (annotation != null) {
                try {
                    String token = jwtProvider.resolveAccessToken(request.getHeader("Authorization"));

                    if (token == null || redisService.isAccessTokenBlocked(token)) {
                        SecurityContextHolder.clearContext();
                        throw new AccessTokenExpiredException();
                    }

                    Claims tokenData = jwtProvider.parseToken(token);
                    String userId = jwtProvider.getUserId(tokenData);
                    User authenUser = userService.getUserFromUserId(userId);

                    UsernamePasswordAuthenticationToken authenticationToken
                            = new UsernamePasswordAuthenticationToken(authenUser, null, null);

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } catch (JwtException exception) {
                    SecurityContextHolder.clearContext();
                    throw new AccessTokenExpiredException();
                }
            }
        }

        return true;
    }
}
