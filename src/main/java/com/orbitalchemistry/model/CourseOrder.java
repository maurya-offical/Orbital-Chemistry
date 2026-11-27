package com.orbitalchemistry.model;

import jakarta.persistence.*;
import java.time.Instant;

/** One purchase attempt. Status: CREATED -> PAID. */
@Entity
@Table(name = "orders")
public class CourseOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false) @JoinColumn(name = "user_id") public User user;
    @ManyToOne(fetch = FetchType.EAGER, optional = false) @JoinColumn(name = "course_id") public Course course;
    public String couponCode;
    @Column(nullable = false) public int amountPaise;
    @Column(nullable = false, length = 10) public String status = "CREATED";
    @Column(unique = true) public String razorpayOrderId;
    public String razorpayPaymentId;
    @Column(nullable = false, updatable = false) public Instant createdAt = Instant.now();
    public Instant paidAt;
}
