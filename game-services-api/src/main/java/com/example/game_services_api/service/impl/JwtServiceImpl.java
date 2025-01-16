package com.example.game_services_api.service.impl;

import com.example.game_services_api.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {
    private final String secretKey;

    public JwtServiceImpl(@Value("${jwt.secret}") String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public boolean isExpired(String token) {
        try {
            return getClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public String extractUserId(String token) {
        try {
            return getClaims(token).getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}
