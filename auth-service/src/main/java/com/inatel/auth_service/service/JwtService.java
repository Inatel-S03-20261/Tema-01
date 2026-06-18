package com.inatel.auth_service.service;

import com.inatel.auth_service.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final UserService userService;

    private SecretKey getKey() {
        String SECRET = "secretKey_secretKey_secretKey_secretKey";
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String subject) {
        long expirationMillis = 1000 * 60 * 60 * 24; // 1 day
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMillis);
        Role role = userService.getRole(subject);

        return Jwts.builder()
                .subject(subject)
                .claim("role", role)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getKey())
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
