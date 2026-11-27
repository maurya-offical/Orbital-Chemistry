package com.orbitalchemistry.controller;

import com.orbitalchemistry.dto.FeatureDtos.*;
import com.orbitalchemistry.dto.UserResponse;
import com.orbitalchemistry.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AccountController {

    private final AccountService service;
    public AccountController(AccountService service) { this.service = service; }

    @PutMapping("/profile")
    public UserResponse profile(@Valid @RequestBody ProfileRequest r, Authentication auth) {
        return service.updateProfile(auth.getName(), r);
    }

    @PostMapping("/change-password")
    public Map<String, String> change(@Valid @RequestBody ChangePasswordRequest r, Authentication auth) {
        service.changePassword(auth.getName(), r);
        return Map.of("message", "Password changed");
    }

    @PostMapping("/forgot-password")
    public Map<String, String> forgot(@Valid @RequestBody ForgotRequest r) {
        service.forgotPassword(r.email());
        return Map.of("message", "If that email is registered, a reset link has been sent. It is valid for 30 minutes.");
    }

    @PostMapping("/reset-password")
    public Map<String, String> reset(@Valid @RequestBody ResetRequest r) {
        service.resetPassword(r);
        return Map.of("message", "Password changed");
    }
}
