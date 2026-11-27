package com.orbitalchemistry.service;

import com.orbitalchemistry.dto.FeatureDtos.*;
import com.orbitalchemistry.dto.UserResponse;
import com.orbitalchemistry.exception.BadRequestException;
import com.orbitalchemistry.exception.ResourceNotFoundException;
import com.orbitalchemistry.model.PasswordResetToken;
import com.orbitalchemistry.model.User;
import com.orbitalchemistry.repository.PasswordResetTokenRepository;
import com.orbitalchemistry.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.HexFormat;

@Service
@Transactional
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);
    private final UserRepository users;
    private final PasswordResetTokenRepository tokens;
    private final PasswordEncoder encoder;
    private final ObjectProvider<JavaMailSender> mail;
    private final String frontendUrl, from;

    public AccountService(UserRepository users, PasswordResetTokenRepository tokens, PasswordEncoder encoder,
                          ObjectProvider<JavaMailSender> mail,
                          @Value("${app.frontend-url:http://127.0.0.1:5500/index.html}") String frontendUrl,
                          @Value("${app.mail-from:no-reply@orbitalchemistry.com}") String from) {
        this.users = users; this.tokens = tokens; this.encoder = encoder; this.mail = mail;
        this.frontendUrl = frontendUrl; this.from = from;
    }

    public UserResponse updateProfile(String email, ProfileRequest r) {
        User u = user(email);
        u.setFullName(r.fullName().trim());
        return UserResponse.from(u);
    }

    public void changePassword(String email, ChangePasswordRequest r) {
        User u = user(email);
        if (!encoder.matches(r.currentPassword(), u.getPasswordHash()))
            throw new BadRequestException("Current password is incorrect");
        u.setPasswordHash(encoder.encode(r.newPassword()));
    }

    /** Always succeeds from the caller's view, so the endpoint cannot be used to find which emails are registered. */
    public void forgotPassword(String emailRaw) {
        users.findByEmail(emailRaw.trim().toLowerCase()).ifPresent(u -> {
            tokens.deleteByUserId(u.getId());
            byte[] bytes = new byte[32];
            new SecureRandom().nextBytes(bytes);
            String raw = HexFormat.of().formatHex(bytes);
            PasswordResetToken t = new PasswordResetToken();
            t.user = u;
            t.tokenHash = sha256(raw);
            t.expiresAt = Instant.now().plus(Duration.ofMinutes(30));
            tokens.save(t);
            send(u.getEmail(), frontendUrl + "#reset-" + raw);
        });
    }

    public void resetPassword(ResetRequest r) {
        PasswordResetToken t = tokens.findByTokenHash(sha256(r.token()))
                .filter(x -> !x.used && x.expiresAt.isAfter(Instant.now()))
                .orElseThrow(() -> new BadRequestException("This reset link is invalid or has expired"));
        t.user.setPasswordHash(encoder.encode(r.newPassword()));
        t.used = true;
    }

    private void send(String to, String link) {
        JavaMailSender sender = mail.getIfAvailable();
        if (sender == null) {   // SMTP not configured (local development)
            log.info("Mail is not configured. Password reset link for {}: {}", to, link);
            return;
        }
        try {
            SimpleMailMessage m = new SimpleMailMessage();
            m.setFrom(from);
            m.setTo(to);
            m.setSubject("Reset your Orbital Chemistry password");
            m.setText("Use this link to choose a new password (valid for 30 minutes):\n\n" + link
                    + "\n\nIf you did not ask for this, you can ignore this email.");
            sender.send(m);
        } catch (Exception e) {
            log.error("Could not send the reset email", e);
        }
    }

    private User user(String email) {
        return users.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private static String sha256(String s) {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(s.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
