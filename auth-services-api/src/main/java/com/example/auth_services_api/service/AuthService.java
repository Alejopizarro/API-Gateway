package com.example.auth_services_api.service;

import com.example.auth_services_api.commons.dtos.LoginRequest;
import com.example.auth_services_api.commons.dtos.TokenResponse;
import com.example.auth_services_api.commons.dtos.UserRequest;

public interface AuthService {
    TokenResponse createUser(UserRequest userRequest);
    TokenResponse loginUser(LoginRequest loginRequest);
}
