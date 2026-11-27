package com.orbitalchemistry.dto;

import jakarta.validation.constraints.*;
import java.time.Instant;

public final class PaymentDtos {
    private PaymentDtos() {}

    public record CreateOrderRequest(@NotNull(message = "Course is required") Long courseId, String couponCode) {}

    /** free = true means no payment was needed and the student is already enrolled. */
    public record CreateOrderResponse(boolean free, String orderId, int amount, String currency,
                                      String keyId, String courseTitle) {}

    public record VerifyRequest(@NotBlank String razorpayOrderId,
                                @NotBlank String razorpayPaymentId,
                                @NotBlank String razorpaySignature) {}

    public record OrderView(Long id, String userEmail, String courseTitle, String couponCode,
                            int amountRupees, String status, Instant createdAt) {}
}
