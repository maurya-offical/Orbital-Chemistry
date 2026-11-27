package com.orbitalchemistry.model;

import jakarta.persistence.*;
import java.time.Instant;

/** Only the SHA-256 hash of the token is stored, so a database leak cannot be used to reset passwords. */
@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetToken {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false) @JoinColumn(name = "user_id") public User user;
    @Column(nullable = false, unique = true, length = 64) public String tokenHash;
    @Column(nullable = false) public Instant expiresAt;
    public boolean used = false;
}
