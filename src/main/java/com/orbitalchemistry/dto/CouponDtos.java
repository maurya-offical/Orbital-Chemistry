package com.orbitalchemistry.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public final class CouponDtos {
    private CouponDtos() {}

    public record CouponRequest(
            @NotBlank(message = "Code is required") @Size(max = 30) @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "Code can use letters, numbers, - and _ only") String code,
            @NotBlank @Pattern(regexp = "PERCENT|FIXED", message = "Type must be PERCENT or FIXED") String type,
            @Min(value = 1, message = "Value must be at least 1") int value,
            boolean active,
            LocalDate expiresAt,
            @Min(value = 1, message = "Max uses must be at least 1") Integer maxUses,
            Long courseId) {}

    public record CouponView(Long id, String code, String type, int value, boolean active,
                             LocalDate expiresAt, Integer maxUses, int usedCount, Long courseId) {}

    public record ValidateRequest(@NotBlank(message = "Enter a coupon code") String code,
                                  @NotNull Long courseId) {}

    public record PriceQuote(String code, int originalPrice, int discount, int finalPrice) {}
}
