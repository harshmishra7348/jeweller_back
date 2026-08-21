package com.example.tea.Configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {
    // Externalized so the signing key is not baked into source control.
    @Value("${app.jwt.secret:change-me-to-a-long-random-secret-at-least-32-chars}")
    private String secret;
    @Value("${app.jwt.expiration-ms:10800000}")
    private long expireTime;
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public String generateToken(String username) {
         String toekn = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
         return toekn;
    }

    public boolean validateToken(String token) {
        try {
            if(!isTokenExpired(token)) {
                Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token);
                return true;
            }else{
                return false;
            }
        } catch (JwtException | IllegalArgumentException e) {
            // Invalid token or expired token
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            Date expirationDate = claims.getExpiration();
            return expirationDate.before(new Date()); // If the expiration date is before current date, token is expired
        } catch (JwtException e) {
            // Handle invalid token parsing here if needed (e.g., logging)
            return true; // If the token is invalid, consider it expired
        }
    }
}
