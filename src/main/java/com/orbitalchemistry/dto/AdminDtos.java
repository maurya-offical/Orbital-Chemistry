package com.orbitalchemistry.dto;

import com.orbitalchemistry.dto.PaymentDtos.OrderView;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.Instant;
import java.util.List;

public final class AdminDtos {
    private AdminDtos() {}

    public record Stats(long totalStudents, long totalCourses, long publishedCourses,
                        long paidOrders, long revenueRupees, long openMessages) {}
    public record UserRow(Long id, String fullName, String email, String role, boolean blocked, Instant createdAt) {}
    public record RoleRequest(@NotBlank @Pattern(regexp = "ADMIN|STUDENT", message = "Role must be ADMIN or STUDENT") String role) {}
    public record BlockRequest(boolean blocked) {}
    public record OrderPage(List<OrderView> items, int page, int totalPages, long total) {}
}
