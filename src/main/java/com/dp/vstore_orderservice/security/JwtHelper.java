package com.dp.vstore_orderservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtHelper {
    @Value("${jwt.secret}")
    private String SECRET_KEY_STRING;
    private SecretKey SECRET_KEY;

    @PostConstruct
    public void init() {
        SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getPayload();
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = extractClaims(token);
            return (!isExpired(claims));
        }
        catch (Exception e) {
            return false;
        }
    }

    private boolean isExpired(Claims claims) {
        try {
            return claims.getExpiration().before(new Date());
        }
        catch(ExpiredJwtException e) {
            return false;
        }
    }
}
