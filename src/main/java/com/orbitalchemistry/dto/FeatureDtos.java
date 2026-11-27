package com.orbitalchemistry.dto;

import jakarta.validation.constraints.*;
import java.time.Instant;
import java.util.List;

public final class FeatureDtos {
    private FeatureDtos() {}

    public record ProfileRequest(@NotBlank(message = "Name is required") @Size(max = 100) String fullName) {}
    public record ChangePasswordRequest(@NotBlank(message = "Enter your current password") String currentPassword,
                                        @NotBlank @Size(min = 8, max = 72, message = "New password must be 8 to 72 characters") String newPassword) {}
    public record ForgotRequest(@NotBlank @Email(message = "Enter a valid email") String email) {}
    public record ResetRequest(@NotBlank String token,
                               @NotBlank @Size(min = 8, max = 72, message = "Password must be 8 to 72 characters") String newPassword) {}

    public record ContactRequest(@NotBlank(message = "Name is required") @Size(max = 100) String name,
                                 @NotBlank @Email(message = "Enter a valid email") @Size(max = 150) String email,
                                 @NotBlank(message = "Message is required") @Size(max = 2000, message = "Message is too long") String message) {}
    public record ContactView(Long id, String name, String email, String message, Instant createdAt, boolean handled) {}

    public record ProgressRequest(boolean completed) {}
    public record ProgressView(Long courseId, List<Long> doneLessonIds) {}
    public record ProgressSummary(Long courseId, int done, int total) {}
}
