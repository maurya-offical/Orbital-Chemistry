package com.orbitalchemistry.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "enrollments", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "course_id"}))
public class Enrollment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false) @JoinColumn(name = "user_id") public User user;
    @ManyToOne(fetch = FetchType.EAGER, optional = false) @JoinColumn(name = "course_id") public Course course;
    @Column(nullable = false, updatable = false) public Instant enrolledAt = Instant.now();
}
