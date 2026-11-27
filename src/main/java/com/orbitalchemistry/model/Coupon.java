package com.orbitalchemistry.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/** Fields are public for brevity. The API returns CouponDtos, not entities. */
@Entity
@Table(name = "coupons")
public class Coupon {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;
    @Column(nullable = false, unique = true, length = 30) public String code;
    @Column(nullable = false, length = 10) public String type;      // PERCENT or FIXED
    @Column(name = "discount_value", nullable = false) public int value;
    public boolean active = true;
    public LocalDate expiresAt;       // null = never expires
    public Integer maxUses;           // null = unlimited
    public int usedCount = 0;
    public Long courseId;             // null = valid for every course
}
