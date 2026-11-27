package com.orbitalchemistry.controller;

import com.orbitalchemistry.dto.PaymentDtos.*;
import com.orbitalchemistry.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PaymentController {

    private final PaymentService service;
    public PaymentController(PaymentService service) { this.service = service; }

    @PostMapping("/api/payments/orders")
    public CreateOrderResponse create(@Valid @RequestBody CreateOrderRequest r, Authentication auth) {
        return service.createOrder(auth.getName(), r);
    }

    @PostMapping("/api/payments/verify")
    public Map<String, String> verify(@Valid @RequestBody VerifyRequest r, Authentication auth) {
        service.verify(auth.getName(), r);
        return Map.of("message", "Payment successful. You are enrolled.");
    }

    /** Ids of the courses the logged-in student has bought. */
    @GetMapping("/api/payments/my")
    public List<Long> mine(Authentication auth) { return service.myCourseIds(auth.getName()); }

    /** Called by Razorpay, not by the browser. Public, but protected by the signature check. */
    @PostMapping("/api/payments/webhook")
    public ResponseEntity<Void> webhook(@RequestBody String body,
                                        @RequestHeader(value = "X-Razorpay-Signature", required = false) String sig) {
        service.webhook(body, sig);
        return ResponseEntity.ok().build();
    }

}
