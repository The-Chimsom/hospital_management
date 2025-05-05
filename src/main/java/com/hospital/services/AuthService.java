package com.hospital.services;

import com.hospital.models.User;
import com.hospital.repositories.UserRepository;
import com.hospital.utils.JWTUtil;
import com.hospital.utils.PasswordUtil;
import com.hospital.factory.UserFactory;

public class AuthService {
    private UserRepository userRepository;

    public AuthService() {
        this.userRepository = new UserRepository();
    }

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (!PasswordUtil.verifyPassword(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        return JWTUtil.generateToken(user);
    }

    public User register(String userType, String username, String password, String email, String id, String additionalInfo) {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        User newUser = UserFactory.createUser(userType, username, password, email, id, additionalInfo);
        userRepository.save(newUser);

        return newUser;
    }

    public User validateToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token is required");
        }

        // Remove "Bearer " prefix if present
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        String username = JWTUtil.validateToken(token);
        return userRepository.findByUsername(username);
    }
}
