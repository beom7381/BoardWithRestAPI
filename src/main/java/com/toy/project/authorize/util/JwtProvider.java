package com.toy.project.authorize.util;

import com.toy.project.authorize.constant.CONSTATNS;
import com.toy.project.authorize.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /*
        1. 최초 로그인시 토큰 생성 요청
        2. 리프레시 토큰 생성 및 기반으로 액세스 토큰 생성
        3. 서버 db/redis에 리프레시 토큰 저장 및 프론트에 쿠키:리프레시 토큰, 바디 : 액세스 토큰 전송
        4. 클라이언트에서 뭔가 권한이 필요한 구간에 진입시 필터에서 액세스 토큰 유효성 검사
        5. 유효할 경우 -> SecurityContextHolder만 검사하고 비어있지 않으면 패스
        6. SecurityContextHolder가 비어있다면 넣어주고 패스
        7. 유효하지 않은 경우 -> 리프레시 토큰 유효성 검사
        8. 리프레시 토큰이 유효한 경우 -> 액세스 토큰 재생성 및 전송해주고 패스
        9. 유효하지 않은 경우 꺼져!!
     */
    public String generateRefreshToken(User user){
        Date issueAt = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(System.currentTimeMillis() + CONSTATNS.REFRESH_TOKEN_EXPIRY_DAY);

        return Jwts.builder()
                .setSubject(user.getUserId())
                .setIssuedAt(issueAt)
                .setExpiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public String generateAccessToken(User user) {
        Date issueAt = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(System.currentTimeMillis() + CONSTATNS.ACCESS_TOKEN_EXPIRY_DAY);

        return Jwts.builder()
                .setSubject(user.getUserId())
                .setIssuedAt(issueAt)
                .setExpiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public String getUserId(Claims token){
        return token.getSubject();
    }

    public Claims parseToken(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(Claims token) {
        return token.getExpiration().before(new Date());
    }

    public String resolveAccessToken(String header) {
        String token;

        try {
            token = (header != null && header.startsWith("Bearer "))
                    ? header.substring(7) : null;

        } catch (Exception e) {
            token = null;
        }

        return token;
    }
}
