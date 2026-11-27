package com.orbitalchemistry.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/** Fields are public for brevity. The API never returns entities directly (see CourseDtos). */
@Entity
@Table(name = "courses")
public class Course {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;
    @Column(nullable = false) public String title;
    @Column(nullable = false, length = 40) public String category;
    @Column(columnDefinition = "TEXT") public String description;
    @Column(nullable = false) public int price;
    public Integer originalPrice;
    @Column(length = 300) public String thumbnailUrl;   // /uploads/xxx.jpg or an https:// image address
    @Column(length = 20) public String color = "#2D4BFF";
    @Column(length = 10) public String badge;
    public boolean published = false;
    @Column(nullable = false, updatable = false) public Instant createdAt = Instant.now();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC, id ASC")
    public List<Section> sections = new ArrayList<>();
}
