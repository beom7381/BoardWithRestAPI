package com.toy.project.authorize.util;

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
public class JwtUtil {
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(User user) {
        Date issueAt = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(System.currentTimeMillis() + minuteToMilisecond(60));

        return Jwts.builder()
                .setSubject(user.getUserId())
                .claim("userName", user.getUserName())
                .setIssuedAt(issueAt)
                .setExpiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public String getUserId(String token){
        return extractClaims(token).getSubject();
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String resolveToken(String bearer) {
        return (bearer != null && bearer.startsWith("Bearer "))
                ? bearer.substring(7) : null;
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            return true;
        }
        catch (JwtException exception){
            return false;
        }
    }

    public boolean validateToken(String token, User user){
        final String userId = extractClaims(token).getSubject();
        return (userId.equals(user.getUserId()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private long minuteToMilisecond(long minute) {
        return 1000 * 60 * minute;
    }
}
