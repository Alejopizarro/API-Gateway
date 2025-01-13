package com.example.user_service_api.controller.impl;

import com.example.user_service_api.commons.entities.UserModel;
import com.example.user_service_api.controller.UserApi;
import com.example.user_service_api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserApi {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<UserModel> getUser(String id, Long userId) {
        // Llamamos al servicio y pasamos el userIdRequest junto con el userId
        return ResponseEntity.ok(userService.getUser(userId, id));
    }

    @Override
    public ResponseEntity<UserModel> updateUser(String id, Long userId, UserModel userRequest) {
        // Llamamos al servicio y pasamos el userIdRequest junto con el userId
        return ResponseEntity.ok(userService.updateUser(id, userId, userRequest));
    }

    @Override
    public ResponseEntity<Void> deleteUser(String id, Long userId) {
        // Llamamos al servicio y pasamos el userIdRequest junto con el userId
        userService.deleteUser(id, userId);
        return ResponseEntity.noContent().build();
    }
}
