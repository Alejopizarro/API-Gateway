package com.example.game_services_api.service;

import io.jsonwebtoken.Claims;

public interface JwtService {
        Claims getClaims(String token);
        boolean isExpired(String token);
        String extractUserId(String token);
}
