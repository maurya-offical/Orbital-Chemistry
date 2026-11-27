package com.orbitalchemistry.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "contact_messages")
public class ContactMessage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;
    @Column(nullable = false, length = 100) public String name;
    @Column(nullable = false, length = 150) public String email;
    @Column(nullable = false, columnDefinition = "TEXT") public String message;
    @Column(nullable = false, updatable = false) public Instant createdAt = Instant.now();
    public boolean handled = false;
}
