package com.example.auth_services_api.controller.impl;

import com.example.auth_services_api.commons.entities.UserModel;
import com.example.auth_services_api.controller.UserApi;
import com.example.auth_services_api.service.UserService;
import org.springframework.http.ResponseEntity;

public class UserController implements UserApi {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<UserModel> getUser(Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @Override
    public ResponseEntity<UserModel> updateUser(Long userId, UserModel userRequest) {
        return ResponseEntity.ok(userService.updateUser(userId, userRequest));
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
