package com.example.user_service_api.service.impl;

import com.example.user_service_api.commons.entities.UserModel;
import com.example.user_service_api.repository.UserRepository;
import com.example.user_service_api.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserModel getUser(Long userId, String id) {
        validateUserAccess(userId, id);
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Error: Couldn't find user"));
    }

    @Override
    public UserModel updateUser(String id, Long userId, UserModel userRequest) {
        validateUserAccess(userId, id);
        return userRepository.findById(userId)
                .map(existingUser -> updateEntity(existingUser, userRequest))
                .map(userRepository::save)
                .orElseThrow(() -> new RuntimeException("Error: Couldn't update user"));
    }

    @Override
    public void deleteUser(String id, Long userId) {
        validateUserAccess(userId, id);
        userRepository.findById(userId)
                .ifPresent(userRepository::delete);
    }

    private void validateUserAccess(Long userId, String id) {
        if (!userId.toString().equals(id)) {
            throw new RuntimeException("Error: User ID in request does not match the authenticated user.");
        }
    }

    private UserModel updateEntity(UserModel existingUser, UserModel userRequest) {
        // Solo actualizamos los campos que no son null en el request
        if (userRequest.getName() != null) {
            existingUser.setName(userRequest.getName());
        }
        if (userRequest.getEmail() != null) {
            existingUser.setEmail(userRequest.getEmail());
        }
        if (userRequest.getPassword() != null) {
            existingUser.setPassword(userRequest.getPassword());
        }
        if (userRequest.getRole() != null) {
            existingUser.setRole(userRequest.getRole());
        }
        return existingUser;
    }}
