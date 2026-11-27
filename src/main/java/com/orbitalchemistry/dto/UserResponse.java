package com.orbitalchemistry.dto;

import com.orbitalchemistry.model.User;

public record UserResponse(Long id, String fullName, String email, String role) {
    public static UserResponse from(User u) {
        return new UserResponse(u.getId(), u.getFullName(), u.getEmail(), u.getRole());
    }
}
