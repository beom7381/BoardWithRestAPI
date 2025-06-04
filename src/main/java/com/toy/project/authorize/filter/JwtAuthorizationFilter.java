package com.toy.project.authorize.filter;

import com.toy.project.authorize.entity.User;
import com.toy.project.authorize.service.UserService;
import com.toy.project.authorize.util.JwtUtil;
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

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public JwtAuthorizationFilter(
            UserService userService,
            JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        boolean debugMode = false;

        try {
            String token = jwtUtil.resolveToken(request.getHeader("Authorization"));

            if (token != null && jwtUtil.validateToken(token)) {
                String userId = jwtUtil.getUserId(token);
                User authenUser = userService.getUserFromUserId(userId);

                UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(authenUser, null, null);

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (JwtException exception) {
            if (debugMode) {
                UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken("hi", null, null);

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                return;
            }

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            SecurityContextHolder.clearContext();
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}
