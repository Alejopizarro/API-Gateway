package com.example.auth_services_api.service;

import com.example.auth_services_api.commons.dtos.TokenResponse;
import io.jsonwebtoken.Claims;

public interface JwtService {
    TokenResponse generateToken(Long userId);

    Claims getClaims(String token);
    boolean isExpired(String token);
    Integer extractedUserId(String token);
}
