package com.example.auth_services_api.controller;

import com.example.auth_services_api.commons.constants.ApiPathConstants;
import com.example.auth_services_api.commons.dtos.LoginRequest;
import com.example.auth_services_api.commons.dtos.TokenResponse;
import com.example.auth_services_api.commons.dtos.UserRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(ApiPathConstants.V1_ROUTE + ApiPathConstants.AUTH_ROUTE)
public interface AuthApi {
    @PostMapping(value = "/register")
    ResponseEntity<TokenResponse> createUser(
            @RequestBody @Valid UserRequest userRequest
    );
    @PostMapping(value = "/login")
    ResponseEntity<TokenResponse> loginUser(
            @RequestBody @Valid LoginRequest loginRequest
    );
}
