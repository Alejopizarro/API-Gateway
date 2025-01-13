package com.example.user_service_api.service;

import com.example.user_service_api.commons.entities.UserModel;

public interface UserService {
    UserModel getUser(Long userId, String userIdRequest);
    UserModel updateUser(String userIdRequest, Long userId, UserModel userRequest);
    void deleteUser(String userIdRequest, Long userId);
}
