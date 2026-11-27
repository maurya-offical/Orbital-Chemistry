package com.orbitalchemistry.dto;

import jakarta.validation.constraints.*;

public record SignupRequest(
        @NotBlank(message = "Full name is required")
        @Size(max = 100, message = "Name must be 100 characters or fewer")
        String fullName,

        @NotBlank(message = "Email is required")
        @Email(message = "Enter a valid email address")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 72, message = "Password must be 8 to 72 characters")
        String password
) {}
